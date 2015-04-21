package com.lvmama.pet.sweb.contract;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.utils.json.JSONResult;

/**
 * 联系人操作类
 * 
 * @author shihui
 * 
 */
@Results({ @Result(name = "edit_contact", location = "/WEB-INF/pages/back/contract/edit_contact.jsp") })
public class EditContactAction extends BackBaseAction {

	private static final long serialVersionUID = -8824943810090287965L;

	private ComContact contact;

	private static String CONTACT_KEY = "CONTACT_KEY";

	private Long contactId;
	
	private static int index = 0;

	@Action("/contract/contact_index")
	public String index() {
		return "edit_contact";
	}

	/**
	 * 新增联系人，暂时保存在session中
	 * */
	@Action("/contract/doAddContact")
	public void doAddContact() {
		JSONResult result = new JSONResult();
		try {
			if (contact != null) {
				HttpSession session = getRequest().getSession();
				List<ComContact> contactList = null;
				Object obj = session.getAttribute(CONTACT_KEY);
				if (obj != null) {
					contactList = (ArrayList<ComContact>) obj;
				} else {
					contactList = new ArrayList<ComContact>();
				}
				contact.setContactId(new Long(index++));
				contactList.add(contact);
				session.setAttribute(CONTACT_KEY, contactList);
				result.put("id", contact.getContactId());
				result.put("name", contact.getName());
				result.put("telephone", contact.getTelephone());
				result.put("memo", contact.getMemo());
			}
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}

	/**
	 * 从session中删除联系人
	 * */
	@Action("/contract/doRemoveContact")
	public void doRemoveContact() {
		JSONResult result = new JSONResult();
		try {
			if (contactId != null) {
				HttpSession session = getRequest().getSession();
				List<ComContact> contactList = null;
				Object obj = session.getAttribute(CONTACT_KEY);
				if (obj != null) {
					contactList = (ArrayList<ComContact>) obj;
					for (int i = 0; i < contactList.size(); i++) {
						if (contactList.get(i).getContactId().equals(contactId)) {
							contactList.remove(i);
							break;
						}
					}
					session.setAttribute(CONTACT_KEY, contactList);
				}
			}
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}

	public ComContact getContact() {
		return contact;
	}

	public void setContact(ComContact contact) {
		this.contact = contact;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
}
