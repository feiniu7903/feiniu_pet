package com.lvmama.comm.pet.service.pub;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComContactRelation;
import com.lvmama.comm.utils.json.ResultHandle;


public interface ContactService {
	/**
	 * 添加联系人
	 * @param contact
	 */
	Long addContact(ComContact contact,String operatorName);
	
	/**
	 * 更新联系人
	 * @param contact
	 */
	ResultHandle updateContact(ComContact contact,String operatorName);
	
	/**
	 * 查询联系人
	 * @param map
	 * @return
	 */
	List<ComContact> getContactByPersonTimeCompany(Map map);
	
	/**
	 * 
	 * @param contactId
	 * @return
	 */
	ComContact getContactById(Long contactId);
	
	/**
	 * 按对象ID与对象类型保存关系
	 * @param list
	 * @param objectId
	 * @param objectType
	 */
	void saveContactRelation(List<ComContact> list, Long objectId, String objectType);

	 /**
	  * 根据对象id，和对象类型查找联系
	  * @param comContactRelation
	  * @author yuzhibing
	  * @return
	  */
	 List<ComContact> getContactByContractRelation(ComContactRelation comContactRelation);
	 
	 /**
	  * 
	  * @param objectId
	  * @param objectType
	  */
	 void deleteContactRelation(Long objectId, String objectType);
	 
	 /**
	  * 删除联系人
	  * @param contactId 修改是否有效状态	
	  * */
	 void deleteContact(Map params);	 
	 
	 ComContact getSupSettlementTragetContactByTargetId(Long targetId);
}
