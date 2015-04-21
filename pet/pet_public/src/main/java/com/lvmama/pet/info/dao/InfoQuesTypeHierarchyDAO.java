/**
 * 
 */
package com.lvmama.pet.info.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.info.InfoQuesTypeHierarchy;


/**
 * @author liuyi
 *
 */
public class InfoQuesTypeHierarchyDAO extends BaseIbatisDAO {

	/**
	 * select valid type by primary key(valid means logic exist, invalid means logic deleted)
	 * @param typeId
	 * @return
	 */
    public InfoQuesTypeHierarchy selectValidTypeByPrimaryKey(Long typeId) {
    	InfoQuesTypeHierarchy key = new InfoQuesTypeHierarchy();
        key.setTypeId(typeId);
        InfoQuesTypeHierarchy record = (InfoQuesTypeHierarchy) super.queryForObject("INFO_QUES_TYPE_HIERARCHY.selectValidTypeByPrimaryKey", key);
        return record;
    }	
  
    public Long insertHierarchy(InfoQuesTypeHierarchy record) {
        Object newKey = super.insert("INFO_QUES_TYPE_HIERARCHY.insertHierarchy", record);
        return (Long) newKey;
    }
    
    
	public List<InfoQuesTypeHierarchy> queryValidQueTypeListByTypeAndParentId(InfoQuesTypeHierarchy infoQuesTypeHierarchy){
    	return super.queryForList("INFO_QUES_TYPE_HIERARCHY.selectValidTypeByObjectTypeAndParentId",infoQuesTypeHierarchy);
    	
    }	    
}
