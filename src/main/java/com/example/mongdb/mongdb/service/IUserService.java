package com.example.mongdb.mongdb.service;


import com.example.mongdb.mongdb.model.entity.User;

import java.util.List;

/**
 * 查询Service
 * @create by Kellach 2019年9月20日
 */
public interface IUserService extends IBaseService<User> {

    /**
     * 获取所有用户
     * @create by Kellach 2019年9月20日
     * @return
     */
    List<User> findAll();

    /**
     * 保存方法
     * @create by Kellach 2019年9月20日
     * @param userList
     */
    void saveListUser(List<User> userList);
}
