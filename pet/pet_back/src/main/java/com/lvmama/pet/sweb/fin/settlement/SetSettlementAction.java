package com.lvmama.pet.sweb.fin.settlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlement;
import com.lvmama.comm.pet.po.fin.SetSettlementChange;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.service.fin.FinSupplierMoneyService;
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
import com.lvmama.pet.sweb.fin.common.FinPageAction;

/**
 * 结算单管理
 * 
 * @author yanggan
 * 
 */
@Results(value = { @Result(name = "index", location = "/WEB-INF/pages/back/fin/settlement/set_settlement.ftl"), 
		@Result(name = "info", location = "/WEB-INF/pages/back/fin/settlement/set_settlement_info.ftl"), 
		@Result(name = "pay", location = "/WEB-INF/pages/back/fin/settlement/set_settlement_pay.ftl"), 
		@Result(name = "orderDetail", location = "/WEB-INF/pages/back/fin/settlement/set_settlement_item_detail.ftl"), 
		@Result(name = "record", location = "/WEB-INF/pages/back/fin/settlement/set_settlement_change.ftl") })
@Namespace(value = "/fin/set/settlement")
public class SetSettlementAction extends FinPageAction {

	private static final long serialVersionUID = 1L;

	private TopicMessageProducer orderMessageProducer;

	private SetSettlementService setSettlementService;

	private FinSupplierMoneyService finSupplierMoneyService;

	private SetSettlementItemService setSettlementItemService;

