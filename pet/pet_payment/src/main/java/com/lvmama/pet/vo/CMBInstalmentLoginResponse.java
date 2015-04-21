package com.lvmama.pet.vo;

/**
 * 招行分期登录——应答数据
 * @author fengyu
 */
public class CMBInstalmentLoginResponse {
	public static final String ROOT_ELEMENT = "Response";
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
		 * 错误码
		 */
		private String Code;

		/**
		 * 错误信息
		 */
		private String ErrMsg;

		public Head(String Code, String ErrMsg) {
			super();
			this.Code = Code;
			this.ErrMsg = ErrMsg;
		}
	}

	public static class Body {
		/**
		 * 连接号
		 **/
		private String Connection;

		public String getConnection() {
			return Connection;
		}

		public void setConnection(String connection) {
			Connection = connection;
		}
	}
}
