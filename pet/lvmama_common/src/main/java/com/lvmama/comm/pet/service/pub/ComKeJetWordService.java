package com.lvmama.comm.pet.service.pub;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComKeJetWord;

public interface ComKeJetWordService {
	void insert(ComKeJetWord word);

	ComKeJetWord queryByPK(Long id);

	List<ComKeJetWord> queryByParam(Map<String, Object> param);

	void delete(Long id);
	
	void update(ComKeJetWord word);

}
