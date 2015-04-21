package com.lvmama.clutter.xml.lv.po;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.pet.po.client.ClientGroupon2;
import com.lvmama.comm.pet.po.client.ClientPlace;
import com.lvmama.comm.pet.po.client.ClientProduct;
import com.lvmama.comm.pet.po.client.ClientProvince;
import com.lvmama.comm.pet.po.client.ClientTimePrice;
import com.lvmama.comm.pet.po.client.ClientUser;
import com.lvmama.comm.pet.po.client.ClientUserCouponInfo;
import com.lvmama.comm.pet.po.client.ClientViewJouney;
import com.lvmama.comm.pet.po.client.ViewClientOrder;
import com.lvmama.comm.pet.po.client.ViewPlace;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.vo.ViewProdProductBranch;
/**
 * xml相应body信息
 * @author dengcheng
 *
 */
public class ResponseBody {
	/**
	 * 景区酒店列表信息
	 */
	private List<ViewPlace> placeList;
	/**
	 * 目的地切换城市信息
	 * @return
	 */
	private List<ClientProvince> listProvince;

	/**
	 * 单个标的信息
	 * @return
	 */
	ClientPlace place;
	
	/**
	 * 产品关联列表
	 * @return
	 */
	List<ClientProduct> productListInfo;
	
	/**
	 * 附加产品列表
	 * @return
	 */
	List<ClientProduct> addtionalListInfo;
	
	/**
	 * 
	 * 用户信息
	 */
	private ClientUser clientUser;
	/**
	 * 单个产品信息
	 */
	private ProductSearchInfo viewProduct;
	
	/**
	 * 时间价格表信息
	 */
	private List<ClientTimePrice> timePriceList;
	
	private List<ClientViewJouney> clientJourneyList;
	
	private List<ViewJourney> journeyList;
	private List<ViewContent> contentList;
	private List<ComPicture> pictureList;
	/**
	 * 单房型
	 */
	private List<ViewProdProductBranch> prodProductBranchList;
	/**
	 * 凭证图片url
	 */
	private String smsUrl;
	/**
	 * 凭证内容
	 */
	private String smsContent;
	/**
	 * 订单列表
	 */
	private List<ViewClientOrder> ordersList;
	
	private boolean hasProduct;
	
	private OrdOrder order;
	
	private String shareContent;
	
	private String shareUrl;
	
	/**
	 * 用户优惠券信息
	 */
	private List<ClientUserCouponInfo> userCouponInfoList;
	
	private List<ClientGroupon2> gouponList;
	/**
	 * 表示十分能参与活动。例如不参与活动的景区 ，黄牛刷票等。
	 */
	private String promotionName="";
	
	public List<ViewPlace> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(List<ViewPlace> placeList) {
		this.placeList = placeList;
	}

	public List<ClientProvince> getListProvince() {
		return listProvince;
	}

	public void setListProvince(List<ClientProvince> listProvince) {
		this.listProvince = listProvince;
	}

	public ClientPlace getPlace() {
		return place;
	}

	public void setPlace(ClientPlace place) {
		this.place = place;
	}

	public List<ClientProduct> getProductListInfo() {
		return productListInfo;
	}

	public void setProductListInfo(List<ClientProduct> productListInfo) {
		this.productListInfo = productListInfo;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public void setViewProduct(ProductSearchInfo viewProduct) {
		this.viewProduct = viewProduct;
	}

	public ProductSearchInfo getViewProduct() {
		return viewProduct;
	}

	public List<ClientTimePrice> getTimePriceList() {
		return timePriceList;
	}

	public void setTimePriceList(List<ClientTimePrice> timePriceList) {
		this.timePriceList = timePriceList;
	}

	public List<ViewJourney> getJourneyList() {
		return journeyList;
	}

	public void setJourneyList(List<ViewJourney> journeyList) {
		this.journeyList = journeyList;
	}



	public List<ViewContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<ViewContent> contentList) {
		this.contentList = contentList;
	}

	public List<ComPicture> getPictureList() {
		return pictureList;
	}

	public void setPictureList(List<ComPicture> pictureList) {
		this.pictureList = pictureList;
	}

	public List<ViewClientOrder> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<ViewClientOrder> ordersList) {
		this.ordersList = ordersList;
	}

	public String getSmsUrl() {
		return smsUrl;
	}

	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}

	public OrdOrder getOrder() {
		return order;
	}

	public void setOrder(OrdOrder order) {
		this.order = order;
	}

	public List<ClientGroupon2> getGouponList() {
		return gouponList;
	}

	public void setGouponList(List<ClientGroupon2> gouponList) {
		this.gouponList = gouponList;
	}

	public boolean isHasProduct() {
		return hasProduct;
	}

	public void setHasProduct(boolean hasProduct) {
		this.hasProduct = hasProduct;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public List<ClientViewJouney> getClientJourneyList() {
		return clientJourneyList;
	}

	public void setClientJourneyList(List<ClientViewJouney> clientJourneyList) {
		this.clientJourneyList = clientJourneyList;
	}

	public List<ClientUserCouponInfo> getUserCouponInfoList() {
		return userCouponInfoList;
	}

	public void setUserCouponInfoList(List<ClientUserCouponInfo> userCouponInfoList) {
		this.userCouponInfoList = userCouponInfoList;
	}

	public List<ClientProduct> getAddtionalListInfo() {
		return addtionalListInfo;
	}

	public void setAddtionalListInfo(List<ClientProduct> addtionalListInfo) {
		this.addtionalListInfo = addtionalListInfo;
	}

	public List<ViewProdProductBranch> getProdProductBranchList() {
		return prodProductBranchList;
	}

	public void setProdProductBranchList(
			List<ViewProdProductBranch> prodProductBranchList) {
		this.prodProductBranchList = prodProductBranchList;
	}

	

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public String getShareContent() {
		return shareContent;
	}

	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}


}
