package com.lvmama.finance.group.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.bee.po.op.OpGroupBudget;
import com.lvmama.comm.bee.po.op.OpOtherIncoming;
import com.lvmama.comm.bee.po.op.ProductOrderDetail;
import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinDeduction;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.fin.FinAdvanceDepositService;
import com.lvmama.comm.pet.service.fin.FinDeductionService;
import com.lvmama.comm.pet.service.fin.FinSupplierMoneyService;
import com.lvmama.comm.pet.service.fin.SetSettlementPaymentService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.finance.BaseService;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.util.CommonUtil;
import com.lvmama.finance.group.ibatis.dao.FinGroupSettlementDAO;
import com.lvmama.finance.group.ibatis.dao.OpGroupBudgetDAO;
import com.lvmama.finance.group.ibatis.dao.OpGroupBudgetFixedDAO;
import com.lvmama.finance.group.ibatis.dao.OpGroupBudgetProdDAO;
import com.lvmama.finance.group.ibatis.dao.OpTravelGroupDAO;
import com.lvmama.finance.group.ibatis.po.FinGroupSettlement;
import com.lvmama.finance.group.ibatis.po.GroupSettlementInfo;
import com.lvmama.finance.group.ibatis.po.OrderInfoDetail;
import com.lvmama.finance.group.ibatis.po.TravelGroup;
import com.lvmama.finance.group.ibatis.vo.GroupBudgetFixed;
import com.lvmama.finance.group.ibatis.vo.GroupBudgetProd;
import com.lvmama.finance.settlement.ibatis.dao.OrdSettlementPaymentDAO;
import com.lvmama.finance.settlement.service.AdvancedepositsService;

@Service
public class GroupServiceImpl extends BaseService implements GroupService {

	@Autowired
	private OpTravelGroupDAO opTravelGroupDAO;

	@Autowired
	private OpGroupBudgetDAO opGroupBudgetDAO;

	@Autowired
	private OpGroupBudgetProdDAO opGroupBudgetProdDAO;

	@Autowired
	private OpGroupBudgetFixedDAO opGroupBudgetFixedDAO;

	@Autowired
	private FinGroupSettlementDAO finGroupSettlementDAO;

	@Autowired
	private OrdSettlementPaymentDAO ordSettlementPaymentDAO;

	@Autowired
	private AdvancedepositsService advancedepositsService;

	@Autowired
	private PaymentInvoiceService paymentInvoiceService;
	
	/**
	 * 远程调用
	 */
	@Autowired
	private FinAdvanceDepositService finAdvanceDepositService;
	@Autowired
	private FinDeductionService finDeductionService;
	@Autowired
	private FinSupplierMoneyService finSupplierMoneyService;
	@Autowired
	private SetSettlementPaymentService setSettlementPaymentService;

	public FinAdvanceDepositService getFinAdvanceDepositService() {
		return finAdvanceDepositService;
	}

	public void setFinAdvanceDepositService(
			FinAdvanceDepositService finAdvanceDepositService) {
		this.finAdvanceDepositService = finAdvanceDepositService;
	}

	public FinDeductionService getFinDeductionService() {
		return finDeductionService;
	}

	public void setFinDeductionService(FinDeductionService finDeductionService) {
		this.finDeductionService = finDeductionService;
	}

	public FinSupplierMoneyService getFinSupplierMoneyService() {
		return finSupplierMoneyService;
	}

	public void setFinSupplierMoneyService(
			FinSupplierMoneyService finSupplierMoneyService) {
		this.finSupplierMoneyService = finSupplierMoneyService;
	}

	@Override
	public Page<TravelGroup> searchList() {
		return opTravelGroupDAO.searchList();
	}

	@Override
	public OpGroupBudget searchBudget(String travelGroupCode) {
		return opGroupBudgetDAO.searchBudget(travelGroupCode);
	}

	@Override
	public List<GroupBudgetProd> searchBudgetProd(String travelGroupCode, String type) {
		return opGroupBudgetProdDAO.searchBudgetProd(travelGroupCode, type);
	}

