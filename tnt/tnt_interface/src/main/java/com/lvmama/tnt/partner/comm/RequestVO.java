package com.lvmama.tnt.partner.comm;

import java.io.Serializable;

public class RequestVO<T> implements Serializable {
	private static final long serialVersionUID = 3229610999558381375L;

	private RequestHeader header;
	private T body;

	public RequestHeader getHeader() {
		return header;
	}

	public void setHeader(RequestHeader header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
}
