package com.lvmama.finance.settlement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.finance.BaseService;
import com.lvmama.finance.base.Constant;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.group.service.PaymentInvoiceService;
import com.lvmama.finance.settlement.ibatis.dao.OrdSettlementChangeDAO;
import com.lvmama.finance.settlement.ibatis.dao.OrdSettlementDAO;
import com.lvmama.finance.settlement.ibatis.dao.OrdSettlementPaymentDAO;
import com.lvmama.finance.settlement.ibatis.dao.OrdSubSettlementDAO;
import com.lvmama.finance.settlement.ibatis.dao.OrdSubSettlementItemDAO;
import com.lvmama.finance.settlement.ibatis.dao.SettlementQueueItemDAO;
import com.lvmama.finance.settlement.ibatis.po.FincAdvancedeposits;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlement;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementChange;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlement;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlementItem;
import com.lvmama.finance.settlement.ibatis.po.SettlementQueueItem;
import com.lvmama.finance.settlement.ibatis.vo.OrderProductDetail;
import com.lvmama.finance.settlement.ibatis.vo.OrderSearchResult;
import com.lvmama.finance.settlement.ibatis.vo.SimpleOrdSettlement;

@Service
public class OrdSettlementServiceImpl extends BaseService implements OrdSettlementService {
	private final int MAX_LIST_SIZE = 1000;
	@Autowired
	private OrdSettlementDAO ordSettlementDAO;

	@Autowired
	private OrdSubSettlementDAO ordSubSettlementDAO;

	@Autowired
	private SettlementQueueItemDAO settlementQueueItemDAO;

	@Autowired
	private OrdSubSettlementItemDAO ordSubSettlementItemDAO;

	@Autowired
	private OrdSettlementPaymentDAO ordSettlementPaymentDAO;

	@Autowired
	private OrdSettlementChangeDAO ordSettlementChangeDAO;

	@Autowired
	private AdvancedepositsService advancedepositsService;

	@Autowired
	private PaymentInvoiceService paymentInvoiceService;

	
	private final String LOG_OBJECT_TYPE = "ORD_SETTLEMENT";

	@Override
	public Page<OrdSettlement> searchOrdSettlement() {
		return ordSettlementDAO.searchOrdSettlement();
	}

	@Override
	public Page<OrdSubSettlement> searchOrdSubSettlement() {
		return ordSubSettlementDAO.searchOrdSubSettlement();
	}

	@Override
	public Page<OrdSubSettlementItem> searchOrdSubSettlementItem() {
		return ordSubSettlementItemDAO.searchOrdSubSettlementItem();
	}

	@Override
	public Integer advancedepositsBalpay(OrdSettlement ors) {
		Double amount = advancedepositsService.searchAmount(ors.getSupplierId(),"CNY");
		if(ors.getPayedAmount()> amount){
			return -1;
		}
		//添加付款发票
		boolean res = paymentInvoiceService.payDone2Invoice(ors.getSupplierId(), ors.getPayedAmount(),"CNY");
		if(!res){
			return -2;
		}
		// 新增供应商的预存款结算记录
		HttpSession session = FinanceContext.getSession();
		PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		OrdSettlementPayment orp = new OrdSettlementPayment();
		orp.setSettlementId(ors.getSettlementId());
		orp.setTargetId(ors.getTargetId());
		orp.setPaytype(Constant.ADVANCEDEPOSITS);
		orp.setAmount(ors.getPayedAmount());
		orp.setCreator(user.getUserId());
		orp.setOperatetime(new Date());
		orp.setBank("预存款");
		orp.setCreatetime(orp.getOperatetime());
		orp.setCurrency("CNY");
		orp.setRate(1d);
		ordSettlementPaymentDAO.insertPayment(orp);
		// 新增供应商的预存款流水记录
		FincAdvancedeposits ffa = new FincAdvancedeposits();
		ffa.setSupplierId(ors.getSupplierId());
		ffa.setAmount(ors.getPayedAmount());
		ffa.setOperatetime(new Date());
		ffa.setType(Constant.PAYMENT);
		ffa.setOperatetime(ffa.getOperatetime());
		ffa.setBank(ors.getSettlementId() + "");
		ffa.setAdvCurrency("CNY");
		advancedepositsService.addAdvancedeposits(ffa);
		// 结算单支付
		ordSettlementDAO.settlementPay(ors);
		this.log(ors.getSettlementId(), this.LOG_OBJECT_TYPE, "ADVANCEDEPOSITS_BAL_PAY", "预存款结算", "使用预存款结算了：" + ors.getPayedAmount() + "元");
		return 1;
	}

