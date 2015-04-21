package com.lvmama.search.lucene.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LuceneCommonDic;

/**
 * documet实现类
 * 
 * @author yuzhibing
 * 
 */
public class PlaceDocument extends AbstactDocument {
	/** id */
	public static String ID = "id";
	/** 景区长id */
	public static String PLACE_ID = "placeId";
	/** 名称 */
	public static String NAME = "name";// 前缀分词
	/**英文名称*/
	public static String EN_NAME ="enName";
	/** 简介 */
	public static String SUMMARY = "summary";
	/** 最低价 */
	public static String PRODUCTS_PRICE = "productsPrice";
	/** 最高价 */
	public static String MAX_PRODUCTS_PRICE = "maxProductsPrice";
	/** 平均分 */
	public static String AVG_SCORE = "avgScore";
	/** 点评数 */
	public static String CMT_NUM = "cmtNum";
	/** 可卖产品数 */
	public static String PRODUCTS_TYPE = "productsType";
	/** 攻略数 */
	public static String GONG_LUE_NUM = "gonglueNum";
	/** 销售优惠 */
	public static String SALE_FAVOURABLE = "saleFavourable";
	/** 内容 */
	public static String CONTENT = "content";// content分词
	/** 权重 */
	public static String BOOST = "boost";
	/** 图片 */
	public static String IMAGE_URL = "imageUrl";
	/** 省份 */
	public static String PROVINCE = "province";
	/** 城市 */
	public static String CITY = "city";
	/** 主题 */
	public static String TOPIC = "topic";
	/** 折扣 */
	public static String AGIO = "agio";
	/** 支付方式 */
	public static String PAY_METHOD = "payMethod";
	/** 优惠方式 */
	public static String COUPON = "coupon";
	/** 是否推荐景区 */
	public static String IS_RECOMMEND = "isRecommend";
	/** 城市编号 */
	public static String CITY_ID = "cityId";
	/** 省份编号 */
	public static String PROVINCE_ID = "provinceId";
	/** 景区图片 */
	public static String SMALL_IMAGE = "smallImage";
	/** 产品总数 */
	public static String PRODUCT_NUM = "productNum";
	/** 门票总数 */
	public static String TICKET_NUM = "ticketNum";
	/** 酒店总数 */
	public static String HOTLE_NUM = "hotleNum";
	/** 自由行总数 */
	public static String FREENESS_NUM = "freenessNum";
	/** 线路总数 */
	public static String ROUTE_NUM = "routeNum";
	public static String QUERY_NAME = "queryName";// name分词
	public static String STAGE = "stage";
	/** 拼音url */
	public static String PIN_YIN_URL = "pinYinUrl";
	/** 省拼音url */
	public static String PROVINCE_PIN_YIN_URL = "provincePinYinUrl";
	/** 市拼音url */
	public static String CITY_PIN_YIN_URL = "cityPinYinUrl";
	/****/
	public static String ADDRESS = "address";
	/****/
	public static String PRICE = "price";
	/****/
	public static String SEQ = "seq";
	/****/
	public static String SHORT_ID = "shortId";
	/**
	 * 所有的周边景区的名称的集合,分割；
	 */
	public static String ROUND_PLACE_NAME = "roundPlaceName";
	/****/
	public static String PIN_YIN = "pinYin";
	/****/
	public static String HAS_HOTEL = "hasHotel";
	/****/
	private final static String DELIM = ",";
	/** 主题 **/
	public static String DEST_SUBJECTS = "destSubjects";
	/** 标签名称 **/
	public static String DEST_TAGS_NAME = "destTagsName";
	/** 标签描述文 **/
	public static String DEST_TAGS_DESCRIPT = "destTagsDescript";
	/** 标签小组名称 **/
	public static String DEST_TAGS_GROUP = "destTagsGroup";
	/** 标签样式**/
	public static String DEST_TAGS_CSS = "destTagsCss";
	
	/** 标签图片号 **/
	public static String DEST_TAGS_IMAGE = "destTagsImage";
	/** 自由自在产品数量 **/
	public static String DEST_FREENESS_NUM = "destFreeneeeNum";
	/** 周边游产品数量 **/
	public static String DEST_PERIPHERY_NUM = "destPeripheryNum";
	/** 境外游产品数量 **/
	public static String DEST_ABROAD_NUM = "destAbroadNum";
	/** 国内游产品数量 **/
	public static String DEST_INTERNAL_NUM = "destInternalNum";
	/** 国内游产品数量 **/
	public static String LONGITUDE = "longitude";
	/** 国内游产品数量 **/
	public static String LATITUDE = "latitude";
	/** 国内游产品数量 **/
	public static String DEST_ID = "destId";
	/** 国内游产品数量 **/
	public static String IS_CLIENT = "isClient";
	/** 国内游产品数量 **/
	public static String PLACE_TYPE = "placeType";
	/** 中图链接 **/
	public static String MIDDLE_IMAGE = "middleImage";
	/** 市场价格 **/
	public static String MARKET_PRICE = "marketPrice";
	/** 销售价格,排序用 **/
	public static String SELL_PRICE = "sellPrice";
	/** 销售价格,筛选用 **/
	public static String SELL_PRICE_RANG = "sellPriceRange";
	/** PLACE_MAIN_TITLE **/
	public static String PLACE_MAIN_TITLE = "placeMainTitel";
	/** PLACE_TITLE **/
	public static String PLACE_TITLE = "placeTitel";
	/** DEST_PERSENT_STR **/
	public static String DEST_PERSENT_STR = "destPresentStr";
	/** DEST_NAME_IDS **/
	public static String DEST_NAME_IDS = "destNameIds";
	/** 高频率关键字,开发给运营部门增加关键字绑定标地搜索 **/
	public static String HFKW = "hfkw";
	/** 一周销售额 **/
	public static String WEEK_SALES = "weekSales";
	/** 景点活动 **/
	public static String PLACE_ACTIVITY = "placeActivity";	
	/** 景点活动 **/
	public static String PLACE_ACTIVITY_NOTOKEN = "placeActivitynotoken";	
	/** 是否包含景点活动 **/
	public static String PLACE_ACTIVITY_HAVE = "placeActivityHave";
	/** 当日可预订 **/
	public static String TODAY_ORDER_ABLE_TIME = "todayOrderAbleTime";
	
