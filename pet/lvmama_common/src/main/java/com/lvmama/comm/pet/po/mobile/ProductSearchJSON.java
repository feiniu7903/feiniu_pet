package com.lvmama.comm.pet.po.mobile;

import java.util.List;

import com.lvmama.comm.pet.po.client.ClientProduct;

public class ProductSearchJSON {
	private String productName;
	private List<ClientProduct> datas;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<ClientProduct> getDatas() {
		return datas;
	}
	public void setDatas(List<ClientProduct> datas) {
		this.datas = datas;
	}
	
}
