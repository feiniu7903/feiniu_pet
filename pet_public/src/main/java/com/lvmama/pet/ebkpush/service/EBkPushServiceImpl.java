package com.lvmama.pet.ebkpush.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.pet.ebkpush.dao.EbkPushMessageDAO;

public class EBkPushServiceImpl implements IEbkPushService {
	
	private EbkPushMessageDAO ebkPushMessageDAO;
	
	@Override
	public Long insertEbkPushMessage(EbkPushMessage record) {
		return ebkPushMessageDAO.insert(record);
	}
	

	public void setEbkPushMessageDAO(EbkPushMessageDAO ebkPushMessageDAO) {
		this.ebkPushMessageDAO = ebkPushMessageDAO;
	}


	@Override
	public EbkPushMessage selectMessageByPK(Long id) {
		// TODO Auto-generated method stub
		return this.ebkPushMessageDAO.selectByPrimaryKey(id);
	}

	@Override
	public Page<EbkPushMessage> selectPushFailedMessage(String udid,Long page,Long pageSize){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("udid", udid);
		
		Long count = this.ebkPushMessageDAO.selectCountByParam(param);
		Page<EbkPushMessage> pageConfig = new Page<EbkPushMessage>(count, pageSize, page);
		param.put("_startRow", pageConfig.getStartRows());
		param.put("_endRow", pageConfig.getEndRows());
		List<EbkPushMessage> list = this.ebkPushMessageDAO.selectByParams(param);
		pageConfig.setItems(list);
		return pageConfig;
	}
	

	@Override
	public void updateEbkPushMessage(EbkPushMessage record) {
		// TODO Auto-generated method stub
		this.ebkPushMessageDAO.updateByPrimaryKey(record);
	}


	@Override
	public Long countTodayMsgByDeviceId(String udid) {
		// TODO Auto-generated method stub
		return ebkPushMessageDAO.countTodayMsgByDeviceId(udid);
	}

	public Long getMessageIdSeq(){
		return ebkPushMessageDAO.getMessageIdSeq();
	}

	@Override
	public Long countTodyTimeOutMsgByDeviceId(String udid) {
		// TODO Auto-generated method stub
		return ebkPushMessageDAO.countTodyTimeOutMsgByDeviceId(udid);
	}


	@Override
	public List<EbkPushMessage> selectPushMsg(Map<String, String> params) {
		// TODO Auto-generated method stub
		return ebkPushMessageDAO.selectPushMessage(params);
	}


	@Override
	public int deleteHistoryDate(String udid) {
		// TODO Auto-generated method stub
		return ebkPushMessageDAO.deleteHistoryDate(udid);
	}

}
