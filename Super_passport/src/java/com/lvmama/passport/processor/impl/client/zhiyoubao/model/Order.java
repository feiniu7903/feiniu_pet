package com.lvmama.passport.processor.impl.client.zhiyoubao.model;
 
import java.util.List;

import com.lvmama.passport.processor.impl.client.zhiyoubao.ZhiyoubaoUtil;
/**
 * 订单
 * 
 * @author gaoxin
 * 
 */
public class Order {

	private String certificateNo;// 身份证号
	private String linkName;// 联系人
	private String linkMobile;// 接收彩信的手机号（电信手机无法收到信息）
	private String orderCode;// 订单编码（或别的），要求唯一，回调我们通知检票完了的标识
	private String orderPrice;// 订单总价格
	private String groupNo;// 团号
	private String assistCheckNo;//对方的辅助码 
	private String payMethod;// 支付方式值为(spot,sign_bill,third_vm,on_line)
	private String payStatus;// 支付状态 （返回报文里边的）
	private String src;
	/** 景区订单集合 */
	private List<ScenicOrder> scenicOrders;
	/** 酒店订单列表 */
	private List<HotelOrder> hotelOrders;
	/** 餐饮订单 */
	private List<RepastOrder> repastOrders;
	/** 套票 */
	private List<FamilyOrder> familyOrders;

	public Order() {}

	public Order(String certificateNo, String linkName, String linkMobile,
			String orderCode, String orderPrice, String groupNo,
			String payMethod, List<ScenicOrder> scenicOrders,
			List<HotelOrder> hotelOrders, List<RepastOrder> repastOrders,
			List<FamilyOrder> familyOrders) {
		this.certificateNo = certificateNo;
		this.linkName = linkName;
		this.linkMobile = linkMobile;
		this.orderCode = orderCode;
		this.orderPrice = orderPrice;
		this.groupNo = groupNo;
		this.payMethod = payMethod;
		this.scenicOrders = scenicOrders;
		this.hotelOrders = hotelOrders;
		this.repastOrders = repastOrders;
		this.familyOrders = familyOrders;
	}

	/**
	 * 创建订单
	 * 
	 * @return
	 */
	public String toCreateOrderXml() {
		StringBuilder sbi = new StringBuilder();
		sbi.append("<order>");
		sbi.append(ZhiyoubaoUtil.buildElement("certificateNo", certificateNo))
			.append(ZhiyoubaoUtil.buildElement("linkName", linkName))
			.append(ZhiyoubaoUtil.buildElement("linkMobile", linkMobile))
			.append(ZhiyoubaoUtil.buildElement("orderCode", orderCode))
			.append(ZhiyoubaoUtil.buildElement("orderPrice", orderPrice))
			.append(ZhiyoubaoUtil.buildElement("groupNo", groupNo))
			.append(ZhiyoubaoUtil.buildElement("payMethod", payMethod));
		if (scenicOrders!=null&&!scenicOrders.isEmpty()) {
			sbi.append("<scenicOrders>");
			for (ScenicOrder scenicOrder : scenicOrders) {
				sbi.append(scenicOrder.toScenicOrderXml());
			}
			sbi.append("</scenicOrders>");
		} else if (hotelOrders!=null&&!hotelOrders.isEmpty()) {
			sbi.append("<hotelOrders>");
			for (HotelOrder hotelOrder : hotelOrders) {
				sbi.append(hotelOrder.toHotelOrderXml());
			}
			sbi.append("</hotelOrders>");
		} else if (repastOrders!=null&&!repastOrders.isEmpty()) {
			sbi.append("<repastOrders>");
			for (RepastOrder repastOrder : repastOrders) {
				sbi.append(repastOrder.toRepastOrderXml());
			}
			sbi.append("</repastOrders>");
		} else if (familyOrders!=null&&!familyOrders.isEmpty()) {
			sbi.append("<familyOrders>");
			for (FamilyOrder familyOrder : familyOrders) {
				sbi.append(familyOrder.toFamilyOrderXml());
			}
			sbi.append("</familyOrders>");
		}
		sbi.append("</order>");
		return sbi.toString();
	}

	/**
	 * 操作订单
	 * 
	 * @return
	 */
	public String toHandleOrderXml() {
		StringBuilder sbi = new StringBuilder();
		sbi.append("<order>").append(
				ZhiyoubaoUtil.buildElement("orderCode", orderCode)).append(
				"</order>");
		return sbi.toString();
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public List<ScenicOrder> getScenicOrders() {
		return scenicOrders;
	}

	public void setScenicOrders(List<ScenicOrder> scenicOrders) {
		this.scenicOrders = scenicOrders;
	}

	public List<HotelOrder> getHotelOrders() {
		return hotelOrders;
	}

	public void setHotelOrders(List<HotelOrder> hotelOrders) {
		this.hotelOrders = hotelOrders;
	}

	public List<RepastOrder> getRepastOrders() {
		return repastOrders;
	}

	public void setRepastOrders(List<RepastOrder> repastOrders) {
		this.repastOrders = repastOrders;
	}

	public List<FamilyOrder> getFamilyOrders() {
		return familyOrders;
	}

	public void setFamilyOrders(List<FamilyOrder> familyOrders) {
		this.familyOrders = familyOrders;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getAssistCheckNo() {
		return assistCheckNo;
	}

	public void setAssistCheckNo(String assistCheckNo) {
		this.assistCheckNo = assistCheckNo;
	}
}
