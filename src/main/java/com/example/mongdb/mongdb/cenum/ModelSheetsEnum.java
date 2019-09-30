package com.example.mongdb.mongdb.cenum;


import java.io.Serializable;

/**
 * 模板sheet枚举类型
 * @create by Kellach 2019年9月29日
 */
public enum ModelSheetsEnum implements Serializable {

    READ_ONLY(0,"只读"),
    READ_WRITE(1,"读写");


    private int value;

    private String name;

    ModelSheetsEnum(int value,String name){
        this.name = name;
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public String getName(){
        return this.name;
    }

    public ModelSheetsEnum getEnum(int value){
        for(ModelSheetsEnum cenum: ModelSheetsEnum.values()){
            if(cenum.getValue() == value){
                return cenum;
            }
        }
        throw new RuntimeException("Error: Invalid ModelSheetsEnum type value: " + value);
    }
}