	@Override
	public List<GroupBudgetFixed> searchBudgetFixed(String travelGroupCode, String type) {
		return opGroupBudgetFixedDAO.searchBudgetFixed(travelGroupCode, type);
	}

	@Override
	public TravelGroup searchGroup(String groupCode) {
		return opTravelGroupDAO.searchGroup(groupCode);
	}

	@Override
	public void check(String groupCode) {
		TravelGroup group = opTravelGroupDAO.searchGroup(groupCode);
		// 如果已核算过
		if ("CHECKED".equals(group.getSettlementStatus())) {
			FinanceContext.getModel().addAttribute(-1);
		}else if(!Constant.TRAVEL_GROUP_STETTLEMENT_STATUS.CONFIRMED.name().equals(group.getSettlementStatus())){
			FinanceContext.getModel().addAttribute(-21);
		} else {// 核算
			HttpSession session = FinanceContext.getSession();
			PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
			// 查询实际成本的产品成本明细
			List<GroupBudgetProd> prodList = opGroupBudgetProdDAO.searchBudgetProd(groupCode, "COST");
			// 查询实际成本的固定成本明细
			List<GroupBudgetFixed> fixedList = opGroupBudgetFixedDAO.searchBudgetFixed(groupCode, "COST");
			boolean returnflag  = false;
			// 抵扣款列表
			for (GroupBudgetProd prod : prodList) {
				/**
				Double payAmount = prod.getPayAmount();
				Double subTotalCostsFc = prod.getSubTotalCostsFc();
				// 如果打款金额小于单项结算总成本（外币）时，不能核算
				if (payAmount < subTotalCostsFc) {
					FinanceContext.getModel().addAttribute(-2);
					returnflag=true;
					break;
				}
				// 打款金额大于单项结算总成本，生成抵扣款
				if (payAmount > subTotalCostsFc) {
					// 新增或是累加抵扣款
					FinDeduction finDeduction = new FinDeduction();
					finDeduction.setSupplierId(prod.getSupplierId());
					Double diffa = payAmount - subTotalCostsFc;
					finDeduction.setAmount(PriceUtil.convertToFen(diffa.toString()));
					finDeduction.setCurrency(prod.getCurrency());
					finDeduction.setType("DEPOSIT");
					finDeduction.setObjectType(Constant.FIN_DEDUCTION_OBJECT_TYPE.OP_TRAVEL_GROUP.name());
					finDeduction.setObjectId(groupCode);
					finDeduction.setCreator(user.getUserName());
					int result = finDeductionService.updateDeduction(finDeduction);
					if(result < 0){
						FinanceContext.getModel().addAttribute(-3);
						returnflag=true;
						break;
					}
				}
				*/
				if(null!=prod.getPayStatus() && 
				  !Constant.GROUP_PAY_STATUS.NOPAY.name().equalsIgnoreCase(prod.getPayStatus()) && 
				  !Constant.GROUP_PAY_STATUS.PAYED.name().equalsIgnoreCase(prod.getPayStatus())){
					FinanceContext.getModel().addAttribute(-10);
					returnflag = Boolean.TRUE;
				}
			}
			if(fixedList.isEmpty()){
				FinanceContext.getModel().addAttribute(-11);
				returnflag = Boolean.TRUE;
			}
			if (returnflag) {
				return;
			}
			
			for (GroupBudgetFixed fixed : fixedList) {
				Double payAmount = fixed.getPayAmount();
				Double subTotalCostsFc = fixed.getSubTotalCostsFc();
				// 如果打款金额小于单项结算总成本（外币）时，不能核算
				if (payAmount < subTotalCostsFc) {
					FinanceContext.getModel().addAttribute(-2);
					returnflag=true;
					break;
				}
				// 打款金额大于单项结算总成本，生成抵扣款
				if (payAmount > subTotalCostsFc) {
					// 新增或是累加抵扣款
					FinDeduction finDeduction = new FinDeduction();
					finDeduction.setSupplierId(fixed.getSupplierId());
					Double diffa = payAmount - subTotalCostsFc;
					finDeduction.setAmount(PriceUtil.convertToFen(diffa.toString()));
					finDeduction.setCurrency(fixed.getCurrency());
					finDeduction.setType("DEPOSIT");
					finDeduction.setObjectType(Constant.FIN_DEDUCTION_OBJECT_TYPE.OP_TRAVEL_GROUP.name());
					finDeduction.setObjectId(groupCode);
					finDeduction.setCreator(user.getUserName());
					int result = finDeductionService.updateDeduction(finDeduction);
					if(result < 0){
						FinanceContext.getModel().addAttribute(-3);
						returnflag=true;
						break;
					}
				}
			}
			if (returnflag) {
				return;
			}
			
			opTravelGroupDAO.checkGroup(groupCode, user.getUserName());
			// 修改订单子子项的结算状态
			opTravelGroupDAO.updateOrderItemMetaSettlementStatus(groupCode);
			// 修改订单的结算状态
			opTravelGroupDAO.updateOrderSettlementStatus(groupCode);
			FinanceContext.getModel().addAttribute(1);
		}
	}

