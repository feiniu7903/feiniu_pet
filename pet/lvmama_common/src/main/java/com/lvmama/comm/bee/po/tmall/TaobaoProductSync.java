package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TaobaoProductSync implements Serializable {
	private static final long serialVersionUID = -4701708758576020320L;
	
	private Long id;
	private String tbType;
	private String tbTicketType;
	private Long tbItemId;
	private Long tbCid;
	private String tbTitle;
	private Date tbModified;
	private String tbAuctionStatus;
	private Long productId;
	private List<TaobaoTravelCombo> travelCombos;
	private List<TaobaoTicketSku> ticketSkus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTbType() {
		return tbType;
	}

	public void setTbType(String tbType) {
		this.tbType = tbType;
	}

	public String getTbTicketType() {
		return tbTicketType;
	}

	public void setTbTicketType(String tbTicketType) {
		this.tbTicketType = tbTicketType;
	}

	public Long getTbItemId() {
		return tbItemId;
	}

	public void setTbItemId(Long tbItemId) {
		this.tbItemId = tbItemId;
	}

	public Long getTbCid() {
		return tbCid;
	}

	public void setTbCid(Long tbCid) {
		this.tbCid = tbCid;
	}

	public String getTbTitle() {
		return tbTitle;
	}

	public void setTbTitle(String tbTitle) {
		this.tbTitle = tbTitle;
	}

	public Date getTbModified() {
		return tbModified;
	}

	public void setTbModified(Date tbModified) {
		this.tbModified = tbModified;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTbAuctionStatus() {
		return tbAuctionStatus;
	}

	public void setTbAuctionStatus(String tbAuctionStatus) {
		this.tbAuctionStatus = tbAuctionStatus;
	}

	public List<TaobaoTravelCombo> getTravelCombos() {
		return travelCombos;
	}

	public void setTravelCombos(List<TaobaoTravelCombo> travelCombos) {
		this.travelCombos = travelCombos;
	}

	public List<TaobaoTicketSku> getTicketSkus() {
		return ticketSkus;
	}

	public void setTicketSkus(List<TaobaoTicketSku> ticketSkus) {
		this.ticketSkus = ticketSkus;
	}


}
