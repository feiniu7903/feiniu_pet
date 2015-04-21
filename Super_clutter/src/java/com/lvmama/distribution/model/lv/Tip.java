package com.lvmama.distribution.model.lv;

import com.lvmama.comm.bee.po.prod.ViewJourneyTips;
import com.lvmama.distribution.util.DistributionUtil;

public class Tip {
	private ViewJourneyTips tips=new ViewJourneyTips();
	
	public Tip(ViewJourneyTips tips) {
		this.tips=tips;
	}
	
	public String buildTipsXml(){
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(DistributionUtil.buildXmlElement("tipTitle", this.tips.getTipTitle()));
		xmlStr.append(DistributionUtil.buildXmlElement("tipContent", this.tips.getTipContent()));
		return xmlStr.toString();
	}
	
}
