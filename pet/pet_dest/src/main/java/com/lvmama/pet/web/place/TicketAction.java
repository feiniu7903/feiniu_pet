package com.lvmama.pet.web.place;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.place.PlaceActivityService;
import com.lvmama.comm.pet.service.place.PlaceHotelNoticeService;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.comm.pet.vo.PlaceVo;
import com.lvmama.comm.pet.vo.ScenicProductAndBranchListVO;
import com.lvmama.comm.pet.vo.place.ScenicVo;
import com.lvmama.comm.search.service.TuangouSearchService;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.SeoUtils;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.utils.homePage.TwoDimensionCode;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;
import com.lvmama.comm.vo.enums.SeoIndexPageCodeEnum;

/**
 * 新版景区门票 ticket.lvmama.com/scenic-id
 * 
 * @author nixianjun
 * 
 */
@Results({
		@Result(name = "ticketDetail", type = "freemarker", location = "/WEB-INF/pages/ticket/ticketDetail.ftl"),
		@Result(name = "ticketDetailForRecommend", type = "freemarker", location = "/WEB-INF/pages/ticket/ticketDetailForRecommend.ftl") })
public class TicketAction extends BaseAction {
	private static final long serialVersionUID = 544590146080261209L;
	private static final Log log = LogFactory.getLog(TicketAction.class);
	private Place place;
	
	private ScenicVo scenicVo;
	private String descripTion; //景点介绍
	private String trafficInfo; //交通信息
 	private String destinationExplore;//目的地探索
	@Autowired
	private ProductSearchInfoService productSearchInfoService;
	@Autowired
	private PlacePageService placePageService;
	@Autowired
	private PlaceActivityService placeActivityService;
	@Autowired
	private CmtLatitudeStatistisService cmtLatitudeStatistisService;
	@Autowired
	private PlaceService placeService;
	@Autowired
    private PlaceHotelNoticeService placeHotelNoticeService;
	@Autowired
	/**
	 * 景区点评统计服务接口
	 */
	private CmtTitleStatistisService cmtTitleStatistisService;
	/**
	 * 团购服务
	 */
	private TuangouSearchService  tuangouSearchService;
	@Autowired
	private SeoLinksService seoLinksService; 
	@Autowired
	private SeoIndexPageService seoIndexPageService;
	
	//点评服务接口
	@Autowired
	private CmtCommentService cmtCommentService;
	/**
	 * 附近景点主题景点最低价格
	 */
	private Map<Long, Object> proMap = new HashMap<Long, Object>();
	
