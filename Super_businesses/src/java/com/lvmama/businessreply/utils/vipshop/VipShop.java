package com.lvmama.businessreply.utils.vipshop;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.utils.DateUtil;

public final class VipShop {
	public static Map<String, MappingPlanAndPrice> planPriceMapping = new HashMap<String, MappingPlanAndPrice>();
	static {
		planPriceMapping.put("北京", new MappingPlanAndPrice("长线游_北京","10.00"));
		planPriceMapping.put("武夷山", new MappingPlanAndPrice("长线游_福建","3.00"));
		planPriceMapping.put("厦门", new MappingPlanAndPrice("长线游_福建","4.00"));
		planPriceMapping.put("敦煌", new MappingPlanAndPrice("长线游_甘肃","4.00"));
		planPriceMapping.put("甘肃", new MappingPlanAndPrice("长线游_甘肃","3.46"));
		planPriceMapping.put("阳朔", new MappingPlanAndPrice("长线游_广西","5.00"));
		planPriceMapping.put("桂林", new MappingPlanAndPrice("长线游_广西","5.00"));
		planPriceMapping.put("广西", new MappingPlanAndPrice("长线游_广西","5.00"));
		planPriceMapping.put("贵州", new MappingPlanAndPrice("长线游_贵州","2.55"));
		planPriceMapping.put("贵阳", new MappingPlanAndPrice("长线游_贵州","2.12"));
		planPriceMapping.put("海南", new MappingPlanAndPrice("长线游_海南","10.00"));
		planPriceMapping.put("三亚", new MappingPlanAndPrice("长线游_海南","7.00"));
		planPriceMapping.put("海口", new MappingPlanAndPrice("长线游_海南","3.00"));
		planPriceMapping.put("黑龙江", new MappingPlanAndPrice("长线游_黑龙江","4.00"));
		planPriceMapping.put("哈尔滨", new MappingPlanAndPrice("长线游_黑龙江","4.00"));
		planPriceMapping.put("湖北", new MappingPlanAndPrice("长线游_湖北","1.52"));
		planPriceMapping.put("张家界", new MappingPlanAndPrice("长线游_湖南","9.00"));
		planPriceMapping.put("湖南", new MappingPlanAndPrice("长线游_湖南","4.00"));
		planPriceMapping.put("吉林", new MappingPlanAndPrice("长线游_吉林","1.50"));
		planPriceMapping.put("长白山", new MappingPlanAndPrice("长线游_吉林","2.56"));
		planPriceMapping.put("南昌", new MappingPlanAndPrice("长线游_江西","1.68"));
		planPriceMapping.put("井冈山", new MappingPlanAndPrice("长线游_江西","2.25"));
		planPriceMapping.put("江西", new MappingPlanAndPrice("长线游_江西","2.73"));
		planPriceMapping.put("庐山", new MappingPlanAndPrice("长线游_江西","2.18"));
		planPriceMapping.put("大连", new MappingPlanAndPrice("长线游_辽宁","4.00"));
		planPriceMapping.put("辽宁", new MappingPlanAndPrice("长线游_辽宁","3.00"));
		planPriceMapping.put("内蒙古", new MappingPlanAndPrice("长线游_内蒙古","3.57"));
		planPriceMapping.put("青岛", new MappingPlanAndPrice("长线游_山东","3.50"));
		planPriceMapping.put("山东", new MappingPlanAndPrice("长线游_山东","2.00"));
		planPriceMapping.put("西安", new MappingPlanAndPrice("长线游_陕西","4.00"));
		planPriceMapping.put("九寨沟", new MappingPlanAndPrice("长线游_四川","8.00"));
		planPriceMapping.put("四川", new MappingPlanAndPrice("长线游_四川","10.00"));
		planPriceMapping.put("拉萨", new MappingPlanAndPrice("长线游_西藏","10.00"));
		planPriceMapping.put("西藏", new MappingPlanAndPrice("长线游_西藏","7.00"));
		planPriceMapping.put("伊犁", new MappingPlanAndPrice("长线游_新疆","4.00"));
		planPriceMapping.put("新疆", new MappingPlanAndPrice("长线游_新疆","6.00"));
		planPriceMapping.put("大理", new MappingPlanAndPrice("长线游_云南","2.00"));
		planPriceMapping.put("云南", new MappingPlanAndPrice("长线游_云南","15.00"));
		planPriceMapping.put("香格里拉", new MappingPlanAndPrice("长线游_云南","2.00"));
		planPriceMapping.put("腾冲", new MappingPlanAndPrice("长线游_云南","2.00"));
		planPriceMapping.put("丽江", new MappingPlanAndPrice("长线游_云南","12.00"));
		planPriceMapping.put("昆明", new MappingPlanAndPrice("长线游_云南","4.00"));
		planPriceMapping.put("埃及", new MappingPlanAndPrice("出境游_上海_埃及","4.00"));
		planPriceMapping.put("澳大利亚", new MappingPlanAndPrice("出境游_上海_澳大利亚","4.00"));
		planPriceMapping.put("巴厘岛", new MappingPlanAndPrice("出境游_上海_巴厘岛","4.00"));
		planPriceMapping.put("朝鲜", new MappingPlanAndPrice("出境游_上海_朝鲜","2.00"));
		planPriceMapping.put("大溪地", new MappingPlanAndPrice("出境游_上海_大溪地","4.00"));
		planPriceMapping.put("迪拜", new MappingPlanAndPrice("出境游_上海_迪拜","4.00"));
		planPriceMapping.put("法国", new MappingPlanAndPrice("出境游_上海_法国","2.68"));
		planPriceMapping.put("菲律宾", new MappingPlanAndPrice("出境游_上海_菲律宾","5.00"));
		planPriceMapping.put("斐济", new MappingPlanAndPrice("出境游_上海_斐济","2.00"));
		planPriceMapping.put("韩国", new MappingPlanAndPrice("出境游_上海_韩国","5.00"));
		planPriceMapping.put("加拿大", new MappingPlanAndPrice("出境游_上海_加拿大","6.00"));
		planPriceMapping.put("柬埔寨", new MappingPlanAndPrice("出境游_上海_柬埔寨","2.50"));
		planPriceMapping.put("肯尼亚", new MappingPlanAndPrice("出境游_上海_肯尼亚","4.00"));
		planPriceMapping.put("马尔代夫", new MappingPlanAndPrice("出境游_上海_马尔代夫","10.00"));
		planPriceMapping.put("毛里求斯", new MappingPlanAndPrice("出境游_上海_毛里求斯","5.00"));
		planPriceMapping.put("美国", new MappingPlanAndPrice("出境游_上海_美国","8.00"));
		planPriceMapping.put("南非", new MappingPlanAndPrice("出境游_上海_南非","4.00"));
		planPriceMapping.put("尼泊尔", new MappingPlanAndPrice("出境游_上海_尼泊尔_自由行","4.00"));
		planPriceMapping.put("欧洲", new MappingPlanAndPrice("出境游_上海_欧洲","22.00"));
		planPriceMapping.put("意大利", new MappingPlanAndPrice("出境游_上海_欧洲","16.00"));
		planPriceMapping.put("法国", new MappingPlanAndPrice("出境游_上海_欧洲","16.00"));
		planPriceMapping.put("奥地利", new MappingPlanAndPrice("出境游_上海_欧洲","8.00"));
		planPriceMapping.put("德国", new MappingPlanAndPrice("出境游_上海_欧洲","4.00"));
		planPriceMapping.put("俄罗斯", new MappingPlanAndPrice("出境游_上海_欧洲","4.00"));
		planPriceMapping.put("日本", new MappingPlanAndPrice("出境游_上海_日本","4.00"));
		planPriceMapping.put("塞班", new MappingPlanAndPrice("出境游_上海_塞班","5.00"));
		planPriceMapping.put("沙巴", new MappingPlanAndPrice("出境游_上海_沙巴文莱","4.00"));
		planPriceMapping.put("文莱", new MappingPlanAndPrice("出境游_上海_沙巴文莱","4.00"));
		planPriceMapping.put("斯里兰卡", new MappingPlanAndPrice("出境游_上海_斯里兰卡","3.00"));
		planPriceMapping.put("泰国", new MappingPlanAndPrice("出境游_上海_泰国","8.00"));
		planPriceMapping.put("土耳其", new MappingPlanAndPrice("出境游_上海_土耳其","4.00"));
		planPriceMapping.put("希腊", new MappingPlanAndPrice("出境游_上海_希腊","4.00"));
		planPriceMapping.put("香港", new MappingPlanAndPrice("出境游_上海_香港","12.00"));
		planPriceMapping.put("新西兰", new MappingPlanAndPrice("出境游_上海_新西兰","4.00"));
		planPriceMapping.put("以色列", new MappingPlanAndPrice("出境游_上海_以色列","2.00"));
		planPriceMapping.put("印度", new MappingPlanAndPrice("出境游_上海_印度","4.00"));
		planPriceMapping.put("英国", new MappingPlanAndPrice("出境游_上海_英国","4.00"));
		planPriceMapping.put("越南", new MappingPlanAndPrice("出境游_上海_越南","4.00"));
	}

