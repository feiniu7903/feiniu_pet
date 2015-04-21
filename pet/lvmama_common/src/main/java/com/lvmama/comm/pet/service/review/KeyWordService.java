package com.lvmama.comm.pet.service.review;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.businesses.po.review.KeyWord;

public interface KeyWordService {
    
    public void insert(KeyWord key);
    /**
     * 批量添加
     * @param keyWord
     */
    public void batchInsert(List<KeyWord> keyWordList);
    
   /**
    * 通过关键字ID删除
    * @param id
    */
    public void deleteByKeyTarget(KeyWord keyWord);
    
    /**
     * 批量删除关键字
     * @param keyWordList
     */
    public void batchDeleteKeyWord(List<KeyWord> keyWordList);
   
    /**
     * 条件查询
     * @param map
     * @return
     */
    public List<KeyWord> batchQueryKeyWordByParam(Map<String, Object> map);
    
    /**
     * 条件查询获取记录数
     * @param map
     * @return
     */
    public Long getCountKeyWordListByParam(Map<String, Object> map);
    
    /**
     * 根据关键字ID得到关键字对象
     * @param id
     * @return
     */
    public KeyWord queryKeyWordByKeyID(long id);

    /**
     * 更新关键字信息
     * @param seoLinks
     */
    public void updateKeyWord(KeyWord keyWord);
	public KeyWord querykeyWordByparam(KeyWord key);
}
