package com.lvmama.back.sweb.invoice;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.vo.InvoiceResult;
import com.lvmama.comm.utils.InvoiceUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;

/**
 * 对多个发票操作.
 * @author yangbin
 *
 */
public class InvoiceOpListAction extends BaseInvoiceAction{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3462971171473224753L;
	
	private JSONArray successObj;
	private StringBuffer cancel;
	
	/**
	 * 发票id列表字符串，当中以逗号分开
	 */
	private String invoices;
	/**
	 * 根据invoices转换后的id数组
	 */
	private String array[];
	
	
	@Action("/ord/invoiceListApprove")
	public void doApprove(){
		JSONResult result = new JSONResult();
		try{					
			changeStatusList(new InvoiceComp() {
				
				@Override
				public boolean hasChangeAble(OrdInvoice ordInvoice) {
					return InvoiceUtil.checkChangeApproveAble(ordInvoice);
				}
			},Constant.INVOICE_STATUS.APPROVE);
			result.put("results", successObj);
			boolean hasCancel=false;
			if(StringUtils.isNotEmpty(cancel.toString())){
				hasCancel=true;
				result.put("cancelMsg", cancel.toString());
			}
			result.put("cancel", hasCancel);
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	void changeStatusList(InvoiceComp comp,Constant.INVOICE_STATUS status){
		doBefore();	
		successObj=new JSONArray();
		cancel=new StringBuffer();
		
		for (String id : array) {
			Long invoice = NumberUtils.toLong(id);
			if (invoice > 0) {
				try{
					OrdInvoice ordInvoice=orderServiceProxy.selectOrdInvoiceByPrimaryKey(invoice);
					//为空或是已经取消的就跳过
					if (comp.hasChangeAble(ordInvoice)) {
						continue;
					}
					
					InvoiceResult ir=orderServiceProxy.update(status
							.name(), invoice, getOperatorName());
					if(ir.isSuccess()){
						successObj.add(invoice);
					}else if(ir.isCancel()){
						cancel.append(ir.getMsg());
						cancel.append("\n");
					}
				}catch(Exception exx){
					
				}
			}
		}
	}
	
	@Action("/ord/invoiceListBill")
	public void doBill(){
		JSONResult result = new JSONResult();
		try{
			changeStatusList(new InvoiceComp() {
				
				@Override
				public boolean hasChangeAble(OrdInvoice ordInvoice) {
					return InvoiceUtil.checkChangeApproveAble(ordInvoice);
				}
			},Constant.INVOICE_STATUS.BILLED);
			result.put("results", successObj);
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	

	private void doBefore(){
		Assert.hasLength(invoices, "没有选中发票");
		
		array=StringUtils.split(invoices,",");
		Assert.notEmpty(array,"没有选中发票");
	}
	
	/**
	 * @param invoices the invoices to set
	 */
	public void setInvoices(String invoices) {
		this.invoices = invoices;
	}
	
}