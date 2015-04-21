package com.lvmama.comm.pet.po.info;
import java.io.Serializable;
import java.util.Date;


public class InfoProductInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -682407960375666943L;

	private Long prodInfoId;

    private String attentionItem;

    private String hotLine;

    private String back;

    private String settle;

    private String cooperator;

    private String isSuper;

    private Long productId;

    private String productType;
    private String other;
    private Date createTime;

    public Long getProdInfoId() {
        return prodInfoId;
    }

    public void setProdInfoId(Long prodInfoId) {
        this.prodInfoId = prodInfoId;
    }

    public String getAttentionItem() {
        return attentionItem;
    }

    public void setAttentionItem(String attentionItem) {
        this.attentionItem = attentionItem == null ? null : attentionItem.trim();
    }

    public String getHotLine() {
        return hotLine;
    }

    public void setHotLine(String hotLine) {
        this.hotLine = hotLine == null ? null : hotLine.trim();
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back == null ? null : back.trim();
    }

    public String getSettle() {
        return settle;
    }

    public void setSettle(String settle) {
        this.settle = settle == null ? null : settle.trim();
    }

    public String getCooperator() {
        return cooperator;
    }

    public void setCooperator(String cooperator) {
        this.cooperator = cooperator == null ? null : cooperator.trim();
    }

    public String getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(String isSuper) {
        this.isSuper = isSuper == null ? null : isSuper.trim();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}