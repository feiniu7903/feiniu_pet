package com.lvmama.pet.pub.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.pub.ComLogContent;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COM_LOG_ORDER_EVENT;

public class ComLogDAO extends BaseIbatisDAO {

	private static Map<String, String> parentTypeMap = new HashMap<String, String>();
	static {
		parentTypeMap.put("ORD_REFUNDMENT","ORD_ORDER");
		parentTypeMap.put("PROD_RELATION","PROD_PRODUCT");
		parentTypeMap.put("ORD_ORDER_ITEM_META","ORD_ORDER");
		parentTypeMap.put("ORD_ORDER","ORD_ORDER");
		parentTypeMap.put("ORD_INVOICE","ORD_ORDER");
		parentTypeMap.put("PROD_PRODUCT_ITEM","PROD_PRODUCT");
		parentTypeMap.put("META_B_CERTIFICATE","META_PRODUCT");
		parentTypeMap.put("ORDER_PERSON","ORD_ORDER");
		parentTypeMap.put("MARK_CHANNEL","MARK_CHANNEL");
		parentTypeMap.put("PASS_CODE","ORD_ORDER");
		parentTypeMap.put("PROD_TIME_PRICE","PROD_PRODUCT");
		parentTypeMap.put("META_TIME_PRICE","META_PRODUCT");
		parentTypeMap.put("META_SETTLEMENT","META_PRODUCT");
		parentTypeMap.put("META_PERFORM","META_PRODUCT");
		parentTypeMap.put("ORD_SETTLEMENT_QUEUE_ITEM","ORD_ORDER");
		parentTypeMap.put("SUP_B_CERTIFICATE_TARGET","SUP_SUPPLIER");
		parentTypeMap.put("SUP_SETTLEMENT_TARGET","SUP_SUPPLIER");
		parentTypeMap.put("SUP_PERFORM_TARGET","SUP_SUPPLIER");
		
	}
	
	public Long queryListAllByCount(Map param) {
		return (Long)super.queryForObject("COM_LOG.selectAllByCount", param);
	}
	
	public List<ComLog> queryListAll(Map param) {
		List<ComLog> list=super.queryForList("COM_LOG.selectAll", param);
		setClobContent(list);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComLog> queryByParentIdAndObjectType( long parentId, String parentType, String objectType) {
		ComLog par = new ComLog();
		par.setObjectType(objectType);
		par.setParentId(parentId);
		par.setParentType(parentType);
		List<ComLog> list = super.queryForList("COM_LOG.selectByParentIdAndObjectType", par);
		setClobContent(list);
		return list;
	}
	
	public List<ComLog> queryByParentId(String objectType, long objectId) {
		ComLog par = new ComLog();
		par.setObjectType(objectType);
		par.setObjectId(objectId);
		List<ComLog> list = super.queryForList("COM_LOG.selectByParentId", par);
		setClobContent(list);
		return list;
	}
	public List<ComLog> queryByObjectId(String objectType, long objectId) {
		ComLog par = new ComLog();
		par.setObjectType(objectType);
		par.setObjectId(objectId);
		List<ComLog> list = super.queryForList("COM_LOG.select", par);
		setClobContent(list);
		return list;
	}
	
	public void insert(ComLog record) {
		//由于有太多的地方，插入ComLog时，未传入parentType，所以此处进行默认添加其parentType.
		if (record.getParentType()==null) {
			record.setParentType(parentTypeMap.get(record.getObjectType()));
		}
		boolean flag = false;
		String content = record.getContent();
		if (content != null) {
			int len = content.getBytes().length;
			if(len <= 4000) {
				record.setContentType(Constant.COM_LOG_CONTENT_TYPE.VARCHAR.name());
			} else {
				flag = true;
				record.setContentType(Constant.COM_LOG_CONTENT_TYPE.CLOB.name());
				record.setContent("");
			}
		}
		Long id = (Long) super.insert("COM_LOG.insert", record);
		if(flag) {
			ComLogContent clc = new ComLogContent();
			clc.setLogId(id);
			clc.setContent(content);
			super.insert("COM_LOG_CONTENT.insert",clc);
		}
	}
	
	
	/*********************************************通过制定参数查询结果集，并进行分页*************************************************/
	public List<ComLog> queryByParentIdAndObjectTypeMap(Map map){
		List<ComLog> list = super.queryForList("COM_LOG.selectByParentIdAndObjectTypeMap", map);
		setClobContent(list);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<ComLog> queryByParentIdMap(Map map){
		List<ComLog> list = super.queryForList("COM_LOG.selectByParentIdMap", map);
		setClobContent(list);
		return list;
	}
	
	public List<ComLog> queryByObjectIdMap(Map map){
		List<ComLog> list = super.queryForList("COM_LOG.selectMap", map);
		setClobContent(list);
		return list;
	}
	public Long queryByParentIdAndObjectTypeMapCount(Map map){
		return (Long)super.queryForObject("COM_LOG.selectByParentIdAndObjectTypeMapCount", map);
	}
	public Long queryByParentIdMapCount(Map map){
		return (Long)super.queryForObject("COM_LOG.selectByParentIdMapCount", map);
	}
	public Long queryByObjectIdMapCount(Map map){
		return (Long)super.queryForObject("COM_LOG.selectMapCount", map);
	}

	/**
	 * 查询操作日志列表.
	 * 
	 * @param objectId
	 *            对象ID
	 * @param objectType
	 *            对象类型
	 * @param logType
	 *            日志类型
	 * @return 操作日志列表
	 */
	public List<ComLog> queryComLog(final Long objectId,
			final String objectType, final COM_LOG_ORDER_EVENT logType) {
		final Map<String, Object> map = new Hashtable<String, Object>();
		map.put("objectId", objectId);
		map.put("objectType", objectType);
		map.put("logType", logType.toString());
		List<ComLog> list = super.queryForList("COM_LOG.queryComLog",
				map);
		setClobContent(list);
		return list;
	}
	
	public List<String> queryObjectTypes(){
		return super.queryForList("COM_LOG.queryObjectTypes");
	}
	
	public List<ComLog> queryByMap(final Map map){
		List<ComLog> list = super.queryForList("COM_LOG.queryByMap",map);
		setClobContent(list);
		return list;
	}
	public Long queryByMapCount(final Map map){
		return (Long)super.queryForObject("COM_LOG.queryByMapCount",map);
	}
	public Date queryByMapMaxCreateTime(final Map map){
		return (Date)super.queryForObject("COM_LOG.queryByMapMaxCreateTime",map);
	}
	
	private void setClobContent(List<ComLog> list) {
		if(list != null&&!list.isEmpty()) {
			for (ComLog log : list) {
				if(Constant.COM_LOG_CONTENT_TYPE.CLOB.name().equals(log.getContentType())) {
					Long logId = log.getLogId();
					ComLogContent clc = (ComLogContent) super.queryForObject("COM_LOG_CONTENT.selectComLogById",logId);
					if(clc != null){
						log.setContent(clc.getContent());
					}
				}
			}
		}
	}
}