	@Override
	public void confirmedGroupCost(final String groupCode){
		opTravelGroupDAO.confirmedGroupCost(groupCode);
	}
	@Override
	public Page<FinGroupSettlement> searchSettlement() {
		return finGroupSettlementDAO.searchSettlement();
	}

	
	/**
	 * 修改实际成本项的人民币总成本
	 * 
	 * @param rate
	 * @param groupSettlementIds
	 */
	public void updateSubTotalCosts(Double rate, Long... groupSettlementIds) {
		List<FinGroupSettlement> fgsList = finGroupSettlementDAO.searchPayedAmount(groupSettlementIds);
		for (FinGroupSettlement fgs : fgsList) {
			Long itemId = fgs.getBudgetItemId();
			if (fgs.getBudgetItemType().equals("PRODUCT")) {
				GroupBudgetProd prod = opGroupBudgetProdDAO.searchBudgetProdById(itemId);
				if (prod != null && "COST".equals(prod.getType())) {
					Double unPay = CommonUtil.doubleSubtract(prod.getSubTotalCostsFc(),fgs.getSubtotalCostsFc());
					if(unPay < 0d){
						unPay = 0d;
					}
					prod.setSubTotalCosts(fgs.getSubtotalCosts() + unPay * rate);
					prod.setExchangeRate(rate);
					opGroupBudgetProdDAO.updateSubTotalCosts(prod);
				}
			} else {
				GroupBudgetFixed fixed = opGroupBudgetFixedDAO.searchBudgetFixedById(itemId);
				if (fixed != null && "COST".equals(fixed.getType())) {
					Double unPay = CommonUtil.doubleSubtract(fixed.getSubTotalCostsFc(),fgs.getSubtotalCostsFc());
					if(unPay < 0d){
						unPay = 0d;
					}
					fixed.setSubTotalCosts(fgs.getSubtotalCosts() + unPay * rate);
					fixed.setExchangeRate(rate);
					opGroupBudgetFixedDAO.updateSubTotalCosts(fixed);
				}
			}
		}
	}

