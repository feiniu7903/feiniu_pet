package com.lvmama.comm.bee.po.distribution;

import java.io.Serializable;

public class DistributionPlaceImage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1030303573362020746L;
	private String size;
	private String title;
	private String src;
	private String href;
	private String placename;
	private Long placId;

	public String buildImage() {
		StringBuilder buf = new StringBuilder();
		buf.append("<image>");
		buf.append("<size>").append(this.size).append("</size>");
		buf.append("<title><![CDATA[").append(this.title).append("]]></title>");
		buf.append("<src>").append(this.src).append("</src>");
		buf.append("<href>").append(this.href).append("</href>");
		buf.append("</image>");
		return buf.toString();
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getPlacename() {
		return placename;
	}

	public void setPlacename(String placename) {
		this.placename = placename;
	}

	public Long getPlacId() {
		return placId;
	}

	public void setPlacId(Long placId) {
		this.placId = placId;
	}

}
