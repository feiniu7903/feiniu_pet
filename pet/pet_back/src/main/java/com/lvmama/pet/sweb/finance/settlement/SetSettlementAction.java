package com.lvmama.pet.sweb.finance.settlement;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.po.fin.SetSettlementChange;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.fin.SetSettlementItemExcel;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.service.fin.FinanceSettlementService;
import com.lvmama.comm.pet.service.fin.FinanceSupplierMoneyService;
import com.lvmama.comm.pet.service.fin.SetSettlementItemService;
import com.lvmama.comm.pet.service.fin.SetSettlementService;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.Pagination;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FILIALE_NAME;
import com.lvmama.comm.vo.Constant.SET_SETTLEMENT_STATUS;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.service.VstSuppSupplierService;
import com.lvmama.comm.vst.util.VstObjectTransferPetObjectUtil;
import com.lvmama.comm.vst.vo.VstSuppContactVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierSettlementVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

/**
 * 结算单管理
 * 
 * @author yanggan
 * 
 */
@Results(value = { @Result(name = "index", location = "/WEB-INF/pages/back/finance/settlement/set_settlement.ftl"), 
		@Result(name = "info", location = "/WEB-INF/pages/back/finance/settlement/set_settlement_info.ftl"), 
		@Result(name = "pay", location = "/WEB-INF/pages/back/finance/settlement/set_settlement_pay.ftl"), 
		@Result(name = "orderDetail", location = "/WEB-INF/pages/back/finance/settlement/set_settlement_item_detail.ftl"), 
		@Result(name = "record", location = "/WEB-INF/pages/back/finance/settlement/set_settlement_change.ftl") })
