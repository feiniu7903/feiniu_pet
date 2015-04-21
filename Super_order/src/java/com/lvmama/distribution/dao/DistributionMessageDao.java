package com.lvmama.distribution.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionMessage;
import com.lvmama.comm.pet.vo.Page;
/**
 * 分销商推送消息
 * @author gaoxin
 *
 */
public class DistributionMessageDao extends BaseIbatisDAO{
	
	/**
	 * 插入一条数据
	 * @param distributionMessage
	 */
	public void insert(DistributionMessage distributionMessage) {
		try {
			super.insert("DISTRIBUTION_MESSAGE.insert", distributionMessage);
		} catch (Exception e) {
		}
	}
	/**
	 * 更新一条数据
	 * @param distributionMessage
	 */
	public void update(DistributionMessage distributionMessage){
		super.update("DISTRIBUTION_MESSAGE.update",distributionMessage);
	}
	
	/**
	 * 条件查询单条分销商消息
	 */
	public DistributionMessage queryByMsgParams(DistributionMessage distributionMessage){
		return (DistributionMessage) this.queryForObject("DISTRIBUTION_MESSAGE.getByMsg", distributionMessage);
	}
	
	/**
	 * 条件查询分销商消息总数
	
	public Long queryCountByMsgParams(DistributionMessage distributionMessage) {
		return (Long) super.queryForObject("DISTRIBUTION_MESSAGE.findCountByParams",distributionMessage);
	}
	 */
	
	/**
	 * 条件查询分销商消息
	 */
	@SuppressWarnings("unchecked")
	public List<DistributionMessage> queryByParams(DistributionMessage distributionMessage){
		List<DistributionMessage> distributionMessages = new ArrayList<DistributionMessage>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", distributionMessage.getObjectId());
		map.put("objectType", distributionMessage.getObjectType());
		map.put("eventType", distributionMessage.getEventType());
		map.put("distributorChannel", distributionMessage.getDistributorChannel());
		map.put("status", distributionMessage.getStatus());
		map.put("reapplyTime", distributionMessage.getReapplyTime());
		long pageNum = 1;
		long pageSize = 2;
		map.put("currentPage", pageNum);
		map.put("pageSize", pageSize);
		Page<DistributionMessage> messagePage = super.queryForPage("DISTRIBUTION_MESSAGE.findByParams", map);
		List<DistributionMessage> tempMessages = new ArrayList<DistributionMessage>();
		tempMessages = messagePage.getItems();
		long totalPage = messagePage.getTotalPages();
		do {
			pageNum ++; //页数先加1，以防死循环
			if(tempMessages!=null && !tempMessages.isEmpty()){
				distributionMessages.addAll(tempMessages);
				map.put("currentPage", pageNum);
				messagePage = super.queryForPage("DISTRIBUTION_MESSAGE.findByParams", map);
				tempMessages = messagePage.getItems();
			}
		} while ( pageNum<=totalPage );
		return distributionMessages;
	}
	
	
	
}
