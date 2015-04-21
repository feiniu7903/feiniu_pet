package com.lvmama.comm.pet.po.seo;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.StringUtil;

public class RecommendInfo implements Serializable{
	private static final long serialVersionUID = 3353066434713131440L;

	private Long recommendInfoId;

    private Long recommendBlockId;

    private Long parentRecommendBlockId;

    private String dataCode;

    private String title;

    private String url;

    private String imgUrl;

    private String memberPrice;

    private String marketPrice;

    private Long num;

    private Long seq;

    private Date createTime;

    private Date updateTime;

    private String recommObjectId;

    private String remark;

    private String cashRefund;

    private String status;

    private String bakWord1;

    private String bakWord2;

    private String bakWord3;

    private String bakWord4;

    private String bakWord5;

    private String bakWord6;

    private String bakWord7;

    private String bakWord8;

    private String bakWord9;

    private String bakWord10;
    
    private Long cmtNum;
    private Long placeId;
    //点评平均分
    private Float cmtAvgScore;
    //酒店星级
    private String hotelStar;

    public Long getRecommendInfoId() {
        return recommendInfoId;
    }

    public void setRecommendInfoId(Long recommendInfoId) {
        this.recommendInfoId = recommendInfoId;
    }

    public Long getRecommendBlockId() {
        return recommendBlockId;
    }

    public void setRecommendBlockId(Long recommendBlockId) {
        this.recommendBlockId = recommendBlockId;
    }

    public Long getParentRecommendBlockId() {
        return parentRecommendBlockId;
    }

    public void setParentRecommendBlockId(Long parentRecommendBlockId) {
        this.parentRecommendBlockId = parentRecommendBlockId;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMemberPrice() {
    	Integer price = 0;
		if (this.memberPrice != null) {
			price = Integer.valueOf(memberPrice);
		}
		return String.valueOf(price / 100);
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getMarketPrice() {
    	Integer price = 0;
		if (this.marketPrice != null) {
			price = Integer.valueOf(marketPrice);
		}
		return String.valueOf(price / 100);
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecommObjectId() {
        return recommObjectId;
    }

    public void setRecommObjectId(String recommObjectId) {
        this.recommObjectId = recommObjectId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCashRefund() {
        return cashRefund;
    }

    public void setCashRefund(String cashRefund) {
        this.cashRefund = cashRefund;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBakWord1() {
        return bakWord1;
    }

    public void setBakWord1(String bakWord1) {
        this.bakWord1 = bakWord1;
    }

    public String getBakWord2() {
        return bakWord2;
    }

    public void setBakWord2(String bakWord2) {
        this.bakWord2 = bakWord2;
    }

    public String getBakWord3() {
    	if(bakWord3!=null){
    		return bakWord3.trim();
    	}else {
    		return bakWord3;
    	}
    }

    public void setBakWord3(String bakWord3) {
    	if(bakWord3==null){
    		this.bakWord3=null;
    	}else {
    		this.bakWord3 = bakWord3.trim();
    	}
    }

    public String getBakWord4() {
        return bakWord4;
    }

    public void setBakWord4(String bakWord4) {
        this.bakWord4 = bakWord4;
    }

    public String getBakWord5() {
        return bakWord5;
    }

    public void setBakWord5(String bakWord5) {
        this.bakWord5 = bakWord5;
    }

    public String getBakWord6() {
        return bakWord6;
    }

    public void setBakWord6(String bakWord6) {
        this.bakWord6 = bakWord6;
    }

    public String getBakWord7() {
        return bakWord7;
    }

    public void setBakWord7(String bakWord7) {
        this.bakWord7 = bakWord7;
    }

    public String getBakWord8() {
        return bakWord8;
    }

    public void setBakWord8(String bakWord8) {
        this.bakWord8 = bakWord8;
    }

    public String getBakWord9() {
        return bakWord9;
    }

    public void setBakWord9(String bakWord9) {
        this.bakWord9 = bakWord9;
    }

    public String getBakWord10() {
        return bakWord10;
    }

    public void setBakWord10(String bakWord10) {
        this.bakWord10 = bakWord10;
    }

	public Long getCmtNum() {
		return cmtNum;
	}

	public void setCmtNum(Long cmtNum) {
		this.cmtNum = cmtNum;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	/**
	 * @return the cmtAvgScore
	 */
	public Float getCmtAvgScore() {
		return cmtAvgScore;
	}

	/**
	 * @param cmtAvgScore the cmtAvgScore to set
	 */
	public void setCmtAvgScore(Float cmtAvgScore) {
		this.cmtAvgScore = cmtAvgScore;
	}

	/**
	 * @return the hotelStar
	 */
	public String getHotelStar() {
		if(StringUtil.isEmptyString(hotelStar)){
			return "0";
		}else{
			return hotelStar.trim();
		}
	}

	/**
	 * @param hotelStar the hotelStar to set
	 */
	public void setHotelStar(String hotelStar) {
		this.hotelStar = hotelStar;
	}
}