package com.lvmama.ord.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ebooking.EbkFaxSend;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.fax.OrdFaxRecv;
import com.lvmama.comm.bee.po.fax.OrdFaxRecvLink;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.bee.service.fax.OrdFaxRecvService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ord.dao.OrdFaxRecvDAO;
import com.lvmama.ord.dao.OrdFaxRecvLinkDAO;

public class OrdFaxRecvServiceImpl implements OrdFaxRecvService{

	private OrdFaxRecvDAO ordFaxRecvDAO;
	private OrdFaxRecvLinkDAO ordFaxRecvLinkDAO;
	private ComLogDAO  comLogDAO;
	private EbkFaxTaskService ebkFaxTaskService;
	/**
	 * 新增回传，回传关联,更新传真发送，更新传真回传状态
	 * @param ordFaxRecv
	 */

	public Long receiveOrdFaxRecv(OrdFaxRecv ordFaxRecv) {
		//新增回传记录
		Long ordFaxRecvId = ordFaxRecvDAO.insertOrdFaxRecv(ordFaxRecv);
		if (StringUtils.isNotEmpty(ordFaxRecv.getBarcode())) {
			Long sendId=null;
			try{
				sendId = Long.parseLong(ordFaxRecv.getBarcode());
			}catch(Exception e){}
			if (sendId!=null&&sendId!=0) {
				ordFaxRecv.setOrdFaxRecvId(ordFaxRecvId);
				EbkFaxTask ebkFaxTask = ebkFaxTaskService.selectEbkFaxTaskByEbkFaxSendId(sendId);
				if(ebkFaxTask!=null){
					//新增回传关联记录
					this.insertOrdFaxRecvLink(ordFaxRecv,ebkFaxTask.getEbkCertificateId());
					//更新EbkFaxSend
					this.updateEbkFaxSendWithOrdFaxRecv(ordFaxRecv, sendId);
					//更新OrdFaxRecv的回传状态为自动关联
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("recvStatus", Constant.FAX_RECV_STATUS.AUTO_LINKED.name());
					map.put("recvId", ordFaxRecvId);
					ordFaxRecvDAO.updateOrdFaxRecvStatus(map);
				}
			}
		}
		return ordFaxRecvId;
	}
	/**
	 * 回传，更新传真发送记录表及任务状态
	 * @param ordFaxRecv
	 * @param sendId
	 */
	private void updateEbkFaxSendWithOrdFaxRecv(OrdFaxRecv ordFaxRecv, Long sendId) {
		EbkFaxSend orderFaxSend = new EbkFaxSend();
		orderFaxSend.setEbkFaxSendId(sendId);
		orderFaxSend.setSendStatus(Constant.EBK_FAX_TASK_STATUS.FAX_SEND_STATUS_REPLIED.getStatus());
		orderFaxSend.setOperatorName(ordFaxRecv.getOperatorName());
		String content = "";
		if("SYSTEM".equalsIgnoreCase(ordFaxRecv.getOperatorName())){
			content="系统回传";
		}else{
			content="手工上传回传件";
		}
		ebkFaxTaskService.updateEbkFaxSend(orderFaxSend, content);
	}
	/**
	 * 根据凭证和传真回传新增回传关联
	 * */
	private void insertOrdFaxRecvLink(OrdFaxRecv record,Long certificateId) {
		OrdFaxRecvLink ordFaxRecvLink = new OrdFaxRecvLink();
		ordFaxRecvLink.setOrderId(0L);
		ordFaxRecvLink.setEbkCertificateId(certificateId);
		ordFaxRecvLink.setOrdFaxRecvId(record.getOrdFaxRecvId());
		ordFaxRecvLink.setCreateTime(new Date());
		ordFaxRecvLink.setOperator(record.getOperatorName());
		ordFaxRecvLinkDAO.insertOrdFaxRecvLink(ordFaxRecvLink);
	}
	public List<OrdFaxRecv> selectByParam(Map param) {
		List<OrdFaxRecv> list = ordFaxRecvDAO.selectByParam(param);
		for(OrdFaxRecv ofr : list){
			List<OrdFaxRecvLink> links = ordFaxRecvLinkDAO.selectLinkByRecvId(ofr.getOrdFaxRecvId());
			ofr.setLinkOrderList(links);
		}
		return list;
	}

