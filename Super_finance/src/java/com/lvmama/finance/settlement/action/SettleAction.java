package com.lvmama.finance.settlement.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.base.annotation.Version;
import com.lvmama.finance.base.util.DateUtil;
import com.lvmama.finance.common.ibatis.po.ComboxItem;
import com.lvmama.finance.settlement.ibatis.po.SettlementQueueItem;
import com.lvmama.finance.settlement.service.SettleService;

@Controller
@RequestMapping(value = "/settlement")
public class SettleAction extends BaseAction{
	@Autowired
	private SettleService settleService;
	
	/**
	 * 进入订单结算页面
	 * @return
	 */
	@RequestMapping(value = "/settle", method = RequestMethod.GET)
	public String settle(HttpServletRequest request){
		request.setAttribute("username", this.getSessionUser().getUserName());
		//是否代售订单结算
		if("op".equals(request.getParameter("from"))){
			request.setAttribute("isProxySale", 1);
			request.getSession().setAttribute("isProxySale", 1);
		}else{
			request.setAttribute("isProxySale", 0);
			request.getSession().setAttribute("isProxySale", 0);
		}
		return "settlement/settle/settle_index";
	}

	/**
	 * 查询采购产品的分支类型
	 * @param productId
	 * @return
	 */
	@RequestMapping("/settle/getMetaBranchTypeByMetaProductId")
	public List<ComboxItem> getMetaBranchTypeByMetaProductId(@RequestParam("productId") Long productId){
		return settleService.getMetaBranchTypeByMetaProductId(productId);
	}
	
	/**
	 * 查询结算队列项
	 * @param params
	 * @return
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/settle/searchSettlementQueueItem")
	public Page<SettlementQueueItem> searchSubSettlementItem(HttpServletRequest request){
		Page<SettlementQueueItem> page = settleService.getSettlementQueueItems(getSearchParams(request));
		return page;
	}
	
	/**
	 * 不结
	 * @return
	 */
	@RequestMapping(value = "/settle/noSettle")
	@ResponseBody
	public String noSettle(@RequestParam("params") String params){
		String[] ids = params.split(",");
		List<Long> list = new ArrayList<Long>();
		for(String id : ids){
			list.add(Long.parseLong(id));
		}
		if(null != settleService.noSettle(list)){
			return "SUCCESS";
		}else {
			return "FAULT";
		}
	}
	
	/**
	 * 缓结
	 * @return
	 */
	@RequestMapping(value = "/settle/delaySettle")
	@ResponseBody
	public String delaySettle(@RequestParam("params") String params){
		String[] ids = params.split(",");
		List<Long> list = new ArrayList<Long>();
		for(String id : ids){
			list.add(Long.parseLong(id));
		}
		if(null != settleService.delaySettle(list)){
			return "SUCCESS";
		}
		return "FAULT";
	}
	
	/**
	 * 删除抵扣款
	 * @param params
	 * @return
	 */
	@Version
	@RequestMapping(value="/settle/deleteSettlementQueueItemForCharge")
	@ResponseBody
	public String deleteSettlementQueueItemForCharge(@RequestParam("params") String params){
		String[] ids = params.split(",");
		List<Long> list = new ArrayList<Long>();
		for(String id : ids){
			list.add(Long.parseLong(id));
		}
		if(null != settleService.deleteSettlementQueueItemForCharge(list)){
			return "SUCCESS";
		}
		return "FAULT";
	}
	
	/**
	 * 生成结算单
	 * @param itemList
	 * @return
	 */
	@Version
	@RequestMapping(value = "/settle/createSettlement")
	public Object createSettlement(@RequestParam("metaItemIds") String metaItemIdsStr,@RequestParam("queueItemIds") String queueItemIdsStr){
		String[] metaItemIds = metaItemIdsStr.split(",");
		String[] queueItemIds = queueItemIdsStr.split(",");
		List<Long> list = new ArrayList<Long>();
		for(String id : metaItemIds){
			list.add(Long.parseLong(id));
		}
		List<Long> list2 = new ArrayList<Long>();
		for(String id : queueItemIds){
			if(id == null || id.equals("")){
				continue;
			}
			list2.add(Long.parseLong(id));
		}
		String settlementType = "ORDER";
		if((Integer)getSession().getAttribute("isProxySale") == 1){
			settlementType = "GROUP";
		}
		Map<String, Object> rs = settleService.settle(list,list2,settlementType);
		return rs;
		
	}
	
