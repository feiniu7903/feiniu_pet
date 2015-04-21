package com.lvmama.pet.onlineLetter.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.onlineLetter.LetterTemplate;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.BaseTest;

public class LetterTemplateDAOTest extends BaseTest{

	@Autowired
	private LetterTemplateDAO  letterTemplateDAO;
	@Test
	public void testInsertTemplate() {
		LetterTemplate template = new LetterTemplate();
		template.setTitle("测试");
		template.setType(Constant.ONLINE_LETTER_TYPE.PROCLAMATION.name());
		template.setBeginTime(DateUtil.getDayStart(new Date()));
		template.setEndTime(DateUtil.getDayEnd(new Date()));
		template.setUserGroupType(Constant.ONLINE_LETTER_USER_GROUP.ALL_USER.name());
		template.setContent("<h3 class=\"t\"><a href=\"http://www.baidu.com/link?url=dHFTGJqjJ4zBBpC8yDF8xDh8rD3nAJZjFWsHhEoSNd85PkV8Xiknq1p7OSKmpGvO3FiS0VCud9ti2UUsOaeERSO7UK\" target=\"_blank\"><span class=\"wenkuType PDFType\"></span><em>oracle</em>全文检索_百度文库</a></h3><font size=\"-1\"> <p style=\"padding-top:3px;\"> <span class=\"m\"><span class=\"starScore\"><span class=\"starMask\" style=\"width:60px;\"></span></span>&nbsp;评分:4/5&nbsp;40页</span> </p> 它可以对存储于文件系统中的文档进行检索和查找,并可检索超过 150 种文档类型,包括 Microsoft Word,PDF 和 XML.<em>Oracle</em> Text 查找功能包括模糊查找,词干查找(搜...<br><span class=\"g\">wenku.baidu.com/view/c53e9e36a32d737... 2010-6-29</span><span data-nolog=\"\" class=\"liketip\" id=\"like_2515850399812234576\"></span></font>");
		letterTemplateDAO.insertTemplate(template);
	}

	@Test
	public void testUpdateTemplate() {
		LetterTemplate template = new LetterTemplate();
		template.setBeginTime(DateUtil.getDayStart(new Date()));
		template.setEndTime(DateUtil.getDayEnd(new Date()));
		template.setId(5L);
		letterTemplateDAO.updateTemplate(template);
	}

	@Test
	public void testQueryTemplate() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("beginTime", new Date());
		parameters.put("endTime", new Date());
		parameters.put("keywords", "baidu");
		List<LetterTemplate> query = letterTemplateDAO.queryTemplate(parameters);
		Assert.assertNotNull(query.size());
	}

	@Test
	public void testCountTemplate() {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("beginTime", new Date());
		parameters.put("endTime", new Date());
		parameters.put("keywords", "baidu");
		Long count = letterTemplateDAO.countTemplate(parameters);
	}

}
