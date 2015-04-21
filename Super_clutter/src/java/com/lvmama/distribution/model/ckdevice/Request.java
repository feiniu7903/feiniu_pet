package com.lvmama.distribution.model.ckdevice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 
 * @author gaoxin
 *
 */
@XmlRootElement
public class Request{
	
	private Body body;

	@XmlElement
	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}
