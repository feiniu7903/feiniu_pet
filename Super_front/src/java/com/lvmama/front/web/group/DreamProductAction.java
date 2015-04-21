package com.lvmama.front.web.group;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.GroupDreamInfo;
/**
 * 团梦想产品Action
 * @author songlianjun
 *
 */
@Results( { @Result(name = "dreamProduct", location = "/WEB-INF/pages/group/dream.ftl", type = "freemarker")
})
public class DreamProductAction extends OtherProductAction {
	private List<GroupDreamInfo> dreamList;
	private GroupDreamService groupDreamService;
	/**
	 * 查看今日团购产品
	 */
	@Action("/group/dream")
	public String execute() throws Exception {
		//查询当前团购梦想产品
		dreamList = groupDreamService.getCurrMonthDreamProducts();
		for(GroupDreamInfo god : dreamList) {
			
//			List<ComPicture> pictureList = comPictureDAO.getComPictureByObjectIdAndType(god.getDreamId(), "GROUP_DREAM");
//			god.setComPictureList(pictureList);
			god.setComPictureList(getComPictureService().getPictureByObjectIdAndType(god.getDreamId(), "GROUP_DREAM"));
		}
		loadOtherPrdList();
		return "dreamProduct";
	}
	public List<GroupDreamInfo> getDreamList() {
		return dreamList;
	}
	public Long getMaxSubmitDreamSeed(){
		return Constant.getInstance().getMaxSubmitDreamSeed();
	}
	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}
}
