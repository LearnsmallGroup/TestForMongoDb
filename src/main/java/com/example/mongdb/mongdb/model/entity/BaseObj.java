package com.example.mongdb.mongdb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseObj implements Serializable {

    private String xxx;

    private String code;

    private String name;

    private Double value;

}
