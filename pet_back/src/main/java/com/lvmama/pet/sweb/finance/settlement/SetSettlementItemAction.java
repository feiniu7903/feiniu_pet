package com.lvmama.pet.sweb.finance.settlement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.service.fin.FinanceSettlementService;
import com.lvmama.comm.pet.service.fin.SetSettlementItemService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FILIALE_NAME;
import com.lvmama.comm.vo.Constant.SETTLEMENT_COMPANY;
import com.lvmama.comm.vo.Constant.SETTLEMENT_TYPE;
import com.lvmama.comm.vo.Constant.SET_SETTLEMENT_ITEM_STATUS;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.service.VstSuppSupplierService;
import com.lvmama.comm.vst.util.VstObjectTransferPetObjectUtil;
import com.lvmama.comm.vst.vo.VstSuppSupplierSettlementVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;
/**
 * 订单结算
 * 
 * @author yanggan
 *
 */
@Results(value={
		@Result(name="index",location="/WEB-INF/pages/back/finance/settlement/set_settlement_item.ftl")		
	})
@Namespace(value="/finance/set/item")
public class SetSettlementItemAction extends FinancePageAction{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private SetSettlementItemService setSettlementItemService;

	@Autowired
	private FinanceSettlementService financeSettlementService;
	@Autowired
	private VstSuppSupplierService vstSuppSupplierService;
	
	@Autowired
	private VstOrdOrderService vstOrdOrderService;
	
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
	
