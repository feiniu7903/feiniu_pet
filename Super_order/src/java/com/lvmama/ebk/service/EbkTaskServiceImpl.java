package com.lvmama.ebk.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ebk.dao.EbkCertificateDAO;
import com.lvmama.ebk.dao.EbkCertificateItemDAO;
import com.lvmama.ebk.dao.EbkOrderDataRevDAO;
import com.lvmama.ebk.dao.EbkTaskDAO;

public class EbkTaskServiceImpl implements EbkTaskService {

	private EbkTaskDAO ebkTaskDAO;

	private EbkCertificateDAO ebkCertificateDAO;
	private EbkCertificateItemDAO ebkCertificateItemDAO;
	private EbkOrderDataRevDAO ebkOrderDataRevDAO;
	private EbkCertificateService ebkCertificateService;
	
	@Override
	public Long insert(EbkTask task) {
		return ebkTaskDAO.insert(task);
	}
	
	@Override
	public void insertList(List<EbkTask> taskList) {
		for(EbkTask t : taskList) {
			this.insert(t);
		}
	}
	@Override
	public ResultHandle updateEbk(Long ebkTaskId, String certificateStatus, Date waitTime, String memo, String confirmUser,String reason, Long version){
		ResultHandle result = new ResultHandle();
		EbkTask task =findEbkTaskByPkId(ebkTaskId);
		if(task==null) {
			result.setMsg("订单不存在");
			return result;
		}
		EbkCertificate ebkCertificate = this.ebkCertificateDAO.selectByPrimaryKey(task.getEbkCertificateId());
		if(ebkCertificate == null) {
			result.setMsg("订单不存在");
			return result;
		}
		if(!ebkCertificate.hasCertificateStatusCreate()) {
			result.setMsg("订单已经"+ebkCertificate.getZhCertificateStatus());
			return result;
		}
		if(!ebkCertificate.getVersion().equals(version)) {
			result.setMsg("数据有更新，请刷新再操作");
			return result;
		}
		ebkCertificate.setConfirmChannel(Constant.EBK_CERTIFICATE_CONFIRM_CHANNEL.EBK.name());
		task.setConfirmUser(confirmUser);
		task.setConfirmTime(new Date());
		if(Constant.EBK_TASK_STATUS.ACCEPT.name().equals(certificateStatus)) {
			if(waitTime!=null){
				ebkCertificate.setRetentionTime(waitTime);
			}
		}
		ebkCertificate.setMemo(memo);	
		ebkCertificate.setCertificateStatus(certificateStatus);	
		ebkCertificate.setReason(reason);
		//更新酒店确认号
		int i=0;

	    i=ebkCertificateDAO.updateByPrimaryKeySelective(ebkCertificate);
		if(i>0) {
			i=ebkTaskDAO.updateByPrimaryKey(task);
			if(i>0) {
				return result;
			}
		}
		result.setMsg("操作失败");
		return result;
	}
	@Override
	public ResultHandle updateEbkCertificateItemSupplierNo(String supplierNo,Long ebkTaskId){
		ResultHandle result = new ResultHandle();
		EbkTask ebkTask=ebkTaskDAO.selectByPrimaryKey(ebkTaskId);
		List<EbkCertificateItem> ebkCertificateItemList=ebkCertificateItemDAO.selectEbkCertificateItemByebkCertificateId(ebkTask.getEbkCertificateId());
		for(EbkCertificateItem ebkCertificateItem:ebkCertificateItemList)
		{
			ebkCertificateItem.setSupplierNo(supplierNo);
			int i=ebkCertificateItemDAO.updateByPrimaryKeySelective(ebkCertificateItem);
			if(i<=0)
			{
				result.setMsg("酒店确认号保存失败");
				return result;
			}
		}	
		return result;
	}
	@Override
	public List<EbkTask> findEbkTaskByExample(Map<String, Object> param) {
		return ebkTaskDAO.selectByExample(param);
	}
	@Override
	public List<EbkTask> findEbkTaskByOrderId(Long orderId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderId", orderId);
		return findEbkTaskByExample(param);
	}
	@Override
	public void updateList(List<EbkTask> updateTaskList) {
		for(EbkTask t : updateTaskList) {
			this.update(t);
		}
	}
	
	@Override
	public Integer findEbkTaskCountByExample(Map<String, Object> params) {
		return ebkTaskDAO.queryEbkTaskCount(params);
	}
	@Override
	public void update(EbkTask task) {
		this.ebkTaskDAO.updateByPrimaryKey(task);
	}
	@Override
	public EbkTask findEbkTaskByPkId(Long ebkTaskId) {
		return this.ebkTaskDAO.selectByPrimaryKey(ebkTaskId);
	}
	
