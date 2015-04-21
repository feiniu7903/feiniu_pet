package com.lvmama.ebk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkFaxSend;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ebk.dao.EbkCertificateDAO;
import com.lvmama.ebk.dao.EbkCertificateItemDAO;
import com.lvmama.ebk.dao.EbkFaxSendDAO;
import com.lvmama.ebk.dao.EbkFaxTaskDAO;
import com.opensymphony.oscache.util.StringUtil;

public class EbkFaxTaskServiceImpl implements EbkFaxTaskService {

	private ComLogDAO comLogDAO;
	private EbkFaxTaskDAO ebkFaxTaskDAO;
	private EbkCertificateDAO ebkCertificateDAO;
	private EbkCertificateItemDAO ebkCertificateItemDAO;
	private EbkFaxSendDAO ebkFaxSendDAO;
	
	@Override
	public Long insertEbkFaxTask(EbkFaxTask faxTask,String operatorName) {
		return ebkFaxTaskDAO.insertEbkFaxTask(faxTask);
	}
	@Override
	public List<EbkFaxTask> selectSendFaxTask() {
		List<EbkFaxTask> ebkFaxTaskList = ebkFaxTaskDAO.selectSendFaxTask();
		initEbkFaxTask(ebkFaxTaskList);
		return ebkFaxTaskList;
	}
	private void initEbkFaxTask(List<EbkFaxTask> ebkFaxTaskList) {
		Map<String, Object> itemParams;
		List<EbkCertificateItem> ebkCertificateItemList=null;
		List<EbkCertificateItem> ebkCertificateItemOrderList=null;
		EbkCertificate ebkCertificate = null;
		for(EbkFaxTask ebkFaxTask:ebkFaxTaskList){
			itemParams = new HashMap<String, Object>();
			itemParams.put("ebkCertificateId", ebkFaxTask.getEbkCertificateId());
			ebkCertificateItemList = ebkCertificateItemDAO.queryEbkCertificateItemList(itemParams);
			
			//传真任务对应的订单
			ebkCertificateItemOrderList = this.creatEbkCertificateItemOrderList(ebkCertificateItemList);
			ebkFaxTask.setEbkCertificateItemOrderList(ebkCertificateItemOrderList);
			
			//订单对应的产品
			this.createItemOrderProdList(ebkCertificateItemOrderList,ebkCertificateItemList);
			
			//与传真任务同级的凭证
			ebkCertificate = ebkCertificateDAO.selectByPrimaryKey(ebkFaxTask.getEbkCertificateId());
			ebkFaxTask.setEbkCertificate(ebkCertificate);
		}
	}
	@Override
	public List<EbkFaxTask> selectOldEbkFaxTaskListWithTaskId(Long ebkFaxTaskId) {
		List<EbkFaxTask> ebkFaxTaskList = ebkFaxTaskDAO.selectOldEbkFaxTaskListWithTaskId(ebkFaxTaskId);
		initEbkFaxTask(ebkFaxTaskList);
		return ebkFaxTaskList;
	}
	@Override
	public List<EbkFaxTask> selectEbkFaxTaskByParams(Map<String, Object> param) {
		List<EbkFaxTask> ebkFaxTaskList = ebkFaxTaskDAO.selectEbkFaxTaskByParams(param);
		initEbkFaxTask(ebkFaxTaskList);
		return ebkFaxTaskList;
	}
	/**
	 * 计算每个传真任务对应的产品
	 * @param itemOrderList
	 * @param itemOrderProdList
	 */
	private void createItemOrderProdList(List<EbkCertificateItem> itemOrderList,List<EbkCertificateItem> itemOrderProdList) {
		List<EbkCertificateItem> ebkCertificateItemList = null;
		for(EbkCertificateItem itemOrderProd : itemOrderList){
			ebkCertificateItemList = new ArrayList<EbkCertificateItem>();
			for(EbkCertificateItem itemOrderProdAll : itemOrderProdList){
				if(itemOrderProdAll.getOrderId().equals(itemOrderProd.getOrderId())){
					ebkCertificateItemList.add(itemOrderProdAll);
				}
			}
			itemOrderProd.setEbkCertificateItemList(ebkCertificateItemList);
		}
	}
	
