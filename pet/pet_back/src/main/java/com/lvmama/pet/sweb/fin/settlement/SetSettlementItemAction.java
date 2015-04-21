package com.lvmama.pet.sweb.fin.settlement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.service.fin.SetSettlementItemService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FILIALE_NAME;
import com.lvmama.comm.vo.Constant.ORDER_PERFORM_STATUS;
import com.lvmama.comm.vo.Constant.SETTLEMENT_COMPANY;
import com.lvmama.comm.vo.Constant.SETTLEMENT_TYPE;
import com.lvmama.comm.vo.Constant.SET_SETTLEMENT_ITEM_STATUS;
import com.lvmama.pet.sweb.fin.common.FinPageAction;
/**
 * 订单结算
 * 
 * @author yanggan
 *
 */
@Results(value={
		@Result(name="index",location="/WEB-INF/pages/back/fin/settlement/set_settlement_item.ftl")		
	})
@Namespace(value="/fin/set/item")
public class SetSettlementItemAction extends FinPageAction{
	
	private static final long serialVersionUID = 1L;
	
	private SetSettlementItemService setSettlementItemService;
	private TopicMessageProducer orderMessageProducer;
	
	/**
	 * 我方结算主体
	 */
	private SETTLEMENT_COMPANY[] settlementCompany;
	/**
	 * 订单结算项状态
	 */
	private SET_SETTLEMENT_ITEM_STATUS[] setSettlementItemStatus;
	/**
	 * 所属分公司
	 */
	private FILIALE_NAME[] filialeNames;
	
	/**履行状态**/
	private ORDER_PERFORM_STATUS[] orderPerformStatus=ORDER_PERFORM_STATUS.values();
	
	private List<Long> settlementItemIds;
	private List<Long> orderItemMetaIds;
	/**
	 * 进入订单结算页面
	 * @return
	 */
	@Action("index")
	public String index(){
		settlementCompany = SETTLEMENT_COMPANY.values();
		setSettlementItemStatus = SET_SETTLEMENT_ITEM_STATUS.values();
		filialeNames =FILIALE_NAME.values();
		getRequest().setAttribute("userName", getSessionUser().getUserName());
		getRequest().setAttribute("settlementType", SETTLEMENT_TYPE.ORDER.name());
		return "index";
	}
	
	/**
	 * 进入订单结算页面(代售团)
	 * @return
	 */
	@Action("groupIndex")
	public String groupIndex(){
		this.index();
		getRequest().setAttribute("settlementType", SETTLEMENT_TYPE.GROUP.name());
		
		return "index";
	}
	/**
	 * 查询订单结算项
	 */
	@Action("searchList")
	public void searchList(){
		index();
		Page<SetSettlementItem> page = setSettlementItemService.searchList(this.initSearchParameter());
		this.sendAjaxResultByJson(JSONObject.fromObject(page).toString());
	}

	/**
	 * 不结
	 */
	@Action("noSettle")
	public void noSettle(){
		setSettlementItemService.noSettle(settlementItemIds,getSessionUserName());
		// 把标记为不结的结算对列项更新为不结算
		orderMessageProducer.sendMsg(MessageFactory.newSetOrderItemMetaMessage(Constant.SETTLEMENT_STATUS.NOSETTLEMENT.name(), orderItemMetaIds, getSessionUserName()));
		sendAjaxMsg("SUCCESS");
	}
	
