package com.lvmama.finance.group.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.comm.bee.service.IGroupBudgetService;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.service.fin.FinSupplierMoneyService;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.group.ibatis.po.FinGroupSettlement;
import com.lvmama.finance.group.ibatis.po.GroupSettlementInfo;
import com.lvmama.finance.group.ibatis.po.OrderInfoDetail;
import com.lvmama.finance.group.ibatis.po.TravelGroup;
import com.lvmama.finance.group.service.GroupService;

/**
 * 团单项结算
 * 
 * @author yanggan
 * 
 */
@Controller
@RequestMapping("/group/settlement")
public class SettlementAction extends BaseAction {

	@Autowired
	private IGroupBudgetService groupBudgetService;
	
	@Autowired
	private GroupService groupService;
	@Autowired
	private ContactService contactService;

	@Autowired
	private FinSupplierMoneyService finSupplierMoneyService;
	
	public FinSupplierMoneyService getFinSupplierMoneyService() {
		return finSupplierMoneyService;
	}

	public void setFinSupplierMoneyService(
			FinSupplierMoneyService finSupplierMoneyService) {
		this.finSupplierMoneyService = finSupplierMoneyService;
	}

	private Long groupSettlementId;

	public Long getGroupSettlementId() {
		return groupSettlementId;
	}

	public void setGroupSettlementId(Long groupSettlementId) {
		this.groupSettlementId = groupSettlementId;
	}
	
	/**
	 * 用来保存总金额
	 */
	private Page<FinGroupSettlement> settlementPage;

	public Page<FinGroupSettlement> getSettlementPage() {
		return settlementPage;
	}

	public void setSettlementPage(Page<FinGroupSettlement> settlementPage) {
		this.settlementPage = settlementPage;
	}

	/**
	 * 进入团单项查询页面
	 * 
	 * @return 查询页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String advancedeposits() {
		return "group/settlement/manage";
	}

	/**
	 * 查询团单项结算
	 * 
	 * @return 结果页面
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/search")
	public Page<FinGroupSettlement> search() {
		
		settlementPage = groupService.searchSettlementSumprice();
		
		return groupService.searchSettlement();
	}
	
	/**
	 * 查询订单详细信息
	 * @return
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/settlementSumprice")
	public void settlementSumprice(Model model){
		// 根据团号查询订单应付总金额和实收总金额
		List<FinGroupSettlement> groupSettlementList = settlementPage.getInvdata();
		FinGroupSettlement amountTotal = new FinGroupSettlement();
		if(null != groupSettlementList && groupSettlementList.size() > 0){
			amountTotal = groupSettlementList.get(0);
		}else{
			amountTotal.setSubTotalCostsTotal(0d);
			amountTotal.setPayAmountTotal(0d);
		}
		
		model.addAttribute("amountTotal", amountTotal);
	}

	/**
	 * 进入打款页面
	 * @param groupSettlementId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String pay(@RequestParam("groupSettlementIds") String groupSettlementIds, Model model) {
		String[] idList = groupSettlementIds.split(",");
		// 查询当前操作的结算单
		List<FinGroupSettlement> settlementList = groupService.searchById(idList);
		// 计算应打款金额
		float oughtPayAmount = 0f;
		for(int i=0; i<settlementList.size(); i++){
			FinGroupSettlement finGroupSettlement = (FinGroupSettlement)settlementList.get(i);
			if(null != finGroupSettlement.getSubtotalCosts()){
				oughtPayAmount += finGroupSettlement.getSubtotalCosts().floatValue() - finGroupSettlement.getPayAmount();
			}
		}
		// 获取当前的供应商
		FinGroupSettlement finGroupSettlement = (FinGroupSettlement)settlementList.get(0);
		// 根据供应商id查询供应上的抵扣款和预存款
		FinSupplierMoney supplierMoney = finSupplierMoneyService.searchBySupplierId(finGroupSettlement.getSupplierId());
		if (supplierMoney == null) {
			supplierMoney = new FinSupplierMoney();
		}
		boolean readOnlyFlag = false;
		if(idList.length > 1){
			readOnlyFlag = true;
		}
		
		String currentDate =  com.lvmama.comm.utils.DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm");
		model.addAttribute("settlement", finGroupSettlement);
		model.addAttribute("supplierMoney", supplierMoney);
		model.addAttribute("oughtPayAmount", oughtPayAmount);
		model.addAttribute("groupSettlementIds", groupSettlementIds);
		model.addAttribute("readOnlyFlag", readOnlyFlag);
		model.addAttribute("currentDate", currentDate);
		
		return "group/settlement/pay";
	}

	/**
	 * 打款/合并打款
	 * @param groupSettlementIds
	 * @param operatetimes
	 * @param invoiceRetdates
	 * @param osp
	 * @param model
	 */
	@RequestMapping(value = "/doPay")
	public void toPay(@RequestParam("amountYuan") Float amountYuan, @RequestParam(value="invoiceRetdates", required=false) String invoiceRetdates, SetSettlementPayment osp, Model model) {
		osp.setAmountYuan(amountYuan);
		if (!StringUtil.isEmptyString(invoiceRetdates)) {
			osp.setInvoiceRetdate(DateUtil.stringToDate(invoiceRetdates, "yyyy-MM-dd"));
		}
		String groupCode = groupService.bankPay(osp);
		
		// 如果团号不为空，则修改团的实际成本表
		if(!StringUtil.isEmptyString(groupCode)){
			this.countFinalBudgetByTravelCode(groupCode);
		}
	}

