package com.springboottest.demo.controller.Topic_Handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.springboottest.demo.controller.User_Account_Handler;
import com.springboottest.demo.entity.Browse_History;
import com.springboottest.demo.entity.Topic_Info.Tag_Info;
import com.springboottest.demo.repository.Browse_History_Repo;
import com.springboottest.demo.repository.Topic_Repo.Tag_Info_Repo;
import com.springboottest.demo.repository.User_Account_Repo;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Topic_Info/Tag_Info")
@CrossOrigin
public class Tag_Info_Handler {
    @Autowired
    Tag_Info_Repo tag_info_repo;

    @Autowired
    private User_Account_Repo user_account_repo;

    @Autowired
    private Browse_History_Repo browse_history_repo;

    private String topic_name = "Tag Information";
    @SuppressWarnings("all")
    private final String Data_Crawl_URL = "https://api.stackexchange.com/2.3/tags?page=1&pagesize=100&order=desc&sort=popular&site=stackoverflow";

    @GetMapping("/Get_Tag_Rank")// 有二十个热度前20的Tag
    public List<Tag_Info> Get_Tag_Rank(){
        user_account_repo.update_search_cnt(User_Account_Handler.Current_User_Account.getUser_name());
        browse_history_repo.save(new Browse_History(
                User_Account_Handler.Current_User_Account.getUser_name(),
                topic_name,
                Browse_History.df.format(System.currentTimeMillis())));

        return tag_info_repo.findAll();
    }

    @PostMapping("/Crawler_Insert")
    public void Crawl_Insert() throws IOException {
        tag_info_repo.deleteAll();
        String json_String_Content = Jsoup.connect(Data_Crawl_URL).ignoreContentType(true).get().text();

        JsonObject jsonObject = JsonParser.parseString(json_String_Content).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("items").getAsJsonArray();

        List<Tag_Info> list = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            JsonObject object = jsonElement.getAsJsonObject();
            list.add(new Tag_Info(object.get("name").getAsString(), object.get("count").getAsInt()));
        }

        tag_info_repo.saveAll(list);
    }
}
