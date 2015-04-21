package com.lvmama.back.web.tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.api.Button;
import org.zkoss.zul.api.Listitem;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdPlaceTagService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.pet.po.prod.ProdPlaceTag;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
import com.lvmama.comm.vo.Constant.PROD_PRODUCT_TAG_CREATOR;
@SuppressWarnings("unchecked")
public class BatchAddTagAction extends BaseAction {
	private ProdProductTagService prodProductTagService;
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	
	/** 目的地 标签 service */
	private ProdPlaceTagService prodPlaceTagService;

	private List<ProdProductTag> batchAddProductTags;

	private List<ProdPlaceTag> batchAddPlaceTags;

	private Date beginTime = null;
	private Date endTime = null;
	private long tagGroupId;
	Window product_forceaddtag;
	Window product_tagaddordel;

	Window place_forceaddtag;
	Window place_tagaddordel;

	@Override
	protected void doBefore() throws Exception {
	}

	/**
	 * 强制添加标签操作 convert 需要强制添加的产品标签列表
	 * 
	 * @param
	 * @throws InterruptedException
	 */
	public void startBatchAddProductTags(Set<Listitem> set) throws Exception {
		if (set.size() <= 0) {
			Messagebox.show("请选择列表后再执行强制添加操作");
			return;
		}
		List<ProdProductTag> convertProductTag = convertBatchAddListitem(set);
		// 以产品id 和 标签 id 删除prod_product_tag记录
		prodProductTagService.addProductTagAndDeleteConflictsProductTag(tagGroupId, convertProductTag);
		for(ProdProductTag prodPTag : convertProductTag){
			comSearchInfoUpdateService.productUpdated(prodPTag.getProductId());
		}
		Messagebox.show("强制添加成功！");
		Component c = product_tagaddordel.getParent();
		Button b = (Button) c.getFellow("queryProductByTagAndProductType");
		Events.sendEvent(new Event("onClick", b));
		product_forceaddtag.detach();
		product_tagaddordel.detach();
	}

	/**
	 * 强制添加标签操作 convert 需要强制添加的 目的地 标签列表
	 */
	public void startBatchAddPlaceTags(Set<Listitem> set) throws Exception {
		if (set.size() <= 0) {
			Messagebox.show("请选择列表后再执行强制添加操作");
			return;
		}
		List<ProdPlaceTag> convertPlaceTag = convertBatchAddListitem(set);
		// 以目的地id 和 标签 id 删除prod_place_tag记录
		prodPlaceTagService.addPlaceTagAndDeleteConflictsPlaceTag(tagGroupId, convertPlaceTag);
		Messagebox.show("强制添加成功！");
		Component c = place_tagaddordel.getParent();
		Button b = (Button) c.getFellow("queryPlaceByTagAndPlaceType");
		Events.sendEvent(new Event("onClick", b));
		place_forceaddtag.detach();
		place_tagaddordel.detach();
	}

	public List convertBatchAddListitem(Set<Listitem> set) {
		List list = new ArrayList();
		for (Iterator<Listitem> iter = set.iterator(); iter.hasNext();) {
			Listitem listitem = (Listitem) iter.next();
			ProdPlaceTag obj = (ProdPlaceTag)listitem.getValue();
			obj.setCreator(PROD_PRODUCT_TAG_CREATOR.USER.getCode());
			list.add(obj);
		}
		return list;
	}

	public ProdProductTagService getProdProductTagService() {
		return prodProductTagService;
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public List<ProdProductTag> getBatchAddProductTags() {
		return batchAddProductTags;
	}

	public void setBatchAddProductTags(List<ProdProductTag> batchAddProductTags) {
		this.batchAddProductTags = batchAddProductTags;
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

	public long getTagGroupId() {
		return tagGroupId;
	}

	public void setTagGroupId(long tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	public List<ProdPlaceTag> getBatchAddPlaceTags() {
		return batchAddPlaceTags;
	}

	public void setBatchAddPlaceTags(List<ProdPlaceTag> batchAddPlaceTags) {
		this.batchAddPlaceTags = batchAddPlaceTags;
	}

	public void setProdPlaceTagService(ProdPlaceTagService prodPlaceTagService) {
		this.prodPlaceTagService = prodPlaceTagService;
	}

	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}

}