	private String basePay(SetSettlementPayment osp) {
		String ids = osp.getGroupSettlementIds();
		String[] idArr = ids.split(",");
		Long[] groupSettlementIds = new Long[idArr.length] ;
		for(int i=0; i<idArr.length; i++){
			groupSettlementIds[i] = Long.parseLong(idArr[i]);
		} 
		Long deductionPayAmount = osp.getDeductionPayAmount();
		Long advanceDepositsPayAmount = osp.getAdvanceDepositsPayAmount();
		
		if(null == deductionPayAmount){
			deductionPayAmount = 0l;
		}
		if(null == advanceDepositsPayAmount){
			advanceDepositsPayAmount = 0l;
		}
		
		String status = "PAYED";
		Double amount = null;
		List<FinGroupSettlement> fgsList = finGroupSettlementDAO.searchSettlementByIds(groupSettlementIds);
		FinGroupSettlement tmp_fgs = fgsList.get(0);
		FinSupplierMoney finSupplierMoney = finSupplierMoneyService.searchBySupplierId(tmp_fgs.getSupplierId());
		for(int i=0; i<fgsList.size(); i++){
			FinGroupSettlement finGroupSettlement = fgsList.get(i);
			// 当预存款余额大于0的时候，判断预存款的币种和要打款的币种是否一致
			if(advanceDepositsPayAmount > 0){
				if(!finGroupSettlement.getCurrency().equals(finSupplierMoney.getAdvanceDepositCurrency())){
					FinanceContext.getModel().addAttribute("res", -2);
					return null;
				}
			}
			// 当抵扣款余额大于0的时候，判断抵扣款的币种和要打款的币种是否一致
			if(deductionPayAmount > 0){
				if(!finGroupSettlement.getCurrency().equals(finSupplierMoney.getDeductionCurrency())){
					FinanceContext.getModel().addAttribute("res", -3);
					return null;
				}
			}
		}
		
		if (groupSettlementIds.length == 1) {
			amount = (double)osp.getAmount() + deductionPayAmount + advanceDepositsPayAmount;
			if (CommonUtil.doubleSubtract(tmp_fgs.getSubtotalCosts(),tmp_fgs.getPayAmount())> amount) {
				status = "PARTPAY";
			}
		}
		if (osp.getAmount() > 0) {
			// 添加付款发票
			boolean res = paymentInvoiceService.payDone2Invoice(tmp_fgs.getSupplierId(), (double)osp.getAmount(), tmp_fgs.getCurrency());
			if (!res) {
				return null;
			}
		}
		
		// 修改单项结算表
		finGroupSettlementDAO.pay(groupSettlementIds, osp.getRate(), amount, osp.getRemark(), status);
		
		// 添加抵扣款流水记录
		if(deductionPayAmount > 0){
			FinDeduction finDeduction = new FinDeduction();
			finDeduction.setSupplierId(tmp_fgs.getSupplierId());
			finDeduction.setType(Constant.FIN_DEDUCTION_TYPE.PAYMENT.name());
			finDeduction.setDirection(Constant.DERECTION_TYPE.CREDIT.name());
			finDeduction.setObjectType("OP_TRAVEL_GROUP");
			finDeduction.setObjectId(tmp_fgs.getTravelGroupCode());
			finDeduction.setAmount((long)deductionPayAmount);
			finDeduction.setRemark("打款时使用抵扣款");
			HttpSession session = FinanceContext.getSession();
			PermUser user = (PermUser) session.getAttribute(Constant.SESSION_BACK_USER);
			finDeduction.setCreator(user.getUserName());
			finDeduction.setCurrency(tmp_fgs.getCurrency());
			finDeductionService.insertFinDeduction(finDeduction);
			// 修改抵扣款
			finSupplierMoneyService.updateMoney(null , deductionPayAmount, tmp_fgs.getSupplierId());
		}
		// 添加预存款流水记录
		if(advanceDepositsPayAmount > 0){
			FinAdvanceDeposit finAdvancedDeposit = new FinAdvanceDeposit();
			finAdvancedDeposit.setSupplierId(tmp_fgs.getSupplierId());
			finAdvancedDeposit.setAmount(osp.getAmount().longValue());
			finAdvancedDeposit.setOperatetime(new Date());
			finAdvancedDeposit.setType(com.lvmama.comm.vo.Constant.FIN_ADVANCE_DEPOSIT_TYPE.PAYMENT.name());
			if (groupSettlementIds.length == 1) {
				finAdvancedDeposit.setBank(tmp_fgs.getTravelGroupCode());
			} else {
				finAdvancedDeposit.setBank("团合并打款");
			}
			HttpSession session = FinanceContext.getSession();
			PermUser user = (PermUser) session.getAttribute(Constant.SESSION_BACK_USER);
			finAdvancedDeposit.setCreator(user.getUserName());
			finAdvancedDeposit.setCurrency(tmp_fgs.getCurrency());
			finAdvancedDeposit.setAmount(advanceDepositsPayAmount);
			finAdvanceDepositService.addAdvanceDeposit(finAdvancedDeposit);
		}
		
		//单项催款记录
		for (FinGroupSettlement fgs : fgsList) {
			String _status = status;
			Double _amount = null;
			// 修改产品成本明细表的打款状态和金额
			if(fgs.getBudgetItemType().equals("PRODUCT")){
				//固定成本明细
				GroupBudgetProd prod = opGroupBudgetProdDAO.searchBudgetProdById(fgs.getBudgetItemId());
				if("PAYED".equals(status)){
					if(prod.getSubTotalCostsFc().doubleValue() > fgs.getSubtotalCosts()){
						_status = "PARTPAY";
						_amount = fgs.getSubtotalCosts()+prod.getPayAmount();
					}else if(fgs.getSubtotalCosts() > prod.getSubTotalCostsFc().doubleValue()){
						_amount = fgs.getSubtotalCosts();
					}
				}else{//部分支付（只存在单笔打款）
					_amount = amount+prod.getPayAmount();
					if((osp.getAmount()+prod.getPayAmount()) >= prod.getSubTotalCostsFc()){//判断打款金额+产品明细的已打款是否大于等于总成本项
						_status = "PAYED";
					}
				}
				opGroupBudgetProdDAO.pay(prod.getItemId(), osp.getRate(), _amount, osp.getRemark(), _status);
			}else if(fgs.getBudgetItemType().equals("FIXED")){// 修改固定成本明细表的打款状态和金额
				GroupBudgetFixed fixed = opGroupBudgetFixedDAO.searchBudgetFixedById(fgs.getBudgetItemId());
				if("PAYED".equals(status)){
					if(fixed.getSubTotalCostsFc().doubleValue() > fgs.getSubtotalCosts()){
						_status = "PARTPAY";
						_amount = fgs.getSubtotalCosts()+fixed.getPayAmount();
					}else if(fgs.getSubtotalCosts() > fixed.getSubTotalCostsFc().doubleValue()){
						_amount = fgs.getSubtotalCosts();
					}
				}else{//部分支付（只存在单笔打款）
					_amount = amount+fixed.getPayAmount();
					if((osp.getAmount()+fixed.getPayAmount()) >= fixed.getSubTotalCostsFc()){//判断打款金额+产品明细的已打款是否大于等于总成本项
						_status = "PAYED";
					}
				}
				opGroupBudgetFixedDAO.pay(fixed.getItemId(), osp.getRate(), _amount, osp.getRemark(), _status);
			}else{
				throw new RuntimeException("团单项结算的类型错误 ERROR_TYPE:"+fgs.getBudgetItemType());
			}
		}
		
		/**
		 * 添加打款记录
		 */
		if (osp.getAmount() + advanceDepositsPayAmount > 0) {
			HttpSession session = FinanceContext.getSession();
			PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
			osp.setCreator(user.getUserName());
			osp.setCreatetime(new Date());
			osp.setCurrency(tmp_fgs.getCurrency());
			// 单项打款
			if (groupSettlementIds.length == 1) {
				osp.setTravelGroupCode(tmp_fgs.getTravelGroupCode());
				osp.setTargetId(tmp_fgs.getTargetId());
				osp.setGroupSettlementId(groupSettlementIds[0]);
				osp.setOperatetime(new Date());
				if(osp.getAmount() > 0){
					osp.setPaytype(Constant.SET_SETTLEMENT_PAYMENT_PAYTYPE.CASH.name());
					osp.setAmount(osp.getAmount());
					setSettlementPaymentService.insertPayment(osp);
					this.log(groupSettlementIds[0], "FIN_GROUP_SETTLEMENT", "PAY", "单项结算(打款)", " 打款金额：" + osp.getAmount() + " 打款状态：" + status);
				}
				// 预存款
				if (advanceDepositsPayAmount > 0) {
					osp.setBank("预存款");
					osp.setPaytype(Constant.SET_SETTLEMENT_PAYMENT_PAYTYPE.ADVANCE_DEPOSIT.name());
					osp.setAmount(advanceDepositsPayAmount);
//					ordSettlementPaymentDAO.insertPayment(osp);
					setSettlementPaymentService.insertPayment(osp);
					this.log(groupSettlementIds[0], "FIN_GROUP_SETTLEMENT", "PAY", "单项结算(打款)", " 打款金额：" + advanceDepositsPayAmount + " 打款状态：" + status);
				}
			} else {// 合并打款
				for (FinGroupSettlement fgs : fgsList) {
					osp.setPaytype(Constant.SET_SETTLEMENT_PAYMENT_PAYTYPE.CASH.name());
					osp.setTravelGroupCode(fgs.getTravelGroupCode());
					osp.setAmount(fgs.getSubtotalCosts().longValue());
					osp.setTargetId(fgs.getTargetId());
					osp.setGroupSettlementId(fgs.getGroupSettlementId());
					setSettlementPaymentService.insertPayment(osp);
					this.log(fgs.getGroupSettlementId(), "FIN_GROUP_SETTLEMENT", "MERGE_PAY", "单项结算(合并打款)", " 打款金额：" + fgs.getSubtotalCosts() + " 打款状态：" + status);
				}
			}
			this.updateSubTotalCosts(osp.getRate(), groupSettlementIds);
		}
		return tmp_fgs.getTravelGroupCode();
	}

