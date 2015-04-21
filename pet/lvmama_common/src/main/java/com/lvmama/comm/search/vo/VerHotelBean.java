package com.lvmama.comm.search.vo;

import java.io.Serializable;

/**
 * 酒店对象(酒店页显示对象)
 * 
 */
public class VerHotelBean implements Serializable {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 5225422785714122094L;

	private String hotel_id;

	private String hotel_name;

	private String hotel_adress;

	private String hoteltopic;

	private String addressenglish;

	private String hotelstar;

	private String hotelbrand;

	private String hotelimage;

	private String enname;

	private Float minproductsprice;

	private Float maxproductsprice;

	private String city;

	private String provinceid;

	private String cityid;

	private String province;

	private String district;

	private String railwaystation;

	private String subwaystation;

	private String minroomtime;

	private String maxroomtime;

	private String roommun;

	private String sale_status;

	private String districtid;

	private String longitude;

	private String latitude;

	private String baidu_geo;

	private String product_time;

	private String hotelbrandid;

	private String returnmoney;

	private String issale;

	private String surrondings;

	private String district_type;

	private String recommedlevel;

	private String hassalecommodity;

	private String room_type;

	private String facilities;

	private String facilities_name;

	private String hotel_pic;

	private String photo_content;
	
	private String effect_times;

	private String max_stay_day;

	private String min_stay_day;

	public String getPhoto_content() {
		return photo_content;
	}

	public void setPhoto_content(String photo_content) {
		this.photo_content = photo_content;
	}

	// 排序用的score
	private Float score;
	// 离搜索框 目的地距离 临时
	private Double distance;
	// normal score
	private Double normalscore;

	public String getHotel_id() {
		if (null != hotel_id) {
			return hotel_id;
		} else {
			return "";
		}
	}

	public void setHotel_id(String hotel_id) {
		this.hotel_id = hotel_id;
	}

	public String getHotel_name() {
		if (null != hotel_name) {
			return hotel_name;
		} else {
			return "";
		}
	}

	public void setHotel_name(String hotel_name) {
		this.hotel_name = hotel_name;
	}

	public String getHotel_adress() {
		if (null != hotel_adress) {
			return hotel_adress;
		} else {
			return "";
		}
	}

	public void setHotel_adress(String hotel_adress) {
		this.hotel_adress = hotel_adress;
	}

	public String getHoteltopic() {
		if (null != hoteltopic) {
			return hoteltopic;
		} else {
			return "";
		}
	}

	public void setHoteltopic(String hoteltopic) {
		this.hoteltopic = hoteltopic;
	}

	public String getAddressenglish() {
		if (null != addressenglish) {
			return addressenglish;
		} else {
			return "";
		}
	}

	public void setAddressenglish(String addressenglish) {
		this.addressenglish = addressenglish;
	}

	public String getHotelstar() {
		if (null != hotelstar) {
			return hotelstar;
		} else {
			return "";
		}
	}

	public void setHotelstar(String hotelstar) {
		this.hotelstar = hotelstar;
	}

	public String getHotelbrand() {
		if (null != hotelbrand) {
			return hotelbrand;
		} else {
			return "";
		}
	}

	public void setHotelbrand(String hotelbrand) {
		this.hotelbrand = hotelbrand;
	}

	public String getHotelimage() {
		if (null != hotelimage) {
			return hotelimage;
		} else {
			return "";
		}
	}

	public void setHotelimage(String hotelimage) {
		this.hotelimage = hotelimage;
	}

