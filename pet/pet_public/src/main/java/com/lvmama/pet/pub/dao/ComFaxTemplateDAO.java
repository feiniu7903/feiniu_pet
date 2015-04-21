package com.lvmama.pet.pub.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComFaxTemplate;

public class ComFaxTemplateDAO extends BaseIbatisDAO {

	public ComFaxTemplateDAO() {
		super();
	}

	@SuppressWarnings("unchecked")
	public List<ComFaxTemplate> selectComFaxTemplateList() {
		return (List<ComFaxTemplate>) super.queryForList("COM_FAX_TEMPLATE.selectComFaxTemplateList");
	}

	public ComFaxTemplate selectByTemplateId(String templateId) {
		ComFaxTemplate key = new ComFaxTemplate();
		key.setTemplateId(templateId);
		ComFaxTemplate record = (ComFaxTemplate) super.queryForObject("COM_FAX_TEMPLATE.selectByTemplateId", key);
		return record;
	}
}