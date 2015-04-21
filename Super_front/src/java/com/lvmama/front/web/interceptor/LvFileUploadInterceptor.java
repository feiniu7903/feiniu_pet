package com.lvmama.front.web.interceptor;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

public class LvFileUploadInterceptor extends AbstractInterceptor {

	public LvFileUploadInterceptor() {
		allowedTypesSet = Collections.EMPTY_SET;
	}

	public void setAllowedTypes(String allowedTypes) {
		this.allowedTypes = allowedTypes;
		allowedTypesSet = getDelimitedValues(allowedTypes);
	}

	public void setMaximumSize(Long maximumSize) {
		this.maximumSize = maximumSize;
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		if (!(request instanceof MultiPartRequestWrapper)) {
			if (log.isDebugEnabled()) {
				ActionProxy proxy = invocation.getProxy();
				log.debug(getTextMessage(
						"struts.messages.bypass.request",
						new Object[] { proxy.getNamespace(),
								proxy.getActionName() }, ActionContext
								.getContext().getLocale()));
			}
			return invocation.invoke();
		}
		Object action = invocation.getAction();
		ValidationAware validation = null;
		if (action instanceof ValidationAware)
			validation = (ValidationAware) action;
		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
		if (multiWrapper.hasErrors()) {
			String error;
			for (Iterator errorIter = multiWrapper.getErrors().iterator(); errorIter
					.hasNext(); log.error(error)) {
				error = (String) errorIter.next();
				if (validation != null)
					validation.addActionError(error);
			}

		}
		Map parameters = ac.getParameters();
		Enumeration fileParameterNames = multiWrapper.getFileParameterNames();
		do {
			if (fileParameterNames == null
					|| !fileParameterNames.hasMoreElements())
				break;
			String inputName = (String) fileParameterNames.nextElement();
			String contentType[] = multiWrapper.getContentTypes(inputName);
			if (isNonEmpty(contentType)) {
				String fileName[] = multiWrapper.getFileNames(inputName);
				if (isNonEmpty(fileName)) {
					File files[] = multiWrapper.getFiles(inputName);
					Map<String, Object> parm = filterZeroFiles(files, fileName, contentType);
					files = (File[]) parm.get("files");
					fileName = (String[]) parm.get("fileName");
					contentType = (String[]) parm.get("contentType");
					
					if (files != null) {
						int index = 0;
						while (index < files.length) {
							if (acceptFile(files[index], contentType[index],
									inputName, validation, ac.getLocale())) {
								parameters.put(inputName, files);
								parameters.put(
										(new StringBuilder()).append(inputName)
												.append("ContentType")
												.toString(), contentType);
								parameters.put(
										(new StringBuilder()).append(inputName)
												.append("FileName").toString(),
										fileName);
							}
							index++;
						}
					}
				} else {
					log.error(getTextMessage("struts.messages.invalid.file",
							new Object[] { inputName }, ActionContext
									.getContext().getLocale()));
				}
			} else {
				log.error(getTextMessage(
						"struts.messages.invalid.content.type",
						new Object[] { inputName }, ActionContext.getContext()
								.getLocale()));
			}
		} while (true);
		String result = invocation.invoke();
		for (fileParameterNames = multiWrapper.getFileParameterNames(); fileParameterNames != null
				&& fileParameterNames.hasMoreElements();) {
			String inputValue = (String) fileParameterNames.nextElement();
			File file[] = multiWrapper.getFiles(inputValue);
			int index = 0;
			while (index < file.length) {
				File currentFile = file[index];
				log.info(getTextMessage("struts.messages.removing.file",
						new Object[] { inputValue, currentFile }, ActionContext
								.getContext().getLocale()));
				if (currentFile != null && currentFile.isFile())
					currentFile.delete();
				index++;
			}
		}

		return result;
	}

