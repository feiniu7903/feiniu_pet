package com.lvmama.back.web.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.api.Listitem;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.comm.bee.service.prod.ProdPlaceTagService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdPlaceTag;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.vo.Constant.PROD_PRODUCT_TAG_CREATOR;

public class PlaceTagAction extends TagAction {
	private static final long serialVersionUID = 7037194179106186837L;
	Logger log = Logger.getLogger(PlaceTagAction.class);
	/** 目的地 标签 service */
	private ProdPlaceTagService prodPlaceTagService;
	private PlaceService placeService;
	/** 目的地 标签 service */
	private List<ProdPlaceTag> batchAddPlaceTags = new ArrayList<ProdPlaceTag>();
	/** 选择的目的地 */
	private List<Place> selectedPlaceList = new ArrayList<Place>();
	/** 查询的目的地列表 */
	private List<Place> searchPlaceList;

	/** 目的地 */
	private Place place = new Place();

	private String placeIds;

	@Override
	protected void doBefore() throws Exception {
		super.doBefore();
	}

	/**
	 * 右移动选择项
	 * 
	 * @param set
	 */
	public void addSelectedPlaceList(Set<Listitem> set) {
		this.addOrDelSelectedPlaces(set, true, false);
	}

	/**
	 * 左移动选择项
	 * 
	 * @param set
	 */
	public void delSelectedPlaceList(Set<Listitem> set) {
		this.addOrDelSelectedPlaces(set, false, true);
	}

	/** 添加或删除 目的地 */
	private void addOrDelSelectedPlaces(Set<Listitem> set, boolean isAdd, boolean isDel) {
		// isAdd 与 isDel 值互斥，如果相同直接返回
		if (isAdd == isDel) {
			return;
		}
		if (isAdd) {
			// 添加之前 清空 之前已经添加的 目的地
			selectedPlaceList.clear();
		}
		for (Iterator<Listitem> iter = set.iterator(); iter.hasNext();) {
			Listitem listitem = (Listitem) iter.next();
			Place place = (Place) listitem.getValue();
			if (isAdd) {
				selectedPlaceList.add(place);
			}
			if (isDel) {
				selectedPlaceList.remove(place);
			}
		}
	}

	/**
	 * 查询
	 */
	public void queryPlaceByTagAndPlaceType() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		if (prodTag.getTagId()!=null) {
			params.put("tagId", prodTag.getTagId());
		}
		
