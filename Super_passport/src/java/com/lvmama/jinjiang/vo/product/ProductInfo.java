package com.lvmama.jinjiang.vo.product;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.MultiMap;

import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.utils.DateUtil;

/**
 * 供应商产品基本信息
 * @author gaoxin
 *
 */
public class ProductInfo {
	/** lvmama销售产品Id*/
	private Long lvmamaProdId;
	/**供应商产品Id */
	private String supplierProdId;
	/** 供应商产品名称 */
	private String supplierProdName;
	/**是否上线 */
	private boolean online = true;
	/**儿童是否占库存*/
	private String childConsumeStock;
	/**婴儿是否占库存*/
	private String infantConsumeStock;
	
	/**行程天数 */
	private Long dayCount;
	/**出发地 */
	private String departCity;
	/**目的地*/
	private String destinationCity;
	/** 行程 */
	private List<ViewJourney> journeys;
	/** 描述内容*/
	private List<ViewContent> contents;
	/** 是否已入库 */
	private boolean valid;
	/** 产品特色 */
	private String features;
	/** 费用包含 */
	private String costcontain;
	/** 费用不包含 */
	private String nocostcontain;
	/** 推荐项目 */
	private String recommendproject;
	/** 产品经理推荐 */
	private String managerrecommend;
	/** 预订须知 */
	private String ordertoknown;
	/** 行前须知 */
	private String acitontoknow;
	/** 游玩提示 */
	private String playpointout;
	/** 内部提示 */
	private String interior;
	/** 图片地址列表 */
	private List<String> pictureList;
	/** 最小成团人数*/
	private String groupMin;
	/**是否境外产品*/
	private boolean foreign;
	/**上线时间*/
	private Date onlineTime;
	/**下线时间*/
	private Date offlineTime;
	/**签证国家*/
	private String visaCountry;
	
	/**
	 * 待定
	 */
	/** 公告 */
	private String announcement;
	/** 交通信息 */
	private String trafficinfo;
	/** 服务保障 */
	private String serviceguarantee;
	/** 购物说明 */
	private String shoppingexplain;
	
	public String getSupplierProdName() {
		return supplierProdName;
	}

	public void setSupplierProdName(String supplierProdName) {
		this.supplierProdName = supplierProdName;
	}

	public Long getDayCount() {
		return dayCount;
	}

	public void setDayCount(Long dayCount) {
		this.dayCount = dayCount;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getSupplierProdId() {
		return supplierProdId;
	}

	public void setSupplierProdId(String supplierProdId) {
		this.supplierProdId = supplierProdId;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public List<ViewJourney> getJourneys() {
		return journeys;
	}

	public void setJourneys(List<ViewJourney> journeys) {
		this.journeys = journeys;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getCostcontain() {
		return costcontain;
	}

	public void setCostcontain(String costcontain) {
		this.costcontain = costcontain;
	}

	public String getNocostcontain() {
		return nocostcontain;
	}

	public void setNocostcontain(String nocostcontain) {
		this.nocostcontain = nocostcontain;
	}

	public String getRecommendproject() {
		return recommendproject;
	}

	public void setRecommendproject(String recommendproject) {
		this.recommendproject = recommendproject;
	}

	public String getShoppingexplain() {
		return shoppingexplain;
	}

	public void setShoppingexplain(String shoppingexplain) {
		this.shoppingexplain = shoppingexplain;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public String getManagerrecommend() {
		return managerrecommend;
	}

	public void setManagerrecommend(String managerrecommend) {
		this.managerrecommend = managerrecommend;
	}

	public String getOrdertoknown() {
		return ordertoknown;
	}

	public void setOrdertoknown(String ordertoknown) {
		this.ordertoknown = ordertoknown;
	}

	public String getServiceguarantee() {
		return serviceguarantee;
	}

	public void setServiceguarantee(String serviceguarantee) {
		this.serviceguarantee = serviceguarantee;
	}

	public String getAcitontoknow() {
		return acitontoknow;
	}

	public void setAcitontoknow(String acitontoknow) {
		this.acitontoknow = acitontoknow;
	}

	public String getPlaypointout() {
		return playpointout;
	}

	public void setPlaypointout(String playpointout) {
		this.playpointout = playpointout;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}

	public String getTrafficinfo() {
		return trafficinfo;
	}

	public void setTrafficinfo(String trafficinfo) {
		this.trafficinfo = trafficinfo;
	}

	public List<String> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<String> pictureList) {
		this.pictureList = pictureList;
	}

	public String getGroupMin() {
		return groupMin;
	}

	public void setGroupMin(String groupMin) {
		this.groupMin = groupMin;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public List<ViewContent> getContents() {
		return contents;
	}

	public void setContents(List<ViewContent> contents) {
		this.contents = contents;
	}

	public Long getLvmamaProdId() {
		return lvmamaProdId;
	}

	public void setLvmamaProdId(Long lvmamaProdId) {
		this.lvmamaProdId = lvmamaProdId;
	}

	public boolean isChildConsumeStock() {
		return "Y".equals(childConsumeStock);
	}

	public void setChildConsumeStock(String childConsumeStock) {
		this.childConsumeStock = childConsumeStock;
	}

	public boolean isInfantConsumeStock() {
		return "Y".equals(infantConsumeStock);
	}

	public void setInfantConsumeStock(String infantConsumeStock) {
		this.infantConsumeStock = infantConsumeStock;
	}

	public boolean isForeign() {
		return foreign;
	}

	public void setForeign(boolean foreign) {
		this.foreign = foreign;
	}

	public Date getOnlineTime() {
		return DateUtil.accurateToDay(onlineTime);
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Date getOfflineTime() {
		return DateUtil.accurateToDay(offlineTime);
	}

	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getChildConsumeStock() {
		return childConsumeStock;
	}

	public String getInfantConsumeStock() {
		return infantConsumeStock;
	}

	public String getVisaCountry() {
		return visaCountry;
	}

	public void setVisaCountry(String visaCountry) {
		this.visaCountry = visaCountry;
	}

	
}