	@Action("/ticket/detail")
	public String ticket() {
		// place 不存在情况
		if (null == place
				|| null == place.getPlaceId()
				|| !"Y".equalsIgnoreCase(place.getIsValid())
				|| !Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()
						.equalsIgnoreCase(place.getStage())) {
			log.debug("The place is null or it's not a scenic, redirect to error page.");
			return ERROR;
		}

		List<ProductSearchInfo> ticketMainProductList = queryProductByPlaceId(place
				.getPlaceId());
		if (ticketMainProductList == null || ticketMainProductList.size() == 0) {
			return ticketDetailForRecommend();
		} else {
 			String memcachedKey=PlaceUtils.TICKET_NEW_MEMCACHED_MAIN_NICKY_KEY+this.place.getPlaceId();
			scenicVo= (ScenicVo) MemcachedUtil.getInstance().get(memcachedKey);
				if (null == scenicVo) {
					scenicVo=new ScenicVo();
					
					// 上级目的地
					if (place.getParentPlaceId() != null) {
						Place fatherPlace = placeService
								.queryPlaceAndComSearchTranscodeByPlaceId(place
										.getParentPlaceId());
						// //热门目的地 随机取6个
						scenicVo.setListPlace(placeService.queryParentPlace(place
								.getParentPlaceId()));
						if (null != fatherPlace) {
							scenicVo.setFatherPlace(fatherPlace);
							Place grandfatherPlace = placeService.queryPlaceAndComSearchTranscodeByPlaceId(fatherPlace.getParentPlaceId());
							if(null!=grandfatherPlace){
								scenicVo.setGrandfatherPlace(grandfatherPlace);
							}
						}
					}
					/**
					 * 门票类别
					 */
					List<ProdBranchSearchInfo> prodBranchSearchInfoAllList = productSearchInfoService
							.getProdBranchSearchInfoByParam(place.getPlaceId(),
									ProductSearchInfo.IS_TICKET.TICKET.getCode(),
									PlaceUtils.FRONTEND);
					Map<String, List<ScenicProductAndBranchListVO>> ticketProductList = executeTicketData(
							ticketMainProductList, prodBranchSearchInfoAllList);
					if (null != ticketProductList
							|| !ticketMainProductList.isEmpty()) {
						scenicVo.setTicketProductList(ticketProductList);
					}
					// 景区 图片
					PlacePhoto placePhoto = new PlacePhoto();
					placePhoto.setPlaceId(place.getPlaceId());
					placePhoto.setType(PlacePhotoTypeEnum.LARGE.getCode());
					scenicVo.setPlacePhoto((List<PlacePhoto>) placePageService
							.getPlacePhotoListByPlacePhoto(placePhoto));
	
					// 景区活动
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("placeId", place.getPlaceId());
					param.put("timeValid", "true");
					scenicVo.setPlaceActivity(placeActivityService
							.queryPlaceActivityListByParam(param));
	
					/**
					 * 点评统计
					 */
	
					scenicVo.setCmtCommentStatisticsVO(// 获取该景点的点评统计信息
					cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(place
							.getPlaceId()));
					/**
					 * 点评维度统计
					 */
					Map<String, Object> pm = new HashMap<String, Object>();
					pm.put("placeId", place.getPlaceId());
					scenicVo.setCmtLatitudeStatisticsList(cmtLatitudeStatistisService
							.getLatitudeStatisticsList(pm));
	
					/**
					 * 最新的一条点评
					 */
					addlastComment();
	
					/**
					 * 目的地自由行+酒店套餐
					 */
					List<ProductSearchInfo> freeNessAndHotelSuitProductList = placePageService
							.getFreeNessAndHotelSuit(this.place);
					scenicVo.setFreeNessAndHotelSuitProductList(freeNessAndHotelSuitProductList);
					/**
					 * 短途跟团游+bus
					 */
					List<ProductSearchInfo> groupAndBusList = placePageService
							.getGroupAndBus(this.place);
					List<String> groupAndBusTabNameList = new ArrayList<String>();
					for (ProductSearchInfo p : groupAndBusList) {
						if (StringUtils.isNotEmpty(p.getFromDest())
								&& !groupAndBusTabNameList.contains(p.getFromDest()
										.trim())) {
							groupAndBusTabNameList.add(p.getFromDest().trim());// 获取几个出发点
						}
					}
					// 设定跟团游出发点
					scenicVo.setGroupAndBusTabNameList(groupAndBusTabNameList);
					// 设定跟团游数据根据出发点
					Map<String, List<ProductSearchInfo>> groupAndBusDataMap = new HashMap<String, List<ProductSearchInfo>>();
					for (String tabName : scenicVo.getGroupAndBusTabNameList()) {
						List<ProductSearchInfo> prInfo = new ArrayList<ProductSearchInfo>();
						for (ProductSearchInfo p2 : groupAndBusList) {
							if (StringUtils.isNotEmpty(tabName)
									&& StringUtils.isNotEmpty(p2.getFromDest())
									&& tabName.equals(p2.getFromDest())) {
								prInfo.add(p2);
							}
						}
						if (prInfo != null && prInfo.size() > 0) {
							groupAndBusDataMap.put(tabName, prInfo);
						}
					}
					scenicVo.setGroupAndBusDataMap(groupAndBusDataMap);
					
					// 设置景点最低价
					scenicVo.setLowerPrice(this.getLowPrice(scenicVo));
	
					/**
					 * 公告
					 */
					PlaceHotelNotice notice = new PlaceHotelNotice();
					notice.setPlaceId(this.place.getPlaceId());
					notice.setNoticeType(PlaceUtils.SCENIC);
					notice.setValidNotice("true");
					List<PlaceHotelNotice> noticeList = placeHotelNoticeService
							.queryByHotelNotice(notice);
					if (null != noticeList && noticeList.size() > 0)
						scenicVo.setNoticeList(noticeList);
	
					// 相关团购产品
					List<ProductBean> productBeans = tuangouSearchService.search(
							place.getName(), "ROUTE", 1);
					if (!productBeans.isEmpty()) {
						scenicVo.setTuangouProduct(productBeans.get(0));
					}
	
				
				//seo友情链接 
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("placeId",place.getPlaceId());
				map.put("location",Constant.PLACE_SEOLINKS.INDEX.getCode());
				scenicVo.setSeoList(PlaceUtils.removeRepeatData(seoLinksService.batchQuerySeoLinksByParam(map)));
				this.getSeoIndexPage();
				//设定memcached  
				MemcachedUtil.getInstance().set(memcachedKey, MemcachedUtil.ONE_HOUR, scenicVo);
			}
			// 周边景点 memcached
			scenicVo.setVictinityScenic(getVicinityByPlace(place.getPlaceId(), null, Long.parseLong(Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()), 6L));
			//周边酒店 memcached
			scenicVo.setVictinityHotel(getVicinityByPlace(place.getPlaceId(), null, Long.parseLong(Constant.PLACE_STAGE.PLACE_FOR_HOTEL.getCode()), 6L));
			//相同主题景点memcached
			scenicVo.setSameSubjectScenic(getVicinityByPlace(place.getPlaceId(), place.getFirstTopic(), Long.parseLong(Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()), 6L));
			//景点介绍+交通地图
			this.descriptionAndTraffic();
		   //二维码生成
		   this.excuteQRDataFile();
			
		}
		return "ticketDetail";

	}
	/**
	 * 二维码生成
	 * @author nixianjun
	 */
	private void excuteQRDataFile(){
		/**
		 *增加二维码 
		 */
		String dirPath=ResourceUtil.getResourceFileName("/placeQr");
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String imgPath=ResourceUtil.getResourceFileName("/placeQr/"+this.place.getPlaceId()+"."+PlaceUtils.QR_IMGTYPE);
		File imgFile = new File(imgPath);
		if(!imgFile.exists()){
		TwoDimensionCode.encoderQRCode("http://m.lvmama.com/clutter/place/"+this.place.getPlaceId()+"?channel=QR", imgPath, PlaceUtils.QR_IMGTYPE, PlaceUtils.QR_SIZE);
		}
	}

