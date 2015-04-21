package com.lvmama.tnt.back.util.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class DataOutput extends BodyTagSupport {

	private Date date;

	private String format;

	public String getFormat() {
		return format != null ? format : "yyyy-MM-dd";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public int doStartTag() throws JspException {

		if (date != null) {
			String format = getFormat();
			SimpleDateFormat SDF = new SimpleDateFormat(format);
			JspWriter out = pageContext.getOut();
			try {
				out.print(SDF.format(date));
			} catch (IOException e) {
				throw new JspException(e);
			}
		}
		return SKIP_BODY;
	}

}
