/**
 * 
 */
package com.lvmama.pet.sweb.sup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.sweb.EditAction;

/**
 * 管理联系人
 * @author yangbin
 *
 */
@Results({
	@Result(name="error_param",location="/WEB-INF/pages/back/sup/error_param.jsp"),
	@Result(name="index",location="/WEB-INF/pages/back/sup/supplier_contact.jsp"),
	@Result(name="input",location="/WEB-INF/pages/back/sup/edit_supplier_contact.jsp"),
	@Result(name="select_contact",location="/WEB-INF/pages/back/sup/select_contact.jsp")
})
public class SupContactAction extends BackBaseAction implements EditAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5236792808504726785L;
	private ContactService contactService;
	private Long supplierId;
	private String[] selectedSupplierId;
	private ComContact contact;
	private List<ComContact> contactList; 
	@Action("/sup/contact/contactList")
	public String index(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("supplierId",supplierId);
		contactList=contactService.getContactByPersonTimeCompany(map);
		return "index";
	}

	@Override	
	@Action("/sup/contact/toAddContact")
	public String toAdd() {
		if(supplierId==null||supplierId<1){
			return "error_param";
		}
		contact = new ComContact();
		contact.setSupplierId(supplierId);
		return INPUT;
	}

	@Override
	@Action("/sup/contact/toEditContact")
	public String toEdit() {
		if(contact.getContactId()==null||contact.getContactId()<1||supplierId==null||supplierId<1){
			return "error_param";
		}
		contact = contactService.getContactById(contact.getContactId());
		if(contact==null){
			return "error_param";
		}
		contact.setSupplierId(supplierId);
		return INPUT;
	}

	@Override
	@Action("/sup/contact/saveContact")
	public void save() {
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(contact.getSupplierId(),"供应商信息不存在");
			if(contact.getContactId()==null){
				contactService.addContact(contact,getSessionUserNameAndCheck());
			}else{
				ComContact entity = contactService.getContactById(contact.getContactId());
				entity=CopyUtil.copy(entity, contact, getRequest().getParameterNames(),"contact.");
				ResultHandle handle=contactService.updateContact(entity,getSessionUserNameAndCheck());
				if(handle.isFail()){
					throw new IllegalArgumentException(handle.getMsg());
				}
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	
	/**
	 * 显示供应商的联系人列表供选择
	 * @return
	 */
	@Action("/sup/contact/selectContact")
	public String selectContact(){
		if(supplierId==null||supplierId<1){
			return "error_param";
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("supplierId",supplierId);
		map.put("_endRow", 1000);
		contactList=contactService.getContactByPersonTimeCompany(map);
		return "select_contact";
	}
	
	public boolean selectContact(Long contactId){
		if (ArrayUtils.isEmpty(selectedSupplierId)) {
			return false;
		}
		return ArrayUtils.contains(selectedSupplierId, String.valueOf(contactId));
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}
	
	public Constant.SEX_CODE[] getSexList(){
		return Constant.SEX_CODE.values();
	}

	public ComContact getContact() {
		return contact;
	}

	public void setContact(ComContact contact) {
		this.contact = contact;
	}

	public List<ComContact> getContactList() {
		return contactList;
	}

	public void setSelectedSupplierId(String selectedSupplierId) {
		this.selectedSupplierId = StringUtils.split(selectedSupplierId, ",");
	}
}
