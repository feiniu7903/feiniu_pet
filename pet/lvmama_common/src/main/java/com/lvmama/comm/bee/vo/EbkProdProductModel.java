package com.lvmama.comm.bee.vo;

import java.util.Date;

import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.vo.Constant;

public class EbkProdProductModel extends EbkProdProduct {

	private static final long serialVersionUID = -4814341064446257212L;
	
	/**上线时间**/
	private Date onlineTime;
	/**下线时间**/
	private Date offlineTime;
	/**上线状态**/
	private String onLine;
	/**供应商名称**/
	private String supSupplierName;
	/**申请人名称**/
	private String applyAuditUserName;
	
	public String getStatusCh(){
		return Constant.EBK_PRODUCT_AUDIT_STATUS.getCnNameByCode(getStatus());
	}
	public String getOnLineCh(){
		if("true".equalsIgnoreCase(onLine)){
			return "上线";
		}else if("false".equalsIgnoreCase(onLine)){
			return "下线";
		}
		return "未导入";
	}
	public Date getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	public Date getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}
	public String getOnLine() {
		return onLine;
	}
	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}
	public String getSupSupplierName() {
		return supSupplierName;
	}
	public void setSupSupplierName(String supSupplierName) {
		this.supSupplierName = supSupplierName;
	}
	public String getApplyAuditUserName() {
		return applyAuditUserName;
	}
	public void setApplyAuditUserName(String applyAuditUserName) {
		this.applyAuditUserName = applyAuditUserName;
	}
	
	
}
