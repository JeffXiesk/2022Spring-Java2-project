package com.springboottest.demo.entity.Topic_Info;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Data
@Table(name = "collectives_info", schema = "java2_pro")
public class Collectives_Info {
    @Id
    private String name;
    private String description;

    public Collectives_Info(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Collectives_Info() {

    }
}
