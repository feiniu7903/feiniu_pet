package com.lvmama.search.lucene.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.util.CommonUtil;

public class NewFreelongQueryUtil {
	
private static final Log log = LogFactory.getLog(NewFreetourQueryUtil.class);	
	/**
	 * (机票+景点)搜索条件
	 * @param keywordMap
	 * @return
	 */
	public static BooleanQuery getNewFreeLongQuery(Map<String, String> keywordMap) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		String fromDest = CommonUtil.escapeString(keywordMap.get(ProductDocument.FROM_DEST));
		String subject = CommonUtil.escapeString(keywordMap.get(ProductDocument.SUBJECT));
		String tagName = CommonUtil.escapeString(keywordMap.get(ProductDocument.TAGS_NAME));		
		String toDest = CommonUtil.escapeString(keywordMap.get(ProductDocument.TO_DEST));
		String city = CommonUtil.escapeString(keywordMap.get("city"));
		String hotelType=CommonUtil.escapeString(keywordMap.get(ProductDocument.HOTEL_TYPE));
		String hotelLocation=CommonUtil.escapeString(keywordMap.get(ProductDocument.HOTEL_LOCATION));		
		String landFeature=CommonUtil.escapeString(keywordMap.get(ProductDocument.LAND_FEATURE));
		String landTraffic=CommonUtil.escapeString(keywordMap.get(ProductDocument.LAND_TRAFFIC));
		String subProductType = CommonUtil.escapeString(keywordMap.get(ProductDocument.SUB_PRODUCT_TYPE));
		String poductNameSearchKeywords = keywordMap.get("poductNameSearchKeywords");
		String keyword2 = CommonUtil.escapeString(keywordMap.get("keyword2"));		
		String productChannel = keywordMap.get(ProductDocument.PRODUCT_CHANNEL);
		String visitDay = CommonUtil.escapeString(keywordMap.get("visitDay"));		
		String traffic=CommonUtil.escapeString(keywordMap.get(ProductDocument.TRAFFIC));
		String playBrand=CommonUtil.escapeString(keywordMap.get(ProductDocument.PLAY_BRAND));
		String startPrice=CommonUtil.escapeString(keywordMap.get(ProductDocument.START_PRICE));
		String endPrice=CommonUtil.escapeString(keywordMap.get(ProductDocument.END_PRICE));		
		String tagsGroup = keywordMap.get("tagsGroup");	
	try {
			// 二次搜索
			if (StringUtils.isNotEmpty(keyword2)) {
				//log.info("keyword2 : " + keyword2);
				booleanQuery.add(productquery.getProductNameQuery(keyword2), BooleanClause.Occur.MUST);
			}
			// 地区筛选
			if (StringUtils.isNotEmpty(city)) {
				//log.info("city_query" + city);
				//对 地区进行分词
//				booleanQuery.add(productquery.getProductAlltoPlaceConent(city), BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN,city)), BooleanClause.Occur.MUST);
			}
			// 主题筛选
			if (StringUtils.isNotEmpty(subject)) {
				//log.info("subject_query" + subject);
				booleanQuery.add(productquery.getTopicQuery(subject), BooleanClause.Occur.MUST);
			}
			// 标签筛选
			if (StringUtils.isNotEmpty(tagName)) {
				//log.info("tagName_query" + tagName);
				booleanQuery.add(productquery.getTagNameQuery(tagName), BooleanClause.Occur.MUST);
			}
			// 游玩天数
			if (StringUtils.isNotEmpty(visitDay)) {
				booleanQuery.add(productquery.getVisitDayQuery(visitDay), BooleanClause.Occur.MUST);
			}
			//展示渠道
			if (StringUtils.isNotEmpty(productChannel)) {
				booleanQuery.add(productquery.getProductChannelQuery(productChannel), BooleanClause.Occur.MUST);
			}
			//特色品牌
			if(StringUtils.isNotBlank(playBrand)){
				booleanQuery.add(productquery.getPlayBrandQuery(playBrand),BooleanClause.Occur.MUST);
			}
			//酒店类型
			if(StringUtils.isNotBlank(hotelType)){
				//对 酒店类型进行分词
//				booleanQuery.add(productquery.getHotelTypeQuery(hotelType),BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.HOTEL_TYPE_NOTOKEN,hotelType)), BooleanClause.Occur.MUST);

			}
			//标签组
			if(StringUtils.isNotBlank(tagsGroup)){
				booleanQuery.add(productquery.getTagsGroupQuery(tagsGroup),BooleanClause.Occur.MUST);
			}
			//往返交通
			if(StringUtils.isNotBlank(traffic)){
				//对 往返交通进行分词
//				booleanQuery.add(productquery.getTrafficQuery(traffic),BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.TRAFFIC_NOTOKEN,traffic)), BooleanClause.Occur.MUST);
			}
			//岛屿特色
			if(StringUtils.isNotBlank(landFeature)){
				//对 岛屿特色进行分词
//				booleanQuery.add(productquery.getLandFeaturesQuery(landFeature),BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.LAND_FEATURE_NOTOKEN,landFeature)), BooleanClause.Occur.MUST);
			}
			//上岛方式
			if(StringUtils.isNotBlank(landTraffic)){
				booleanQuery.add(productquery.getLandTrafficQuery(landTraffic),BooleanClause.Occur.MUST);
			}
			//酒店位置
			if(StringUtils.isNotBlank(hotelLocation)){
				//对 酒店位置进行分词
//				booleanQuery.add(productquery.getHotelLocationQuery(hotelLocation),BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.HOTEL_LOCATION_NOTOKEN,hotelLocation)), BooleanClause.Occur.MUST);
			}
			//价格
			if (StringUtils.isNotBlank(startPrice)||StringUtils.isNotBlank(endPrice)) {
				booleanQuery.add(productquery.getSellPriceQuery(startPrice, endPrice),BooleanClause.Occur.MUST);
			}
			//出发地,
			if (StringUtils.isNotEmpty(fromDest)) {
				booleanQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.MUST);
			}
			//产品SUB类型为FREENESS_LONG +FREENESS_FOREIGN	
			BooleanQuery  subProductTypeQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(subProductType)) {				
				//log.info("query_rountType" + subProductType);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("FREENESS_LONG"),BooleanClause.Occur.SHOULD);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("FREENESS_FOREIGN"),BooleanClause.Occur.SHOULD);
				
			}
			if (subProductTypeQuery.clauses().size() > 0) {
				booleanQuery.add(subProductTypeQuery, BooleanClause.Occur.MUST);
			}
			// 对线路产品的名称进行搜索,支持空格分词功能  // 考虑空格分词情况，先切词
			BooleanQuery poductNameSearchQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(poductNameSearchKeywords)) {
//				StringTokenizer stringSplit = new StringTokenizer(poductNameSearchKeywords, " ");
//				while (stringSplit.hasMoreTokens()) {
//					poductNameSearchQuery.add(productquery.getProductNameQuery(stringSplit.nextToken()), BooleanClause.Occur.MUST);
//				}
				List<String> list=new ArrayList<String>();
				list.add(ProductDocument.PRODUCT_NAME);
				Query query = QueryUtil.getMultiFiedlParserQueryByHasField(poductNameSearchKeywords,list);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			if (poductNameSearchQuery.clauses().size() > 0) {
				booleanQuery.add(poductNameSearchQuery, BooleanClause.Occur.MUST);
			}			
			// 目的地,标地
//			BooleanQuery toDestQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(toDest) && StringUtils.isEmpty(poductNameSearchKeywords)) {
//				toDestQuery.add(productquery.getProductAlltoPlaceConent(toDest), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getdepartAreaQuery(toDest), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getTopicQuery(toDest), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getTagNameQuery(toDest), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getPlaceKeywordBindQuery(toDest), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getPlayAreaQuery(toDest), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getPlayFeaturesQuery(toDest), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getTrafficQuery(toDest), BooleanClause.Occur.SHOULD);
				
				//加入分词器的query，加入PRODUCT_NAME字段直接查
				Query query = QueryUtil.getMultiFiedlParserQuery(toDest);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}			
			//加入分词器的query所以注释掉
//			if (toDestQuery.clauses().size() > 0) {
//				booleanQuery.add(toDestQuery, BooleanClause.Occur.MUST);
//			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return booleanQuery;
	}	


	public static String[] splitKeyword(String keyword) {
		String[] key = keyword.split("_");
		return key;
	}

}
