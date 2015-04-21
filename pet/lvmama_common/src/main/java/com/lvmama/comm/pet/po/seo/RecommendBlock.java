package com.lvmama.comm.pet.po.seo;

import java.io.Serializable;

public class RecommendBlock implements Serializable{
	private static final long serialVersionUID = 7768900192532772661L;

	private Long recommendBlockId;

    private String modeType;

    private String name;

    private Long parentRecommendBlockId;

    private Long itemNumberLimit;

    private Long levels;

    private String pageChannel;

    private String dataCode;

    public Long getRecommendBlockId() {
        return recommendBlockId;
    }

    public void setRecommendBlockId(Long recommendBlockId) {
        this.recommendBlockId = recommendBlockId;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(String modeType) {
        this.modeType = modeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentRecommendBlockId() {
        return parentRecommendBlockId;
    }

    public void setParentRecommendBlockId(Long parentRecommendBlockId) {
        this.parentRecommendBlockId = parentRecommendBlockId;
    }

    public Long getItemNumberLimit() {
        return itemNumberLimit;
    }

    public void setItemNumberLimit(Long itemNumberLimit) {
        this.itemNumberLimit = itemNumberLimit;
    }

    public Long getLevels() {
        return levels;
    }

    public void setLevels(Long levels) {
        this.levels = levels;
    }

    public String getPageChannel() {
        return pageChannel;
    }

    public void setPageChannel(String pageChannel) {
        this.pageChannel = pageChannel;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }
}