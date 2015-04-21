package com.lvmama.search.lucene.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LuceneCommonDic;

/**
 * documet实现类
 * 
 * @author yuzhibing
 * 
 */
public class ProductDocument extends AbstactDocument {
	/***/
	public static String PRODUCT_ID = "productId";
	/***/
	public static String PRODUCT_NAME = "productName";
	/***/
	public static String RECOMMEND_REASON = "recommendReason";
	/***/
	public static String MARKET_PRICE = "marketPrice";
	/***/
	public static String START_PRICE = "startPrice";// 销售价格范围
	/***/
	public static String END_PRICE = "endPrice";// 销售价格范围
	/***/
	public static String SELL_PRICE = "sellPrice";// 销售价格范围
	/***/
	public static String SMALL_IMAGE = "smallImage";
	/***/
	public static String FROM_DEST = "fromDest";
	/***/
	public static String TO_DEST = "toDest";
	/** 包含出发的上级标的 */
	public static String FROM_DEST_CONTENT = "fromDestContent";
	/** 包含目的地上级标的 */
	public static String TO_DEST_CONTENT = "toDestContent";
	/***/
	public static String CREATE_TIME = "createTime";
	/***/
	public static String IS_VALID = "isValid";
	/***/
	public static String UPDATE_TIME = "updateTime";
	/** 产品类型 */
	public static String IS_TICKET = "isTicket";
	/** 排序 */
	public static String SEQ = "seq";
	/***/
	public static String IS_HID = "isHid";
	/** 折扣 */
	public static String AGIO = "agio";
	/** 主题 */
	public static String TOPIC = "topic";
	/** 支付方式 */
	public static String PAY_METHOD = "payMethod";
	/** 优惠卷 */
	public static String COUPON = "coupon";
	/** 游玩天数 */
	public static String VISIT_DAY = "visitDay";
	/** 出游天数 */
	public static String TICKET_TYPE_NAME = "ticketTypeName";
	/** 奖金查询 */
	public static String PRODUCT_URL = "productUrl";
	/** 奖金查询 */
	public static String CASH_REFUND = "cashRefund";
	/** 查询字段 */
	public static String PRODUCT_INFO = "productInfo";
	/** 产品销售渠道 */
	public static String PRODUCT_CHANNEL = "productChannel";
	/**
	 * 是否是老产品
	 */
	public static String IS_OLD = "isOld";

	public static String PRODUCT_TYPE = "productType";
	public static String SUB_PRODUCT_TYPE = "subProductType";
	/** 标签 */
	public static String CITY="city";
	
	public static String SUBJECT="subject";
	
	/** 标签名称 */
	public static String TAGS_NAME = "tagsName";
	
	/** 标签名称 */
	public static String TAGS_NAME_ORI = "tagsNameOri";
	
	/**标签描述文字**/
	public static String TAGS_DESCRIPT="tagsDescript";
	
	/**标签小组名称**/
	public static String TAGS_GROUP="tagsGroup";
	
	/**标签的样式名称**/
	public static String TAGS_CSS="tagsCss";
	/** 产品的省市地标*/
	public static String PRODUCT_ALLTO_PLACE = "productAllToPlace";
	
	/** 产品经过的地标*/
	public static String PRODUCT_ALLTO_PLACE_CONTENT = "productAllToPlaceContent";
	/** 产品经过的地标不分词*/
	public static String PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN = "productAllToPlaceContentNotoken";
	
	/** 的地标高频关键字集合*/
	public static String PLACE_KEYWORD_BIND = "placeKeywordBind";
	
	/** 产品的目的地地标*/
	public static String PRODUCT_ALLTO_PLACE_IDS = "productAllToPlaceIds";
	
	/****/
	public static String PROD_ROUTE_TYPE_NAME="productRouteTypeName";
	/****/
	public static String PROD_ROUTE_TYPE_DES="productRouteTypeDes";
	/****/
	public static String PROD_ROUTE_TYPE_IMG="productRouteTypeImg";
	/**补全拼音**/
	public static String PRODUCT_ALLTO_PLACE_PINYIN="productAllToPlacePinYin";
	/**出境区域**/
	public static String DEPART_AREA="departArea";
	/**默认类别产品床型**/
	public static String BED_TYPE = "bedType";
	/**默认类别产品宽带**/
	public static String BROADBAND="broadBand";
	/**产品下线时间**/
	public static String OFFLINE_TIME="offlineTime";	
	
