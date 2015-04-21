package com.lvmama.pet.pub.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant.COM_LOG_CASH_EVENT;
import com.lvmama.comm.vo.Constant.COM_LOG_ORDER_EVENT;
import com.lvmama.comm.vo.Constant.COM_LOG_SALE_SERVICE;
import com.lvmama.pet.pub.dao.ComLogDAO;

public class ComLogServiceImpl implements ComLogService {
    @Autowired
	ComLogDAO comLogDAO;
	
	/**
	 * @param objectType
	 *         对象类型.
	 * @param  parentId 
	 *           parentId.
	 * @param objectId
	 *          对象ID.
	 * @param operatorName      
	 *          操作者名字.
	 * @param logType
	 *         日志类型.
	 * @param logName
	 *         日志表名字.        
	 * @param   content 
	 *           日志内容. 
	 * @param   parentType 
	 *            parentType.        
	 * @return  list
	 *        日志的列表.
	 */
    @Override
	public void insert(String objectType, Long parentId, Long objectId, String operatorName,
			String logType, String logName, String content, String parentType) {
		ComLog log = new ComLog();
		log.setParentId(parentId);
		log.setParentType(parentType);
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setContent(content);
		comLogDAO.insert(log);
	}
	
	@Override
	public void addComLog(ComLog comlog){
		comLogDAO.insert(comlog);
	}
	public void addComLogList(List<ComLog> comlogList){
		for (ComLog comLog2 : comlogList) {
			comLogDAO.insert(comLog2);	
		}
	}
	
	@Override
	public List<ComLog> queryByParentIdAndObjectType( long parentId, String parentType, String objectType) {
		return comLogDAO.queryByParentIdAndObjectType(parentId, parentType, objectType);
	}
	
	@Override
	public List<ComLog> queryByParentId(String objectType, long objectId) {
		return comLogDAO.queryByParentId(objectType, objectId);
	}
	
	@Override
	public List<ComLog> queryByObjectId(String objectType, long objectId) {
		return comLogDAO.queryByObjectId(objectType, objectId);
	}
	
	@Override
	public List<ComLog> queryByObjectId(long objectId,String objectType,COM_LOG_ORDER_EVENT logType) {
		if(objectType==null){
			objectType="ORD_SETTLEMENT";
			logType=COM_LOG_ORDER_EVENT.insertOrderSettlementMemo;
		}
		return comLogDAO.queryComLog(
				objectId, objectType,logType);
	}

	/**
	 * 取结算单的日志列表.
	 * @param settlementId
	 *            结算单号.
	 * @return  ComLogList
	 *            日志列表.
	 */
	@Override
	public List<ComLog> queryBySubAndSettlement(long settlementId){
		return this.queryByObjectId(settlementId,"ORD_SETTLEMENT",COM_LOG_ORDER_EVENT.updateOrderSettlementConfirm);
	}

	@Override
	public List<ComLog> queryByParentIdAndObjectTypeMap(Map map){
		return this.comLogDAO.queryByParentIdAndObjectTypeMap(map);
	}
	
	@Override
	public List<ComLog> queryByParentIdMap(Map map){
		return this.comLogDAO.queryByParentIdMap(map);
	}
	
	@Override
	public List<ComLog> queryByObjectIdMap(Map map){
		return this.comLogDAO.queryByObjectIdMap(map);
	}
	
	@Override
	public Long queryByParentIdAndObjectTypeMapCount(Map map){
		return this.comLogDAO.queryByParentIdAndObjectTypeMapCount(map);
	}
	
	@Override
	public Long queryByParentIdMapCount(Map map){
		return this.comLogDAO.queryByParentIdMapCount(map);
	}
	
	@Override
	public Long queryByObjectIdMapCount(Map map){
		return this.comLogDAO.queryByObjectIdMapCount(map);
	}

	@Override
	public List<ComLog> queryByMap(Map<String, Object> map) {
		return comLogDAO.queryByMap(map);
	}
	@Override
	public Long queryCountByMap(Map<String, Object> map) {
		return comLogDAO.queryByMapCount(map);
	}	
	
	/**
	 * 返回object_type列表
	 * @return
	 */
	public List<String> selectObjectTypes(){
		return comLogDAO.queryObjectTypes();
	}

	@Override
	public List<ComLog> queryLogByObjectId(long objectId, String objectType,
			COM_LOG_CASH_EVENT logType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComLog> queryComLogSaleService(long objectId,
			String objectType, COM_LOG_SALE_SERVICE logType) {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", objectId);
		map.put("objectType", objectType);
		map.put("logType", logType.toString());
		map.put("skipResults", 0);
		map.put("maxResults", 1000);
		return comLogDAO.queryByMap(map);
	}
	@Override
	public Date queryByMapMaxCreateTime(Map<String, Object> params) {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("objectId", params.get("objectId"));
		map.put("objectType", params.get("objectType"));
		map.put("logType", params.get("logType"));
		map.put("operatorName", params.get("operatorName"));
		return comLogDAO.queryByMapMaxCreateTime(map);
	}

	@Override
	public List<ComLog> queryListAll(Map map) {
		return comLogDAO.queryListAll(map);
	}
	
}
