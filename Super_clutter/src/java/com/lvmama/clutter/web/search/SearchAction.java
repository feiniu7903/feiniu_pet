package com.lvmama.clutter.web.search;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.model.MobileTimePrice;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.IClientSearchService;
import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.DateUtil;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.utils.StringUtil;

/**
 * 查询景点、景点详情Action
 * 
 * @author YangGan
 *
 */
@Results({ 
	@Result(name="place_search",location="/WEB-INF/pages/search/place_search.html",type="freemarker"),
	@Result(name="date_price",location="/WEB-INF/pages/order/date_price.html",type="freemarker")
	
})
@Namespace("/mobile")
public class SearchAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 搜索服务.
	 */
	IClientSearchService mobileSearchService;
	/**
	  * 产品服务 
	 */
	IClientProductService mobileProductService;

	private String keyWorld; // 查询关键字 
	private String page = "1"; // 当前页数 
	private String sort ;// 排序
	private String stage;//2:景点；3：酒店 
	private String fromDest; // 出发地
	private String toDest; // 目的地 
	
	private String productId;// 产品id
	private String branchId; //  类别id 
	private Long location;//返回次数
	private String status;//类型baidu
	private String qingPlaceUrl;//景点详情URL
	/**
	 * 只是跳转到搜索页面.
	 * @return string 
	 */
	@Action("place_search")
	public String placeSearch() {
		return "place_search";
	}
	
	
	/**
	 * ajax 返回查询结果. 
	 * 2：景区；3：酒店；
	 */
	@Action("search_auto_complete")
	public void searchAutoComplete() {
		if(StringUtil.isEmptyString(stage)){
			stage = "2";
		}
		Map<String,Object> resultMap= new HashMap<String,Object>();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("keyword", URLDecoder.decode(keyWorld,"UTF-8"));
			// 景点搜索 
			if("2".equals(stage)) {
				resultMap = mobileSearchService.placeAutoComplete(params);
			// 度假搜索 
			} else { 
				if(!StringUtils.isEmpty(fromDest)) {
					params.put("fromDest", URLDecoder.decode(fromDest,"UTF-8"));
				}
				resultMap = mobileSearchService.routeAutoComplete(params);
			}
			resultMap.put("code", "success");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			resultMap.put("code", "faild");
		}
		super.sendAjaxResult(resultJson(resultMap));
	}
	
	
	/**
	 * 填写订单. 
	 * @return
	 */
	@Action("date_price")
	public String datePrice() {
		try {
			//返回计数
			this.urlBackCount();
			Map<String,Object> param = new HashMap<String,Object>();
			// 获取brancId 
			param.put("branchId", branchId);
			// 时间价格表
			param.put("productId", productId);
			List<MobileTimePrice> timePrice = mobileProductService.timePrice(param);
			// 返回的时间价格表  
			List<List<Map<String,Object>>>  resultTimePrice = new ArrayList<List<Map<String,Object>>>();
			
			if(null != timePrice && timePrice.size() > 0) {
				// 第一个月 的时间价格表
				MobileTimePrice mt = timePrice.get(0);
				Date date = com.lvmama.comm.utils.DateUtil.stringToDate(mt.getDate(),"yyyy-MM-dd");
				int day =  com.lvmama.comm.utils.DateUtil.getDay(date); // 日
				resultTimePrice.add(initTimePriceData(init2Month(date),timePrice));
				// 第二个月 
				resultTimePrice.add(initTimePriceData(init2Month(DateUtil.getMontOfAdd(date,1)),timePrice));
				// 第三个月 
				if(day > 1 && timePrice.size() > 60) {
					resultTimePrice.add(initTimePriceData(init2Month(DateUtil.getMontOfAdd(date,2)),timePrice));
					//resultTimePrice.add(initTimePriceData(init2Month(DateUtil.getMontOfAdd(date,3)),timePrice));
				}
			}	
			
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("datas", timePrice);
			getRequest().setAttribute("timePriceJson", JSONObject.fromObject(m));
			
			getRequest().setAttribute("resultTimePrice", resultTimePrice);
		}catch(Exception e){
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			
		}
		return "date_price";
	}
	/**
	 *返回地址SESSION读写 
	 */
	public void urlBackCount(){
		if(location!=null){
			this.getRequest().getSession().setAttribute("location",location+2);
		}
		if(this.getRequest().getSession().getAttribute("location")!=null && !"".equals(this.getRequest().getSession().getAttribute("location").toString())){
			location=Long.valueOf(this.getRequest().getSession().getAttribute("location").toString());
		}
	}
	/**
	 * 时间价格 格式化 .
	 * @param listMap
	 * @param timePrice
	 */
	public List<Map<String,Object>> initTimePriceData(List<Map<String,Object>> listMap,List<MobileTimePrice> timePrice) {
		for(int i = 0;i<listMap.size();i++) {
			Map<String,Object> m = listMap.get(i);
			for(int j = 0 ; j < timePrice.size();j++) {
				MobileTimePrice mt = timePrice.get(j);
			    if(mt.getDate().equals(m.get("date").toString())) {
			    	// 数据复制 
			    	m.put("date", mt.getDate());
			    	if(status!=null && "baidu".equals(status)){
			    		m.put("sellPriceYuan", baiduSubZeroAndDot(BaiduActivityUtils.getProductPrice(productId,mt.getSellPriceYuan()).toString()));
			    	}else{
			    		m.put("sellPriceYuan", ClientUtils.subZeroAndDot(mt.getSellPriceYuan()+""));
			    	}
					m.put("marketPriceYuan", ClientUtils.subZeroAndDot(mt.getMarketPriceYuan()+""));
					m.put("dayStock", mt.getDayStock()+"");
					m.put("isSellable", mt.isSellable());
					if(mt.getDate().equals(DateUtils.formatDate(new Date(), "yyyy-MM-dd"))) {
						m.put("istoday", true); // 是否当前天 
					} else {
						m.put("istoday", false); // 是否当前天 
					}
			    	break;
			    }
			}
		}
		
		return listMap;
	}
	/** 
     * 使用java正则表达式去掉多余的.与0 
     * @param s 
     * @return  
     */  
    public static String baiduSubZeroAndDot(String s){  
        if(s.indexOf(".0") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
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
		m.put("sellPriceYuan", "");
		m.put("marketPriceYuan", "");
		m.put("dayStock", "");
		m.put("isSellable", false);
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
					return com.lvmama.comm.utils.DateUtil.getZHDay(date);
				}
			}
		}
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
			m.put("sellPriceYuan", "");
			m.put("marketPriceYuan", "");
			m.put("dayStock", "");
			m.put("isSellable", false);
			m.put("year", year); // 年 
			m.put("month", t_m); // 月份 
			m.put("week", 0); // 星期 数字
			m.put("zHweek", "");// 星期 中文格式
			m.put("istoday", false); // 是否当前天 
			
			t_list.add(m);
		}
		
		return t_list;
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
	 * ajax 返回查询结果. 
	 * 2：景区；3：酒店；
	 */
	@Action("search")
	public void search() {
		if(StringUtil.isEmptyString(stage)){
			stage = "2";
		}
		Map<String,Object> resultMap= new HashMap<String,Object>();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("keyword", URLDecoder.decode(keyWorld,"UTF-8"));
			params.put("page", page);
			params.put("stage", stage);
			params.put("sort", sort);
			// 景点搜索 
			if("2".equals(stage)) {
				resultMap = mobileSearchService.placeSearch(params);
			// 度假搜索 
			} else { 
				resultMap = mobileSearchService.routeSearch(params);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		super.sendAjaxResult(resultJson(resultMap));
	}
	
	
	/**
	 * 返回json数据. 
	 * @param resultMap
	 * @return
	 */
	public String resultJson(Map<String,Object> resultMap) {
    	JSONObject json = JSONObject.fromObject(resultMap);
		return json.toString();
    }
	
	
	public String getKeyWorld() {
		return keyWorld;
	}

	public void setKeyWorld(String keyWorld) {
		this.keyWorld = keyWorld;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getFromDest() {
		return fromDest;
	}


	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}


	public String getToDest() {
		return toDest;
	}


	public void setToDest(String toDest) {
		this.toDest = toDest;
	}
	
	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	 public void setMobileSearchService(IClientSearchService mobileSearchService) {
		this.mobileSearchService = mobileSearchService;
	}


	public void setMobileProductService(IClientProductService mobileProductService) {
		this.mobileProductService = mobileProductService;
	}


	public Long getLocation() {
		return location;
	}


	public void setLocation(Long location) {
		this.location = location;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getQingPlaceUrl() {
		return qingPlaceUrl;
	}


	public void setQingPlaceUrl(String qingPlaceUrl) {
		this.qingPlaceUrl = qingPlaceUrl;
	}
	
	


}
