package com.springboottest.demo.controller;

import com.springboottest.demo.entity.item;
import com.springboottest.demo.entity.user_info;
import com.springboottest.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/getuser")
@CrossOrigin
public class User_infoHandler {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findAlluser")
    public List<user_info> findAll(){
        return userRepository.findAll();
    }

    @PostMapping("/adduser")
    public String save(@RequestBody user_info user1){
        user_info result = userRepository.save(user1);
        if (result!=null){
            return "success";
        }else
            return "error";
    }
}
