package com.gemframework.model.entity.po;

import com.gemframework.model.common.BasePo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @Title: Menu.java
 * @Package: com.gemframework.model.po
 * @Date: 2019/11/30 17:54
 * @Version: v1.0
 * @Description: 用户信息

 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@Entity
@Table(name = "gem_menu")
@Data
@NoArgsConstructor
public class Menu extends BasePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(10) COMMENT '菜单/按钮名称'")
    private String name;
    @Column(columnDefinition = "VARCHAR(30) COMMENT '菜单/按钮标签'")
    private String tag;
    @Column(columnDefinition = "VARCHAR(50) COMMENT '菜单/按钮链接'")
    private String link;
    @Column(columnDefinition = "TINYINT(1) COMMENT '类型 0菜单 1按钮 2其他'")
    private Integer type;
    @Column(columnDefinition = "TINYINT(1) COMMENT '级别，最大支持四级'")
    private Integer level;
    //图标
    @Column(columnDefinition = "VARCHAR(30) COMMENT '图标'")
    private String icon;
    //父级ID
    @Column(columnDefinition = "BIGINT(20) DEFAULT 0 COMMENT '父级ID'" )
    private Long pid;
    //是否选中 0 未选中 1 选中
    @Column(columnDefinition = "TINYINT(1) COMMENT '是否选中 0 未选中 1 选中'")
    private Integer active;
    //排序
    @Column(columnDefinition = "INT(10) COMMENT '排序编号'")
    private Integer sortNumber;

    //路径 1-2-1 用于treetable页面渲染
    @Column(columnDefinition = "VARCHAR(50) COMMENT 'ID路径'")
    private String idPath;

    //父级的路径 1-2-1 用于treetable页面渲染
    @Transient
    private String parentIdPath;
    @Transient
    List<Menu> childs;
}
