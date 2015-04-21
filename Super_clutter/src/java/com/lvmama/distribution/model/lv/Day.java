package com.lvmama.distribution.model.lv;

import java.util.List;

import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewJourneyTips;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.distribution.util.DistributionUtil;

public class Day {
	
	public Day(){}
	
	public Day(ViewJourney viewjourney){
		this.viewjourney=viewjourney;
	}
	private ViewJourney viewjourney=new ViewJourney();


    public String buildDays(){
    	StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<day>");
		xmlStr.append(DistributionUtil.buildXmlElement("seq", viewjourney.getSeq()));
		xmlStr.append(DistributionUtil.buildXmlElement("title", viewjourney.getTitle()));
		xmlStr.append(DistributionUtil.buildXmlElement("content", viewjourney.getContent()));
		xmlStr.append(DistributionUtil.buildXmlElement("dinner", viewjourney.getDinner()));
		xmlStr.append(DistributionUtil.buildXmlElement("hotel", viewjourney.getHotel()));
		xmlStr.append("<images>");
		List<ComPicture> pictureList=viewjourney.getJourneyPictureList();
		if(pictureList!=null){
			for(ComPicture picture : pictureList){
				xmlStr.append(DistributionUtil.buildXmlElement("url", picture.getPictureUrl()));
			}
		}
		xmlStr.append("</images>");
		xmlStr.append("<tipList>");
		List<ViewJourneyTips> viewJourneyTips=viewjourney.getJourneyTipsList();
		if(viewJourneyTips!=null){
			for(ViewJourneyTips tips : viewJourneyTips){
				Tip tip=new Tip(tips);
				xmlStr.append("<tip>");
				xmlStr.append(tip.buildTipsXml());
				xmlStr.append("</tip>");
			}
		}
		xmlStr.append("</tipList>");
		xmlStr.append("</day>");
		return xmlStr.toString();
    }



}