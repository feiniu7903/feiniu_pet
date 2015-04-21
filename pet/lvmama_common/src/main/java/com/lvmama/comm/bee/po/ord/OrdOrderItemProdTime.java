package com.lvmama.comm.bee.po.ord;

import java.util.Date;

public class OrdOrderItemProdTime implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4154315204116552977L;

	private Long itemProdTimeId;

    private Long orderItemProdId;

    private Date createTime;

    private Long price;

    private Long quantity;
    
    private Date visitTime;
    
    public Long getItemProdTimeId() {
        return itemProdTimeId;
    }

    public void setItemProdTimeId(Long itemProdTimeId) {
        this.itemProdTimeId = itemProdTimeId;
    }

    public Long getOrderItemProdId() {
        return orderItemProdId;
    }

    public void setOrderItemProdId(Long orderItemProdId) {
        this.orderItemProdId = orderItemProdId;
    }

  

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}


}