	public String getEnname() {
		if (null != enname) {
			return enname;
		} else {
			return "";
		}
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public Float getMinproductsprice() {
		if (null != minproductsprice) {
			return minproductsprice;
		} else {
			return 0F;
		}
	}

	public void setMinproductsprice(Float minproductsprice) {
		this.minproductsprice = minproductsprice;
	}

	public Float getMaxproductsprice() {
		if (null != maxproductsprice) {
			return maxproductsprice;
		} else {
			return 0F;
		}
	}

	public void setMaxproductsprice(Float maxproductsprice) {
		this.maxproductsprice = maxproductsprice;
	}

	public String getCity() {
		if (null != city) {
			return city;
		} else {
			return "";
		}
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvinceid() {
		if (null != provinceid) {
			return provinceid;
		} else {
			return "";
		}
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getCityid() {
		if (null != cityid) {
			return cityid;
		} else {
			return "";
		}
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getProvince() {
		if (null != province) {
			return province;
		} else {
			return "";
		}
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		if (null != district) {
			return district;
		} else {
			return "";
		}
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRailwaystation() {
		if (null != railwaystation) {
			return railwaystation;
		} else {
			return "";
		}
	}

	public void setRailwaystation(String railwaystation) {
		this.railwaystation = railwaystation;
	}

	public String getSubwaystation() {
		if (null != subwaystation) {
			return subwaystation;
		} else {
			return "";
		}
	}

	public void setSubwaystation(String subwaystation) {
		this.subwaystation = subwaystation;
	}

	public String getMinroomtime() {
		if (null != minroomtime) {
			return minroomtime;
		} else {
			return "";
		}
	}

	public void setMinroomtime(String minroomtime) {
		this.minroomtime = minroomtime;
	}

	public String getMaxroomtime() {
		if (null != maxroomtime) {
			return maxroomtime;
		} else {
			return "";
		}
	}

	public void setMaxroomtime(String maxroomtime) {
		this.maxroomtime = maxroomtime;
	}

	public String getRoommun() {
		if (null != roommun) {
			return roommun;
		} else {
			return "";
		}
	}

	public void setRoommun(String roommun) {
		this.roommun = roommun;
	}

	public String getSale_status() {
		if (null != sale_status) {
			return sale_status;
		} else {
			return "";
		}
	}

	public void setSale_status(String sale_status) {
		this.sale_status = sale_status;
	}

	public String getDistrictid() {
		if (null != districtid) {
			return districtid;
		} else {
			return "";
		}
	}

	public void setDistrictid(String districtid) {
		this.districtid = districtid;
	}

	public String getLongitude() {
		if (null != longitude) {
			return longitude;
		} else {
			return "";
		}
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		if (null != latitude) {
			return latitude;
		} else {
			return "";
		}
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getBaidu_geo() {
		if (null != baidu_geo) {
			return baidu_geo;
		} else {
			return "";
		}
	}

	public void setBaidu_geo(String baidu_geo) {
		this.baidu_geo = baidu_geo;
	}

	public String getProduct_time() {
		if (null != product_time) {
			return product_time;
		} else {
			return "";
		}
	}

	public void setProduct_time(String product_time) {
		this.product_time = product_time;
	}

	public String getHotelbrandid() {
		if (null != hotelbrandid) {
			return hotelbrandid;
		} else {
			return "";
		}
	}

	public void setHotelbrandid(String hotelbrandid) {
		this.hotelbrandid = hotelbrandid;
	}

	public String getReturnmoney() {
		if (null != returnmoney) {
			return returnmoney;
		} else {
			return "";
		}
	}

	public void setReturnmoney(String returnmoney) {
		this.returnmoney = returnmoney;
	}

	public String getIssale() {
		if (null != issale) {
			return issale;
		} else {
			return "";
		}
	}

	public void setIssale(String issale) {
		this.issale = issale;
	}

	public String getSurrondings() {
		if (null != surrondings) {
			return surrondings;
		} else {
			return "";
		}
	}

	public void setSurrondings(String surrondings) {
		this.surrondings = surrondings;
	}

	public String getDistrict_type() {
		if (null != district_type) {
			return district_type;
		} else {
			return "";
		}
	}

	public void setDistrict_type(String district_type) {
		this.district_type = district_type;
	}

	public Float getScore() {
		if (null != score) {
			return score;
		} else {
			return 0F;
		}
	}

	public void setScore(Float score) {
		this.score = score;
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

	public String getRecommedlevel() {
		if (null != recommedlevel) {
			return recommedlevel;
		} else {
			return "";
		}
	}

	public void setRecommedlevel(String recommedlevel) {
		this.recommedlevel = recommedlevel;
	}

	public String getHassalecommodity() {
		if (null != hassalecommodity) {
			return hassalecommodity;
		} else {
			return "";
		}
	}

	public void setHassalecommodity(String hassalecommodity) {
		this.hassalecommodity = hassalecommodity;
	}

	public Double getNormalscore() {
		if (null != normalscore) {
			return normalscore;
		} else {
			return 0D;
		}
	}

	public void setNormalscore(Double normalscore) {
		this.normalscore = normalscore;
	}

	public String getRoom_type() {
		if (null != room_type) {
			return room_type;
		} else {
			return "";
		}
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public String getFacilities() {
		if (null != facilities) {
			return facilities;
		} else {
			return "";
		}
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public String getFacilities_name() {
		if (null != facilities_name) {
			return facilities_name;
		} else {
			return "";
		}
	}

	public void setFacilities_name(String facilities_name) {
		this.facilities_name = facilities_name;
	}

	public String getHotel_pic() {
		if (null != hotel_pic) {
			return hotel_pic;
		} else {
			return "";
		}
	}

	public void setHotel_pic(String hotel_pic) {
		this.hotel_pic = hotel_pic;
	}

	public String getEffect_times() {
		if (null != effect_times) {
			return effect_times;
		} else {
			return "";
		}
	}

	public void setEffect_times(String effect_times) {
		this.effect_times = effect_times;
	}

	public String getMax_stay_day() {
		if (null != max_stay_day) {
			return max_stay_day;
		} else {
			return "";
		}
	}

	public void setMax_stay_day(String max_stay_day) {
		this.max_stay_day = max_stay_day;
	}

	public String getMin_stay_day() {
		if (null != min_stay_day) {
			return min_stay_day;
		} else {
			return "";
		}
	}

	public void setMin_stay_day(String min_stay_day) {
		this.min_stay_day = min_stay_day;
	}

}
