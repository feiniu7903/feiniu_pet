/**
 * 
 */
package com.lvmama.comm.vo.comment;

/**
 * 景点点评对象
 * @author liuyi
 *
 */
public class PlaceCmtCommentVO  extends CommonCmtCommentVO  implements java.io.Serializable {
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -7184166985142538075L;

	/**
	 * 景区名称
	 */
	private String placeName;
	/**
	 * place的类型
	 */
	private String stage;

	/**
	 * 带逻辑的拼音URL
	 */
	private String pinYinUrl;	

	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 景点类型
	 * @return 景点类型中文名
	 */
	public String getChStage() {
		if ("1".equals(stage)) {
			return "目的地";
		}
		if ("2".equals(stage)) {
			return "景点";
		}
		if ("3".equals(stage)) {
			return "酒店";
		}
		return "";
	}

//	/**
//	 *  获取主题名称
//	 * @return String
//	 */
//	public String getSubjectName(){
//		return (getPlaceName() != null ? getPlaceName() : "") + (getProductName() != null ? getProductName() : "");
//	}
//	
//	/**
//	 *  获取主题类型
//	 * @return String
//	 */
//	public String getSubjectType(){
//		return (getChStage() != null ? getChStage() : "") + (getChProductType() != null ? getChProductType() : "");
//	}
	
	
	/**
	 *  获取主题名称
	 * @return String
	 */
	public String getSubjectName(){
		return (getPlaceName() != null ? getPlaceName() : "");
	}
	
	/**
	 *  获取主题类型
	 * @return String
	 */
	public String getSubjectType(){
		return (getChStage() != null ? getChStage() : "");
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPinYinUrl() {
		return pinYinUrl;
	}
	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}
	
}
