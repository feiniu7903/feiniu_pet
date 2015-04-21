package com.lvmama.pet.web.place;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.comm.pet.vo.place.ScenicVo;
import com.lvmama.comm.search.service.TuangouSearchService;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.utils.homePage.TwoDimensionCode;
import com.lvmama.comm.vo.Constant;

/**
 * 新作景点详情页
 * @author nixianjun 2013-7-8
 *
 */
@Results({ 
	@Result(name = "scenicDetail", type="freemarker", location = "/WEB-INF/pages/scenicDetail/scenicDetail.ftl"),
	@Result(name = "navigationNew", type="freemarker", location = "/WEB-INF/pages/common/navigationNew.ftl"),
	@Result(name = "oldscenic301Forword",params={"status", "301", "headers.Location", "/dest/${place.pinYin}"}, type="httpheader"),
	@Result(name = "oldscenic301Forword2",params={"status", "301", "headers.Location", "http://ticket.lvmama.com/scenic-${place.placeId}"}, type="httpheader")
  	})
public class ScenicAction extends DestBaseAction{
	 // 序列值
 	private static final long serialVersionUID = 6125091387893823362L;
 	//日志输出器
 	private static final Log log = LogFactory.getLog(ScenicAction.class);
 	// 缓存数据
 	private Map<String,Object> navigationDataMap;
 	private ScenicVo scenicVo;
 	private SeoLinksService seoLinksService; 
 	private String  descriptionFlag="false";
 	private String  trafficInfoFlag="false";
 	private Place place;
 	
 	/**
	 * 目的地页面远程服务
	 */
	private PlacePageService placePageService;
	/**
	 * 团购服务
	 */
	private TuangouSearchService  tuangouSearchService;
	
	@Action("/place/scenicNewAction")
	public String oldscenic301Forword2() {
		// place 不存在情况
		if (null == place
				|| null == place.getPlaceId()
				|| !"Y".equalsIgnoreCase(place.getIsValid())
				|| !Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()
						.equalsIgnoreCase(place.getStage())) {
			debug(log,
					"The place is null or it's not a scenic, redirect to error page.");
			return ERROR;
		}
		return "oldscenic301Forword2";
	}
	