	/** 旅游区域 */
	public static String PLAY_AREA="playArea";
	/** 游玩线路*/
	public static String PLAY_LINE="playLine";
	/** 游玩线路*/
	public static String PLAY_LINE_NOTOKEN="playLineNotoken";
	/** 游玩特色 */
	public static String PLAY_FEATURES="playFeature";
	/** 游玩特色 */
	public static String PLAY_FEATURES_NOTOKEN="playFeatureNotoken";
	/** 游玩特色品牌 */
	public static String PLAY_BRAND="playBrand";
	/** 游玩人数 */
	public static String PLAY_NUM="playNum";
	/** 游玩人数 */
	public static String PLAY_NUM_NOTOKEN="playNumNotoken";
	/** 往返交通*/
	public static String TRAFFIC="traffic";
	/** 往返交通*/
	public static String TRAFFIC_NOTOKEN="trafficNotoken";
	/** 酒店类型 */
	public static String HOTEL_TYPE="hotelType";
	/** 酒店类型 */
	public static String HOTEL_TYPE_NOTOKEN="hotelTypeNotoken";
	/** 酒店位置 */
	public static String HOTEL_LOCATION="hotelLocation";
	/** 酒店位置 */
	public static String HOTEL_LOCATION_NOTOKEN="hotelLocationNotoken";
	/** 线路主题 */
	public static String ROUTE_TOPIC="routeTopic";
	/** 线路主题 */
	public static String ROUTE_TOPIC_NOTOKEN="routeTopicNotoken";
	/** 出发班期 */
	public static String TRAVEL_TIME="travelTime";
	/** 途径景点 */
	public static String SCENIC_PLACE="scenicPlace";
	/** 超级自由行标示 */
	public static String SELF_PACK="selfPack";
	/** 一句话推荐2 */
	public static String RECOMMEND_INFO_SECOND="recommendInfoSecond";
	/** 上岛交通*/
	public static String LAND_TRAFFIC="landTraffic";
	/** 岛屿特色*/
	public static String LAND_FEATURE="landFeature";
	/** 岛屿特色*/
	public static String LAND_FEATURE_NOTOKEN="landFeatureNotoken";
	/** 当日可预订 **/
	public static String TODAY_ORDER_ABLE_TIME = "todayOrderAbleTime";
	/** 团购频道 **/
	public static String CHANNEL_FRONT = "channelFront";		
	/** 前台频道 **/
	public static String CHANNEL_GROUP = "channelGroup";	
	/** 产品评论数 **/
	public static String CMT_NUM = "cmtNum";	
	/** 一周销售额 **/
	public static String WEEK_SALES = "weekSales";	
	/**是否是期票*/
	public static String IS_APERIODIC ="isAperiodic";
	
	/** 普通排序的socre **/
	public static String NORMALSCORE = "normalScore";	
	/** hbase的关键字 **/
	public static String HBASEKEY = "hbasekey";	
	/** hbase的关键字的score **/
	public static String HBASEKEYSCORE = "hbasekeyscore";
	
	public static String OWNFIELD="ownfield";
	//lucene系数
	public static String LUCENEFACTOR="lucenefactor";
	
