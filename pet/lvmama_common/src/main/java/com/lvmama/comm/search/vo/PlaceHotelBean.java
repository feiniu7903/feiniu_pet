package com.lvmama.comm.search.vo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 酒店对象(酒店页显示对象)
 * 
 */
public class PlaceHotelBean extends PlaceBean {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 5225422785714122094L;
	/**
	 * 酒店主题(逗号隔开)
	 */
	private String hotelTopic;

	/**
	 * 酒店主题List
	 */
	private List<String> hotelTopicList;
	/**
	 * 产品主题(逗号隔开)
	 */
	private String prodTopic;
	/**
	 * 产品主题List
	 */
	private List<String> prodTopicList;
	/**
	 * 产品标签(逗号隔开)
	 */
	private String prodTagsName;

	/**
	 * 酒店星级
	 */
	private String hotelStar;

	/**
	 * 酒店星级
	 */
	private String hotelStarMerge;

	/**
	 * 一句话推荐
	 */
	private String recommendContent;

	/**
	 * 酒店列表中显示的图片(大图;逗号隔开)
	 */
	private String hotelImage;

	/**
	 * 封装后的酒店图片 map中存放了 图片URL,名称,描述 KEY: url 图片URL KEY: name 图片名称 KEY: context
	 * 图片描述
	 */
	private List<Map<String, String>> hotelImageList;
	/**
	 * 酒店图片名称
	 */
	private String hotelImageName;

	/**
	 * 酒店图片描述
	 */
	private String hotelImageContext;
	/**
	 * 图片显示 (大图 or 小图)
	 */
	private String picDisplay;
	/**
	 * 距离查询经纬度的距离 单位:公里
	 */
	private Double distance;
	/**
	 * 团购产品数量
	 */
	private Integer groupProductNum;

	public String getHotelStar() {
		if (null != hotelStar) {
			return hotelStar;
		} else {
			return "";
		}
	}

	public void setHotelStar(String hotelStar) {
		this.hotelStar = hotelStar;
	}

	public String getRecommendContent() {
		if (null != recommendContent) {
			return recommendContent;
		} else {
			return "";
		}
	}

	public void setRecommendContent(String recommendContent) {
		this.recommendContent = recommendContent;
	}

	public String getHotelImage() {
		if (null != hotelImage) {
			return hotelImage;
		} else {
			return "";
		}
	}

	public void setHotelImage(String hotelImage) {
		this.hotelImage = hotelImage;
	}

	public List<String> getHotelTopicList() {
		return hotelTopicList;
	}

	public void setHotelTopicList(List<String> hotelTopicList) {
		this.hotelTopicList = hotelTopicList;
	}

	public String getHotelTopic() {
		if (null != hotelTopic) {
			return hotelTopic;
		} else {
			return "";
		}
	}

	public void setHotelTopic(String hotelTopic) {
		this.hotelTopic = hotelTopic;
	}

	public String getProdTopic() {
		if (null != prodTopic) {
			return prodTopic;
		} else {
			return "";
		}
	}

	public void setProdTopic(String prodTopic) {
		this.prodTopic = prodTopic;
	}

	public List<Map<String, String>> getHotelImageList() {
		return hotelImageList;
	}

	public void setHotelImageList(List<Map<String, String>> hotelImageList) {
		this.hotelImageList = hotelImageList;
	}

	/*
	 * 排序：按照距离排序
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public static class compareDistance implements Comparator<PlaceHotelBean> {
		public int compare(PlaceHotelBean p1, PlaceHotelBean p2) {
			if (p1.distance < p2.distance)
				return -1;
			else
				return 1;
		}
	}

	public Double getDistance() {
		if (null != distance) {
			return distance;
		} else {
			return 0D;
		}
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getPicDisplay() {
		if (null != picDisplay) {
			return picDisplay;
		} else {
			return "";
		}
	}

	public String getHotelStarMerge() {
		if (null != hotelStarMerge) {
			return hotelStarMerge;
		} else {
			return "";
		}
	}

	public void setHotelStarMerge(String hotelStarMerge) {
		this.hotelStarMerge = hotelStarMerge;
	}

	public void setPicDisplay(String picDisplay) {
		this.picDisplay = picDisplay;
	}

	public String getHotelImageName() {
		if (null != hotelImageName) {
			return hotelImageName;
		} else {
			return "";
		}
	}

	public void setHotelImageName(String hotelImageName) {
		this.hotelImageName = hotelImageName;
	}

	public String getHotelImageContext() {
		if (null != hotelImageContext) {
			return hotelImageContext;
		} else {
			return "";
		}
	}

	public Integer getGroupProductNum() {
		if (null != groupProductNum) {
			return groupProductNum;
		} else {
			return 0;
		}
	}

	public void setGroupProductNum(Integer groupProductNum) {
		this.groupProductNum = groupProductNum;
	}

	public void setHotelImageContext(String hotelImageContext) {
		this.hotelImageContext = hotelImageContext;
	}

	public List<String> getProdTopicList() {
		return prodTopicList;
	}

	public void setProdTopicList(List<String> prodTopicList) {
		this.prodTopicList = prodTopicList;
	}

	public String getProdTagsName() {
		if (null != prodTagsName) {
			return prodTagsName;
		} else {
			return "";
		}
	}

	public void setProdTagsName(String prodTagsName) {
		this.prodTagsName = prodTagsName;
	}
}
