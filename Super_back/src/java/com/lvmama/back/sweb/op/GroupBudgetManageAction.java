package com.lvmama.back.sweb.op;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.op.OpGroupBudget;
import com.lvmama.comm.bee.po.op.OpGroupBudgetFixed;
import com.lvmama.comm.bee.po.op.OpGroupBudgetProd;
import com.lvmama.comm.bee.po.op.OpOtherIncoming;
import com.lvmama.comm.bee.po.op.OpTravelGroup;
import com.lvmama.comm.bee.po.op.ProductOrderDetail;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.IGroupBudgetService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.FincConstant;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.FinanceUtil;
import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;

@Results(value={
		@Result(name="search",location="/WEB-INF/pages/back/op/groupBudget/search.jsp"),
		@Result(name="doBudget",location="/WEB-INF/pages/back/op/groupBudget/budget.jsp"),
		@Result(name="groupFinalBudget",location="/WEB-INF/pages/back/op/groupBudget/final_budget.jsp"),
		@Result(name="FinalBudget",location="/WEB-INF/pages/back/op/groupBudget/final_budget.jsp"),
		@Result(name="printGroupCostIncome",location="/WEB-INF/pages/back/op/groupBudget/group_income_cost.jsp"),
		@Result(name="printGroupSettle",location="/WEB-INF/pages/back/op/groupBudget/group_settle.jsp")
})
public class GroupBudgetManageAction extends BaseAction{
	private static final Log log = LogFactory.getLog(GroupBudgetManageAction.class);
	private String productName; 
	private Long productId;
	private String travelGroupCode; 
	private String visitTimeStart;
	private String visitTimeEnd;
	private String settlementStatus;
	private String visitTime;
	private OpGroupBudgetFixed fixedItem;
	private String isExistOrder;
	private String productManager;
	private String productManagerName;
	private Long supplierId;
	
	private IGroupBudgetService groupBudgetService;
	/**
	 * 产品接口
	 */
	private ProdProductService prodProductService;
	
	private SupplierService supplierService;
	private SettlementTargetService settlementTargetService;

	private OrderService orderServiceProxy;//lightedCompositeQueryOrdOrder

	
	/**
	 * 进入团计划页面
	 * @return
	 */
	@Action(value = "/op/group_budget_manage")
	public String search(){
		return "search";
	}
	/**
	 * 查询团列表
	 */
	@Action(value = "getGroupList")
	public String getGroupList(){
		Map<String,String> param=new HashMap<String, String>();
		setSearchParams(param);
		initPagination();
		pagination.setActionUrl(WebUtils.getUrl(getRequest(),param));
		
		long total = groupBudgetService.getGroupListCount(param);
		pagination.setTotalRecords(total);
		if(total>0){
			param.put("startRow", String.valueOf(pagination.getFirstRow()));
			param.put("endRow", String.valueOf(pagination.getLastRow()));
			
			List<OpTravelGroup> list = groupBudgetService.getGroupList(param);
			pagination.setRecords(list);			
		}
		return "search";
	}

	/**
	 * 进入团预算表页面
	 * @return
	 */
	@Action(value="doBudget")
	public String doBudget(){
		/* 取消团预算功能
		this.decodeGroupCode();
		OpTravelGroup group = groupBudgetService.getOpTravelGroupByCode(travelGroupCode);
		
		//查询团销售价
		TimePrice tp=prodProductService.getProductPrice(group.getProductId(), group.getVisitTime());
		if(tp!=null){
			group.setSellPrice(tp.getPrice());
		}
		getSession().setAttribute("group", group);
		getRequest().setAttribute("group", group);
		if(!OpTravelGroup.SETTLEMENT_STATUS.UNBUDGET.name().equals(group.getSettlementStatus())){
			OpGroupBudget budget = groupBudgetService.getGroupBudgetByGroupCode(group.getTravelGroupCode());
			getSession().setAttribute("groupBudget", budget);
			getRequest().setAttribute("groupBudget", budget);
		}else{
			getSession().removeAttribute("groupBudget");
		}*/
		return "doBudget";
	}
	
