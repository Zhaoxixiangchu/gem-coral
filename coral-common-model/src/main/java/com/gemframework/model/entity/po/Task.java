/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */

package com.gemframework.model.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gemframework.model.common.BaseEntityPo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Title: Task
 * @Package: com.gemframework.model.entity.po
 * @Date: 2020-04-04 11:46:21
 * @Version: v1.0
 * @Description: 任务对象
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Data
@TableName("gem_sys_task")
public class Task extends BaseEntityPo {

	//任务名称
	private String name;
	//className名称
	private String className;
	//参数
	private String params;
	//cron表达式
	private String cron;
	//任务状态 0 暂停 1 开始 2 停止
	private Integer status;

}
