package com.lvmama.tnt.recognizance.po;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.tnt.comm.util.PriceUtil;

/**
 * 保证金账户
 * 
 * @author gaoxin
 * @version 1.0
 */
public class TntRecognizance implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * recognizanceId
	 */
	private Long recognizanceId;
	/**
	 * userId
	 */
	private Long userId;
	/**
	 * 额度
	 */
	private Long limits;
	/**
	 * 余额
	 */
	private Long balance;

	// columns END
	/** 临时字段 **/
	private String userName; // 分销商用户名
	private String realName, companyName;// 分销商名称,公司名称

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public TntRecognizance() {
	}

	public TntRecognizance(Long recognizanceId) {
		this.recognizanceId = recognizanceId;
	}

	public void setRecognizanceId(Long value) {
		this.recognizanceId = value;
	}

	public Long getRecognizanceId() {
		return this.recognizanceId;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setLimits(Long value) {
		this.limits = value;
	}

	public Long getLimits() {
		return this.limits != null ? limits : 0;
	}

	public void setBalance(Long value) {
		this.balance = value;
	}

	public Long getBalance() {
		return this.balance != null ? balance : 0;
	}

	public String getNeedPay() {
		return limits > balance ? ""
				+ PriceUtil.convertToYuan(limits - balance) : "0";
	}

	@Override
	public String toString() {
		return "TntRecognizance [recognizanceId=" + recognizanceId
				+ ", userId=" + userId + ", limits=" + limits + ", balance="
				+ balance + "]";
	}

	public static enum TYPE {

		RECHARGE("RECHARGE", "充值"), DEBIT("DEBIT", "扣款"), SETLIMIT("LIMIT",
				"设置保证金");

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		private String value, desc;

		private TYPE(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static String getDesc(String value) {
			for (TYPE t : TYPE.values()) {
				if (t.getValue().equals(value))
					return t.getDesc();
			}
			return null;
		}

		public static boolean isRecharge(String type) {
			return RECHARGE.getValue().equals(type);
		}

		public static boolean isRecharge(TYPE type) {
			return RECHARGE.equals(type);
		}

		public static boolean isDebit(String type) {
			return DEBIT.getValue().equals(type);
		}

		public static boolean isDebit(TYPE type) {
			return DEBIT.equals(type);
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (TYPE t : values()) {
				map.put(t.getValue(), t.getDesc());
			}
			return map;
		}
	}

	public void trim() {
		if (realName != null)
			setRealName(realName.trim());
	}

	public String getLimitsY() {
		return "" + PriceUtil.convertToYuan(getLimits());
	}

	public void setLimitsY(String limitsY) {
		this.setLimits(PriceUtil.convertToFen(limitsY));
	}

	public String getBalanceY() {
		return "" + PriceUtil.convertToYuan(getBalance());
	}

	public String getCompanyName() {
		return companyName != null ? companyName : "个人";
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}
