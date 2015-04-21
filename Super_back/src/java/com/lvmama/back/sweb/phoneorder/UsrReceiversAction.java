package com.lvmama.back.sweb.phoneorder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.sweb.ord.OrdInvoiceReqAction.InvoiceReq;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

@ParentPackage("json-default")
@Results( {
	@Result(name = "address", location = "/WEB-INF/pages/back/usrreceiver/address.jsp"),
	@Result(name = "insertAddress", location = "/WEB-INF/pages/back/usrreceiver/insertAddress.jsp")})
public class UsrReceiversAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8306986861808834092L;
	private String userId;
	private List<UsrReceivers> receiversList;
	private IReceiverUserService receiverUserService;
	private String receiverId;
	private UsrReceivers usrReceivers;
	private String jsonMsg;
	HttpSession session = this.getSession();
	private List<UsrReceivers> contactorList;
	private List<UsrReceivers> visitorList;
	private List<UsrReceivers> usrReceiversList;
	private Long personId;
	private Long orderId;
	private String provinceId;
	private String selectedReceiverId;
	private String selectedReceiverId2;//暂时用来做发票地址使用
	private PlaceCityService placeCityService;
	private String index="";
	private List<ComCity> cityList;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	@Action(value = "/usrReceivers/loadList", results = @Result(name = "receiversList", location = "/WEB-INF/pages/back/usrreceiver/receivers.jsp"))
	public String loadUserReceivers() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("receiversType", Constant.RECEIVERS_TYPE.CONTACT.name());
		this.receiversList = this.receiverUserService
				.loadRecieverByParams(params);
		return "receiversList";
	}

	@Action(value = "/usrReceivers/loadReciever", results = @Result(name = "receiver", location = "/WEB-INF/pages/back/usrreceiver/selectReciever.jsp"))
	public String getReceiverById() {
		usrReceivers = this.receiverUserService.getRecieverByPk(receiverId);
		this.getRequest();
		return "receiver";
	}

	@Action(value = "/usrReceivers/toAddReciever", results = @Result(name = "toAddReciever", location = "/WEB-INF/pages/back/usrreceiver/insertReciever.jsp"))
	public String toAddReceiver() {
		return "toAddReciever";
	}
	
	@Action(value = "/usrReceivers/toChannelReciever", results = @Result(name = "toChannelReciever", location = "/WEB-INF/pages/back/usrreceiver/channelReciever.jsp"))
	public String toChannelReceiver() {
		return "toChannelReciever";
	}

	@Action(value = "/usrReceivers/saveReceiver", results = @Result(type = "json", name = "saveReceiver", params = {
			"includeProperties", "jsonMsg.*" }))
	public String saveReceiver() {
		try {
			UUIDGenerator gen = new UUIDGenerator();
			this.usrReceivers.setReceiverId(gen.generate().toString());
			this.usrReceivers.setUserId(userId);
			this.usrReceivers.setIsValid("Y");
			this.usrReceivers.setCreatedDate(new Date());
			this.usrReceivers.setReceiversType(Constant.RECEIVERS_TYPE.CONTACT.name());
			this.receiverUserService.insert(usrReceivers);
			this.jsonMsg = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			this.jsonMsg = "error";
		}
		return "saveReceiver";
	}

	@Action(value = "/usrReceivers/updateReceiver", results = @Result(type = "json", name = "updateReceiver", params = {
			"includeProperties", "jsonMsg.*" }))
	public String updateReceiver() {
		try {
			UsrReceivers usrReceivers = this.receiverUserService
					.getRecieverByPk(receiverId);
			usrReceivers.setCardNum(this.usrReceivers.getCardNum());
			usrReceivers.setCardType(this.usrReceivers.getCardType());
			usrReceivers.setEmail(this.usrReceivers.getEmail());
			usrReceivers.setFax(this.usrReceivers.getFax());
			usrReceivers.setFaxContactor(this.usrReceivers.getFaxContactor());
			usrReceivers.setMobileNumber(this.usrReceivers.getMobileNumber());
			usrReceivers.setPhone(this.usrReceivers.getPhone());
			usrReceivers.setReceiverName(this.usrReceivers.getReceiverName());
			this.receiverUserService.update(usrReceivers);
			this.jsonMsg = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			this.jsonMsg = "error";
		}
		return "updateReceiver";
	}

	@Action(value = "/usrReceivers/toUpdateReciever", results = @Result(name = "toUpdateReciever", location = "/WEB-INF/pages/back/usrreceiver/insertReciever.jsp"))
	public String toUpdateReciever() {
		usrReceivers = this.receiverUserService.getRecieverByPk(receiverId);
		return "toUpdateReciever";
	}
    

	/**
	 * 加载地址列表
	 * */
	@Action("/usrReceivers/loadAddresses")
	public String loadAddresses() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("receiversType", Constant.RECEIVERS_TYPE.ADDRESS.name());
		this.usrReceiversList = receiverUserService.loadRecieverByParams(params);
		
		return "address";
	}
	
	/**
	 * 弹出新增地址框
	 */
	@Action("/usrReceivers/doAddAddress")
	public String doAddAddress() {
		this.usrReceivers = new UsrReceivers();
		this.usrReceivers.setUserId(userId);	
		return "insertAddress";
	}	
	
	/**
	 * 让页面联动，取城市信息
	 */
	@Action("/usrReceivers/citys")
	public void getCitys(){
		JSONResult result=new JSONResult();
		try{
			List<ComCity> list = placeCityService
					.getCityListByProvinceId(provinceId);
			if(CollectionUtils.isNotEmpty(list)){
				JSONArray array=new JSONArray();
				for(ComCity cc:list){
					JSONObject obj=new JSONObject();
					obj.put("cityId", cc.getCityId());
					obj.put("cityName", cc.getCityName());
					array.add(obj);
				}
				result.put("list", array);
			}
		}catch(Exception ex){
			
		}
		
		result.output(getResponse());
	}

	/**
	 * 新增地址
	 */
	@Action("/usrReceivers/saveAddress")
	public void saveAddress() {
		try {
			String province=usrReceivers.getProvince();
			String city=usrReceivers.getCity();
			if(StringUtils.isNotEmpty(province)){//用编号转名字
				ComProvince cp=placeCityService.selectByPrimaryKey(province);
				if(cp!=null){
					usrReceivers.setProvince(cp.getProvinceName());
					
					ComCity cc=placeCityService.selectCityByPrimaryKey(city);
					if(cc!=null){
						usrReceivers.setCity(cc.getCityName());
					}
				}
			}
			UUIDGenerator gen = new UUIDGenerator();
			this.usrReceivers.setReceiverId(gen.generate().toString());
			this.usrReceivers.setIsValid("Y");
			this.usrReceivers.setCreatedDate(new Date());
			this.usrReceivers.setReceiversType(Constant.ORD_PERSON_TYPE.ADDRESS.name());
			this.receiverUserService.insert(usrReceivers);
			returnMessage(true);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 弹出修改地址框
	 */
	@Action("/usrReceivers/doUpdateAddress")
	public String doUpdateAddress() {
		usrReceivers = this.receiverUserService.getRecieverByPk(receiverId);
		if(StringUtils.isNotEmpty(usrReceivers.getProvince())){
			ComProvince cp=placeCityService.selectByProvinceName(usrReceivers.getProvince());
			if(cp!=null){				
				usrReceivers.setProvince(cp.getProvinceId());
				if(StringUtils.isNotEmpty(usrReceivers.getCity())){
					ComCity cc=placeCityService.selectCityByNameAndCity(cp.getProvinceId(),usrReceivers.getCity());
					if(cc!=null){
						usrReceivers.setCity(cc.getCityId());
					}
				}
				cityList=placeCityService.getCityListByProvinceId(cp.getProvinceId());
			}
		}
		return "insertAddress";
	}

	/**
	 * 修改地址
	 */
	@Action("/usrReceivers/updateAddress")
	public void updateAddress() {
		try {
			
			String province=usrReceivers.getProvince();
			String city=usrReceivers.getCity();
			if(StringUtils.isNotEmpty(province)){//用编号转名字
				ComProvince cp=placeCityService.selectByPrimaryKey(province);
				if(cp!=null){
					usrReceivers.setProvince(cp.getProvinceName());
					
					ComCity cc=placeCityService.selectCityByPrimaryKey(city);
					if(cc!=null){
						usrReceivers.setCity(cc.getCityName());
					}
				}
			}
			this.receiverUserService.update(usrReceivers);			
			if(personId != null) {//如果有实体票地址
				OrdPerson ordPerson = orderServiceProxy.selectOrdPersonByPrimaryKey(personId);
				Person person = new Person();
				org.springframework.beans.BeanUtils.copyProperties(ordPerson,person);
				person.setProvince(usrReceivers.getProvince());
				person.setCity(usrReceivers.getCity());
				person.setAddress(usrReceivers.getAddress());
				person.setPostcode(usrReceivers.getPostCode());
				person.setMobile(usrReceivers.getMobileNumber());
				person.setName(usrReceivers.getReceiverName());
				orderServiceProxy.updatePerson(person, orderId, getOperatorName());
			}
			returnMessage(true);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}
	
	/**
	 * 删除地址
	 */
	@Action("/usrReceivers/deleteAddress")
	public void deleteAddress() {
		try {
			this.receiverUserService.delete(receiverId);
			returnMessage(true);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}
	
	/**
	 * 返回操作成功信息
	 */
	private void returnMessage(boolean flag) {
		try {
			if (flag) {
				this.getResponse().getWriter().write("{result:true}");
			} else {
				this.getResponse().getWriter().write("{result:false}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<UsrReceivers> getReceiversList() {
		return receiversList;
	}

	public void setReceiversList(List<UsrReceivers> receiversList) {
		this.receiversList = receiversList;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public void setUsrReceivers(UsrReceivers usrReceivers) {
		this.usrReceivers = usrReceivers;
	}

	public UsrReceivers getUsrReceivers() {
		return usrReceivers;
	}

	public String getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}

	public List<UsrReceivers> getContactorList() {
		return contactorList;
	}

	public void setContactorList(List<UsrReceivers> contactorList) {
		this.contactorList = contactorList;
	}

	public List<UsrReceivers> getVisitorList() {
		return visitorList;
	}

	public void setVisitorList(List<UsrReceivers> visitorList) {
		this.visitorList = visitorList;
	}

	public List<UsrReceivers> getUsrReceiversList() {
		return usrReceiversList;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setSelectedReceiverId(String selectedReceiverId) {
		this.selectedReceiverId = selectedReceiverId;
	}

	public String getSelectedReceiverId() {
		return selectedReceiverId;
	}

	public Long getPersonId() {
		return personId;
	}

	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @return the selectedReceiverId2
	 */
	public String getSelectedReceiverId2() {
		return selectedReceiverId2;
	}

	/**
	 * @param selectedReceiverId2 the selectedReceiverId2 to set
	 */
	public void setSelectedReceiverId2(String selectedReceiverId2) {
		this.selectedReceiverId2 = selectedReceiverId2;
	}

	/**
	 * @return the provinceList
	 */
	public List<ComProvince> getProvinceList() {
		return placeCityService.getProvinceList();
	}


	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the cityList
	 */
	public List<ComCity> getCityList() {
		return cityList;
	}

	private String hidePhysical="false";//页面当中是否隐藏实体票的按钮
	private String hideButton="false";//是否隐藏操作按钮

	
	/**
	 * @return the hidePhysical
	 */
	public String getHidePhysical() {
		return hidePhysical;
	}

	/**
	 * @param hidePhysical the hidePhysical to set
	 */
	public void setHidePhysical(String hidePhysical) {
		this.hidePhysical = hidePhysical;
	}

	/**
	 * @return the hideButton
	 */
	public String getHideButton() {
		return hideButton;
	}

	/**
	 * @param hideButton the hideButton to set
	 */
	public void setHideButton(String hideButton) {
		this.hideButton = hideButton;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}	
	
}
