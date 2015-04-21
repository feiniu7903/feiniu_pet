package com.lvmama.clutter.web.hotel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.model.MobileHotelGeo;
import com.lvmama.clutter.model.MobilePlaceHotel;
import com.lvmama.clutter.model.MobileTimePrice;
import com.lvmama.clutter.service.IClientHotelService;
import com.lvmama.clutter.service.IClientSearchService;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.DateUtil;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.utils.StringUtil;


/**
 * WAP酒店ACTION
 * @author jswangqian
 *
 */
@Results({
	@Result(name="hotel_search",location="/WEB-INF/pages/hotel/hotel_search.html",type="freemarker"),
	@Result(name="hotel_cities",location="/WEB-INF/pages/hotel/hotel_cities.html",type="freemarker"),
	@Result(name="hotel_keywords",location="/WEB-INF/pages/hotel/hotel_keywords.html",type="freemarker"),
	@Result(name="get_date_picker",location="/WEB-INF/pages/hotel/hotel_datepicker.html",type="freemarker"),
	@Result(name="hotel_search_list",location="/WEB-INF/pages/hotel/hotel_list.html",type="freemarker"),
	@Result(name="hotel_search_list_ajax",location="/WEB-INF/pages/hotel/hotel_list_ajax.html",type="freemarker"),
	@Result(name="hotel_detail",location="/WEB-INF/pages/hotel/hotel_detail.html",type="freemarker"),
	@Result(name="spotticket_ajax",location="/WEB-INF/pages/hotel/hotel_spotticket_ajax.html",type="freemarker"),
	@Result(name="hotel_circum_list",location="/WEB-INF/pages/hotel/hotel_circum_list.html",type="freemarker"),
	@Result(name="hotel_circum_list_ajax",location="/WEB-INF/pages/hotel/hotel_circum_list_ajax.html",type="freemarker"),
	@Result(name="hotel_map",location="/WEB-INF/pages/hotel/hotel_map.html",type="freemarker"),
	@Result(name = "error", location = "/WEB-INF/error.html", type = "freemarker")
})
@Namespace("/mobile/hotel")
public class HotelWapAction extends BaseAction{
	private static final long serialVersionUID = -6164959031257208915L;
	/**
	 * 酒店接口服务
	 */
	protected IClientHotelService clientHotelService;
	
	/**
	 * 搜索服务 
	 */
	IClientSearchService mobileSearchService;
	
	private String cityId;//城市ID
	
	private String cityName;//城市名称
	
	private String arrivalDate;//酒店入住日期
	
	private String arrivalZhDate;//酒店入住星期
	
	private String departureDate;//酒店离开日期
	
	private String departureZhDate;//酒店离开星期
	
	private int clickType;//点击时间类型(1入住/0离开)
	
	//搜索页hotel_search  详情页hotel_detail   订单填写页 hotel_order
	private String clickPageType;//是那个页面点击的时间表
	
	private String lowRate;//最小价格
	
	private String highRate;//最大价格
	
	private String queryText;//关键字/地标/商圈
	
	private String locationId;//关键字/地标/商圈编码
	
	private String starRate;//星级类型/经济/豪华...
	
	private String brandId;//品牌ID
	
	private String pageIndex;//分页
	
	private String pageSize;//分页大小
	
	private String sort;//排序
	
	private String latitude;//纬度
	
	private String longitude;//经度
	
	private String radius;//半径
	
	private boolean ajax;// 是否ajax 查询
	
	private String hotelId;//酒店ID
	
	private int windage;//周边景点范围单位M
	
	private String searchPriceValue;//综合筛选--价格VALUE
	
	private String searchBrandValue;//综合筛选--品牌VALUE
	
	private String searchAreaValue;//综合筛选--区域VALUE
	
	private String hotelName;//酒店名称
	
	private String hotelTypeName;//酒店类型名称
	
	
	
