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
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.util.CommonUtil;

public class NewFreetourQueryUtil {
	
private static final Log log = LogFactory.getLog(NewFreetourQueryUtil.class);	
	/**
	 * (景点+酒店)搜索条件
	 * 
	 * @param keywordMap
	 * @return
	 */
	public static BooleanQuery getNewFreetourQuery(Map<String, String> keywordMap) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		String keyword = CommonUtil.escapeString(keywordMap.get("keyword"));
		String tagName = CommonUtil.escapeString(keywordMap.get(ProductDocument.TAGS_NAME));
		String visitDay = CommonUtil.escapeString(keywordMap.get(ProductDocument.VISIT_DAY));	
		String city = CommonUtil.escapeString(keywordMap.get("city"));
		String scenicPlace=keywordMap.get(ProductDocument.SCENIC_PLACE);
		String subject = CommonUtil.escapeString(keywordMap.get(ProductDocument.SUBJECT));
		String playNum=CommonUtil.escapeString(keywordMap.get(ProductDocument.PLAY_NUM));
		String hotelType=CommonUtil.escapeString(keywordMap.get(ProductDocument.HOTEL_TYPE));
		String playBrand=CommonUtil.escapeString(keywordMap.get(ProductDocument.PLAY_BRAND));
		String productChannel = keywordMap.get(ProductDocument.PRODUCT_CHANNEL);
		String subProductType = CommonUtil.escapeString(keywordMap.get(ProductDocument.SUB_PRODUCT_TYPE));
		String startPrice=CommonUtil.escapeString(keywordMap.get(ProductDocument.START_PRICE));
		String endPrice=CommonUtil.escapeString(keywordMap.get(ProductDocument.END_PRICE));
		String keyword2 = CommonUtil.escapeString(keywordMap.get("keyword2"));		
		String poductNameSearchKeywords = keywordMap.get("poductNameSearchKeywords");	
		String playFeature=CommonUtil.escapeString(keywordMap.get(ProductDocument.PLAY_FEATURES));
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
				//对 酒店类型进行分词
				booleanQuery.add(new TermQuery(new Term(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN,city)), BooleanClause.Occur.MUST);
//				booleanQuery.add(productquery.getProductAlltoPlaceConent(city), BooleanClause.Occur.MUST);
			}
			// 主题筛选
			if (StringUtils.isNotEmpty(subject)) {
				//log.info("subject_query" + subject);
				//对 主题类型进行分词
//				booleanQuery.add(productquery.getTopicQuery(subject), BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.ROUTE_TOPIC_NOTOKEN,subject)), BooleanClause.Occur.MUST);

			}
			// 游玩天数
			if (StringUtils.isNotEmpty(visitDay)) {
				booleanQuery.add(productquery.getVisitDayQuery(visitDay),
						BooleanClause.Occur.MUST);
			}
			// 标签筛选
			if (StringUtils.isNotEmpty(tagName)) {
				//log.info("tagName_query" + tagName);
				booleanQuery.add(productquery.getTagNameQuery(tagName), BooleanClause.Occur.MUST);
			}
			//展示渠道
			if (StringUtils.isNotEmpty(productChannel)) {
				booleanQuery.add(productquery.getProductChannelQuery(productChannel), BooleanClause.Occur.MUST);
			}
			//途径景点
			if(StringUtils.isNotBlank(scenicPlace)){
				//对 途径景点进行分词
				booleanQuery.add(productquery.getScenicPlaceQuery(scenicPlace),BooleanClause.Occur.MUST);
//				booleanQuery.add(productquery.getScenicPlaceQuery(scenicPlace),BooleanClause.Occur.MUST);
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
			//游玩人数
			if(StringUtils.isNotBlank(playNum)){
				//对游玩人数进行分词
//				booleanQuery.add(productquery.getPlayNumQuery(playNum),BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.PLAY_NUM_NOTOKEN,playNum)), BooleanClause.Occur.MUST);
			}
			//标签组
			if(StringUtils.isNotBlank(tagsGroup)){
				booleanQuery.add(productquery.getTagsGroupQuery(tagsGroup),BooleanClause.Occur.MUST);
			}
			//游玩特色
			if(StringUtils.isNotBlank(playFeature)){
				//对 游玩特色进行分词
//				booleanQuery.add(productquery.getPlayFeaturesQuery(playFeature),BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.PLAY_FEATURES_NOTOKEN,playFeature)), BooleanClause.Occur.MUST);
			}
			//价格
			if (StringUtils.isNotBlank(startPrice)||StringUtils.isNotBlank(endPrice)) {
				booleanQuery.add(productquery.getSellPriceQuery(startPrice, endPrice),BooleanClause.Occur.MUST);
			}
			//产品SUB类型为自由行,出发地必须为ANYWHERE 
			if (StringUtils.isNotEmpty(subProductType)) {
				BooleanQuery subProductTypeQuery = new BooleanQuery();
				BooleanQuery freenessQuery = new BooleanQuery();
				freenessQuery.add(productquery.getSubProductTypeQuery("FREENESS"),BooleanClause.Occur.MUST);
				freenessQuery.add(new TermQuery(new Term(ProductDocument.FROM_DEST, "ANYWHERE")), BooleanClause.Occur.MUST);
				
				if(SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(subProductType)){
					subProductTypeQuery = PlaceQuery.shouldQuerys(freenessQuery,productquery.getSubProductTypeQuery(SUB_PRODUCT_TYPE.HOTEL_SUIT.name()));
				}else{
					subProductTypeQuery = freenessQuery;
				}
				booleanQuery.add(subProductTypeQuery,BooleanClause.Occur.MUST);
			}
			// 对线路产品的名称进行搜索,支持空格分词功能  // 考虑空格分词情况，先切词
			BooleanQuery poductNameSearchQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(poductNameSearchKeywords)) {
//				StringTokenizer stringSplit = new StringTokenizer(poductNameSearchKeywords, " ");
//				while (stringSplit.hasMoreTokens()) {
//					poductNameSearchQuery.add(productquery.getProductNameQuery(stringSplit.nextToken()), BooleanClause.Occur.MUST);
//				}
				//加入分词器的query
				List<String> list=new ArrayList<String>();
				list.add(ProductDocument.PRODUCT_NAME);
				Query query = QueryUtil.getMultiFiedlParserQueryByHasField(poductNameSearchKeywords,list);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			if (poductNameSearchQuery.clauses().size() > 0) {
				booleanQuery.add(poductNameSearchQuery, BooleanClause.Occur.MUST);
			}			
			
//			 目的地,标地
			BooleanQuery toDestQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(keyword) && StringUtils.isEmpty(poductNameSearchKeywords)) {
//				toDestQuery.add(productquery.getProductAlltoPlaceConent(keyword), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getdepartAreaQuery(keyword), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getTopicQuery(keyword), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getTagNameQuery(keyword), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getPlaceKeywordBindQuery(keyword), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getPlayAreaQuery(keyword), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getPlayFeaturesQuery(keyword), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getTrafficQuery(keyword), BooleanClause.Occur.SHOULD);
				
				//加入分词器的query
				Query query = QueryUtil.getMultiFiedlParserQuery(keyword);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			
			/*//出发地,由于fromDest存在，routeType=domesticAll 中查询不到 subPrudctType=FREENESS的自由行产品，需要单独查. *
			BooleanQuery formDestQuery = new BooleanQuery();
			if(StringUtils.isEmpty(subProductType)){
				subProductType="FREENESS";//没有输入产品类型的情况下，系统默认显示自由行产品
			}
			if (StringUtils.isNotEmpty(fromDest) && subProductType.indexOf(",") > 0) {
				formDestQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.SHOULD);
				formDestQuery.add(new TermQuery(new Term(ProductDocument.FROM_DEST, "ANYWHERE")), BooleanClause.Occur.SHOULD);
			} else if (StringUtils.isNotEmpty(fromDest) && !subProductType.equals("FREENESS")) {
				formDestQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.MUST);
			}
			if (formDestQuery.clauses().size() > 0) {
				booleanQuery.add(formDestQuery, BooleanClause.Occur.MUST);
			}*/
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
