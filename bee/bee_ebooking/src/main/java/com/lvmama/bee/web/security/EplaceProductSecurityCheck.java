package com.lvmama.bee.web.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * E景通产品权限校验
 * @author zhaojindong
 *
 */
public class EplaceProductSecurityCheck implements SecurityCheck{
	private String targetIdParam;
	private String orderIdParam;
	private OrderService orderServiceProxy;
	private PerformTargetService performTargetService;
	@Override
	public CheckResult check(HttpServletRequest request, HttpServletResponse response){
		String orderId = request.getParameter(orderIdParam);
		String targetId=request.getParameter(targetIdParam);
		if(!StringUtil.isEmptyString(orderId) && StringUtils.isNotBlank(targetId)){
			CompositeQuery compositeQuery = new CompositeQuery();
			PerformDetailRelate performDetailRelate = new PerformDetailRelate();
			List<Long> branchIds=(ArrayList<Long>)ServletUtil.getSession(request, response, Constant.Session_EBOOKING_USER_META_BRANCH_LIST);
			performDetailRelate.setBranchIds(branchIds);
			performDetailRelate.setOrderId(Long.valueOf(orderId));
			compositeQuery.setPerformDetailRelate(performDetailRelate);
			List<PerformDetail> list = orderServiceProxy.queryPerformDetailForEplaceList(compositeQuery);
			List<Long> brandIdList=new ArrayList<Long>();
			for(PerformDetail item:list){
				List<MetaPerform> metaPerformLst=performTargetService.getMetaPerformByMetaProductId(item.getMetaProductId());
				if(!CollectionUtils.isEmpty(metaPerformLst) && metaPerformLst.get(0).getTargetId().equals(Long.valueOf(targetId))){
					brandIdList.add(item.getMetaBrandId());
				}
			}
			List<Long> permProductList = (List<Long>)ServletUtil.getSession(request, response, Constant.Session_EBOOKING_USER_META_BRANCH_LIST);
			if(permProductList == null || permProductList.size() == 0 || CollectionUtils.isEmpty(brandIdList)){
				return CheckResult.FAIL_EPLACE_PRODUCT;
			}
			for(Long item:brandIdList){
				if(!permProductList.contains(item)){
					return CheckResult.FAIL_EPLACE_PRODUCT;
				}
			}
		}
		return CheckResult.SUCCESS;
	}
	public String getTargetIdParam() {
		return targetIdParam;
	}

	public void setTargetIdParam(String targetIdParam) {
		this.targetIdParam = targetIdParam;
	}

	public String getOrderIdParam() {
		return orderIdParam;
	}

	public void setOrderIdParam(String orderIdParam) {
		this.orderIdParam = orderIdParam;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}
}