	private void settleBase(Map<String,Object> resMap){
		//订单子子项的结算状态更新为结算中
		orderMessageProducer.sendMsg(MessageFactory.newSetOrderItemMetaMessage(Constant.SETTLEMENT_STATUS.SETTLEMENTING.name(), orderItemMetaIds, getSessionUserName()));
		resMap.put("result", "SUCCESS");
		List<Long> newList = (List<Long>) resMap.get("newSettlement");
		List<Long> mergeList =(List<Long>) resMap.get("mergeSettlement");
		if(newList.size() == 0 && mergeList.size() ==0 ){
			resMap.put("result", "EMPTY");
		}else{
			resMap.put("result", "SUCCESS");
		}
		sendAjaxResultByJson(JSONObject.fromObject(resMap).toString());
	}
	/**
	 * 生成结算单
	 */
	@Action("settle")
	public void settle(){
		String settlementType = getRequestParameter("settlementType");
		Map<String,Object> resMap = setSettlementItemService.settle(settlementItemIds,SETTLEMENT_TYPE.valueOf(settlementType),getSessionUserName());
		this.settleBase(resMap);
	}
	/**
	 * 全部生成结算单（根据查询条件）
	 */
	@Action("settleAll")
	public void settleAll(){
		String settlementType = getRequestParameter("settlementType");
		Map<String,Object> resMap = setSettlementItemService.settleAll(this.initSearchParameter(),SETTLEMENT_TYPE.valueOf(settlementType),getSessionUserName());
		orderItemMetaIds = (List<Long>) resMap.get("orderItemMetaIds");
		resMap.remove("orderItemMetaIds");
		this.settleBase(resMap);
	}
	@Override 
	public Map<String,Object> initRequestParameter() { 
		HttpServletRequest request = getRequest(); 
		Map<String, Object> map = new HashMap<String, Object>(); 
//      extractRequestParam(map,"settlementType",request);
		extractRequestParam(map,"targetId",Long.class,request); 
		extractRequestParam(map,"settlementTarget",request); 
		extractRequestParam(map,"settlementPeriod",request); 
		extractRequestParam(map,"metaFilialeName",request); 
		extractRequestParam(map,"visitDateStart",request); 
		extractRequestParam(map,"visitDateEnd",request); 
		extractRequestParam(map,"metaProductId",Long.class, request); 
		extractRequestParam(map,"metaBranchType",request); 
		if(map.get("metaProduct") == null){ 
			map.remove("metaBranchType"); 
		} 
		extractRequestParam(map,"notContainLvmama",request); 
		extractRequestParam(map,"payTimeStart",request); 
		extractRequestParam(map,"payTimeEnd",request); 
		extractRequestParam(map,"settlementCompany",request); 
		extractRequestParam(map,"hasRefunded",request); 
		//如果建议打款时间小于当天日期，则系统自动将该打款时间设为当天 
		extractRequestParam(map,"suggestionPayDateStart",request); 
		String s1 = (String) map.get("suggestionPayDateStart"); 
		if(!StringUtil.isEmptyString(s1)){ 
			Date start = DateUtil.stringToDate(s1, "yyyy-MM-dd"); 
			if(start != null && start.before(DateUtil.getTodayYMDDate())){ 
				map.put("suggestionPayDateStart", DateUtil.formatDate(new Date(), "yyyy-MM-dd")); 
			} 
		} 
		extractRequestParam(map,"suggestionPayDateEnd",request); 
		String s2 = (String) map.get("suggestionPayDateEnd"); 
		if(!StringUtil.isEmptyString(s2)){ 
			Date end = DateUtil.stringToDate(s2, "yyyy-MM-dd"); 
			if(end != null && end.before(DateUtil.getTodayYMDDate())){ 
				map.put("suggestionPayDateEnd", DateUtil.formatDate(new Date(), "yyyy-MM-dd")); 
			} 
		} 
		extractRequestParam(map,"orderId",Long.class,request); 
		extractRequestParam(map,"supplierId",Long.class,request); 
		extractRequestParam(map,"bankAccount",request); 
		extractRequestParam(map,"status",List.class,request); 
		extractRequestParam(map,"username",request); 
		extractRequestParam(map,"createOrderTimeBegin",request); 
		extractRequestParam(map,"createOrderTimeEnd",request);
		extractRequestParam(map,"filialeName",request);
		extractRequestParam(map,"performStatus",request);
		extractRequestParam(map,"routeType",List.class,request); 
		extractRequestParam(map,"productType",List.class,request); 
		return map; 
	}
	
	public SETTLEMENT_COMPANY[] getSettlementCompany() {
		return settlementCompany;
	}
	public void setSetSettlementItemService(SetSettlementItemService setSettlementItemService) {
		this.setSettlementItemService = setSettlementItemService;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setSettlementItemIds(List<Long> settlementItemIds) {
		this.settlementItemIds = settlementItemIds;
	}

	public void setOrderItemMetaIds(List<Long> orderItemMetaIds) {
		this.orderItemMetaIds = orderItemMetaIds;
	}

	public SET_SETTLEMENT_ITEM_STATUS[] getSetSettlementItemStatus() {
		return setSettlementItemStatus;
	}

	public FILIALE_NAME[] getFilialeNames() {
		return filialeNames;
	}

	public ORDER_PERFORM_STATUS[] getOrderPerformStatus() {
		return orderPerformStatus;
	}

	
	
}