	/**
	 * 线下付款
	 * 
	 * @param groupSettlementIds
	 *            团单项结算ID集合
	 * @param osp
	 *            打款信息
	 */
//	@RequestMapping(value = "/bankpay")
//	public void bankPay(@RequestParam("groupSettlementIds") Long[] groupSettlementIds, @RequestParam("operatetimes") String operatetimes, @RequestParam(value = "invoiceRetdates", required = false) String invoiceRetdates, OrdSettlementPayment osp, Model model) {
//		Date operatetime = DateUtil.stringToDate(operatetimes, "yyyy-MM-dd HH:mm");
//		osp.setOperatetime(operatetime);
//		if (invoiceRetdates != null) {
//			Date invoiceRetdate = DateUtil.stringToDate(invoiceRetdates, "yyyy-MM-dd");
//			osp.setInvoiceRetdate(invoiceRetdate);
//		}
//		String groupCode = groupService.bankPay(groupSettlementIds, osp);
//		if(!StringUtil.isEmptyString(groupCode)){
//			this.countFinalBudgetByTravelCode(groupCode);
//		}
//	}

	/**
	 * 预存款付款
	 * 
	 * @param groupSettlementIds
	 *            团单项结算ID集合
	 * @param osp
	 *            打款信息
	 */
//	@RequestMapping(value = "/advpay")
//	public void advPay(@RequestParam("groupSettlementIds") Long[] groupSettlementIds, @RequestParam(value = "invoiceRetdates", required = false) String invoiceRetdates, OrdSettlementPayment osp, Model model) {
//		if (invoiceRetdates != null) {
//			Date invoiceRetdate = DateUtil.stringToDate(invoiceRetdates, "yyyy-MM-dd");
//			osp.setInvoiceRetdate(invoiceRetdate);
//		}
//		String groupCode = groupService.advPay(groupSettlementIds, osp);
//		if(!StringUtil.isEmptyString(groupCode)){
//			this.countFinalBudgetByTravelCode(groupCode);
//		}
//	}

	private void countFinalBudgetByTravelCode(String groupCode){
		TravelGroup group = groupService.searchGroup(groupCode);
		if("COSTED".equals(group.getSettlementStatus())){
			//修改团的实际成本表
			groupBudgetService.countFinalBudgetByTravelCode(groupCode);
		}
	}
	/**
	 * 删除抵扣款
	 * 
	 * @param ids
	 *            团单项结算ID集合
	 */
	@RequestMapping(value = "/deldk")
	public void deldk(@RequestParam("ids") String ids) {
		groupService.deldk(ids);
	}
	
	/**
	 * 结算单详细信息
	 * 
	 * @param id
	 *            结算单ID
	 * @return
	 */
	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public String confirm(@PathVariable("id") Long id, Model model) {
		// 原始对象信息
		GroupSettlementInfo ord = groupService.searchGroupSettlement(id);
		ComContact comContact = null;
		if(ord.getTargetId()!=null){
			comContact = contactService.getSupSettlementTragetContactByTargetId(Long.valueOf(ord.getTargetId()+""));			
		}
		if(comContact==null){
			comContact = new ComContact();
		}
		ord.setContact(comContact);
		model.addAttribute("group", ord);
		return "group/settlement/info";
	}
	
	/**
	 * 进入结算单详细信息页面
	 * 
	 * @param id
	 *            结算单ID
	 * @return
	 */
	@RequestMapping(value = "/orderInfoDetail/{groupSettlementId}", method = RequestMethod.GET)
	public String orderInfo(@PathVariable("groupSettlementId") Long groupSettlementId, Model model) {
		this.groupSettlementId = groupSettlementId;
		FinGroupSettlement finGroupSettlement = groupService.searchSettlementById(groupSettlementId);
		model.addAttribute("init", true);
		model.addAttribute("groupSettlementCode", finGroupSettlement.getTravelGroupCode());
		return "group/settlement/orderInfo";
	}
	
