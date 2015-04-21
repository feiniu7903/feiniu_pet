package com.lvmama.front.web.ajax;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.front.web.BaseAction;



/**
 * 
 * 销售产品信息检测操作
 * 
 * @author yanggan
 *
 */
public class AjaxProductAction  extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	
	private ProdProductService prodProductService;
	/**
	 * 检测销售产品库存数量
	 */
	@Action("/check/product/sale")
	public void checkSale(){
		String sale = "true";
		try{
			String pid = getRequest().getParameter("productId");
			TimePrice tp = prodProductService.selectLowestPriceByProductId(Long.parseLong(pid));
			sale = tp == null ? "false" : "true";
		}catch(Exception e){
			log.error("get product sale state error,exception message:"+e.getMessage());
			sale = "false";
		}
		sendAjaxMsg(sale);		
	}
	
	/**
	 * 检测销售产品库存数量
	 * @throws IOException 
	 */
	@Action("/ajax/product/productInfo")
	public void productInfo() throws IOException{
		ProdProduct tp=new ProdProduct();
 		try{
			String pid = getRequest().getParameter("productId");
		  tp = prodProductService.getProdProductById(Long.parseLong(pid));
 		}catch(Exception e){
			log.error("get product sale state error,exception message:"+e.getMessage());
 		}
 		printRtn(tp);	
	}
	private void printRtn(final Object object) throws IOException {
		String json = null;
		getResponse().setContentType("text/json; charset=utf-8");
		if (null == object) {
			return;
		} else {
			if (object instanceof java.util.Collection) {
				json = JSONArray.fromObject(object).toString();
			} else {
				json = JSONObject.fromObject(object).toString();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("返回对象:" + json);
			}
		}
		if (getRequest().getParameter("jsoncallback") == null) {
			getResponse().getWriter().print(json);
		} else {
				getResponse().getWriter().print(getRequest().getParameter(
						"jsoncallback") + "(" +json + ")");				
		}
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
}
