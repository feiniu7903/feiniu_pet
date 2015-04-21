package com.lvmama.clutter.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.pet.po.search.ProdTrainCache;

public class TrainUtils {

	public static Map<String, Object> getDataMap(String name, String argName,
			Map<String, Object> datas) {
		Map<String, Object> dataMap = getDatas(name, argName, datas);
		return dataMap;
	}

	public static List<Map<String, Object>> addToResultList(
			List<Map<String, Object>> resultList, String name, String argName,
			Map<String, Object> datas) {
		Map<String, Object> dataMap = getDatas(name, argName, datas);
		resultList.add(dataMap);
		return resultList;
	}

	private static Map<String, Object> getDatas(String name, String argName,
			Map<String, Object> datas) {
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		for (Entry<String, Object> entry : datas.entrySet()) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("key", entry.getKey());
			map.put("value", entry.getValue());
			listResult.add(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("argName", argName);
		map.put("datas", listResult);

		if ("trainType".equals(argName)) {
			Date currentDate = Calendar.getInstance().getTime();
			map.put("orderStartDate", DateFormatUtils.format(
					DateUtils.addDays(currentDate, 3), "yyyy-MM-dd"));
			map.put("orderEndDate", DateFormatUtils.format(
					DateUtils.addDays(currentDate, 19), "yyyy-MM-dd"));
		}

		return map;
	}

	public static Map<String, Object> getTrainDepartureTime() {
		Map<String, Object> datas = new LinkedHashMap<String, Object>();
		datas.put("", "全部");
		datas.put("0000-0600", "凌晨(0-6点)");
		datas.put("0600-1200", "上午(6-12点)");
		datas.put("1200-1300", "中午(12-13点)");
		datas.put("1300-1800", "下午(13-18点)");
		datas.put("1800-2400", "晚上(18-24点)");
		Map<String, Object> dataMap = getDataMap("发车时间", "departureTime", datas);
		return dataMap;
	}

	public static Map<String, Object> getTrainArrivalTime() {
		Map<String, Object> datas = new LinkedHashMap<String, Object>();
		datas.put("", "全部");
		datas.put("0000-0600", "凌晨(0-6点)");
		datas.put("0600-1200", "上午(6-12点)");
		datas.put("1200-1300", "中午(12-13点)");
		datas.put("1300-1800", "下午(13-18点)");
		datas.put("1800-2400", "晚上(18-24点)");
		Map<String, Object> dataMap = getDataMap("到站时间", "arrivalTime", datas);
		return dataMap;
	}

	public static Map<String, Object> getSortTypes() {
		Map<String, Object> datas = new LinkedHashMap<String, Object>();
		datas.put("", "默认排序");
		datas.put("timeDesc", "发车时间降序");
		datas.put("timeAsc", "发车时间升序");
		datas.put("timeLengthDesc", "运行时长降序");
		datas.put("timeLengthAsc", "运行时长升序");
		datas.put("priceDesc", "价格降序");
		datas.put("priceAsc", "价格升序");
		Map<String, Object> dataMap = getDataMap("排序", "sort", datas);
		return dataMap;
	}

	public static Map<String, Object> getTrainTypes() {
		Map<String, Object> datas = new LinkedHashMap<String, Object>();
		datas.put("", "全部");
		datas.put("GD", "高铁/动车");
//		datas.put("D", "动车");
//		datas.put("T", "特快");
//		datas.put("K", "普通");
//		datas.put("X", "其他");
		datas.put("OTHER", "普通");
		Map<String, Object> dataMap = getDataMap("车型选择", "trainType", datas);
		return dataMap;
	}

	public static Set<String> getDepartureStations(List<ProdTrainCache> trains) {
		Set<String> departureStations = new HashSet<String>();
		for (ProdTrainCache train : trains) {
			departureStations.add(train.getDepartureStationName());
		}
		return departureStations;
	}

	public static Set<String> getArrivalStations(List<ProdTrainCache> trains) {
		Set<String> arrivalStations = new HashSet<String>();
		for (ProdTrainCache train : trains) {
			arrivalStations.add(train.getArrivalStationName());
		}
		return arrivalStations;
	}

	public static Map<String, Object> getDepartureStationsOfCity(
			List<LineStation> lineStations, Set<String> filterSet) {
		Map<String, Object> datas = new LinkedHashMap<String, Object>();
		datas.put("", "全部");
		for (LineStation lineStation : lineStations) {
			String stationPinyin = lineStation.getStationPinyin();
			String stationName = lineStation.getStationName();
			if (filterSet.contains(stationName)) {
				if (!StringUtils.isEmpty(stationPinyin)) {
					datas.put(lineStation.getStationPinyin(),
							lineStation.getStationName());
				} else {
					datas.put(lineStation.getOldStationPinyin(),
							lineStation.getStationName());
				}
			}
		}
		Map<String, Object> dataMap = getDataMap("出发车站", "departureStation",
				datas);
		return dataMap;
	}

	public static Map<String, Object> getArrivalStationsOfCity(
			List<LineStation> lineStations, Set<String> filterSet) {
		Map<String, Object> datas = new LinkedHashMap<String, Object>();
		datas.put("", "全部");
		for (LineStation lineStation : lineStations) {
			String stationPinyin = lineStation.getStationPinyin();
			String stationName = lineStation.getStationName();
			if (filterSet.contains(stationName)) {
				if (!StringUtils.isEmpty(stationPinyin)) {
					datas.put(lineStation.getStationPinyin(),
							lineStation.getStationName());
				} else {
					datas.put(lineStation.getOldStationPinyin(),
							lineStation.getStationName());
				}
			}
		}
		Map<String, Object> dataMap = getDataMap("到达车站", "arrivalStation",
				datas);
		return dataMap;
	}

	public static void main(String[] args) {
		System.out.println(TrainUtils.getSortTypes());
		System.out.println(TrainUtils.getTrainDepartureTime());
		System.out.println(TrainUtils.getTrainArrivalTime());
		System.out.println(TrainUtils.getTrainTypes());
	}
}
