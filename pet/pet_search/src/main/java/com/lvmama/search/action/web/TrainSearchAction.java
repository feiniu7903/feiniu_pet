/**
 * 
 */
package com.lvmama.search.action.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.comm.search.vo.TrainBean;
import com.lvmama.comm.search.vo.TrainSearchVO;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.search.util.AccessURLException;
import com.lvmama.search.util.LoggerParms;
import com.lvmama.search.util.ProdTrainCacheComparator;

/**
 * 暂时不通过luncene查询数据
 * @author yangbin
 *
 */
@Namespace("/train")
public class TrainSearchAction extends SearchAction{
	
	

	public TrainSearchAction() {
		super("train", TrainSearchVO.class,"CH_TRAIN_SEARCH");
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5841437426053286268L;
	private ProdTrainCacheService prodTrainCacheService;
	private ProdTrainService prodTrainService;
	private TrainSearchVO trainSearchVO;
	private List<TrainBean> trainBeanList=Collections.emptyList();
	private boolean showBookFlag=false;
	private String toDest;//到这地
	private String departurePinyin;
	private String arrivalPinyin;
	private Map<String, String> formStationList;
	private Map<String, String> toStationList;
	@Autowired
	private PlaceService placeService;
	private Long toplaceId;//到达地id
	private List<Place> recommendPlaceList;
	private List<Place> recommendHotelList;
	
	@Override
	protected void parseFilterStr() {
		//trainSearchVO = (TrainSearchVO)super.fillSearchvo();
	}
	
	/**
	 * 搜索方法
	 * @return
	 */
	@Action("search")
	public String search(){
		//火车票
		return searchTrain();
	}
	private String searchTrain(){
		HttpServletRequest req = getRequest();
		HttpServletResponse resp = getResponse();
		StringBuffer log_content = (StringBuffer) ServletUtil.getSession(req, resp, "SEARCH_BUSINESS_LOG_CONTENT");
		if(log_content == null){
			log_content = new StringBuffer();
			UserUser user = (UserUser)getSession(Constant.SESSION_FRONT_USER);
			log_content.append(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS")).append("\t");//开始时间
			log_content.append(LoggerParms.getIpAddr(req)).append("\t");//ip地址
			log_content.append(req.getHeader("referer")).append("\t");//referer
			log_content.append("DIRECT").append("\t");//搜索的动作
			log_content.append(ServletUtil.getLvSessionId(req, resp)).append("\t");//lvsessionId
			log_content.append(user == null ?"guest":user.getUserNo()).append("\t");//userNo
		}else{
			ServletUtil.removeSession(req, resp, "SEARCH_BUSINESS_LOG_CONTENT");
		}
		try{
			this.parseParams();
			this.parseFilterStr();
			this.searchData();
			
			//this.initPageURL();
			this.initTitle();
		}catch (AccessURLException e){
				searchvo = new SearchVO();
				return "urlerror";
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see com.lvmama.search.action.web.SearchAction#parseParams()
	 */
	@Override
	protected void parseParams() {
		trainSearchVO = new TrainSearchVO();
		if(!StringUtils.isEmpty(params)){
			String[] array=params.split("_");
			if(array.length<2){
				
			}
			
			
			if(array.length>1){
				trainSearchVO.setDate(array[1]);
			}
			if(array.length>2){
				trainSearchVO.setLineName(array[2]);
			}
			
			Date date = null;
			if(StringUtils.isNotEmpty(trainSearchVO.getDate())){
				if(trainSearchVO.getDate().matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
				date = getDate(trainSearchVO.getDate());
				}
			}
			if(date==null){
				date = DateUtils.addDays(new Date(), 3);	
			}else{
				Date tmp =DateUtils.addHours(date, -36);
				showBookFlag=tmp.before(new Date());
			}
			trainSearchVO.setDateDate(DateUtil.getDayStart(date));
			boolean flag =trainSearchVO.setStationKey(array[0]);
			if(flag){
				departurePinyin = trainSearchVO.getDeparture();
				arrivalPinyin = trainSearchVO.getArrival();
				
				LineStation fromLineStation = prodTrainService.getLineStationByStationPinyin(trainSearchVO.getDeparture());
				if(fromLineStation != null) {
					fromDest = fromLineStation.getStationName();
					trainSearchVO.setFromCityPinyin(fromLineStation.getCityPinyin());
				}
				LineStation toLineStation = prodTrainService.getLineStationByStationPinyin(trainSearchVO.getArrival());
				if(toLineStation != null) {
					toDest = toLineStation.getStationName();
					trainSearchVO.setToCityPinyin(toLineStation.getCityPinyin());
					toplaceId=toLineStation.getPlaceId();
				}
				
				filedValueMap.put("fromDest", fromDest);
				filedValueMap.put("toDest", toDest);
			}
//			System.out.println(fromDest+"    "+toDest+"    "+departurePinyin+"      "+arrivalPinyin+"   "+DateUtil.formatDate(trainSearchVO.getDateDate(), "yyyy-MM-dd HH:mm:ss"));
		}
	}

	private static Date getDate(String str) {
		String[] array=str.split("-");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, NumberUtils.toInt(array[0]));
		c.set(Calendar.MONTH, NumberUtils.toInt(array[1])-1);
		c.set(Calendar.DAY_OF_MONTH, NumberUtils.toInt(array[2]));
		return DateUtil.getDayStart(c.getTime());
	}

	@Override
	protected void searchData() {
		//先同步，再搜索
//		trainDataSyncServiceProxy.syncCity(departurePinyin,arrivalPinyin, trainSearchVO.getDateDate());
		List<ProdTrainCache> list = prodTrainCacheService.selectCacheList(trainSearchVO);
		compositeProduct(list);
		if(!list.isEmpty()) {
			formStationList = new HashMap<String, String>();
			toStationList = new HashMap<String, String>();
			for (ProdTrainCache ptc : list) {
				String fromPinyin = ptc.getDepartureStation();
				String toPinyin = ptc.getArrivalStation();
				if(!formStationList.containsKey(fromPinyin)) {
					formStationList.put(fromPinyin, ptc.getDepartureStationName());
				}
				if(!toStationList.containsKey(toPinyin)) {
					toStationList.put(toPinyin, ptc.getArrivalStationName());
				}
			}
		}
		
		//增加酒店和景区推荐模块
		if(toplaceId != null){
		 recommendPlaceList = placeService.getPlaceInfoBySameParentPlaceIdTrain(toplaceId,Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode(),6L);
		 recommendHotelList = placeService.getPlaceInfoBySameParentPlaceIdTrain(toplaceId,Constant.PLACE_STAGE.PLACE_FOR_HOTEL.getCode(),6L);
		 Place bean;
		 for (int i = 0; i < recommendPlaceList.size(); i++) {
			bean = recommendPlaceList.get(i);
			recommendPlaceList.get(i).setSellPrice(bean.getSellPriceInteger()*1L);
		 }
		 for (int i = 0; i < recommendHotelList.size(); i++) {
				bean = recommendHotelList.get(i);
				recommendHotelList.get(i).setSellPrice(bean.getSellPriceInteger()*1L);
			 }
		}
		
	}
	
	String[] soldoutSeatTypes;
	

	private void compositeProduct(List<ProdTrainCache> list){
		Map<Long,List<ProdTrainCache>> map = new HashMap<Long, List<ProdTrainCache>>();
		Map<Long,Boolean> mapPullman = new HashMap<Long, Boolean>();
		soldoutSeatTypes = SearchConstants.getInstance().getTrainSoldoutList();
		for(ProdTrainCache cache: list){
			if(soldoutSeatTypes!=null){
				if(!cache.hasSoldout()){
					if(ArrayUtils.contains(soldoutSeatTypes, cache.getSeatType())){
						cache.setSoldout("true");
					}
				}
			}
			cache.makeSoldout();
			List<ProdTrainCache> trainList = null;
			boolean hasPullman=ArrayUtils.contains(seat_catalog, cache.getSeatType());
			if(hasPullman){
				mapPullman.put(cache.getStationStationId(), true);
			}
			if(map.containsKey(cache.getStationStationId())){
				trainList =map.get(cache.getStationStationId());
			}else{
				trainList = new ArrayList<ProdTrainCache>();
				map.put(cache.getStationStationId(), trainList);
			}
			trainList.add(cache);
		}
	
		trainBeanList = new ArrayList<TrainBean>();
		for(Long key:map.keySet()){
			TrainBean bean = new TrainBean();
			List<ProdTrainCache> cacheList = map.get(key);
			if(mapPullman.containsKey(key)){
				cacheList = compositePullman(cacheList);
			}
			Collections.sort(cacheList);
			bean.setTicketList(cacheList);
			
//			updateCache(bean);
			trainBeanList.add(bean);
		}
		
		Collections.sort(trainBeanList);
	}
	
	private List<ProdTrainCache> compositePullman(List<ProdTrainCache> list){
		List<ProdTrainCache> result = new ArrayList<ProdTrainCache>();
		List<ProdTrainCache> pullman = new ArrayList<ProdTrainCache>();
		for(ProdTrainCache c:list){
			if(ArrayUtils.contains(seat_catalog,c.getSeatType())){
				pullman.add(c);
			}else{
				result.add(c);
			}
		}
		if(!pullman.isEmpty()){
			if(pullman.size()>1){
				Collections.sort(pullman,new ProdTrainCacheComparator());
				ProdTrainCache c = pullman.get(pullman.size()-1);//取最后一个。下单以该数据为准
				c.setPullmanList(pullman);
				result.add(c);
			}else{
				result.add(pullman.get(0));
			}
		}
		
		return result;
	}
	
	private static final String[] seat_catalog = {
			Constant.TRAIN_SEAT_CATALOG.SC_212.getAttr1(),
			Constant.TRAIN_SEAT_CATALOG.SC_213.getAttr1(),
			Constant.TRAIN_SEAT_CATALOG.SC_214.getAttr1() 
	};
	
//	private void updateCache(TrainBean bean){
//		bean.setLineStationStation(prodTrainService.getStationStationDetailById(bean.getFirst().getStationStationId()));
//		bean.setLineInfo(prodTrainService.getLineInfo(bean.getFirst().getLineInfoId()));
//	}

	public List<TrainBean> getTrainBeanList() {
		return trainBeanList;
	}



	public void setProdTrainCacheService(ProdTrainCacheService prodTrainCacheService) {
		this.prodTrainCacheService = prodTrainCacheService;
	}



	public TrainSearchVO getTrainSearchVO() {
		return trainSearchVO;
	}



	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}



	public boolean isShowBookFlag() {
		return showBookFlag;
	}



	public String getToDest() {
		return toDest;
	}



	public String getDeparturePinyin() {
		return departurePinyin;
	}



	public String getArrivalPinyin() {
		return arrivalPinyin;
	}
	
	public static void main(String[] args) {
		Date date=getDate("2013-06-10");
		System.out.println(date);
		System.out.println(DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss"));
	}

	public Map<String, String> getFormStationList() {
		return formStationList;
	}

	public Map<String, String> getToStationList() {
		return toStationList;
	}

	/**
	 * @return the recommendPlaceList
	 */
	public List<Place> getRecommendPlaceList() {
		return recommendPlaceList;
	}

	/**
	 * @param recommendPlaceList the recommendPlaceList to set
	 */
	public void setRecommendPlaceList(List<Place> recommendPlaceList) {
		this.recommendPlaceList = recommendPlaceList;
	}

	/**
	 * @return the recommendHotelList
	 */
	public List<Place> getRecommendHotelList() {
		return recommendHotelList;
	}

	/**
	 * @param recommendHotelList the recommendHotelList to set
	 */
	public void setRecommendHotelList(List<Place> recommendHotelList) {
		this.recommendHotelList = recommendHotelList;
	}

}
