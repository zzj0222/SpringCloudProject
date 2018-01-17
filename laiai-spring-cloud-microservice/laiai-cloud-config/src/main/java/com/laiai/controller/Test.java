package com.laiai.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class Test {
	
	@Value("${server.port}")
	private String port;

	
	@RequestMapping("/test")
	public String getInfo() {
		return "从Github仓库中获取" + port;
//		return "从Github仓库中获取得到我博客信息：【" + location + "," + "," + url + "," + name + "】";
	}
}
