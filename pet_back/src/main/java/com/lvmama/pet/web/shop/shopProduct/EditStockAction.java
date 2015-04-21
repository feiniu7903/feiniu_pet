package com.lvmama.pet.web.shop.shopProduct;
/**
 * @author shangzhengyuan
 * @createTime 2012-10-25
 * @description edit cooperation coupon stock
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Textbox;

import com.lvmama.comm.pet.po.shop.ShopCooperationCoupon;
import com.lvmama.comm.pet.service.shop.ShopCooperationCouponService;
public class EditStockAction extends com.lvmama.pet.web.BaseAction {

	private static final long serialVersionUID = -1393100009205578059L;
	
	private static final Logger LOG = Logger.getLogger(EditStockAction.class);
	
	/**
	 * 合作网站优惠券接口
	 */
	private ShopCooperationCouponService shopCooperationCouponService;
	
	/**
	 * 合作网站优惠券产品编号
	 */
	private Long productId;
	/**
	 * 合作网站优惠券产品名称
	 */
	private String productName;
	
	/**
	 * 此优惠券剩余总数
	 */
	private Long count;
	
	/**
	 * 合作网站优惠券
	 */
	private String couponString;
	
	private boolean cleanOldData;
	
	/**
	 * 上传文件
	 */
	private String furl;
	/**
	 * 查询
	 * @throws UnsupportedEncodingException 
	 */
	@Action("/shop/cooperateCoupon")
	public void doBefore(){
		query();
	}

	public void query(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		count=shopCooperationCouponService.count(parameters);
	}
	/**
	 * 保存合作网站优惠券信息
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@Action("/shop/saveCooperateCouponStock")
	public void save(){
		//final String validateExp = "^[A-Za-z0-9\\s\\u4E00-\\u9FA5]+$";
		if (null != getComponent().getFellowIfAny("furl")) {
			furl = ((Textbox) getComponent().getFellow("furl")).getValue();
		}
		if (StringUtils.isEmpty(couponString) && StringUtils.isEmpty(furl)) {
			alert("没有上传文件");
			return;
		}
		Object[] array = null;
		if (StringUtils.isNotEmpty(furl)) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(furl));
				String temp = null;
				List<String> list = new ArrayList<String>();
				while ((temp = reader.readLine()) != null) {
					list.add(temp);
				}
				IOUtils.closeQuietly(reader);
				array = list.toArray();
			} catch (Exception e) {
				LOG.warn("read upload file throw Exception info:\r\n" + e);
				alert("读取上传文件出错");
				return ;
			}
		} else {
			array = couponString.split("\r\n|\n");
		}
		List<ShopCooperationCoupon> couponList = new ArrayList<ShopCooperationCoupon>();
		String currentUserName=null; 
		try{
			currentUserName = getSessionUserName();
		}catch(Exception e){
			
		}
		if(StringUtils.isEmpty(currentUserName)){
			currentUserName = Sessions.getCurrent().getRemoteAddr();
		}
		for (int i = 0; i < array.length; i++) {
			String str = (String) array[i];
			if (StringUtils.isNotEmpty(str)) {
				ShopCooperationCoupon coupon = new ShopCooperationCoupon();
				coupon.setProductId(productId);
				coupon.setCouponInfo(str);
				coupon.setCreateUser(currentUserName);
				if (str.length() <= 400) {
					couponList.add(coupon);
				} else {
					alert("第"+(i+1)+"行大于400个字节");
					return;
				}
			}
		}
		if (couponList.size() > 0) {
			shopCooperationCouponService
					.batchInsertCoupon(couponList,cleanOldData,productId,currentUserName);
			query();
			refreshParent("search");
			getComponent().detach();
			alert("增加优惠券库存成功");
		}else{
			alert("没有取得优惠券");
		}
	}
	
	public void exportStock(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		List<ShopCooperationCoupon> couponList = shopCooperationCouponService.query(parameters);
		StringBuffer sb = new StringBuffer();
		final String COUPON_SIGN = "\r\n";
		for(ShopCooperationCoupon coupon:couponList){
			sb.append(coupon.getCouponInfo()).append(COUPON_SIGN);
		}
		try{
			Filedownload.save(sb.toString().getBytes("UTF-8"), "application/txt;charset=UTF-8",productId+"_stock.txt");
		}catch(Exception e){
			alert("下载失败");
		}
	}
	public ShopCooperationCouponService getShopCooperationCouponService() {
		return shopCooperationCouponService;
	}
	public void setShopCooperationCouponService(
			ShopCooperationCouponService shopCooperationCouponService) {
		this.shopCooperationCouponService = shopCooperationCouponService;
		
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getCouponString() {
		return couponString;
	}
	public void setCouponString(String couponString) {
		this.couponString = couponString;
	}
	public boolean getCleanOldData() {
		return cleanOldData;
	}
	public void setCleanOldData(boolean cleanOldData) {
		this.cleanOldData = cleanOldData;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}
}
