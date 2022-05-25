package com.springboottest.demo.controller.Topic_Handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.springboottest.demo.controller.*;
import com.springboottest.demo.entity.Browse_History;
import com.springboottest.demo.entity.Topic_Info.Privileges_Info;
import com.springboottest.demo.repository.Browse_History_Repo;
import com.springboottest.demo.repository.Topic_Repo.Privileges_Info_Repo;
import com.springboottest.demo.repository.User_Account_Repo;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/Topic_Info/Privileges_Info")
@CrossOrigin
public class Privileges_Info_Handler {
    @Autowired
    private Privileges_Info_Repo privileges_info_repo;

    @Autowired
    private User_Account_Repo user_account_repo;

    @Autowired
    private Browse_History_Repo browse_history_repo;

    @SuppressWarnings("all")
    private final String topic_name = "Privilege Level";
    @SuppressWarnings("all")
    private final String Data_Crawl_URL = "https://api.stackexchange.com/2.3/privileges?site=stackoverflow";

    @GetMapping("/Get_Privileges_Info")
    public List<Privileges_Info> Get_Popular_Collectives() {
        user_account_repo.update_search_cnt(User_Account_Handler.Current_User_Account.getUser_name());
        browse_history_repo.save(new Browse_History(
                User_Account_Handler.Current_User_Account.getUser_name(),
                topic_name,
                Browse_History.df.format(System.currentTimeMillis())));

        List<Privileges_Info> list = privileges_info_repo.findAll();
        list.sort(Comparator.comparing(Privileges_Info::getReputation));
        return list;
    }

    @PostMapping("/Crawler_Insert")
    public void Crawl_Insert() throws IOException {
        privileges_info_repo.deleteAll();
        String json_String_Content = Jsoup.connect(Data_Crawl_URL).ignoreContentType(true).get().text();

        JsonObject jsonObject = JsonParser.parseString(json_String_Content).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("items").getAsJsonArray();

        List<Privileges_Info> list = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject1 = jsonElement.getAsJsonObject();
            list.add(new Privileges_Info(jsonObject1.get("reputation").getAsInt(), jsonObject1.get("description").getAsString()));
        }
        privileges_info_repo.saveAll(list);
    }
}
