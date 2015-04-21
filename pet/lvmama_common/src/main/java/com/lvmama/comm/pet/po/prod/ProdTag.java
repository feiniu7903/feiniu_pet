package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProdTag implements Serializable{
	private static final long serialVersionUID = 975305381032981639L;

	private Long tagId;

    private Long tagGroupId;	//标签组ID

    private String tagName;		//标签名
  
    private String tagPinYin;		//标签名
    
    private String tagGroupName;	//标签所属组名
    
    private Long tagSEQ;	//排序
    
    private String description;		//描述
    
    private String cssId;		//样式
    
    /**
     * 用来代替isShow字段进行友好显示
     */
   /* private String strIsShow;
    private String buttonIsShow;*/

	public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getTagGroupId() {
        return tagGroupId;
    }

    public void setTagGroupId(Long tagGroupId) {
        this.tagGroupId = tagGroupId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName.trim();
    }

	public String getTagPinYin() {
		return tagPinYin;
	}

	public void setTagPinYin(String tagPinYin) {
		this.tagPinYin = tagPinYin;
	}

	public String getTagGroupName() {
		return tagGroupName;
	}

	public void setTagGroupName(String tagGroupName) {
		this.tagGroupName = tagGroupName;
	}

	public Long getTagSEQ() {
		return tagSEQ;
	}

	public void setTagSEQ(Long tagSEQ) {
		this.tagSEQ = tagSEQ;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCssId() {
		return cssId;
	}

	public void setCssId(String cssId) {
		this.cssId = cssId;
	}

}