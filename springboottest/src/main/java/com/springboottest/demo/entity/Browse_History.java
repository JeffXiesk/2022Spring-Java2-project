package com.springboottest.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.SimpleDateFormat;

@Entity
@Data
public class Browse_History {
    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Id
    private String time;
    private String user_name;
    private String history;

    public Browse_History(String user_name, String history, String time) {
        this.user_name = user_name;
        this.history = history;
        this.time = time;
    }

    public Browse_History() {
    }
}
