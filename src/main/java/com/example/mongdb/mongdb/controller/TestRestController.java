package com.example.mongdb.mongdb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mongdb.mongdb.model.entity.User;
import com.example.mongdb.mongdb.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试Controller
 * @create by Kellach 2019年9月20日
 */
@RestController
@RequestMapping(value = "/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class TestRestController {

    @Autowired
    private IUserService userService;

    /**
     * 测试接口是否通
     * @return
     */
    @GetMapping(value = "/getUser")
    public String getTestUser(){
        List<User> all = userService.findAll();
        String rest = JSONObject.toJSON(all).toString();
        return rest;
    }

    /**
     * 测试接口
     * @return
     */
    @PostMapping(value = "/insertUser")
    public String insertTest(){
        List<User> userList = new ArrayList<User>();
        for(int i = 0;i<10;i++){
            User user = new User();
            ObjectId objectId = new ObjectId();
            user.setId(objectId.toString());
            user.setAge(i+20);
            user.setUserName("test"+i);
            user.setNickName("昵称："+i);
            userList.add(user);
        }
        userService.saveListUser(userList);
        return "success!";
    }
}
