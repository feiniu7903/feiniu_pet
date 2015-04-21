package com.lvmama.front.web.home;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 网站频道酒店
 * @author nixianjun 2013-6-15
 * @version 1.0
 */
@Results({
	@Result(name = "channelHotel", location = "/WEB-INF/pages/www/channel/hotel/channelHotel.ftl", type = "freemarker"),
	@Result(name = "recommendScenicDiv", location = "/WEB-INF/pages/www/channel/hotel/recommendScenicDiv.ftl", type = "freemarker"),
	@Result(name = "recommendScenicDiv_Abroad", location = "/WEB-INF/pages/www/channel/hotel/recommendScenicDiv_Abroad.ftl", type = "freemarker")
 })
public class ChannelHotelHomeAction extends ChannelBaseHomeAction{

	private static final long serialVersionUID = -154127556678799278L;
	
	//模块id
	private Long commonBlockId = PindaoPageUtils.HOTEL_COMMONBLOCKID;
	private String channelPage = PindaoPageUtils.HOTEL_CHANNELPAGE;	
	private String containerCode =PindaoPageUtils.HOTEL_CONTAINERCODE ;
	
	private String paramBakWord2;
	private String paramBakWord3;
	
	
	
	@Action("/homePage/channelHotelAction")
	public String execute() {
		//初始化
 		this.init(containerCode, Constant.CHANNEL_ID.CH_HOTEL.name());
		
		//获取推荐数据
		if (null != fromPlaceId && fromPlaceId.longValue() != DEFAULT_FROM_PLACE_ID.longValue()) {
			//如果出发地已经是上海，则不需要使用默认出发地当做备用字段,不是上海的分站
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, DEFAULT_FROM_PLACE_ID);
		} else {
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, null);
		}
	    //今日团购
		 getLastTuanGou("TUANGOU_PRODUCT",Constant.PRODUCT_TYPE.HOTEL.getCode(),null,DEFAULT_FROM_PLACE_ID);
		return "channelHotel";
	}

    /**
     *ajax请求获取推荐景点 参数paramDataCode
     * @return
     * @author:nixianjun 2013-7-2
     */
    @Action("/homePage/ajaxGetRecommendScenicForHotel")
	public String  ajaxGetRecommendScenicForHotel() {
    	if(paramDataCode!=null&&StringUtils.isNotEmpty(paramDataCode)){
    		this.recommendInfoForScenicList= getRecommendInfoData(this.channelPage, null, this.containerCode,paramDataCode);
    	}
    	return "recommendScenicDiv";
    }
    /**
     *ajax请求获取推荐景点 参数paramDataCode
     * @return
     * @author:nixianjun 2013-7-2
     */
    @Action("/homePage/ajaxGetRecommendScenicForAbroadHotel")
	public String  ajaxGetRecommendScenicForAbroadHotel() {
    	if(paramDataCode!=null&&StringUtils.isNotEmpty(paramDataCode)){
    		this.recommendInfoForScenicList= getRecommendInfoData(this.channelPage, null, this.containerCode,paramDataCode);
    	}
    	return "recommendScenicDiv_Abroad";
    }

	public String getChannelPage() {
		return channelPage;
	}

	public String getParamBakWord2() {
		return paramBakWord2;
	}

	public void setParamBakWord2(String paramBakWord2) {
		this.paramBakWord2 = paramBakWord2;
	}

	public String getParamBakWord3() {
		return paramBakWord3;
	}

	public void setParamBakWord3(String paramBakWord3) {
		this.paramBakWord3 = paramBakWord3;
	}

}
