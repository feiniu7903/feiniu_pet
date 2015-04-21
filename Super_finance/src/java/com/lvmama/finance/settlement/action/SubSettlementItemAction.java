package com.lvmama.finance.settlement.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.Constant;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlement;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementChange;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlementItem;
import com.lvmama.finance.settlement.ibatis.vo.OrderProductDetail;
import com.lvmama.finance.settlement.ibatis.vo.OrderSearchResult;
import com.lvmama.finance.settlement.service.OrdSettlementService;

/**
 * 结算子单项管理
 * 
 * @author yanggan
 * 
 */
@Controller
@RequestMapping("/settlement/ordsettlement/detail")
public class SubSettlementItemAction extends BaseAction {

	@Autowired
	private OrdSettlementService ordSettlementService;
	
	/**
	 * 从结算单中进入结算单明细
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/{settlementId}")
	public String detail(@PathVariable("settlementId") Long settlementId, Model model) {
		return this.detail(1, settlementId, null, model);
	}

	/**
	 * 从结算子单中进入结算单明细
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @param subSettlementId
	 *            结算子单ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search/{settlementId}/{subSettlementId}")
	public String detail(@PathVariable("settlementId") Long settlementId, @PathVariable("subSettlementId") Long subSettlementId, Model model) {
		return this.detail(2, settlementId, subSettlementId, model);
	}

	private String detail(Integer type, Long settlementId, Long subSettlementId, Model model) {
		model.addAttribute("init", true);
		model.addAttribute("type", type);
		model.addAttribute("settlementId", settlementId);
		OrdSettlement ors = ordSettlementService.getOrdSettlementById(settlementId);
		model.addAttribute("ors", ors);
		if (type == 2) {
			model.addAttribute("subSettlementId", subSettlementId);
		}
		return "settlement/ordsettlement/order_detail";
	}

	/**
	 * 查询结算子单项明细
	 * 
	 * @return
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/search")
	public Page<OrdSubSettlementItem> detail() {
		return ordSettlementService.searchOrdSubSettlementItem();
	}

	/**
	 * 查询结算总价
	 * 
	 * @param model
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/sumprice")
	public void sumprice(Model model) {
		Double f = ordSettlementService.searchSumprice();
		model.addAttribute(f + "");
	}

	/**
	 * 修改单个结算子单项的结算价
	 * 
	 */
	@RequestMapping(value = "/modify/price/{settlementId}")
	public void modify_price(OrdSettlementChange change, @PathVariable("settlementId") Long settlementId, @RequestParam("subSettlementId") Long subSettlementId,Model model) {
		Integer res = ordSettlementService.modifyPrice(change, settlementId, subSettlementId);
		model.addAttribute("res",res);
	}

	/**
	 * 
	 * 批量结算子单项的结算价
	 * 
	 */
	@RequestMapping(value = "/batchmodify/price/{settlementId}")
	public void modify_price(@PathVariable("settlementId") Long settlementId, @RequestParam("subSettlementId") Long subSettlementId, @RequestParam("metaProductId") Long metaProductId, @RequestParam("amount") double amount, @RequestParam("remark") String remark,Model model) {
		Integer res = ordSettlementService.modifyPrice(settlementId, subSettlementId, metaProductId, amount, remark);
		model.addAttribute(res);
	}

	/**
	 * 
	 * 查询操作流水记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/record/{type}/{id}")
	public String record(@PathVariable("type") Integer type, @PathVariable("id") Long id, Model model) {
		List<OrdSettlementChange> list = ordSettlementService.searchChangeRecord(type, id);
		model.addAttribute("result", list);
		return "settlement/ordsettlement/record";
	}

	/**
	 * 
	 * 删除结算子单项
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @param delIds
	 *            删除的id
	 */
	@RequestMapping(value = "/delete/{settlementId}")
	public void delete(@PathVariable("settlementId") Long settlementId, @RequestParam("subSettlementId") Long[] subSettlementIds, @RequestParam("delId") Long[] delIds,@RequestParam(value="slow",required=false) String slow, Model model) {
		String status = null;
		if(StringUtil.isEmptyString(slow)){
			status = Constant.SETTLEMENT_QUEUE_ITEM_STATUS_NORMAL;
		}else{
			status = Constant.SETTLEMENT_QUEUE_ITEM_STATUS_PAUSE;
		}
		Integer res = ordSettlementService.deleteSubSettlementItem(settlementId, subSettlementIds, delIds, status);
		model.addAttribute(res);
	}
	/**
	 * 根据订单号查询
	 * 
	 * @param settlementId 结算单ID
	 * @param orderId 订单号
	 */
	@RequestMapping(value = "/order/{settlementId}")
	public void searchorder(@PathVariable("settlementId") Long settlementId,@RequestParam("orderId") Long orderId,Model model){
		List<OrderSearchResult> res = ordSettlementService.searchOrder(settlementId, orderId);
		model.addAttribute(res);
	}
	
	@RequestMapping(value = "/order/add/{settlementId}")
	public void add(@PathVariable("settlementId") Long settlementId,@RequestParam("ids") List<String> ids,Model model){
		Integer res = ordSettlementService.addOrder(settlementId, ids);
		model.addAttribute(res);
	}
	
	@PageSearch(autobind=true)
	@RequestMapping(value = "/export")
	public void export(@RequestParam(value="exporttype",required = false) String exporttype){
		if(StringUtil.isEmptyString(exporttype)){
			String templatePath = "/WEB-INF/resources/template/order_detail.xls";
			String fileName = "order_detail" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
			Map<String, Object> map = new HashMap<String, Object>();
			List<OrdSubSettlementItem> list = ordSettlementService.exportOrdSubSettlementItem();
			if(list != null && list.size() > 0){
				OrdSubSettlementItem i =  list.get(0);
			}
			map.put("list",list);
			this.exportXLS(map, templatePath, fileName);
		}else if("1".equals(exporttype) || "2".equals(exporttype) ){
			String templatePath = "/WEB-INF/resources/template/order_product_detail.xls";
			String fileName = "order_product_detail" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
			Map<String, Object> map = new HashMap<String, Object>();
			List<OrderProductDetail> list = ordSettlementService.exportOrdProductDetail();
			if(list.size()==0){
				OrderProductDetail a = new OrderProductDetail();
				list.add(a);
			}
			map.put("list",list);
			this.exportXLS(map, templatePath, fileName);
		}
		
	}
	

	/**
	 * 修改结算总价
	 * 
	 */
	@RequestMapping(value = "/modify/totalPrice/{settlementId}")
	public void modify_total_price(OrdSettlementChange change, @PathVariable("settlementId") Long settlementId, Model model) {
		Integer res = ordSettlementService.modifyTotalPrice(change, settlementId);
		if(res == 1){
			res = ordSettlementService.modifyPayAmount(settlementId);
		}
		model.addAttribute("res",res);
	}
	
}