	public static void buildJSONString(List<ProductSearchInfo> results,
			StringBuilder sb) {
		if (null == results || results.isEmpty() || null == sb) {
			return;
		}
		for (ProductSearchInfo psi : results) {
			if (!"上海".equals(psi.getFromDest()) || StringUtils.isEmpty(psi.getToDest())) {
				continue;
			}
			MappingPlanAndPrice mpap = planPriceMapping.get(psi.getToDest());
			if (null == mpap) {
				continue;
			}
			
			sb.append("{");
			sb.append("\"user_name\": \"baidu-驴妈妈2111452-2\",");
			sb.append("\"plan_name\": \"" + mpap.getPlan() + "\",");
			sb.append("\"line_name\": \"" + (psi.getProductName().length() > 15 ? psi.getProductName().substring(0,15) : psi.getProductName()) + "\",");
			sb.append("\"line_bid\": \"" + mpap.getPrice() + "\",");
			if (null != psi.getSubProductType()
					&& psi.getSubProductType().contains("FREENESS")) {
				sb.append("\"line_type\": \"自助游\",");
			} else {
				sb.append("\"line_type\": \"跟团游\",");
			}
			sb.append("\"line_image\": \"" + psi.getSmallImageUrl() + "\",");
			sb.append("\"idea\": [{\"idea_title\": \"" + (psi.getFromDest() == null ? "" : psi.getFromDest()) + "到" +  (psi.getToDest() == null ? "" : psi.getToDest()) + "旅游线路热卖中! 驴妈妈旅游网 \",\"idea_desc\":\"驴妈妈精选" + (psi.getFromDest() == null ? "" : psi.getFromDest()) + "到" +  (psi.getToDest() == null ? "" : psi.getToDest()) + "旅游线路,品质保证,行程透明公开,满意度达99%,赶快预订! 详情咨询:1010-6060 \"},{\"idea_title\":\"" + (psi.getFromDest() == null ? "" : psi.getFromDest()) + "到" +  (psi.getToDest() == null ? "" : psi.getToDest()) + "旅游 特价线路5折起! 驴妈妈旅游网\",\"idea_desc\": \"" + (psi.getFromDest() == null ? "" : psi.getFromDest()) + "到" +  (psi.getToDest() == null ? "" : psi.getToDest()) + "旅游,热门线路,当季特价5折起,减价不减质! 驴妈妈带您畅游天下,马上出发!\"}],");
			if (null == psi.getTravelTime()) {
				sb.append("\"line_date\": {\"data_list\": []},");
			}
			if (null != psi.getTravelTime()
					&& psi.getTravelTime().indexOf(',') == -1) {
				Calendar cal = Calendar.getInstance();
				StringBuilder sbuilder = new StringBuilder();
				for (int i = 0; i < 8; i++) {
					cal.add(Calendar.DATE, 1);
					sbuilder.append(
							"\""
									+ DateUtil.formatDate(cal.getTime(),
											"yyyy-MM-dd") + "\"").append(",");
				}
				sbuilder.setLength(sbuilder.length() - 1);
				sb.append("\"line_date\": {\"data_list\": ["
						+ sbuilder.toString() + "]},");
			}
			if (null != psi.getTravelTime()
					&& psi.getTravelTime().indexOf(',') != -1) {
				int year = Calendar.getInstance().get(Calendar.YEAR);
				StringBuilder sbuilder = new StringBuilder();

				Date now = new Date(System.currentTimeMillis());
				StringTokenizer stoken = new StringTokenizer(
						psi.getTravelTime(), ",");
				while (stoken.hasMoreTokens()) {
					String token = stoken.nextToken();
					Date travelTime = DateUtil.getDateByStr(year + "/" + token,
							"yyyy/MM/dd");
					if (null == travelTime) {
						continue;
					}
					if (travelTime.before(now)) {
						sbuilder.append(
								"\""
										+ DateUtil.formatDate(DateUtil
												.getDateByStr((year + 1) + "/"
														+ token, "yyyy/MM/dd"),
												"yyyy-MM-dd") + "\"").append(
								",");
					} else {
						sbuilder.append(
								"\""
										+ DateUtil.formatDate(travelTime,
												"yyyy-MM-dd") + "\"").append(
								",");
					}
				}

				sbuilder.setLength(sbuilder.length() - 1);
				sb.append("\"line_date\": {\"data_list\": ["
						+ sbuilder.toString() + "]},");
			}

			sb.append("\"line_price\": \""
					+ (psi.getSellPrice() == null ? "" : new DecimalFormat("#.00").format((psi.getSellPrice() / 100.0)))
					+ "\",");
			sb.append("\"vehicle_go\": \"\",");
			sb.append("\"vehicle_back\": \"\",");
			sb.append("\"travel_day_total\": \"" + (psi.getVisitDay()  == null ? "" : psi.getVisitDay()) + "\",");
			sb.append("\"travel_day_hotel\": \"" + (psi.getVisitDay() == null ? "" : psi.getVisitDay()) + "\",");
			//sb.append("\"hotel_level\": \"\",");
			sb.append("\"has_shopping\": \"0\",");
			// "has_extra_cost": "0|1【可选，没有就不传，0 没有，1 有 自费景点】",
			sb.append("\"line_page\": \"http://www.lvmama.com/product/"
					+ psi.getProductId() + "\",");
			sb.append("\"citys\": {");
			sb.append("\"start\": \"" + (psi.getFromDest() == null ? "" : psi.getFromDest()) + "\",");
			sb.append("\"end\": [{");
			sb.append("\"city_name\": \"" + (psi.getToDest() == null ? "" : psi.getToDest()) + "\",");
			sb.append("\"city_spots\": [");
//			StringBuilder sbuilder = new StringBuilder();
//			if (StringUtils.isNotEmpty(psi.getProductAlltoPlace())) {
//				StringTokenizer stoken = new StringTokenizer(
//						psi.getProductAlltoPlace(), ",");
//				while (stoken.hasMoreTokens()) {
//					String token = stoken.nextToken();
//					if (token.indexOf("~") == -1) {
//						sbuilder.append("\"" + token + "\",");
//					} else {
//						sbuilder.append("\"" + token.subSequence(0, token.indexOf("~")) + "\",");
//					}
//					
//				}
//			} else {
//				sbuilder.append(",");
//			}
//			sbuilder.setLength(sbuilder.length() - 1);
//			sb.append(sbuilder.toString());

			sb.append("]");
			sb.append("}");
			sb.append("]");
			sb.append("}");
			//sb.append("}");
			sb.append("}\n");
		}
	}
}

class MappingPlanAndPrice {
	private String plan;
	private String price;
	
	public MappingPlanAndPrice(String plan, String price) {
		this.plan = plan;
		this.price = price;
	}
	
	public String getPlan() {
		return plan;
	}
	public String getPrice() {
		return price;
	}
}


