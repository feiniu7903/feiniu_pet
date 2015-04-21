package com.lvmama.pet.web.shop.shopProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.util.Clients;

import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.ZkMessage;
/**
 * 产品的查询
 * @author Brian
 *
 */
public class ListProductAction  extends com.lvmama.pet.web.BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 498073296144921559L;
	/**
	 * 产品管理的逻辑层
	 */
	private ShopProductService shopProductService;
	/**
	 * 产品列表
	 */
	private List<ShopProduct> productList = new ArrayList<ShopProduct>();
	/**
	 * 查询条件
	 */
	private Map<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * 查询
	 */
	public void doQuery() {
		initialPageInfoByMap(shopProductService.count(parameters), parameters);
		productList = shopProductService.query(parameters);
	}

	/**
	 *  修改兑换类型的查询条件
	 * @param type 产品类型
	 */
	public void changeChangeType(final String type) {
		if (StringUtils.isEmpty(type)) {
			parameters.remove("changeType");
		} else {
			parameters.put("changeType", type);
		}
	}

	/**
	 *  修改产品类型的查询条件
	 * @param type 产品类型
	 */
	public void changeProductType(final String type) {
		if (StringUtils.isEmpty(type)) {
			parameters.remove("productType");
		} else {
			parameters.put("productType", type);
		}
	}

	/**
	 *  修改产品上线状态的查询条件
	 * @param type 上线状态
	 */
	public void changeProductIsValid(final String isValidStatus) {
		if (StringUtils.isEmpty(isValidStatus)) {
			parameters.remove("isValid");
		} else {
			parameters.put("isValid", isValidStatus);
		}
	}
	
	/**
	 * 修改上下线属性
	 * @param param 参数
	 */
	@SuppressWarnings("rawtypes")
	public void changeValid(final Map param) {
		if (null == param || null == param.get("productId")) {
			ZkMessage.showError("无法找到相关的产品信息");
		}
		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			ZkMessage.showInfo("请先登陆.");
			return;
		}
		ShopProduct p = shopProductService.queryByPk((Long) param.get("productId"));
		p.setIsValid((String) param.get("isValid"));
		shopProductService.save(p, getSessionUserName());
		if("Y".equals(p.getIsValid())) {
			ZkMessage.showInfo("商品已上线!");
		} else {
			ZkMessage.showInfo("商品已下线!");
		}
		this.refreshComponent("search");
	}
	
	public void showEditStock(final Map param){
		String currentUser = getSessionUserName();
		if(StringUtils.isEmpty(currentUser)){
			ZkMessage.showInfo("请先登陆.");
			return;
		}
		Long productId = (Long)param.get("productId");
		String productName=(String)param.get("productName");
		String productType=(String)param.get("productType");
		if(Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(productType)){
			Clients.evalJavaScript("window.open('/pet_back/shop/cooperateCoupon.do?productId="+productId+"&productName="+productName+"', '编辑合作网站优惠券库存', 'height=600, width=1000, top=300, right=100, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');");   
		}
	}
	
	public void setShopProductService(ShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}

	public List<ShopProduct> getProductList() {
		return productList;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(final Map<String, Object> parameters) {
		this.parameters = parameters;
	}

}
