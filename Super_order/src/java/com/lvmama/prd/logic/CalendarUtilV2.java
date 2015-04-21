package com.lvmama.prd.logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;

public class CalendarUtilV2 {
	
	/**最多显示180天的时间价格**/
	protected Integer MAX_DAYS=180;
	/**最多显示6个月的时间价格**/
	private Integer MAX_MONTHS=6;
	
	private ProdProductDAO prodProductDAO = (ProdProductDAO)SpringBeanProxy.getBean("prodProductDAO");
	private ProdProductBranchDAO prodProductBranchDAO = (ProdProductBranchDAO)SpringBeanProxy.getBean("prodProductBranchDAO");
	protected ProductTimePriceLogic productTimePriceLogic = (ProductTimePriceLogic)SpringBeanProxy.getBean("productTimePriceLogic");
	
	Date firstDate=null;
	Date lastDate=null;
	SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat sfYear = new SimpleDateFormat("yyyy");
	SimpleDateFormat sfMonth = new SimpleDateFormat("MM");
	private ProdProduct prodProduct;
	
	/**
	 * 查询销售产品默认类别时间价格表
	 * @param productId 销售产品id.
	 * @return
	 */
	public List<CalendarModel> selectSaleTimePriceByProductId(Long productId) {
		ProdProductBranch prodBranch = prodProductBranchDAO.selectDefaultBranchByProductId(productId);
		if(prodBranch != null){
			return this.selectSaleTimePrice(prodBranch.getProdBranchId());
		} else {
			this.prodProduct = this.prodProductDAO.selectByPrimaryKey(productId);
			initDays();
			return initCalendarModel(MAX_MONTHS, new HashMap<String, TimePrice>());
		}
	}
	
	private void initDays(){
		MAX_DAYS = prodProduct.getShowSaleDays();
		MAX_MONTHS = (int)Math.ceil((double)(MAX_DAYS/30.0));
	}
	
	/**
	 * 查询销售类别可售时间价格表.
	 * @param prodBranchId 销售产品类别id.
	 * @return 
	 */
	public List<CalendarModel> selectSaleTimePrice(Long prodBranchId){
		//计算取时间价格的开始时间和结束时间
		this.firstDate = null;
		this.lastDate = null;
		Map<String, TimePrice> map = new HashMap<String, TimePrice>();
		if(prodBranchId != null) {
			this.prodProduct = prodProductDAO.selectProductByProdBranchId(prodBranchId);
			initDays();
			if(prodProduct != null) {
				//查询时间价格
				map = this.getMapObject(this.prodProduct.getProductId() ,prodBranchId);
			}
		}
		Integer monthNumber = MAX_MONTHS;
		if(map == null || map.size()==0){
			monthNumber = 1;
		}
		List<CalendarModel> mlist = initCalendarModel(monthNumber, map);
		return mlist;
	}
	
