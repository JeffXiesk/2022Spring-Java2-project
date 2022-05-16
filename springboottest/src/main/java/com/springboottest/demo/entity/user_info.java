package com.springboottest.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class user_info {
    @Id
    private String account;
    private String password;
}
