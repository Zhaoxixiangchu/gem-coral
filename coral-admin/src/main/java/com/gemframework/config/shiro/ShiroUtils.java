/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.config.shiro;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 加密工具栏
 */
public class ShiroUtils {

	//加密算法名称
	public final static String HASH_ALGORITHM_NAME = "SHA-256";
	//加密次数
	public final static int HASH_ITERATIONS = 5;

	//cookie名称
	public final static String REMEMBERME_COOKIE_NAME = "rememberMe";
	//记住我时长
	public final static int REMEMBERME_COOKIE_MAXAGE = 604800;
	//cookie加密key，防止服务器重启报错
	// org.apache.shiro.crypto.CryptoException: Unable to execute 'doFinal' with cipher instance
	public final static String REMEMBERME_CIPHERKEY = "Qtr5KRwI4qBJPrhH";

	/***
	 * 密码加密（单向，不可逆）
	 * @param password
	 * @param salt 加盐
	 * @return
	 */
	public static String passwordSHA256(String password, String salt) {
		return new SimpleHash(HASH_ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toString();
	}

}