	/**
	 * 预定门票
	 * @param ticketMainProductList
	 * @param prodBranchSearchInfoAllList
	 * @return
	 */
	private Map<String, List<ScenicProductAndBranchListVO>> executeTicketData(
			List<ProductSearchInfo> ticketMainProductList,
			List<ProdBranchSearchInfo> prodBranchSearchInfoAllList) {

		List<ScenicProductAndBranchListVO> singleList = new ArrayList<ScenicProductAndBranchListVO>();
		List<ScenicProductAndBranchListVO> unionList = new ArrayList<ScenicProductAndBranchListVO>();
		List<ScenicProductAndBranchListVO> suitList = new ArrayList<ScenicProductAndBranchListVO>();
		for (ProductSearchInfo ticket : ticketMainProductList) {
			// 单门票或者通票
			if (Constant.SUB_PRODUCT_TYPE.SINGLE.name().equalsIgnoreCase(
					ticket.getSubProductType())
					|| Constant.SUB_PRODUCT_TYPE.WHOLE.name().equalsIgnoreCase(
							ticket.getSubProductType())) {
				ScenicProductAndBranchListVO vo = new ScenicProductAndBranchListVO();
				vo.setProductSearchInfo(ticket);// 产品
				List<ProdBranchSearchInfo> ticketBranchList = new ArrayList<ProdBranchSearchInfo>();
				for (ProdBranchSearchInfo prodBranch : prodBranchSearchInfoAllList) {
					if (prodBranch.getProductId().equals(
							vo.getProductSearchInfo().getProductId())) {
						ticketBranchList.add(prodBranch);
					}
				}
				vo.setBranchSearchInfo(ticketBranchList);// 类别list
				singleList.add(vo);
			}
			// 联票
			if (Constant.SUB_PRODUCT_TYPE.UNION.name().equalsIgnoreCase(
					ticket.getSubProductType())) {
				ScenicProductAndBranchListVO vo = new ScenicProductAndBranchListVO();
				vo.setProductSearchInfo(ticket);// 产品
				List<ProdBranchSearchInfo> ticketBranchList = new ArrayList<ProdBranchSearchInfo>();
				for (ProdBranchSearchInfo prodBranch : prodBranchSearchInfoAllList) {
					if (prodBranch.getProductId().equals(
							vo.getProductSearchInfo().getProductId())) {
						ticketBranchList.add(prodBranch);
					}
				}
				vo.setBranchSearchInfo(ticketBranchList);// 类别list
				unionList.add(vo);
			}

			// 套票
			if (Constant.SUB_PRODUCT_TYPE.SUIT.name().equalsIgnoreCase(
					ticket.getSubProductType())) {
				ScenicProductAndBranchListVO vo = new ScenicProductAndBranchListVO();
				vo.setProductSearchInfo(ticket);// 产品
				List<ProdBranchSearchInfo> ticketBranchList = new ArrayList<ProdBranchSearchInfo>();
				for (ProdBranchSearchInfo prodBranch : prodBranchSearchInfoAllList) {
					if (prodBranch.getProductId().equals(
							vo.getProductSearchInfo().getProductId())) {
						ticketBranchList.add(prodBranch);
					}
				}
				vo.setBranchSearchInfo(ticketBranchList);// 类别list
				suitList.add(vo);
			}
		}
		Map<String, List<ScenicProductAndBranchListVO>> ticketProductList = new HashMap<String, List<ScenicProductAndBranchListVO>>();
		ticketProductList.put("SINGLE", singleList);
		ticketProductList.put("UNION", unionList);
		ticketProductList.put("SUIT", suitList);
		return ticketProductList;
	}
	
