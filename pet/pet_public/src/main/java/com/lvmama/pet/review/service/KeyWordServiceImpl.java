package com.lvmama.pet.review.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.businesses.po.review.KeyWord;
import com.lvmama.comm.pet.service.review.KeyWordService;
import com.lvmama.pet.review.dao.KeyWordDAO;

 
public class KeyWordServiceImpl implements KeyWordService {
    private KeyWordDAO keyWordDao;
    
    @Override
    public void batchInsert(List<KeyWord> keyWordList) {
       keyWordDao.batchInsertKeyWord(keyWordList);
    }

    @Override
    public void deleteByKeyTarget(KeyWord keyWord) {
       keyWordDao.deleteKeyWord(keyWord);
    }

    @Override
    public void batchDeleteKeyWord(List<KeyWord> keyWordList) {
        keyWordDao.batchDeleteKeyWord(keyWordList);
    }

    @Override
    public List<KeyWord> batchQueryKeyWordByParam(Map<String, Object> map) {
        return keyWordDao.queryKeyWordByParamForReport(map);
    }

    @Override
    public Long getCountKeyWordListByParam(Map<String, Object> map) {
        return keyWordDao.getCountKeyWordByParam(map);
    }

    @Override
    public KeyWord queryKeyWordByKeyID(long id) {
       return keyWordDao.queryKeyWordByKeyID(id);
    }

    @Override
    public void updateKeyWord(KeyWord keyWord) {
        keyWordDao.updateKeyWord(keyWord);
    }

    @Override
    public void insert(KeyWord key) {
        keyWordDao.insertKeyWord(key);
    }
    public void setKeyWordDao(KeyWordDAO keyWordDao) {
        this.keyWordDao = keyWordDao;
    }

	@Override
	public KeyWord querykeyWordByparam(KeyWord key) {
 		return keyWordDao.querykeyWordByparam(key);
	}



}
