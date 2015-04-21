package com.lvmama.pet.vo;


/**
 * 招行分期支付——请求数据
 * @author fengyu
 */
public class CDPRequest_Pay {
	public static final String ROOT_ELEMENT = "CDPRequest_Pay";
	public static final String HEAD_ELEMENT = "Head";
	public static final String BODY_ELEMENT = "Body";

	/**
	 * $Head$
	 */
	private Head Head;

	/**
	 * $Body$
	 */
	private Body Body;

	/**
	 * 银行签名（$Head$ + $Body$，含Tag）
	 */
	private String Signature;

	public String getSignature() {
		return Signature;
	}

	public Head getHead() {
		return Head;
	}

	public void setHead(Head head) {
		Head = head;
	}

	public Body getBody() {
		return Body;
	}

	public void setBody(Body body) {
		Body = body;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public static class Head {
		/**
		 * 版本号（固定为1.0.0.0）
		 */
		private String Version;

		/**
		 * 测试包标志（Y：测试包；N：非测试包）
		 */
		private String TestFlag;

		public Head(String version, String testFlag) {
			super();
			Version = version;
			TestFlag = testFlag;
		}
	}

	public static class Body {
		/**
		 * 商户号（6位数字，开通业务时分配）
		 **/
		private String MchMchNbr;
		/**
		 * 商户定单号（6位或10位数字，一天内不重复）
		 **/
		private String MchBllNbr;
		/**
		 * 商户定单日期（格式YYYYMMDD）
		 **/
		private String MchBllDat;
		/**
		 * 商户定单时间（格式HHMMSS）
		 **/
		private String MchBllTim;
		/**
		 * 币种（固定为156）
		 **/
		private String TrxTrxCcy;
		/**
		 * 定单总金额
		 **/
		private Float TrxBllAmt;
		/**
		 * 分期数
		 **/
		private Integer TrxPedCnt;
		/**
		 * 付款页面隐藏分期相关文字
		 **/
		private String UIHidePed;
		/**
		 * 商户用户识别号
		 **/
		private String MchUsrIdn;
		/**
		 * 商户提供的通知地址
		 **/
		private String MchNtfUrl;
		/**
		 * 商户提供的通知参数
		 **/
		private String MchNtfPam;
		/**
		 * 商品组号
		 **/
		private String TrxGdsGrp;
		/**
		 * 商品名称
		 **/
		private String TrxGdsNam;
		/**
		 * 送货地址
		 **/
		private String TrxPstAdr;
		/**
		 * 联系电话
		 **/
		private String TrxPstTel;

		public String getMchMchNbr() {
			return MchMchNbr;
		}

		public void setMchMchNbr(String mchMchNbr) {
			MchMchNbr = mchMchNbr;
		}

		public String getMchBllNbr() {
			return MchBllNbr;
		}

		public void setMchBllNbr(String mchBllNbr) {
			MchBllNbr = mchBllNbr;
		}

		public String getMchBllDat() {
			return MchBllDat;
		}

		public void setMchBllDat(String mchBllDat) {
			MchBllDat = mchBllDat;
		}

		public String getMchBllTim() {
			return MchBllTim;
		}

		public void setMchBllTim(String mchBllTim) {
			MchBllTim = mchBllTim;
		}

		public String getTrxTrxCcy() {
			return TrxTrxCcy;
		}

		public void setTrxTrxCcy(String trxTrxCcy) {
			TrxTrxCcy = trxTrxCcy;
		}

		public Float getTrxBllAmt() {
			return TrxBllAmt;
		}

		public void setTrxBllAmt(Float trxBllAmt) {
			TrxBllAmt = trxBllAmt;
		}

		public Integer getTrxPedCnt() {
			return TrxPedCnt;
		}

		public void setTrxPedCnt(Integer trxPedCnt) {
			TrxPedCnt = trxPedCnt;
		}

		public String getUIHidePed() {
			return UIHidePed;
		}

		public void setUIHidePed(String uIHidePed) {
			UIHidePed = uIHidePed;
		}

		public String getMchUsrIdn() {
			return MchUsrIdn;
		}

		public void setMchUsrIdn(String mchUsrIdn) {
			MchUsrIdn = mchUsrIdn;
		}

		public String getMchNtfUrl() {
			return MchNtfUrl;
		}

		public void setMchNtfUrl(String mchNtfUrl) {
			MchNtfUrl = mchNtfUrl;
		}

		public String getMchNtfPam() {
			return MchNtfPam;
		}

		public void setMchNtfPam(String mchNtfPam) {
			MchNtfPam = mchNtfPam;
		}

		public String getTrxGdsGrp() {
			return TrxGdsGrp;
		}

		public void setTrxGdsGrp(String trxGdsGrp) {
			TrxGdsGrp = trxGdsGrp;
		}

		public String getTrxGdsNam() {
			return TrxGdsNam;
		}

		public void setTrxGdsNam(String trxGdsNam) {
			TrxGdsNam = trxGdsNam;
		}

		public String getTrxPstAdr() {
			return TrxPstAdr;
		}

		public void setTrxPstAdr(String trxPstAdr) {
			TrxPstAdr = trxPstAdr;
		}

		public String getTrxPstTel() {
			return TrxPstTel;
		}

		public void setTrxPstTel(String trxPstTel) {
			TrxPstTel = trxPstTel;
		}
	}

}
