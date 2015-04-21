package com.lvmama.front.web.usr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.InvoiceResult;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.InvoiceUtil;
import com.lvmama.comm.utils.Pair;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;


/**
 * 
 *  自动发票Action.
 *
 */
@Results( {  
	@Result(name = "invoiceApply", location = "/WEB-INF/pages/usr/invoice/invoiceApply.ftl", type = "freemarker")
	,@Result(name = "invoiceApplyList", location = "/WEB-INF/pages/usr/invoice/invoiceApplyList.ftl", type = "freemarker")
	,@Result(name = "newUserAddAdds", location = "/WEB-INF/pages/usr/invoice/newUserAddAdds.ftl", type = "freemarker")
	,@Result(name = "oldUserAddAdds", location = "/WEB-INF/pages/usr/invoice/oldUserAddAdds.ftl", type = "freemarker")
	,@Result(name = "invoiceApplyDetail", location = "/WEB-INF/pages/usr/invoice/invoiceApplyDetail.ftl", type = "freemarker")
	,@Result(name = "submitMessage", location = "/WEB-INF/pages/usr/invoice/submitMessage.ftl", type = "freemarker")
	,@Result(name="receivers_list",location="/WEB-INF/pages/usr/invoice/receivers_list.ftl", type = "freemarker")
})
public class AutoInvoiceAction extends BaseAction {
	
	private static final long serialVersionUID = 4119581681357571010L;
	private static final Logger logger = Logger.getLogger(AutoInvoiceAction.class);

	private OrderService orderServiceProxy;
	private IReceiverUserService receiverUserService;
	private PlaceCityService placeCityService;
	
