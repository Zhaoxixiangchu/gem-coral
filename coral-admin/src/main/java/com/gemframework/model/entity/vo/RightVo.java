package com.gemframework.model.entity.vo;

import com.gemframework.model.common.BaseEntityPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class RightVo extends BaseEntityPo {

    //父级ID 默认是0根节点
    @NotNull(message = "父级ID不能为空")
    private Long pid;
    //名称
    @NotBlank(message = "名称不能为空")
    private String name;
    //标识
    @NotBlank(message = "标识不能为空")
    private String flag;
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