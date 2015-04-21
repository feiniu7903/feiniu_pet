/**
 * 
 */
package com.lvmama.comm.bee.service.op;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.op.OpTravelGroup;


/**
 * 对团操作的相关接口
 * @author yangbin
 *
 */
public interface IOpTravelGroupService extends IOpTravelGroupNumService{	
	
	/**
	 * 根据销售产品生成团号列表
	 * @param productId
	 */
	public void createTravelGroupByProductId(Long productId);
	/**
	 * 增加未支付人数
	 * @param travelGroupId
	 * @param count 人数
	 */
	void updatePayNot(Long travelGroupId,long count);
	
	/**
	 * 操作从未支付转换到部分支付的人数
	 * @param travelGroupId 
	 * @param count 人数
	 */
	void updatePayNot2Part(Long travelGroupId,long count);
	
	/**
	 * 操作从未支付到完成支付
	 * @param travelGroupId
	 * @param count
	 */
	void updatePayNot2Success(Long travelGroupId,long count);
	
	
	/**
	 * 操作从部分支付到完成支付
	 * @param travelGroupId
	 * @param count
	 */
	void updatePayPart2Success(Long travelGroupId,long count);
	
	
	/**
	 * 更新计划人数
	 * @param travelGroupId
	 * @param count
	 * @param operator
	 */
	void updateGroupInitialNum(Long travelGroupId,long count,String operator);
	
	/**
	 * 取团数量
	 * @param parameter
	 * @return
	 */
	long selectCountByParam(final Map<String,Object> parameter);
	
	/**
	 * 读取团列表
	 * @param parameter
	 * @return
	 */
	List<OpTravelGroup> selectListByParam(final Map<String,Object> parameter);
	
	/**
	 * 取参数对应的产品列表
	 * @param parameter
	 * @return
	 */
	List<OpTravelGroup> selectProductListByParam(final Map<String,Object> parameter);
	
	
	/**
	 * 取产品总数.
	 * @param parameter
	 * @return
	 */
	long selectProductCountByParam(final Map<String,Object> parameter);
	
	/**
	 * 取对应产品开始日期后的所有的团.
	 * @param productId
	 * @param startDate
	 * @return
	 */
	List<OpTravelGroup> selectListByProductDate(final Long productId,Date startDate);
	
	/**
	 * 按主键取得其对象
	 * @param id 
	 * @return
	 */
	OpTravelGroup getOpTravelGroup(Long id);
	
	/**
	 * 根据团号读取团信息
	 * @param code
	 * @return
	 */
	OpTravelGroup getOptravelGroup(String code);
	
	/**
	 * 修改团的状态 ,并且需要对状态做部分检测
	 * @param id
	 * @param status
	 * @param operator
	 */
	void changeStatus(Long id,String status,String memo,String operator);
	
	void update(OpTravelGroup group);
	
	/**
	 * 修改团是否可以发出团通知书
	 * @param id 团ID
	 * @param operator 操作人
	 */
	void changeGroupwordAble(Long id,String operator);
}
