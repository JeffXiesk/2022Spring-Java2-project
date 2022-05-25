package com.springboottest.demo.controller.Topic_Handler;

import com.springboottest.demo.repository.Topic_Repo.Test_Item_Repo;
import com.springboottest.demo.entity.Topic_Info.Test_Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Test_Item")
@CrossOrigin
public class Test_Item_Handler {
    @Autowired
    private Test_Item_Repo itemRepository;

    @GetMapping("/FindAll")
    public List<Test_Item> findAll() {
        return itemRepository.findAll();
    }

    @PostMapping("/Add")
    public String save(@RequestBody Test_Item i) {
        Test_Item result = itemRepository.save(i);
        if (result != null) {
            return "success";
        } else
            return "error";
    }
}
