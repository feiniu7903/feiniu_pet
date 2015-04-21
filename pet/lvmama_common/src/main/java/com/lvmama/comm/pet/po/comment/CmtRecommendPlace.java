package com.lvmama.comm.pet.po.comment;
/**
 * 点评招募ID
 * 备注：系统上线预先用SQL加载10条记录
 * @author yangchen
 */
public class CmtRecommendPlace implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3495249875504814481L;
	/**
	 * 点评招募Id
	 */
	private Long cmtRecommendPlaceId;
	/**
	 * 点评招募景点ID
	 */
	private Long placeId;
	/**
	 * 存入数据的动作
	 */
	private String action;
	
	private String placeName;
	
	private String placeLargeImage;
	
	private String pinYinUrl;

	/**
	 * 将数据改为中文
	 * @return 中文描述
	 */
	public String getZhAction() {
		if ("Y".equals(action)) {
			return "自动";
		} else {
			return "手动";
		}
	}

	public Long getCmtRecommendPlaceId() {
		return cmtRecommendPlaceId;
	}

	public void setCmtRecommendPlaceId(Long cmtRecommendPlaceId) {
		this.cmtRecommendPlaceId = cmtRecommendPlaceId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceLargeImage() {
		return placeLargeImage;
	}

	public void setPlaceLargeImage(String placeLargeImage) {
		this.placeLargeImage = placeLargeImage;
	}

	public String getPinYinUrl() {
		return pinYinUrl;
	}

	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}

}
