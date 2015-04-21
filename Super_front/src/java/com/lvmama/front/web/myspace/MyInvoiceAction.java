package com.lvmama.front.web.myspace;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.utils.InvoiceUtil;
import com.lvmama.comm.utils.Pair;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "addInvoicInfo", location = "/WEB-INF/pages/myspace/sub/order/createInvoice.ftl", type = "freemarker")
	,@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/order/addInvoiceResult.ftl", type = "freemarker")
	,@Result(name = "invalid.token", location = "/WEB-INF/pages/buy/wrong.ftl", type = "freemarker")
	,@Result(name="myspace",location="/myspace/order.do",type="redirect")
})
public class MyInvoiceAction extends SpaceBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1643511215368812550L;
	
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 发票列表
	 */
	private List<OrdInvoice> invoiceList;
	
	private List<UsrReceivers> usrReceiversList;
	
	private UsrReceivers usrReceivers;
	
	private IReceiverUserService receiverUserService;
	
	private List<OrdOrder> orderList;
	
	private List<CodeItem> codeItems;
	
	private String orderIds;
	
	private PayPaymentService payPaymentService;
	
	private String message;
	
	
	@Action("/myspace/addInvoicInfo")
	public String addInvoiceInfo(){
		orderList = new ArrayList<OrdOrder>();
		if(valideAndInitCardAmountRefuntAmountData()){
			if(orderList.size()==1){
				codeItems = InvoiceUtil.getInvoiceContents(orderList.get(0).getOrderType(), false);
				loadAddresses();
				return "addInvoicInfo";
			}else{
				if(validCanMergeInvoice()){
					initCodeItemByOrder();					
					loadAddresses();
					return "addInvoicInfo";
				}
			}
		}
		return "myspace";
	}
	
	private boolean valideAndInitCardAmountRefuntAmountData(){
		if(StringUtils.isEmpty(orderIds)) return false;
		
		orderList = new ArrayList<OrdOrder>();
		String[] ids = orderIds.split(",");
		for(int i=0;i<ids.length; i++){
			Long orderId = NumberUtils.toLong(ids[i]);
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
			if(isCanCreateInvoic(order)){
				order.setSumCardAmount(payPaymentService.selectCardPaymentSuccessSumAmount(orderId));
				order.setRefundedAmount(orderServiceProxy.getRefundAmountByOrderId(orderId, Constant.COMPLAINT_SYS_CODE.SUPER.name()));
				orderList.add(order);
			}else{
				return false;
			}
		}
		return valideOderIsBelongCurrentUser();
	}
	private boolean isCanCreateInvoic(OrdOrder ordOrder){
		if(ordOrder==null) return false;
		if(StringUtils.equals("true", ordOrder.getCanCreatInvoice())){
			long amount=orderServiceProxy.unableInvoiceAmountByOrderId(ordOrder.getOrderId());
			if(ordOrder.getActualPay()-amount<1){
				ordOrder.setCanCreatInvoice("false");
				return false;
			}
			return true;
		}
		return false;
	}
	private boolean valideOderIsBelongCurrentUser() {
		if(CollectionUtils.isEmpty(orderList)){
			return false;
		}
		for(OrdOrder order:orderList){
			if(!order.getUserId().equals(getUserId())){
				return false;
			}
		}
		return true;
	}
	//酒店不可以和非酒店订单合并开发票
	private boolean validCanMergeInvoice() {
		OrdOrder fristOrder = orderList.get(0);
		for(OrdOrder order:orderList){
			if(fristOrder.isHotel() && !order.isHotel()){
				return false;
			}
			if(!fristOrder.isHotel() && order.isHotel()){
				return false;
			}
		}
		return true;
	}

	// todo 初始化发票项目 取各订单项目交集
		private void initCodeItemByOrder() {
			codeItems = InvoiceUtil.getInvoiceContents(orderList.get(0).getOrderType(), false);
			if(orderList.size()>0){
				for(OrdOrder order:orderList){
					List<CodeItem> codeItemList = InvoiceUtil.getInvoiceContents(order.getOrderType(), false);
					retailList(codeItemList);
				}
			}
		}

		
	private void retailList(List<CodeItem> codeItemList) {
		List<CodeItem> codeItemListTem = new ArrayList<CodeItem>();
		for(CodeItem code:codeItems){
			for(CodeItem codeItem:codeItemList){
				if(code.getCode().equals(codeItem.getCode())){
					codeItemListTem.add(code);
				}
			}
		}
		codeItems = codeItemListTem;
	}

	@Action(value="/myspace/saveInvoicInfo",    
	interceptorRefs ={@InterceptorRef("token"),@InterceptorRef("defaultStack")})  	
	public String saveInvoiceInfo() {
		saveAddress();
		if(invoiceList!=null&&valideAndInitCardAmountRefuntAmountData()){
			try{
				Float m=getTotalAmount();
				long temp= 0L;
				List<Pair<Invoice,Person>> invoices=new ArrayList<Pair<Invoice,Person>>();
				for(OrdInvoice or:invoiceList){
					Invoice invoice = new Invoice();
					BeanUtils.copyProperties(invoice, or);					
					Person person=new Person();
					
					if(or.getAmount()!=null){
						temp=temp+or.getAmount();
					}	
					person=initAddress(usrReceivers.getReceiverId());
					invoice.setDeliveryType(Constant.DELIVERY_TYPE.EXPRESS.getCode());
					invoice.setCompany("COMPANY_3");
					Pair<Invoice, Person> kv = Pair.make_pair(invoice, person);
					invoices.add(kv);
				}
				if(m!=null && m.compareTo(PriceUtil.convertToYuan(temp))==0){
					ResultHandle handle = orderServiceProxy.insertInvoiceByOrders(invoices, getOrderIdList(), this.getUser().getUserName());
					if(handle.isFail()){
						message="申请开票失败" + handle.getMsg();
					}
				}else{
					message="申请开票失败,申请开票金额与订单金额不一致";
				}
				
			}catch(Exception e){
				e.fillInStackTrace();
			}
			
		}else{
			message="申请开票失败,申请开票订单未通过验证";
		}
		return "success";
	}
	
	private void loadAddresses() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", getUserId());
		params.put("receiversType", Constant.RECEIVERS_TYPE.ADDRESS.name());
		this.usrReceiversList = receiverUserService.loadRecieverByParams(params);
	}
	
	private void saveAddress() {
		try {
			if("0".equals(usrReceivers.getReceiverId())){
				UUIDGenerator gen = new UUIDGenerator();
				this.usrReceivers.setReceiverId(gen.generate().toString());
				this.usrReceivers.setIsValid("Y");
				this.usrReceivers.setCreatedDate(new Date());
				this.usrReceivers.setReceiversType(Constant.ORD_PERSON_TYPE.ADDRESS.name());
				this.usrReceivers.setUserId(getUserId());
				this.receiverUserService.insert(usrReceivers);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Person initAddress(String id) { 
		UsrReceivers usrReceiver = this.receiverUserService.getRecieverByPk(id); 
		if(usrReceiver==null){
			throw new NullPointerException("收件地址不存在");
		}
		Person person = new Person(); 
		person.setProvince(usrReceiver.getProvince()); 
		person.setCity(usrReceiver.getCity()); 
		person.setAddress(usrReceiver.getAddress()); 
		person.setPostcode(usrReceiver.getPostCode()); 
		person.setMobile(usrReceiver.getMobileNumber()); 
		person.setName(usrReceiver.getReceiverName()); 
		person.setPersonType(Constant.ORD_PERSON_TYPE.ADDRESS.name()); 
		person.setReceiverId(usrReceiver.getReceiverId()); 
		return person; 
	}
	
	public float getTotalAmount(){
		
		if(CollectionUtils.isEmpty(orderList)){
			return 0l;
		}
		Long amount=0l;
		for(OrdOrder order:orderList){
			amount+=(order.getActualPay()-orderServiceProxy.unableInvoiceAmountByOrderId(order.getOrderId()));
		}
		return PriceUtil.convertToYuan(amount);
	}
	
	/**
	 * 最多可开发票张数 合并开票只能开一张票
	 * @return
	 */
	public int getInvoiceNum(){
		if(orderList!=null && orderList.size()>1){
			return 1;
		}
		Map<String,String> map = new HashMap();
		for(OrdOrder order:orderList){
			if(order.getTravellerList()!=null){
				for(OrdPerson per:order.getTravellerList()){
					map.put(""+per.getPersonId(), ""+per.getPersonId());
				}
			}
		}
		return map.size()<1?1:map.size();
	}
	
	public List<Long> getOrderIdList(){
		if(CollectionUtils.isEmpty(orderList)){
			return null;
		}
		List<Long> list=new ArrayList<Long>();
		for(OrdOrder order:orderList){
			list.add(order.getOrderId());
		}
		return list;
	}
	public List<OrdInvoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<OrdInvoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public List<OrdOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrdOrder> orderList) {
		this.orderList = orderList;
	}

	public List<CodeItem> getCodeItems() {
		return codeItems;
	}

	public void setCodeItems(List<CodeItem> codeItems) {
		this.codeItems = codeItems;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public List<UsrReceivers> getUsrReceiversList() {
		return usrReceiversList;
	}

	public void setUsrReceiversList(List<UsrReceivers> usrReceiversList) {
		this.usrReceiversList = usrReceiversList;
	}

	public UsrReceivers getUsrReceivers() {
		return usrReceivers;
	}

	public void setUsrReceivers(UsrReceivers usrReceivers) {
		this.usrReceivers = usrReceivers;
	}
	
	
	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
