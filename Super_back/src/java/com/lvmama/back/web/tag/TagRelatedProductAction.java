package com.lvmama.back.web.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.api.Label;
import org.zkoss.zul.api.Paging;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;

/**
 * 标签管理
 * 
 * @author lipengcheng
 *
 */
@SuppressWarnings("serial")
public class TagRelatedProductAction extends BaseAction {
	
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	private ProdProductTagService prodProductTagService;
	private List<ProdProductTag> searchProductTagsList;// 页面点击查询按钮之后返回的产品标签数据源
	private List<ProdProductTag> prodProductTags = new ArrayList<ProdProductTag>();//用于存放checkbox选中的ProdProductTag对象
	private Long attrTagId;//上层容器传来的标签ID
	
	protected Label _totalCountLabel1;
	protected Paging _paging1;
	
	public void doAfter() {
		searchProductTags();
	}

	/**
	 * 查询对应关联产品的标签
	 */
	public void searchProductTags() {
		Integer totalRowCount = prodProductTagService.getProductTagsTotalCount(attrTagId);
		_totalCountLabel1.setValue(totalRowCount.toString());
		_paging1.setTotalSize(totalRowCount.intValue());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagId", attrTagId);
		params.put("_startRow", _paging1.getActivePage() * _paging1.getPageSize() + 1);
		params.put("_endRow", _paging1.getActivePage() * _paging1.getPageSize() + _paging1.getPageSize());
		searchProductTagsList = prodProductTagService.selectByParams(params);
	}
	
	/**
	 * 设置被选中的任務
	 * */
	public void onCheckboxEvent(Long productTagId, Long productId, Long tagId, boolean checked) {
		ProdProductTag prodProductTag = new ProdProductTag(productTagId, productId, tagId);
		if (checked) {
			prodProductTags.add(prodProductTag);
		} else {
			prodProductTags.remove(prodProductTag);
		}
	}
	
	/**
	 * 复选框全选
	 */
	public void selectAllCheckbox(boolean isChecked){
		prodProductTags.clear();
		for (int i = 0; i < this.searchProductTagsList.size(); i++) {
			ProdProductTag prodProductTag=this.searchProductTagsList.get(i);
			prodProductTag.setChecked(isChecked);
			onCheckboxEvent(prodProductTag.getProductTagId(), prodProductTag.getProductId(), prodProductTag.getTagId(), isChecked);
		}
	}
	
	/**
	 * 批量删除产品标签
	 */
	public void delProductTags() {
		if (isDeletable()) {
			ZkMessage.showQuestion("您确认要删除这些记录？", new ZkMsgCallBack() {
				public void execute() {
					prodProductTagService.delProductTags(prodProductTags);
					for(ProdProductTag prodProductTag : prodProductTags){
						comSearchInfoUpdateService.productUpdated(prodProductTag.getProductId());
					}
					refreshComponent("searchProdTag");
				}
			}, new ZkMsgCallBack() {
				public void execute() {
				}
			});
		} else {
			ZkMessage.showInfo("请选择您要删除的记录！");
		}
	}
	
	/**
	 * 检测是否可删
	 */
	private boolean isDeletable() {
		return prodProductTags.size() > 0;
	}
	
	// 传送到页面的值
	public List<ProdProductTag> getSearchProductTagsList() {
		return searchProductTagsList;
	}

	// 从页面传来的值
	public void setAttrTagId(Long attrTagId) {
		this.attrTagId = attrTagId;
	}

	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}
	
}
