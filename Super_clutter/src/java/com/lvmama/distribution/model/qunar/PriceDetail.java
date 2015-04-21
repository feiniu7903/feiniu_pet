package com.lvmama.distribution.model.qunar;


public class PriceDetail {
	private Package_ package_;
	private Summary summary;
	

	@Override
	public String toString() {
		StringBuilder route = new StringBuilder();
		route.append("<route>");
		route.append(summary.toPriceString());
		route.append(package_.toString());
		route.append("</route>");
		return route.toString();
	}


	public Package_ getPackage_() {
		return package_;
	}


	public void setPackage_(Package_ package_) {
		this.package_ = package_;
	}


	public Summary getSummary() {
		return summary;
	}


	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	

}
