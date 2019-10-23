package com.example.mongdb.mongdb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 测试demo2
 * @create by
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseObj2 implements Serializable {
    private String colA;
    private double colB;
    private double colC;
    private double colD;
}
