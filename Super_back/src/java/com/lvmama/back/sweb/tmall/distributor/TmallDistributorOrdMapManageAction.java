package com.lvmama.back.sweb.tmall.distributor;

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
import com.lvmama.comm.bee.po.tmall.OrdTmallDistributorMap;
import com.lvmama.comm.bee.service.tmall.OrdTmallDistributorMapService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
@Results(value = { @Result(name = "failedOrderList", location = "/WEB-INF/pages/back/tmall/distributor/distributorFailed.jsp"),
		           @Result(name = "orderList", location = "/WEB-INF/pages/back/tmall/distributor/distributorOrderList.jsp") })
public class TmallDistributorOrdMapManageAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(TmallDistributorOrdMapManageAction.class);
	private OrdTmallDistributorMapService ordTmallDistributorMapService;
	private String distributorUserName;
	private Long productId;
	private Long fenXiaoId;
	private String systemOrder;//类别
	private String status;//处理状态
	private String productType;
	private String createTimeStart;//下单日
	private Long distributorNo;//采购单编号
	private String buyerMobile;
	private Long ordTmallDistributorMapId;
	private Long lvOrderId;
	private String seller;
	private ComLogService comLogService;
	
	/**
	 * 查询订单列表
	 */
	@Action(value = "/distributor/getOrderList")
	public String getOrderList() {
		Map<String, Object> param = new HashMap<String, Object>();
		
		if (!StringUtil.isEmptyString(distributorUserName)) {
			param.put("distributorUserName", distributorUserName);
		}
		if (productId!= null) {
			param.put("productId", productId);
		}
		if (fenXiaoId!=null) {
			param.put("fenXiaoId", fenXiaoId);
		}
		if(distributorNo!=null ){
			param.put("distributorNo", distributorNo);
		}
		if (!StringUtil.isEmptyString(systemOrder)) {
			param.put("systemOrder", systemOrder);
		}
		if (!StringUtil.isEmptyString(status)) {
			param.put("status", status);
		}
		if (!StringUtil.isEmptyString(productType)) {
			param.put("productType", productType);
		}
		if (!StringUtil.isEmptyString(createTimeStart)) {
			param.put("createTimeStart", createTimeStart);
		}
		
		PermUser user = getSessionUser();
		if (user != null && !user.isAdministrator()) {
			param.put("orgId", String.valueOf(this.getSessionUser().getDepartmentId()));
		}
		pagination = super.initPagination();
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		long total = ordTmallDistributorMapService.selectCountByParam(param);
		pagination.setTotalRecords(total);
		if (total > 0) {
			param.put("_startRow", pagination.getFirstRow());
			param.put("_endRow", pagination.getLastRow());
			List<OrdTmallDistributorMap> list = ordTmallDistributorMapService.selectByParam(param);
			pagination.setRecords(list);
		}
		return "orderList";
	}
	
	/**
	 * 进入Tmall Distributor失败订单页面
	 * 
	 * @return
	 */
	@Action(value = "/distributor/getFailedOrderList")
	public String failedOrderList() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtil.isEmptyString(distributorUserName)) {
			param.put("distributorUserName", distributorUserName);
		}
		if (productId!= null) {
			param.put("productId", productId);
		}
		if (fenXiaoId!=null) {
			param.put("fenXiaoId", fenXiaoId);
		}
		if(buyerMobile!=null){
			param.put("buyerMobile",buyerMobile);
		}
		if(distributorNo!=null ){
			param.put("distributorNo", distributorNo);
		}
		//设置查询条件为失败状态
		param.put("failStatus","true");
		pagination = super.initPagination();
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		long total = ordTmallDistributorMapService.selectCountByParam(param);
		pagination.setTotalRecords(total);
		if (total > 0) {
			param.put("_startRow", pagination.getFirstRow());
			param.put("_endRow", pagination.getLastRow());
			List<OrdTmallDistributorMap> list = ordTmallDistributorMapService.selectByParam(param);
			pagination.setRecords(list);
		}
		return "failedOrderList";
	}
	
	/****
	 * 显示修改日志
	 */
	@Action(value = "/distributor/showLog")
	public void showLog() {
		OrdTmallDistributorMap ordTmallDitributorMap = null;
		JSONResult result = new JSONResult();
		try{
			if(ordTmallDistributorMapId!=null ){
				ordTmallDitributorMap = ordTmallDistributorMapService.selectByPK(ordTmallDistributorMapId);
				result.put("operator", ordTmallDitributorMap.getOperatorName());
				result.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ordTmallDitributorMap
						.getProcessTime()));
			}
		}catch(Exception e){
			log.error("tmall distributor showLog json output", e);
			result.raise(new JSONResultException(e.getMessage()));
		}
		result.output(getResponse());
	}
	/***
	 * 手动搬单
	 * 
	 * @return
	 */
	@Action(value = "/distributor/manualProcess")
	public void manualProcess() {
		OrdTmallDistributorMap ordTmallDitributorMap = null;
		JSONResult result = new JSONResult();
		try{
			String flag = "SUCCESS";
			if(ordTmallDistributorMapId!=null ){
				ordTmallDitributorMap = ordTmallDistributorMapService.selectByPK(ordTmallDistributorMapId);
				if (lvOrderId != null) {
					ordTmallDitributorMap.setLvOrderId(lvOrderId);
				}
				if (!StringUtil.isEmptyString(seller)) {
					ordTmallDitributorMap.setSeller(seller);
				}
				ordTmallDitributorMap.setProcessTime(new Date());
				ordTmallDitributorMap.setSystemOrder("false");
				ordTmallDitributorMap.setStatus("success");
				PermUser user = super.getSessionUser();
				if (user != null) {
					ordTmallDitributorMap.setOperatorName(user.getUserName());

				}
				if (ordTmallDistributorMapService.updateAllByPrimaryKey(ordTmallDitributorMap) != 1) {
					flag = "FAILED";
				}
				if(user != null && !"FAILED".equalsIgnoreCase(flag)){
					comLogService.insert("ORD_ORDER", null, lvOrderId, user.getUserName(), "UPDATE_ORD_TMALL_DISTRIBUTOR_MAP",
							"客服手动搬分销单" , "客服手动搬分销单" + ordTmallDitributorMap.getFenXiaoId(), null);
				}
			}else {
				flag = "FAILED";
			}
			result.put("flag", flag);
			result.put("lvOrderId", lvOrderId);
		}catch(Exception e ){
			log.error("tmall distributor manualProcess json output", e);
			result.raise(new JSONResultException(e.getMessage()));
		}
		result.output(getResponse());
	}
	/****
	 * 修改处理状态
	 */
	@Action(value = "/distributor/updateProcessStatus")
	public void updateProcessStatus() {
		OrdTmallDistributorMap ordTmallDitributorMap = null;
		JSONResult result = new JSONResult();
		String flag = "SUCCESS";
		try{
			if(ordTmallDistributorMapId!=null ){
				ordTmallDitributorMap = ordTmallDistributorMapService.selectByPK(ordTmallDistributorMapId);
				ordTmallDitributorMap.setStatus(status);
				PermUser user = super.getSessionUser();
				if (user != null) {
					ordTmallDitributorMap.setOperatorName(user.getUserName());
				}
				if (ordTmallDistributorMapService.updateAllByPrimaryKey(ordTmallDitributorMap) != 1) {
					flag = "FAILED";
				}
			}else{
				flag = "FAILED";
			}
			result.put("flag", flag);
		}catch(Exception e){
			log.error("tmall distributor updateProcessStatus json output", e);
			result.raise(new JSONResultException(e.getMessage()));
		}
		result.output(getResponse());
	}

	public String getDistributorUserName() {
		return distributorUserName;
	}

	public void setDistributorUserName(String distributorUserName) {
		this.distributorUserName = distributorUserName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getFenXiaoId() {
		return fenXiaoId;
	}

	public void setFenXiaoId(Long fenXiaoId) {
		this.fenXiaoId = fenXiaoId;
	}

	public String getSystemOrder() {
		return systemOrder;
	}

	public void setSystemOrder(String systemOrder) {
		this.systemOrder = systemOrder;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public void setOrdTmallDistributorMapService(
			OrdTmallDistributorMapService ordTmallDistributorMapService) {
		this.ordTmallDistributorMapService = ordTmallDistributorMapService;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public Long getOrdTmallDistributorMapId() {
		return ordTmallDistributorMapId;
	}

	public void setOrdTmallDistributorMapId(Long ordTmallDistributorMapId) {
		this.ordTmallDistributorMapId = ordTmallDistributorMapId;
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

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public Long getDistributorNo() {
		return distributorNo;
	}

	public void setDistributorNo(Long distributorNo) {
		this.distributorNo = distributorNo;
	}
	

}
