package com.lvmama.front.web.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.TravelTips;
import com.lvmama.comm.bee.service.view.ViewTravelTipsService;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.vo.Constant;

@Results( {
	@Result(name = "travelTipsContent", location = "/WEB-INF/pages/product/newdetail/buttom/travelTips.ftl", type = "freemarker")
	}
)
public class ProductTravelTipsAction extends ProductBaseAction{

	private static final long serialVersionUID = 1L;
	private TravelTips travelTips; 
	/**
	 * 附件类
	 */
	private ComAffix affix = new ComAffix();
	private ViewTravelTipsService viewTravelTipsService;
	private ComAffixService comAffixService;
	
	@Action("/product/getTravelTips")
	public String getTravelTipsDetail(){
		travelTips = viewTravelTipsService.selectByTravelTipsId(travelTips.getTravelTipsId());
		if(travelTips.getContent() != null){
		travelTips.setContent(travelTips.getContent().replaceAll("style=\"[\\S\\.\\-\\ ]+\"", "").replaceAll("<td height=\"[\\d]+\" width=\"0\"><br />[\n]+</td>", "")
				);
		}
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("objectId", travelTips.getTravelTipsId());
		parameter.put("objectType", Constant.COM_LOG_OBJECT_TYPE.TRAVEL_TIPS.name());
		
		
		List<ComAffix> comAffixList = comAffixService.selectListByParam(parameter);
		
		if(comAffixList != null && !comAffixList.isEmpty()){
			affix = comAffixList.get(0);
		}
		
		return "travelTipsContent";
	}

	public ComAffix getAffix() {
		return affix;
	}

	public void setAffix(ComAffix affix) {
		this.affix = affix;
	}

	public void setTravelTips(TravelTips travelTips) {
		this.travelTips = travelTips;
	}

	public TravelTips getTravelTips() {
		return travelTips;
	}

	public void setViewTravelTipsService(ViewTravelTipsService viewTravelTipsService) {
		this.viewTravelTipsService = viewTravelTipsService;
	}

	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}
	
	
}
