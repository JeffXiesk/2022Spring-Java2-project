package com.springboottest.demo.controller.Topic_Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.*;
import com.springboottest.demo.controller.TextProcessor;
import com.springboottest.demo.controller.User_Account_Handler;
import com.springboottest.demo.entity.Browse_History;
import com.springboottest.demo.repository.Browse_History_Repo;
import com.springboottest.demo.repository.User_Account_Repo;
import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Topic_Info/StackOverflow_Info")
@CrossOrigin
public class Search_Tagged_Info_Handler {
    @Autowired
    private User_Account_Repo user_account_repo;

    @Autowired
    private Browse_History_Repo browse_history_repo;

    private final String topic_name = "Search Tag Information";
    private final String topic_name2 = "Search Tag Popular Word";
    private String Data_Crawl_URL;

    @GetMapping("/Select_Latest_ten_quesionts/{tag}")
    public List<Object[]> Select_Latest_ten_quesionts(@PathVariable("tag") String tag) throws IOException {
        user_account_repo.update_search_cnt(User_Account_Handler.Current_User_Account.getUser_name());
        browse_history_repo.save(new Browse_History(
                User_Account_Handler.Current_User_Account.getUser_name(),
                topic_name,
                Browse_History.df.format(System.currentTimeMillis())));

        Data_Crawl_URL = "https://api.stackexchange.com/2.3/search?pagesize=10&order=desc&sort=activity&tagged=" + tag + "&site=stackoverflow";
        String json_String_Content = Jsoup.connect(Data_Crawl_URL).ignoreContentType(true).get().text();

        // 谢绍康的处理一下字符串的方法
        json_String_Content = pre_process(json_String_Content);

        JsonObject jsonObject = JsonParser.parseString(json_String_Content).getAsJsonObject();
        JsonArray items = jsonObject.get("items").getAsJsonArray();

        List<Object[]> result = new ArrayList<>();
        for (JsonElement jsonElement : items) {
            JsonObject jsonObject1 = jsonElement.getAsJsonObject();
            List<String> list = new ArrayList<>();
            for (JsonElement jsonElement1 : jsonObject1.get("tags").getAsJsonArray()) {
                list.add(jsonElement1.getAsString());
            }
            int view_count = jsonObject1.get("view_count").getAsInt();
            String link = jsonObject1.get("link").getAsString();
            String title = jsonObject1.get("title").getAsString();

            Object[] temp = new Object[4];
            temp[0] = list;
            temp[1] = view_count;
            temp[2] = link;
            temp[3] = title;
            result.add(temp);
        }
        return result;
    }

    @GetMapping("/Select_Popular_Words/{tag}")
    public List<Pair<String, Integer>> Popular_Words(@PathVariable("tag") String tag) throws IOException {
        user_account_repo.update_search_cnt(User_Account_Handler.Current_User_Account.getUser_name());
        browse_history_repo.save(new Browse_History(
                User_Account_Handler.Current_User_Account.getUser_name(),
                topic_name2,
                Browse_History.df.format(System.currentTimeMillis())));

        StringBuilder stringBuilder = new StringBuilder();

        boolean has_more = true;
        int page = 1;
        while (has_more && page <= 10) {
            Data_Crawl_URL = "https://api.stackexchange.com/2.3/search?page=" + page++ + "&pagesize=100&order=desc&sort=activity&tagged="+tag+"&site=stackoverflow";
            String json_String_Content = Jsoup.connect(Data_Crawl_URL).ignoreContentType(true).get().text();

            // 谢绍康的处理一下字符串的方法
            json_String_Content = pre_process(json_String_Content);

            JsonObject jsonObject = JsonParser.parseString(json_String_Content).getAsJsonObject();
            JsonArray items = jsonObject.get("items").getAsJsonArray();
            for (JsonElement jsonElement : items) {
                JsonObject jsonObject1 = jsonElement.getAsJsonObject();
                String title = jsonObject1.get("title").getAsString();
                stringBuilder.append(title).append(" ");
            }

            has_more = jsonObject.get("has_more").getAsString().equals("true");
        }
        TextProcessor textProcessor = new TextProcessor(stringBuilder.toString());
        return textProcessor.getCommonWords();
    }

    public String pre_process(String a) {
        List<Integer> invalid_quote_index = new ArrayList<>();
        boolean help = false;
        StringBuilder sb = new StringBuilder(a);
        for (int i = 0; i < a.length() - 10; i++) {
            if (a.substring(i, i + 9).equals("\"title\":\"")) {
                help = true;
                i += 8;
                continue;
            }

            if (help) {
                if (a.charAt(i) == '\"') {
                    if (a.substring(i, i + 2).equals("\"}")) {
                        help = false;
                        i += 1;
                    } else {
                        invalid_quote_index.add(i);
                    }
                }
            }
        }
        for (Integer invalidQuoteIndex : invalid_quote_index) {
            sb.replace(invalidQuoteIndex, invalidQuoteIndex + 1, " ");
        }
        return sb.toString();
    }
}