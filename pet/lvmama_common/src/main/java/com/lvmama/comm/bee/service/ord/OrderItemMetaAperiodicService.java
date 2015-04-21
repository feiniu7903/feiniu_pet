package com.lvmama.comm.bee.service.ord;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.utils.json.ResultHandle;


/**
 * 订单密码券关联SERVICE.
 * @author zhangjie
 * @version  20130516
 * 
 */
public interface OrderItemMetaAperiodicService {
	
	/**
	 * 密码券订单关联修改
	 * 
	 * @author: zhangjie 
	 * @param ordAperiodic        密码券实体
	 * @return
	 */
	ResultHandle updateStatusByPrimaryKey(final OrdOrderItemMetaAperiodic ordAperiodic);
	
	/**
	 * 不定期订单激活状态修改
	 * 
	 * @author: zhangjie 
	 * @param orderId        订单号
	 * @param visitPerName       客人姓名
	 * @param mobile              客人电话号码
	 * @param visitTimeStart      入住时间
	 * @param passwordUseStatus   激活状态
	 * @param userId   操作人ID 
	 * @param ebkCertificateId   凭证ID 
	 * @return
	 */
	ResultHandle updateAperiodicOrderUseStatus(final Long orderId, String visitPerName,String mobile, String visitTimeStart, String passwordUseStatus,String userId,List<Long> orderItemMetaIds);
	
	/**
	 * 判断不定期订单是否激活
	 * */
	boolean isOrderActivated(final Long orderId);
	
	/**
	 * 根据订单子子项id查询密码券
	 * */
	OrdOrderItemMetaAperiodic selectOrderAperiodicByOrderItemMetaId(Long orderItemMetaId);
	
	OrdOrderItemMetaAperiodic selectFirstOrderAperiodicByOrderId(Long orderId);
	

	/**
	 * 密码券校验
	 * */
	Map<String, Object> checkPasswordCertificate(Long orderId,Long supplierId,String passwordCertificate, String visitTime);
	
	/**
	 * 获取订单子子项对应的密码券状态
	 * */
	Map<Long, String> getAperiodicStatusByOrderId(Long orderId);
}