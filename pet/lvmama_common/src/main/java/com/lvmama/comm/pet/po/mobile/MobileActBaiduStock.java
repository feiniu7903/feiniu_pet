package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class MobileActBaiduStock implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long productid;

    private Long quantity;

    private Long canSelQty;

    private String valid="1"; // 默认有效  

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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
}