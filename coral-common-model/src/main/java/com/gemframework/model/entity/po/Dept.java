package com.gemframework.model.entity.po;

import com.gemframework.model.common.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


/**
 * @Title: Dept.java
 * @Package: com.gemframework.model.po
 * @Date: 2019/11/30 17:54
 * @Version: v1.0
 * @Description: 部门（针对User）

 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@Entity
@Table(name = "gem_dept")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Dept extends BasePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(10) NOT NULL COMMENT '部门名称'",nullable = false)
    private String name;

    @Column(columnDefinition = "INT(20) COMMENT '父ID'")
    private Long pid;

    @Column(columnDefinition = "TINYINT(1) COMMENT '级别'")
    private Integer level;

    @Column(columnDefinition = "VARCHAR(300) COMMENT '描述'")
    private String desp;

    //系列 用于归类 存放家族一级分类ID 一级分类存自己ID
    @Column(columnDefinition = "VARCHAR(20) COMMENT '所属系列'")
    private String series;

    //路径 1-2-1
    @Column(columnDefinition = "VARCHAR(20) COMMENT 'ID路径'")
    private String idPath;

    //详情字段
    @Column(columnDefinition = "VARCHAR(20) COMMENT '部门编号'")
    private String num;
    @Column(columnDefinition = "VARCHAR(10) COMMENT '负责人'")
    private String boss;
    @Column(columnDefinition = "VARCHAR(20) COMMENT '简称'")
    private String abbr;
    @Column(columnDefinition = "VARCHAR(20) COMMENT '类型'")
    private String type;
    @Column(columnDefinition = "VARCHAR(20) COMMENT '电话'")
    private String tel;
    @Column(columnDefinition = "VARCHAR(20) COMMENT '邮箱'")
    private String email;

    //父级的路径 1-2-1
    @Transient
    private String parentIdPath;
    @Transient
    List<Dept> childs;
}
