package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdPerson;

public class TravellerInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] name;
	private String[] certType;
	private String[] certNo;
	private String[] mobile;
	
	public List<OrdPerson> getList() {
		List<OrdPerson> list = new ArrayList<OrdPerson>();
		if (null == name) {
			return null;
		}
		for (int i = 0; i < name.length; i++) {
			OrdPerson op = new OrdPerson();
			op.setName(name[i]);
			op.setCertType(certType[i]);
			op.setCertNo(certNo[i]);
			op.setMobile(mobile[i]);
			list.add(op);
		}
		return list;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getCertType() {
		return certType;
	}

	public void setCertType(String[] certType) {
		this.certType = certType;
	}

	public String[] getCertNo() {
		return certNo;
	}

	public void setCertNo(String[] certNo) {
		this.certNo = certNo;
	}

	public String[] getMobile() {
		return mobile;
	}

	public void setMobile(String[] mobile) {
		this.mobile = mobile;
	}

}
