package com.lvmama.distribution.model.qunar;


public class ProductDetail {
	private Summary summary;
	private Days days;

	private StringBuffer xml = new StringBuffer();

	private static String replaceEnter(String s){
		String a = s.replaceAll("\n", "<br>");
		String b = a.replaceAll("(< /br>)|(</ br>)|(</br>)|(<br>)|(<br >)|(<br />)|(<br/>)", "<br>");
		String c = b.replaceAll("(<br><br>)", "<br>");
		return c;
	}
	
	@Override
	public String toString() {
		xml.append("<route>");
		xml.append(summary.toString());
		xml.append(days.toString());
		xml.append("</route>");
		
		String xmlstr = replace(xml.toString());
		xmlstr = replaceEnter(xmlstr); 
		return xmlstr;
	}

	private String replace(String xml) {
		xml = xml.replaceAll("10106060", "4008-737-727 转1138"); //lvmama客服电话 转 成去哪儿的客服电话
		return xml;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public void setDays(Days days) {
		this.days = days;
	}
}
