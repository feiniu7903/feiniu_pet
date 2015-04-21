package com.lvmama.comm.pet.po.usertags;

import java.io.Serializable;
import java.util.Date;

public class UserTagsRelationship implements Serializable {
	private static final long serialVersionUID = 1L;
	private long relationshipId;
	//用户标签1
	private long tagsId1;
	//用户标签2
	private long tagsId2;
	//关联关系（数字表示1：同义，2：别名，3：错别字，4：其他）
	private long relationshipType;
	private Date relationshipDate;
	
	private String tagsName1;
	private String tagsName2;
	
	public long getRelationshipId() {
		return relationshipId;
	}
	public void setRelationshipId(long relationshipId) {
		this.relationshipId = relationshipId;
	}
	public long getTagsId1() {
		return tagsId1;
	}
	public void setTagsId1(long tagsId1) {
		this.tagsId1 = tagsId1;
	}
	public long getTagsId2() {
		return tagsId2;
	}
	public void setTagsId2(long tagsId2) {
		this.tagsId2 = tagsId2;
	}
	public long getRelationshipType() {
		return relationshipType;
	}
	public void setRelationshipType(long relationshipType) {
		this.relationshipType = relationshipType;
	}
	public Date getRelationshipDate() {
		return relationshipDate;
	}
	public void setRelationshipDate(Date relationshipDate) {
		this.relationshipDate = relationshipDate;
	}
	public String getTagsName1() {
		return tagsName1;
	}
	public void setTagsName1(String tagsName1) {
		this.tagsName1 = tagsName1;
	}
	public String getTagsName2() {
		return tagsName2;
	}
	public void setTagsName2(String tagsName2) {
		this.tagsName2 = tagsName2;
	}
}
