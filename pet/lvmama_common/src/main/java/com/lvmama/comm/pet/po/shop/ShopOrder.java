package com.lvmama.comm.pet.po.shop;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 商城订单
 * @author ganyingwen
 *
 */
public class ShopOrder implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1456566664564864565L;
	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 应付金额
	 */
	private Long oughtPay;
	/**
	 * 实付金额
	 */
	private Long actualPay;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 产品ID
	 */
	private Long productId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品类型
	 */
	private String productType;	
	/**
	 * 数量
	 */
	private int quantity;
	/**
	 * 收件人名字
	 */
	private String name;
	/**
	 * 收件人地址
	 */
	private String address;
	/**
	 * 收件人手机
	 */
	private String mobile;
	/**
	 * 收件人邮政编码
	 */
	private String zip;	
	/**
	 * 订单备注
	 */
	private String remark;
	/**
	 * 商品信息
	 */
	private String productInfo;
	
	public ShopOrder() {
		super();
	}
	
	public ShopOrder(Long orderId, Date createTime, Long oughtPay,
			Long actualPay, Long userId, String orderStatus, Long productId,
			int quantity, String name, String address, String mobile, String zip,String productInfo) {
		super();
		this.orderId = orderId;
		this.createTime = createTime;
		this.oughtPay = oughtPay;
		this.actualPay = actualPay;
		this.userId = userId;
		this.orderStatus = orderStatus;
		this.productId = productId;
		this.quantity = quantity;
		this.name = name;
		this.address = address;
		this.mobile = mobile;
		this.zip = zip;
		this.productInfo = productInfo;
	}

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getFormatterCreatedTime() {
		if (createTime != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(createTime);
		} else {
			return null;
		}
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getOughtPay() {
		return oughtPay;
	}
	public void setOughtPay(Long oughtPay) {
		this.oughtPay = oughtPay;
	}
	public Long getActualPay() {
		return actualPay;
	}
	public void setActualPay(Long actualPay) {
		this.actualPay = actualPay;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}	public String getProductName() {
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
	
	/**
	 * 格式化产品类型
	 * @return
	 */
	public String getFormatProductType() {
		if(StringUtil.isEmptyString(productType)){
			return null;
		}
		return Constant.SHOP_PRODUCT_TYPE.getCnName(productType);
	}
	
	/**
	 * 格式化的订单状态
	 * @return
	 */
	public String getFormatOrderStatus() {
		if (Constant.ORDER_STATUS.FINISHED.name().equals(orderStatus)) {
			return "已发货";
		} else if (Constant.ORDER_STATUS.UNCONFIRM.name().equals(orderStatus)) {
			return "末发货";
		} else if (Constant.ORDER_STATUS.CANCEL.name().equals(orderStatus)) {
			return "取消";
		} else {
			return null;
		}
	}
	
	/**
	 * 订单是否取消
	 * @return
	 */
	public boolean isCancel() {
		if (Constant.ORDER_STATUS.CANCEL.name().equals(orderStatus)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 订单是否完成
	 * @return
	 */
	public boolean isFinished() {
		if (Constant.ORDER_STATUS.FINISHED.name().equals(orderStatus)) {
			return true;
		}
		return false;
	}
	
	public boolean isFinishedOrCancel() {
		return isCancel() || isFinished();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}
	
}
