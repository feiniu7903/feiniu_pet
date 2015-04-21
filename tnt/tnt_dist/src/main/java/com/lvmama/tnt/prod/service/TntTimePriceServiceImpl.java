package com.lvmama.tnt.prod.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.service.prod.ProductServiceProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.tnt.comm.util.PriceUtil;
import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.order.service.TntTimePriceService;

@Repository("tntTimePriceService")
public class TntTimePriceServiceImpl implements TntTimePriceService {

	protected static final Log LOG = LogFactory
			.getLog(TntTimePriceServiceImpl.class);

	@Autowired
	private ProductServiceProxy productServiceProxy;
	@Autowired
	private PageService pageService;
	@Autowired
	private TntProdPolicyService tntProdPolicyService;

	@Override
	public String getJSONTimePrice(Long productId, Long userId) {
		String[] month = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12" };
		ProdProductBranch prodProductBranch = pageService
				.selectDefaultBranchByProductId(productId);
		if (prodProductBranch == null)
			return null;
		Long branchId = prodProductBranch.getProdBranchId();
		List<CalendarModel> cmList = getTimePriceByBranchId(branchId);
		if (userId != null && userId > 0
				&& prodProductBranch.getProdProduct().isPaymentToLvmama()) {
			fillDistInfoForCalendar(cmList, branchId, userId);
		}
		JSONObject objM = new JSONObject();
		if (CollectionUtils.isNotEmpty(cmList)) {
			try {
				// 循环判断月份
				for (int m = 0; m < month.length; m++) {
					JSONArray array = new JSONArray();
					for (int k = 0; k < cmList.size(); k++) {
						CalendarModel cm = cmList.get(k);
						TimePrice[][] calendar = cm.getCalendar();
						for (int i = 0; i < calendar.length; i++) {
							TimePrice[] objH = calendar[i];
							for (int j = 0; j < objH.length; j++) {
								JSONObject obj = new JSONObject();
								TimePrice tp = objH[j];
								if ((DateUtil
										.getDateTime("M", tp.getSpecDate()))
										.equals(month[m])) {
									obj.put("date", DateUtil.getDateTime(
											"yyyy-MM-dd", tp.getSpecDate()));
									long dayStock = tp.getDayStock();
									if (dayStock == -1 || dayStock == 0
											|| dayStock > 0
											|| tp.isOnlyForLeave() == true
											|| tp.getOverSale().equals("true")) {
										JSONObject j1 = excecuteGetJsonTimeData(
												tp, dayStock);
										obj.putAll(j1);
									} else {
										obj.put("number", "");
										obj.put("price", "");
										obj.put("active", "");
									}
									if (!array.contains(obj)) {
										array.add(obj);
									}
								}
							}
						}
					}
					if (array != null && !array.equals("")) {
						objM.put(month[m], array);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				LOG.info("新版景区返回时间价格表出错");
			}
		}
		return objM.toString();
	}

	public void fillDistInfoForCalendar(List<CalendarModel> calendarModelList,
			Long branchId, Long userId) {
		if (calendarModelList != null) {
			String rule = tntProdPolicyService.getCalculateRule(branchId,
					userId);
			Map<String, Long> map = new HashMap<String, Long>();
			for (CalendarModel calendarModel : calendarModelList) {
				TimePrice[][] timePriceArray = calendarModel.getCalendar();
				for (int i = 0; i < timePriceArray.length; i++) {
					for (int j = 0; j < timePriceArray[i].length; j++) {
						TimePrice timePrice = timePriceArray[i][j];
						if (timePrice.getSpecDate() != null) {
							String specDate = TntUtil.formatDate(timePrice
									.getSpecDate());
							if (!map.containsKey(specDate)) {
								Long price = timePrice.getPrice();
								if (price != null && price > 0) {
									price = tntProdPolicyService
											.getPriceByRule(rule, timePrice
													.getPrice(), timePrice
													.getSettlementPrice());
									timePrice.setPrice(price);
								}
								map.put(specDate, price);
							}
						}
					}
				}
			}
		}
	}

	public JSONObject excecuteGetJsonTimeData(TimePrice tp, long dayStock) {
		boolean isMultiJourney = false;
		JSONObject obj = new JSONObject();
		if ((!tp.isNeedResourceConfirm() && dayStock == -1) || dayStock > 9) {
			if (isMultiJourney && tp.getMultiJourneyId() == null) {
			} else {
				obj.put("number", "充足");
				obj.put("price", PriceUtil.convertToYuanInt(tp.getPrice()));
				if ((tp.getFavorJsonParams() != null && !tp
						.getFavorJsonParams().equals(""))
						|| (tp.getCuCouponFlag() > 0)) {
					obj.put("active", "促");
				} else {
					obj.put("active", "");
				}
			}
		} else if (dayStock > -1 && dayStock != 0) {
			if (isMultiJourney && tp.getMultiJourneyId() == null) {
			} else {
				obj.put("number", dayStock);
				obj.put("price", PriceUtil.convertToYuanInt(tp.getPrice()));
				if ((tp.getFavorJsonParams() != null && !tp
						.getFavorJsonParams().equals(""))
						|| (tp.getCuCouponFlag() > 0)) {
					obj.put("active", "促");
				} else {
					obj.put("active", "");
				}
			}
		} else if (((tp.isOverSaleAble() || tp.isNeedResourceConfirm()) && dayStock == -1)
				|| (tp.isOverSaleAble() && dayStock == 0)) {
			if (isMultiJourney && tp.getMultiJourneyId() == null) {

			} else {
				obj.put("number", "");
				obj.put("price", PriceUtil.convertToYuanInt(tp.getPrice()));
				if ((tp.getFavorJsonParams() != null && !tp
						.getFavorJsonParams().equals(""))
						|| (tp.getCuCouponFlag() > 0)) {
					obj.put("active", "促");
				} else {
					obj.put("active", "");
				}
			}
		} else if (!tp.isSellable(1)) {
			obj.put("number", "售完");
			obj.put("price", PriceUtil.convertToYuanInt(tp.getPrice()));
			obj.put("active", "");
		}
		return obj;
	}

	private List<CalendarModel> getTimePriceByBranchId(Long branchId) {
		List<CalendarModel> cmList = null;
		if (branchId != null && branchId > 0) {
			cmList = productServiceProxy.getProductCalendarByBranchId(branchId);

		}
		return cmList;
	}

}
