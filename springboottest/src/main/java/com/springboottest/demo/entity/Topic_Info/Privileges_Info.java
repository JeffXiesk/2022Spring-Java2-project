package com.springboottest.demo.entity.Topic_Info;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "privileges_info", schema = "java2_pro")
public class Privileges_Info {
    @Id
    private Integer reputation;
    private String description;

    public Privileges_Info(Integer reputation, String description) {
        this.reputation = reputation;
        this.description = description;
    }

    public Privileges_Info() {
    }
}
