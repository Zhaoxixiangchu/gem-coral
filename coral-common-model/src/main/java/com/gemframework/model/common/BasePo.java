package com.gemframework.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @Title: BasePo.java
 * @Package: com.gemframework.model.po
 * @Date: 2019/11/28 22:15
 * @Version: v1.0
 * @Description: PO基类
 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */

//表示父类映射到数据库表
@MappedSuperclass
//启动审计监听器
@EntityListeners(AuditingEntityListener.class)
@Data
public class BasePo implements Serializable {


    @Column(columnDefinition = "DATETIME COMMENT '创建时间'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @CreatedDate
    private Date createTime;

    @Column(columnDefinition = "DATETIME COMMENT '更新时间'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    private Date updateTime;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0 COMMENT '数据状态 0 正常 1 删除'")
    private int deleted;

}