	public void bankPay(OrdSettlement ors, String bank, String operatetimes, String serial) {
		//添加付款发票
		boolean res = paymentInvoiceService.payDone2Invoice(ors.getSupplierId(), ors.getPayedAmount(),"CNY");
		if(!res){
			return;
		}
		// 新增结算记录
		HttpSession session = FinanceContext.getSession();
		PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		OrdSettlementPayment orp = new OrdSettlementPayment();
		orp.setSettlementId(ors.getSettlementId());
		orp.setTargetId(ors.getTargetId());
		orp.setPaytype(Constant.CASH);
		orp.setAmount(ors.getPayedAmount());
		orp.setCreator(user.getUserId());
		orp.setBank(bank);
		orp.setSerial(serial);
		orp.setOperatetime(DateUtil.toDate(operatetimes, "yyyy-MM-dd HH:mm"));
		orp.setCreatetime(new Date());
		orp.setCurrency("CNY");
		orp.setRate(1d);
		ordSettlementPaymentDAO.insertPayment(orp);
		// 结算单支付
		ordSettlementDAO.settlementPay(ors);
		this.log(ors.getSettlementId(), this.LOG_OBJECT_TYPE, "BANK_PAY", "线下结算", "线下结算了：" + ors.getPayedAmount() + "元");
	}

	public SimpleOrdSettlement searchSimpleOrdSettlement(Long id) {
		return ordSettlementDAO.searchSingleOrdSettlementWithTarget(id);
	}

	/**
	 * 结算单确认/结算时查询原始结算单信息
	 * 
	 * @param id
	 *            结算单ID
	 */
	public SimpleOrdSettlement searchInitalOrdSettlement(Long id) {
		return ordSettlementDAO.searchInitalOrdSettlementWithTarget(id);
	}

	@Override
	public void updateInitalInfo(Long id, OrdSettlement ors) {
		ordSettlementDAO.updateInitalInfo(id, ors);
	}

	@Override
	public void confirm(OrdSettlement ors) {
		ors.setConfirmTime(new Date());
		ors.setStatus(Constant.SETTLEMENT_STATUS_CONFIRMED);
		ordSettlementDAO.update(ors);
		this.log(ors.getSettlementId(), this.LOG_OBJECT_TYPE, "CONFIRMED", "结算单确认", "结算单确认");
	}

	@Override
	public void settlement(OrdSettlement ors) {
		Long settlementId = ors.getSettlementId();
		OrdSettlement ordSettlement = ordSettlementDAO.searchSingleOrdSettlementAmount(settlementId);
		double diffAmount = ordSettlement.getPayAmount() - ordSettlement.getPayedAmount();
		// 打款金额，应结算金额存在差额产生抵扣款
		if (diffAmount < 0) {
			OrdSettlementChange change = ordSettlementChangeDAO.searchModifyOrDelChange(settlementId);
			if (change == null) {
				throw new RuntimeException("抵扣款生成异常，没有查询到任何调价，删除的变动记录");
			}
			// 生成抵扣款
			settlementQueueItemDAO.insertSettlementQueueItemDeduction(diffAmount, change.getSubSettlementId(), settlementId, change.getOrderItemMetaId());
		}
		// 把之前删除至结算对列项的payamount改为0
		settlementQueueItemDAO.updateSettlementQueueItem2Zero(settlementId);

		ors.setStatus(Constant.SETTLEMENT_STATUS_SETTLEMENTED);
		ordSettlementDAO.update(ors);
		// 修改订单子子项的结算状态
		ordSettlementDAO.updateOrderItemMetaSettlementStatus(ors.getSettlementId());
		// 修改订单的结算状态
		ordSettlementDAO.updateOrderSettlementStatus(ors.getSettlementId());
		this.log(ors.getSettlementId(), this.LOG_OBJECT_TYPE, "SETTLEMENTED", "结算单结算", "结算单结算");
	}