	/**
	 * 
	 * 过滤掉重复的订单
	 * @param ebkCertificateItemList
	 * @return
	 */
	private List<EbkCertificateItem> creatEbkCertificateItemOrderList(List<EbkCertificateItem> ebkCertificateItemList){
		List<EbkCertificateItem> ebkCertificateItemTempList= new ArrayList<EbkCertificateItem>();
		Map<Long,Object> ebkCertificateItemMap = new HashMap<Long, Object>();
		for(EbkCertificateItem ebkCertificateItem:ebkCertificateItemList){
			ebkCertificateItemMap.put(ebkCertificateItem.getOrderId(), ebkCertificateItem);
		}
		for(Object  obj:ebkCertificateItemMap.values()){
			ebkCertificateItemTempList.add((EbkCertificateItem)obj);
		}
		return ebkCertificateItemTempList;
	}
	
	@Override
	public EbkCertificate getEbkCertificateById(Long ebkCertificateId) {
		return ebkCertificateDAO.selectByPrimaryKey(ebkCertificateId);
	}

	@Override
	public void updateEbkCertificateByFaxTaskId(String memo, List<Long> ebkFaxTaskIds,String operatorName) {
		ebkCertificateDAO.updateEbkCertificateByFaxTaskId(memo, ebkFaxTaskIds);
	}

	@Override
	public void updateEbkCertificate(EbkCertificate ebkCertificate) {
		ebkCertificateDAO.updateByPrimaryKeySelective(ebkCertificate);
	}

	@Override
	public Integer getEbkFaxTaskCountByParams(Map<String, Object> params) {
		return ebkFaxTaskDAO.getEbkFaxTaskCountByParams(params);
	}
	
	@Override
	public void updateEbkFaxTask(EbkFaxTask task,String operatorName, String logContent) {
		this.ebkFaxTaskDAO.updateEbkFaxTask(task);
		if(!StringUtil.isEmpty(logContent)){
			comLogDAO.insert("EBK_FAX_TASK", null, task.getEbkFaxTaskId(), operatorName, Constant.COM_LOG_EBK_FAX_TASK_EVENT.updateSendStatus.name(), 
					"修改传真状态", logContent, null);
		}
	}
	
	@Override
	public EbkFaxTask getByEbkFaxTaskId(Long ebkTaskId) {
		return this.ebkFaxTaskDAO.getByEbkFaxTaskId(ebkTaskId);
	}
	
	@Override
	public EbkFaxTask findEbkFaxTaskAndCertificateByPkId(Long ebkFaxTaskId) {
		EbkFaxTask eft = this.ebkFaxTaskDAO.getByEbkFaxTaskId(ebkFaxTaskId);
		EbkCertificate ec = this.ebkCertificateDAO.selectByPrimaryKey(eft.getEbkCertificateId());
		List<EbkCertificateItem> eci = this.ebkCertificateItemDAO.selectEbkCertificateItemByebkCertificateId(eft.getEbkCertificateId());
		eft.setEbkCertificate(ec);
		eft.setEbkCertificateItemOrderList(eci);
		return eft;
	}
	
	@Override
	public long getEbkFaxSendCountByParam(Map<String, Object> params) {
		return ebkFaxSendDAO.getEbkFaxSendCountByParams(params);
	}

	@Override
	public EbkFaxSend selectEbkFaxSendByPrimaryKey(Long ebkFaxSendId) {
		return ebkFaxSendDAO.selectByPrimaryKey(ebkFaxSendId);
	}
	@Override
	public List<EbkFaxSend> queryEbkFaxSendByParam(Map<String, Object> params) {
		return ebkFaxSendDAO.queryEbkFaxSendByParams(params);
	}
	@Override
	public EbkFaxTask getEbkFaxTaskByOrderItemMetaId(Long orderItemMetaId)
	{
		Map<String, Object> params=new HashMap<String, Object>();	 
		params.put("orderItemMetaId", orderItemMetaId);
		params.put("sort", "ebkFaxTaskIdDesc");
		List<EbkFaxTask> ebkFaxTaskList=ebkFaxTaskDAO.selectEbkFaxTaskByParams(params);
		if(ebkFaxTaskList.size()>0){
    		return ebkFaxTaskList.get(0);
    	}
		return new EbkFaxTask();
	}
	@Override
	public EbkFaxTask getByEbkCertificateId(Long ebkCertificateId) {
		return this.ebkFaxTaskDAO.getByEbkCertificateId(ebkCertificateId);
	}

	public EbkFaxTaskDAO getEbkFaxTaskDAO() {
		return ebkFaxTaskDAO;
	}

