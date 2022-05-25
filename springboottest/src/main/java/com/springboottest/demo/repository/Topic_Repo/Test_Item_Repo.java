package com.springboottest.demo.repository.Topic_Repo;

import com.springboottest.demo.entity.Topic_Info.Test_Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Test_Item_Repo extends JpaRepository<Test_Item,Integer> {

}