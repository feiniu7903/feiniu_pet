package com.lvmama.pet.sweb.shop;
/**
 * @author shangzhengyuan
 * @createTime 2012-10-25
 * @description edit cooperation coupon stock
 */
import java.io.BufferedReader;
import java.io.File;
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
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.shop.ShopCooperationCoupon;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.service.shop.ShopCooperationCouponService;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.utils.pic.UploadCtrl;
@Results({
	@Result(name = "edit", location = "/WEB-INF/pages/shop/product/editStock.jsp")
	})
public class EditStockAction extends BackBaseAction {

	private static final long serialVersionUID = -1393100009205578059L;
	
	private static final Logger LOG = Logger.getLogger(EditStockAction.class);
	
	/**
	 * 合作网站优惠券接口
	 */
	private ShopCooperationCouponService shopCooperationCouponService;
	
	private ShopProductService shopProductService;
	
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
	
	private String cleanOldData;
	
	/**
	 * 上传文件
	 */
	private File file;
	private String fileContentType;
	private String fileFileName;
	/**
	 * 出错优惠券列表
	 */
	List<ShopCooperationCoupon> errorList;
	
	private String messageText;
	/**
	 * 查询
	 * @throws UnsupportedEncodingException 
	 */
	@Action("/shop/cooperateCoupon")
	public String excute() throws UnsupportedEncodingException {
		if(getRequest().getMethod().equals("GET")){
			productName = new String(productName.getBytes("ISO-8859-1"),"UTF-8");
		}
		query();
		return "edit";
	}

	public void query(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		parameters.put("used", "N");//未使用
		count=shopCooperationCouponService.count(parameters);
	}
	/**
	 * 保存合作网站优惠券信息
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	@Action("/shop/saveCooperateCouponStock")
	public String save(){
		final String validateExp = "^[A-Za-z0-9\\s\\u4E00-\\u9FA5]+$";
		if (!StringUtils.isNotEmpty(couponString)) {
			if (null == file || StringUtils.isEmpty(fileContentType)
					|| StringUtils.isEmpty(fileFileName)) {
				messageText="没有上传文件";
				return "edit";
			}
		}
		Object[] array = null;
		if (null != file) {
			try {
				UploadCtrl.postToRemote(file, fileFileName);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String temp = null;
				List<String> list = new ArrayList<String>();
				while ((temp = reader.readLine()) != null) {
					list.add(temp);
				}
				IOUtils.closeQuietly(reader);
				array = list.toArray();
			} catch (Exception e) {
				LOG.warn("read upload file throw Exception info:\r\n" + e);
				messageText="读取上传文件出错";
				return "edit";
			} finally {

			}
		} else {
			array = couponString.split("\r\n|\n");
		}
		List<ShopCooperationCoupon> couponList = new ArrayList<ShopCooperationCoupon>();
		errorList = new ArrayList<ShopCooperationCoupon>();
		String currentUserName = "local";
		try{
			currentUserName = getSessionUserName();
		}catch(Exception e){
			
		}
		for (int i = 0; i < array.length; i++) {
			String str = (String) array[i];
			if (StringUtils.isNotEmpty(str)) {
				ShopCooperationCoupon coupon = new ShopCooperationCoupon();
				coupon.setProductId(productId);
				coupon.setCouponInfo(str);
				coupon.setCreateUser(currentUserName);
				if (str.length() < 100 && str.matches(validateExp)) {
					couponList.add(coupon);
				} else {
					coupon.setId(i + 1L);
					errorList.add(coupon);
					if (i == 100) {
						break;
					}
				}
			}
		}
		if (errorList.size() > 0) {
			return "edit";
		}
		if (couponList.size() > 0) {
			if (StringUtils.isNotEmpty(cleanOldData)
					&& !"false".equalsIgnoreCase(cleanOldData)) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("productId", productId);
				parameters.put("used", "N");
				shopCooperationCouponService.batchDeleteCoupon(parameters);
			}
			int result = shopCooperationCouponService
					.batchInsertCoupon(couponList,StringUtils.isNotEmpty(cleanOldData)
							&& !"false".equalsIgnoreCase(cleanOldData),productId,currentUserName);
			if (result == 0) {
				messageText="增加优惠券库存失败";
			}
		}
		messageText="增加优惠券库存成功";
		query();
		ShopProduct shopProduct =shopProductService.queryByPk(productId);
		shopProduct.setStocks(count);
		shopProductService.save(shopProduct, currentUserName);
		return "edit";
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
	public List<ShopCooperationCoupon> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<ShopCooperationCoupon> errorList) {
		this.errorList = errorList;
	}
	public String getCouponString() {
		return couponString;
	}
	public void setCouponString(String couponString) {
		this.couponString = couponString;
	}
	public String getCleanOldData() {
		return cleanOldData;
	}
	public void setCleanOldData(String cleanOldData) {
		this.cleanOldData = cleanOldData;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getMessageText(){
		return messageText;
	}

	public void setShopProductService(ShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}
}
