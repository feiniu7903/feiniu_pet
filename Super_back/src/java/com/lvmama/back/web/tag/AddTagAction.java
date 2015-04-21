package com.lvmama.back.web.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.service.prod.ProdTagService;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;

/**
 * 添加标签 除了描述
 * 
 * 其它的属性都为必填
 * 
 * @author lipengcheng
 * 
 */
public class AddTagAction extends BaseAction {

	private static final long serialVersionUID = 7262603220223757790L;
	private ProdTagService prodTagService;
	
	private List<ProdTagGroup> groups;
	private ProdTag tag = new ProdTag();
	private Long tagGroupId;
	
	public void doBefore() throws Exception {
		loadGroups();
		
		tag.setTagSEQ(new Long(0));
	}
	
	/**
	 * 加载标签小组
	 */
	public void loadGroups() {
		groups = new ArrayList<ProdTagGroup>();

		// 列表头增加“不限”选项
		ProdTagGroup prodTagGroup = new ProdTagGroup();
		prodTagGroup.setTagGroupName("不限");
		groups.add(prodTagGroup);

		List<ProdTagGroup> localTagGroups = prodTagService.getGroups();
		if (localTagGroups != null && !localTagGroups.isEmpty()) {
			groups.addAll(localTagGroups);
		}
	}

	/**
	 * 添加方法
	 */
	public void addTag() {
		if (!validTag()) {
			return;
		}
		if (StringUtils.isNotBlank(tag.getTagName()) && tagGroupId != null) {
			tag.setTagGroupId(tagGroupId);
			if (tag.getTagSEQ() == null) {
				tag.setTagSEQ(new Long(0));// 根据项目需求，SEQ的默认值为0
			}
			prodTagService.addTag(tag);
			//this.refreshParent("refreshButton");
			//this.refreshParent("searchButton");

		}
		this.closeWindow();
	}
	
	/**
	 * 验证新增的标签参数
	 * 
	 * @author lipengcheng
	 * @return
	 */
	public Boolean validTag() {
		boolean flag = true;
		if (tagGroupId == null || tagGroupId == 0) {
			alert("请选择一个用户组");
			flag = false;
		} 
		if (tag.getTagName() == null || "".equals(tag.getTagName())) {
			alert("标签名不能为空");
			flag = false;
		}else if (tag.getTagName() != null && (!"".equals(tag.getTagName()))) {
			String tagName = tag.getTagName().replaceAll(",", "，");
			tag.setTagName(tagName);
			
			//标签名不能重名
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tagName", tag.getTagName());
			Integer num = prodTagService.getGroupsAndTagsCount(params);
			if(num > 0){
				alert("标签名已经被使用，保存失败");
				flag = false;
			}
		} 
		if (tag.getTagPinYin() == null || "".equals(tag.getTagPinYin())) {
			alert("标签拼音不能为空");
			flag = false;
		}else if (tag.getTagPinYin() != null && (!"".equals(tag.getTagPinYin()))) {
			String pinYin = tag.getTagPinYin().replaceAll(",", "，");
			tag.setTagPinYin(pinYin);
		}
		if (tag.getTagSEQ() == null) {
			alert("SEQ不能为空");
			flag = false;
		} else if (tag.getTagSEQ() < -10000) {
			alert("SEQ必须大于-10000");
			flag = false;
		}
		if (tag.getCssId() == null) {
			alert("样式ID不能为空");
			flag = false;
		}else if (tag.getCssId() != null && (!"".equals(tag.getCssId()))) {
			String cssId = tag.getCssId().replaceAll(",", "，");
			tag.setCssId(cssId);
		}
		if(tag.getDescription()==null || "".equals(tag.getDescription())){
			tag.setDescription(tag.getTagName()); //标签描述的默认值为标签名
		}else if (tag.getDescription() != null && (!"".equals(tag.getDescription()))) {
			String description = tag.getDescription().replaceAll(",", "，");
			tag.setDescription(description);
		}
		return flag;
	}
	
	
	// 从页面得到的值
	public void setGroupId(Long tagGroupId) {
		if(tagGroupId == null) {
			tag.setCssId("");
			return;
		}
		this.tagGroupId = tagGroupId;
		ProdTagGroup prodTagGroup = prodTagService.selectByPrimaryKey(tagGroupId);
		tag.setCssId(prodTagGroup.getCssId());
	}

	
	//传入页面的值
	public List<ProdTagGroup> getGroups() {
		return groups;
	}

	public ProdTag getTag() {
		return tag;
	}

	public void setProdTagService(ProdTagService prodTagService) {
		this.prodTagService = prodTagService;
	}

}