	/**
	 * 酒店列表搜索
	 * @return
	 */
	@Action("hotelSearchList")
	public String hotelSearchList(){
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			//参数验证
			boolean bool= verifyParams();
			if(bool==false){
				return "hotel_search_list";
			}
			//初始化搜索参数
			initParams(param);
			Map<String, Object> resultMap=clientHotelService.search(param);//获取酒店列表数据==========================
			if(resultMap!=null){
				List<MobilePlaceHotel> hotelList = (List<MobilePlaceHotel>) resultMap
						.get("hotels");//酒店列表========================
				getRequest().setAttribute("hotelList", hotelList);
				getRequest().setAttribute("hasNext", resultMap.get("hasNext"));
			}
			if(ajax) {
				return "hotel_search_list_ajax";
			}
			//查询筛选条件
			Map<String, Object> resultHotelSearchMap=new HashMap<String, Object>();//筛选条件返回数据
			
				resultHotelSearchMap=clientHotelService.hotelSearchFilterData(param);//刷选条件==========================
				
				List<Map<String, Object>> markList=getMarkListByCityId(param.get("cityId").toString());
				List<Map<String, Object>> brandList = (List<Map<String, Object>>) resultHotelSearchMap.get("brandList");//品牌列表
				List<Map<String, Object>> priceList = (List<Map<String, Object>>) resultHotelSearchMap.get("priceList");//价格列表
				List<Map<String, Object>> starList = (List<Map<String, Object>>) resultHotelSearchMap.get("starList");//星级列表
				List<Map<String, Object>> sortList = (List<Map<String, Object>>) resultHotelSearchMap.get("seqs");//排序列表
				
				getRequest().setAttribute("markList", markList);
				getRequest().setAttribute("brandList", brandList);
				getRequest().setAttribute("priceList", priceList);
				getRequest().setAttribute("starList", starList);
				getRequest().setAttribute("sortList", sortList);
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "hotel_search_list";
		}
		return "hotel_search_list";
	}
	/**
	 * 酒店周边酒店
	 * @return
	 * @throws Exception 
	 */
	@Action("hotelCircumLlist")
	public String hotelCircumLlist() throws Exception{
		//根据cityName查找CITYID
		String hanzi2 = new String(
				cityName.getBytes("ISO-8859-1"), "UTF-8");
		cityName = java.net.URLDecoder.decode(hanzi2, "UTF-8");
		if (StringUtils.isEmpty(cityId)) {
			cityId=getCityIdByCityName(cityName);//城市ID
		}
		
		//参数验证
		boolean bool= verifyParams();
		if(bool==false){
			return "hotel_circum_list";
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		//初始化搜索参数
		initParams(param);
		Map<String, Object> resultMap=clientHotelService.search(param);//获取酒店列表数据==========================
		if(resultMap!=null){
			List<MobilePlaceHotel> hotelList = (List<MobilePlaceHotel>) resultMap
					.get("hotels");//酒店列表========================
			getRequest().setAttribute("hotelList", hotelList);
			getRequest().setAttribute("hasNext", resultMap.get("hasNext"));
		}
		
		//ajax搜索
		if(ajax){
			return "hotel_circum_list_ajax";
		}
		return "hotel_circum_list";
	}
	/**
	 * 根据城市名称查询城市ID
	 * @param cityName
	 * @return
	 */
	public String getCityIdByCityName(String cityName){
		Map<String, Object> param=new HashMap<String, Object>();
		
		try {
			//酒店城市列表
			Map<String, Object> resultMap=clientHotelService.getCities(param);
			if (null != resultMap && null != resultMap.get("cities")) {
				List<Map<String, Object>> blockMapList = (List<Map<String, Object>>) resultMap
						.get("cities");
				if (null != blockMapList && blockMapList.size() > 0) {
					for(Map<String, Object> m : blockMapList){
						if(m.get("name").equals(cityName)){
							return m.get("id").toString();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 酒店详情
	 * @return
	 */
	@Action("hotelDetail")
	public String hotel_detail(){
		Map<String, Object> param = new HashMap<String, Object>();
		//如果是客户端m.lvmama.com/clutter/hotel/50101002
		//入住日期和离店日期为今天和明天
		if(StringUtils.isEmpty(arrivalDate) || StringUtils.isEmpty(departureDate)){
			Date now=new Date();
			Date tomorrow=DateUtil.getDateAddDay(now,1);//
			arrivalDate=getDateToString(now);
			departureDate=getDateToString(tomorrow);
		}
		//参数验证
		Date date1=getStringToDate(arrivalDate);//入住星期
		Date date2=getStringToDate(departureDate);//离开日期
		
		boolean bool=date2.after(date1);
		if(bool==true){
			arrivalZhDate=this.intZhDate(date1);//入住星期
			departureZhDate=this.intZhDate(date2);//离开星期
		}else {
			if(clickType==1){
				arrivalZhDate=this.intZhDate(date1);//入住星期
				
				Date departureDatNew=DateUtil.getDateAddDay(date1,1);//离开日期
				departureDate=getDateToString(departureDatNew);
				departureZhDate=this.intZhDate(departureDatNew);//离开星期
			}else{
				departureZhDate=this.intZhDate(date2);//离开星期
				
				Date arrivalZhDateNew=DateUtil.getDateAddDay(date2,-1);//入住日期
				arrivalDate=getDateToString(arrivalZhDateNew);
				arrivalZhDate=this.intZhDate(arrivalZhDateNew);//入住星期
			}
		}
		if(hotelId==null || hotelId==""){
			return "error";
		}
		
		
//		if(cityId==null || cityId==""){
//			return "error";
//		}
		//数据组装==============================
		try {
			param.put("arrivalDate", arrivalDate);
			param.put("departureDate", departureDate);
			param.put("hotelId", hotelId);
			Map<String, Object> resultMapDetael=clientHotelService.getHotel(param);//获取酒店详情
			Map<String, Object> resultMapRooms=clientHotelService.getHotelRooms(param);//获取酒店房型
			Map<String, Object> resultMapComments=clientHotelService.getComments(param);//获取酒店点评列表
			
			if(resultMapDetael!=null){
				JSONObject hotelDetael=(JSONObject) resultMapDetael.get("hotel");
				//如果酒店详情不为空根据酒店经纬度查询周边景点
				windage=100000;
				latitude=(String) hotelDetael.get("latitude");
				longitude=(String) hotelDetael.get("longitude");
				pageIndex="1";
				this.hotelDetailStnearby();//景点详情周边景点
				
				getRequest().setAttribute("hotelDetael", hotelDetael);
			}
			if(resultMapRooms!=null){
				JSONArray hotelRooms=(JSONArray) resultMapRooms.get("hotelRooms");
				getRequest().setAttribute("hotelRooms", hotelRooms);
			}
			if(resultMapComments!=null){
				JSONArray hotelComments=(JSONArray) resultMapComments.get("hotelComments");
				getRequest().setAttribute("hotelComments", hotelComments);//点评列表
				getRequest().setAttribute("totalComment", resultMapComments.get("totalComment"));//评论数
				getRequest().setAttribute("hasNext", resultMapComments.get("hasNext"));//是否有下页
				getRequest().setAttribute("badCommentRate",resultMapComments.get("badCommentRate"));//差评百分比
				getRequest().setAttribute("goodCommentRate",resultMapComments.get("goodCommentRate"));//好评百分比
			}
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "hotel_detail";
		}
		return "hotel_detail";
	}
	/**
	 * 酒店地图
	 * @return
	 */
	@Action("hotelMap")
	public String position(){
		getRequest().setAttribute("lat", getRequest().getParameter("lat"));
		getRequest().setAttribute("lon", getRequest().getParameter("lon"));
		return "hotel_map";
	}
//	/**
//	 * 酒店名称和类型存入SESSION
//	 *防止登录乱码
//	 * @return
//	 */
//	@Action("saveHotelDetailName")
//	public void saveHotelDetailName(){
//		if(hotelName!=null && hotelName!=""){
//			this.getRequest().getSession().setAttribute("hotelNameSession", hotelName);
//		}
//		if(hotelTypeName!=null && hotelTypeName!=""){
//			this.getRequest().getSession().setAttribute("hotelTypeNameSession", hotelTypeName);
//		}
//		JSONObject jsonObj = new JSONObject();
//		jsonObj.put("hotelName", hotelName);
//		jsonObj.put("hotelTypeName", hotelTypeName);
//		
//		this.sendAjaxResult(jsonObj.toString());
//		
//	}
	/**
	 * 查询参数验证
	 * @return
	 */
	public boolean verifyParams(){
		Date date=new Date();
		String nowString=getDateToString(date);
		Date nowDate=getStringToDate(nowString);//当前时间yy-mm-dd
		Date date1=getStringToDate(arrivalDate);//入住星期
		Date date2=getStringToDate(departureDate);//离开日期
		if(cityId==null || "".equals(cityId)){
			return false;
		}else if(date1.after(date2)){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 酒店查询页
	 * @return
	 */
	@Action("hotel")
	public String hotSpringIndex(){
		//只有从选择酒店日期进来才计算入住和离开日期
		if(arrivalDate!=null && !"".equals(arrivalDate) && departureDate!=null && !"".equals(departureDate)){
			Date date1=getStringToDate(arrivalDate);//入住日期
			Date date2=getStringToDate(departureDate);//离开日期
			boolean bool=date2.after(date1);
			if(bool==true){
				arrivalZhDate=this.intZhDate(date1);//入住星期
				departureZhDate=this.intZhDate(date2);//离开星期
			}else {
				if(clickType==1){
					arrivalZhDate=this.intZhDate(date1);//入住星期
					
					Date departureDatNew=DateUtil.getDateAddDay(date1,1);//离开日期
					departureDate=getDateToString(departureDatNew);
					departureZhDate=this.intZhDate(departureDatNew);//离开星期
				}else{
					departureZhDate=this.intZhDate(date2);//离开星期
					
					Date arrivalZhDateNew=DateUtil.getDateAddDay(date2,-1);//入住日期
					arrivalDate=getDateToString(arrivalZhDateNew);
					arrivalZhDate=this.intZhDate(arrivalZhDateNew);//入住星期
				}
				
			}
		}
		return "hotel_search";
	}
	/**
	 * 查询城市列表
	 * @return
	 */
	@Action("getCities")
	public String getCities(){
		Map<String, Object> param=new HashMap<String, Object>();
		
		try {
			//酒店城市列表
			Map<String, Object> resultMap=clientHotelService.getCities(param);
			if (null != resultMap && null != resultMap.get("cities")) {
				List<Map<String, Object>> blockMapList = (List<Map<String, Object>>) resultMap
						.get("cities");
				if (null != blockMapList && blockMapList.size() > 0) {
					getRequest().setAttribute("cityList", blockMapList);
					// 热门城市搜索用到 .
					getRequest().setAttribute("cityListJson",
							JSONArray.fromObject(blockMapList));
					
					Set<String> slist = getChars((List<Map<String, Object>>) blockMapList);
					getRequest().setAttribute("charList", slist);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}
		return "hotel_cities";
	}
	/**
	 * 关键字/地标/商圈
	 * @return
	 */
	@Action("getLandMarks")
	public String getLandMarks(){
		Map<String, Object> param=new HashMap<String, Object>();
		//根据CITYID查询地表商圈
		try {
			param.put("cityCode", cityId);
			Map<String, Object> resultMap=clientHotelService.getLandMarks(param);
			List<Map<String, Object>> landMarkMapList=null;
			List<MobileHotelGeo> landMarkMapListAll=new ArrayList<MobileHotelGeo>();
			if(resultMap!=null && resultMap.size()>0){
				landMarkMapList = (List<Map<String, Object>>) resultMap.get("datas");
			}
			if(landMarkMapList!=null && landMarkMapList.size()>0){
				for(Map<String, Object> m : landMarkMapList){
					List<MobileHotelGeo> geoList=(List<MobileHotelGeo>) m.get("datas");
					for(MobileHotelGeo o : geoList){
						landMarkMapListAll.add(o);
					}
				}
			}
			getRequest().setAttribute("landMarkMapList", landMarkMapList);
			getRequest().setAttribute("landMarkMapListAll", JSONArray.fromObject(landMarkMapListAll));//搜索框补全用到
			
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}
		return "hotel_keywords";
	}
	/**
	 * 酒店时间列表
	 * @return
	 */
	@Action("getDatePicker")
	public String getDatePicker(){
		try {
			
			// 返回的时间价格表  
			List<List<Map<String,Object>>>  resultTimePicker = new ArrayList<List<Map<String,Object>>>();
			
				Date date = new Date();
				//Date date=DateUtil.getDateAddDay(new Date(),-1);//昨天
				//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//Date date = sdf1.parse("2014-01-30 00:05:00");
				// 第一个月 的时间表
				resultTimePicker.add(this.zeroAndSixHour(this.initTimeData(init2Month(date),arrivalDate,departureDate, clickType), arrivalDate, departureDate, clickType));
				// 第二个月 
				resultTimePicker.add(this.initTimeData(init2Month(DateUtil.getMontOfAdd(date,1)),arrivalDate,departureDate, clickType));
				// 第三个月 
				resultTimePicker.add(this.initTimeData(init2Month(DateUtil.getMontOfAdd(date,2)),arrivalDate,departureDate, clickType));
				// 第四个月 
				resultTimePicker.add(this.initTimeData(init2Month(DateUtil.getMontOfAdd(date,3)),arrivalDate,departureDate, clickType));
				// 第五个月 
				resultTimePicker.add(this.initTimeData(init2Month(DateUtil.getMontOfAdd(date,4)),arrivalDate,departureDate, clickType));
				// 第六个月 
				resultTimePicker.add(this.initTimeData(init2Month(DateUtil.getMontOfAdd(date,5)),arrivalDate,departureDate, clickType));
				
				Date now=new Date();
				//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//Date now = sdf1.parse("2014-01-22 01:50:00");
				int hours=now.getHours();
				if(hours>=0 && hours<=5){
					Date yesterday=DateUtil.getDateAddDay(now,-1);//昨天
					int jintian=now.getDate();
					int zuotianDate=yesterday.getDate();
					getRequest().setAttribute("zeroSixText", jintian+"日凌晨0点-6点入住，请选择"+zuotianDate+"日");
				}
			getRequest().setAttribute("resultTimePicker", resultTimePicker);
		}catch(Exception e){
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			
		}
		return "get_date_picker";
	}
	/**
	 * 默认酒店入住/离开时间
	 * @return
	 * @throws ParseException 
	 */
	@Action("getDefaultDate")
	public String getDefaultDate() throws ParseException{
		//给酒店默认入住日期和离开日期-- 今天和明天
		//如果当前时间是0-6则显示昨天今天
		Date now=new Date();
		
		//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Date now = sdf1.parse("2014-01-22 01:50:00");
		int hours=now.getHours();
		//int minutes=now.getMinutes();
		//int senonds=now.getSeconds();

		String arrivalDate="";
		String arrivalZhDate="";
		
		String departureDate="";
		String departureZhDate="";
		
		if(hours>=0 && hours<=5){
			Date date1=DateUtil.getDateAddDay(now,-1);//昨天
			arrivalDate=getDateToString(date1);
			arrivalZhDate=this.intZhDate(date1);
			
			Date date2=DateUtil.getDateAddDay(now,0);//今天
			departureDate=getDateToString(date2);
			departureZhDate=this.intZhDate(date2);
			
		}else{
			Date date1=DateUtil.getDateAddDay(now,0);//今天
			arrivalDate=getDateToString(date1);
			arrivalZhDate=this.intZhDate(date1);
			
			Date date2=DateUtil.getDateAddDay(now,1);//明天
			departureDate=getDateToString(date2);
			departureZhDate=this.intZhDate(date2);
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("arrivalDate", arrivalDate);
		jsonObj.put("arrivalZhDate", arrivalZhDate);
		jsonObj.put("departureDate", departureDate);
		jsonObj.put("departureZhDate", departureZhDate);
		this.sendAjaxResult(jsonObj.toString());
		return null;
	}
	/**
	 * 日期转换String
	 * @param dateString yyyy-MM-dd
	 * @param days  int
	 * @return
	 */
	public String getDateToString(Date date){
		String newDateString=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Date date1= dateFormat.parse(dateString);
		
		//Date date2=DateUtil.getDateAddDay(date,days);
		newDateString=dateFormat.format(date);
		return newDateString;
	}
	/**
	 * String转换日期
	 * @param dateString yyyy-MM-dd
	 * @param days  int
	 * @return
	 */
	public Date getStringToDate(String dateString){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date=null;
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}
		return date;
	}
	public List<Map<String,Object>> zeroAndSixHour(List<Map<String,Object>> listMap,String arrivalDate,String departureDate,int clickType) throws ParseException{
		
		Date now=new Date();
		//SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//Date now = sdf1.parse("2014-01-22 01:50:00");
		int hours=now.getHours();
		Date yesterday=DateUtil.getDateAddDay(now,-1);//昨天
		String yesterdayString=getDateToString(yesterday);

		String nowString=getDateToString(now);
		if(clickType==1){
			for(int i = 0;i<listMap.size();i++) {
				Map<String,Object> m = listMap.get(i);
				if(!"".equals(m.get("date").toString())){
					Date d=getStringToDate(m.get("date").toString());
					if(d.after(now) || m.get("date").toString().equals(nowString)) {
						// 数据复制 
						m.put("dayStock", "-2");
					}
					if(m.get("date").toString().equals(arrivalDate)){
						m.put("isSellable", "入住");
					}
				}
			}
			if(hours>=0 && hours<=5){
				for(int i = 0;i<listMap.size();i++){
					Map<String,Object> m = listMap.get(i);
					if(!"".equals(m.get("date").toString())){
						Date d=getStringToDate(m.get("date").toString());
						if(m.get("date").toString().equals(yesterdayString)){
							m.put("zHweek", "昨天");
							m.put("dayStock", "-2");
						}
					}
				}
			}
		}else{
			Date arrival=getStringToDate(arrivalDate);
			for(int i = 0;i<listMap.size();i++) {
				Map<String,Object> m = listMap.get(i);
				if(!"".equals(m.get("date").toString())){
					Date d=getStringToDate(m.get("date").toString());
					if(d.after(now)) {
				    	// 数据复制 
						m.put("dayStock","-2");
				    }
					if(m.get("date").toString().equals(departureDate)){
						m.put("isSellable", "离店");
					}
				}    
			}
			if(hours>=0 && hours<=5){
				for(int i = 0;i<listMap.size();i++){
					Map<String,Object> m = listMap.get(i);
					if(!"".equals(m.get("date").toString())){
						Date d=getStringToDate(m.get("date").toString());
						if(d.after(now) || m.get("date").toString().equals(nowString)) {
					    	// 数据复制 
							m.put("dayStock","-2");
					    }
						if(m.get("date").toString().equals(departureDate)){
							m.put("isSellable", "离店");
						}
					}
				}
			}
		}
		return listMap;
	}
	/**
	 * 时间 格式化 .
	 * @param listMap
	 * @param timePrice
	 */
	public List<Map<String,Object>> initTimeData(List<Map<String,Object>> listMap,String arrivalDate,String departureDate,int clickType){
		Date now=new Date();
		String nowString=getDateToString(now);
		if(clickType==1){
			for(int i = 0;i<listMap.size();i++) {
				Map<String,Object> m = listMap.get(i);
				if(!"".equals(m.get("date").toString())){
					Date d=getStringToDate(m.get("date").toString());
					if(d.after(now) || m.get("date").toString().equals(nowString)) {
						// 数据复制 
						m.put("dayStock", "-2");
					}
					if(m.get("date").toString().equals(arrivalDate)){
						m.put("isSellable", "入住");
					}
				}
			}
		}else{
			Date arrival=getStringToDate(arrivalDate);
			for(int i = 0;i<listMap.size();i++) {
				Map<String,Object> m = listMap.get(i);
				if(!"".equals(m.get("date").toString())){
					Date d=getStringToDate(m.get("date").toString());
					if(d.after(now)) {
				    	// 数据复制 
						m.put("dayStock","-2");
				    }
					if(m.get("date").toString().equals(departureDate)){
						m.put("isSellable", "离店");
					}
				}    
			}
		}
		return listMap;
	}
	/**
	 * 根据日期获取当年月份 ，获取最近两个月的数据 . 
	 * @return
	 */
	public List<Map<String,Object>> init2Month(Date date) {
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		int maxDay = DateUtil.getMaxDayOfMonth(date); // 月的最后一天 
		int year = Integer.valueOf(com.lvmama.comm.utils.DateUtil.getFormatYear(date)); // 年 
		int month = com.lvmama.comm.utils.DateUtil.getMonth(date); // 月
		if(month == 0) {
			month = 12;
		}
		
		for(int i = 1; i <= maxDay;i++) {
			Date d = DateUtil.getDayOfMonth(date,i);
			int day =  com.lvmama.comm.utils.DateUtil.getDay(d); // 日
			int week = getDay(d); // 星期-数字
			listMap.add(initDefaultDate(DateUtil.dateFormat(d, "yyyy-MM-dd"),year,month,day,week));
		}
		// 前加转换日期格式.
		listMap = initWeekDateFormat(listMap,1,year+"",month);
		// 后加转换日期格式.
		listMap = initWeekDateFormat(listMap,2,year+"",month);
		
		return listMap;
	}
	/**
	 * 日期格式：日 一 二 三 四 五 六
	 * @param listMap
	 */
	public List<Map<String,Object>> initWeekDateFormat(List<Map<String,Object>> listMap,int type,String year,int month) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < listMap.size();i++) {
			if(type == 1) {
				if(i == 0) {
					Map<String,Object> m = listMap.get(i);
					int w = Integer.parseInt(m.get("week").toString());
					if(w != 7) {
						List<Map<String,Object>> t = initPriceDate(listMap,year,month,w,0);
						list.addAll(t);
						list.addAll(listMap);
					}else {
						list = listMap;
					}
				}
			} else {
				if(i == listMap.size()-1) {
					Map<String,Object> m = listMap.get(i);
					int w = Integer.parseInt(m.get("week").toString());
					if(w != 6) {
						if(w > 6) {
							w = 7-1;
						} else {
							w = 6-w;
						}
						List<Map<String,Object>> t = initPriceDate(listMap,year,month,w,0);
						list.addAll(listMap);
						list.addAll(t);
					}else {
						list = listMap;
					}
				}
			}
		}
		return list;
	}
	// 初始化时间 
	public List<Map<String,Object>> initPriceDate(List<Map<String,Object>> list, String year,int t_m ,int t_day,int week){
		List<Map<String,Object>> t_list = new ArrayList<Map<String,Object>>();
		for(int j = 0; j < t_day;j++) {
			Map<String,Object> m  = new HashMap<String,Object> ();
			m.put("date", "");
			m.put("day", "");
			m.put("dayStock", "");
			m.put("isSellable", "");
			m.put("year", year); // 年 
			m.put("month", t_m); // 月份 
			m.put("week", 0); // 星期 数字
			m.put("zHweek", "");// 星期 中文格式
			m.put("istoday", false); // 是否当前天 
			
			t_list.add(m);
		}
		
		return t_list;
	}
	/**
	 * 初始化默认日期 . 
	 * @param year
	 * @param month
	 * @param day
	 * @param week
	 * @return
	 */
	public Map<String,Object> initDefaultDate(String strDate,int year,int month,int day,int week) {
		Map<String,Object> m  = new HashMap<String,Object> ();
		m.put("date", strDate);
		m.put("day", day+"");
		m.put("dayStock", "");
		m.put("isSellable", "");
		m.put("year", year); // 年 
		m.put("month", month); // 月份 
		m.put("week", week); // 星期 数字
		m.put("zHweek", intZhDate(DateUtil.parseDate(strDate,"yyyy-MM-dd")));// 星期 中文格式
		m.put("istoday", false); // 是否当前天 
		return m;
	}
	/**
	 * 更加日期获取星期 
	 * @param date
	 * @return
	 */
    public String intZhDate(Date date) {
    	Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		if(org.apache.commons.lang3.time.DateUtils.isSameDay(new Date(), date)){
			return "今天";
		} else {
			c.add(Calendar.DATE, 1);
			if  (org.apache.commons.lang3.time.DateUtils.isSameDay(c.getTime(), date)){
				return "明天";
			}  else {
				c.add(Calendar.DATE, 1);
				if  (org.apache.commons.lang3.time.DateUtils.isSameDay(c.getTime(), date)){
					return "后天";
				}  else {
					c.add(Calendar.DATE, -3);
					if  (org.apache.commons.lang3.time.DateUtils.isSameDay(c.getTime(), date)){
						return "昨天";
					}else{
						return com.lvmama.comm.utils.DateUtil.getZHDay(date);
					}
				}
			}
		}
    }
    public static int getDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			return 1;
		case Calendar.TUESDAY:
			return 2;
		case Calendar.WEDNESDAY:
			return 3;
		case Calendar.THURSDAY:
			return 4;
		case Calendar.FRIDAY:
			return 5;
		case Calendar.SATURDAY:
			return 6;
		case Calendar.SUNDAY:
			return 7;
		default:
			return 0;
		}
	}
	
	/**
	 * 获取pinyin首字母
	 * 
	 * @param mList
	 * @return
	 */
	public Set<String> getChars(List<Map<String, Object>> mList) {
		Map<String, Object> t_m = new HashMap<String, Object>();
		for (Map<String, Object> m : mList) {
			if (null != m && null != m.get("pinyin")) {
				String str = m.get("pinyin").toString().substring(0, 1);
				t_m.put(str, str);
			}
		}
		return new TreeSet(t_m.keySet());
	}
	/**
	 * 初始化参数
	 * @param param
	 * @throws UnsupportedEncodingException 
	 */
	public void initParams(Map<String, Object> param) throws UnsupportedEncodingException{
		param.put("cityId", cityId);
		param.put("arrivalDate",arrivalDate );
		param.put("departureDate", departureDate);
		if(!StringUtils.isEmpty(lowRate)){
			param.put("lowRate", lowRate); // 最大价格
		}
		if(!StringUtils.isEmpty(highRate)){
			param.put("highRate", highRate); // 最小价格
		}
		if (!StringUtils.isEmpty(queryText)) {
			queryText = URLDecoder.decode(queryText, "UTF-8");
			param.put("queryText", queryText); // 关键字
		}
		if(!StringUtils.isEmpty(locationId)){
			param.put("locationId", locationId); // 关键字地标商圈编码
		}
		if(!StringUtils.isEmpty(starRate)){
			param.put("starRate", starRate); // 星级类型
		}
		if(!StringUtils.isEmpty(brandId)){
			param.put("brandId", brandId); // 品牌ID
		}
		if(!StringUtils.isEmpty(sort)){
			param.put("sort",sort );//排序
		}
		if(!StringUtils.isEmpty(latitude)){
			param.put("pLatitude", latitude); // 纬度
		}
		if(!StringUtils.isEmpty(longitude)){
			param.put("pLongitude", longitude); // 经度
		}
		
		param.put("pageIndex", pageIndex);
		param.put("pageSize", pageSize);
		param.put("pRadius", radius);
		
		//综合筛选值编码
		if (!StringUtils.isEmpty(searchPriceValue)) {
			searchPriceValue = URLDecoder.decode(searchPriceValue, "UTF-8");
		}
		if (!StringUtils.isEmpty(searchBrandValue)) {
			searchBrandValue = URLDecoder.decode(searchBrandValue, "UTF-8");
		}
		if (!StringUtils.isEmpty(searchAreaValue)) {
			searchAreaValue = URLDecoder.decode(searchAreaValue, "UTF-8");
		}
	}
	/**
	 * 根据CITYID 查询关键字/地标/商圈
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getMarkListByCityId(String cityId){
		
		//根据CITYID查询地表商圈
		List<Map<String, Object>> landMarkMapList=null;
		
		Map<String,Object> param=new HashMap<String, Object>();
		try {
			param.put("cityCode",cityId);
			Map<String, Object> resultMap=clientHotelService.getLandMarks(param);
			
			if(resultMap!=null && resultMap.size()>0){
				landMarkMapList = (List<Map<String, Object>>) resultMap.get("datas");
			}
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}
		return landMarkMapList;
	}
	/**
	 *  酒店详情--周边景点 
	 * @return 
	 */
	@Action("spotticket_ajax")
	public String stnearby(){
		try {
			this.hotelDetailStnearby();
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}
		return "spotticket_ajax";
	} 
	/**
	 * 周边景点--方法
	 */
	public void hotelDetailStnearby(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("stage", 2);
		param.put("page", pageIndex);
		param.put("sort", "juli");//按照距离排序
		param.put("longitude", longitude);
		param.put("latitude", latitude);
		param.put("windage", windage);
		Map<String,Object> map = new HashMap<String,Object>();
		// 景区 
		map = mobileSearchService.placeSearch(param);
		
		if(null != map && null != map.get("datas")) {
			getRequest().setAttribute("placelist", map.get("datas"));
			getRequest().setAttribute("isLastPage", map.get("isLastPage"));
			
		}
		// 设置图片前缀 
		this.setImagePrefix();
	}
	public IClientHotelService getClientHotelService() {
		return clientHotelService;
	}
	public void setClientHotelService(IClientHotelService clientHotelService) {
		this.clientHotelService = clientHotelService;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public int getClickType() {
		return clickType;
	}
	public void setClickType(int clickType) {
		this.clickType = clickType;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public String getArrivalZhDate() {
		return arrivalZhDate;
	}
	public void setArrivalZhDate(String arrivalZhDate) {
		this.arrivalZhDate = arrivalZhDate;
	}
	public String getDepartureZhDate() {
		return departureZhDate;
	}
	public void setDepartureZhDate(String departureZhDate) {
		this.departureZhDate = departureZhDate;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	public String getLowRate() {
		return lowRate;
	}
	public void setLowRate(String lowRate) {
		this.lowRate = lowRate;
	}
	public String getHighRate() {
		return highRate;
	}
	public void setHighRate(String highRate) {
		this.highRate = highRate;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getStarRate() {
		return starRate;
	}
	public void setStarRate(String starRate) {
		this.starRate = starRate;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public boolean isAjax() {
		return ajax;
	}
	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getClickPageType() {
		return clickPageType;
	}
	public void setClickPageType(String clickPageType) {
		this.clickPageType = clickPageType;
	}
	public int getWindage() {
		return windage;
	}
	public void setWindage(int windage) {
		this.windage = windage;
	}
	public IClientSearchService getMobileSearchService() {
		return mobileSearchService;
	}
	public void setMobileSearchService(IClientSearchService mobileSearchService) {
		this.mobileSearchService = mobileSearchService;
	}
	public String getSearchPriceValue() {
		return searchPriceValue;
	}
	public void setSearchPriceValue(String searchPriceValue) {
		this.searchPriceValue = searchPriceValue;
	}
	public String getSearchBrandValue() {
		return searchBrandValue;
	}
	public void setSearchBrandValue(String searchBrandValue) {
		this.searchBrandValue = searchBrandValue;
	}
	public String getSearchAreaValue() {
		return searchAreaValue;
	}
	public void setSearchAreaValue(String searchAreaValue) {
		this.searchAreaValue = searchAreaValue;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelTypeName() {
		return hotelTypeName;
	}
	public void setHotelTypeName(String hotelTypeName) {
		this.hotelTypeName = hotelTypeName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
