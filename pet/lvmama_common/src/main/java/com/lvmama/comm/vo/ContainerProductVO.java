package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.List;

public class ContainerProductVO implements Serializable {
	private static final long serialVersionUID = -2200704098348245351L;

	private Long productId;
	private Long seq;
	private String productType;
	private String subProductType;
	private Long fromPlaceId;
	private String toPlaceId;
	private boolean online;
	private boolean offline;
	private String isValid;
	private String channel;
	private List<Long> productTagIds;

	private List<Long> containerIdListBefore;
	private List<Long> containerIdListAfter;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public Long getFromPlaceId() {
		return fromPlaceId;
	}

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

	public String getToPlaceId() {
		return toPlaceId;
	}

	public void setToPlaceIdStr(String toPlaceId) {
		this.toPlaceId = toPlaceId;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public List<Long> getContainerIdListBefore() {
		return containerIdListBefore;
	}

	public void setContainerIdListBefore(List<Long> containerIdListBefore) {
		this.containerIdListBefore = containerIdListBefore;
	}

	public List<Long> getContainerIdListAfter() {
		return containerIdListAfter;
	}

	public void setContainerIdListAfter(List<Long> containerIdListAfter) {
		this.containerIdListAfter = containerIdListAfter;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

    public List<Long> getProductTagIds() {
        return productTagIds;
    }

    public void setProductTagIds(List<Long> productTagIds) {
        this.productTagIds = productTagIds;
    }
}
