package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProdContainer implements Serializable {
	private static final long serialVersionUID = 9000815407203095594L;

	private Long id;
	private String containerName;// 容器名称
	private String containerCode;// 容器代码
	private Long fromPlaceId;// 出发地ID
	private String toPlaceId;// 目的地ID
	private String fromPlaceName;// 出发地名称
	private String toPlaceName;// 目的地名称
	private String destId;// 上级目的地ID
	private Integer toPlaceSeq;// 目的地排序
	private String ipLocationId;// 当目的地为省时：存usr_capital表的capital_id；当目的地为市时：存usr_city表的city_id
	private String isToPlaceHidden;// 是否隐藏目的地
	private String isShownInMore;// 是否显示在更多里
	private String zoneName;// 区域名称，如：华东、华北
	private Integer zoneSeq;// 区域排序
	private String displayedToPlaceName;// 目的地显示在频道页前台的显示名称
	private String referredFromPlaceId; //引用同目的地下另一个出发地的产品

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getContainerId() {
		return id;
	}

	public void setContainerId(Long containerId) {
		this.id = containerId;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public String getContainerCode() {
		return containerCode;
	}

	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
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

	public void setToPlaceId(String toPlaceId) {
		this.toPlaceId = toPlaceId;
	}

	public String getFromPlaceName() {
		return fromPlaceName;
	}

	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}

	public String getToPlaceName() {
		return toPlaceName;
	}

	public void setToPlaceName(String toPlaceName) {
		this.toPlaceName = toPlaceName;
	}

	public String getDestId() {
		return destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public Integer getToPlaceSeq() {
		return toPlaceSeq;
	}

	public void setToPlaceSeq(Integer toPlaceSeq) {
		this.toPlaceSeq = toPlaceSeq;
	}

	public String getIpLocationId() {
		return ipLocationId;
	}

	public void setIpLocationId(String ipLocationId) {
		this.ipLocationId = ipLocationId;
	}

	public String getIsToPlaceHidden() {
		return isToPlaceHidden;
	}

	public void setIsToPlaceHidden(String isToPlaceHidden) {
		this.isToPlaceHidden = isToPlaceHidden;
	}

	public String getIsShownInMore() {
		return isShownInMore;
	}

	public void setIsShownInMore(String isShownInMore) {
		this.isShownInMore = isShownInMore;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getZoneSeq() {
		return zoneSeq;
	}

	public void setZoneSeq(Integer zoneSeq) {
		this.zoneSeq = zoneSeq;
	}

	public String getDisplayedToPlaceName() {
		return displayedToPlaceName;
	}

	public void setDisplayedToPlaceName(String displayedToPlaceName) {
		this.displayedToPlaceName = displayedToPlaceName;
	}

    public String getReferredFromPlaceId() {
        return referredFromPlaceId;
    }

    public void setReferredFromPlaceId(String referredFromPlaceId) {
        this.referredFromPlaceId = referredFromPlaceId;
    }
}
