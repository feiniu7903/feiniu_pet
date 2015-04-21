package com.lvmama.comm.bee.po.duijie;

import java.io.Serializable;

/**
 * 供应商行程 
 * @author yanzhirong
 */
public class SupplierViewJourney implements Serializable {

	private static final long serialVersionUID = -1159313953953557914L;
	
	/** 主键id*/
	private Long journeyId;
	
	/** 序列，天数*/
	private Long seq;
	
	/** 标题*/
	private String title;
	
	/** 内容*/
	private String content;
	
	/** 供应商产品外键id*/
	private Long productId;
	
	/** 餐饮*/
	private String dinner;
	
	/** 住宿 */
	private String hotel;
	
	/** 交通*/
	private String traffic;

	public Long getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Long journeyId) {
		this.journeyId = journeyId;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getDinner() {
		return dinner;
	}

	public void setDinner(String dinner) {
		this.dinner = dinner;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
}
