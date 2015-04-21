package com.lvmama.passport.processor.impl.client.gmedia.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Mo {
	private static final Log log = LogFactory.getLog(Mo.class);
	private List<Fd> fds;
	private String asy;
	public String getAsy() {
		return asy;
	}
	public void setAsy(String asy) {
		this.asy = asy;
	}
	public String toResponseMoXml() {
		StringBuilder buf = new StringBuilder();
		for (Fd fd : this.fds) {
			buf.append(fd.toResponseFdXml());
		}
		return buf.toString();
	}
	public String makePersonXml(){
		StringBuilder buf=new StringBuilder();
		buf.append("<Mo>");

			 buf.append("<Fd>");
				 buf.append("<Id>").append("1").append("</Id>") ;
				 buf.append("<Ne>").append("成人数").append("</Ne>") ;
				 buf.append("<Ve>").append("0").append("</Ve>") ;
			 buf.append("</Fd>") ;
			 
			 buf.append("<Fd>");
				 buf.append("<Id>").append("2").append("</Id>") ;
				 buf.append("<Ne>").append("儿童数").append("</Ne>") ;
				 buf.append("<Ve>").append("0").append("</Ve>") ;
			 buf.append("</Fd>") ;
			 
		buf.append("</Mo>");
		log.info("Update Person Xml:"+buf.toString());
		return buf.toString();
	}
	public List<Fd> getFds() {
		return fds;
	}
	public void setFds(List<Fd> fds) {
		this.fds = fds;
	}
	public boolean isOnline(){
		return "0".equalsIgnoreCase(this.asy.trim());
	}
}
