/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.model.entity.vo;

import com.gemframework.model.common.BaseEntityVo;
import com.gemframework.model.common.validator.SuperValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class RightVo extends BaseEntityVo {

    //父级ID 默认是0根节点
    private Long pid;
    //名称
    @NotBlank(message = "名称不能为空",groups = SuperValidator.class)
    private String name;
    //标识组 多个以逗号隔开
    @NotBlank(message = "标识不能为空",groups = SuperValidator.class)
    private String flags;
    //图标
    private String icon;
    //链接
    private String link;
    //类型 0 菜单 1 按钮 2 权限
    private Integer type;
    //级别
    private Integer level;
    //位置 0 左侧 1 顶部 2 底部
    private Integer position;

    private List<RightVo> child;

}