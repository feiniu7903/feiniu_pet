package com.lvmama.comm.bee.service.ebooking;

import java.util.Date;

/**
 * ebk产品审核服务
 * @author taiqichao
 *
 */
public interface EbkProdAuditService {
	
	
	/**
	 * 判断ebk产品是否需要审核
	 * @param ebkProdProductId ebk产品id
	 * @return 是否需要审核true/false
	 */
	public boolean isNeedAudit(Long ebkProdProductId);
	
	/**
	 * 系统自动审核通过
	 * @param ebkProdProductId ebk产品id
	 * @param updateUserId 更新人
	 */
	public void auditPassBySystem(Long ebkProdProductId,Long updateUserId)throws Exception;
	
	
	/**
	 * super后台人工审核通过
	 * @param ebkProdProductId ebk产品id
	 * @param onlineTime 上线时间
	 * @param offlineTime 下线时间
	 * @param online 是否上线
	 * @throws Exception
	 */
	public void auditPassByUser(Long ebkProdProductId,Date onlineTime,Date offlineTime,Boolean online)throws Exception;

}
