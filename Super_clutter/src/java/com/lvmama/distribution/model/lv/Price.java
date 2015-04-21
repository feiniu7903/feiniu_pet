package com.lvmama.distribution.model.lv;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.distribution.util.DistributionUtil;

/**
 * 分销--时间价格对象
 * @author lipengcheng
 *
 */
public class Price {
	private TimePrice timePrice;
	public Price(TimePrice timePrice){
		this.timePrice = timePrice;
	}

	/**
	 * 构造时候价格节点
	 * @return
	 */
	public String buildPriceStr(){
		if(timePrice.getAheadHour() != null){
			StringBuilder xmlStr = new StringBuilder();
			xmlStr.append("<price>");
			xmlStr.append(DistributionUtil.buildXmlElement("date", DateUtil.getFormatDate(timePrice.getSpecDate(),"yyyy-MM-dd")));
			xmlStr.append(DistributionUtil.buildXmlElement("sellPrice",timePrice.getSellPriceFloat()));
			xmlStr.append(DistributionUtil.buildXmlElement("marketPrice",timePrice.getMarketPriceFloat()));
			xmlStr.append(DistributionUtil.buildXmlElement("stock",timePrice.getDayStock()));
			xmlStr.append(DistributionUtil.buildXmlElement("aheadHour", PriceUtil.convertToHoursForDistribution(timePrice.getAheadHour())));
			xmlStr.append("</price>");
			return xmlStr.toString();
		}else{
			return "";
		}
	}
}
