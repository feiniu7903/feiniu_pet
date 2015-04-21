package com.lvmama.pet.vo;

/**
 * 招行分期退款——应答数据
 * 
 * @author fengyu
 */
public class CMBInstalmentRefundResponse {
	public static final String ROOT_ELEMENT = "Response";
	public static final String HEAD_ELEMENT = "Head";
	public static final String BODY_ELEMENT = "Body";

	/**
	 * $Head$
	 */
	private Head Head;

	public Head getHead() {
		return Head;
	}

	public void setHead(Head head) {
		Head = head;
	}

	public static class Head {
		/**
		 * 错误码
		 */
		private String Code;

		/**
		 * 错误信息
		 */
		private String ErrMsg;

		public Head(String code, String errMsg) {
			super();
			Code = code;
			ErrMsg = errMsg;
		}

		public String getCode() {
			return Code;
		}

		public void setCode(String code) {
			Code = code;
		}

		public String getErrMsg() {
			return ErrMsg;
		}

		public void setErrMsg(String errMsg) {
			ErrMsg = errMsg;
		}

		
	}

}
