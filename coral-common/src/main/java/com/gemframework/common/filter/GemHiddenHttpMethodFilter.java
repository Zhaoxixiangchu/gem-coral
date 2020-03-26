/**
 * 开源版本请务必保留此注释头信息，若删除gemframe官方保留所有法律责任追究！
 * 本软件受国家版权局知识产权以及国家计算机软件著作权保护（登记号：2018SR503328）
 * 不得恶意分享产品源代码、二次转售等，违者必究。
 * Copyright (c) 2020 gemframework all rights reserved.
 * http://www.gemframework.com
 * 版权所有，侵权必究！
 */
package com.gemframework.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.HiddenHttpMethodFilter;


/**
 * @Title: 处理form表单头的过滤器，
 * @Package: com.gemframework.common.filter
 * @Date: 2019/12/10 23:06
 * @Version: v1.0
 * @Description: 如果表单有_header字段，可以自动将该字段转为request的header头信息（增加一条头）
 * @Author: zhangysh
 * @Copyright: Copyright (c) 2019 GemStudio
 * @Company: www.gemframework.com
 */
@Slf4j
@Component
public class GemHiddenHttpMethodFilter extends HiddenHttpMethodFilter{


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getParameter("_header");
		if (header!=null && !header.trim().equals("")) {
			HttpServletRequest wrapper = new HttpHeaderRequestWrapper(request,header);
			super.doFilterInternal(wrapper, response, filterChain);
		}else {
			super.doFilterInternal(request, response, filterChain);
		}
	}

	private static class HttpHeaderRequestWrapper extends HttpServletRequestWrapper{

		private final String header;

		public HttpHeaderRequestWrapper(HttpServletRequest request,String header) {
			super(request);
			this.header = header;
		}

		@Override
		public String getHeader(String name) {
			if (name!=null &&
					name.equals("licence") &&
					super.getHeader("licence")==null) {
				return header;
			}else {
				return super.getHeader(name);
			}
		}

	}


}
