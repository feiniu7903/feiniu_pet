package com.lvmama.back.web.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.prod.ProdContainerProduct;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;

public class ContainerProductAction extends BaseAction {
	private static final long serialVersionUID = -6474345862019589600L;

	private ProdContainerProductService prodContainerProductService;

	private String containerCode;
	private Long fromPlaceId;
	private String toPlaceId;
	private String destId;

	private Long productId;
	private String productName;
	private Integer sortType = 4;

	private String productType;
	private String subProductType;

	private int containerProductIndex = 0;
	private Map<String, Integer> containerProductIndexMap;
	private static Map<String, List<SubProductType>> subProductTypeMap;

	private Map<String, Object> params = new HashMap<String, Object>();

	public List<ProdContainer> getContainerList() {
		return  prodContainerProductService.getContainerNameCodePairs();
	}

	public List<ProdContainer> getFromPlaceList() {
		List<ProdContainer> fromPlaceList = new ArrayList<ProdContainer>();
		if (this.containerCode != null) {
			fromPlaceList =  prodContainerProductService.getFromPlacesByContainerCode(this.containerCode);
		}
		return fromPlaceList;
	}

	public List<ProdContainer> getToPlaceList() {
		List<ProdContainer> toPlaceList = new ArrayList<ProdContainer>();
		if (this.containerCode != null) {
			if (prodContainerProductService.isFromPlaceEmpty(this.containerCode)) {
				toPlaceList =  prodContainerProductService.getToPlacesByContainerCodeAndDestId(this.containerCode, "3548");
			} else if(this.fromPlaceId!=null){
				toPlaceList =  prodContainerProductService.getToPlacesByContainerCodeAndFromPlaceId(this.containerCode, ""+this.fromPlaceId);
			}
		}
		return toPlaceList;
	}

	public List<ProdContainer> getToPlaceList2() {
		List<ProdContainer> toPlaceList2 = new ArrayList<ProdContainer>();
		if (this.destId != null) {
			toPlaceList2 =  prodContainerProductService.getToPlacesByContainerCodeAndDestId(this.containerCode, this.destId);
			if (!toPlaceList2.isEmpty()) {
				ProdContainer prodContainer = new ProdContainer();
				prodContainer.setToPlaceName("全部");
				prodContainer.setToPlaceId(toPlaceId);
				toPlaceList2.add(0, prodContainer);
			}
		}
		return toPlaceList2;
	}

	public List<ProdContainerProduct> getContainerProductList() {
		List<ProdContainerProduct> containerProductList = new ArrayList<ProdContainerProduct>();
		if (containerCode != null) {
			Map<String,Object> param=builderParam();
			Long totalRowCount =  prodContainerProductService.getContainerProductListCount(param);
			_totalRowCountLabel.setValue(totalRowCount.toString());
			_paging.setTotalSize(totalRowCount.intValue());
			int startRow = _paging.getActivePage() * _paging.getPageSize() + 1;
			int endRow = _paging.getActivePage() * _paging.getPageSize() + _paging.getPageSize();
			param.put("startRow", startRow);
			param.put("endRow", endRow);
			containerProductList =  prodContainerProductService.getContainerProductList(param);
		}
		return containerProductList;
	}

