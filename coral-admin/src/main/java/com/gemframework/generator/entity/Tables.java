/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.generator.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Title: Tables
 * @Package: com.gemframework.generator
 * @Date: 2020-04-14 19:00:01
 * @Version: v1.0
 * @Description: 数据库表
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Data
@Builder
public class Tables {
	private String tableName;
	private String comments;
	//编码
	private String tableCollation;
	//引擎
	private String engine;
	//主键
	private Columns pk;
	//列（不含主键）
	private List<Columns> columns;
	//如：User  SysLog
	private String classNameUp;
	//如：user  sysLog
	private String classNameLow;
}
