package com.lvmama.distribution.model.ckdevice.vo;

import org.dom4j.DocumentException;

public interface CKBody {

	public void init(String requestXml) throws DocumentException;

	public String checkParams();
}
