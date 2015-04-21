package com.lvmama.back.sweb.ord;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.Pagination;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaPrice;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdSettlementPriceRecord;
import com.lvmama.comm.bee.service.ord.ModifySettlementPriceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.fin.SetSettlementItemService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_CHANGE_RESULT;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_REASON;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_RECORD_STATUS;

/**
 * 修改结算价 Action
 * @author zhangwenjun
 *
 */
@Results({
	@Result(name = "index", location = "/WEB-INF/pages/back/ord/modifySettlementPrice/modify_settlement_price_index.jsp"),
	@Result(name = "history", location = "/WEB-INF/pages/back/ord/modifySettlementPrice/history_record.jsp"),
	@Result(name = "verify", location = "/WEB-INF/pages/back/ord/modifySettlementPrice/verify_record.jsp")
	})
public class ModifySettlementPriceAction extends BackBaseAction{
	
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1219012267482045364L;

	private ModifySettlementPriceService modifySettlementPriceService;
	private TopicMessageProducer orderMessageProducer;
	private SetSettlementItemService setSettlementItemService;
	private ComLogService comLogService;
	
	/**
	 * 订单远程服务接口
	 */
	private OrderService orderServiceProxy;
	private ORD_SETTLEMENT_PRICE_CHANGE_TYPE[] changeTypeList;
	private ORD_SETTLEMENT_PRICE_CHANGE_RESULT[] changeResultList;
	private ORD_SETTLEMENT_PRICE_REASON[] resultList;
	private ORD_SETTLEMENT_PRICE_RECORD_STATUS[] statusList;
	
	// 采购产品ID
	private Long metaProductId;
	private String productName;
	// 采购产品类别ID
	private Long productBranchId;
	// 供应商ID
	private Long supplierId;
	private String supplierName;
	// 订单号
	private String orderId;
	// 游玩时间
	private String visitDateStart;
	private String visitDateEnd;
	// 下单时间
	private String createOrderTimeBegin;
	private String createOrderTimeEnd;
	// 支付时间
	private String payTimeStart;
	private String payTimeEnd;
	// 结算对象ID
	private Long targetId;
	private String targetName;
	// 修改时间
	private String modifyDateBegin;
	private String modifyDateEnd;
	// 操作结果（调高或是调低）
	private String operateType;
	// 修改原因
	private String reason;
	// 操作类型（单价或是总价）
	private List<String> changeType;
	// 审核状态
	private String status;
	private List<String> settlementPay;
	
	private String ordItemId4add;
	private String settlementPrice4add;
	private String changeType4add;
	private String reason4add;
	private String remark4add;
	private Float priceBeforeUpdate4add;
	
	private Long recordId;

	private Page pagination = new Page();
	
	private Long totalSettlePriceBegin;
	private Long totalSettlePriceEnd;
	private String refundment;
	private String travelGroupCode;
	private String virtual;
	/**
	 * 进入修改结算价主页面
	 * @return
	 */
	@Action("/ord/modifySettlementPriceIndex")
	public String index(){
		statusList = ORD_SETTLEMENT_PRICE_RECORD_STATUS.values();
		
		return "index";
	}

	/**
	 * 进入历史记录页面
	 * @return
	 */
	@Action(value = "/ord/historyRecord")
	public String history() {
		changeTypeList = ORD_SETTLEMENT_PRICE_CHANGE_TYPE.values();
		changeResultList = ORD_SETTLEMENT_PRICE_CHANGE_RESULT.values();
		resultList = ORD_SETTLEMENT_PRICE_REASON.values();
		changeType = new ArrayList<String>();
		changeType.add("UNIT_PRICE");
		changeType.add("TOTAL_PRICE");
		settlementPay = new ArrayList<String>();
		settlementPay.add("0");
		settlementPay.add("1");
		
		return "history";
	}

	/**
	 * 进入审核列表页面
	 * @return
	 */
	@Action(value = "/ord/verifyList")
	public String verifyList() {
		
		return "verify";
	}

