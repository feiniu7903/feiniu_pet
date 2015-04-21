package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录每天销售额 
 * @author qinzubo
 *
 */
public class MobileActBaidu implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long productid;

    private Date availableDate;

    private String amOrPm;

    private Long quantity;

    private Long canSelQty;

    private String valid;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public String getAmOrPm() {
        return amOrPm;
    }

    public void setAmOrPm(String amOrPm) {
        this.amOrPm = amOrPm == null ? null : amOrPm.trim();
    }

    public Long getQuantity() {
    	if(null == quantity) {
    		return 0l;
    	}
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getCanSelQty() {
    	if(null == canSelQty) {
    		return 0l;
    	}
        return canSelQty;
    }

    public void setCanSelQty(Long canSelQty) {
        this.canSelQty = canSelQty;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid == null ? null : valid.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}