	@Override
	public Integer deleteSubSettlement(Long settlementId, Long subSettlementId) {
		// 查询结算单是否已经打款完成（打款金额大于结算金额时，才可以删除结算子单）
		OrdSettlement ors = ordSettlementDAO.searchSingleOrdSettlementAmount(settlementId);
		if (ors.getPayedAmount() != null && ors.getPayedAmount() > 0 && ors.getPayedAmount() < ors.getPayAmount()) {
			return -1;
		}
		// 已打款的结算子单删除时，要记录删除记录
		if (ors.getPayedAmount() != null) {
			PermUser user = (PermUser) FinanceContext.getSession().getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
			ordSettlementChangeDAO.insertBatchDel(user.getUserId(), subSettlementId);
		}
		if (!Constant.SETTLEMENT_STATUS_UNSETTLEMENTED.equals(ors.getStatus())) {
			if (Constant.SETTLEMENT_STATUS_SETTLEMENTED.equals(ors.getStatus())) {
				return -2;
			} else {
				return -3;
			}
		}
		StringBuffer log_content = new StringBuffer("删除结算子单:");
		log_content.append(subSettlementId);
		// 新增结算队列项目
		settlementQueueItemDAO.insertSettlementQueueItem(subSettlementId);
		// 删除结算子单项
		ordSubSettlementItemDAO.deleteOrdSubSettlementItem(subSettlementId);
		// 删除结算子单
		ordSubSettlementDAO.deleteOrdSubSettlement(subSettlementId);
		// 更新结算子单金额
		ordSubSettlementDAO.updatePayAmount(subSettlementId);
		// 修改结算单的应结金额
		ordSettlementDAO.updateSettlementPayAmount(settlementId);

		this.log(ors.getSettlementId(), this.LOG_OBJECT_TYPE, "DELETE_SUB_SETTLEMENT", "删除结算子单", log_content.toString());
		return 0;
	}

	@Override
	public Integer deleteSubSettlementItem(Long settlementId, Long[] subSettlementIds, Long[] delIds, String status) {
		OrdSettlement ors = ordSettlementDAO.searchSingleOrdSettlementAmount(settlementId);
		if(ors.getStatus().equals(Constant.SETTLEMENT_STATUS_SETTLEMENTED)){
			return 0;
		}
		
		if (ors.getPayedAmount() != null && ors.getPayedAmount() > 0 && ors.getPayedAmount() < ors.getPayAmount()) {
			return -1;
		}
		// if
		// (!Constant.SETTLEMENT_STATUS_UNSETTLEMENTED.equals(ors.getStatus()))
		// {
		// if (Constant.SETTLEMENT_STATUS_SETTLEMENTED.equals(ors.getStatus()))
		// {
		// return -2;
		// } else {
		// return -3;
		// }
		// }
		if (Constant.SETTLEMENT_STATUS_SETTLEMENTED.equals(ors.getStatus())) {
			return -2;
		}
		// 已打款的结算单删除时，要记录删除记录
		if (ors.getPayedAmount() != null) {
			PermUser user = (PermUser) FinanceContext.getSession().getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
			ordSettlementChangeDAO.insertBatchDel(user.getUserId(), delIds);
		}
		StringBuffer log_content = new StringBuffer("删除结算子单项  ");
		for (Long id : delIds) {
			log_content.append(id).append(" ");
		}
		// 删除结算子单项
		settlementQueueItemDAO.batchInsertSettlementQueueItem(delIds, status);
		ordSubSettlementItemDAO.batchDelete(delIds);

		// 修改结算子单的结算金额
		for (Long ssi : subSettlementIds) {
			ordSubSettlementDAO.updatePayAmount(ssi);
		}

		// 修改结算单的应结算金额
		ordSettlementDAO.updateSettlementPayAmount(settlementId);

		this.log(settlementId, this.LOG_OBJECT_TYPE, "DELETE_SUB_SETTLEMENT_ITEM", "删除结算子单项", log_content.toString());
		return 1;
	}

