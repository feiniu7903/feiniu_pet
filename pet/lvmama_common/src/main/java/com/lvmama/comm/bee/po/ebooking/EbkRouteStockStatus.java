package com.lvmama.comm.bee.po.ebooking;
import java.util.Date;
/**
 * 
 * EBooking房态信息类.
 *
 */
public class EbkRouteStockStatus {
	private Long metaProductBranchId;
	
	//房型,采购产品类别的类型.
	private String metaProductBranchName;
	//房态改变的开始时间.
	private Date beginDate;
	//房态改变的结束时间.
	private Date endDate;
	//适用星期.
	private String applyWeek;
	//增、减保留房.  
	private String baoliu;
	//增、减保留房的数量.
	private int baoliuQuantity;
	//是否是超卖. 
	private String chaomai;
	//是否是满房. MAN_FANG_TRUE,MAN_FANG_FALSE
	private String manfang;
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getApplyWeek() {
		return applyWeek;
	}
	public void setApplyWeek(String applyWeek) {
		this.applyWeek = applyWeek;
	}
	public String getBaoliu() {
		return baoliu;
	}
	public void setBaoliu(String baoliu) {
		this.baoliu = baoliu;
	}
 
	public String getChaomai() {
		return chaomai;
	}
	public void setChaomai(String chaomai) {
		this.chaomai = chaomai;
	}
	public String getManfang() {
		return manfang;
	}
	public void setManfang(String manfang) {
		this.manfang = manfang;
	}
	public String getMetaProductBranchName() {
		return metaProductBranchName;
	}
	public void setMetaProductBranchName(String metaProductBranchName) {
		this.metaProductBranchName = metaProductBranchName;
	}
	public Long getMetaProductBranchId() {
		return metaProductBranchId;
	}
	public void setMetaProductBranchId(Long metaProductBranchId) {
		this.metaProductBranchId = metaProductBranchId;
	}
	
	public int getBaoliuQuantity() {
		return baoliuQuantity;
	}
	public void setBaoliuQuantity(int baoliuQuantity) {
		this.baoliuQuantity = baoliuQuantity;
	}
	@Override
	public String toString() {
		return "EbkHouseStatus [metaProductBranchId=" + metaProductBranchId
				+ ", metaProductBranchName=" + metaProductBranchName
				+ ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", applyWeek=" + applyWeek + ", baoliu=" + baoliu
				+ ", quantity=" + this.baoliuQuantity + ", chaomai=" + chaomai
				+ ", manfang=" + manfang + "]";
	}

}
