package com.springboottest.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User_Account {
    @Id
    private String user_name;
    private String password;
    private int Search_Cnt;


    public User_Account(String user_name,String password,int Search_Cnt){
        this.user_name = user_name;
        this.password = password;
        this.Search_Cnt = Search_Cnt;
    }

    public User_Account() {

    }
}
