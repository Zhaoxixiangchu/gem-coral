package com.gemframework.model.entity.vo;

import com.gemframework.model.common.BaseEntityPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends BaseEntityPo {

    @NotNull(message = "父级ID不能为空")
    private Long pid;

    @NotBlank(message = "名称不能为空")
    private String name;
    private Integer type;
    private Integer level;
}