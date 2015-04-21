package com.lvmama.pet.pub.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComKeJetWord;
import com.lvmama.comm.pet.service.pub.ComKeJetWordService;
import com.lvmama.pet.pub.dao.ComKeJetWordDAO;

public class ComKeJetWordServiceImpl implements ComKeJetWordService {
	@Autowired
	private ComKeJetWordDAO comKeJetWordDAO;
	
	public void insert(ComKeJetWord word) {
		comKeJetWordDAO.insert(word);
	}
	
	public ComKeJetWord queryByPK(Long id) {
		return comKeJetWordDAO.queryByPK(id);
	}
	
	public List<ComKeJetWord> queryByParam(Map<String, Object> param) {
		return comKeJetWordDAO.queryByParam(param);
	}
	
	public void delete(Long id) {
		comKeJetWordDAO.delete(id);
	}
	
	public void update(ComKeJetWord word) {
		comKeJetWordDAO.update(word);
	}
}
