package com.lvmama.tnt.user.po;

import java.io.Serializable;

public class TntFAQ implements Serializable {

	private static final long serialVersionUID = 7929383792300119937L;

	private java.lang.Long tntFAQId;
	private java.lang.String question;
	private java.lang.String answer;
	private java.util.Date publishtime;
	
	private java.lang.String beginpublishdate;
	private java.lang.String endpublishdate;
	
	public java.lang.String getEndpublishdate() {
		return endpublishdate;
	}

	public void setEndpublishdate(java.lang.String endpublishdate) {
		this.endpublishdate = endpublishdate;
	}

	public java.lang.String getBeginpublishdate() {
		return beginpublishdate;
	}

	public void setBeginpublishdate(java.lang.String beginpublishdate) {
		this.beginpublishdate = beginpublishdate;
	}

	public java.lang.Long getTntFAQId() {
		return tntFAQId;
	}

	public void setTntFAQId(java.lang.Long tntFAQId) {
		this.tntFAQId = tntFAQId;
	}

	public java.lang.String getQuestion() {
		return question;
	}

	public void setQuestion(java.lang.String question) {
		this.question = question;
	}

	public java.lang.String getAnswer() {
		return answer;
	}

	public void setAnswer(java.lang.String answer) {
		this.answer = answer;
	}

	public java.util.Date getPublishtime() {
		return publishtime;
	}

	public void setPublishTime(java.util.Date publishtime) {
		this.publishtime = publishtime;
	}

	public String toString() {
		return new StringBuilder().append("TntFAQId" + getTntFAQId())
				.append("Question" + getQuestion())
				.append("Answer" + getAnswer())
				.append("PublishTime" + getPublishtime()).toString();
	}
	
	public void trim() {
		if (question != null)
			setQuestion(question.trim());
	}	
}
