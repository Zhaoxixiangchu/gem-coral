package com.gemframework.model.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gemframework.model.common.BasePo;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 系统用户
 */
@Data
@TableName("tb_user")
public class User extends BasePo {

	/**
	 * 用户ID
	 */
	@TableId
	private Long id;

	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空")
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

}
