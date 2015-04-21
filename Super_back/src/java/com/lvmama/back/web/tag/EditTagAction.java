package com.lvmama.back.web.tag;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdTagService;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.vo.Constant.PROD_TAG_NAME;

/**
 * 编辑标签
 * 
 * @author lipengcheng
 * 
 */
public class EditTagAction extends BaseAction {

	private static final long serialVersionUID = -2899328855100378945L;

	private ProdTagService prodTagService;
	private Long attrTagId;
	private ProdTag tag = new ProdTag();
	private Boolean tagDisable = false;
	
	public void doBefore() throws Exception {
		tag = prodTagService.getTagByTagId(attrTagId);
		
		//如果原来数据没有SEO值，那么默认输入0
		if(tag.getTagSEQ()==null){
			tag.setTagSEQ(0l);
		}

		for (PROD_TAG_NAME item : PROD_TAG_NAME.values()) {
			if (item.getCnName().equals(tag.getTagName())) {
				tagDisable = true;
			}
		}
	}

	/**
	 * @author lipengcheng 对标签进行修改
	 */
	public void editTag() {
		if (!validTag()) {
			return;
		}
		if (StringUtils.isNotBlank(tag.getTagName())) {
			prodTagService.editTag(tag);
			this.refreshParent("refreshButton");
			this.refreshParent("searchButton");
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
		if (tag.getTagName() == null || "".equals(tag.getTagName())) {
			alert("标签名不能为空");
			flag = false;
		}else if (tag.getTagName() != null && (!"".equals(tag.getTagName()))) {
			String tagName = tag.getTagName().replaceAll(",", "，");
			tag.setTagName(tagName);
			
			//标签名不能重名
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tagName", tag.getTagName());
			params.put("excludeTagId", tag.getTagId());
			Integer num = prodTagService.getGroupsAndTagsCount(params);
			if(num > 0){
				alert("标签名已经被使用，保存失败");
				flag = false;
			}
		}
		if(tag.getTagPinYin() == null || "".equals(tag.getTagPinYin())) {
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
		if (tag.getDescription() != null && (!"".equals(tag.getDescription()))) {
			String description = tag.getDescription().replaceAll(",", "，");
			tag.setDescription(description);
		} else if (tag.getDescription() == null || "".equals(tag.getDescription())) {
			tag.setDescription(tag.getTagName()); // 标签描述的默认值为标签名
		}
		return flag;
	}
	
	public void setTag(ProdTag tag) {
		this.tag = tag;
	}

	public ProdTag getTag() {
		return tag;
	}

	public void setAttrTagId(Long attrTagId) {
		this.attrTagId = attrTagId;
	}

	public void setProdTagService(ProdTagService prodTagService) {
		this.prodTagService = prodTagService;
	}

	public Boolean getTagDisable() {
		return tagDisable;
	}

	public void setTagDisable(Boolean tagDisable) {
		this.tagDisable = tagDisable;
	}

}