		if (StringUtils.isNotEmpty(placeIds)) {
			String[] ids = placeIds.split(",");
			if (super.isNumber(ids)) {
				params.put("placeIds", placeIds);
			} else {
				Messagebox.show("输入的编号不正确，请确认！", "警告", Messagebox.OK, Messagebox.ERROR);
				return;
			}
		}
		String stage="'2','3'";//景点 酒店
		if(StringUtils.isNotEmpty(place.getStage())){
			stage="'"+place.getStage()+"'";
		}
		params.put("placeStage", stage);
		params.put("placeName", place.getName());
		params.put("tagGroupId", tagGroupId);
		params.put("_startRow", _paging.getActivePage() * _paging.getPageSize() + 1);
		params.put("_endRow", _paging.getActivePage() * _paging.getPageSize() + _paging.getPageSize());
		Integer totalRowCount = prodPlaceTagService.queryPlaceByTagAndPlaceTypeCount(params);
		_paging.setTotalSize(totalRowCount.intValue());
		searchPlaceList = prodPlaceTagService.queryPlaceByTagAndPlaceType(params);
	}

	/** 删除 目的地 标签 */
	public void submitDelPlaceTag() {
		try {
			List<String> selectTag = new ArrayList<String>();
			// 获取选中的标签，一次只能操作单选或多选
			this.getSelectTag(selectTag);
			// 验证
			if (!super.validate(selectTag)) {
				return;
			}
			final List<ProdPlaceTag> placeTags = this.initProdPlaceTag(selectTag);
			ZkMessage.showQuestion("您确认要删除吗？", new ZkMsgCallBack() {
				public void execute() {
					try {
						prodPlaceTagService.delPlaceTags(placeTags);
						Messagebox.show("删除成功！");
						for(ProdPlaceTag prodPlaceTag : placeTags){
							Long placeId = prodPlaceTag.getPlace().getPlaceId();
							Place place = placeService.queryPlaceByPlaceId(placeId);
							comSearchInfoUpdateService.placeUpdated(placeId,place.getStage());
						}
						refreshParentColseCurrWin();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}, new ZkMsgCallBack() {
				public void execute() {
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 刷新父查询 关闭当前窗口 */
	private void refreshParentColseCurrWin() {
		this.refreshParent("queryPlaceByTagAndPlaceType");
		this.closeWindow();
	}

	/**
	 * 提交添加标签
	 */
	public void submitPlaceTag(Long tagGroupId) {

		try {

			List<String> selectTag = new ArrayList<String>();
			// 获取选中的标签，一次只能操作单选或多选
			this.getSelectTag(selectTag);
			// 验证
			if (!super.validate(selectTag)) {
				return;
			}

			List<ProdPlaceTag> placeTags = this.initProdPlaceTag(selectTag);
			// 保存数据，返
			List<ProdPlaceTag> alreadyAddPlaceTags = new ArrayList<ProdPlaceTag>();
			alreadyAddPlaceTags = prodPlaceTagService.addProgPlaceTags(tagGroupId, placeTags, alreadyAddPlaceTags);
			for(ProdPlaceTag prodPlaceTag : placeTags){
				Long placeId = prodPlaceTag.getPlace().getPlaceId();
				Place place = placeService.queryPlaceByPlaceId(placeId);
				comSearchInfoUpdateService.placeUpdated(placeId,place.getStage());
			}
			
			if (alreadyAddPlaceTags.isEmpty()) {
				Messagebox.show("批量添加成功！");
				this.refreshParentColseCurrWin();
			} else {
				Map<String, Object> alreadyAddPlaceTagsMap = new HashMap<String, Object>();
				this.batchAddPlaceTags = alreadyAddPlaceTags;
				alreadyAddPlaceTagsMap.put("batchAddPlaceTags", batchAddPlaceTags);
				alreadyAddPlaceTagsMap.put("tagGroupId", tagGroupId);
				showWindow("/tag/place_forceaddtag.zul", alreadyAddPlaceTagsMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 初始化创建 place tag关系数据 */
	private List<ProdPlaceTag> initProdPlaceTag(List<String> selectTag) {
		List<ProdPlaceTag> prodPlaceTags = new ArrayList<ProdPlaceTag>();
		for (int i = 0; i < selectedPlaceList.size(); i++) {
			Place place = selectedPlaceList.get(i);
			for (int j = 0; j < selectTag.size(); j++) {
				String tagId = selectTag.get(j);
				ProdPlaceTag progPlaceTag = new ProdPlaceTag();
				progPlaceTag.setPlace(place);
				progPlaceTag.setBeginTime(beginTime);
				progPlaceTag.setEndTime(endTime);
				ProdTag tag = new ProdTag();
				tag.setTagId(Long.parseLong(tagId));
				progPlaceTag.setTag(tag);
				progPlaceTag.setCreator(PROD_PRODUCT_TAG_CREATOR.USER.getCode());
				prodPlaceTags.add(progPlaceTag);
			}
		}
		return prodPlaceTags;
	}

	/**
	 * 打开窗体
	 * 
	 * @param uri
	 * @param windowId
	 */
	public void showWin(String uri) {
		try {
			if (selectedPlaceList.size() <= 0) {
				Messagebox.show("请先查询 目的地 标签，然后再添加相关 目的地  到右边列表！");
				return;
			}
			Map<String, Object> selectedPlaceMap = new HashMap<String, Object>();
			selectedPlaceMap.put("selectedPlaceList", selectedPlaceList);
			super.showWindow(uri, selectedPlaceMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ProdPlaceTag> getBatchAddPlaceTags() {
		return batchAddPlaceTags;
	}

	public void setBatchAddPlaceTags(List<ProdPlaceTag> batchAddPlaceTags) {
		this.batchAddPlaceTags = batchAddPlaceTags;
	}

	public List<Place> getSearchPlaceList() {
		return searchPlaceList;
	}

	public void setSearchPlaceList(List<Place> searchPlaceList) {
		this.searchPlaceList = searchPlaceList;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public List<Place> getSelectedPlaceList() {
		return selectedPlaceList;
	}

	public void setSelectedPlaceList(List<Place> selectedPlaceList) {
		this.selectedPlaceList = selectedPlaceList;
	}

	public void setProdPlaceTagService(ProdPlaceTagService prodPlaceTagService) {
		this.prodPlaceTagService = prodPlaceTagService;
	}

	public String getPlaceIds() {
		return placeIds;
	}

	public void setPlaceIds(String placeIds) {
		this.placeIds = placeIds;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

}
