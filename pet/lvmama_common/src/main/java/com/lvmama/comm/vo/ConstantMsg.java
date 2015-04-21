package com.lvmama.comm.vo;

public class ConstantMsg {

	
	public static enum CK_MSG {
		SUCCESS("1000","成功"),
		REG_ERROR("1001","创建订单时异常，用户注册出错"), 
		PHONE_ERROR("1002","创建订单时异常，手机号不合法"),
		PHONE_NULL("1003","创建订单时异常，手机号为空"),
		PROD_NUM_NULL("1004","创建订单时异常，选购产品数量为空"),
		PROD_NUM_LESS_ZERO("1005","创建订单时异常，选购产品数量<=0！"),
		PROD_NUM_OUTNUMBER("1006","创建订单时异常，订购数量超过最大可售数"),
		PROD_NUM_LESS_MIN("1007","创建订单时异常， 订购数量小于最小订购数"),
		PLAY_DATE_NULL("1008","创建订单时异常，游玩日期为空！"),
		LEAVE_DATE_NULL("1009","创建订单时异常，离店日期为空！"),
		LEAVE_DATE_LESSIN("1010","创建订单时异常，离店日期必须大于入住日期"),
		STOCK_NOTENOUGH("1011","创建订单时异常，当前库存不足，请选择其他日期"),
		LEAVE_DATE_ERROR("1012","创建订单时异常，离店日期格式错误！"),
		NO_CHOOSE_PROD("1013","创建订单时异常，未选购产品！"),
		MSG_ERROR("1014","报文解析异常，请检查报文结构！"),
		SIGN_ERROR("1015","签证验证未通过！"),
		ORDER_EXITS("1016","没有此订单，请检查订单号是否正确！"),
		FAIL_PAY("1017","订单支付失败，请重新支付！"),
		PLAY_DATE_ERROR("1018","游玩日期格式不正确！"),
		BRANCH_EXISTS("1019","没有此产品类别！"),
		ORDER_CANCEL_ERROR("1020","取消订单失败"),
		PARTINER_EXISTS("1021","商户不存在！"),
		DUPLICATE_PAYED("1022","订单已经支付,不能重复操作！"),
		AFTER_CANCEL_DATE("1023","订单已经过了最晚取消时间，不能取消！"),
		DUPLICATE_CANCEL("1024","订单已经取消，不能重复操作"),
		INVALID_PARAM("1025","无效订单状态参数，无法更新状态"),
		INVALID_IP("1026","无效IP访问"),
		PROD_EXITS("1027","无此分销产品"),
		DATE_ERROR("1028","创建订单异常，当前日期无法预订"),
		ABNORMAL("1029","重发凭证失败,原因为订单未支付或订单状态不正常"),
		PAY_CANCEL_ORDER("1030","订单已经取消，不能进行支付"),
		UNDEFINED_DATE_PRICE("1031","当前日期的价格不符，请检查价格"),
		OFFLINE_ERROR("1032","创建订单时异常，产品已下线"),
		CARD_NO_NULL("1033","创建订单时异常，游客必填信息部分为空"),
		CANCEL_NOT_FOUND("1034","不提供取消接口"),
		CODE_EXISTS("1035","辅助码不存在，请检查！"),
		UNDEFINED_PARAM("1036","无效参数，请检查！"),
		ORDER_EXISTS("1037","手机号不存在或无可支付订单，请检查!"),
		CODEUNMACHPHONE("1038","手机号与码不匹配"),
		CHECKSUM_FAULT("1039","校验码验证失败"),
		PAY_TOSUPPLIER("1040","此订单为景区现付，请你到景区票口付款取票"),
		PAY_TOSUPPLIER_BOOK_FAIL("1041","景区现付产品，不支持下单"),
		ORDER_UNPAY("1042","订单未支付不可以出票"),
		UNSUPPORT_CODE("1043","设备不支持该码打印"),
		NOT_ENOUGH_MONEY("1044","余额不足"),
		ERROE_PHONE("1045","手机号码格式不对"),
		NOPROUCT_NOW("1046","已超过今日最晚售票时间，暂不能订购"),
		UNKNOW_ERROR("2000","未知异常");
		private final String code;
		private final String cnName;
		CK_MSG(String code, String cnName) {
			this.code = code;
			this.cnName = cnName;
		}

		public String getCode() {
			return this.code;
		}

		public String getCnName() {
			return this.cnName;
		}
		
		public static String getCnName(String code){
			for(CK_MSG item:CK_MSG.values()){
				if(item.getCode().equals(code))
				{
					return item.getCnName();
				}
			}
			return code;
		}

		public String toString() {
			return this.name();
		}
	}
}
