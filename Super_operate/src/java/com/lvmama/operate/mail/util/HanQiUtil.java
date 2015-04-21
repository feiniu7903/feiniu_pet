package com.lvmama.operate.mail.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hanqinet.edm.ws.prnasia.dto.upload.Task;
import com.hanqinet.edm.ws.prnasia.dto.upload.Template;
import com.lvmama.comm.utils.StringUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 汉启邮件发送工具
 * 
 * @author likun
 * 
 */
public class HanQiUtil {

	/**
	 * 创建一个任务对象
	 * 
	 * @param taskName
	 *            任务名称
	 * @param emailColumnName
	 *            email地址上传的列标题,以","分隔,列标题中必须包含 email 字段
	 *  @param taskGroupId
	 *  	任务组id
	 * @return
	 */
	public static Task createTask(String taskName, String emailColumnName,String taskGroupId) {
		Task task = new Task();
		task.setEmailColumnName(emailColumnName);
		task.setTaskGroupId(taskGroupId);
		task.setTaskName(taskName);
		return task;
	}

	/**
	 * 创建一个模板对象
	 * 
	 * @param fromName
	 *            发件人名称
	 * @param senderEmail
	 *            发送人邮箱
	 * @param subject
	 *            邮件标题
	 * @param tempName
	 *            模板名称
	 * @param templateContent
	 *            模板内容
	 * @return
	 */
	public static Template createTemplate(String fromName, String senderEmail,
			String subject, String tempName, String templateContent) {
		Template template = new Template();
		template.setTemplateName(tempName);
		template.setSenderChinese(fromName);
		template.setSenderEmail(senderEmail);
		template.setSubject(subject);
		template.setBodyCharset(HanQiResources.get("hanqiBodyCharset"));
		template.setBodyEncoding(HanQiResources.get("hanqiBodyEncoding"));
		// 去掉特殊字符
		template.setTemplateContent(chopWhitespace(templateContent));
		template.setToChinese("$$fullname$$");
		template.setTemplateType("1");
		return template;
	}

	/**
	 * base64编码
	 * 
	 * @param string
	 * @param charsetName
	 *            编码字符编码格式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String base64Encode(String string, String charsetName)
			throws UnsupportedEncodingException {
		if (string == null || "".endsWith(string.trim())) {
			return null;
		}
		return new BASE64Encoder().encode(string.getBytes(charsetName));
	}

	/**
	 * base64解码
	 * 
	 * @param string
	 * @param charsetName
	 *            解码字符编码格式
	 * @return
	 * @throws IOException
	 */
	public static String base64Decode(String string, String charsetName)
			throws IOException {
		if (string == null || "".endsWith(string.trim())) {
			return null;
		}
		return new String(new BASE64Decoder().decodeBuffer(string), charsetName);
	}

	public static final String chopWhitespace(String str) {
		if (StringUtil.isEmptyString(str)) {
			return "";
		}
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			int ci = ch;
			if (9 == ci || 10 == ci || 13 == ci || 32 <= ci
					&& !Character.isISOControl(ci)) {
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static Collection<Object> disposeEmailData(Collection<? extends Object> obj) {
		if (obj != null) {
			List<Object> list = new ArrayList<Object>();
			for (Object object : obj) {
				if (object != null) {
					list.add(object.toString().replaceAll(",", "、"));
				} else {
					list.add(object);
				}
			}
			return list;
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		/*String aString = base64Encode("likun_557@163.com",
				HanQiResources.get("hanqiDefaultCharsetName"));
		System.out.println(aString);
		System.out.println(base64Decode(aString,
				HanQiResources.get("hanqiDefaultCharsetName")));*/
		List<Object> list = new ArrayList<Object>();
		list.add("上海，我去,你好啊,嘿嘿,;范德萨");
		list.add("把范德萨广泛地");
		System.out.println(disposeEmailData(list));
	}

}
