package com.lvmama.comm.bee.vo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.utils.DateUtil;

public class EbkCalendarModel extends CalendarModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4779337640619556380L;
	private EbkProdTimePrice[][] ebkCalendar;
	private boolean haveShareStock = false;
	private String shareStockBranchId;
	private String shareStockBranchName;

	public void setEbkTimePrice(List<EbkProdTimePrice> timePriceList,
			Date beginDate) {
		Map<String, EbkProdTimePrice> timePriceMap = new HashMap<String, EbkProdTimePrice>();
		if (timePriceList != null && timePriceList.size() > 0) {
			for (int i = 0; i < timePriceList.size(); i++) {
				EbkProdTimePrice timePrice = timePriceList.get(i);
				String key = DateUtil.getDateTime(format,
						timePrice.getSpecDate());
				timePriceMap.put(key, timePrice);
			}
		}
		Calendar c = Calendar.getInstance();

		c.setTime(beginDate);
		c.add(Calendar.DATE, -1);
		this.ebkCalendar = new EbkProdTimePrice[6][7];
		for (int i = 0; i < ebkCalendar.length; i++) {
			EbkProdTimePrice[] objH = ebkCalendar[i];
			for (int j = 0; j < objH.length; j++) {
				EbkProdTimePrice tp = null;
				c.add(Calendar.DATE, 1);
				String key = DateUtil.getDateTime(format, c.getTime());
				tp = timePriceMap.get(key);
				if (tp != null) {
					objH[j] = tp;
				} else {
					tp = new EbkProdTimePrice();
					tp.setSpecDate(c.getTime());

					objH[j] = tp;
				}
			}
		}
	}

	public EbkProdTimePrice[][] getEbkCalendar() {
		return ebkCalendar;
	}

	public void setEbkCalendar(EbkProdTimePrice[][] ebkCalendar) {
		this.ebkCalendar = ebkCalendar;
	}

	public boolean isHaveShareStock() {
		return haveShareStock;
	}

	public void setHaveShareStock(boolean haveShareStock) {
		this.haveShareStock = haveShareStock;
	}

	public String getShareStockBranchId() {
		return shareStockBranchId;
	}

	public void setShareStockBranchId(String shareStockBranchId) {
		this.shareStockBranchId = shareStockBranchId;
	}

	public String getShareStockBranchName() {
		return shareStockBranchName;
	}

	public void setShareStockBranchName(String shareStockBranchName) {
		this.shareStockBranchName = shareStockBranchName;
	}
}
