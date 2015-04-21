package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.List;

public class ClientContainProd implements Serializable{

	private static final long serialVersionUID = 111440625468024604L;

	private String containProdtitle;
	
	private List<ClientProduct> clientProductList;


	public List<ClientProduct> getClientProductList() {
		return clientProductList;
	}

	public void setClientProductList(List<ClientProduct> clientProductList) {
		this.clientProductList = clientProductList;
	}

	public String getContainProdtitle() {
		return containProdtitle;
	}

	public void setContainProdtitle(String containProdtitle) {
		this.containProdtitle = containProdtitle;
	}
}
