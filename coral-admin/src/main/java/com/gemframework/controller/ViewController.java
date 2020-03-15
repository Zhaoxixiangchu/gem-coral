package com.gemframework.controller;

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
	
	@RequestMapping("{url}.html")
	public String module(@PathVariable("url") String url){
		log.info("访问="+"modules/" + url);
		return "modules/" + url;
	}

	@GetMapping("login")
	public String login(){
		return "login";
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
