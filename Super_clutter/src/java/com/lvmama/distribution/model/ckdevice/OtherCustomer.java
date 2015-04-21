package com.lvmama.distribution.model.ckdevice;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 其他游玩人信息列表
 * @author gaoxin
 *
 */
@XmlRootElement
public class OtherCustomer {
	/** 单个游玩人信息*/
	private List<Person> personList;
	
	
	@XmlElement(name = "person")
	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	
}
