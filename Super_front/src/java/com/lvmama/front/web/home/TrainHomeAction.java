/**
 * 
 */
package com.lvmama.front.web.home;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.LineInfo;
import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.bee.po.prod.LineStationStation;
import com.lvmama.comm.bee.po.prod.LineStops;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 * 
 */
@Results({
		@Result(name = "forwardShikebiao", params = { "status", "301","headers.Location", "/train/shikebiao" }, type = "httpheader"),
		@Result(name = "forwardCheci", params = { "status", "301","headers.Location", "/train/checi" }, type = "httpheader"),
		@Result(name = "success", location = "/WEB-INF/pages/www/train.ftl", type = "freemarker"),
		@Result(name = "trainScheduleQuery", location = "/WEB-INF/pages/www/train/trainScheduleQuery.ftl", type = "freemarker"),
		@Result(name = "trainStationStation", location = "/WEB-INF/pages/www/train/trainStationStation.ftl", type = "freemarker"),
		@Result(name = "trainCheci", location = "/WEB-INF/pages/www/train/trainCheci.ftl", type = "freemarker"),
		@Result(name = "trainCheciQuery", location = "/WEB-INF/pages/www/train/trainCheciQuery.ftl", type = "freemarker"),
		@Result(name = "trainSchedule", location = "/WEB-INF/pages/www/train/trainSchedule.ftl", type = "freemarker") })
