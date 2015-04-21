package com.lvmama.pet.pub.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComPinyin;

public class ComPinyinDAO extends BaseIbatisDAO{
	
	/**
	 * 查询汉语词典所对应的拼音列表
	 * 
	 * @param comPinyin
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<ComPinyin> findComPinyin(ComPinyin comPinyin) {
		return super.queryForList("COM_PINYIN.queryPinyibyWords", comPinyin);
	}

}