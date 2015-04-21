package com.lvmama.distribution.model.lv;

import java.util.List;

/**
 * 其他游玩人信息列表
 *
 */
public class OtherCustomer {
	/** 单个游玩人信息*/
	private List<Person> personList;
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned(){
		StringBuilder localSigned = new StringBuilder();
		for(Person person : personList){
			localSigned.append(person.getLocalSigned());
		}
		return localSigned.toString();
	}

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	
}
