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
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.vo.Constant.PROD_PRODUCT_TAG_CREATOR;

public class ProductTagAction extends TagAction {
	private static final long serialVersionUID = -5655220970615803545L;
	Logger log = Logger.getLogger(ProductTagAction.class);
	private List<ProdProductTag> batchAddProductTags = new ArrayList<ProdProductTag>();
	private List<ProdProduct> selectedProductList = new ArrayList<ProdProduct>();
	private List<ProdProduct> searchProductList;

	private String productType;
	private String productName;
	private String productIds;

	/**
	 * 右移动选择项
	 * 
	 * @param set
	 */
	public void addSelectedProductList(Set<Listitem> set) {
		List<ProdProduct> prodProducts = new ArrayList<ProdProduct>();
		for (Iterator<Listitem> iter = set.iterator(); iter.hasNext();) {
			Listitem listitem = (Listitem) iter.next();
			ProdProduct prodProduct = (ProdProduct) listitem.getValue();
			prodProducts.add(prodProduct);
		}
		selectedProductList = prodProducts;
	}

	/**
	 * 左移动选择项
	 * 
	 * @param set
	 */
	public void delSelectedProductList(Set<Listitem> set) {
		for (Iterator<Listitem> iter = set.iterator(); iter.hasNext();) {
			Listitem listitem = (Listitem) iter.next();
			ProdProduct prodProduct = (ProdProduct) listitem.getValue();
			selectedProductList.remove(prodProduct);
		}
	}

	/**
	 * 构造产品与标签的关系
	 * 
	 * @param list
	 * @param beginTime
	 * @param endTime
	 */
	public List<ProdProductTag> initProdProductTag(List<String> checkedTag) {
		List<ProdProductTag> prodProductTags = new ArrayList<ProdProductTag>();
		// 遍历产品
		for (int i = 0; i < selectedProductList.size(); i++) {
			ProdProduct prodProduct = selectedProductList.get(i);
			// 遍历标签
			for (int j = 0; j < checkedTag.size(); j++) {
				String tagId = checkedTag.get(j);
				// 检查被选择的prodProductTag
				ProdProductTag newProdProductTag = new ProdProductTag();
				newProdProductTag.setProductId(prodProduct.getProductId());
				newProdProductTag.setBeginTime(beginTime);
				newProdProductTag.setEndTime(endTime);
				newProdProductTag.setTagId(Long.parseLong(tagId));
				newProdProductTag.setCreator(PROD_PRODUCT_TAG_CREATOR.USER.getCode());
				prodProductTags.add(newProdProductTag);
			}
		}
		return prodProductTags;
	}

	/**
	 * 查询
	 */
	public void queryProductByTagAndProductType() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			if (prodTag.getTagId()!=null) {
				params.put("tagId", prodTag.getTagId());
			}
			//
			if (StringUtils.isNotEmpty(productIds)) {
				String[] ids = productIds.split(",");
				if (super.isNumber(ids)) {
					params.put("productIds", productIds);
				} else {
					Messagebox.show("输入的编号不正确，请确认！", "警告", Messagebox.OK, Messagebox.ERROR);
					return;
				}
			}
			if(StringUtils.isEmpty(productType)){
				productType="'TICKET','HOTEL','ROUTE'";
			}
			params.put("productType", productType);
			params.put("productName", productName);
			params.put("tagGroupId", tagGroupId);
			params.put("_startRow", _paging.getActivePage() * _paging.getPageSize() + 1);
			params.put("_endRow", _paging.getActivePage() * _paging.getPageSize() + _paging.getPageSize());
			Integer totalRowCount = prodProductTagService.queryProductByTagAndProductTypeCount(params);
			_paging.setTotalSize(totalRowCount.intValue());
			searchProductList = prodProductTagService.queryProductByTagAndProductType(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 删除 产品 标签 关联 */
	public void submitDelProductTag() {
		try {
			// 获取选中的标签，一次只能操作单选或多选
			List<String> selectTag = new ArrayList<String>();
			getSelectTag(selectTag);
			// 验证
			if (!super.validate(selectTag)) {
				return;
			}
			
			final List<ProdProductTag> productTags = this.initProdProductTag(selectTag);
			ZkMessage.showQuestion("您确认要删除吗？", new ZkMsgCallBack() {
				public void execute() {
					try {
						prodProductTagService.delProductTags(productTags);
						for(ProdProductTag prodProductTag : productTags){
							comSearchInfoUpdateService.productUpdated(prodProductTag.getProductId());
						}
						Messagebox.show("删除成功！");
						refreshParentColseCurrWin();
					} catch (Exception e) {
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
		this.refreshParent("queryProductByTagAndProductType");
		this.closeWindow();
	}
	
	/**
	 * 提交添加标签
	 */
	public void submitProductTag(Long tagGroupId) {

		try {
			// 获取选中的标签，一次只能操作单选或多选
			List<String> selectTag = new ArrayList<String>();
			getSelectTag(selectTag);
			// 验证
			if (!super.validate(selectTag)) {
				return;
			}

			List<ProdProductTag> productTags = this.initProdProductTag(selectTag);
			List<ProdProductTag> alreadyAddProductTags = new ArrayList<ProdProductTag>();
			prodProductTagService.addProgProductTags(tagGroupId, productTags, alreadyAddProductTags);
			for(ProdProductTag prodPTag : productTags){
				comSearchInfoUpdateService.productUpdated(prodPTag.getProductId());
			}
			
			if (alreadyAddProductTags.size() < 1) {
				Messagebox.show("批量添加成功！");
				this.refreshParentColseCurrWin();
			} else {
				Map<String, Object> alreadyAddProductTagsMap = new HashMap<String, Object>();
				this.batchAddProductTags = alreadyAddProductTags;
				alreadyAddProductTagsMap.put("batchAddProductTags", batchAddProductTags);
				alreadyAddProductTagsMap.put("tagGroupId", tagGroupId);
				showWindow("/tag/product_forceaddtag.zul", alreadyAddProductTagsMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 打开窗体
	 * 
	 * @param uri
	 * @param windowId
	 */
	public void showWin(String uri) {
		try {
			if (selectedProductList.size() <= 0) {
				Messagebox.show("请先查询产品标签，然后再添加相关产品到右边列表！");
				return;
			}
			Map<String, List<ProdProduct>> selectedProductMap = new HashMap<String, List<ProdProduct>>();
			selectedProductMap.put("selectedProductList", selectedProductList);
			super.showWindow(uri, selectedProductMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setSearchProductList(List<ProdProduct> searchProductList) {
		this.searchProductList = searchProductList;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<ProdProduct> getSearchProductList() {
		return searchProductList;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<ProdProduct> getSelectedProductList() {
		return selectedProductList;
	}

	public void setSelectedProductList(List<ProdProduct> selectedProductList) {
		this.selectedProductList = selectedProductList;
	}

	public List<ProdProductTag> getBatchAddProductTags() {
		return batchAddProductTags;
	}

	public void setBatchAddProductTags(List<ProdProductTag> batchAddProductTags) {
		this.batchAddProductTags = batchAddProductTags;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

}
