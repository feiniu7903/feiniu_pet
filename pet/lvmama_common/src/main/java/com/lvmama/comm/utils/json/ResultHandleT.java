/**
 * 
 */
package com.lvmama.comm.utils.json;

import java.io.Serializable;

/**
 * @author yangbin
 *
 */
public class ResultHandleT<T extends Serializable> extends ResultHandle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5743682697348051374L;

	private T returnContent;

	public T getReturnContent() {
		return returnContent;
	}

	public void setReturnContent(T returnContent) {
		this.returnContent = returnContent;
	}
	
	
}