	@Override
	public Double searchSumprice() {
		return ordSubSettlementItemDAO.searchSumPrice(FinanceContext.getPageSearchContext().getContext());
	}

	@Override
	public Integer modifyPrice(OrdSettlementChange change, Long settlementId, Long subSettlementId) {
		OrdSettlement ors = ordSettlementDAO.searchSingleOrdSettlementAmount(settlementId);
		if(ors.getStatus().equals(Constant.SETTLEMENT_STATUS_SETTLEMENTED)){
			return 0;
		}
		OrdSubSettlementItem item = new OrdSubSettlementItem();
		item.setSubSettlementItemId(change.getSubSettlementItemId());
		change.setSubSettlementId(subSettlementId);
		change.setSettlementId(settlementId);
		item.setRealItemPrice(change.getAmountAfterChange());
		change.setChangetype(Constant.SETTLEMENT_CHANGETYPE_MODIFY);

		PermUser user = (PermUser) FinanceContext.getSession().getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		StringBuffer log_content = new StringBuffer("结算子单项(");
		log_content.append(change.getSubSettlementItemId());
		log_content.append(")的实际结算价从：");
		log_content.append(change.getAmountBeforeChange());
		log_content.append("修改为：");
		log_content.append(change.getAmountAfterChange());
		change.setCreator(user.getUserId());
		// 修改实际结算价
		ordSubSettlementItemDAO.modifyPrice(item);
		// 生成修改记录
		ordSettlementChangeDAO.insert(change);
		// 修改结算子单的结算金额
		ordSubSettlementDAO.updatePayAmount(subSettlementId);
		// 修改结算单的应结算金额
		ordSettlementDAO.updateSettlementPayAmount(settlementId);
		this.log(settlementId, this.LOG_OBJECT_TYPE, "MODIFY_SUB_SETTLEMENT_ITEM", "修改实际结算价", log_content.toString());
		return 1;
	}

	public Integer modifyPrice(Long settlementId, Long subSettlementId, Long metaProductId, Double amount, String remark) {
		OrdSettlement ors = ordSettlementDAO.searchSingleOrdSettlementAmount(settlementId);
		if(ors.getStatus().equals(Constant.SETTLEMENT_STATUS_SETTLEMENTED)){
			return 0;
		}
		PermUser user = (PermUser) FinanceContext.getSession().getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		// 生成修改记录
		ordSettlementChangeDAO.insertBatch(subSettlementId, metaProductId, amount, user.getUserId(), remark);
		// 修改实际结算价
		ordSubSettlementItemDAO.batchModifyPrice(subSettlementId, metaProductId, amount);
		// 修改结算子单的结算金额
		ordSubSettlementDAO.updatePayAmount(subSettlementId);
		// 修改结算单的应结算金额
		ordSettlementDAO.updateSettlementPayAmount(settlementId);
		StringBuffer log_content = new StringBuffer("批量修改结算子单(");
		log_content.append(subSettlementId);
		log_content.append(") 采购产品(" + metaProductId + ")的实际结算价为：");
		log_content.append(amount);
		this.log(settlementId, this.LOG_OBJECT_TYPE, "MODIFY_SUB_SETTLEMENT_ITEM", "批量修改实际结算价", log_content.toString());
		return 1;
	}

	public List<OrdSettlementChange> searchChangeRecord(Integer type, Long id) {
		if (type == 1) {
			return ordSettlementChangeDAO.searchBySettlement(id);
		} else if (type == 2) {
			return ordSettlementChangeDAO.searchBySubSettlement(id);
		} else {
			return null;
		}

	}

	@Override
	public OrdSettlement getOrdSettlementById(Long id) {
		return ordSettlementDAO.getOrdSettlementById(id);
	}

