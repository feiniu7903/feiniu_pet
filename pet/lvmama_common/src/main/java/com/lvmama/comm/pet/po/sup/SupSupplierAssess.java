package com.lvmama.comm.pet.po.sup;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 供应商考核
 * @author yuzhibing
 *
 */
public class SupSupplierAssess implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3821273270182549288L;

	private Long supplierAssessId;

    private Long supplierId;

    private Long assessPoints;

    private String assessMemo;
    
    private String operatorName;
    
    private Date createTime;
    
    public Long getSupplierAssessId() {
        return supplierAssessId;
    }

    public void setSupplierAssessId(Long supplierAssessId) {
        this.supplierAssessId = supplierAssessId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getAssessPoints() {
        return assessPoints;
    }

    public void setAssessPoints(Long assessPoints) {
        this.assessPoints = assessPoints;
    }
    
    /**
     * 
     * @param type minus时更改为负数
     */
    public void changeSymbol(String type){
    	if(StringUtils.equals(type, "minus")){
    		assessPoints=0-assessPoints;
		}
    }

    public String getAssessMemo() {
        return assessMemo;
    }

    public void setAssessMemo(String assessMemo) {
        this.assessMemo = assessMemo == null ? null : assessMemo.trim();
    }

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}