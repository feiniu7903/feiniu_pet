package com.lvmama.tmall.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lvmama.comm.bee.po.tmall.ProdTimePrice;
import com.lvmama.comm.utils.DateUtil;

public class TravelComboTypeHelp {
	private Date startDate;
	private Date endDate;
	
	private ComboTypeTimePrice man;
	private ComboTypeTimePrice child;
	private ComboTypeTimePrice diff;
	
	private Map<String, List<ProdTimePrice>> ptpMap;
	
	private List<Map<String, Object>> jsonPriceCalendars;
	
	public TravelComboTypeHelp(Map<String, List<ProdTimePrice>> ptpMap) {
		this.ptpMap = ptpMap;
	}
	
	public List<Map<String, Object>> getJsonPriceCalendar() {
		if (ptpMap == null || ptpMap.isEmpty()) {
			return null;
		}
		// 验证ptpMap是否有效
		if (checkPtpMap()) {
			// 初始化最大，最小时间
			initDateMap();
			// 初始化时间价格
			initComboTypeTimePrice();
			// 处理获取套餐价格时间表
			proccessComboTypeTimePrice();
			
			if (jsonPriceCalendars != null && !jsonPriceCalendars.isEmpty()) {
				return jsonPriceCalendars;
			}
		}
		return null;
	}
	
	private boolean checkPtpMap() {
		Set<String> set = ptpMap.keySet();
		for (String key : set) {
			if (ptpMap.get(key) != null && !ptpMap.get(key).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private void proccessComboTypeTimePrice() {
		Date tempDate = startDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		List<PriceCalendar> priceCalendars = new ArrayList<PriceCalendar>();
		while (tempDate.before(endDate) || tempDate.equals(endDate)) {
			String dateStr = DateUtil.formatDate(tempDate, DateUtil.PATTERN_yyyy_MM_dd);
			priceCalendars.add(new PriceCalendar(dateStr));
			// 时间加1
			calendar.add(Calendar.DATE, 1);
			tempDate = calendar.getTime();
		}
		// 初始化套餐
		initJsonPriceCalendar(priceCalendars);
	}
	
	private void initJsonPriceCalendar(List<PriceCalendar> priceCalendars) {
		List<Map<String, Object>> jsonPriceCalendars = new ArrayList<Map<String,Object>>();
		for (PriceCalendar priceCalendar : priceCalendars) {
			Map<String, Object> jsonPriceCalendar = new HashMap<String, Object>();
			jsonPriceCalendar.put("date", priceCalendar.date);
			jsonPriceCalendar.put("man_num", priceCalendar.man_num);
			jsonPriceCalendar.put("man_price", priceCalendar.man_price);
			jsonPriceCalendar.put("diff_price", priceCalendar.diff_price);
			jsonPriceCalendar.put("child_num", priceCalendar.child_num);
			jsonPriceCalendar.put("child_price", priceCalendar.child_price);
			jsonPriceCalendars.add(jsonPriceCalendar);
		}
		this.jsonPriceCalendars = jsonPriceCalendars;
	}

	private void initDateMap() {
		Set<String> set = ptpMap.keySet();
		for (String key : set) {
			if (ptpMap.get(key) != null) {
				List<ProdTimePrice> list = ptpMap.get(key);
				if (list != null && !list.isEmpty()) {
					if (startDate != null) {
						Date tempDate = getMinDate(list);
						if (tempDate.before(startDate)) {
							startDate = tempDate;
						}
					} else {
						startDate = getMinDate(list);
					}
					if (endDate != null) {
						Date tempDate = getMaxDate(list);
						if (tempDate.after(endDate)) {
							endDate = tempDate;
						}
					} else {
						endDate = getMaxDate(list);
					}
				}
			}
		}
	}
	
	private void initComboTypeTimePrice() {
		// 成人
		man = getComboTypeTimePrice(TaobaoSyncHelp.TB_COMBO_TYPE_MAN, ptpMap);
		// 儿童
		child = getComboTypeTimePrice(TaobaoSyncHelp.TB_COMBO_TYPE_CHILD, ptpMap);
		// 房差
		diff = getComboTypeTimePrice(TaobaoSyncHelp.TB_COMBO_TYPE_DIFF, ptpMap);
	}
	
	private ComboTypeTimePrice getComboTypeTimePrice(String comboType, Map<String, List<ProdTimePrice>> ptpMap) {
		ComboTypeTimePrice cttp = new ComboTypeTimePrice();
		cttp.comboType = comboType;
		if (ptpMap.get(comboType) != null) {
			List<ProdTimePrice> ptpList = ptpMap.get(comboType);
			cttp.timePriceMap = prodTimePrice2Map(ptpList);
		}
		return cttp;
	}
	
	public Date getMaxDate(List<ProdTimePrice> list) {
		ProdTimePrice prodTimePrice = list.get(list.size() - 1);
		return prodTimePrice.getSpecDate();
	}
	
	public Date getMinDate(List<ProdTimePrice> list) {
		ProdTimePrice prodTimePrice = list.get(0);
		return prodTimePrice.getSpecDate();
	}
	
	private Map<String, ProdTimePrice> prodTimePrice2Map(List<ProdTimePrice> ptpList) {
		Map<String, ProdTimePrice> ptpMap = new HashMap<String, ProdTimePrice>();
		for (ProdTimePrice prodTimePrice : ptpList) {
			Date specDate = prodTimePrice.getSpecDate();
			if (specDate != null) {
				String dateStr = DateUtil.formatDate(specDate, DateUtil.PATTERN_yyyy_MM_dd);
				ptpMap.put(dateStr, prodTimePrice);
			}
		}
		return ptpMap;
	}
	
	class ComboTypeTimePrice {
		String comboType = null;
		Map<String, ProdTimePrice> timePriceMap = null;
		
		public ProdTimePrice getProdTimePrice(String dateStr) {
			if (timePriceMap != null) {
				return timePriceMap.get(dateStr);
			}
			return null;
		}
		
		public Long getPrice(String dateStr) {
			if (timePriceMap != null) {
				ProdTimePrice prodTimePrice = getProdTimePrice(dateStr);
				if (prodTimePrice != null) {
					return prodTimePrice.getPrice();
				}
			}
			return null;
		}
		
		public Long getDayStock(String dateStr) {
			if (timePriceMap != null) {
				ProdTimePrice prodTimePrice = getProdTimePrice(dateStr);
				if (prodTimePrice != null) {
					Long dayStock = prodTimePrice.getDayStock();
					// 余票，默认为6
					if (dayStock == null || dayStock.longValue() < 0) {
						dayStock = 6L;
					}
					return dayStock;
				}
			}
			return null;
		}
	}
	
	class PriceCalendar {
        Long man_num;
        Long man_price;
		Long child_num;
		Long child_price;
        Long diff_price; 
		String date;
		
		public PriceCalendar(String dateStr) {
			date = dateStr;
			if (man != null) {
				man_num = man.getDayStock(dateStr);
				man_price = man.getPrice(dateStr);
			}
			if (child != null) {
				child_num = child.getDayStock(dateStr);
				child_price = child.getPrice(dateStr);
			}
			if (diff != null) {
				diff_price = diff.getPrice(dateStr);
			}
		}
	}
}
