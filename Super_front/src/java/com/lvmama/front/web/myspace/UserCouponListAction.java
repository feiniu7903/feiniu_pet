package com.lvmama.front.web.myspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mark.MarkCouponUserService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.UserCouponDTO;

/**
 * 我的优惠券
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/usrcouponlist.ftl", type = "freemarker")
})
public class UserCouponListAction extends SpaceBaseAction {
	private static final long serialVersionUID = -3495821351822356426L;

	private Long codeId;
	
	private MarkCouponService markCouponService;
	private MarkCouponUserService markCouponUserService;
	private Page pageConfig;
	private long currentPage = 1;
	private long pageSize = 5;
	
	private String used = "false"; //默认为可用的优惠券查询
	private long page = 1;
	private Map<String,Object> countMap = new HashMap<String,Object>();
	private FavorOrderService favorOrderService;
	
	@Action("/myspace/account/coupon")
	public String couponList() throws Exception {
		if(!isLogin()){
			return LOGIN;
		}
		Long userId = this.getUser().getId();
		HashMap<String, Object> params = new HashMap<String, Object>();
		List<UserCouponDTO> userCouponList = null;
		countMap = new HashMap<String, Object>();
		//获得当前在看的未使用优惠列表
		if("false".equals(used)){
			params.put("userId", userId);
			params.put("used", used);
			params.put("applyField", "userCouponList1");
			Long totalRecords = markCouponUserService.selectCountByRelateUser(params);
			this.pageConfig = Page.page(totalRecords, pageSize, currentPage);
			params.put("_startRow", pageConfig.getStartRows() + "");
			params.put("_endRow", pageConfig.getEndRows() + "");
			userCouponList = markCouponUserService.getMySpaceUserCouponData(params);
		}else if("true".equals(used)){//获得当前在看的已使用优惠列表
			params.put("userId", this.getUser().getUserNo());
			params.put("used", used);
			params.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER.name());//获取用户的优惠券信息
			Long totalRecords = favorOrderService.selectCountByParam(params);
			this.pageConfig = Page.page(totalRecords, pageSize, currentPage);
			params.put("_startRow", pageConfig.getStartRows() + "");
			params.put("_endRow", pageConfig.getEndRows() + "");
			List<MarkCouponUsage> markCouponUsageList = favorOrderService.selectByParam(params);
			userCouponList = markCouponUserService.getMySpaceUserCouponData(markCouponUsageList);
			for(int i = 0; i < userCouponList.size(); i++){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("couponCodeId", userCouponList.get(i).getMarkCouponCode().getCouponCodeId());
				param.put("userId", this.getUser().getUserNo());
				param.put("objectId", userCouponList.get(i).getOrderId());	
				Long amount = favorOrderService.getSumUsageAmount(param);
				if(amount != null){
					userCouponList.get(i).setAmountYuan(PriceUtil.convertToYuan(amount));
				}
			}
		}else{
			userCouponList = new ArrayList<UserCouponDTO>();
		}
		
		params.clear();
		params.put("userId", userId);
		params.put("used", "false");
		params.put("applyField", "userCouponList2");
		Long totalRecords = markCouponUserService.selectCountByRelateUser(params);
		countMap.put("USE_COUNT", totalRecords);
		params.clear();
		params.put("userId", this.getUser().getUserNo());
		params.put("used", "true");
		params.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER.name());//获取用户的优惠券信息
	    totalRecords = favorOrderService.selectCountByParam(params);
		countMap.put("USED_COUNT", totalRecords);
		
		this.pageConfig.setItems(userCouponList);
		StringBuffer url = new StringBuffer("/myspace/account/coupon");
		url.append(".do?");
		url.append("used="+used+"&");
		url.append("page="+page+"&");
		url.append("currentPage=");
		if (pageConfig.getItems().size() > 0) {
			this.pageConfig.setUrl(url.toString());
		}
		
		//获取已使用优惠总金额
		Long sumUsageAmountByUser = favorOrderService.getSumUsageAmountByUser(getUser());
		if(sumUsageAmountByUser != null){
			countMap.put("USED_AMOUNT", PriceUtil.convertToYuan(sumUsageAmountByUser));
		}else{
			countMap.put("USED_AMOUNT", 0);
		}
//		List<CodeItem> result = markCouponService.selectByRelateUserAmount(params);
//		if(result.isEmpty()){
//			countMap = new HashMap<String, Object>();
//			countMap.put("USE_COUNT", "0");
//			countMap.put("USED_COUNT", "0");
//			countMap.put("USE_AMOUNT", "0");
//			countMap.put("USED_AMOUNT", "0");
//		}else{
//			for(CodeItem item:result){
//				countMap.put(item.getName(), item.getCode());
//			}
//		}
		return SUCCESS;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public Page getPageConfig() {
		return pageConfig;
	}

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public Map<String, Object> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String, Object> countMap) {
		this.countMap = countMap;
	}
	public boolean getContentFooter() {
		return Boolean.TRUE;
	}

	public void setMarkCouponUserService(MarkCouponUserService markCouponUserService) {
		this.markCouponUserService = markCouponUserService;
	}

	public void setFavorOrderService(FavorOrderService favorOrderService) {
		this.favorOrderService = favorOrderService;
	}
}
