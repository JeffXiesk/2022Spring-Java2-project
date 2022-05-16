package com.springboottest.demo.repository;

import com.springboottest.demo.entity.item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface itemRepository extends JpaRepository<item,Integer> {

}
