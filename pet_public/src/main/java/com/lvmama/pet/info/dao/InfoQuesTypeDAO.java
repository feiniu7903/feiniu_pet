package com.lvmama.pet.info.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.info.InfoQuesType;


@SuppressWarnings("unchecked")
public class InfoQuesTypeDAO extends BaseIbatisDAO {

    public InfoQuesTypeDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long typeId) {
        InfoQuesType key = new InfoQuesType();
        key.setTypeId(typeId);
        int rows = super.delete("INFO_QUES_TYPE.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(InfoQuesType record) {
        Object newKey = super.insert("INFO_QUES_TYPE.insert", record);
        return (Long) newKey;
    }

    public Long insertSelective(InfoQuesType record) {
        Object newKey = super.insert("INFO_QUES_TYPE.insertSelective", record);
        return (Long) newKey;
    }

    public InfoQuesType selectByPrimaryKey(Long typeId) {
        InfoQuesType key = new InfoQuesType();
        key.setTypeId(typeId);
        InfoQuesType record = (InfoQuesType) super.queryForObject("INFO_QUES_TYPE.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(InfoQuesType record) {
        int rows = super.update("INFO_QUES_TYPE.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(InfoQuesType record) {
        int rows = super.update("INFO_QUES_TYPE.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<InfoQuesType> selectByObjectType(String objectType){
    	InfoQuesType info = new InfoQuesType();
    	info.setObjectType(objectType);
    	return super.queryForList("INFO_QUES_TYPE.selectByObjectType",info);
    	
    }
    /**
     * 通过类型名称查询子分类
     * @param objectType
     * @return
     */
	public List<InfoQuesType> queryQuesByType(String objectType){
    	Map<String,String> param=new HashMap<String,String>();
    	param.put("objectType", objectType);
    	return super.queryForList("INFO_QUES_TYPE.selectQuesByType",param);    	
    }
	
	/**
	 * 标记一个类型无效
	 * @param typeId
	 */
	public void markValid(Long typeId){
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("typeId", typeId);
		param.put("valid", "N");
		super.update("INFO_QUES_TYPE.markValid",param);
	}
}