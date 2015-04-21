package com.lvmama.back.sweb.distribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
/**
 * 分销退款历史
 * @author yanzhirong
 *
 */
import com.lvmama.comm.bee.service.distribution.DistributionOrderRefundService;
import com.lvmama.comm.bee.service.distribution.DistributionService;
import com.lvmama.comm.bee.service.distribution.IDistributionRefundRemote;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;

@Results({
	@Result(name = "searchDistributionOrderRefund", location = "/WEB-INF/pages/back/distribution/distributionOrderRefund/searchListDistributionOrderRefund.jsp")
})

@ParentPackage("json-default")
public class DistributionOrderRefundAction extends BackBaseAction{

	private static final long serialVersionUID = -240117953517895168L;
	
	/** 订单ID*/
	private String orderId;
	
	/** 退款状态*/
	private String refundStatus;
	
	/** 创建时间的查询开始时间*/
	private String createTimeStart;
	
	/** 创建时间的查询结束时间*/
	private String createTimeEnd;
	
	/** 分销商Id*/
	private String distributorInfoId;
	
	/** 分销退款ID*/
	private Long refundId;
	
	
	/**分销商列表*/
	private List<DistributorInfo> distributorList;
	
	/** 分销订单退款历史分页*/
	private Page<DistributionOrderRefund> distributionOrderRefundPage = new Page<DistributionOrderRefund>();
	
	/** 分销订单退款历史服务*/
	private DistributionOrderRefundService distributionOrderRefundService;

	/** 关于分销的服务*/
	private DistributionService distributionService;
	/** super_clutter的远程服务*/
	private IDistributionRefundRemote distributionRefundService = (IDistributionRefundRemote)SpringBeanProxy.getBean("distributionRefundService");;
	
	@Action("/distribution/searchDistributionOrderRefund")
	public String searchDistributionOrderRefund(){
		Map<String, Object> params = new HashMap<String, Object>();
		/* 订单ID*/
		if(!StringUtil.isEmptyString(this.orderId)){
			params.put("orderId", this.orderId.trim());
		}		
		/*退款状态*/
		if(!StringUtil.isEmptyString(this.refundStatus)){
			if(!"0".equals(this.refundStatus.trim())){
				params.put("refundStatus", this.refundStatus.trim());
			}
		}
		/* 分销商名称*/
		if(!StringUtil.isEmptyString(distributorInfoId)){
			params.put("distributorInfoId", distributorInfoId);
		}
		/* 创建时间的查询开始时间*/
		if(null != createTimeStart && !createTimeStart.equals("")){
			params.put("beginDate", createTimeStart+" 00:00:00");
		}
		
		/* 创建时间的查询开始时间*/
		if(null != createTimeEnd && !createTimeEnd.equals("")){
			params.put("endDate", createTimeEnd+" 23:59:59");
		}
		distributorList = distributionService.getAllDistributors();
		distributionOrderRefundPage.setTotalResultSize(distributionOrderRefundService.queryDistributionOrderRefundByParamsCount(params));
		distributionOrderRefundPage.buildUrl(getRequest());
		distributionOrderRefundPage.setCurrentPage(super.page);
		params.put("start",distributionOrderRefundPage.getStartRows());
		params.put("end",distributionOrderRefundPage.getEndRows());
		if(distributionOrderRefundPage.getTotalResultSize()>0){
			distributionOrderRefundPage.setItems(this.distributionOrderRefundService.queryDistributionOrderRefundByParams(params));
		}
		return "searchDistributionOrderRefund";
	}
	
	@Action("/distribution/reApplyRefund")
	public void reApplyRefund(){
		JSONResult result=new JSONResult();
		try{
			if(checkRefundedSuccess(refundId)){
				result.put("result", "-1");
			}else{
				DistributionOrderRefund distributionOrderRefund = distributionOrderRefundService.selectDistributionOrderRefundById(refundId);
				boolean flag = distributionRefundService.refund(distributionOrderRefund);
				if(flag){
					result.put("result", "1");
				}else{
					result.put("result", "0");
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			log.debug("DistributionOrderRefund.reApplyRefund :"+ex.getMessage());
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	private Boolean checkRefundedSuccess(Long refundId){
		String refundStatu=distributionOrderRefundService.selectRefundStatusByOrderRefundId(refundId);
		if("SUCCESS".equals(refundStatu)){
			return true;
		}else{
			return false;
		}
	}

	public void setDistributionOrderRefundService(
			DistributionOrderRefundService distributionOrderRefundService) {
		this.distributionOrderRefundService = distributionOrderRefundService;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getDistributorInfoId() {
		return distributorInfoId;
	}

	public void setDistributorInfoId(String distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}

	public Page<DistributionOrderRefund> getDistributionOrderRefundPage() {
		return distributionOrderRefundPage;
	}

	public void setDistributionOrderRefundPage(
			Page<DistributionOrderRefund> distributionOrderRefundPage) {
		this.distributionOrderRefundPage = distributionOrderRefundPage;
	}

	public DistributionOrderRefundService getDistributionOrderRefundService() {
		return distributionOrderRefundService;
	}

	public List<DistributorInfo> getDistributorList() {
		return distributorList;
	}

	public void setDistributorList(List<DistributorInfo> distributorList) {
		this.distributorList = distributorList;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public DistributionService getDistributionService() {
		return distributionService;
	}

	public void setDistributionService(DistributionService distributionService) {
		this.distributionService = distributionService;
	}
	
	
}
