package com.lvmama.eplace.web.supplier;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassPortSummary;
import com.lvmama.comm.bee.po.pass.PassPortTotal;
import com.lvmama.comm.bee.service.eplace.EPlaceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PassPortSummaryRelate;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.utils.ZkMessage;

/**
 * 通关信息汇总
 * 
 * @author chenlinjun
 * 
 */
public class ListPassAction extends ZkBaseAction {
	private static final long serialVersionUID = 1105833035316477806L;
	private List<PassPortSummary> passList = new ArrayList<PassPortSummary>();
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private List<PassPortTotal> passPortTotals;
	private EPlaceService eplaceService;
	private Long metaProductBranchId = -1l;
	private boolean orderStatus=false;
	private boolean operate=true;
	
	public void doBefore() {
		Long userId = this.getSessionUser().getPassPortUserId();
		long  total = eplaceService.getSupplierUserTargetIdTotal(userId);
		if(total>1){
			this.operate=false;
		}
		doQuery();
	}
	

	public void doQuery()  {
		try{
		CompositeQuery compositeQuery = new CompositeQuery();
		PassPortSummaryRelate passPortSummaryRelate = new PassPortSummaryRelate();
		
		if (this.validate()) {
			if (queryOption.get("visitTimeStart") != null && queryOption.get("visitTimeEnd") != null) {
				passPortSummaryRelate.setVisitTimeStart((Date) queryOption.get("visitTimeStart"));
				passPortSummaryRelate.setVisitTimeEnd((Date) queryOption.get("visitTimeEnd"));
			} else {
				Date today = new Date();
				Date visitTimeStart = DateUtils.addDays(today, -3);
				Date visitTimeEnd = DateUtils.addDays(today, 4);
				passPortSummaryRelate.setVisitTimeStart(visitTimeStart);
				passPortSummaryRelate.setVisitTimeEnd(visitTimeEnd);
			}
			if (metaProductBranchId > 0) {
				passPortSummaryRelate.setMetaProductBranchId(metaProductBranchId);
				
			}else if(this.metaProductBranchId==-1){
				passPortSummaryRelate.setPassPortUserId(this.getSessionUser().getPassPortUserId());
			}
			if(orderStatus){
				passPortSummaryRelate.setOrderStatus(Constant.ORDER_STATUS.CANCEL.name());
			}
			compositeQuery.setPassPortSummaryRelate(passPortSummaryRelate);
			compositeQuery.getPageIndex().setBeginIndex(0);
			compositeQuery.getPageIndex().setEndIndex(50);
			passList = orderServiceProxy.queryPassPortSummary(compositeQuery);
			long orderCount = 0;
			long visitorQuantity = 0;
			long passedCount = 0;
			long toBePassCount = 0;
			for (PassPortSummary passPortSummary : passList) {
				orderCount += passPortSummary.getOrderCount() == null ? 0 : passPortSummary.getOrderCount();
				visitorQuantity += passPortSummary.getVisitorQuantity() == null ? 0 : passPortSummary
						.getVisitorQuantity();
				passedCount += passPortSummary.getPassedCount() == null ? 0 : passPortSummary.getPassedCount();
				toBePassCount += passPortSummary.getToBePassCount() == null ? 0 : passPortSummary.getToBePassCount();
			}
			passPortTotals = new ArrayList<PassPortTotal>();
			PassPortTotal passPortTotal = new PassPortTotal();
			passPortTotal.setTitle("汇总");
			passPortTotal.setOrderCount(orderCount);
			passPortTotal.setVisitorQuantity(visitorQuantity);
			passPortTotal.setPassedCount(passedCount);
			passPortTotal.setToBePassCount(toBePassCount);
			this.passPortTotals.add(passPortTotal);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean validate() {
		boolean flag = true;
		if (metaProductBranchId ==0) {
			ZkMessage.showInfo("请选择产品");
			flag = false;
		}
		return flag;
	}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}

	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

	public List<PassPortSummary> getPassList() {
		return passList;
	}

	public void setPassList(List<PassPortSummary> passList) {
		this.passList = passList;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}


	public Long getMetaProductBranchId() {
		return metaProductBranchId;
	}

	public void setMetaProductBranchId(Long metaProductBranchId) {
		this.metaProductBranchId = metaProductBranchId;
	}


	public List<PassPortTotal> getPassPortTotals() {
		return passPortTotals;
	}

	public void setPassPortTotals(List<PassPortTotal> passPortTotals) {
		this.passPortTotals = passPortTotals;
	}

	public boolean isOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(boolean orderStatus) {
		this.orderStatus = orderStatus;
	}

	public boolean isOperate() {
		return operate;
	}

	public void setOperate(boolean operate) {
		this.operate = operate;
	}


	public void setEplaceService(EPlaceService eplaceService) {
		this.eplaceService = eplaceService;
	}
 
}
