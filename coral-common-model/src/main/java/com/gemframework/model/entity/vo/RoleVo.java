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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends BaseEntityVo {

    @NotBlank(message = "名称不能为空",groups = SuperValidator.class)
    private String name;

    @NotBlank(message = "标识不能为空",groups = SuperValidator.class)
    private String flag;

    //数据范围 0 仅限个人 1 仅限本部门 2 本部门以及下属部门 3 自定义设置部门
    @NotBlank(message = "数据范围不能为空",groups = SuperValidator.class)
    private Integer dataRange;
}