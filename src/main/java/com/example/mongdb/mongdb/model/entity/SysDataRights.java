package com.example.mongdb.mongdb.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 实体类
 * @create by Kellach 2019年10月11日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDataRights implements Serializable{
    /** 创建人 */
    private String createUid ;
    /** 创建时间 */
    private Date createTime ;
    /** 更新人 */
    private String updateUid ;
    /** 更新时间 */
    private Date updateTime ;
    /** 逻辑删除 */
    private boolean isDel ;
    /** 主键 */
    @TableId(value = "data_right_id", type = IdType.UUID)
    private String dataRightId ;
    /** 关联所属人表 */
    private String ownerId ;
    /** 关联用户 */
    private String uid ;
    /** 数据权限类型;0.是否可引用 1.是否可查看 2.是否可修改 3.是否可删除 4.是否可下载 5.是否可上传
     也可以用二进制来表示（eg. 111111 表示全部都有权限，000000 表示全部都没有，看后期设计） */
    private BigInteger rightsType ;
}
