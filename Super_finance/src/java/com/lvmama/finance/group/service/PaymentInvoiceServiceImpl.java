package com.lvmama.finance.group.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.finance.BaseService;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.group.ibatis.dao.PaymentInvoiceDao;
import com.lvmama.finance.group.ibatis.po.FinInvoice;
import com.lvmama.finance.group.ibatis.po.FinInvoiceAmount;
import com.lvmama.finance.group.ibatis.po.FinInvoiceLink;
import com.lvmama.finance.group.ibatis.po.PaymentInvoice;
import com.lvmama.finance.settlement.ibatis.dao.OrdSettlementPaymentDAO;

@Service
public class PaymentInvoiceServiceImpl extends BaseService implements PaymentInvoiceService {
	@Autowired
	private PaymentInvoiceDao paymentInvoiceDao;
	private final String LOG_OBJECT_TYPE = "FIN_INVOICE";
	@Autowired
	private OrdSettlementPaymentDAO ordSettlementPaymentDAO;
	
	@Override
	public Page<PaymentInvoice> searchInvoice(String num, String invoiceId) {
		return paymentInvoiceDao.searchInvoice(num, invoiceId);
	}

	@Override
	public boolean insertInvoice(FinInvoice finInvoice) {
		boolean flag = paymentInvoiceDao.insertInvoice(finInvoice);
		return flag;
	}

	@Override
	public boolean insertInvoiceLink(FinInvoiceLink finInvoiceLink) {
		boolean flag = paymentInvoiceDao.insertInvoiceLink(finInvoiceLink);
		return flag;
	}

	@Override
	public FinInvoiceAmount queryAmountBySupplierId(Long supplierId, Double invoiceAmount) {
		FinInvoiceAmount finInvoiceAmount = paymentInvoiceDao.queryAmountBySupplierId(supplierId);
		// 如果该供应商不存在发票金额信息，则finInvoiceAmount为null
		if(null != finInvoiceAmount && invoiceAmount >= 0){

			double balance = this.sub(finInvoiceAmount.getPayAmount(), finInvoiceAmount.getInvoiceAmount() + invoiceAmount);
			
			this.log(finInvoiceAmount.getId(), this.LOG_OBJECT_TYPE, "INVOICE_AMOUNT", "新增付款发票信息",
					"新增发票金额：" + invoiceAmount 
					+ ",发票余额：" + balance);
		}
		
		return finInvoiceAmount;
	}
	
	public double sub(double d1,double d2){ 
		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
		BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
		return bd1.subtract(bd2).doubleValue(); 
	} 
	
	@Override
	public boolean updateAmount(FinInvoiceAmount finInvoiceAmount) {
		return paymentInvoiceDao.updateAmount(finInvoiceAmount);
	}

	@Override
	public Page<PaymentInvoice> searchAlert() {
		return paymentInvoiceDao.searchAlert();
	}

	@Override
	public boolean updateRecoveryStatus(String type, String code) {
		return paymentInvoiceDao.updateRecoveryStatus(type, code);
	}

	@Override
	public boolean payDone2Invoice(Long supplierId,Double payAmount,String currency) {
		//查询发票金额
		FinInvoiceAmount fa = paymentInvoiceDao.queryAmountBySupplierId(supplierId);
		//如果发票余额大于0 查询最后一次打款记录的币种
		if(fa !=null && fa.getPayAmount() - fa.getInvoiceAmount()>0){
			String currency_s = ordSettlementPaymentDAO.searchLastCurrency(supplierId);
			if(!currency.equals(currency_s)){
				//如果打款币种与发票余额的币种不一致，无法进行打款
				if(FinanceContext.getModel()!=null){
					FinanceContext.getModel().addAttribute("res",-1);
				}
				return false;
			}
		}
		if (fa == null) {//新增发票信息表
			FinInvoiceAmount fia = new FinInvoiceAmount();
			fia.setSupplierId(supplierId);
			fia.setPayAmount(payAmount);
			fia.setInvoiceAmount(0d);
			paymentInvoiceDao.insertInvoiceAmount(fia);
		} else {//修改发票信息表的打款金额
			FinInvoiceAmount fia = new FinInvoiceAmount();
			fia.setSupplierId(supplierId);
			fia.setPayAmount(payAmount + fa.getPayAmount()*100);
			paymentInvoiceDao.updateInvoiceAmount(fia);
		}
		return true;
	}

}
