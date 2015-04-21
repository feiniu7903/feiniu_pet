package com.lvmama.ord.dao;
/**
 * @author shangzhengyuan
 * @description 电子合同线下签约备份文件
 * @version 在线预售权
 * @time 20120727
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdEcontractBackUpFile;

public class OrdEcontractBackUpFileDAO extends BaseIbatisDAO {
	private final static String SQL_SPACE = "ORD_ECONTRACT_BACKUP_FILE.";
	private final static String INSERT = SQL_SPACE+"insert";
	private final static String UPDATE = SQL_SPACE+"update";
	private final static String QUERY  = SQL_SPACE+"query";
	
	/**
	 * 插入电子合同签约备份文件记录
	 */
	public OrdEcontractBackUpFile insert(OrdEcontractBackUpFile object){
		super.insert(INSERT,object);
		return object;
	}
	
	/**
	 * 更新电子合同线下签约备份文件
	 * @param object
	 * @return
	 */
	public int update(OrdEcontractBackUpFile object){
		return super.update(UPDATE, object);
	}
	/**
	 * 根据条件查询签约备份文件列表
	 * @param parameters
	 * @return
	 */
	public List<OrdEcontractBackUpFile> query(Map<String,Object> parameters){
		List<OrdEcontractBackUpFile> queryForList = super.queryForList(QUERY,parameters);
		return queryForList;
	}
}
