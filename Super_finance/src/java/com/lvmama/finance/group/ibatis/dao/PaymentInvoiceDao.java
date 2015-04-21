package com.lvmama.finance.group.ibatis.dao;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.group.ibatis.po.FinInvoice;
import com.lvmama.finance.group.ibatis.po.FinInvoiceAmount;
import com.lvmama.finance.group.ibatis.po.FinInvoiceLink;
import com.lvmama.finance.group.ibatis.po.PaymentInvoice;

/**
 * 付款发票管理DAO
 * @author zhangwenjun
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class PaymentInvoiceDao extends PageDao {
	/**
	 * 查询付款发票信息
	 * @param num ：团号/结算单号
	 * @param invoiceId : 发票号
	 * @return
	 */
	public Page<PaymentInvoice> searchInvoice(String num, String invoiceId){
		if((null != num && !"".equals(num)) || (null != invoiceId && !"".equals(invoiceId))){
			return queryForPageFin("PaymentInvoiceManage.searchInvoiceByNum", FinanceContext.getPageSearchContext().getContext());
		} else {
			return queryForPageFin("PaymentInvoiceManage.searchInvoice", FinanceContext.getPageSearchContext().getContext());
		}
	}
	
	/**
	 * 添加发票信息
	 * @return flag
	 */
	public boolean insertInvoice(FinInvoice finInvoice){
		boolean flag = true;
		try {
			insert("PaymentInvoiceManage.insertInvoice", finInvoice);
		} catch(Exception ex){
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 添加发票关联信息
	 * @return flag
	 */
	public boolean insertInvoiceLink(FinInvoiceLink finInvoiceLink){
		boolean flag = true;
		try {
			insert("PaymentInvoiceManage.insertInvoiceLink", finInvoiceLink);
		} catch(Exception ex){
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 
	 * @param supplierId
	 * @return
	 */
	public FinInvoiceAmount queryAmountBySupplierId(Long supplierId){
		return (FinInvoiceAmount)queryForObject("PaymentInvoiceManage.queryAmountBySupplierId", supplierId);
	}
	
	/**
	 * 修改发票金额
	 * @param finInvoiceAmount
	 * @return
	 */
	public boolean updateAmount(FinInvoiceAmount finInvoiceAmount) {
		boolean flag = true;
		try {
			update("PaymentInvoiceManage.updateAmount", finInvoiceAmount);
		} catch(Exception ex){
			flag = false;
			ex.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 查询付款发票信息
	 * @return
	 */
	public Page<PaymentInvoice> searchAlert(){
		return queryForPageFin("PaymentInvoiceManage.searchAlert", FinanceContext.getPageSearchContext().getContext());
	}
	
	/**
	 * 根据团号或是结算单号更新发票回收状态
	 * @param type
	 * @param code
	 * @return
	 */
	public boolean updateRecoveryStatus(String type, String code) {
		boolean flag = true;
		if(type.equals("group")){
			try {
				update("PaymentInvoiceManage.updateRecoveryStatusByGroupId", code);
			} catch(Exception ex) {
				flag = false;
				ex.printStackTrace();
			}
		} else if(type.equals("settlement")){
			try {
				update("PaymentInvoiceManage.updateRecoveryStatusBySettlementId", code);
			} catch(Exception ex) {
				flag = false;
				ex.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 新增供应商发票信息表
	 * @param fia
	 */
	public void insertInvoiceAmount(FinInvoiceAmount fia) {
		this.insert("PaymentInvoiceManage.insertInvoiceAmount", fia);
	}
	
	/**
	 * 修改供应商发票信息表的打款金额
	 * @param fia
	 */
	public void updateInvoiceAmount(FinInvoiceAmount fia){
		this.update("PaymentInvoiceManage.updateInvoiceAmount",fia);
	}
}
