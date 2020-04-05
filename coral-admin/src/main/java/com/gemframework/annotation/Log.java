/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */

package com.gemframework.annotation;

import com.gemframework.model.enums.OperateType;

import java.lang.annotation.*;

/**
 * @Title: Log
 * @Package: com.gemframework.annotation
 * @Date: 2020-04-02 15:40:31
 * @Version: v1.0
 * @Description: 这里写描述
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

	//0 操作日志 1 登录日志
	OperateType type() default OperateType.NORMAL;

	String value() default "";

	/**查询的bean名称*/
	String serviceClass() default "";

	/**查询单个详情的bean的方法*/
	String queryMethod() default "";

	/**查询详情的参数类型*/
	String parameterType() default "";

	/**从页面参数中解析出要查询的id，
	* 如域名修改中要从参数中获取customerDomainId的值进行查询
	*/
	String parameterKey() default "";

	/**是否为批量类型操作*/
	boolean paramIsArray() default false;
}
