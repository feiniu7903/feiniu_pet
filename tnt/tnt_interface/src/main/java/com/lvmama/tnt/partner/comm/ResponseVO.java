/**
 * 
 */
package com.lvmama.tnt.partner.comm;

import java.io.Serializable;

/**
 * 结果信息包装对象
 * 
 * @author gaoyafeng
 * 
 * @param <T>
 */

public class ResponseVO<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = -6142044915216014408L;

	private ResponseHeader header;
	private T body;

	public ResponseHeader getHeader() {
		return header;
	}

	public void setHeader(ResponseHeader header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

}
