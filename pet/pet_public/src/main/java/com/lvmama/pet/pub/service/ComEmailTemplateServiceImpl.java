package com.lvmama.pet.pub.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComEmailTemplate;
import com.lvmama.comm.pet.service.pub.ComEmailTemplateService;
import com.lvmama.pet.pub.dao.ComEmailTemplateDAO;


class ComEmailTemplateServiceImpl implements ComEmailTemplateService {
	@Autowired
	private ComEmailTemplateDAO comEmailTemplateDAO;
	
	@Override
	public ComEmailTemplate getComEmailTemplateByTemplateName(final String name) {
		return comEmailTemplateDAO.queryComEmailTemplateByTemplateName(name);
	}
}
