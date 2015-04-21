package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.utils.ReplaceEnter;
import com.lvmama.comm.vo.Constant;

public class ProdJourney implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2059002192259158537L;

	private String trafficDesc;

	private String placeDesc;
	/**
	 * add by zzq
	 */
	private List<String> prodTargetId;
	/**
	 * @author yuzhibing
	 */
	private List<Place> PlaceList;

	private Long journeyId;

	private Long seq;

	private String title;

	private String content;

	private Long pageId;

	private String dinner;

	private String hotel;

	private String traffic;
	
	private Long productId;
	
	/**
	 * 行程小贴士列表
	 */
	private List<ProdJourneyTip> journeyTipsList;
	/**
	 * 行程图片列表
	 */
	private List<ComPicture> journeyPictureList;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

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

	public String getContentBr() {
		return ReplaceEnter.replaceEnterRn(content, null);
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
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

	public List<String> getProdTargetId() {
		return prodTargetId;
	}

	public void setProdTargetId(List<String> prodTargetId) {
		this.prodTargetId = prodTargetId;
	}

	public List<Place> getPlaceList() {
		return PlaceList;
	}

	public void setPlaceList(List<Place> PlaceList) {
		this.PlaceList = PlaceList;
	}

	public String getTrafficDesc() {
		this.trafficDesc = "";
		if (StringUtils.isNotBlank(this.traffic)) {
			this.traffic = this.traffic.trim();
			String[] arr = this.traffic.split(",");
			for (String str : arr) {
				this.trafficDesc += "," + Constant.TRAFFIC_TYPE.getCnName(str.trim());
			}
			this.trafficDesc=this.trafficDesc.substring(1);
		}
		return trafficDesc;
	}

	public void setTrafficDesc(String trafficDesc) {
		this.trafficDesc = trafficDesc;
	}

	public String getPlaceDesc() {
		return placeDesc;
	}

	public void setPlaceDesc(String placeDesc) {
		this.placeDesc = placeDesc;
	}

	public List<ProdJourneyTip> getJourneyTipsList() {
		return journeyTipsList;
	}

	public void setJourneyTipsList(List<ProdJourneyTip> journeyTipsList) {
		this.journeyTipsList = journeyTipsList;
	}

	public List<ComPicture> getJourneyPictureList() {
		return journeyPictureList;
	}

	public void setJourneyPictureList(List<ComPicture> journeyPictureList) {
		this.journeyPictureList = journeyPictureList;
	}
	
}