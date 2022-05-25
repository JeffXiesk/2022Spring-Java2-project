package com.springboottest.demo.entity.Topic_Info;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "tag_info", schema = "java2_pro")
public class Tag_Info {
    @Id
    private String name;
    private int value;

    public Tag_Info(String name, int cnt) {
        this.name = name;
        this.value = cnt;
    }

    public Tag_Info() {

    }
}
