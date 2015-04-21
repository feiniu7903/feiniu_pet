package com.lvmama.tnt.comm.vo;

import java.util.HashMap;
import java.util.Map;

public class TntConstant {

	/**
	 * TNT登陆用户
	 */
	public final static String SESSION_TNT_USER = "SESSION_TNT_USER";
	/**
	 * TNT登录验证码
	 */
	public final static String SESSION_TNT_VALIDATE_CODE = "SESSION_TNT_VALIDATE_CODE";

	public final static String VALID_Y = "Y";

	public final static String VALID_N = "N";

	/**
	 * 用户标识
	 */
	public static enum USER_IDENTITY_TYPE {
		/**
		 * 用户名
		 */
		USER_NAME,
		/**
		 * 手机
		 */
		MOBILE,
		/**
		 * 邮箱地址
		 */
		EMAIL
	}

	/**
	 * 改变时需同事更改user/list.jsp中js function toFinal
	 * 
	 * @author hupeipei
	 * 
	 */
	public static enum USER_INFO_STATUS {

		WAITING("WAITING", "待审核"), REWAITING("REWAITING", "重审"), REREJECT(
				"REREJECT", "重审不通过"), REJECT("REJECT", "审核不通过"), NEEDACTIVE(
				"NEEDACTIVE", "账户需激活"), ACTIVED("ACTIVED", "账户已激活");

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

		private USER_INFO_STATUS(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static boolean isWaiting(String status) {
			return USER_INFO_STATUS.WAITING.getValue().equals(status);
		}

		public static boolean isReWaiting(String status) {
			return USER_INFO_STATUS.REWAITING.getValue().equals(status);
		}

		public static boolean isReject(String status) {
			return USER_INFO_STATUS.REJECT.getValue().equals(status);
		}

		public static boolean isReReject(String status) {
			return USER_INFO_STATUS.REREJECT.getValue().equals(status);
		}

		public static boolean isNeedActivate(String status) {
			return USER_INFO_STATUS.NEEDACTIVE.getValue().equals(status);
		}

		public static boolean isActived(String status) {
			return USER_INFO_STATUS.ACTIVED.getValue().equals(status);
		}

		public static boolean isAgreed(String status) {
			return isNeedActivate(status) || isActived(status);
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (USER_INFO_STATUS t : values()) {
				map.put(t.getValue(), t.getDesc());
			}
			return map;
		}
	}

	public static enum USER_MATERIAL_STATUS {

		WAITING("WAITING", "等待审核"), AGREE("AGREE", "审核通过"), REJECT("REJECT",
				"审核不通过");

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

		private USER_MATERIAL_STATUS(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static boolean isAgreed(String status) {
			return USER_MATERIAL_STATUS.AGREE.getValue().equals(status);
		}

		public static boolean isRejct(String status) {
			return USER_MATERIAL_STATUS.REJECT.getValue().equals(status);
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (USER_MATERIAL_STATUS t : values()) {
				map.put(t.getValue(), t.getDesc());
			}
			map.put(null, "待提交");
			return map;
		}

	}

	public static enum AUDIT_STATUS {
		PENDING_AUDIT("待审核"), PASS_AUDIT("审核通过"), REJECTED_AUDIT("审核不通过");

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		private String desc;

		private AUDIT_STATUS(String desc) {
			this.desc = desc;
		}

		public static String getCnName(String code) {
			for (AUDIT_STATUS item : AUDIT_STATUS.values()) {
				if (item.name().equals(code)) {
					return item.getDesc();
				}
			}
			return code;
		}
	}

	public static enum FREEZE_STATUS {
		FREEZE("已冻结"), WAIT_RELEASE("解冻待确认"), RELEASE("已解冻");

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		private String desc;

		private FREEZE_STATUS(String desc) {
			this.desc = desc;
		}

		public static String getCnName(String code) {
			for (FREEZE_STATUS item : FREEZE_STATUS.values()) {
				if (item.name().equals(code)) {
					return item.getDesc();
				}
			}
			return code;
		}
	}

	public static enum DRAW_AUDIT_STATUS {
		PENDING_AUDIT("申请中"), PASS_AUDIT("已处理"), REJECTED_AUDIT("已作废");

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		private String desc;

		private DRAW_AUDIT_STATUS(String desc) {
			this.desc = desc;
		}

