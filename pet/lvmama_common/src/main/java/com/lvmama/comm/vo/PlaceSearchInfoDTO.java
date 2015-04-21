package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
public class PlaceSearchInfoDTO implements Serializable {
	private static final long serialVersionUID = -6039867247679971091L;

	private PlaceSearchInfo placeSearchInfo;
	private List<ProductSearchInfo> productSearchInfoList = new ArrayList<ProductSearchInfo>();
	public List<ProductSearchInfo> getProductSearchInfoList() {
		return productSearchInfoList;
	}
	public void setProductSearchInfoList(
			List<ProductSearchInfo> productSearchInfoList) {
		this.productSearchInfoList = productSearchInfoList;
	}
	public PlaceSearchInfo getPlaceSearchInfo() {
		return placeSearchInfo;
	}
	public void setPlaceSearchInfo(PlaceSearchInfo placeSearchInfo) {
		this.placeSearchInfo = placeSearchInfo;
	}
	
}
