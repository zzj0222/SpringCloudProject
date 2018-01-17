package com.laiai.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ribbon")
public class RibbonTestApi {

	@Value("${server.port}")
	private String port;

	@RequestMapping(value = "/name", method = RequestMethod.GET)
	public String getMyBlogNameApi() {
		return "您请求的服务器端口是：" + port;
	}
}
