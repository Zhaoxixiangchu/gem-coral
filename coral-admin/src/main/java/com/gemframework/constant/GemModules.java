package com.gemframework.constant;

public interface GemModules {

	//预设模块
	interface PreKit{
		String PATH_PRE= "/prekit";
		//工作台
		String PATH_DESKTOP= PATH_PRE+"/desk";
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
