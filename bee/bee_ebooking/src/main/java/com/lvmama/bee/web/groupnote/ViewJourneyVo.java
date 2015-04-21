package com.lvmama.bee.web.groupnote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.utils.DateUtil;

public class ViewJourneyVo {
	private String visitTimeDesc;
	private String titleDesc;
	private String contentDesc;
	private String dinnerDesc;
	private String hotelDesc;

	private String[] visitTime;
	private String[] title;
	private String[] content;
	private String[] dinner;
	private String[] hotel;
	
	public ViewJourneyVo() {
		
	};

	public ViewJourneyVo(ViewJourney viewJourney, Date visitTime) {
		if (null != viewJourney && null != visitTime) {
			this.visitTimeDesc = DateUtil.getFormatDate(DateUtil.dsDay_Date(
					visitTime, (viewJourney.getSeq().intValue() - 1)),
					"yyyy-MM-dd");
			this.titleDesc = viewJourney.getTitle();
			this.contentDesc = viewJourney.getContent();
			this.dinnerDesc = viewJourney.getDinner();
			this.hotelDesc = viewJourney.getHotel();
		}
	}

	public List<ViewJourneyVo> getViewJourneyList() {
		List<ViewJourneyVo> list = new ArrayList<ViewJourneyVo>();
		if (null == title) {
			return null;
		}
		for (int i = 0; i < title.length; i++) {
			ViewJourneyVo v = new ViewJourneyVo();
			if (null != visitTime) {
				v.setVisitTimeDesc(visitTime[i]);
			}
			if (null != title) {
				v.setTitleDesc(title[i]);
			}
			if (null != content) {
				v.setContentDesc(content[i]);
			}
			if (null != dinner) {
				v.setDinnerDesc(dinner[i]);
			}
			if (null != hotel) {
				v.setHotelDesc(hotel[i]);
			}
			list.add(v);
		}
		return list;
	}

	public String getVisitTimeDesc() {
		return visitTimeDesc;
	}

	public void setVisitTimeDesc(String visitTimeDesc) {
		this.visitTimeDesc = visitTimeDesc;
	}

	public String getTitleDesc() {
		return titleDesc;
	}

	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}

	public String getContentDesc() {
		return contentDesc;
	}

	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}

	public String getDinnerDesc() {
		return dinnerDesc;
	}

	public void setDinnerDesc(String dinnerDesc) {
		this.dinnerDesc = dinnerDesc;
	}

	public String getHotelDesc() {
		return hotelDesc;
	}

	public void setHotelDesc(String hotelDesc) {
		this.hotelDesc = hotelDesc;
	}

	public String[] getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String[] visitTime) {
		this.visitTime = visitTime;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public String[] getContent() {
		return content;
	}

	public void setContent(String[] content) {
		this.content = content;
	}

	public String[] getDinner() {
		return dinner;
	}

	public void setDinner(String[] dinner) {
		this.dinner = dinner;
	}

	public String[] getHotel() {
		return hotel;
	}

	public void setHotel(String[] hotel) {
		this.hotel = hotel;
	}

}
