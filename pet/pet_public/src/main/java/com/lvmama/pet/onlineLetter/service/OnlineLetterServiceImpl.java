package com.lvmama.pet.onlineLetter.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.onlineLetter.LetterTemplate;
import com.lvmama.comm.pet.onlineLetter.LetterUserMessage;
import com.lvmama.comm.pet.service.onlineLetter.OnlineLetterService;
import com.lvmama.pet.onlineLetter.dao.LetterTemplateDAO;
import com.lvmama.pet.onlineLetter.dao.LetterUserMessageDAO;

public class OnlineLetterServiceImpl implements OnlineLetterService {

	private LetterTemplateDAO letterTemplateDAO;
	private LetterUserMessageDAO letterUserMessageDAO;
	@Override
	public Long insertTemplate(LetterTemplate template) {
		return letterTemplateDAO.insertTemplate(template);
	}

	@Override
	public int updateTemplate(LetterTemplate template) {
		return letterTemplateDAO.updateTemplate(template);
	}

	@Override
	public List<LetterTemplate> queryTemplate(Map<String, Object> parameters) {
		return letterTemplateDAO.queryTemplate(parameters);
	}

	@Override
	public Long countTemplate(Map<String, Object> parameters) {
		return letterTemplateDAO.countTemplate(parameters);
	}

	@Override
	public List<LetterUserMessage> batchInsertUserLetter(List<LetterUserMessage> list) {
		return letterUserMessageDAO.batchInsertUserLetter(list);
	}
	
	@Override
	public int updateUserLetter(Long id){
		return letterUserMessageDAO.updateUserLetter(id);
	}
	@Override
	public int batchDeleteUserLetter(Map<String, Object> parameters) {
		return letterUserMessageDAO.batchDeleteUserLetter(parameters);
	}

	@Override
	public List<LetterUserMessage> queryMessage(Map<String, Object> parameters) {
		return letterUserMessageDAO.queryMessage(parameters);
	}

	@Override
	public Long countMessage(Map<String, Object> parameters) {
		return letterUserMessageDAO.countMessage(parameters);
	}

	public LetterTemplateDAO getLetterTemplateDAO() {
		return letterTemplateDAO;
	}

	public void setLetterTemplateDAO(LetterTemplateDAO letterTemplateDAO) {
		this.letterTemplateDAO = letterTemplateDAO;
	}

	public LetterUserMessageDAO getLetterUserMessageDAO() {
		return letterUserMessageDAO;
	}

	public void setLetterUserMessageDAO(LetterUserMessageDAO letterUserMessageDAO) {
		this.letterUserMessageDAO = letterUserMessageDAO;
	}

}
