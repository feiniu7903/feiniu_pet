package com.lvmama.distribution.model.qihoo;

import com.lvmama.distribution.util.DistributionUtil;

public class QiHooNote {
	private String title;
	private String authorname;
	private String authorpic;
	private String href;
	private String outline;
	private String viewcount;
	public QiHooNote(){}
	public QiHooNote(String title,String authorname,String authorpic, String href,String outline,String viewcount){
		this.title=title;
		this.authorname=authorname;
		this.authorpic=authorpic;
		this.href=href;
		this.outline=outline;
		this.viewcount=viewcount;
	}
	
	public String buildNoteXml(){
		StringBuilder buf = new StringBuilder();
		buf.append("<note>");
		buf.append(DistributionUtil.buildXmlElementInCDATA("title", this.title));
		buf.append(DistributionUtil.buildXmlElementInCDATA("authorname", this.authorname));
		buf.append(DistributionUtil.buildXmlElement("authorpic", this.authorpic));
		buf.append(DistributionUtil.buildXmlElement("href", this.href));
		buf.append(DistributionUtil.buildXmlElementInCDATA("outline",this.outline));
		buf.append(DistributionUtil.buildXmlElement("viewcount",this.viewcount));
		buf.append("</note>");
		return buf.toString();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthorname() {
		return authorname;
	}
	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}
	public String getAuthorpic() {
		return authorpic;
	}
	public void setAuthorpic(String authorpic) {
		this.authorpic = authorpic;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getOutline() {
		return outline;
	}
	public void setOutline(String outline) {
		this.outline = outline;
	}
	public String getViewcount() {
		return viewcount;
	}
	public void setViewcount(String viewcount) {
		this.viewcount = viewcount;
	}
}
