package com.lvmama.comm.pet.po.lvmamacard;

import java.io.Serializable;

/**
 * 出库明细表
 * 
 * @author yifan
 * 
 */
public class StoredCardOutDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5488400405094947201L;
	private int outDetailsId;
	private int outDetailsAmount; // 面值
	private int outDetailsCount; // 数量
	private String cardNoBegin; // 卡号范围（开始）
	private String cardNoEnd; // 卡号范围（结束）
	private String outCode; // 出库单号

	public int getOutDetailsId() {
		return outDetailsId;
	}

	public void setOutDetailsId(int outDetailsId) {
		this.outDetailsId = outDetailsId;
	}

	public int getOutDetailsAmount() {
		return outDetailsAmount;
	}

	public void setOutDetailsAmount(int outDetailsAmount) {
		this.outDetailsAmount = outDetailsAmount;
	}

	public int getOutDetailsCount() {
		return outDetailsCount;
	}

	public void setOutDetailsCount(int outDetailsCount) {
		this.outDetailsCount = outDetailsCount;
	}

	public String getCardNoBegin() {
		return cardNoBegin;
	}

	public void setCardNoBegin(String cardNoBegin) {
		this.cardNoBegin = cardNoBegin;
	}

	public String getCardNoEnd() {
		return cardNoEnd;
	}

	public void setCardNoEnd(String cardNoEnd) {
		this.cardNoEnd = cardNoEnd;
	}

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

}
