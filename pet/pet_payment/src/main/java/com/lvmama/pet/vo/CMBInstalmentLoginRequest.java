package com.lvmama.pet.vo;

/**
 * 招行分期登录——请求数据
 * @author fengyu
 */
public class CMBInstalmentLoginRequest {
	public static final String ROOT_ELEMENT = "Request";
	public static final String HEAD_ELEMENT = "Head";
	public static final String BODY_ELEMENT = "Body";
	public static final String COMMAND = "Login";

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
		 * 命令名，固定为“Login”
		 */
		private String Command;

		public Head(String command) {
			super();
			Command = command;
		}
	}

	public static class Body {
		/**
		 * 商户号
		 **/
		private String Cono;
		/**
		 * 操作员号
		 **/
		private String OperatorID;
		/**
		 * 登录密码
		 **/
		private String Password;

		public String getCono() {
			return Cono;
		}
		public void setCono(String cono) {
			Cono = cono;
		}
		public String getOperatorID() {
			return OperatorID;
		}
		public void setOperatorID(String operatorID) {
			OperatorID = operatorID;
		}
		public String getPassword() {
			return Password;
		}
		public void setPassword(String password) {
			Password = password;
		}
	}

}
