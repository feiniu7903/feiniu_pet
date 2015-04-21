package com.lvmama.pet.fax.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.pet.fax.utils.Constant;

public class TrafaxStatusDao extends BaseJdbcTemplateDao {
	private static final Log log = LogFactory.getLog(TrafaxStatusDao.class);
	public void addFaxOut(Map<String, Object> map) {
		String telephone = map.get("telephone").toString().replace("-", "").replace('*', '+');
		Object[] objs = new Object[]{"administrator", map.get("filename"), telephone, map.get("receiver"), 
				3, map.get("serialno"), map.get("serialno").toString().substring(3)};
		String sql = "";
		if(Constant.isOracleDB()){
			sql = "Insert into TdbiFaxOut (FaxUserName, FaxId, FaxCreateDateTime, FaxNumber, FaxReceiver, " +
					"FaxPRI, FaxSendDateTime, FaxSchedule, FaxRetryTimes, FaxRetryInterval, TaskUniqueID, barcode)" +
					" values " +
					" (?,?,sysdate,?,?,?,sysdate,null,5,20,?,?)";
		}else {
			//sql server db
			sql = "Insert into TdbiFaxOut (FaxUserName, FaxId, FaxCreateDateTime, FaxNumber, FaxReceiver, " +
					"FaxPRI, FaxSendDateTime, FaxSchedule, FaxRetryTimes, FaxRetryInterval, TaskUniqueID, barcode)" +
					" values " +
					" (?,?,getdate(),?,?,?,getdate(),null,5,20,?,?)";
		}
		
		super.getJdbcTemplate().update(sql, objs);
	}
	
	public List<Map<String,Object>> queryErrorList() {
		String sql = "";
		if(Constant.isOracleDB()){
			sql = "select TaskUniqueID, FaxStatus,ErrorMsg, barcode from TdbiFaxOut where FaxSendDateTime < (sysdate - 15/60/24) and FaxSendDateTime > (sysdate - 25/60/24) and Faxstatus = 1 and Notified='FALSE'";	
		}else {
			//sql server db
			sql = "select TaskUniqueID, FaxStatus,ErrorMsg, barcode from TdbiFaxOut where FaxSendDateTime < (getdate() - 15/60/24) and FaxSendDateTime > (getdate() - 25/60/24) and Faxstatus = 1 and Notified='FALSE'";	
		}
		return getJdbcTemplate().queryForList(sql);
	}
	public List<Map<String,Object>> queryList() {
		String sql = "";
		if(Constant.isOracleDB()){
			sql = "select TaskUniqueID, FaxStatus,ErrorMsg, barcode from TdbiFaxOut where FaxSendDateTime>(sysdate-1) and (Faxstatus=4 or Faxstatus=5) and Notified='FALSE'";	
		}else {
			//sql server db
			sql = "select TaskUniqueID, FaxStatus,ErrorMsg, barcode from TdbiFaxOut where FaxSendDateTime>(getdate()-1) and (Faxstatus=4 or Faxstatus=5) and Notified='FALSE'";	
		}
		return getJdbcTemplate().queryForList(sql);
	}
	
	public void setNotified(String ORD_NO) {
		String sql = "update TdbiFaxOut set NOTIFIED='TRUE' where TaskUniqueID='"+ORD_NO+"'";
		log.info(sql);
		this.getJdbcTemplate().update(sql);
	}
	
	public void updateFaxStatus(String faxstatus,String barcode,String errorMsg) {
		String sql = "update TDBIFAXOUT set faxstatus='"+faxstatus+"',errorMsg='"+errorMsg+"',Notified='FALSE' where barcode='"+barcode+"'";
		log.info(sql);
		this.getJdbcTemplate().update(sql);
	}

	public void updateNullCallerId(){  
		String sql = "update TdbiFaxIn set FaxCallerID = '0' where FaxCallerID is null";  
		this.getJdbcTemplate().update(sql);  
	}
	
	public List<Map<String,Object>> queryAllUnnotified(){
		String sqlselect = "select SID,FaxRecvDateTime,FaxCallerID,FaxID,barcode from TdbiFaxIn where NOTIFIED = 'FALSE'";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sqlselect);
		return list;
	}
	
	/**
	 * 模拟传真回传使用
	 * @param map
	 */
	public void insertTdbiFaxIn(Map<String, Object> map){
		Date now = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String nowTime = sdf.format(now);
		Object[] objs = new Object[]{map.get("sid"), map.get("faxcallerId"), map.get("faxId"), map.get("barCode"),now};
		String sql = "insert into TDBIFAXIN (SID, FAXCALLERID, FAXID, BARCODE, NOTIFIED, FAXRECVDATETIME) values (?, ?, ?, ?, 'FALSE', ?)";
		
		super.getJdbcTemplate().update(sql, objs);
	}
	
	public void updateFaxRecvNotified(Long id){
		String sqlupdate = "update TdbiFaxIn set NOTIFIED = 'TRUE' where SID = "+id;
		this.getJdbcTemplate().update(sqlupdate);
	}
	
	/**
	 * 判断BARCODE是否存在对应VST传真
	 * @param barCode 发送ID
	 * @return true:存在  false:不存在
	 */
	public boolean isTdbiFaxOutByBarCode(String barCode){
		String selectCount = "select count(1) from TdbiFaxOut f where f.barcode ='"+barCode+"' and f.taskuniqueid like 'VST%'";
		int count = this.getJdbcTemplate().queryForInt(selectCount);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
}
