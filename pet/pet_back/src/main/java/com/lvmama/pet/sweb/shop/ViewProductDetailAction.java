package com.lvmama.pet.sweb.shop;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.shop.ShopProduct;

/**
 * 产品预览的Action
 * @author Brian
 */
@Results({
		@Result(name = "viewProductDetail", location = "/shop/shopProduct/viewProductDetail.ftl", type = "freemarker") })
public class ViewProductDetailAction  extends BackBaseAction {
	/**
	 * 虚列值
	 */
	private static final long serialVersionUID = -2240990352751933080L;
	/**
	 * 产品对象
	 */
	private ShopProduct product;

	/**
	 * 显示产品详细页面
	 * @return 预览页面
	 * @throws IOException  IO错误
	 */
	@Action("/shop/shopProduct/showProductDetail")
	public String showProductDetail() throws IOException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		product = (ShopProduct) session.getAttribute("SHOP_PRODUCT");

		//新增页面传递new product(),不能预览
		if (null == product.getProductName()) {
			HttpServletRequest req = (HttpServletRequest) ServletActionContext.getRequest();
			HttpServletResponse res = (HttpServletResponse) ServletActionContext.getResponse();
			String host = req.getHeader("Host");
			res.sendRedirect("http://" + host + "/shop/index.html");
		}

		return "viewProductDetail";
	}

	public ShopProduct getProduct() {
		return product;
	}

}