	@Override
	public List<OrdFaxRecv> queryOrdFaxRecvCertificateId(Long ebkCertificateId) {
		OrdFaxRecv ordFaxRecv = null;
		List<OrdFaxRecv> ordFaxRecvList = new ArrayList<OrdFaxRecv>();
		List<OrdFaxRecvLink> ordFaxRecvLinkList = ordFaxRecvLinkDAO.queryLinkByEbkCertificateId(ebkCertificateId);
		for(int i=0;ordFaxRecvLinkList!=null && i<ordFaxRecvLinkList.size();i++){
			ordFaxRecv = ordFaxRecvDAO.selectByPrimaryKey(ordFaxRecvLinkList.get(i).getOrdFaxRecvId());
			if(ordFaxRecv!=null){
			   ordFaxRecvList.add(ordFaxRecv);
			}
		}
		return ordFaxRecvList;
	}
	
	public Long selectByParamCount(Map param) {
		return ordFaxRecvDAO.selectByParamCount(param);
	}	
	
	
	/**
	 * 新增传真回传关联并更新传真关联状态为已关联
	 * */
	public Long insertLinkAndUpdateRecvStatus(OrdFaxRecvLink ordFaxRecvLink){
		ordFaxRecvLink.setOrderId(0L);
		Long linkId = ordFaxRecvLinkDAO.insertOrdFaxRecvLink(ordFaxRecvLink);		

		comLogDAO.insert("ORD_ORDER",null,ordFaxRecvLink.getOrderId(),ordFaxRecvLink.getOperator(),
				Constant.COM_LOG_ORDER_EVENT.updateOrderFaxTaskStatus.name(),
				"updateOrderFaxTaskStatus","修改订单传真关联状态为已关联", null);

		//更新订单传真关联状态为已关联
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("recvId", ordFaxRecvLink.getOrdFaxRecvId());
		map.put("recvStatus", Constant.FAX_RECV_STATUS.LINKED.name());
		ordFaxRecvDAO.updateOrdFaxRecvStatus(map);
		return linkId;
	}
	
	public int deleteByLinkId(Long ordFaxRecvLinkId) {
		return ordFaxRecvLinkDAO.deleteByLinkId(ordFaxRecvLinkId);
	}
	
	public void updateOrdFaxRecvLinkResultStatus(OrdFaxRecvLink ordFaxRecvLink) {
		ordFaxRecvLinkDAO.updateOrdFaxRecvLinkResultStatus(ordFaxRecvLink);
	}

	@Override
	public OrdFaxRecv selectByPrimaryKey(Long ordFaxRecvId) {
        return ordFaxRecvDAO.selectByPrimaryKey(ordFaxRecvId);
    }

	@Override
	public List<Long> selectLinkCertificateIdsByRecvId(Long recvId) {
		return ordFaxRecvLinkDAO.selectLinkCertificateIdsByRecvId(recvId);
	}

	@Override
	public Long selectLinksCountByParams(Map<String, Object> params) {
		return ordFaxRecvLinkDAO.selectLinksCountByParams(params);
	}

	@Override
	public Page<OrdFaxRecvLink> selectLinksByParams(Map<String, Object> params,
			Long pageSize, Long currentPage) {
		Long count = ordFaxRecvLinkDAO.selectLinksCountByParams(params);
		Page<OrdFaxRecvLink> page = Page.page(count, pageSize, currentPage);
		if(page != null && count > 0){
			params.put("skipResults", page.getStartRows());
			params.put("maxResults", page.getEndRows());
			List<OrdFaxRecvLink> list = ordFaxRecvLinkDAO.selectLinksByParams(params);
			page.setItems(list);
		}
		return page;
	}
	@Override
	public void updateOrdFaxRecvValidToFalse(Map<String,List<Long>>  recvIdMapList) {
		ordFaxRecvDAO.updateOrdFaxRecvValidToFalse(recvIdMapList);
	}
	
	@Override
	public List<OrdFaxRecvLink> selectLinkAndCertificateByRecvId(Long recvId) {
		List<OrdFaxRecvLink> linkList = ordFaxRecvLinkDAO.selectLinkAndCertificateByRecvId(recvId);
		return linkList;
	}

	public void setEbkFaxTaskService(EbkFaxTaskService ebkFaxTaskService) {
		this.ebkFaxTaskService = ebkFaxTaskService;
	}
	public OrdFaxRecvLinkDAO getOrdFaxRecvLinkDAO() {
		return ordFaxRecvLinkDAO;
	}
	public void setOrdFaxRecvLinkDAO(OrdFaxRecvLinkDAO ordFaxRecvLinkDAO) {
		this.ordFaxRecvLinkDAO = ordFaxRecvLinkDAO;
	}

	public void setOrdFaxRecvDAO(OrdFaxRecvDAO ordFaxRecvDAO) {
		this.ordFaxRecvDAO = ordFaxRecvDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}


}