	private List<Long> settlementItemIds;
	private List<Long> orderItemMetaIds;
	/**
	 * 进入订单结算页面
	 * @return
	 */
	@Action("index")
	public String index(){
		super.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
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
		Map<String,Object> params = this.initSearchParameter();
		Object	targetIds = params.get("targetIds");
		if(targetIds!=null && ((List<Long>)targetIds).isEmpty()){
			this.sendAjaxResultByJson(JSONObject.fromObject(new Page<SetSettlementItem>()).toString());
			return;
		}
		Page<SetSettlementItem> page = financeSettlementService.searchItemListPage(params);
		if(null!=page && null!=page.getItems() && !page.getItems().isEmpty()){
			for(SetSettlementItem setSettlementItem:page.getItems()){
				Long supplierId = setSettlementItem.getSupplierId();
				VstSuppSupplierVo vstSuppSupplierVo =  vstSuppSupplierService.findVstSuppSupplierById(supplierId);
				if(null!=vstSuppSupplierVo){
					setSettlementItem.setSupplierName(vstSuppSupplierVo.getSupplierName());
				}
				Long targetId = setSettlementItem.getTargetId();
				VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(targetId);
				if(null!=vstSuppSupplierSettlementVo){
					setSettlementItem.setTargetName(vstSuppSupplierSettlementVo.getSettleName());
					setSettlementItem.setCompanyId(vstSuppSupplierSettlementVo.getLvAccSubject());
					setSettlementItem.setSuggestionPayTime(DateUtil.dsDay_Date(setSettlementItem.getVisitTime(), vstSuppSupplierSettlementVo.getSettleCycle()!=null?(int)vstSuppSupplierSettlementVo.getSettleCycle().longValue():0));
				}
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(page).toString());
	}

	/**
	 * 不结
	 */
	@Action("noSettle")
	public void noSettle(){
		setSettlementItemService.noSettle(settlementItemIds,getSessionUserName());
		sendAjaxMsg("SUCCESS");
	}
	
	private void settleBase(Map<String,Object> resMap){
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
		final String LOCK_KEY = "ADD_SETTELENT_ITEM_USER_KEY_"+getSessionUserName();
		if(SynchronizedLock.isOnDoingMemCached(LOCK_KEY)){
			return;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("setSettlementIds", settlementItemIds);
		List<SetSettlementItem> setSettlementItems = financeSettlementService.searchItemList(params);
		Map<String,Object>  resMap = settleBase(setSettlementItems);
		SynchronizedLock.releaseMemCached(LOCK_KEY);
		this.settleBase(resMap);
	}
	/**
	 * 全部生成结算单（根据查询条件）
	 */
	@Action("settleAll")
	public void settleAll(){
		Object	targetIds = initSearchParameter().get("targetIds");
		if(targetIds!=null && ((List<Long>)targetIds).isEmpty()){
			settleBase(new HashMap<String,Object>());
			return;
		}
		final String LOCK_KEY = "ADD_SETTELENT_ITEM_USER_KEY_"+getSessionUserName();
		if(SynchronizedLock.isOnDoingMemCached(LOCK_KEY)){
			return;
		}
		List<SetSettlementItem> setSettlementItems = financeSettlementService.searchItemList(initSearchParameter());
		Map<String,Object>  resMap = settleBase(setSettlementItems);
		SynchronizedLock.releaseMemCached(LOCK_KEY);
		this.settleBase(resMap);
	}
	/**
	 * 修改结算价
	 */
	@Action("updateSettlementMoney")
	public void updateSettlementMoney(){
		String setSettlementItemIdStr = this.getRequestParameter("settlementItemId");
		String actualSettlementPriceStr = this.getRequestParameter("actualSettlementPrice");
		String messageType = this.getRequestParameter("messageType");
		if(StringUtils.isNotEmpty(setSettlementItemIdStr) && StringUtils.isNotEmpty(actualSettlementPriceStr) && StringUtils.isNotEmpty(messageType)){
			Long setSettlementItemId = Long.valueOf(setSettlementItemIdStr);
			Long actualSettlementPrice = Long.valueOf(actualSettlementPriceStr);
			List<SetSettlementItem> setSettlementItems = new ArrayList<SetSettlementItem>();
			SetSettlementItem item = new SetSettlementItem();
			item.setSettlementItemId(setSettlementItemId);
			setSettlementItems.add(item);
			financeSettlementService.updateSettlementPrice(setSettlementItems,actualSettlementPrice, super.getSessionUser().getUserName(), messageType);
			Map resMap = new HashMap();
			resMap.put("success", Boolean.TRUE);
			sendAjaxResultByJson(JSONObject.fromObject(resMap).toString());
		}
	}
	
	public Map<String,Object> settleBase(final List<SetSettlementItem> setSettlementItems){
		String settlementType =Constant.SETTLEMENT_TYPE.ORDER.name();
		Map<String,List<SetSettlementItem>> targetItemMap =new HashMap<String,List<SetSettlementItem>>();
		Map<String,SupSettlementTarget> targetMap = new HashMap<String,SupSettlementTarget>();
		for(SetSettlementItem item:setSettlementItems){
			String businessName = item.getBusinessName();
				if(Constant.SET_SETTLEMENT_ITEM_STATUS.NORMAL.name().equals(item.getStatus())){
					String key = item.getTargetId().toString();
					key = key + "," +item.getFilialeName()+","+businessName;
					List<SetSettlementItem> itemList = targetItemMap.get(key);
					if (itemList == null) {
						itemList = new ArrayList<SetSettlementItem>();
					}
					itemList.add(item);
					targetItemMap.put(key, itemList);
					if(null==targetMap.get(key)){
						VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(item.getTargetId());
						if(null!=vstSuppSupplierSettlementVo){
							SupSettlementTarget SupSettlementTarget = VstObjectTransferPetObjectUtil.VstSuppSupplierSettlementVoTransferSupSettlementTarget(vstSuppSupplierSettlementVo);
							VstSuppSupplierVo vstSuppSupplierVo =vstSuppSupplierService.findVstSuppSupplierById(vstSuppSupplierSettlementVo.getSupplierId());
							if(null!=vstSuppSupplierVo){
								SupSettlementTarget.setSupplier(VstObjectTransferPetObjectUtil.VstSuppSupplierVoTransferSupSupplier(vstSuppSupplierVo));
								SupSettlementTarget.getSupplier().setCompanyId(vstSuppSupplierSettlementVo.getLvAccSubject());
							}
							targetMap.put(key, SupSettlementTarget);
						}
					}
				}
		}
		
		Map<String,Object> resMap = setSettlementItemService.settleByTarget(targetItemMap,SETTLEMENT_TYPE.valueOf(settlementType),getSessionUserName(),null,targetMap);
		
		//更新vst结算子项信息
		for (Map.Entry<String, List<SetSettlementItem>> targetItem : targetItemMap.entrySet()) {
			for (SetSettlementItem item : targetItem.getValue()) {
				vstOrdOrderService.updateOrderItemSettlement(item.getOrderItemMetaId(), Constant.SETTLEMENT_STATUS.SETTLEMENTING.name());
			}
		}
		
		return resMap;
	}
	@Override 
	public Map<String,Object> initRequestParameter() { 
		HttpServletRequest request = getRequest(); 
		Map<String, Object> map = new HashMap<String, Object>(); 
		extractRequestParam(map,"targetId",Long.class,request); 
		extractRequestParam(map,"settlementTarget",request); 
		extractRequestParam(map,"settlementPeriod",request); 
		extractRequestParam(map,"visitDateStart",request); 
		extractRequestParam(map,"visitDateEnd",request); 
		extractRequestParam(map,"metaProductId",Long.class, request); 
		extractRequestParam(map,"metaBranchType",request); 
		if(map.get("metaProduct") == null){ 
			map.remove("metaBranchType"); 
		} 
		//extractRequestParam(map,"notContainLvmama",request); 
		extractRequestParam(map,"payTimeStart",request); 
		extractRequestParam(map,"payTimeEnd",request); 
		extractRequestParam(map,"hasRefunded",request); 
		//建议打款时间实现起来太复杂，可考虑在结算子项上加提前打款天数
		/**如果建议打款时间小于当天日期，则系统自动将该打款时间设为当天 
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
		*/
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtil.isEmptyString(getRequestParameter("settlementCompany"))){
			params.put("settlementCompany", getRequestParameter("settlementCompany"));
		}
		if(!StringUtil.isEmptyString(getRequestParameter("bankAccount"))){
			params.put("accountNo", getRequestParameter("bankAccount"));
		}
		if(!StringUtil.isEmptyString(getRequestParameter("settlementPeriod"))){
			params.put("settleCycle", VstObjectTransferPetObjectUtil.settlementPeriodTransferSettleCycle(getRequestParameter("settlementPeriod"),null));
		}
		/**
		List<VstSuppSupplierVo> vstSuppSupplierVos = vstSuppSupplierService.findVstSuppSupplierByParams(params);
		if(null!=vstSuppSupplierVos && !vstSuppSupplierVos.isEmpty()){
			List<Long> supplierIds = new ArrayList<Long>();
			for(VstSuppSupplierVo vstSuppSupplierVo:vstSuppSupplierVos){
				supplierIds.add(vstSuppSupplierVo.getSupplierId());
			}
			map.put("supplierIds", supplierIds);
		}
		*/
		if(!params.isEmpty()){
			map.put("targetIds", new ArrayList<Long>());
			List<VstSuppSupplierSettlementVo> vstSuppSupplierSettlementVos = vstSuppSupplierService.findSuppSupplierSettlementByParams(params);
			if(null!=vstSuppSupplierSettlementVos && !vstSuppSupplierSettlementVos.isEmpty()){
				List<Long> targetIds = new ArrayList<Long>();
				for(VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo:vstSuppSupplierSettlementVos){
					targetIds.add(vstSuppSupplierSettlementVo.getSettleRuleId());
				}
				map.put("targetIds", targetIds);
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
		extractRequestParam(map,"routeType",List.class,request); 
		extractRequestParam(map,"productType",List.class,request);
		return map; 
	}
	public SETTLEMENT_COMPANY[] getSettlementCompany() {
		return settlementCompany;
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

	
	
}
