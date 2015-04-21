package com.lvmama.finance.group.action;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.comm.bee.po.op.OpGroupBudget;
import com.lvmama.comm.bee.po.op.OpOtherIncoming;
import com.lvmama.comm.bee.po.op.ProductOrderDetail;
import com.lvmama.comm.bee.service.IGroupBudgetService;
import com.lvmama.comm.pet.service.fin.FinGLCostService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.group.ibatis.po.TravelGroup;
import com.lvmama.finance.group.ibatis.vo.GroupBudgetFixed;
import com.lvmama.finance.group.ibatis.vo.GroupBudgetProd;
import com.lvmama.finance.group.service.GroupService;

/**
 * 团列表Action
 * 
 * @author yanggan
 * 
 */
@Controller
@RequestMapping("/group/list")
public class GroupListAction extends BaseAction {

	@Autowired
	private GroupService groupService;

	@Autowired
	private IGroupBudgetService groupBudgetService;
	
	@Autowired
	private FinGLCostService finGLCostService;
	
	private String[] groupCodes;
	
	/**
	 * 进入团列表查询页面
	 * 
	 * @return 查询页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String advancedeposits() {
		return "group/list/manage";
	}

	/**
	 * 查询团列表
	 * 
	 * @return 结果页面
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/search")
	public Page<TravelGroup> search() {
		return groupService.searchList();
	}

	/**
	 * 查看团预算/实际成本
	 * 
	 * @param groupCode
	 *            团号
	 * @param type
	 *            类型 （预算/实际成本/核算）
	 * @return 团预算信息
	 */
	@RequestMapping(value = "/budget/{groupCode}/{type}")
	public String budget(@PathVariable("groupCode") String groupCode, @PathVariable("type") String type, Model model) {
		groupCode=decodeGroupCode(groupCode);
		model.addAttribute("groupCode",groupCode);
		OpGroupBudget opGroupBudget = groupService.searchBudget(groupCode);

		boolean check = false;
		if ("CHECK".equals(type)) {
			TravelGroup group = groupService.searchGroup(groupCode);
			model.addAttribute("group", group);
			
			type = "COST";
			check = true;
		}
		if("COST".equals(type)){
			//修改团的实际成本表
			groupBudgetService.countFinalBudgetByTravelCode(groupCode);
		}
		List<GroupBudgetProd> budgetProdList = groupService.searchBudgetProd(groupCode, type);
		List<GroupBudgetFixed> budgetFixedList = groupService.searchBudgetFixed(groupCode, type);
		// 查询附加收入
		List<OpOtherIncoming> otherIncomingList = groupService.searchOtherIncoming(groupCode);
		
		model.addAttribute("budget", opGroupBudget);
		model.addAttribute("budgetProdList", budgetProdList);
		model.addAttribute("budgetFixedList", budgetFixedList);
		model.addAttribute("otherIncomingList", otherIncomingList);
		if (check) {
			type = "CHECK";
		}
		model.addAttribute("type", type);
		return "group/list/detail";
	}

	@RequestMapping(value = "/print/{groupCode}/{type}")
	public String print(@PathVariable("groupCode") String groupCode,@PathVariable("type") String type, Model model){
		groupCode=decodeGroupCode(groupCode);
		model.addAttribute("type", type);
		model.addAttribute("groupCode", groupCode);
		return "group/list/print_detail";
	}
	/**
	 * 核算
	 * 
	 * @param groupCode
	 *            团号
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public void check(@RequestParam("groupCode") String groupCode, Model model) {
		groupService.check(groupCode);
	}
	/**
	 * 进入产品成本查看订单明细查询页面
	 * @param productBranchId 产品分类ID
	 * @param groupCode 团号
	 * @param model 
	 * @return
	 */
	@RequestMapping(value = "/orderDetails/{groupCode}/{prodBranchId}")
	public String searchProductOrderDetails(@PathVariable("groupCode") String groupCode,
			@PathVariable("prodBranchId") Long prodBranchId,Model model){
		groupCode=decodeGroupCode(groupCode);
		model.addAttribute("prodBranchId", prodBranchId);
		model.addAttribute("groupCode", groupCode);
		return "group/list/order_detail";
	}
	/**
	 * 产品成本查看订单明细
	 * @param productBranchId 产品分类ID
	 * @param groupCode 团号
	 * @param model 
	 * @return
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/orderDetails/search")
	public Page<ProductOrderDetail> searchProductOrderDetails(){
		return groupService.searchProductOrderDetails();
	}
	/**
	 * 团成本入账（已做成本，只入固定成本）
	 */
	@RequestMapping(value = "/finGLGroupCost", method = RequestMethod.POST)
	public void finGLGroupCost(@RequestParam("groupCodes") String groupCodes ,Model model){			
		try{
			String[] array=groupCodes.split(",");
			for(int i=0;i<array.length;i++){
				groupService.confirmedGroupCost(array[i]);
			}
			String errorMsg = finGLCostService.generateTravelGroupCostGLData(array);
			model.addAttribute("resultInfo", StringUtil.isEmptyString(errorMsg)); 
			model.addAttribute("errorCode", errorMsg);
			model.addAttribute("errorMsg", "");
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("resultInfo", false); 
			model.addAttribute("errorCode", "");
			model.addAttribute("errorMsg", e);
		}
	}
	public String decodeGroupCode(final String travelGroupCode){
		try {
			return new String(URLDecoder.decode(travelGroupCode.replaceAll("\\+", "########")).getBytes("ISO-8859-1")).replaceAll("########", "+");
		} catch (UnsupportedEncodingException e) {
		}
		return travelGroupCode;
	}

	public String[] getGroupCodes() {
		return groupCodes;
	}

	public void setGroupCodes(String[] groupCodes) {
		this.groupCodes = groupCodes;
	}
}
