package com.lvmama.comm.bee.service.pass;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.vo.Passport;

/**
 * @author ShiHui
 */
public interface PassPortCodeService {
	/**
	 * 按通关码ID查询
	 */
	List<PassPortCode> queryPassPortCodes(Long codeId);

	/**
	 * 按通关点ID查询
	 */
	boolean selectPassPortCodeByPortId(Long portId);
	/**
	 * 添加通关码关联信息
	 * @param passPortCodes
	 */
	void addPassPortCodes(PassPortCode passPortCodes);
	/**
	 * 修改
	 * */
	public void updatePassPortCode(PassPortCode passPortCode);
	
	/**
	 * 查询通关点关联信息
	 * @param codeId
	 * @return
	 */
	public PassPortCode getPassPortCodeByCodeIdAndPortId(Long codeId,Long portId) ;
	/**
	 * 查询通关点的提供商信息
	 * @param codeId
	 * @return
	 */
	public List<PassPortCode> queryProviderByCode(Long codeId);
	/**
	 * 查询通关点关联信息
	 * @param codeId
	 * @return
	 */
	public PassPortCode getPassPortCodeByObjectIdAndTargetId(List<Long> objectId,Long targetId);
	/**
	 * 修改
	 * */
	public void updatePassPortCode(PassCode passCode, Passport data);

	List<PassPortCode> selectAllMergeSmsByParams(Map params);
	
	/**
	 * 根据订单号查询通关码列表 
	 */
	public List<PassPortCode> searchPassPortByOrderId(Long orderId);
}
