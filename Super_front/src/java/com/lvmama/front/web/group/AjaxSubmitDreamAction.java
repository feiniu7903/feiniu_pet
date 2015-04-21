/**
 * 
 */
package com.lvmama.front.web.group;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.bee.po.group.GroupDream;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.front.web.BaseAction;

/**
 * 团梦想提交Action
 * @author songlianjun
 *
 */
@ParentPackage("json-default")
@SuppressWarnings("unused")
public class AjaxSubmitDreamAction extends BaseAction {
	private GroupDreamService groupDreamService;
	private Long dreamId;
	private String email;
	private String enjoyFlag;
	
	private GroupDream groupDream;
	/**
	 * 
	 * 
	 * @return
	 */
	@Action(value="/group/submitDream",results=@Result(type="json",name="groupDream",params={"includeProperties","groupDream.*"}))
	public String submitDream(){
		String remoteHost  = InternetProtocol.getRemoteAddr(super.getRequest());
		groupDream = groupDreamService.submitDream(this.dreamId, this.email, enjoyFlag, remoteHost);
		return "groupDream";
	}
	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}
	public String getEnjoyFlag() {
		return enjoyFlag;
	}
	public void setEnjoyFlag(String enjoyFlag) {
		this.enjoyFlag = enjoyFlag;
	}
	public Long getDreamId() {
		return dreamId;
	}
	public void setDreamId(Long dreamId) {
		this.dreamId = dreamId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public GroupDream getGroupDream() {
		return groupDream;
	}
	
}
