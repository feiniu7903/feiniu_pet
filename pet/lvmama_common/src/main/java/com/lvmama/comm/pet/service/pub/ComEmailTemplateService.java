package com.lvmama.comm.pet.service.pub;

import com.lvmama.comm.pet.po.pub.ComEmailTemplate;

/**
 * 邮件模板的对外逻辑接口
 * @author Brian
 *
 */
public interface ComEmailTemplateService {
	/**
	 * 根据模板名称获取邮件模板
	 * @param name
	 * @return
	 */
	ComEmailTemplate getComEmailTemplateByTemplateName(String name);
}