	@Override
	public String bankPay(SetSettlementPayment osp) {
		return this.basePay(osp);
	}

//	@Override
//	public String advPay(Long[] groupSettlementIds, OrdSettlementPayment osp) {
//		return this.basePay(groupSettlementIds, osp, Constant.ADVANCEDEPOSITS);
//	}

	@Override
	public void deldk(String ids) {
		String[] idList = ids.split(",");
		this.finGroupSettlementDAO.deldk(idList);
	}

	@Override
	public GroupSettlementInfo searchGroupSettlement(Long id) {
		return finGroupSettlementDAO.searchGroupSettlement(id);
	}

	@Override
	public List<OpOtherIncoming> searchOtherIncoming(String groupCode) {
		return opGroupBudgetFixedDAO.searchOtherIncoming(groupCode);
	}
	
	@Override
	public Page<ProductOrderDetail> searchProductOrderDetails() {
		return opTravelGroupDAO.searchProductOrderDetails();
	}


	@Override
	public Page<FinGroupSettlement> searchSettlementSumprice() {
		return finGroupSettlementDAO.searchSettlementSumprice();
	}

	@Override
	public Page<OrderInfoDetail> searchOrderInfoDetail(Map map) {
		return finGroupSettlementDAO.searchOrderInfoDetail(map);
	}

