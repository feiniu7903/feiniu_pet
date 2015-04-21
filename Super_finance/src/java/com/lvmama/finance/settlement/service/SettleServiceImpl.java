package com.lvmama.finance.settlement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.finance.BaseService;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.common.ibatis.po.ComboxItem;
import com.lvmama.finance.settlement.ibatis.dao.OrdSettlementChangeDAO;
import com.lvmama.finance.settlement.ibatis.dao.OrdSettlementDAO;
import com.lvmama.finance.settlement.ibatis.dao.OrdSubSettlementDAO;
import com.lvmama.finance.settlement.ibatis.dao.OrdSubSettlementItemDAO;
import com.lvmama.finance.settlement.ibatis.dao.SettlementQueueItemDAO;
import com.lvmama.finance.settlement.ibatis.po.OrdRefundment;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlement;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementChange;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlement;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlementItem;
import com.lvmama.finance.settlement.ibatis.po.SettlementQueueItem;
import com.lvmama.finance.settlement.ibatis.po.SupSettlementTarget;

@Service
public class SettleServiceImpl extends BaseService implements SettleService{
	private Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SettlementQueueItemDAO settlementQueueItemDAO;
	@Autowired
	private OrdSettlementDAO ordSettlementDAO;
	@Autowired
	private OrdSubSettlementDAO ordSubSettlementDAO;
	@Autowired
	private OrdSubSettlementItemDAO ordSubSettlementItemDAO;
	@Autowired
	private OrdSettlementChangeDAO ordSettlementChangeDAO;
	
	/**
	 * 查询采购产品的分支类型
	 * @param id
	 * @return
	 */
	public List<ComboxItem> getMetaBranchTypeByMetaProductId(Long id){
		return settlementQueueItemDAO.getMetaBranchTypeByMetaProductId(id);
	}
	
	/**
	 * 查询结算队列项/订单子子项
	 */
	public Page<SettlementQueueItem> getSettlementQueueItems(Map<String, Object> map) {
		log.debug("查询结算队列项开始：" + System.currentTimeMillis());
		Page<SettlementQueueItem> page = settlementQueueItemDAO.getSettlementQueueItems(map);
		log.debug("查询结算队列项结束：" + System.currentTimeMillis());
		List<SettlementQueueItem> items = page.getInvdata();
		List<Long> orderItemMetaIds = new ArrayList<Long>();
		if(items != null && items.size() > 0){
			for(SettlementQueueItem item : items){
				orderItemMetaIds.add(item.getOrderItemMetaId());
			}
			List<OrdRefundment> refundments = settlementQueueItemDAO.getRefundmentByOrderItemMetaIds(orderItemMetaIds);
			if(refundments != null){
				for(SettlementQueueItem item : items){
					if(item.getRefundMemo() == null){
						item.setRefundMemo("");
					}
					for(OrdRefundment refundment : refundments){
						if(item.getOrderItemMetaId() - refundment.getOrderItemMetaId() == 0){
							item.setRefundMemo(item.getRefundMemo() + "[" + refundment.getMemo() + "]");
						}
					}
				}
			}
		}
		log.debug("补充查询退款备注：" + System.currentTimeMillis());
		return page;
	}

	/**
	 * 不结
	 * 
	 * @param ids
	 * @return
	 */
	public List<Long> noSettle(List<Long> ids) {
		// 更新结算队列项状态为”不结“
		List<Long> list1 = settlementQueueItemDAO.updateSettlementItemStatus(ids, Constant.SET_SETTLEMENT_ITEM_STATUS.NOSETTLEMENT.name());
		//系统日志
		if(list1 != null){
			for(Long id : list1){
				log(id,"ORD_SETTLEMENT_QUEUE_ITEM","UPDATE","不结操作",null,null,"更新结算队列项状态为不结");
			}
		}
		
		//根据结算队列项ID，更新订单子子项的结算状态为已结算
		for(Long id : ids){
			List<Long> list = new ArrayList<Long>();
			list.add(id);
			settlementQueueItemDAO.updateOrderItemMetaSettlementStatusForNeverSettle(list);
			log(id,"ORD_SETTLEMENT_QUEUE_ITEM","UPDATE","不结操作",null,null,"更新订单子子项结算状态为已结算");
		}
		return ids;
	}

