package com.example.mongdb.mongdb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 模板信息表
 * @create by Kellach 2019年9月29日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ModelInfo implements Serializable {
    /**
     * 主键标识
     */
    @Id
    private String modelInfoId;
    /**
     * 模板名称
     */
    private String modelName;
    /**
     * 模板编码
     */
    private String modelCode;
    /**
     * 版本号
     */
    private String version;
    /**
     * workbook 内容
     */
    private String workBookContentId;
    /**
     * sheetJson
     */
    private String sheetJson;

}