	/** 新当日可预订 **/
	public static String TODAY_ORDER_LAST_TIME = "todayOrderLastTime";

	/** 返现金额(单位:元) **/
	public static String CASH_REFUND = "cashRefund";	
	/** 普通排序的socre **/
	public static String NORMALSCORE = "normalScore";	
	/** hbase的关键字 **/
	public static String HBASEKEY = "hbasekey";	
	/** hbase的关键字的score **/
	public static String HBASEKEYSCORE = "hbasekeyscore";	
	
	public static String OWNFIELD="ownfield";
	//lucene系数
	public static String LUCENEFACTOR="lucenefactor";
	
	public static String DEST_SUBJECTS_NOTOKEN="destsubjectsnotoken";
	
	public static String SHAREWEIXIN = "shareweixin";
	
	public PlaceDocument() {
		
	}
	public Document createDocument(Object t) {
		PlaceBean placeBean = (PlaceBean) t;
		Document doc = new Document();
		
		Field id = new StringField(PlaceDocument.ID, placeBean.getId(), Field.Store.YES);
		if (placeBean.getBoost() > 0) {
			id.setBoost(placeBean.getBoost());
		}
		doc.add(id);
		
		/**NAME字段**/
		String nameStr = placeBean.getName();
		//获得中文NAME,只可能为一个
		String name=getChinaWordStr(nameStr);
		
		Field placeName = new StringField(PlaceDocument.QUERY_NAME, name, Field.Store.NO);
		doc.add(placeName);
		//NAME不过滤特殊字符，存入索引第一条，做显示用
//		doc.add(new TextField(PlaceDocument.NAME, name, Field.Store.YES));
		// 特殊字符处理，特殊字符过滤不做索引
		name = CommonUtil.escapeString(name);
//		if (name != null && !"".equals(name)) {
//			for (int i = 0; i < name.length(); i++) {
//				doc.add(new StringField(PlaceDocument.NAME, name.substring(i), Field.Store.YES));
//			}
//			for (int i = 1; i <= name.length(); i++) {
//				doc.add(new StringField(PlaceDocument.NAME, name.substring(0, i), Field.Store.YES));
//			}
//			//含有大小写的字段统一改为小写例如：日本HelloKitty乐园 
//			if (!name.toLowerCase().equals(name)) {
//				name = name.toLowerCase();
//				for (int i = 0; i < name.length(); i++) {
//					doc.add(new StringField(PlaceDocument.NAME, name.substring(i), Field.Store.YES));
//				}
//				for (int i = 1; i <= name.length(); i++) {
//					doc.add(new StringField(PlaceDocument.NAME, name.substring(0, i), Field.Store.YES));
//				}
//			}
//		}
		String stage=placeBean.getStage();
		if("3".equals(stage)){
			doc.add(new StringField(PlaceDocument.NAME, name, Field.Store.YES));
		}
		else{
			doc.add(new TextField(PlaceDocument.NAME, name, Field.Store.YES));
		}
		
		double normalScore=1;
		try {
			normalScore=DocumentUtil.normalScore(placeBean);
		} catch (Exception e1) {
			throw new RuntimeException();
		}
		doc.add(new DoubleField(PlaceDocument.NORMALSCORE, normalScore, Field.Store.YES));
		//存入中值*Lucene系数
		float lucenefactor=DocumentUtil.getLuceneFactor(placeBean);
		doc.add(new FloatField(PlaceDocument.LUCENEFACTOR, lucenefactor, Field.Store.YES));
		
		//打印测试
		doc.add(new StringField("aaaaa","销售比值：" +placeBean.getSalePer()+"-----"+"中值比值：" +placeBean.getMidSalePer()+"-----"+"标签比值：" +placeBean.getTagratio(), Field.Store.YES));
		
		//加入默认字段
		doc.add(new TextField(PlaceDocument.OWNFIELD,LuceneCommonDic.OWNFIELD,Field.Store.YES));

		doc.add(new StringField(PlaceDocument.EN_NAME, placeBean.getEnName(), Field.Store.YES));
		
		//获得多个拼音,简拼NAME
		String namePinYin=getPinyinWordStr(nameStr);
		StringTokenizer namePinYinToken = new StringTokenizer(namePinYin, DELIM);
		while (namePinYinToken.hasMoreTokens()) {
			String key = namePinYinToken.nextToken();
			doc.add(new StringField(PlaceDocument.NAME, key, Field.Store.YES));
		}
		
		/** 经度**/
		DoubleField longitude = new DoubleField(PlaceDocument.LONGITUDE,placeBean.getLongitude(), Field.Store.YES);
		doc.add(longitude);
		// 纬度
		DoubleField latitude = new DoubleField(PlaceDocument.LATITUDE, placeBean.getLatitude(), Field.Store.YES);
		doc.add(latitude);
		
		FloatField productsPrice = new FloatField(PlaceDocument.PRODUCTS_PRICE,placeBean.getProductsPrice(), Field.Store.YES);
		doc.add(productsPrice);
		/**
		 * SELL_PRICE 同时做排序和筛选用，为排序和筛选各做一个索引字段,对其中空值傅一最大值888888888处理
		 * **/
		doc.add(new StringField(PlaceDocument.SELL_PRICE, placeBean.getSellPrice(), Field.Store.YES));// 排序用

		LongField sellPrice = new LongField(PlaceDocument.SELL_PRICE_RANG,Long.parseLong(placeBean.getSellPrice()), Field.Store.YES);
		doc.add(sellPrice);

		LongField marketPrice = new LongField(PlaceDocument.MARKET_PRICE,placeBean.getMarketPrice(), Field.Store.YES);
		doc.add(marketPrice);

		FloatField maxProductsPrice = new FloatField(PlaceDocument.MAX_PRODUCTS_PRICE, Float.parseFloat(placeBean.getMaxProductsPrice()),Field.Store.YES);
		doc.add(maxProductsPrice);
		float avgScore_val = 0f;
		if (placeBean.getAvgScore() <= 5 && placeBean.getAvgScore() > 0) {
			avgScore_val = placeBean.getAvgScore();
		} else if (placeBean.getAvgScore() > 5) {
			avgScore_val = 5.0f;
		} else if (placeBean.getAvgScore() <= 0) {// 按郑致力要求为0则设为4星
			avgScore_val = 4.0f;
		}
		FloatField avgScore = new FloatField(PlaceDocument.AVG_SCORE,avgScore_val, Field.Store.YES);
		doc.add(avgScore);

		IntField cmtNum = new IntField(PlaceDocument.CMT_NUM, placeBean.getCmtNum(),Field.Store.YES);
		doc.add(cmtNum);
		
		doc.add(new StringField(PlaceDocument.PROVINCE, placeBean.getProvince(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.SALE_FAVOURABLE, placeBean.getSaleFavourable(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.PLACE_MAIN_TITLE, placeBean.getPlaceMainTitel(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.PLACE_TITLE, placeBean.getPlaceTitel(), Field.Store.YES));
		if("3".equals(stage)){
			doc.add(new StringField(PlaceDocument.SUMMARY, placeBean.getSummary(), Field.Store.YES));
		}
		else{
			doc.add(new TextField(PlaceDocument.SUMMARY, placeBean.getSummary(), Field.Store.YES));
		}
		if("3".equals(stage)){
			doc.add(new StringField(PlaceDocument.PLACE_ACTIVITY,placeBean.getPlaceActivity(), Field.Store.YES));
		}
		else{
			doc.add(new TextField(PlaceDocument.PLACE_ACTIVITY,placeBean.getPlaceActivity(), Field.Store.YES));
			doc.add(new StringField(PlaceDocument.PLACE_ACTIVITY_NOTOKEN,placeBean.getPlaceActivity(), Field.Store.YES));
			
		}
		doc.add(new StringField(PlaceDocument.PLACE_ACTIVITY_HAVE,placeBean.getPlaceActivityHave()+"", Field.Store.YES));
		Date todayOrderAbleTime = placeBean.getTodayOrderAbleTime();
		doc.add(new StringField(PlaceDocument.TODAY_ORDER_ABLE_TIME, todayOrderAbleTime == null ? "" :todayOrderAbleTime.getTime()+"", Field.Store.YES));
		Date todayOrderLastTime = placeBean.getTodayOrderLastTime();
		doc.add(new StringField(PlaceDocument.TODAY_ORDER_LAST_TIME, todayOrderLastTime == null ? "" :todayOrderLastTime.getTime()+"", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.CASH_REFUND, placeBean.getCashRefund() , Field.Store.YES));
		doc.add(new StringField(PlaceDocument.CITY_ID, placeBean.getCityId(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.PROVINCE_ID, placeBean.getProvinceId(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.PRODUCT_NUM, placeBean.getProductNum() + "", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.ROUTE_NUM, placeBean.getRouteNum() + "", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.DEST_ABROAD_NUM, placeBean.getRouteNum() + "", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.DEST_INTERNAL_NUM, placeBean.getRouteNum() + "", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.DEST_PERIPHERY_NUM, placeBean.getRouteNum() + "", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.SMALL_IMAGE, placeBean.getSmallImage(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.MIDDLE_IMAGE, placeBean.getMiddleImage(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.STAGE, placeBean.getStage(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.PIN_YIN_URL, placeBean.getPinYinUrl(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.PROVINCE_PIN_YIN_URL, placeBean.getProvincePinYinUrl(), Field.Store.YES));
		doc.add(new StringField(PlaceDocument.CITY_PIN_YIN_URL, placeBean.getCityPinYinUrl(), Field.Store.YES));
		if("3".equals(stage)){
			doc.add(new StringField(PlaceDocument.ADDRESS, placeBean.getAddress() + "", Field.Store.YES));
		}
		else{
			doc.add(new TextField(PlaceDocument.ADDRESS, placeBean.getAddress() + "", Field.Store.YES));
		}
		doc.add(new StringField(PlaceDocument.SHORT_ID, placeBean.getShortId() + "", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.DEST_ID, placeBean.getDestId() + "", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.IS_CLIENT, placeBean.getIsClient() + "", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.PLACE_TYPE, placeBean.getPlaceType() + "", Field.Store.YES));
		doc.add(new StringField(PlaceDocument.SHAREWEIXIN, placeBean.getShareweixin() + "", Field.Store.YES));
		Long seq_val = null;
		
		try {
			seq_val = Long.parseLong(placeBean.getSeq());
		} catch (NumberFormatException e) {
			seq_val = 0l;
		}
		LongField seq = new LongField(PlaceDocument.SEQ, seq_val,Field.Store.YES);
		doc.add(seq);

		String price = placeBean.getPrice();
		if (price != null && !"".equals(price)) {
			StringTokenizer st = new StringTokenizer(price, DELIM);
			while (st.hasMoreTokens()) {
				doc.add(new FloatField(PlaceDocument.PRICE, Float.parseFloat(st.nextToken()),Field.Store.YES));
			}
		} else {
			doc.add(new FloatField(PlaceDocument.PRICE, 0f,Field.Store.YES));
		}

		LongField ticketNum = new LongField(PlaceDocument.TICKET_NUM,placeBean.getTicketNum(), Field.Store.YES);
		doc.add(ticketNum);
		
		LongField weekSalseNum = new LongField(PlaceDocument.WEEK_SALES,placeBean.getWeekSales(), Field.Store.YES);
		doc.add(weekSalseNum);

		LongField hotelNum = new LongField(PlaceDocument.HOTLE_NUM, placeBean.getHotleNum(), Field.Store.YES);
		doc.add(hotelNum);

		doc.add(new LongField(PlaceDocument.FREENESS_NUM, placeBean.getFreenessNum(),Field.Store.YES));

		doc.add(new LongField(PlaceDocument.DEST_FREENESS_NUM,placeBean.getDestFreenessNum(), Field.Store.YES));

		/** tagsName,字段中数据按分隔符","分词 **/
		String tagsNameStr = placeBean.getDestTagsName();
		String tagsName = getChinaWordStr(tagsNameStr)+","+getPinyinWordStr(tagsNameStr);
		if (tagsName != null && !",".equals(tagsName)&&!" ".equals(tagsName)) {
			StringTokenizer st = new StringTokenizer(tagsName, DELIM);
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				if("3".equals(stage)){
					doc.add(new StringField(PlaceDocument.DEST_TAGS_NAME, key, Field.Store.YES));
				}
				else{
					doc.add(new TextField(PlaceDocument.DEST_TAGS_NAME, key, Field.Store.YES));
				}
				
			}
		} else {
			if("3".equals(stage)){
				doc.add(new StringField(PlaceDocument.DEST_TAGS_NAME, "", Field.Store.YES));
			}
			else{
				doc.add(new TextField(PlaceDocument.DEST_TAGS_NAME, "", Field.Store.YES));
			}
			
		}
		doc.add(new TextField(ProductDocument.TAGS_NAME_ORI, tagsNameStr, Field.Store.YES));
		/**标签说明文**/
		String tagsDescriptStr = placeBean.getDestTagsDescript();
		if (tagsDescriptStr!= null && !"".equals(tagsDescriptStr)){
			 doc.add(new StringField(PlaceDocument.DEST_TAGS_DESCRIPT, tagsDescriptStr, Field.Store.YES));
		} else {
				 doc.add(new StringField(PlaceDocument.DEST_TAGS_DESCRIPT, "", Field.Store.YES));
		}
		
		/**标签小组名称**/
		String tagsGroupStr = placeBean.getDestTagsGroup();
		if (tagsGroupStr!= null && !"".equals(tagsGroupStr)){
			 doc.add(new StringField(PlaceDocument.DEST_TAGS_GROUP, tagsGroupStr, Field.Store.YES));
		} else {
				 doc.add(new StringField(PlaceDocument.DEST_TAGS_GROUP, "", Field.Store.YES));
		}
		/**标签样式名称**/
		String tagsCssStr = placeBean.getDestTagsCss();
		if (tagsCssStr!= null && !"".equals(tagsCssStr)){
			doc.add(new StringField(PlaceDocument.DEST_TAGS_CSS, tagsCssStr, Field.Store.YES));
		} else {
				 doc.add(new StringField(PlaceDocument.DEST_TAGS_CSS, "", Field.Store.YES));
		}
		
		
		/** destSubjects,字段中数据按分隔符","分词 **/
		String destSubjectsStr = placeBean.getDestSubjects();
		String destSubjects = getChinaWordStr(destSubjectsStr)+","+getPinyinWordStr(destSubjectsStr);
		if (destSubjects != null && !"".equals(destSubjects)&& !",".equals(destSubjects)) {
			StringTokenizer st = new StringTokenizer(destSubjects, DELIM);
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				
				if("3".equals(stage)){
					doc.add(new StringField(PlaceDocument.DEST_SUBJECTS, key, Field.Store.YES));
				}
				else{
					doc.add(new TextField(PlaceDocument.DEST_SUBJECTS, key, Field.Store.YES));
					doc.add(new StringField(PlaceDocument.DEST_SUBJECTS_NOTOKEN, key, Field.Store.YES));
				}
			}
			// 解决destSubjects不为空，但是为"," 的BUG!
			if (destSubjects.equals(",")) {
				if("3".equals(stage)){
					doc.add(new StringField(PlaceDocument.DEST_SUBJECTS, "", Field.Store.YES));
				}
				else{
					doc.add(new TextField(PlaceDocument.DEST_SUBJECTS, "", Field.Store.YES));
					doc.add(new StringField(PlaceDocument.DEST_SUBJECTS_NOTOKEN, "", Field.Store.YES));
				}
			}

		} else {
			if("3".equals(stage)){
				doc.add(new StringField(PlaceDocument.DEST_SUBJECTS, "", Field.Store.YES));
			}
			else{
				doc.add(new TextField(PlaceDocument.DEST_SUBJECTS, "", Field.Store.YES));
				doc.add(new StringField(PlaceDocument.DEST_SUBJECTS_NOTOKEN, "", Field.Store.YES));
			}
		}

		/** destPresentStr,字段中数据按分隔符","分词 **/
		String destPresentStrStr = placeBean.getDestPresentStr();
		String destPresentStr = getChinaWordStr(destPresentStrStr)+","+getPinyinWordStr(destPresentStrStr);
		if (destPresentStr != null && !"".equals(destPresentStr)&& !",".equals(destPresentStr)) {
			StringTokenizer st = new StringTokenizer(destPresentStr, DELIM);
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				key = CommonUtil.escapeString(key); // 特殊字符去掉不进入搜索
				if (!"中国".equals(key) && !"亚洲".equals(key)) {//过滤掉中国，亚洲
					if("3".equals(stage)){
						doc.add(new StringField(PlaceDocument.DEST_PERSENT_STR, key, Field.Store.YES));
					}
					else{
						doc.add(new TextField(PlaceDocument.DEST_PERSENT_STR, key, Field.Store.YES));
					}
				}
			}
			// 解决destPresentStr不为空，但是为"," 的BUG!
			if (destPresentStr.equals(",")) {
				if("3".equals(stage)){
					doc.add(new StringField(PlaceDocument.DEST_PERSENT_STR, ",", Field.Store.YES));
				}
				else{
					doc.add(new TextField(PlaceDocument.DEST_PERSENT_STR, ",", Field.Store.YES));
				}
			}

		} else {
			if("3".equals(stage)){
				doc.add(new StringField(PlaceDocument.DEST_PERSENT_STR, "", Field.Store.YES));
			}
			else{
				doc.add(new TextField(PlaceDocument.DEST_PERSENT_STR, "", Field.Store.YES));
			}
		}
		
		/** destNameIds,字段中数据按分隔符","分词 **/
		String destNameIdsStrStr = placeBean.getDestNameIds();
		String destNameIdsStr = getChinaWordStr(destNameIdsStrStr)+","+getPinyinWordStr(destNameIdsStrStr);
		if (destNameIdsStr != null && !"".equals(destNameIdsStr)&& !",".equals(destNameIdsStr)) {
			StringTokenizer st = new StringTokenizer(destNameIdsStr, DELIM);
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				key = CommonUtil.escapeString(key); // 特殊字符去掉不进入搜索
				if (!"中国".equals(key) && !"亚洲".equals(key)) {//过滤掉中国，亚洲
					doc.add(new StringField(PlaceDocument.DEST_NAME_IDS, key, Field.Store.YES));
				}
			}
			// 解决destNameIds不为空，但是为"," 的BUG!
			if (destNameIdsStr.equals(",")) {
				doc.add(new StringField(PlaceDocument.DEST_NAME_IDS, ",", Field.Store.YES));
			}

		} else {
			doc.add(new StringField(PlaceDocument.DEST_NAME_IDS, "", Field.Store.YES));
		}

		/** 高频关键字HFKW,字段中数据按分隔符","分词 **/
		String HFKWStr = placeBean.getHfkw();
		String HFKW = getChinaWordStr(HFKWStr)+","+getPinyinWordStr(HFKWStr);
		if (HFKW != null && !"".equals(HFKW)&& !",".equals(HFKW)) {
			StringTokenizer st = new StringTokenizer(HFKW, DELIM);
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				key = CommonUtil.escapeString(key); // 特殊字符去掉不进入搜索
				if("3".equals(stage)){
					doc.add(new StringField(PlaceDocument.HFKW, key, Field.Store.YES));
				}
				else{
					doc.add(new TextField(PlaceDocument.HFKW, key, Field.Store.YES));
				}
			}
			// 解决destPresentStr不为空，但是为"," 的BUG!
			if (HFKW.equals(",")) {
				if("3".equals(stage)){
					doc.add(new StringField(PlaceDocument.HFKW, ",", Field.Store.YES));
				}
				else{
					doc.add(new TextField(PlaceDocument.HFKW, ",", Field.Store.YES));
				}
			}

		} else {
			if("3".equals(stage)){
				doc.add(new StringField(PlaceDocument.HFKW, "", Field.Store.YES));
			}
			else{
				doc.add(new TextField(PlaceDocument.HFKW, "", Field.Store.YES));
			}
		}
		
		/** CITY,字段中数据按分隔符","分词  , cityId也存入这个字段了**/
		String city = placeBean.getCity();
		/** 先把用逗号分隔开的地标存入field[0]，便于整个取出页面展示，不用于搜索,也不去除特殊字符 **/
		doc.add(new StringField(PlaceDocument.CITY, city, Field.Store.YES));
		/** 根据逗号分词存入地标，做搜索匹配用 **/
		if (city != null && !"".equals(city)) {
			StringTokenizer st = new StringTokenizer(city, DELIM);
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				key = CommonUtil.escapeString(key); // 特殊字符去掉不进入搜索
				doc.add(new StringField(PlaceDocument.CITY, key, Field.Store.YES));
			}
			// 解决CITY不为空，但是为"," 的BUG!
			if (city.equals(",")) {
				doc.add(new StringField(PlaceDocument.CITY, ",", Field.Store.YES));
			}

		} else {
			doc.add(new StringField(PlaceDocument.CITY, "", Field.Store.YES));
		}

		/** roundPlaceName,字段中数据按分隔符","分词 **/
		String roundPlaceName = placeBean.getRoundPlaceName();
		/** 先把用逗号分隔开的地标存入field[0]，便于整个取出页面展示，不用于搜索,也不去除特殊字符 **/
		if("3".equals(stage)){
			doc.add(new StringField(PlaceDocument.ROUND_PLACE_NAME, roundPlaceName, Field.Store.YES));
		}
		else{
			doc.add(new TextField(PlaceDocument.ROUND_PLACE_NAME, roundPlaceName, Field.Store.YES));
		}
		/** 根据逗号分词存入地标，做搜索匹配用 **/
		if (roundPlaceName != null && !"".equals(roundPlaceName)) {
			StringTokenizer st = new StringTokenizer(roundPlaceName, DELIM);
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				key = CommonUtil.escapeString(key); // 特殊字符去掉不进入搜索
				if("3".equals(stage)){
					doc.add(new StringField(PlaceDocument.ROUND_PLACE_NAME, key, Field.Store.YES));
				}
				else{
					doc.add(new TextField(PlaceDocument.ROUND_PLACE_NAME, key, Field.Store.YES));
				}
			}
			// 解决roundPlaceName不为空，但是为"," 的BUG!
			if (roundPlaceName.equals(",")) {
				if("3".equals(stage)){
					doc.add(new StringField(PlaceDocument.ROUND_PLACE_NAME, ",", Field.Store.YES));
				}
				else{
					doc.add(new TextField(PlaceDocument.ROUND_PLACE_NAME, ",", Field.Store.YES));
				}
			}
		} else {
			if("3".equals(stage)){
				doc.add(new StringField(PlaceDocument.ROUND_PLACE_NAME, "", Field.Store.YES));
			}
			else{
				doc.add(new TextField(PlaceDocument.ROUND_PLACE_NAME, "", Field.Store.YES));
			}
		}
		doc.add(new StringField(PlaceDocument.PIN_YIN, placeBean.getPinYin(),Field.Store.YES));
		doc.add(new StringField(PlaceDocument.HAS_HOTEL, placeBean.getHasHotel(), Field.Store.YES));
		return doc;
	}
	public Object parseDocument(PlaceBean s,Document doc){
		s.setId(doc.get(PlaceDocument.ID));
//		s.setName(doc.getValues(PlaceDocument.NAME)[0]);
		s.setName(doc.getValues(PlaceDocument.NAME)[0].replaceAll("\\\\\\(", "\\(").replaceAll("\\\\\\)", "\\)"));
		s.setEnName(doc.get(PlaceDocument.EN_NAME));
		s.setAvgScore(Float.parseFloat(doc.get(PlaceDocument.AVG_SCORE)));
		s.setCmtNum(Integer.parseInt(doc.get(PlaceDocument.CMT_NUM)));
		s.setProductsType(doc.get(PlaceDocument.PRODUCTS_TYPE));
		s.setGonglueNum(doc.get(PlaceDocument.GONG_LUE_NUM));
		s.setSummary(doc.get(PlaceDocument.SUMMARY));
		s.setLongitude(Double.parseDouble(doc.get(PlaceDocument.LONGITUDE)));
		s.setLatitude(Double.parseDouble(doc.get(PlaceDocument.LATITUDE)));
		s.setIsClient(doc.get(PlaceDocument.IS_CLIENT));
		s.setPlaceType(doc.get(PlaceDocument.PLACE_TYPE));
		s.setShareweixin(doc.get(PlaceDocument.SHAREWEIXIN));
		s.setDestId(doc.get(PlaceDocument.DEST_ID));
		BigDecimal bigDecimal = new BigDecimal(doc.get(PlaceDocument.PRODUCTS_PRICE));
		s.setProductsPrice(bigDecimal.multiply(new BigDecimal(100)).longValue() / 10000);
		/**
		 * SELL_PRICE 同时做排序和筛选用，为排序和筛选各做一个索引字段,对其中空值傅一最大值888888888处理,展示取第一个索引字段.
		 * **/
		s.setSellPrice(doc.get(PlaceDocument.SELL_PRICE));
		BigDecimal bigDecimal3 = new BigDecimal(doc.get(PlaceDocument.MARKET_PRICE));
		s.setMarketPrice(bigDecimal3.multiply(new BigDecimal(100)).longValue() / 10000);
		s.setMaxProductsPrice(doc.get(PlaceDocument.MAX_PRODUCTS_PRICE));
		s.setSaleFavourable(doc.get(PlaceDocument.SALE_FAVOURABLE));
		s.setPlaceId(doc.get(PlaceDocument.PLACE_ID));
		s.setProvince(doc.get(PlaceDocument.PROVINCE));		
		s.setTopic(doc.get(PlaceDocument.TOPIC));
		String todayOrderAbleTimeStr = doc.get(PlaceDocument.TODAY_ORDER_ABLE_TIME);
		if(StringUtils.isNotEmpty(todayOrderAbleTimeStr)){
			s.setTodayOrderAbleTime(new Date(Long.parseLong(todayOrderAbleTimeStr)));
		}
		String todayOrderLastTimeStr = doc.get(PlaceDocument.TODAY_ORDER_LAST_TIME);
		if(StringUtils.isNotEmpty(todayOrderLastTimeStr)){
			s.setTodayOrderLastTime(new Date(Long.parseLong(todayOrderLastTimeStr)));
		}
		s.setCashRefund(doc.get(PlaceDocument.CASH_REFUND));
		s.setAgio(doc.get(PlaceDocument.AGIO));
		s.setPayMethod(doc.get(PlaceDocument.PAY_METHOD));
		s.setCoupon(doc.get(PlaceDocument.COUPON));
		s.setCityId(doc.get(PlaceDocument.CITY_ID));
		s.setSmallImage(doc.get(PlaceDocument.SMALL_IMAGE));
		s.setMiddleImage(doc.get(PlaceDocument.MIDDLE_IMAGE));
		s.setPlaceMainTitel(doc.get(PlaceDocument.PLACE_MAIN_TITLE));
		s.setPlaceActivity(doc.get(PlaceDocument.PLACE_ACTIVITY));
		s.setPlaceActivityHave(new Integer(doc.get(PlaceDocument.PLACE_ACTIVITY_HAVE)));
		s.setPlaceTitel(doc.get(PlaceDocument.PLACE_TITLE));
		s.setProvinceId(doc.get(PlaceDocument.PROVINCE_ID));
		s.setProductNum(new Long(doc.get(PlaceDocument.PRODUCT_NUM)));
		s.setHotleNum(new Long(doc.get(PlaceDocument.HOTLE_NUM)));
		s.setTicketNum(new Long(doc.get(PlaceDocument.TICKET_NUM)));
		s.setFreenessNum(new Long(doc.get(PlaceDocument.FREENESS_NUM)));
		s.setRouteNum(new Long(doc.get(PlaceDocument.ROUTE_NUM)));
		s.setWeekSales(new Long(doc.get(PlaceDocument.WEEK_SALES)));
		s.setDestAbroadNum(new Long(doc.get(PlaceDocument.DEST_ABROAD_NUM)));
		s.setDestFreenessNum(new Long(doc.get(PlaceDocument.DEST_FREENESS_NUM)));
		s.setDestInternalNum(new Long(doc.get(PlaceDocument.DEST_INTERNAL_NUM)));
		s.setDestPeripheryNum(new Long(doc.get(PlaceDocument.DEST_PERIPHERY_NUM)));
		s.setStage(doc.get(PlaceDocument.STAGE));
		s.setPinYinUrl(doc.get(PlaceDocument.PIN_YIN_URL));
		s.setProvincePinYinUrl(doc.get(PlaceDocument.PROVINCE_PIN_YIN_URL));
		s.setCityPinYinUrl(doc.get(PlaceDocument.CITY_PIN_YIN_URL));
		s.setAddress(doc.get(PlaceDocument.ADDRESS));
		s.setSeq(doc.get(PlaceDocument.SEQ));
		s.setRoundPlaceName(doc.get(PlaceDocument.ROUND_PLACE_NAME));
		s.setPinYin(doc.getValues(PlaceDocument.PIN_YIN)[0].toUpperCase());
		s.setShortId(doc.get(PlaceDocument.SHORT_ID));
		/**城市和ID共用一个字段，需要去掉逗号分隔后的城市ID**/
		String cityStr= doc.getValues(PlaceDocument.CITY)[0];
		if (cityStr.indexOf(",")>0) {
			cityStr=cityStr.substring(0, cityStr.indexOf(","));
		}		
		s.setCity(cityStr);		

		/***/
		if (doc.get(PlaceDocument.DEST_SUBJECTS).isEmpty()) {

			s.setDestSubjects(doc.get(PlaceDocument.DEST_SUBJECTS));

		} else {
			List<String> tmp_list = new ArrayList<String>();
			for (int i = 0; i < doc.getValues(PlaceDocument.DEST_SUBJECTS).length; i++) {
				// 判断是中文
				if (doc.getValues(PlaceDocument.DEST_SUBJECTS)[i].matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					tmp_list.add(doc.getValues(PlaceDocument.DEST_SUBJECTS)[i] + ",");
				}
			}
			s.setDestSubjects(StringUtils.join(tmp_list,","));
		}

		/**标签名称**/
		if (StringUtils.isEmpty(doc.get(PlaceDocument.DEST_TAGS_NAME))) {

			s.setDestTagsName("");
			s.setDestTagsDescript("");
			s.setDestTagsGroup("");
			s.setDestTagsCss("");
			
		} else {
			List<String> tmp_list = new ArrayList<String>();
			for (int i = 0; i < doc.getValues(PlaceDocument.DEST_TAGS_NAME).length; i++) {
				// 判断是中文
				if (doc.getValues(PlaceDocument.DEST_TAGS_NAME)[i].matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					tmp_list.add(doc.getValues(PlaceDocument.DEST_TAGS_NAME)[i]);
				}
			}
			s.setDestTagsName(StringUtils.join(tmp_list,","));
			
			/**标签描述*/
			String tagsDescript = doc.get(PlaceDocument.DEST_TAGS_DESCRIPT);
			s.setDestTagsDescript(tagsDescript);
			List<String> tagsDescriptList = Arrays.asList(tagsDescript.split(","));
			
			/**标签小组名称*/
			String tagsGroup = doc.get(PlaceDocument.DEST_TAGS_GROUP);
			s.setDestTagsGroup(tagsGroup);
			List<String> tagsGroupList = Arrays.asList(tagsGroup.split(","));
			
			/**标签CSS*/
			String tagsCss = doc.get(PlaceDocument.DEST_TAGS_CSS);
			List<String> tagsCssList = Arrays.asList(tagsCss.split(","));
			s.setDestTagsCss(tagsCss);
			
			List<ProdTag> tagList = new ArrayList<ProdTag>();
			Map<String,List<ProdTag>> tagGroupMap =new HashMap<String, List<ProdTag>>();
			
			for(int i = 0 ;i < tmp_list.size() ;i ++){
				String tagName = tmp_list.get(i);
				ProdTag pt = new ProdTag();
				pt.setTagName(tagName);
				String description = tagsDescriptList.get(i);
				if(StringUtils.isNotBlank(description)){
					pt.setDescription(description);
				}
				pt.setCssId(tagsCssList.get(i));
				String tagGroup=tagsGroupList.get(i);
				pt.setTagGroupName(tagGroup);
				List<ProdTag> tagGroupList = tagGroupMap.get(tagGroup);
				if( tagGroupList == null ){
					tagGroupList = new ArrayList<ProdTag>();
					tagGroupMap.put(tagGroup,tagGroupList);
				}
				tagGroupList.add(pt);
				tagList.add(pt);
			}
			s.setTagGroupMap(tagGroupMap);
			s.setTagList(tagList);
			
		}		
		return s;
	}
	public Object parseDocument(Document doc) {
		return this.parseDocument( new PlaceBean(), doc);
	}

	/**
	 * 把一个字符串:{中文A~pinyinA~pyA,中文B~pinyinB~pyB,....}中的中文抽出了转换成格式
	 * :{中文A,中文B...},中文顺序不变,去重复.
	 * @return String
	 */
	public static String getChinaWordStr(String str) {
		// 分隔符全部变成逗号
		StringBuffer stringBuffer = new StringBuffer();
		// 排重用hashmap
		HashMap<String, String> check = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			StringTokenizer kg = new StringTokenizer(st.nextToken(), "~");
			while (kg.hasMoreTokens()) {
				String word = kg.nextToken();
				// 判断是中文：只要不是纯拼音的都是中文
				if (word.matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					if (check.get(word) == null) {// 新的数据,保留
						check.put(word, word);
						stringBuffer.append(word).append(",");
					}
				}
			}

		}
		if (stringBuffer.toString().endsWith(",")) {
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
		}
		return stringBuffer.toString();
	}

	/**
	 * 把一个字符串:{中文A~pinyinA~pyA,中文B~pinyinB~pyB,....}中的全拼,简拼抽出了转换成格式
	 * :{pinyinA,pyA,pinyinB,pyB...},去重复.
	 * @return String
	 */
	public static String getPinyinWordStr(String str) {
		// 分隔符全部变成逗号
		StringBuffer stringBuffer = new StringBuffer();
		// 排重用hashmap
		HashMap<String, String> check = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			StringTokenizer kg = new StringTokenizer(st.nextToken(), "~");
			while (kg.hasMoreTokens()) {
				String word = kg.nextToken();
				// 判断是拼音:只要包含一个汉字的即排除
				if (!word.matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					if (check.get(word) == null) {// 新的数据,保留
						check.put(word, word);
						stringBuffer.append(word).append(",");
					}
				}
			}

		}
		if (stringBuffer.toString().endsWith(",")) {
			stringBuffer.deleteCharAt(stringBuffer.length()-1);
		}
		return stringBuffer.toString();
	}
}
