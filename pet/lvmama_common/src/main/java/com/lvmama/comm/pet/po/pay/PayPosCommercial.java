package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;


/**
 * 银行POS商户实体类.
 * @author huyunyan
 *
 */
public class PayPosCommercial implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -837312560010784041L;
	/**
	 * POS商户ID.
	 */
	private Long commercialId;
	/**
	 * POS商户编号.
	 */
    private String commercialNo;
    /**
     * POS商户名称.
     */
    private String commercialName;
    /**
     * POS商户备注.
     */
    private String remark;
    /**
     * POS商户状态.
     */
    private String status;
    
    private String supplier;
    
    private String zhSupplier;
    
	public Long getCommercialId() {
		return commercialId;
	}
	public void setCommercialId(Long commercialId) {
		this.commercialId = commercialId;
	}
	public String getCommercialNo() {
		return commercialNo;
	}
	public void setCommercialNo(String commercialNo) {
		this.commercialNo = commercialNo;
	}
	public String getCommercialName() {
		return commercialName;
	}
	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getZhSupplier() {
		if(this.getSupplier()!=null){
			return Constant.PAY_POS_SUPPLIER_TYPE.getCnName(this.getSupplier());
		}
		return zhSupplier;
	}
	
}
