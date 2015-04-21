package com.lvmama.clutter.service.impl;

import java.util.ArrayList;

import com.lvmama.clutter.xml.lv.po.ClientXmlAliasSet;
import com.lvmama.clutter.xml.lv.po.RequestObject;
import com.thoughtworks.xstream.XStream;

public class BaseClientService {
	
	public RequestObject getRequestObjectFromXml(String reqXml){
		XStream reqXtream = ClientXmlAliasSet.getAllRequestObj();
		RequestObject requestObj = (RequestObject) reqXtream.fromXML(reqXml);
		requestObj.getBody().paramters = new ArrayList<String>();
		return requestObj;
	}
}
