package com.springboottest.demo.controller.Topic_Handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.springboottest.demo.repository.Topic_Repo.*;
import com.springboottest.demo.repository.*;
import com.springboottest.demo.entity.Topic_Info.*;
import com.springboottest.demo.entity.*;
import com.springboottest.demo.controller.Topic_Handler.*;
import com.springboottest.demo.controller.*;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Topic_Info/StackOverflow_Info")
@CrossOrigin
public class StackOverflow_Info_Handler {
    @Autowired
    private StackOverflow_Info_Repo stackOverflow_info_repo;

    @Autowired
    private User_Account_Repo user_account_repo;

    @Autowired
    private Browse_History_Repo browse_history_repo;

    private final String name = "Basic information of StackOverflow";
    @SuppressWarnings("all")
    private final String Data_Crawl_URL = "https://api.stackexchange.com/2.3/info?site=stackoverflow";

    @GetMapping("/Get_All_Information")
    public List<StackOverflow_Info> Select_All() {
        stackOverflow_info_repo.updateBrowseCnt();
        user_account_repo.update_search_cnt(User_Account_Handler.Current_User_Account.getUser_name());
        browse_history_repo.save(new Browse_History(
                User_Account_Handler.Current_User_Account.getUser_name(),
                name,
                Browse_History.df.format(System.currentTimeMillis())));

        return stackOverflow_info_repo.findAll();
    }

    @PostMapping("/Crawler_Insert")
    public void Crawl_Insert() throws IOException {
        stackOverflow_info_repo.deleteAll();
        String json_String_Content = Jsoup.connect(Data_Crawl_URL).ignoreContentType(true).get().text();

        JsonObject jsonObject = JsonParser.parseString(json_String_Content).getAsJsonObject();
        JsonObject jsonObject1 = jsonObject.get("items").getAsJsonArray().get(0).getAsJsonObject();

        List<StackOverflow_Info> list = new ArrayList<>();
        int auto_increment_id = 0;
        for (Map.Entry<String, JsonElement> entry : jsonObject1.entrySet()) {
            if (!entry.getKey().equals("api_revision")) {
                StackOverflow_Info stackOverflow_info = new StackOverflow_Info();

                // Set the content of the StackOverflow_info
                stackOverflow_info.setAuto_increment_id(auto_increment_id++);
                stackOverflow_info.setInfo_name(entry.getKey());
                stackOverflow_info.setNumeric_data(entry.getValue().getAsDouble());

                list.add(stackOverflow_info);
            }
        }

        stackOverflow_info_repo.saveAll(list);
    }


}
