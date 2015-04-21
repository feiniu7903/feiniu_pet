package com.lvmama.back.web.tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Radiogroup;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdPlaceTagService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdPlaceTag;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.prod.ProdTagGroup;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;

/**
 * 产品关联标签
 * 
 * @author qiuguobin
 * 
 */
public class ProdTagAction extends BaseAction {
	private static final long serialVersionUID = 7111997800603735602L;

	/** 目的地 标签 service */
	private ProdPlaceTagService prodPlaceTagService;
	private PlaceService placeService;
	private ProdProductTagService prodProductTagService;
	private List<ProdTagGroup> tagGroups; // 组数据源
	private List<ProdProductTag> productTags; // 产品标签数据源
	
	private List<ProdPlaceTag> placeTags; //目的地 标签数据源
	private ProdContainerProductService prodContainerProductService;
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	
	/** 产品 id */
	private Long productId;

	/** 目的地 id */
	private Long placeId;

	private Date beginTime;
	private Date endTime;
	/** 容纳动态生成的标签 */
	Groupbox checkboxChildren;
	Radiogroup radioChildren;

	/** 页面初始化时加载 */
	public void doBefore() throws Exception {
		if (productId != null) {
			loadProductTags();
		}
		if (placeId != null) {
			loadPlaceTags();
		}
	}

	/**
	 * 加载所有 产品 标签数据源
	 */
	public void loadProductTags() {
		ProdProductTag prodProductTag = new ProdProductTag();
		prodProductTag.setProductId(productId);
		productTags = prodProductTagService.selectProdProductByParams(prodProductTag);
	}
	
	/**
	 * 加载所有 目的地 标签数据源
	 */
	public void loadPlaceTags() {
		Place place=new Place();
		place.setPlaceId(placeId);
		placeTags = prodPlaceTagService.selectPlaceTagsByPlace(place);
	}

	/**
	 * 删除指定的产品标签
	 */
	public void delProductTag(final Long productTagId, final Long productId, final Long tagId) {
		ZkMessage.showQuestion("您确认要删除这条记录？", new ZkMsgCallBack() {
			public void execute() {
				prodProductTagService.delProductTag(productTagId);
				prodContainerProductService.deleteTagContainerProduct(productId, tagId);
				comSearchInfoUpdateService.productUpdated(productId);
				refreshComponent("refreshButton");
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}
	
	/**
	 * 删除指定的 目的地 标签
	 */
	public void delPlaceTag(final Long placeTagId, final Long placeId) {
		ZkMessage.showQuestion("您确认要删除这条记录？", new ZkMsgCallBack() {
			public void execute() {
				List<ProdPlaceTag> prodPlaceTags=new ArrayList<ProdPlaceTag>();
				prodPlaceTags.add(new ProdPlaceTag(placeTagId));
				prodPlaceTagService.delPlaceTags(prodPlaceTags);
				Place place = placeService.queryPlaceByPlaceId(placeId);
				comSearchInfoUpdateService.placeUpdated(placeId,place.getStage());
				refreshComponent("refreshButton");
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<ProdTagGroup> getTagGroups() {
		return tagGroups;
	}

	public List<ProdProductTag> getProductTags() {
		return productTags;
	}

	public Long getProductId() {
		return productId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public void setProdPlaceTagService(ProdPlaceTagService prodPlaceTagService) {
		this.prodPlaceTagService = prodPlaceTagService;
	}

	public List<ProdPlaceTag> getPlaceTags() {
		return placeTags;
	}

	public void setPlaceTags(List<ProdPlaceTag> placeTags) {
		this.placeTags = placeTags;
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}

	public void setProdContainerProductService(ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}

	public void setComSearchInfoUpdateService(
			ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

}
