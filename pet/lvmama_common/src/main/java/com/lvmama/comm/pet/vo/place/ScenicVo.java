package com.lvmama.comm.pet.vo.place;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceActivity;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.SeoLinks;
import com.lvmama.comm.pet.vo.ScenicProductAndBranchListVO;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

public class ScenicVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1460227078193504418L;
	
	private long lowerPrice;
	private List<Place> listPlace;
	//景点目的地父亲
    private Place fatherPlace;
    //爷爷
    private Place grandfatherPlace;
    //父亲的兄弟目的地
    private List<Place> fatherBrotherList;
    //打折门票
    private Map<String, List<ScenicProductAndBranchListVO>> ticketProductList;
    //景区图片
    private List<PlacePhoto> placePhoto;
    //景区活动
    private List<PlaceActivity> placeActivity;
    //目的地自由行+酒店套餐
    private List<ProductSearchInfo> freeNessAndHotelSuitProductList;
    //周边景点
    private List<PlaceSearchInfo> victinityScenic;
    //周边酒店
    private List<PlaceSearchInfo> victinityHotel;
     //相同主题景点
    private List<PlaceSearchInfo> sameSubjectScenic;
    //相关团购
    private ProductBean tuangouProduct;
    //跟团游出发点(短途跟团游+bus)
    private List<String> groupAndBusTabNameList;
    //短途跟团游+bus
    private Map<String,List<ProductSearchInfo>> groupAndBusDataMap;
    //公告
    private List<PlaceHotelNotice> noticeList;
    //点评统计
    private List<CmtLatitudeStatistics> cmtLatitudeStatisticsList;
    
	/**
	 * 获取景点的点评统计信息
	 */
	private CmtTitleStatisticsVO cmtCommentStatisticsVO;
	//最新一条通过审核点评vo
	private CommonCmtCommentVO lastcommonCmtCommentVO;
    //问答总数
    private Long qaCount = 0L ;
    //seo title 
    private String seoTitle;
    //seoDescription
    private String seoDescription;
    //seoKeyword
    private String seoKeyword;
    //友情链接的公用的地方
    private String seoPublicContent;
    //友情链接的私用的地方
    /**
     * @deprecated
     */
    private String seoPrivateContent;

    //第一主题转码
    private Long firstTopicCodeId;
    //第二主题转码
    private Long scenicSecondTopicCodeId;

    private List<SeoLinks> seoList;
    
	public Place getFatherPlace() {
		return fatherPlace;
	}
	public void setFatherPlace(Place fatherPlace) {
		this.fatherPlace = fatherPlace;
	}
	public Place getGrandfatherPlace() {
		return grandfatherPlace;
	}
	public void setGrandfatherPlace(Place grandfatherPlace) {
		this.grandfatherPlace = grandfatherPlace;
	}
	public Map<String, List<ScenicProductAndBranchListVO>> getTicketProductList() {
		return ticketProductList;
	}
	public void setTicketProductList(
			Map<String, List<ScenicProductAndBranchListVO>> ticketProductList) {
		this.ticketProductList = ticketProductList;
	}
	public List<PlacePhoto> getPlacePhoto() {
		return placePhoto;
	}
	public void setPlacePhoto(List<PlacePhoto> placePhoto) {
		this.placePhoto = placePhoto;
	}
	public List<PlaceActivity> getPlaceActivity() {
		return placeActivity;
	}
	public void setPlaceActivity(List<PlaceActivity> placeActivity) {
		this.placeActivity = placeActivity;
	}
	public List<ProductSearchInfo> getFreeNessAndHotelSuitProductList() {
		return freeNessAndHotelSuitProductList;
	}
	public void setFreeNessAndHotelSuitProductList(
			List<ProductSearchInfo> freeNessAndHotelSuitProductList) {
		this.freeNessAndHotelSuitProductList = freeNessAndHotelSuitProductList;
	}
	public List<PlaceSearchInfo> getVictinityScenic() {
		return victinityScenic;
	}
	public void setVictinityScenic(List<PlaceSearchInfo> victinityScenic) {
		this.victinityScenic = victinityScenic;
	}
	public List<PlaceSearchInfo> getVictinityHotel() {
		return victinityHotel;
	}
	public void setVictinityHotel(List<PlaceSearchInfo> victinityHotel) {
		this.victinityHotel = victinityHotel;
	}
	public List<PlaceSearchInfo> getSameSubjectScenic() {
		return sameSubjectScenic;
	}
	public void setSameSubjectScenic(List<PlaceSearchInfo> sameSubjectScenic) {
		this.sameSubjectScenic = sameSubjectScenic;
	}
	public ProductBean getTuangouProduct() {
		return tuangouProduct;
	}
	public void setTuangouProduct(ProductBean tuangouProduct) {
		this.tuangouProduct = tuangouProduct;
	}
	/**
	 * 跟团游出发点(短途跟团游+bus)
	 * @return
	 * @author:nixianjun 2013-7-14
	 */
	public List<String> getGroupAndBusTabNameList() {
		return groupAndBusTabNameList;
	}
	public void setGroupAndBusTabNameList(List<String> groupAndBusTabNameList) {
		this.groupAndBusTabNameList = groupAndBusTabNameList;
	}
	public Map<String, List<ProductSearchInfo>> getGroupAndBusDataMap() {
		return groupAndBusDataMap;
	}
	public void setGroupAndBusDataMap(
			Map<String, List<ProductSearchInfo>> groupAndBusDataMap) {
		this.groupAndBusDataMap = groupAndBusDataMap;
	}
	/**
	 * 公告list
	 * @return the noticeList
	 */
	public List<PlaceHotelNotice> getNoticeList() {
		return noticeList;
	}
	/**
	 * @param noticeList the noticeList to set
	 */
	public void setNoticeList(List<PlaceHotelNotice> noticeList) {
		this.noticeList = noticeList;
	}
	/**
	 * 问答数量
	 */
	public Long getQaCount() {
		return qaCount;
	}
	public void setQaCount(Long qaCount) {
		this.qaCount = qaCount;
	}
	/**
	 * 点评
	 */
	public List<CmtLatitudeStatistics> getCmtLatitudeStatisticsList() {
		return cmtLatitudeStatisticsList;
	}
	public void setCmtLatitudeStatisticsList(
			List<CmtLatitudeStatistics> cmtLatitudeStatisticsList) {
		this.cmtLatitudeStatisticsList = cmtLatitudeStatisticsList;
	}
	/**
	 * 父亲的兄弟目的地
	 * @return the fatherBrotherList
	 */
	public List<Place> getFatherBrotherList() {
		return fatherBrotherList;
	}
	/**
	 * @param fatherBrotherList the fatherBrotherList to set
	 */
	public void setFatherBrotherList(List<Place> fatherBrotherList) {
		this.fatherBrotherList = fatherBrotherList;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public String getSeoKeyword() {
		return seoKeyword;
	}
	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}
	public String getSeoPublicContent() {
		return seoPublicContent;
	}
	public void setSeoPublicContent(String seoPublicContent) {
		this.seoPublicContent = seoPublicContent;
	}
	public String getSeoPrivateContent() {
		return seoPrivateContent;
	}
	public void setSeoPrivateContent(String seoPrivateContent) {
		this.seoPrivateContent = seoPrivateContent;
	}
	public Long getScenicSecondTopicCodeId() {
		return scenicSecondTopicCodeId;
	}
	public void setScenicSecondTopicCodeId(Long scenicSecondTopicCodeId) {
		this.scenicSecondTopicCodeId = scenicSecondTopicCodeId;
	}
	public Long getFirstTopicCodeId() {
		return firstTopicCodeId;
	}
	public void setFirstTopicCodeId(Long firstTopicCodeId) {
		this.firstTopicCodeId = firstTopicCodeId;
	}
    public List<SeoLinks> getSeoList() {
        return seoList;
    }
    public void setSeoList(List<SeoLinks> seoList) {
        this.seoList = seoList;
    }

	public long getLowerPrice() {
		return lowerPrice;
	}
	public void setLowerPrice(long lowerPrice) {
		this.lowerPrice = lowerPrice;
	}

	public CmtTitleStatisticsVO getCmtCommentStatisticsVO() {
		return cmtCommentStatisticsVO;
	}
	public void setCmtCommentStatisticsVO(
			CmtTitleStatisticsVO cmtCommentStatisticsVO) {
		this.cmtCommentStatisticsVO = cmtCommentStatisticsVO;
	}
	public CommonCmtCommentVO getLastcommonCmtCommentVO() {
		return lastcommonCmtCommentVO;
	}
	public void setLastcommonCmtCommentVO(CommonCmtCommentVO lastcommonCmtCommentVO) {
		this.lastcommonCmtCommentVO = lastcommonCmtCommentVO;
	}
	public List<Place> getListPlace() {
		return listPlace;
	}
	public void setListPlace(List<Place> listPlace) {
		this.listPlace = listPlace;
	}


}
