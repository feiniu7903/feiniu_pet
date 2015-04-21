package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;
/**
 * 推荐
 * @author qinzubo
 *
 */
public class MobileRecommendInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String recommendTitle; // 标题 

    private String recommendType; // 推荐类型 

    private String recommendImageUrl;//推荐的图片链接
    
    private String recommendHDImageUrl;//推荐的HD图片链接

    private Long recommendParendId;//父推荐id

    private String recommendContent;//推荐的描述内容

    private Date createdTime = new Date();//创建时间

    private Long recommendBlockId;//所属板块id

    private String isValid="N";//是否有效  ,默认无效 

    private Long objectId;//关联的objectId

    private String objectType;//关联的objectType类型

    private String isNode;//是否是根节点

    private String url;//需要跳转的url
    
    private String hdUrl;//需要跳转的HD url

    private Long seqNum;// 排序号
    
	private Date beginDate; // 开始日期
    private Date endDate; // 结束日期

    private String tag;// 标签 
   
    // from v4.0
	private Double longitude; // 经度
	private Double latitude; // 纬度
	private String price; // 价格 
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return null == latitude?0:latitude;
	}

	public Double getLongitude() {
		return null == longitude?0:longitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Constant.CLIENT_RECOMMEND_TAG
	 */
    public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecommendTitle() {
        return recommendTitle;
    }

    public void setRecommendTitle(String recommendTitle) {
        this.recommendTitle = recommendTitle == null ? null : recommendTitle.trim();
    }

    public String getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(String recommendType) {
        this.recommendType = recommendType == null ? null : recommendType.trim();
    }

    public String getRecommendImageUrl() {
        return recommendImageUrl;
    }

    public void setRecommendImageUrl(String recommendImageUrl) {
        this.recommendImageUrl = recommendImageUrl == null ? null : recommendImageUrl.trim();
    }

	public String getRecommendHDImageUrl() {
		return recommendHDImageUrl;
	}

	public void setRecommendHDImageUrl(String recommendHDImageUrl) {
		this.recommendHDImageUrl = recommendHDImageUrl;
	}

	public Long getRecommendParendId() {
        return recommendParendId;
    }

    public void setRecommendParendId(Long recommendParendId) {
        this.recommendParendId = recommendParendId;
    }

    public String getRecommendContent() {
        return recommendContent;
    }

    public void setRecommendContent(String recommendContent) {
        this.recommendContent = recommendContent == null ? null : recommendContent.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getRecommendBlockId() {
        return recommendBlockId;
    }

    public void setRecommendBlockId(Long recommendBlockId) {
        this.recommendBlockId = recommendBlockId;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long ojbectId) {
        this.objectId = ojbectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType == null ? null : objectType.trim();
    }

    public String getIsNode() {
        return isNode;
    }

    public void setIsNode(String isNode) {
        this.isNode = isNode == null ? null : isNode.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getHdUrl() {
		return hdUrl;
	}

	public void setHdUrl(String hdUrl) {
		this.hdUrl = hdUrl;
	}

	public Long getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Long seqNum) {
        this.seqNum = seqNum;
    }
    
    public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}