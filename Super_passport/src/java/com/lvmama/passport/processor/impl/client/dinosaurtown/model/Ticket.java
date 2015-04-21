package com.lvmama.passport.processor.impl.client.dinosaurtown.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Ticket {
	private static final Log log = LogFactory.getLog(Ticket.class);
	private String no="";
	private String name="";
	private String visitDate="";
	private String elder="";
	private String hasElder="";
	private String man="";
	private String hasMan="";
	private String child="";
	private String hasChild="";
	private String count="";
	private String state_id="";

	public String toTicketXml(){
    	StringBuilder buf=new StringBuilder();
		buf.append("<Ticket")
		.append(" No=").append("\""+this.no+"\"")
		.append(" Name=").append("\""+this.name+"\"")
	    .append(" VisitDate=").append("\""+this.visitDate+"\"")
	    .append(" Elder=").append("\""+this.elder+"\"")
	    .append(" HasElder=").append("\""+this.hasElder+"\"")
	    .append(" Man=").append("\""+this.man+"\"")
	    .append(" HasMan=").append("\""+this.hasMan+"\"")
	    .append(" Child=").append("\""+this.child+"\"")
	    .append(" HasChild=").append("\""+this.hasChild+"\"")
	    .append(" Count=").append("\""+this.count+"\"")
	    .append(" State_id=").append("\""+this.state_id+"\"")
	    .append(" />");
		//log.info("请求信息"+buf.toString());
    	return buf.toString();
	}
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getElder() {
		return elder;
	}

	public void setElder(String elder) {
		this.elder = elder;
	}

	public String getHasElder() {
		return hasElder;
	}

	public void setHasElder(String hasElder) {
		this.hasElder = hasElder;
	}

	public String getMan() {
		return man;
	}

	public void setMan(String man) {
		this.man = man;
	}

	public String getHasMan() {
		return hasMan;
	}

	public void setHasMan(String hasMan) {
		this.hasMan = hasMan;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getHasChild() {
		return hasChild;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}
}
