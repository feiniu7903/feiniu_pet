package com.lvmama.distribution.model.ckdevice;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyTips;

public class Day {
	
	private String seq;
	private String title;
	private String content;
	private String dinner;
	private String hotel;
	
	private List<String> images;
	
	private List<Tip> tipList;
	
	public Day(){}
	
	public Day(ViewJourney viewjourney){
		List<ViewJourneyTips> viewJourneyTips=viewjourney.getJourneyTipsList();
		this.seq = String.valueOf(viewjourney.getSeq());
		this.title = viewjourney.getTitle(); 
		this.content = viewjourney.getContent();
		this.dinner = viewjourney.getDinner();
		this.hotel = viewjourney.getHotel();
		if(viewJourneyTips!=null){
			this.tipList = new ArrayList<Tip>();
			for(ViewJourneyTips tips : viewJourneyTips){
				Tip tip=new Tip(tips);
				tipList.add(tip);
			}
		}
	}


	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
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

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<Tip> getTipList() {
		return tipList;
	}

	public void setTipList(List<Tip> tipList) {
		this.tipList = tipList;
	}

}