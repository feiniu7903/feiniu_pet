package com.lvmama.comm.pet.po.lvmamacard;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.vo.Constant;

public class StoredCardOut implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3772082601796287254L;
	private Integer outId;
	private String outCode; // 出库单号
	private Date outDate; // 出库日期
	private Integer outStatus; // 出库状态
	private String salePerson; // 销售人员
	private Integer saleFlag;//是否销售
	private String remarks; // 备注
	private String saleToCompany;// 购买公司名称
	private String salePersonInfor1;
	private String salePersonInfor2;
	private String salePersonInfor3;
	private String salePersonInfor4;
	private Double saleDiscountAmount; // 销售转让额度
	private Double saleRebate; // 客户返点
	private Double saleBonus;// 销售奖金比率
	private Double saleDisMoney;// 折让额度金额
	private Double saleRebateMoney;// 客户返点金额
	private Double saleBonusMoney;// 销售奖金金额
	private Double saleSum;// 销售总额
	private String contractId; // 合同号
	private List<StoredCardOutDetails> details;
	
	private Long countSum; // 查询卡总数
	private Long moneySum; // 查询总金额
	private String cardNo; //卡号
	private Long amount;  //面值
	private Long balance;  //余额
	
	
	
	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	

	public Integer getOutId() {
		return outId;
	}

	public void setOutId(Integer outId) {
		this.outId = outId;
	}

	public Integer getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Integer outStatus) {
		this.outStatus = outStatus;
	}

	public String getSalePerson() {
		return salePerson;
	}

	public void setSalePerson(String salePerson) {
		this.salePerson = salePerson;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<StoredCardOutDetails> getDetails() {
		return details;
	}

	public void setDetails(List<StoredCardOutDetails> details) {
		this.details = details;
	}

	public String getSaleToCompany() {
		return saleToCompany;
	}

	public void setSaleToCompany(String saleToCompany) {
		this.saleToCompany = saleToCompany;
	}

	public Double getSaleDiscountAmount() {
		return saleDiscountAmount;
	}

	public void setSaleDiscountAmount(Double saleDiscountAmount) {
		this.saleDiscountAmount = saleDiscountAmount;
	}

	public Double getSaleRebate() {
		return saleRebate;
	}

	public void setSaleRebate(Double saleRebate) {
		this.saleRebate = saleRebate;
	}

	public Double getSaleBonus() {
		return saleBonus;
	}

	public void setSaleBonus(Double saleBonus) {
		this.saleBonus = saleBonus;
	}

	public Double getSaleDisMoney() {
		return saleDisMoney;
	}

	public void setSaleDisMoney(Double saleDisMoney) {
		this.saleDisMoney = saleDisMoney;
	}

	public Double getSaleRebateMoney() {
		return saleRebateMoney;
	}

	public void setSaleRebateMoney(Double saleRebateMoney) {
		this.saleRebateMoney = saleRebateMoney;
	}

	public Double getSaleBonusMoney() {
		return saleBonusMoney;
	}

	public void setSaleBonusMoney(Double saleBonusMoney) {
		this.saleBonusMoney = saleBonusMoney;
	}

	public Double getSaleSum() {
		return saleSum;
	}

	public void setSaleSum(Double saleSum) {
		this.saleSum = saleSum;
	}
	
	public String getCnOutStatus() {
		if(null!=outStatus){
			return Constant.CARD_OUT_STATUS.getCname(outStatus.toString());
		}else {
			return "";
		}
			
	}

	public String getSalePersonInfor1() {
		return salePersonInfor1;
	}

	public void setSalePersonInfor1(String salePersonInfor1) {
		this.salePersonInfor1 = salePersonInfor1;
	}

	public String getSalePersonInfor2() {
		return salePersonInfor2;
	}

	public void setSalePersonInfor2(String salePersonInfor2) {
		this.salePersonInfor2 = salePersonInfor2;
	}

	public String getSalePersonInfor3() {
		return salePersonInfor3;
	}

	public void setSalePersonInfor3(String salePersonInfor3) {
		this.salePersonInfor3 = salePersonInfor3;
	}

	public String getSalePersonInfor4() {
		return salePersonInfor4;
	}

	public void setSalePersonInfor4(String salePersonInfor4) {
		this.salePersonInfor4 = salePersonInfor4;
	}

	public Integer getSaleFlag() {
		return saleFlag;
	}

	public void setSaleFlag(Integer saleFlag) {
		this.saleFlag = saleFlag;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Long getCountSum() {
		return countSum;
	}

	public void setCountSum(Long countSum) {
		this.countSum = countSum;
	}

	public Long getMoneySum() {
		return moneySum;
	}

	public void setMoneySum(Long moneySum) {
		this.moneySum = moneySum;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	public String getOutDateStr(){
		if(outDate != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			return sdf.format(outDate);
		}
		return "";
	}
}
