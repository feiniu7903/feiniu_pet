package com.lvmama.distribution.model.qunar;

import com.lvmama.distribution.util.DistributionUtil;

public class Day {
//	private String days;
	private String /*day,*/ daynum, daytitle;
	private String sightimage;
	private String daydescription;
	private String daytraffic;
	private String meals;
	
	@Override
	public String toString() {
		StringBuffer day = new StringBuffer();
		day.append("<day daynum=\""+daynum+"\" daytitle=\""+daytitle+"\">");
		day.append(DistributionUtil.buildXmlElementInCDATA("sightimage", this.sightimage));
		day.append(DistributionUtil.buildXmlElementInCDATA("daydescription", this.daydescription));
		day.append(DistributionUtil.buildXmlElementInCDATA("daytraffic", this.daytraffic));
		day.append("<dayhotelstar starname=\"\" stardesc=\"\" />");
		day.append(DistributionUtil.buildXmlElementInCDATA("meals", this.meals));
		day.append("</day>");
		return day.toString();
	}

	public void setDaynum(String daynum) {
		this.daynum = daynum;
	}

	public void setDaytitle(String daytitle) {
		this.daytitle = daytitle;
	}

	public void setSightimage(String sightimage) {
		this.sightimage = sightimage;
	}

	public void setDaydescription(String daydescription) {
		this.daydescription = daydescription;
	}

	public void setDaytraffic(String daytraffic) {
		this.daytraffic = daytraffic;
	}

	public void setMeals(String meals) {
		this.meals = meals;
	}
	
	
}
