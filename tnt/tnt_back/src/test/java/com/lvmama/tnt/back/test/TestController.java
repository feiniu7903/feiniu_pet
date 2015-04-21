package com.lvmama.tnt.back.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.tnt.user.po.TntUser;

@Controller
@RequestMapping("/test")
public class TestController {

	/** 分销商类型列表 **/
	@RequestMapping
	public String index(Model model) {
		model.addAttribute(new TntUser());
		return "test";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String post(Model model) {
		return "redirect:test";
	}

	@RequestMapping("/remote")
	public void remote(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		PrintWriter writer = response.getWriter();
		writer.print(false);
		writer.flush();
		writer.close();
	}
}
