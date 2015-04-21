package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class MobileActBaiduOrder implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long productid;
    
    private Long orderid;

	private String baiduUserid;

    private String lvUserid;
    
    private Long placeid; // 景点id

	private Date createDate;

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

    public String getBaiduUserid() {
        return baiduUserid;
    }

    public void setBaiduUserid(String baiduUserid) {
        this.baiduUserid = baiduUserid == null ? null : baiduUserid.trim();
    }

    public String getLvUserid() {
        return lvUserid;
    }

    public void setLvUserid(String lvUserid) {
        this.lvUserid = lvUserid == null ? null : lvUserid.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
    public Long getPlaceid() {
		return placeid;
	}

	public void setPlaceid(Long placeid) {
		this.placeid = placeid;
	}

}