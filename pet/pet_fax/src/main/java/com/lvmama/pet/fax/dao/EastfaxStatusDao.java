package com.lvmama.pet.fax.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EastfaxStatusDao extends BaseJdbcTemplateDao {
	private static final Log log = LogFactory.getLog(EastfaxStatusDao.class);
	
	public List<Map<String,Object>> queryList() {
		String sql = "select ORD_NO, to_char(STATUS) STATUS from FAXSTAT where create_date>sysdate-1 and NOTIFIED='FALSE'";
		return getJdbcTemplate().queryForList(sql);
	}
	
	public void setNotified(String ORD_NO) {
		String sql = "update FAXSTAT set NOTIFIED='TRUE' where ORD_NO='"+ORD_NO+"'";
		log.info(sql);
		this.getJdbcTemplate().update(sql);
	}

	public void updateNullCallerId(){  
		String sql = "update FAXRECV set CALLERID = '0' where CALLERID is null";  
		this.getJdbcTemplate().update(sql);  
	}
	
	public List<Map<String,Object>> queryAllUnnotified(){
		String sqlupdate = "update FAXRECV set RECV_ID = FAXRECV_ID_SEQ.Nextval where NOTIFIED = 'FALSE' AND RECV_ID is null";
		this.getJdbcTemplate().update(sqlupdate);
		String sqlselect = "select RECV_TIME, CREATE_TIME,CALLERID,RECV_ID,RECV_FILENAME from FAXRECV where NOTIFIED = 'FALSE' AND RECV_ID is not null";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sqlselect);
		return list;
	}
	
	public void updateFaxRecvNotified(Long id){
		String sqlupdate = "update FAXRECV set NOTIFIED = 'TRUE' where RECV_ID = "+id;
		this.getJdbcTemplate().update(sqlupdate);
	}
}
