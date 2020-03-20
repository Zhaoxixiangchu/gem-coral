package com.gemframework.model.entity.vo;

import com.gemframework.model.common.BaseEntityVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRolesVo extends BaseEntityVo {

    @NotNull(message = "userId不能为空")
    private Long userId;

    private Long roleId;

    @NotBlank(message = "roleIds不能为空")
    private String roleIds;

}