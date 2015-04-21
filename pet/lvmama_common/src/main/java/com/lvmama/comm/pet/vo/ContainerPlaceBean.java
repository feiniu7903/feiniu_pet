package com.lvmama.comm.pet.vo;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.prod.ProdContainerFromPlace;

public class ContainerPlaceBean implements Serializable {
	private ProdContainer prodContainer;
	private List<ProdContainer> prodContainerList;
	private String zoneName;
	private ProdContainerFromPlace prodContainerFromPlace;
	public ProdContainer getProdContainer() {
		return prodContainer;
	}
	public void setProdContainer(ProdContainer prodContainer) {
		this.prodContainer = prodContainer;
	}
	public List<ProdContainer> getProdContainerList() {
		return prodContainerList;
	}
	public void setProdContainerList(List<ProdContainer> prodContainerList) {
		this.prodContainerList = prodContainerList;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public ProdContainerFromPlace getProdContainerFromPlace() {
		return prodContainerFromPlace;
	}
	public void setProdContainerFromPlace(
			ProdContainerFromPlace prodContainerFromPlace) {
		this.prodContainerFromPlace = prodContainerFromPlace;
	}

}
