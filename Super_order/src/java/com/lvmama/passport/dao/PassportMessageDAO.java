/**
 * 
 */
package com.lvmama.passport.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassportMessage;

/**
 * @author yangbin
 *
 */
public class PassportMessageDAO extends BaseIbatisDAO{

	public void insert(PassportMessage record){
		super.insert("PASSPORT_MESSAGE.insert", record);
	}
	
	public void delteByPK(final Long messageId){
		PassportMessage record = new PassportMessage();
		record.setMessageId(messageId);
		super.delete("PASSPORT_MESSAGE.delete",record);
	}
	
	public List<PassportMessage> selectList(String hostname,List<String> processorList,int size){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("hostname", hostname);
		if(CollectionUtils.isNotEmpty(processorList)){
			map.put("processorList", processorList);
		}
		map.put("_endRow", size);
		return super.queryForList("PASSPORT_MESSAGE.selectByParam",map);
	}
}
