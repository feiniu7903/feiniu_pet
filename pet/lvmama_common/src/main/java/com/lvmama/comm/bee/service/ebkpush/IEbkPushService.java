package com.lvmama.comm.bee.service.ebkpush;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.pet.vo.Page;


public interface IEbkPushService {
	/**
	 * 插入一条推送纪录
	 * @param record
	 * @return
	 */
	Long insertEbkPushMessage(EbkPushMessage record);
	EbkPushMessage selectMessageByPK(Long id);
	void updateEbkPushMessage(EbkPushMessage reco);
	Long countTodayMsgByDeviceId(String udid);
	Long countTodyTimeOutMsgByDeviceId(String udid);
	Page<EbkPushMessage> selectPushFailedMessage(String udid,Long page,Long pageSize);
	Long getMessageIdSeq();
	List<EbkPushMessage> selectPushMsg(Map<String, String> params);
	int deleteHistoryDate(String udid);
}