	protected Map<String, Object> builderParam() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("containerCode", containerCode);
		params.put("fromPlaceId", this.fromPlaceId);
		params.put("toPlaceId", this.toPlaceId);
		params.put("subProductType", subProductType);
		params.put("productId", productId);
		params.put("productName", productName);
		params.put("sortType", sortType);
		return params;
	}
	/**
	 * 设置后台产品背景色 根据频道和产品类型设置
	 * 
	 * @param listitem
	 */
	public void setBgColor(org.zkoss.zul.Listitem listitem) {
		int containerProductChannelIndex = 0;
		String containerCode = getContainerCode();
		containerProductIndexMap = new HashMap<String, Integer>();
		containerProductIndexMap.put("ZYZZ_RECOMMEND", 10);
		containerProductIndexMap.put("ON_SALE", 10);
		containerProductIndexMap.put("NEW_ARRIVAL", 10);
		containerProductIndexMap.put("DZMP_RECOMMEND", 10);
		containerProductIndexMap.put("ZYZZ_RECOMMEND", 10);
		containerProductIndexMap.put("GNY_RECOMMEND", 10);
		containerProductIndexMap.put("ZBY_RECOMMEND", 10);
		containerProductIndexMap.put("CJY_RECOMMEND", 10);
		containerProductIndexMap.put("KXLX", 10);

		if (StringUtils.isNotEmpty(getSubProductType())) {
			containerProductIndexMap.put("GNY_RECOMMEND_GROUP_LONG", 0);
			containerProductIndexMap.put("GNY_RECOMMEND_FREENESS_LONG", 0);
			containerProductIndexMap.put("ZBY_RECOMMEND_GROUP", 0);
			containerProductIndexMap.put("ZBY_RECOMMEND_SELFHELP_BUS", 0);
			containerProductIndexMap.put("CJY_RECOMMEND_GROUP_FOREIGN", 0);
			containerProductIndexMap.put("CJY_RECOMMEND_FREENESS_FOREIGN", 0);
			containerProductChannelIndex = containerProductIndexMap.get(containerCode + "_" + getSubProductType());
		} else {
			containerProductChannelIndex = containerProductIndexMap.get(containerCode);
		}

		if (containerProductIndex < containerProductChannelIndex) {
			if ("".equals(listitem.getId())) {
				listitem.setId(String.valueOf(containerProductIndex));
			}
			listitem.setStyle("background:#C7E8FB;color:#3E3E3E;");
		}
		containerProductIndex++;
	}

	public void containerProductShowOrHide(final Button restoreButton) {
		final Long containerProductId = (Long) restoreButton.getAttribute("containerProductId");
		final String containerProductIsValid = (String) restoreButton.getAttribute("containerProductIsValid");
		String isValidStr;
		if ("Y".equals(restoreButton.getAttribute("containerProductIsValid"))) {
			isValidStr = "是否确定设置该产品 前台隐藏 ";
		} else {
			isValidStr = "是否确定设置该产品 前台显示";
		}
		try {
			Messagebox.show(isValidStr, "Question", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
				@Override
				public void onEvent(Event evt) throws Exception {
					String isValid = "Y";
					if ("Y".equals(containerProductIsValid)) {
						isValid = "N";
					}
					if (evt.getName().equals("onOK")) {
						prodContainerProductService.containerProductShowOrHide(containerProductId, isValid, getOperatorName());
						refreshComponent("queryButton");
					}
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
			ZkMessage.showError("setting failed!");
		}
	}

	public void showOrHideButtOnCreate(Button showOrHideButton) {
		String isValidCheck = showOrHideButton.getAttribute("containerProductIsValid").toString();
		if ("Y".equals(isValidCheck)) {
			showOrHideButton.setLabel("前台隐藏");
		} else {
			showOrHideButton.setLabel("前台显示");
		}
	}

	public List<SubProductType> getSubProductTypeList() {
		if (subProductTypeMap == null) {
			subProductTypeMap = new HashMap<String, List<SubProductType>>();

			List<SubProductType> gnyProductTypeList = new ArrayList<SubProductType>();
			List<SubProductType> zbyProductTypeList = new ArrayList<SubProductType>();
			List<SubProductType> cjyProductTypeList = new ArrayList<SubProductType>();

			gnyProductTypeList.add(new SubProductType("全部", ""));// 全部
			gnyProductTypeList.add(new SubProductType("长途跟团游", "GROUP_LONG"));
			gnyProductTypeList.add(new SubProductType("长途自由行", "FREENESS_LONG"));
			subProductTypeMap.put("GNY_RECOMMEND", gnyProductTypeList);

			zbyProductTypeList.add(new SubProductType("全部", ""));// 全部
			zbyProductTypeList.add(new SubProductType("短途跟团游", "GROUP"));
			zbyProductTypeList.add(new SubProductType("自助巴士班", "SELFHELP_BUS"));
			subProductTypeMap.put("ZBY_RECOMMEND", zbyProductTypeList);

			cjyProductTypeList.add(new SubProductType("全部", ""));// 全部
			cjyProductTypeList.add(new SubProductType("出境跟团游", "GROUP_FOREIGN"));
			cjyProductTypeList.add(new SubProductType("出境自由行", "FREENESS_FOREIGN"));
			subProductTypeMap.put("CJY_RECOMMEND", cjyProductTypeList);
		}
		return subProductTypeMap.get(containerCode);
	}

	public void setProdContainerProductService(ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}

	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
		this.fromPlaceId = null;
		this.destId = null;
		this.toPlaceId = null;
		this.subProductType = null;
		this.sortType = null;
	}

	public String getContainerCode() {
		return containerCode;
	}

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
		this.toPlaceId = destId;
	}

	public void setToPlaceId(String toPlaceId) {
		this.toPlaceId = toPlaceId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setSortType(String sortType) {
		this.sortType = Integer.valueOf(sortType);
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void setContainerProductIndex() {
		containerProductIndex = 0;
	}
}
