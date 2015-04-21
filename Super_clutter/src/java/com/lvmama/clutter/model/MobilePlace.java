package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.RefundUtils;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ReplaceEnter;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.PlaceCmtScoreVO;

/**
 * 
 * 移动端专用- 手机标的模型
 * 
 * @author dengcheng
 * 
 */
public class MobilePlace implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*********** 标的基本信息 *********/
	private Long id; // 景点id
	private String name = ""; // 名称
	private String address = ""; // 地址
	private Double baiduLongitude = 0d; // 经度
	private Double baiduLatitude = 0d; // 维度
	private Double googleLongitude = 0d; // 经度
	private Double googleLatitude = 0d; // 维度
	private String cmtNum; // 驴友评论数
	private String cmtStarts; // 驴友评论分数（星数） *new*
	private String description = "";// 介绍
	private String recommendReason = ""; // 推荐理由 *new**
	private List<String> imageList; // 图片
	private boolean hasTicket; // 是否有产品
	private boolean hasRoute;
	private String stage = ""; // 类型 1:城市 2：景点 3：酒店
	private String orderNotice; // 预订须知.
	private String importantTips; // 重要提示
	private Float sellPriceYuan;
	private Float marketPriceYuan;
	private String middleImage;
	private boolean canOrderToday;
	private boolean orderTodayAble;//新今日可订标识
	private boolean shareWeixin;
	private Long freenessNum;
	/**
	 * 离定位的距离 附近搜索有用
	 */
	private String juli;
	private List<PlaceCmtScoreVO> placeCmtScoreList;
	private boolean hasIn = false;// 是否收藏，默认false
	private MobilePlaceAddInfo mpAddInfo;
	private Set<MobileGuaranteeOption> guaranteeOptions = Collections.emptySet();

	/********* v3.1 add *********/
	/**
	 * 主题
	 */
	private String subject;
	/**
	 * 点评返现金额，单位分
	 */
	private Long maxCashRefund = 0l;

	/**
	 * 是否支持多定多惠，早定早惠
	 */
	private boolean hasBusinessCoupon = false;

	/**
	 * 开放时间
	 */
	private String scenicOpenTime;

	private Long routeNum;

	private boolean hasActivity;
	/**交通信息**/
	private String trafficInfo;
	
	private boolean weiXinActivity;

	/**
	 * 景点活动
	 */
	private List<MobilePlaceActivity> placeActivity;

	public List<MobilePlaceActivity> getPlaceActivity() {
		if (null == placeActivity) {
			placeActivity = new ArrayList<MobilePlaceActivity>();
		}
		return placeActivity;
	}

	public void setPlaceActivity(List<MobilePlaceActivity> placeActivity) {
		this.placeActivity = placeActivity;
	}

	public boolean isHasIn() {
		return hasIn;
	}

	public void setHasIn(boolean hasIn) {
		this.hasIn = hasIn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCmtNum() {
		return cmtNum;
	}

	public void setCmtNum(String cmtNum) {
		this.cmtNum = cmtNum;
	}

	public String getCmtStarts() {
		return cmtStarts;
	}

	public void setCmtStarts(String cmtStarts) {
		this.cmtStarts = cmtStarts;
	}

	public String getAvgScore() {
		return cmtStarts;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getOrderNotice() {
		return ClientUtils.filterOutHTMLTags(this.orderNotice);
	}

	public void setOrderNotice(String orderNotice) {
		this.orderNotice = orderNotice;
	}

	public String getImportantTips() {
		return importantTips;
	}

	public void setImportantTips(String importantTips) {
		this.importantTips = importantTips;
	}

	public String getRecommendReason() {
		if (StringUtils.isEmpty(recommendReason))
			return "";
		return "★"
				+ StringUtil.filterOutHTMLTags(recommendReason).replaceAll(
						"\n", "\n★");
	}

	public String getConverRecommendReason() {
		if (StringUtils.isEmpty(getRecommendReason()))
			return "";
		return ReplaceEnter.replaceEnterRn(getRecommendReason(), "");
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}

	public List<PlaceCmtScoreVO> getPlaceCmtScoreList() {
		return placeCmtScoreList;
	}

	public void setPlaceCmtScoreList(List<PlaceCmtScoreVO> placeCmtScoreList) {
		this.placeCmtScoreList = placeCmtScoreList;
	}

	public Double getGoogleLongitude() {
		return googleLongitude;
	}

	public void setGoogleLongitude(Double googleLongitude) {
		this.googleLongitude = googleLongitude;
	}

	public Double getGoogleLatitude() {
		return googleLatitude;
	}

	public void setGoogleLatitude(Double googleLatitude) {
		this.googleLatitude = googleLatitude;
	}

	public Double getBaiduLongitude() {
		return baiduLongitude;
	}

	public void setBaiduLongitude(Double baiduLongitude) {
		this.baiduLongitude = baiduLongitude;
	}

	public Double getBaiduLatitude() {
		return baiduLatitude;
	}

	public void setBaiduLatitude(Double baiduLatitude) {
		this.baiduLatitude = baiduLatitude;
	}

	public Float getSellPriceYuan() {
		return sellPriceYuan;
	}

	public void setSellPriceYuan(Float sellPriceYuan) {
		this.sellPriceYuan = sellPriceYuan;
	}

	public Float getMarketPriceYuan() {
		return marketPriceYuan;
	}

	public void setMarketPriceYuan(Float marketPriceYuan) {
		this.marketPriceYuan = marketPriceYuan;
	}

	public String getMiddleImage() {
		return middleImage;
	}

	public void setMiddleImage(String middleImage) {
		this.middleImage = middleImage;
	}

	public String getAbsoluteMiddleImage() {
		return StringUtils.isEmpty(this.getMiddleImage()) ? Constant.DEFAULT_PIC
				: Constant.getInstance().getPrefixPic() + this.getMiddleImage();
	}

	public boolean isHasRoute() {
		return hasRoute;
	}

	public void setHasRoute(boolean hasRoute) {
		this.hasRoute = hasRoute;
	}

	public boolean isHasTicket() {
		return hasTicket;
	}

	public void setHasTicket(boolean hasTicket) {
		this.hasTicket = hasTicket;
	}

	public String getMoreDetailUrl() {
		return "/html5/index.htm?placeId=" + id;
	}

	public boolean isCanOrderToday() {
		return canOrderToday;
	}

	public void setCanOrderToday(boolean canOrderToday) {
		this.canOrderToday = canOrderToday;
	}

	public String getJuli() {
		return juli;
	}

	public void setJuli(String juli) {
		this.juli = juli;
	}

	public String getSubject() {
		return null == subject ? "" : subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isHasBusinessCoupon() {
		return hasBusinessCoupon;
	}

	public void setHasBusinessCoupon(boolean hasBusinessCoupon) {
		this.hasBusinessCoupon = hasBusinessCoupon;
	}

	public Long getMaxCashRefund() {
		return maxCashRefund;
	}

	public void setMaxCashRefund(Long maxCashRefund) {
		this.maxCashRefund = maxCashRefund;
	}

	/**
	 * 加上了景点多返的
	 * 
	 * @return
	 */
	public Float getCashRefundY() {
		if (!isCashRefund()) {
			return 0f;
		}
		return RefundUtils.getTicketMobileRefundYuan(maxCashRefund);
	}

	/**
	 * 景点多返的
	 * 
	 * @return
	 */
	public Float getMobileCashRefundY() {
		if (!isCashRefund()) {
			return 0f;
		}
		return RefundUtils.getTicketMoreMobileRefundYuan(maxCashRefund);
	}

	/**
	 * 是否支持返现
	 * 
	 * @return
	 */
	public boolean isCashRefund() {
		// 是否支持返现
		return this.maxCashRefund != 0;
	}

	/**
	 * 
	 * @return
	 */
	public String getScenicOpenTime() {
		return null == scenicOpenTime ? "" : scenicOpenTime;
	}

	public void setScenicOpenTime(String scenicOpenTime) {
		this.scenicOpenTime = scenicOpenTime;
	}

	public Long getFreenessNum() {
		return freenessNum;
	}

	public void setFreenessNum(Long freenessNum) {
		this.freenessNum = freenessNum;
	}

	public Long getRouteNum() {
		return routeNum;
	}

	public void setRouteNum(Long routeNum) {
		this.routeNum = routeNum;
	}

	public String getShareContent() {
		if (this.getMarketPriceYuan() == null) {
			return null;
		}
		String content = String.format(
				"我在@驴妈妈旅游网 上看到“%s”这个景点不错，驴妈妈评分%s分，门票%s元起。",
				com.lvmama.clutter.utils.ClientUtils.subProductName(this
						.getName()), this.getAvgScore(), this
						.getSellPriceYuan());
		return content;
	}

	public String getShareContentTitle() {
		if (this.getMarketPriceYuan() == null
				|| null == this.getSellPriceYuan()) {
			return null;
		}
		String contentTitle = String.format(
				ClutterConstant.SHARE_CONTENT,
				this.getName(),
				PriceUtil.formatDecimal(this.getSellPriceYuan()
						/ this.getMarketPriceYuan() * 10));
		return contentTitle;
	}

	public String getWapUrl() {
		return "http://m.lvmama.com/clutter/place/" + getId();
	}

	public boolean isHasActivity() {
		return hasActivity;
	}

	public void setHasActivity(boolean hasActivity) {
		this.hasActivity = hasActivity;
	}

	public MobilePlaceAddInfo getMpAddInfo() {
		return mpAddInfo;
	}

	public void setMpAddInfo(MobilePlaceAddInfo mpAddInfo) {
		this.mpAddInfo = mpAddInfo;
	}

	public Set<MobileGuaranteeOption> getGuaranteeOptions() {
		return guaranteeOptions;
	}

	public void setGuaranteeOptions(Set<MobileGuaranteeOption> guaranteeOptions) {
		this.guaranteeOptions = guaranteeOptions;
	}

	public String getTrafficInfo() {
		return trafficInfo;
	}

	public void setTrafficInfo(String trafficInfo) {
		this.trafficInfo = trafficInfo;
	}

	public boolean isOrderTodayAble() {
		return orderTodayAble;
	}

	public void setOrderTodayAble(boolean orderTodayAble) {
		this.orderTodayAble = orderTodayAble;
	}

	public boolean isShareWeixin() {
		return shareWeixin;
	}

	public void setShareWeixin(boolean shareWeixin) {
		this.shareWeixin = shareWeixin;
	}
	
	public Map<String,String> getWeiXinActivity(){
		Map<String,String> result = new HashMap<String,String>();
		result.put("weixinTitle", "自助游天下，就找驴妈妈- 分享到朋友圈,每张门票立减￥10");
		result.put("shareWeiXinTitle", "只需分享到微信朋友圈，每张该门票立减￥10，赶紧吧！");
		result.put("shareWeiXinContent", "只需分享到微信朋友圈，每张该门票立减￥10。");
		return result;
	}

	public void setWeiXinActivity(boolean weiXinActivity) {
		this.weiXinActivity = weiXinActivity;
	}
	
}
