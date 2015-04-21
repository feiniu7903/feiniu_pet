package com.lvmama.back.sweb.tmall;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.tmall.OrdTmallMap;
import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;

@Results(value = { @Result(name = "search", location = "/WEB-INF/pages/back/tmall/failed.jsp"),
		@Result(name = "check", location = "/WEB-INF/pages/back/tmall/tmallOrderList.jsp") })
public class TmallOrdMapManageAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(TmallOrdMapManageAction.class);
	private Long tmallMapId;
	private String buyerNick;
	private Long productId;
	private String tmallId;
	private String buyerMobile;
	private String status;
	private String systemOrder;
	private Long lvOrderId;
	private String seller;
	private Date createTime;
	private String productType;
	private String orderStatus;
	private String paymentTarget;
	private String certificate;
	private String resourceConfirmStatus;
	private String gtMaxNum;
	private String createTimeStart;
	private OrdTmallMapService ordTmallMapService;
	private ComLogService comLogService;

	/**
	 * 进入tmall失败订单页面
	 * 
	 * @return
	 */
	@Action(value = "/tmall/failedOrderList")
	public String search() {
		return "search";
	}

	/**
	 * 进入tmall失败订单页面
	 * 
	 * @return
	 */
	@Action(value = "/tmall/OrderList")
	public String check() {
		return "check";
	}

	/**
	 * 查询失败订单列表
	 */
	@Action(value = "getFailedOrderList")
	public String getFailedOrderList() {
		Map<String, String> param = new HashMap<String, String>();
		if (!StringUtil.isEmptyString(buyerNick)) {
			param.put("buyerNick", buyerNick);
		}
		if (productId != null) {
			param.put("productId", String.valueOf(productId));
		}
		if (!StringUtil.isEmptyString(tmallId)) {
			param.put("tmallId", tmallId);
		}
		if (!StringUtil.isEmptyString(buyerMobile)) {
			param.put("buyerMobile", buyerMobile);
		}
		// processStatus="unprocess";
		// param.put("processStatus", processStatus);
		PermUser user = getSessionUser();
		if (user != null && !user.isAdministrator()) {
			param.put("orgId", String.valueOf(this.getSessionUser().getDepartmentId()));
		}
		initPagination();
		pagination.setActionUrl(WebUtils.getUrl(getRequest(), param));

		long total = ordTmallMapService.getFailedOrderListCount(param);
		pagination.setTotalRecords(total);
		if (total > 0) {
			param.put("startRow", String.valueOf(pagination.getFirstRow()));
			param.put("endRow", String.valueOf(pagination.getLastRow()));

			List<OrdTmallMap> list = ordTmallMapService.getFailedOrderList(param);
			pagination.setRecords(list);
		}
		return "search";
	}

	/**
	 * 查询订单列表
	 */
	@Action(value = "getOrderList")
	public String getOrderList() {
		Map<String, String> param = new HashMap<String, String>();
		if (!StringUtil.isEmptyString(buyerNick)) {
			param.put("buyerNick", buyerNick);
		}
		if (productId != null) {
			param.put("productId", String.valueOf(productId));
		}
		if (!StringUtil.isEmptyString(tmallId)) {
			param.put("tmallId", tmallId);
		}
		if (!StringUtil.isEmptyString(systemOrder)) {
			param.put("systemOrder", systemOrder);
		}
		if(!StringUtil.isEmptyString(status)){
			param.put("status", status);
		}
        if(!StringUtil.isEmptyString(productType)){
        	param.put("productType", productType);
		}
        if(!StringUtil.isEmptyString(createTimeStart)){
        	param.put("createTimeStart", createTimeStart);
		}
		PermUser user = getSessionUser();
		if (user != null && !user.isAdministrator()) {
			param.put("orgId", String.valueOf(this.getSessionUser().getDepartmentId()));
		}
		initPagination();
		pagination.setActionUrl(WebUtils.getUrl(getRequest(), param));

		long total = ordTmallMapService.getOrderListCount(param);
		pagination.setTotalRecords(total);
		if (total > 0) {
			param.put("startRow", String.valueOf(pagination.getFirstRow()));
			param.put("endRow", String.valueOf(pagination.getLastRow()));

			List<OrdTmallMap> list = ordTmallMapService.getOrderList(param);
			pagination.setRecords(list);
		}
		return "check";
	}

	/***
	 * 手动搬单
	 * 
	 * @return
	 */
	@Action(value = "manualProcess")
	public void manualProcess() {
		JSONResult result = new JSONResult();
		try {
			OrdTmallMap ordTmallMap = null;
			String flag = "SUCCESS";
			if (tmallMapId != null) {
				ordTmallMap = ordTmallMapService.selectByPrimaryKey(tmallMapId);
			} else {
				flag = "FAILED";
			}
			if (lvOrderId != null) {
				ordTmallMap.setLvOrderId(lvOrderId);
			}
			if (!StringUtil.isEmptyString(seller)) {
				ordTmallMap.setSeller(seller);
			}
			ordTmallMap.setProcessTime(new Date());
			// ordTmallMap.setProcessStatus("processed");
			ordTmallMap.setSystemOrder("false");
			ordTmallMap.setStatus("success");
			PermUser user = super.getSessionUser();
			if (user != null) {
				ordTmallMap.setOperatorName(user.getUserName());

			}
			if (ordTmallMapService.updateByPrimaryKeySelective(ordTmallMap) != 1) {
				flag = "FAILED";
			}
			if(user != null && !"FAILED".equalsIgnoreCase(flag)){
				comLogService.insert("ORD_ORDER", null, lvOrderId, user.getUserName(), "UPDATE_ORD_TMALL_MAP",
						"客服手动搬单" , "客服手动搬单" + ordTmallMap.getTmallOrderNo(), null);
			}

			result.put("flag", flag);
			result.put("lvOrderId", lvOrderId);
		} catch (Exception ex) {
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());

	}

	/****
	 * 显示修改日志
	 */
	@Action(value = "showLog")
	public void showLog() {
		OrdTmallMap ordTmallMap = null;
		JSONResult result = new JSONResult();
		try {
			if (tmallMapId != null) {
				ordTmallMap = ordTmallMapService.selectByPrimaryKey(tmallMapId);
			}
			result.put("operator", ordTmallMap.getOperatorName());
			result.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ordTmallMap
					.getProcessTime()));
		} catch (Exception ex) {
			log.error("json output", ex);
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}

		result.output(getResponse());

	}

	/****
	 * 修改处理状态
	 */
	@Action(value = "updateProcessStatus")
	public void updateProcessStatus() {
		OrdTmallMap ordTmallMap = null;
		JSONResult result = new JSONResult();
		String flag = "SUCCESS";
		try {
			if (tmallMapId != null) {
				ordTmallMap = ordTmallMapService.selectByPrimaryKey(tmallMapId);
			}
			if (ordTmallMap != null) {
				ordTmallMap.setStatus(status);
				PermUser user = super.getSessionUser();
				if (user != null) {
					ordTmallMap.setOperatorName(user.getUserName());

				}
			} else {
				flag = "FAILED";
			}
			if (ordTmallMapService.updateByPrimaryKeySelective(ordTmallMap) != 1) {
				flag = "FAILED";
			}
			result.put("flag", flag);
		} catch (Exception ex) {
			log.error("json output", ex);
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}

		result.output(getResponse());

	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTmallId() {
		return tmallId;
	}

	public void setTmallId(String tmallId) {
		this.tmallId = tmallId;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSystemOrder() {
		return systemOrder;
	}

	public void setSystemOrder(String systemOrder) {
		this.systemOrder = systemOrder;
	}

	public Long getTmallMapId() {
		return tmallMapId;
	}

	public void setTmallMapId(Long tmallMapId) {
		this.tmallMapId = tmallMapId;
	}

	public Long getLvOrderId() {
		return lvOrderId;
	}

	public void setLvOrderId(Long lvOrderId) {
		this.lvOrderId = lvOrderId;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public void setOrdTmallMapService(OrdTmallMapService ordTmallMapService) {
		this.ordTmallMapService = ordTmallMapService;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getResourceConfirmStatus() {
		return resourceConfirmStatus;
	}

	public void setResourceConfirmStatus(String resourceConfirmStatus) {
		this.resourceConfirmStatus = resourceConfirmStatus;
	}

	public String getGtMaxNum() {
		return gtMaxNum;
	}

	public void setGtMaxNum(String gtMaxNum) {
		this.gtMaxNum = gtMaxNum;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}
	
	
	
	
	
	

}
