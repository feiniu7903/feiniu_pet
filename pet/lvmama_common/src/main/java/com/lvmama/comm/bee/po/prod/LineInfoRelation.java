/**
 * 
 */
package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

/**
 * @author yangbin
 *
 */
public class LineInfoRelation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8768605035688424629L;
	private Long relationId;
	private Long lineInfoId;
	private String lineName;
	public Long getRelationId() {
		return relationId;
	}
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	public Long getLineInfoId() {
		return lineInfoId;
	}
	public void setLineInfoId(Long lineInfoId) {
		this.lineInfoId = lineInfoId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	
	
}
