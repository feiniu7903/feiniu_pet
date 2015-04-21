package com.lvmama.comm.pet.service.pub;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComPinyin;


public interface ComPinyinService {
	
	/**
	 * 查找所有拼音词典库
	 * 
	 * @param usedBy
	 * @param count
	 * @return
	 */
	public List<ComPinyin> findComPinyin(ComPinyin comPinyin);
	

}
