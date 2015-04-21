package com.lvmama.pet.vo;

/**
 * 招行分期支付——请求通知数据
 * @author fengyu
 */
public class CDPNotice_Pay {
	public static final String ROOT_ELEMENT = "CDPNotice_Pay";
	public static final String HEAD_ELEMENT = "Head";
	public static final String BODY_ELEMENT = "Body";

	public CDPNotice_Pay() {
		
	}
	
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

		public Head() {
			
		}
		
		public Head(String version, String testFlag) {
			super();
			Version = version;
		}
	}

	public static class Body {
		/**
		 * 交易成功标志（Y：成功；N：失败；S：待人工处理）
		 **/
		private String ResultFlag;
		/**
		 * 商户号（原样返回支付请求中的对应数据）
		 **/
		private String MchMchNbr;
		/**
		 * 商户定单号（原样返回支付请求中的对应数据）
		 **/
		private String MchBllNbr;
		/**
		 * 商户定单日期（原样返回支付请求中的对应数据）
		 **/
		private String MchBllDat;
		/**
		 * 商户定单时间（原样返回支付请求中的对应数据）
		 **/
		private String MchBllTim;
		/**
		 * 币种（原样返回支付请求中的对应数据）
		 **/
		private String TrxTrxCcy;
		/**
		 * 定单总金额（原样返回支付请求中的对应数据）
		 **/
		private String TrxBllAmt;
		/**
		 * 分期数（原样返回支付请求中的对应数据）
		 **/
		private Integer TrxPedCnt;
		/**
		 * 商户提供的通知参数（原样返回支付请求中的对应数据）
		 **/
		private String MchNtfPam;
		/**
		 * 信用卡中心内部定单号（15位）
		 **/
		private String CrdBllNbr;
		/**
		 * 信用卡中心内部参考号（12位）
		 **/
		private String CrdBllRef;
		/**
		 * 信用卡中心内部授权号（6位）
		 **/
		private String CrdAutCod;

		public String getResultFlag() {
			return ResultFlag;
		}

		public void setResultFlag(String resultFlag) {
			ResultFlag = resultFlag;
		}

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

		public String getTrxBllAmt() {
			return TrxBllAmt;
		}

		public void setTrxBllAmt(String trxBllAmt) {
			TrxBllAmt = trxBllAmt;
		}

		public Integer getTrxPedCnt() {
			return TrxPedCnt;
		}

		public void setTrxPedCnt(Integer trxPedCnt) {
			TrxPedCnt = trxPedCnt;
		}

		public String getMchNtfPam() {
			return MchNtfPam;
		}

		public void setMchNtfPam(String mchNtfPam) {
			MchNtfPam = mchNtfPam;
		}

		public String getCrdBllNbr() {
			return CrdBllNbr;
		}

		public void setCrdBllNbr(String crdBllNbr) {
			CrdBllNbr = crdBllNbr;
		}

		public String getCrdBllRef() {
			return CrdBllRef;
		}

		public void setCrdBllRef(String crdBllRef) {
			CrdBllRef = crdBllRef;
		}

		public String getCrdAutCod() {
			return CrdAutCod;
		}

		public void setCrdAutCod(String crdAutCod) {
			CrdAutCod = crdAutCod;
		}

		public Body() {
			
		}
	}

}
