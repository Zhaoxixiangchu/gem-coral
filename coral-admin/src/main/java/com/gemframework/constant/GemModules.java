/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.constant;

public interface GemModules {

	//预设模块
	interface PreKit{
		String PATH_PRE= "/prekit";
		//工作台
		String PATH_DESKTOP= PATH_PRE+"/desk";
		//RBAC
		String PATH_RBAC= PATH_PRE+"/rbac";
		//示例模块
		String PATH_DEMO= PATH_PRE+"/demo";
		//系统模块
		String PATH_SYSTEM= PATH_PRE+"/sys";
	}

	//扩展模块 用于二次开发
	interface Extend{
		String PATH_PRE= "/extend";

		//eg:自定义扩展模块名...
		String CUSTOM_M1= PATH_PRE+"/m1";
	}

}
