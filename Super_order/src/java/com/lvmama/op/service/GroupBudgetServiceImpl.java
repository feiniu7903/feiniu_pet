package com.lvmama.op.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.op.FinGroupSettlement;
import com.lvmama.comm.bee.po.op.OpGroupBudget;
import com.lvmama.comm.bee.po.op.OpGroupBudgetFixed;
import com.lvmama.comm.bee.po.op.OpGroupBudgetProd;
import com.lvmama.comm.bee.po.op.OpGroupBudgetProdRefund;
import com.lvmama.comm.bee.po.op.OpOtherIncoming;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.op.ProductOrderDetail;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.IGroupBudgetService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.vo.FincConstant;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.OptionItem;
import com.lvmama.op.dao.GroupBudgetDAO;
import com.lvmama.op.dao.OpTravelGroupDAO;
import com.lvmama.order.service.Query;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;

public class GroupBudgetServiceImpl implements IGroupBudgetService{
	private static final Log log = LogFactory.getLog(GroupBudgetServiceImpl.class);
	
	private GroupBudgetDAO groupBudgetDAO;	
	private ComLogDAO comLogDAO;
	/**
	 * 查询服务.
	 */
	private transient Query queryService;
	private OpTravelGroupDAO opTravelGroupDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private ProductTimePriceLogic productTimePriceLogic;
	
	/**
	 * 查询团列表结果集总数
	 * @param param
	 * @return
	 */
	public Long getGroupListCount(Map<String, String> param){
		return groupBudgetDAO.getGroupListCount(param);
	}
	
	/**
	 * 查询团列表分页结果集
	 * @param param
	 * @return
	 */
	public List<OpTravelGroup> getGroupList(Map<String, String> param){
		return groupBudgetDAO.getGroupList(param);
	}
	/**
	 * 查询团列表，不带分页条件
	 */
	public List<OpTravelGroup> getGroupListForExport(Map<String, String> map){
		return groupBudgetDAO.getGroupListForExport(map);
	}
	
	/**
	 * 查询团预算
	 */
	public OpGroupBudget getGroupBudgetByGroupCode(String travelGroupCode){
		return groupBudgetDAO.getGroupBudgetByGroupCode(travelGroupCode);
	}
	/**
	 * 从团信息中查询团产品项
	 */
	public List<OpGroupBudgetProd> getGroupProductByGroupCode(String travelGroupCode){
		return groupBudgetDAO.getGroupProductByGroupCode(travelGroupCode);
	}
	
	/**
	 * 查询团预算产品项
	 */
	public List<OpGroupBudgetProd> getGroupBudgetProductByGroupCode(String travelGroupCode, String groupProdType){
		return groupBudgetDAO.getGroupBudgetProductByGroupCode(travelGroupCode, groupProdType);
	}
	/**
	 * 查询团预算固定成本项
	 */
	public List<OpGroupBudgetFixed> getGroupBudgetFixedByGroupCode(String travelGroupCode){
		return groupBudgetDAO.getGroupBudgetFixedByGroupCode(travelGroupCode);
	}
	