	/**
	 * 保存成本项
	 */
	@Action(value="saveFixedItem")
	public void saveFixedItem(){
		String s = getRequest().getParameter("data");
		OpGroupBudgetFixed fixed = (OpGroupBudgetFixed)JSONObject.toBean(JSONObject.fromObject(JSONArray.fromObject(JsonUtil.convertQuoteBack(s)).get(0)),OpGroupBudgetFixed.class);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		fixed.setTravelGroupCode(group.getTravelGroupCode());
		OpGroupBudget budget = (OpGroupBudget)getSession().getAttribute("groupBudget");
		fixed.setBudgetId(budget.getBudgetId());
		fixed.setType("BUDGET");
		if(fixed.getItemId() == null){	
			//新增
			fixed.setPayAmount(0D);
			fixed.setPayStatus("NOPAY");
			Long itemId = groupBudgetService.insertOpGroupBudgetFixed(fixed);
			groupBudgetService.log("BUDGET", "新增成本项", group.getTravelGroupId(), fixed.getCostsItemName() + ":" + fixed.getSubtotalCosts() + "元",getLoginUserName());
			sendAjaxResult("{result:\"SUCCESS\",itemId:" + itemId +"}");
		}else{
			//修改
			groupBudgetService.updateOpGroupBudgetFixed(fixed);
			groupBudgetService.log("BUDGET", "修改成本项", group.getTravelGroupId(), fixed.getCostsItemName() + ":" + fixed.getSubtotalCosts() + "元",getLoginUserName());
			sendAjaxResult("{result:\"SUCCESS\",itemId:" + fixed.getItemId() +"}");
		}
	}
	/**
	 * 保存实际固定成本项
	 */
	@Action("saveFinalFixedItem")
	public void saveFinalFixedItem(){
		String s = getRequest().getParameter("data");
		OpGroupBudgetFixed fixed = (OpGroupBudgetFixed)JSONObject.toBean(JSONObject.fromObject(JSONArray.fromObject(JsonUtil.convertQuoteBack(s)).get(0)),OpGroupBudgetFixed.class);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		fixed.setTravelGroupCode(group.getTravelGroupCode());
		OpGroupBudget budget = (OpGroupBudget)getSession().getAttribute("finalBudget");
		fixed.setBudgetId(budget.getBudgetId());
		fixed.setType("COST");
		if(fixed.getItemId() == null){	
			//新增
			fixed.setPayAmount(0D);
			fixed.setPayStatus("NOPAY");
			Long itemId = groupBudgetService.insertOpGroupBudgetFixed(fixed);
			OpGroupBudgetFixed f = groupBudgetService.getBudgetFixedByItemId(itemId);
			groupBudgetService.log("FINAL_BUDGET", "新增固定成本项", group.getTravelGroupId(), f.getCostsItemName() + ":" + f.getSubtotalCosts() + "元",getLoginUserName());
			
			// 更新状态为已做成本
			groupBudgetService.updateToCosted(group.getTravelGroupCode(), group.getSettlementStatus());
			sendAjaxResult("{result:\"SUCCESS\",itemId:" + itemId +"}");
		}else{
			//修改
			groupBudgetService.updateOpGroupBudgetFixed(fixed);
			OpGroupBudgetFixed f = groupBudgetService.getBudgetFixedByItemId(fixed.getItemId());
			groupBudgetService.log("FINAL_BUDGET", "修改固定成本项", group.getTravelGroupId(), f.getCostsItemName() + ":" + f.getSubtotalCosts() + "元",getLoginUserName());
			sendAjaxResult("{result:\"SUCCESS\",itemId:" + fixed.getItemId() +"}");
		}
	}
	
	/**
	 * 删除成本项
	 */
	@Action("deleteFixedItem")
	public void deleteFixedItem(){
		Long itemId = Long.parseLong(getRequest().getParameter("id"));
		OpGroupBudgetFixed fixed = groupBudgetService.getBudgetFixedByItemId(itemId);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		groupBudgetService.log("BUDGET", "删除成本项", group.getTravelGroupId(), fixed.getCostsItemName(),getLoginUserName());
		groupBudgetService.deleteOpGroupBudgetFixed(itemId);
		sendAjaxResult("{result:\"SUCCESS\",itemId:" + itemId +"}");
	}
	/**
	 * 删除实际成本项
	 */
	@Action("deleteFinalFixedItem")
	public void deleteFinalFixedItem(){
		Long itemId = Long.parseLong(getRequest().getParameter("id"));
		OpGroupBudgetFixed fixed = groupBudgetService.getBudgetFixedByItemId(itemId);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		groupBudgetService.log("FINAL_BUDGET", "删除成本项", group.getTravelGroupId(), fixed.getCostsItemName(),getLoginUserName());
		groupBudgetService.deleteOpGroupBudgetFixed(itemId);
		sendAjaxResult("{result:\"SUCCESS\",itemId:" + itemId +"}");
	}
	
	/**
	 * 查询产品成本项
	 */
	@Action("getProductByGroupCode")
	public void getProductByGroupCode(){
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		String groupCode = group.getTravelGroupCode();
		/*String status = group.getSettlementStatus();
		if(OpTravelGroup.SETTLEMENT_STATUS.UNBUDGET.name().equals(status)){
			//未预算，从团信息中查询产品
			List<OpGroupBudgetProd> prods = groupBudgetService.getGroupProductByGroupCode(groupCode);
			sendAjaxResult(JSONArray.fromObject(prods).toString());
		}else{
			List<OpGroupBudgetProd> prods = groupBudgetService.getGroupBudgetProductByGroupCode(groupCode);
			sendAjaxResult(JSONArray.fromObject(prods).toString());
		}*/
		List<OpGroupBudgetProd> prods = groupBudgetService.getGroupBudgetProductByGroupCode(groupCode, "BUDGET");
		sendAjaxResult(JSONArray.fromObject(prods).toString());
	}
	/**
	 * 查询固定成本项
	 */
	@Action("getFixedItemByGroupCode")
	public void getFixedItemByGroupCode(){
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		String groupCode = group.getTravelGroupCode();
		List<OpGroupBudgetFixed> fixeds = groupBudgetService.getGroupBudgetFixedByGroupCode(groupCode);
		sendAjaxResult(JSONArray.fromObject(fixeds).toString());
	}
	
