package com.example.mongdb.mongdb.service;

import com.example.mongdb.mongdb.model.entity.ModelInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 模板Service
 * @create by Kellach 2019年9月29日
 */
public interface IModelInfoService extends IBaseMDBService<ModelInfo> {
    /**
     * 处理上传的Excel
     * @param file
     */
    void dealModelInfoExcel(MultipartFile file) throws Exception;
}
