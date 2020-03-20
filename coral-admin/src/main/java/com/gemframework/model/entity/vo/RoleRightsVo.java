package com.gemframework.model.entity.vo;

import com.gemframework.model.common.BaseEntityVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoleRightsVo extends BaseEntityVo {

    @NotNull(message = "roleId不能为空")
    private Long roleId;

    private Long rightId;

    @NotBlank(message = "rightIds不能为空")
    private String rightIds;

}