	/**
	 * 查询订单详细信息
	 * @return
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/orderInfoDetail/search")
	public Page<OrderInfoDetail> searchOrderInfoDetail(Model model){
		Map map = new HashMap();
		FinGroupSettlement finGroupSettlement = groupService.searchSettlementById(groupSettlementId);
		String type = finGroupSettlement.getBudgetItemType();
		map.put("travelGroupCode", finGroupSettlement.getTravelGroupCode());
		map.put("groupSettlementId", groupSettlementId);
		map.put("type", type);
		return groupService.searchOrderInfoDetail(map);
	}
	
	/**
	 * 查询订单详细信息
	 * @return
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/orderInfoDetail/sumprice")
	public void sumprice(Model model){
		// 根据团号查询申请付款金额和已付款金额
		FinGroupSettlement finGroupSettlement = groupService.searchSettlementById(groupSettlementId);
		Map map = new HashMap();
		String type = finGroupSettlement.getBudgetItemType();
		map.put("travelGroupCode", finGroupSettlement.getTravelGroupCode());
		map.put("groupSettlementId", groupSettlementId);
		map.put("type", type);
		// 根据团号查询订单应付总金额和实收总金额
		OrderInfoDetail orderInfoDetail = groupService.searchSumprice(map);
		if(null != finGroupSettlement){
			if(null != finGroupSettlement.getUnit()){
				orderInfoDetail.setUnit(finGroupSettlement.getUnit());
			}else{
				orderInfoDetail.setUnit("元");
			}
			
			if(null != finGroupSettlement.getSubtotalCosts()){
				orderInfoDetail.setSubTotalCosts(finGroupSettlement.getSubtotalCosts());
			}else{
				orderInfoDetail.setSubTotalCosts(0d);
			}
			
			if(null != finGroupSettlement.getPayAmount()){
				orderInfoDetail.setPayAmount(finGroupSettlement.getPayAmount());
			}else{
				orderInfoDetail.setPayAmount(0d);
			}
			orderInfoDetail.setSurplusAmount(orderInfoDetail.getSubTotalCosts() - orderInfoDetail.getPayAmount());
		} else {
			orderInfoDetail.setUnit("元");
			orderInfoDetail.setSubTotalCosts(0d);
			orderInfoDetail.setPayAmount(0d);
			orderInfoDetail.setSurplusAmount(0d);
		}
		
		model.addAttribute("orderInfoDetail", orderInfoDetail);
	}
	
	/**
	 * 导出订单明细
	 */
	@PageSearch(autobind=true)
	@RequestMapping(value = "/orderInfoDetail/export")
	public void exportOrderDetail(){
		String templatePath = "/WEB-INF/resources/template/order_group_detail.xls";
		String fileName = "order_group_detail_" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
		
		FinGroupSettlement finGroupSettlement = groupService.searchSettlementById(groupSettlementId);
		Map<String, Object> searchMap = new HashMap<String, Object>();
		String type = finGroupSettlement.getBudgetItemType();
		searchMap.put("travelGroupCode", finGroupSettlement.getTravelGroupCode());
		searchMap.put("groupSettlementId", groupSettlementId);
		searchMap.put("type", type);
		List<OrderInfoDetail> list = groupService.exportOrderInfoDetail(searchMap);
		if(list.size()==0){
			OrderInfoDetail a = new OrderInfoDetail();
			list.add(a);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list",list);
		if(null != list && list.size() > 0){
			map.put("OrderInfoDetail",list.get(0));
		}else{
			map.put("OrderInfoDetail", new OrderInfoDetail());
		}
		this.exportXLS(map, templatePath, fileName);
	}
	
	/**
	 * 导出
	 */
	@PageSearch(autobind=true)
	@RequestMapping(value = "/export")
	public void export(){
		String templatePath = "/WEB-INF/resources/template/order_group_detail.xls";
		String fileName = "order_group_detail_" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
		Map<String, Object> map = new HashMap<String, Object>();
		List<OrderInfoDetail> list = groupService.exportOrderDetail();
		if(list.size() == 0){
			OrderInfoDetail a = new OrderInfoDetail();
			list.add(a);
		}
		map.put("list",list);
		if(null != list && list.size() > 0){
			map.put("OrderInfoDetail",list.get(0));
		}else{
			map.put("OrderInfoDetail", new OrderInfoDetail());
		}
		this.exportXLS(map, templatePath, fileName);
	}
	
}
