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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class SysLogsVo extends BaseEntityVo{

	@NotBlank(message = "用户名不能为空")
	private String username;
	private String operation;
	private String method;
	private String params;
	private Long times;
	private String userip;
	//请求前参数
	private String beforeParams;
	//操作执行状态信息
	private Integer status;
	private Integer statusCode;
	private String statusMsg;
}