	/**
	 * 保存团预算表
	 */
	@Action("saveGroupBudget")
	public void saveGroupBudget(){
		OpGroupBudget budget = new OpGroupBudget();
		OpGroupBudget sessionBudget = (OpGroupBudget)getSession().getAttribute("groupBudget");
		if(sessionBudget != null){
			budget.setBudgetId(sessionBudget.getBudgetId());
			OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
			groupBudgetService.log("BUDGET", "更新预算表", group.getTravelGroupId(), "预计人数："+sessionBudget.getBgPersons(),getLoginUserName());
		}else{
			OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
			groupBudgetService.log("BUDGET", "新增预算表", group.getTravelGroupId(), "团号："+group.getTravelGroupCode(),getLoginUserName());
		}
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		String groupCode = group.getTravelGroupCode();
		budget.setTravelGroupCode(groupCode);
		budget.setTravelGroupId(group.getTravelGroupId());
		budget.setBgPersons(Long.parseLong(getRequest().getParameter("bgPersons").trim()));
		budget.setSalePrice(Double.parseDouble(getRequest().getParameter("salePrice").trim().replace(",", "")));
		budget.setBgTotalCosts(Double.parseDouble(getRequest().getParameter("bgTotalCosts").trim().replace(",", "")));
		budget.setBgPerCosts(Double.parseDouble(getRequest().getParameter("bgPerCosts").trim().replace(",", "")));
		budget.setBgIncoming(Double.parseDouble(getRequest().getParameter("bgIncoming").trim().replace(",", "")));
		budget.setBgProfit(Double.parseDouble(getRequest().getParameter("bgProfit").trim()));
		budget.setBgProfitRate(Double.parseDouble(getRequest().getParameter("bgProfitRate").trim()) / 100);
		
		String prdStr = getRequest().getParameter("prdList");
		if(!StringUtil.isEmptyString(prdStr)){
			List<OpGroupBudgetProd> prods = new ArrayList<OpGroupBudgetProd>();
			JSONArray jsonArray = JSONArray.fromObject(prdStr);
			if(jsonArray.size() > 0){
				for(int i=0; i < jsonArray.size(); i++){
					JSONObject obj = JSONObject.fromObject(jsonArray.get(i));
					OpGroupBudgetProd prod = (OpGroupBudgetProd)JSONObject.toBean(obj, OpGroupBudgetProd.class);
					prods.add(prod);
				}
			}
			budget.setProds(prods);
		}
		
		String fixedStr = getRequest().getParameter("fixedList");
		if(!StringUtil.isEmptyString(fixedStr)){
			List<OpGroupBudgetFixed> fixeds = new ArrayList<OpGroupBudgetFixed>();
			JSONArray jsonArray = JSONArray.fromObject(JsonUtil.convertQuoteBack(fixedStr));
			if(jsonArray.size() > 0){
				for(int i=0; i < jsonArray.size(); i++){
					JSONObject obj = JSONObject.fromObject(jsonArray.get(i));
					OpGroupBudgetFixed fixed = (OpGroupBudgetFixed)JSONObject.toBean(obj, OpGroupBudgetFixed.class);
					fixeds.add(fixed);
				}
			}
			budget.setFixeds(fixeds);
		}
		Long id = groupBudgetService.saveGroupBudget(budget);
		sendAjaxResult("{result:\"SUCCESS\",budgetId:" + id +"}");
	}
	/**
	 * 查询成本项列表
	 */
	@Action("getFixedOptions")
	public void getFixedOptions(){
		sendAjaxResult(JSONArray.fromObject(groupBudgetService.getFixedOptions()).toString());
	}
	/**
	 * 查询币种列表
	 */
	@Action("getCurrencyOptions")
	public void getCurrencyOptions(){
		sendAjaxResult(JSONArray.fromObject(groupBudgetService.getCurrencyOptions()).toString());
	}
	
	/**
	 * 查询产品经理列表
	 */
	@Action("getProductManagerForAutocomplete")
	public void getProductManagerForAutocomplete(){
		String term = getRequest().getParameter("term");
		sendAjaxResult(JSONArray.fromObject(groupBudgetService.getProductManagerForAutocomplete(term)).toString());
	}
	