	@Override
	public EbkTask selectNearbyEbkTaskByOrderItemMetaId(Long ordItemMetaId) {
		List<EbkTask> result = findEbkTaskByOrdItemMetaId(ordItemMetaId);
		if(result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	@Override
	public List<EbkTask> findEbkTaskByOrdItemMetaId(Long ordItemMetaId) {
		return this.ebkTaskDAO.selectByOrdItemMetaId(ordItemMetaId);
	}

	@Override
	public EbkTask selectByEbkCertificateId(Long ebkCertificateId) {
		return this.ebkTaskDAO.selectByEbkCertificateId(ebkCertificateId);
	}
	@Override
	public EbkTask findEbkTaskAndCertificateByPkId(Long ebkTaskId) {
		EbkTask ebkTask=findEbkTaskByPkId(ebkTaskId);
		if(ebkTask != null) {
			EbkCertificate ebkCertificate=ebkCertificateDAO.selectByPrimaryKey(ebkTask.getEbkCertificateId());
			if(ebkCertificate != null) {
				List<EbkCertificateItem> ebkCertificateItemList = ebkCertificateItemDAO.selectEbkCertificateItemByebkCertificateId(ebkTask.getEbkCertificateId());
				ebkCertificate.setEbkCertificateItemList(ebkCertificateItemList);
				
				Long metaProductID = ebkCertificateItemList.get(0).getMetaProductId();
				ebkCertificate.setMainMetaProductID(metaProductID);
				String metaProductName = ebkCertificateItemList.get(0).getRealProductName();
				ebkCertificate.setMainMetaProductName(metaProductName);
				String allUserMemo = "";
				String isResourceSendFax = "";
				for (int i = 0; i < ebkCertificateItemList.size(); i++) {
					EbkCertificateItem ebkCertificateItem = ebkCertificateItemList.get(i);
					ebkCertificateItem.setEbkCertificate(ebkCertificate);
					if(ebkCertificateItem.getFaxMemo()!=null&&!"".equals(ebkCertificateItem.getFaxMemo()))
					{
						allUserMemo += ebkCertificateItem.getFaxMemo()+"<br>";
					}
					isResourceSendFax = ebkCertificateItem.getIsResourceSendFax();
				}
				ebkCertificate.setIsResourceSendFax(isResourceSendFax);
				ebkCertificate.setAllUserMemo(allUserMemo);
				
				
			}
			ebkTask.setEbkCertificate(ebkCertificate);
		}
		return ebkTask;
	}
	@Override
	public void updateEbkTaskSynOrder(Long orderId, String paymentStatus, String orderStatus) {
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put("orderId", orderId);
		Page<EbkTask> eList = this.ebkTaskDAO.queryEbkTaskList(1L, 1000L, parameterObject );
		for(EbkTask e : eList.getItems()) {
			e.setPaymentStatus(paymentStatus);
			e.setOrderStatus(orderStatus);
		}
		this.updateList(eList.getItems());
	}
	@Override
	public EbkTask findEbkTaskAndCertificateAndGetContentByPkId(Long ebkTaskId) {
		 EbkTask ebkTask = findEbkTaskByPkId(ebkTaskId);
		 if(null!=ebkTask){
			 EbkCertificate ebkCertificate=this.ebkCertificateService.selectEbkCertDetailAndGetContentByPrimaryKey(ebkTask.getEbkCertificateId());
			 ebkTask.setEbkCertificate(ebkCertificate);
		 }
		return ebkTask;
	}
	
	@Override
	public void updateTaskConfirmStatus(Long ebkTaskId,String date,String confirmUser,String status,String memo) {
		EbkTask ebkTask = findEbkTaskByPkId(ebkTaskId);
		Date confirmTime = DateUtil.stringToDate(date,"yyyy-MM-dd HH:mm:ss");
		if(Constant.EBK_TASK_STATUS.ACCEPT.name().equalsIgnoreCase(status)){
			ebkTask.setConfirmTime(confirmTime);
			ebkTask.setConfirmUser(confirmUser);
		}else{
			ebkTask.setConfirmTime(null);
			ebkTask.setConfirmUser("");
		}
		ebkTask.setStatus(status);
		ebkTask.setMemo(memo);
		this.update(ebkTask);
		
		EbkCertificate ebkCertificate=ebkCertificateDAO.selectByPrimaryKey(ebkTask.getEbkCertificateId());
		ebkCertificate.setMemo(memo);
		ebkCertificateDAO.updateByPrimaryKey(ebkCertificate);
	}
	
	public EbkTaskDAO getEbkTaskDAO() {
		return ebkTaskDAO;
	}
	public void setEbkTaskDAO(EbkTaskDAO ebkTaskDAO) {
		this.ebkTaskDAO = ebkTaskDAO;
	}
	public EbkCertificateDAO getEbkCertificateDAO() {
		return ebkCertificateDAO;
	}
	public void setEbkCertificateDAO(EbkCertificateDAO ebkCertificateDAO) {
		this.ebkCertificateDAO = ebkCertificateDAO;
	}
	public EbkCertificateItemDAO getEbkCertificateItemDAO() {
		return ebkCertificateItemDAO;
	}
	public void setEbkCertificateItemDAO(EbkCertificateItemDAO ebkCertificateItemDAO) {
		this.ebkCertificateItemDAO = ebkCertificateItemDAO;
	}
	public EbkOrderDataRevDAO getEbkOrderDataRevDAO() {
		return ebkOrderDataRevDAO;
	}
	public void setEbkOrderDataRevDAO(EbkOrderDataRevDAO ebkOrderDataRevDAO) {
		this.ebkOrderDataRevDAO = ebkOrderDataRevDAO;
	}
	public EbkCertificateService getEbkCertificateService() {
		return ebkCertificateService;
	}
	public void setEbkCertificateService(EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
	}

}
