package com.lvmama.back.sweb.fax;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.fax.OrdFaxRecv;
import com.lvmama.comm.bee.po.fax.OrdFaxRecvLink;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.bee.service.fax.OrdFaxRecvService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

@Results(value = {
		@Result(name = "fax_receive", location = "/WEB-INF/pages/back/fax/fax_receive.jsp"),
		@Result(name = "receive_link_certificate", location = "/WEB-INF/pages/back/fax/receive_link_order.jsp"),
		@Result(name = "upload_receive_file", location = "/WEB-INF/pages/back/fax/upload_receive_file.jsp"),
		@Result(name = "receive_file_detail", location = "/WEB-INF/pages/back/fax/receive_file_detail.jsp"),
		@Result(name = "relate_certificate", location = "/WEB-INF/pages/back/fax/relate_certificate.jsp") })
/**
 * @author shihui
 * 订单回传
 * */
public class FaxReceiveAction extends BackBaseAction {

	private static final long serialVersionUID = 4968675045395752968L;

	private OrdFaxRecv ordFaxRecv = new OrdFaxRecv();

	private OrdFaxRecvService ordFaxRecvService;

	private Date minRecvTime;

	private Date maxRecvTime;

	private Date minOperateTime;

	private Date maxOperateTime;

	private Page<OrdFaxRecv> ordFaxRecvPage = new Page<OrdFaxRecv>();
	private Page<OrdFaxRecvLink> ordFaxRecvLinkPage;
	private Long page = 1L;

	private Long page_2 = 1L;

	private String operator;

	private Long ordFaxRecvId;
	private Long orderId;

	private Long[] ordFaxRecvIds;
	private Long ebkCertificateId;

	private List<OrdFaxRecvLink> ordFaxRecvLinkList;
	
	private List<OrdFaxRecv> ordFaxRecvList;

	private Long ordFaxRecvLinkId;

	private String fileType;

	private int tifPage = 1;

	private String tifPath;

	private ComLogService comLogService;

	private OrderService orderServiceProxy;	
	private EbkCertificateService ebkCertificateService;
	private EbkFaxTaskService ebkFaxTaskService;
	
	private OrdFaxRecvLink ordFaxRecvLink;
	private String isModifyCertificateId;
	private String modifyCertIdflag;
	private String updateFaxRecvStatusAndCertMemoOnly;

	private String faxSendRecvStatus;

	@Action("/fax/faxReceive/index")
	public String index() {
		return "fax_receive";
	}

