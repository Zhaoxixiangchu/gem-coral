package com.gemframework.config.shiro;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 加密工具栏
 */
public class ShiroUtils {

	//加密算法名称
	public final static String hashAlgorithmName = "SHA-256";
	//加密次数
	public final static int hashIterations = 5;

	/***
	 * 密码加密（单向，不可逆）
	 * @param password
	 * @param salt 加盐
	 * @return
	 */
	public static String passwordSHA256(String password, String salt) {
		return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
	}

}
