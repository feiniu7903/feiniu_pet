package com.lvmama.pet.sweb.shop.shopProduct;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.shop.ShopLog;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.shop.ShopProductCondition;
import com.lvmama.comm.pet.po.shop.ShopProductCoupon;
import com.lvmama.comm.pet.service.shop.ShopCooperationCouponService;
import com.lvmama.comm.pet.service.shop.ShopLogService;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.pic.UploadCtrl;
import com.lvmama.comm.vo.Constant;
/**
 * 产品的相关操作增删改查
 * @author YuanXueBo
 *
 */
@Results({
	@Result(name = "list", location = "/WEB-INF/pages/back/shop/shopProduct/index.jsp"),
	@Result(name = "editShopProudct",location="/WEB-INF/pages/back/shop/shopProduct/editShopProudct.jsp" ),
	@Result(name = "toViewLog",location="/WEB-INF/pages/back/shop/shopProduct/viewLog.jsp" )
})
public class NewListProductAction  extends BackBaseAction {
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
	private String productCode;
	private String productName;
	private String changeType;
	private String productType;
	private String  isValid;
	private String query;
	/**
	 * POJO对象
	 */
	private ShopProduct shopProduct;
	
	/**
	 * 优惠券标识
	 */
	private Long couponId;
	/**
	 * 主键
	 */
	private Long productId;
	/**
	 * 上传的图片列表
	 */
	private File[] fileData;
	/**
	 * 上传图片的文件名
	 */
	private String[] fileDataFileName;
	/**
	 * 兑换限制
	 */
	private Boolean isCheckEmail;
	private Boolean isCheckOrder;
	private Boolean isCheckNum;
	private String num;
	/**
	 * 产品日志列表
	 */
	private List<ShopLog> logList;
	/**
	 * 日志逻辑层
	 */
	private ShopLogService shopLogService;
	/**
	 * 合作网站优惠券接口
	 */
	private ShopCooperationCouponService shopCooperationCouponService;

	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@Action(value="/shop/shopProduct/queryProductList")
	public String doQuery() throws Exception{
		Long totalRecords=0l;
		pagination = Page.page(10, page);
		if(!"N".equals(query)){
			Map<String,Object> searchConds = new HashMap<String,Object>();
			if (StringUtils.isNotBlank(productCode)) {
				searchConds.put("productCode", productCode);
			}
			if (StringUtils.isNotBlank(productName)) {
				searchConds.put("productName", productName);
			}
			if (StringUtils.isNotBlank(changeType)) {
				searchConds.put("changeType", changeType);
			}
			if (StringUtils.isNotBlank(productType)) {
				searchConds.put("productType", productType);
			}
			if (StringUtils.isNotBlank(isValid)) {
				searchConds.put("isValid", isValid);
			}
			totalRecords=shopProductService.count(searchConds);
			searchConds.put("_startRow", pagination.getStartRows());
			searchConds.put("_endRow", pagination.getEndRows());
			productList = shopProductService.query(searchConds);
		}
		pagination.setTotalResultSize(totalRecords);
		pagination.setUrl(getReqUrl());
		return "list";
	}
	/**
	 * 打开新增修改页面
	 * 
	 * @return
	 */
	@Action(value="/shop/shopProduct/addOrModifyShopProduct")
	public String addOrModifyShopProduct(){
		if (null == productId) {
			shopProduct = new ShopProduct();
		} else {
			shopProduct = shopProductService.queryByPk(productId);
			if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(shopProduct.getProductType())) {
				this.couponId = ((ShopProductCoupon) shopProduct).getCouponId();
			}
			//取出限制条件
			List<ShopProductCondition> shopProductConditions=shopProduct.getShopProductConditions();
			if(shopProductConditions!=null && shopProductConditions.size()>0){
				for(ShopProductCondition shopProductCondition:shopProductConditions){
					if(Constant.SHOP_PRODUCT_CONDITION.CHECK_EXCHANGE_EMAIL.getCode().equals(shopProductCondition.getConditionX())){
						isCheckEmail=true;
					}else if(Constant.SHOP_PRODUCT_CONDITION.CHECK_EXCHANGE_ORDER.getCode().equals(shopProductCondition.getConditionX())){
						isCheckOrder=true;
					}else if(Constant.SHOP_PRODUCT_CONDITION.CHECK_EXCHANGE_NUM.getCode().equals(shopProductCondition.getConditionX())){
						isCheckNum=true;
						num=shopProductCondition.getConditionY();
					}
				}
			}
			Map<String, Object> parametes = new HashMap<String, Object>();
			parametes.put("objectId", productId);
			parametes.put("objectType", "SHOP_PRODUCT");
		}
		return "editShopProudct";
	}
	
	/**
	 * 保存或更新
	 * @return 
	 */
	@Action(value="/shop/shopProduct/savaOrUpdateShopProduct",results=@Result(name="toList",location="/shop/shopProduct/queryProductList.do",type="redirect",params={"productCode","${productCode}","productName","${productName}","changeType","${changeType}","productType","${productType}","isValid","${isValid}"}))
	public String savaOrUpdateShopProduct()throws Exception{
		//设置结束时间
		if(shopProduct.getEndTime()!=null){
			shopProduct.setEndTime(DateUtil.getDayEnd(shopProduct.getEndTime()));
		}
		if (null != fileData && null != fileDataFileName) {
			shopProduct.setPictures(savaPic());
		}
		if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(shopProduct.getProductType())) {
			ShopProductCoupon cp = new ShopProductCoupon();
			org.springframework.beans.BeanUtils.copyProperties(shopProduct, cp);
			cp.setCouponId(this.couponId);
			productId=shopProductService.insertOrUpdateShopProduct(cp, getSessionUserName());
		} else {
			//如何是合作网站优惠券则更新库存
			if(Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(shopProduct.getProductType())){
				Long count=0l;
				if(shopProduct.getProductId()!=null){
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("productId", shopProduct.getProductId());
					parameters.put("used", "N");//未使用
					count=shopCooperationCouponService.count(parameters);
				}
				shopProduct.setStocks(count);
			}
			productId=shopProductService.insertOrUpdateShopProduct(shopProduct, getSessionUserName());
		}
		return "toList";
	}
	
	
	/**
	 * 取反积分商城的上线状态，即原本上线的更新为下线，下线的变为上线
	 * 
	 */
	@Action(value="/shop/shopProduct/editValidStatus")
	public void editShopProductValidStatus() {
		Map<String, Object> param = new HashMap<String, Object>();
		shopProduct = shopProductService.queryByPk(productId);
		
		if (null == shopProduct) {
			param.put("success", false);
			param.put("errorMessage", "找不到相应的积分产品");
		} else {
			if ("Y".equals(shopProduct.getIsValid())) {
				shopProduct.setIsValid("N");
			} else {
				shopProduct.setIsValid("Y");
			}
			//更新
			productId=shopProductService.insertOrUpdateShopProduct(shopProduct, getSessionUserName());
			param.put("success", true);
			param.put("successMessage", "积分产品上/下线成功");
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			
		}
	}
	/**
	 * 查看日志
	 * @return
	 */
	@Action(value="/shop/shopProduct/viewLog")
	public String viewLog(){
		Map<String, Object> parametes = new HashMap<String, Object>();
		parametes.put("objectId", productId);
		logList = shopLogService.query(parametes);
		return "toViewLog";
	}
	
	
	/**
	 * 获取图片
	 * @return 图片的集合
	 */
	@SuppressWarnings("static-access")
	private String savaPic()throws Exception {
		String imageUrl="";
		if (null != fileData && null != fileDataFileName) {
			for (int i = 0; i < fileData.length; i++) {
				UploadCtrl uc = new UploadCtrl();
				String fileName = "super/"
						+ DateUtil.getFormatDate(new Date(), "yyyy/MM/dd")+"/"
						+ fileDataFileName[i];
					imageUrl += ","+uc.postToRemote(fileData[i], fileName);
			}
		}
		if(imageUrl!=null && !"".equals(imageUrl)){
			imageUrl=imageUrl.substring(1);
		}
		return imageUrl;
	}
	
	/**
	 * 获取分页参数
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getReqUrl() {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> pns = getRequest().getParameterNames();
		while (pns.hasMoreElements()) {
			String pn = pns.nextElement();
			if ("page".equalsIgnoreCase(pn)) {
				continue;
			}
			if ("productName".equalsIgnoreCase(pn)) {
				try {
					sb.append(pn + "=" + new String(getRequest().getParameter(pn).getBytes("ISO8859-1"),"UTF-8") + "&");
				} catch (Exception e) {
				
				}
				continue;
			}
			if ("productCode".equalsIgnoreCase(pn)) {
				try {
					sb.append(pn + "=" + new String(getRequest().getParameter(pn).getBytes("ISO8859-1"),"UTF-8") + "&");
				} catch (Exception e) {
				
				}
				continue;
			}
			sb.append(pn + "=" + getRequest().getParameter(pn) + "&");
		}
		return "/pet_back/shop/shopProduct/queryProductList.do?" + sb.toString() + "page=";
		
	}
	
	public void setShopProductService(ShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}

	public List<ShopProduct> getProductList() {
		return productList;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		if(getRequest().getMethod().equals("GET")){
			try {
				this.productCode = new String(productCode.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e);
			}
		}else{
			this.productCode=productCode;
		}

	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		if(getRequest().getMethod().equals("GET")){
			try {
				this.productName = new String(productName.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error(e);
			}
		}else{
			this.productName=productName;
		}

	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public ShopProduct getShopProduct() {
		return shopProduct;
	}
	public void setShopProduct(ShopProduct shopProduct) {
		this.shopProduct = shopProduct;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public File[] getFileData() {
		return fileData;
	}
	public void setFileData(File[] fileData) {
		this.fileData = fileData;
	}
	public String[] getFileDataFileName() {
		return fileDataFileName;
	}
	public void setFileDataFileName(String[] fileDataFileName) {
		this.fileDataFileName = fileDataFileName;
	}
	public Boolean getIsCheckEmail() {
		return isCheckEmail;
	}
	public void setIsCheckEmail(Boolean isCheckEmail) {
		this.isCheckEmail = isCheckEmail;
	}
	public Boolean getIsCheckOrder() {
		return isCheckOrder;
	}
	public void setIsCheckOrder(Boolean isCheckOrder) {
		this.isCheckOrder = isCheckOrder;
	}
	public Boolean getIsCheckNum() {
		return isCheckNum;
	}
	public void setIsCheckNum(Boolean isCheckNum) {
		this.isCheckNum = isCheckNum;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public void setLogList(List<ShopLog> logList) {
		this.logList = logList;
	}
	public List<ShopLog> getLogList() {
		return logList;
	}
	public void setShopLogService(ShopLogService shopLogService) {
		this.shopLogService = shopLogService;
	}
	public void setShopCooperationCouponService(
			ShopCooperationCouponService shopCooperationCouponService) {
		this.shopCooperationCouponService = shopCooperationCouponService;
	}

}
