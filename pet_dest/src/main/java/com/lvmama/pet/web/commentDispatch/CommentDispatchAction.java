/**
 * 
 */
package com.lvmama.pet.web.commentDispatch;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;

/**
 * @author liuyi
 *
 */
@Results({
	@Result(name="listCmtsOfDestBasic",type="dispatcher",location = "/404.jsp"),
	@Result(name="listCmtsOfDestBasicDish",
			location="http://www.lvmama.com/travel/${opinyin}/dish",
			params={"statusCode", "301"},
			type = "redirect"),
	@Result(name="listCmtsOfDestBasicHotel",
			location="http://www.lvmama.com/travel/${opinyin}/hotel",
			params={"statusCode", "301"},
			type = "redirect"),
	@Result(name="listCmtsOfDestBasicTraffic",
			location="http://www.lvmama.com/travel/${opinyin}/traffic",
			params={"statusCode", "301"},
			type = "redirect"),
	@Result(name="listCmtsOfDestBasicEntertainment",
			location="http://www.lvmama.com/travel/${opinyin}/entertainment",
			params={"statusCode", "301"},
			type = "redirect"),
	@Result(name="listCmtsOfDestBasicShop",
			location="http://www.lvmama.com/travel/${opinyin}/shop",
			params={"statusCode", "301"},
			type = "redirect"),
	@Result(name="listCmtsOfDestBasicWeekendtravel",
			location="http://www.lvmama.com/travel/${opinyin}/weekendtravel",
			params={"statusCode", "301"},
			type = "redirect"),
	@Result(name="listCmtsOfDestBasicScenery",
			location="http://www.lvmama.com/travel/${opinyin}/scenery",
			params={"statusCode", "301"},
			type = "redirect"),
	@Result(name="listCmtsOfDestBasicPhoto",
			location="http://www.lvmama.com/travel/${opinyin}/photo",
			params={"statusCode", "301"},
			type = "redirect"),
	@Result(name="listCmtsOfDestBasicPlace",
			location="http://www.lvmama.com/travel/${opinyin}/place",
			params={"statusCode", "301"},
			type = "redirect")
})
public class CommentDispatchAction extends BaseAction {
	private String id;
	
	private String page;

	/**
	 * 当前显示的tab
	 */
	private String currentTab;
	
	private String opinyin;
	

	@Action("/commentdispatch/listCmtsOfDestBasic")
	public String cmtList()
	{
		if("dish".equals(currentTab)){
			return "listCmtsOfDestBasicDish";
			
		}else if("hotel".equals(currentTab)){
			return "listCmtsOfDestBasicHotel";
		}
		else if("traffic".equals(currentTab)){
			return "listCmtsOfDestBasicTraffic";
		}
		else if("entertainment".equals(currentTab)){
			return "listCmtsOfDestBasicEntertainment";
		}		
		else if("shop".equals(currentTab)){
			return "listCmtsOfDestBasicShop";
		}		
		else if("weekendtravel".equals(currentTab)){
			return "listCmtsOfDestBasicWeekendtravel";
		}
		else if("scenery".equals(currentTab)){
			return "listCmtsOfDestBasicScenery";
		}
		else if("photo".equals(currentTab)){
			return "listCmtsOfDestBasicPhoto";
		}
		else if("place".equals(currentTab)){
			return "listCmtsOfDestBasicPlace";
		}
		if(StringUtils.isEmpty(page))
		{
			page = "1";
		}
		
		return "listCmtsOfDestBasic";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}

	public String getOpinyin() {
		return opinyin;
	}

	public void setOpinyin(String opinyin) {
		this.opinyin = opinyin;
	}

}