		public static String getCnName(String code) {
			for (DRAW_AUDIT_STATUS item : DRAW_AUDIT_STATUS.values()) {
				if (item.name().equals(code)) {
					return item.getDesc();
				}
			}
			return code;
		}
	}

	public static enum CASH_RECHARGE_STATUS {
		PENDING_AUDIT("申请中"), PASS_AUDIT("已处理"), DOUBLE_AUDIT("重新申请"), UNPASS_AUDIT(
				"打回申请");//, REJECTED_AUDIT("已作废");

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		private String desc;

		private CASH_RECHARGE_STATUS(String desc) {
			this.desc = desc;
		}

		public static String getCnName(String code) {
			for (CASH_RECHARGE_STATUS item : CASH_RECHARGE_STATUS.values()) {
				if (item.name().equals(code)) {
					return item.getDesc();
				}
			}
			return code;
		}
	}

	public static enum USER_FINAL_STATUS {

		WAITING("WAITING", "待审核"), DOING("DOING", "合作中"), REJECT("REJECT",
				"拒绝合作"), END("END", "终止合作");

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

		private USER_FINAL_STATUS(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static boolean isWaiting(String status) {
			return USER_FINAL_STATUS.WAITING.getValue().equals(status);
		}

		public static boolean isDoing(String status) {
			return USER_FINAL_STATUS.DOING.getValue().equals(status);
		}

		public static boolean isReject(String status) {
			return USER_FINAL_STATUS.REJECT.getValue().equals(status);
		}

		public static boolean isEnd(String status) {
			return USER_FINAL_STATUS.END.getValue().equals(status);
		}

		public static boolean isNotDoing(String status) {
			return isReject(status) || isEnd(status);
		}
	}

	public static enum USER_MATERIAL_TYPE {

		COMPANY_BUSINESS_LICENSE("法人营业执照"), COMPANY_CARD_FRONT("法人身份证正面"), COMPANY_CARD_CONTRARY(
				"法人身份证背面"), COMPANY_TAX("税务登记证"), COMPANY_PRINCIPAL_CARD_FRONT(
				"负表人身份证正面"), COMPANY_PRINCIPAL_CARD_CONTRARY("负表人身份证背面"), COMPANY_BANK(
				"银行开户许可证"), PERSON_CARD_FRONT("个人身份证正面"), PERSON_CARD_CONTRARY(
				"个人身份证背面"), CERT_ORG_CODE(
						"组织机构代码证");

		private String zhName;

		private USER_MATERIAL_TYPE(String zhName) {
			this.zhName = zhName;
		}

		public String getZhName() {
			return this.zhName;
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (USER_MATERIAL_TYPE t : values()) {
				map.put(t.toString(), t.getZhName());
			}
			return map;
		}
	}

	public static enum ACCOUNT_TYPE {

		RECOGNIZANCE("保证金"), CASHACCOUNT("预存款");
		private String zhName;

		public String getZhName() {
			return zhName;
		}

		private ACCOUNT_TYPE(String name) {
			this.zhName = name;
		}

		public static String getCnName(String code) {
			for (PRODUCT_TYPE item : PRODUCT_TYPE.values()) {
				if (item.name().equals(code)) {
					return item.getZhName();
				}
			}
			return code;
		}
	}

	public static enum PRODUCT_TYPE {

		TICKET("门票"), HOTEL("酒店");
		private String zhName;

		public String getZhName() {
			return zhName;
		}

		private PRODUCT_TYPE(String name) {
			this.zhName = name;
		}

		public static String getCnName(String code) {
			for (PRODUCT_TYPE item : PRODUCT_TYPE.values()) {
				if (item.name().equals(code)) {
					return item.getZhName();
				}
			}
			return code;
		}
	}

	public static enum GENDER {

		MAN("Y", "先生"), WOMEN("M", "女士");

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

		private GENDER(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (GENDER t : values()) {
				map.put(t.getValue(), t.getDesc());
			}
			return map;
		}

	}

	public static enum PAY_TYPE {

		MONTH("MONTH", "月结"), WEEK("WEEK", "周结"), SINGLE("SINGLE", "单结");

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

		private PAY_TYPE(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static boolean isMonthPay(String payType) {
			return MONTH.getValue().equals(payType);
		}

		public static boolean isSinglePay(String payType) {
			return SINGLE.getValue().equals(payType);
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (PAY_TYPE t : values()) {
				map.put(t.getValue(), t.getDesc());
			}
			return map;
		}

	}

