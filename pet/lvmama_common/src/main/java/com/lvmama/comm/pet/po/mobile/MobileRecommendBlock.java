package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class MobileRecommendBlock  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String blockName;
    private String blockDes;
    private String blockKey;
    private Date createdTime;
    private String blockImageUrl;
    private String isValid = "Y"; // Y or N
    private Long objectId;
    private Long objectType;
    private Long parentId;    // 父节点id
	private String pageChannel; // 对应channel 
	private String blockType; // 推荐类别(1：内容推荐，2目的地推荐3，产品推荐，4：其它)
	/**
	 * 该字段被占用 ，用来存放pinyin字段 
	 */
	private String reserve1; // 备用字段1 

	
	private String reserve2; // 备用字段2 
	private String reserve3; // 备用字段3 
	private String reserve4; // 备用字段4 
	private String reserve5; // 备用字段5 
	private Long seqNum;// 排序号 
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName == null ? null : blockName.trim();
    }

    public String getBlockDes() {
        return blockDes;
    }

    public void setBlockDes(String blockDes) {
        this.blockDes = blockDes == null ? null : blockDes.trim();
    }

    public String getBlockKey() {
        return blockKey;
    }

    public void setBlockKey(String blockKey) {
        this.blockKey = blockKey == null ? null : blockKey.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getBlockImageUrl() {
        return blockImageUrl;
    }

    public void setBlockImageUrl(String blockImageUrl) {
        this.blockImageUrl = blockImageUrl == null ? null : blockImageUrl.trim();
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? "Y" : isValid.trim();
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getObjectType() {
        return objectType;
    }

    public void setObjectType(Long objectType) {
        this.objectType = objectType;
    }
    
    public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPageChannel() {
		return pageChannel;
	}

	public void setPageChannel(String pageChannel) {
		this.pageChannel = pageChannel;
	}

    public String getBlockType() {
		return blockType;
	}

	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public String getReserve4() {
		return reserve4;
	}

	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}

	public String getReserve5() {
		return reserve5;
	}

	public void setReserve5(String reserve5) {
		this.reserve5 = reserve5;
	}
	public Long getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}
}