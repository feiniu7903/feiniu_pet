package com.lvmama.businessreply.utils.etao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.lvmama.businessreply.po.EtaoProduct;
import com.lvmama.comm.vo.Constant;


/**
 * 一淘团购构造XML类.
 * 
 * @author huyunyan
 * @see com.lvmama.group.service.IGroupBuyService
 */
public class GroupBuyEtaoBuild {
	/**
	 * 创建XML文档.
	 * 
	 * @see com.lvmama.group.service.IGroupBuyService#BuildXML(java.util.List)
	 */
	public static void buildXML(List<EtaoProduct> groupBuyInfoList, String xmlPath) {
		String filepath = XMLBuilderUtil.inintTuanGouBasePath(xmlPath, Constant.getInstance().getProperty("GROUPBUY_ETAO_FILENAME"));
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		StringBuilder data = new StringBuilder();
		data.append(XMLBuilderUtil.buildElement("site_name", Constant.getInstance().getProperty("LVMAMA_SITE_NAME")));
		data.append(XMLBuilderUtil.buildElement("site_url", Constant.getInstance().getProperty("LVMAMA_SITE_TUANGOU_URL")));
		StringBuilder itemsdata = new StringBuilder();
		int i = 0;
		for (EtaoProduct groupBuyInfo : groupBuyInfoList) {
			itemsdata.append("<items id=\"" + (++i) + "\">");
			itemsdata.append(buildItem(groupBuyInfo));
			itemsdata.append("</items>");
		}
		data.append(XMLBuilderUtil.buildElement("itemsdata", itemsdata.toString()));
		xmlBuilder.append(XMLBuilderUtil.buildElement("data", data.toString()));
		XMLBuilderUtil.saveXML(filepath, xmlBuilder.toString());
	}

	/**
	 * 添加一个产品信息到XML DOC.
	 * 
	 * @param groupBuyInfo
	 */
	private static String buildItem(EtaoProduct groupBuyInfo) {
		StringBuilder builder = new StringBuilder();
		builder.append(XMLBuilderUtil.buildElement("g_city_name", groupBuyInfo.getCity()));
		builder.append(XMLBuilderUtil.buildElement("title", groupBuyInfo.getProductName()));
		builder.append(XMLBuilderUtil.buildElement("href", Constant.getInstance().getProperty("LVMAMA_SITE_PRODUCT_DIR") + groupBuyInfo.getProductId()));
		builder.append(XMLBuilderUtil.buildElement("g_class", Constant.getInstance().getProperty("GROUPBUY_ETAO_GCLASS")));
		builder.append(XMLBuilderUtil.buildElement("tags", XMLBuilderUtil.getTags(groupBuyInfo.getTags(), "/")));
		builder.append(XMLBuilderUtil.buildElement("start", new SimpleDateFormat("yyyyMMddHHmmss").format(groupBuyInfo.getOnlineTime())));
		builder.append(XMLBuilderUtil.buildElement("end", new SimpleDateFormat("yyyyMMddHHmmss").format(groupBuyInfo.getOfflineTime())));
		builder.append(XMLBuilderUtil.buildElement("price", new DecimalFormat("0.00").format(groupBuyInfo.getSellPrice())));
		builder.append(XMLBuilderUtil.buildElement("g_price", new DecimalFormat("0.00").format(groupBuyInfo.getMarketPrice())));
		builder.append(XMLBuilderUtil.buildElement("g_rate", new DecimalFormat("0.0").format(groupBuyInfo.getDisCount())));
		builder.append(XMLBuilderUtil.buildElement("sales", groupBuyInfo.getSalesNum().toString()));
		builder.append(XMLBuilderUtil.buildElement("image", groupBuyInfo.getPictureUrl()));
		builder.append(XMLBuilderUtil.buildElement("desc", groupBuyInfo.getRecommandInfo()));
		builder.append(XMLBuilderUtil.buildElement("showcase",  Constant.getInstance().getProperty("ETAO_SHOWCASE")));// 选填,但是要求是true/false
																								// TODO
		builder.append(XMLBuilderUtil.buildElement("g_merchant_name", Constant.getInstance().getProperty("MERCHANT_NAME")));
		builder.append(XMLBuilderUtil.buildElement("g_merchant_tel", Constant.getInstance().getProperty("MERCHANT_TEL")));
		builder.append(XMLBuilderUtil.buildElement("g_merchant_addr", Constant.getInstance().getProperty("MERCHANT_ADDR")));
		builder.append(XMLBuilderUtil.buildElement("g_district", Constant.getInstance().getProperty("MERCHANT_DISTRICT")));
		builder.append(XMLBuilderUtil.buildElement("g_spend_start_time", new SimpleDateFormat("yyyyMMddHHmmss").format(groupBuyInfo.getGroupBuyStartTime())));
		builder.append(XMLBuilderUtil.buildElement("g_spend_close_time", new SimpleDateFormat("yyyyMMddHHmmss").format(groupBuyInfo.getGroupBuyCloseTime())));
		builder.append(XMLBuilderUtil.buildElement("g_lbsid", Constant.getInstance().getProperty("STRING_EMPTY")));
		builder.append(XMLBuilderUtil.buildElement("g_longitude", Constant.getInstance().getProperty("STRING_EMPTY")));
		builder.append(XMLBuilderUtil.buildElement("g_latitude", Constant.getInstance().getProperty("STRING_EMPTY")));
		return builder.toString();
	}
}
