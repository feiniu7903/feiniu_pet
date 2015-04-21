package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

/**行程小贴士实体类
 * @author zx
 *
 */
public class ViewJourneyTips implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**小贴士的ID*/
	private Long tipId;
	
	/**小贴士的标题*/
	private String tipTitle;
	
	/**小贴士的内容*/
	private String tipContent;

	/**小贴士所属的行程ID*/
	private Long journeyId;

	public Long getTipId() {
		return tipId;
	}

	public void setTipId(Long tipId) {
		this.tipId = tipId;
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

	public Long getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Long journeyId) {
		this.journeyId = journeyId;
	}
}
