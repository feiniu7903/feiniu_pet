package com.lvmama.passport.processor.impl.client.gmedia.model;

public class Fd {
	private String id;
	private String ne;
	private String ve;
	public String toResponseFdXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<Fd>")
			.append("<Id>")
			.append(this.id)
			.append("</Id>")
			.append("<Ne>")
			.append(this.ne)
			.append("</Ne>")
			.append("<Ve>")
			.append(this.ve)
			.append("</Ve>")
		.append("</Fd>");
		return buf.toString();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNe() {
		return ne;
	}
	public void setNe(String ne) {
		this.ne = ne;
	}
	public String getVe() {
		return ve;
	}
	public void setVe(String ve) {
		this.ve = ve;
	}
	
}
