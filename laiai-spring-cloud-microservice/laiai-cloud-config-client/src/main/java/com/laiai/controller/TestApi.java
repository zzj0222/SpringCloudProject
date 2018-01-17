package com.laiai.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置中心-客户端展示API
 */

@RefreshScope
@RestController
public class TestApi {
	
	@Value("${server.port}")
	private String port;
	@Value("${neo.hello}")
	private String hello;

	@RequestMapping("/blog-info")
	public String getBlogInfo() {
		return "从Github仓库中获取" + hello;
//		return "从Github仓库中获取得到我博客信息：【" + location + "," + "," + url + "," + name + "】";
	}
}
