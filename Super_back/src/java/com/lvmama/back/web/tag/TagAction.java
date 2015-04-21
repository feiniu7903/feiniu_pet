package com.lvmama.back.web.tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.service.prod.ProdTagService;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;

/** 标签管理，标签基类 */
public abstract class TagAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	protected ProdProductTagService prodProductTagService;
	protected ComSearchInfoUpdateService comSearchInfoUpdateService;
	private ProdTagService prodTagService;
	private List<ProdTagGroup> groupsList = new ArrayList<ProdTagGroup>();

	private List<ProdTag> prodTagList;

	/** 标签 */
	protected ProdTag prodTag = new ProdTag();

	protected Long tagGroupId;

	/** 标签有效 开始时间 */
	protected Date beginTime;

	/** 标签有效 结束 时间 */
	protected Date endTime;

	Groupbox checkboxChildren;
	Radiogroup radioChildren;

	/**
	 * 加载小组
	 */
	protected void doBefore() throws Exception {
		ProdTagGroup prodTagGroup = new ProdTagGroup();
		prodTagGroup.setTagGroupName("-请选择-");
		prodTagGroup.setTagGroupId(-1l);
		groupsList.add(prodTagGroup);
		this.groupsList.addAll(prodTagService.getGroups());

	}

	/** 数据验证 */
	protected boolean validate(List<String> selectTag) throws Exception {
		if (beginTime != null && endTime == null) {
			Messagebox.show("当填写了 开始时间 时，则必须填写格式正确的 结束时间！", "警告", Messagebox.OK, Messagebox.ERROR);
			return false;
		} else if (beginTime == null && endTime != null) {
			Messagebox.show("当填写了 结束时间 时，则必须填写格式正确的 开始时间！", "警告", Messagebox.OK, Messagebox.ERROR);
			return false;
		} else if (beginTime != null && endTime != null && beginTime.compareTo(endTime) >= 0) {
			Messagebox.show("请正确填写时效信息(开始时间>结束时间)", "警告", Messagebox.OK, Messagebox.ERROR);
			return false;
		}
		if (selectTag.size() <= 0) {
			Messagebox.show("请选择标签名!", "温馨提示", Messagebox.OK, Messagebox.INFORMATION);
			return false;
		}

		return true;
	}

	/** 判断输入的 ids 是否正确 */
	protected boolean isNumber(String ids[]) {
		try {
			for (int k = 0; k < ids.length; k++) {
				String strId = ids[k].trim();
				int len = strId.length();
				for (int i = 0; i < len; i++) {
					if (!Character.isDigit(strId.charAt(i))) {
						return false;
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/** 得到 选中的 标签 */
	@SuppressWarnings("unchecked")
	protected String getSelectTag(List<String> selectTag) {
		String tagName = "";
		Radio radio = radioChildren.getSelectedItem();
		if (radio != null) {
			selectTag.add(radio.getValue());
			tagName = radio.getLabel();
		}

		List<Checkbox> list = checkboxChildren.getChildren();
		if (list != null && list.size() > 0) {
			for (Checkbox checkbox : list) {
				if (checkbox.isChecked() == true) {
					selectTag.add(checkbox.getValue());
					tagName += " " + checkbox.getLabel();
				}
			}
		}
		return tagName;
	}

	/**
	 * 加载小组标签
	 * 
	 * @param tagGroupId
	 */
	public void selectTagesInGroups(Long tagGroupId) {
		this.setTagGroupId(tagGroupId);
		if (tagGroupId == null) {
			return;
		}
		prodTagList = new ArrayList<ProdTag>();
		ProdTag prodTag = new ProdTag();
		prodTag.setTagName("-请选择-");
		prodTagList.add(prodTag);

		List<ProdTag> localTags = prodTagService.getTagsInGroup(tagGroupId);
		if (localTags != null && !localTags.isEmpty()) {
			prodTagList.addAll(localTags);
		}
	}

	/**
	 * 动态加载标签名
	 */
	public void createtag(long tagGroupId) {
		Checkbox check = null;
		Radio radio = null;
		checkboxChildren.getChildren().clear();
		radioChildren.getChildren().clear();
		if(tagGroupId!=-1l){
			ProdTagGroup prodProductTag = this.prodTagService.selectByPrimaryKey(tagGroupId);
			if (prodProductTag.isSingleOption()) {
				for (ProdTag prodTag : prodTagList) {
					if (prodTag.getTagId() == null) {
						continue;
					}
					radio = new Radio();
					radio.setValue(prodTag.getTagId().toString());
					radio.setLabel(prodTag.getTagName());
					radioChildren.appendChild(radio);
				}
			} else if (prodProductTag.isMultipleOption()) {
				for (ProdTag prodTag : prodTagList) {
					if (prodTag.getTagId() == null) {
						continue;
					}
					check = new Checkbox();
					check.setValue(prodTag.getTagId().toString());
					check.setLabel(prodTag.getTagName());
					checkboxChildren.appendChild(check);
				}
			}
		}
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public List<ProdTag> getProdTagList() {
		return prodTagList;
	}

	public void setProdTagList(List<ProdTag> prodTagList) {
		this.prodTagList = prodTagList;
	}

	public List<ProdTagGroup> getGroupsList() {
		return groupsList;
	}

	public void setGroupsList(List<ProdTagGroup> groupsList) {
		this.groupsList = groupsList;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public ProdTag getProdTag() {
		return prodTag;
	}

	public void setProdTag(ProdTag prodTag) {
		this.prodTag = prodTag;
	}

	public Long getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(Long tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	public void setProdTagService(ProdTagService prodTagService) {
		this.prodTagService = prodTagService;
	}

	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}

}
