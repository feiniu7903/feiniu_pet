package com.lvmama.clutter.xml.po;

import java.util.List;

import com.lvmama.comm.pet.po.client.ClientComment;

public class ResponseObject {
	private HeadObject headObject;
	private BodyObject bodyObject;
	public HeadObject getHeadObject() {
		return headObject;
	}
	public void setHeadObject(HeadObject headObject) {
		this.headObject = headObject;
	}
	public BodyObject getBodyObject() {
		return bodyObject;
	}
	public void setBodyObject(BodyObject bodyObject) {
		this.bodyObject = bodyObject;
	}
	
	public String commentToXml(HeadObject headObject,List<ClientComment> comments){
		StringBuffer xml = new StringBuffer();
		xml.append("<response>");
		xml.append(headXml(headObject));
		xml.append("<body>\n<comments>\n");
		for (int i = 0; i < comments.size(); i++) {
			ClientComment cc = comments.get(i);
			xml.append("<comment>\n");
			xml.append("<name>"+cc.getPublisher()+"</name>\n");
			xml.append("<date>"+cc.getPublishTime()+"</date>\n");
			xml.append("<content>"+cc.getContent()+"</content>\n");
			xml.append("</comment>\n");
		}
		xml.append("</comments>\n</body>\n");
		xml.append("</response>");
		return xml.toString();
	}
	
	public String headXml(HeadObject headObject){
		String head = "<head>";
		 head += "<version>"+headObject.getVersion()+"</version>";
		 head += "</head>";
		 return head;
	}
}
