package com.gemframework.model.entity.vo;

import com.gemframework.model.common.BaseEntityVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleVo extends BaseEntityVo {

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "标识不能为空")
    private String flag;

    //数据范围 0 仅限个人 1 仅限本部门 2 本部门以及下属部门 3 自定义设置部门
    @NotBlank(message = "数据范围不能为空")
    private Integer dataRange;
}