	public static enum PRODUCT_PAY_TYPE {

		TOLVMAMA("TOLVMAMA", "在线支付"), TOSUPPLIER("TOSUPPLIER", "景区支付");

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

		private PRODUCT_PAY_TYPE(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static boolean isPayToLvmama(String payType) {
			return TOLVMAMA.getValue().equals(payType);
		}

		public static boolean isPayToSupplier(String payType) {
			return TOSUPPLIER.getValue().equals(payType);
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (PRODUCT_PAY_TYPE t : values()) {
				map.put(t.getValue(), t.getDesc());
			}
			return map;
		}

	}

	public static enum TNT_FILE_TYPE {

		TNT_FILE_CONTRACT("分销商合同");

		private String cnName;

		private TNT_FILE_TYPE(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (TNT_FILE_TYPE item : TNT_FILE_TYPE.values()) {
				if (item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}

	/**
	 * 分销商结算状态
	 * 
	 */
	public static enum TNT_SETTLE_STATUS {
		UNSETTLEMENTED("未结算"),
		/** 已确认 （不使用） */
		CONFIRMED("已确认"),
		/** 争议单（不使用） */
		DISPUTED("争议单"),
		/** 已结算 */
		SETTLEMENTED("已结算"),
		/** 结算中 */
		SETTLEMENTING("结算中"),

		NOSETTLEMENT("不结算");

		private String cnName;

		private TNT_SETTLE_STATUS(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (TNT_SETTLE_STATUS item : TNT_SETTLE_STATUS.values()) {
				if (item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}

	public static enum EMAIL_SSO_TEMPLATE {
		/**
		 * 重设EMAIL
		 */
		EMAIL_RESET_PASSWORD,
		/**
		 * EMAIL验证激活邮件
		 */
		EMAIL_AUTHENTICATE,

		/** 信息，资料审核不通过通知邮件 **/
		INFORMATION_UNQUALIFIED,

		/** 终止合作通知邮件 **/
		REFUSED_COOPERATE
	};

	/**
	 * 减价规则类型
	 * 
	 * @author gaoxin
	 * 
	 */
	public static enum PROD_POLICY_TYPE {
		CUT_PROFIT("按毛利润减价", "销售价-（销售价-结算价）*%s%%", "销售价-(销售价-结算价)*%s"), CUT_SALE_PRICE(
				"按售价减价", "销售价*（1-%s%%）", "销售价*(1-%s)");
		private String cnName;
		private String rule;
		private String calRule;

		PROD_POLICY_TYPE(String name, String rule, String calRule) {
			this.cnName = name;
			this.rule = rule;
			this.calRule = calRule;
		}

		public String getRule() {
			return rule;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public String getCalRule() {
			return calRule;
		}

		public static String getCnName(String code) {
			PROD_POLICY_TYPE item = getByCode(code);
			return item != null ? item.getCnName() : null;
		}

		public static String getRule(String code) {
			PROD_POLICY_TYPE item = getByCode(code);
			return item != null ? item.getRule() : CUT_PROFIT.getRule();
		}

		public static String getCalRule(String code) {
			PROD_POLICY_TYPE item = getByCode(code);
			return item != null ? item.getCalRule() : CUT_PROFIT.getCalRule();
		}

		public static PROD_POLICY_TYPE getByCode(String code) {
			for (PROD_POLICY_TYPE item : PROD_POLICY_TYPE.values()) {
				if (item.getCode().equals(code)) {
					return item;
				}
			}
			return null;
		}

		public String toString() {
			return this.name();
		}
	};

	/**
	 * 策略类型
	 * 
	 * @author gaoxin
	 * 
	 */
	public static enum PROD_TARGET_TYPE {
		CHANNEL("单个渠道通用规则"), DISTRIBUTOR("单个分销商通用规则"), PROD_CHANNEL(
				"单个产品在所有渠道规则"), PROD_DISTRIBUTOR("单个产品在单个分销商的规则");
		private String cnName;

		PROD_TARGET_TYPE(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static boolean isChannel(String value) {
			return CHANNEL.name().equals(value);
		}

		public static boolean isProdChannel(String value) {
			return PROD_CHANNEL.name().equals(value);
		}

		public static boolean isProdDistributor(String value) {
			return PROD_DISTRIBUTOR.name().equals(value);
		}

		public static String getCnName(String code) {
			for (PROD_TARGET_TYPE item : PROD_TARGET_TYPE.values()) {
				if (item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	};

	public static enum CHANNEL_CODE {

		DISTRIBUTOR_B2B("B2B分销平台");

		private String cnName;

		private CHANNEL_CODE(String name) {
			this.cnName = name;
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String name) {
			for (CHANNEL_CODE item : CHANNEL_CODE.values()) {
				if (item.name().equals(name)) {
					return item.getCnName();
				}
			}
			return name;
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (CHANNEL_CODE t : values()) {
				map.put(t.name(), t.getCnName());
			}
			return map;
		}
	}

	public static enum COM_LOG_OBJECT_TYPE {
		/** 策略修改 */
		TNT_POLICY("分销策略"),
		/** 策略修改 */
		TNT_ACCOUNT("分销预存款"),
		/** 分销保证金操作 */
		TNT_RECOGNIZANCE_CHANGE("分销保证金操作");

		private String cnName;

		COM_LOG_OBJECT_TYPE(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (COM_LOG_OBJECT_TYPE item : COM_LOG_OBJECT_TYPE.values()) {
				if (item.getCode().equals(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}

	public static enum PAYMENT_STATUS {
		/** 未支付 */
		UNPAY("未支付"),
		/** 支付完成 */
		PAYED("支付完成"),
		/** 支付失败 */
		PAYFAIL("支付失败"),
		/** 部分支付 */
		PARTPAY("部分支付"),
		/** 已转移 **/
		TRANSFERRED("已转移");

		private String cnName;

		PAYMENT_STATUS(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (PAYMENT_STATUS item : PAYMENT_STATUS.values()) {
				if (item.getCode().equalsIgnoreCase(code)) {
					return item.getCnName();
				}
			}
			return "";
		}

		public static boolean isUnPayed(String status) {
			return UNPAY.name().equalsIgnoreCase(status);
		}

		public static boolean isPayed(String status) {
			return PAYED.name().equalsIgnoreCase(status);
		}

		public String toString() {
			return this.name();
		}
	}

	public static enum ORDER_APPROVE_STATUS {
		/** 未审核 */
		UNVERIFIED("未审核"),
		/** 信息通过 */
		INFOPASS("信息通过"),
		/** 资源审核通过 */
		RESOURCEPASS("资源审核通过"),
		/** 资源审核不通过 */
		RESOURCEFAIL("资源审核不通过"),
		/** 已审核 */
		VERIFIED("已审核"),
		/** 待跟进 */
		BEFOLLOWUP("待跟进");

		private String cnName;

		ORDER_APPROVE_STATUS(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (ORDER_APPROVE_STATUS item : ORDER_APPROVE_STATUS.values()) {
				if (item.getCode().equalsIgnoreCase(code)) {
					return item.getCnName();
				}
			}
			return "";
		}

		public String toString() {
			return this.name();
		}

		public static boolean isUnverified(String status) {
			return UNVERIFIED.name().equals(status);
		}

		public static boolean isVerified(String status) {
			return VERIFIED.name().equals(status);
		}
	}

	// 订单状态
	public static enum ORDER_STATUS {
		/** 正常 */
		NORMAL("正常"),
		/** 取消 */
		CANCEL("取消"),
		/** 完成 （结束） */
		FINISHED("完成"),
		/** 未确认结束 */
		UNCONFIRM("未确认结束");

		private String cnName;

		ORDER_STATUS(String name) {
			this.cnName = name;
		}

		public String getCode() {
			return this.name();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static boolean isNormal(String status) {
			return NORMAL.name().equalsIgnoreCase(status);
		}

		public static String getCnName(String code) {
			for (ORDER_STATUS item : ORDER_STATUS.values()) {
				if (item.getCode().equalsIgnoreCase(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}

	// 订单状态
	public static enum REFUND_STATUS {
		/** 正常 */
		NORMAL("无退款"),
		/** 取消 */
		WAITING("待退款"),
		/** 完成 （结束） */
		FINISHED("退款完成"),
		/** 未确认结束 */
		FAILED("退款失败");

		private String cnName;

		REFUND_STATUS(String name) {
			this.cnName = name;
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (REFUND_STATUS item : REFUND_STATUS.values()) {
				if (item.name().equalsIgnoreCase(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}

	public static enum COM_LOG_TYPE {
		/** 通用策略修改 */
		changeChannelPolicy,
		/** 单个产品渠道策略修改 */
		changeProdChannelPolicy,
		/** 单个产品单分销商策略修改 */
		changeProdDistributor,
		/** 单个分销商通用策略修改 */
		changeDistributor,

		/** 预存款冻结 */
		ACCOUNT_FREEZE,
		/** 预存款提现 */
		ACCOUNT_DRAW,
		/** 预存款返佣 */
		ACCOUNT_COMMISSION,
		/** 预存款充值 */
		ACCOUNT_RECHARGE,
		/** 预存款退款*/
		ACCOUNT_REFUNDMENT,
		/** 预存款账户操作 */
		ACCOUNT_ACC,

		/** ======保证金系列======= **/
		/** 充值 **/
		CHANGE_RECHARGE,
		/** 扣款 **/
		CHANGE_DEBIT,
		/** 记录修改 **/
		CHANGE_EDIT,
		/** 记录审核 **/
		CHANGE_APPROVE,
		/** 提交确认 **/
		CHANGE_CONFIRM,
		/** 作废 **/
		CHANGE_CANCEL;
	}

	public static enum PROD_APERIODIC {

		TRUE("true", "期票"), FALSE("false", "非期票");

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

		private PROD_APERIODIC(String value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (PROD_APERIODIC t : values()) {
				map.put(t.getValue(), t.getDesc());
			}
			return map;
		}

	}

	public static enum TRUE_FALSE {
		TRUE("true", "是"), FALSE("false", "否");

		private String attr1;
		private String cnName;

		TRUE_FALSE(String value, String cnName) {
			this.cnName = cnName;
			this.attr1 = value;
		}

		public String getValue() {
			return attr1;
		}

		public String getAttr1() {
			return attr1;
		}

		public String getCode() {
			return this.name().toLowerCase();
		}

		public String getCnName() {
			return this.cnName;
		}

		public static String getCnName(String code) {
			for (TRUE_FALSE item : TRUE_FALSE.values()) {
				if (item.getCode().equalsIgnoreCase(code)) {
					return item.getCnName();
				}
			}
			return code;
		}

		public static String getAttr1(String code) {
			for (TRUE_FALSE item : TRUE_FALSE.values()) {
				if (item.getCode().equalsIgnoreCase(code)) {
					return item.getAttr1();
				}
			}
			return code;
		}

		public String toString() {
			return this.name().toLowerCase();
		}
	}

	/**
	 * object类型
	 * 
	 * @author gaoxin
	 * 
	 */
	public static enum OBJECT_TYPE {
		/** 采购 */
		METAPRODUCT,
		/** 销售 */
		PRODUCT,
	}

	public static enum PAYMENT_GATEWAY {
		DISTRIBUTOR_B2B,
	}

	public static enum CASHPAY_STATUS {
		CREATE, SUCCESS, FAIL
	}

	public static enum RECOGNIZANCE_CHANGE_STATUS {

		WAITING("待审核"), AGREE("已确认"), REJECT("已打回"), CANCEL("已废除"), EDITED(
				"已修改");

		private String desc;

		private RECOGNIZANCE_CHANGE_STATUS(String desc) {
			this.desc = desc;
		}

		public static boolean isAgree(String status) {
			return AGREE.name().equals(status);
		}

		public static boolean isWaiting(String status) {
			return WAITING.name().equals(status);
		}

		public static boolean isReject(String status) {
			return REJECT.name().equals(status);
		}

		public static boolean isEdited(String status) {
			return EDITED.name().equals(status);
		}

		public static boolean canEdit(String status) {
			return isReject(status) || isEdited(status);
		}

		public static boolean canApprove(String status) {
			return isWaiting(status);
		}

		public static boolean canConfirm(String status) {
			return isEdited(status);
		}

		public static String getDesc(String status) {
			for (RECOGNIZANCE_CHANGE_STATUS item : RECOGNIZANCE_CHANGE_STATUS
					.values()) {
				if (item.name().equalsIgnoreCase(status)) {
					return item.getDesc();
				}
			}
			return status;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public static Map<String, String> toMap() {
			Map<String, String> map = new HashMap<String, String>();
			for (RECOGNIZANCE_CHANGE_STATUS t : values()) {
				map.put(t.name(), t.getDesc());
			}
			return map;
		}

	}

}