	@Override
	public List<OrderSearchResult> searchOrder(Long settlementId, Long orderId) {
		List<OrderSearchResult> list = ordSubSettlementItemDAO.searchOrder(settlementId, orderId);
		List<OrderSearchResult> list_copy = new ArrayList<OrderSearchResult>(list);
		for (int i = 0; i < list.size(); i++) {
			OrderSearchResult r = list.get(i);
			if (!"UNSETTLEMENTED".equals(r.getSettlementStatus())) {// 不是未结算
				if ("SETTLEMENTING".equals(r.getSettlementStatus()) || "SETTLEMENTED".equals(r.getSettlementStatus())) {// 结算中的时候判断是否存在结算对列项（不能是不结）
					if ("NEVER".equals(r.getStatus()) || StringUtil.isEmptyString(r.getStatus())) {
						list_copy.remove(r);
					}
				} else {
					throw new RuntimeException(r.getSettlementStatus() + "订单状态异常!order_id:" + r.getOrderId());
				}
			}
		}
		list = null;
		return list_copy;
	}

	private Integer payedAmountHandle(OrdSettlement settlement, List<SettlementQueueItem> items) {
		List<Long> settlementQueueItemIds = new ArrayList<Long>();
		List<Long> orderItemMetaIds1 = new ArrayList<Long>();
		List<Long> orderIds = new ArrayList<Long>();
		for (SettlementQueueItem item : items) {
			if (item.getSettlementQueueItemId() != null) {
				settlementQueueItemIds.add(item.getSettlementQueueItemId());
			}
			if (item.getPayedAmount() != null) {
				if (item.getPayedAmount() > 0) {// 付款金额大于0的处理逻辑
					if (item.getSettlementId().longValue() != settlement.getSettlementId()) {
						return -1; // 支付金额大于0 , 不是同一结算单
					}
				} else if (item.getPayedAmount() < 0) {// 付款金额小于0 （抵扣款）的处理
					double pa = 0 - item.getPayedAmount();
					if (pa > settlement.getPayAmount()) {
						return -3;// 抵扣款大于结算单应结算金额
					}
					if (settlement.getPayedAmount() > 0) {// 已打款
						if (pa > settlement.getPayAmount() - settlement.getPayedAmount()) {
							return -2;// 结算单已付款，无法使用抵扣款
						}
					}
				}
			}
			Double amount = 0d;
			Double payedAmount = 0d;
			Double realItemPrice = 0d;
			if (item.getPayedAmount() == null || item.getPayedAmount() == 0) {
				orderItemMetaIds1.add(item.getOrderItemMetaId());
				orderIds.add(item.getOrderId());
				amount = Double.valueOf(String.valueOf(item.getRealSettlementPrice()));
				payedAmount = amount * item.getProductQuantity() * item.getQuantity();
				realItemPrice = amount;
			} else {
				amount = Double.valueOf(String.valueOf(item.getPayedAmount()));
				payedAmount = amount;
				if (item.getPayedAmount() > 0) {
					realItemPrice = amount / item.getProductQuantity() / item.getQuantity();// 实际结算单价
				}
			}
			Double beforeAmount = settlement.getPayAmount();
			settlement.setPayAmount(settlement.getPayAmount() + payedAmount);
			Double afterAmount = settlement.getPayAmount();
			ordSettlementDAO.updateOrdSettlement(settlement);// 修改结算单价格
			// 合并到原结算子单
			OrdSubSettlement subSettlement = null;
			if (item.getPayedAmount() > 0) {
				subSettlement = ordSubSettlementDAO.getOrdSubSettlementById(item.getSubSettlementId());
			} else {
				subSettlement = ordSubSettlementDAO.getOrdSubSettlementBySettlementIdMetaProductId(settlement.getSettlementId(), item.getMetaProductId(),item.getMetaBranchId());
			}
			Long subSettlementId = null;
			if (subSettlement == null) {// 不存在原结算子单（被删掉），则新增一张结算子单
				OrdSubSettlement po = new OrdSubSettlement();
				po.setSettlementId(settlement.getSettlementId());
				po.setMetaProductId(item.getMetaProductId());
				po.setPayAmount(payedAmount);
				po.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
				po.setMetaBranchId(item.getMetaBranchId());
				if (item.getPayedAmount() > 0) {
					subSettlementId = item.getSubSettlementId();
					po.setSubSettlementId(item.getSubSettlementId());
					ordSubSettlementDAO.insertOrdSubSettlementWithId(po);
				} else {
					subSettlementId = ordSubSettlementDAO.insertOrdSubSettlement(po);
				}
			} else {// 存在原结算子单，则合并金额
				subSettlementId = subSettlement.getSubSettlementId();
				subSettlement.setPayAmount(subSettlement.getPayAmount() + payedAmount);
				ordSubSettlementDAO.updateOrdSubSettlement(subSettlement);
			}

			OrdSubSettlementItem slt = new OrdSubSettlementItem();
			slt.setSubSettlementId(subSettlementId);
			slt.setOrderItemMetaId(item.getOrderItemMetaId());
			slt.setItemPrice(Double.valueOf(String.valueOf(item.getRealSettlementPrice())));
			slt.setRealItemPrice(realItemPrice);
			slt.setPayAmount(payedAmount);
			Long subSettlementItemId = ordSubSettlementItemDAO.insertOrdSubSettlementItem(slt);

			if (item.getPayedAmount() > 0) {
				// 记录结算单的金额修改流水
				OrdSettlementChange settlementChange = new OrdSettlementChange();
				settlementChange.setAmountAfterChange(afterAmount);
				settlementChange.setAmountBeforeChange(beforeAmount);
				settlementChange.setChangetype("CREATE_SETTLEMENT_FOR_PAYED_ITEM");
				settlementChange.setCreatetime(new Date());
				settlementChange.setMetaProductId(item.getMetaProductId());
				settlementChange.setOrderId(item.getOrderId());
				settlementChange.setRemark("合并已打款的结算队列项，更改结算单的结算金额");
				settlementChange.setSettlementId(item.getSettlementId());
				settlementChange.setSubSettlementId(item.getSubSettlementId());
				settlementChange.setSubSettlementItemId(subSettlementItemId);
				PermUser user = (PermUser) FinanceContext.getSession().getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
				settlementChange.setCreator(user.getUserId());
				settlementChange.setCreatorName(user.getUserName());
				ordSettlementChangeDAO.insertOrdSettlementChange(settlementChange);
			}
			log(settlement.getSettlementId(),"ORD_SETTLEMENT","ADD","新增结算子单项", null, null, "新增结算子单项,订单号："+item.getOrderId());
		}
		if (settlementQueueItemIds.size() > 0) {
			// 删除结算队列项
			settlementQueueItemDAO.deleteSettlementQueueItemById(list2Array(settlementQueueItemIds));
		}
		
		// 更新订单子子项的结算状态”结算中“
		settlementQueueItemDAO.updateOrderItemMetaSettlementStatus(list2Array(orderItemMetaIds1), "SETTLEMENTING");
		// 更新订单的结算状态”结算中“
		settlementQueueItemDAO.updateOrderSettlementStatus(list2Array(orderIds), "SETTLEMENTING");
		return 1;
	}

