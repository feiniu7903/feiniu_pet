package com.lvmama.pet.pub.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComEmailTemplate;

public class ComEmailTemplateDAO extends BaseIbatisDAO {
	/**
	 * 根据主键查询邮件模板
	 * @param id 主键
	 * @return 邮件模板
	 */
	public ComEmailTemplate queryComEmailTemplateByTemplateName(final String name) {
		return (ComEmailTemplate) super.queryForObject("COM_EMAIL_TEMPLATE.queryByTemplateName", name);
	}
}
