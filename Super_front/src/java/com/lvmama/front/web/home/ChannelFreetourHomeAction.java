/**
 * 网站频道-周边
 * @author zhongshuangxi
 * @version 1.0
 */
package com.lvmama.front.web.home;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.utils.homePage.HomePageUtils;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "channelFreetour", location = "/WEB-INF/pages/www/channel/freetour/channelFreetour.ftl", type = "freemarker"),
	@Result(name = "recommendScenicDiv", location = "/WEB-INF/pages/www/channel/freetour/recommendScenicDiv.ftl", type = "freemarker")
})
public class ChannelFreetourHomeAction extends ChannelBaseHomeAction {
	
	private static final long serialVersionUID = -154127556678799278L;
	
	private Long commonBlockId = PindaoPageUtils.FREETOUR_COMMONBLOCKID;
	private String channelPage = PindaoPageUtils.FREETOUR_CHANNELPAGE;
	private String containerCode = PindaoPageUtils.FREETOUR_CONTAINERCODE;
	
	@Action("/homePage/channelFreetourAction")
	public String execute() {
		
		this.initExcute(Constant.CHANNEL_ID.CH_FREETOUR.name());
 		
		if (null != fromPlaceId && fromPlaceId.longValue() != DEFAULT_FROM_PLACE_ID) {
			//如果出发地已经是上海，则不需要使用默认出发地当做备用字段
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, DEFAULT_FROM_PLACE_ID);
		} else {
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, null);
		}
		  //今日团购
		List<String> list=new ArrayList<String>();
		list.add(Constant.SUB_PRODUCT_TYPE.FREENESS.getCode());
		list.add(Constant.SUB_PRODUCT_TYPE.GROUP.getCode());
		list.add(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode());
		list.add(Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.getCode());
		//getLastTuanGou("TUANGOU_PRODUCT",Constant.PRODUCT_TYPE.ROUTE.getCode(),list,DEFAULT_FROM_PLACE_ID);
		return "channelFreetour";
	}

    /**
     *ajax请求获取推荐景点 参数paramDataCode
     * @return
     * @author:nixianjun 2013-7-2
     */
    @Action("/homePage/ajaxGetRecommendScenicForFreetour")
	public String  ajaxGetRecommendScenic() {
    	if(paramDataCode!=null&&StringUtils.isNotEmpty(paramDataCode)){
    		this.recommendInfoForScenicList= getRecommendInfoData(this.channelPage, null, this.containerCode,paramDataCode);
    	}
    	return "recommendScenicDiv";
    }
    
	 /**
     * 热门推荐（获取首页限时热卖-前两条）
     * 
     * @author:nixianjun 2013-7-2
     */
    public List<RecommendInfo> getHotRecommendXianShiTeMaiSet(){
    	List<RecommendInfo> list=new ArrayList<RecommendInfo>();
    	List<RecommendInfo> xianShiTeMai= getRecommendInfoData(PindaoPageUtils.HOME_CHANNELPAGE, null, PindaoPageUtils.HOME_CONTAINERCODE, PindaoPageUtils.XIANSHITEMAI);
 	    if(xianShiTeMai!=null&&!xianShiTeMai.isEmpty()){
	    	 list.addAll(HomePageUtils.getFrontDataTeMaiByType(xianShiTeMai, HomePageUtils.AROUND));
	    }
	    return list;
    }
    /**
     * 热门推荐（获取热销排行-前两条）
     * 
     * @author:nixianjun 2013-7-2
     */
    public List<RecommendInfo> getHotRecommendReXiaoPaiHangSet(){
    	List<RecommendInfo> list=new ArrayList<RecommendInfo>();
     	List<RecommendInfo> reXiaoPaiHang= getRecommendInfoData(PindaoPageUtils.HOME_CHANNELPAGE, null, PindaoPageUtils.HOME_CONTAINERCODE, PindaoPageUtils.REXIAOPAIHANG);
	    if(reXiaoPaiHang!=null&&!reXiaoPaiHang.isEmpty()){
	    	 list.addAll(HomePageUtils.getFrontDataReXiaoPaiHangByType(reXiaoPaiHang,HomePageUtils.AROUND));
	    }
	    return list;
    }

    public String getChannelPage() {
		return channelPage;
	}
}
