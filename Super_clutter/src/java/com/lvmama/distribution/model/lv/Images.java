package com.lvmama.distribution.model.lv;

import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.util.DistributionUtil;

/**
 * 分销产品图片列表对象
 * @author lipengcheng
 *
 */
public class Images {
	/** 小图*/
	private String small;
	/** 中图*/
	private String middle;
	/** 大图*/
	private String large;

	public Images(){};
	
	public Images(String url) {
		if (url != null) {
			this.small = Constant.getInstance().getPrifix200x100Pic() + url;
//			this.middle = Constant.getInstance().getPrifix580x290Pic() + url;
			this.large = Constant.getInstance().getPrifix580x290Pic() + url;
		}
	}

	/**
	 * 构造图片信息产品XML
	 * @return
	 */
	public String buildXmlStr() {
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append(DistributionUtil.buildXmlElement("small", small));
		xmlStr.append(DistributionUtil.buildXmlElement("middle", middle));
		xmlStr.append(DistributionUtil.buildXmlElement("large", large));
		return xmlStr.toString();
	}

	//setter and getter
	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getMiddle() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}
}
