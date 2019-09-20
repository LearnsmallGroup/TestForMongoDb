package com.example.mongdb.mongdb.dao;

import com.example.mongdb.mongdb.model.entity.User;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * 用户持久层
 *
 */
@EnableMongoRepositories
public interface UserRepository extends BaseRepository<User> {

}
