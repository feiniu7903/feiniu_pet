package com.lvmama.comm.pet.vo;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;

/**
 * 酒店目的地页面vo
 * @author zuozhengpeng
 *
 */
public class ProductList implements java.io.Serializable{
	private static final long serialVersionUID = 1172114483601072518L;
	/**
	 * 产品列表
	 */
	private List<ProductSearchInfo> productAllList = new ArrayList<ProductSearchInfo>();
	/**
	 * 门票
	 */
	private List<ProductSearchInfo> productTicketList = new ArrayList<ProductSearchInfo>();
	/**
	 * 门票类别列表
	 */
	private List<ProdBranchSearchInfo> prodBranchTicketList = new ArrayList<ProdBranchSearchInfo>();
	/**
	 * 酒店
	 */
	private List<ProductSearchInfo> productHotelList = new ArrayList<ProductSearchInfo>();
	/**
	 * 跟团游
	 */
	private List<ProductSearchInfo> productRouteList = new ArrayList<ProductSearchInfo>();
	/**
	 * 自游行
	 */
	private List<ProductSearchInfo> productSinceList = new ArrayList<ProductSearchInfo>();

	public boolean isNotNull() {
		if (productAllList.size() > 0) {
			return true;
		}
		if (productTicketList.size() > 0) {
			return true;
		}
		if (productHotelList.size() > 0) {
			return true;
		}
		if (productRouteList.size() > 0) {
			return true;
		}
		if (productSinceList.size() > 0) {
			return true;
		}
		return false;
	}

	public List<ProductSearchInfo> getProductAllList() {
		return productAllList;
	}

	public void setProductAllList(List<ProductSearchInfo> productAllList) {
		this.productAllList = productAllList;
	}

	public List<ProductSearchInfo> getProductHotelList() {
		return productHotelList;
	}

	public void setProductHotelList(List<ProductSearchInfo> productHotelList) {
		this.productHotelList = productHotelList;
	}

	public List<ProductSearchInfo> getProductRouteList() {
		return productRouteList;
	}

	public void setProductRouteList(List<ProductSearchInfo> productRouteList) {
		this.productRouteList = productRouteList;
	}

	public List<ProductSearchInfo> getProductSinceList() {
		return productSinceList;
	}

	public void setProductSinceList(List<ProductSearchInfo> productSinceList) {
		this.productSinceList = productSinceList;
	}

	public List<ProductSearchInfo> getProductTicketList() {
		return productTicketList;
	}

	public void setProductTicketList(
			List<ProductSearchInfo> productTicketList) {
		this.productTicketList = productTicketList;
	}

	public List<ProductSearchInfo> getSingelRoomProductList() {
		List<ProductSearchInfo> list = new ArrayList<ProductSearchInfo>();
		if (this.productHotelList.size() != 0) {
			for (ProductSearchInfo productSearchInfo : productHotelList) {
				if (productSearchInfo.getSubProductType().equals(
						"SINGLE_ROOM")) {
					list.add(productSearchInfo);
				}
			}
		}
		return list;
	}

	public List<ProductSearchInfo> getHotelSuitProductList() {
		List<ProductSearchInfo> list = new ArrayList<ProductSearchInfo>();
		if (this.productHotelList.size() != 0) {
			for (ProductSearchInfo productSearchInfo : productHotelList) {
				if (productSearchInfo.getSubProductType().equals(
						"HOTEL_SUIT")) {
					list.add(productSearchInfo);
				}
			}
		}
		return list;
	}

	public List<ProdBranchSearchInfo> getProdBranchTicketList() {
		return prodBranchTicketList;
	}

	public void setProdBranchTicketList(
			List<ProdBranchSearchInfo> prodBranchTicketList) {
		this.prodBranchTicketList = prodBranchTicketList;
	}

	public List<ProductSearchInfo> filterSinceListBySubProductType(
			String subProductType) {
		List<ProductSearchInfo> list = new ArrayList<ProductSearchInfo>();
		if (this.productSinceList.size() != 0) {
			for (ProductSearchInfo productSearchInfo : productSinceList) {
				if (productSearchInfo.getSubProductType().equals(
						subProductType)) {
					list.add(productSearchInfo);
				}
			}
		}
		return list;
	}
}
