package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
/**
 * 
 * Ebooking房价变价信息类.
 *
 */
public class EbkHousePrice implements Serializable {
    
	private static final long serialVersionUID = 1464043385911239829L;
	//申请单号.
	private Long housePriceId;
	//酒店ID.
    private Long supplierId;
    //酒店名称.
    private String supplierName;
    //采购产品ID.
    //采购产品名称.
    private Long metaProductId;
    private String productName;
    //采购类别名称.
    private String branchName;
	//房型ID.
    private Long metaBranchId;
    //申请主题.
    private String subject;
    //开始日期.
    private Date startDate;
	//结束日期.
    private Date endDate;
    //适用星期.
    private String applyWeek;
    //结算价.
    private Long settlementPrice;
    //建议售价.
    private Long suggestPrice;
    //门市价.
    private Long marketPrice;
    //早餐.
    private Long breakfastCount;
    //提交人.
    private String submitUser;
    //提交时间.
    private Date createTime;
    //申请状态.
    private String status;
    //审核人.
    private String confirmUser;
    //审核时间.
    private Date confirmTime;
    //备注.
    private String memo;
	//增减库存数量
	private Long stockAddOrMinus;		
	//是否超卖
	private String isOverSale;
	//是否增加库存
	private String isAddDayStock;
	//是否关班
	private String isStockZero;
	//产品类型
	private String productType;
	//申请类型
	private String applyType;
    //--非持久性属性--.
    //采购产品类别名称.
    private String metaProductBranchName;
    //供应商.
    private SupSupplier supSupplier;
     

	public String getMetaProductBranchName() {
		return metaProductBranchName;
	}

	public void setMetaProductBranchName(String metaProductBranchName) {
		this.metaProductBranchName = metaProductBranchName;
	}

	public Long getHousePriceId() {
        return housePriceId;
    }

    public void setHousePriceId(Long housePriceId) {
        this.housePriceId = housePriceId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getMetaProductId() {
        return metaProductId;
    }

    public void setMetaProductId(Long metaProductId) {
        this.metaProductId = metaProductId;
    }

    public Long getMetaBranchId() {
        return metaBranchId;
    }

    public void setMetaBranchId(Long metaBranchId) {
        this.metaBranchId = metaBranchId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getApplyWeek() {
        return applyWeek;
    }

    public void setApplyWeek(String applyWeek) {
        this.applyWeek = applyWeek;
    }

    public Long getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(Long settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public Long getSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(Long suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getBreakfastCount() {
        return breakfastCount;
    }

    public void setBreakfastCount(Long breakfastCount) {
        this.breakfastCount = breakfastCount;
    }

    public String getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(String submitUser) {
        this.submitUser = submitUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    public String getApplyWeekString() {
    	if (StringUtils.isEmpty(this.applyWeek)) {
    		return "全部";
    	}
    	String[] applyWeekArray = this.applyWeek.split(",");
    	if (applyWeekArray.length == 7) {
    		return "全部";
    	}
    	return this.applyWeek;
    }
    
    public Constant.EBK_SUGGEST_AUDIT_STATUS getAuditStatus() {
    	return Constant.EBK_SUGGEST_AUDIT_STATUS.valueOf(this.status);
    }
    
    public float getSettlementPriceYuan() {
    	return PriceUtil.convertToYuan(this.getSettlementPrice());
    }
    public float getSuggestPriceYuan() {
    	return PriceUtil.convertToYuan(this.getSuggestPrice());
    }
    public float getMarketPriceYuan() {
    	return PriceUtil.convertToYuan(this.getMarketPrice());
    }
    public String getSuggestPriceYuanStr() {
    	String s = String.valueOf(PriceUtil.convertToYuan(this.getSuggestPrice()));
    	if(s.endsWith(".0")||s.endsWith(".00")){
    		return s.substring(0,s.indexOf("."));
    	}
    	return s;
    }
    public String getMarketPriceYuanStr() {
    	String s = String.valueOf(PriceUtil.convertToYuan(this.getMarketPrice()));
    	if(s.endsWith(".0")||s.endsWith(".00")){
    		return s.substring(0,s.indexOf("."));
    	}
    	return s;
    }
    public SupSupplier getSupSupplier() {
		return supSupplier;
	}

	public void setSupSupplier(SupSupplier supSupplier) {
		this.supSupplier = supSupplier;
	}
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIsOverSale() {
		return isOverSale;
	}

	public void setIsOverSale(String isOverSale) {
		this.isOverSale = isOverSale;
	}

	public String getIsAddDayStock() {
		return isAddDayStock;
	}

	public void setIsAddDayStock(String isAddDayStock) {
		this.isAddDayStock = isAddDayStock;
	}

	public Long getStockAddOrMinus() {
		return stockAddOrMinus;
	}

	public void setStockAddOrMinus(Long stockAddOrMinus) {
		this.stockAddOrMinus = stockAddOrMinus;
	}

	public String getIsStockZero() {
		return isStockZero;
	}

	public void setIsStockZero(String isStockZero) {
		this.isStockZero = isStockZero;
	}
	@Override
	public String toString() {
		return "EbkHousePrice [housePriceId=" + housePriceId + ", supplierId="
				+ supplierId +", supplierName="+supplierName+ ", metaProductId=" + metaProductId
				+ ", metaBranchId=" + metaBranchId + ", subject=" + subject
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", applyWeek=" + applyWeek + ", settlementPrice="
				+ settlementPrice + ", suggestPrice=" + suggestPrice
				+ ", marketPrice=" + marketPrice + ", breakfastCount="
				+ breakfastCount + ", submitUser=" + submitUser
				+ ", createTime=" + createTime + ", status=" + status
				+ ", confirmUser=" + confirmUser + ", confirmTime="
				+ confirmTime + ", memo=" + memo + "]";
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}


}