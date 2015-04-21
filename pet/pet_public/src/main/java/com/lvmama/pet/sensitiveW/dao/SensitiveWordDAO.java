package com.lvmama.pet.sensitiveW.dao;

import java.util.List;
import java.util.Map;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.sensitiveW.SensitiveWord;

public class SensitiveWordDAO extends BaseIbatisDAO {
	
	public SensitiveWord selectByPrimaryKey(Long sensitiveId) {
		return (SensitiveWord) super.queryForObject("SENSITIVE_WORD.selectByPrimarykey", sensitiveId);
	}

    public Long insert(SensitiveWord sensitiveWord) {
        return (Long) super.insert("SENSITIVE_WORD.insert", sensitiveWord);
    }

    public Long update(SensitiveWord sensitiveWord) {
    	return (long) super.update("SENSITIVE_WORD.update", sensitiveWord);
    }
    
    public void deleteByPrimaryKey(Long sensitiveId) {
    	super.delete("SENSITIVE_WORD.deleteByPrimaryKey", sensitiveId);
    }
    
    public Long selectByParamsCount(Map<String, Object> params) {
    	return (Long) super.queryForObject("SENSITIVE_WORD.selectByParamsCount", params);
    }
    
    public List<SensitiveWord> selectByParams(Map<String, Object> params) {
    	return super.queryForList("SENSITIVE_WORD.selectByParams", params);
    }
    
    public boolean checkIsExisted(String content) {
    	Long count = (Long) super.queryForObject("SENSITIVE_WORD.checkIsExisted", content);
    	return count > 0 ? true : false;
    }
    
    public List<SensitiveWord> selectAll() {
    	return super.queryForListForReport("SENSITIVE_WORD.selectAll");
    }
    
    public List<SensitiveWord> selectListByIds(Map<String, Object> params) {
		return super.queryForList("SENSITIVE_WORD.selectListByIds", params);
	}
    
    public void deleteByIds(Map<String, Object> params) {
    	super.delete("SENSITIVE_WORD.deleteByIds", params);
    }
}
