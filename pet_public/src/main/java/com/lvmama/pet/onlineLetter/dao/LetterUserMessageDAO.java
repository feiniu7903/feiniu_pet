package com.lvmama.pet.onlineLetter.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.onlineLetter.LetterUserMessage;

public class LetterUserMessageDAO extends BaseIbatisDAO {
	
	public List<LetterUserMessage> batchInsertUserLetter(final List<LetterUserMessage> list) {
		 super.execute(new SqlMapClientCallback<Object>() { 
	        public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException { 
	            executor.startBatch(); 
	            for (LetterUserMessage message : list) { 
	            	executor.insert("ONLINE_LETTER_USER_MESSAGE.insertTemplate", message); 
	            } 
	            return executor.executeBatch(); 
	        } 
	    }); 
		 return list;
	}
	
	public int updateUserLetter(Long id){
		return super.update("ONLINE_LETTER_USER_MESSAGE.updateUserLetter", id);
	}
	public int batchDeleteUserLetter(Map<String, Object> parameters) {
		return super.delete("ONLINE_LETTER_USER_MESSAGE.batchDeleteUserLetter", parameters);
	}
	
	public List<LetterUserMessage> queryMessage(Map<String, Object> parameters) {
		return (List<LetterUserMessage>)super.queryForList("ONLINE_LETTER_USER_MESSAGE.queryMessage", parameters);
	}

	public Long countMessage(Map<String, Object> parameters) {
		parameters.remove("maxResult");
		parameters.remove("skipResult");
		return (Long)super.queryForObject("ONLINE_LETTER_USER_MESSAGE.countMessage",parameters);
	}

}