	/**
	 * 查询团是否有订单存在
	 * @param groupCode
	 * @return
	 */
	public int hasOrderByGroupCode(String groupCode){
		return groupBudgetDAO.hasOrderByGroupCode(groupCode);
	}
	/**
	 * 根据团号计算实际成本表
	 * @param param
	 * @return
	 */
	public OpGroupBudget countFinalBudgetByTravelCode(String travelCode){
		if(SynchronizedLock.isOnDoingMemCached("COUNTFINALBUDGETBYTRAVELCODE_"+travelCode)){
			return null;
		}
		OpTravelGroup group = groupBudgetDAO.getOpTravelGroupByCode(travelCode);
		OpGroupBudget groupBudget = getGroupBudgetByGroupCode(travelCode);
		if (null == groupBudget && FincConstant.GROUP_SETTLEMENT_STATUS_UNCOST.equals(group.getSettlementStatus())) {
			groupBudget = new OpGroupBudget();
			groupBudget.setTravelGroupCode(group.getTravelGroupCode());
			groupBudget.setTravelGroupId(group.getTravelGroupId());
			groupBudget.setBgPersons(0l);
			groupBudget.setSalePrice(Double.parseDouble("0"));
			groupBudget.setBgTotalCosts(Double.parseDouble("0"));
			groupBudget.setBgPerCosts(Double.parseDouble("0"));
			groupBudget.setBgIncoming(Double.parseDouble("0"));
			groupBudget.setBgProfit(Double.parseDouble("0"));
			groupBudget.setBgProfitRate(Double.parseDouble("0"));
			Long id = groupBudgetDAO.insertGroupBudget(groupBudget);
			groupBudget.setBudgetId(id);
		}
		//生成/更新 产品成本
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("travelGroupCode", travelCode);
		//查询状态为正常的，且没有退款明细的订单子子项
		List<OpGroupBudgetProd> groupBudgetProdList = groupBudgetDAO.getGroupBudgetProdListFromOrderByGroupCode(parameterMap);
		List<OpGroupBudgetProdRefund> groupBudgetProdRefundList = groupBudgetDAO.selectGroupBudgetProdRefundListFromOrderByParam(parameterMap);
		//循环有退款的订单子子项
		//存放退款相关的结算价 key-groupBudgetProdRefundList value - 结算价
		Map<String,OpGroupBudgetProdRefund> refund_map = new HashMap<String,OpGroupBudgetProdRefund>();
		for(OpGroupBudgetProdRefund obpr : groupBudgetProdRefundList){
			String key = obpr.getPrdBranchId().toString();
			OpGroupBudgetProdRefund val = refund_map.get(obpr.getPrdBranchId().toString());
			if(val == null){
				val = obpr;
			}
			double subtotalCostsFc = val.getSubtotalCostsFc();
			Double settlement_price = null;
			if("VISITOR_LOSS".equals(obpr.getRefundType())){
				settlement_price = obpr.getRefundAmount();
			}else{
				settlement_price = obpr.getBgCosts()* obpr.getQuantity() - obpr.getRefundAmount();
			}
			settlement_price = settlement_price < 0 ? 0 : settlement_price;
			subtotalCostsFc += settlement_price;
			val.setSubtotalCostsFc(subtotalCostsFc);
			refund_map.put(key, val);
		}
		for (OpGroupBudgetProd opGroupBudgetProd : groupBudgetProdList){//遍历订单产品
			String key = opGroupBudgetProd.getPrdBranchId().toString();
			OpGroupBudgetProdRefund v = refund_map.get(key);
			if(v!=null){
				opGroupBudgetProd.setSubtotalCostsFc(opGroupBudgetProd.getSubtotalCostsFc()+v.getSubtotalCostsFc());
				if(!Constant.ORDER_STATUS.CANCEL.equals(v.getOrderStatus())){
					opGroupBudgetProd.setQuantity(opGroupBudgetProd.getQuantity()+v.getQuantity());
				}
				refund_map.remove(key);
			}
		}
		if(refund_map.size()>0){
			for(Map.Entry<String, OpGroupBudgetProdRefund> entry : refund_map.entrySet())   
			{   
				OpGroupBudgetProdRefund v = entry.getValue();
				if(Constant.ORDER_STATUS.CANCEL.equals(v.getOrderStatus())){
					v.setQuantity(0l);
					v.setBgCosts(0d);
				}
				groupBudgetProdList.add(v);  
			}  
		}
		for (OpGroupBudgetProd opGroupBudgetProd : groupBudgetProdList){		
			//遍历订单产品
			double rate = groupBudgetDAO.getLatestExchangeRateByCurrency(opGroupBudgetProd.getCurrency());	//最新汇率
			boolean isExistsFinalBudgetProd = true;							//标记是否存在实际产品成本
			if(FincConstant.GROUP_SETTLEMENT_STATUS_COSTED.equals(group.getSettlementStatus())){	//已做成本,更新
				Map<String,Object> finalProdParam = new HashMap<String,Object>();
				finalProdParam.put("travelGroupCode", travelCode);
				finalProdParam.put("type", "COST");
				finalProdParam.put("prodBranchId", opGroupBudgetProd.getPrdBranchId());
				List<OpGroupBudgetProd> finalBudgetProdList = groupBudgetDAO.getGroupBudgetProductByParam(finalProdParam);
				if(finalBudgetProdList == null || finalBudgetProdList.size() == 0){				//实际成本表中不存在采购产品，新增此采购产品为实际成本项
					isExistsFinalBudgetProd = false;
				}else{																			//实际成本表中存在采购产品，更新采购产品成本
					isExistsFinalBudgetProd = true;
					OpGroupBudgetProd finalBudgetProd= finalBudgetProdList.get(0);
					finalBudgetProd.setBgCosts(opGroupBudgetProd.getBgCosts());
					finalBudgetProd.setQuantity(opGroupBudgetProd.getQuantity());
					finalBudgetProd.setCurrency(opGroupBudgetProd.getCurrency());
	//				finalBudgetProd.setExchangeRate(rate);
					finalBudgetProd.setSubtotalCosts(opGroupBudgetProd.getSubtotalCostsFc() * rate);
					finalBudgetProd.setSubtotalCostsFc(opGroupBudgetProd.getSubtotalCostsFc());
					Map<String, Object> map = new HashMap<String, Object>();
					map = new HashMap<String, Object>();
					map.put("budgetItemType", "PRODUCT");
					map.put("budgetItemId", finalBudgetProd.getItemId());
					FinGroupSettlement fs = groupBudgetDAO.getFinGroupSettlement(map);
					if("PARTPAY".equals(finalBudgetProd.getPayStatus()) || "PAYED".equals(finalBudgetProd.getPayStatus())){ //成本项打款状态为部分支付或已打款
						if(finalBudgetProd.getPayAmount() < finalBudgetProd.getSubtotalCostsFc()){//已打款金额小于修改后的金额，打款状态变成部分支付
							finalBudgetProd.setPayStatus("PARTPAY");
							if("PAYED".equals(fs.getPaymentStatus()) && fs.getSubtotalCosts() < fs.getPayAmount()){
								fs.setSubtotalCosts(fs.getPayAmount());
							}
							if(fs.getPayAmount() < finalBudgetProd.getSubtotalCostsFc() && finalBudgetProd.getSubtotalCostsFc() < fs.getSubtotalCosts()){
								fs.setSubtotalCosts(finalBudgetProd.getSubtotalCostsFc());
								fs.setPaymentStatus("PARTPAY");
							}
						}else{//打款金额大于修改后的金额，打款状态变成已支付
							finalBudgetProd.setPayStatus("PAYED");
							fs.setSubtotalCosts(finalBudgetProd.getSubtotalCostsFc());
							fs.setPaymentStatus("PAYED");
						}
					}
					
					if("PARTREQPAY".equals(finalBudgetProd.getPayStatus()) || "REQPAY".equals(finalBudgetProd.getPayStatus())){
						if(finalBudgetProd.getSubtotalCostsFc() > fs.getSubtotalCosts()){
							finalBudgetProd.setPayStatus("PARTREQPAY");
						}else{
							fs.setSubtotalCosts(finalBudgetProd.getSubtotalCostsFc());//成本项已催款（未打款）时调低结算金额，需要把团单项结算记录的结算价更新
							finalBudgetProd.setPayStatus("REQPAY");
						}
					}
					if(fs!=null){
						groupBudgetDAO.updateFinGroupSettlement(fs);
					}
					finalBudgetProd.setSupplierId(opGroupBudgetProd.getSupplierId());
					finalBudgetProd.setTargetId(opGroupBudgetProd.getTargetId());
					finalBudgetProd.setPaymentType(opGroupBudgetProd.getPaymentType());
					groupBudgetDAO.updateOpGroupBudgetProd(finalBudgetProd);
					
					//如果已打款，重新计算单项人民币总额
					if("PARTPAY".equals(finalBudgetProd.getPayStatus()) || "PAYED".equals(finalBudgetProd.getPayStatus())){
						groupBudgetDAO.updateBudgetItemSubtotalCosts(finalBudgetProd.getItemId(), "PRODUCT", finalBudgetProd.getSubtotalCostsFc()*100,rate);
					}
				}
			}
			if(FincConstant.GROUP_SETTLEMENT_STATUS_UNCOST.equals(group.getSettlementStatus()) || !isExistsFinalBudgetProd){		//未做成本 或 实际成本表中不存在此采购产品，生成
				opGroupBudgetProd.setBudgetId(groupBudget.getBudgetId());
				opGroupBudgetProd.setExchangeRate(rate);
				opGroupBudgetProd.setSubtotalCosts(opGroupBudgetProd.getSubtotalCostsFc() * rate);
				opGroupBudgetProd.setType("COST");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("travelGroupCode", opGroupBudgetProd.getTravelGroupCode());
				map.put("prodBranchId", opGroupBudgetProd.getPrdBranchId());
				map.put("type", "COST");
				List<OpGroupBudgetProd> list = groupBudgetDAO.getGroupBudgetProductByParam(map);
				OpGroupBudgetProd budgetProd = null;	//预算产品
				if(list != null && list.size() > 0){		//存在预算产品
					budgetProd = list.get(0);
					//同步支付状态
					opGroupBudgetProd.setPayStatus(budgetProd.getPayStatus());
					if ("PARTPAY".equals(budgetProd.getPayStatus()) || "PAYED".equals(budgetProd.getPayStatus())) {	//已打款，重置打款状态
						if(budgetProd.getPayAmount() < opGroupBudgetProd.getSubtotalCostsFc()){
							opGroupBudgetProd.setPayStatus("PARTPAY");
						}else{
							opGroupBudgetProd.setPayStatus("PAYED");
						}
						opGroupBudgetProd.setPayAmount(budgetProd.getPayAmount());	//同步支付金额
						opGroupBudgetProd.setExchangeRate(budgetProd.getExchangeRate());	//同步最后一次打款汇率
					}
					if("PARTREQPAY".equals(budgetProd.getPayStatus()) || "REQPAY".equals(budgetProd.getPayStatus())){	//已催款，重置催款状态
						map = new HashMap<String, Object>();
						map.put("budgetItemId", budgetProd.getItemId());
						map.put("budgetItemType", "PRODUCT");
						FinGroupSettlement s = groupBudgetDAO.getFinGroupSettlement(map);
						if(opGroupBudgetProd.getSubtotalCostsFc() > s.getSubtotalCosts()){
							opGroupBudgetProd.setPayStatus("PARTREQPAY");
						}else{
							opGroupBudgetProd.setPayStatus("REQPAY");
						}
					}
					if(!"NOPAY".equals(budgetProd.getPayStatus())){			//已催款或付款，更新订单和订单子子项结算状态
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("groupCode", travelCode);
						map2.put("status", "SETTLEMENTING");
						map2.put("metaBranchId", opGroupBudgetProd.getPrdBranchId());
						groupBudgetDAO.updateOrderItemMetaSettlementStatus(map2);
						groupBudgetDAO.updateOrderSettlementStatus(map2);
					}
					//同步是否加入总成本
					opGroupBudgetProd.setIsInCost(budgetProd.getIsInCost());
				}else{
					opGroupBudgetProd.setPayStatus("NOPAY");
					Long insrtId = groupBudgetDAO.insertGroupBudgetProd(opGroupBudgetProd);
				}
				/* 团结算修改，注释掉的原因是会产生多条产品成本记录
				Long insrtId = groupBudgetDAO.insertGroupBudgetProd(opGroupBudgetProd);
				*/
				
				//存在预算产品，且 预算催过款，则更新催款记录关联的itemID
				/*if(budgetProd != null && !"NOPAY".equals(budgetProd.getPayStatus())){
					map = new HashMap<String, Object>();
					map.put("newBudgetItemId", insrtId);
					map.put("oldBudgetItemId", budgetProd.getItemId());
					map.put("budgetItemType", "PRODUCT");
					groupBudgetDAO.updateFinGroupSettlementBudgetItemId(map);
					
				}*/
				
				//如果已打款，重新计算单项人民币总额
				/*if ("PARTPAY".equals(opGroupBudgetProd.getPayStatus()) || "PAYED".equals(opGroupBudgetProd.getPayStatus())) {
					groupBudgetDAO.updateBudgetItemSubtotalCosts(insrtId, "PRODUCT", opGroupBudgetProd.getSubtotalCostsFc()*100,rate);
				}*/
			}
		}
		//如果未做过成本，则取出预算时的固定成本，保存为实际固定成本
		/*if(OpTravelGroup.SETTLEMENT_STATUS.BUDGETED.name().equals(group.getSettlementStatus())){	
			Map map = new HashMap();
			map.put("travelGroupCode", travelCode);
			map.put("type", "BUDGET");
			List<OpGroupBudgetFixed> groupBudgetFixedList = groupBudgetDAO.getGroupBudgetFixedByParam(map);
			if(groupBudgetFixedList != null && groupBudgetFixedList.size() > 0){
				for (OpGroupBudgetFixed opGroupBudgetFixed : groupBudgetFixedList){
					double rate = groupBudgetDAO.getLatestExchangeRateByCurrency(opGroupBudgetFixed.getCurrency());	//最新汇率
					opGroupBudgetFixed.setType("COST");
					Long oldBudgetItemId = opGroupBudgetFixed.getItemId();
					Long insrtId = groupBudgetDAO.insertOpGroupBudgetFixed(opGroupBudgetFixed);
					//预算已催款，更新催款记录关联的itemID
					if(!"NOPAY".equals(opGroupBudgetFixed.getPayStatus())){
						map = new HashMap<String, Object>();
						map.put("newBudgetItemId", insrtId);
						map.put("oldBudgetItemId", oldBudgetItemId);
						map.put("budgetItemType", "FIXED");
						groupBudgetDAO.updateFinGroupSettlementBudgetItemId(map);
					}
					//如果已打款，重新计算单项人民币总额,更新汇率
					if ("PARTPAY".equals(opGroupBudgetFixed.getPayStatus()) || "PAYED".equals(opGroupBudgetFixed.getPayStatus())) {
						groupBudgetDAO.updateBudgetItemSubtotalCosts(insrtId, "FIXED", opGroupBudgetFixed.getSubtotalCostsFc()*100,rate);
					}
				}
			}
		}*/
		
		//如果未做成本，则取团信息中的支付成功人数作为实际人数
		if(FincConstant.GROUP_SETTLEMENT_STATUS_UNCOST.equals(group.getSettlementStatus())){
			groupBudget.setActPersons(group.getPaySuccessNum());
		}

		//计算实际收入
		double sumOtherIncoming = groupBudgetDAO.getSumOtherIncomingByGroupCode(parameterMap);	//附加收入
		Double orderAmount = groupBudgetDAO.getTotalOrderActualPay(travelCode);
		orderAmount = orderAmount == null ? 0D : orderAmount;
		Long totalInsuranceImcoming = groupBudgetDAO.getTotalInsuranceIncomingByGroupCode(travelCode); 		//团订单中的保险总收入
		groupBudget.setActIncoming(sumOtherIncoming + (orderAmount - totalInsuranceImcoming) / 100 );
		
		//计算活动转让
		Map<String, Object> map = new HashMap<String, Object>();
		map = new HashMap<String, Object>();
		map.put("travelGroupCode", travelCode);
		Double allowanceSum = groupBudgetDAO.getOrderAllowanceSum(map);
		groupBudget.setActAllowance(allowanceSum != null?allowanceSum:0);
		
		//计算总实际成本
		map = new HashMap<String, Object>();
		map.put("travelGroupCode", travelCode);
		map.put("type", "COST");
		double totalProductCosts = 0d;
		List<OpGroupBudgetProd> prods = groupBudgetDAO.getGroupBudgetProductByParam(map);
		for(OpGroupBudgetProd prod : prods){
			if("Y".equals(prod.getIsInCost())){
				totalProductCosts += prod.getSubtotalCosts();
			}
		}
		double sumGroupBudgetFixed = groupBudgetDAO.getSumGroupBudgetFixedByGroupCode(map);	//总固定成本
		groupBudget.setActTotalCosts(totalProductCosts + sumGroupBudgetFixed);
		
		//计算实际毛利润
		groupBudget.setActProfit(groupBudget.getActIncoming() - groupBudget.getActTotalCosts());
		
		//计算实际毛利率		
		if(groupBudget.getActIncoming() > 0){
			groupBudget.setActProfitRate(groupBudget.getActProfit() / groupBudget.getActIncoming());	
		}else{
			groupBudget.setActProfitRate(0.0);
		}
		
		//更新团预算实际成本
		groupBudgetDAO.updateGroupBudget(groupBudget);
		
		SynchronizedLock.releaseMemCached("COUNTFINALBUDGETBYTRAVELCODE_"+travelCode);
		return groupBudget;
	}
	
