package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProdContainerFromPlace implements Serializable {
	private static final long serialVersionUID = 7357849679644248577L;

	private Long id;
	private String fromPlaceCode; // 出发地code，用来定位ip区域范围
	private Integer fromPlaceSeq;// 出发地排序
	private Long blockId;// 出发地对应pc后台推荐块id
	private Long searchBlockId;// 出发地对应pc后台推荐里的搜索块id
	private String isFromPlaceHidden;// 是否隐藏出发地
	private String containerCode;// 容器代码
	private String fromPlaceName;// 出发地名称
	private Long fromPlaceId;// 出发地id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromPlaceCode() {
		return fromPlaceCode;
	}

	public void setFromPlaceCode(String fromPlaceCode) {
		this.fromPlaceCode = fromPlaceCode;
	}

	public Integer getFromPlaceSeq() {
		return fromPlaceSeq;
	}

	public void setFromPlaceSeq(Integer fromPlaceSeq) {
		this.fromPlaceSeq = fromPlaceSeq;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public Long getSearchBlockId() {
		return searchBlockId;
	}

	public void setSearchBlockId(Long searchBlockId) {
		this.searchBlockId = searchBlockId;
	}

	public String getIsFromPlaceHidden() {
		return isFromPlaceHidden;
	}

	public void setIsFromPlaceHidden(String isFromPlaceHidden) {
		this.isFromPlaceHidden = isFromPlaceHidden;
	}

	public String getContainerCode() {
		return containerCode;
	}

	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}

	public String getFromPlaceName() {
		return fromPlaceName;
	}

	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}

	public Long getFromPlaceId() {
		return fromPlaceId;
	}

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}
}
