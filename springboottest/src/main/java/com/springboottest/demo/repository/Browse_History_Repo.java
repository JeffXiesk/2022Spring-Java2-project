package com.springboottest.demo.repository;

import com.springboottest.demo.entity.Browse_History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface Browse_History_Repo extends JpaRepository<Browse_History, Integer> {
    @Transactional
    @Modifying
    @Query("select user_name, history, time\n" +
           "from Browse_History \n" +
           "where user_name = ?1")
    List<Object[]> select_browse_history(String username);
}
