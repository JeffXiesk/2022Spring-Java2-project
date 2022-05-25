package com.springboottest.demo.entity.Topic_Info;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="stackoverflow_info",schema = "java2_pro")
public class StackOverflow_Info {
    @Id
    private Integer auto_increment_id;
    private String info_name;
    private Double numeric_data;
}
