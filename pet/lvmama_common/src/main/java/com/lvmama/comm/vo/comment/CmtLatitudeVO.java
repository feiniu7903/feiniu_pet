/**
 * 
 */
package com.lvmama.comm.vo.comment;

import java.util.Comparator;

/**
 * @author liuyi
 *
 */
public class CmtLatitudeVO implements java.io.Serializable, Comparator<CmtLatitudeVO> {
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 4551209772142092587L;
	/**
	 * 标识
	 */
	private Long cmtLatitudeId;
	/**
	 * 维度ID
	 */
	private String latitudeId;
	/**
	 * 评分(非常好，较好，好，一般，差)
	 */
	private Integer score;
	/**
	 * 点评ID
	 */
	private Long commentId;
	/**
	 * 维度名称
	 */
	private String latitudeName;
 	
	public String getChScore() {
		if (null == this.getScore()) {
			return "";
		} else {
			switch (this.getScore().intValue()) {
			case 5:
				return "非常好";
			case 4:
				return "较好";
			case 3:
				return "好";
			case 2:
				return "一般";
			case 1:
				return "差";
			default:
				return "";
			}
		}
	}	

	public Long getCmtLatitudeId() {
		return cmtLatitudeId;
	}

	public void setCmtLatitudeId(Long cmtLatitudeId) {
		this.cmtLatitudeId = cmtLatitudeId;
	}

	public String getLatitudeId() {
		return latitudeId;
	}

	public void setLatitudeId(String latitudeId) {
		this.latitudeId = latitudeId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getLatitudeName() {
		return latitudeName;
	}

	public void setLatitudeName(String latitudeName) {
		this.latitudeName = latitudeName;
	}
	
	 public int compare(CmtLatitudeVO arg0, CmtLatitudeVO arg1) {  
	        if(arg0.getLatitudeId().compareTo(arg1.getLatitudeId()) > 0){  
	            return 1;  
	        }else{  
	            return 0;  
	        }  
	    }  

}
