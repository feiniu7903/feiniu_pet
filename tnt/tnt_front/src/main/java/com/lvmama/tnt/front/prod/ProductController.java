package com.lvmama.tnt.front.prod;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.tnt.front.BaseController;
import com.lvmama.tnt.order.service.OrderCreateService;
import com.lvmama.tnt.order.service.TntTimePriceService;
import com.lvmama.tnt.order.vo.TntBuyInfo;
import com.lvmama.tnt.order.vo.TntPriceInfo;

@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController {

	@Autowired
	private OrderCreateService orderCreateService;

	@Autowired
	private TntTimePriceService tntTimePriceService;

	@RequestMapping("/timePriceJson.do/{productId}")
	public void JSONTimePrice(@PathVariable Long productId,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long userId = this.getLoginUserId(session);
		String timeJson = tntTimePriceService.getJSONTimePrice(productId,
				userId);
		outputJsonMsg(request, response, timeJson);
	}

	@RequestMapping(value = "/ajaxPriceInfo")
	public void ajaxPriceInfo(TntBuyInfo buyInfo, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Long userId = this.getLoginUserId(session);
		buyInfo.setDistributor(userId.toString());
		JSONResult result = new JSONResult(response);
		TntPriceInfo priceInfo = orderCreateService.countPrice(buyInfo);
		try {
			JSONObject obj = new JSONObject();
			if (!priceInfo.isSuccess()) {
				obj.put("success", priceInfo.isSuccess());
				obj.put("msg", priceInfo.getMsg());
			} else {
				obj = net.sf.json.JSONObject.fromObject(priceInfo);
			}
			result.put("priceInfo", obj);
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(request, response, e);
		}
		result.output();
	}

	@RequestMapping(value = "/ajaxCheckDistPrice")
	public void checkDistPrice(TntBuyInfo buyInfo,
			HttpServletResponse response, HttpSession session) {
		Long userId = getLoginUserId(session);
		buyInfo.setDistributor(userId != null ? userId.toString() : null);
		Map<String, Object> map = orderCreateService.checkDistPrice(buyInfo);
		print(map, response);
	}

	@RequestMapping(value = "/ajaxCheckPrice")
	public void checkPrice(TntBuyInfo buyInfo, HttpServletResponse response) {
		Map<String, Object> map = orderCreateService.checkPrice(buyInfo);
		print(map, response);
	}

	private void print(Map<String, Object> map, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		if (map != null) {
			for (String key : map.keySet()) {
				json.put(key, map.get(key));
			}
		}
		response.setContentType("text/json; charset=gb2312");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.print(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

}