	//可以申请发票的订单列表.
	private List<OrdOrder> ordOrderList = new ArrayList<OrdOrder>();
	//以";"为分隔符的订单id列表.
	private String orderIds;
	private Invoice invoiceForm = new Invoice();
	private OrdInvoice ordInvoice = new OrdInvoice();
	//发票申请列表
	private List<OrdInvoice> ordInvoiceList = new ArrayList<OrdInvoice>();
	private String receiverId;
	private UsrReceivers usrReceivers = new UsrReceivers();
	//地址信息列表.
	private List<UsrReceivers> usrReceiversList = new ArrayList<UsrReceivers>();
	private OrdPerson ordPerson = new OrdPerson();
	private List<OrdPerson> ordPersonList = new ArrayList<OrdPerson>();
	private String provinceId;
	private List<ComProvince> provinceList;
	private List<ComCity> cityList;
	//结算主体,取值为COMPANY_1,COMPANY_2,COMPANY_3其中之一,参看:com.lvmama.common.utils.InvoiceUtil
	private String companyType;
	//查询条件
	private CompositeQuery.InvoiceRelate invoiceRelate = new CompositeQuery.InvoiceRelate();
	private CompositeQuery.OrderTimeRange orderTimeRange = new CompositeQuery.OrderTimeRange();
	//是否主动显示出收件地址填写表单的DIV.
	private boolean showAddNewCustomAddressDiv = false;
	private Page pageConfig;
	private long currentPage = 1;
	private long pageSize = 10;
	private boolean invoiceResult;
	private Long invoiceId;
		/**
	 * 初始化待开发票的订单列表.
	 * @return
	 */
	@Action("/usr/invoiceApply")
	public String invoiceApply() {
		this.initOrdOrderList();
		return "invoiceApply";
	}
	/**
	 * 发票申请详情.
	 * @return
	 */
	@Action("/usr/invoiceApplyDetail")
	public String invoiceApplyDetail() {
		this.ordInvoice = this.orderServiceProxy.selectOrdInvoiceByPrimaryKey(this.invoiceForm.getInvoiceId());
		if (this.ordInvoice.getDeliveryAddress() == null) {
			this.ordInvoice.setDeliveryAddress(new OrdPerson());
		}
		logger.info(this.ordInvoice.getInvoiceRelationList().size());
		return "invoiceApplyDetail";
	}
	/**
	 * 发票申请列表.
	 * @return
	 */
	@Action("/usr/invoiceApplyList")
	public String invoiceApplyList() {
		this.initInvoiceList();
		return "invoiceApplyList";
	}
	/**
	 * 取消发票申请.
	 * @return
	 */
	@Action("/usr/cancelInvoiceApply")
	public void cancelApply() {
		JSONResult result= new JSONResult();
		try{
			OrdInvoice invoice=orderServiceProxy.selectOrdInvoiceByPrimaryKey(invoiceId);
			if(invoice==null)
				throw new Exception("发票不存在");
			
			if(!StringUtils.equals(invoice.getUserId(),getUserId())){
				throw new Exception("您没有权限操作");
			}
			if(InvoiceUtil.checkUserCancelAble(invoice)){
				throw new Exception("您的发票当前状态您不可以取消");
			}
			InvoiceResult ir=this.orderServiceProxy.update(Constant.INVOICE_STATUS.CANCEL.name(), invoiceId, this.getUserId());
			if(!ir.isSuccess()){
				throw new Exception(ir.getMsg());
			}
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}	
		result.output(getResponse());
	}
	/**
	 * 查询发票申请列表.
	 */
	private void initInvoiceList() {
		//查询条件:申请时间、发票抬头.
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getInvoiceRelate().setUserId(this.getUserId());
		if(orderTimeRange.getCreateInvoiceStart()!=null){
			compositeQuery.getOrderTimeRange().setCreateInvoiceStart(DateUtil.getDayStart(this.orderTimeRange.getCreateInvoiceStart()));
		}
		if(orderTimeRange.getCreateInvoiceEnd()!=null){
			compositeQuery.getOrderTimeRange().setCreateInvoiceEnd(DateUtil.getDayEnd(this.orderTimeRange.getCreateInvoiceEnd()));
		}
		if(StringUtils.isNotEmpty(invoiceRelate.getTitle())){
			compositeQuery.getInvoiceRelate().setTitle(this.invoiceRelate.getTitle());
		}
		Long totalRecords = this.orderServiceProxy.queryOrdInvoiceCount(compositeQuery);
		this.pageConfig = Page.page(totalRecords, pageSize, currentPage);
		compositeQuery.setPageIndex(this.initPageIndex());
		this.ordInvoiceList = this.orderServiceProxy.queryOrdInvoice(compositeQuery);
		this.pageConfig.setItems(this.ordInvoiceList);
		if(pageConfig.getItems().size()>0){
			this.pageConfig.setUrl("/usr/invoiceApplyList.do?currentPage=");
		}
	}
	private String globalResult;//做最后消息失败使用
	/**
	 * 提交保存发票信息.
	 * @return
	 */
	@Action("/usr/saveInvoice")
	public String saveInvoice() {
		try{			
			this.invoiceForm.setCompany(this.companyType);
	//		logger.info("orderIds:" + string2List(this.orderIds));
			List<Long> list=string2List(this.orderIds);
			if(CollectionUtils.isNotEmpty(list)){
				for(Long ord:list){
					OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(ord);
					if(!StringUtils.equals(order.getUserId(), getUserId())){
						throw new Exception("您没有权限为"+ord+" 开出发票");
					}
					
					if(!order.isPaymentSucc()||!(order.isNormal()||order.getOrderStatus().equals(Constant.ORDER_STATUS.FINISHED.name()))||!order.isPayToLvmama()){
						throw new Exception("订单:"+ord+" 现在还不可以开发票");
					}
					
					if(StringUtils.equals("true", order.getNeedInvoice()))
					{
						throw new Exception("订单:"+ord+"发票已经开出或正在开的状态");
					}
				}
			}
			//配送方式如果为自取方式,则不需要初始化地址信息.
			Person person = Constant.DELIVERY_TYPE.SELF.name().equals(this.invoiceForm.getDeliveryType()) ? new Person() : this.initAddress(this.getReceiverId());
			Pair<Invoice,Person> invoice=Pair.make_pair(invoiceForm,person);
			List<Pair<Invoice,Person>> invoices =new ArrayList<Pair<Invoice,Person>>();
			invoices.add(invoice);
			ResultHandle handle = this.orderServiceProxy.insertInvoiceByOrders(invoices, string2List(this.orderIds), this.getUserId());
			invoiceResult = handle.isSuccess();
		}catch(Exception ex){
			globalResult=new String(ex.getMessage());
		}
		return "submitMessage";
	}
	
	
	/**
	 * @return the invoiceResult
	 */
	public boolean isInvoiceResult() {
		return invoiceResult;
	}
	/**
	 * 初始化新用户填写地址.
	 * @return
	 */
	@Action("/usr/newUserAddAdds")
	public String newUserAddAdds() {
		this.initInvoiceAndUsrReceiversList();
		return "newUserAddAdds";
	}
	/**
	 * 老用户填写地址.
	 * @return
	 */
	@Action("/usr/loadReceivers")
	public String oldUserAddAdds() {
		return "oldUserAddAdds";
	}
	/**
	 * 提交完成提示信息.
	 * @return
	 */
	@Action("/usr/submitMessage")
	public String submitMessage() {
		return "submitMessage";
	}
	