	/**
	 * 修改结算价主页面查询列表
	 * @return
	 */
	@Action("/ord/queryList")
	public String queryList(){
		Boolean hasParam = Boolean.FALSE;
		if(orderId != null && !orderId.equals("")){
			hasParam = Boolean.TRUE;
		}
		if(visitDateStart != null && !visitDateStart.equals("") && visitDateEnd != null && !visitDateEnd.equals("")){
			hasParam = Boolean.TRUE;
		}
		if(createOrderTimeBegin != null && !createOrderTimeBegin.equals("") && createOrderTimeEnd != null && !createOrderTimeEnd.equals("")){
			hasParam = Boolean.TRUE;
		}

		if (!hasParam) {
			this.getRequest().setAttribute("hasParamMessage", "请填写相关的必填查询条件！");
			return "index";
		}
		
		// 封装查询条件
		Map<String,Object> searchConditions = initParam();
		// 执行查询
		String success = toResult(searchConditions, true);
		
		return success;
	}

	/**
	 * 封装查询条件
	 * @return
	 */
	private Map<String,Object> initParam() {
		Map<String,Object> searchConditions = new HashMap<String,Object>();
		if(null != this.metaProductId){
			searchConditions.put("productId", metaProductId);
		}
		if (null != this.productBranchId) {
 			searchConditions.put("productBranchId",productBranchId );
		} 
		if(null != this.supplierId){
			searchConditions.put("supplierId", supplierId);
		}
		if(StringUtils.isNotEmpty(this.orderId)){
			searchConditions.put("orderId", orderId);
		}
		if (StringUtils.isNotEmpty(this.visitDateStart)) {
 			searchConditions.put("visitDateStart", visitDateStart);
		} 
		if (StringUtils.isNotEmpty(this.visitDateEnd)) {
 			searchConditions.put("visitDateEnd", visitDateEnd);
		} 
		if (StringUtils.isNotEmpty(this.createOrderTimeBegin)) {
 			searchConditions.put("createOrderTimeBegin", createOrderTimeBegin);
		}
		if (StringUtils.isNotEmpty(this.createOrderTimeEnd)) {
 			searchConditions.put("createOrderTimeEnd", createOrderTimeEnd);
		} 
		if (StringUtils.isNotEmpty(this.payTimeStart)) {
 			searchConditions.put("payTimeStart", payTimeStart);
		} 
		if (StringUtils.isNotEmpty(this.payTimeEnd)) {
 			searchConditions.put("payTimeEnd", payTimeEnd);
		} 
		if (null != this.targetId) {
 			searchConditions.put("targetId", targetId);
		} 
		if (StringUtils.isNotEmpty(this.modifyDateBegin)) {
 			searchConditions.put("modifyDateBegin", modifyDateBegin);
		} 
		if (StringUtils.isNotEmpty(this.modifyDateEnd)) {
 			searchConditions.put("modifyDateEnd", modifyDateEnd);
		} 
		if (StringUtils.isNotEmpty(this.operateType)) {
 			searchConditions.put("operateType", operateType);
		}
		if (StringUtils.isNotEmpty(this.reason)) {
 			searchConditions.put("reason", reason);
		}
		if (null != this.changeType) {
 			searchConditions.put("changeType", changeType);
		}
		if (StringUtils.isNotEmpty(this.status)) {
 			searchConditions.put("status", status);
		}
		if (null != this.settlementPay) {
 			searchConditions.put("settlementPay", settlementPay);
		}
		if(null!=totalSettlePriceBegin){
			searchConditions.put("totalSettlePriceBegin", totalSettlePriceBegin);
		}
		if(null!=totalSettlePriceEnd){
			searchConditions.put("totalSettlePriceEnd", totalSettlePriceEnd);
		}
		if(StringUtils.isNotEmpty(refundment)){
			searchConditions.put("refundment", refundment);
		}
		return searchConditions;
	}
	