	/**
	 * 
	 * @param placeId
	 * @param subject
	 * @param stage
	 * @param limit
	 * @return
	 * @author nixianjun 2014-2-24
	 */
	@SuppressWarnings("unchecked")
	private List<PlaceSearchInfo> getVicinityByPlace(Long placeId, String subject, Long stage, Long limit) {
		String memcachedKey = PlaceUtils.TICKET_NEW_MEMCACHED_VICINITY_PLACE_KEY + placeId + (StringUtils.isEmpty(subject) ? "" : "_" + subject) + "_" + stage;
		List<PlaceSearchInfo> result= (List<PlaceSearchInfo>) MemcachedUtil.getInstance().get(memcachedKey);
		if (null == result) {
			result = this.placePageService.getVicinityByPlace(placeId, subject, stage, limit);
			Calendar now = Calendar.getInstance();
			MemcachedUtil.getInstance().set(memcachedKey, 24 * 60 * 60 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND), result);
		}
		return result;
	}

	/**
	 * 该目的地下没有产品页面
	 * 
	 * @return
	 * @author nixianjun 2014-1-23
	 */
	public String ticketDetailForRecommend() {
 		String memcachedKey=PlaceUtils.TICKET_NEW_MEMCACHED_NO_MAIN_NICKY_KEY+this.place.getPlaceId();
		scenicVo= (ScenicVo) MemcachedUtil.getInstance().get(memcachedKey);
		if(null==scenicVo){
			scenicVo=new ScenicVo();
		// 上级目的地
		if (place.getParentPlaceId() != null) {
			Place fatherPlace = placeService.queryPlaceAndComSearchTranscodeByPlaceId(place.getParentPlaceId());
		    //热门目的地 随机取6个
			scenicVo.setListPlace(placeService.queryParentPlace(place.getParentPlaceId()));
			if (null != fatherPlace) {
				scenicVo.setFatherPlace(fatherPlace);
				Place grandfatherPlace = placeService.queryPlaceAndComSearchTranscodeByPlaceId(fatherPlace.getParentPlaceId());
				if(null!=grandfatherPlace){
					scenicVo.setGrandfatherPlace(grandfatherPlace);
				}
			}
		}
		// 景区 图片
		PlacePhoto placePhoto = new PlacePhoto();
		placePhoto.setPlaceId(place.getPlaceId());
		placePhoto.setType(PlacePhotoTypeEnum.LARGE.getCode());
		scenicVo.setPlacePhoto((List<PlacePhoto>) placePageService
				.getPlacePhotoListByPlacePhoto(placePhoto));
		
		// 景区活动
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeId", place.getPlaceId());
		param.put("timeValid", "true");
		scenicVo.setPlaceActivity(placeActivityService
				.queryPlaceActivityListByParam(param));

		/**
		 * 点评统计
		 */
		scenicVo.setCmtCommentStatisticsVO(// 获取该景点的点评统计信息
		cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(place
				.getPlaceId()));
		/**
		 * 点评维度统计
		 */
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("placeId", place.getPlaceId());
		scenicVo.setCmtLatitudeStatisticsList(cmtLatitudeStatistisService
				.getLatitudeStatisticsList(pm));
		
		/**
		  * 最新的一条点评
		  */
		 addlastComment();
			
		
		 /**
   	     * 周边景点
   	     *
   	     */
		scenicVo.setVictinityScenic(getVicinityByPlace(place.getPlaceId(), null, Long.parseLong(Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()), 6L));
		if(scenicVo.getVictinityScenic()!=null && scenicVo.getVictinityScenic().size()>0){
			for (int i = 0; i < scenicVo.getVictinityScenic().size(); i++) {
				List<ProductSearchInfo> ticketProductList = queryTicketProductByPlaceId(scenicVo.getVictinityScenic().get(i).getPlaceId());
				
				if (ticketProductList != null && ticketProductList.size() > 0) {
					long sellPrice = ticketProductList.get(0).getSellPrice();
					for (int j = 1; j < ticketProductList.size(); j++) {
						if (sellPrice > ticketProductList.get(j).getSellPrice()) {
							sellPrice = ticketProductList.get(j).getSellPrice();
						}
					}
					proMap.put(scenicVo.getVictinityScenic().get(i)
							.getPlaceId(), sellPrice/100);
				}
			}
		}
		
		//相同主题景点
		scenicVo.setSameSubjectScenic(getVicinityByPlace(place.getPlaceId(), place.getFirstTopic(), Long.parseLong(Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()), 6L));
		if(scenicVo.getSameSubjectScenic()!=null && scenicVo.getSameSubjectScenic().size()>0){
			for (int i = 0; i < scenicVo.getSameSubjectScenic().size(); i++) {
				List<ProductSearchInfo> ticketProductList = queryTicketProductByPlaceId(scenicVo.getSameSubjectScenic().get(i).getPlaceId());
				if (ticketProductList != null && ticketProductList.size() > 0) {
					long sellPrice = ticketProductList.get(0).getSellPrice();
					for (int j = 1; j < ticketProductList.size(); j++) {
						if (sellPrice > ticketProductList.get(j).getSellPrice()) {
							sellPrice = ticketProductList.get(j).getSellPrice();
						}
					}
					proMap.put(scenicVo.getSameSubjectScenic().get(i)
							.getPlaceId(), sellPrice/100);
				}
			}
		}
		//seo友情链接
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("placeId",place.getPlaceId());
		map.put("location",Constant.PLACE_SEOLINKS.INDEX.getCode());
		scenicVo.setSeoList(PlaceUtils.removeRepeatData(seoLinksService.batchQuerySeoLinksByParam(map)));
		this.getSeoIndexPage();
	
		}
		//景区介绍和交通地图
		this.descriptionAndTraffic();
		//二维码
		this.excuteQRDataFile();
		return "ticketDetailForRecommend";
	}
	
	private void descriptionAndTraffic(){
		//景点介绍 第一次读数据库
		String memcachedKey=PlaceUtils.TICKET_MEMCACHED_DESCRIPTION_KEY+this.place.getPlaceId();
		descripTion= (String) MemcachedUtil.getInstance().get(memcachedKey);
		Place p =  placeService.getDescripAndTrafficByPlaceId(place.getPlaceId());
		if(null==descripTion||descripTion.isEmpty()){
			descripTion=p.getDescription();
			//保存memcached中
			MemcachedUtil.getInstance().set(memcachedKey,7200, place.getDescription());
		}	
		
		//目的地探索
		String memcachedKey3=PlaceUtils.TICKET_MEMCACHED_DESTINATION_EXPLORE_KEY+this.place.getPlaceId();
		destinationExplore=(String) MemcachedUtil.getInstance().get(memcachedKey3);
			if(null==destinationExplore||destinationExplore.isEmpty()){
				destinationExplore=p.getDestinationExplore();
				MemcachedUtil.getInstance().set(memcachedKey3,7200, place.getDestinationExplore());
			}
		
		
		
		//交通介绍
		String memcachedKey2=PlaceUtils.TICKET_MEMCACHED_TRAFFIC_INFO_KEY+this.place.getPlaceId();;
		trafficInfo=(String) MemcachedUtil.getInstance().get(memcachedKey2);
		if(null==trafficInfo||trafficInfo.isEmpty()){
			trafficInfo=p.getTrafficInfo();
			MemcachedUtil.getInstance().set(memcachedKey2,7200, place.getTrafficInfo());
		}
	}
	
	private void getSeoIndexPage(){
		SeoIndexPage defaultseoIndexPage=seoIndexPageService.getSeoIndexPageByPageCode(SeoIndexPageCodeEnum.CH_DEST_NEWSCENIC.getCode());
		if(null!=defaultseoIndexPage){
			scenicVo.setSeoPublicContent(defaultseoIndexPage.getSeoContent());
		}
 		PlaceVo placeVo=new PlaceVo();
		placeVo.setPlace(place);
		if(null!=scenicVo.getFatherPlace())placeVo.setParentPlace(scenicVo.getFatherPlace());
		if(defaultseoIndexPage!=null){
			defaultseoIndexPage.setSeoTitle(SeoUtils.getSeoIndexPageRegular(placeVo, defaultseoIndexPage.getSeoTitle()));
			defaultseoIndexPage.setSeoKeyword(SeoUtils.getSeoIndexPageRegular(placeVo, defaultseoIndexPage.getSeoKeyword()));
			defaultseoIndexPage.setSeoDescription(SeoUtils.getSeoIndexPageRegular(placeVo, defaultseoIndexPage.getSeoDescription()));
		}
		if(StringUtils.isNotEmpty(place.getSeoTitle())){
			scenicVo.setSeoTitle(place.getSeoTitle());
		}else{
			scenicVo.setSeoTitle(defaultseoIndexPage.getSeoTitle());
		}
		if(StringUtils.isNotEmpty(place.getSeoKeyword())){
			scenicVo.setSeoKeyword(place.getSeoKeyword());
		}else{
			scenicVo.setSeoKeyword(defaultseoIndexPage.getSeoKeyword());
		}
		if(StringUtils.isNotEmpty(place.getSeoDescription())){
			scenicVo.setSeoDescription(place.getSeoDescription());
		}else{
			scenicVo.setSeoDescription(defaultseoIndexPage.getSeoDescription());
		}
	}
	
