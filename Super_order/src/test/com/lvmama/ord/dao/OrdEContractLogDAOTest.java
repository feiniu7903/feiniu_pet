package com.lvmama.ord.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.pet.client.FSClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class OrdEContractLogDAOTest {
	@Autowired
	OrdEContractLogDAO ordEContractLogDAO;
	@Autowired
	FSClient fsClient;
	SqlMapClientTemplate sqlMapClientTemplate;
	final String SPACE = "ORD_ECONTRACT_LOG.";
	@Test
	public void testUpdateContent() throws Exception {
		 String SELECT_CONTRACT_SQL = "SELECT ROWID ROW_ID,E.ORDER_ID,E.CONTENT FROM ORD_ECONTRACT E WHERE E.CONTENT IS NOT NULL AND E.FIXED_FILE_ID IS NULL AND E.CONTENT_FILE_ID IS NULL AND ROWNUM<=500";
		 execute(SELECT_CONTRACT_SQL,"content.xhtml","updateContent");
	}
	@Test
	public void testUpdateContentLog() throws Exception {
		 String SELECT_CONTRACT_SQL = "SELECT ROWID ROW_ID,E.ORDER_ID,E.CONTRACT_VERSION,E.CONTENT FROM ORD_ECONTRACT_LOG E WHERE E.CONTENT IS NOT NULL AND E.CONTENT_FILE_ID IS NULL AND E.FIXED_FILE_ID IS NULL AND ROWNUM<=500";
		 execute(SELECT_CONTRACT_SQL,"content.xhtml","updateContentLog");
	}
	@Test
	public void testUpdateOrderTravel() throws Exception{
		 String SELECT_CONTRACT_SQL = "SELECT ROWID ROW_ID, ORDER_ID, ROUTE_TRAVEL CONTENT  FROM ORD_ORDER_ROUTE_TRAVEL T WHERE ROWNUM <= 500";
		 execute(SELECT_CONTRACT_SQL,"travel.xhtml","updateOrderTravel");		
	}
	private void execute(final String selectSql,final String fileName,final String sqlName) throws Exception{
		 boolean firstExecute = false;
		 List<Map<String,Object>> results;
		 do{
			 results = ordEContractLogDAO.queryForList(SPACE+"dynamicSelect", selectSql);
			 firstExecute = results.size()>0;
			 Long fileId = null;
			 for(Map<String,Object> result:results){
				 byte[] content = (byte[])result.get("CONTENT");
				 Object version = result.get("CONTRACT_VERSION");
				 String subName = null!=version?version.toString()+"_":"";
				 fileId = fsClient.uploadFile(result.get("ORDER_ID")+"_"+subName+fileName, content, "eContract");
				 result.remove("CONTENT");
				 result.put("FIXED_FILE_ID", fileId);
			 }
			 update(sqlName,results);
		 }while(firstExecute);
	}
	private void update(final String sqlName,final List<Map<String,Object>> results){
		sqlMapClientTemplate.execute(new SqlMapClientCallback<Object>() { 
	        public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException { 
	            executor.startBatch(); 
	            for (Map<String,Object> result : results) { 
	                executor.insert(SPACE+sqlName, result); 
	            } 
	            return executor.executeBatch(); 
	        } 
	    });
	}
}
