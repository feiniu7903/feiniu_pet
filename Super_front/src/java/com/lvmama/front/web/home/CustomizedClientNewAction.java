package com.lvmama.front.web.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.client.RecommendInfoClient;
import com.lvmama.comm.pet.po.seo.RecommendBlock;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.seo.RecommendBlockService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;

/**
 * 大客户频道的首页
 */
@Results({
 	@Result(name = "success", location = "/WEB-INF/pages/www/customize/customized_client_new.ftl", type = "freemarker"),
	@Result(name = "productPage", location = "/WEB-INF/pages/www/customize/customized_client_productpage.ftl", type = "freemarker")
})
public class CustomizedClientNewAction extends BaseHomeAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 528957101961072959L;
	/**
	 * 频道名
	 */
	private final static String CHANNEL_STATION = "qiye";
	
	//块id
	private  Long  blockId=0L;
	private String placeName;

	//定制游-导航的块号
	public final static Long BLOCK_ID_FOR_NAVIGATOR = 15493L;
	//定制游-产品列表的块号
	public final static Long BLOCK_ID_FOR_PRODUCTLIST = 15498L;
	//定制游-成功案例的块号
	public final static Long BLOCK_ID_FOR_SUCCESSFUL_CASE = 15776L;
	//定制游-热门推荐线路的块号
	public final static Long BLOCK_ID_FOR_HOT_RECOMMEND_ROUTE = 15775L;
	
	/**
	 * 定制游的推荐结果
	 */
	private Map<String,List<RecommendInfo>> navigatorMap = new HashMap<String,List<RecommendInfo>>();
	/**
	 * 定制游-产品列表的推荐结果
	 */
	private List<RecommendInfo> productList = new ArrayList<RecommendInfo>();	
	
	/**
	 * 定制游-成功案例的推荐结果
	 */
	private Map<String,List<RecommendInfo>> recommendRouteMap = new HashMap<String,List<RecommendInfo>>();
	/**
	 * 定制游-热门推荐线路的推荐结果
	 */
	private Map<String,List<RecommendInfo>> successfulCaseMap = new HashMap<String,List<RecommendInfo>>();	
	private RecommendInfoClient recommendInfoClient;
	private RecommendInfoService recommendInfoService;
	private RecommendBlockService recommendBlockService;

	@Action("/homePage/customizedClientNewAction")
	public String execute() {
		initSeoIndexPage("CH_CUSTOMED");
		
		navigatorMap = recommendInfoClient.getRecommendProductByBlockIdAndStation(BLOCK_ID_FOR_NAVIGATOR, CHANNEL_STATION);
  		successfulCaseMap = recommendInfoClient.getRecommendProductByBlockIdAndStation(BLOCK_ID_FOR_SUCCESSFUL_CASE, CHANNEL_STATION);
		recommendRouteMap = recommendInfoClient.getRecommendProductByBlockIdAndStation(BLOCK_ID_FOR_HOT_RECOMMEND_ROUTE, CHANNEL_STATION);

		return "success";
	}
	
	@Action("/homePage/company/city")
	public String city() {
 		navigatorMap = recommendInfoClient.getRecommendProductByBlockIdAndStation(BLOCK_ID_FOR_NAVIGATOR, CHANNEL_STATION);
  		successfulCaseMap = recommendInfoClient.getRecommendProductByBlockIdAndStation(BLOCK_ID_FOR_SUCCESSFUL_CASE, CHANNEL_STATION);
  		if(blockId!=null){
  			productList =recommendInfoService.getRecommendInfoByBlockId(blockId, null);
  			RecommendBlock recommendBlock=recommendBlockService.getRecommendBlockById(blockId);
  			if(recommendBlock!=null&&StringUtils.isNotBlank(recommendBlock.getName())){
  				this.placeName=recommendBlock.getName().substring(recommendBlock.getName().length()-2);
  			}
  		}
 		return "productPage";
	}

	//setter and getter
	public Map<String, List<RecommendInfo>> getRecommendRouteMap() {
		return recommendRouteMap;
	}

	public Map<String, List<RecommendInfo>> getSuccessfulCaseMap() {
		return successfulCaseMap;
	}
 
	public Map<String, List<RecommendInfo>> getNavigatorMap() {
		return navigatorMap;
	}


	public String getStation() {
		return CHANNEL_STATION;
	}

	public void setRecommendInfoClient(RecommendInfoClient recommendInfoClient) {
		this.recommendInfoClient = recommendInfoClient;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public List<RecommendInfo> getProductList() {
		return productList;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public void setRecommendBlockService(RecommendBlockService recommendBlockService) {
		this.recommendBlockService = recommendBlockService;
	}
}