	private ContactService contactService;

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
		if(StringUtil.isEmptyString(orderBy)){
			orderBy = "SETTLEMENTID";
			order = "desc";
		}
		Page<SetSettlement> result = this.setSettlementService.searchList(this.initSearchParameter());
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}

	/**
	 * 进入打款页面
	 * 
	 * @return
	 */
	@Action("pay")
	public String pay() {
		SetSettlement settlement = setSettlementService.searchBySettlementId(Long.parseLong(settlementId));
		FinSupplierMoney supplierMoney = finSupplierMoneyService.searchBySupplierId(settlement.getSupplierId());
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
		Map<String, Object> res = setSettlementService.toPay(Long.parseLong(settlementId), advanceDepositPayAmount, deductionPayAmount, bankPayAmount, bankName, operatetime, serial, getSessionUserName());
		this.sendAjaxResultByJson(JSONObject.fromObject(res).toString());
	}

	/**
	 * 结算单详情
	 * 
	 * @return
	 */
	@Action("info")
	public String info() {
		Long id = Long.parseLong(settlementId);
		HttpServletRequest request = getRequest();
		Integer type = Integer.parseInt(request.getParameter("type"));
		SetSettlement settlement = setSettlementService.searchBySettlementId(id);
		ComContact contact = null;
		if (settlement.getTargetId() != null) {
			contact = this.contactService.getSupSettlementTragetContactByTargetId(settlement.getTargetId());
		}
		if (contact == null) {
			contact = new ComContact();
		}
		settlement.setContact(contact);
		SetSettlement initalSettlement = setSettlementService.searchInitalSettlement(id);
		ComContact contactSo = null;
		if (initalSettlement.getTargetId() != null) {
			contactSo = this.contactService.getSupSettlementTragetContactByTargetId(initalSettlement.getTargetId());
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
		// 原始对象信息
		SetSettlement initalSettlement = setSettlementService.searchInitalSettlement(id);
		ComContact contactSo = null;
		if (initalSettlement.getTargetId() != null) {
			contactSo = this.contactService.getSupSettlementTragetContactByTargetId(initalSettlement.getTargetId());
		}
		if (contactSo == null) {
			contactSo = new ComContact();
		}
		initalSettlement.setContact(contactSo);
		SetSettlement settlement = new SetSettlement();
		settlement.setSettlementId(initalSettlement.getSettlementId());
		settlement.setMemo(initalSettlement.getMemo());
		settlement.setBankName(initalSettlement.getBankName());
		settlement.setBankAccountName(initalSettlement.getBankAccountName());
		settlement.setBankAccount(initalSettlement.getBankAccount());
		settlement.setAlipayName(initalSettlement.getAlipayName());
		settlement.setAlipayAccount(initalSettlement.getAlipayAccount());
		// 修改固话结算对象数据
		setSettlementService.updateInitalInfo(settlement);
		this.sendAjaxResultByJson(JSONObject.fromObject(initalSettlement).toString());
	}

	/**
	 * 结算单结算
	 */
	@Action("settle")
	public void settle() {
		String memo = getRequestParameter("memo");
		Map<String,Object> res = setSettlementService.settle(Long.parseLong(settlementId), memo, getSessionUserName());
		List<Long> orderItemMetaIds = setSettlementItemService.searchOrderItemMetaIdsBySettlementId(Long.parseLong(settlementId));
		if (orderItemMetaIds.size() > 0) {
			orderMessageProducer.sendMsg(MessageFactory.newSetOrderItemMetaMessage(Constant.SETTLEMENT_STATUS.SETTLEMENTED.name(), orderItemMetaIds, getSessionUserName()));
		}
		this.sendAjaxMsg(JSONObject.fromObject(res).toString());
	}

	/**
	 * 导出Excel
	 */
	@Action("exportExcel")
	public void exportExcel() {
		List<SetSettlementItem> items = new ArrayList<SetSettlementItem>();
		if ("single".equals(getRequestParameter("type"))) {
			items = this.setSettlementItemService.searchItemExcelData2(this.initSearchParameter());
		} else {
			List<Long> settlementIds = this.setSettlementService.searchSettlementIds(this.initSearchParameter());
			if(settlementIds.size() > 0){
				int pagesize=800;
				int size = settlementIds.size();
				int count = getTotalpages(size,pagesize);
				for(int i=0;i<count;i++){
					int begin = (i*pagesize)>0?((i*pagesize)>size?size:(i*pagesize)):0;
					int end = (begin+pagesize)>size?size:(begin+pagesize);
					List<Long> subList = settlementIds.subList(begin, end);
					List<SetSettlementItem> subItems = this.setSettlementItemService.searchItemExcelData1(subList);
					if((items.size() + subItems.size())>Constant.MAX_EXPORT_EXCEL_NUM)
						break;
					items.addAll(subItems);
				}
			}
		}
	//	String settlementType = getRequestParameter("settlementType");
		String templatePath = null;
		//if("ORDER".equals(settlementType)){
		//	templatePath = "/WEB-INF/resources/template/set_order_detail.xlsx";
		//}else{
			templatePath = "/WEB-INF/resources/template/set_settlement_detail.xls";
		//}
		String fileName = "set_order_detail_" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss")+".xls";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", items);
		this.exportXLS(map, templatePath, fileName);
	}

	@Override
	public Map<String, Object> initRequestParameter() {
		HttpServletRequest request = getRequest();
		Map<String, Object> map = new HashMap<String, Object>();
		extractRequestParam(map, "orderId", Long.class, request);
		extractRequestParam(map, "metaProductId", Long.class, request);
		if(map.get("metaProductId")!=null ||map.get("orderId")!=null ){
			map.put("unionFlag", true);
		}
		//extractRequestParam(map, "settlementType", request);
		extractRequestParam(map, "filialeName", request);
		extractRequestParam(map, "targetId", Long.class, request);
		extractRequestParam(map, "settlementId", request);
		extractRequestParam(map, "status", request);
		extractRequestParam(map, "createTimeBegin", request);
		extractRequestParam(map, "createTimeEnd", request);
		extractRequestParam(map, "userName", request);
//		extractRequestParam(map, "settlementType", request);
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
		SetSettlement settlement = setSettlementService.searchBySettlementId(sid);
		getRequest().setAttribute("settlement", settlement);
		return "orderDetail";
	}

	/**
	 * 查询结算单订单明细
	 */
	@Action("searchOrderDetail")
	public void searchOrderDetail() {
		Page<SetSettlementItem> page = setSettlementItemService.searchItemDetailList(this.initSearchParameter());
		this.sendAjaxResultByJson(JSONObject.fromObject(page).toString());
	}

	/**
	 * 查询结算总价
	 */
	@Action("sumprice")
	public void sumPrice() {
		Long p = setSettlementItemService.searchSumprice(this.initSearchParameter());
		p = p == null ? 0 : p;
		Float f = PriceUtil.convertToYuan(p);
		this.sendAjaxMsg(f.toString());
	}

	/**
	 * 删除结算单中的订单结算项
	 */
	@Action("removeSettlementItem")
	public void removeSettlementItem() {
		int res = setSettlementItemService.removeSettlementItem(Long.parseLong(settlementId), settlementItemIds, getSessionUserName());
		// 把从结算单中移除的订单结算项更新为未结算
		orderMessageProducer.sendMsg(MessageFactory.newSetOrderItemMetaMessage(Constant.SETTLEMENT_STATUS.UNSETTLEMENTED.name(), orderItemMetaIds, getSessionUserName()));
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
		String settlementType = getRequestParameter("settlementType");
		List<SetSettlementItem> result = setSettlementItemService.searchListByOrderId(orderId, targetId,settlementType);
		this.sendAjaxResultByJson(JSONArray.fromObject(result).toString());
	}

	/**
	 * 增加订单结算项到结算单中
	 */
	@Action("addOrder")
	public void addOrder() {
		Integer res = setSettlementItemService.addOrder(Long.parseLong(settlementId), settlementItemIds, getSessionUserName());
		if(res == 1){
			List<Long> orderItemMetaIds = setSettlementItemService.searchOrderItemMetaIdsBySettlementItemId(settlementItemIds);
			//把订单子子项的结算状态更新为结算中
			orderMessageProducer.sendMsg(MessageFactory.newSetOrderItemMetaMessage(Constant.SETTLEMENT_STATUS.SETTLEMENTING.name(), orderItemMetaIds, getSessionUserName()));
		}
		this.sendAjaxMsg(res.toString());
	}
	/**
	 * 修改结算总价
	 */
	@Action("modifySettlementPrice")
	public void modifySettlementPrice(){
		String type = getRequestParameter("type");
		Long settlementItemId = Long.parseLong(getRequestParameter("settlementItemId"));
		long settlementPrice = PriceUtil.convertToFen(getRequestParameter("settlementPrice"));
		String remark = getRequestParameter("remark");
		Integer res = setSettlementItemService.modifySettlementPrice(settlementItemId, settlementPrice, Long.parseLong(settlementId), remark, type, getSessionUserName());
		this.sendAjaxMsg(res.toString());
	}
	/**
	 * 批量结算价
	 */
	@Action("batchModifySettlementPrice")
	public void batchModifySettlementPrice(){
		Long metaProductId = Long.parseLong(getRequestParameter("metaProductId"));
		long settlementPrice = PriceUtil.convertToFen(getRequestParameter("settlementPrice"));
		String remark = getRequestParameter("remark");
		Integer res = setSettlementItemService.batchModifySettlementPrice(metaProductId, settlementPrice, Long.parseLong(settlementId), remark, getSessionUserName());
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

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}

	public void setFinSupplierMoneyService(FinSupplierMoneyService finSupplierMoneyService) {
		this.finSupplierMoneyService = finSupplierMoneyService;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setSetSettlementItemService(SetSettlementItemService setSettlementItemService) {
		this.setSettlementItemService = setSettlementItemService;
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
