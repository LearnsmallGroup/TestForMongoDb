package com.example.mongdb.mongdb.service;

import org.springframework.data.mongodb.core.query.Query;

import java.io.InputStream;
import java.util.List;

/**
 * 作为基础服务类
 * @create by Kellach 2019年9月20日
 */
public interface IBaseMDBService<T> {
    /**
     * 保存方法
     * @param list
     */
    void saveList(List<T> list);

    /**
     * 保存方法
     * @param entity
     */
    T save(T entity);

    /**
     * 通过条件查询
     * @param query 条件
     * @return
     */
    List<T> getByQuery(Query query);

    /**
     * 保存文件
     * @param file
     * @param fileName
     * @return
     */
    String saveFile(InputStream file, String fileName);

    /**
     * 获取文件by ID
     * @param fielID
     * @return
     */
    InputStream getFileById(String fielID);
    /**
     * 通过id 删文件
     * @param fileID
     */
    void deleteFileById(String fileID);

    /**
     * 获取数据通过 query
     * @param query
     * @return
     */
    T getOneByQuery(Query query);

}