	@Override
	public List<OrderInfoDetail> exportOrderInfoDetail(Map map) {
		return finGroupSettlementDAO.exportOrderInfoDetail(map);
	}

	@Override
	public OrderInfoDetail searchSumprice(Map map) {
		return finGroupSettlementDAO.searchSumPrice(map);
	}

	@Override
	public FinGroupSettlement searchSettlementById(Long groupSettlementId) {
		return finGroupSettlementDAO.searchSettlementById(groupSettlementId);
	}

	@Override
	public List<OrderInfoDetail> exportOrderDetail() {
		return finGroupSettlementDAO.exportOrderDetail();
	}
//	private void fillRefundmentData(List<OrdSubSettlementItem> res){
//		for(OrdSubSettlementItem item : res){
//			if(item.getRefundmentId() != null){//包含退款信息
//				List<OrdRefundMentItem> oriList = finGroupSettlementDAO.searchRefundmentDetail(item.getOrderItemMetaId());
//				for (OrdRefundMentItem ori : oriList) {
//					Double settlementPrice = item.getRealItemPriceSum();
//					Double new_settlementPrice = null;
//					if ("VISITOR_LOSS".equals(ori.getType())) {//退款明细类型为游客损失
//						new_settlementPrice = ori.getAmount() / 100;
//					}else{
//						new_settlementPrice = settlementPrice - ori.getAmount() / 100 ;
//					}
//					item.setRealItemPriceSum(new_settlementPrice);
//				}
//			}
//		}
//	}

	@Override
	public List<FinGroupSettlement> searchById(String[] groupSettlementIds) {
		return finGroupSettlementDAO.searchById(groupSettlementIds);
	}

}
