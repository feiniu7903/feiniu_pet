package  com.lvmama.back.sweb.phoneorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassEvent;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.vo.Constant;

@ParentPackage("json-default")
@Results( {
	@Result(name = "ord_person", location = "/WEB-INF/pages/back/phoneorder/ord_person.jsp"),
	@Result(name = "saveOrUpdate",type = "json", params = {"includeProperties", "jsonMsg.*" })
})
public class OrderPersonAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1775672167118313383L;
	private String receiverId;
	private PassCodeService passCodeService;
	private TopicMessageProducer orderMessageProducer;
	private TopicMessageProducer passportMessageProducer;
	private OrderService orderServiceProxy;
	private Long personId;
	private List<Person> person;
	private OrdPerson ordPerson;
	private List<Person> personList;
	private UsrReceivers usrReceivers;
	private Long orderId;
	private String jsonMsg;
	private OrdOrder orderDetail;
	private String deletePersonIds;
	private IReceiverUserService receiverUserService;
	private List<UsrReceivers> receiversList;
	@Action(value = "/ord/person")
	public String toOrdPerson(){
		personList=orderServiceProxy.queryPersonByOrderId(orderId);
		sortPerson(personList);
		orderDetail = orderServiceProxy.queryOrdOrderByOrderId(new Long(orderId));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", orderDetail.getUserId());
		params.put("receiversType", Constant.RECEIVERS_TYPE.CONTACT.name());
		this.receiversList = receiverUserService.loadRecieverByParams(params);
		for(UsrReceivers rec : receiversList) {
			rec.setCreatedDate(null);
			rec.setReceiverJsonData(JSONObject.fromObject(rec).toString());
		}
		this.getRequest().setAttribute("nullRecJson", JSONObject.fromObject(new UsrReceivers()).toString());
		return "ord_person";
	}
	private void sortPerson(List<Person> personList) {
		if(personList != null) {
			Collections.sort(personList, new Comparator<Person>() {
				@Override
				public int compare(Person o1, Person o2) {
					if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(o2.getPersonType())) {
						return 1;
					}
					if(Constant.ORD_PERSON_TYPE.EMERGENCY_CONTACT.name().equals(o1.getPersonType())) {
						return 1;
					}
					return 0;
				}
			});
		}
		
	}
	@Action(value = "/ord/saveOrUpdate")
	public void saveOrUpdate() throws Exception{
		try {
			List<Person> updateList = new ArrayList<Person>();
			boolean isSendMsgflag = false;
			boolean isUpdateTravellerflag = false;
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			for(Person per : person) {
				if(per == null) {
					continue;
				}
				Person p = new Person();
				BeanUtils.copyProperties(per, p);
				//如果新增紧急联系人，判断订单中是否已经含有紧急联系人
				if (Constant.ORD_PERSON_TYPE.EMERGENCY_CONTACT.name().equals(per.getPersonType())
						&& null != orderId) {
					if (order.getEmergencyContact() != null && !(p.getPersonId() == order.getEmergencyContact().getPersonId())) {
						this.jsonMsg = "订单已存在紧急联系人";
						this.sendAjaxMsg(jsonMsg);
						return;
					}
				}
				//判断游客的姓名和联系方式是否为空
				if(StringUtils.isEmpty(p.getName())) {
					this.jsonMsg = "姓名不能为空";
					this.sendAjaxMsg(jsonMsg);
					return;
				}
				if(Constant.ORD_PERSON_TYPE.CONTACT.name().equals(p.getPersonType())){
					isSendMsgflag = true;
				}else if(Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(p.getPersonType()) && !isUpdateTravellerflag){
					isUpdateTravellerflag = checkPersonUpdate(order, per);	
				}
				updateList.add(p);
			}	
			for(Person p : updateList) {
				orderServiceProxy.addPerson2OrdOrder(p, orderId, getOperatorName());
			}
			if(deletePersonIds != null) {
				for(String pId : deletePersonIds.split(",")) {
					if(pId == null || "".equals(pId)) {
						continue;
					}
					orderServiceProxy.removePersonFromOrdOrder(Long.valueOf(pId), orderId, getOperatorName());
					if(!isUpdateTravellerflag){
						isUpdateTravellerflag = true;
					}
				}
			}
			//二维码修改联系人
			if(isSendMsgflag){
				sendMsg(orderId);
			}
			if(isUpdateTravellerflag){
				orderMessageProducer.sendMsg(MessageFactory.newOrderTravellerPersonModifyEventMessage(orderId));
			}
			this.jsonMsg = "";
		} catch (Exception e) {
			this.jsonMsg = "操作失败";
			e.printStackTrace();
		}
		this.sendAjaxMsg(jsonMsg);
		return;
	}
	private boolean checkPersonUpdate(OrdOrder order, Person per) {
		if(per.getPersonId() > 0){
			List<OrdPerson> ordPersion = order.getPersonList();
			for (int i = 0; i < ordPersion.size(); i++) {
				OrdPerson person = ordPersion.get(i);
				if(person != null && per != null){
					if(person.getPersonId().longValue()==per.getPersonId()){
						if(person.getCertNo() == null) {
							person.setCertNo("");
						}
						if(!per.getName().equals(person.getName())){
							return true;
						}else if (!per.getCertNo().equals(person.getCertNo())) {
							return true;
						}else if(per.getPersonId() == order.getFristTravellerOrdPerson().getPersonId().longValue()){
							if(order.getFristTravellerOrdPerson().getMobile() == null) {
								order.getFristTravellerOrdPerson().setMobile("");
							}
							if(!per.getMobile().equals(order.getFristTravellerOrdPerson().getMobile())){
								return true;
							}
						}
					}
				}
			}
		}else {
			return true;
		}
		return false;
	}

	private void sendMsg(Long orderId) {
		OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
		List<PassEvent> list = passCodeService.updateContact(order);
		//发送申码请求处理消息
		for (PassEvent passEvent : list) {
			passportMessageProducer.sendMsg(MessageFactory.newPasscodeEventMessage(passEvent.getEventId()));
		}
	}
	public List<Person> getPerson() {
		return person;
	}
	public void setPerson(List<Person> person) {
		this.person = person;
	}
	public String updateOrdPerson(){
		
		return "updateOrdPerson";
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public UsrReceivers getUsrReceivers() {
		return usrReceivers;
	}
	public void setUsrReceivers(UsrReceivers usrReceivers) {
		this.usrReceivers = usrReceivers;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public OrdPerson getOrdPerson() {
		return ordPerson;
	}
	public void setOrdPerson(OrdPerson ordPerson) {
		this.ordPerson = ordPerson;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	public void setPassportMessageProducer(
			TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}
	public OrdOrder getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(OrdOrder orderDetail) {
		this.orderDetail = orderDetail;
	}
	public List<UsrReceivers> getReceiversList() {
		return receiversList;
	}
	public void setReceiversList(List<UsrReceivers> receiversList) {
		this.receiversList = receiversList;
	}
	public IReceiverUserService getReceiverUserService() {
		return receiverUserService;
	}
	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}
	public String getDeletePersonIds() {
		return deletePersonIds;
	}
	public void setDeletePersonIds(String deletePersonIds) {
		this.deletePersonIds = deletePersonIds;
	}
}
