/**
 * 
 */
package com.lvmama.comm.pet.po.seo;

/**
 * 友情链接实体
 * @author nixianjun
 *
 */
public class SeoLinks implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2342884421341546366L;
	
	private Long seoLinksId;
	// place id
	private Long placeId;
	private String linkName;
	private String linkUrl;
	private String location;
	private String remark;
	// place名称
	private String placeName;
	// place类型
 	private String placeStage;
 	private String pinYinUrl;
	/**
	 * @return 获取 seoLinksId
	 */
	public Long getSeoLinksId() {
		return seoLinksId;
	}
	/**
	 * @param seoLinksId the seoLinksId to set
	 */
	public void setSeoLinksId(Long seoLinksId) {
		this.seoLinksId = seoLinksId;
	}
	/**
	 * @return 获取 placeId
	 */
	public Long getPlaceId() {
		return placeId;
	}
	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	/**
	 * @return 获取 linkName
	 */
	public String getLinkName() {
		return linkName;
	}
	/**
	 * @param linkName the linkName to set
	 */
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	/**
	 * @return 获取 linkUrl
	 */
	public String getLinkUrl() {
		return linkUrl;
	}
	/**
	 * @param linkUrl the linkUrl to set
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	/**
	 * @return 获取 location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return 获取 remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return 获取 placeName
	 */
	public String getPlaceName() {
		return placeName;
	}
	/**
	 * @param placeName the placeName to set
	 */
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	/**
	 * @return 获取 placeStage
	 */
	public String getPlaceStage() {
		return placeStage;
	}
	/**
	 * @param placeStage the placeStage to set
	 */
	public void setPlaceStage(String placeStage) {
		this.placeStage = placeStage;
	}
	/**
	 * 为了去重通过（placeid linkUrl）
	 * @return
	 */
	public String getPlaceIdAndUrl(){
		return this.placeId+this.getLinkUrl();
	}
	/**
	 * @return 获取 pinYinUrl
	 */
	public String getPinYinUrl() {
		return pinYinUrl;
	}
	/**
	 * @param pinYinUrl the pinYinUrl to set
	 */
	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}
	
}
