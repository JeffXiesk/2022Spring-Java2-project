package com.springboottest.demo.repository.Topic_Repo;

import com.springboottest.demo.entity.Topic_Info.StackOverflow_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface StackOverflow_Info_Repo extends JpaRepository<StackOverflow_Info, Integer> {
    @Transactional
    @Modifying
    @Query("update StackOverflow_Info " +
            "set numeric_data = numeric_data + 1 " +
            "where info_name = 'browse_cnt'")
    void updateBrowseCnt();
}
