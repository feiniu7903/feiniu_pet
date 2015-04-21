/**
 * 
 */
package com.lvmama.comm.utils;

import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.vo.Constant;

/**
 * person相关工具类.
 * @author yangbin
 *
 */
public abstract class PersonUtil {

	/**
	 * 转换person到ordPerson
	 * @param person
	 * @param type
	 * @param objectId
	 * @return
	 */
	public static OrdPerson converPerson(Person person,Constant.OBJECT_TYPE type,Long objectId){
		OrdPerson ordPerson = new OrdPerson();
		converPerson(person, ordPerson);		
		ordPerson.setObjectId(objectId);
		ordPerson.setObjectType(type.name());
		return ordPerson;
	}
	
	public static OrdPerson converPerson(Person person,OrdPerson ordPerson){
		ordPerson.setProvince(person.getProvince());
		ordPerson.setCity(person.getCity());
		ordPerson.setAddress(person.getAddress());
		ordPerson.setCertNo(person.getCertNo());
		ordPerson.setCertType(person.getCertType());
		ordPerson.setEmail(person.getEmail());
		ordPerson.setFax(person.getFax());
		ordPerson.setFaxTo(person.getFaxTo());
		ordPerson.setMemo(person.getMemo());
		ordPerson.setMobile(person.getMobile());
		ordPerson.setName(person.getName());
		
		ordPerson.setPersonType(person.getPersonType());
		ordPerson.setPostcode(person.getPostcode());
		ordPerson.setQq(person.getQq());
		ordPerson.setTel(person.getTel());
		ordPerson.setReceiverId(person.getReceiverId());
		
		ordPerson.setBrithday(person.getBrithday());
		ordPerson.setNeedInsure(person.getNeedInsure());
		ordPerson.setGender(person.getGender());
		ordPerson.setPinyin(person.getPinyin());
		return ordPerson;
	}
	
	/**
	 * 转换动态的信息
	 * @param usrReceiver
	 * @return
	 */
	public static Person converReceiver(UsrReceivers usrReceiver,Constant.ORD_PERSON_TYPE type){
		Person person = new Person();
		person.setProvince(usrReceiver.getProvince());
		person.setCity(usrReceiver.getCity());
		person.setAddress(usrReceiver.getAddress());
		person.setPostcode(usrReceiver.getPostCode());
		person.setMobile(usrReceiver.getMobileNumber());
		person.setName(usrReceiver.getReceiverName());
		person.setPersonType(type.name());
		person.setReceiverId(usrReceiver.getReceiverId());
		return person;
	}
}
