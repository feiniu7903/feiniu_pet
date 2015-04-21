package com.lvmama.businessreply.utils.ruichuang;

import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;

import com.lvmama.businessreply.utils.etao.XMLBuilderUtil;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 瑞创xml 结构
 * 
 * @author liukang
 * 
 */
public final class Ruichuang {
	public static String buildXMLForRuiChuang(final List<ProductSearchInfo> results) {
		StringBuilder xmlBuilder = new StringBuilder();// 建立xml
		if (results != null && results.size() > 0) {
			for (ProductSearchInfo productSearchInfo : results) {
				xmlBuilder.append(buildProdListData(productSearchInfo)); // 逐个生成产品详细信息文件
			}
		}
		return addHeadTail(xmlBuilder).toString();
	}
	
	private static String addHeadTail(final StringBuilder sb) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
		stringBuilder.append("<urlset>\r\n");
		
		stringBuilder.append("<loc>http://www.lvmama.com/</loc>\r\n");
		stringBuilder.append("<source>驴妈妈旅行网 </source>\r\n");
		stringBuilder.append("<lastmod>" + DateUtil.formatDate(new Date(), "yyyy-MM-dd") + "</lastmod>\r\n");
		stringBuilder.append(sb);
		stringBuilder.append("</urlset>\r\n");
		return stringBuilder.toString();
	}


	
	private static String getIsGroup(final String subProductType) {
		if (Constant.SUB_PRODUCT_TYPE.GROUP.name().equals(subProductType) 
				|| Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(subProductType) 
				|| Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(subProductType)) {
			return "1";
		}
		if (Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(subProductType) 
				|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(subProductType) 
				|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(subProductType) 
				|| Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(subProductType)) {
			return "2";
		}
		return "3";
	}	

	/**
	 * 生成中间xml信息
	 * 
	 * @param productSearchInfo
	 * @return
	 */
	private static String buildProdListData(final ProductSearchInfo productSearchInfo) {
		StringBuilder item = new StringBuilder();
		item.append("<data>\r\n");
		item.append("<display>\r\n");
		//
		item.append(XMLBuilderUtil.buildElement("title", "<![CDATA[" + productSearchInfo.getProductName() + "]]>"));
		// 跟团游，自助游
		item.append(XMLBuilderUtil.buildElement("isgroup", getIsGroup(productSearchInfo.getSubProductType())));
		// 周边，国内，境外
		item.append(XMLBuilderUtil.buildElement("linetype", ""));
		// 旅游主题名称
		item.append(XMLBuilderUtil.buildElement("linetheme", productSearchInfo.getProdRouteTypeName()));
		// 出发城市
		item.append(XMLBuilderUtil.buildElement("from", productSearchInfo.getFromDest()));
		// 景点所属城市
		item.append(XMLBuilderUtil.buildElement("to", productSearchInfo.getToDest()));
				
		// 景点名称
		item.append(XMLBuilderUtil.buildElement("travelname", ""));
		// 最早出行日期 -最晚出行日期
//		if (null != productSearchInfo.getTravelTime()) {
//			if (productSearchInfo.getTravelTime().indexOf(",") != -1) {
//				String travelTime = productSearchInfo.getTravelTime();
//				item.append(XMLBuilderUtil.buildElement("date", travelTime.substring(0, travelTime.indexOf(",")) + "~" + ));
//			} else {
//				item.append(XMLBuilderUtil.buildElement("date", productSearchInfo.getTravelTime()));
//			}
//		} else {
//			item.append(XMLBuilderUtil.buildElement("date", ""));
//		}
		item.append(XMLBuilderUtil.buildElement("date", productSearchInfo.getTravelTime()));
		
		// 交通
		item.append(XMLBuilderUtil.buildElement("traffic", ""));
		// 酒店
		item.append(XMLBuilderUtil.buildElement("hotel", ""));
		// 满意度
		item.append(XMLBuilderUtil.buildElement("satisfaction", ""));
		// 几日游
		item.append(XMLBuilderUtil.buildElement("days", productSearchInfo.getVisitDay()));
		// 最低价格
		item.append(XMLBuilderUtil.buildElement("price", "" + productSearchInfo.getSellPriceInteger()));
		// 产品路径
		item.append(XMLBuilderUtil.buildElement("url", "http://www.lvmama.com" + productSearchInfo.getProductUrl() + "?losc=030509"));
		// 【基本介绍】 [旅游线路的简短介绍80~120字] ]
		item.append(XMLBuilderUtil.buildElement("description", "<![CDATA[" + StringUtil.subStringStr(productSearchInfo.getRecommendReason(), 120) + "]]>"));
		// 订购数量ordernum
		item.append(XMLBuilderUtil.buildElement("ordernum", "" + productSearchInfo.getOrderQuantitySum()));
		// 图片地址(图片大小160px * 120px)] ]
		item.append(XMLBuilderUtil.buildElement("img",productSearchInfo.getSmallImageUrl()));
		item.append("</display>\r\n");
		item.append("</data>\r\n");
		return item.toString();

	}

//	/**
//	 * 生成xml 尾部
//	 * 
//	 * @return
//	 */
//	private static String buildXmlTail() {
//		StringBuilder xmlBuilder = new StringBuilder();
//		xmlBuilder.append("</urlset>");
//		return xmlBuilder.toString();
//	}

}
