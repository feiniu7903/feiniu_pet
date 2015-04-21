package com.lvmama.comm.pet.service.sensitiveW;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.sensitiveW.SensitiveWord;

public interface SensitiveWordService {
	SensitiveWord selectByPrimaryKey(Long sensitiveId);

	Long insert(SensitiveWord sensitiveWord);

    Long update(SensitiveWord sensitiveWord);
    
    void deleteByPrimaryKey(Long sensitiveId);
    
    Long selectByParamsCount(Map<String, Object> params);
    
    List<SensitiveWord> selectByParams(Map<String, Object> params);
    
    void initSensitiveWordCacheMap(boolean create);
    
    List<SensitiveWord> selectListByIds(Long[] sensitiveIds);
    
    void deleteByIds(Long[] sensitiveIds);
    
    List<SensitiveWord> getAllSensitiveWords();
    
    /**
     * 校验是否有敏感词，有则返回true，否则返回false
     * 
     * {@value}需要判断的值
     * */
    boolean checkSensitiveWords(String value);
    
    /**
     * 校验敏感词，返回包含的敏感词内容，没有则为null
     * 
     * {@value}需要判断的值
     * */
    String returnSensitiveWords(String value);
}
