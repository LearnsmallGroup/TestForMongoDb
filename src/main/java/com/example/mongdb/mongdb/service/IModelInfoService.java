package com.example.mongdb.mongdb.service;

import com.example.mongdb.mongdb.model.entity.ModelInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 模板Service
 * @create by Kellach 2019年9月29日
 */
public interface IModelInfoService extends IBaseMDBService<ModelInfo> {
    /**
     * 处理上传的Excel
     * @param file 文件
     * @param fileCode 文件CODE
     */
    void dealModelInfoExcel(MultipartFile file,String fileCode) throws Exception;

    /**
     * 保存数据Sheet
     * @param file
     * @param fileCode
     */
    void saveExcelBySheet(MultipartFile file,String fileCode) throws Exception;

    /**
     * 获取文件中的Json
     * @param file
     * @return
     */
    String getfileJson(InputStream file);
    /**
     * 获取后台完整的workBook
     * @create by Kellach 2019年10月23日
     * @param modelCode
     * @return
     */
    String getNewWorkBookByCode(String modelCode);
}
