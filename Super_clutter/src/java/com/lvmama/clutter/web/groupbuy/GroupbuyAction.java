package com.lvmama.clutter.web.groupbuy;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.model.MobileGroupOn;
import com.lvmama.clutter.service.IClientOfflineCacheService;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.web.BaseAction;

/**
 * 团购. 
 * @author qinzubo
 *
 */
@Results({ 
	@Result(name="groupbuy",location="/WEB-INF/pages/groupbuy/groupbuy.html",type="freemarker"),
	@Result(name="groupbuy_ajax",location="/WEB-INF/pages/groupbuy/groupbuy_ajax.html",type="freemarker"),
	@Result(name="groupby_detail",location="/WEB-INF/pages/groupbuy/groupbuy_detail.html",type="freemarker")
})
@Namespace("/mobile/groupbuy")
public class GroupbuyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 产品服务 
	 */
	IClientProductService mobileProductService;
	
	/**
	 * 
	 */
	IClientOfflineCacheService mobileOfflineCacheService;
	
	List<MobileGroupOn> mglist; // 团购列表. 

	private int page = 1; // 第几页 
	private String sort;  // 排序
	private String productType; // 产品类型，多个用逗号隔开
	private String toDest; // 目的地
	
	private String productId; // 产品id 
	private boolean ajax;// 是否异步请求
	/**
	 *  团购首页. 
	 */
	@SuppressWarnings("unchecked")
	@Action("groupbuy")
	public String index() {
		if(ajax) {
			Map<String,Object> param = new HashMap<String,Object>();
			initParams(param);
			Map<String,Object> map = mobileProductService.getGroupOnList(param);
			if(null != map && null != map.get("datas")) {
				mglist = (List<MobileGroupOn>)map.get("datas");
				getRequest().setAttribute("isLastPage", map.get("isLastPage"));
			}
			// 设置图片前缀 
			this.setImagePrefix();
			return "groupbuy_ajax";
		}
		// 查询列表信息
		Map<String,Object> p = new HashMap<String,Object>();
		p.put("method", "api.com.cache.getGroupCitiesCache"); // 缓存用
		Map<String,Object> mlist = mobileOfflineCacheService.getGroupCitiesCache(p);
		getRequest().setAttribute("mlist", mlist);
		if(null != mlist.get("cities")) {
			getRequest().setAttribute("cityJson", JSONArray.fromObject(mlist.get("cities")));
		} else {
			getRequest().setAttribute("cityJson", "{}");
		}
		return "groupbuy";
	}

	/**
	 * 初始化查询参数 
	 * @param param
	 */
	public void initParams(Map<String,Object> param) {
		if(page < 1) {
			page = 1;
		}
		param.put("page", page);
		
		if(!StringUtils.isEmpty(sort)) {
			param.put("sort", sort);
		}
		if(!StringUtils.isEmpty(productType)) {
			param.put("productType", productType);
		}
		if(!StringUtils.isEmpty(toDest)) {
			try {
				toDest = URLDecoder.decode(toDest,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			param.put("toDest", toDest);
		}
	}
	
	
	/**
	 *  团购首页. 
	 */
	@Action("groupby_detail")
	public String groupbyDetail() {
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("productId", productId);
			// 如果是线路产品 
		/*	if(Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)) {
				MobileProductRoute mobileProductRoute = api_com_product.getRouteDetail(params);
				getRequest().setAttribute("mobileProduct", mobileProductRoute);
			} else {
				MobileProduct mobileProduct = api_com_product.getProduct(params);
				getRequest().setAttribute("mobileProduct", mobileProduct);
			}*/
			
			MobileGroupOn mailGroupOn = mobileProductService.getGroupOnDetail(params);
			String productNameTitle="";
			if(mailGroupOn!=null && mailGroupOn.getProductName()!=null){
				if(mailGroupOn.getProductName().length()>30){
					productNameTitle=getProductTitle(mailGroupOn.getProductName());
				}else{
					productNameTitle=mailGroupOn.getProductName();
				}
			}
			getRequest().setAttribute("mobileProduct", mailGroupOn);
			getRequest().setAttribute("productNameTitle", productNameTitle);
			if(mailGroupOn!=null){
				getRequest().setAttribute("productType", mailGroupOn.getProductType());
			}
			
			// 设置图片前缀 
			this.setImagePrefix();
		}catch(Exception e) {
			
		}
		return "groupby_detail";
	}
	/**
	 * 产品名在页面TITLE限制30字
	 * @param productName
	 * @return
	 */
	public String getProductTitle(String productName){
		String ss=productName.substring(0, 30);
		return ss;
	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<MobileGroupOn> getMglist() {
		return mglist;
	}

	public void setMglist(List<MobileGroupOn> mglist) {
		this.mglist = mglist;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getToDest() {
		return toDest;
	}

	public void setToDest(String toDest) {
		this.toDest = toDest;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public void setMobileProductService(IClientProductService mobileProductService) {
		this.mobileProductService = mobileProductService;
	}

	public void setMobileOfflineCacheService(
			IClientOfflineCacheService mobileOfflineCacheService) {
		this.mobileOfflineCacheService = mobileOfflineCacheService;
	}

}
