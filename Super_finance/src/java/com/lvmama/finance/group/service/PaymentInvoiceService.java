package com.lvmama.finance.group.service;

import com.lvmama.finance.base.Page;
import com.lvmama.finance.group.ibatis.po.FinInvoice;
import com.lvmama.finance.group.ibatis.po.FinInvoiceAmount;
import com.lvmama.finance.group.ibatis.po.FinInvoiceLink;
import com.lvmama.finance.group.ibatis.po.PaymentInvoice;

/**
 * 付款发票信息Service
 * 
 * @author zhangwenjun
 * 
 */
public interface PaymentInvoiceService {

	

	/**
	 * 打款成功后生成发票信息
	 * @param supplierId
	 * @param payAmount
	 * @param currency
	 */
	boolean payDone2Invoice(Long supplierId,Double payAmount,String currency);
	/**
	 * 查询付款发票信息
	 * 
	 * @return
	 */
	Page<PaymentInvoice> searchInvoice(String num, String invoiceId);
	
	/**
	 * 添加发票信息
	 * @return flag
	 */
	boolean insertInvoice(FinInvoice finInvoice);

	/**
	 * 添加发票关联信息
	 * @return flag
	 */
	boolean insertInvoiceLink(FinInvoiceLink finInvoiceLink);
	/**
	 * 添加发票关联信息
	 * @return flag
	 */
	boolean updateRecoveryStatus(String type, String code);
	
	/**
	 * 根据供应商ID 查询发票金额信息
	 * @param supplierId
	 * @return
	 */
	FinInvoiceAmount queryAmountBySupplierId(Long supplierId, Double invoiceAmount);
	
	/**
	 * 修改发票金额
	 * @param finInvoiceAmount
	 * @return
	 */
	boolean updateAmount(FinInvoiceAmount finInvoiceAmount);
	
	/**
	 * 发票预警
	 * @return
	 */
	Page<PaymentInvoice> searchAlert();
}
