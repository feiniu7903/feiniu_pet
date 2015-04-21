package com.lvmama.front.web.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.vo.Constant;

@Results( {
	 @Result(name = "journeyDetail", location = "/WEB-INF/pages/product/newdetail/buttom/multiJourneyDetail.ftl", type = "freemarker"),
	 @Result(name = "feeDetail", location = "/WEB-INF/pages/product/newdetail/buttom/feeDetail.ftl", type = "freemarker")
})
public class ProductMultiJourneyAction extends ProductBaseAction {
	private static final long serialVersionUID = 1L;
	private ViewMultiJourneyService viewMultiJourneyService;
	private List<ViewMultiJourney> viewMultiJourneyList;
	private String multiDateStr;
	private ComPictureService comPictureService;
	private String multiIdStr = "";
	private List<ViewContent> costContainList;
	private List<ViewContent> nocostContainList;
	private ViewPageService viewPageService;
	
	/**
	 * 获取多行程信息
	 * */
	@Action("/product/getMultiJourneyInfo")
	public String getMultiJourneyInfo(){
		Map<Long, String> map = new LinkedHashMap<Long, String>();
		if(StringUtils.isNotEmpty(multiDateStr)) {
			String[]ds = multiDateStr.split(",");
			for (int i = 0; i < ds.length; i++) {
				String[]strs = (ds[i]).split(":");
				if(StringUtils.isNotEmpty(strs[0])) {
					Long multiId = Long.valueOf(strs[0]);
					String specDate = strs[1];
					if(map.containsKey(multiId)) {
						//exists
						boolean exists = false;
						for (int j = 0; j < map.get(multiId).split("、").length; j++) {
							if(map.get(multiId).split("、")[j].equals(specDate)){
								exists=true;
							}
						}
						if(!exists){
							specDate =  map.get(multiId) + "、" + specDate;
						}else{
							specDate =  map.get(multiId);
						}
					}
					map.put(multiId, specDate);
				}
			}
			viewMultiJourneyList = new ArrayList<ViewMultiJourney>();
			for (Iterator<Long> it = map.keySet().iterator(); it.hasNext();) {
				Long id = it.next();
				multiIdStr += id + ",";
				ViewMultiJourney vmj = viewMultiJourneyService.selectByPrimaryKey(id);
				vmj.setSpecDate(map.get(id));
				List<ViewJourney> vjList = pageService.getViewJourneyByMultiJourneyId(id);
				vjList = pageService.fillJourneyTipsList(vjList);
				for (ViewJourney vj : vjList) {
					vj.setJourneyPictureList(comPictureService.getPictureByObjectIdAndType(vj.getJourneyId(), "VIEW_JOURNEY"));
				}
				vmj.setViewJourneyList(vjList);
				viewMultiJourneyList.add(vmj);
			}
		}
		return "journeyDetail";
	}
	
	/**
	 * 获取多行程费用说明信息
	 * */
	@Action("/product/getFeeDetailInfo")
	public String getFeeDetailInfo(){
		if(StringUtils.isNotEmpty(multiIdStr)) {
			costContainList = new ArrayList<ViewContent>();
			nocostContainList = new ArrayList<ViewContent>();
			String[]ds = multiIdStr.split(",");
			for (int i = 0; i < ds.length; i++) {
				Long multiId = Long.valueOf(ds[i]);
				ViewContent ccVc = viewPageService.getViewContentByMultiJourneyId(multiId, Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
				if(ccVc != null) {
					costContainList.add(ccVc);
				}
				
				ViewContent ncVc = viewPageService.getViewContentByMultiJourneyId(multiId, Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
				if(ncVc != null) {
					nocostContainList.add(ncVc);
				}
			}
		}
		return "feeDetail";
	}

	public void setViewMultiJourneyService(
			ViewMultiJourneyService viewMultiJourneyService) {
		this.viewMultiJourneyService = viewMultiJourneyService;
	}

	public List<ViewMultiJourney> getViewMultiJourneyList() {
		return viewMultiJourneyList;
	}

	public void setMultiDateStr(String multiDateStr) {
		this.multiDateStr = multiDateStr;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public String getMultiIdStr() {
		return multiIdStr;
	}

	public void setMultiIdStr(String multiIdStr) {
		this.multiIdStr = multiIdStr;
	}

	public List<ViewContent> getCostContainList() {
		return costContainList;
	}

	public List<ViewContent> getNocostContainList() {
		return nocostContainList;
	}

	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}
}
