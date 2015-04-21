/**
 * 
 */
package com.lvmama.back.sweb.invoice;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.InvoiceRelate;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 发票的列表页.
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
@Results({
	@Result(name="success",location="/WEB-INF/pages/back/ord/invoice/invoice_list.jsp"),
	@Result(name="redList",location="/WEB-INF/pages/back/ord/invoice/redInvoice_list.jsp")
})
public class InvoiceListAction extends BaseInvoiceAction {
	
	private String orderId;//订单号
	private String invoiceNo;//发票号
	private String deliveryType;//送货类型
	private String settlementCommpany;//结算主体
	private String orderUserId;//订票人
	private String status;//发票状态
	private Date startTime;
	private Date endTime;
	private Date billStartTime;
	private Date billEndTime;
	private String filialeName;//订单所属公司
	private String invoiceId;//发票ID
	private String invoiceLogistics;
	
	
	private List<OrdRefundment> listOrdRefundment;
	
	
	@Action("/ord/goInvoiceList")
	public String execute(){
		return SUCCESS;
	}
	
	@Action("/ord/goRedInvoiceList")
	public String executeRed(){
		return "redList";
	}
	
	@Action("/ord/invoiceList")
	public String loadDataList(){
		CompositeQuery compositeQuery=initCompositeQuery();		
		long count=orderServiceProxy.queryOrdInvoiceCount(compositeQuery);
		
		initPagination();
		pagination.setTotalRecords(count);
		
		PageIndex pageIndex=new PageIndex();
		pageIndex.setBeginIndex(pagination.getFirstRow());
		pageIndex.setEndIndex(pagination.getLastRow());
		compositeQuery.setPageIndex(pageIndex);
		compositeQuery.getInvoiceRelate().setFindAddress(true);		
		compositeQuery.getInvoiceRelate().setFindOrder(true);
		
		if(count>0){
			List<OrdInvoice> list = orderServiceProxy.queryOrdInvoice(compositeQuery);
			for(OrdInvoice invoice:list){
				for(OrdOrder order:invoice.getOrderList()){
					order.setOrderPay(order.getActualPay()-orderServiceProxy.unableInvoiceAmountByOrderId(order.getOrderId()));
				}				
			}			
			pagination.setRecords(list);						
		}
		
		
		
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));		
		return SUCCESS;
	}
	
	
	@Action("/ord/redInvoiceList")
	public String loadRedDataList(){
		CompositeQuery compositeQuery=new CompositeQuery();		
		if(StringUtils.isNotEmpty(invoiceNo)){
			compositeQuery.getInvoiceRelate().setInvoiceNo(invoiceNo);
		}
		if(StringUtils.isNotEmpty(orderId)){
			compositeQuery.getOrderIdentity().setOrderId(NumberUtils.toLong(orderId));
		}
		if(StringUtils.isNotEmpty(invoiceId)){
			compositeQuery.getInvoiceRelate().setInvoiceId(NumberUtils.toLong(invoiceId));
		}
		compositeQuery.getInvoiceRelate().setRedFlag("true");
		long count=orderServiceProxy.queryOrdInvoiceCount(compositeQuery);
		
		initPagination();
		pagination.setTotalRecords(count);
		
		PageIndex pageIndex=new PageIndex();
		pageIndex.setBeginIndex(pagination.getFirstRow());
		pageIndex.setEndIndex(pagination.getLastRow());
		compositeQuery.setPageIndex(pageIndex);
		compositeQuery.getInvoiceRelate().setFindAddress(true);		
		compositeQuery.getInvoiceRelate().setFindOrder(true);
		compositeQuery.getInvoiceRelate().setFindOrderInvoiceAmount(true);
		if(count>0){
			List<OrdInvoice> list = orderServiceProxy.queryOrdInvoice(compositeQuery);
			pagination.setRecords(list);	
		}
		
		
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));		
		return "redList";
	}

	@Action("/ord/export/invoiceData")
	public void doOutputData(){
		CompositeQuery compositeQuery=initCompositeQuery();
		compositeQuery.getInvoiceRelate().setFindAddress(true);
		compositeQuery.getInvoiceRelate().setFindOrderRelation(true);
		compositeQuery.getInvoiceRelate().setFindOrder(true);
		compositeQuery.getInvoiceRelate().setFindOrderInvoiceAmount(true);
		PageIndex pageIndex=new PageIndex();
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(100000);
		compositeQuery.setPageIndex(pageIndex);
		List<OrdInvoice> list=orderServiceProxy.queryOrdInvoice(compositeQuery);		
		output(list, "/WEB-INF/resources/template/invoiceTemplate.xls");
	}
	
	@Action("/ord/export/invoiceAddress")
	public void doOutputAddress(){
		CompositeQuery compositeQuery=initCompositeQuery();
		compositeQuery.getInvoiceRelate().setFindAddress(true);
		PageIndex pageIndex=new PageIndex();
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(100000);
		compositeQuery.setPageIndex(pageIndex);
		List<OrdInvoice> list=orderServiceProxy.queryOrdInvoice(compositeQuery);
		if(CollectionUtils.isNotEmpty(list)){
			for(Iterator<OrdInvoice> it=list.iterator();it.hasNext();){
				OrdInvoice invoice=it.next();
				if(!invoice.hasNotNullDelivery()){//如果地址不存在直接删除
					it.remove();
				}
			}
		}
		output(list, "/WEB-INF/resources/template/invoiceAddressTemplate.xls");
	}
	
	private void output(List<OrdInvoice> list,String template){
		FileInputStream fin=null;
		OutputStream os=null;
		try
		{
			File templateResource = ResourceUtil.getResourceFile(template);
			Map beans = new HashMap();
			beans.put("invoiceList", list);
			XLSTransformer transformer = new XLSTransformer();
			File destFileName = new File(Constant.getTempDir() + "/excel"+new Date().getTime()+".xls");
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName.getAbsolutePath());
					
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + destFileName.getName());
			os=getResponse().getOutputStream();
			fin=new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}
	
	/**
	 * 初始化compositeQuery
	 * @return
	 */
	private CompositeQuery initCompositeQuery(){
		CompositeQuery compositeQuery =new CompositeQuery();
		OrderTimeRange orderTimeRange=new OrderTimeRange();
		OrderContent orderContent=new OrderContent();
		InvoiceRelate ir=new InvoiceRelate();
		if(StringUtils.isNotEmpty(orderId)){
			ir.setOrderId(NumberUtils.toLong(orderId.trim()));
		}
		if(StringUtils.isNotEmpty(invoiceNo)){
			ir.setInvoiceNo(invoiceNo.trim());
		}
		if(StringUtils.isNotEmpty(orderUserId)){
			ir.setUserId(orderUserId);
		}
		if(StringUtils.isNotEmpty(settlementCommpany)){
			ir.setCompanyId(settlementCommpany);
		}
		if(StringUtils.isNotEmpty(deliveryType)){
			ir.setDeliveryType(deliveryType);			
		}
		if(StringUtils.isNotEmpty(status)){
			ir.setStatus(status);
		}
		if(StringUtils.isNotEmpty(invoiceId)){
			ir.setInvoiceId(NumberUtils.toLong(invoiceId.trim()));
		}
		if(StringUtils.isNotEmpty(invoiceLogistics)){
			ir.setLogisticsStatus(invoiceLogistics);
		}
		if(StringUtils.isNotEmpty(filialeName)){
			orderContent.setFilialeName(filialeName);
		}
		boolean flag=false;
		if(startTime!=null&&endTime!=null){
			if(startTime.before(endTime)||startTime.equals(endTime)){
				orderTimeRange.setCreateInvoiceStart(DateUtil.getDayStart(startTime));
				orderTimeRange.setCreateInvoiceEnd(DateUtil.getDayEnd(endTime));
				flag=true;
			}
		}
		if(!flag){
			if(startTime!=null){
				orderTimeRange.setCreateInvoiceStart(DateUtil.getDayStart(startTime));
			}else if(endTime!=null){
				orderTimeRange.setCreateInvoiceEnd(DateUtil.getDayEnd(endTime));
			}
		}
		flag=false;
		if(billStartTime!=null&&billEndTime!=null){
			if(billStartTime.before(billEndTime)||billStartTime.equals(billEndTime)){
				orderTimeRange.setBillInvoiceStart(DateUtil.getDayStart(billStartTime));
				orderTimeRange.setBillInvoiceEnd(DateUtil.getDayEnd(billEndTime));
				flag=true;
			}
		}
		if(!flag){
			if(billStartTime!=null){
				orderTimeRange.setBillInvoiceStart(DateUtil.getDayStart(billStartTime));
			}else if(billEndTime!=null){
				orderTimeRange.setBillInvoiceEnd(DateUtil.getDayEnd(billEndTime));
			}
		}
		//必填参数
		orderTimeRange.setOrdOrderItemProdVisitTimeEnd(DateUtil.getDayEnd(new Date()));//必须为游玩时间在今天之前
		OrderStatus orderStatus=new OrderStatus();		
		orderStatus.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		compositeQuery.setInvoiceRelate(ir);
		compositeQuery.setContent(orderContent);
		compositeQuery.getExcludedContent().setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
		
		return compositeQuery;
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
	 * @return the invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}
	/**
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	/**
	 * @return the deliveryType
	 */
	public String getDeliveryType() {
		return deliveryType;
	}
	/**
	 * @param deliveryType the deliveryType to set
	 */
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	/**
	 * @return the settlementCommpany
	 */
	public String getSettlementCommpany() {
		return settlementCommpany;
	}
	/**
	 * @param settlementCommpany the settlementCommpany to set
	 */
	public void setSettlementCommpany(String settlementCommpany) {
		this.settlementCommpany = settlementCommpany;
	}
	
	/**
	 * @return the orderUserId
	 */
	public String getOrderUserId() {
		return orderUserId;
	}
	/**
	 * @param orderUserId the orderUserId to set
	 */
	public void setOrderUserId(String orderUserId) {
		this.orderUserId = orderUserId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 结算主体
	 * @return
	 */
	public List<CodeItem> getSettlementCompanyList(){
		return CodeSet.getInstance().getCodeListAndBlank("SETTLEMENT_COMPANY");
	}
	
	/**
	 * 送货类型
	 * @return
	 */
	public List<CodeItem> getDeliveryTypeList(){
		return CodeSet.getInstance().getCodeListAndBlank("DELIVERY_TYPE");
	}
	
	public List<CodeItem> getInvoiceStatusList(){
		return CodeSet.getInstance().getCodeListAndBlank("INVOICE_STATUS");
	}
	
	public List<CodeItem> getFilialeNameList(){
		return CodeSet.getInstance().getCodeListAndBlank(Constant.CODE_TYPE.FILIALE_NAME.name());
	}
	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId() {
		return invoiceId;
	}
	public List<OrdRefundment> getListOrdRefundment() {
		return listOrdRefundment;
	}
	public void setListOrdRefundment(List<OrdRefundment> listOrdRefundment) {
		this.listOrdRefundment = listOrdRefundment;
	}
	
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return the billStartTime
	 */
	public Date getBillStartTime() {
		return billStartTime;
	}
	/**
	 * @param billStartTime the billStartTime to set
	 */
	public void setBillStartTime(Date billStartTime) {
		this.billStartTime = billStartTime;
	}
	/**
	 * @return the billEndTime
	 */
	public Date getBillEndTime() {
		return billEndTime;
	}
	/**
	 * @param billEndTime the billEndTime to set
	 */
	public void setBillEndTime(Date billEndTime) {
		this.billEndTime = billEndTime;
	}
	/**
	 * @return the filialeName
	 */
	public String getFilialeName() {
		return filialeName;
	}
	/**
	 * @param filialeName the filialeName to set
	 */
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}	
	
	public List<CodeItem> getLogisticsList(){
		return CodeSet.getInstance().getCodeListAndBlank("INVOICE_LOGISTICS");
	}

	public String getInvoiceLogistics() {
		return invoiceLogistics;
	}

	public void setInvoiceLogistics(String invoiceLogistics) {
		this.invoiceLogistics = invoiceLogistics;
	}
	
}
