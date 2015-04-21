package com.lvmama.finance.group.action;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.group.ibatis.po.FinInvoice;
import com.lvmama.finance.group.ibatis.po.FinInvoiceAmount;
import com.lvmama.finance.group.ibatis.po.FinInvoiceLink;
import com.lvmama.finance.group.ibatis.po.PaymentInvoice;
import com.lvmama.finance.group.service.PaymentInvoiceService;

/**
 * 付款发票管理Action
 * 
 * @author zhangwenjun
 * 
 */
@Controller
@RequestMapping("/group/paymentInvoice")
public class PaymentInvoiceAction extends BaseAction {

	@Autowired
	private PaymentInvoiceService paymentInvoiceService;

	/**
	 * 进入付款发票管理页面
	 * 
	 * @return 查询页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String advancedeposits() {
		return "group/paymentInvoice/manage";
	}

	/**
	 * 查询付款发票信息
	 * 
	 * @return 结果页面
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/search")
	public Page<PaymentInvoice> searchInvoice(@RequestParam("id") String num, @RequestParam("invoiceId") String invoiceId) {
		return paymentInvoiceService.searchInvoice(num, invoiceId);
	}
	
	/**
	 * 根据供应商id查询供应商的发票余额
	 */
	@RequestMapping(value = "/queryBalance")
	public void queryBalance(@RequestParam("supplierId") Long supplierId, Model model){
		FinInvoiceAmount finInvoiceAmount = paymentInvoiceService.queryAmountBySupplierId(supplierId, -0.2);
		if(null != finInvoiceAmount && null != finInvoiceAmount.getPayAmount() && null != finInvoiceAmount.getInvoiceAmount()){
			model.addAttribute(finInvoiceAmount.getPayAmount()-finInvoiceAmount.getInvoiceAmount());
		} else {
			model.addAttribute(-1);
		}
	}
	
	/**
	 * 增加发票信息
	 */
	@RequestMapping(value = "/insert")
	public void insertInvoice(@RequestParam("invoiceCode") String invoiceCode, 
			@RequestParam("invoiceAmount") Double invoiceAmount, @RequestParam("supplier") Long supplierId, 
			@RequestParam("settlementCode") String settlementCode, @RequestParam("groupCode") String groupCode, 
			@RequestParam("invoiceTitle") String invoiceTitle, Model model){
		boolean flag = true;
		
		/**
		 * 增加发票信息
		 */
		FinInvoice finInvoice = new FinInvoice();
		// 发票号
		finInvoice.setInvoiceCode(invoiceCode);
		// 本次添加的发票金额
		finInvoice.setInvoiceAmount(invoiceAmount);
		// 供应商ID  
		finInvoice.setSupplierId(supplierId);
		// 添加时间
		finInvoice.setCreateTime(new Date());
		// 添加人
		HttpSession session = FinanceContext.getSession();
		PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		finInvoice.setCreator(user.getUserId());
		// 发票抬头
		finInvoice.setInvoiceTitle(invoiceTitle);
		// 保存付款发票信息
		flag = paymentInvoiceService.insertInvoice(finInvoice);
		if(!flag){
			model.addAttribute("添加失败");
			return;
		}
		
		/**
		 * 保存结算单号
		 */
		// 获取所有的结算单号
		String[] settlementCodes = settlementCode.split(",");
		if(settlementCodes.length > 0){
			for(int i=0; i < settlementCodes.length; i++){
				if(null != settlementCodes[i] && !"".equals(settlementCodes[i])){
					FinInvoiceLink finInvoiceLink = new FinInvoiceLink();
					// 关联的发票ID
					finInvoiceLink.setInvoiceId(finInvoice.getInvoiceId());
					// 结算单号
					finInvoiceLink.setCode(settlementCodes[i]);
					// 类型（结算单号/团号）
					finInvoiceLink.setType("SETTLEMENT");
					
					// 执行保存
					flag = paymentInvoiceService.insertInvoiceLink(finInvoiceLink);
					if(!flag){
						model.addAttribute("添加失败");
						return;
					}
					/**
					 * 根据结算单号修改发票回收状态
					 */
					paymentInvoiceService.updateRecoveryStatus("settlement", finInvoiceLink.getCode());
				}
			}
		}
		
		/**
		 * 保存团号
		 */
		// 获取所有的团号
		String[] groupCodes = groupCode.split(",");
		if(groupCodes.length > 0){
			for(int i=0; i < groupCodes.length; i++){
				if(null != groupCodes[i] && !"".equals(groupCodes[i])){
					FinInvoiceLink finInvoiceLink = new FinInvoiceLink();
					// 关联的发票ID
					finInvoiceLink.setInvoiceId(finInvoice.getInvoiceId());
					// 结算单号
					finInvoiceLink.setCode(groupCodes[i]);
					// 类型（结算单号/团号）
					finInvoiceLink.setType("GROUP");

					// 执行保存
					flag = paymentInvoiceService.insertInvoiceLink(finInvoiceLink);
					if(!flag){
						model.addAttribute("添加失败");
						return;
					}

					/**
					 * 根据团号修改发票回收状态
					 */
					paymentInvoiceService.updateRecoveryStatus("group", finInvoiceLink.getCode());
				}
			}
		}

		/**
		 * 修改发票金额
		 */
		FinInvoiceAmount finInvoiceAmount = paymentInvoiceService.queryAmountBySupplierId(supplierId, invoiceAmount);
		if(null != finInvoiceAmount){
			// 发票金额
			finInvoiceAmount.setInvoiceAmount(invoiceAmount);
			flag = paymentInvoiceService.updateAmount(finInvoiceAmount);
		} else {
			model.addAttribute("供应商不存在发票信息");
			return;
		}
		if(!flag){
			model.addAttribute("添加失败");
			return;
		}
		
		model.addAttribute("添加成功");
	}
	
	/**
	 * 进入发票预警页面
	 * 
	 * @return 查询页面
	 */
	@RequestMapping(value = "/alertIndex", method = RequestMethod.GET)
	public String alertIndex() {
		return "group/paymentInvoice/invoiceAlert";
	}
	
	/**
	 * 查询发票预警信息
	 * 
	 * @return 结果页面
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/searchAlert")
	public Page<PaymentInvoice> searchAlert() {
		return paymentInvoiceService.searchAlert();
	}
}
