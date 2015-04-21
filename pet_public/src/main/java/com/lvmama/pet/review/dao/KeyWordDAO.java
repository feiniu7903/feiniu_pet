package com.lvmama.pet.review.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.businesses.po.review.KeyWord;

public class KeyWordDAO extends BaseIbatisDAO{
    /**
     * 批量添加关键词
     * @param keyWordList
     */
    public void batchInsertKeyWord(List<KeyWord> keyWordList){
        super.batchInsert("KEYWORD.batchInsertKeyWord", keyWordList);
    }
    
    /**
     * 批量选中删除关键词
     * @param keyWordList
     */
    public void batchDeleteKeyWord(List<KeyWord> keyWordList){
        super.batchDelete("KEYWORD.deleteKeyWord", keyWordList);
    }
   
    /**
     * 删除单条关键词
     * @param keyWord
     */
    public void deleteKeyWord(KeyWord keyWord){
        super.delete("KEYWORD.deleteKeyWord",keyWord);
    }
    
    /**
     * 修改一条关键词
     * @param keyWord
     */
    public void updateKeyWord(KeyWord keyWord){
        super.update("KEYWORD.updateKeyWord",keyWord);
    }

    /**
     * 根据条件查询关键词
     * @param map
     * @return
     */
    public List<KeyWord> queryKeyWordByParamForReport(Map<String, Object> map) {
        return super.queryForList("KEYWORD.batchKeyWordByParamForReport",map);
    }
    
    /**
     * 根据条件得到总条数
     * @param map
     * @return
     */
    public Long getCountKeyWordByParam(Map<String, Object> map) {
        return (Long) super.queryForObject("KEYWORD.getCountKeyWordByParam",map);
    }
    
    /**
     * 根据关键词ID查找对象
     * @param keyWord
     * @return
     */
    public KeyWord queryKeyWordByKeyID(Long keyId){
        return (KeyWord)super.queryForObject("KEYWORD.queryKeyWordByKeyID",keyId);
    }

    public void insertKeyWord(KeyWord key) {
        super.insert("KEYWORD.insertKey",key);
    }

	public KeyWord querykeyWordByparam(KeyWord key) {
		 return (KeyWord)super.queryForObject("KEYWORD.querykeyWordByparam",key);
	}
}
