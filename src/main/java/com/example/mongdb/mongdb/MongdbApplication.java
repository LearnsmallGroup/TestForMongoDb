package com.example.mongdb.mongdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongdbApplication.class, args);
        System.out.println("启动成功！");
    }

}
