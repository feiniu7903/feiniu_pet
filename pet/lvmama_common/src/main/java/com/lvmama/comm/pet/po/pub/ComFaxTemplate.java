package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

public class ComFaxTemplate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2832012483126419827L;

	private String templateId;

	private String templateName;

	private String fileName;

	private boolean checked = false;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId == null ? null : templateId.trim();
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName == null ? null : templateName.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}