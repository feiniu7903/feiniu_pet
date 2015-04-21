package com.lvmama.comm.pet.po.info;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;

public class InfoHotProduct implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4092194845038812342L;

	private Long id;

    private String position;

    private String type;

    private String productName;

    private Long marketPrice;
    private float marketPriceYuan;
    private float memberPriceYuan;
    private float blacePriceYuan;
    private Long memberPrice;

    private Long blacePrice;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public float getMarketPriceYuan() {
    	if(this.marketPrice!=null)
    		return PriceUtil.convertToYuan(this.marketPrice);
    	return 0f;
	}

	public void setMarketPriceYuan(float marketPriceYuan) {
		this.marketPriceYuan = marketPriceYuan;
	}

	public float getMemberPriceYuan() {
		if(this.memberPrice!=null)
    		return PriceUtil.convertToYuan(this.memberPrice);
    	return 0f;
	}

	public void setMemberPriceYuan(float memberPriceYuan) {
		this.memberPriceYuan = memberPriceYuan;
	}

	public float getBlacePriceYuan() {
		if(this.blacePrice!=null)
    		return PriceUtil.convertToYuan(this.blacePrice);
    	return 0f;
	}

	public void setBlacePriceYuan(float blacePriceYuan) {
		this.blacePriceYuan = blacePriceYuan;
	}

	public Long getMarketPrice() {
    	return PriceUtil.convertToFen(this.marketPriceYuan);
    }
    public void setMarketPrice(Long marketPrice) {
    	this.marketPrice=marketPrice;
    }

    public Long getMemberPrice() {
        return  PriceUtil.convertToFen(this.memberPriceYuan);
    }

    public void setMemberPrice(Long memberPrice) {
        this.memberPrice =memberPrice;
    }

    public Long getBlacePrice() {
        return PriceUtil.convertToFen(this.blacePriceYuan);
    }

    public void setBlacePrice(Long blacePrice) {
        this.blacePrice =blacePrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}