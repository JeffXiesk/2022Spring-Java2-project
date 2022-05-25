package com.springboottest.demo.entity.Topic_Info;

import com.springboottest.demo.repository.Topic_Repo.User_Reputation_Grouped_Info_Repo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_reputation_grouped_info", schema = "java2_pro")
public class User_Reputation_Grouped_Info {
    @Id
    private String min_repu;
    private String max_repu;
    private int num;

    public User_Reputation_Grouped_Info(String min_repu, String max_repu, int num) {
        this.min_repu = min_repu;
        this.max_repu = max_repu;
        this.num = num;
    }

    public User_Reputation_Grouped_Info() {

    }
}
