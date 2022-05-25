package com.springboottest.demo.controller;

import com.springboottest.demo.entity.*;
import com.springboottest.demo.controller.*;
import com.springboottest.demo.controller.Topic_Handler.*;
import com.springboottest.demo.repository.Browse_History_Repo;
import com.springboottest.demo.repository.User_Account_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/User_Account")
@CrossOrigin
public class User_Account_Handler {
    @Autowired
    private User_Account_Repo user_account_repo;

    @Autowired
    private Browse_History_Repo browse_history_repo;

    public static User_Account Current_User_Account;

    @GetMapping("/Get_User_Name")
    public String Get_User_Name() {
        return Current_User_Account.getUser_name();
    }

    @GetMapping("/Find_Active_User")
    public List<User_Account> Find_Active_User() {
        List<Object[]> raw_list = user_account_repo.select_active_user_list();
        List<User_Account> active_user_list = new ArrayList<>();
        for (Object[] object_array : raw_list) {
            User_Account user_account = new User_Account((String) object_array[0], "You don't have the authorization to see it!", (int) object_array[1]);
            active_user_list.add(user_account);
        }
        if (active_user_list.size() >= 5) {
            return active_user_list.subList(0, 5);
        } else {
            return active_user_list.subList(0, active_user_list.size());
        }
    }

    @GetMapping("/Browse_History")
    public List<Object[]> Browse_History() {
        return browse_history_repo.select_browse_history(Current_User_Account.getUser_name());
    }

    @GetMapping("/Initialize")
    public void Initialize() {
        user_account_repo.deleteAll();
        browse_history_repo.deleteAll();
    }

    @PostMapping("/Sign_in/{user_name}/{password_set}/{password_verify}")
    public User_Account Sign_in(@PathVariable("user_name") String user_name, @PathVariable("password_set") String password_set, @PathVariable("password_verify") String password_verify) {

        // 两次输入密码相同
        if (password_set.equals(password_verify) && !user_account_repo.existsById(user_name)) {
            User_Account user_account = new User_Account(user_name, password_set, 0);
            user_account_repo.save(user_account);
            Current_User_Account = user_account;
            return user_account;
        } else {
            return new User_Account("Sign in Failed!", null, 0);
        }
    }

    @PostMapping("/Log_in/{user_name}/{password}")
    public User_Account Log_in(@PathVariable("user_name") String user_name, @PathVariable("password") String password) {

        if (user_account_repo.existsById(user_name) && password.equals(user_account_repo.findById(user_name).get().getPassword())) {
            Current_User_Account = user_account_repo.findById(user_name).get();
            return Current_User_Account;
        } else {
            return new User_Account("Log in Failed!", null, 0);
        }
    }

    @PostMapping("/Log_out")
    public User_Account Log_out() {
        Current_User_Account = null;
        return new User_Account("Please sign in a new account or log in!", null, 0);
    }
}
