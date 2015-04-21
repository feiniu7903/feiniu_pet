package com.lvmama.shholiday.response;

import org.dom4j.Element;

public class ModifyOrderResponse extends AbstractResponse {

	private String bookModifyNo;
	
	public ModifyOrderResponse(){
		super("BookModifyRS");
	}
	@Override
	protected void parseBody(Element body) {

		Element BookModifyInfo = body.element("BookModifyInfo");
		if(BookModifyInfo!=null){
			bookModifyNo = BookModifyInfo.attributeValue("BookModifyNo");
		}
	}
	
	public String getBookModifyNo() {
		return bookModifyNo;
	}
	public void setBookModifyNo(String bookModifyNo) {
		this.bookModifyNo = bookModifyNo;
	}

	
	
}
