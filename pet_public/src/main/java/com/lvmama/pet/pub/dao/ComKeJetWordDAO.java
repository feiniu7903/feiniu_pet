package com.lvmama.pet.pub.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComKeJetWord;

public class ComKeJetWordDAO extends BaseIbatisDAO {
	public void insert(ComKeJetWord word) {
		super.insert("COM_KEJET_WORD.insert", word);
	}
	
	public ComKeJetWord queryByPK(Long id) {
		return (ComKeJetWord) super.queryForObject("COM_KEJET_WORD.selectByPrimaryKey", id);
	}
	
	public ComKeJetWord update(ComKeJetWord word) {
		super.update("COM_KEJET_WORD.update", word);
		return word;
	}
	
	@SuppressWarnings("unchecked")
	public List<ComKeJetWord> queryByParam(Map<String, Object> param) {
		return (List<ComKeJetWord>) super.queryForList("COM_KEJET_WORD.query", param);
	}
	
	public void delete(Long id) {
		super.delete("COM_KEJET_WORD.delete", id);
	}
}
