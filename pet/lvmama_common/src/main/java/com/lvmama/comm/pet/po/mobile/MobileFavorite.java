package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的收藏 
 * @author qinzubo
 *
 */
public class MobileFavorite   implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long userId;// 用户id 

	private Long objectId;

	/**
	 * PLACE("标的"),
	 * PRODUCT("产品"),
	 * GUIDE("攻略");
	 */
    private String objectType;

    private Date createdTime;

    private String objectImageUrl;

    private String objectName;

    private String isValid="Y";
    
	/**
	 * 点评数
	 */
	private Long commentCount = 0L;
	/**
	 * 平均分
	 */
	private Float avgScore = 0F;
	
	/**
	 * 销售价格
	 */
	 private Long sellPrice;
	 
	 /**
	  * 拼音url
	  */
	
	 private String pinYinUrl;
	/**
	 * 获取平均分的区间值
	 * @return float
	 */
	public int getRoundHalfUpOfAvgScore() {
		float avg = getAvgScore();
		int value = 0;
		if (avg == 0) {
			value = 0;
		} else if (avg > 0 && avg <= 0.5) {
			value = 10;
		} else if (avg > 0.5 && avg <= 1) {
			value = 20;
		} else if (avg > 1 && avg <= 1.5) {
			value = 30;
		} else if (avg > 1.5 && avg <= 2) {
			value = 40;
		} else if (avg > 2 && avg <= 2.5) {
			value = 50;
		} else if (avg > 2.5 && avg <= 3) {
			value = 60;
		} else if (avg > 3 && avg <= 3.5) {
			value = 70;
		} else if (avg > 3.5 && avg <= 4) {
			value = 80;
		} else if (avg > 4 && avg <= 4.5) {
			value = 90;
		} else if (avg > 4.5 && avg <= 5) {
			value = 100;
		}
		
		return value;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getObjectImageUrl() {
        return objectImageUrl;
    }

    public void setObjectImageUrl(String objectImageUrl) {
        this.objectImageUrl = objectImageUrl == null ? null : objectImageUrl.trim();
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }
	public void setCommentCount(final Long commentCount) {
		this.commentCount = commentCount;
	}
	public void setAvgScore(final Float avgScore) {
		this.avgScore = avgScore;
	}
	public Long getCommentCount() {
		return commentCount;
	}
	public Float getAvgScore() {
		return avgScore;
	}

	public Long getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getPinYinUrl() {
		return pinYinUrl;
	}

	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}
	public String getPlacePinYinUrl() {
		return "http://www.lvmama.com/dest/"+pinYinUrl;
	}
	public String getGuidePinYinUrl() {
		return "http://www.lvmama.com/guide/place/"+pinYinUrl;
	}
	public Long getSellPriceYuan() {
		if(this.sellPrice!=null&&this.sellPrice>=0){
			return this.sellPrice/100;
		}else {
			return sellPrice;
		}
	}

}