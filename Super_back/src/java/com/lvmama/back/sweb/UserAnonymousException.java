/**
 * 
 */
package com.lvmama.back.sweb;

/**
 * 用户未未登录的异常.
 * @author yangbin
 *
 */
public class UserAnonymousException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -471468719498806837L;

	public UserAnonymousException() {
		super("没有登录或离开太久,请重新登录");
		// TODO Auto-generated constructor stub
	}

	public UserAnonymousException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
