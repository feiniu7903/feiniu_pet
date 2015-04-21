package com.lvmama.elong.service;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.RelatedOrderCondition;
import com.lvmama.elong.model.RelatedOrderResult;

/**
 * 当客人入住情况发生变化(增加或减少房间、延住、换酒店等)，系统会在原始订单的基础上生成一张新订单。
 * 本接口即用来查询新老订单的关系，一般使用场景是：老订单状态变化为特定状态(已结账、删除、删除另换酒店)时查询是否有新订单。
 *    同时需要在本地数据库中保持一个标记来表示是否已经请求过该接口，避免重复调用。
 * 须使用https访问本接口。
 *
 * @author qinzubo
 *
 */
public interface IOrderRelationService {
	RelatedOrderResult getRelatedOrder(RelatedOrderCondition condition) throws ElongServiceException;
}
