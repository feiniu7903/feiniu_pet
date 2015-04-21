package com.lvmama.report.po;


/**
 * 用户注册统计(整体状况)
 * 
 * @author yuzhizeng
 */
public class TotalAnalysisTV {
	/**
	 * 创建时间
	 */
	public String createTime;
	/**
	 * 用户总数
	 */
	public String userTotal;
	/**
	 * 邮箱填充总数
	 */
	public String emailTotal;
	/**
	 * 手机填充总数
	 */
	public String mobileTotal;
	/**
	 * 邮箱填充比率
	 */
	public String emailRatio;
	/**
	 * 手机填充比率
	 */
	public String mobileRatio;
	/**
	 * 邮箱验证总数
	 */
	public String emailCheckTotal;
	/**
	 * 手机验证总数
	 */
	public String mobileCheckTotal;
	/**
	 * 邮箱验证比率
	 */
	public String emailCheckRatio;
	/**
	 * 手机验证比率
	 */
	public String mobileCheckRatio;
	/**
	 * 购买比率
	 */
	public String orderRatio;
	/**
	 * 重复购买比率
	 */
	public String repeatOrderRadio;
	/**
	 * 交叉购买比率
	 */
	public String jcOrderRadio;
	/**
	 * sem购买比率
	 */
	public String semOrderRadio;
	/**
	 * free购买比率
	 */
	public String freeOrderRadio;
	/**
	 * 累积支付用户占比
	 */
	public String payRadio;
	/**
	 * 累积二次支付用户占比
	 */
	public String repeatPayRadio;
	/**
	 * 累积交叉支付用户占比
	 */
	public String jcPayRadio;
	/**
	 * 累积SEM注册支付用户占比
	 */
	public String semPayRadio;
	/**
	 * 累积非SEM用户注册支付占比
	 */
	public String freePayRadio;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUserTotal() {
		return userTotal;
	}

	public void setUserTotal(final String userTotal) {
		this.userTotal = userTotal;
	}

	public String getEmailCheckTotal() {
		return emailCheckTotal;
	}

	public void setEmailCheckTotal(final String emailCheckTotal) {
		this.emailCheckTotal = emailCheckTotal;
	}

	public String getMobileCheckTotal() {
		return mobileCheckTotal;
	}

	public void setMobileCheckTotal(final String mobileCheckTotal) {
		this.mobileCheckTotal = mobileCheckTotal;
	}

	public String getEmailTotal() {
		return emailTotal;
	}

	public void setEmailTotal(final String emailTotal) {
		this.emailTotal = emailTotal;
	}

	public String getMobileTotal() {
		return mobileTotal;
	}

	public void setMobileTotal(final String mobileTotal) {
		this.mobileTotal = mobileTotal;
	}

	public String getEmailCheckRatio() {
		return emailCheckRatio;
	}

	public void setEmailCheckRatio(String emailCheckRatio) {
		this.emailCheckRatio = emailCheckRatio;
	}

	public String getMobileCheckRatio() {
		return mobileCheckRatio;
	}

	public void setMobileCheckRatio(String mobileCheckRatio) {
		this.mobileCheckRatio = mobileCheckRatio;
	}

	public String getOrderRatio() {
		return orderRatio;
	}

	public void setOrderRatio(String orderRatio) {
		this.orderRatio = orderRatio;
	}

	public String getRepeatOrderRadio() {
		return repeatOrderRadio;
	}

	public void setRepeatOrderRadio(String repeatOrderRadio) {
		this.repeatOrderRadio = repeatOrderRadio;
	}

	public String getJcOrderRadio() {
		return jcOrderRadio;
	}

	public void setJcOrderRadio(String jcOrderRadio) {
		this.jcOrderRadio = jcOrderRadio;
	}

	public String getSemOrderRadio() {
		return semOrderRadio;
	}

	public void setSemOrderRadio(String semOrderRadio) {
		this.semOrderRadio = semOrderRadio;
	}

	public String getFreeOrderRadio() {
		return freeOrderRadio;
	}

	public void setFreeOrderRadio(String freeOrderRadio) {
		this.freeOrderRadio = freeOrderRadio;
	}

	public String getEmailRatio() {
		return emailRatio;
	}

	public void setEmailRatio(String emailRatio) {
		this.emailRatio = emailRatio;
	}

	public String getMobileRatio() {
		return mobileRatio;
	}

	public void setMobileRatio(String mobileRatio) {
		this.mobileRatio = mobileRatio;
	}

	public String getPayRadio() {
		return payRadio;
	}

	public void setPayRadio(String payRadio) {
		this.payRadio = payRadio;
	}

	public String getRepeatPayRadio() {
		return repeatPayRadio;
	}

	public void setRepeatPayRadio(String repeatPayRadio) {
		this.repeatPayRadio = repeatPayRadio;
	}

	public String getJcPayRadio() {
		return jcPayRadio;
	}

	public void setJcPayRadio(String jcPayRadio) {
		this.jcPayRadio = jcPayRadio;
	}

	public String getSemPayRadio() {
		return semPayRadio;
	}

	public void setSemPayRadio(String semPayRadio) {
		this.semPayRadio = semPayRadio;
	}

	public String getFreePayRadio() {
		return freePayRadio;
	}

	public void setFreePayRadio(String freePayRadio) {
		this.freePayRadio = freePayRadio;
	}
}
