package com.lvmama.passport.processor.impl.client.dinosaurtown.model;

public class Order {
	private String no="";
	private String createDate="";
	private String linkMan="";
	private String linkSex="";
	private String linkMobile="";
	private String linkPhone="";
	private String linkCard="";
	private String linkFax="";
	private String linkAddr="";
    private Ticket ticket;
    
    public String toOrderXml(){
        StringBuilder buf=new StringBuilder();
    	buf.append("<Order")
		.append(" No=").append("\""+this.no+"\"")
		.append(" CreateDate=").append("\""+this.createDate+"\"")
	    .append(" LinkMan=").append("\""+this.linkMan+"\"")
	    .append(" LinkSex=").append("\""+this.linkSex+"\"")
	    .append(" LinkMobile=").append("\""+this.linkMobile+"\"")
	    .append(" LinkPhone=").append("\""+this.linkPhone+"\"")
	    .append(" LinkCard=").append("\""+this.linkCard+"\"")
	    .append(" LinkFax=").append("\""+this.linkFax+"\"")
	    .append(" LinkAddr=").append("\""+this.linkAddr+"\"")
	    .append(" >")
	    .append(this.ticket.toTicketXml())
		.append("</Order>");
        return buf.toString();
    }
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkSex() {
		return linkSex;
	}

	public void setLinkSex(String linkSex) {
		this.linkSex = linkSex;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkCard() {
		return linkCard;
	}

	public void setLinkCard(String linkCard) {
		this.linkCard = linkCard;
	}

	public String getLinkFax() {
		return linkFax;
	}

	public void setLinkFax(String linkFax) {
		this.linkFax = linkFax;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

}
