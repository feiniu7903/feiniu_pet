package com.lvmama.back.web.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.api.Label;
import org.zkoss.zul.api.Paging;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdPlaceTagService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdPlaceTag;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;

/**
 * 标签管理
 * 
 * @author lipengcheng
 *
 */
public class TagRelatedPlaceAction extends BaseAction {

	private static final long serialVersionUID = 8562583086221441886L;
	private ProdPlaceTagService prodPlaceTagService;
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	private PlaceService placeService;
	private List<ProdPlaceTag> searchPlaceTagsList;// 页面点击queryButton之后返回的产品标签数据源
	private List<Long> placeTagIds = new ArrayList<Long>();// 页面点击queryButton之后用于存放checkbox选中的PlaceTagId
	private Long attrTagId;
	protected Label _totalCountLabel2;
	protected Paging _paging2;
	
	
	public void doAfter() {
		searchPlaceTags();
	}

	/**
	 * 查询对应关联产品的标签
	 */
	public void searchPlaceTags() {
		Integer totalRowCount = prodPlaceTagService.getPlaceTagsTotalCount(attrTagId);
		_totalCountLabel2.setValue(totalRowCount.toString());
		_paging2.setTotalSize(totalRowCount.intValue());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tagId", attrTagId);
		params.put("_startRow", _paging2.getActivePage() * _paging2.getPageSize() + 1);
		params.put("_endRow", _paging2.getActivePage() * _paging2.getPageSize() + _paging2.getPageSize());
		searchPlaceTagsList = prodPlaceTagService.selectByParams(params);

	}

	/**
	 * 页面点击queryButton之后用于存放checkbox选中的id
	 */
	public void choseItem(Checkbox chkbox, Long placeTagId) {
		if (chkbox.isChecked()) {// 勾选添加
			placeTagIds.add(placeTagId);
		} else {// 取消勾选移除
			placeTagIds.remove(placeTagId);
		}
	}

	/**
	 * 批量删除产品标签
	 */
	public void delPlaceTags() {
		if (isDeletable()) {
			ZkMessage.showQuestion("您确认要删除这些记录？", new ZkMsgCallBack() {
				public void execute() {
					List<ProdPlaceTag> prodPlaceTags = new ArrayList<ProdPlaceTag>();
					for (Long p : placeTagIds) {
						prodPlaceTags.add(new ProdPlaceTag(p));
					}
					prodPlaceTagService.delPlaceTags(prodPlaceTags);
					for(Long p : placeTagIds){
						Place place = placeService.queryPlaceByPlaceId(p);
						comSearchInfoUpdateService.placeUpdated(p,place.getStage());
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
	 * 设置被选中的任務
	 * */
	public void onCheckboxEvent(Long id, boolean checked) {
		if (checked) {
			placeTagIds.add(id);
		} else {
			placeTagIds.remove(id);
		}
	}

	/**
	 * 复选框全选
	 */
	public void selectAllCheckbox(Boolean isChecked) {

		placeTagIds.clear();// 清除刚才单选的全部数目
		for (int i = 0; i < this.searchPlaceTagsList.size(); i++) {
			ProdPlaceTag prodPlaceTag = this.searchPlaceTagsList.get(i);
			prodPlaceTag.setIsChecked(isChecked);
			onCheckboxEvent(prodPlaceTag.getId(), isChecked);
		}
	}

	/**
	 * 检测是否可删
	 */
	private boolean isDeletable() {
		return placeTagIds.size() > 0;
	}
	
	// 传入页面的值
	public List<ProdPlaceTag> getSearchPlaceTagsList() {
		return searchPlaceTagsList;
	}

	// 接页面传来的值
	public void setAttrTagId(Long attrTagId) {
		this.attrTagId = attrTagId;
	}

	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

}
