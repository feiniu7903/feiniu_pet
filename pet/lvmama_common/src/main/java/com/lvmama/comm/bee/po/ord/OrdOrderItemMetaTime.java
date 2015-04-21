package com.lvmama.comm.bee.po.ord;

import java.math.BigDecimal;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;


public class OrdOrderItemMetaTime implements java.io.Serializable,Comparable<OrdOrderItemMetaTime>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 497075303016894564L;

	private BigDecimal itemMetaTimeId;

    private Long orderItemMetaId;

    private Date createTime;

    private Long settlementPrice;

    private Long quatity;

    private Long marketPrice;
    
    private Date visitTime;

    private String stockReduced;
    
    private Long suggestPrice;
    
    private Long breakfastCount;
    
    private Long buyOutQuantity;
    
    public Long getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(Long suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

	public Long getBreakfastCount() {
		return breakfastCount;
	}

	public void setBreakfastCount(Long breakfastCount) {
		this.breakfastCount = breakfastCount;
	}

	public float getSettlementPriceYuan() {
		return PriceUtil.convertToYuan(this.settlementPrice);
	}
	public float getMarketPriceYuan() {
		return PriceUtil.convertToYuan(this.marketPrice);
	}
    public float getSuggestPriceYuan() {
    	return PriceUtil.convertToYuan(this.suggestPrice);
    }
    
    public BigDecimal getItemMetaTimeId() {
        return itemMetaTimeId;
    }

    public void setItemMetaTimeId(BigDecimal itemMetaTimeId) {
        this.itemMetaTimeId = itemMetaTimeId;
    }

    public Long getOrderItemMetaId() {
        return orderItemMetaId;
    }

    public void setOrderItemMetaId(Long orderItemMetaId) {
        this.orderItemMetaId = orderItemMetaId;
    }

    public Long getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(Long settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public Long getQuatity() {
        return quatity;
    }

    public void setQuatity(Long quatity) {
        this.quatity = quatity;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
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

	public String getStockReduced() {
		return stockReduced;
	}

	public void setStockReduced(String stockReduced) {
		this.stockReduced = stockReduced;
	}
	public boolean isHaveStockReduced() {
		return "true".equals(stockReduced);
	}
	public String getZhStockReduced(){
		if("true".equals(stockReduced)){
			return "已减库存";
		}else{
			return "未减库存";
		}
		
	}

	public Long getBuyOutQuantity() {
		return buyOutQuantity == null ? 0L : buyOutQuantity;
	}

	public void setBuyOutQuantity(Long buyOutQuantity) {
		this.buyOutQuantity = buyOutQuantity;
	}

	@Override
	public int compareTo(OrdOrderItemMetaTime o) {
		return (this.visitTime == null || o == null || o.visitTime == null) ? -1 : this.visitTime.compareTo(o.getVisitTime());
	}
}