	/**
	 * 查询供应商列表
	 */
	@Action("getSupplierForAutocomplete")
	public void getSupplierForAutocomplete(){
		String term = getRequest().getParameter("term");
		String json = JSONArray.fromObject(groupBudgetService.getSupplierForAutocomplete(term)).toString();
		sendAjaxResult(json);
	}
	/**
	 * 查询结算对象列表
	 */
	@Action("getTargetForAutocomplete")
	public void getTargetForAutocomplete(){
		String term = getRequest().getParameter("term");
		String supplierId = getRequest().getParameter("supplierId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("term", term);
		map.put("supplierId", supplierId);
		sendAjaxResult(JSONArray.fromObject(groupBudgetService.getTargetForAutocomplete(map)).toString());
	}
	
	/**
	 * 产品催款
	 * @return
	 */
	@Action("requireProductPay")
	public void requireProductPay(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", getRequest().getParameter("itemId"));
		map.put("reqPayAmount", getRequest().getParameter("reqPayAmount"));
		map.put("isUseAdvance", getRequest().getParameter("isUseAdvance"));
//		map.put("payStatus", "REQPAY");
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		String logType = null;
		if(FincConstant.GROUP_SETTLEMENT_STATUS_UNCOST.equals(group.getSettlementStatus())){
			logType = "BUDGET";
		}else if(FincConstant.GROUP_SETTLEMENT_STATUS_COSTED.equals(group.getSettlementStatus())){
			logType = "FINAL_BUDGET";
		}
		groupBudgetService.log(logType, "产品申请打款", group.getTravelGroupId(), "产品明细项ID：" + getRequest().getParameter("itemId")+ ";金额：" + getRequest().getParameter("reqPayAmount"),getLoginUserName());
		map.put("groupCode", group.getTravelGroupCode());
		groupBudgetService.updateBudgetProdSettlementStatus(map);
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	/**
	 * 固定成本催款
	 * @return
	 */
	@Action("requireFixedPay")
	public void requireFixedPay(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", getRequest().getParameter("itemId"));
		map.put("reqPayAmount", getRequest().getParameter("reqPayAmount"));
		map.put("isUseAdvance", getRequest().getParameter("isUseAdvance"));
//		map.put("payStatus", "REQPAY");
		groupBudgetService.updateBudgetFixedSettlementStatus(map);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		String logType = null;
		if(FincConstant.GROUP_SETTLEMENT_STATUS_UNCOST.equals(group.getSettlementStatus())){
			logType = "BUDGET";
		}else if(FincConstant.GROUP_SETTLEMENT_STATUS_COSTED.equals(group.getSettlementStatus())){
			logType = "FINAL_BUDGET";
		}
		groupBudgetService.log(logType, "固定成本申请打款", group.getTravelGroupId(), "固定成本明细项ID：" + getRequest().getParameter("itemId")+ ";金额：" + getRequest().getParameter("reqPayAmount"),getLoginUserName());
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	/**
	 * 产品延迟
	 * @return
	 */
	@Action("delayProductPay")
	public void delayProductPay(){
		Long itemId = Long.parseLong(getRequest().getParameter("id"));
		String timeStr = getRequest().getParameter("time");
		Date time = DateUtil.stringToDate(timeStr, "yyyy-MM-dd");
		String memo = getRequest().getParameter("memo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", itemId);
		map.put("itemType", "PRODUCT");
		map.put("time", time);
		map.put("memo", memo);
		groupBudgetService.updateDelayTime(map);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		groupBudgetService.log("BUDGET", "产品延迟打款设置", group.getTravelGroupId(), "产品明细项ID：" + itemId + ";延迟时间：" + timeStr,getLoginUserName());
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	/**
	 * 实际产品延迟
	 * @return
	 */
	@Action("delayFinalProductPay")
	public void delayFinalProductPay(){
		Long itemId = Long.parseLong(getRequest().getParameter("id"));
		String timeStr = getRequest().getParameter("time");
		Date time = DateUtil.stringToDate(timeStr, "yyyy-MM-dd");
		String memo = getRequest().getParameter("memo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", itemId);
		map.put("itemType", "PRODUCT");
		map.put("time", time);
		map.put("memo", memo);
		groupBudgetService.updateDelayTime(map);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		groupBudgetService.log("FINAL_BUDGET", "产品延迟打款设置", group.getTravelGroupId(), "产品明细项ID：" + itemId + ";延迟时间：" + timeStr, getLoginUserName());
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	
	/**
	 * 固定成本延迟
	 * @return
	 */
	@Action("delayFixedPay")
	public void delayFixedPay(){
		Long itemId = Long.parseLong(getRequest().getParameter("id"));
		String timeStr = getRequest().getParameter("time");
		Date time = DateUtil.stringToDate(timeStr, "yyyy-MM-dd");
		String memo = getRequest().getParameter("memo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", itemId);
		map.put("itemType", "FIXED");
		map.put("time", time);
		map.put("memo", memo);
		groupBudgetService.updateDelayTime(map);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		groupBudgetService.log("BUDGET", "固定成本延迟打款设置", group.getTravelGroupId(), "固定成本明细项ID：" + itemId + ";延迟时间：" + timeStr,getLoginUserName());
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	/**
	 * 实际固定成本延迟
	 * @return
	 */
	@Action("delayFinalFixedPay")
	public void delayFinalFixedPay(){
		Long itemId = Long.parseLong(getRequest().getParameter("id"));
		String timeStr = getRequest().getParameter("time");
		Date time = DateUtil.stringToDate(timeStr, "yyyy-MM-dd");
		String memo = getRequest().getParameter("memo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", itemId);
		map.put("itemType", "FIXED");
		map.put("time", time);
		map.put("memo", memo);
		groupBudgetService.updateDelayTime(map);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		groupBudgetService.log("FINAL_BUDGET", "固定成本延迟打款设置", group.getTravelGroupId(), "固定成本明细项ID：" + itemId + ";延迟时间：" + timeStr,getLoginUserName());
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	
	/**
	 * 团实际成本管理
	 */
	@Action(value = "/op/groupFinalBudget")
	public String finalBudget(){
		//http://localhost:8080/super_back/op/groupFinalBudget.do
		groupBudgetService.countFinalBudgetByTravelCode("LV-SH-ECP-20120610-60227");		
		return "groupFinalBudget";
	}
	/**
	 * 查询团是否有订单存在
	 */
	@Action(value = "/op/hasOrder")
	public void hasOrder(){
		int r = groupBudgetService.hasOrderByGroupCode(getRequest().getParameter("groupCode"));
		sendAjaxResult("{result:" + r + "}");
	}
	
	/**
	 * 进入成本表页面
	 * @return
	 */
	@Action("doFinalBudget")
	public String doFinalBudget(){
		try{
			decodeGroupCode();
			OpTravelGroup group = groupBudgetService.getOpTravelGroupByCode(travelGroupCode);
			getSession().setAttribute("group", group);
			getRequest().setAttribute("group", group);
			// 初始化产品成本数据和实际成本数据
			OpGroupBudget finalBudget = groupBudgetService.countFinalBudgetByTravelCode(travelGroupCode);
			if (null == finalBudget) {
				finalBudget = new OpGroupBudget();
			}
			getSession().setAttribute("finalBudget", finalBudget);
			getRequest().setAttribute("finalBudget", finalBudget);
			getRequest().setAttribute("incomingList", groupBudgetService.getGroupIncomingByGroupCode(travelGroupCode));
			getRequest().setAttribute("productList", groupBudgetService.getGroupFinalBudgetProductByGroupCode(travelGroupCode));
			getRequest().setAttribute("fixedList", groupBudgetService.getGroupFinalBudgetFixedByGroupCode(travelGroupCode));
		}catch (Exception e) {
			e.printStackTrace();
			log.error(GroupBudgetManageAction.class,e);
			throw new RuntimeException("团实际成本异常");
		}
		return "groupFinalBudget";
	}
	
	/**
	 * 保存附加收入
	 */
	@Action("saveIncoming")
	public void saveIncoming(){
		OpGroupBudget budget = (OpGroupBudget)getSession().getAttribute("finalBudget");
		String idStr = getRequest().getParameter("id");
		Long costsItemId = Long.parseLong(getRequest().getParameter("costsItemId"));
		Double amount = Double.parseDouble(getRequest().getParameter("amount"));
		String remark = getRequest().getParameter("remark");
		OpOtherIncoming incoming = new OpOtherIncoming();
		incoming.setId(StringUtil.isEmptyString(idStr)?null:Long.parseLong(idStr));
		incoming.setCostsItemId(costsItemId);
		incoming.setAmount(amount);
		//incoming.setBudgetId(budget.getBudgetId());
		incoming.setCreatetime(new Date());
		incoming.setTravelGroupCode(budget.getTravelGroupCode());
		incoming.setCreator(getLoginUserName());
		incoming.setRemark(remark);
		Long id = groupBudgetService.saveOtherIncoming(incoming);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		List<OpOtherIncoming> list = groupBudgetService.getOtherIncoming(map);
		if(costsItemId == null){
			groupBudgetService.log("FINAL_BUDGET", "新增附加收入", budget.getTravelGroupId(), list.get(0).getCostsItemName() + ":" +list.get(0).getAmount() + "元",getLoginUserName());
		}else{
			groupBudgetService.log("FINAL_BUDGET", "修改附加收入", budget.getTravelGroupId(), list.get(0).getCostsItemName() + ":" +list.get(0).getAmount() + "元",getLoginUserName());
		}
		
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	/**
	 * 删除附加收入
	 */
	@Action("deleteIncoming")
	public void deleteIncoming(){
		OpGroupBudget budget = (OpGroupBudget)getSession().getAttribute("finalBudget");
		Long id = Long.parseLong(getRequest().getParameter("id"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		OpOtherIncoming incoming = groupBudgetService.getOtherIncoming(map).get(0);
		groupBudgetService.log("FINAL_BUDGET", "删除附加收入", budget.getTravelGroupId(), "内容：" + incoming.getCostsItemName(), getLoginUserName());
		groupBudgetService.deleteOtherIncoming(id);
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	
	/**
	 * 实际产品成本催款
	 */
	@Action("requireFinalProductPay")
	public void requireFinalProductPay(){
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", getRequest().getParameter("itemId"));
		map.put("reqPayAmount", getRequest().getParameter("reqPayAmount"));
		map.put("isUseAdvance", getRequest().getParameter("isUseAdvance"));
		map.put("reqType", "FINAL_BUDGET");
		map.put("groupCode", group.getTravelGroupCode());
		//map.put("payStatus", "REQPAY");
		groupBudgetService.updateBudgetProdSettlementStatus(map);
		groupBudgetService.log("FINAL_BUDGET", "产品成本申请打款", group.getTravelGroupId(), "产品成本明细项ID：" + getRequest().getParameter("itemId")+ ";金额：" + getRequest().getParameter("reqPayAmount"),getLoginUserName());
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	
	/**
	 *  实际固定成本催款
	 */
	@Action("requireFinalFixedPay")
	public void requireFinalFixedPay(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", getRequest().getParameter("itemId"));
		map.put("reqPayAmount", getRequest().getParameter("reqPayAmount"));
		map.put("isUseAdvance", getRequest().getParameter("isUseAdvance"));
		//map.put("payStatus", "REQPAY");
		groupBudgetService.updateBudgetFixedSettlementStatus(map);
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		groupBudgetService.log("FINAL_BUDGET", "固定成本申请打款", group.getTravelGroupId(), "固定成本明细项ID：" + getRequest().getParameter("itemId")+ ";金额：" + getRequest().getParameter("reqPayAmount"),getLoginUserName());
		sendAjaxResult("{result:\"SUCCESS\"}");
	}

	/**
	 * 查看订单详细
	 */
	@Action("getProductOrderDetails")
	public void getProductOrderDetails(){
		decodeGroupCode();
		Map<String,Object> map=new HashMap<String, Object>();
		String prdBranchId = getRequest().getParameter("prdBranchId");
		map.put("prdBranchId", prdBranchId);
		map.put("groupCode",travelGroupCode);
		
		int currpage = Integer.parseInt(getRequest().getParameter("page"));
		int pagesize = Integer.parseInt(getRequest().getParameter("rows"));
		Page page = Page.page(pagesize, currpage);
		page = groupBudgetService.getProductOrderDetailsByPage(page,map);
		
		JSONObject jsonObj=new JSONObject();
		JSONArray rows=new JSONArray();
		jsonObj.put("page", ""+page.getCurrentPage());
		jsonObj.put("total", page.getTotalPageNum());
		jsonObj.put("records", ""+page.getTotalResultSize());
		for (int i = 0; i < page.getItems().size(); i++) {
			ProductOrderDetail detail=(ProductOrderDetail) page.getItems().get(i);
			rows.add(JSONObject.fromObject(detail));
		}
		jsonObj.put("rows", rows);
		sendAjaxResultByJson(jsonObj.toString());
	}
	/**
	 * 查询催款所需信息（预付款余额、已催款金额等）
	 */
	@Action("getReqPayFormInfo")
	public void getReqPayFormInfo(){
		//查询明细项币种
		String currency = groupBudgetService.getCurrencyByItemId(Long.parseLong((String)getRequest().getParameter("itemId")),(String)getRequest().getParameter("type"));
		//预付款余额
		Double amount = groupBudgetService.getSupAdvanceAmount(Long.parseLong((String)getRequest().getParameter("supplierId")),currency);
		//已催款金额
		Double reqPayAmount = groupBudgetService.getReqPayAmount(Long.parseLong((String)getRequest().getParameter("itemId")),
												(String)getRequest().getParameter("type"));
		sendAjaxResult("{result:\"SUCCESS\",supAdvanceAmount:" + amount + ",reqPayAmount:" + reqPayAmount + "}");
	}
	
	@Action("getLogs")
	public void getLogs(){
		String type = getRequest().getParameter("type");
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		List<ComLog> logs = groupBudgetService.getLogs(group.getTravelGroupId(),type);
		sendAjaxResult(JSONArray.fromObject(logs).toString());
	}
	/**
	 * 导出团列表
	 */
	@Action("exportGroupList")
	public void exportGroupList(){
		Map<String,String> param=new HashMap<String, String>();
		setSearchParams(param);
		List<OpTravelGroup> list = groupBudgetService.getGroupListForExport(param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupList", list);
		output(map, "/WEB-INF/resources/template/opGroupListTemplate.xls", "op_group_list_" + System.currentTimeMillis());
	}
	/**
	 * 导出团订单列表
	 * @param param
	 */
	@Action("exportOrderListByGroupCode")
	public void exportOrderListByGroupCode(){
		
	}
	/**
	 * 更新实际人数
	 * @return
	 * @throws IOException 
	 */
	@Action("updateActPersons")
	public void updateActPersons() throws IOException{
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupCode", group.getTravelGroupCode());
		map.put("actAdult", Long.valueOf(getRequest().getParameter("actAdult")));
		map.put("actChild", Long.valueOf(getRequest().getParameter("actChild")));
		groupBudgetService.updateActPersons(map);
		groupBudgetService.log("FINAL_BUDGET", "更新实际人数", group.getTravelGroupId(), "成人数：" + getRequest().getParameter("actAdult")+ ";儿童数：" + getRequest().getParameter("actChild"),getLoginUserName());
		getResponse().sendRedirect("doFinalBudget.do?travelGroupCode=" + URLEncoder.encode(group.getTravelGroupCode())); 
	}
	
	
	/**
	 * 打印团实际成本收入信息
	 * @return
	 * @throws IOException
	 */
	@Action("printGroupCostIncome")
	public String printGroupCostIncome() throws IOException{
		try{
			decodeGroupCode();
			OpTravelGroup group = groupBudgetService.getOpTravelGroupByCode(travelGroupCode);
			productName = group.getProductName();
			OpGroupBudget finalBudget = groupBudgetService.getGroupBudgetByGroupCode(travelGroupCode);
			ProdProduct product = prodProductService.getProdProduct(group.getProductId());
			List<OpGroupBudgetFixed> fixedList = groupBudgetService.getGroupFinalBudgetFixedByGroupCode(travelGroupCode);
			CompositeQuery compositeQuery = new CompositeQuery();
			//CompositeQuery.ExcludedContent excludedContent = new CompositeQuery.ExcludedContent();
			//excludedContent.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			CompositeQuery.OrderContent orderContent = new CompositeQuery.OrderContent();
			orderContent.setTravelCode(travelGroupCode);
			CompositeQuery.OrderStatus orderStatus = new CompositeQuery.OrderStatus();
			orderStatus.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
			PageIndex pageIndex = new PageIndex();
			pageIndex.setBeginIndex(0);
			pageIndex.setEndIndex(Integer.MAX_VALUE);
			compositeQuery.setPageIndex(pageIndex);
			//compositeQuery.setExcludedContent(excludedContent);
			compositeQuery.setContent(orderContent);
			compositeQuery.setStatus(orderStatus);
			List<OrdOrder> productOrders = orderServiceProxy.lightedCompositeQueryOrdOrder(compositeQuery);
			getRequest().setAttribute("finalBudget", finalBudget);
			getRequest().setAttribute("group", group);
			getRequest().setAttribute("filialeName", Constant.FILIALE_NAME.getCnName(product.getFilialeName()));
			getRequest().setAttribute("incomingList", groupBudgetService.getGroupIncomingByGroupCode(travelGroupCode));
			getRequest().setAttribute("productList", groupBudgetService.getGroupFinalBudgetProductByGroupCode(travelGroupCode));
			getRequest().setAttribute("fixedList", fixedList);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("groupCode",travelGroupCode);
			Page page = Page.page(1000, 1);
			page = groupBudgetService.getProductOrderDetailsByPage(page,map);
			List<ProductOrderDetail> OrderItems = page.getItems();
			Double payedAmount = 0.00;
			for(int i=0;i<productOrders.size();i++){
				OrdOrder order = productOrders.get(i);
				Boolean isAdd = Boolean.FALSE;
				for(ProductOrderDetail detail:OrderItems){
					if(order.getOrderId().longValue()==detail.getOrderId().longValue()){
						isAdd = Boolean.TRUE;
					}
				}
				if(isAdd){
					order.setActualPay(order.getActualPay()-(null!=order.getRefundedAmount()?order.getRefundedAmount():0));
					payedAmount+=order.getActualPay();
				}else{
					productOrders.remove(i);
					i--;
				}
			}
			Double subtotalCosts=0.00;
			for(OpGroupBudgetFixed fixed:fixedList){
				subtotalCosts+=fixed.getSubtotalCosts();
			}
			if(fixedList.size()>productOrders.size()){
				int rows=fixedList.size()-productOrders.size();
				for(int i=0;i<rows;i++){
					productOrders.add(new OrdOrder());
				}
			}else if(fixedList.size()<productOrders.size()){
				int rows=productOrders.size()-fixedList.size();
				for(int i=0;i<rows;i++){
					fixedList.add(new OpGroupBudgetFixed());
				}
			}
			getRequest().setAttribute("productOrders", productOrders);
			getRequest().setAttribute("itemRows", fixedList.size()+3);
			getRequest().setAttribute("payedAmount", payedAmount/100);
			getRequest().setAttribute("subtotalCosts", subtotalCosts);
			getRequest().setAttribute("subtotalCostsZH",  FinanceUtil.change(subtotalCosts));
			getRequest().setAttribute("profit", payedAmount/100-subtotalCosts);
		}catch (Exception e) {
			log.error(GroupBudgetManageAction.class,e);
			throw new RuntimeException("团实际成本异常");
		}
		return "printGroupCostIncome";
	}
	
	/**
	 * 打印团结算信息
	 * @return
	 * @throws IOException
	 */
	@Action("printGroupSettle")
	public String printGroupSettle() throws IOException{
		decodeGroupCode();
		List<OpGroupBudgetFixed>  fixedList = groupBudgetService.getGroupFinalBudgetFixedByGroupCode(travelGroupCode);
		Map<Long,String> supplierMap = new HashMap<Long,String>();
		getRequest().setAttribute("supplierMap", supplierMap);
		for(OpGroupBudgetFixed fixed:fixedList){
			supplierMap.put(fixed.getSupplierId(), fixed.getSupplierName());
		}
		if(null==supplierId && !supplierMap.keySet().isEmpty()){
			supplierId = (Long)supplierMap.keySet().toArray()[0];
		}else if(null==supplierId){
			return "printGroupSettle";
		}
		OpTravelGroup group = groupBudgetService.getOpTravelGroupByCode(travelGroupCode);
		OpGroupBudget finalBudget = groupBudgetService.getGroupBudgetByGroupCode(travelGroupCode);
		ProdProduct product = prodProductService.getProdProduct(group.getProductId());
		SupSupplier supplier = supplierService.getSupplier(supplierId);
		productName = group.getProductName();
		getRequest().setAttribute("filialeName", Constant.FILIALE_NAME.getCnName(product.getFilialeName()));
		getRequest().setAttribute("finalBudget", finalBudget);
		getRequest().setAttribute("applyDate", DateUtil.getTodayYMDDate());
		getRequest().setAttribute("supplier", supplier);
		SupSettlementTarget target = null;
		double count=0.00;
		for(int i=0;i<fixedList.size();i++){
			OpGroupBudgetFixed fixed = fixedList.get(i);
			if(supplierId.equals(fixed.getSupplierId())){
				count+=fixed.getSubtotalCosts();
				if(null==target){
					target= settlementTargetService.getSettlementTargetById(fixed.getTargetId());
				}
			}else{
				fixedList.remove(i);
				i--;
			}
		}
		getRequest().setAttribute("target", target);
		getRequest().setAttribute("fixedList",fixedList);
		getRequest().setAttribute("totalSettle", count);
		getRequest().setAttribute("totalSettleZH", FinanceUtil.change(count));
		return "printGroupSettle";
	}
	
	/**
	 * 产品是否加入总成本--预算
	 * @throws IOException 
	 */
	@Action("isInCostBudget")
	public void isInCostBudget() throws IOException{
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupCode", group.getTravelGroupCode());
		map.put("prdBranchId", Long.valueOf(getRequest().getParameter("prdBranchId")));
		map.put("isInCost", getRequest().getParameter("isInCost"));
		groupBudgetService.updateIsInCostBudget(map);
		groupBudgetService.log("BUDGET", "更新是否加入总成本", group.getTravelGroupId(), 
				"产品类别ID：" + getRequest().getParameter("prdBranchId")+ ";是否加入总成本：" + getRequest().getParameter("isInCost"),getLoginUserName());
		sendAjaxResult("{result:\"SUCCESS\"}");
	}
	/**
	 * 产品是否加入总成本
	 * @throws IOException 
	 */
	@Action("isInCost")
	public void isInCost() throws IOException{
		OpTravelGroup group = (OpTravelGroup)getSession().getAttribute("group");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupCode", group.getTravelGroupCode());
		map.put("prdBranchId", Long.valueOf(getRequest().getParameter("prdBranchId")));
		map.put("isInCost", getRequest().getParameter("isInCost"));
		groupBudgetService.updateIsInCost(map);
		groupBudgetService.log("FINAL_BUDGET", "更新是否加入总成本", group.getTravelGroupId(), 
				"产品类别ID：" + getRequest().getParameter("prdBranchId")+ ";是否加入总成本：" + getRequest().getParameter("isInCost"),getLoginUserName());
		getResponse().sendRedirect("doFinalBudget.do?travelGroupCode=" + URLEncoder.encode(group.getTravelGroupCode())); 
	}
	
	//查询条件
	private void setSearchParams(Map<String, String> param){
		if(!StringUtil.isEmptyString(productName)){
			param.put("productName", "%" + productName + "%");
		}
		if(productId != null){
			param.put("productId", String.valueOf(productId));
		}
		if(!StringUtil.isEmptyString(travelGroupCode)){
			param.put("travelGroupCode", travelGroupCode);
		}
		if(!StringUtil.isEmptyString(visitTimeStart)){
			param.put("visitTimeStart", visitTimeStart);
		}
		if(!StringUtil.isEmptyString(visitTimeEnd)){
			param.put("visitTimeEnd", visitTimeEnd);
		}
		if(!StringUtil.isEmptyString(settlementStatus)){
			param.put("settlementStatus", settlementStatus);
		}
		if(!StringUtil.isEmptyString(isExistOrder) && !"0".equals(isExistOrder)){
			isExistOrder = "1";
			param.put("isExistOrder", isExistOrder);
		}else{
			isExistOrder = "0";
			param.put("isExistOrder", isExistOrder);
		}
		if(!StringUtil.isEmptyString(productManager)){
			param.put("productManager", productManager);
		}
		PermUser user = getSessionUser();
		if(user != null && !user.isAdministrator()){
			param.put("orgId", String.valueOf(this.getSessionUser().getDepartmentId()));
		}
	}
	
	//response Excel文件
	private void output(Map<String, Object> map,String template,String fileName){
		FileInputStream fin=null;
		OutputStream os=null;
		try{
			File templateResource = ResourceUtil.getResourceFile(template);
			XLSTransformer transformer = new XLSTransformer();
			String destFileName = Constant.getTempDir() + "/excel"+new Date().getTime()+".xls";
			transformer.transformXLS(templateResource.getAbsolutePath(), map, destFileName);
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xls");
			getResponse().setContentType("application/vnd.ms-excel");
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
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getTravelGroupCode() {
		return travelGroupCode;
	}
	public void decodeGroupCode(){
		try {
			travelGroupCode=travelGroupCode.replaceAll("\\+", "########");
			travelGroupCode=new String(URLDecoder.decode(travelGroupCode).getBytes("ISO-8859-1"));
			travelGroupCode=travelGroupCode.replaceAll("########", "+");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public void setTravelGroupCode(String travelGroupCode){
		this.travelGroupCode = travelGroupCode;
	}
	public String getVisitTimeStart() {
		return visitTimeStart;
	}
	public void setVisitTimeStart(String visitTimeStart) {
		this.visitTimeStart = visitTimeStart;
	}
	public String getVisitTimeEnd() {
		return visitTimeEnd;
	}
	public void setVisitTimeEnd(String visitTimeEnd) {
		this.visitTimeEnd = visitTimeEnd;
	}
	
	public String getSettlementStatus() {
		return settlementStatus;
	}
	public void setSettlementStatus(String settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public IGroupBudgetService getGroupBudgetService() {
		return groupBudgetService;
	}
	public void setGroupBudgetService(IGroupBudgetService groupBudgetService) {
		this.groupBudgetService = groupBudgetService;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public OpGroupBudgetFixed getFixedItem() {
		return fixedItem;
	}
	public void setFixedItem(OpGroupBudgetFixed fixedItem) {
		this.fixedItem = fixedItem;
	}
	
	private String getLoginUserName(){
		PermUser user = getSessionUser();
		if(user != null){
			return user.getUserName();
		}
		return "admin";
	}
	 
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public String getIsExistOrder() {
		return isExistOrder;
	}
	public void setIsExistOrder(String isExistOrder) {
		this.isExistOrder = isExistOrder;
	}
	public String getProductManager() {
		return productManager;
	}
	public void setProductManager(String productManager) {
		this.productManager = productManager;
	}
	public String getProductManagerName() {
		return productManagerName;
	}
	public void setProductManagerName(String productManagerName) {
		this.productManagerName = productManagerName;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public SettlementTargetService getSettlementTargetService() {
		return settlementTargetService;
	}
	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}
	public SupplierService getSupplierService() {
		return supplierService;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
}
