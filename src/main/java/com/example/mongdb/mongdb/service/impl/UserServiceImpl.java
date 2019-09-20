package com.example.mongdb.mongdb.service.impl;

import com.example.mongdb.mongdb.dao.UserRepository;
import com.example.mongdb.mongdb.model.entity.User;
import com.example.mongdb.mongdb.service.IUserService;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户服务类
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {

    @Autowired
    private MongoTemplate mongoTemplate;

/**    @Resource
    private UserRepository userRepository;
**/

    /**
     *
     * @return
     */
    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    /**
     *
     * @param userList
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void saveListUser(List<User> userList) {
//        for(User tmp :userList){
//            mongoTemplate.save(tmp);
//        }
        mongoTemplate.insertAll(userList);
    }
}
