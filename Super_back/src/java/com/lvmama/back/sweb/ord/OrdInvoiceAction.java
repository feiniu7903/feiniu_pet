package com.lvmama.back.sweb.ord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.InvoiceResult;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;

@ParentPackage("json-default")
@Results( {
		@Result(name = "ord_invoice", location = "/WEB-INF/pages/back/ord/ord_invoice.jsp"),
		@Result(name = "insert_invoice", location = "/WEB-INF/pages/back/ord/insert_invoice.jsp") })
/**
 * 发票操作类
 * 
 * @author shihui
 */
public class OrdInvoiceAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2885074375243136088L;
	/**
	 * 发票对象
	 */
	private OrdInvoice ordInvoice;
	/**
	 * 发票内容
	 */
	private List<CodeItem> invoiceDetails=new ArrayList<CodeItem>();
	/**
	 * 发票列表
	 */
	private List<OrdInvoice> invoiceList;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 订单号
	 */
	private Long orderId;
	/**
	 * 能否修改收件地址
	 */
	private String editable;
	
	
	private Long invoiceId;//发票ID
	/**
	 * 加载发票列表
	 */
	@Action("/ord/loadInvoices")
	public String loadInvoices() {
		// 后台下单
		if (orderId == null || orderId == 0) {
			Object obj = this.getSession().getAttribute("INVOICELIST");
			if (obj != null) {
				this.invoiceList = (List<OrdInvoice>) obj;
			} else {
				this.invoiceList = new ArrayList<OrdInvoice>();
			}
		} else {// 订单审核
			this.invoiceList = orderServiceProxy.queryInvoiceByOrderId(orderId);
		}
		return "ord_invoice";
	}
	
 
	/**
	 * 修改发票 
	 * 
	 */
	@Deprecated
	@Action("/ord/updateInvoice")
	public void updateInvoice() {
		JSONResult result=new JSONResult();
		try {
			Long invId = this.ordInvoice.getInvoiceId();
			// 后台下单
			if (invId < 0) {
				this.invoiceList = (List<OrdInvoice>) this.getSession()
						.getAttribute("INVOICELIST");
				for (Iterator<OrdInvoice> iterator = invoiceList.iterator(); iterator
						.hasNext();) {
					OrdInvoice ordInv = iterator.next();
					if (ordInv.getInvoiceId().equals(invId)) {
						invoiceList.remove(ordInv);
						invoiceList.add(this.ordInvoice);
						break;
					}
				}
				this.getSession().setAttribute("INVOICELIST", invoiceList);
				
			} else {// 订单审核
				OrdInvoice inv = orderServiceProxy
						.selectOrdInvoiceByPrimaryKey(this.ordInvoice
								.getInvoiceId());
				inv.setTitle(this.ordInvoice.getTitle());
				inv.setDetail(this.ordInvoice.getDetail());
//				inv.setAmount(this.ordInvoice.getAmount());
				inv.setMemo(this.ordInvoice.getMemo());
				boolean flag = orderServiceProxy.updateOrdInvoice(inv,
						getOperatorName());
				if(!flag){
					throw new Exception("更新发票操作失败");
				}
			}
		} catch (Exception e) {
			result.raise(new JSONResultException(e.getMessage()));
		}
		
		result.output(getResponse());
	}

	/**
	 * 删除发票信息
	 */
	@Action("/ord/doDeleteInvoice")
	public void doDeleteInvoice() {
		JSONResult result=new JSONResult();
		try {
			Long invId = this.ordInvoice.getInvoiceId();
			// 后台下单
			if (invId < 0) {
				this.invoiceList = (List<OrdInvoice>) this.getSession()
						.getAttribute("INVOICELIST");
				for (Iterator<OrdInvoice> iterator = invoiceList.iterator(); iterator
						.hasNext();) {
					OrdInvoice ordInv = iterator.next();
					if (ordInv.getInvoiceId().equals(invId)) {
						invoiceList.remove(ordInv);
						break;
					}
				}
				this.getSession().setAttribute("INVOICELIST", invoiceList);
			} else {// 订单审核
				throw new Exception("发票不可以删除，只可以取消");
				//boolean flag = orderServiceProxy.delete(this.ordInvoice
				//		.getInvoiceId(), getOperatorName());
				//returnMessage(flag);
			}
		} catch (Exception e) {
			result.raise(new JSONResultException(e.getMessage()));
		}
		result.output(getResponse());
	}
	@Action("/ord/doCancelInvoice")
	public void doCancelInvoice(){
		JSONResult result=new JSONResult();
		try{
			Long invId = this.ordInvoice.getInvoiceId();
			if(invId<1){
				throw new Exception("发票ID不存在");
			}
			OrdInvoice invoice=orderServiceProxy.selectOrdInvoiceByPrimaryKey(invId);
			if(invoice==null){
				throw new Exception("发票不存在");
			}
			if(!Constant.INVOICE_STATUS.UNBILL.name().equals(invoice.getStatus())){
			   throw new Exception("发票当前状态不可以直接取消，联系财务开票人员");
		    }
			
			InvoiceResult ir=null;		
	        ir=orderServiceProxy.update(Constant.INVOICE_STATUS.CANCEL.name(), invId, getOperatorName());
		
			if(ir.isError()){
				throw new Exception(ir.getMsg());
			}	
		}catch(Exception ex){
			result.raise(ex);
		}
		
		result.output(getResponse());
	}
	
	/**
	 * 发票申请红冲操作.
	 */
	@Action("/ord/doReqRedInvoice")
	public void dochangeRedStatus(){
		JSONResult result=new JSONResult();
		try{		
			Long invId = this.ordInvoice.getInvoiceId();
			if(invId<1){
				throw new Exception("发票ID不存在");
			}
			OrdInvoice invoice=orderServiceProxy.selectOrdInvoiceByPrimaryKey(invId);
			if(invoice==null){
				throw new Exception("发票不存在");
			}
		
			String redFlag="true";
	        InvoiceResult ir = orderServiceProxy.updateRedFlag(ordInvoice.getInvoiceId(),redFlag,getOperatorNameAndCheck());						
	        if(ir.isError()){
	        	throw new Exception(ir.getMsg());
	        }
		}catch(Exception ex){
			result.raise(ex);
		}
		
		result.output(getResponse());
	}


	


	public void setOrdInvoice(OrdInvoice ordInvoice) {
		this.ordInvoice = ordInvoice;
	}

	public OrdInvoice getOrdInvoice() {
		return ordInvoice;
	}

	public List<CodeItem> getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<OrdInvoice> getInvoiceList() {
		return invoiceList;
	}

	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(String editable) {
		this.editable = editable;
	}
	
	public boolean isEditable(){
		return StringUtils.equals("true", editable);
	}
	
	public List<CodeItem> getDeliveryTypeList(){
		return CodeSet.getInstance().getCodeList("DELIVERY_TYPE");
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}
}