	@Override
	public Integer addOrder(Long settlementId, List<String> ids) {
		List<Long> orderItemMetaIds = new ArrayList<Long>();
		List<Long> queueitemIds = new ArrayList<Long>();
		for(String id : ids){
			String[] tmp = id.split("_");
			orderItemMetaIds.add(Long.parseLong(tmp[0]));
			if(!StringUtil.isEmptyString(tmp[1])){
				queueitemIds.add(Long.parseLong(tmp[1]));
			}
		}
		OrdSettlement settlement = ordSettlementDAO.getOrdSettlementById(settlementId);
		if(settlement.getStatus().equals(Constant.SETTLEMENT_STATUS_SETTLEMENTED)){
			return 0;
		}
		
		List<List<Long>> metaIdList = new ArrayList<List<Long>>();
		if(orderItemMetaIds != null && orderItemMetaIds.size() > 0){
			int n = orderItemMetaIds.size() / MAX_LIST_SIZE;
			if(orderItemMetaIds.size() % MAX_LIST_SIZE > 0){
				n = n + 1;
			}
			for(int i=0;i<n;i++){
				int end = (i + 1) * MAX_LIST_SIZE;
				if(end > orderItemMetaIds.size()){
					end = orderItemMetaIds.size();
				}
				metaIdList.add(orderItemMetaIds.subList(i * MAX_LIST_SIZE, end));
			}
		}
		List<List<Long>> queueIdList = new ArrayList<List<Long>>();
		if(queueitemIds != null && queueitemIds.size() > 0){
			int n = queueitemIds.size() / MAX_LIST_SIZE;
			if(queueitemIds.size() % MAX_LIST_SIZE > 0){
				n = n + 1;
			}
			for(int i=0;i<n;i++){
				int end = (i + 1) * MAX_LIST_SIZE;
				if(end > queueitemIds.size()){
					end = queueitemIds.size();
				}
				queueIdList.add(queueitemIds.subList(i * MAX_LIST_SIZE, end)); 
			}
		}
		List<SettlementQueueItem> settlementQueueItems = null;
		settlementQueueItems = settlementQueueItemDAO.getSettleDataByOrderItemMetaId(metaIdList,queueIdList);
		
		return payedAmountHandle(settlement, settlementQueueItems);
	}