	/**
	 * 执行查询
	 * @param searchConditions
	 * @param autoReq
	 * @return
	 */
	private String toResult(Map<String,Object> searchConditions, boolean autoReq){
		Integer totalRowCount = modifySettlementPriceService.selectRowCount(searchConditions);
		pagination.setCurrentPage(super.page);
		pagination.setTotalResultSize(totalRowCount);
		searchConditions.put("skipResults", pagination.getStartRows());
		searchConditions.put("maxResults", pagination.getEndRows());
		pagination.buildUrl(getRequest());
		List<OrdOrderItemMetaPrice> productList = modifySettlementPriceService.selectByParms(searchConditions);
		pagination.setItems(productList);
		resultList = ORD_SETTLEMENT_PRICE_REASON.values();
		statusList = ORD_SETTLEMENT_PRICE_RECORD_STATUS.values();
		
		return "index";
	}
	
	/**
	 * 查看结算价修改的历史记录
	 * @return
	 */
	@Action("/ord/queryHistoryRecordList")
	public String queryHistoryRecordList(){
		Map<String, Object> searchConditions = this.initParam();
		String result = this.queryHistoryRecords(searchConditions);
		return result;
	}

	/**
	 * 查看审核列表
	 * @return
	 */
	@Action("/ord/queryVerifyList")
	public String queryVerifyList(){
		Map<String, Object> searchConditions = this.initParam();
		String result = this.queryVerifyRecords(searchConditions);
		return result;
	}
	
	/**
	 * 查询审核列表
	 * @param map
	 * @return
	 */
	private String queryVerifyRecords(Map<String, Object> map){
		Integer totalRowCount = modifySettlementPriceService.queryVerifyListCount(map);
		pagination.setCurrentPage(super.page);
		pagination.setTotalResultSize(totalRowCount);
		map.put("skipResults", pagination.getStartRows());
		map.put("maxResults", pagination.getEndRows());
		List<OrdOrderItemMetaPrice> historyRecordList = modifySettlementPriceService.queryVerifyList(map);
		pagination.setItems(historyRecordList);
		pagination.buildUrl(getRequest());
		
		return "verify";
	}
	
	/**
	 * 查询结算价修改历史记录
	 * @param map
	 * @return
	 */
	private String queryHistoryRecords(Map<String, Object> map){
		Integer totalRowCount = modifySettlementPriceService.queryHistoryRecordCount(map);
		pagination.setCurrentPage(super.page);
		pagination.setTotalResultSize(totalRowCount);
		map.put("skipResults", pagination.getStartRows());
		map.put("maxResults", pagination.getEndRows());
		List<OrdOrderItemMetaPrice> historyRecordList = modifySettlementPriceService.queryHistoryRecordList(map);
		pagination.setItems(historyRecordList);
		pagination.buildUrl(getRequest());
		changeTypeList = ORD_SETTLEMENT_PRICE_CHANGE_TYPE.values();
		changeResultList = ORD_SETTLEMENT_PRICE_CHANGE_RESULT.values();
		resultList = ORD_SETTLEMENT_PRICE_REASON.values();
		
		return "history";
	}
	
	/**
	 * 导出结算价修改的历史记录
	 * @return
	 */
	@Action("/ord/exportHistoryRecordList")
	public void exportHistoryRecordList(){
		Map<String, Object> searchConditions = this.initParam();
		List<OrdOrderItemMetaPrice> list = modifySettlementPriceService.exportHistoryRecordList(searchConditions);
		
		this.output(list, "/WEB-INF/resources/template/settlement_price_record.xls");
	}
	
