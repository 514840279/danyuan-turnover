package org.danyuan.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 文件名 ： Redirects.java
 * 包 名 ： tk.ainiyue.danyuan.application.common.controller
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2017年10月10日 上午10:13:43
 * 版 本 ： V1.0
 */
@Controller
public class Redirects {
	
	@RequestMapping(path = { "/index", "/", "/index.html", "/home" })
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("index");
		return view;
	}
	
	@RequestMapping("/templates/{path1}/{path2}/{page}")
	public String templates(@PathVariable("path1") String path1, @PathVariable("path2") String path2, @PathVariable("page") String page) {
		return path1 + "/" + path2 + "/" + page;
	}
	
	@RequestMapping("/templates/{path1}/{path2}")
	public String templates(@PathVariable("path1") String path1, @PathVariable("path2") String path2) {
		return path1 + "/" + path2;
	}

	@RequestMapping("/templates/{path1}")
	public String templates(@PathVariable("path1") String path1) {
		return path1;
	}
}
