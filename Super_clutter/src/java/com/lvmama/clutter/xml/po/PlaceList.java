package com.lvmama.clutter.xml.po;

import java.util.List;

import com.lvmama.comm.pet.po.client.ClientPlace;

public class PlaceList {
	private String totalPage;
	private String stage;
	private String totalSize;
	private List<ClientPlace> clientPlace;
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public List<ClientPlace> getClientPlace() {
		return clientPlace;
	}
	public void setClientPlace(List<ClientPlace> clientPlace) {
		this.clientPlace = clientPlace;
	}
	public String getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

}
