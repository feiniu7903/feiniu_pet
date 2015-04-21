package com.ejingtong.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6596283040159587363L;
	private OrderHead baseInfo;
	private List<OrderMeta> metas;
	private List<OrderPerson> persons;
	
	
	public OrderHead getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(OrderHead baseInfo) {
		this.baseInfo = baseInfo;
	}
	public List<OrderMeta> getMetas() {
		return metas;
	}
	public void setMetas(List<OrderMeta> metas) {
		this.metas = metas;
	}
	public List<OrderPerson> getPersons() {
		return persons;
	}
	public void setPersons(List<OrderPerson> persons) {
		this.persons = persons;
	}

	public String getContactName(){
		OrderPerson contactName = getContactPerson();;
		if(null != contactName){
			return contactName.getName();
		}else{
			return "";
		}
	}
	
	public String getMobileNumber(){
		OrderPerson contactName = getContactPerson();;
		if(null != contactName){
			return contactName.getMobile();
		}else{
			return "";
		}
	}
	
	
	public OrderPerson getContactPerson(){
		OrderPerson orderPerson = null;
		if(null != persons){
			for(OrderPerson person : persons){
				if("CONTACT".equals(person.getPersonType())){
					return person;
				}
			}
		}
		return orderPerson;
	}

}
