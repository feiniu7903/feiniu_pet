package com.lvmama.front.web.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.zkoss.json.JSONArray;

import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.ProdCProduct;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 产品推荐的Ajax Action
 * 根据第三方公司提供的用户浏览行为，购买记录等综合因素统计分析得出的推荐产品id，进行二次
 * 过滤来推荐给终端用户
 * @author Brian
 *
 */
public class AjaxProductRecommendAction extends ActionSupport {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 2351774446523834303L;
	/**
	 * url需返回的标识餐宿
	 */
	private String pid;
	/**
	 * 产品列表
	 */
	private String ids;
	/**
	 * 显示的产品数
	 */
	private String number = "5";
	/**
	 * 页面服务
	 */
	private PageService pageService;

	private List<AjaxRtnRecommendProduct> guessYourFavories = new ArrayList<AjaxRtnRecommendProduct>(5);
	
	/**
	 * 睿广的推荐
	 * 
	 */
	@Action("/ajax/uguideRecommend")
	public void  uguideRecommend() throws IOException {
		String[] idArray = ids.split(",");
		int favNumber = 5;
		try
		{
			favNumber = Integer.parseInt(number);
		}
		catch(Exception e)
		{
			favNumber = 5;
		}
		for (String id : idArray) {
			if (guessYourFavories.size() == favNumber) {
				break;
			}
			AjaxRtnRecommendProduct recommendProduct = null;
			String key = "AJAXRTNRECOMMENDPRODUCT_"+id;
			Object obj = MemcachedUtil.getInstance().get(key);
			if (obj!=null && obj instanceof AjaxRtnRecommendProduct) {
				recommendProduct=(AjaxRtnRecommendProduct)obj;
				recommendProduct.setSid(pid);
				guessYourFavories.add(recommendProduct);
			}else{
				ProdCProduct prodCProduct = pageService.getProdCProduct(Long.parseLong(id));
				if (prodCProduct==null || !prodCProduct.getProdProduct().isOnLine() || prodCProduct.getProdProduct()==null) {
					continue;
				} else {
					AjaxRtnRecommendProduct recommendProd = new AjaxRtnRecommendProduct(null, 
							prodCProduct.getProdProduct().getProductId(), 
							prodCProduct.getProdProduct().getProductName(),
							prodCProduct.getProdProduct().getAbsoluteSmallImageUrl(), 
							prodCProduct.getProdProduct().getSellPriceYuan());
					MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, recommendProd);
					recommendProd.setSid(pid);
					guessYourFavories.add(recommendProd);
				}
				
			}
		}
		printRtn(guessYourFavories);
	}
	
	/**
	 * 输出返回码
	 * @param bean
	 * @throws IOException
	 */
	private void printRtn(final List<AjaxRtnRecommendProduct> lists) throws IOException {
		ServletActionContext.getResponse().setContentType("text/json; charset=utf-8");
		if (ServletActionContext.getRequest().getParameter("jsoncallback") == null) {
			ServletActionContext.getResponse().getWriter().print(JSONArray.toJSONString(lists));
		} else {
			ServletActionContext.getResponse().getWriter().print(ServletActionContext.getRequest().getParameter("jsoncallback") + "(" + JSONArray.toJSONString(lists)+ ")");
		}
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public void setNumber(String number) {
		this.number = number;
	}		


}
