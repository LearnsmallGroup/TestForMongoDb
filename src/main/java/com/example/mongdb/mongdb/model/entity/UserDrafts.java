package com.example.mongdb.mongdb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 用户底稿
 * @create by Kellach 2019年9月29日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDrafts implements Serializable {
    /**
     * 主键
     */
    @Id
    private String userDraftsId;
    /**
     * 底稿名称
     */
    private String draftName;
    /**
     * 模板Code
     */
    private String modelCode;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 项目CODE
     */
    private String projectCode;
    /**
     * workbook 内容
     */
    private String workBookContentId;

}
