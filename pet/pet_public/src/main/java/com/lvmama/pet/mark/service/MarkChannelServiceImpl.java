package com.lvmama.pet.mark.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.mark.MarkChannelService;
import com.lvmama.comm.pet.vo.mark.MarkChannelVO;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.mark.dao.MarkChannelDAO;
import com.lvmama.pet.pub.dao.ComLogDAO;

public class MarkChannelServiceImpl implements MarkChannelService {
	@Autowired
	private MarkChannelDAO markChannelDAO;
	@Autowired
	private ComLogDAO comLogDAO;
	
	@Override
	public Long insertMarkDicChannelWithLog(MarkChannel markDicChannel, String operatorName) {
		// 插入渠道信息
		MarkChannel c = markChannelDAO.insert(markDicChannel);
		
		ComLog log = new ComLog();			
		log.setObjectType("MARK_CHANNEL");
		log.setParentId(markDicChannel.getFatherId());
		log.setObjectId(markDicChannel.getChannelId());
		log.setOperatorName(operatorName);
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.insertProdProductChannel.name());
		log.setLogName("创建渠道对象");
		log.setContent(LogViewUtil.logNewStr(operatorName));
		log.setParentType("MARK_CHANNEL");
		comLogDAO.insert(log);
		
		return c.getChannelId();		
	}
	
	@Override
	public void updateMarkDicChannelByPrimaryKeyWithLog(
			MarkChannel markDicChannel, String operatorName) {
		// 取得旧值
		MarkChannel oldObj = markChannelDAO
				.queryByPrimaryKey(markDicChannel.getChannelId());
		// 更新渠道信息
		markChannelDAO.update(markDicChannel);

		ComLog log = new ComLog();
		log.setObjectType("MARK_CHANNEL");
		log.setParentId(markDicChannel.getFatherId());
		log.setObjectId(markDicChannel.getChannelId());
		log.setOperatorName(operatorName);
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.updateProdProductChannel
				.name());
		log.setLogName("编辑渠道对象");
		log.setContent(LogViewUtil.logEditStr(
				"渠道名称，渠道代码，渠道描述，是否可用",
				oldObj.getChannelName()
						+ ","
						+ oldObj.getChannelCode()
						+ ","
						+ (null != oldObj.getChannelComment() ? oldObj
								.getChannelComment() : "") + ","
						+ oldObj.getValid(),
				markDicChannel.getChannelName()
						+ ","
						+ markDicChannel.getChannelCode()
						+ ","
						+ (null != markDicChannel.getChannelComment() ? markDicChannel
								.getChannelComment() : "") + ","
						+ markDicChannel.getValid()));
		log.setParentType("MARK_CHANNEL");
		comLogDAO.insert(log);
	}
	
	@Override
	public MarkChannel selectByPrimaryKey(Long channelId) {
		if (null == channelId) {
			return null;
		} else {
			return markChannelDAO.queryByPrimaryKey(channelId);
		}
	}

	@Override
	public List<MarkChannel> search(Map<String, Object> parameters) {
		if (null == parameters || parameters.isEmpty()) {
			return new ArrayList<MarkChannel>(0);
		} else {
			return markChannelDAO.query(parameters);
		}
	}

	@Override
	public List<MarkChannelVO> searchComplexVO(
			Map<String, Object> parameters) {
		if (null == parameters || parameters.isEmpty()) {
			return new ArrayList<MarkChannelVO>(0);
		} else {
			return markChannelDAO.queryComplexVO(parameters);
		}		
	}

	@Override
	public Long countComplexVO(Map<String, Object> parameters) {
		//这里不需要做parameters非空保护，渠道很少，一般我们都要查全COUNT
		return markChannelDAO.countComplexVO(parameters);
	}



}
