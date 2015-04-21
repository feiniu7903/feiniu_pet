/**
 * 
 */
package com.lvmama.shholiday.response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.lvmama.comm.utils.XmlUtils;
import com.lvmama.shholiday.Response;
import com.lvmama.shholiday.Header;

/**
 * @author yangbin
 *
 */
public abstract class AbstractResponse implements Response{
	protected static final Log log =LogFactory.getLog(AbstractResponse.class);
	protected Document doc;
	private Header header;
	private Element root;
	
	private String bodyElementTag;
	private boolean success;
	
	
	@Override
	public void parse(String responseXml) {
		try {
			doc = XmlUtils.createDocument(responseXml);
			root=doc.getRootElement();
			
			parseHeader();
			
			if(header.isSuccess()){
				Element body=root.element(bodyElementTag);
				parseBody(body);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AbstractResponse(String bodyElementTag) {
		super();
		this.bodyElementTag = bodyElementTag;
	}

	protected abstract void parseBody(Element body);
	
	private void parseHeader(){
		Element successTag = root.element("Success");
		header = XmlUtils.toBean(Header.class, successTag);
		if(!header.isSuccess()){
			String exception = XmlUtils.getChildElementContent(successTag, "Exception");
			header.setException(exception);
		}
		success=header.isSuccess();
	}

	/**
	 * 响应状态
	 */
	@Override
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public final Header getHeader(){
		if(header==null){
			new Header();
		}
		return header;
	}
}
