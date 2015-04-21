package com.lvmama.back.sweb.invoice;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.vo.InvoiceResult;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.InvoiceUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;

/**
 * 单个发票相关的操作,包含审核，变更其状态
 * @author yangbin
 *
 */
@Results({
	@Result(name="invoice_detail",location="/WEB-INF/pages/back/ord/invoice/invoice_detail.jsp")
})
public class InvoiceDetailAction extends BaseInvoiceAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2715306442400778370L;
	private Long invoiceId;
	/**
	 * 发票单号
	 */
	private String invoiceNo;
	private String expressNo;
	private OrdInvoice ordInvoice;
	private String status;
	private ComLogService comLogService;
	private List<ComLog> comLogList;
	

	@Action("/ord/invoiceDetail")
	public String doDetail(){		
		ordInvoice = orderServiceProxy.selectOrdInvoiceByPrimaryKey(invoiceId);		
		comLogList=comLogService.queryByObjectId("ORD_INVOICE", invoiceId);
		return "invoice_detail";
	}

	/**
	 * 发票状态操作.
	 */
	@Action("/ord/invoiceChangeStatus")
	public void doChangeStatus(){
		JSONResult result=new JSONResult();
		try{
			Assert.hasLength(status, "变更的状态不可以为空");
			doBefore();
			
			if(status.equals(ordInvoice.getStatus())){
				throw new Exception("准备变更的状态与实际的状态是一样的");
			}
			InvoiceResult ir=orderServiceProxy.update(status,ordInvoice.getInvoiceId(), getOperatorNameAndCheck());
			if(ir.isError()){
				throw new Exception(ir.getMsg());
			}else if(ir.isCancel()){
				throw new JSONResultException(-2,ir.getMsg());
			}else{
				//该数据给后面取其当前的值使用			
				ordInvoice.setStatus(status);			
			}
			setResult(result);
		}catch(JSONResultException ex){
			result.raise(ex);
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		
		result.output(getResponse());
	}	
	

	/**
	 * 发票关闭红冲操作.
	 */
	@Action("/ord/doCloseRedInvoice")
	public void doCloseRedStatus(){
		JSONResult result=new JSONResult();
		try{		
			Long invId = this.invoiceId;
			if(invId<1){
				throw new Exception("发票ID不存在");
			}
			OrdInvoice invoice=orderServiceProxy.selectOrdInvoiceByPrimaryKey(invId);
			if(invoice==null){
				throw new Exception("发票不存在");
			}
			
			String redFlag="false";
	        InvoiceResult ir = orderServiceProxy.updateRedFlag(invId,redFlag,getOperatorNameAndCheck());						
	        if(ir.isError()){
	        	throw new Exception(ir.getMsg());
	        }
		}catch(Exception ex){
			result.raise(ex);
		}
		
		result.output(getResponse());
	}
	
	@Action("/ord/updateInvoiceExpress")
	public void doChangeExpress(){
		JSONResult result=new JSONResult();
		try{
			doBefore();
			if(InvoiceUtil.checkChangeExpressNo(ordInvoice)){
				throw new Exception("只有已经开票的状态才可以添加快递单号");
			}
			
			if(!orderServiceProxy.updateInvoiceExpressNo(invoiceId,expressNo,getOperatorNameAndCheck())){
				throw new Exception("操作未成功");
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		
		result.output(getResponse());
	}
	
	
	@Action("/ord/updateInvoiceNo")	
	public void doChangeNo(){
		JSONResult result=new JSONResult();
		try{
			doBefore();
			if(!isShowInvoiceForm()){
				throw new Exception("当前状态不可以再变更发票号");
			}
			
			if(!orderServiceProxy.updateInvoiceNo(invoiceId, invoiceNo, getOperatorNameAndCheck())){
				throw new Exception("操作未成功");
			}
			
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		
		result.output(getResponse());
	}	
	/**
	 * 传回操作状态值
	 * @param result
	 */
	private void setResult(JSONResult result){
		result.put("zhStatus", ordInvoice.getZhStatus());
		result.put("status", ordInvoice.getStatus());
	}
	
	/**
	 * 前处理.
	 */
	private void doBefore(){
		Assert.isTrue(!(invoiceId==null||invoiceId<1),"发票序号不正确");
		
		ordInvoice=orderServiceProxy.selectOrdInvoiceByPrimaryKey(invoiceId);
		Assert.notNull(ordInvoice,"发票不存在");			
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	/**
	 * @return the ordInvoice
	 */
	public OrdInvoice getOrdInvoice() {
		return ordInvoice;
	}
	
	
	
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param invoiceNo the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	/**
	 * @param comLogService the comLogService to set
	 */
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	/**
	 * 完成或取消的单号不再显示修改框
	 * @return
	 */
	public boolean isShowInvoiceForm(){
		return !InvoiceUtil.checkChangeInvoiceNo(ordInvoice);
	}

	/**
	 * @return the comLogList
	 */
	public List<ComLog> getComLogList() {
		return comLogList;
	}

	/**
	 * @param expressNo the expressNo to set
	 */
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	
	
}