public class TrainHomeAction extends ToPlaceOnlyTemplateHomeAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7712131570097203371L;

	private String defaultDate;
	private String showWeekDay;
	private ProdTrainService prodTrainService;
	private Map<String, Object> param;
	private Map<String, Object> scheduleMap;
	private Map<String, List<LineStops>> lineStopsMap;
	private Long startStationId;
	private Long endStationId;
	private List<LineInfo> lineInfos;
	private List<LineStationStation> lineStationStations;
	private List<LineStops> lineStopsList;
	private List<LineStation> lineStations;
	private List<LineStation> lineStations2;
	private String stationPinyin;

	private LineStation lineStation;
	private LineStation lineStation2;
	private LineInfo lineInfo;
	private String category;
	private String fullName;

	@Override
	@Action("/homePage/trainAction")
	public String execute() {
		init(Constant.CHANNEL_ID.CH_TRAIN.name());
		Date date = DateUtils.addDays(new Date(), 3);
		defaultDate = DateUtil.formatDate(date, "yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int w = c.get(Calendar.DAY_OF_WEEK) - 1;
		showWeekDay = weekDay[w];
		return SUCCESS;
	}

	/**
	 * 火车时刻表
	 * 
	 * @return
	 */
	@Action("/train/trainSchedule")
	public String trainSchedule() {
		Date date = DateUtils.addDays(new Date(), 3);
		defaultDate = DateUtil.formatDate(date, "yyyy-MM-dd");
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		List<LineStation> lineStations = this.prodTrainService
				.selectLineStationAll(null);
		List<LineStation> list;
		LineStation bean;
		String temp;
		scheduleMap = new LinkedHashMap<String, Object>();
		for (int i = 0; i < letters.length(); i++) {
			list = new ArrayList<LineStation>();
			temp = letters.charAt(i) + "";
			for (int j = 0; j < lineStations.size(); j++) {
				bean = lineStations.get(j);
				if (bean.getStationPinyin() == null
						|| bean.getStationPinyin().equals("")) {
					continue;
				}
				if (temp.equalsIgnoreCase(bean.getStationPinyin().charAt(0)
						+ "")) {
					list.add(lineStations.get(j));
				}
			}
			scheduleMap.put(temp, list);
		}
		this.setSeoIndexPage("CH_TRAIN_SHIKEBIAO");
		return "trainSchedule";
	}

	/**
	 * 车站时刻表
	 * 
	 * @return
	 */
	@Action("/train/trainScheduleQuery")
	public String trainScheduleQuery() {
		this.lineStation = this.prodTrainService
				.getLineStationByStationPinyin(this.stationPinyin);
		if (lineStation != null) {
			Date date = DateUtils.addDays(new Date(), 3);
			defaultDate = DateUtil.formatDate(date, "yyyy-MM-dd");
			param = new HashMap<String, Object>();
			lineStopsMap = new HashMap<String, List<LineStops>>();
			param.put("stationId", lineStation.getStationId());
			this.lineInfos = this.prodTrainService.selectCheZhan(param);
			List<LineStops> stopslist = this.prodTrainService
					.selectCheZhanStops(param);
			List<LineStops> tempList;
			for (int i = 0; i < this.lineInfos.size(); i++) {
				tempList = new ArrayList<LineStops>();
				for (int j = 0; j < stopslist.size(); j++) {
					LineStops stops = stopslist.get(j);
					if (this.lineInfos.get(i).getLineInfoId()
							.equals(stops.getLineInfoId())) {
						tempList.add(stops);
					}
				}
				lineStopsMap.put(lineInfos.get(i).getLineInfoId().toString(),
						tempList);
			}
			// 取出经过该车站的30个站
			this.lineStations = this.prodTrainService
					.selectLineStationByChezhan(param);
		}
		this.setSeoIndexPage("CH_TRAIN_SHIKEBIAO_CHEZHAN");
		return "trainScheduleQuery";
	}

	/**
	 * 火车时刻表-站站
	 * 
	 * @return
	 */
	@Action("/train/trainStationStation")
	public String trainStationStation() {
		param = new HashMap<String, Object>();
		param.put("pinyinKey", this.stationPinyin);
		this.lineStationStations = this.prodTrainService
				.selectLineStationStationByPinyinKey(param);
		String[] zhanzhan = stationPinyin.split("-");
		this.lineStation = this.prodTrainService.getLineStationByStationPinyin(zhanzhan[0]);
		this.lineStation2 = this.prodTrainService.getLineStationByStationPinyin(zhanzhan[1]);
		Date date = DateUtils.addDays(new Date(), 3);
		defaultDate = DateUtil.formatDate(date, "yyyy-MM-dd");
		lineStopsMap = new HashMap<String, List<LineStops>>();
		List<LineStops> stopslist = this.prodTrainService
				.selectZhanZhanStops(param);
		List<LineStops> tempList;
		for (int i = 0; i < this.lineStationStations.size(); i++) {
			tempList = new ArrayList<LineStops>();
			for (int j = 0; j < stopslist.size(); j++) {
				LineStops stops = stopslist.get(j);
				if (this.lineStationStations.get(i).getLineInfoId()
						.equals(stops.getLineInfoId())) {
					tempList.add(stops);
				}
			}
			lineStopsMap.put(lineStationStations.get(i).getLineInfoId()
					.toString(), tempList);
		}
		// 取出经过该站站的30个站
		if(lineStation != null){
			param.put("stationId", lineStation.getStationId());
			this.lineStations = this.prodTrainService.selectLineStationByChezhan(param);
		}
		if(lineStation2 != null){
			param.put("stationId", lineStation2.getStationId());
			this.lineStations2 = this.prodTrainService.selectLineStationByChezhan(param);
		}
		this.setSeoIndexPage("CH_TRAIN_SHIKEBIAO_ZHANZHAN");
		return "trainStationStation";
	}

	/**
	 * 所有车次
	 * 
	 * @return
	 */
	@Action("/train/trainCheci")
	public String trainCheci() {
		Date date = DateUtils.addDays(new Date(), 3);
		defaultDate = DateUtil.formatDate(date, "yyyy-MM-dd");
		if (this.category == null) {
			this.lineInfos = this.prodTrainService.selectAllLineInfo(null);
		} else {
			param = new HashMap<String, Object>();
			if (this.category.equals("gaotie"))
				param.put("category",
						Constant.TRAIN_CATALOG.CATALOG_101.getValue());
			else if (this.category.equals("dongche"))
				param.put("category",
						Constant.TRAIN_CATALOG.CATALOG_103.getValue());
			else if (this.category.equals("tekuai"))
				param.put("category",
						Constant.TRAIN_CATALOG.CATALOG_104.getValue());
			else if (this.category.equals("kuaiche"))
				param.put("category",
						Constant.TRAIN_CATALOG.CATALOG_106.getValue());
			else if (this.category.equals("qita")) {
				param.put(
						"category",
						Constant.TRAIN_CATALOG.CATALOG_102.getValue() + ","
								+ Constant.TRAIN_CATALOG.CATALOG_105.getValue()
								+ ","
								+ Constant.TRAIN_CATALOG.CATALOG_107.getValue()
								+ ","
								+ Constant.TRAIN_CATALOG.CATALOG_108.getValue()
								+ ","
								+ Constant.TRAIN_CATALOG.CATALOG_109.getValue()
								+ ","
								+ Constant.TRAIN_CATALOG.CATALOG_110.getValue());
			}
			this.lineInfos = this.prodTrainService.selectAllLineInfo(param);
		}
		this.setSeoIndexPage("CH_TRAIN_CHECI");
		return "trainCheci";
	}

	/**
	 * 车次信息查询
	 * 
	 * @return
	 */
	@Action("/train/trainCheciQuery")
	public String trainCheciQuery() {
		this.lineInfo = this.prodTrainService
				.selectLineInfoByFullName(this.fullName);
		Date date = DateUtils.addDays(new Date(), 3);
		defaultDate = DateUtil.formatDate(date, "yyyy-MM-dd");
		param = new HashMap<String, Object>();
		param.put("fullName", this.fullName);
		this.lineStopsList = this.prodTrainService.selectLineStopsCheci(param);
		this.setSeoIndexPage("CH_TRAIN_CHECI");
		return "trainCheciQuery";
	}

	public void setSeoIndexPage(String code){
		super.comSeoIndexPage = super.getSeoIndexPageService().getSeoIndexPageByPageCode(code);
		super.comSeoIndexPage.setSeoKeyword(comSeoIndexPage.getSeoKeyword().replace("{fromDest}", getFromDest()).replace("{toDest}", getToDest()));
		super.comSeoIndexPage.setSeoDescription(comSeoIndexPage.getSeoDescription().replace("{fromDest}", getFromDest()).replace("{toDest}", getToDest()));
		super.comSeoIndexPage.setSeoTitle(comSeoIndexPage.getSeoTitle().replace("{fromDest}", getFromDest()).replace("{toDest}", getToDest()));
	}
	
	public String getDefaultDate() {
		return defaultDate;
	}

	public String getShowWeekDay() {
		return showWeekDay;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public Map<String, Object> getScheduleMap() {
		return scheduleMap;
	}

	public void setScheduleMap(Map<String, Object> scheduleMap) {
		this.scheduleMap = scheduleMap;
	}

	public ProdTrainService getProdTrainService() {
		return prodTrainService;
	}

	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}

	public Long getStartStationId() {
		return startStationId;
	}

	public void setStartStationId(Long startStationId) {
		this.startStationId = startStationId;
	}

	public Long getEndStationId() {
		return endStationId;
	}

	public void setEndStationId(Long endStationId) {
		this.endStationId = endStationId;
	}

	public void setDefaultDate(String defaultDate) {
		this.defaultDate = defaultDate;
	}

	public void setShowWeekDay(String showWeekDay) {
		this.showWeekDay = showWeekDay;
	}

	public List<LineInfo> getLineInfos() {
		return lineInfos;
	}

	public void setLineInfos(List<LineInfo> lineInfos) {
		this.lineInfos = lineInfos;
	}

	public Map<String, List<LineStops>> getLineStopsMap() {
		return lineStopsMap;
	}

	public void setLineStopsMap(Map<String, List<LineStops>> lineStopsMap) {
		this.lineStopsMap = lineStopsMap;
	}

	public String getStationPinyin() {
		return stationPinyin;
	}

	public void setStationPinyin(String stationPinyin) {
		this.stationPinyin = stationPinyin;
	}

	public LineStation getLineStation() {
		return lineStation;
	}

	public void setLineStation(LineStation lineStation) {
		this.lineStation = lineStation;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LineInfo getLineInfo() {
		return lineInfo;
	}

	public void setLineInfo(LineInfo lineInfo) {
		this.lineInfo = lineInfo;
	}

	public List<LineStationStation> getLineStationStations() {
		return lineStationStations;
	}

	public void setLineStationStations(
			List<LineStationStation> lineStationStations) {
		this.lineStationStations = lineStationStations;
	}

	public List<LineStops> getLineStopsList() {
		return lineStopsList;
	}

	public void setLineStopsList(List<LineStops> lineStopsList) {
		this.lineStopsList = lineStopsList;
	}

	public List<LineStation> getLineStations() {
		return lineStations;
	}

	public void setLineStations(List<LineStation> lineStations) {
		this.lineStations = lineStations;
	}

	public List<LineStation> getLineStations2() {
		return lineStations2;
	}

	public void setLineStations2(List<LineStation> lineStations2) {
		this.lineStations2 = lineStations2;
	}

	public LineStation getLineStation2() {
		return lineStation2;
	}

	public void setLineStation2(LineStation lineStation2) {
		this.lineStation2 = lineStation2;
	}
	
	public String getFromDest(){
		if(this.lineStation==null)
			return "";
		return lineStation.getStationName();
	}
	
	public String getToDest(){
		if(this.lineStation2 == null)
			return "";
		return lineStation2.getStationName();
	}
	
	private static final String[] weekDay = { "星期日", "星期一", "星期二", "星期三",
			"星期四", "星期五", "星期六" };
}
