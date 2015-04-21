package com.lvmama.comm.pet.service.pub;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.vo.Constant;

public interface ComLogService {
	
	public static final String ORD_ORDER="ORD_ORDER";
	
	public static final String ORD_ORDER_APPROVE_INFO = "approveInfo" ;
	/**
	 * 记录操作日志
	 * @param objectType 对象类型，从本类成员中取
	 * @param parentId 父类编号.例如：订单子项和订单子子项(此项需要记录父类编号).
	 * @param objectId 对象编号或子类编号
	 * @param logType 事件代码
	 * @param logName 事件名称
	 * @param content 修改内容
	 * @param parentType TODO
	 * @param userId 操作用户编号
	 */
	public void insert(String objectType, Long parentId, Long objectId, String operatorName,
			String logType, String logName, String content, String parentType);
	
	/**
	 * 记录操作日志
	 * @param comlog
	 */
	public void addComLog(ComLog comlog);
	/**
	 * 记录操作日志集合
	 * @param comlog
	 */
	public void addComLogList(List<ComLog> comlogList);
	/**
	 * 查下面的某种类别的
	 * @param parentId
	 * @param parentType
	 * @param objectType
	 * @return
	 */
	public List<ComLog> queryByParentIdAndObjectType( long parentId, String parentType, String objectType);
	/**
	 * 查所有的
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	public List<ComLog> queryByParentId(String objectType, long objectId);
	
	
	/**
	 * 根据结算单子的id号取COMLOG的列表
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	public List<ComLog> queryByObjectId(long objectId,String objectType,Constant.COM_LOG_ORDER_EVENT logType);
	
	/**
	 * 取结算单的日志列表.
	 * @param settlementId
	 *            结算单号.
	 * @return  ComLogList
	 *            日志列表.
	 */
	public List<ComLog> queryBySubAndSettlement(long settlementId);
	/**
	 * 查自己的
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	public List<ComLog> queryByObjectId(String objectType, long objectId);
	
	
	/*********************************************通过指定参数查询结果集，并进行分页*************************************************/
	/**
	 * 条件由前台定义
	 */
	public List<ComLog> queryByMap(Map<String,Object> map);
	/**
	 * 条件由前台定义
	 * @param map
	 * @return
	 */
	public Long queryCountByMap(Map<String,Object> map);
	/**
	 * 查下面的某种类别的
	 * @param parentId
	 * @param parentType
	 * @param objectType
	 * @return
	 */
	public List<ComLog> queryByParentIdAndObjectTypeMap(Map map);
	/**
	 * 查所有
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	public List<ComLog> queryByParentIdMap(Map map);
	
	/**
	 * 查自己的
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	public List<ComLog> queryByObjectIdMap(Map map);
	/**
	 * 查下面的某种类别的
	 * @param parentId
	 * @param parentType
	 * @param objectType
	 * @return
	 */
	public Long queryByParentIdAndObjectTypeMapCount(Map map);
	/**
	 * 
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	public Long queryByParentIdMapCount(Map map);
	
	/**
	 * 查自己的
	 * @param objectType
	 * @param objectId
	 * @return
	 */
	public Long queryByObjectIdMapCount(Map map);

	
	/**
	 * 查询日志的object_type类型列表
	 * @return
	 */
	public List<String> selectObjectTypes();
	
	public List<ComLog> queryLogByObjectId(long objectId,String objectType, Constant.COM_LOG_CASH_EVENT logType);
	
	public List<ComLog> queryComLogSaleService(long objectId,String objectType, Constant.COM_LOG_SALE_SERVICE logType);
	public Date queryByMapMaxCreateTime(Map<String, Object> params);
	/**
	 * 根据参数查询
	 * @param map
	 * @return
	 */
	public List<ComLog>  queryListAll(Map map);
}