package com.springboottest.demo.repository.Topic_Repo;

import com.springboottest.demo.entity.Topic_Info.Tag_Info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Tag_Info_Repo extends JpaRepository<Tag_Info,String> {
    
}