	/**
	 * 新增固定成本项
	 */
	public Long insertOpGroupBudgetFixed(OpGroupBudgetFixed item){
		Long id = groupBudgetDAO.insertOpGroupBudgetFixed(item);
		return id;
	}
	/**
	 * 更新固定成本项
	 */
	public void updateOpGroupBudgetFixed(OpGroupBudgetFixed item){
		if(SynchronizedLock.isOnDoingMemCached("UPDATEOPGROUPBUDGETFIXED_"+Long.toString(item.getBudgetId())+Long.toString(item.getItemId()))){
			return;
		}
		//如果已打款，则更新打款状态
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", item.getItemId());
		OpGroupBudgetFixed fixed = groupBudgetDAO.getGroupBudgetFixedByParam(map).get(0);
		map = new HashMap<String, Object>();
		map.put("budgetItemType", "FIXED");
		map.put("budgetItemId", item.getItemId());
		FinGroupSettlement fs = groupBudgetDAO.getFinGroupSettlement(map);
		if("PARTPAY".equals(fixed.getPayStatus()) || "PAYED".equals(fixed.getPayStatus())){ //成本项打款状态为部分支付或已打款
			if(fixed.getPayAmount() < item.getSubtotalCostsFc()){//已打款金额小于修改后的金额，打款状态变成部分支付
				item.setPayStatus("PARTPAY");
				if("PAYED".equals(fs.getPaymentStatus()) && fs.getSubtotalCosts() < fs.getPayAmount()){
					fs.setSubtotalCosts(fs.getPayAmount());
				}
				if(fs.getPayAmount() < item.getSubtotalCostsFc() && item.getSubtotalCostsFc() < fs.getSubtotalCosts()){
					fs.setSubtotalCosts(item.getSubtotalCostsFc());
					fs.setPaymentStatus("PARTPAY");
				}
			}else{//打款金额大于修改后的金额，打款状态变成已支付
				item.setPayStatus("PAYED");
				fs.setSubtotalCosts(item.getSubtotalCostsFc());
				fs.setPaymentStatus("PAYED");
			}
		}
		
		if("PARTREQPAY".equals(fixed.getPayStatus()) || "REQPAY".equals(fixed.getPayStatus())){
			if(item.getSubtotalCostsFc() > fs.getSubtotalCosts()){
				item.setPayStatus("PARTREQPAY");
			}else{
				fs.setSubtotalCosts(item.getSubtotalCostsFc());//成本项已催款（未打款）时调低结算金额，需要把团单项结算记录的结算价更新
				item.setPayStatus("REQPAY");
			}
		}
		groupBudgetDAO.updateOpGroupBudgetFixed(item);
		if(fs!=null){
			groupBudgetDAO.updateFinGroupSettlement(fs);
		}
		//如果已催款，则更新催款记录的打款状态
//		if(fs != null){
//			if("NOPAY".equals(fs.getPaymentStatus())){
//				fs.setPaymentStatus("NOPAY");
//			}else if("PARTPAY".equals(fs.getPaymentStatus()) || "PAYED".equals(fs.getPaymentStatus())){
//				if(fs.getPayAmount() < fs.getSubtotalCosts()){
//					fs.setPaymentStatus("PARTPAY");
//				}else{
//					fs.setPaymentStatus("PAYED");
//				}
//			}
//			groupBudgetDAO.updateFinGroupSettlement(fs);
//		}
		//如果已打款，重新计算单项人民币总额
		if("PARTPAY".equals(item.getPayStatus()) || "PAYED".equals(item.getPayStatus())){
			double rate = groupBudgetDAO.getLatestExchangeRateByCurrency(item.getCurrency());	//最新汇率
			groupBudgetDAO.updateBudgetItemSubtotalCosts(item.getItemId(), "FIXED", item.getSubtotalCostsFc()*100,rate);
		}
		SynchronizedLock.releaseMemCached("UPDATEOPGROUPBUDGETFIXED_"+Long.toString(item.getBudgetId())+Long.toString(item.getItemId()));
	}
	/**
	 * 删除固定成本项
	 */
	public void deleteOpGroupBudgetFixed(Long id){
		groupBudgetDAO.deleteOpGroupBudgetFixed(id);
	}
	/**
	 * 保存预算表
	 */
	public Long saveGroupBudget(OpGroupBudget budget){
		Long id = groupBudgetDAO.insertGroupBudget(budget);
		/*if(budget.getBudgetId() == null){	//未做预算
			id = groupBudgetDAO.insertGroupBudget(budget);
			//生成产品明细
			List<OpGroupBudgetProd> prods = groupBudgetDAO.getGroupProductByGroupCode(budget.getTravelGroupCode());
			for(OpGroupBudgetProd prod : prods){
				prod.setBudgetId(id);
				prod.setTravelGroupCode(budget.getTravelGroupCode());
				prod.setQuantity(budget.getBgPersons() / (prod.getAudltQuantity() + prod.getChildQuantity()));
				prod.setSubtotalCostsFc(prod.getBgCosts() * prod.getQuantity());
				prod.setSubtotalCosts(prod.getSubtotalCostsFc() * prod.getExchangeRate());
				prod.setType("BUDGET");
				prod.setPayStatus("NOPAY");
				if(budget.getProds() != null && budget.getProds().size() > 0){
					for(OpGroupBudgetProd prod2 : budget.getProds()){
						if(prod.getPrdBranchId().equals(prod2.getPrdBranchId())){
							prod.setIsInCost(prod2.getIsInCost());
						}
					}
				}
				groupBudgetDAO.insertGroupBudgetProd(prod);
			}
			//保存成本明细
			if(budget.getFixeds() != null && budget.getFixeds().size() > 0){
				for(OpGroupBudgetFixed fixed : budget.getFixeds()){
					fixed.setBudgetId(id);
					fixed.setTravelGroupCode(budget.getTravelGroupCode());
					fixed.setType("BUDGET");
					fixed.setPayAmount(0d);
					fixed.setPayStatus("NOPAY");
					Long fixedId = groupBudgetDAO.insertOpGroupBudgetFixed(fixed);
				}
			}
			//更新团信息的结算状态
			//groupBudgetDAO.updateOpTravelGroupSettlementStatus(budget.getTravelGroupCode(),OpTravelGroup.SETTLEMENT_STATUS.BUDGETED.name());
		}else{
			//更新预算单
			groupBudgetDAO.updateGroupBudget(budget);
			//更新产品成本数量和预算总成本
			List<OpGroupBudgetProd> prods = groupBudgetDAO.getGroupBudgetProductByGroupCode(budget.getTravelGroupCode(), "BUDGET");
			for(OpGroupBudgetProd prod : prods){
				prod.setQuantity(budget.getBgPersons() / (prod.getAudltQuantity() + prod.getChildQuantity()));
				prod.setSubtotalCostsFc(prod.getBgCosts() * prod.getQuantity());
				prod.setSubtotalCosts(prod.getSubtotalCostsFc() * prod.getExchangeRate());
				groupBudgetDAO.updateGroupBudgetProd(prod);
			}
			id = budget.getBudgetId();
		}*/
		return id;
	}
	/**
	 * 包装查询条件.
	 * @return CompositeQuery.
	 */
	private CompositeQuery getCompositeQuery(String travelCode) {
		CompositeQuery compositeQuery = new CompositeQuery();		
		OrderContent orderContent = new OrderContent();		
		//团号 
		if(StringUtils.isNotEmpty(travelCode)){
			orderContent.setTravelCode(travelCode);
		}
		compositeQuery.setContent(orderContent);
		return compositeQuery;
	}
	
