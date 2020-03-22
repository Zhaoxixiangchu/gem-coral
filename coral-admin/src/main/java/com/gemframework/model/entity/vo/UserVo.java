package com.gemframework.model.entity.vo;

import com.gemframework.model.annotation.ValidMoblie;
import com.gemframework.model.common.BaseEntityVo;
import com.gemframework.model.common.validator.PasswordValidator;
import com.gemframework.model.common.validator.SaveValidator;
import com.gemframework.model.common.validator.StatusValidator;
import com.gemframework.model.common.validator.SuperValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends BaseEntityVo {

    @NotNull(message = "部门ID不能为空",groups = SuperValidator.class)
    private Long deptId;

    @NotBlank(message = "用户名不能为空",groups = SuperValidator.class)
    private String username;

    @NotBlank(message = "密码不能为空",groups = {SaveValidator.class, PasswordValidator.class})
    private String password;

    private String realname;
    private Integer jobnumber;
    private String post;
    private Integer sex;
    private Date birthday;
    private Integer province;
    private Integer city;
    private Integer area;
    private String address;

    @NotBlank(message = "手机号不能为空！",groups = SuperValidator.class)
    @ValidMoblie
    private String phone;
    private String tel;
    private String email;
    private String qq;
    //0正常 1禁用
    @NotNull(message = "status不能为空",groups = {StatusValidator.class})
    private Integer status;
    //角色ID
    private String roleIds;
    //角色ID
    private List<RoleVo> roles;
}