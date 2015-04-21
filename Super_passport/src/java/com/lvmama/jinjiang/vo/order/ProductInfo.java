/**
 * 
 */
package com.lvmama.jinjiang.vo.order;

/**
 * @author yangbin
 *
 */
public class ProductInfo {

	private String uniqueId;
	private String teamUniqueId;
	private String takeoffDate;
	private String teamName;
	public String getUniqueId() {
		return uniqueId;
	}
	public String getTeamUniqueId() {
		return teamUniqueId;
	}
	public String getTakeoffDate() {
		return takeoffDate;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public void setTeamUniqueId(String teamUniqueId) {
		this.teamUniqueId = teamUniqueId;
	}
	public void setTakeoffDate(String takeoffDate) {
		this.takeoffDate = takeoffDate;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
	
}
