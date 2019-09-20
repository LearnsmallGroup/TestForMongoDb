package com.example.mongdb.mongdb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 测试用户类
 * @creaete by Kellach 2019年9月20日
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {

    @Id
    String id;
    /**用户名*/
    String userName;
    /**昵称*/
    String nickName;
    /**年龄*/
    Integer age;
}