	/**
	 * 查询传真回传列表
	 * */
	@Action("/fax/faxReceive/search")
	public String searchOrdFaxRecvList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("valid", "true");
		params.put("recvId", ordFaxRecv.getOrdFaxRecvId());
		params.put("callerNo", ordFaxRecv.getCallerId());
		params.put("minRecvTime", minRecvTime);
		params.put("orderId", orderId);
		if (maxRecvTime != null) {
			params.put("maxRecvTime", DateUtil.dsDay_Date(maxRecvTime, 1));
		}
		params.put("recvStatus", ordFaxRecv.getRecvStatus());
		this.ordFaxRecvPage.setTotalResultSize(ordFaxRecvService.selectByParamCount(params));
		this.ordFaxRecvPage.buildUrl(getRequest());
		this.ordFaxRecvPage.setCurrentPage(this.page);
		params.put("start", this.ordFaxRecvPage.getStartRows());
		params.put("end", this.ordFaxRecvPage.getEndRows());
		params.put("orderby", "RECV_TIME");
		params.put("order", "DESC");
		ordFaxRecvList = ordFaxRecvService.selectByParam(params);
		this.ordFaxRecvPage.setItems(ordFaxRecvList);
		return "fax_receive";
	}

	/**
	 * 关联记录页面弹出框
	 * */
	@Action("/fax/faxReceive/receiveLinkIndex")
	public String receiveLinkIndex() {
		return "receive_link_certificate";
	}

	/**
	 * 查询关联记录
	 * */
	@Action("/fax/faxReceive/searchRelateList")
	public String searchRelateList() {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(operator)) {
			params.put("operator", operator);
		}
		if (minOperateTime != null) {
			params.put("minOperateTime", minOperateTime);
		}
		if (maxOperateTime != null) {
			params.put("maxOperateTime", maxOperateTime);
		}
		ordFaxRecvLinkPage = ordFaxRecvService.selectLinksByParams(params, 10L, this.page_2);
		ordFaxRecvLinkPage.setActionType("do");
		ordFaxRecvLinkPage.setUrl("javascript:refreshLinkCertificateList('"
				+ initUrl("searchRelateList.do") + "')");
		ordFaxRecvLinkPage.setCurrentPageParamName("page_2");
		return "receive_link_certificate";
	}

	protected String initUrl(String actionDo) {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> pns = getRequest().getParameterNames();
		while (pns.hasMoreElements()) {
			String pn = pns.nextElement();
			if ("page_2".equalsIgnoreCase(pn)) {
				continue;
			}
			try {
				sb.append(pn
						+ "="
						+ new String(getRequest().getParameter(pn).getBytes(
								"ISO-8859-1"), "utf-8") + "&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "super_back/fax/faxReceive/" + actionDo + "?" + sb.toString()
				+ "page_2=argPage";

	}


	/**
	 * 上传回传件弹出框
	 * */
	@Action("/fax/faxReceive/uploadFileIndex")
	public String uploadFileIndex() {
		return "upload_receive_file";
	}

	/**
	 * 上传回传件
	 * */
	@Action("/fax/faxReceive/uploadReceiveFile")
	public void uploadReceiveFile() {
		JSONResult result = new JSONResult();
		ordFaxRecv.setCreateTime(new Date());
		ordFaxRecv.setOperatorName(getSessionUserName());
		Long ordFaxRecvId = ordFaxRecvService.receiveOrdFaxRecv(ordFaxRecv);
		comLogService.insert("ORD_FAX_RECV", null, ordFaxRecvId,
				this.getSessionUserName(),
				Constant.COM_LOG_EBK_FAX_TASK_EVENT.uploadReceiveFile.name(),
				"上传回传件", "上传回传件", null);
		if (ordFaxRecvId != null) {
			result.put("ordFaxRecvId", ordFaxRecvId);
		} else {
			result.raise("保存出错！");
		}
		result.output(getResponse());
	}

	/**
	 * 查看回传件弹出框
	 * */
	@Action("/fax/faxReceive/showFaxRecvFileDetail")
	public String showFaxRecvFileDetail() {
		if(ebkCertificateId != null){
			//加载传真回传文件
			ordFaxRecvList = ordFaxRecvService.queryOrdFaxRecvCertificateId(ebkCertificateId);
			if(ordFaxRecvId == null && ordFaxRecvList!=null && ordFaxRecvList.size()>0){
				ordFaxRecvId = ordFaxRecvList.get(0).getOrdFaxRecvId();
			}
			EbkFaxTask faxTask = ebkFaxTaskService.selectEbkFaxTaskByEbkCertificateId(ebkCertificateId);
			faxSendRecvStatus= faxTask.getFaxSendRecvStatus();
		}
		showRelateList();
		if (ordFaxRecv != null) {
			String fileUrl = ordFaxRecv.getFileUrl();
			// 未上传回传件不进入
			if (StringUtils.isNotEmpty(fileUrl)) {
				int index = fileUrl.lastIndexOf(".");
				if (index != -1) {
					fileType = fileUrl.substring(index + 1);
				}
			}
		}
		setRequestAttribute("modifyCertIdflag", modifyCertIdflag);
		setRequestAttribute("showEbkCertificateId", ebkCertificateId);
		return "receive_file_detail";
	}

	@Action("/fax/faxReceive/showRelateList")
	public String showRelateList() {
		if (ordFaxRecvId != null) {
			ordFaxRecv = ordFaxRecvService.selectByPrimaryKey(ordFaxRecvId);
			ordFaxRecvLinkList = ordFaxRecvService.selectLinkAndCertificateByRecvId(ordFaxRecvId);
		}
		return "relate_certificate";
	}
	
	/**
	 * 关联凭证并且更新传真回传备注和凭证备注
	 * */
	@Action("/fax/faxReceive/relateCertUpdateStatusAndCertMemo")
	public void relateCertUpdateStatusAndCertMemo() {
		JSONResult result = new JSONResult();
		if (ordFaxRecvLink == null) {
			result.raise("数据异常");
			result.output(getResponse());
			return;
		}
		Long certificateId = ordFaxRecvLink.getEbkCertificateId();//页面输入的凭证ID
		EbkFaxTask ebkFaxTask =ebkFaxTaskService.selectEbkFaxTaskByEbkCertificateId(certificateId);
		if(ebkFaxTask==null){
			result.raise("该凭证关联的传真不存在，请重新输入！");
			result.output(getResponse());
			return;
		}
		List<Long> certificateIdList = ordFaxRecvService.selectLinkCertificateIdsByRecvId(ordFaxRecvLink.getOrdFaxRecvId());
		ordFaxRecvLink.setCreateTime(new Date());
		ordFaxRecvLink.setOperator(this.getSessionUserName());
		//Id为空，就不更新ordFaxRecvLink的结果状态resultStatus
		if(ordFaxRecvLink.getOrdFaxRecvId() != null) {
			if(ordFaxRecvLink.getOrdFaxRecvLinkId() != null){
				//更新传真回传状态和凭证备注
				ordFaxRecvService.updateOrdFaxRecvLinkResultStatus(ordFaxRecvLink);
			} else {
				// 已关联过的重复凭证不再关联
				if (certificateIdList.contains(certificateId)) {
					result.raise("该凭证关联已经存在！");
					result.output(getResponse());
					return;
				}
				ordFaxRecvService.insertLinkAndUpdateRecvStatus(ordFaxRecvLink);
				comLogService.insert("ORD_FAX_RECV_LINK",null,ordFaxRecvLink.getOrdFaxRecvLinkId(),ordFaxRecvLink.getOperator(),
						Constant.COM_LOG_EBK_FAX_TASK_EVENT.addRelateCertificate.name(), "关联凭证", "为回传件" + ordFaxRecvLink.getOrdFaxRecvLinkId()
						+ "关联凭证" + ordFaxRecvLink.getEbkCertificateId(), null);
			}
			ebkFaxTask.setSendStatus(Constant.EBK_FAX_TASK_STATUS.FAX_SEND_STATUS_REPLIED.getStatus());
		}
		//更新EbkFaxTask的回传状态
		ebkFaxTask.setFaxSendRecvStatus(ordFaxRecvLink.getResultStatus());
		ebkFaxTaskService.updateEbkSendOrRecvStatus(ebkFaxTask,ordFaxRecvLink.getOperator());
		//更新凭证,订单子子项
		updateEbkCertificateAndOrdItemMeta(certificateId,ordFaxRecvLink);
		result.put("ordFaxRecvId", ordFaxRecvLink.getOrdFaxRecvId());
		result.output(getResponse());
	}

	private void updateEbkCertificateAndOrdItemMeta(Long certificateId,OrdFaxRecvLink ordFaxRecvLink) {
		EbkCertificate ebkCertificate = this.ebkCertificateService.selectEbkCertificateDetailByPrimaryKeyAndValid(certificateId);
		ebkCertificate.setMemo(ebkCertificate.getMemo()+";"+ordFaxRecvLink.getMemo());
		ebkCertificate.setEbkCertificateId(certificateId);
		ebkCertificate.setCertificateStatus(Constant.EBK_TASK_STATUS.REJECT.name());
		if(Constant.FAX_SEND_RECV_STATUS.FAX_SEND_STATUS_RECVOK.name().equals(ordFaxRecvLink.getResultStatus())) {
			ebkCertificate.setCertificateStatus(Constant.EBK_TASK_STATUS.ACCEPT.name());
		}
		ebkCertificate.setConfirmChannel(Constant.EBK_CERTIFICATE_CONFIRM_CHANNEL.FAX.name());
		ebkCertificateService.update(ebkCertificate);
		for(EbkCertificateItem item :ebkCertificate.getEbkCertificateItemList()) {
			comLogService.insert(Constant.COM_LOG_OBJECT_TYPE.EBK_ORDER_TASK.name(),
					item.getOrderId(),item.getOrderItemMetaId(),ordFaxRecvLink.getOperator(),
					Constant.COM_LOG_EBK_FAX_TASK_EVENT.updateCertificateMemo.name(), "传真回传", 
					"传真回传：" + ordFaxRecvLink.getMemo(), Constant.COM_LOG_OBJECT_TYPE.ORD_ORDER.name());
			orderServiceProxy.updateCertificateStatusAndTypeOrConfirmChannel(
					item.getOrderItemMetaId(),
					ebkCertificate.getCertificateStatus(), 
					ebkCertificate.getEbkCertificateType(),
					Constant.EBK_CERTIFICATE_CONFIRM_CHANNEL.FAX.name()
					);
		}
	}

	/**
	 * 删除关联
	 * */
	@Action("/fax/faxReceive/deleteReceiveLink")
	public void deleteReceiveLink() {
		JSONResult result = new JSONResult();
		if (ordFaxRecvLinkId != null) {
			ordFaxRecvService.deleteByLinkId(ordFaxRecvLinkId);
			comLogService.insert("ORD_FAX_RECV_LINK", null, ordFaxRecvLinkId,
					this.getSessionUserName(),
					Constant.COM_LOG_EBK_FAX_TASK_EVENT.deleteRelateCertificate
							.name(), "删除关联凭证", "删除关联凭证" + ordFaxRecvLinkId,
					null);
		} else {
			result.raise("操作出错！");
		}
		result.output(getResponse());
	}
	/**
	 * 删除关联
	 * */
	@Action("/fax/faxReceive/deleteOrdFaxRecvItem")
	public void deleteOrdFaxRecvItem() {
		JSONResult result = new JSONResult();
		List<Long> ordFaxRecvIdList = new ArrayList<Long>();
		Map<String,List<Long>> recvIdMapList = new HashMap<String,List<Long>>();
		for(Long ordFaxRecvId:ordFaxRecvIds){
			ordFaxRecvIdList.add(ordFaxRecvId);
		}
		if (ordFaxRecvIdList != null) {
			recvIdMapList.put("recvIdMapList", ordFaxRecvIdList);
			ordFaxRecvService.updateOrdFaxRecvValidToFalse(recvIdMapList);
		}
		result.output(getResponse());
	}

	public List<CodeItem> getRecvStatusList() {
		return CodeSet.getInstance().getCodeList(
				Constant.CODE_TYPE.FAX_RECV_STATUS.name());
	}

	public OrdFaxRecv getOrdFaxRecv() {
		return ordFaxRecv;
	}

	public void setOrdFaxRecv(OrdFaxRecv ordFaxRecv) {
		this.ordFaxRecv = ordFaxRecv;
	}


	public Date getMinRecvTime() {
		return minRecvTime;
	}

	public void setMinRecvTime(Date minRecvTime) {
		this.minRecvTime = minRecvTime;
	}

	public Date getMaxRecvTime() {
		return maxRecvTime;
	}

	public void setMaxRecvTime(Date maxRecvTime) {
		this.maxRecvTime = maxRecvTime;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Page getPagination() {
		return pagination;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setOrdFaxRecvId(Long ordFaxRecvId) {
		this.ordFaxRecvId = ordFaxRecvId;
	}

	public List<OrdFaxRecvLink> getOrdFaxRecvLinkList() {
		return ordFaxRecvLinkList;
	}

	public List<OrdFaxRecv> getOrdFaxRecvList() {
		return ordFaxRecvList;
	}

	public void setOrdFaxRecvLinkId(Long ordFaxRecvLinkId) {
		this.ordFaxRecvLinkId = ordFaxRecvLinkId;
	}

	public void setMinOperateTime(Date minOperateTime) {
		this.minOperateTime = minOperateTime;
	}

	public void setMaxOperateTime(Date maxOperateTime) {
		this.maxOperateTime = maxOperateTime;
	}

	public Long getEbkCertificateId() {
		return ebkCertificateId;
	}

	public void setEbkCertificateId(Long ebkCertificateId) {
		this.ebkCertificateId = ebkCertificateId;
	}


	public Long getPage_2() {
		return page_2;
	}

	public void setPage_2(Long page_2) {
		this.page_2 = page_2;
	}

	public String getFileType() {
		return fileType;
	}

	public Date getMinOperateTime() {
		return minOperateTime;
	}

	public Date getMaxOperateTime() {
		return maxOperateTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setTifPage(int tifPage) {
		this.tifPage = tifPage;
	}

	public OrdFaxRecvLink getOrdFaxRecvLink() {
		return ordFaxRecvLink;
	}

	public void setOrdFaxRecvLink(OrdFaxRecvLink ordFaxRecvLink) {
		this.ordFaxRecvLink = ordFaxRecvLink;
	}

	public void setTifPath(String tifPath) {
		this.tifPath = tifPath;
	}

	public Long[] getOrdFaxRecvIds() {
		return ordFaxRecvIds;
	}

	public void setOrdFaxRecvIds(Long[] ordFaxRecvIds) {
		this.ordFaxRecvIds = ordFaxRecvIds;
	}

	public String getFaxRoot(){
		return Constant.getInstance().getFaxRecv();
	}

	public String getIsModifyCertificateId() {
		return isModifyCertificateId;
	}

	public void setIsModifyCertificateId(String isModifyCertificateId) {
		this.isModifyCertificateId = isModifyCertificateId;
	}

	public String getModifyCertIdflag() {
		return modifyCertIdflag;
	}

	public void setModifyCertIdflag(String modifyCertIdflag) {
		this.modifyCertIdflag = modifyCertIdflag;
	}

	public String getUpdateFaxRecvStatusAndCertMemoOnly() {
		return updateFaxRecvStatusAndCertMemoOnly;
	}

	public void setUpdateFaxRecvStatusAndCertMemoOnly(String updateFaxRecvStatusAndCertMemoOnly) {
		this.updateFaxRecvStatusAndCertMemoOnly = updateFaxRecvStatusAndCertMemoOnly;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public EbkCertificateService getEbkCertificateService() {
		return ebkCertificateService;
	}

	public void setEbkCertificateService(EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
	}

	public EbkFaxTaskService getEbkFaxTaskService() {
		return ebkFaxTaskService;
	}

	public void setEbkFaxTaskService(EbkFaxTaskService ebkFaxTaskService) {
		this.ebkFaxTaskService = ebkFaxTaskService;
	}

	public String getFaxSendRecvStatus() {
		return faxSendRecvStatus;
	}

	public void setFaxSendRecvStatus(String faxSendRecvStatus) {
		this.faxSendRecvStatus = faxSendRecvStatus;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Page<OrdFaxRecv> getOrdFaxRecvPage() {
		return ordFaxRecvPage;
	}

	public void setOrdFaxRecvPage(Page<OrdFaxRecv> ordFaxRecvPage) {
		this.ordFaxRecvPage = ordFaxRecvPage;
	}



	public OrdFaxRecvService getOrdFaxRecvService() {
		return ordFaxRecvService;
	}

	public void setOrdFaxRecvService(OrdFaxRecvService ordFaxRecvService) {
		this.ordFaxRecvService = ordFaxRecvService;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public Page<OrdFaxRecvLink> getOrdFaxRecvLinkPage() {
		return ordFaxRecvLinkPage;
	}

	public void setOrdFaxRecvLinkPage(Page<OrdFaxRecvLink> ordFaxRecvLinkPage) {
		this.ordFaxRecvLinkPage = ordFaxRecvLinkPage;
	}


}
