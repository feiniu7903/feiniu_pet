package com.lvmama.back.sweb.distribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.distribution.OrdOrderDistribution;
import com.lvmama.comm.bee.service.distribution.DistributionOrderRefundService;
import com.lvmama.comm.bee.service.distribution.DistributionOrderService;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.bee.service.distribution.IDistributionRefundRemote;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;

/**
 * 分销订单管理
 * @author yanzhirong
 *
 */
@Results({
	@Result(name = "searchListDistributionOrder", location = "/WEB-INF/pages/back/distribution/distributionOrder/searchListDistributionOrder.jsp"),
	@Result(name = "saveOrUpdateDistributionOrderRefund", location = "/WEB-INF/pages/back/distribution/distributionOrder/saveOrUpdateDistributionRefund.jsp")
})

@ParentPackage("json-default")
public class DistributionOrderAction extends BackBaseAction{

	private static final long serialVersionUID = -8971760839311668039L;
	private IDistributionRefundRemote distributionRefundService = (IDistributionRefundRemote)SpringBeanProxy.getBean("distributionRefundService");
	
	/** 订单ID*/
	private String orderId;
	
	/** 分销商订单ID*/
	private String distributionOrderID;
	
	/** 创建时间的查询开始时间*/
	private String createTimeStart;
	
	/** 创建时间的查询结束时间*/
	private String createTimeEnd;
	
	/** 分销商Id*/
	private String distributorInfoId;
	
	/** 分销商订单 id*/
	private String partnerOrderId;
	
	/** 分销商户号*/
	private String distributorCode;
	
	/** 分销商密钥*/
	private String distributorKey;
	
	/** 退款金额*/
	private Double refundAmountYuan;

	/** 手续费*/
	private Double factorageYuan;

	
	
	/**分销订单分页*/
	private Page<OrdOrderDistribution> distributionOrderPage = new Page<OrdOrderDistribution>();
	
	/**分销商列表*/
	private List<DistributorInfo> distributorList;
	
	/**分销订单退款历史*/
	private DistributionOrderRefund refund = new DistributionOrderRefund();
	
	
	/** 分销订单服务*/
	private DistributionOrderService distributionOrderService;
	/** 关于分销的服务*/
	private DistributionService distributionService;

	/** 分销订单退款历史服务*/
	private DistributionOrderRefundService distributionOrderRefundService;
	
	@Action("/distribution/searchListDistributionOrder")
	public String searchListDistributionOrder(){
		Map<String, Object> params = new HashMap<String, Object>();
		/* 订单ID*/
		if(!StringUtil.isEmptyString(this.orderId)){
			params.put("orderId", this.orderId.trim());
		}		
		/* 分销商订单ID*/
		if(!StringUtil.isEmptyString(distributionOrderID)){
			params.put("partnerOrderId", distributionOrderID);
		}
		/* 分销商名称*/
 		if(!StringUtil.isEmptyString(distributorInfoId)){
			params.put("distributorInfoId", distributorInfoId);
		}
		/* 创建时间的查询开始时间*/
		if(null != createTimeStart && !createTimeStart.equals("")){
			params.put("minCreateTime", createTimeStart+" 00:00:00");
		}
		
		/* 创建时间的查询开始时间*/
		if(null != createTimeEnd && !createTimeEnd.equals("")){
			params.put("maxCreateTime", createTimeEnd+" 23:59:59");
		}
		distributorList = distributionService.getAllDistributors();
		distributionOrderPage.setTotalResultSize(distributionOrderService.selectDistributionOrderByParamsCount(params));
		distributionOrderPage.buildUrl(getRequest());
		distributionOrderPage.setCurrentPage(super.page);
		params.put("start", distributionOrderPage.getStartRows());
		params.put("end", distributionOrderPage.getEndRows());
		
		if(distributionOrderPage.getTotalResultSize()>0){
			distributionOrderPage.setItems(distributionOrderService.selectDistributionOrderByParams(params));
		}
		return "searchListDistributionOrder";
	}
	
	@Action("/distribution/searchDistributionOrder")
	public String searchDistributionOrder(){
		return "saveOrUpdateDistributionOrderRefund";
	}
	
	@Action("/distribution/saveOrUpdateDistributionOrderRefund")
	public void saveOrUpdateDistributionOrder(){
		ResultHandle result = new ResultHandle();
		refund.setPartnerOrderId(partnerOrderId);
		if(refundAmountYuan == null ){
			refund.setRefundAmount(0L);
		}
		if(factorageYuan == null){
			refund.setFactorage(0L);
		}
		refund.setRefundAmount(new Double(refundAmountYuan*100).longValue());
		refund.setFactorage(new Double(factorageYuan*100).longValue());
		boolean flag = distributionRefundService.refund(refund);
		
		if (flag) {
			refund.setRefundStatus("SUCCESS");
		} else {
			refund.setRefundStatus("FAILED");
			result.setMsg("退款失败");
		}
		distributionOrderRefundService.insertDistributionRefund(refund);
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDistributionOrderID() {
		return distributionOrderID;
	}

	public void setDistributionOrderID(String distributionOrderID) {
		this.distributionOrderID = distributionOrderID;
	}

	public String getDistributorInfoId() {
		return distributorInfoId;
	}

	public void setDistributorInfoId(String distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}

	public Page<OrdOrderDistribution> getDistributionOrderPage() {
		return distributionOrderPage;
	}

	public void setDistributionOrderPage(
			Page<OrdOrderDistribution> distributionOrderPage) {
		this.distributionOrderPage = distributionOrderPage;
	}

	public DistributionOrderService getDistributionOrderService() {
		return distributionOrderService;
	}

	public void setDistributionOrderService(
			DistributionOrderService distributionOrderService) {
		this.distributionOrderService = distributionOrderService;
	}


	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public List<DistributorInfo> getDistributorList() {
		return distributorList;
	}

	public void setDistributorList(List<DistributorInfo> distributorList) {
		this.distributorList = distributorList;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public String getDistributorKey() {
		return distributorKey;
	}

	public void setDistributorKey(String distributorKey) {
		this.distributorKey = distributorKey;
	}

	public DistributionOrderRefund getRefund() {
		return refund;
	}

	public void setRefund(DistributionOrderRefund refund) {
		this.refund = refund;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public DistributionOrderRefundService getDistributionOrderRefundService() {
		return distributionOrderRefundService;
	}

	public void setDistributionOrderRefundService(DistributionOrderRefundService distributionOrderRefundService) {
		this.distributionOrderRefundService = distributionOrderRefundService;
	}

	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}

	public Double getRefundAmountYuan() {
		return refundAmountYuan;
	}

	public void setRefundAmountYuan(Double refundAmountYuan) {
		this.refundAmountYuan = refundAmountYuan;
	}

	public Double getFactorageYuan() {
		return factorageYuan;
	}

	public void setFactorageYuan(Double factorageYuan) {
		this.factorageYuan = factorageYuan;
	}
	
}