	public void setEbkFaxTaskDAO(EbkFaxTaskDAO ebkFaxTaskDAO) {
		this.ebkFaxTaskDAO = ebkFaxTaskDAO;
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

	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
	
	@Override
	public void updateEbkSendOrRecvStatus(EbkFaxTask ebkFaxTask, String operatorName) {
		ebkFaxTaskDAO.updateEbkSendOrRecvStatus(ebkFaxTask);
		EbkFaxTask ebkFaxTaskTemp =ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(ebkFaxTask.getEbkCertificateId());
		comLogDAO.insert("EBK_FAX_TASK", null, ebkFaxTaskTemp.getEbkFaxTaskId(), operatorName, 
				Constant.COM_LOG_EBK_FAX_TASK_EVENT.updateResultStatus.name(), "修改传真回传状态", 
				"根据传真编号"+ebkFaxTaskTemp.getEbkFaxTaskId()+"修改传真回传状态为[ "+Constant.FAX_SEND_RECV_STATUS.getCnName(ebkFaxTaskTemp.getFaxSendRecvStatus())+" ]", null);
	}
	@Override
	public Long insertOrdFaxSend(EbkFaxSend record) {
		Long ebkFaxSendId = ebkFaxSendDAO.insert(record);
		String content="";
		if(ebkFaxSendId>=1){
			if(record.getOperatorName() != null && record.getOperatorName().contains("System")){
				content="自动发送";
			}else{
				content="手工发送";
			}
			content+=" 传真状态为["+Constant.EBK_FAX_TASK_STATUS.getCnNameByStatus(record.getSendStatus())+"]";
		}
		EbkFaxTask ebkFaxTask = this.ebkFaxTaskDAO.getByEbkFaxTaskId(record.getEbkFaxTaskId());
		ebkFaxTask.setSendStatus(record.getSendStatus());
		ebkFaxTask.setSendCount(ebkFaxTask.getSendCount()+1L);
		ebkFaxTask.setNewSend("true");
		ebkFaxTask.setSendTime(record.getCreateTime());
		if(ebkFaxTask.hasAgainSend()){
			ebkFaxTask.setAgainSend("false");
		}
		
		this.updateEbkFaxTask(ebkFaxTask, record.getOperatorName(), content);
		// 更新前一个凭证的传真任务的最新发送状态(OLD_CERTIFICATE_ID)
		EbkFaxTask updateOldEbkFaxTask = new EbkFaxTask();
		//where EBK_CERTIFICATE_ID=(select a.OLD_CERTIFICATE_ID from EBK_CERTIFICATE a where EBK_CERTIFICATE_ID=#ebkCertificateId#)
		updateOldEbkFaxTask.setEbkCertificateId(ebkFaxTask.getEbkCertificateId());
		updateOldEbkFaxTask.setNewSend("false");
		this.ebkFaxTaskDAO.updateEbkFaxTaskNewStatusWithNewId(updateOldEbkFaxTask);
		
		return ebkFaxSendId;
	}

	public void setEbkFaxSendDAO(EbkFaxSendDAO ebkFaxSendDAO) {
		this.ebkFaxSendDAO = ebkFaxSendDAO;
	}

	@Override
	public boolean updateEbkFaxSend(EbkFaxSend send, String logContent) {
		EbkFaxSend entity = ebkFaxSendDAO.selectByPrimaryKey(send.getEbkFaxSendId());
		entity.setSendStatus(send.getSendStatus());
		entity.setSendTime(send.getSendTime());
		ebkFaxSendDAO.updateByPrimaryKey(entity);
		EbkFaxTask task = new EbkFaxTask();
		task.setEbkFaxTaskId(entity.getEbkFaxTaskId());
		task.setSendStatus(send.getSendStatus());
		this.updateEbkFaxTask(task, send.getOperatorName(), logContent);
		return true;
	}

	@Override
	public int updateUserMemoStatus(Long orderId,
			String updateUserMemoStatus) {
		List<EbkCertificate> ebkCertList = ebkCertificateDAO.selectEbkCertificateByOrderId(orderId);
		EbkCertificate ebk = null;
		for (int i = 0; i < ebkCertList.size(); i++) {
			ebk = ebkCertList.get(i);
			 ebkCertificateDAO.updateUserMemoStatus(ebk.getEbkCertificateId(), updateUserMemoStatus);
		}
		return ebkCertList.size();
	}
	@Override
	public EbkFaxTask selectEbkFaxTaskByEbkCertificateId(Long certificateId) {
		return this.ebkFaxTaskDAO.selectEbkFaxTaskByEbkCertificateId(certificateId);
	}
	@Override
	public EbkFaxTask selectEbkFaxTaskByEbkFaxSendId(Long sendId){
		EbkFaxTask ebkFaxTask = ebkFaxTaskDAO.selectEbkFaxTaskByEbkFaxSendId(sendId);
		return ebkFaxTask;
	}

}
