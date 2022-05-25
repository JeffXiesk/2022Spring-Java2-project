package com.springboottest.demo.entity.Topic_Info;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Test_Item {
    @Id
    private Integer id;
    private String name;
    private String author;
}
