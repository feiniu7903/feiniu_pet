package com.lvmama.clutter.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.clutter.service.IClientTrainService;
import com.lvmama.clutter.utils.JSONUtil;
import com.lvmama.clutter.utils.TrainUtils;
import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.pet.po.search.ProdTrainCache;
import com.lvmama.comm.pet.service.search.ProdTrainCacheService;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.TrainSearchVO;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;

public class ClientTrainServiceImpl extends AppServiceImpl implements
		IClientTrainService {
	private boolean showBookFlag = false;
	protected final static String MOBILE_TRAIN_CACHE = "MOBILE_TRAIN_CACHE";
	private String[] soldoutSeatTypes;
//	private final String[] hotCities = new String[] { "上海", "杭州", "苏州", "南京",
//			"无锡", "北京", "广州", "深圳", "天津", "成都", "重庆", "西安", "徐州", "郑州", "武汉",
//			"长沙", "兰州", "大连", "济南", "青岛" };
	private final String[] hotCities = new String[] { "上海", "北京", "杭州", "广州",
	"无锡", "南京", "成都", "西安", "郑州", "重庆", "武汉", "长沙", "厦门", "南昌", "沈阳",
	"天津"};
	private static final String[] seat_catalog = {
			Constant.TRAIN_SEAT_CATALOG.SC_212.getAttr1(),
			Constant.TRAIN_SEAT_CATALOG.SC_213.getAttr1(),
			Constant.TRAIN_SEAT_CATALOG.SC_214.getAttr1() };

	public Map<String, Object> getTrainTypes(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> trainTypes = TrainUtils.getTrainTypes();
		resultMap.put("trainTypes", trainTypes);
		return resultMap;
	}

	/**
	 * 出发城市站点
	 * 
	 * @param citySearch
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTrainDepartureStations(String citySearch)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityPinyin", citySearch);
		List<LineStation> lineStations = prodTrainService
				.selectLineStationByParam(params);
		resultMap.put("lineStations", lineStations);
		return resultMap;
	}

	/**
	 * 到达城市站点
	 * 
	 * @param citySearch
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTrainArrivalStations(String citySearch)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cityPinyin", citySearch);
		List<LineStation> lineStations = prodTrainService
				.selectLineStationByParam(params);
		resultMap.put("lineStations", lineStations);
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	private List<LineStation> getAllLineStations() {
		String key = "CLUTTER_TRAIN_ALL_STATIONS";
		Object o = MemcachedUtil.getInstance().get(key);
		List<LineStation> lineStations = null;
		if (o == null) {
			lineStations = mobileTrainService.getAllLineStations();
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,
					lineStations);
		} else {
			lineStations = (List<LineStation>) o;
		}
		return lineStations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> listTrainCities(Map<String, Object> param)
			throws Exception {
		String key = "CLUTTER_TRAIN_CITIES";
		Object o = MemcachedUtil.getInstance().get(key);
		Map<String, Object> resultMap = null;
		if (o == null) {
			resultMap = new HashMap<String, Object>();
			List<LineStation> lineStations = this.getAllLineStations();
			// String[] excludeProperties = new String[] { "stationId",
			// "stationPy", "oldStationPinyin", "cityName", "cityPinyin" /*
			// * "stationName"
			// * ,
			// * "stationPinyin"
			// * ,
			// * "placeId"
			// * ,
			// */};
			// JSONArray jsonTempTrainCities = JSONUtil.jsonArrayPropertyIgnore(
			// lineStations, excludeProperties);
			JSONArray jsonTempTrainCities = JSONArray.fromObject(lineStations);
			JSONArray jsonTrainCities = new JSONArray();
			Set<String> filterSet = new HashSet<String>();
			for (Object obj : jsonTempTrainCities) {
				JSONObject jsonObj = (JSONObject) obj;
				Object cityNameObj = jsonObj.get("stationName");
				if (null == cityNameObj || "".equals(cityNameObj.toString())) {
					// 去掉城市名称为空的记录
				} else {
					String cityName = cityNameObj.toString();
					if (!filterSet.contains(cityName)) {
						filterSet.add(cityName);
						if (Arrays.asList(hotCities).contains((cityName))) {
							jsonObj.put("isHot", true);
							jsonTrainCities.add(jsonObj);
						} else {
							jsonTrainCities.add(jsonObj);
						}
					}
				}
			}
			filterSet = null;
			resultMap.put("trainCities", jsonTrainCities);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,
					resultMap);
		} else {
			resultMap = (Map<String, Object>) o;
		}
		return initVersion(resultMap, param);
	}

	@Override
	public Map<String, Object> searchTrainPlace(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String citySearch = (String) param.get("key");
		params.put("citySearch", citySearch);
		List<LineStation> lineStations = prodTrainService
				.selectLineStationByParam(params);

		resultMap.put("lineStations", lineStations);
		return resultMap;
	}

	/**
	 * 根据中文名城市获取城市拼音
	 * 
	 * @param zhCity
	 * @return
	 */
	private String getCityPinyin(String zhCity) {
		List<LineStation> lineStations = this.getAllLineStations();
		for (LineStation lineStation : lineStations) {
			if (zhCity.equals(lineStation.getCityName())) {
				return lineStation.getCityPinyin();
			}
		}
		return null;
	}

	/**
	 * 根据中文名火车站获取火车站拼音
	 * 
	 * @param zhStation
	 * @return
	 */
	private String getStationPinyin(String zhStation) {
		List<LineStation> lineStations = this.getAllLineStations();
		for (LineStation lineStation : lineStations) {
			if (zhStation.equals(lineStation.getStationName())) {
				return lineStation.getStationPinyin();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> search(Map<String, Object> param)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		TrainSearchVO trainSearchVO = new TrainSearchVO();
		String dateStr = (String) param.get("date");// 发车时间
		String lineName = (String) param.get("line");// 车次
		String departure = (String) param.get("departure");// 出发地站
		String arrival = (String) param.get("arrival");// 目的地站
		trainSearchVO.setDate(dateStr);
		trainSearchVO.setLineName(lineName);

		Date date = null;
		if (StringUtils.isNotEmpty(trainSearchVO.getDate())) {
			if (trainSearchVO.getDate().matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {// 符合日期格式
				date = getDate(trainSearchVO.getDate());
			}
		}
		if (date == null) {
			date = DateUtils.addDays(new Date(), 3);
		} else {
			Date tmp = DateUtils.addHours(date, -36);
			showBookFlag = tmp.before(new Date());
		}
		trainSearchVO.setDateDate(DateUtil.getDayStart(date));

		String regex = "^[A-Za-z]";// 如果不是以字母开头表示是输入的中文
		Pattern pattern = Pattern.compile(regex);
		Matcher mDeparture = pattern.matcher(departure);
		if (!mDeparture.find()) {
			// 处理中文城市
			String tempDeparture = this.getCityPinyin(departure);
			// 处理中文火车站
			if (StringUtils.isEmpty(tempDeparture)) {// 如果未匹配到对应的城市，就匹配火车站
				tempDeparture = this.getStationPinyin(departure);
			} else {
				// @See
				// ProdTrainCacheServiceImpl#selectCacheList,trainSearchVO.getDeparture().equalsIgnoreCase(trainSearchVO.getFromCityPinyin())
				trainSearchVO.setFromCityPinyin(tempDeparture);
			}
			if (!StringUtils.isEmpty(tempDeparture)) {
				departure = tempDeparture;
			}
		}
		trainSearchVO.setDeparture(departure);

		Matcher mArrival = pattern.matcher(arrival);
		if (!mArrival.find()) {
			// 处理中文城市
			String tempArrival = this.getCityPinyin(arrival);
			// 处理中文火车站
			if (StringUtils.isEmpty(tempArrival)) {// 如果未匹配到对应的城市，就匹配火车站
				tempArrival = this.getStationPinyin(arrival);
			} else {
				// @See
				// ProdTrainCacheServiceImpl#selectCacheList,trainSearchVO.getArrival().equalsIgnoreCase(trainSearchVO.getToCityPinyin())
				trainSearchVO.setToCityPinyin(tempArrival);
			}
			if (!StringUtils.isEmpty(tempArrival)) {
				arrival = tempArrival;
			}
		}
		trainSearchVO.setArrival(arrival);

		LineStation fromLineStation = prodTrainService
				.getLineStationByStationPinyin(trainSearchVO.getDeparture());
		if (fromLineStation != null) {
			trainSearchVO.setFromCityPinyin(fromLineStation.getCityPinyin());
		}
		LineStation toLineStation = prodTrainService
				.getLineStationByStationPinyin(trainSearchVO.getArrival());
		if (toLineStation != null) {
			trainSearchVO.setToCityPinyin(toLineStation.getCityPinyin());
		}

		List<ProdTrainCache> list = prodTrainCacheService
				.selectCacheList(trainSearchVO);
		List<ProdTrainCache> trains = this.compositeProduct(list);

		trains = this.processData(param, trains);

		Set<String> departureStations = TrainUtils.getDepartureStations(trains);
		Set<String> arrivalStations = TrainUtils.getArrivalStations(trains);

		JSONArray jsonTrains = JSONUtil.jsonArrayDateFormat(trains,
				"yyyy-MM-dd");

		resultMap.put("trains", jsonTrains);
		@SuppressWarnings("rawtypes")
		List filterDatas = new ArrayList();
		filterDatas.add(TrainUtils.getTrainTypes());
		filterDatas.add(TrainUtils.getTrainDepartureTime());
		filterDatas.add(TrainUtils.getTrainArrivalTime());
		// filterDatas.add(TrainUtils.getSortTypes());

		// 根据城市查询，列出该城市所有火车站信息
		String departureStation = trainSearchVO.getFromCityPinyin();
		if (!StringUtils.isEmpty(departureStation)) {
			List<LineStation> subLineStations = (List<LineStation>) this
					.getTrainDepartureStations(departureStation).get(
							"lineStations");
			filterDatas.add(TrainUtils.getDepartureStationsOfCity(
					subLineStations, departureStations));
		}

		String arrivalStation = trainSearchVO.getToCityPinyin();
		if (!StringUtils.isEmpty(arrivalStation)) {
			List<LineStation> subLineStations = (List<LineStation>) this
					.getTrainDepartureStations(arrivalStation).get(
							"lineStations");
			filterDatas.add(TrainUtils.getArrivalStationsOfCity(
					subLineStations, arrivalStations));
		}

		resultMap.put("filterDatas", filterDatas);
		return resultMap;
	}

	/**
	 * 处理数据
	 * 
	 * @param param
	 * @param trains
	 * @return
	 */
	private List<ProdTrainCache> processData(Map<String, Object> param,
			List<ProdTrainCache> trains) {

		// 过滤相同车次,由于相同车次存在各种席别
		List<ProdTrainCache> prodTrainCaches = new ArrayList<ProdTrainCache>(
				100);
		Set<String> filterSet = new HashSet<String>();
		for (ProdTrainCache prodTrainCache : trains) {
			String lineName = prodTrainCache.getLineName();
			if (!filterSet.contains(lineName)) {
				filterSet.add(lineName);
				prodTrainCaches.add(prodTrainCache);
			}
		}
		filterSet = null;
		trains = prodTrainCaches;

		// 过滤车次类型
		String trainType = (String) param.get("trainType");
		if (!StringUtils.isEmpty(trainType)) {
			List<ProdTrainCache> tempTrains = new ArrayList<ProdTrainCache>(100);
			for (ProdTrainCache prodTrainCache : trains) {
				if("GD".equals(trainType)){//G-高铁,D-动车
					if ("GD".indexOf(prodTrainCache.getCharCategory())!=-1) {//保留高铁、动车
						tempTrains.add(prodTrainCache);
					}
				}else if("OTHER".equals(trainType)){//普通
					if ("GD".indexOf(prodTrainCache.getCharCategory())==-1) {// 过滤高铁、动车
						tempTrains.add(prodTrainCache);
					}
				}else{
					tempTrains.add(prodTrainCache);
				}
			}
			trains = tempTrains;
		}

		// 时间范围内的车次
		String departureTime = (String) param.get("departureTime");
		String arrivalTime = (String) param.get("arrivalTime");
		if (!StringUtils.isEmpty(departureTime)
				&& !StringUtils.isEmpty(arrivalTime)) {// 如果发车时间和到站时间都不为空
			List<ProdTrainCache> tempTrains = new ArrayList<ProdTrainCache>(100);

			String[] arrivalTimes = arrivalTime.split("-");
			String[] departureTimes = departureTime.split("-");
			for (ProdTrainCache prodTrainCache : trains) {
				if (Long.parseLong(departureTimes[0]) < prodTrainCache
						.getDepartureTime()
						&& Long.parseLong(departureTimes[1]) > prodTrainCache
								.getDepartureTime()) {
					if (Long.parseLong(arrivalTimes[0]) < prodTrainCache
							.getArrivalTime()
							&& Long.parseLong(arrivalTimes[1]) > prodTrainCache
									.getArrivalTime()) {
						tempTrains.add(prodTrainCache);
					}
				}
			}
			trains = tempTrains;
		} else if (!StringUtils.isEmpty(departureTime)) {// 过滤出发时间范围内的车次
			List<ProdTrainCache> tempTrains = new ArrayList<ProdTrainCache>(100);

			String[] departureTimes = departureTime.split("-");
			for (ProdTrainCache prodTrainCache : trains) {
				if (Long.parseLong(departureTimes[0]) < prodTrainCache
						.getDepartureTime()
						&& Long.parseLong(departureTimes[1]) > prodTrainCache
								.getDepartureTime()) {
					tempTrains.add(prodTrainCache);
				}
			}
			trains = tempTrains;
		} else if (!StringUtils.isEmpty(arrivalTime)) {// 过滤到站时间范围内的车次
			List<ProdTrainCache> tempTrains = new ArrayList<ProdTrainCache>(100);

			String[] arrivalTimes = arrivalTime.split("-");
			for (ProdTrainCache prodTrainCache : trains) {
				if (Long.parseLong(arrivalTimes[0]) < prodTrainCache
						.getArrivalTime()
						&& Long.parseLong(arrivalTimes[1]) > prodTrainCache
								.getArrivalTime()) {
					tempTrains.add(prodTrainCache);
				}
			}
			trains = tempTrains;
		}

		// 过滤出发站范围内的车次
		String departureStation = (String) param.get("departureStation");
		if (!StringUtils.isEmpty(departureStation)) {
			List<ProdTrainCache> tempTrains = new ArrayList<ProdTrainCache>(100);

			for (ProdTrainCache prodTrainCache : trains) {
				if (departureStation.equals(prodTrainCache
						.getDepartureStation())) {
					tempTrains.add(prodTrainCache);
				}
			}
			trains = tempTrains;
		}

		// 过滤出发站范围内的车次
		String arrivalStation = (String) param.get("arrivalStation");
		if (!StringUtils.isEmpty(arrivalStation)) {
			List<ProdTrainCache> tempTrains = new ArrayList<ProdTrainCache>(100);

			for (ProdTrainCache prodTrainCache : trains) {
				if (arrivalStation.equals(prodTrainCache.getArrivalStation())) {
					tempTrains.add(prodTrainCache);
				}
			}
			trains = tempTrains;
		}

		// 过滤出发站范围内的车次
		String sort = (String) param.get("sort");
		if (!StringUtils.isEmpty(sort)) {
			this.sort(sort, trains);
		}

		return trains;
	}

	/**
	 * 排序
	 * 
	 * @param sort
	 * @param trains
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sort(String sort, List<ProdTrainCache> trains) {
		if ("timeDesc".equals(sort)) {
			Collections.sort(trains, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					ProdTrainCache p1 = (ProdTrainCache) o1;
					ProdTrainCache p2 = (ProdTrainCache) o2;
					if (p1.getDepartureTime() > p2.getDepartureTime()) {
						return 1;
					} else if (p1.getDepartureTime() < p2.getDepartureTime()) {
						return -1;
					}
					return 0;
				}
			});

		} else if ("timeAsc".equals(sort)) {
			Collections.sort(trains, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					ProdTrainCache p1 = (ProdTrainCache) o1;
					ProdTrainCache p2 = (ProdTrainCache) o2;
					if (p1.getDepartureTime() > p2.getDepartureTime()) {
						return -1;
					} else if (p1.getDepartureTime() < p2.getDepartureTime()) {
						return 1;
					}
					return 0;
				}
			});
		} else if ("timeLengthDesc".equals(sort)) {
			Collections.sort(trains, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					ProdTrainCache p1 = (ProdTrainCache) o1;
					ProdTrainCache p2 = (ProdTrainCache) o2;
					return p1.getTakenTime().compareTo(p2.getTakenTime());
				}
			});
		} else if ("timeLengthAsc".equals(sort)) {
			Collections.sort(trains, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					ProdTrainCache p1 = (ProdTrainCache) o1;
					ProdTrainCache p2 = (ProdTrainCache) o2;
					return -p1.getTakenTime().compareTo(p2.getTakenTime());
				}
			});
		} else if ("priceDesc".equals(sort)) {
			Collections.sort(trains, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					ProdTrainCache p1 = (ProdTrainCache) o1;
					ProdTrainCache p2 = (ProdTrainCache) o2;
					if (p1.getPriceYuan() > p2.getPriceYuan()) {
						return 1;
					} else if (p1.getPriceYuan() < p2.getPriceYuan()) {
						return -1;
					} else {
						return 0;
					}
				}
			});
		} else if ("priceAsc".equals(sort)) {
			Collections.sort(trains, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					ProdTrainCache p1 = (ProdTrainCache) o1;
					ProdTrainCache p2 = (ProdTrainCache) o2;
					if (p1.getPriceYuan() > p2.getPriceYuan()) {
						return -1;
					} else if (p1.getPriceYuan() < p2.getPriceYuan()) {
						return 1;
					} else {
						return 0;
					}
				}
			});
		} else {
			Collections.sort(trains, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					ProdTrainCache p1 = (ProdTrainCache) o1;
					ProdTrainCache p2 = (ProdTrainCache) o2;
					if (p1.getDepartureTime() > p2.getDepartureTime()) {
						return 1;
					} else if (p1.getDepartureTime() < p2.getDepartureTime()) {
						return -1;
					}
					return 0;
				}
			});
		}
	}

	private List<ProdTrainCache> compositeProduct(List<ProdTrainCache> list) {
		List<ProdTrainCache> trains = new ArrayList<ProdTrainCache>();
		soldoutSeatTypes = SearchConstants.getInstance().getTrainSoldoutList();
		for (ProdTrainCache cache : list) {
			if (soldoutSeatTypes != null) {
				if (!cache.hasSoldout()) {
					if (ArrayUtils.contains(soldoutSeatTypes,
							cache.getSeatType())) {
						cache.setSoldout("true");
					}
				}
			}
			cache.makeSoldout();
			boolean hasPullman = ArrayUtils.contains(seat_catalog,
					cache.getSeatType());
			if (!hasPullman) {// 过滤卧铺
				trains.add(cache);
			}
		}
		Collections.sort(trains);
		return trains;
	}

	/**
	 * 根据参数获取相应的key .
	 * 
	 * @param params
	 * @return key
	 */
	public String getMemcacheKeyByParams(Map<String, Object> params) {
		String memcacheKey = "";
		// 先从缓存中区
		try {
			// memcacheKey = params.get("method").toString();
			memcacheKey = MD5.encode(params.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memcacheKey;
	}

	/**
	 * 先从缓存中回去信息.
	 * 
	 * @param params
	 * @return obj
	 */
	public Object getMemecachedInfo(String memcacheKey) {
		Object obj = null;
		// 先从缓存中区
		if (!StringUtils.isEmpty(memcacheKey)) {
			obj = MemcachedUtil.getInstance().get(
					MOBILE_TRAIN_CACHE + memcacheKey);
		}

		return obj;
	}

	private static Date getDate(String str) {
		String[] array = str.split("-");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, NumberUtils.toInt(array[0]));
		c.set(Calendar.MONTH, NumberUtils.toInt(array[1]) - 1);
		c.set(Calendar.DAY_OF_MONTH, NumberUtils.toInt(array[2]));
		return DateUtil.getDayStart(c.getTime());
	}

	public void setProdTrainCacheService(
			ProdTrainCacheService prodTrainCacheService) {
		this.prodTrainCacheService = prodTrainCacheService;
	}

	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}

	public boolean isShowBookFlag() {
		return showBookFlag;
	}

	public void setShowBookFlag(boolean showBookFlag) {
		this.showBookFlag = showBookFlag;
	}

}
