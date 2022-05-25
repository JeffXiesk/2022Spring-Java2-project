package com.springboottest.demo.controller.Topic_Handler;

import com.springboottest.demo.controller.User_Account_Handler;
import com.springboottest.demo.entity.Browse_History;
import com.springboottest.demo.entity.Topic_Info.User_Reputation_Grouped_Info;
import com.springboottest.demo.repository.Browse_History_Repo;
import com.springboottest.demo.repository.Topic_Repo.User_Reputation_Grouped_Info_Repo;
import com.springboottest.demo.repository.User_Account_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Topic_Info/User_Reputation_Grouped_Info")
@CrossOrigin
public class User_Reputation_Grouped_Info_Handler {
    @Autowired
    private User_Reputation_Grouped_Info_Repo user_reputation_grouped_info_repo;

    @Autowired
    private User_Account_Repo user_account_repo;

    @Autowired
    private Browse_History_Repo browse_history_repo;

    private final String topic_name = "User Reputation Grouped Information";
    private String Data_Crawl_URL = "https://api.stackexchange.com/2.3/users?pagesize=5&order=desc&sort=reputation&site=stackoverflow";

    @GetMapping("/Select_All")
    public List<User_Reputation_Grouped_Info> Select_All(){
        user_account_repo.update_search_cnt(User_Account_Handler.Current_User_Account.getUser_name());
        browse_history_repo.save(new Browse_History(
                User_Account_Handler.Current_User_Account.getUser_name(),
                topic_name,
                Browse_History.df.format(System.currentTimeMillis())));

        return user_reputation_grouped_info_repo.findAll();
    }


}