	@Override
	public List<OrdSubSettlementItem> exportOrdSubSettlementItem() {
		return ordSubSettlementItemDAO.searchOrdSubSettlementItemList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderProductDetail> exportOrdProductDetail() {
		String exporttype = (String) FinanceContext.getPageSearchContext().getContext().get("exporttype");
		if("2".equals(exporttype)){
			List<Long> idList = ordSettlementDAO.searchOrdSettlementIdList();
			if(idList.size()<=0){
				return new ArrayList<OrderProductDetail>();
			}
			StringBuffer sb = new StringBuffer();
			for(Long id : idList){
				sb.append(id).append(",");
			}
			String ids = sb.substring(0, sb.length()-1);
			FinanceContext.getPageSearchContext().getContext().put("settlementIds", ids);
		}
		return ordSubSettlementItemDAO.searchOrdSubSettlementItemProductList();
	}
	
	@Override
	public Integer modifyTotalPrice(OrdSettlementChange change, Long settlementId) {
		OrdSettlement ors = ordSettlementDAO.searchSingleOrdSettlementAmount(settlementId);
		// 订单状态为 已结算 时，不允许修改结算总价
		if(ors.getStatus().equals(Constant.SETTLEMENT_STATUS_SETTLEMENTED)){
			return 0;
		}
		// 修改实际结算价
		ordSubSettlementItemDAO.modifyTotalPrice(change.getSubSettlementItemId(), change.getAmountAfterChange(), change.getTotalQuantity());
		
		change.setSubSettlementId(change.getSubSettlementId());
		change.setSettlementId(settlementId);
		change.setChangetype(Constant.SETTLEMENT_CHANGETYPE_MODIFY_TOTAL_PRICE);
		PermUser user = (PermUser) FinanceContext.getSession().getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		change.setCreator(user.getUserId());
		change.setAmountBeforeChange(change.getAmountBeforeChange());
		change.setAmountAfterChange(change.getAmountAfterChange());
		// 生成修改记录
		ordSettlementChangeDAO.insert(change);
		// 修改结算子单的结算金额
		ordSubSettlementDAO.updatePayAmount(change.getSubSettlementId());
		// 修改结算单的应结算金额
		ordSettlementDAO.updateSettlementPayAmount(settlementId);
		// 日志内容
		StringBuffer log_content = new StringBuffer("结算子单项(");
		log_content.append(change.getSubSettlementItemId());
		log_content.append(")的实际结算价从：");
		log_content.append(change.getAmountBeforeChange());
		log_content.append("修改为：");
		log_content.append(change.getAmountAfterChange());
		this.log(settlementId, this.LOG_OBJECT_TYPE, "MODIFY_SUB_SETTLEMENT_ITEM", "修改结算总价", log_content.toString());
		return 1;
	}

	@Override
	public Integer modifyPayAmount(Long settlementId) {
		int res = 1;
		try {
			Double amount = ordSubSettlementItemDAO.queryPayAmount(settlementId);
			ordSubSettlementItemDAO.modifyPayAmount(amount, settlementId);
		} catch(Exception ex){
			res = 0;
		}
		
		return res;
	}
	
}
