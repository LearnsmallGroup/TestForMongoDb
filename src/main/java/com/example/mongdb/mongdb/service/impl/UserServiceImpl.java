package com.example.mongdb.mongdb.service.impl;

import com.example.mongdb.mongdb.model.entity.User;
import com.example.mongdb.mongdb.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 用户服务类
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl extends BaseMDBServiceImpl<User> implements IUserService{


    @Override
    public List<User> findAll() {
        return findAll();
    }

    @Override
    public void saveListUser(List<User> userList) {
        saveList(userList);
    }
}
