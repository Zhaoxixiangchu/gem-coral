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

import com.gemframework.model.annotation.ValidMoblie;
import com.gemframework.model.common.BaseEntityVo;
import com.gemframework.model.common.validator.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class UserVo extends BaseEntityVo {

    @NotNull(message = "部门ID不能为空",groups = SuperValidator.class)
    private Long deptId;

    @NotBlank(message = "用户名不能为空",groups = SuperValidator.class)
    private String username;

    @NotBlank(message = "原密码不能为空",groups = {PasswordEditValidator.class})
    private String oldPass;

    @NotBlank(message = "密码不能为空",groups = {SaveValidator.class, PasswordResetValidator.class,PasswordEditValidator.class})
    private String password;

    private String avatarUrl;
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