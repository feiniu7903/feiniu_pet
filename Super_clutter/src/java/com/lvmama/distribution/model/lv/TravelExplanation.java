package com.lvmama.distribution.model.lv;

import java.util.List;

import com.lvmama.comm.bee.po.distribution.DistributionProduct;
import com.lvmama.comm.bee.po.prod.ViewJourney;

public class TravelExplanation {
	
	public TravelExplanation(){}
	public TravelExplanation(DistributionProduct distributionProduct){
		this.viewList=distributionProduct.getViewJourneyList();
	}
	private List<ViewJourney> viewList;
	
	public List<ViewJourney> getViewList() {
		return viewList;
	}


	public String buildTravelExplanation(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<travelExplanation>");
		if(viewList!=null){
			xmlStr.append("<list>");
			for(ViewJourney viewjourney : viewList){
				Day day=new Day(viewjourney);
				xmlStr.append(day.buildDays());
			}
			xmlStr.append("</list>");
		}
		xmlStr.append("</travelExplanation>");
		return xmlStr.toString();
	}
	
	public void setViewList(List<ViewJourney> viewList) {
		this.viewList = viewList;
	}

}