	/**
	 * 构建calendarModel便于页面显示
	 * @param monthNumber 取几个月数据
	 * @param map
	 * @param c
	 * @return
	 */
	private List<CalendarModel> initCalendarModel(Integer monthNumber, Map<String, TimePrice> map) {
		Calendar c = Calendar.getInstance();
		if (this.firstDate != null) {
			c.setTime(this.firstDate);
		} else {
			c.setTime(new Date());
		}
		//可售日期
		Integer currentDay = DateUtil.getDay(c.getTime());
		if(!map.isEmpty()&&currentDay>=15){
			Date now = DateUtil.getDayStart(new Date());
			Date date=DateUtils.addMonths(now, 1);
			date = DateUtils.setDays(date, 1);
			int days=DateUtil.getDaysBetween(now, date);
			if(days<map.size()){
				monthNumber ++;
			}
		}
		List<CalendarModel> mlist = new ArrayList<CalendarModel>();
		for (int i = 1; i <= monthNumber; i++) {
			CalendarModel cal = new CalendarModel();
			//循环第二个月加月份
			if(i > 1) {
				c.add(Calendar.MONTH, 1);
			}
			int month = Integer.parseInt(sfMonth.format(c.getTime()));
			int year = Integer.parseInt(sfYear.format(c.getTime()));
			cal.setMonth(month);
			cal.setYear(year);
			
			Calendar calMonth=Calendar.getInstance();
			calMonth.set(Calendar.YEAR,year);
			calMonth.set(Calendar.MONTH,month-1);
			calMonth.set(Calendar.DATE, 1);
			calMonth.set(Calendar.HOUR_OF_DAY, 0);
			calMonth.set(Calendar.MINUTE, 0);
			calMonth.set(Calendar.SECOND, 0);
			calMonth.set(Calendar.MILLISECOND,0);
			if(null!=lastDate&&calMonth.getTime().after(lastDate)){//只显示到最后一天所在的月份
				continue;
			}
			
			//系统时间>=1号并且<=14号时显示跨月时间价格
			Calendar currentCalendar = Calendar.getInstance();
			if(currentDay >= 15 && i == 1) {				
				currentCalendar.setTime(this.getCurrentContentFirstDate(c.getTime(),15));
				cal.setCalendar(loadTimePriceByPid(this.prodProduct, currentCalendar, map));
				cal.setFlagNextMonth(DateUtil.getMonth(currentCalendar.getTime()));
			} else {
				currentCalendar.setTime(this.getCurrentContentFirstDate(c.getTime(),1));
				cal.setCalendar(loadTimePriceByPid(this.prodProduct,currentCalendar,map));
			}
			mlist.add(cal);
		}
		return mlist;
	}
	
	private  TimePrice[][] loadTimePriceByPid(final ProdProduct product, final Calendar calendar, final Map<String, TimePrice> map){
		Calendar newCalendar = calendar;

		TimePrice[][] calendarTimePrice = new TimePrice[6][7];
		for (int i = 0; i <calendarTimePrice.length; i++) {
			TimePrice[] objH = calendarTimePrice[i];
			for (int j = 0; j <objH.length; j++) {
				TimePrice tp = null;			
				tp = map.get(sf.format(newCalendar.getTime()));
				if (tp!=null) {
					objH[j] = tp;
				} else {
					tp = new TimePrice();
					tp.setSpecDate(newCalendar.getTime());
					objH[j] = tp;
				}
				newCalendar.add(Calendar.DATE, 1);
			}
		}
		return calendarTimePrice;
	}
	
	protected List<TimePrice> getTimePriceList(Long productId,Long prodBranchId){
		return productTimePriceLogic.getTimePriceList(productId, prodBranchId, MAX_DAYS);
	}
	
	private Map<String, TimePrice> getMapObject(Long id ,Long prodBranchId) {
		Map<String, TimePrice> map = new HashMap<String, TimePrice>();
	
		List<TimePrice> ptpList = getTimePriceList(id, prodBranchId);
	
		//可销售的第一天
		if (!ptpList.isEmpty()) {
			TimePrice timePrice = ptpList.get(0);
			if (timePrice != null) {
				firstDate = timePrice.getSpecDate();
			}
		}
		//时间价格表最后一天
		if(ptpList.size()>0){
			TimePrice timePrice = ptpList.get(ptpList.size()-1);
			if (timePrice != null) {
				lastDate = timePrice.getSpecDate();
			}
		}
		//list转换为map方便后续处理
		if (this.firstDate != null) {
			for (TimePrice timePrice : ptpList) {
					if(timePrice.getSpecDate().after(this.firstDate)||sf.format(timePrice.getSpecDate()).equals(sf.format(this.firstDate))){
					map.put(sf.format(timePrice.getSpecDate()), timePrice);
				}
			}	
		}
		
		return map;
	} 
	
	/**
	 * 计算当期日期前面的日期
	 * @param date
	 * @param day 取到起始的日期.
	 * @return
	 */
	private Date getCurrentContentFirstDate(Date date,int day_in_month){		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, day_in_month);		
		int day= -cal.get(Calendar.DAY_OF_WEEK)+1;		
		return DateUtils.addDays(cal.getTime(), day);
	}
}
