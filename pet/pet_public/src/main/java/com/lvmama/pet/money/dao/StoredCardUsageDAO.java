package com.lvmama.pet.money.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.StoredCardUsage;
import com.lvmama.comm.vo.Constant.STORED_CARD_ENUM;

/**
 * 
 * @author Libo Wang
 *
 */
public class StoredCardUsageDAO extends BaseIbatisDAO {
	public Long insert(StoredCardUsage usage){
		return (Long)super.insert("STORED_CARD_USAGE.insert", usage);
	}
	
	public void update(StoredCardUsage usage){
		super.update("STORED_CARD_USAGE.updateByPrimaryKey", usage);
	}
	
	/**
	 * 消费记录.
	 * @param param
	 * @return
	 */
	public List<StoredCardUsage> queryByParam(Map<String, Object> param){
		return super.queryForList("STORED_CARD_USAGE.queryByParam", param);
	}
	
	/**
	 * 根据Serial取相应的支付记录 .
	 * @param param
	 * @return
	 */
	public StoredCardUsage queryBySerial(String serial){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("serial", serial);
		map.put("usageType", STORED_CARD_ENUM.STORED_PAY.name());
		List<StoredCardUsage> list = super.queryForList("STORED_CARD_USAGE.queryByParam", map);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
}
