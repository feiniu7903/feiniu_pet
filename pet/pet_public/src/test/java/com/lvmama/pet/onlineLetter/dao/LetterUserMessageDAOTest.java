package com.lvmama.pet.onlineLetter.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.onlineLetter.LetterUserMessage;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseTest;

public class LetterUserMessageDAOTest extends BaseTest {

	@Autowired
	private LetterUserMessageDAO letterUserMessageDAO;
	@Test
	public void testBatchInsertUserLetter() {
		LetterUserMessage message = new LetterUserMessage();
		message.setMessageType(Constant.ONLINE_LETTER_TYPE.PROCLAMATION.name());
		message.setMessageContent("中华民族到了最危险的时候");
		message.setTemplateId(5L);
		message.setUserId(12L);
		List<LetterUserMessage> list = new ArrayList<LetterUserMessage>();
		list.add(message);
		letterUserMessageDAO.batchInsertUserLetter(list);
	}

	@Test
	public void testUpdateUserLetter() {
		letterUserMessageDAO.updateUserLetter(6L);
	}

	@Test
	public void testBatchDeleteUserLetter() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("endTime", new Date());
		letterUserMessageDAO.batchDeleteUserLetter(parameters);
	}

	@Test
	public void testQueryMessage() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("maxResult",15); 
		parameters.put("skipResult",0);
		parameters.put("endTime", new Date());
		letterUserMessageDAO.queryMessage(parameters);
	}

	@Test
	public void testCountMessage() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("endTime", new Date());
		letterUserMessageDAO.countMessage(parameters);
	}

}