	public static String SHAREWEIXIN = "shareweixin";
	
	
	public ProductDocument() {

	}
    //传入PRUDUCTBEAN 建立DOCUMNET. 注意这里改参数
	public Document createDocument(Object t) {
		ProductBean productBean = (ProductBean) t;
		Document doc = new Document();
		String name= productBean.getProductName();
		//NAME不过滤特殊字符，存入索引第一条，做显示用
		doc.add(new StringField(ProductDocument.PRODUCT_NAME, name, Field.Store.YES));
		// 特殊字符处理，特殊字符过滤不做索引
		name = CommonUtil.escapeString(name);
//		if (name != null && !"".equals(name)) {
//			for (int i = 0; i < name.length(); i++) {
//				doc.add(new StringField(ProductDocument.PRODUCT_NAME, name.substring(i), Field.Store.YES));
//			}
//			for (int i = 1; i <= name.length(); i++) {
//				doc.add(new StringField(ProductDocument.PRODUCT_NAME, name.substring(0, i), Field.Store.YES));
//			}
//			//含有大小写的字段统一改为小写例如：日本HelloKitty乐园 
//			if (!name.toLowerCase().equals(name)) {
//				name = name.toLowerCase();
//				for (int i = 0; i < name.length(); i++) {
//					doc.add(new StringField(ProductDocument.PRODUCT_NAME, name.substring(i), Field.Store.YES));
//				}
//				for (int i = 1; i <= name.length(); i++) {
//					doc.add(new StringField(ProductDocument.PRODUCT_NAME, name.substring(0, i), Field.Store.YES));
//				}
//			}
//		}
		doc.add(new TextField(ProductDocument.PRODUCT_NAME,name,Field.Store.YES));
		//加入默认字段
		doc.add(new TextField(ProductDocument.OWNFIELD,LuceneCommonDic.OWNFIELD,Field.Store.YES));
		double normalScore=1;
		try {
			normalScore=DocumentUtil.normalScore(productBean);
		} catch (Exception e1) {
			throw new RuntimeException();
		}
		doc.add(new DoubleField(ProductDocument.NORMALSCORE, normalScore, Field.Store.YES));
		//存入中值*Lucene系数
		float lucenefactor=DocumentUtil.getLuceneFactor(productBean);
		doc.add(new FloatField(ProductDocument.LUCENEFACTOR, lucenefactor, Field.Store.YES));
		//打印测试
		doc.add(new StringField("aaaaa","销售比值：" +productBean.getSalePer()+"-----"+"中值比值：" +productBean.getMidSalePer()+"-----"+"标签比值：" +productBean.getTagratio()+"-----"+"品牌比值：" +productBean.getBrandratio()+"-----"+"时间差：" +productBean.getTimediff(), Field.Store.YES));
				
		
		LongField weekSalseNum = new LongField(ProductDocument.WEEK_SALES,productBean.getWeekSales(), Field.Store.YES);
		doc.add(weekSalseNum);
		
		FloatField sellPrice = new FloatField(ProductDocument.SELL_PRICE,productBean.getSellPrice(), Field.Store.YES);
		doc.add(sellPrice);

		LongField seq = new LongField(ProductDocument.SEQ,productBean.getSeq(), Field.Store.YES);
		doc.add(seq);

		LongField agio = new LongField(ProductDocument.AGIO,productBean.getAgio(), Field.Store.YES);
		doc.add(agio);

		LongField visitDay = new LongField(ProductDocument.VISIT_DAY,productBean.getVisitDay(), Field.Store.YES);
		doc.add(visitDay);
		LongField cmtNum = new LongField(ProductDocument.CMT_NUM,productBean.getCmtNum(), Field.Store.YES);
		doc.add(cmtNum);
		doc.add(new StringField(ProductDocument.MARKET_PRICE, productBean.getMarketPrice()+"", Field.Store.YES));
		doc.add(new StringField(ProductDocument.PRODUCT_ID, productBean.getProductId()+"" , Field.Store.YES));
		doc.add(new TextField(ProductDocument.RECOMMEND_REASON, productBean.getRecommendReason(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.SMALL_IMAGE, productBean.getSmallImage() + "", Field.Store.YES));
		if (!productBean.getSubProductType().isEmpty()&&productBean.getSubProductType().equals("FREENESS")) {
			/*
			 * 如果产品类型为自由行，则出发点不能为空，改为"ANYWHERE" 以满足QueryUtil.getProductSearchQuery()中查产品子类型包括自由行的算法
			 */
			doc.add(new StringField(ProductDocument.FROM_DEST,  "ANYWHERE", Field.Store.YES));
			//log.info("自由行出发点ANYWHERE");
			
			
		}else {
			doc.add(new StringField(ProductDocument.FROM_DEST, productBean.getFromDest() + "", Field.Store.YES));
		}
		if( productBean.getOfflineTime()!=null){
			doc.add(new LongField(ProductDocument.OFFLINE_TIME, productBean.getOfflineTime().getTime(), Field.Store.YES));
		}
		doc.add(new StringField(ProductDocument.TO_DEST, productBean.getToDest(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.FROM_DEST_CONTENT, productBean.getFromDestContent(), Field.Store.NO));
		doc.add(new StringField(ProductDocument.TO_DEST_CONTENT, productBean.getToDestContent() , Field.Store.YES));
		doc.add(new StringField(ProductDocument.IS_VALID, productBean.getIsValid(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.IS_TICKET, productBean.getIsTicket() , Field.Store.YES));
		doc.add(new StringField(ProductDocument.IS_HID, productBean.getIsHid(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.PAY_METHOD, productBean.getPayMethod(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.COUPON, productBean.getCoupon() , Field.Store.YES));
		doc.add(new StringField(ProductDocument.PRODUCT_URL, productBean.getProductUrl() , Field.Store.YES));
		doc.add(new StringField(ProductDocument.CASH_REFUND, productBean.getCashRefund() , Field.Store.YES));
		doc.add(new StringField(ProductDocument.PRODUCT_INFO, productBean.getProductName() + productBean.getToDest() , Field.Store.NO));
		doc.add(new StringField(ProductDocument.IS_OLD, productBean.getIsOld() , Field.Store.YES));
		doc.add(new StringField(ProductDocument.PRODUCT_TYPE, productBean.getProductType(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.SUB_PRODUCT_TYPE, productBean.getSubProductType(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.PROD_ROUTE_TYPE_NAME, productBean.getProductRouteTypeName(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.PROD_ROUTE_TYPE_DES, productBean.getProductRouteTypeDes(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.PROD_ROUTE_TYPE_IMG, productBean.getProductRouteTypeImg(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.BED_TYPE, productBean.getBedType(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.BROADBAND, productBean.getBroadBand(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.SELF_PACK, productBean.getSelfPack(), Field.Store.YES));
		doc.add(new TextField(ProductDocument.RECOMMEND_INFO_SECOND, productBean.getRecommendInfoSecond(), Field.Store.YES));
		Date todayOrderAbleTime = productBean.getTodayOrderAbleTime();
		doc.add(new StringField(ProductDocument.TODAY_ORDER_ABLE_TIME, todayOrderAbleTime == null ? "" : todayOrderAbleTime.getTime() +"" , Field.Store.YES));
		doc.add(new StringField(ProductDocument.CHANNEL_FRONT, productBean.getChannelFront() , Field.Store.YES));
		doc.add(new StringField(ProductDocument.CHANNEL_GROUP, productBean.getChannelGroup() , Field.Store.YES));
		doc.add(new StringField(ProductDocument.IS_APERIODIC, productBean.getIsAperiodic(), Field.Store.YES));
		doc.add(new StringField(ProductDocument.SHAREWEIXIN, productBean.getShareweixin(), Field.Store.YES));
		/** DEPART_AREA字段中数据按分隔符","分词,修正查不到"澳新,大洋洲"的BUG **/
		String departAreaStr = productBean.getDepartArea();
		String departArea = getChinaWordStr(departAreaStr) + "," + getPinyinWordStr(departAreaStr);
		if (departArea != null && !"".equals(departArea) && !",".equals(departArea)) {
			StringTokenizer st = new StringTokenizer(departArea, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				doc.add(new StringField(ProductDocument.DEPART_AREA, key, Field.Store.YES));
			}
			// 解决DEPART_AREA不为空，但是为"," 的BUG!
			if (departArea.equals(",")) {
				doc.add(new StringField(ProductDocument.DEPART_AREA, ",", Field.Store.YES));
			}
		} else {
			doc.add(new StringField(ProductDocument.DEPART_AREA, "", Field.Store.YES));
		}
		
		/**TOPICS,字段中数据按分隔符","分词**/
		String topicsStr = productBean.getTopic();
		String topics = getChinaWordStr(topicsStr)+","+getPinyinWordStr(topicsStr);
		if (topics!= null && !"".equals(topics) && !",".equals(topics)) {
			StringTokenizer st = new StringTokenizer(topics, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				  String key=st.nextToken();
				  doc.add(new TextField(ProductDocument.TOPIC, key, Field.Store.YES));
			}
			//解决TOPIC不为空，但是为"," 的BUG! 
			if (topics.equals(",")){doc.add(new TextField(ProductDocument.TOPIC, ",", Field.Store.YES));}
			
		} else {
				 doc.add(new TextField(ProductDocument.TOPIC, "", Field.Store.YES));
		}
		
		/**标签名称**/
		String tagsNameStr = productBean.getTagsName();
		String tagsname = getChinaWordStr(tagsNameStr)+","+getPinyinWordStr(tagsNameStr);
		if (tagsname!= null && !"".equals(tagsname)&& !",".equals(tagsname)) {
			StringTokenizer st = new StringTokenizer(tagsname, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				  doc.add(new TextField(ProductDocument.TAGS_NAME, st.nextToken(), Field.Store.YES));
			}
		} else {
				 doc.add(new TextField(ProductDocument.TAGS_NAME, "", Field.Store.YES));
		}
		doc.add(new TextField(ProductDocument.TAGS_NAME_ORI, tagsNameStr, Field.Store.YES));
		/**标签说明文**/
		String tagsDescriptStr = productBean.getTagsDescript();
		if (tagsDescriptStr!= null && !"".equals(tagsDescriptStr)){
//			StringTokenizer st = new StringTokenizer(tagsDescriptStr, SearchConstants.DELIM);
//			while (st.hasMoreTokens()) {
//				  doc.add(new StringField(ProductDocument.TAGS_DESCRIPT, st.nextToken(), Field.Store.YES));
//			}
			 doc.add(new StringField(ProductDocument.TAGS_DESCRIPT, tagsDescriptStr, Field.Store.YES));
		} else {
				 doc.add(new StringField(ProductDocument.TAGS_DESCRIPT, "", Field.Store.YES));
		}
		
		/**标签小组名称**/
		String tagsGroupStr = productBean.getTagsGroup();
		if (tagsGroupStr!= null && !"".equals(tagsGroupStr)){
//			StringTokenizer st = new StringTokenizer(tagsGroupStr, SearchConstants.DELIM);
//			while (st.hasMoreTokens()) {
//				  doc.add(new StringField(ProductDocument.TAGS_GROUP, st.nextToken(), Field.Store.YES));
//			}
			 doc.add(new StringField(ProductDocument.TAGS_GROUP, tagsGroupStr, Field.Store.YES));
		} else {
				 doc.add(new StringField(ProductDocument.TAGS_GROUP, "", Field.Store.YES));
		}
		/**标签样式名称**/
		String tagsCssStr = productBean.getTagsCss();
		if (tagsCssStr!= null && !"".equals(tagsCssStr)){
//			StringTokenizer st = new StringTokenizer(tagsCssStr, SearchConstants.DELIM);
//			while (st.hasMoreTokens()) {
//				  doc.add(new StringField(ProductDocument.TAGS_CSS, st.nextToken(), Field.Store.YES));
//			}
			doc.add(new StringField(ProductDocument.TAGS_CSS, tagsCssStr, Field.Store.YES));
		} else {
				 doc.add(new StringField(ProductDocument.TAGS_CSS, "", Field.Store.YES));
		}
		
		/**productAllToPlace**/
		String productAllToPlaceStr = productBean.getProductAllToPlace();
		String productAllToPlace = getChinaWordStr(productAllToPlaceStr)+","+getPinyinWordStr(productAllToPlaceStr);
		if (StringUtils.isNotEmpty(productAllToPlace) && !",".equals(productAllToPlace)) {
			Map<String, String> check = new HashMap<String, String>();
			StringTokenizer st = new StringTokenizer(productAllToPlace, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				StringTokenizer kg = new StringTokenizer(st.nextToken(), " ");
				while (kg.hasMoreTokens()) {
					String key = kg.nextToken();
					if (!check.containsKey(key)) {
						if (!"中国".equals(key) && !"亚洲".equals(key)) {// 过滤掉中国，亚洲
							doc.add(new TextField(ProductDocument.PRODUCT_ALLTO_PLACE, key, Field.Store.YES));
						}
						else if (("中国".equals(key)||"亚洲".equals(key))&&("GROUP_FOREIGN".equals(productBean.getSubProductType())||"FREENESS_FOREIGN".equals(productBean.getSubProductType()))) {
							//出境游栏目需要加"中国" ,"亚洲".
							doc.add(new TextField(ProductDocument.PRODUCT_ALLTO_PLACE, key, Field.Store.YES));
						}
						// 特殊字符处理，特殊字符不进入索引！
						if (key.length() != CommonUtil.escapeString(key).length()) {
							// System.out.println(key);
							doc.add(new TextField(ProductDocument.PRODUCT_ALLTO_PLACE, CommonUtil.escapeString(key), Field.Store.YES));
						}
						check.put(key, key);

					}
				}
			}
		} else {
			doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE, "", Field.Store.YES));
		}
		
		/**productAllToPlaceContent**/
		String productAllToPlaceContentStr = productBean.getProductAllToPlaceContent();
		String productAllToPlaceContent = getChinaWordStr(productAllToPlaceContentStr)+","+getPinyinWordStr(productAllToPlaceContentStr);
		if (StringUtils.isNotEmpty(productAllToPlaceContent) && !",".equals(productAllToPlaceContent)) {
			Map<String, String> check = new HashMap<String, String>();
			StringTokenizer st = new StringTokenizer(productAllToPlaceContent, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				StringTokenizer kg = new StringTokenizer(st.nextToken(), " ");
				while (kg.hasMoreTokens()) {
					String key = kg.nextToken();
					if (!check.containsKey(key)) {
						if (!"中国".equals(key) && !"亚洲".equals(key)) {// 过滤掉中国，亚洲
							doc.add(new TextField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT, key, Field.Store.YES));
							doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN, key, Field.Store.YES));
						}
						else if (("中国".equals(key)||"亚洲".equals(key))&&("GROUP_FOREIGN".equals(productBean.getSubProductType())||"FREENESS_FOREIGN".equals(productBean.getSubProductType()))) {
							//出境游栏目需要加"中国" ,"亚洲".
							doc.add(new TextField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT, key, Field.Store.YES));
							doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN, key, Field.Store.YES));
						}
						// 特殊字符处理，特殊字符不进入索引！
						if (key.length() != CommonUtil.escapeString(key).length()) {
							// System.out.println(key);
							doc.add(new TextField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT, CommonUtil.escapeString(key), Field.Store.YES));
							doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN, CommonUtil.escapeString(key), Field.Store.YES));
						}
						check.put(key, key);

					}
				}
			}
		} else {
			doc.add(new TextField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT, "", Field.Store.YES));
			doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN, "", Field.Store.YES));
		}
		
		/** PLACE_KEYWORD_BIND ,(来自PLACE的高频关键字) **/
		String placeKeywordBindStr = productBean.getPlaceKeywordBind();
		String placeKeywordBind = getChinaWordStr(placeKeywordBindStr)+","+getPinyinWordStr(placeKeywordBindStr);
		if (placeKeywordBind != null && !"".equals(placeKeywordBind)&& !",".equals(placeKeywordBind)) {
			StringTokenizer st = new StringTokenizer(placeKeywordBind, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				key = CommonUtil.escapeString(key); // 特殊字符去掉不进入搜索
				doc.add(new TextField(ProductDocument.PLACE_KEYWORD_BIND, key, Field.Store.YES));
			}
			// 解决destPresentStr不为空，但是为"," 的BUG!
			if (placeKeywordBind.equals(",")) {
				doc.add(new TextField(ProductDocument.PLACE_KEYWORD_BIND, ",", Field.Store.YES));
			}

		} else {
			doc.add(new TextField(ProductDocument.PLACE_KEYWORD_BIND, "", Field.Store.YES));
		}
		
		/****/
		String productAllToPlacePinYin = productBean.getProductAllToPlacePinYin();
		if (productAllToPlacePinYin != null && !"".equals(productAllToPlacePinYin)) {
			Map check = new HashMap();
			StringTokenizer st = new StringTokenizer(productAllToPlacePinYin, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				StringTokenizer kg = new StringTokenizer(st.nextToken(), SearchConstants.SPACE);

				while (kg.hasMoreTokens()) {
					String key = kg.nextToken();
					if (!check.containsKey(key)) {
						doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE_PINYIN, key, Field.Store.YES));
						check.put(key, key);

					}
				}
			}
		} else {
			doc.add(new TextField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT, "", Field.Store.YES));
			doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN, "", Field.Store.YES));
		}
		
		/****/
		String productAllToPlaceIds = productBean.getProductAllToPlaceIds();
		if (productAllToPlaceIds!= null && !"".equals(productAllToPlaceIds )) {
			StringTokenizer st = new StringTokenizer(productAllToPlaceIds, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				 doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE_IDS, st.nextToken(), Field.Store.YES));
				// 解决destPresentStr不为空，但是为"," 的BUG!
					if (productAllToPlaceIds.equals(",")) {
						doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE_IDS, " ", Field.Store.YES));
					}
			}
		} else {
				 doc.add(new StringField(ProductDocument.PRODUCT_ALLTO_PLACE_IDS, "", Field.Store.YES));
		}
		
		/****/
		String productChannels = productBean.getProductChannel();
		if (productChannels!= null && !"".equals(productChannels )) {
			StringTokenizer st = new StringTokenizer(productChannels, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				 doc.add(new StringField(ProductDocument.PRODUCT_CHANNEL, st.nextToken(), Field.Store.YES));
				// 解决destPresentStr不为空，但是为"," 的BUG!
					if (productAllToPlaceIds.equals(",")) {
						doc.add(new StringField(ProductDocument.PRODUCT_CHANNEL, "", Field.Store.YES));
					}
			}
		} else {
				 doc.add(new StringField(ProductDocument.PRODUCT_CHANNEL, "", Field.Store.YES));
		}
		
		// product_property_search_info属性处理
		createCommonDoc(doc,productBean.getLandFeature(),ProductDocument.LAND_FEATURE,0);
		createCommonDoc(doc,productBean.getLandFeature(),ProductDocument.LAND_FEATURE_NOTOKEN,1);
		createCommonDoc(doc,productBean.getLandTraffic(),ProductDocument.LAND_TRAFFIC,0);
		createCommonDoc(doc,productBean.getPlayArea(),ProductDocument.PLAY_AREA,0);
		createCommonDoc(doc,productBean.getPlayLine(),ProductDocument.PLAY_LINE,0);
		createCommonDoc(doc,productBean.getPlayLine(),ProductDocument.PLAY_LINE_NOTOKEN,1);
		createCommonDoc(doc,productBean.getPlayFeatures(),ProductDocument.PLAY_FEATURES,0);
		createCommonDoc(doc,productBean.getPlayFeatures(),ProductDocument.PLAY_FEATURES_NOTOKEN,1);
		createCommonDoc(doc,productBean.getPlayBrand(),ProductDocument.PLAY_BRAND,0);
		createCommonDoc(doc,productBean.getPlayNum(),ProductDocument.PLAY_NUM,0);
		createCommonDoc(doc,productBean.getPlayNum(),ProductDocument.PLAY_NUM_NOTOKEN,1);
		createCommonDoc(doc,productBean.getTraffic(),ProductDocument.TRAFFIC,0);
		createCommonDoc(doc,productBean.getTraffic(),ProductDocument.TRAFFIC_NOTOKEN,1);
		createCommonDoc(doc,productBean.getHotelType(),ProductDocument.HOTEL_TYPE,0);
		createCommonDoc(doc,productBean.getHotelType(),ProductDocument.HOTEL_TYPE_NOTOKEN,1);
		createCommonDoc(doc,productBean.getHotelLocation(),ProductDocument.HOTEL_LOCATION,0);
		createCommonDoc(doc,productBean.getHotelLocation(),ProductDocument.HOTEL_LOCATION_NOTOKEN,1);
		createCommonDoc(doc,productBean.getRouteTopic(),ProductDocument.ROUTE_TOPIC,0);
		createCommonDoc(doc,productBean.getRouteTopic(),ProductDocument.ROUTE_TOPIC_NOTOKEN,1);
		//加入途径景点索引
		createPropertySearchInfoDoc(doc,productBean.getScenicPlace(),ProductDocument.SCENIC_PLACE);
		//处理出发班期索引   格式如：3/12,3/15,4/13,4/15
		createPropertySearchInfoDoc(doc,productBean.getTravelTime(),ProductDocument.TRAVEL_TIME);
		
		
		
		return doc;
	}
	
	
	//把document转换成PRODUCTBEAN
	public Object parseDocument(Document doc) {
		ProductBean s = new ProductBean();
		s.setValidTime(doc.get(ProductDocument.OFFLINE_TIME));
		s.setProductId(new Long(doc.get(ProductDocument.PRODUCT_ID)));
		s.setProductName(doc.getValues(ProductDocument.PRODUCT_NAME)[0]);
		s.setRecommendReason(doc.get(ProductDocument.RECOMMEND_REASON));
		BigDecimal marketPrice = new BigDecimal(doc.get(ProductDocument.MARKET_PRICE));
		s.setMarketPrice(marketPrice.multiply(new BigDecimal(100)).longValue());
		s.setWeekSales(new Long(doc.get(ProductDocument.WEEK_SALES)));
		BigDecimal bigDecimal = new BigDecimal(doc.get(ProductDocument.SELL_PRICE));
		s.setSellPrice(bigDecimal.multiply(new BigDecimal(100)).longValue());
		s.setSmallImage(doc.get(ProductDocument.SMALL_IMAGE));
		s.setFromDest(doc.getValues(ProductDocument.FROM_DEST)[0]);
		s.setIsValid(doc.get(ProductDocument.IS_VALID));
		s.setIsTicket(doc.get(ProductDocument.IS_TICKET));
		s.setSeq(new Long(doc.get(ProductDocument.SEQ)));
		s.setIsHid(doc.get(ProductDocument.IS_HID));
		s.setAgio(new Long(doc.get(ProductDocument.AGIO)));
		//s.setTopic(doc.get(ProductDocument.TOPIC)); //分字段
		s.setPayMethod(doc.get(ProductDocument.PAY_METHOD));
		s.setCoupon(doc.get(ProductDocument.COUPON));
		s.setVisitDay(new Long(doc.get(ProductDocument.VISIT_DAY)));
		s.setCmtNum(new Long(doc.get(ProductDocument.CMT_NUM)));
		s.setProductUrl(doc.get(ProductDocument.PRODUCT_URL));
		s.setProductInfo(doc.get(ProductDocument.PRODUCT_INFO));
		s.setToDest(doc.get(ProductDocument.TO_DEST));
		s.setCashRefund(doc.get(ProductDocument.CASH_REFUND));
		s.setIsOld(doc.get(ProductDocument.IS_OLD));
		s.setToDestContent(doc.get(ProductDocument.TO_DEST_CONTENT));
		s.setProductType(doc.get(ProductDocument.PRODUCT_TYPE));
		s.setSubProductType(doc.get(ProductDocument.SUB_PRODUCT_TYPE));
		s.setProductRouteTypeName(doc.get(ProductDocument.PROD_ROUTE_TYPE_NAME));
		s.setProductRouteTypeDes(doc.get(ProductDocument.PROD_ROUTE_TYPE_DES));
		s.setProductRouteTypeImg(doc.get(ProductDocument.PROD_ROUTE_TYPE_IMG));
		s.setBedType(doc.get(ProductDocument.BED_TYPE));
		s.setBroadBand(doc.get(ProductDocument.BROADBAND));
		s.setSelfPack(doc.get(ProductDocument.SELF_PACK));
		s.setShareweixin(doc.get(ProductDocument.SHAREWEIXIN));
		s.setRecommendInfoSecond(doc.get(ProductDocument.RECOMMEND_INFO_SECOND));
		String todayOrderAbleTimeStr = doc.get(ProductDocument.TODAY_ORDER_ABLE_TIME);
		if(StringUtils.isNotEmpty(todayOrderAbleTimeStr)){
			s.setTodayOrderAbleTime(new Date(Long.parseLong(todayOrderAbleTimeStr)));
		}
		s.setIsAperiodic(doc.get(ProductDocument.IS_APERIODIC));
		/**DEPART_AREA**/
		if (StringUtils.isEmpty(doc.get(ProductDocument.DEPART_AREA))) {
			s.setDepartArea("");
		}else
		{
			List<String> tmp_list = new ArrayList<String>();
			for (int i = 0; i < doc.getValues(ProductDocument.DEPART_AREA).length; i++) {
				// 判断是中文
				if (doc.getValues(ProductDocument.DEPART_AREA)[i].matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					tmp_list.add(doc.getValues(ProductDocument.DEPART_AREA)[i]);
				}
			}		
			s.setDepartArea(StringUtils.join(tmp_list,","));
		}
		
		/**TOPIC**/
		if (StringUtils.isEmpty(doc.get(ProductDocument.TOPIC))) {
			s.setTopic("");
		}else
		{
			List<String> tmp_list = new ArrayList<String>();
			for (int i = 0; i < doc.getValues(ProductDocument.TOPIC).length; i++) {
				// 判断是中文
				if (doc.getValues(ProductDocument.TOPIC)[i].matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					tmp_list.add(doc.getValues(ProductDocument.TOPIC)[i]);
				}
			}		
			s.setTopic(StringUtils.join(tmp_list,","));
		}
		
		
		/**标签名称**/
		if (StringUtils.isEmpty(doc.get(ProductDocument.TAGS_NAME))) {

			s.setTagsName("");
			s.setTagsDescript("");
			s.setTagsGroup("");
			s.setTagsCss("");
			
		} else {
			List<String> tmp_list = new ArrayList<String>();
			for (int i = 0; i < doc.getValues(ProductDocument.TAGS_NAME).length; i++) {
				// 判断是中文
				if (doc.getValues(ProductDocument.TAGS_NAME)[i].matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					tmp_list.add(doc.getValues(ProductDocument.TAGS_NAME)[i]);
				}
			}
			s.setTagsName(StringUtils.join(tmp_list,","));
			
			/**标签描述*/
			String tagsDescript = doc.get(ProductDocument.TAGS_DESCRIPT);
			s.setTagsDescript(tagsDescript);
			List<String> tagsDescriptList = Arrays.asList(tagsDescript.split(","));
			
			/**标签小组名称*/
			String tagsGroup = doc.get(ProductDocument.TAGS_GROUP);
			s.setTagsGroup(tagsGroup);
			List<String> tagsGroupList = Arrays.asList(tagsGroup.split(","));
			
			/**标签CSS*/
			String tagsCss = doc.get(ProductDocument.TAGS_CSS);
			List<String> tagsCssList = Arrays.asList(tagsCss.split(","));
			s.setTagsCss(tagsCss);
			
			List<ProdTag> tagList = new ArrayList<ProdTag>();
			Map<String, List<ProdTag>> tagGroupMap =new HashMap<String, List<ProdTag>>();
			
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
				List<ProdTag> tagGroupList = (List<ProdTag>) tagGroupMap.get(tagGroup);
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


		/**PRODUCT_ALLTO_PLACE**/
		if (StringUtils.isEmpty(doc.get(ProductDocument.PRODUCT_ALLTO_PLACE))) {

			s.setProductAllToPlace("");

		} else {
			List<String> tmp_list = new ArrayList<String>();
			for (int i = 0; i < doc.getValues(ProductDocument.PRODUCT_ALLTO_PLACE).length; i++) {
				// 判断是中文：只要不是纯拼音的都是中文
				if (doc.getValues(ProductDocument.PRODUCT_ALLTO_PLACE)[i].matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					tmp_list.add(doc.getValues(ProductDocument.PRODUCT_ALLTO_PLACE)[i]);
				}
			}
			s.setProductAllToPlace(StringUtils.join(tmp_list,","));
		}
		
		/**PRODUCT_ALLTO_PLACE_CONTENT**/
		if (StringUtils.isEmpty(doc.get(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT))) {

			s.setProductAllToPlaceContent("");

		} else {
			List<String> tmp_list = new ArrayList<String>();
			for (int i = 0; i < doc.getValues(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT).length; i++) {
				// 判断是中文：只要不是纯拼音的都是中文
				if (doc.getValues(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT)[i].matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					tmp_list.add(doc.getValues(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT)[i]);
				}
			}
			s.setProductAllToPlaceContent(StringUtils.join(tmp_list,","));
		}
		
		/***/
		if (StringUtils.isEmpty(doc.get(ProductDocument.PRODUCT_ALLTO_PLACE_IDS))) {

			s.setProductAllToPlaceIds("");

		} else {
			List<String> tmp_list = new ArrayList<String>();
			for (int i = 0; i < doc.getValues(ProductDocument.PRODUCT_ALLTO_PLACE_IDS).length; i++) {
				tmp_list.add(doc.getValues(ProductDocument.PRODUCT_ALLTO_PLACE_IDS)[i]);
			}
			s.setProductAllToPlaceIds(StringUtils.join(tmp_list,","));
		}
		
		// 增加property_search_info属性处理
		s.setLandFeature(parseProperty(doc.getValues(ProductDocument.LAND_FEATURE)));
		s.setLandTraffic(parseProperty(doc.getValues(ProductDocument.LAND_TRAFFIC)));
		s.setPlayArea(parseProperty(doc.getValues(ProductDocument.PLAY_AREA)));
		s.setPlayLine(parseProperty(doc.getValues(ProductDocument.PLAY_LINE)));
		s.setPlayFeatures(parseProperty(doc.getValues(ProductDocument.PLAY_FEATURES)));
		s.setPlayBrand(parseProperty(doc.getValues(ProductDocument.PLAY_BRAND)));
		s.setPlayNum(parseProperty(doc.getValues(ProductDocument.PLAY_NUM)));
		s.setTraffic(CommonUtil.StringFilter(parseProperty(doc.getValues(ProductDocument.TRAFFIC))));
		s.setRouteTopic(CommonUtil.StringFilter(parseProperty(doc.getValues(ProductDocument.ROUTE_TOPIC))));
		s.setHotelType(parseProperty(doc.getValues(ProductDocument.HOTEL_TYPE)));
		s.setHotelLocation(parseProperty(doc.getValues(ProductDocument.HOTEL_LOCATION)));
		s.setScenicPlace(wrapProperty(doc.getValues(ProductDocument.SCENIC_PLACE)));
		s.setTravelTime(wrapProperty(doc.getValues(ProductDocument.TRAVEL_TIME)));
		return s;
	}
	/**
	 * 解析属性，并将默认每组第一个属性(propertyName)取出来，封装成以','分割的字符串
	 * 格式如：propertyName~pinyin~叙词1、叙词2,propertyName~pinyin~叙词1、叙词2,propertyName~pinyin~叙词1、叙词2
	 * @param docProperty
	 * @return
	 */
	private String parseProperty(String[] docProperty) {
		String result="";
		if(docProperty!=null&&docProperty.length>0){
			if (docProperty[0] != null && !"".equals(docProperty[0])) {
				result=docProperty[0];
			}
		}
		return result;
	}
	/**
	 * 获取数组数据
	 * 
	 * @param docProperty
	 * @return
	 */
	private String[][] getPropertyArray(String docProperty) {
		if (StringUtils.isNotBlank(docProperty)) {
			String[] propertyArray = docProperty.split(SearchConstants.DELIM);
			String[][] resultArray = null;
			if (propertyArray != null && propertyArray.length > 0) {
				resultArray = new String[propertyArray.length][3];
				for (int i = 0; i < propertyArray.length; i++) {
					if (propertyArray[i] != null && !"".equals(propertyArray[i])) {
						String[] subPropertyArray = propertyArray[i].split(SearchConstants.TILDE);
						resultArray[i] = subPropertyArray;
					}
				}
			}
			return resultArray;
		} else {
			return null;
		}
	}
	/**
	 * 封装属性成 用','分割的字符串
	 * 
	 * @param docProperty
	 * @return
	 */
	private String wrapProperty(String[] docProperty){
		if(docProperty!=null&&docProperty.length>0){
			return StringUtils.join(docProperty,",");
		}else{
			return null;
		}
	}
	
	/**
	 * 处理属性，格式类似：propertyName,propertyName,propertyName,propertyName
	 * 
	 * @param doc
	 * @param propertyName
	 * @param documentName
	 */
	private void createPropertySearchInfoDoc(Document doc, String propertyName, String documentName) {
		if (StringUtils.isNotBlank(propertyName)) {
			String[] termArray = propertyName.split(SearchConstants.DELIM);
			for (int i = 0; i < termArray.length; i++) {
				if (termArray[i] != null && !"".equals(termArray[i])) {
					doc.add(new StringField(documentName, termArray[i], Field.Store.YES));
				}
			}
		} else {
			doc.add(new StringField(documentName, "", Field.Store.YES));
		}
	}
	/**
	 * 通用屬性索引創建  处理格式如：name~pinyin、pinyin2;叙词、叙词2,name~pinyin;叙词
	 * 
	 * @param doc
	 * @param propertyName
	 * @param documentName
	 */
	private void createCommonDoc(Document doc, String propertyName, String documentName,int isToken) {
		if (StringUtils.isNotBlank(propertyName)) {
			propertyName=propertyName.replaceAll(";", "~");
			StringBuffer sb=new StringBuffer("");
			StringBuffer sb2=new StringBuffer("");
			String result="";
			String[][] propertyArray = getPropertyArray(propertyName);
			if (propertyArray != null && propertyArray.length > 0) {
				String cleanStr = null;
				for (String[] subPropertyArray : propertyArray) {
					if (subPropertyArray != null && subPropertyArray.length > 0) {
						if(subPropertyArray[0]!=null&&!"".equals(subPropertyArray[0]))
							sb.append(subPropertyArray[0]+",");
						for (String propValue : subPropertyArray) {
							if(propValue!=null&&!"".equals(propValue)){
								String[] valueArray = propValue.split(SearchConstants.COMMA);
								for (String value : valueArray) {
									cleanStr = CommonUtil.escapeString(value);
									// 只处理不为空的属性和不等于','的属性
									if (cleanStr != null && !"".equals(cleanStr) && !"、".equals(cleanStr))
										sb2.append(cleanStr+",");
										
								}
							}
						}
					}
				}
				if(sb.toString()!=null&&!"".equals(sb.toString())){
					result=sb.toString().substring(0,sb.toString().length()-1);
				}
				doc.add(new StringField(documentName, result, Field.Store.YES));
				if(sb2.toString()!=null&&!"".equals(sb2.toString())){
					result=sb2.toString().substring(0,sb2.toString().length()-1);
				}
				String[] docValues=result.split(SearchConstants.DELIM);
				for(String value:docValues){
//					doc.add(new StringField(documentName, value, Field.Store.YES));
					if(isToken==0){
						doc.add(new TextField(documentName, value, Field.Store.YES));
					}else if(isToken==1){
						doc.add(new StringField(documentName, value, Field.Store.YES));
					}
				
					
				}
			}
		} else {
			doc.add(new StringField(documentName, "", Field.Store.YES));
		}
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
					if (check.get(word) == null&&word!=null) {// 新的数据,保留
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
				// 判断是拼音
				if (!word.matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					if (check.get(word) == null&&word!=null) {// 新的数据,保留
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