	/**
	 * 缓结
	 * 
	 * @param ids
	 * @return
	 */
	public List<Long> delaySettle(List<Long> ids) {
		List<Long> list = settlementQueueItemDAO.updateSettlementItemStatus(ids, "PAUSE");
		//系统日志
		if(list != null){
			for(Long id : list){
				log(id,"ORD_SETTLEMENT_QUEUE_ITEM","UPDATE","缓结操作",null,null,"更新状态为缓结");
			}
		}
		return list;
	}
	/**
	 * 删除抵扣款
	 */
	public List<Long> deleteSettlementQueueItemForCharge(List<Long> ids){
		for(Long id : ids){
			SettlementQueueItem item = settlementQueueItemDAO.getSettlementQueueItemById(id);
			
			//录入一条负值的打款记录
			OrdSettlementPayment payment = new OrdSettlementPayment();
			payment.setAmount(Double.valueOf(String.valueOf(item.getPayedAmount())));
			payment.setCreatetime(new Date());
			payment.setOperatetime(new Date());
			payment.setPaytype("CASH");
			payment.setRemark("删除抵扣款时产生负值的打款记录");
			payment.setSettlementId(item.getSettlementId());	//结算单ID
			payment.setBank("删除抵扣款");
			Long targetId = settlementQueueItemDAO.getTargetIdByQueueItemId(id);	//查询结算对象ID
			payment.setTargetId(targetId);
			Long insertedId = settlementQueueItemDAO.insertOrdSettlementPayment(payment);
			log(insertedId,"ORD_SETTLEMENT_PAYMENT","ADD","删除抵扣款",null,null,"删除抵扣款，产生负值的打款记录");

			//删除抵扣款
			List<Long> list = new ArrayList<Long>();
			list.add(id);
			settlementQueueItemDAO.deleteSettlementQueueItemById(list2Array(list));
			log(id,"ORD_SETTLEMENT_QUEUE_ITEM","DELETE","删除抵扣款",null,null,"从结算队列项中删除抵扣款");
			
			//更新结算单的打款金额
			OrdSettlement ordSettlement = ordSettlementDAO.getOrdSettlementById(item.getSettlementId());
			Double beforeAmount = ordSettlement.getPayedAmount();
			ordSettlement.setPayedAmount(ordSettlement.getPayedAmount() + item.getPayedAmount());
			Double afterAmount = ordSettlement.getPayedAmount();
			ordSettlementDAO.updateOrdSettlement(ordSettlement);
			log(item.getSettlementId(),"ORD_SETTLEMENT","UPDATE","删除抵扣款",null,null,"删除抵扣款，更新结算单的打款金额");
			
			//结算单修改记录
			OrdSettlementChange settlementChange = new OrdSettlementChange();
			settlementChange.setAmountAfterChange(afterAmount);
			settlementChange.setAmountBeforeChange(beforeAmount);
			settlementChange.setChangetype("DEL_QUEUE_ITEM_FOR_CHARGE");
			settlementChange.setCreatetime(new Date());
			settlementChange.setMetaProductId(item.getMetaProductId());
			settlementChange.setOrderId(item.getOrderId());
			settlementChange.setRemark("删除抵扣款，更改结算单金额");
			settlementChange.setSettlementId(item.getSettlementId());
			settlementChange.setSubSettlementId(item.getSubSettlementId());
			PermUser user = (PermUser) FinanceContext.getSession().getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
			settlementChange.setCreator(user.getUserId());
			settlementChange.setCreatorName(user.getUserName());
			Long tmp = ordSettlementChangeDAO.insertOrdSettlementChange(settlementChange);
			log(tmp,"ORD_SETTLEMENT_CHANGE","ADD","删除抵扣款",null,null,"删除抵扣款，更新结算单的打款金额,记录结算单的修改记录");
		}
		return ids;
	}
	/**
	 * 结算
	 * 
	 * @param metaItemIdList
	 *            订单子子项id数组
	 * @param queueItemIdList
	 * 			  结算队列项id数组
	 */
	public Map<String, Object> settle(List<Long> metaItemIdList,List<Long> queueItemIdList, String settlementType) {
		int errorCode = 0;	//业务异常编码。0无异常；1抵扣款金额过大，合并后是结算单的结算金额<0;2根据参数ids无法查到可结算项
		int counter = 0;		//记录新增的结算单数量
		int mergeCounter = 0;	//记录合并的结算单数量
		Map<Long,List<SettlementQueueItem>> filtedMap = new HashMap<Long,List<SettlementQueueItem>>(); // 记录不能加入结算单的结算队列项
		List<SettlementQueueItem> payedItems = new ArrayList<SettlementQueueItem>();	//记录已付款的结算队列项
		List<OrdSettlement> newSettlements = new ArrayList<OrdSettlement>();			//记录新建的结算单
		List<OrdSettlement> oldSettlements = new ArrayList<OrdSettlement>();			//记录合并的结算单
		
		String paymentTarget = "";
		// 查询结算所需信息，返回结果包含结算对象ID，采购产品ID，采购产品 分支ID，订单子子项ID,实际结算价，打包数量，购买数量，结算队列项ID，已打款金额,订单ID,支付对象
		List<SettlementQueueItem> settlementQueueItems = null;
		settlementQueueItems = settlementQueueItemDAO.getSettleDataByOrderItemMetaId(list2Array(metaItemIdList),list2Array(queueItemIdList));
		
		//没有可结算的结算项
		if(settlementQueueItems == null || settlementQueueItems.size() == 0){
			errorCode = 2;
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("result", "FAULT");
			result.put("errorCode", errorCode);		
			return result;
		}
		
		paymentTarget = settlementQueueItems.get(0).getPaymentTarget();
		//提取已打款的结算队列项，需特殊处理：加入原结算单
		for(SettlementQueueItem item : settlementQueueItems){
			if(item.getPayedAmount() > 0){
				payedItems.add(item);
				log.debug("提取已付款的结算队列项：" + item.getSettlementQueueItemId());
			}
		}
		
		//去掉已付款的结算队列项
		settlementQueueItems.removeAll(payedItems);
		
		if(settlementQueueItems.size() > 0){
			// 分组
			Map<Long, Map<Long, List<SettlementQueueItem>>> baseMap = new HashMap<Long, Map<Long, List<SettlementQueueItem>>>(); // 结构：结算对象ID - 采购产品分支ID- 结算队列项
			for (SettlementQueueItem item : settlementQueueItems) {
				Map<Long, List<SettlementQueueItem>> tmp = baseMap.get(item.getSettlementTargetId());
				if (tmp == null) {
					tmp = new HashMap<Long, List<SettlementQueueItem>>();
					baseMap.put(item.getSettlementTargetId(), tmp);
				}
				if (tmp.get(item.getMetaBranchId()) == null) {
					tmp.put(item.getMetaBranchId(), new ArrayList<SettlementQueueItem>());
				}
				tmp.get(item.getMetaBranchId()).add(item);
			}

			for (Long targetId : baseMap.keySet()) {		//轮训结算对象
				log.debug("生成结算单，结算对象:" + targetId);
				OrdSettlement existedSettlement = ordSettlementDAO.getNoConfirmedSettlementByTargetId(targetId,settlementType); // 查询结算对象的待结算的结算单
				if (null != existedSettlement) { 										// 已存在结算单,合并结算单
					log.debug("已存在结算单:" + existedSettlement.getSettlementId());
					Map<Long, List<SettlementQueueItem>> tmp = baseMap.get(targetId); 	// 结算对象下需结算的结算队列项
				
					// 计算结算子单的结算总额
					Map<Long, Long> map1 = new HashMap<Long, Long>(); 					// 记录结算子单结算金额（结算子单对应的采购产品类别ID ：结算金额）
					for (Long metaBranchId : tmp.keySet()) {
						Long amount = 0L;
						for (SettlementQueueItem item : tmp.get(metaBranchId)) {
							if (isSettlementQueueItemForCharge(item)) { 				// 如果是抵扣款，减掉抵扣款的已打款金额
								amount = amount + item.getPayedAmount();
							} else {
								amount = amount + item.getRealSettlementAmountMoney();
							}
						}
						map1.put(metaBranchId, amount);
					}

					// 计算结算单的结算总额
					Long amount1 = 0L;
					for (Long metaBranchId : map1.keySet()) {
						amount1 = amount1 + map1.get(metaBranchId);
					}
					
					//如果结算单合并后的结算金额小于0，或者（已打款 && amount1<0 && 合并后金额<已打款金额）,此结算对象的结算项不能结算
					if(((existedSettlement.getPayAmount() + amount1) < 0) 
							|| ((amount1 < 0 
									&& existedSettlement.getPayedAmount() != null && existedSettlement.getPayedAmount() > 0)	
									&& (existedSettlement.getPayAmount() + amount1) <  existedSettlement.getPayedAmount())){
						List<SettlementQueueItem> items = new ArrayList<SettlementQueueItem>();
						for(Long id : tmp.keySet()){
							items.addAll(tmp.get(id));
						}
						filtedMap.put(targetId, items);
						continue;
					}
					
					// 更新结算单的结算金额
					existedSettlement.setPayAmount(existedSettlement.getPayAmount() + amount1);
					ordSettlementDAO.updateOrdSettlement(existedSettlement);
					log(existedSettlement.getSettlementId(),"ORD_SETTLEMENT","UPDATE","生成结算单", null, null, "合并结算单的结算金额");
					log.debug("已更新结算单的结算金额：" + existedSettlement.getSettlementId() + "------------" + existedSettlement.getPayAmount());
					if(countMergeSettlement(oldSettlements,existedSettlement)){
						mergeCounter++;
					}

					// 合并/新增 结算子单
					List<OrdSubSettlement> subSettlements = ordSubSettlementDAO.getOrdSubSettlementBySettlementId(existedSettlement.getSettlementId());
					if (subSettlements != null) {
						for (Long metaBranchId : map1.keySet()) {
							Long productId = tmp.get(metaBranchId).get(0).getMetaProductId();		//采购产品类别对应的采购产品id
							boolean isExistSubSettlement = false;						//标识是否存在结算子单
							OrdSubSettlement existedSubSettlement = null;				//记录已存在的结算子单
							for (OrdSubSettlement item : subSettlements) {
								if (metaBranchId.equals(item.getMetaBranchId())) {
									isExistSubSettlement = true;
									existedSubSettlement = item;
									break;
								}
							}
							Long subSettlementId = null;
							if (isExistSubSettlement) { 								// 合并结算子单
								existedSubSettlement.setPayAmount(existedSubSettlement.getPayAmount() + map1.get(metaBranchId));
								ordSubSettlementDAO.updateOrdSubSettlement(existedSubSettlement);
								log(existedSubSettlement.getSubSettlementId(),"ORD_SUB_SETTLEMENT","UPDATE","生成结算单", null, null, "合并结算子单的结算金额");
								subSettlementId = existedSubSettlement.getSubSettlementId();
								log.debug("已合并结算子单:" + subSettlementId + "---------" + map1.get(metaBranchId));
							} else { 													// 新增结算子单
								OrdSubSettlement po = new OrdSubSettlement();
								po.setSettlementId(existedSettlement.getSettlementId());
								po.setMetaProductId(productId);
								po.setMetaBranchId(metaBranchId);
								po.setPayAmount(Double.valueOf(String.valueOf(map1.get(metaBranchId))));
								po.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
								subSettlementId = ordSubSettlementDAO.insertOrdSubSettlement(po);
								log(subSettlementId,"ORD_SUB_SETTLEMENT","INSERT","生成结算单", null, null, "新增结算子单");
								log.debug("已新增结算子单:" + subSettlementId + "---------" + map1.get(metaBranchId));
							}
							// 关联结算子单项
							for (SettlementQueueItem item : tmp.get(metaBranchId)) {
								OrdSubSettlementItem po = new OrdSubSettlementItem();
								po.setSubSettlementId(subSettlementId);
								po.setOrderItemMetaId(item.getOrderItemMetaId());
								po.setItemPrice(Double.valueOf(String.valueOf(item.getRealSettlementPrice())));
								if(isSettlementQueueItemForCharge(item)){	//抵扣款
									po.setRealItemPrice(0D);
									po.setPayAmount(Double.valueOf(String.valueOf(item.getPayedAmount())));
								}else{										//正常数据
									po.setRealItemPrice(Double.valueOf(String.valueOf(item.getRealSettlementPrice())));
									if(item.getTotalSettlementPrice()!=null && item.getTotalSettlementPrice() > 0 ){//实际结算总价大于0的时候
										po.setPayAmount(Double.valueOf(String.valueOf(item.getTotalSettlementPrice())));
									}else{
										po.setPayAmount(Double.valueOf(String.valueOf(item.getRealSettlementPrice() * item.getProductQuantity() * item.getQuantity())));
									}
								}
								Long subSettlementItemId = ordSubSettlementItemDAO.insertOrdSubSettlementItem(po);
								log(subSettlementItemId,"ORD_SUB_SETTLEMENT_ITEM","INSERT","生成结算单", null, null, "新增结算子单项");
								log.debug("插入的结算子单项：" + subSettlementItemId);
							}
						}
					}
				} else { 																// 不存在结算单，新增
					Map<Long, List<SettlementQueueItem>> tmp = baseMap.get(targetId); 	// 结算对象下需结算的结算队列项
					// 计算结算子单的结算总额
					Map<Long, Long> map1 = new HashMap<Long, Long>(); 					// 记录结算子单结算金额（采购产品类别ID ： 结算金额）
					for (Long metaBranchId : tmp.keySet()) {
						Long amount = 0L;
						for (SettlementQueueItem item : tmp.get(metaBranchId)) {
							if (isSettlementQueueItemForCharge(item)) { 				// 如果是抵扣款，减掉抵扣款的已打款金额
								amount = amount + item.getPayedAmount();
							} else {
								amount = amount + item.getRealSettlementAmountMoney();
							}
						}
						map1.put(metaBranchId, amount);
					}

					// 计算结算单的结算总额
					Long amount1 = 0L;
					for (Long metaBranchId : map1.keySet()) {
						amount1 = amount1 + map1.get(metaBranchId);
					}
					
					//如果结算单合并后的结算金额小于0,此结算对象的结算项不能结算
					if(amount1 < 0){
						List<SettlementQueueItem> items = new ArrayList<SettlementQueueItem>();
						for(Long id : tmp.keySet()){
							items.addAll(tmp.get(id));
						}
						filtedMap.put(targetId, items);
						continue;
					}
					
					// 新增结算单
					OrdSettlement po = new OrdSettlement();
					po.setTargetId(targetId);
					po.setPayAmount(Double.valueOf(String.valueOf(amount1)));
					po.setStatus("UNSETTLEMENTED");
					po.setReceiveAmount(0d);
					po.setPayedAmount(0d);
					SupSettlementTarget target = settlementQueueItemDAO.getSettlementTargetById(targetId);
					po.setPaymentTarget(paymentTarget);	
					po.setTargetName(target.getName());
					po.setSettlementPeriod(target.getSettlementPeriod());
					po.setAdvancedDays(target.getAdvancedDays());
					po.setBankAccountName(target.getBankAccountName());
					po.setBankName(target.getBankName());
					po.setBankAccount(target.getBankAccount());
					po.setAlipayAccount(target.getAlipayAccount());
					po.setAlipayName(target.getAlipayName());
					po.setTargetType(target.getType());
					po.setBankLines(target.getBankLines());
					po.setCompanyId(settlementQueueItemDAO.getCompanyIdByTargetId(targetId));
					po.setCreateTime(new Date());
					PermUser user = (PermUser) FinanceContext.getSession().getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
					po.setUserId(user.getUserId());
					po.setSettlementType(settlementType);
					Long insertedSettlementId = ordSettlementDAO.insertOrdSettlement(po);
					log(insertedSettlementId,"ORD_SETTLEMENT","INSERT","生成结算单", null, null, "新增结算单");
					log.debug("已新增结算单：" + insertedSettlementId + "---------" + amount1);
					counter++;
					newSettlements.add(po);

					// 新增结算子单
					for (Long metaBranchId : map1.keySet()) {
						Long productId = tmp.get(metaBranchId).get(0).getMetaProductId();	//采购产品类别对应的采购产品ID
						OrdSubSettlement po1 = new OrdSubSettlement();
						po1.setSettlementId(insertedSettlementId);
						po1.setMetaProductId(productId);
						po1.setMetaBranchId(metaBranchId);
						po1.setPayAmount(Double.valueOf(String.valueOf(map1.get(metaBranchId))));
						po1.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
						Long insertedSubSettlementId = ordSubSettlementDAO.insertOrdSubSettlement(po1);
						log(insertedSubSettlementId,"ORD_SUB_SETTLEMENT","INSERT","生成结算单", null, null, "新增结算子单");
						log.debug("已新增结算子单：" + insertedSubSettlementId + "---------" + map1.get(metaBranchId));

						// 关联结算子单项
						for (SettlementQueueItem item : tmp.get(metaBranchId)) {
							OrdSubSettlementItem po2 = new OrdSubSettlementItem();
							po2.setSubSettlementId(insertedSubSettlementId);
							po2.setOrderItemMetaId(item.getOrderItemMetaId());
							po2.setItemPrice(Double.valueOf(String.valueOf(item.getRealSettlementPrice())));	//结算价取订单子子项中的实际结算价
							if(isSettlementQueueItemForCharge(item)){	//抵扣款
								po2.setRealItemPrice(0D);
								po2.setPayAmount(Double.valueOf(String.valueOf(item.getPayedAmount())));
							}else{										//正常数据
								po2.setRealItemPrice(Double.valueOf(String.valueOf(item.getRealSettlementPrice())));
								if(item.getTotalSettlementPrice()!=null && item.getTotalSettlementPrice() > 0 ){//实际结算总价大于0的时候
									po2.setPayAmount(Double.valueOf(String.valueOf(item.getTotalSettlementPrice())));
								}else{
									po2.setPayAmount(Double.valueOf(String.valueOf(item.getRealSettlementPrice() * item.getProductQuantity() * item.getQuantity())));
								}
							}
							Long subSettlementItemId = ordSubSettlementItemDAO.insertOrdSubSettlementItem(po2);
							log(subSettlementItemId,"ORD_SUB_SETTLEMENT_ITEM","INSERT","生成结算单", null, null, "新增结算子单项");
							log.debug("插入的结算子单项：" + subSettlementItemId);
						}
					}
				}
			}
		}
		
		//把已结算的结算队列项加入原结算单
		for(SettlementQueueItem item : payedItems){
			//合并到原结算单
			OrdSettlement settlement = ordSettlementDAO.getOrdSettlementById(item.getSettlementId());
			Double beforeAmount = settlement.getPayAmount();
			settlement.setPayAmount(settlement.getPayAmount() + item.getPayedAmount());
			Double afterAmount = settlement.getPayAmount();
			ordSettlementDAO.updateOrdSettlement(settlement);
			log(settlement.getSettlementId(), "ORD_SETTLEMENT", "UPDATE", "生成结算单",null,null,"把已付款的结算队列项加入原结算单");
			log.debug("把已结算的结算队列项加入原结算单，结算单ID：" + settlement.getSettlementId());
			if(countMergeSettlement(oldSettlements,settlement)){
				mergeCounter++;
			}
			
			//合并到原结算子单
			OrdSubSettlement subSettlement = ordSubSettlementDAO.getOrdSubSettlementById(item.getSubSettlementId());
			if(subSettlement == null){				//不存在原结算子单（被删掉），则新增一张结算子单
				OrdSubSettlement po = new OrdSubSettlement();
				po.setSubSettlementId(item.getSubSettlementId());
				po.setSettlementId(settlement.getSettlementId());
				po.setMetaProductId(item.getMetaProductId());
				po.setMetaBranchId(item.getMetaBranchId());
				po.setPayAmount(Double.valueOf(String.valueOf(item.getPayedAmount())));
				po.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
				ordSubSettlementDAO.insertOrdSubSettlementWithId(po);
				log(item.getSubSettlementId(),"ORD_SUB_SETTLEMENT","INSERT","生成结算单", null, null, "把已付款的结算队列项加入原结算单，新增结算子单");
				log.debug("把已付款的结算队列项加入原结算单，已新增结算子单:" + item.getSubSettlementId() + "---------" + item.getPayedAmount());
			}else {									//存在原结算子单，则合并金额
				subSettlement.setPayAmount(subSettlement.getPayAmount() + item.getPayedAmount());
				ordSubSettlementDAO.updateOrdSubSettlement(subSettlement);
				log(subSettlement.getSettlementId(),"ORD_SUB_SETTLEMENT","UPDATE","生成结算单", null, null, "把已付款的结算队列项加入原结算单，合并结算子单的结算金额");
				log.debug("把已付款的结算队列项加入原结算单，已合并结算子单:" + subSettlement.getSubSettlementId() + "---------" + item.getPayedAmount());
			}
			//新增结算子单项
			OrdSubSettlementItem po = new OrdSubSettlementItem();
			po.setSubSettlementId(item.getSubSettlementId());
			po.setOrderItemMetaId(item.getOrderItemMetaId());
			po.setItemPrice(Double.valueOf(String.valueOf(item.getRealSettlementPrice())));
			po.setRealItemPrice(Double.valueOf(String.valueOf(item.getPayedAmount() / item.getProductQuantity() / item.getQuantity())));	//实际结算单价
			po.setPayAmount(Double.valueOf(String.valueOf(item.getPayedAmount())));		//结算总价
			Long subSettlementItemId = ordSubSettlementItemDAO.insertOrdSubSettlementItem(po);
			log(subSettlementItemId,"ORD_SUB_SETTLEMENT_ITEM","INSERT","生成结算单", null, null, "把已付款的结算队列项加入原结算单，新增结算子单项");
			log.debug("把已付款的结算队列项加入原结算单，插入的结算子单项：" + subSettlementItemId);
			
			//记录结算单的金额修改流水
			OrdSettlementChange settlementChange = new OrdSettlementChange();
			settlementChange.setOrderItemMetaId(item.getOrderItemMetaId());
			settlementChange.setAmountAfterChange(afterAmount);
			settlementChange.setAmountBeforeChange(beforeAmount);
			settlementChange.setChangetype("CREATE_SETTLEMENT_FOR_PAYED_ITEM");
			settlementChange.setCreatetime(new Date());
			settlementChange.setRemark("合并已打款的结算队列项，更改结算单的结算金额");
			settlementChange.setSettlementId(item.getSettlementId());
			settlementChange.setSubSettlementId(item.getSubSettlementId());
			settlementChange.setSubSettlementItemId(subSettlementItemId);
			PermUser user = (PermUser) FinanceContext.getSession().getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
			settlementChange.setCreator(user.getUserId());
			Long tmp = ordSettlementChangeDAO.insertOrdSettlementChange(settlementChange);
			log(tmp,"ORD_SETTLEMENT_CHANGE","ADD","生成结算单",null,null,"把已付款的结算队列项加入原结算单，更新结算单的结算金额");
		}
		
		settlementQueueItems.addAll(payedItems);	//把已打款的结算队列项加入结果集
		//去掉未结算的结算项
		for(Long key : filtedMap.keySet()){
			settlementQueueItems.removeAll(filtedMap.get(key));
		}
		
		//提取结算队列项ID、订单子子项ID、订单ID
		List<Long> queueItemIds = new ArrayList<Long>();
		List<Long> orderItemMetaIds1 = new ArrayList<Long>();
		List<Long> orderIds = new ArrayList<Long>();
		for (SettlementQueueItem item : settlementQueueItems) {
			queueItemIds.add(item.getSettlementQueueItemId());
			orderItemMetaIds1.add(item.getOrderItemMetaId());
			if(!orderIds.contains(item.getOrderId())){
				orderIds.add(item.getOrderId());
			}
		}
		// 删除结算队列项
		settlementQueueItemDAO.deleteSettlementQueueItemById(list2Array(queueItemIds));
		if(queueItemIds != null && queueItemIds.size() > 0){
			for(Long id : queueItemIds){
				log(id,"ORD_SETTLEMENT_QUEUE_ITEM","DELETE","生成结算单", null, null, "删除结算队列项");
			}
		}
		log.debug("删除结算队列项：" + queueItemIds);
		
		//更新订单子子项的结算状态”结算中“
		settlementQueueItemDAO.updateOrderItemMetaSettlementStatus(list2Array(orderItemMetaIds1),"SETTLEMENTING");
		if(orderItemMetaIds1 != null && orderItemMetaIds1.size() > 0){
			for(Long id : orderItemMetaIds1){
				log(id,"ORD_ORDER_ITEM_META","UPDATE","生成结算单", null, null, "更新订单子子项结算状态为结算中");
			}
		}
		log.debug("更新订单子子项的结算状态”结算中“：" + orderItemMetaIds1);
		
		//更新订单的结算状态”结算中“
		settlementQueueItemDAO.updateOrderSettlementStatus(list2Array(orderIds),"SETTLEMENTING");
		if(orderIds != null && orderIds.size() > 0){
			for(Long id : orderIds){
				log(id,"ORD_ORDER","UPDATE","生成结算单", null, null, "更新订单结算状态为结算中");
			}
		}
		log.debug("更新订单的结算状态”结算中“：" + orderIds);
		
		Map<String, Object> result = new HashMap<String, Object>();
		//如果所有的结算项都被过滤掉，没有结算项参与结算，则提示操作失败
		if(counter == 0 && mergeCounter == 0 && payedItems.size() == 0 && filtedMap.keySet().size() > 0){
			result.put("result", "FAULT");
			result.put("errorCode", 3);
			result.put("filtedMap", filtedMap);
			return result;
		}
		result.put("result", "SUCCESS");
		result.put("counter", counter);
		result.put("newSettlements", newSettlements);
		result.put("mergeCounter", mergeCounter);
		result.put("oldSettlements", oldSettlements);
		if(filtedMap.keySet().size() > 0){
			result.put("filtedMap", filtedMap);
		}
		result.put("payedItems", payedItems);
		return result;
	}
	/**
	 * 全部生成结算单
	 * @param map	查询条件
	 * @return
	 */
	public Map<String, Object> settlementAll(Map<String, Object> map,String settlementType){
		//获取结算队列/订单子子项ID
		List<SettlementQueueItem> items = settlementQueueItemDAO.getSettlementQueueItemOnlyIds(map);
		if(items == null || items.size() == 0){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("result", "FAULT");
			result.put("errorCode", 2);		//没有可结算的结算项
			return result;
		}
		List<Long> orderItemsIds = new ArrayList<Long>();
		List<Long> queueItemIds = new ArrayList<Long>();
		for(SettlementQueueItem item : items){
			if(item.getOrderItemMetaId() != null){
				orderItemsIds.add(item.getOrderItemMetaId());
			}
			if(item.getSettlementQueueItemId() != null){
				queueItemIds.add(item.getSettlementQueueItemId());
			}
		}
		return settle(orderItemsIds,queueItemIds,settlementType);
	}

	// 判断结算队列项是否为抵扣款
	private boolean isSettlementQueueItemForCharge(SettlementQueueItem item) {
		return item.getPayedAmount() < 0;
	}
	//记录合并的结算单
	private boolean countMergeSettlement(List<OrdSettlement> list, OrdSettlement settlement){
		boolean b = false;
		for(OrdSettlement item : list){
			if(item.getSettlementId() - settlement.getSettlementId() == 0){
				b = true;
			}
		}
		if(!b){
			list.add(settlement);
			return true;
		}
		return false;
	}
}

