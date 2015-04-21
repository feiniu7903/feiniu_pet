package com.lvmama.pet.onlineLetter.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.onlineLetter.LetterTemplate;

public class LetterTemplateDAO extends BaseIbatisDAO {
	
	public Long insertTemplate(LetterTemplate template) {
		return (Long)super.insert("ONLINE_LETTER_TEMPLATE.insertTemplate", template);
	}

	public int updateTemplate(LetterTemplate template) {
		return super.update("ONLINE_LETTER_TEMPLATE.updateTemplate", template);
	}

	public List<LetterTemplate> queryTemplate(Map<String, Object> parameters) {
		return (List<LetterTemplate>)super.queryForList("ONLINE_LETTER_TEMPLATE.queryTemplate", parameters);
	}
	public Long countTemplate(Map<String, Object> parameters) {
		parameters.remove("maxResult");
		parameters.remove("skipResult");
		return (Long)super.queryForObject("ONLINE_LETTER_TEMPLATE.countTemplate", parameters);
	}
}
