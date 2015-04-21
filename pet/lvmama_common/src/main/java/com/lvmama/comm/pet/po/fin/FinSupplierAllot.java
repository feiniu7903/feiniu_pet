package com.lvmama.comm.pet.po.fin;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 供应商分配
 * @author zhangwenjun
 *
 */
public class FinSupplierAllot extends FinanceBusiness {

	private Long supplierAllotId;
	// 供应商ID
	private Long supplierId;
	// 供应商名称
	private String supplierName;
	// 用户名
	private String userName;
	// 姓名
	private String realName;
	// 创建时间
	private Date createTime;
	// 新增时的供应商id
	private Long supplierId_hid;
	// 我方结算主体
	private String companyName;
	// 指派状态
	private String status;
	// 结算对象
	private String targetName;
	// 创建人
	private String createUser;
	
	// 指派供应商的id
	private Long supplierIdAdd;
	// 将供应商指派给的用户名
	private String userNameAdd;
	// 批量操作的供应商id集合
	private String supplierIds;
	private String userNameBatch;
	
	public String getSupplierIds() {
		return supplierIds;
	}
	public void setSupplierIds(String supplierIds) {
		this.supplierIds = supplierIds;
	}
	public String getUserNameBatch() {
		return userNameBatch;
	}
	public void setUserNameBatch(String userNameBatch) {
		this.userNameBatch = userNameBatch;
	}
	public Long getSupplierIdAdd() {
		return supplierIdAdd;
	}
	public void setSupplierIdAdd(Long supplierIdAdd) {
		this.supplierIdAdd = supplierIdAdd;
	}
	public String getUserNameAdd() {
		return userNameAdd;
	}
	public void setUserNameAdd(String userNameAdd) {
		this.userNameAdd = userNameAdd;
	}
	public Long getSupplierAllotId() {
		return supplierAllotId;
	}
	public void setSupplierAllotId(Long supplierAllotId) {
		this.supplierAllotId = supplierAllotId;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getCreateTimeStr() {
		return DateUtil.formatDate(this.createTime, "yyyy-MM-dd HH:mm:ss");
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getSupplierId_hid() {
		return supplierId_hid;
	}
	public void setSupplierId_hid(Long supplierId_hid) {
		this.supplierId_hid = supplierId_hid;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getZhCompanyName() {
		return Constant.SETTLEMENT_COMPANY.getCnName(companyName);
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getStatus() {
		if(null != userName && !"".equals(userName)){
			status = "已指派";
		} else {
			if(null != supplierName && !"".equals(supplierName)){
				status = "未指派";
			} else {
				status = "";
			}
		}
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}
