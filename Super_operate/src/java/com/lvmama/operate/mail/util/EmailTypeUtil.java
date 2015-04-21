package com.lvmama.operate.mail.util;

import java.util.List;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.operate.util.CodeSet;

/**
 * 邮件类型工具类
 * 
 * @author likun
 * 
 */
public class EmailTypeUtil {

	private static final String EMAIL_TYPE_KEY = "EDM_EMAIL_TYPE";

	/**
	 * 根据有邮件类型获取邮件类型名称
	 * 
	 * @param emailType
	 * @return
	 */
	public static String getEmailTypeNameByEmailType(String emailType) {
		List<CodeItem> list = CodeSet.getInstance().getCachedCodeList(
				EMAIL_TYPE_KEY);
		for (CodeItem codeItem : list) {
			if (codeItem.getCode() != null
					&& codeItem.getCode().equals(emailType)) {
				return codeItem.getName();
			} else if (codeItem.getCode() == null && emailType == null) {
				return codeItem.getName();
			}
		}
		return null;
	}
}
