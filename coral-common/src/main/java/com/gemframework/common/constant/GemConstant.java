package com.gemframework.common.constant;

public interface GemConstant {

	//公共常量
	interface Common{
		//NULL
		String NULL= "null";
	}

	//系统常量
	interface System{
		String DEF_PASSWORD= "123456";
	}

	//公共常量
	interface Auth{
		String CLIENT_ID_NAME = "client_id_name";
		String CLIENT_ID_PASS = "client_id_pass";
		String HEADER_AUTHOR = "Authorization";

		String ROLE_PREFIX = "ROLE_";

		// 匿名的字符出
		String ANONY_MOUS_USER = "anonymousUser";

		String COOKIES_TOKEN_NAME = "access_token";

		String SESSION_USERKEY = "SPRING_SECURITY_CONTEXT";
	}


	//常用的字符集编码类型
	interface Character{
		// json编码
		String UTF8 = "UTF-8";
		String GBK = "GBK";
		String GB_2312 = "GB2312";
		String ISO_8859_1 = "iso-8859-1";
	}

	//返回结果
	interface Result{
		// 自定义标识字段--失败标示
		String FAILURE = "0";
		// 自定义标识字段--成功标示
		String SUCCESS = "1";
		// 匿名的字符出
	}

	//数字
	interface Number{
		// 数值
		int ZERO = 0;
		int ONE = 1;
		int TWO = 2;
		int THREE = 3;
		int FOUR = 4;
		int FIVE = 5;
		int SIX = 6;
		int SEVEN = 7;
		int EIGHT = 8;
		int NINE = 9;
		int TEN = 10;
	}

	//媒体类型
	interface MediaType{
		// json编码
		String APPLICATION_XML = "application/xml";
		String APPLICATION_XML_UTF_8 = "application/xml; charset=UTF-8";

		String JSON = "application/json";
		String JSON_UTF_8 = "application/json; charset=UTF-8";

		String APPLICATION_XWWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
		String APPLICATION_XWWW_FORM_URLENCODED_UTF_8 = "application/x-www-form-urlencoded;charset=UTF-8";

		String JAVASCRIPT = "application/javascript";
		String JAVASCRIPT_UTF_8 = "application/javascript; charset=UTF-8";

		String APPLICATION_XHTML_XML = "application/xhtml+xml";
		String APPLICATION_XHTML_XML_UTF_8 = "application/xhtml+xml; charset=UTF-8";


		String TEXT_PLAIN = "text/plain";
		String TEXT_PLAIN_UTF_8 = "text/plain; charset=UTF-8";

		String TEXT_XML = "text/xml";
		String TEXT_XML_UTF_8 = "text/xml; charset=UTF-8";

		String TEXT_HTML = "text/html";
		String TEXT_HTML_UTF_8 = "text/html; charset=UTF-8";

	}

	//媒体类型
	interface DBOperType{

		Integer INSERT = 1;
		Integer DELETE = 2;
		Integer UPDATE = 3;
		Integer SELECT = 4;
	}

}
