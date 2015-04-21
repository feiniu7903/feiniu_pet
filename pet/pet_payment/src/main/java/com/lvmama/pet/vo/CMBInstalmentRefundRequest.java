package com.lvmama.pet.vo;

/**
 * 招行分期登录——请求数据
 * 
 * @author fengyu
 */
public class CMBInstalmentRefundRequest {
	public static final String ROOT_ELEMENT = "Request";
	public static final String HEAD_ELEMENT = "Head";
	public static final String BODY_ELEMENT = "Body";
	public static final String COMMAND = "Refund";

	/**
	 * $Head$
	 */
	private Head Head;

	/**
	 * $Body$
	 */
	private Body Body;

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

	public static class Head {
		/**
		 * 连接号
		 */
		private String Connection;

		/**
		 * 命令名，固定为“Refund”
		 */
		private String Command;

		/**
		 * 对“商户密钥+Body内容”进行的SHA1结果。 以十六进制表示，大小写不限。（不包括<$Body$>标记本身）
		 */
		private String Hash;

		public Head(String connection, String command, String hash) {
			super();
			Connection = connection;
			Command = command;
			Hash = hash;
		}

		public String getHash() {
			return Hash;
		}

		public void setHash(String hash) {
			Hash = hash;
		}
	}

	public static class Body {
		/**
		 * 信用卡中心内部定单号
		 **/
		private String CrdBllNbr;
		/**
		 * 信用卡中心参考号
		 **/
		private String CrdBllRef;
		/**
		 * 商品号启用标志（Y：启用；N：不启用）
		 **/
		private String TrxGdsFlg;
		/**
		 * 退款金额（商品号启用标志为“N”时有效）
		 **/
		private String TrxRfdAmt;

		/**
		 * 以下商品号启用标志为“Y”时有效
		 */
		/**
		 * 商品号
		 **/
		private String TrxGdsNbr;
		/**
		 * 商品退款数量
		 **/
		private Float TrxGdsRfdCnt;

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

		public String getTrxGdsFlg() {
			return TrxGdsFlg;
		}

		public void setTrxGdsFlg(String trxGdsFlg) {
			TrxGdsFlg = trxGdsFlg;
		}

		public String getTrxRfdAmt() {
			return TrxRfdAmt;
		}

		public void setTrxRfdAmt(String trxRfdAmt) {
			TrxRfdAmt = trxRfdAmt;
		}

		public String getTrxGdsNbr() {
			return TrxGdsNbr;
		}

		public void setTrxGdsNbr(String trxGdsNbr) {
			TrxGdsNbr = trxGdsNbr;
		}

		public Float getTrxGdsRfdCnt() {
			return TrxGdsRfdCnt;
		}

		public void setTrxGdsRfdCnt(Float trxGdsRfdCnt) {
			TrxGdsRfdCnt = trxGdsRfdCnt;
		}

	}

}
