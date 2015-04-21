package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.prod.ProdTag;

public class PlaceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 945321433203513892L;
	
	/** id */
	protected String id;
	/** 景区长id */
	protected String placeId;
	/** 名称 */
	protected String name;
	/** 英文名称 */
	protected String enName;
	/** 简介 */
	protected String summary;
	/** 最低价格 */
	protected Long productsPrice;
	/** 最高价格 */
	protected String maxProductsPrice;
	/** 平均分 */
	protected Float avgScore;
	/** 点评数 */
	protected Integer cmtNum;
	/** 可卖产品数 */
	protected String productsType;
	/** 攻略数 */
	protected String gonglueNum;
	/** 销售优惠 */
	protected String saleFavourable = "";
	/** 内容 */
	protected String content;
	/** 权重 */
	protected Integer boost;
	/** 图片 */
	protected String imageUrl;
	/** 省份 */
	protected String province;
	/** 城市 */
	protected String city;
	/** 主题 */
	protected String topic;
	/** 折扣 */
	protected String agio;
	/** 支付方式 */
	protected String payMethod;
	/** 优惠方式 */
	protected String coupon;
	/** 是否推荐景区 */
	protected String isRecommend;
	/** 城市编号 */
	protected String cityId;
	/** 省份编号 */
	protected String provinceId;
	/** 景区图片 */
	protected String smallImage;
	/** 产品总数 */
	protected Long productNum;
	/** 门票总数 */
	protected Long ticketNum;
	/** 酒店总数 */
	protected Long hotleNum;
	/** 自由行总数 */
	protected Long freenessNum;
	/** 线路总数 */
	protected Long routeNum;
	/** 类型 */
	protected String stage;
	/** 拼音url */
	protected String pinYinUrl;
	/** 省 url */
	protected String provincePinYinUrl;
	/** 城市url */
	protected String cityPinYinUrl;
	/** 高频搜索关键字 */
	protected String hfkw;
	/** 拼音 */
	protected String pinYin;

	protected String address;

	protected String price;

	protected String seq;

	protected String hasHotel;
	/** 所有的周边景区的名称的集合,分割 */
	protected String roundPlaceName;

	protected String lpUrl;

	protected String destSubjects;
	/**
	 * 标签名称,多个标签以 , 分隔
	 * */
	protected String destTagsName;
	/**
	 * 标签描述,多个标签以 , 分隔
	 * */
	protected String destTagsDescript;
	/**
	 * 标签小组名称,多个标签以 , 分隔
	 * */
	protected String destTagsGroup;
	/**
	 * 标签Class样式名称,多个标签以 , 分隔
	 * */
	protected String destTagsCss;

	/**
	 * 所有的标签列表
	 */
	protected List<ProdTag> tagList = new ArrayList<ProdTag>();
	/**
	 * 标签按照标签组分类
	 */
	protected Map<String, List<ProdTag>> tagGroupMap = new HashMap<String, List<ProdTag>>();

	/** 自由自在 */
	protected Long destFreenessNum;
	/** 周边游 */
	protected Long destPeripheryNum;
	/** 出境游 */
	protected Long destAbroadNum;
	/** 国内游 */
	protected Long destInternalNum;
	/** 关联产品外键 */
	protected String shortId;
	/** 经纬度 */
	protected Double longitude;
	/** 经纬度 */
	protected Double latitude;
	/** 上级城市ID */
	protected String destId;
	/** 是否手机端展示 */
	protected String isClient;
	/** 标地类型 */
	protected String placeType;
	protected String middleImage;
	protected Long marketPrice;
	protected String sellPrice;
	protected String placeMainTitel;
	protected String placeTitel;
	protected String destPresentStr;
	protected String destNameIds;
	/** 景点活动 **/
	protected String placeActivity;
	/** 是否包含景点活动 */
	protected Integer placeActivityHave;
	/** 一周销量统计数 **/
	protected Long weekSales;
	protected Date todayOrderAbleTime;
	/**
	 * 奖金返现(单位:元)
	 * */
	private String cashRefund;

	private String updated_date;
	/** lucene计算的文档分数 **/
	private Float score;

	private Float hbasescore;// hbase的分数
	
	private Float normalscore;// 初始的分数

	private Float subTypeSale;// 子类别

	private Float salePer;// 销量比值

	private Float midSalePer;// 中值销量比值

	private Float tagnum;// 标签数量

	private Float subTypeMaxTagNum;// 子类别最大标签数

	private Float tagratio;// 标签比值

	private Float realWeekSales;// 真实销量

	private Date todayOrderLastTime;//最晚可定时间
	
	private String shareweixin; //微信分享标识
	
	public String getId() {
		if (null != id) {
			return id;
		} else {
			return "";
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlaceId() {
		if (null != placeId) {
			return placeId;
		} else {
			return "";
		}
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getName() {
		if (null != name) {
			return name;
		} else {
			return "";
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public synchronized Float getRealWeekSales() {
		if (null != realWeekSales) {
			return realWeekSales;
		} else {
			return 0F;
		}
	}

	public synchronized void setRealWeekSales(Float realWeekSales) {
		this.realWeekSales = realWeekSales;
	}

	public String getSummary() {
		if (null != summary) {
			return summary;
		} else {
			return "";
		}
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public synchronized Float getHbasescore() {
		if (null != hbasescore) {
			return hbasescore;
		} else {
			return 0F;
		}
	}

	public synchronized void setHbasescore(Float hbasescore) {
		this.hbasescore = hbasescore;
	}

	public synchronized Float getMidSalePer() {
		if (null != midSalePer) {
			return midSalePer;
		} else {
			return 0F;
		}
	}

	public synchronized void setMidSalePer(Float midSalePer) {
		this.midSalePer = midSalePer;
	}

	public synchronized Float getSalePer() {
		if (null != salePer) {
			return salePer;
		} else {
			return 0F;
		}
	}

	public synchronized void setSalePer(Float salePer) {
		this.salePer = salePer;
	}

	public synchronized Float getTagratio() {
		if (null != tagratio) {
			return tagratio;
		} else {
			return 0F;
		}
	}

	public synchronized void setTagratio(Float tagratio) {
		this.tagratio = tagratio;
	}

	public synchronized Float getNormalscore() {
		if (null != normalscore) {
			return normalscore;
		} else {
			return 0F;
		}
	}

	public synchronized void setNormalscore(Float normalscore) {
		this.normalscore = normalscore;
	}

	public synchronized Float getSubTypeSale() {
		if (null != subTypeSale) {
			return subTypeSale;
		} else {
			return 0F;
		}
	}

	public synchronized void setSubTypeSale(Float subTypeSale) {
		this.subTypeSale = subTypeSale;
	}

	public synchronized Float getTagnum() {
		if (null != tagnum) {
			return tagnum;
		} else {
			return 0F;
		}
	}

	public synchronized void setTagnum(Float tagnum) {
		this.tagnum = tagnum;
	}

	public synchronized Float getSubTypeMaxTagNum() {
		if (null != subTypeMaxTagNum) {
			return subTypeMaxTagNum;
		} else {
			return 0F;
		}
	}

	public synchronized void setSubTypeMaxTagNum(Float subTypeMaxTagNum) {
		this.subTypeMaxTagNum = subTypeMaxTagNum;
	}

	public Long getProductsPrice() {
		if (null != productsPrice) {
			return productsPrice;
		} else {
			return 0L;
		}
	}

	public void setProductsPrice(Long productsPrice) {
		this.productsPrice = productsPrice;
	}

	public String getMaxProductsPrice() {
		if (null != maxProductsPrice) {
			return maxProductsPrice;
		} else {
			return "0";
		}
	}

	public void setMaxProductsPrice(String maxProductsPrice) {
		this.maxProductsPrice = maxProductsPrice;
	}

	public String getUpdated_date() {
		if (null != updated_date) {
			return updated_date;
		} else {
			return "";
		}
	}

	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}

	public Float getAvgScore() {
		if (null != avgScore) {
			return avgScore;
		} else {
			return 0F;
		}
	}

	public void setAvgScore(Float avgScore) {
		this.avgScore = avgScore;
	}

	public Integer getCmtNum() {
		if (null != cmtNum) {
			return cmtNum;
		} else {
			return 0;
		}
	}

	public synchronized Float getScore() {
		if (null != score) {
			return score;
		} else {
			return 0F;
		}
	}

	public synchronized void setScore(Float score) {
		this.score = score;
	}

	public void setCmtNum(Integer cmtNum) {
		this.cmtNum = cmtNum;
	}

	public String getProductsType() {
		if (null != productsType) {
			return productsType;
		} else {
			return "";
		}
	}

	public void setProductsType(String productsType) {
		this.productsType = productsType;
	}

	public String getGonglueNum() {
		if (null != gonglueNum) {
			return gonglueNum;
		} else {
			return "";
		}
	}

	public void setGonglueNum(String gonglueNum) {
		this.gonglueNum = gonglueNum;
	}

	public String getSaleFavourable() {
		if (null != saleFavourable) {
			return saleFavourable;
		} else {
			return "";
		}
	}

	public void setSaleFavourable(String saleFavourable) {
		this.saleFavourable = saleFavourable;
	}

	public String getContent() {
		if (null != content) {
			return content;
		} else {
			return "";
		}
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getBoost() {
		if (null != boost) {
			return boost;
		} else {
			return 0;
		}
	}

	public void setBoost(Integer boost) {
		this.boost = boost;
	}

	public String getImageUrl() {
		if (null != imageUrl) {
			return imageUrl;
		} else {
			return "";
		}
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public String getTopic() {
		if (null != topic) {
			return topic;
		} else {
			return "";
		}
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getAgio() {
		if (null != agio) {
			return agio;
		} else {
			return "";
		}
	}

	public void setAgio(String agio) {
		this.agio = agio;
	}

	public String getPayMethod() {
		if (null != payMethod) {
			return payMethod;
		} else {
			return "";
		}
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getCoupon() {
		if (null != coupon) {
			return coupon;
		} else {
			return "";
		}
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getIsRecommend() {
		if (null != isRecommend) {
			return isRecommend;
		} else {
			return "";
		}
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getCityId() {
		if (null != cityId) {
			return cityId;
		} else {
			return "";
		}
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceId() {
		if (null != provinceId) {
			return provinceId;
		} else {
			return "";
		}
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getSmallImage() {
		if (null != smallImage) {
			return smallImage;
		} else {
			return "";
		}
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public Long getProductNum() {
		if (null != productNum) {
			return productNum;
		} else {
			return 0L;
		}
	}

	public void setProductNum(Long productNum) {
		this.productNum = productNum;
	}

	public Long getTicketNum() {
		if (null != ticketNum) {
			return ticketNum;
		} else {
			return 0L;
		}
	}

	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}

	public Long getHotleNum() {
		if (null != hotleNum) {
			return hotleNum;
		} else {
			return 0L;
		}
	}

	public void setHotleNum(Long hotleNum) {
		this.hotleNum = hotleNum;
	}

	public Long getFreenessNum() {
		if (null != freenessNum) {
			return freenessNum;
		} else {
			return 0L;
		}
	}

	public void setFreenessNum(Long freenessNum) {
		this.freenessNum = freenessNum;
	}

	public Long getRouteNum() {
		if (null != routeNum) {
			return routeNum;
		} else {
			return 0L;
		}
	}

	public void setRouteNum(Long routeNum) {
		this.routeNum = routeNum;
	}

	public String getStage() {
		if (null != stage) {
			return stage;
		} else {
			return "";
		}
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPinYinUrl() {
		if (null != pinYinUrl) {
			return pinYinUrl;
		} else {
			return "";
		}
	}

	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}

	public String getProvincePinYinUrl() {
		if (null != provincePinYinUrl) {
			return provincePinYinUrl;
		} else {
			return "";
		}
	}

	public void setProvincePinYinUrl(String provincePinYinUrl) {
		this.provincePinYinUrl = provincePinYinUrl;
	}

	public String getCityPinYinUrl() {
		if (null != cityPinYinUrl) {
			return cityPinYinUrl;
		} else {
			return "";
		}
	}

	public void setCityPinYinUrl(String cityPinYinUrl) {
		this.cityPinYinUrl = cityPinYinUrl;
	}

	public String getHfkw() {
		if (null != hfkw) {
			return hfkw;
		} else {
			return "";
		}
	}

	public void setHfkw(String hfkw) {
		this.hfkw = hfkw;
	}

	public String getPinYin() {
		if (null != pinYin) {
			return pinYin;
		} else {
			return "";
		}
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getAddress() {
		if (null != address) {
			return address;
		} else {
			return "";
		}
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrice() {
		if (null != price) {
			return price;
		} else {
			return "";
		}
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSeq() {
		if (null != seq) {
			return seq;
		} else {
			return "";
		}
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getHasHotel() {
		if (null != hasHotel) {
			return hasHotel;
		} else {
			return "";
		}
	}

	public void setHasHotel(String hasHotel) {
		this.hasHotel = hasHotel;
	}

	public String getRoundPlaceName() {
		if (null != roundPlaceName) {
			return roundPlaceName;
		} else {
			return "";
		}
	}

	public void setRoundPlaceName(String roundPlaceName) {
		this.roundPlaceName = roundPlaceName;
	}

	public String getLpUrl() {
		if (null != lpUrl) {
			return lpUrl;
		} else {
			return "";
		}
	}

	public void setLpUrl(String lpUrl) {
		this.lpUrl = lpUrl;
	}

	public String getDestSubjects() {
		if (null != destSubjects) {
			return destSubjects;
		} else {
			return "";
		}
	}

	public void setDestSubjects(String destSubjects) {
		this.destSubjects = destSubjects;
	}

	public String getDestTagsName() {
		if (null != destTagsName) {
			return destTagsName;
		} else {
			return "";
		}
	}

	public void setDestTagsName(String destTagsName) {
		this.destTagsName = destTagsName;
	}

	public Long getDestFreenessNum() {
		if (null != destFreenessNum) {
			return destFreenessNum;
		} else {
			return 0L;
		}
	}

	public void setDestFreenessNum(Long destFreenessNum) {
		this.destFreenessNum = destFreenessNum;
	}

	public Long getDestPeripheryNum() {
		if (null != destPeripheryNum) {
			return destPeripheryNum;
		} else {
			return 0L;
		}
	}

	public void setDestPeripheryNum(Long destPeripheryNum) {
		this.destPeripheryNum = destPeripheryNum;
	}

	public Long getDestAbroadNum() {
		if (null != destAbroadNum) {
			return destAbroadNum;
		} else {
			return 0L;
		}
	}

	public void setDestAbroadNum(Long destAbroadNum) {
		this.destAbroadNum = destAbroadNum;
	}

	public Long getDestInternalNum() {
		if (null != destInternalNum) {
			return destInternalNum;
		} else {
			return 0L;
		}
	}

	public void setDestInternalNum(Long destInternalNum) {
		this.destInternalNum = destInternalNum;
	}

	public String getShortId() {
		if (null != shortId) {
			return shortId;
		} else {
			return "";
		}
	}

	public void setShortId(String shortId) {
		this.shortId = shortId;
	}

	public Double getLongitude() {
		if (null != longitude) {
			return longitude;
		} else {
			return 0D;
		}
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		if (null != latitude) {
			return latitude;
		} else {
			return 0D;
		}
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getDestId() {
		if (null != destId) {
			return destId;
		} else {
			return "";
		}
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getIsClient() {
		if (null != isClient) {
			return isClient;
		} else {
			return "";
		}
	}

	public void setIsClient(String isClient) {
		this.isClient = isClient;
	}

	public String getPlaceType() {
		if (null != placeType) {
			return placeType;
		} else {
			return "";
		}
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getMiddleImage() {
		if (null != middleImage) {
			return middleImage;
		} else {
			return "";
		}
	}

	public void setMiddleImage(String middleImage) {
		this.middleImage = middleImage;
	}

	public Long getMarketPrice() {
		if (null != marketPrice) {
			return marketPrice;
		} else {
			return 0L;
		}
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getSellPrice() {
		if (null != sellPrice) {
			return sellPrice;
		} else {
			return "";
		}
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getPlaceMainTitel() {
		if (null != placeMainTitel) {
			return placeMainTitel;
		} else {
			return "";
		}
	}

	public void setPlaceMainTitel(String placeMainTitel) {
		this.placeMainTitel = placeMainTitel;
	}

	public String getPlaceTitel() {
		if (null != placeTitel) {
			return placeTitel;
		} else {
			return "";
		}
	}

	public void setPlaceTitel(String placeTitel) {
		this.placeTitel = placeTitel;
	}

	public String getDestPresentStr() {
		if (null != destPresentStr) {
			return destPresentStr;
		} else {
			return "";
		}
	}

	public void setDestPresentStr(String destPresentStr) {
		this.destPresentStr = destPresentStr;
	}

	public String getDestNameIds() {
		if (null != destNameIds) {
			return destNameIds;
		} else {
			return "";
		}
	}

	public void setDestNameIds(String destNameIds) {
		this.destNameIds = destNameIds;
	}

	public String getPlaceActivity() {
		if (null != placeActivity) {
			return placeActivity;
		} else {
			return "";
		}
	}

	public void setPlaceActivity(String placeActivity) {
		this.placeActivity = placeActivity;
	}

	public Long getWeekSales() {
		return weekSales;
	}

	public void setWeekSales(Long weekSales) {
		this.weekSales = weekSales;
	}

	public Date getTodayOrderAbleTime() {
		return todayOrderAbleTime;
	}

	public void setTodayOrderAbleTime(Date todayOrderAbleTime) {
		this.todayOrderAbleTime = todayOrderAbleTime;
	}

	/**
	 * 点评数排序比较器
	 * 
	 * @author huangzhi
	 */
	public static class comparatorCmtNum implements Comparator<PlaceBean> {
		public int compare(PlaceBean o1, PlaceBean o2) {
			PlaceBean s1 = (PlaceBean) o1;
			PlaceBean s2 = (PlaceBean) o2;
			Integer result = s2.cmtNum - s1.cmtNum;
			return result;
		}
	}

	/**
	 * 一周销售额排序比较器
	 * 
	 * @author huangzhi
	 */
	public static class comparatorSales implements Comparator<PlaceBean> {
		public int compare(PlaceBean o1, PlaceBean o2) {
			PlaceBean s1 = (PlaceBean) o1;
			PlaceBean s2 = (PlaceBean) o2;
			Integer result = s2.weekSales.intValue() - s1.weekSales.intValue();
			return result;
		}
	}

	/**
	 * 点评分排序比较器
	 * 
	 * @author huangzhi
	 */

	public static class comparatorCmtAvg implements Comparator<PlaceBean> {
		public int compare(PlaceBean o1, PlaceBean o2) {
			PlaceBean s1 = (PlaceBean) o1;
			PlaceBean s2 = (PlaceBean) o2;
			Integer result = (int) (s2.avgScore - s1.avgScore);
			return result;
		}
	}

	/**
	 * seq排序比较器
	 * 
	 * @author huangzhi
	 */
	public static class comparatorChinaTree implements Comparator<PlaceBean> {
		public int compare(PlaceBean o1, PlaceBean o2) {
			PlaceBean s1 = (PlaceBean) o1;
			PlaceBean s2 = (PlaceBean) o2;
			// 只要大于9个9，就为-888888888
			if (!StringUtils.isEmpty(s1.seq) && s1.seq.length() > 9) {
				s1.setSeq(s1.seq.substring(0, 9));
			}

			if (!StringUtils.isEmpty(s2.seq) && s2.seq.length() > 9) {
				s2.setSeq(s2.seq.substring(0, 9));
			}
			Integer result = Integer.parseInt(s1.seq) - Integer.parseInt(s2.seq);
			return result;
		}
	}

	/**
	 * 下拉提示综合SEQ排序比较器
	 * 
	 * @author huangzhi *
	 */
	public static class comparatorAutoCompletePlace implements
			Comparator<PlaceBean> {
		public int compare(PlaceBean o1, PlaceBean o2) {
			PlaceBean s1 = (PlaceBean) o1;
			PlaceBean s2 = (PlaceBean) o2;
			Integer result = s1.seq.compareTo(s2.seq);
			return result;
		}
	}

	/**
	 * 按距离远近排序比较器
	 * 
	 * @author huangzhi
	 */
	public static class comparatorBoost implements Comparator<PlaceBean> {
		public int compare(PlaceBean o1, PlaceBean o2) {
			PlaceBean s1 = (PlaceBean) o1;
			PlaceBean s2 = (PlaceBean) o2;
			Integer result = s1.boost - s2.boost;
			return result;
		}
	}

	/**
	 * 按销售价格降序比较器
	 * 
	 * @author huangzhi *
	 */
	public static class comparatorSellPriceDesc implements
			Comparator<PlaceBean> {
		public int compare(PlaceBean o1, PlaceBean o2) {
			PlaceBean s1 = (PlaceBean) o1;
			PlaceBean s2 = (PlaceBean) o2;
			Integer result = Integer.parseInt(s2.sellPrice)
					- Integer.parseInt(s1.sellPrice);
			return result;
		}
	}

	/**
	 * 按销售价格降序比较器
	 * 
	 * @author huangzhi
	 */
	public static class comparatorSellPriceAsc implements Comparator<PlaceBean> {
		public int compare(PlaceBean o1, PlaceBean o2) {
			PlaceBean s1 = (PlaceBean) o1;
			PlaceBean s2 = (PlaceBean) o2;
			Integer result = Integer.parseInt(s1.sellPrice)
					- Integer.parseInt(s2.sellPrice);
			return result;
		}
	}

	/**
	 * 按拼音降序比较器
	 * 
	 * @author huangzhi
	 */
	public static class comparatorPinyin implements Comparator<PlaceBean> {
		public int compare(PlaceBean o1, PlaceBean o2) {
			PlaceBean s1 = (PlaceBean) o1;
			PlaceBean s2 = (PlaceBean) o2;
			Integer result = (s1.pinYin).compareTo(s2.pinYin);
			return result;
		}
	}

	public Integer getPlaceActivityHave() {
		if (null != placeActivityHave) {
			return placeActivityHave;
		} else {
			return 0;
		}
	}

	public void setPlaceActivityHave(Integer placeActivityHave) {
		this.placeActivityHave = placeActivityHave;
	}

	public String getDestTagsDescript() {
		if (null != destTagsDescript) {
			return destTagsDescript;
		} else {
			return "";
		}
	}

	public boolean canOrderTodayCurrentTimeForPlace() {
		if (this.getTodayOrderAbleTime() == null) {
			return false;
		}
		return new Date().before(this.todayOrderAbleTime);
	}

	public void setDestTagsDescript(String destTagsDescript) {
		this.destTagsDescript = destTagsDescript;
	}

	public String getDestTagsGroup() {
		if (null != destTagsGroup) {
			return destTagsGroup;
		} else {
			return "";
		}
	}

	public void setDestTagsGroup(String destTagsGroup) {
		this.destTagsGroup = destTagsGroup;
	}

	public String getDestTagsCss() {
		if (null != destTagsCss) {
			return destTagsCss;
		} else {
			return "";
		}
	}

	public List<ProdTag> getTagList() {
		return tagList;
	}

	public void setTagList(List<ProdTag> tagList) {
		this.tagList = tagList;
	}

	public Map<String, List<ProdTag>> getTagGroupMap() {
		return tagGroupMap;
	}

	public void setTagGroupMap(Map<String, List<ProdTag>> tagGroupMap) {
		this.tagGroupMap = tagGroupMap;
	}

	public String getEnName() {
		if (null != enName) {
			return enName;
		} else {
			return "";
		}
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public void setDestTagsCss(String destTagsCss) {
		this.destTagsCss = destTagsCss;
	}

	public String getCashRefund() {
		if (null != cashRefund) {
			return cashRefund;
		} else {
			return "";
		}
	}

	public void setCashRefund(String cashRefund) {
		this.cashRefund = cashRefund;
	}

	public Date getTodayOrderLastTime() {
		return todayOrderLastTime;
	}

	public void setTodayOrderLastTime(Date todayOrderLastTime) {
		this.todayOrderLastTime = todayOrderLastTime;
	}
	
	public boolean getTodayOrderAble(){
		if (this.todayOrderLastTime == null) {
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(sdf.format(new Date()).equals(sdf.format(this.todayOrderLastTime)) && new Date().before(this.todayOrderLastTime)){
			return true;
		}
		return false;
	}

	public String getShareweixin() {
		return shareweixin;
	}

	public void setShareweixin(String shareweixin) {
		this.shareweixin = shareweixin;
	}

}