@Namespace(value = "/finance/set/settlement")
public class SetSettlementAction extends FinancePageAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SetSettlementService setSettlementService;
	@Autowired
	private SetSettlementItemService setSettlementItemService;
	@Autowired
	private ContactService contactService;

	@Autowired
	private FinanceSettlementService financeSettlementService;
	@Autowired
	private VstSuppSupplierService vstSuppSupplierService;
	@Autowired
	private FinanceSupplierMoneyService financeSupplierMoneyService;
	
	@Autowired
	private VstOrdOrderService vstOrdOrderService;
	
	/**
	 * 结算单状态
	 */
	private SET_SETTLEMENT_STATUS[] setSettlementStatus;

	private String settlementId;
	private List<Long> settlementItemIds;
	private List<Long> orderItemMetaIds;
	/**
	 * 所属分公司
	 */
	private FILIALE_NAME[] filialeNames;
	
	private Map<String,Object> params = new HashMap<String,Object>();
	
	private static final Log LOG = LogFactory.getLog(SetSettlementAction.class);
	
	/**
	 * 进入结算单管理页面
	 * 
	 * @return
	 */
	@Action("index")
	public String index() {
		setSettlementStatus = SET_SETTLEMENT_STATUS.values();
		HttpServletRequest request = getRequest();
		String settlementType = request.getParameter("settlementType");
		request.setAttribute("settlementType", settlementType);
		filialeNames =FILIALE_NAME.values();
		if (!StringUtil.isEmptyString(settlementId)) {
			request.setAttribute("init", true);
		}
		return "index";
	}
	/**
	 * 查询结算单
	 */
	@Action("searchList")
	public void searchList() {
		super.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		if(StringUtil.isEmptyString(orderBy)){
			orderBy = "SETTLEMENT_ID";
			order = "desc";
		}
		Page<SetSettlement> result = this.financeSettlementService.searchSettleListPage(this.initSearchParameter());
		if(null!=result && null!=result.getItems() && !result.getItems().isEmpty()){
			for(SetSettlement setSettlement:result.getItems()){
				Long targetId = setSettlement.getTargetId();
				VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(targetId);
				if(null!=vstSuppSupplierSettlementVo){
					setSettlement.setTargetName(vstSuppSupplierSettlementVo.getSettleName());
					Long supplierId = vstSuppSupplierSettlementVo.getSupplierId();
					VstSuppSupplierVo vstSuppSupplierVo =vstSuppSupplierService.findVstSuppSupplierById(supplierId);
					if(null!=vstSuppSupplierVo){
						setSettlement.setSupplierName(vstSuppSupplierVo.getSupplierName());
						setSettlement.setSupplierId(supplierId);
					}
				}
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}

	/**
	 * 进入打款页面
	 * 
	 * @return
	 */
	@Action("pay")
	public String pay() {
		SetSettlement settlement =null;
		params.put("settlementId", settlementId);
		List<SetSettlement> result =  financeSettlementService.searchSettleList(params);
		if(null!=result && !result.isEmpty()){
			settlement = result.get(0);
			Long targetId = settlement.getTargetId();
			VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(targetId);
			if(null!=vstSuppSupplierSettlementVo){
				settlement.setTargetName(vstSuppSupplierSettlementVo.getSettleName());
				settlement.setSupplierId(vstSuppSupplierSettlementVo.getSupplierId());
				Long supplierId = vstSuppSupplierSettlementVo.getSupplierId();
				VstSuppSupplierVo vstSuppSupplierVo =vstSuppSupplierService.findVstSuppSupplierById(supplierId);
				if(null!=vstSuppSupplierVo){
					settlement.setSupplierName(vstSuppSupplierVo.getSupplierName());
					settlement.setSupplierId(supplierId);
				}
			}
		}
		FinSupplierMoney supplierMoney = financeSupplierMoneyService.searchBySupplierId(settlement.getSupplierId());
		if (supplierMoney == null) {
			supplierMoney = new FinSupplierMoney();
		}
		HttpServletRequest request = getRequest();
		request.setAttribute("settlement", settlement);
		request.setAttribute("supplierMoney", supplierMoney);
		request.setAttribute("currentDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm"));
		return "pay";
	}

	/**
	 * 打款
	 * 
	 * @return
	 */
	@Action("toPay")
	public void toPay() {
		HttpServletRequest request = getRequest();
		Long bankPayAmount = PriceUtil.convertToFen(Float.parseFloat(request.getParameter("bankPayAmount")));
		Long advanceDepositPayAmount = PriceUtil.convertToFen(Float.parseFloat(request.getParameter("advanceDepositPayAmount")));
		Long deductionPayAmount = PriceUtil.convertToFen(Float.parseFloat(request.getParameter("deductionPayAmount")));
		String bankName = request.getParameter("bankName");
		Date operatetime = DateUtil.stringToDate(request.getParameter("operatetime"), "yyyy-MM-dd HH:mm");
		String serial = request.getParameter("serial");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("settlementId", settlementId);
		List<SetSettlement> setSettlements = financeSettlementService.searchSettleList(params);
		if(CollectionUtils.isNotEmpty(setSettlements)){
			SetSettlement settlement = setSettlements.get(0);
			VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(settlement.getTargetId());
			if(vstSuppSupplierSettlementVo!=null){
				settlement.setTargetName(vstSuppSupplierSettlementVo.getSettleName());
				settlement.setSupplierId(vstSuppSupplierSettlementVo.getSupplierId());
				Long supplierId = vstSuppSupplierSettlementVo.getSupplierId();
				VstSuppSupplierVo vstSuppSupplierVo =vstSuppSupplierService.findVstSuppSupplierById(supplierId);
				if(null!=vstSuppSupplierVo){
					settlement.setSupplierName(vstSuppSupplierVo.getSupplierName());
					settlement.setSupplierId(supplierId);
				}

				Map<String, Object> res = financeSettlementService.toPay(settlement, advanceDepositPayAmount, deductionPayAmount, bankPayAmount, bankName, operatetime, serial, getSessionUserName());
				this.sendAjaxResultByJson(JSONObject.fromObject(res).toString());
				return;
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(new HashMap<String,Object>()).toString());
	}

	/**
	 * 结算单详情
	 * 
	 * @return
	 */
	@Action("info")
	public String info() {
		HttpServletRequest request = getRequest();
		Integer type = Integer.parseInt(request.getParameter("type"));
		SetSettlement settlement = new SetSettlement();
		params.put("settlementId", settlementId);
		List<SetSettlement> result =  financeSettlementService.searchSettleList(params);
		if(null!=result && !result.isEmpty()){
			settlement = result.get(0);
		}
		ComContact contact = null;
		Map<String,Object> vstParams = new HashMap<String,Object>();
		if (settlement.getTargetId() != null) {
			vstParams.put("settleRuleId", settlement.getTargetId());
			List<VstSuppContactVo> contacts =vstSuppSupplierService.findSuppContactVoBySettleRuleId(settlement.getTargetId());
			if(null!=contacts && !contacts.isEmpty()){
				contact = VstObjectTransferPetObjectUtil.VstSuppContactVoTransferComContact(contacts.get(0));
			}
		}
		if (contact == null) {
			contact = new ComContact();
		}
		settlement.setContact(contact);
		SetSettlement initalSettlement= new SetSettlement();
		BeanUtils.copyProperties(settlement, initalSettlement);
		VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(initalSettlement.getTargetId());
		initalSettlement.setMemo(vstSuppSupplierSettlementVo.getSettleDesc());
		initalSettlement.setBankAccount(vstSuppSupplierSettlementVo.getAccountNo());
		initalSettlement.setBankAccountName(vstSuppSupplierSettlementVo.getAccountName());
		initalSettlement.setBankName(vstSuppSupplierSettlementVo.getAccountBank());
		initalSettlement.setAlipayName(vstSuppSupplierSettlementVo.getAlipayName());
		initalSettlement.setAlipayAccount(vstSuppSupplierSettlementVo.getAlipayNo());
		initalSettlement.setSettlementPeriod(vstSuppSupplierSettlementVo.getSettlePeriod());
		ComContact contactSo = null;
		if (settlement.getTargetId() != null) {
			vstParams.put("settleRuleId", settlement.getTargetId());
			List<VstSuppContactVo> contacts =vstSuppSupplierService.findSuppContactVoBySettleRuleId(settlement.getTargetId());
			if(null!=contacts && !contacts.isEmpty()){
				contactSo = VstObjectTransferPetObjectUtil.VstSuppContactVoTransferComContact(contacts.get(0));
			}
		}
		if (contactSo == null) {
			contactSo = new ComContact();
		}
		initalSettlement.setContact(contactSo);
		request.setAttribute("settlement", settlement);
		request.setAttribute("initalSettlement", initalSettlement);
		request.setAttribute("type", type);
		return "info";
	}

	/**
	 * 刷新结算单固化对象信息
	 */
	@Action("refreshInitalInfo")
	public void refreshInitalInfo() {
		Long id = Long.parseLong(settlementId);
		SetSettlement initalSettlement =null; 
		params.put("settlementId", settlementId);
		List<SetSettlement> result =  financeSettlementService.searchSettleList(params);
		if(null!=result && !result.isEmpty()){
			initalSettlement = result.get(0);
		}
		if(null==initalSettlement){
			initalSettlement = new SetSettlement();
			initalSettlement.setSettlementId(id);
		}
		ComContact contactSo = null;
		if (initalSettlement.getTargetId() != null) {
			List<VstSuppContactVo> contacts =vstSuppSupplierService.findSuppContactVoBySettleRuleId(initalSettlement.getTargetId());
			if(null!=contacts && !contacts.isEmpty()){
				contactSo = VstObjectTransferPetObjectUtil.VstSuppContactVoTransferComContact(contacts.get(0));
			}
		}
		if (contactSo == null) {
			contactSo = new ComContact();
		}
		VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(initalSettlement.getTargetId());
		initalSettlement.setMemo(vstSuppSupplierSettlementVo.getSettleDesc());
		initalSettlement.setBankAccount(vstSuppSupplierSettlementVo.getAccountNo());
		initalSettlement.setBankAccountName(vstSuppSupplierSettlementVo.getAccountName());
		initalSettlement.setBankName(vstSuppSupplierSettlementVo.getAccountBank());
		initalSettlement.setAlipayName(vstSuppSupplierSettlementVo.getAlipayName());
		initalSettlement.setAlipayAccount(vstSuppSupplierSettlementVo.getAlipayNo());
		initalSettlement.setSettlementPeriod(vstSuppSupplierSettlementVo.getSettlePeriod());
		// 修改固话结算对象数据
		setSettlementService.updateInitalInfo(initalSettlement);
		this.sendAjaxResultByJson(JSONObject.fromObject(initalSettlement).toString());
	}

	/**
	 * 结算单结算
	 */
	@Action("settle")
	public void settle() {
		String memo = getRequestParameter("memo");
		
		SetSettlement settlement = financeSettlementService.getSetSettlementById(Long.parseLong(settlementId));
		VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(settlement.getTargetId());
		VstSuppSupplierVo sup = vstSuppSupplierService.findVstSuppSupplierById(vstSuppSupplierSettlementVo.getSupplierId());
		Map<String,Object> res = financeSettlementService.settle(settlement, memo, getSessionUserName(), sup);
		
		//更新vst结算子项信息
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("settlementId", settlementId);
		params.put("excludeSettlementId", Boolean.TRUE);
		List<SetSettlementItem> setSettlementItems = financeSettlementService.searchItemList(params);
		for (SetSettlementItem setSettlementItem : setSettlementItems) {
			vstOrdOrderService.updateOrderItemSettlement(setSettlementItem.getOrderItemMetaId(), setSettlementItem.getSettlementStatus());
		}
		
		this.sendAjaxMsg(JSONObject.fromObject(res).toString());
	}

	/**
	 * 导出Excel,数据量大有问题的
	 */
	@Action("exportExcel")
	public void exportExcel() {
		super.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		List<SetSettlementItem> items = financeSettlementService.searchItemList(this.initSearchParameter());
		Set<Long> supplierIds = new HashSet<Long>();
		Set<Long> targetIds = new HashSet<Long>();
		for(SetSettlementItem item:items){
			supplierIds.add(item.getSupplierId());
			targetIds.add(item.getTargetId());
		}
		Map<String,Object> vstParams = new HashMap<String,Object>();
		vstParams.put("supplierIds", supplierIds);
		List<VstSuppSupplierVo> vstSuppSupplierVos =vstSuppSupplierService.findVstSuppSupplierByParams(vstParams); 
		vstParams.put("settleRuleIds", targetIds);
		List<VstSuppSupplierSettlementVo> vstSuppSupplierSettlementVos =vstSuppSupplierService.findSuppSupplierSettlementByParams(vstParams);
		Map<Long,VstSuppSupplierVo> supplierMap = supplierListTransferMap(vstSuppSupplierVos);
		Map<Long,VstSuppSupplierSettlementVo> settleMap = settleListTransferMap(vstSuppSupplierSettlementVos);
		for(int i=0;i<items.size();i++){
			SetSettlementItem item=items.get(i);
			SetSettlementItemExcel excel = new SetSettlementItemExcel();
			BeanUtils.copyProperties(item, excel);
			VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo = settleMap.get(item.getTargetId());
			if(null!=vstSuppSupplierSettlementVo){
				excel.setTargetName(vstSuppSupplierSettlementVo.getSettleName());
				excel.setSettlementPeriod(vstSuppSupplierSettlementVo.getSettlePeriod());
				excel.setBankAccount(vstSuppSupplierSettlementVo.getAccountNo());
				excel.setBankName(vstSuppSupplierSettlementVo.getAccountBank());
				excel.setBankAccountName(vstSuppSupplierSettlementVo.getAccountName());
				excel.setCompanyId(vstSuppSupplierSettlementVo.getLvAccSubject());
			}
			VstSuppSupplierVo vstSuppSupplierVo = supplierMap.get(item.getSupplierId());
			if(null!=vstSuppSupplierVo){
				excel.setSupplierName(vstSuppSupplierVo.getSupplierName());
				excel.setSupplierId(item.getSupplierId());
			}
			items.set(i, excel);
		}
		String templatePath = "/WEB-INF/resources/template/settlement_detail.xls";
		String fileName = "set_order_detail_" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss")+".xls";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", items);
		this.exportXLS(map, templatePath, fileName);
	}

	@Override
	public Map<String, Object> initRequestParameter() {
		super.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		HttpServletRequest request = getRequest();
		Map<String, Object> map = new HashMap<String, Object>();
		extractRequestParam(map, "orderId", Long.class, request);
		extractRequestParam(map, "metaProductId", Long.class, request);
		if(map.get("metaProductId")!=null ||map.get("orderId")!=null ){
			map.put("unionFlag", true);
		}
		extractRequestParam(map, "filialeName", request);
		extractRequestParam(map, "targetId", Long.class, request);
		extractRequestParam(map, "settlementId", request);
		extractRequestParam(map, "status", request);
		extractRequestParam(map, "createTimeBegin", request);
		extractRequestParam(map, "createTimeEnd", request);
		extractRequestParam(map, "userName", request);
		extractRequestParam(map, "visitTimeBegin", request);
		extractRequestParam(map, "visitTimeEnd", request); 
		return map;
	}

	/**
	 * 从结算单中进入结算单订单明细
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @return
	 */
	@Action("searchOrderDetailIndex")
	public String detail() {
		Long sid = Long.parseLong(settlementId);
		getRequest().setAttribute("init", true);
		getRequest().setAttribute("settlementId", sid);
		SetSettlement settlement = new SetSettlement();
		params.put("settlementId", settlementId);
		List<SetSettlement> result =  financeSettlementService.searchSettleList(params);
		if(null!=result && !result.isEmpty()){
			settlement = result.get(0);
		}
		Long targetId = settlement.getTargetId();
		VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(targetId);
		if(null!=vstSuppSupplierSettlementVo){
			settlement.setTargetName(vstSuppSupplierSettlementVo.getAccountName());
			Long supplierId = vstSuppSupplierSettlementVo.getSupplierId();
			VstSuppSupplierVo vstSuppSupplierVo =vstSuppSupplierService.findVstSuppSupplierById(supplierId);
			if(null!=vstSuppSupplierVo){
				settlement.setSupplierName(vstSuppSupplierVo.getSupplierName());
				settlement.setSupplierId(supplierId);
			}
			
		}
		getRequest().setAttribute("settlement", settlement);
		return "orderDetail";
	}

	/**
	 * 查询结算单订单明细
	 */
	@Action("searchOrderDetail")
	public void searchOrderDetail() {
		Page<SetSettlementItem> page = financeSettlementService.searchItemListPage(this.initSearchParameter());
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
	 * 查询结算总价
	 */
	@Action("sumprice")
	public void sumPrice() {
		Long p = financeSettlementService.searchSumprice(this.initSearchParameter());
		p = p == null ? 0 : p;
		Float f = PriceUtil.convertToYuan(p);
		this.sendAjaxMsg(f.toString());
	}

	/**
	 * 删除结算单中的订单结算项
	 */
	@Action("removeSettlementItem")
	public void removeSettlementItem() {
		int res = financeSettlementService.removeSettlementItem(Long.parseLong(settlementId), settlementItemIds, getSessionUserName());
		
		//更新VST系统订单子项结算状态
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("settlementItemIds", settlementItemIds);
		params.put("excludeSettlementId", Boolean.TRUE);
		LOG.info("params ----- " + params);
		List<SetSettlementItem> setSettlementItems = financeSettlementService.searchItemList(params);
		LOG.info("setSettlementItems size ----- " + setSettlementItems.size());
		for (SetSettlementItem setSettlementItem : setSettlementItems) {
			vstOrdOrderService.updateOrderItemSettlement(setSettlementItem.getOrderItemMetaId(), Constant.SETTLEMENT_STATUS.UNSETTLEMENTED.name());
		}
		
		this.sendAjaxMsg(res + "");
	}

	/**
	 * 查看change日志
	 * 
	 * @return
	 */
	@Action("searchSettlementChange")
	public String searchSettlementChange() {
		HttpServletRequest request = getRequest();
		String page = request.getParameter("page");
		if(!StringUtil.isEmptyString(page)){
			currentPage = Long.parseLong(page);
		}else{
			currentPage = 1;
		}
		Map<String, Object> searchParameter = new HashMap<String, Object>();
		searchParameter.put("currentPage", currentPage);
		searchParameter.put("pageSize", 12);
		searchParameter.put("settlementId", settlementId);
		Page<SetSettlementChange> settlementChangePage = setSettlementService.searchChangeRecord(searchParameter);
		settlementChangePage.buildUrl(getRequest());
		request.setAttribute("settlementChangePage", settlementChangePage);
		String paginationHtml = Pagination.pagination(settlementChangePage);
		request.setAttribute("paginationHtml", paginationHtml);
		
		return "record";
	}

	/**
	 * 根据订单号查询订单结算项
	 */
	@Action("searchOrder")
	public void searchOrder() {
		Long orderId = Long.parseLong(getRequestParameter("orderId").trim());
		Long targetId = Long.parseLong(getRequestParameter("targetId"));
		params.put("orderId", orderId);
		params.put("targetId", targetId);
		List<SetSettlementItem> result = financeSettlementService.searchItemList(params);
		this.sendAjaxResultByJson(JSONArray.fromObject(result).toString());
	}

	/**
	 * 增加订单结算项到结算单中
	 */
	@Action("addOrder")
	public void addOrder() {
		Integer res = financeSettlementService.addOrder(Long.parseLong(settlementId), settlementItemIds, getSessionUserName());
		
		
		//更新VST系统订单子项结算状态
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("settlementItemIds", settlementItemIds);
		params.put("excludeSettlementId", Boolean.TRUE);
		List<SetSettlementItem> setSettlementItems = financeSettlementService.searchItemList(params);
		for (SetSettlementItem setSettlementItem : setSettlementItems) {
			vstOrdOrderService.updateOrderItemSettlement(setSettlementItem.getOrderItemMetaId(), Constant.SETTLEMENT_STATUS.SETTLEMENTING.name());
		}
		
		this.sendAjaxMsg(res.toString());
	}
	
	public static int getTotalpages(final int totalrecords,final int pagesize) {
		if (totalrecords < 0) {
			return -1;
		}
		int count = totalrecords / pagesize;
		if (totalrecords % pagesize > 0) {
			count++;
		}
		return count;
	}
	
	private Map<Long,VstSuppSupplierVo> supplierListTransferMap(final List<VstSuppSupplierVo> vstSuppSupplierVos){
		Map<Long,VstSuppSupplierVo> map = new HashMap<Long,VstSuppSupplierVo>();
		for(VstSuppSupplierVo vstSuppSupplierVo:vstSuppSupplierVos){
			map.put(vstSuppSupplierVo.getSupplierId(), vstSuppSupplierVo);
		}
		return map;
	}
	private Map<Long,VstSuppSupplierSettlementVo> settleListTransferMap(final List<VstSuppSupplierSettlementVo> vstSuppSupplierSettlementVos){
		Map<Long,VstSuppSupplierSettlementVo> map = new HashMap<Long,VstSuppSupplierSettlementVo>();
		for(VstSuppSupplierSettlementVo vstSuppSupplierVo:vstSuppSupplierSettlementVos){
			map.put(vstSuppSupplierVo.getSettleRuleId(), vstSuppSupplierVo);
		}
		return map;
	}
	public SET_SETTLEMENT_STATUS[] getSetSettlementStatus() {
		return setSettlementStatus;
	}

	public void setSetSettlementService(SetSettlementService setSettlementService) {
		this.setSettlementService = setSettlementService;
	}

	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}
	public void setSettlementItemIds(List<Long> settlementItemIds) {
		this.settlementItemIds = settlementItemIds;
	}

	public void setOrderItemMetaIds(List<Long> orderItemMetaIds) {
		this.orderItemMetaIds = orderItemMetaIds;
	}
	public FILIALE_NAME[] getFilialeNames() {
		return filialeNames;
	}

}
