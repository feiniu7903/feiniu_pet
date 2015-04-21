package com.lvmama.search.lucene.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.CommonUtil;

public class NewRouteQueryUtil {
	
private static final Log log = LogFactory.getLog(NewRouteQueryUtil.class);	
	
	/**
	 * Group+Freelong搜索条件
	 * @param keywordMap
	 * @return
	 */
	public static BooleanQuery getGroupFreelongQuery(Map<String, String> keywordMap) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		String fromDest = CommonUtil.escapeString(keywordMap.get(ProductDocument.FROM_DEST));
		String toDest = CommonUtil.escapeString(keywordMap.get(ProductDocument.TO_DEST));
		String city = CommonUtil.escapeString(keywordMap.get(ProductDocument.CITY));
		String subject = CommonUtil.escapeString(keywordMap.get(ProductDocument.SUBJECT));
		String tagName = CommonUtil.escapeString(keywordMap.get(ProductDocument.TAGS_NAME));
		String visitDay = CommonUtil.escapeString(keywordMap.get(ProductDocument.VISIT_DAY));	
		String subProductType = CommonUtil.escapeString(keywordMap.get(ProductDocument.SUB_PRODUCT_TYPE));		
		String productChannel = keywordMap.get(ProductDocument.PRODUCT_CHANNEL);
		String startPrice=CommonUtil.escapeString(keywordMap.get(ProductDocument.START_PRICE));
		String endPrice=CommonUtil.escapeString(keywordMap.get(ProductDocument.END_PRICE));
		String keyword2 = CommonUtil.escapeString(keywordMap.get("keyword2"));		
		String poductNameSearchKeywords = keywordMap.get("poductNameSearchKeywords");	
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
//				booleanQuery.add(productquery.getProductAlltoPlaceConent(city), BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN,city)), BooleanClause.Occur.MUST);
			}
			// 主题筛选
			if (StringUtils.isNotEmpty(subject)) {
//				booleanQuery.add(productquery.getTopicQuery(subject), BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.ROUTE_TOPIC_NOTOKEN,subject)), BooleanClause.Occur.MUST);
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
			//标签组
			if(StringUtils.isNotBlank(tagsGroup)){
				booleanQuery.add(productquery.getTagsGroupQuery(tagsGroup),BooleanClause.Occur.MUST);
			}
			//价格
			if (StringUtils.isNotBlank(startPrice)||StringUtils.isNotBlank(endPrice)) {
				booleanQuery.add(productquery.getSellPriceQuery(startPrice, endPrice),BooleanClause.Occur.MUST);
			}
			// 产品SUB类型为FREENESS_FOREIGN,FREENESS_LONG,GROUP_LONG,GROUP_FOREIGN,GROUP,SELFHELP_BUS
			BooleanQuery subProductTypeQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(subProductType)) {
				// log.info("query_rountType" + subProductType);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("FREENESS_FOREIGN"), BooleanClause.Occur.SHOULD);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("FREENESS_LONG"), BooleanClause.Occur.SHOULD);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("GROUP_LONG"), BooleanClause.Occur.SHOULD);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("GROUP_FOREIGN"), BooleanClause.Occur.SHOULD);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("GROUP"), BooleanClause.Occur.SHOULD);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("SELFHELP_BUS"), BooleanClause.Occur.SHOULD);

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
			// 出发地,
			if (StringUtils.isNotEmpty(fromDest)) {
				booleanQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.MUST);
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
			
//			if (toDestQuery.clauses().size() > 0) {
//				booleanQuery.add(toDestQuery, BooleanClause.Occur.MUST);
//			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return booleanQuery;
	}	
	
	
	/**
	 * AROUND+Freetour, 
	 * 搜索条件
	 * @param keywordMap
	 * @return
	 */
	public static BooleanQuery getFreetourAroundWithFromDestQuery(Map<String, String> keywordMap) {
		BooleanQuery booleanQuery = new BooleanQuery();
		try {
			BooleanQuery freetourQuery = NewFreetourQueryUtil.getNewFreetourQuery(keywordMap);
			//FREETOUR的查询逻辑不变，如果 tc.isKeywordIsFromDest()  = true 则周边当地跟团游的查询逻辑为 FROM_DEST = KEYWORD 
			if ("true".equals(keywordMap.get("isKeywordIsFromDest"))) {
				keywordMap.put(ProductDocument.FROM_DEST, keywordMap.get("keyword"));
				keywordMap.remove("keyword");
			}
			BooleanQuery aroundTypeQuery = NewAroundQueryUtil.getNewAroundQuery(keywordMap);
			if (freetourQuery.clauses().size() > 0) {
				booleanQuery.add(freetourQuery, BooleanClause.Occur.SHOULD);
			}
			if (aroundTypeQuery.clauses().size() > 0 && !"true".equals(keywordMap.get("isKeywordIsFromDest"))) {
				booleanQuery.add(aroundTypeQuery, BooleanClause.Occur.SHOULD);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return booleanQuery;
	}

	public static String[] splitKeyword(String keyword) {
		String[] key = keyword.split("_");
		return key;
	}

}
