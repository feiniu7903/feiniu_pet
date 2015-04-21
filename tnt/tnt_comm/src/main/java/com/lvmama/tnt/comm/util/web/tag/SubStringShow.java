package com.lvmama.tnt.comm.util.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class SubStringShow extends BodyTagSupport {

	private String value;

	private String fillWhat;

	private Integer maxLength;

	@Override
	public int doStartTag() throws JspException {
		if (value != null) {
			if (value.length() > getMaxLength()) {
				value = value.substring(0, getMaxLength()) + getFillWhat();
			}
			JspWriter out = pageContext.getOut();
			try {
				out.print(value);
			} catch (IOException e) {
				throw new JspException(e);
			}
		}
		return SKIP_BODY;
	}

	public Integer getMaxLength() {
		return maxLength != null ? maxLength : 18;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFillWhat() {
		return fillWhat != null ? fillWhat : "...";
	}

	public void setFillWhat(String fillWhat) {
		this.fillWhat = fillWhat;
	}

}
