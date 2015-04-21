package com.lvmama.back.web.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.bee.service.prod.ProdTagService;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;

/**
 * 标签管理
 * 
 * @author qiuguobin
 *
 */
public class TagManagerAction extends BaseAction {

	private static final long serialVersionUID = -4162523235378286197L;
	private ProdProductTagService prodProductTagService;
	private ProdTagService prodTagService;
	private List<ProdTagGroup> tagGroups; // 组数据源
	private List<ProdTag> tags;// 标签数据源
	private List<ProdTag> searchTags;// 页面点击searchButton返回的数据源
	// 页面传入的参数
	private Long tagGroupId;
	private Long tagId;
	private Long attrTagId;
	

	/**
	 * 页面初始化时加载
	 */
	public void doBefore() throws Exception {
		load();
	}

	public void load() {
		loadGroups();
		loadTags();
	}

	/**
	 * 加载所有组数据源
	 */
	public void loadGroups() {
		tagGroups = new ArrayList<ProdTagGroup>();
		// 列表头增加“不限”选项
		ProdTagGroup prodTagGroup = new ProdTagGroup();
		prodTagGroup.setTagGroupName("不限");
		tagGroups.add(prodTagGroup);

		List<ProdTagGroup> localTagGroups = prodTagService.getGroups();
		if (localTagGroups != null && !localTagGroups.isEmpty()) {
			tagGroups.addAll(localTagGroups);
		}
	}

	/**
	 * 加载所有标签数据源
	 */
	public void loadTags() {
		
		tags = new ArrayList<ProdTag>();

		// 列表头增加“不限”选项
		ProdTag prodTag = new ProdTag();
		prodTag.setTagName("不限");
		tags.add(prodTag);

		List<ProdTag> localTags = prodTagService.getTags();
		if (localTags != null && !localTags.isEmpty()) {
			tags.addAll(localTags);
		}
	}

	/**
	 * 页面选中一个组之后加载属于该组的标签数据源
	 */
	public void loadTags(Long tagGroupId) {
		tags.clear();// 清除之前的操作数据

		// 列表头增加“不限”选项
		ProdTag prodTag = new ProdTag();
		prodTag.setTagName("不限");
		tags.add(prodTag);
		List<ProdTag> prodTags = null;
		if (this.tagGroupId == null) {
			prodTags = prodTagService.getTags();
			this.tagId = null;
		} else {
			prodTags = prodTagService.getTagsInGroup(tagGroupId);
		}
		for (ProdTag p : prodTags) {
			tags.add(p);
		}
		_paging.setActivePage(0);
	}

	/**
	 * 页面点击searchButton返回的数据源
	 */
	public void searchTags() {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("tagGroupId", tagGroupId);
		searchParams.put("tagId", tagId);
		Integer totalRowCount = prodTagService.getGroupsAndTagsCount(searchParams);
		_totalRowCountLabel.setValue(totalRowCount.toString());
		_paging.setTotalSize(totalRowCount.intValue());
		searchParams.put("_startRow", _paging.getActivePage() * _paging.getPageSize());
		searchParams.put("_endRow", _paging.getActivePage() * _paging.getPageSize() + _paging.getPageSize() -1);
		searchTags = prodTagService.getGroupsAndTags(searchParams);

	}


	// 以下setter方法接收页面传递过来的数据
	public void setTagGroupId(Long tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	// 以下getter方法提供页面所需的数据
	public List<ProdTagGroup> getTagGroups() {
		return tagGroups;
	}

	public List<ProdTag> getTags() {
		return tags;
	}

	public List<ProdTag> getSearchTags() {
		return searchTags;
	}

	public Long getAttrTagId() {
		return attrTagId;
	}

	public void setProdTagService(ProdTagService prodTagService) {
		this.prodTagService = prodTagService;
	}

	
}
