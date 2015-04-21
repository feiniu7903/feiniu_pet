/**
 * 
 */
package com.lvmama.pet.sweb.sup;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.po.pub.ComContactRelation;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.json.JSONResult;

/**
 * 
 * @author yangbin
 *
 */
@Results({
	@Result(name="error_param",location="/WEB-INF/pages/back/sup/error_param.jsp")
})
public abstract class SupTargetBaseAction extends BackBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8002631929689447013L;
	protected SupplierService supplierService;
	protected Long supplierId;
	protected Long targetId;//修改对象时传此值
	protected final String errorParam="error_param";
	protected ComContact comContact = new ComContact();
	private String contactListId;
	private List<ComContact> contactList=Collections.emptyList();
	protected ContactService contactService;

	public List<ComContact> getComContactList(){
		if(supplierId!=null){
			Map<String,Object> param=new HashMap<String, Object>();
			param.put("supplierId", supplierId);
			param.put("_endRow", 1000);
			return contactService.getContactByPersonTimeCompany(param);
		}else{
			return Collections.emptyList();
		}
	}
	
	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	protected void initEditContact(Long targetId,String objectType){
		ComContactRelation ccr=new ComContactRelation();
		ccr.setObjectId(targetId);
		ccr.setObjectType(objectType);
		contactList = contactService.getContactByContractRelation(ccr);
		if(!contactList.isEmpty()){
			StringBuffer sb=new StringBuffer();
			for(ComContact cc:contactList){
				sb.append(cc.getContactId());
				sb.append(",");
			}
			sb.setLength(sb.length()-1);
			contactListId=sb.toString();
		}
	}
	
	protected void saveHandle(Long supplierId,String objectType){
		JSONResult result=new JSONResult();
		try{
			String contactIds[]=StringUtils.split(contactListId,",");
			List<ComContact> list=new Vector<ComContact>();
			if(ArrayUtils.isNotEmpty(contactIds)){
				for(String c:contactIds){
					Long id=NumberUtils.toLong(c);
					ComContact cc=contactService.getContactById(id);
					if(cc==null){
						throw new IllegalArgumentException("绑定的联系人不存在");
					}					
					list.add(cc);
				}
			}
			Long targetId=doSaveTarget();
			if(targetId==null){
				throw new Exception("对象保存失败");
			}
			
			if(!list.isEmpty()){
				contactService.saveContactRelation(list, targetId, objectType);
			}else{
				//清除对应的绑定的联系人
				contactService.deleteContactRelation(targetId, objectType);
			}
		}catch(Exception ex){
			result.raise(ex);			
		}
		result.output(getResponse());
	}
	
	/**
	 * 保存当前的实体 
	 */
	protected abstract Long doSaveTarget();
		
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public ComContact getComContact() {
		return comContact;
	}

	public void setComContact(ComContact comContact) {
		this.comContact = comContact;
	}

	public void setContactListId(String contactListId) {
		this.contactListId = contactListId;
	}

	public List<ComContact> getContactList() {
		return contactList;
	}

	public String getContactListId() {
		return contactListId;
	}
	
	
}
