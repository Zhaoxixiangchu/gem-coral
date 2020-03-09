package com.gemframework.model.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gemframework.common.annotation.ValidMoblie;
import com.gemframework.model.common.BasePo;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@TableName("gem_user")
@Data
public class User extends BasePo {

    private Long id;
    private Long dept_id;
    private String username;
    private String password;
    private String realname;
    private Integer worknum;
    private String post;
    private Integer sex;
    private Date birthday;
    private Integer province;
    private Integer city;
    private Integer area;
    private String address;
    private String phone;
    private String tel;
    private String email;
    private String qq;
    private String desp;
}