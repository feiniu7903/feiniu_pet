package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.vo.Constant;

/**
 * 驴途 移动端 from 3.0 推荐信息. 
 * @author qinzubo
 *
 */
public class MobileRecommend implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2657600318509066702L;
	private Long id;
	private String recommendTitle; // 标题 
	private String recommendHDImageUrl;//推荐的HD图片链接
    private String recommendImageUrl;//推荐的图片链接
    private String recommendContent;//推荐的描述内容
    private Long objectId;//关联的objectId
    /**
     * 产品类型。 目的地，酒店，景点等 . 
     */
    private String objectType;//关联的objectType类型()
    private String url;//需要跳转的url
    private String hdUrl;//需要跳转的url
    private Double lat; // 经度
	private Double lon; // 纬度
    private String price;// 价格4.0.0 
    
    private Double juli = 0.0;// 当前位置的距推荐自由行的距离
    
    
    public Double getJuli() {
		return juli;
	}
	public void setJuli(Double juli) {
		this.juli = juli;
	}
	
	public String getJuliStr() {
		if(null == this.juli) {
			return "";
		}
		if(this.juli > 1000) {
			double d = this.juli/1000;
			java.text.DecimalFormat df=new   java.text.DecimalFormat("#0.0"); //格式化成1位小数,小数点后四舍五入
			String width = df.format(d);
			return width+"km";
		} else {
			return this.juli.intValue()+"m";
		}
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
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
		this.recommendTitle = recommendTitle;
	}

	public String getRecommendImageUrl() {
		return recommendImageUrl;
	}

	public void setRecommendImageUrl(String recommendImageUrl) {
		this.recommendImageUrl = recommendImageUrl;
	}

	public String getRecommendHDImageUrl() {
		return recommendHDImageUrl;
	}


	public void setRecommendHDImageUrl(String recommendHDImageUrl) {
		this.recommendHDImageUrl = recommendHDImageUrl;
	}


	public String getAbsoluteRecommendImageUrl() {
		return StringUtils.isEmpty(this.getRecommendImageUrl()) ? Constant.DEFAULT_PIC
				: Constant.getInstance().getPrefixPic() + this.getRecommendImageUrl();
	}
	
	public String getRecommendContent() {
		return recommendContent;
	}

	public void setRecommendContent(String recommendContent) {
		this.recommendContent = recommendContent;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getHdUrl() {
		return hdUrl;
	}


	public void setHdUrl(String hdUrl) {
		this.hdUrl = hdUrl;
	}


	public String getDebugImageUrl(){
		return "http://192.168.0.245"+this.recommendImageUrl;
	}
	
	public String getDebugImageHDUrl(){
		return "http://192.168.0.245"+this.recommendHDImageUrl;
	}
	
	/**
	 * 按距离由近到远排序
	 */
	public static class comparatorBoost implements Comparator<MobileRecommend> {
		public int compare(MobileRecommend o1, MobileRecommend o2) {
			
			MobileRecommend s1 = (MobileRecommend) o1;
			MobileRecommend s2 = (MobileRecommend) o2;
			double result = s1.getJuli() - s2.getJuli();
			if(result > 0.0) {
				return 1;
			}
			
			if(result < 0.0) {
				return -1;
			}
			
			return 0;
		}
	}
}
