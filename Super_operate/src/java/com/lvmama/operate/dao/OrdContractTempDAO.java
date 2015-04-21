package com.lvmama.operate.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.operate.OrdEContract;
import com.lvmama.comm.utils.DateUtil;

public class OrdContractTempDAO extends BaseIbatisDAO{
	private static final Logger LOG = Logger.getLogger(OrdContractTempDAO.class);
	final String SPACE = "ORD_CONTRACT_TEMP.";
	public List<OrdEContract> selectTravel(final Map<String,Object> parameters){
		return  super.queryForList(SPACE+"selectTravel", parameters);
	}
	public void update(final String sqlName,final List<OrdEContract> results){
		super.execute(new SqlMapClientCallback<Object>() { 
	        public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	        	LOG.info(DateUtil.formatDate(new java.util.Date(), "yyyy-MM-dd hh:mm:ss")+" > begin update "+sqlName);
	            executor.startBatch(); 
	            for (OrdEContract result : results) { 
	                executor.update(SPACE+sqlName, result); 
	            } 
	            LOG.info(DateUtil.formatDate(new java.util.Date(), "yyyy-MM-dd hh:mm:ss")+" > save update ");
	            return executor.executeBatch(); 
	        } 
	    });
	}
}
