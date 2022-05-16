package com.springboottest.demo.controller;

import com.springboottest.demo.entity.item;
import com.springboottest.demo.repository.itemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testdata")
@CrossOrigin
public class ItemHandler {
    @Autowired
    private itemRepository bookRepository;

    @GetMapping("/findAll")
    public List<item> findAll(){
        return bookRepository.findAll();
    }

    @PostMapping("/save")
    public String save(@RequestBody item i){
        item result = bookRepository.save(i);
        if (result!=null){
            return "success";
        }else
            return "error";
    }
}