 	@Override
	@Action("/place/scenicNewAction2")
	public String execute() {
		// place 不存在情况
		if (null == place
				|| null == place.getPlaceId()
				|| !"Y".equalsIgnoreCase(place.getIsValid())
				|| !Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()
						.equalsIgnoreCase(place.getStage())) {
			debug(log,
					"The place is null or it's not a scenic, redirect to error page.");
			return ERROR;
		}
		
		/**
		 * 如果place描述+交通未创建文件，新建文件
		 */
 		if(!(getDescriptionFlag().equals("true")&&getTrafficInfoFlag().equals("true"))){
 			 Place p =  placeService.getDescripAndTrafficByPlaceId(place.getPlaceId());
 			 PlaceUtils.createDescriptionAndTrafficInfo(p);
 			log.info("#####创建描述和交通文件:placeid:"+place.getPlaceId());
		}else {
 			log.info("##### 不做创建描述和交通文件placeid:"+place.getPlaceId());
		}
		
		
		//缓存数据
        String memcachedKey = PlaceUtils.SCENIC_MEMCACHED_PREFIX_KEY + place.getPlaceId();
    	scenicVo = (ScenicVo) MemcachedUtil.getInstance().get(memcachedKey);
		if (null == scenicVo) {
			scenicVo = placePageService.getScenicPageMainInfo(place);
			//DOTO 国庆期间暂时取消,待优化
			//周边景点
			scenicVo.setVictinityScenic(getVicinityByPlace(place.getPlaceId(), null, Long.parseLong(Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()), 6L));
			//周边酒店
			scenicVo.setVictinityHotel(getVicinityByPlace(place.getPlaceId(), null, Long.parseLong(Constant.PLACE_STAGE.PLACE_FOR_HOTEL.getCode()), 6L));
			//相同主题景点
			scenicVo.setSameSubjectScenic(getVicinityByPlace(place.getPlaceId(), place.getFirstTopic(), Long.parseLong(Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode()), 6L));
			//相关团购产品
			List<ProductBean> productBeans =  tuangouSearchService.search(place.getName(), "ROUTE", 1);
			
			//seo友情链接
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("placeId",place.getPlaceId());
			map.put("location",Constant.PLACE_SEOLINKS.INDEX.getCode());
			scenicVo.setSeoList(PlaceUtils.removeRepeatData(seoLinksService.batchQuerySeoLinksByParam(map)));
			
			
			if (!productBeans.isEmpty()) {
				scenicVo.setTuangouProduct(productBeans.get(0));
			} 
			MemcachedUtil.getInstance().set(memcachedKey, MemcachedUtil.ONE_HOUR, scenicVo);
  		}
		
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
		
		return "scenicDetail";
	}
 	
	
	/**
	 * 面包屑导航
	 * @return
	 * @author nixianjun 2013-7-18
	 */
	@SuppressWarnings("unchecked")
	@Action("/place/navigationNew")
	public String navigationNew() {
		String key=PlaceUtils.LOADNAVIGATION_MEMCACHED_PREFIX_KEY + getId();
		navigationDataMap=(Map<String,Object>)MemcachedUtil.getInstance().get(key);
		if(navigationDataMap==null){
			navigationDataMap=placeService.loadNavigation(getId()); 
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, navigationDataMap);
 		}
		return "navigationNew";
	}
	public Long getId(){
		if(null!=place){
			return place.getPlaceId();
		}else{
			return null;
		}
	}
 	
	@Action("/place/oldscenic301Forword")
	public String oldscenic301Forword(){
		return "oldscenic301Forword";
	}
	
	/**
	 * 由于此操作将占用大量数据库CPU且无法优化，故延长缓存时间
	 * @param placeId
	 * @param subject
	 * @param stage
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<PlaceSearchInfo> getVicinityByPlace(Long placeId, String subject, Long stage, Long limit) {
		String memcachedKey = "VICINITY_PLACE_" + placeId + (StringUtils.isEmpty(subject) ? "" : "_" + subject) + "_" + stage;
		List<PlaceSearchInfo> result= (List<PlaceSearchInfo>) MemcachedUtil.getInstance().get(memcachedKey);
		if (null == result) {
			result = this.placePageService.getVicinityByPlace(placeId, subject, stage, limit);
			Calendar now = Calendar.getInstance();
			MemcachedUtil.getInstance().set(memcachedKey, 24 * 60 * 60 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND), result);
		}
		return result;
	}
	
	public void setPlacePageService(PlacePageService placePageService) {
		this.placePageService = placePageService;
	}


	public ScenicVo getScenicVo() {
		return scenicVo;
	}

	
	public Place getPlace() {
		return place;
	}


	public void setPlace(Place place) {
		this.place = place;
	}


	public void setTuangouSearchService(TuangouSearchService tuangouSearchService) {
		this.tuangouSearchService = tuangouSearchService;
	}


	public Map<String, Object> getNavigationDataMap() {
		return navigationDataMap;
	}


	public void setNavigationDataMap(Map<String, Object> navigationDataMap) {
		this.navigationDataMap = navigationDataMap;
	}


    public SeoLinksService getSeoLinksService() {
        return seoLinksService;
    }


    public void setSeoLinksService(SeoLinksService seoLinksService) {
        this.seoLinksService = seoLinksService;
    }
    public String getDescriptionPath(){
		  String descriptionPath=PlaceUtils.FILEDIR+"/"+PlaceUtils.DESCRIPTION+"/"+place.getStage()+"_"+place.getPlaceId()+".ftl";
		  return descriptionPath;
    }
    public String getTrafficinfoPath(){
		  String t=PlaceUtils.FILEDIR+"/"+PlaceUtils.TRAFFICINFO+"/"+place.getStage()+"_"+place.getPlaceId()+".ftl";
		  return t;
  }


	/**
	 * @return the descriptionFlag
	 */
	public String getDescriptionFlag() {
		try {
			descriptionFlag=String.valueOf(FileUtil.isExistContentForFile(ResourceUtil.getResourceFileName(getDescriptionPath())));
		} catch (IOException e) {
 			e.printStackTrace();
		}
		return descriptionFlag;
	}


	/**
	 * @return the trafficInfoFlag
	 */
	public String getTrafficInfoFlag() {
		try {
			trafficInfoFlag=String.valueOf(FileUtil.isExistContentForFile(ResourceUtil.getResourceFileName(getTrafficinfoPath())));
		} catch (IOException e) {
 			e.printStackTrace();
		}
		return trafficInfoFlag;
 	}
}
