package com.lvmama.back.web.ord.eContract;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import com.lvmama.back.utils.Pagination;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdEcontractSignLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.EContractRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.ExcludedContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.OrderEcontractModel;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.comm.utils.ord.TemplateFillDataUtil;
import com.lvmama.comm.vo.Constant;

public class ListOrdEContractAction extends BaseAction {
	private static final long serialVersionUID = -4295957332060668419L;
	private Log logger=LogFactory.getLog(ListOrdEContractAction.class);
	
	private OrderService orderServiceProxy = (OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private OrdEContractService ordEContractService;
	private Map<String,Object> searchConds = new HashMap<String,Object>();
	
	private List<OrderEcontractModel> orderEcontractsList=new ArrayList<OrderEcontractModel>();
	private Long totalRowCount;
	protected Pagination pagination;
	private transient TopicMessageProducer orderMessageProducer;
	private ComLogService comLogService;
	private EContractClient contractClient = (EContractClient)SpringBeanProxy.getBean("contractClient");
	private ProdProductService prodProductService;
	private FSClient fsClient=(FSClient)SpringBeanProxy.getBean("fsClient");
	
	
	private List<CodeItem> statusList =new ArrayList<CodeItem>();
	
	private Long productId;
	protected void doBefore() throws Exception {
		setSignModeList();
	}
	public List<OrderEcontractModel> getOrderEcontractsList() {
		return orderEcontractsList;
	}
	/**
	 * 固定条件：要签约，已支付，不为废单的订单
	 */
	public void search(){
		orderEcontractsList.clear();
		//1.创建订单查询类
		CompositeQuery compositeQuery = new CompositeQuery();
		//设置支付状态为已支付  2012-07-27 szy:签约前置后，当订单生成后就生成合同，所以不要已支付条件
		String status=(String)searchConds.get("eContractStatus");
		if("PAYED".equals(status)){
			OrderStatus orderStatusForQuery = new OrderStatus();
			orderStatusForQuery.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
			orderStatusForQuery.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
			compositeQuery.setStatus(orderStatusForQuery);
			//设置订单状态不为废单
			ExcludedContent excludedContent=new ExcludedContent();
			excludedContent.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			compositeQuery.setExcludedContent(excludedContent);
		}else if("CANCEL".equals(status)){
			OrderStatus orderStatusForQuery = new OrderStatus();
			orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			compositeQuery.setStatus(orderStatusForQuery);
		}else if("CONFIRM".equals(status)){
			//设置订单状态不为废单
			ExcludedContent excludedContent=new ExcludedContent();
			excludedContent.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			excludedContent.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
			compositeQuery.setExcludedContent(excludedContent);
		}
		//2.设置订单查询ID
		compositeQuery.setOrderIdentity(getOrderIdentity());
		//3.设置游玩时间段
		compositeQuery.setOrderTimeRange(getOrderTimeRange());
		//4.设置订单合同状态
		compositeQuery.setEContractRelate(getEcontractStatus());		
		OrderContent orderContent = new OrderContent();
		orderContent.setOrderType(new String[]{Constant.ORDER_TYPE.SELFHELP_BUS.name(),
				Constant.ORDER_TYPE.FREENESS_FOREIGN.name(),Constant.ORDER_TYPE.FREENESS_LONG.name(),
				Constant.ORDER_TYPE.GROUP.name(),Constant.ORDER_TYPE.GROUP_FOREIGN.name(),
				Constant.ORDER_TYPE.GROUP_LONG.name(),Constant.ORDER_TYPE.FREENESS.name()});
		
		compositeQuery.setContent(orderContent);
		//5.根据上面查询条件得到订单总数
		totalRowCount= orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);
		//6.设置要查询的起止行
		compositeQuery.setPageIndex(getPageIndex());
		//7.根据查询条件查询订单列表
		List<OrdOrder> ordersList = orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
		//8.根据合同编码列表取得签约方式
		Map<String,Object> parameters = new HashMap<String,Object>();
		List<String> ids = new ArrayList<String>();
		for(int i=0;i<ordersList.size();i++){
			OrdOrder order=ordersList.get(i);
			if(order.hasSelfPack()){
				ordersList.remove(order);
				i--;
				break;
			}
			OrderEcontractModel model=new OrderEcontractModel();
			model.setId(i+1L);
			ids.add(""+order.getOrderId());
			model.setOrdOrder(order);
			model.setContact(order.getContact());
			orderEcontractsList.add(model);
		}
		if(null!=ids && !ids.isEmpty()){
			parameters.put("orderIds", ids);
			List<OrdEContract> ordEContractList =ordEContractService.queryOrdEContract(parameters);
			ids.clear();
			for(OrderEcontractModel model:orderEcontractsList){
				for(OrdEContract ordEcontract:ordEContractList){
					if(model.getOrdOrder().getOrderId().equals(ordEcontract.getOrderId())){
						model.setOrdEContract(ordEcontract);
						ids.add(ordEcontract.getEcontractNo());
					}
				}
			}
			parameters.put("econtractNoList", ids);
			List<OrdEcontractSignLog> signLogList = ordEContractService.queryEcontractSignLog(parameters);
			for(OrderEcontractModel model:orderEcontractsList){
				for(OrdEcontractSignLog signLog:signLogList){
					if(StringUtil.isEmptyString(model.getSignMode()) && null != model.getOrdEContract() && model.getOrdEContract().getEcontractNo().equals(signLog.getEcontractNo())){
						model.setSignMode(signLog.getSignMode());
					}
				}
			}
		}
	}
	public void sendRedirect(String orderId){
		Executions.sendRedirect("/ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId="+orderId);
	}
	public void sendContractEmail(Long orderId) throws InterruptedException {
			try{
				orderMessageProducer.sendMsg(MessageFactory.newOrderSendEContract (orderId));
				Messagebox.show("重发合同成功", "信息", Messagebox.OK, Messagebox.INFORMATION);
			}catch(Exception e){
				logger.error("重发合同失败:" +e);
				Messagebox.show("重发合同失败", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			}
	}
	/**
	 * 下载合同
	 * @param orderId
	 * @throws Exception
	 */
	public void viewPdfContractDetail(final Long orderId) throws Exception{
		OrdEContract ordEcontract = ordEContractService.getOrdEContractByOrderId(orderId);
		ComFile comFile = fsClient.downloadFile(ordEcontract.getFixedFileId());
		downFile(orderId,"Contract",EcontractUtil.createOutPutContractPdf(new String(comFile.getFileData(),"UTF-8")));
	}
	public void viewAdditionDetail(final Long orderId) throws Exception{	
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		String complementTemplate = EcontractUtil.getComplementTemplate(order);
		String complement = ordEContractService.getContractComplement(orderId,complementTemplate);
		downFile(orderId,"ContractComplement",EcontractUtil.createOutPutContractPdf(complement));	
	}
	public void viewOrderTravel(final Long orderId,final Long productId) throws Exception{
		try{
			String travel =contractClient.loadRouteTravel(orderId);
			String destFileName = Constant.getTempDir() + "/"+orderId+"_Travel.html";
			File downFile = StringUtil.getFileFromBytes(travel.getBytes("UTF-8"),destFileName);
			if (downFile != null && downFile.exists()) {
				Filedownload.save(downFile, "application/html");
			} else {
				alert(orderId+"_Travel.html下载失败");
				return;
			}
			alert(orderId+"_Travel.html下载成功");	
		}catch(Exception e){
			logger.warn(orderId+"_Travel.html下载失败\r\n"+e);
			alert(orderId+"_Travel.html下载失败");	
		}
	}
	
	public void setContractStatus(String value){
		if(StringUtil.isEmptyString(value)){
			searchConds.put("eContractStatus",null);
		}
		searchConds.put("eContractStatus",value);
	}
	public void setSignModeList(){
		CodeItem codeItem = new CodeItem("","请选择");
		CodeItem UNCONFIRMED = new CodeItem("UNCONFIRMED","未签约");
		CodeItem CONFIRM = new CodeItem("CONFIRM","已签约未生效");
		CodeItem PAYED = new CodeItem("PAYED","已生效");
		CodeItem CANCEL = new CodeItem("CANCEL","已作废");
		statusList.clear();
		statusList.add(codeItem);
		statusList.add(UNCONFIRMED);
		statusList.add(CONFIRM);
		statusList.add(PAYED);
		statusList.add(CANCEL);
	}
	private void downFile(final Long orderId,final String subject,final ByteArrayOutputStream bs){
		try{
			String destFileName = Constant.getTempDir() + "/"+orderId+"_"+subject+".pdf";
			File downFile = StringUtil.getFileFromBytes(bs.toByteArray(),destFileName);
			if (downFile != null && downFile.exists()) {
				Filedownload.save(downFile, "application/pdf");
			} else {
				alert(subject+"下载失败");
				return;
			}
			alert(subject+"下载成功");	
		}catch(Exception e){
			logger.warn(subject+"下载失败\r\n"+e);
			alert(subject+"下载失败");	
		}
	}
	private OrderIdentity getOrderIdentity(){
		String orderId=(String)searchConds.get("orderId");
		OrderIdentity orderIdentity = new OrderIdentity();
		if (orderId != null && !"".equals(orderId.trim())) {
			try {
				orderIdentity.setOrderId(Long.parseLong(orderId.trim()));
			} catch (Exception e) {
			}
		}
		return orderIdentity;
	}
	private OrderTimeRange getOrderTimeRange(){
		Date visitTimeStart=(Date)searchConds.get("visitTimeStart");
		Date visitTimeEnd=(Date)searchConds.get("visitTimeEnd");
		OrderTimeRange orderTimeRange = new OrderTimeRange();
		Calendar c = Calendar.getInstance();
		if (visitTimeStart != null && !("".equals(visitTimeStart))) {
			orderTimeRange.setOrdOrderVisitTimeStart(visitTimeStart);
		}
		if (visitTimeEnd != null && !("".equals(visitTimeEnd))) {
			c.setTime(visitTimeEnd);
			c.add(Calendar.DATE, 1); // 因取到的结束日期为当天的0:00:00，故查询时需往后延一天
			orderTimeRange.setOrdOrderVisitTimeEnd(c.getTime());

		}
		return orderTimeRange;
	}
	private EContractRelate getEcontractStatus(){
		String status=(String)searchConds.get("eContractStatus");
		if("PAYED".equals(status) || "CANCEL".equals(status)){
			status = Constant.ECONTRACT_STATUS.CONFIRM.name();
		}
		EContractRelate eContractRelate=new EContractRelate();
		if(!StringUtil.isEmptyString(status)){
			eContractRelate.setNeedEContract(Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name());
			if(null!=status && !"".equals(status.trim())){
				eContractRelate.setEContractStatus(status);
			}
		}
		return eContractRelate;
	}
	private PageIndex getPageIndex(){
		Integer begin = _paging.getActivePage()*_paging.getPageSize()+1;
		Integer end   = _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize();
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		_paging.setTotalSize(totalRowCount.intValue());
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(begin);
		pageIndex.setEndIndex(end);
		return pageIndex;
	}
	
	public String getSendStatus(String email){
		if(StringUtil.isEmptyString(email)){
			return "未发送";
		}else{
			return "已发送";
		}
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public Map<String,Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String,Object> searchConds) {
		this.searchConds = searchConds;
	}
	public OrdEContractService getOrdEContractService() {
		return ordEContractService;
	}
	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
	public ComLogService getComLogService() {
		return comLogService;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
 
	public List<CodeItem> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<CodeItem> statusList) {
		this.statusList = statusList;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ProdProductService getProdProductService() {
		return prodProductService;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public void setOrderEcontractsList(List<OrderEcontractModel> orderEcontractsList) {
		this.orderEcontractsList = orderEcontractsList;
	}
	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}
	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}
	
}