	/**
	 * 导出
	 * @param list
	 * @param template
	 */
	private void output(List list,String template){
		FileInputStream fin=null;
		OutputStream os=null;
		try
		{
			File templateResource = ResourceUtil.getResourceFile(template);
			Map beans = new HashMap();
			beans.put("list", list);
			XLSTransformer transformer = new XLSTransformer();
			File destFileName = new File(Constant.getTempDir() + "/excel"+new Date().getTime()+".xls");
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName.getAbsolutePath());
					
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + destFileName.getName());
			os=getResponse().getOutputStream();
			fin=new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}

	/**
	 * 修改结算价 
	 */
	@Action("/ord/updateSettlementPrice")
	public void updateSettlementPrice(){
		// 执行修改结算价
		boolean result = modifySettlementPriceService.updateSettlementPrice(Long.parseLong(ordItemId4add), Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE.valueOf(changeType4add), Constant.ORD_SETTLEMENT_PRICE_REASON.valueOf(reason4add), remark4add, PriceUtil.convertToFen(settlementPrice4add), getSessionUser().getUserName());

		if(result){
			List<Object> list = new ArrayList<Object>();
			list.add(Long.parseLong(ordItemId4add));
			try{
				if(changeType4add.equals(ORD_SETTLEMENT_PRICE_CHANGE_TYPE.UNIT_PRICE.toString())){
					orderMessageProducer.sendMsg(MessageFactory.newModifySettlementPricesMessage(list,this.getSessionUser().getUserName()));
				}else{
					orderMessageProducer.sendMsg(MessageFactory.newModifyTotalSettlementPricesMessage(list,this.getSessionUser().getUserName()));
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 批量修改时，判断是否存在退款单
	 */
	@Action("/ord/confirmSettltmentPrice")
	public void existRefundment(){
		int result = 0;
		if(null != ordItemId4add){
			String[] idArray = ordItemId4add.split(",");
			for(int i=0; i<idArray.length; i++){
				Long orderItemMetaId = Long.parseLong(idArray[i]);
				OrdOrderItemMeta ordOrderItemMeta = modifySettlementPriceService.selectByOrderItemMetaId(orderItemMetaId);
				String changeType = this.getRequest().getParameter("changeType");
				
				// 判断是单项修改还是批量修改（单项修改时为null）
				String type = this.getRequest().getParameter("type");
				if(null != type && type.equals("batchUpdate")){
					boolean ifExist = modifySettlementPriceService.ifExistRefundment(orderItemMetaId);
					if(!ifExist){
						returnMessage(3);
						return;
					}
				}
				
				// 判断是否存在审核记录
				boolean existUnverifiedRecord = modifySettlementPriceService.searchUnverifiedRecord(orderItemMetaId);
				if(!existUnverifiedRecord){
					returnMessage(4);
					return;
				}
				if(null != changeType){
					OrdOrderItemProd itemProd = modifySettlementPriceService.selectItemByOrderItemProdId(ordOrderItemMeta.getOrderItemId());
					Long updatePrice = PriceUtil.convertToFen(this.getRequest().getParameter("updatePrice"));
					Long comparePrice;
					if(changeType.equals(Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE.UNIT_PRICE.toString())){
						comparePrice = itemProd.getPrice();
						//结算单价*打包数量 与 销售单价比较
						if(updatePrice * ordOrderItemMeta.getProductQuantity() > comparePrice){
							result = 1;
						}
					}else{
						//销售单价*订购数量 与 结算总价比较
						comparePrice = itemProd.getPrice() * itemProd.getQuantity();
						if(updatePrice > comparePrice){
							result = 1;
						}
					}
					if(result == 1){
						returnMessage(result);
						return;
					}
				}
				boolean flag = setSettlementItemService.searchSettlementPayByOrderItemMetaId(orderItemMetaId);
				if(flag){
					returnMessage(2);
					return;
				}
			}
		}
		returnMessage(result);
	}

	/**
	 * 批量修改结算价 
	 */
	@Action("/ord/batchUpdateSettlementPrice")
	public void batchUpdateSettlementPrice(){
		// 修改结算价参数
		if(null != ordItemId4add){
			String[] idArray = ordItemId4add.split(",");
			try{
				List<Object> list = new ArrayList<Object>();
				int flag = 1;
				for(int i=0; i<idArray.length; i++){
					Long ordItemId = Long.parseLong(idArray[i]);
					
					// 结算价未修改时，不执行操作
					if(Float.parseFloat(settlementPrice4add) != priceBeforeUpdate4add){
						
						boolean result = modifySettlementPriceService.updateSettlementPrice(ordItemId, ORD_SETTLEMENT_PRICE_CHANGE_TYPE.UNIT_PRICE, Constant.ORD_SETTLEMENT_PRICE_REASON.valueOf(reason4add), remark4add, PriceUtil.convertToFen(settlementPrice4add), getSessionUser().getUserName());
						
						if(result){
							list.add(ordItemId);
						}else{
							flag = 0;
						}
					}
				}
				returnMessage(flag);
				orderMessageProducer.sendMsg(MessageFactory.newModifySettlementPricesMessage(list,this.getSessionUser().getUserName()));
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 审核/驳回
	 */
	@Action("/ord/doVerify")
	public void doVerify(){
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("recordId", recordId);
		conditionMap.put("status", status);
		conditionMap.put("verifiedOperator", getSessionUser().getUserName());
		// 修改历史记录的审核状态
		boolean flag = modifySettlementPriceService.doVerify(conditionMap);
		// 如果是审核通过，则修改订单子子项的结算价
		if(status.equals(Constant.ORD_SETTLEMENT_PRICE_RECORD_STATUS.VERIFIED.toString())){
			// 根据recordId查询历史记录信息 
			OrdSettlementPriceRecord ordSettlementPriceRecord = modifySettlementPriceService.queryHistoryRecordById(recordId);
			OrdOrderItemMetaPrice ordOrderItemMetaPrice = new OrdOrderItemMetaPrice();
			ordOrderItemMetaPrice.setOrderItemMetaId(ordSettlementPriceRecord.getOrderItemMetaId());
			if(ordSettlementPriceRecord.getChangeType().equals(Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE.UNIT_PRICE.toString())){
				ordOrderItemMetaPrice.setActualSettlementPrice(ordSettlementPriceRecord.getActualSettlementPrice());
				ordOrderItemMetaPrice.setChangeType(Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE.UNIT_PRICE.toString());
			}else{
				ordOrderItemMetaPrice.setTotalSettlementPrice(ordSettlementPriceRecord.getTotalSettlementPrice());
				ordOrderItemMetaPrice.setChangeType(Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE.TOTAL_PRICE.toString());
			}
			ordOrderItemMetaPrice.setOperatorName(getSessionUserName());
			// 修改订单子子项的结算单价和结算总价
			modifySettlementPriceService.updateSettlementPriceForVedify(ordOrderItemMetaPrice);
			
			// 保存审核人
			modifySettlementPriceService.updateHistoryRecordById(conditionMap);
			
			List<Object> list = new ArrayList<Object>();
			list.add(ordSettlementPriceRecord.getOrderItemMetaId());
			if(ordSettlementPriceRecord.getChangeType().equals(ORD_SETTLEMENT_PRICE_CHANGE_TYPE.UNIT_PRICE.toString())){
				orderMessageProducer.sendMsg(MessageFactory.newModifySettlementPricesMessage(list,this.getSessionUser().getUserName()));
			}else{
				orderMessageProducer.sendMsg(MessageFactory.newModifyTotalSettlementPricesMessage(list,this.getSessionUser().getUserName()));
			}
		}
	}
	/**
	 * 发送订单子子项结算
	 */
	@Action("/ord/hanlderCreateSettleItem")
	public void createSettleItem(){
		orderMessageProducer.sendMsg(MessageFactory.newOrderItemMetaSettle(recordId,this.getSessionUser().getUserName())); 
	}
	@Action("/ord/settle/showGroupCodeStock")
	public void showGroupCodeStock(){
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderItemMetaId(recordId);
		String travelGroupCode = order.getTravelGroupCode();
		OrdOrderItemMeta item = orderServiceProxy.queryOrdOrderItemMetaBy(recordId);
		if(null==travelGroupCode){
			travelGroupCode="";
		}
		sendAjaxResultByJson("{\"travelGroupCode\":\""+travelGroupCode+"\",\"virtual\":"+item.getVirtual()+"}");
	}
	@Action("/ord/settle/updateGroupCodeStock")
	public void updateGroupCodeStock(){
		try{
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderItemMetaId(recordId);
		String oldTravelGroupCode = order.getTravelGroupCode(); 
		if((travelGroupCode==null && oldTravelGroupCode!=null) || (travelGroupCode!=null&&!travelGroupCode.equals(oldTravelGroupCode))){
			order.setTravelGroupCode(travelGroupCode);
			orderServiceProxy.updateOrdOrderByPrimaryKey(order);
			comLogService.insert("ORD_ORDER", Long.getLong(orderId), Long.getLong(orderId), getSessionUserName(), "updateOrderTravelGroupCode", "修改订单的团号", "把订单团号由"+oldTravelGroupCode+" 修改为"+travelGroupCode, "ORD_ORDER");
		}
		OrdOrderItemMeta item = orderServiceProxy.queryOrdOrderItemMetaBy(recordId);
		if(virtual!=null && !virtual.equalsIgnoreCase(item.getVirtual())){
			modifySettlementPriceService.updateOrderItemMetaVirtual(recordId, virtual);
			comLogService.insert("ORD_ORDER_ITEM_META", Long.getLong(orderId), recordId, getSessionUserName(), "updateOrderItemMetaVirtual", "修改订单子子项", "把订单"+orderId+"子子项"+recordId+"的虚拟库存由原值"+item.getVirtual()+" 设置为"+virtual, "ORD_ORDER");
		}
		sendAjaxResultByJson("{\"success\":true}");
		}catch(Exception e){
			e.printStackTrace();
			sendAjaxResultByJson("{\"success\":false}");
		}
	}
	/**
	 * 返回操作成功信息
	 */
 	private void returnMessage(int flag) {
		try {
				this.getResponse().getWriter().write("{result:" + flag + "}");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public Long getProductBranchId() {
		return productBranchId;
	}

	public void setProductBranchId(Long productBranchId) {
		this.productBranchId = productBranchId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		if(orderId!=null){
			this.orderId = orderId.trim();
		}
	}

	public String getVisitDateStart() {
		return visitDateStart;
	}

	public void setVisitDateStart(String visitDateStart) {
		this.visitDateStart = visitDateStart;
	}

	public String getVisitDateEnd() {
		return visitDateEnd;
	}

	public void setVisitDateEnd(String visitDateEnd) {
		this.visitDateEnd = visitDateEnd;
	}

	public String getCreateOrderTimeBegin() {
		return createOrderTimeBegin;
	}

	public void setCreateOrderTimeBegin(String createOrderTimeBegin) {
		this.createOrderTimeBegin = createOrderTimeBegin;
	}

	public String getCreateOrderTimeEnd() {
		return createOrderTimeEnd;
	}

	public void setCreateOrderTimeEnd(String createOrderTimeEnd) {
		this.createOrderTimeEnd = createOrderTimeEnd;
	}

	public String getPayTimeStart() {
		return payTimeStart;
	}

	public void setPayTimeStart(String payTimeStart) {
		this.payTimeStart = payTimeStart;
	}

	public String getPayTimeEnd() {
		return payTimeEnd;
	}

	public void setPayTimeEnd(String payTimeEnd) {
		this.payTimeEnd = payTimeEnd;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getModifyDateBegin() {
		return modifyDateBegin;
	}

	public void setModifyDateBegin(String modifyDateBegin) {
		this.modifyDateBegin = modifyDateBegin;
	}

	public String getModifyDateEnd() {
		return modifyDateEnd;
	}

	public void setModifyDateEnd(String modifyDateEnd) {
		this.modifyDateEnd = modifyDateEnd;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOrdItemId4add() {
		return ordItemId4add;
	}

	public void setOrdItemId4add(String ordItemId4add) {
		this.ordItemId4add = ordItemId4add;
	}

	public String getSettlementPrice4add() {
		return settlementPrice4add;
	}

	public void setSettlementPrice4add(String settlementPrice4add) {
		this.settlementPrice4add = settlementPrice4add;
	}

	public String getChangeType4add() {
		return changeType4add;
	}

	public void setChangeType4add(String changeType4add) {
		this.changeType4add = changeType4add;
	}

	public String getReason4add() {
		return reason4add;
	}

	public void setReason4add(String reason4add) {
		this.reason4add = reason4add;
	}

	public String getRemark4add() {
		return remark4add;
	}

	public void setRemark4add(String remark4add) {
		this.remark4add = remark4add;
	}

	public Float getPriceBeforeUpdate4add() {
		return priceBeforeUpdate4add;
	}

	public void setPriceBeforeUpdate4add(Float priceBeforeUpdate4add) {
		this.priceBeforeUpdate4add = priceBeforeUpdate4add;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
	
	public ORD_SETTLEMENT_PRICE_CHANGE_TYPE[] getChangeTypeList() {
		return changeTypeList;
	}

	public void setChangeTypeList(ORD_SETTLEMENT_PRICE_CHANGE_TYPE[] changeTypeList) {
		this.changeTypeList = changeTypeList;
	}

	public ORD_SETTLEMENT_PRICE_CHANGE_RESULT[] getChangeResultList() {
		return changeResultList;
	}

	public void setChangeResultList(
			ORD_SETTLEMENT_PRICE_CHANGE_RESULT[] changeResultList) {
		this.changeResultList = changeResultList;
	}

	public ORD_SETTLEMENT_PRICE_REASON[] getResultList() {
		return resultList;
	}

	public void setResultList(ORD_SETTLEMENT_PRICE_REASON[] resultList) {
		this.resultList = resultList;
	}

	public ModifySettlementPriceService getModifySettlementPriceService() {
		return modifySettlementPriceService;
	}

	public void setModifySettlementPriceService(
			ModifySettlementPriceService modifySettlementPriceService) {
		this.modifySettlementPriceService = modifySettlementPriceService;
	}

	public ORD_SETTLEMENT_PRICE_RECORD_STATUS[] getStatusList() {
		return statusList;
	}

	public void setStatusList(ORD_SETTLEMENT_PRICE_RECORD_STATUS[] statusList) {
		this.statusList = statusList;
	}

	public SetSettlementItemService getSetSettlementItemService() {
		return setSettlementItemService;
	}

	public void setSetSettlementItemService(
			SetSettlementItemService setSettlementItemService) {
		this.setSettlementItemService = setSettlementItemService;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Page getPagination() {
		return pagination;
	}

	public void setPagination(Page pagination) {
		this.pagination = pagination;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getChangeType() {
		return changeType;
	}

	public void setChangeType(List<String> changeType) {
		this.changeType = changeType;
	}

	public List<String> getSettlementPay() {
		return settlementPay;
	}

	public void setSettlementPay(List<String> settlementPay) {
		this.settlementPay = settlementPay;
	}

	public Long getTotalSettlePriceBegin() {
		return totalSettlePriceBegin;
	}

	public void setTotalSettlePriceBegin(Long totalSettlePriceBegin) {
		this.totalSettlePriceBegin = totalSettlePriceBegin;
	}

	public Long getTotalSettlePriceEnd() {
		return totalSettlePriceEnd;
	}

	public void setTotalSettlePriceEnd(Long totalSettlePriceEnd) {
		this.totalSettlePriceEnd = totalSettlePriceEnd;
	}

	public String getRefundment() {
		return refundment;
	}

	public void setRefundment(String refundment) {
		this.refundment = refundment;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getTravelGroupCode() {
		return travelGroupCode;
	}

	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}

	public String getVirtual() {
		return virtual;
	}

	public void setVirtual(String virtual) {
		this.virtual = virtual;
	}

}
