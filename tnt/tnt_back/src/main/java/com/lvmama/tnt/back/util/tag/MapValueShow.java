package com.lvmama.tnt.back.util.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

@SuppressWarnings("serial")
public class MapValueShow extends BodyTagSupport {

	private Map<Object, Object> map;

	public Map<Object, Object> getMap() {
		return map;
	}

	public void setMap(Map<Object, Object> map) {
		this.map = map;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	private Object key;

	@Override
	public int doStartTag() throws JspException {
		if (map != null) {
			JspWriter out = pageContext.getOut();
			try {
				Object object = map.get(key);
				if (object == null && key != null)
					object = map.get(key.toString());
				out.print(object != null ? object : "");
			} catch (IOException e) {
				throw new JspException(e);
			}
		}
		return SKIP_BODY;
	}

}
