package com.lvmama.back.sweb.ord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.InvoiceUtil;
import com.lvmama.comm.utils.Pair;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name = "invoice_form", location="/WEB-INF/pages/back/ord/invoice/invoice_form.jsp"),
	@Result(name = "add_invoice_info",location="/WEB-INF/pages/back/ord/invoice/add_invoice_info.jsp"),
	@Result(name = "wait_to_add", location="/WEB-INF/pages/back/ord/invoice/wait_to_add.jsp")
})
public class OrdInvoiceReqAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4237157047620135316L;

	private UserUserProxy userUserProxy;
	
	
	private String orderId="";
	private String userName="";
	private String userMobile="";
	private String userEmail="";
	private String contactName="";
	private String contactMobile="";
	private OrderService orderServiceProxy;
	private List<OrdOrder> ordersList;
	private String orderids;//添加订单号到开发票里面
	private OrdInvoice ordInvoice;
	private String invoiceAddressId;//送货地址
	private IReceiverUserService receiverUserService;
	private List<OrdInvoice> ordInvoiceList;
	
	List<Object> invoiceNumberList=new ArrayList<Object>();
	String totalYuan;//获取发票的总金额
	private Long invoiceNumber;//获取发票的总张数
	/**
	 * 进入添加发票的页面，可以添加多个发票号.
	 * @return
	 */
	@Action("/ord/goInvoceForm")
	public String goInvoiceForm(){	
		if(getSession().getAttribute(INVOICE_REQ_KEY)!=null){
			getSession().removeAttribute(INVOICE_REQ_KEY);
		}
		return "invoice_form";
	}
	
	/**
	 * 读取能开票的订单列表
	 * @return
	 */
	@Action("/ord/waitInvoceOrder")
	public String waitInvoceOrder(){
		CompositeQuery compositeQuery=new CompositeQuery();
		CompositeQuery.ExcludedContent excludedContent=compositeQuery.getExcludedContent();
		excludedContent.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		
		CompositeQuery.OrderStatus orderStatus=compositeQuery.getStatus();
		orderStatus.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		
		CompositeQuery.OrderContent orderContent=compositeQuery.getContent();
		CompositeQuery.OrderIdentity orderIdentity=compositeQuery.getOrderIdentity();
		
		orderContent.setNeedInvoice("false,part");//包含没有发票以及开过部分票的订单
		
		if(StringUtils.isNotEmpty(orderId)){
			orderIdentity.setOrderId(NumberUtils.toLong(orderId.trim()));
		}
		if(StringUtils.isNotEmpty(userName)){
			orderContent.setUserName(userName.trim());
		}
		if(StringUtils.isNotEmpty(userEmail)){
			orderContent.setEmail(userEmail.trim());
		}
		
		if(StringUtils.isNotEmpty(userMobile)){
			orderContent.setMobile(userMobile.trim());
		}
		
		if(StringUtils.isNotEmpty(contactName)){
			orderContent.setContactName(contactName.trim());			
		}
		if(StringUtils.isNotEmpty(contactMobile)){
			orderContent.setContactMobile(contactMobile);
		}
		//将用户名Email等转为IdList形式查询
		Map<String, Object> param = orderContent.getUserParam(orderContent);
		if(param != null) {
			orderContent.setUserIdList(orderContent.getUserList(userUserProxy.getUsers(param)));
		}
		Long totalRecords = orderServiceProxy
				.compositeQueryOrdOrderCount(compositeQuery);
		pagination = initPagination();
		pagination.setTotalRecords(totalRecords);
		
		PageIndex pageIndex=new PageIndex();
		pageIndex.setBeginIndex(pagination.getFirstRow());
		pageIndex.setEndIndex(pagination.getLastRow());
		
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		
		compositeQuery.setPageIndex(pageIndex);
		ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		return "invoice_form";
	}
	
	@Action("/ord/invoiceAddReq")
	public void addSession(){
		JSONResult result=new JSONResult();
		if(StringUtils.isNotEmpty(orderids)){
			String array[]=StringUtils.split(orderids,",");
			InvoiceReq ir=getReq();
			JSONArray error=new JSONArray();
			StringBuffer errMsg=new StringBuffer();
			for(String o:array){
				Long ordId=NumberUtils.toLong(o);
				if(ordId>0){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(ordId);
					if(order!=null){
						try{
							long amount=0;
							amount+=orderServiceProxy.getOrderInvoiceAmountNotInvoiceId(ordId);
							amount+=orderServiceProxy.unableInvoiceAmountByOrderId(ordId);
							ir.add(order,amount);
						}catch(NotSameException ex){
							error.add(ex.getOrderId());
						}catch(AmountException ex){							
							errMsg.append(ordId);
							errMsg.append("、");
						}
					}
				}
			}
			putToSession(ir);
			StringBuffer sb=new StringBuffer();
			if(error.size()>0){
				sb.append("不能添加到开发票当中的订单号:");
				sb.append(error.toString());
			}
			if(errMsg.length()>0){
				if(sb.length()>0){
					sb.append(",");
				}
				sb.append("因可开票金额小于1不能开票的订单号：");
				sb.append(errMsg.substring(0, errMsg.length()-1));
			}
			if(sb.length()>0){
				result.put("error", sb.toString());
			}
		}		
		result.output(getResponse());
	}
	@Action("/ord/waitToAddList")
	public String waitToAdd(){
		return "wait_to_add";
	}
	
	@Action("/ord/goAddInvoiceInfo")
	public String goAddInvoiceInfo(){
		Map<String, String[]> params=getRequest().getParameterMap();
		InvoiceReq ir=getReq();			
		for(String key:params.keySet()){	
			if(key.startsWith("orderContent_")){
				Long orderId=NumberUtils.toLong(key.replace("orderContent_", ""));
				if(orderId>0){
					ir.addContent(orderId, getRequest().getParameter(key));
				} 
				this.orderId=orderId.toString();
			}
		}
		
		return "add_invoice_info";
	}
	
	@Action("/ord/removeOrderInInvoice")
	public void removeOrder(){
		JSONResult result=new JSONResult();
		try{			
			if(StringUtils.isNotEmpty(orderId)){
				InvoiceReq ir=getReq();				
				if(ir.remove(NumberUtils.toLong(orderId))){
					putToSession(ir);
				}else{
					throw new Exception("没有找到订单内容");
				}
			}
		}catch(Exception ex){
			result.raise(ex);			
		}
		result.output(getResponse());
	}
	
	@Action("/ord/saveCompositeInvoice")
	public void saveCompositeInvoice(){
		JSONResult result=new JSONResult();	
		try{
			InvoiceReq ir=getReqSession();
			if(ir==null||ir.isEmpty()){
				throw new Exception("开票订单为空");
			}
			if(StringUtils.isEmpty(ir.getInvoiceContent())){
				throw new Exception("发票内容为空");
			}
				
			int pos =0;
			//String money=getRequest().getParameter("totalYuan");
			//double totalMoney= Double.parseDouble(money);
			
			Long m=ir.getTotalAmount();
			long temp= 0L;
			List<Pair<Invoice,Person>> invoices=new ArrayList<Pair<Invoice,Person>>();
			for(OrdInvoice or:ordInvoiceList){
				this.ordInvoice=or;
				Invoice invoice = new Invoice();
				BeanUtils.copyProperties(invoice, this.ordInvoice);					
				Person person=new Person();
				invoiceAddressId = getRequest().getParameter("invoiceAddressId"+(pos++));
			
				if(or.getAmount()!=null){
					temp=temp+or.getAmount();
				}				
				if(pos==ordInvoiceList.size()){					
					invoice.setAmount(m-temp);
					if(invoice.getAmount()<1){
						throw new Exception("开票金额少于1");
					}
				}
				
				if(!StringUtils.equals("SELF", invoice.getDeliveryType())){
					if(StringUtils.isEmpty(invoiceAddressId)){
						throw new Exception("送货地址不可以为空");
					}
					person=initAddress(invoiceAddressId);
				}
				invoice.setDetail(ir.getInvoiceContent());
				invoice.setCompany(InvoiceUtil.getInvoiceCompany(ir.getFirst()).name());
				Pair<Invoice, Person> kv = Pair.make_pair(invoice, person);
				invoices.add(kv);
			}
			ResultHandle handle = orderServiceProxy.insertInvoiceByOrders(invoices, ir.getOrderIds(), getOperatorNameAndCheck());
			if(handle.isFail()){
				result.raise(handle.getMsg());
			}else{
				getSession().removeAttribute(INVOICE_REQ_KEY);
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
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
	
	private static final String INVOICE_REQ_KEY="invoice_req";
	
	InvoiceReq getReqSession(){
		return (InvoiceReq)getSession().getAttribute(INVOICE_REQ_KEY);
	}
	InvoiceReq getReq(){
		InvoiceReq req=getReqSession();
		if(req==null){
			req = new InvoiceReq();
		}
		return req;
	}
	void putToSession(InvoiceReq req){
		getSession().setAttribute(INVOICE_REQ_KEY, req);
	}
	
	static class FindOrder implements org.apache.commons.collections.Predicate{

		private Long orderid;
		
		public FindOrder(Long orderid) {
			super();
			this.orderid = orderid;
		}




		@Override
		public boolean evaluate(Object arg0) {			
			if(arg0 instanceof OrdOrder){
				return ((OrdOrder)arg0).getOrderId().equals(orderid);
			}
			return false;
		}
		
	}
	
	static class AmountException extends Exception{

		/**
		 * 
		 */
		private static final long serialVersionUID = 5549642879899064338L;
		
	}
	
	static class NotSameException extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 5278253298534519214L;
		Long orderId;
		public NotSameException(Long orderId) {
			super();
			this.orderId=orderId;
		}
		/**
		 * @return the orderId
		 */
		public Long getOrderId() {
			return orderId;
		}
		
	}
	
	/**
	 * 发布申请的数据结构
	 * @author yangbin
	 *
	 */
	public static class InvoiceReq implements Serializable,Iterable<OrdOrder>{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1601240182847564288L;
		private List<OrdOrder> orders;
		private long unAmount=0;
		private boolean partInvoice=false;
		private Map<Long, String> contents;
		public OrdOrder getFirst(){
			return orders.get(0);
		}
		
		public boolean remove(Long orderId){
			OrdOrder order=(OrdOrder)CollectionUtils.find(orders, new FindOrder(orderId));
			if(order!=null){
				if(contents.containsKey(order.getOrderId())){
					contents.remove(order.getOrderId());
				}
				return orders.remove(order);
			}
			return false;	
		}
		
		public boolean isEmpty(){
			return orders.isEmpty();
		}
		
		/**
		 * 添加一个订单号
		 * @param order
		 * @param unAmount 不能开出发票的金额.
		 * @throws NotSameException
		 * @throws AmountException
		 */
		public void add(OrdOrder order,long unAmount)throws NotSameException,AmountException{			
			if(CollectionUtils.find(orders, new FindOrder(order.getOrderId()))!=null){
				return;
			}
			
			if(partInvoice){
				throw new NotSameException(order.getOrderId());
			}
			if(StringUtils.equals("true", order.getNeedInvoice())){
				throw new NotSameException(order.getOrderId());
			}else if(StringUtils.equals("part", order.getNeedInvoice())){//如果是部分开票的发票，之前已经存在发票了就不可以再继续
				if(!orders.isEmpty()){
					throw new NotSameException(order.getOrderId());
				}
				partInvoice=true;
			}
			
			//添加对订单的金额检查
			if(order.getActualPay()-unAmount<1){
				throw new AmountException();
			}
			//检查订单号是否已经当前用户的
			if(!orders.isEmpty()){
				for(OrdOrder o:orders){
					if(!o.getUserId().equals(order.getUserId())){
						throw new NotSameException(order.getOrderId());
					}
				}
				
				OrdOrder firstOrder=getFirst();
				
				Constant.CODE_TYPE type=InvoiceUtil.getInvoiceDetail(firstOrder);
				Constant.CODE_TYPE newType=InvoiceUtil.getInvoiceDetail(order);
				if(!type.equals(newType))//两次类型不一置时不可以添加
					throw new NotSameException(order.getOrderId());
			}
			this.unAmount+=unAmount;
			orders.add(order);			
		}
		
		
		
		public void addContent(Long orderId,String content){
			if(null==CollectionUtils.find(orders, new FindOrder(orderId))){
				throw new IllegalArgumentException("订单不存在");
			}
			contents.put(orderId, content);
		}
		
		
		/**
		 * 得到发票内容列表，以字符串显示出来
		 * @return
		 */
		public String getInvoiceContent(){
			StringBuffer sb=new StringBuffer();
			Set<String> set=new HashSet<String>();
			for(Long key:contents.keySet()){
				String value=contents.get(key);
				if(!set.contains(value)){
					set.add(value);
					sb.append(value);
					sb.append(",");
				}
			}
			sb.setLength(sb.length()-1);
			return sb.toString();
		}
		
		public String getZhInvoiceContent(){
			return InvoiceUtil.getZhInvoiceContent(getInvoiceContent());
		}
		
		/**
		 * 读取订单的金额列表
		 * @return
		 */
		public Float getTotalYuan(){
			long t=getTotalAmount();
			
			return PriceUtil.convertToYuan(t);
		}
		
		public long getTotalAmount(){
			long t=0;
			if(CollectionUtils.isNotEmpty(orders)){
				for(OrdOrder order:orders){					
					t+=order.getActualPay();
				}
			}
			return t-unAmount;
		}
		
		public InvoiceReq() {
			super();
			orders=new ArrayList<OrdOrder>();
			contents=new HashMap<Long, String>();
		}

		

		public List<Long> getOrderIds(){
			if(CollectionUtils.isEmpty(orders)){
				return null;
			}
			
			List<Long> list=new ArrayList<Long>();
			for(OrdOrder order:orders){
				list.add(order.getOrderId());
			}
			
			return list;
		}

		@Override
		public Iterator<OrdOrder> iterator() {
			// TODO Auto-generated method stub
			return orders.iterator();
		}
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userMobile
	 */
	public String getUserMobile() {
		return userMobile;
	}

	/**
	 * @param userMobile the userMobile to set
	 */
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactMobile
	 */
	public String getContactMobile() {
		return contactMobile;
	}

	/**
	 * @param contactMobile the contactMobile to set
	 */
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	/**
	 * @param orderServiceProxy the orderServiceProxy to set
	 */
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}	

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public List<Long> getInvoiceNumberList() {
		List<Long> array =new ArrayList<Long>();
		if(invoiceNumber!=null){
			array =new ArrayList<Long>(invoiceNumber.intValue());
			for(long i=0;i<invoiceNumber;i++){
				array.add(i);
			}
		}else{//只开一张发票			
			array.add(NumberUtils.toLong("0"));
		}
		return array;
	}

	/**
	 * @return the ordersList
	 */
	public List<OrdOrder> getOrdersList() {
		return ordersList;
	}

	/**
	 * @param orderids the orderids to set
	 */
	public void setOrderids(String orderids) {
		this.orderids = orderids;
	}
	
	public List<CodeItem> getInvoiceDetails(){
		InvoiceReq ir=getReqSession();
		if(ir!=null){
			return CodeSet.getInstance().getCodeListAndBlank(InvoiceUtil.getInvoiceDetail(ir.getFirst()).name());
		}
		return null;
	}
	
	public String getOrderUserId(){
		try{
			return getReqSession().getFirst().getUserId();
		}catch(Exception ex){
			return null;
		}
	}
	
	public List<CodeItem> getDeliveryTypeList(){
		return CodeSet.getInstance().getCodeList(Constant.CODE_TYPE.DELIVERY_TYPE.name());
	}

	/**
	 * @return the ordInvoice
	 */
	public OrdInvoice getOrdInvoice() {
		return ordInvoice;
	}

	/**
	 * @param ordInvoice the ordInvoice to set
	 */
	public void setOrdInvoice(OrdInvoice ordInvoice) {
		this.ordInvoice = ordInvoice;
	}

	/**
	 * @param invoiceAddressId the invoiceAddressId to set
	 */
	public void setInvoiceAddressId(String invoiceAddressId) {
		this.invoiceAddressId = invoiceAddressId;
	}

	public List<CodeItem> getInvoiceContentList(String orderType){
		return InvoiceUtil.getInvoiceContents(orderType, false);
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public List<OrdInvoice> getOrdInvoiceList() {
		return ordInvoiceList;
	}

	public void setOrdInvoiceList(List<OrdInvoice> ordInvoiceList) {
		this.ordInvoiceList = ordInvoiceList;
	}	
	/**
	 * 最多可开发票张数 合并开票只能开一张票
	 * @return
	 */
	public int getInvoiceNum(){
		
		InvoiceReq ir=getReq();
		if(ir==null) return 1;
		
		if(ir.getOrderIds()!=null&&ir.getOrderIds().size()>1){
			return 1;
		}
		OrdOrder order = ir.getFirst();
		Map<String,String> map = new HashMap();
		if(order.getTravellerList()!=null){
			for(OrdPerson per:order.getTravellerList()){
				map.put(""+per.getPersonId(), ""+per.getPersonId());
			}
		}
		return map.size()<1?1:map.size();
	}
}