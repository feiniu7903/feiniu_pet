package com.lvmama.front.web.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.homePage.HomePageUtils;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 网站频道门票
 * @author nixianjun 2013-6-5
 * @version 1.0
 */
@Results({
	@Result(name = "channelTicket", location = "/WEB-INF/pages/www/channel/ticket/channelTicket.ftl", type = "freemarker"),
	@Result(name = "recommendScenicDiv", location = "/WEB-INF/pages/www/channel/ticket/recommendScenicDiv.ftl", type = "freemarker")
 })
public class ChannelTicketHomeAction extends ChannelBaseHomeAction{

	private static final long serialVersionUID = -154127556678799278L;
	private static final Logger LOG = Logger.getLogger(ChannelTicketHomeAction.class);
	
	//模块id
	private Long commonBlockId = PindaoPageUtils.TICKET_COMMONBLOCKID;
	private String channelPage = PindaoPageUtils.TICKET_CHANNELPAGE;	
	private String containerCode =PindaoPageUtils.TICKET_CONTAINERCODE ;
	
	//
	private ProductSearchInfoService productSearchInfoService;
  
	
	@Action("/homePage/channelTicketAction")
	public String execute() {
		//初始化
 		this.initExcute(Constant.CHANNEL_ID.CH_TICKET.name());
 		
		//获取推荐数据
 		if (null != fromPlaceId && fromPlaceId.longValue() != DEFAULT_FROM_PLACE_ID.longValue()) {
			//如果出发地已经是上海，则不需要使用默认出发地当做备用字段,不是上海的分站
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, DEFAULT_FROM_PLACE_ID);
		
		} else {
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, null);
		}
		
		//今日团购
		//getLastTuanGou("TUANGOU_PRODUCT",Constant.PRODUCT_TYPE.TICKET.getCode(),null,DEFAULT_FROM_PLACE_ID);
		//推荐景点门票左边10个
		setRecommendTicket("RECOMMENDTICKET_PRODUCT_LEFT", this.fromPlaceCode, "orderQuantity_desc");
		//推荐景点门票左边10个
		setRecommendTicket("RECOMMENDTICKET_PRODUCT_RIGHT", this.fromPlaceCode, "updateTime_desc");

  		return "channelTicket";
	}
	
	/**
	 * 设定推荐景点门票
	 * @param recommendTicketName
	 * @param formPlaceIdParam
	 * @author:nixianjun 2013-6-18
	 */
	public void setRecommendTicket(String recommendTicketName,
			String fromPlaceCodeParam, String orderField) {
		String key = "base_setRecommendTickett_" + channelPage + "_"
				+ recommendTicketName + "_" + fromPlaceCodeParam + "_"
				+ orderField;
		List<ProductSearchInfo> productSearchInfoList = (List<ProductSearchInfo>) MemcachedUtil
				.getInstance().get(key);
		if (productSearchInfoList == null) {
			List<String> list = new ArrayList<String>();
			  if (fromPlaceCodeParam.equals(HomePageUtils.BJ)
					 ||fromPlaceCodeParam.equals(HomePageUtils.SD)
					 || fromPlaceCodeParam.equals(HomePageUtils.LN)) {
				list.add("北京");
				list.add("北戴河");
				list.add("月坨岛");
				list.add("野三坡");
				list.add("青岛");
				list.add("威海");
				list.add("台儿庄");
				list.add("泰山");
				list.add("平遥");
				list.add("天津");
				list.add("济南");
				list.add("呼和浩特");
				list.add("包头");
				list.add("沈阳");
				list.add("大连");
				list.add("哈尔滨");
			} else if (fromPlaceCodeParam.equals(HomePageUtils.CD)) {
				list.add("九寨沟");
				list.add("重庆");
				list.add("都姜堰");
				list.add("峨眉山");
				list.add("天台山");
				list.add("成都");
			}else if(fromPlaceCodeParam.equals(HomePageUtils.GX)){
				list.add("桂林");
				list.add("南宁");
				list.add("北海");
			}else if (fromPlaceCodeParam.equals(HomePageUtils.GZ)
					|| fromPlaceCodeParam.equals(HomePageUtils.SZ)) {
				list.add("长隆旅游度假区");
				list.add("清远");
				list.add("惠州");
				list.add("江门");
				list.add("珠海");
				list.add("广州");
			}
			else if (fromPlaceCodeParam.equals(HomePageUtils.SY)) {
				list.add("三亚");
				list.add("海口");
				list.add("保亭");
				list.add("陵水");
				list.add("万宁");
				list.add("琼海");
				list.add("文昌");
				list.add("儋州");
			} else if (fromPlaceCodeParam
					.equals(com.lvmama.comm.utils.homePage.PindaoPageUtils.PLACEID_PLACECODE.CQ
							.getPlacecode())) {
				list.add("九寨沟");
				list.add("重庆");
				list.add("都江堰");
				list.add("峨眉山");
				list.add("天台山 ");
				list.add("云南");
				list.add("贵州");
				list.add("西岭雪山");
				list.add("武隆");
			}else {
				list.add("常州");
				list.add("天目湖");
				list.add("杭州");
				list.add("安吉");
				list.add("黄山");
				list.add("宁波");
				list.add("扬州");
				list.add("横店");
				list.add("无锡");
				list.add("普陀山");
				list.add("苏州");
				list.add("上海");
			} 

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("isValid", "Y");
			param.put("productType", Constant.PRODUCT_TYPE.TICKET.getCode());
			param.put("onLine", "true");
			param.put("channel", "FRONTEND");
			// /标的name list
			param.put("placeNames", list);
			param.put("orderField", orderField);
			param.put("endRows", 10);
			productSearchInfoList = productSearchInfoService
					.queryProductSearchInfoByParam(param);
			LOG.info("====================frontticket:" + productSearchInfoList.size());
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,
					productSearchInfoList);
			if (null == MemcachedUtil.getInstance().get(key)) {
				LOG.info("SAVE MemCache Failure:" + key);
			}
		}
		if (productSearchInfoList != null && !productSearchInfoList.isEmpty()) {
			map.put(recommendTicketName, productSearchInfoList);
		}
	}

    /**
     *ajax请求获取推荐景点 参数paramDataCode
     * @return
     * @author:nixianjun 2013-7-2
     */
    @Action("/homePage/ajaxGetRecommendScenic")
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
	    	 list.addAll(HomePageUtils.getFrontDataTeMaiByType(xianShiTeMai, HomePageUtils.TICKET));
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
	    	 list.addAll(HomePageUtils.getFrontDataReXiaoPaiHangByType(reXiaoPaiHang,HomePageUtils.TICKET));
	    }
	    return list;
    }
    
	public String getChannelPage() {
		return channelPage;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

}