	protected boolean acceptFile(File file, String contentType,
			String inputName, ValidationAware validation, Locale locale) {
		boolean fileIsAcceptable = false;
		if (file == null) {
			String errMsg = getTextMessage("struts.messages.error.uploading",
					new Object[] { inputName }, locale);
			if (validation != null)
				validation.addFieldError(inputName, errMsg);
			log.error(errMsg);
		} else if (maximumSize != null
				&& maximumSize.longValue() < file.length()) {
			String errMsg = getTextMessage(
					"struts.messages.error.file.too.large",
					new Object[] {
							inputName,
							file.getName(),
							(new StringBuilder()).append("")
									.append(file.length()).toString() }, locale);
			if (validation != null)
				validation.addFieldError(inputName, errMsg);
			log.error(errMsg);
		} else if (!allowedTypesSet.isEmpty()
				&& !containsItem(allowedTypesSet, contentType)) {
			String errMsg = getTextMessage(
					"struts.messages.error.content.type.not.allowed",
					new Object[] { inputName, file.getName(), contentType },
					locale);
			if (validation != null)
				validation.addFieldError(inputName, errMsg);
			log.error(errMsg);
		} else {
			fileIsAcceptable = true;
		}
		return fileIsAcceptable;
	}
	
	private Map<String, Object> filterZeroFiles(File[] files, String[] fileName, String[] contentType) {
		List<File> filesList = new ArrayList<File>();
		List<String> fileNameList = new ArrayList<String>();
		List<String> contentTypeList = new ArrayList<String>();
		
		for (int i = 0 ; i < files.length ; i++) {
			if (StringUtils.isNotEmpty(fileName[i]) && null != files[i] && files[i].length() > 0) {
				filesList.add(files[i]);
				fileNameList.add(fileName[i]);
				contentTypeList.add(contentType[i]);
			}
		}
		
		
		files = new File[filesList.size()];
		for (int i = 0 ; i < files.length ; i++) {
			files[i] = filesList.get(i);
		}
		fileName = new String[fileNameList.size()];
		for (int i = 0 ; i < fileName.length ; i++) {
			fileName[i] = fileNameList.get(i);
		}
		contentType = new String[contentTypeList.size()];
		for (int i = 0 ; i < contentType.length ; i++) {
			contentType[i] = contentTypeList.get(i);
		}
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("files", files);
		parms.put("fileName", fileNameList.toArray(fileName));
		parms.put("contentType", contentTypeList.toArray(contentType));
		
		return parms;
	}

	private static boolean containsItem(Collection itemCollection, String key) {
		return itemCollection.contains(key.toLowerCase());
	}

	private static Set getDelimitedValues(String delimitedString) {
		Set delimitedValues = new HashSet();
		if (delimitedString != null) {
			StringTokenizer stringTokenizer = new StringTokenizer(
					delimitedString, ",");
			do {
				if (!stringTokenizer.hasMoreTokens())
					break;
				String nextToken = stringTokenizer.nextToken().toLowerCase()
						.trim();
				if (nextToken.length() > 0)
					delimitedValues.add(nextToken);
			} while (true);
		}
		return delimitedValues;
	}

	private static boolean isNonEmpty(Object objArray[]) {
		boolean result = false;
		for (int index = 0; index < objArray.length && !result; index++)
			if (objArray[index] != null)
				result = true;

		return result;
	}

	private String getTextMessage(String messageKey, Object args[],
			Locale locale) {
		if (args == null || args.length == 0)
			return LocalizedTextUtil.findText(getClass(), messageKey, locale);
		else
			return LocalizedTextUtil.findText(getClass(), messageKey, locale,
					"no.message.found", args);
	}

	private static final long serialVersionUID = 0xbde0a48d516698d2L;
	protected static final Log log = LogFactory
			.getLog(LvFileUploadInterceptor.class);
	private static final String DEFAULT_DELIMITER = ",";
	private static final String DEFAULT_MESSAGE = "no.message.found";
	protected Long maximumSize;
	protected String allowedTypes;
	protected Set allowedTypesSet;

}
