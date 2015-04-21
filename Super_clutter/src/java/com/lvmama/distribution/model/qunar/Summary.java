package com.lvmama.distribution.model.qunar;

import com.lvmama.distribution.util.DistributionUtil;

public class Summary {
	//private String summary;
	private String title;
	//private String teamno; //don't send this
	private String resourceid;
	private String pfunction;
	private String pcomposition;
	private String day;
	private String advanceday;
	private String advancedaytype;
	private String departure;//{始发地,必填}	如果是目的地自由行，则没有始发地
	private String arrive;
	private String arrivetype;
	private String distancetype;
	private String freetriptotraffic;
	private String freetripbacktraffic;
	private String image;
	private String recommendation;
	private String feature;
	private String visa;
	private String feeinclude;
	private String feeexclude;
	private String attention;
	private String tip;
	private String payway;
	private String istaocan;
	private String taocanadultcount;
	private String taocanchildcount;
	private String taocanroomcount;
	private String status;
	private String refunddesc;
	
	private String night;
	
	
	private static final String tip_head="本产品由上海驴妈妈兴旅国际旅行社有限公司或其他具有相关资质的合作旅行社提供并同时提供旅游接待服务<br/>";
	
	public String toPriceString(){
		StringBuffer summary = new StringBuffer();
		summary.append("<summary>");
		summary.append(DistributionUtil.buildXmlElementInCDATA("resourceid", this.resourceid));
		summary.append(DistributionUtil.buildXmlElementInCDATA("pfunction", this.pfunction));
		summary.append(DistributionUtil.buildXmlElementInCDATA("istaocan", this.istaocan));
		summary.append("</summary>");
		return summary.toString();
	}
	
	@Override
	public String toString() {
		StringBuffer summary = new StringBuffer();
		summary.append("<summary>");
		summary.append(DistributionUtil.buildXmlElementInCDATA("title", this.title));
		summary.append(DistributionUtil.buildXmlElementInCDATA("resourceid", this.resourceid));
		summary.append(DistributionUtil.buildXmlElementInCDATA("pfunction", this.pfunction));
		summary.append(DistributionUtil.buildXmlElementInCDATA("pcomposition", this.pcomposition));
		summary.append(DistributionUtil.buildXmlElementInCDATA("day", this.day));
		summary.append(DistributionUtil.buildXmlElementInCDATA("night", this.night));
		summary.append(DistributionUtil.buildXmlElementInCDATA("advanceday", this.advanceday));
		summary.append(DistributionUtil.buildXmlElementInCDATA("advancedaytype", this.advancedaytype));
		summary.append(DistributionUtil.buildXmlElementInCDATA("departure", this.departure));
		summary.append(DistributionUtil.buildXmlElementInCDATA("arrive", this.arrive));
		summary.append(DistributionUtil.buildXmlElementInCDATA("arrivetype", this.arrivetype));
		summary.append(DistributionUtil.buildXmlElementInCDATA("distancetype", this.distancetype));
		summary.append(DistributionUtil.buildXmlElementInCDATA("freetriptotraffic", this.freetriptotraffic));
		summary.append(DistributionUtil.buildXmlElementInCDATA("freetripbacktraffic", this.freetripbacktraffic));
		summary.append(DistributionUtil.buildXmlElementInCDATA("image", this.image));
		summary.append(DistributionUtil.buildXmlElementInCDATA("recommendation", this.recommendation));
		summary.append(DistributionUtil.buildXmlElementInCDATA("feature", this.feature));
		summary.append(DistributionUtil.buildXmlElementInCDATA("visa", this.visa));
		summary.append(DistributionUtil.buildXmlElementInCDATA("feeinclude", this.feeinclude));
		summary.append(DistributionUtil.buildXmlElementInCDATA("feeexclude", this.feeexclude));
		summary.append(DistributionUtil.buildXmlElementInCDATA("attention", this.attention));
		summary.append(DistributionUtil.buildXmlElementInCDATA("tip", this.tip));
		summary.append(DistributionUtil.buildXmlElementInCDATA("payway", this.payway));
		summary.append(DistributionUtil.buildXmlElementInCDATA("istaocan", this.istaocan));
		summary.append(DistributionUtil.buildXmlElementInCDATA("taocanadultcount", this.taocanadultcount));
		summary.append(DistributionUtil.buildXmlElementInCDATA("taocanchildcount", this.taocanchildcount));
		summary.append(DistributionUtil.buildXmlElementInCDATA("taocanroomcount", this.taocanroomcount));
		summary.append(DistributionUtil.buildXmlElementInCDATA("status", this.status));
		summary.append(DistributionUtil.buildXmlElementInCDATA("refunddesc", refunddesc));
		summary.append("</summary>");
		return summary.toString();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public void setPfunction(String pfunction) {
		this.pfunction = pfunction;
	}

	public void setPcomposition(String pcomposition) {
		this.pcomposition = pcomposition;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setAdvanceday(String advanceday) {
		this.advanceday = advanceday;
	}

	public void setAdvancedaytype(String advancedaytype) {
		this.advancedaytype = advancedaytype;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public void setArrivetype(String arrivetype) {
		this.arrivetype = arrivetype;
	}

	public void setDistancetype(String distancetype) {
		this.distancetype = distancetype;
	}

	public void setFreetriptotraffic(String freetriptotraffic) {
		this.freetriptotraffic = freetriptotraffic;
	}

	public void setFreetripbacktraffic(String freetripbacktraffic) {
		this.freetripbacktraffic = freetripbacktraffic;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public void setVisa(String visa) {
		this.visa = visa;
	}

	public void setFeeinclude(String feeinclude) {
		this.feeinclude = feeinclude;
	}

	public void setFeeexclude(String feeexclude) {
		this.feeexclude = feeexclude;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	public void setTip(String tip) {
		this.tip = tip_head+tip;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public void setIstaocan(String istaocan) {
		this.istaocan = istaocan;
	}

	public void setTaocanadultcount(String taocanadultcount) {
		this.taocanadultcount = taocanadultcount;
	}

	public void setTaocanchildcount(String taocanchildcount) {
		this.taocanchildcount = taocanchildcount;
	}

	public void setTaocanroomcount(String taocanroomcount) {
		this.taocanroomcount = taocanroomcount;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefunddesc() {
		return refunddesc;
	}

	public void setRefunddesc(String refunddesc) {
		this.refunddesc = refunddesc;
	}

	public void setNight(String night) {
		this.night = night;
	}		
	
}