	/**
	 * 查询团信息
	 */
	public OpTravelGroup getOpTravelGroupByCode(String code){
		return groupBudgetDAO.getOpTravelGroupByCode(code);
	}
	
	/**
	 * 产品成本催款，更新产品成本结算状态
	 */
	public void updateBudgetProdSettlementStatus(Map<String, Object> map){
		insertRequirePayProduct(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("budgetItemId", map.get("itemId"));
		map1.put("budgetItemType", "PRODUCT");
		FinGroupSettlement s = groupBudgetDAO.getFinGroupSettlement(map1);
		map1 = new HashMap<String, Object>();
		map1.put("itemId", map.get("itemId"));
		OpGroupBudgetProd prod = groupBudgetDAO.getGroupBudgetProductByParam(map1).get(0);
		if("NOPAY".equals(prod.getPayStatus()) || "PARTREQPAY".equals(prod.getPayStatus())){
			if(prod.getSubtotalCostsFc() > s.getSubtotalCosts()){
				map.put("payStatus", "PARTREQPAY");
			}else{
				map.put("payStatus", "REQPAY");
			}
		}else{
			map.put("payStatus", prod.getPayStatus());
		}
		groupBudgetDAO.updateBudgetProdSettlementStatus(map);
		//团预算表、实际产品成本催款，更新订单的结算状态
		//if("FINAL_BUDGET".equals(map.get("reqType"))){
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("groupCode", map.get("groupCode"));
			map2.put("status", "SETTLEMENTING");
			map2.put("metaBranchId", prod.getPrdBranchId());
			groupBudgetDAO.updateOrderItemMetaSettlementStatus(map2);
			groupBudgetDAO.updateOrderSettlementStatus(map2);
		//}
		
	}
	
	/**
	 * 固定成本催款，更新预算产品固定成本结算状态
	 */
	public void	updateBudgetFixedSettlementStatus(Map<String, Object> map){
		insertRequirePayFixed(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("budgetItemId", map.get("itemId"));
		map1.put("budgetItemType", "FIXED");
		FinGroupSettlement s = groupBudgetDAO.getFinGroupSettlement(map1);
		map1 = new HashMap<String, Object>();
		map1.put("itemId", map.get("itemId"));
		OpGroupBudgetFixed fixed = groupBudgetDAO.getGroupBudgetFixedByParam(map1).get(0);
		if("NOPAY".equals(fixed.getPayStatus()) || "PARTREQPAY".equals(fixed.getPayStatus())){
			if(fixed.getSubtotalCostsFc() > s.getSubtotalCosts()){
				map.put("payStatus", "PARTREQPAY");
			}else{
				map.put("payStatus", "REQPAY");
			}
		}else{
			map.put("payStatus", fixed.getPayStatus());
		}
		groupBudgetDAO.updateBudgetFixedSettlementStatus(map);
	}
	//插入产品催款记录。如果已存在，则累加金额。itemId：产品成本明细ID
	private void insertRequirePayProduct(Map<String, Object> map){
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("budgetItemId", map.get("itemId"));
		map1.put("budgetItemType", "PRODUCT");
		FinGroupSettlement s = groupBudgetDAO.getFinGroupSettlement(map1);
		if(s != null){	//存在催款记录
			s.setSubtotalCosts(s.getSubtotalCosts() + Double.parseDouble((String)map.get("reqPayAmount")));
			if("PARTPAY".equals(s.getPaymentStatus()) || "PAYED".equals(s.getPaymentStatus())){
				if(s.getSubtotalCosts() > s.getPayAmount()){
					s.setPaymentStatus("PARTPAY");
				}else{
					s.setPaymentStatus("PAYED");
				}
			}
			groupBudgetDAO.updateFinGroupSettlement(s);
		}else{		//新增催款记录
			List<OpGroupBudgetProd> prods = groupBudgetDAO.getGroupBudgetProductByParam(map);
			OpGroupBudgetProd prod = prods.get(0);
			FinGroupSettlement settlement = new FinGroupSettlement();
			settlement.setTravelGroupCode(prod.getTravelGroupCode());
			settlement.setBudgetItemId(prod.getItemId());
			settlement.setBudgetItemType("PRODUCT");
			settlement.setBudgetItemName(prod.getProductName());
			settlement.setPrdBranchName(prod.getPrdBranchName());
			settlement.setSupplierId(prod.getSupplierId());
			settlement.setTargetId(prod.getTargetId());
			settlement.setPaymentType(prod.getPaymentType());
			settlement.setCurrency(prod.getCurrency());
			settlement.setExchangeRate(prod.getExchangeRate());
			settlement.setSubtotalCosts(Double.parseDouble((String)map.get("reqPayAmount")));
			settlement.setCreatetime(new Date());
			settlement.setPaymentStatus("NOPAY");
			settlement.setPayAmount(0);
			settlement.setIsUseAdvancedeposits((String)map.get("isUseAdvance"));
			groupBudgetDAO.insertRequirePay(settlement);
		}
	}
	//插入固定成本催款记录。如果已存在，则累加。itemId：固定成本明细ID
	private void insertRequirePayFixed(Map<String, Object> map){
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("budgetItemId", map.get("itemId"));
		map1.put("budgetItemType", "FIXED");
		FinGroupSettlement s = groupBudgetDAO.getFinGroupSettlement(map1);
		if(s != null){	//存在催款记录
			s.setSubtotalCosts(s.getSubtotalCosts() + Double.parseDouble((String)map.get("reqPayAmount")));
			if("PARTPAY".equals(s.getPaymentStatus()) || "PAYED".equals(s.getPaymentStatus())){
				if(s.getSubtotalCosts() > s.getPayAmount()){
					s.setPaymentStatus("PARTPAY");
				}else{
					s.setPaymentStatus("PAYED");
				}
			}
			groupBudgetDAO.updateFinGroupSettlement(s);
		}else{		//新增催款记录
			List<OpGroupBudgetFixed> fixeds = groupBudgetDAO.getGroupBudgetFixedByParam(map);
			OpGroupBudgetFixed fixed = fixeds.get(0);
			FinGroupSettlement settlement = new FinGroupSettlement();
			settlement.setTravelGroupCode(fixed.getTravelGroupCode());
			settlement.setBudgetItemId(fixed.getItemId());
			settlement.setBudgetItemName(fixed.getCostsItemName());
			settlement.setBudgetItemType("FIXED");
			settlement.setSupplierId(fixed.getSupplierId());
			settlement.setTargetId(fixed.getTargetId());
			settlement.setPaymentType(fixed.getPaymentType());
			settlement.setCurrency(fixed.getCurrency());
			settlement.setExchangeRate(fixed.getExchangeRate());
			settlement.setSubtotalCosts(Double.parseDouble((String)map.get("reqPayAmount")));
			settlement.setCreatetime(new Date());
			settlement.setPaymentStatus("NOPAY");
			settlement.setPayAmount(0);
			settlement.setIsUseAdvancedeposits((String)map.get("isUseAdvance"));
			groupBudgetDAO.insertRequirePay(settlement);
		}
	}
	//更新延迟时间
	public void updateDelayTime(Map<String, Object> map){
		groupBudgetDAO.updateDelayTime(map);
	}
	
	/**
	 * 查询固定成本项
	 */
	public OpGroupBudgetFixed getBudgetFixedByItemId(Long itemId){
		return groupBudgetDAO.getBudgetFixedByItemId(itemId);
	}
	
	/**
	 * 查询附加收入
	 */
	public List<OpOtherIncoming> getGroupIncomingByGroupCode(String travelGroupCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("travelGroupCode", travelGroupCode);
		return groupBudgetDAO.getOtherIncomingByGroupCode(map);
	}
	/**
	 * 查询实际产品成本
	 */
	public List<OpGroupBudgetProd> getGroupFinalBudgetProductByGroupCode(String travelGroupCode){
		Map map = new HashMap();
		map.put("travelGroupCode", travelGroupCode);
		map.put("type", "COST");
		return groupBudgetDAO.getGroupBudgetProductByParam(map);
	}
	/**
	 * 查询实际固定成本
	 */
	public List<OpGroupBudgetFixed> getGroupFinalBudgetFixedByGroupCode(String travelGroupCode){
		Map map = new HashMap();
		map.put("travelGroupCode", travelGroupCode);
		map.put("type", "COST");
		return groupBudgetDAO.getGroupBudgetFixedByParam(map);
	}
	/**
	 * 保存附加收入
	 */
	public Long saveOtherIncoming(OpOtherIncoming incoming){
		if(incoming.getId() == null){
			return groupBudgetDAO.insertOtherIncoming(incoming);
		}else{
			groupBudgetDAO.updateOtherIncoming(incoming);
			return incoming.getId();
		}
	}
	/**
	 * 删除附加收入
	 */
	public void deleteOtherIncoming(Long id){
		groupBudgetDAO.deleteOtherIncoming(id);
	}
	/**
	 * 查询附加收入
	 */
	public List<OpOtherIncoming> getOtherIncoming(Map<String, Object> map){
		return groupBudgetDAO.getOtherIncoming(map);
	}
	public void log(String type,String name,Long groupId, String content,String userName){
		ComLog log = new ComLog();
		log.setLogType(type);
		log.setLogName(name);
		log.setObjectId(groupId);
		log.setContent(content);
		log.setOperatorName(userName);
		log.setCreateTime(new Date());
		comLogDAO.insert(log);
	}
	
	/**
	 * 查询订单明细分页
	 */
	public Page getProductOrderDetailsByPage(Page page,Map<String, Object> map){
		long count = groupBudgetDAO.getProductOrderDetailsCount(map);
		page.setTotalResultSize(count);
		map.put("startRow", page.getStartRows());
		map.put("endRow", page.getEndRows());
		List<ProductOrderDetail> list = groupBudgetDAO.getProductOrderDetails(map);
		page.setItems(list);
		return page;
	}
	
	/**
	 * 查询供应商预付款总额
	 */
	public Double getSupAdvanceAmount(Long supplierId,String currencyType){
		return groupBudgetDAO.getSupAdvanceAmount(supplierId,currencyType);
	}
	/**
	 * 查询明细催款金额
	 */
	public Double getReqPayAmount(Long itemId,String type){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("budgetItemId", itemId);
		map.put("budgetItemType", type);
		FinGroupSettlement s = groupBudgetDAO.getFinGroupSettlement(map);
		if(s == null){
			return 0d;
		}else{
			return s.getSubtotalCosts();
		}
	}
	
	/**
	 * 取消团后处理团预算数据
	 */
	public void cancelTravelGroupHandler(Long travelGroupId){
		//删除未打款的明细项
		groupBudgetDAO.deleteFinGroupSettlementByGroupId(travelGroupId);
		//已打款的明细项生成抵扣款
		groupBudgetDAO.createMoneyForChargeByGroupId(travelGroupId);	
	}
	
	/**
	 * 根据订单信息自动生成实际成本表
	 */
	public void autoCreateFinalBudget(String travelCode){
		//生成团预算表
		OpGroupBudget budget = new OpGroupBudget();
		budget.setTravelGroupCode(travelCode);
		OpTravelGroup group = groupBudgetDAO.getOpTravelGroupByCode(travelCode);
		budget.setTravelGroupId(group.getTravelGroupId());
		budget.setBgIncoming(0D);
		budget.setBgPerCosts(0D);
		budget.setBgPersons(0L);
		budget.setBgProfit(0D);
		budget.setBgProfitRate(0D);
		budget.setBgTotalCosts(0D);
		//团销售价
		budget.setSalePrice(0D);
		ProdProductBranch branch=prodProductBranchDAO.getProductDefaultBranchByProductId(group.getProductId());
		if(branch != null){
			TimePrice tp=productTimePriceLogic.calcProdTimePrice(branch.getProdBranchId(), group.getVisitTime());
			if(tp!=null){
				budget.setSalePrice(tp.getPrice() * 1.0 / 100);
			}
		}
		Long budgetId = groupBudgetDAO.insertGroupBudget(budget);
		
		//生成团实际成本明细
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("travelGroupCode", travelCode);
		List<OpGroupBudgetProd> groupBudgetProdList = groupBudgetDAO.getGroupBudgetProdListFromOrderByGroupCode(parameterMap);
		for (OpGroupBudgetProd opGroupBudgetProd : groupBudgetProdList){			//遍历订单产品
			double rate = groupBudgetDAO.getLatestExchangeRateByCurrency(opGroupBudgetProd.getCurrency());	//最新汇率
			opGroupBudgetProd.setBudgetId(budgetId);
			opGroupBudgetProd.setExchangeRate(rate);
			opGroupBudgetProd.setSubtotalCosts(opGroupBudgetProd.getSubtotalCostsFc() * rate);
			opGroupBudgetProd.setType("COST");
			opGroupBudgetProd.setPayStatus("NOPAY");
			opGroupBudgetProd.setPayAmount(0D);
			groupBudgetDAO.insertGroupBudgetProd(opGroupBudgetProd);	
		}
		
		//计算实际人数
		Long num = groupBudgetDAO.getActOrderPersonNum(travelCode);
		budget.setActPersons(num);
		//计算实际收入
		CompositeQuery compositeQuery1 = new CompositeQuery();
		compositeQuery1.getContent().setTravelCode(travelCode);
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
		compositeQuery1.setStatus(orderStatus);
		OrdOrderSum ordOrderSum1=queryService.compositeQueryOrdOrderSum(compositeQuery1);
		CompositeQuery compositeQuery2 = new CompositeQuery();
		compositeQuery2.getContent().setTravelCode(travelCode);
		orderStatus = new OrderStatus();
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		compositeQuery2.setStatus(orderStatus);
		OrdOrderSum ordOrderSum2=queryService.compositeQueryOrdOrderSum(compositeQuery2);
		budget.setActIncoming((ordOrderSum1.getActualPay() + ordOrderSum2.getActualPay()) * 1.0 / 100);
		//计算活动转让
		Map<String, Object> map = new HashMap<String, Object>();
		map = new HashMap<String, Object>();
		map.put("travelGroupCode", travelCode);
		Double allowanceSum = groupBudgetDAO.getOrderAllowanceSum(map);
		budget.setActAllowance(allowanceSum != null?allowanceSum:0);
		//计算总实际成本
		map = new HashMap<String, Object>();
		map.put("travelGroupCode", travelCode);
		map.put("type", "COST");
		double totalProductCosts = 0d;
		List<OpGroupBudgetProd> prods = groupBudgetDAO.getGroupBudgetProductByParam(map);
		for(OpGroupBudgetProd prod : prods){
			totalProductCosts += prod.getSubtotalCosts();
		}
		budget.setActTotalCosts(totalProductCosts);
		//计算实际毛利润
		budget.setActProfit(budget.getActIncoming() - budget.getActTotalCosts());
		//计算实际毛利率		
		if(budget.getActIncoming() > 0){
			budget.setActProfitRate(budget.getActProfit() / budget.getActIncoming());	
		}else{
			budget.setActProfitRate(0.0);
		}
		//更新团预算实际成本
		groupBudgetDAO.updateGroupBudget(budget);
		
		//更新团信息的结算状态为“已做成本”
		groupBudgetDAO.updateOpTravelGroupSettlementStatus(travelCode, "COSTED");
	}
	
	/**
	 * 查询明细项币种
	 */
	public String getCurrencyByItemId(Long itemId, String itemType){
		if("PRODUCT".equals(itemType)){
			return groupBudgetDAO.getProductCurrencyByItemId(itemId);
		}else if("FIXED".equals(itemType)){
			return groupBudgetDAO.getFixedCurrencyByItemId(itemId);
		}
		return null;
	}
	
	/**
	 * 更新实际人数
	 */
	public void updateActPersons(Map<String, Object> map){
		groupBudgetDAO.updateActPersons(map);
	}
	
	/**
	 * 是否加入总成本--团预算表
	 */
	public void updateIsInCostBudget(Map<String, Object> map){
		groupBudgetDAO.updateIsInCostBudget(map);
	}
	/**
	 * 是否加入总成本
	 */
	public void updateIsInCost(Map<String, Object> map){
		groupBudgetDAO.updateIsInCost(map);
	}
	
	public List<String> getGroupCode(Map<String, Object> map){
		return groupBudgetDAO.getGroupCode(map);
	}

	public List<ComLog> getLogs(Long groupId,String logType){
		return groupBudgetDAO.getLogs(groupId,logType);
	}
	
	public List<OptionItem> getFixedOptions(){
		return groupBudgetDAO.getFixedOptions();
	}
	public List<OptionItem> getCurrencyOptions(){
		 List<OptionItem> ois =  groupBudgetDAO.getCurrencyOptions();
		 for(OptionItem oi : ois ){
			 oi.setLabel(com.lvmama.comm.vo.Constant.FIN_CURRENCY.getCnName(oi.getLabel()));
		 }
		 return ois;
	}
	public List<OptionItem> getProductManagerForAutocomplete(String term){
		return groupBudgetDAO.getProductManagerForAutocomplete(term);
	}
	public List<OptionItem> getSupplierForAutocomplete(String term){
		return groupBudgetDAO.getSupplierForAutocomplete(term);
	}
	public List<OptionItem> getTargetForAutocomplete(Map<String, Object> map){
		return groupBudgetDAO.getTargetForAutocomplete(map);
	}
	
	/**
	 * 生成产品明细
	 * @param prod
	 */
	public Long insertGroupBudgetProd(OpGroupBudgetProd prod){
		return groupBudgetDAO.insertGroupBudgetProd(prod);
	}
	
	/**
	 * 更新结算状态
	 * @param paraStatus
	 */
	public void updateToCosted(String paraGroupTravelCode,
			String paraStatus) {
		// 未做过成本，更新团信息的结算状态为“已做成本”
		if (FincConstant.GROUP_SETTLEMENT_STATUS_UNCOST.equals(paraStatus)) {
			groupBudgetDAO.updateOpTravelGroupSettlementStatus(
					paraGroupTravelCode,
					FincConstant.GROUP_SETTLEMENT_STATUS_COSTED);
		}
	}
	
	public GroupBudgetDAO getGroupBudgetDAO() {
		return groupBudgetDAO;
	}

	public void setGroupBudgetDAO(GroupBudgetDAO groupBudgetDAO) {
		this.groupBudgetDAO = groupBudgetDAO;
	}
 
	public OpTravelGroupDAO getOpTravelGroupDAO() {
		return opTravelGroupDAO;
	}

	public void setOpTravelGroupDAO(OpTravelGroupDAO opTravelGroupDAO) {
		this.opTravelGroupDAO = opTravelGroupDAO;
	}
	

	public ProdProductBranchDAO getProdProductBranchDAO() {
		return prodProductBranchDAO;
	}

	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	public ProductTimePriceLogic getProductTimePriceLogic() {
		return productTimePriceLogic;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	public void setQueryService(Query queryService) {
		this.queryService = queryService;
	}
	
}
