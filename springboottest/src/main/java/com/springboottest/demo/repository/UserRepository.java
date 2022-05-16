package com.springboottest.demo.repository;

import com.springboottest.demo.entity.user_info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <user_info,String>{
}
