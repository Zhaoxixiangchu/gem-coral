/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.controller.prekit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Title: ViewController
 * @Package: com.gemframework.controller
 * @Date: 2020-03-15 20:38:15
 * @Version: v1.0
 * @Description: 获取页面路径控制器
 * @Author: nine QQ 769990999
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@Controller
public class ViewController {
	
	@RequestMapping("prekit/{url}.html")
	public String prekit(@PathVariable("url") String url){
		return "modules/prekit/" + url;
	}
	@RequestMapping("prekit/{module}/{url}.html")
	public String prekit(@PathVariable("module") String module,@PathVariable("url") String url){
		return "modules/prekit/"+module+"/" + url;
	}

	@RequestMapping("extend/{url}.html")
	public String extend(@PathVariable("url") String url){
		return "modules/extend/" + url;
	}

	@RequestMapping("extend/{module}/{url}.html")
	public String extend(@PathVariable("module") String module,@PathVariable("url") String url){
		return "modules/extend/"+module+"/" + url;
	}

	@RequestMapping("error/{url}.html")
	public String error(@PathVariable("url") String url){
		return "common/error/" + url;
	}


	@GetMapping("login")
	public String login(){
		return "login";
	}

	@GetMapping("login1")
	public String login1(){
		return "login1";
	}

	@GetMapping({"/", "index"})
	public String index(){
		return "index";
	}

	@GetMapping(value = "/home")
	public String home() {
		return "home";
	}

	@RequestMapping("404")
	public String notFound(){
		return "404";
	}

}
