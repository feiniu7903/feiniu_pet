/**
 * 
 */
package com.lvmama.back.remote;

import java.util.Date;
import java.util.List;

/**
 * @author yangbin
 *
 */
public interface InvoiceRemoteService {

	/**
	 * 读取默认的可以开发票的数据列表
	 * @return
	 */
	List<InvoiceInfo> queryInvoiceList();
	
	
	/**
	 * 按申请时间读取发票
	 * @param start
	 * @param end
	 * @return
	 */
	List<InvoiceInfo> queryInvoiceListByReq(Date start,Date end,String companyId);
	
	/**
	 * 读取单个发票
	 * @param invoiceId
	 * @return
	 */
	InvoiceInfo queryInvoiceDetail(Long invoiceId);
	
	/**
	 * 添加发票号
	 * @param invoiceId
	 * @param invoiceNo
	 * @return
	 */
	boolean changeStatus(Long invoiceId,String invoiceNo);
}
