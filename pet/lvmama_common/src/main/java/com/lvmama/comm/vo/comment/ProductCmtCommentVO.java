/**
 * 
 */
package com.lvmama.comm.vo.comment;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 产品点评对象
 * @author liuyi
 *
 */
public class ProductCmtCommentVO   extends CommonCmtCommentVO  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1790763804332205998L;

	/**
	 * 产品类型
	 */
	private String productType;
	
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 订单NO
	 */
	private String orderNo;
	
	/**
	 * 产品上线
	 */
	private String productOnline;
	
	/**
	 * 产品下线时间
	 */
	private Date productOfflineTime;
	
	/**
	 * 相关产品是否有效
	 */
	private String isProductValid;
	
	
	/**
	 * 产品可售价
	 */
	private Long productSellPrice;
	
	/**
	 * 产品渠道
	 */
	private String productChannel;
	
	private String productLargeImage;
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public String getProductLargeImgUrl() {
		//return Constant.getInstance().getPrifix580x290Pic() + getPlaceSmallImage();
		return Constant.getInstance().getPrifix580x290Pic() + getProductLargeImage();
	}
	
	public String getProductSmallImgUrl() {
		return Constant.getInstance().getPrifix580x290Pic() + getSmallImage();
	}
	
	public float getCashRefundYuan() {
		if (null == this.getCashRefund()) {
			return 0f;/**@author liudong 下面一行调用会报错*/
			//return (Float) null; 
		} else {
			return PriceUtil.convertToYuan(this.getCashRefund());
		}
	}
	
	/**
	 * 产品类型
	 * @return 产品类型中文名
	 */
	public String getChProductType() {
		if ("TICKET".equals(getProductType())) {
			return "门票";
		}
		if ("HOTEL".equals(getProductType())) {
			return "酒店产品";
		}
		if ("ROUTE".equals(getProductType())) {
			return "线路";
		}
		return "";
	}
	
	/**
	 * 点评的产品是否可售，产品和点评的逻辑混在一起不好。
	 * 由于PC没有提供很好的产品逻辑暂时这样，将来要重构出单独的产品逻辑
	 * @return
	 */
	public boolean getProductOfCommentSellable() {
		
		if(StringUtils.isEmpty(this.getProductChannel()) || this.getProductChannel().indexOf("FRONTEND") == -1)
		{
			//非前台渠道产品不可售
			//prodcut channle可能是个组合值，例如FRONTEND,BACKEND
			return false;
		}
		
		if ("Y".equals(this.getIsProductValid()) && this.getProductOnline() != null && this.getProductOfflineTime() != null) {
			Date now = new Date();
			if ("true".equals(this.getProductOnline()) && now.before(this.getProductOfflineTime())
					&& this.getProductSellPrice() != null) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 *  获取主题名称
	 * @return String
	 */
	public String getSubjectName(){
		return (getProductName() != null ? getProductName() : "");
	}
	
	/**
	 *  获取主题类型
	 * @return String
	 */
	public String getSubjectType(){
		return (getChProductType() != null ? getChProductType() : "");
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductOnline() {
		return productOnline;
	}

	public void setProductOnline(String productOnline) {
		this.productOnline = productOnline;
	}

	public Date getProductOfflineTime() {
		return productOfflineTime;
	}

	public void setProductOfflineTime(Date productOfflineTime) {
		this.productOfflineTime = productOfflineTime;
	}

	public String getIsProductValid() {
		return isProductValid;
	}

	public void setIsProductValid(String isProductValid) {
		this.isProductValid = isProductValid;
	}

	public Long getProductSellPrice() {
		return productSellPrice;
	}

	public void setProductSellPrice(Long productSellPrice) {
		this.productSellPrice = productSellPrice;
	}

	public String getProductChannel() {
		return productChannel;
	}

	public void setProductChannel(String productChannel) {
		this.productChannel = productChannel;
	}

	public String getProductLargeImage() {
		return productLargeImage;
	}

	public void setProductLargeImage(String productLargeImage) {
		this.productLargeImage = productLargeImage;
	}

}
