/**
 * 
 */
package com.lvmama.pet.info.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.info.InfoHelpCenter;


/**
 * 帮助中心DAO
 * @author liuyi
 *
 */
public class InfoHelpCenterDAO extends BaseIbatisDAO {
	
	public InfoHelpCenterDAO()
	{
		super();
	}
	
    public int deleteByPrimaryKey(Long id) {
    	InfoHelpCenter key = new InfoHelpCenter();
        key.setId(id);
        int rows = super.delete("INFO_HELP_CENTER.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(InfoHelpCenter record) {
    	try{
        Object newKey = super.insert("INFO_HELP_CENTER.insert", record);
        return (Long) newKey;
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return null;
    }

    public Long insertSelective(InfoHelpCenter record) {
        Object newKey = super.insert("INFO_HELP_CENTER.insertSelective", record);
        return (Long) newKey;
    }

    public InfoHelpCenter selectByPrimaryKey(Long id) {
    	InfoHelpCenter key = new InfoHelpCenter();
        key.setId(id);
        InfoHelpCenter record = (InfoHelpCenter) super.queryForObject("INFO_HELP_CENTER.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(InfoHelpCenter record) {
        int rows = super.update("INFO_HELP_CENTER.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(InfoHelpCenter record) {
        int rows = super.update("INFO_HELP_CENTER.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<InfoHelpCenter> selectByTitleAndType(InfoHelpCenter record){
    	return super.queryForList("INFO_HELP_CENTER.selectByTitleAndType",record);
    }
	

}
