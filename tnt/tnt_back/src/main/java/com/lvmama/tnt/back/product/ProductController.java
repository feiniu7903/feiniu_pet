package com.lvmama.tnt.back.product;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.tnt.comm.util.WebFetchPager;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.prod.po.TntProduct;
import com.lvmama.tnt.prod.service.TntProdProductService;
import com.lvmama.tnt.prod.service.TntProductService;
import com.lvmama.tnt.prod.vo.TntProdProduct;
import com.lvmama.tnt.user.service.TntChannelService;

@Controller
@RequestMapping("/product")
public class ProductController {

	protected Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private TntProdProductService tntProdProductService;

	@Autowired
	private TntProductService tntProductService;

	@Autowired
	private TntChannelService tntChannelService;

	/**
	 * 分销商预存款账户列表
	 * 
	 * @throws UnsupportedEncodingException
	 **/
	@RequestMapping(value = "/list")
	public String search(Model model, HttpServletRequest request,
			HttpServletResponse response, TntProduct t, Integer page)
			throws UnsupportedEncodingException {
		int pageNo = page != null ? page : 1;
		if (t == null)
			t = new TntProduct();
		if (t.getChannelId() != null)
			t.setValid(TntConstant.VALID_Y);
		Page<TntProduct> p = Page.page(pageNo, t);
		t.trim();
		List<TntProduct> tntProductList = list(p, request);
		model.addAttribute(tntProductList);
		model.addAttribute(Page.KEY, p);
		model.addAttribute(t);
		model.addAttribute("payTypeMap", getPayTypeMap());
		model.addAttribute("prodAperiodicMap",
				TntConstant.PROD_APERIODIC.toMap());
		model.addAttribute("validMap", getValidMap());
		model.addAttribute("channelMap", tntChannelService.getMap());

		Map<String, String> isDistMap = new HashMap<String, String>();
		isDistMap.put(TntConstant.VALID_Y, "分销");
		isDistMap.put(TntConstant.VALID_N, "不分销");
		model.addAttribute("isDistMap", isDistMap);
		return "/product/list";
	}

	protected List<TntProduct> list(Page<TntProduct> page,
			HttpServletRequest request) {
		WebFetchPager<TntProduct> pager = new WebFetchPager<TntProduct>(page,
				request) {

			@Override
			protected long getTotalCount(TntProduct t) {
				return tntProductService.count(t);
			}

			@Override
			protected List<TntProduct> fetchDetail(Page<TntProduct> page) {
				List<TntProduct> list = tntProductService.search(page);
				if (list != null && !list.isEmpty()) {
					for (TntProduct p : list) {
						TntProdProduct prod = tntProdProductService
								.getByBranchIdSure(p.getBranchId());
						p.setProd(prod);
					}
				}
				return list;
			}
		};
		return pager.fetch();
	}

	private Map<String, String> getValidMap() {
		HashMap<String, String> validMap = new HashMap<String, String>();
		validMap.put("true", "上线");
		validMap.put("false", "下线");
		return validMap;
	}

	private Map<String, String> getPayTypeMap() {
		HashMap<String, String> payTypeMap = new HashMap<String, String>();
		payTypeMap.put("true", "在线支付");
		payTypeMap.put("false", "景区支付");
		return payTypeMap;
	}
}
