package com.lvmama.finance.settlement.ibatis.po;

import java.util.Date;
import java.util.UUID;

import com.lvmama.finance.base.util.DateUtil;

public class OrdSettlementPayment {
    private Long settlementPaymentId;

    private Long targetId;
    
    private String targetName;

    private Long settlementId;

    private String paytype;

    private Double amount;

    private String bank;

    private String serial;

    private Date operatetime;

    private String remark;

    private Long creator;

    private Date createtime;
    
    private String supplierName;
    
    // 团号
    private String travelGroupCode;
    // 类型
    private String branchName;
    // 成本项
    private String productName;
    //币种
    private String currency;
    //发票回收时间
    private Date invoiceRetdate;
    //汇率
    private Double rate;
    
    private Long groupSettlementId;
    
    public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getSettlementPaymentId() {
        return settlementPaymentId;
    }

    public void setSettlementPaymentId(Long settlementPaymentId) {
        this.settlementPaymentId = settlementPaymentId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Date getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	//金额（元）
	public double getAmountYuan(){
		if(amount == null){
			return 0.00;
		}
		return amount / 100.00;
	}
	//打款时间（yyyy-MM-dd HH:mm:ss)
	public String getOperatetimeFormat(){
		if (operatetime != null) {
			return DateUtil.dateToString(operatetime, "yyyy-MM-dd HH:mm");
		}
		return "";
		
	}
	//添加款时间（yyyy-MM-dd HH:mm:ss)
	public String getCreatetimeFormat(){
		if (createtime != null) {
			return DateUtil.dateToString(createtime, "yyyy-MM-dd HH:mm");
		}
		return "";
	}
	

	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public String getRowId(){
		return UUID.randomUUID().toString();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getInvoiceRetdate() {
		return invoiceRetdate;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public void setInvoiceRetdate(Date invoiceRetdate) {
		this.invoiceRetdate = invoiceRetdate;
	}

	public Long getGroupSettlementId() {
		return groupSettlementId;
	}

	public void setGroupSettlementId(Long groupSettlementId) {
		this.groupSettlementId = groupSettlementId;
	}
}