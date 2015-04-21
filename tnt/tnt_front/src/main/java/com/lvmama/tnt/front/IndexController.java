package com.lvmama.tnt.front;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.order.vo.TntBuyInfo;
import com.lvmama.tnt.user.po.TntUser;

@Controller
public class IndexController {

	// @RequestMapping(value = "/index")
	public String index(Model model) {
		TntBuyInfo buyInfo = new TntBuyInfo();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("product_794860", 1);
		map.put("product_794840", 1);
		buyInfo.setBuyNum(map);
		model.addAttribute("buyInfo", buyInfo);
		model.addAttribute("visitDate", TntUtil.formatDate(new Date()));

		model.addAttribute("tntUser", new TntUser());
		return "index";
	}
}
