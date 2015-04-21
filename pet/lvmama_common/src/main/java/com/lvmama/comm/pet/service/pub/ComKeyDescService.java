package com.lvmama.comm.pet.service.pub;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComKeyDesc;

public interface ComKeyDescService {
	void insert(String key, String desc);
	List<ComKeyDesc> queryAll();
	public Long countComKeyDesc();
	public List<ComKeyDesc> queryComKeyDescByPage(Map<String,Object> param);
	void remove(String key);
	void deleteInValid();
}
