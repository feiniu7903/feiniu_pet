package com.lvmama.distribution.model.ckdevice;

import com.lvmama.comm.bee.po.prod.ViewJourneyTips;

public class Tip {
	private String tipTitle;
	private String tipContent;
	public Tip(ViewJourneyTips tips) {
		this.tipTitle = tips.getTipTitle();
		this.tipContent = tips.getTipContent();
	}
	
	public String getTipTitle() {
		return tipTitle;
	}
	public void setTipTitle(String tipTitle) {
		this.tipTitle = tipTitle;
	}
	public String getTipContent() {
		return tipContent;
	}
	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}
	
}