	/**
	 * 保存地址.
	 * @return
	 */
	@Action("/usr/confirmAddress")
	public void confirmAddress() {
		JSONResult result=new JSONResult();
		try{
			if(!isLogin()){
				throw new Exception("您没有登录不可以操作");
			}
			this.initUsrReceiversSaveAddress();
			UsrReceivers usr=null;
			if(StringUtils.isEmpty(usrReceivers.getReceiverId())){//为空时表示新加
				usr = new UsrReceivers();				
				UUIDGenerator gen = new UUIDGenerator();
				usr.setReceiverId(gen.generate().toString());
				usr.setUserId(this.getUserId());
				usr.setCreatedDate(new Date());
			}else{
				usr=receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
				if(usr==null){
					throw new Exception("收件信息不存在不可以编辑");
				}
				
				if(!StringUtils.equals(usr.getUserId(),getUserId())){
					throw new Exception("您修改的收件信息错误");
				}
			}
			usr.setAddress(this.usrReceivers.getAddress());
			usr.setMobileNumber(this.usrReceivers.getMobileNumber());
			usr.setReceiverName(this.usrReceivers.getReceiverName());
			usr.setPostCode(this.usrReceivers.getPostCode());			
			usr.setIsValid(UsrReceivers.VALID);			
			usr.setReceiversType(Constant.RECEIVERS_TYPE.ADDRESS.name());
			usr.setProvince(this.usrReceivers.getProvince());
			usr.setCity(this.usrReceivers.getCity());
			//新增地址操作.
			if (StringUtils.isEmpty(this.usrReceivers.getReceiverId())) {
				this.receiverUserService.insert(usr);
			} else {
			//修改地址操作.
				logger.info(this.usrReceivers.getReceiverId());				
				this.receiverUserService.update(usr);
			}
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	/**
	 * 初始化usrReceivers对象的省份(province)与城市(city)属性值,将code码转换为name.
	 */
	private void initUsrReceiversSaveAddress() {
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
		
	}
	@Action("/usr/loadReceiversList")	
	public String loadReceivers(){
		this.initReceiversList();
		return "receivers_list";
	}
	
	/**
	 * 加载收件信息.
	 * @return
	 */
	@Action("/usr/getReceivers")
	public void modifyAddress() {
		JSONResult result=new JSONResult();
		try{
			this.usrReceivers = this.receiverUserService.getRecieverByPk(this.usrReceivers.getReceiverId());
			if(!StringUtils.equals(usrReceivers.getUserId(),getUserId())){
				throw new Exception("没有权限操作");
			}
				
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
			result.put("citys", converCity(cityList));
			result.put("info", JSONObject.fromObject(usrReceivers));
			
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 删除地址.
	 * @return
	 */
	@Action("/usr/removeAddress")
	public void removeAddress() {
		JSONResult result=new JSONResult();
		try{
			if(StringUtils.isEmpty(usrReceivers.getReceiverId())){
				throw new Exception("删除的地址不存在");
			}
			
			UsrReceivers usr=receiverUserService.getRecieverByPk(usrReceivers.getReceiverId());
			if(usr==null){
				throw new Exception("收件信息不存在");
			}
			
			if(StringUtils.isEmpty(getUserId())||!StringUtils.equals(usr.getUserId(), getUserId())){
				throw new Exception("您没有权限删除");
			}
			this.receiverUserService.delete(this.usrReceivers.getReceiverId());
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 初始化发票、地址相关信息.
	 */
	private void initInvoiceAndUsrReceiversList() {
		//logger.info(this.getOrderIds() + ",companyType:" + this.companyType + "invoiceFormTile:" + this.invoiceForm.getTitle() + this.invoiceForm.getDeliveryType());
		this.invoiceForm.setAmount(this.getInvoiceAmount());
		
		initReceiversList();
	}
	
	private void initReceiversList(){
		Map params = new HashMap();
		params.put("userId", getUserId());
		params.put("receiversType", Constant.RECEIVERS_TYPE.ADDRESS.name());
		this.usrReceiversList = receiverUserService.loadRecieverByParams(params);
	}

	/**
	 * 初始化可以申请发票的订单列表.
	 */
	private void initOrdOrderList() {
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderContent orderContent = new OrderContent();
		orderContent.setUserId(getUserId());
		orderContent.setOrderType(InvoiceUtil.getProductTypeStringByCompany(this.companyType == null ? InvoiceUtil.COMPANY_1 : this.companyType));
		orderContent.setNeedInvoice(CompositeQuery.OrderContent.NEED_INVOICE_FALSE);
		
		OrderStatus orderStatus=new OrderStatus();
		orderStatus.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		compositeQuery.getExcludedContent().setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setContent(orderContent);
		
		//游玩时间为现在的三个月前
		Date date=DateUtils.addMonths(new Date(), -3);
		java.util.Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		compositeQuery.getOrderTimeRange().setOrdOrderItemProdVisitTimeStart(c.getTime());
		
		orderContent.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());//添加支付给驴妈
		long totalRecords = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);
		this.pageConfig = Page.page(totalRecords, pageSize, currentPage);
		compositeQuery.setTypeList(Arrays.asList(SortTypeEnum.ORDER_ID_DESC));
		compositeQuery.setPageIndex(this.initPageIndex());
		this.ordOrderList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		this.pageConfig.setItems(this.ordOrderList);
		
		if(pageConfig.getItems().size()>0){
			this.pageConfig.setUrl("/usr/invoiceApply.do?currentPage=");
		}
	}
	/**
	 * 初始化PageIndex对象.
	 * @return 返回初始化后的PageIndex对象.
	 */
	private PageIndex initPageIndex() {
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(new Integer(pageConfig.getStartRows()+""));
		Integer integer = new Integer(pageConfig.getEndRows()+"");
		pageIndex.setEndIndex(integer);
		return pageIndex;
	}
	
	
	/**
	 * 获取选中的订单金额总和.
	 * @return 返回选中的订单金额总和.
	 */
	private long getInvoiceAmount() {
		long result = 0L;
		if (this.getOrderIds() == null) {
			return result;
		}
		String[] orderIdArray = this.orderIds.split(";");
		for (String orderId : orderIdArray) {
			OrdOrder ordOrder = this.orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
			result += ordOrder.getActualPay();
		}
		return result;
	}
	/**
	 * 将以";"分隔的字符串内容放置到一个List列表中.
	 * @param ids  
	 * @return  
	 */
	private List<Long> string2List(String ids) {
		List<Long> result = new ArrayList<Long>();
		if (ids == null || ids.equals("")) {
			return result;
		}
		String[] idArray = ids.split(";");
		for (String id : idArray) {
			Long orderId = Long.valueOf(id);
			if (checkOrderValid(orderId)) {
				result.add(orderId);
			}
		}
		return result;
	}
	
	/**
	 * 检查订单的用户(userId)是否是当前登录用户(userId).
	 * @param orderId 订单ID。
	 * @return 订单用户等于当前用户返回true,其它情况返回false.
	 */
	private boolean checkOrderValid(long orderId) {
		OrdOrder ordOrder = this.orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (ordOrder == null || !ordOrder.getUserId().equals(this.getUserId())) {
			return false;
		}
		return true;
	}
	
	
	private Person initAddress(String id) { 
		UsrReceivers usrReceiver = this.receiverUserService.getRecieverByPk(id); 
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
	
	private JSONArray converCity(List<ComCity> list){
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(ComCity cc:list){
				JSONObject obj=new JSONObject();
				obj.put("cityId", cc.getCityId());
				obj.put("cityName", cc.getCityName());
				array.add(obj);
			}			
		}
		return array;
	}
	/**
	 * 让页面联动，取城市信息
	 */
	@Action("/usr/citys")
	public void getCitys(){
		
		JSONResult result=new JSONResult();
		try{
			List<ComCity> list = placeCityService
					.getCityListByProvinceId(provinceId);
			
			
			result.put("list", converCity(list));
		}catch(Exception ex){
			
		}
		
		result.output(getResponse());
	}
	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
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
	
	public OrdPerson getOrdPerson() {
		return ordPerson;
	}
	public void setOrdPerson(OrdPerson ordPerson) {
		this.ordPerson = ordPerson;
	}
	public CompositeQuery.InvoiceRelate getInvoiceRelate() {
		return invoiceRelate;
	}
	public void setInvoiceRelate(CompositeQuery.InvoiceRelate invoiceRelate) {
		this.invoiceRelate = invoiceRelate;
	}
	public String getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	public Page getPageConfig() {
		return pageConfig;
	}
	public OrdInvoice getOrdInvoice() {
		return ordInvoice;
	}
	public void setOrdInvoice(OrdInvoice ordInvoice) {
		this.ordInvoice = ordInvoice;
	}
	public void setPageConfig(Page pageConfig) {
		this.pageConfig = pageConfig;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public Invoice getInvoiceForm() {
		return invoiceForm;
	}
	public long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
	public void setInvoiceForm(Invoice invoiceForm) {
		this.invoiceForm = invoiceForm;
	}
	
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public List<ComProvince> getProvinceList() {
		return placeCityService.getProvinceList();
	}
	public void setProvinceList(List<ComProvince> provinceList) {
		this.provinceList = provinceList;
	}
	public List<ComCity> getCityList() {
		return cityList;
	}
	public void setCityList(List<ComCity> cityList) {
		this.cityList = cityList;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public CompositeQuery.OrderTimeRange getOrderTimeRange() {
		return orderTimeRange;
	}
	public void setOrderTimeRange(CompositeQuery.OrderTimeRange orderTimeRange) {
		this.orderTimeRange = orderTimeRange;
	}
	public List<OrdInvoice> getOrdInvoiceList() {
		return ordInvoiceList;
	}
	public void setOrdInvoiceList(List<OrdInvoice> ordInvoiceList) {
		this.ordInvoiceList = ordInvoiceList;
	}
	/**
	 * 当前用户是否存在已有地址.
	 * @return
	 */
	public boolean isOldUsrReceivers() {
		return this.usrReceiversList != null && !this.usrReceiversList.isEmpty();
	}
	
	public boolean isShowAddNewCustomAddressDiv() {
		return showAddNewCustomAddressDiv;
	}
	public void setShowAddNewCustomAddressDiv(boolean showAddNewCustomAddressDiv) {
		this.showAddNewCustomAddressDiv = showAddNewCustomAddressDiv;
	}
	
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
	public List<OrdPerson> getOrdPersonList() {
		return ordPersonList;
	}
	public void setOrdPersonList(List<OrdPerson> ordPersonList) {
		this.ordPersonList = ordPersonList;
	}
	/**
	 * @return the globalResult
	 */
	public String getGlobalResult() {
		return globalResult;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}
	
	
}