/*	private Long getLowPrice(List<ProductSearchInfo> ticketProductList){
		if (ticketProductList != null && ticketProductList.size() > 0) {
			long sellPrice = ticketProductList.get(0).getSellPrice();
			for (int j = 1; j < ticketProductList.size(); j++) {
				if (sellPrice > ticketProductList.get(j).getSellPrice()) {
					sellPrice = ticketProductList.get(j).getSellPrice();
				}
			}
			return sellPrice/100;
		}
		return 0l;
	}*/
	private Long getLowPrice(ScenicVo scenicVo){
		List<Long> sellPriceList=new ArrayList<Long>();
		if(null!=scenicVo.getTicketProductList()&&scenicVo.getTicketProductList().size()>0){
			List<ScenicProductAndBranchListVO>  ticketlist=new ArrayList<ScenicProductAndBranchListVO>();
			List<ProdBranchSearchInfo> branchSearchInfoList=new ArrayList<ProdBranchSearchInfo>();
			if(null!=scenicVo.getTicketProductList().get(Constant.SUB_PRODUCT_TYPE.SINGLE.name()))ticketlist.addAll(scenicVo.getTicketProductList().get(Constant.SUB_PRODUCT_TYPE.SINGLE.name()));
			if(null!=scenicVo.getTicketProductList().get(Constant.SUB_PRODUCT_TYPE.UNION.name()))ticketlist.addAll(scenicVo.getTicketProductList().get(Constant.SUB_PRODUCT_TYPE.UNION.name()));
			if(null!=scenicVo.getTicketProductList().get(Constant.SUB_PRODUCT_TYPE.SUIT.name()))ticketlist.addAll(scenicVo.getTicketProductList().get(Constant.SUB_PRODUCT_TYPE.SUIT.name()));
			for(ScenicProductAndBranchListVO vo:ticketlist){
				branchSearchInfoList.addAll(vo.getBranchSearchInfo());
			}
			for(ProdBranchSearchInfo p:branchSearchInfoList){
				sellPriceList.add(p.getSellPrice());
			}
		}
		if(null!=scenicVo.getFreeNessAndHotelSuitProductList()&&scenicVo.getFreeNessAndHotelSuitProductList().size()>0){
			List<ProductSearchInfo> pList=scenicVo.getFreeNessAndHotelSuitProductList();
			for(ProductSearchInfo p2:pList){
				sellPriceList.add(p2.getSellPrice());
			}
		}
		if(null!=scenicVo.getGroupAndBusTabNameList()&&null!=scenicVo.getGroupAndBusDataMap()&&scenicVo.getGroupAndBusTabNameList().size()>0&&scenicVo.getGroupAndBusDataMap().size()>0){
			String sName=scenicVo.getGroupAndBusTabNameList().get(0);
			List<ProductSearchInfo>  pList2=scenicVo.getGroupAndBusDataMap().get(sName);
			for(ProductSearchInfo p22:pList2){
				sellPriceList.add(p22.getSellPrice());
			}
		}
		if(null!=sellPriceList&&sellPriceList.size()>0){
			Long lowPrice = new Long(0);
			for (int j = 0; j < sellPriceList.size(); j++) {
				if (j == 0) {
					lowPrice = sellPriceList.get(0);
				} else {
					if (lowPrice.longValue() > sellPriceList.get(j).longValue()) {
						lowPrice = sellPriceList.get(j);
					}
				}
			}
			return lowPrice/100;
		}else{
			return 0L;
		}
		
		
	}
	
	/**
	 * 
	 * @param placeId
	 * @return
	 * @author nixianjun 2014-2-24
	 */
	private List<ProductSearchInfo> queryTicketProductByPlaceId(Long placeId) {
		Map pm = new HashedMap();
		pm.put("placeId", placeId);
		pm.put("isTicket", ProductSearchInfo.IS_TICKET.TICKET.getCode());
		pm.put("channel", PlaceUtils.FRONTEND);
		List<ProductSearchInfo> productTicketList = productSearchInfoService
				.queryProductSearchInfoByParam(pm);
		return productTicketList;
	}

	/**
	 * 
	 * @param placeId
	 * @return 
	 * @author nixianjun 2014-2-24
	 */
	private List<ProductSearchInfo> queryProductByPlaceId(Long placeId) {
		Map pm = new HashedMap();
		pm.put("placeId", placeId);
		//pm.put("isTicket", ProductSearchInfo.IS_TICKET.TICKET.getCode());
		pm.put("channel", PlaceUtils.FRONTEND);
		List<ProductSearchInfo> productTicketList = productSearchInfoService
				.queryProductSearchInfoByParam(pm);
		return productTicketList;
	}
	/**
	 * 最新一条点评
	 * 
	 * @author nixianjun 2014-2-24
	 */
	private void addlastComment(){
		Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("placeId", this.place.getPlaceId());
			parameters.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name()); //审核通过的
			parameters.put("createTime321", "true"); //按时间倒序
			parameters.put("_startRow", 1);
			parameters.put("_endRow", 10);//默认景点页获取5条点评
			List<CommonCmtCommentVO> cmtCommentVOList = cmtCommentService.getCmtCommentList(parameters);
			if(null!=cmtCommentVOList&&cmtCommentVOList.size()>0){
				scenicVo.setLastcommonCmtCommentVO(cmtCommentVOList.get(0));
			}
	}
	
	@Action("/ticket/ajaxGetNewsComment")
	public void ajaxGetNewsComment(){
		Map map=new HashedMap();
		List<CommonCmtCommentVO> commList=cmtCommentService.getNewestCommentByPlaceId(this.place.getPlaceId(),5);
		if(null!=commList&&commList.size()>0){
			map.put("success", "true");
			map.put("message", commList.get(0));
		}else{
			map.put("success", "false");
			map.put("message", commList.get(0));
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public ScenicVo getScenicVo() {
		return scenicVo;
	}

	public void setScenicVo(ScenicVo scenicVo) {
		this.scenicVo = scenicVo;
	}


	public String getDescripTion() {
		return descripTion;
	}


	public void setDescripTion(String descripTion) {
		this.descripTion = descripTion;
	}


	public String getTrafficInfo() {
		return trafficInfo;
	}


	public void setTrafficInfo(String trafficInfo) {
		this.trafficInfo = trafficInfo;
	}


	public TuangouSearchService getTuangouSearchService() {
		return tuangouSearchService;
	}


	public void setTuangouSearchService(TuangouSearchService tuangouSearchService) {
		this.tuangouSearchService = tuangouSearchService;
	}



	public Map<Long, Object> getProMap() {
		return proMap;
	}


	public void setProMap(Map<Long, Object> proMap) {
		this.proMap = proMap;
	}


	public String getDestinationExplore() {
		return destinationExplore;
	}


	public void setDestinationExplore(String destinationExplore) {
		this.destinationExplore = destinationExplore;
	}

	public String getBasePath() {
		String path = this.getRequest().getContextPath();
		String basePath = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":" + getRequest().getServerPort()
				+ path + "/";
		return basePath;
	}
}
