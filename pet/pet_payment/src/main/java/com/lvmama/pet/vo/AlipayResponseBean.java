package com.lvmama.pet.vo;

import java.util.ArrayList;
import java.util.List;
/**
 * 支付宝的对账记录VO
 * @author ranlongfei 2012-7-2
 * @version
 */
public class AlipayResponseBean {

	private String isSuccess;
	
	private String error;
	
	private String hasNextPage;
	
	private List<AlipayAccountLogVO> accountLogList = new ArrayList<AlipayAccountLogVO>();

	public void addAccountLogList(AlipayAccountLogVO log) {
		accountLogList.add(log);
	}
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(String hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public List<AlipayAccountLogVO> getAccountLogList() {
		return accountLogList;
	}

	public void setAccountLogList(List<AlipayAccountLogVO> accountLogList) {
		this.accountLogList = accountLogList;
	}
	
}