	/**
	 * 全部生成结算单
	 * @param request
	 * @return
	 */
	@Version
	@RequestMapping(value = "/settle/createSettlementAll")
	public Object createSettlementAll(HttpServletRequest request){
		Map<String, Object> map = getSearchParams(request);
		String settlementType = "ORDER";
		if((Integer)getSession().getAttribute("isProxySale") == 1){
			settlementType = "GROUP";
		}
		return settleService.settlementAll(map,settlementType);
	}
	
	/**
	 * 获取查询条件参数
	 * @param request
	 */
	private Map<String, Object> getSearchParams(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		extractRequestParam(map,"payToFlag",request,"payToFlag");
		extractRequestParam(map,"settlementTarget",request,"settlementTargetInput");
		if(map.get("settlementTarget") != null){
			map.put("settlementTarget", Long.parseLong((String)map.get("settlementTarget")));
		}
		extractRequestParam(map,"settlementPeriod",request,"settlementPeriod");
		extractRequestParam(map,"visitDateStart",request,"visitDateStart");
		extractRequestParam(map,"visitDateEnd",request,"visitDateEnd");
		extractRequestParam(map,"metaProduct",request,"metaProductInput");
		if(map.get("metaProduct") != null){
			map.put("metaProduct", Long.parseLong((String)map.get("metaProduct")));
		}
		extractRequestParam(map,"metaBranchType",request,"metaBranchType");
		if(map.get("metaProduct") == null){
			map.remove("metaBranchType");
		}
		extractRequestParam(map,"isLvmamaTargetContained",request,"isLvmamaTargetContained");
		extractRequestParam(map,"payTimeStart",request,"payTimeStart");
		extractRequestParam(map,"payTimeEnd",request,"payTimeEnd");
		extractRequestParam(map,"settlementCompany",request,"settlementCompany");
		extractRequestParam(map,"hasRefunded",request,"hasRefunded");
//		extractRequestParam(map,"status",request,"status");
		//如果建议打款时间小于当天日期，则系统自动将该打款时间设为当天
		extractRequestParam(map,"suggestionPayDateStart",request,"suggestionPayDateStart");
		String start = (String)map.get("suggestionPayDateStart");
		if(start != null && start.trim().length() > 0){
			Date d = DateUtil.stringToDate(start, "yyyy-MM-dd");
			if(d != null && d.before(DateUtil.getTodayDate())){
				map.put("suggestionPayDateStart", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			}
		}
		extractRequestParam(map,"suggestionPayDateEnd",request,"suggestionPayDateEnd");
		String end = (String)map.get("suggestionPayDateEnd");
		if(end != null && end.trim().length() > 0){
			Date d = DateUtil.stringToDate(end, "yyyy-MM-dd");
			if(d != null && d.before(DateUtil.getTodayDate())){
				map.put("suggestionPayDateEnd", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			}
		}
		extractRequestParam(map,"ordId",request,"ordId");
//		if(map.get("ordId") != null && (map.get("ordId") instanceof String)){
//			map.put("ordId", Long.parseLong((String)map.get("ordId")));
//		}
		extractRequestParam(map,"supplier",request,"supplierInput");
		if(map.get("supplier") != null){
			map.put("supplier", Long.parseLong((String)map.get("supplier")));
		}
		extractRequestParam(map,"bankAccount",request,"bankAccount");
		if(map.get("bankAccount") != null){
			map.put("bankAccount",URLDecoder.decode(URLDecoder.decode((String)map.get("bankAccount"))));
		}
		extractRequestParam(map,"statusNormal",request,"statusNormal");
		extractRequestParam(map,"statusDelaySettle",request,"statusDelaySettle");
		extractRequestParam(map,"statusNoSettle",request,"statusNoSettle");
		extractRequestParam(map,"orderby",request,"orderby");
		if("1".equals(map.get("statusNormal"))
				|| "1".equals(map.get("statusDelaySettle"))
				|| "1".equals(map.get("statusNoSettle"))){
			map.put("existStatus", 1);
		}
		extractRequestParam(map,"username",request,"username");
		extractRequestParam(map,"isProxySale",request,"isProxySale");
		extractRequestParam(map,"createOrderTimeBegin",request,"createOrderTimeBegin");
		extractRequestParam(map,"createOrderTimeEnd",request,"createOrderTimeEnd");
		return map;
	}
	
	/**
	 * 提取HttpServletRequest参数
	 * @param map
	 * @param key
	 * @param request
	 * @param param
	 */
	private void extractRequestParam(Map<String, Object> map,String key, HttpServletRequest request, String param){
		if(map == null || key == null 
			|| key.trim().length() == 0 || param == null 
			|| request.getParameter(param) == null
			|| ((request.getParameter(param) instanceof String) 
					&& ((String)(request.getParameter(param))).trim().length() == 0)){
			return;
		}
		map.put(key, request.getParameter(param).trim());
	}
}
