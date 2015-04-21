package com.lvmama.comm.pet.service.shop;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.shop.ShopLog;

/**
 * 积分商城日志的逻辑接口
 * @author Brian
 *
 */
public interface ShopLogService {
	/**
	 * 新增日志
	 * @param content 内容
	 * @param objectId 目标标识
	 * @param objectType 目标类型
	 * @param logType 日志类型
	 * @param operatorId 操作人
	 */
	void insert(String content, Long objectId, String objectType, String logType, String operatorId);

	/**
	 * 查询日志
	 * @param parametes 查询参数
	 * @return 日志列表
	 */
	List<ShopLog> query(Map<String, Object> parametes);
	
	/**
	 * 保存客服发放积分的信息
	 * @param info
	 *            信息
	 */
	void savePutPoint(final Map<String, Object> info);
}
