package com.springboottest.demo.repository;

import com.springboottest.demo.entity.User_Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface User_Account_Repo extends JpaRepository<User_Account, String> {
    @Transactional
    @Modifying
    @Query("update User_Account \n" +
           "set Search_Cnt = Search_Cnt + 1\n" +
           "where user_name = ?1")
    void update_search_cnt(String username);

    @Transactional
    @Modifying
    @Query("select user_name, Search_Cnt\n" +
           "from User_Account \n" +
           "order by Search_Cnt desc\n")
    List<Object[]> select_active_user_list();
}