package com.lvmama.passport.processor.impl.client.gmedia.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Gts {
	private static final Log log = LogFactory.getLog(Gts.class);
	public static final String CHILD="儿童数";
	public static final String ADULT="成人数";
	private List<Gt> gt=new ArrayList<Gt>();
    private String cn;
    private String tn;
	public String makePersonXml(List<Gt> list){
		StringBuilder buf=new StringBuilder();
		buf.append("<Gts>");
		 for(Gt gt:list){
			 buf.append("<Gt>");
			 buf.append("<Cn>").append(gt.getCn()).append("</Cn>") ;
			 buf.append("<Tn>").append(gt.getTn()).append("</Tn>") ;
			 buf.append("</Gt>") ;
		 }
		buf.append("</Gts>");
		log.info(" Update Person Xml:"+buf.toString());
		return buf.toString();
	}
	public String makePersonXml(){
		StringBuilder buf=new StringBuilder();
		buf.append("<Gts>");

			 buf.append("<Gt>");
			 buf.append("<Cn>").append("成人数").append("</Cn>") ;
			 buf.append("<Tn>").append("0").append("</Tn>") ;
			 buf.append("</Gt>") ;
			 
			 buf.append("<Gt>");
			 buf.append("<Cn>").append("儿童数").append("</Cn>") ;
			 buf.append("<Tn>").append("0").append("</Tn>") ;
			 buf.append("</Gt>") ;
			 
//			 buf.append("<Gt>");
//			 buf.append("<Cn>").append("学生数").append("</Cn>") ;
//			 buf.append("<Tn>").append("0").append("</Tn>") ;
//			 buf.append("</Gt>") ;
//		 
//			 buf.append("<Gt>");
//			 buf.append("<Cn>").append("老人数").append("</Cn>") ;
//			 buf.append("<Tn>").append("0").append("</Tn>") ;
//			 buf.append("</Gt>") ;
////			 
//			 buf.append("<Gt>");
//			 buf.append("<Cn>").append("军人数").append("</Cn>") ;
//			 buf.append("<Tn>").append("0").append("</Tn>") ;
//			 buf.append("</Gt>") ;
		buf.append("</Gts>");
		log.info("Update Person Xml:"+buf.toString());
		return buf.toString();
	}
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	public List<Gt> getGt() {
		return gt;
	}
	public void setGt(List<Gt> gt) {
		this.gt = gt;
	}

	
}
