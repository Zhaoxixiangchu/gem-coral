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

import javax.validation.constraints.NotNull;

/**
 * @Title: TaskLogsVo
 * @Package: com.gemframework.model.entity.vo
 * @Date: 2020-04-04 11:55:36
 * @Version: v1.0
 * @Description: TaskLogsVo
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class TaskLogsVo extends BaseEntityVo{

	//任务ID
	@NotNull(message = "任务ID不能为空")
	private Long taskId;
	//错误消息
	private String error;
	//耗时(单位：毫秒)
	private Long times;
}
