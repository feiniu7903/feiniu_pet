package com.lvmama.search.lucene.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lvmama.comm.search.vo.VerHotelSearchVO;
import com.lvmama.comm.utils.GeoLocation;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant.PROD_TAG_GROUP_NAME;
import com.lvmama.search.lucene.LuceneContext;
import com.lvmama.search.lucene.analyser.AnalyzerUtil;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.PlaceHotelDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.document.VerhotelDocument;
import com.lvmama.search.lucene.document.VerplaceDocument;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LocalCacheManager;

public class QueryUtil {

	private static final Log log = LogFactory.getLog(QueryUtil.class);

	/**
	 * 获得自动补全的目的地
	 * 
	 * @param keywordMap
	 * @return
	 */
	public static Query getSearchAutoCompletePlace(Map<String, String> keywordMap) {
		PlaceFromDestQuery placeFromDestQuery = new PlaceFromDestQuery();
		
		String pinYin = CommonUtil.escapeString(keywordMap.get("pinYin"));
		String name = CommonUtil.escapeString(keywordMap.get("name"));

		BooleanQuery booleanQuery = new BooleanQuery();
		if (StringUtils.isNotEmpty(pinYin)) {
			booleanQuery.add(placeFromDestQuery.getPlacePinyinWildcardQuery(pinYin), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(placeFromDestQuery.getPlaceNameWildcardQuery(name), BooleanClause.Occur.SHOULD);
		}

		BooleanQuery booleanQuery2 = new BooleanQuery();
		booleanQuery2.add(placeFromDestQuery.getStageQuery(keywordMap.get("stage")), BooleanClause.Occur.MUST);
		booleanQuery2.add(placeFromDestQuery.getFromDestNameQuery(keywordMap.get("fromDestName")), BooleanClause.Occur.MUST);

		if ("around".equals(keywordMap.get("channel"))) {
			booleanQuery2.add(placeFromDestQuery.getDestPeripheryNumQuery(), BooleanClause.Occur.MUST);
		} else if ("destroute".equals(keywordMap.get("channel"))) {
			booleanQuery2.add(placeFromDestQuery.getDestInternalNumQuery(), BooleanClause.Occur.MUST);
		}
		if ("abroad".equals(keywordMap.get("channel"))) {
			booleanQuery2.add(placeFromDestQuery.getDestAbroadNumQuery(), BooleanClause.Occur.MUST);
		}

		booleanQuery2.add(placeFromDestQuery.getIsValidQuery("Y"), BooleanClause.Occur.MUST);
		booleanQuery2.add(booleanQuery, BooleanClause.Occur.MUST);
		return booleanQuery2;
	}


	public static Query fromDestIsZoneOfKeywordQuery(String fromDest,String keyword){
		BooleanQuery booleanQuery = new BooleanQuery();
		//List<String> tmpkeywordList=(ArrayList<String>)LocalCacheManager.get(keyword);
		List<String> tmpkeywordList=(ArrayList<String>)LocalSession.get(keyword);
		List<String[]> keywordList=new ArrayList<String[]>();
		for (String keywords : tmpkeywordList) {
			keywordList.add(keywords.split(","));
		}
		if(StringUtils.isNotEmpty(keyword)){		
			BooleanQuery bq = new BooleanQuery();
			bq.add(PlaceQuery.getCityQuery(fromDest), BooleanClause.Occur.SHOULD);
			bq.add(new TermQuery(new Term(PlaceDocument.PROVINCE, fromDest)), BooleanClause.Occur.SHOULD);
			booleanQuery.add(bq, BooleanClause.Occur.MUST);
			//增加keyword 分词同义词之后的query
			BooleanQuery listquery = new BooleanQuery();
			if(keywordList!=null && keywordList.size()>0){
				for(String[] keywords:keywordList){
					if(keywords!=null && keywords.length>0){
						//定义一个数组的query
						BooleanQuery arrayquery = new BooleanQuery();
						for(int i=0;i<keywords.length;i++){
							String name=keywords[i];
							if(StringUtils.isNotEmpty(name)){
								BooleanQuery multifieldquery=new BooleanQuery();
								WildcardQuery query1=new WildcardQuery(new Term(PlaceDocument.NAME,"*" + name + "*"));
								WildcardQuery query2=new WildcardQuery(new Term(PlaceDocument.OWNFIELD,"*" + name + "*"));
								multifieldquery.add(query1, BooleanClause.Occur.SHOULD);
								multifieldquery.add(query2, BooleanClause.Occur.SHOULD);
								arrayquery.add(multifieldquery, BooleanClause.Occur.MUST);
							}
						}
						listquery.add(arrayquery, BooleanClause.Occur.SHOULD);
					}
				}
			
			}
			
			booleanQuery.add(listquery, BooleanClause.Occur.MUST);	
//			booleanQuery.add(new TermQuery(new Term(PlaceDocument.NAME, keyword)), BooleanClause.Occur.MUST);			
		}
		return booleanQuery;
	}
	
	public static Query getCityQuery(String keyword){
		BooleanQuery booleanQuery = new BooleanQuery();
		if(StringUtils.isNotEmpty(keyword)){			
			booleanQuery.add(PlaceQuery.getStageQuery("1"), BooleanClause.Occur.MUST);
			booleanQuery.add(new TermQuery(new Term(PlaceDocument.NAME, keyword)), BooleanClause.Occur.MUST);
		}
		return booleanQuery;
	}

	public static Query getPlaceQueryFullMath(String keyword,String stage){
		BooleanQuery booleanQuery = new BooleanQuery();
		if(StringUtils.isNotEmpty(keyword)){			
			booleanQuery.add(PlaceQuery.getStageQuery(stage), BooleanClause.Occur.MUST);
			BooleanQuery booleanQuery1 = new BooleanQuery(); 
			booleanQuery1.add(new TermQuery(new Term(PlaceDocument.QUERY_NAME, keyword)), BooleanClause.Occur.SHOULD);
			booleanQuery1.add(new TermQuery(new Term(PlaceDocument.PIN_YIN, keyword)), BooleanClause.Occur.SHOULD);
			booleanQuery1.add(new TermQuery(new Term(PlaceDocument.HFKW, keyword)), BooleanClause.Occur.SHOULD);
			booleanQuery.add(booleanQuery1, BooleanClause.Occur.MUST);
		}
		return booleanQuery;
	}
	public static Query getSceneQuery(String fromDest,String keyword){
		BooleanQuery booleanQuery = new BooleanQuery();
		if(StringUtils.isNotEmpty(fromDest)){
			booleanQuery.add(PlaceQuery.getDestNameIdsQuery(fromDest), BooleanClause.Occur.MUST);
		}
		if(StringUtils.isNotEmpty(keyword)){
			booleanQuery.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.MUST);			
			booleanQuery.add(new TermQuery(new Term(PlaceDocument.NAME, keyword)), BooleanClause.Occur.MUST);
		}
		return booleanQuery;
	}
	
	public static Query getCityIdQuery(String cityId){
		Query query = null;		
		if (StringUtils.isNotEmpty(cityId)) {
			query = PlaceQuery.getShortIdQuery(cityId);
		}
		return query;
	}

	public static Query getPlaceQuery(String toDest) throws IOException {
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.MUST);// stage=2指景点
		booleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);//有门票
		BooleanQuery booleanQuery2 = new BooleanQuery();
		booleanQuery2.add(PlaceQuery.getCityQuery(toDest), BooleanClause.Occur.SHOULD);//city和cityId共用一个字段
		booleanQuery2.add(PlaceQuery.getCapitalQuery(toDest), BooleanClause.Occur.SHOULD);
		booleanQuery.add(booleanQuery2, BooleanClause.Occur.MUST);
		//System.out.println(booleanQuery);
		return booleanQuery;
	}

	/**
	 * 自由自在关键字为空或匹配为空时按SEQ推荐景点
	 * 
	 * @throws IOException
	 */
	public static Query getFreenessKeywordUndoQuery() throws IOException {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productQuery = new ProductQuery();
		booleanQuery.add(productQuery.getSubProductTypeQuery("FREENESS"), BooleanClause.Occur.MUST);
		booleanQuery.add(productQuery.getIsValid("Y"), BooleanClause.Occur.MUST);
		booleanQuery.add(productQuery.getProductChannelQuery("FRONTEND"), BooleanClause.Occur.MUST);

		return booleanQuery;
	}

	/**
	 * 自由自在下拉提示补全目的地
	 * 
	 * @param keywordMap
	 * @return
	 */
	public static Query getDestWithFreenessQueryInProduct(Map<String, String> keywordMap) throws IOException {
		String toDest = CommonUtil.escapeString(keywordMap.get("toDest"));
		//log.info(toDest);
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productQuery = new ProductQuery();
		if (StringUtils.isNotEmpty(toDest)) {
			booleanQuery.add(productQuery.getProductAlltoPlaceConent(toDest), BooleanClause.Occur.MUST);
			booleanQuery.add(productQuery.getIsValid("Y"), BooleanClause.Occur.MUST);
			booleanQuery.add(productQuery.getSubProductTypeQuery("FREENESS"), BooleanClause.Occur.MUST);
		}
		return booleanQuery;
	}


	/**
	 * 自由行下拉提示补全城市的Query 仅搜索有自由行产品的城市
	 * 
	 * @param keywordMap
	 * @return
	 */
	public static Query getDestWithFreenessQuery(Map<String, String> keywordMap) throws IOException {
		String pinYin = CommonUtil.escapeString(keywordMap.get("pinYin"));
		String name = CommonUtil.escapeString(keywordMap.get("name"));
		String tag = CommonUtil.escapeString(keywordMap.get("tag"));
		String subject = CommonUtil.escapeString(keywordMap.get("subject"));
		BooleanQuery booleanQuery = new BooleanQuery();
		if (StringUtils.isNotEmpty(pinYin)) {
			booleanQuery.add(PlaceQuery.getPinYinQuery(pinYin), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(PlaceQuery.getNameQuery(name), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(PlaceQuery.getCityQuery(name), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(PlaceQuery.getCapitalQuery(name), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(subject)) {
			booleanQuery.add(PlaceQuery.getDestSubjectsQuery(subject), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(tag)) {
			booleanQuery.add(PlaceQuery.getDestTagsNameQuery(tag), BooleanClause.Occur.SHOULD);
		}
		/**
		 * 只考虑有自由行买的城市
		 */
		BooleanQuery booleanQuery2 = new BooleanQuery();
		booleanQuery2.add(PlaceQuery.getdestFreenessNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
		booleanQuery2.add(booleanQuery, BooleanClause.Occur.MUST);
		return booleanQuery2;
	}
	/**
	 * 门票景点
	 * 
	 * @throws IOException
	 */
	public static Query getPlaceWithPlaceIds(Set<String> placeIds) {
		BooleanQuery booleanQuery = new BooleanQuery();
		BooleanQuery idsQuery = new BooleanQuery();
		for(String id : placeIds){
			idsQuery.add(PlaceQuery.getPlaceIdQuery(id), BooleanClause.Occur.SHOULD);
		}
		if (idsQuery.clauses().size() > 0) {
			booleanQuery.add(idsQuery, BooleanClause.Occur.MUST);
		}
		booleanQuery.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.MUST);
		booleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
		return booleanQuery;
	}
	public static Query getPlaceWithPlaceIdsQuery(Map<String, String> keywordMap,String... placeIds) {
		return getPlaceWithTicketQuery(keywordMap,"2","place",null,placeIds);
	}
	public static Query getPlaceWithTicketQuery(Map<String, String> keywordMap,String stage,String... cityIds) {
		return getPlaceWithTicketQuery(keywordMap,stage,"city",null,cityIds);
	}
	
	public static Query getPlaceWithTicketQueryForClient(Map<String, String> keywordMap,String stage,String... cityIds) {
		return getPlaceWithTicketQueryForClient(keywordMap,stage,"city",null,cityIds);
	}
	
	/**
	 * 分词query
	 * 
	 * @throws IOException
	 */
	public  static Query getMultiFiedlParserQuery(String keyword)
			throws ParseException {
		//获得keywordList
//		List<String> tmpkeywordList=(ArrayList<String>)LocalCacheManager.get(keyword);
		List<String> tmpkeywordList=(ArrayList<String>)LocalSession.get(keyword);
		List<String[]> keywordList=new ArrayList<String[]>();
		for (String keywords : tmpkeywordList) {
			keywordList.add(keywords.split(","));
		}
		BooleanQuery listquery = new BooleanQuery();
		if(keywordList!=null && keywordList.size()>0){
		
		for(String[] keywords:keywordList){
			if(keywords!=null && keywords.length>0){
				//定义一个数组的query
				BooleanQuery arrayquery = new BooleanQuery();
				for(int i=0;i<keywords.length;i++){
					String name=keywords[i];
					if(StringUtils.isNotEmpty(name)){
						BooleanQuery multifieldquery=new BooleanQuery();
//						WildcardQuery query1=new WildcardQuery(new Term(ProductDocument.PRODUCT_NAME,"*" + name + "*"));
						TermQuery query2=new TermQuery(new Term(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT,name));
						WildcardQuery query3=new WildcardQuery(new Term(ProductDocument.DEPART_AREA,"*" + name + "*"));
						WildcardQuery query4=new WildcardQuery(new Term(ProductDocument.ROUTE_TOPIC,"*" + name + "*"));
						TermQuery query5=new TermQuery(new Term(ProductDocument.TAGS_NAME,name));
						WildcardQuery query6=new WildcardQuery(new Term(ProductDocument.PLACE_KEYWORD_BIND,"*" + name + "*"));
						WildcardQuery query7=new WildcardQuery(new Term(ProductDocument.PLAY_AREA,"*" + name + "*"));
						WildcardQuery query8=new WildcardQuery(new Term(ProductDocument.PLAY_FEATURES,"*" + name + "*"));
						WildcardQuery query9=new WildcardQuery(new Term(ProductDocument.PLAY_FEATURES,"*" + name + "*"));
						WildcardQuery query10=new WildcardQuery(new Term(ProductDocument.OWNFIELD,"*" + name + "*"));
//						multifieldquery.add(query1, BooleanClause.Occur.SHOULD);
						multifieldquery.add(query2, BooleanClause.Occur.SHOULD);
						multifieldquery.add(query3, BooleanClause.Occur.SHOULD);
						multifieldquery.add(query4, BooleanClause.Occur.SHOULD);
						multifieldquery.add(query5, BooleanClause.Occur.SHOULD);
						multifieldquery.add(query6, BooleanClause.Occur.SHOULD);
						multifieldquery.add(query7, BooleanClause.Occur.SHOULD);
						multifieldquery.add(query8, BooleanClause.Occur.SHOULD);
						multifieldquery.add(query9, BooleanClause.Occur.SHOULD);
						multifieldquery.add(query10, BooleanClause.Occur.SHOULD);
						arrayquery.add(multifieldquery, BooleanClause.Occur.MUST);
					}
					
				}
				listquery.add(arrayquery, BooleanClause.Occur.SHOULD);
			}
		}
		}
		return listquery;
	}
	
	/**
	 * 分词query
	 * 通过方法，可以自己传进field来，用list封装
	 * @throws IOException
	 */
	public  static Query getMultiFiedlParserQueryByHasField(String keyword,List<String> fieldlist)
			throws ParseException {
		//获得keywordList
//		List<String> tmpkeywordList=(ArrayList<String>)LocalCacheManager.get(keyword);
		List<String> tmpkeywordList=(ArrayList<String>)LocalSession.get(keyword);
		List<String[]> keywordList=new ArrayList<String[]>();
		for (String keywords : tmpkeywordList) {
			keywordList.add(keywords.split(","));
		}
		BooleanQuery listquery = new BooleanQuery();
		if(keywordList!=null && keywordList.size()>0){
		
		for(String[] keywords:keywordList){
			if(keywords!=null && keywords.length>0){
				//定义一个数组的query
				BooleanQuery arrayquery = new BooleanQuery();
				for(int i=0;i<keywords.length;i++){
					String name=keywords[i];
					if(StringUtils.isNotEmpty(name)){
						BooleanQuery multifieldquery=new BooleanQuery();
						if(fieldlist!=null && fieldlist.size()>0){
							for(String field:fieldlist){
								WildcardQuery termquery=new WildcardQuery(new Term(field,"*" + name + "*"));
								multifieldquery.add(termquery, BooleanClause.Occur.SHOULD);
							}
						}
						arrayquery.add(multifieldquery, BooleanClause.Occur.MUST);
					}
					
				}
				listquery.add(arrayquery, BooleanClause.Occur.SHOULD);
			}
		}
		}
		return listquery;
	}	
	
	
	/**
	 * 分词query
	 * 通过方法，可以自己传进field来，用list封装
	 * @throws IOException
	 */
	public  static void builderMultiFiedlParserQueryByHasFieldForClientPlaceSearch(String keyword,List<String> fieldlist,BooleanQuery q,
			List<String[]> keywordList)
			throws ParseException {
		BooleanQuery listquery = new BooleanQuery();
		if(keywordList!=null && keywordList.size()>0){
		
		for(String[] keywords:keywordList){
			if(keywords!=null && keywords.length>0){
				//定义一个数组的query
				BooleanQuery arrayquery = new BooleanQuery();
				for(int i=0;i<keywords.length;i++){
					String name=keywords[i];
					if(StringUtils.isNotEmpty(name)){
						BooleanQuery multifieldquery=new BooleanQuery();
						
						
						if(fieldlist!=null && fieldlist.size()>0){
							for(String field:fieldlist){
								WildcardQuery termquery=new WildcardQuery(new Term(field,"*" + name + "*"));
								multifieldquery.add(termquery, BooleanClause.Occur.SHOULD);
							}
						}
						arrayquery.add(multifieldquery, BooleanClause.Occur.MUST);
					}
					
				}
				listquery.add(arrayquery, BooleanClause.Occur.MUST);
			}
			
		}
		q.add(listquery, BooleanClause.Occur.MUST);
		}
	}	
	/**
	 * 门票景点
	 * 
	 * @throws IOException
	 */
	private static Query getPlaceWithTicketQuery(Map<String, String> keywordMap,String stage,String idType,List<String[]> keywordList,String... ids) {
		String name = CommonUtil.escapeString(keywordMap.get("name"));
		String city = CommonUtil.escapeString(keywordMap.get("city"));
		String tag = CommonUtil.escapeString(keywordMap.get("tag"));
		String subject = CommonUtil.escapeString(keywordMap.get("subject"));
		String fromPage = CommonUtil.escapeString(keywordMap.get("fromPage"));
		String isfromClient = CommonUtil.escapeString(keywordMap.get("isfromClient"));
		String placeActivity = keywordMap.get("placeActivity");
		String placeActivityHave = keywordMap.get("placeActivityHave");
		BooleanQuery booleanQuery = new BooleanQuery();
		BooleanQuery keywordQuery = new BooleanQuery();
		try{
//			if (StringUtils.isNotEmpty(name)) {
//				keywordQuery.add(PlaceQuery.getNameQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getDestSubjectsQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getCityQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getDestTagsNameQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getPlaceActivityQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getHfkwWildcardQuery(name), BooleanClause.Occur.SHOULD);
//			}
//			if (keywordQuery.clauses().size() > 0) {
//				booleanQuery.add(keywordQuery, BooleanClause.Occur.MUST);
//			}
			//定义多个同义词的Listquery
			BooleanQuery listquery = new BooleanQuery();
			if(keywordList!=null && keywordList.size()>0){
			
			for(String[] keywords:keywordList){
				if(keywords!=null && keywords.length>0){
					//定义一个数组的query
					BooleanQuery arrayquery = new BooleanQuery();
					for(int i=0;i<keywords.length;i++){
						 name=keywords[i];
						if(StringUtils.isNotEmpty(name)){
							BooleanQuery multifieldquery=new BooleanQuery();
							WildcardQuery query1=new WildcardQuery(new Term(PlaceDocument.NAME,"*" + name + "*"));
							Query query2=new TermQuery(new Term(PlaceDocument.DEST_SUBJECTS, name));
							WildcardQuery query3=new WildcardQuery(new Term(PlaceDocument.CITY,"*" + name + "*"));
							WildcardQuery query4=new WildcardQuery(new Term(PlaceDocument.DEST_TAGS_NAME,"*" + name + "*"));
							WildcardQuery query5=new WildcardQuery(new Term(PlaceDocument.PLACE_ACTIVITY,"*" + name + "*"));
							WildcardQuery query6=new WildcardQuery(new Term(PlaceDocument.HFKW,"*" + name + "*"));
							WildcardQuery query7=new WildcardQuery(new Term(PlaceDocument.OWNFIELD,"*" + name + "*"));
							multifieldquery.add(query1, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query2, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query3, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query4, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query5, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query6, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query7, BooleanClause.Occur.SHOULD);
							arrayquery.add(multifieldquery, BooleanClause.Occur.MUST);
						}
						
					}
					listquery.add(arrayquery, BooleanClause.Occur.SHOULD);
				}
			}
			booleanQuery.add(listquery,BooleanClause.Occur.MUST);
			//加入分词器的query
			}
			
			
			
			
			if("city".equals(idType)){
				BooleanQuery cityIdsQuery = new BooleanQuery();
				for (String cityId : ids) {
					if (!StringUtil.isEmptyString(cityId)) {
						cityIdsQuery.add(PlaceQuery.getDestNameIdsQuery(cityId), BooleanClause.Occur.SHOULD);
					}
				}
				if (cityIdsQuery.clauses().size() > 0) {
					booleanQuery.add(cityIdsQuery, BooleanClause.Occur.MUST);
				}
			}else if("place".equals(idType)){
				BooleanQuery idsQuery = new BooleanQuery();
				for(String id : ids){
					idsQuery.add(PlaceQuery.getPlaceIdQuery(id), BooleanClause.Occur.SHOULD);
				}
				if (idsQuery.clauses().size() > 0) {
					booleanQuery.add(idsQuery, BooleanClause.Occur.MUST);
				}
			}
			if (StringUtils.isNotEmpty(city)) {
				//对城市进行分词
				Query query = getQueryParser(city,PlaceDocument.CITY);
//				booleanQuery.add(placeQuery.getCityQuery(city), BooleanClause.Occur.MUST);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			if (StringUtils.isNotEmpty(subject)) {
				//对 主题进行分词
//				Query query = getQueryParser(subject,PlaceDocument.DEST_SUBJECTS);
//				booleanQuery.add(query, BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(PlaceDocument.DEST_SUBJECTS_NOTOKEN,subject)), BooleanClause.Occur.MUST);
			}
			if (StringUtils.isNotEmpty(tag)) {
				booleanQuery.add(PlaceQuery.getDestTagsNameQuery(tag), BooleanClause.Occur.MUST);
			}
			if (StringUtils.isNotEmpty(placeActivity)) {
				//对 活动进行分词
//				Query query = getQueryParser(placeActivity,PlaceDocument.PLACE_ACTIVITY);
//				booleanQuery.add(query, BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(PlaceDocument.PLACE_ACTIVITY_NOTOKEN,placeActivity)), BooleanClause.Occur.MUST);
			}
			if (StringUtils.isNotEmpty(placeActivityHave)) {
				booleanQuery.add(PlaceQuery.getPlaceActivityQueryHave(), BooleanClause.Occur.MUST);
			}
		/**
		 * 只考虑有门票买的城市
		 */
		booleanQuery.add(PlaceQuery.getStageQuery(stage), BooleanClause.Occur.MUST);
		//加入最小价格>0
		Query minquery=getMinPriceQuery(1, Integer.MAX_VALUE, true, true, PlaceDocument.PRODUCTS_PRICE);
//		booleanQuery.add(minquery, BooleanClause.Occur.MUST);
		if (StringUtils.isNotEmpty(fromPage)) {// 点评过来的搜索请求不考虑是否有门票
			booleanQuery.add(PlaceQuery.getTicketNumQuery(0, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
		} else {
			booleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
		}
//		判断是否从客户端过来
		if(StringUtils.isNotEmpty(isfromClient)&& isfromClient.equals("1")){
			booleanQuery.add(new TermQuery(new Term(PlaceDocument.CASH_REFUND, "-1")), BooleanClause.Occur.MUST_NOT);
		}
		return booleanQuery;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	
	/**
	 * 门票景点
	 * 
	 * @throws IOException
	 */
	private static Query getPlaceWithTicketQueryForClient(Map<String, String> keywordMap,String stage,String idType,List<String[]> keywordList,String... ids) {
		String name = CommonUtil.escapeString(keywordMap.get("name"));
		String city = CommonUtil.escapeString(keywordMap.get("city"));
		String tag = CommonUtil.escapeString(keywordMap.get("tag"));
		String subject = CommonUtil.escapeString(keywordMap.get("subject"));
		String placeActivity = keywordMap.get("placeActivity");
		String placeActivityHave = keywordMap.get("placeActivityHave");
		BooleanQuery booleanQuery = new BooleanQuery();
		try{
//			if (StringUtils.isNotEmpty(name)) {
//				keywordQuery.add(PlaceQuery.getNameQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getDestSubjectsQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getCityQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getDestTagsNameQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getPlaceActivityQuery(name), BooleanClause.Occur.SHOULD);
//				keywordQuery.add(placeQuery.getHfkwWildcardQuery(name), BooleanClause.Occur.SHOULD);
//			}
//			if (keywordQuery.clauses().size() > 0) {
//				booleanQuery.add(keywordQuery, BooleanClause.Occur.MUST);
//			}
			//定义多个同义词的Listquery
			BooleanQuery listquery = new BooleanQuery();
			if(keywordList!=null && keywordList.size()>0){
			
			for(String[] keywords:keywordList){
				if(keywords!=null && keywords.length>0){
					//定义一个数组的query
					BooleanQuery arrayquery = new BooleanQuery();
					for(int i=0;i<keywords.length;i++){
						 name=keywords[i];
						if(StringUtils.isNotEmpty(name)){
							BooleanQuery multifieldquery=new BooleanQuery();
							WildcardQuery query1=new WildcardQuery(new Term(PlaceDocument.NAME,"*" + name + "*"));
							Query query2=new TermQuery(new Term(PlaceDocument.DEST_SUBJECTS, name));
							WildcardQuery query3=new WildcardQuery(new Term(PlaceDocument.CITY,"*" + name + "*"));
							WildcardQuery query4=new WildcardQuery(new Term(PlaceDocument.DEST_TAGS_NAME,"*" + name + "*"));
							WildcardQuery query5=new WildcardQuery(new Term(PlaceDocument.PLACE_ACTIVITY,"*" + name + "*"));
							WildcardQuery query6=new WildcardQuery(new Term(PlaceDocument.HFKW,"*" + name + "*"));
							WildcardQuery query7=new WildcardQuery(new Term(PlaceDocument.OWNFIELD,"*" + name + "*"));
							multifieldquery.add(query1, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query2, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query3, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query4, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query5, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query6, BooleanClause.Occur.SHOULD);
							multifieldquery.add(query7, BooleanClause.Occur.SHOULD);
							arrayquery.add(multifieldquery, BooleanClause.Occur.MUST);
						}
						
					}
					listquery.add(arrayquery, BooleanClause.Occur.SHOULD);
				}
			}
			booleanQuery.add(listquery,BooleanClause.Occur.MUST);
			//加入分词器的query
			}
			
			
			
			
			if("city".equals(idType)){
				BooleanQuery cityIdsQuery = new BooleanQuery();
				for (String cityId : ids) {
					if (!StringUtil.isEmptyString(cityId)) {
						cityIdsQuery.add(PlaceQuery.getDestNameIdsQuery(cityId), BooleanClause.Occur.SHOULD);
					}
				}
				if (cityIdsQuery.clauses().size() > 0) {
					booleanQuery.add(cityIdsQuery, BooleanClause.Occur.MUST);
				}
			}else if("place".equals(idType)){
				BooleanQuery idsQuery = new BooleanQuery();
				for(String id : ids){
					idsQuery.add(PlaceQuery.getPlaceIdQuery(id), BooleanClause.Occur.SHOULD);
				}
				if (idsQuery.clauses().size() > 0) {
					booleanQuery.add(idsQuery, BooleanClause.Occur.MUST);
				}
			}
			if (StringUtils.isNotEmpty(city)) {
				//对城市进行分词
				Query query = getQueryParser(city,PlaceDocument.CITY);
//				booleanQuery.add(placeQuery.getCityQuery(city), BooleanClause.Occur.MUST);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			if (StringUtils.isNotEmpty(subject)) {
				//对 主题进行分词
//				Query query = getQueryParser(subject,PlaceDocument.DEST_SUBJECTS);
//				booleanQuery.add(query, BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(PlaceDocument.DEST_SUBJECTS_NOTOKEN,subject)), BooleanClause.Occur.MUST);
			}
			if (StringUtils.isNotEmpty(tag)) {
				booleanQuery.add(PlaceQuery.getDestTagsNameQuery(tag), BooleanClause.Occur.MUST);
			}
			if (StringUtils.isNotEmpty(placeActivity)) {
				//对 活动进行分词
//				Query query = getQueryParser(placeActivity,PlaceDocument.PLACE_ACTIVITY);
//				booleanQuery.add(query, BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(PlaceDocument.PLACE_ACTIVITY_NOTOKEN,placeActivity)), BooleanClause.Occur.MUST);
			}
			if (StringUtils.isNotEmpty(placeActivityHave)) {
				booleanQuery.add(PlaceQuery.getPlaceActivityQueryHave(), BooleanClause.Occur.MUST);
			}
		/**
		 * 只考虑有门票买的城市
		 */
		booleanQuery.add(PlaceQuery.getStageQuery(stage), BooleanClause.Occur.MUST);

		/**
		 * 必须有门票
		 */
		booleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
		
		
		booleanQuery.add(new TermQuery(new Term(PlaceDocument.CASH_REFUND, "-1")), BooleanClause.Occur.MUST_NOT);

		return booleanQuery;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public static Query getMinPriceQuery(long min, long max, boolean minInclud, boolean maxInclud,String field) {
		return NumericRangeQuery.newLongRange(field, min, max, minInclud, maxInclud);
	}
	
	public static Query getQueryParser(String city,String field) throws ParseException {
		QueryParser parser=new QueryParser(LuceneContext.LUCENE_VERSION,field ,AnalyzerUtil.getIkAnalyzer() );
		Query query=parser.parse(city);
		return query;
	}

	public static Query getQueryParserAnd(String city,String field) throws ParseException {
		QueryParser parser=new QueryParser(LuceneContext.LUCENE_VERSION,field ,AnalyzerUtil.getIkAnalyzer() );
		parser.setDefaultOperator(QueryParser.AND_OPERATOR);
		Query query=parser.parse(city);
		return query;
	}

	/**
	 * 门票关键字为空或匹配为空时按SEQ推荐景点
	 * 
	 * @throws IOException
	 */
	public static Query getTicketKeywordUndoQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();
		// 只考虑STAGE=2
		booleanQuery.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.MUST);
		// 只考虑有门票买的
		booleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);

		return booleanQuery;
	}

	/**
	 * 自由自在下拉补全：查景点
	 * 
	 * @throws IOException
	 */
	public static Query getPlaceWithFreenessQuery(Map<String, String> keywordMap) throws IOException {
		String pinYin = CommonUtil.escapeString(keywordMap.get("pinYin"));
		String name = CommonUtil.escapeString(keywordMap.get("name"));
		String tag = CommonUtil.escapeString(keywordMap.get("tag"));
		String subject = CommonUtil.escapeString(keywordMap.get("subject"));
		BooleanQuery booleanQuery = new BooleanQuery();
		if (StringUtils.isNotEmpty(pinYin)) {
			booleanQuery.add(PlaceQuery.getPinYinQuery(pinYin), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(PlaceQuery.getNameQuery(name), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(subject)) {
			booleanQuery.add(PlaceQuery.getDestSubjectsQuery(subject), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(tag)) {
			booleanQuery.add(PlaceQuery.getDestTagsNameQuery(tag), BooleanClause.Occur.SHOULD);
		}
		BooleanQuery booleanQuery2 = new BooleanQuery();
		/**
		 * 只考虑STAGE=2
		 */
		booleanQuery2.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.MUST);
		/**
		 * 只考虑有自由自在买的
		 */
		booleanQuery2.add(PlaceQuery.getdestFreenessNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
		booleanQuery2.add(booleanQuery, BooleanClause.Occur.MUST);
		return booleanQuery2;
	}

	public static Query getSearchCityQuery(Map<String, String> keywordMap) {
		String pinYin = CommonUtil.escapeString(keywordMap.get("pinYin"));
		String hasHotel = CommonUtil.escapeString(keywordMap.get("hasHotel"));
		String name = CommonUtil.escapeString(keywordMap.get("name"));
		BooleanQuery booleanQuery = new BooleanQuery();
		if (StringUtils.isNotEmpty(pinYin)) {
			booleanQuery.add(PlaceQuery.getPinYinQuery(pinYin), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(PlaceQuery.getNameQuery(name), BooleanClause.Occur.SHOULD);
		}
		BooleanQuery booleanQuery2 = new BooleanQuery();
		if (StringUtils.isNotEmpty(hasHotel)) {
			booleanQuery2.add(PlaceQuery.getHasHotelQuery(hasHotel), BooleanClause.Occur.MUST);
		}
		booleanQuery2.add(booleanQuery, BooleanClause.Occur.MUST);
		return booleanQuery2;
	}

	public static Query getSearchSurroundingPlacesQuery(Map<String, String> keywordMap) {
		String pinYin = CommonUtil.escapeString(keywordMap.get("pinYin"));
		String hasHotel = CommonUtil.escapeString(keywordMap.get("hasHotel"));
		String name = CommonUtil.escapeString(keywordMap.get("name"));
		String cityId = CommonUtil.escapeString(keywordMap.get("cityId"));
		String stage = CommonUtil.escapeString(keywordMap.get("stage"));
		BooleanQuery booleanQuery = new BooleanQuery();
		if (StringUtils.isNotEmpty(pinYin)) {
			booleanQuery.add(PlaceQuery.getPinYinQuery(pinYin), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(PlaceQuery.getNameWildcardQuery(name), BooleanClause.Occur.SHOULD);
		}
		BooleanQuery booleanQuery2 = new BooleanQuery();
		if (StringUtils.isNotEmpty(stage)) {
			booleanQuery2.add(PlaceQuery.getStageQuery(stage), BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(hasHotel)) {
			booleanQuery2.add(PlaceQuery.getHasHotelQuery(hasHotel), BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(cityId)) {
			booleanQuery2.add(PlaceQuery.getCityIdQuery(cityId), BooleanClause.Occur.MUST);
		}
		if (!booleanQuery.clauses().isEmpty()) {
			booleanQuery2.add(booleanQuery, BooleanClause.Occur.MUST);
		}
		return booleanQuery2;
	}

	public static Query getSearchHotelName(Map<String, String> keywordMap) {
		String pinYin = CommonUtil.escapeString(keywordMap.get("pinYin"));
		String hasHotel = CommonUtil.escapeString(keywordMap.get("hasHotel"));
		String name = CommonUtil.escapeString(keywordMap.get("name"));
		String cityId = CommonUtil.escapeString(keywordMap.get("cityId"));
		String stage = CommonUtil.escapeString(keywordMap.get("stage"));
		String roundPlaceName = CommonUtil.escapeString(keywordMap.get("roundPlaceName"));
		BooleanQuery booleanQuery = new BooleanQuery();
		if (StringUtils.isNotEmpty(pinYin)) {
			 booleanQuery.add(PlaceQuery.getPinYinQuery(pinYin),BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(PlaceQuery.getNameQuery(name), BooleanClause.Occur.SHOULD);
		}
		if (StringUtils.isNotEmpty(roundPlaceName)) {
			booleanQuery.add(PlaceQuery.getRoundPlaceNameQuery(roundPlaceName), BooleanClause.Occur.SHOULD);
		}
		BooleanQuery booleanQuery2 = new BooleanQuery();
		if (StringUtils.isNotEmpty(stage)) {
			booleanQuery2.add(PlaceQuery.getStageQuery(stage), BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(hasHotel)) {
			booleanQuery2.add(PlaceQuery.getHasHotelQuery(hasHotel), BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(cityId)) {
			booleanQuery2.add(PlaceQuery.getCityIdQuery(cityId), BooleanClause.Occur.MUST);
		}
		if (!booleanQuery.clauses().isEmpty()) {
			booleanQuery2.add(booleanQuery, BooleanClause.Occur.MUST);
		}
		return booleanQuery2;
	}

	/** 右侧推荐自由自在产品 **/
	public static BooleanQuery getFreenessRecommendQuery(String cityId) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		try {
			if (StringUtils.isNotEmpty(cityId)) {
				booleanQuery.add(productquery.getProductAlltoPlaceIds(cityId), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getSubProductTypeQuery("FREENESS"), BooleanClause.Occur.MUST);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return booleanQuery;
	}
	
	/** 产品ID号查询 **/
	public static BooleanQuery getProductIdQuery(String productID) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		try {
			if (StringUtils.isNotEmpty(productID)) {
				booleanQuery.add(productquery.getProductIDQuery(productID), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getProductTypeQuery("ROUTE"), BooleanClause.Occur.MUST);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return booleanQuery;
	}

	/** 新门票产品搜索 **/
	public static BooleanQuery getTicketProductByProductName(String productName) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		try {
			if (StringUtils.isNotEmpty(productName)) {
//				booleanQuery.add(productquery.getProductNameQuery(productName), BooleanClause.Occur.MUST);
				List<String> list=new ArrayList<String>();
				list.add(ProductDocument.PRODUCT_NAME);
				booleanQuery.add(getMultiFiedlParserQueryByHasField(productName,list),BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getIsTicketQuery("1"), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getProductChannelQuery("FRONTEND"), BooleanClause.Occur.MUST);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return booleanQuery;
	}
	/** 新门票产品搜索 **/
	public static BooleanQuery getProductByPlaceSearchAllQuery(Map<String,String> map) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		String placeShortId = map.get("placeShortId");
		String tagsGroupName = map.get("tagsGroupName");
		String productName = map.get("productName");
		try {
			if (StringUtils.isNotEmpty(placeShortId)) {
				booleanQuery.add(productquery.getProductAlltoPlaceIds(placeShortId), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getIsTicketQuery("1"), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getProductChannelQuery("FRONTEND"), BooleanClause.Occur.MUST);
			}
			if(StringUtils.isNotEmpty(tagsGroupName)){
				booleanQuery.add(productquery.getTagsGroupQuery(tagsGroupName),BooleanClause.Occur.MUST);
			}
			if(StringUtils.isNotEmpty(productName)){
				List<String>list =new ArrayList<String>();
				list.add(ProductDocument.PRODUCT_NAME);
				booleanQuery.add(getMultiFiedlParserQueryByHasField(productName, list),BooleanClause.Occur.MUST);
//				booleanQuery.add(productquery.getProductNameQuery(productName),BooleanClause.Occur.MUST);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return booleanQuery;
	}
	
	/** 新酒店套餐产品搜索 **/
	public static BooleanQuery getProductByHotelQuery(String placeShortId) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		try {
			if (StringUtils.isNotEmpty(placeShortId)) {
				booleanQuery.add(productquery.getProductAlltoPlaceIds(placeShortId), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getSubProductTypeQuery("HOTEL_SUIT"), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getProductTypeQuery("HOTEL"), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getProductChannelQuery("FRONTEND"), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getIsValid("Y"), BooleanClause.Occur.MUST);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return booleanQuery;
	}
	
	/** 新酒店自由行（酒店+门票）产品搜索 **/
	public static BooleanQuery getFreenessByHotelQuery(String placeShortId) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		try {
			if (StringUtils.isNotEmpty(placeShortId)) {
				booleanQuery.add(productquery.getProductAlltoPlaceIds(placeShortId), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getSubProductTypeQuery("FREENESS"), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getProductTypeQuery("ROUTE"), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getProductChannelQuery("FRONTEND"), BooleanClause.Occur.MUST);
				booleanQuery.add(productquery.getIsValid("Y"), BooleanClause.Occur.MUST);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return booleanQuery;
	}
	
	/** 酒店的类别产品搜索 **/
	public static BooleanQuery getProductBranchByHotelQuery(String placeShortId) {
		BooleanQuery ProductBranchQuery = new BooleanQuery();
		ProductBranchQuery productBranchquery = new ProductBranchQuery();
		try {
			if (StringUtils.isNotEmpty(placeShortId)) {
				ProductBranchQuery.add(productBranchquery.getproductAllPlaceIdsQuery(placeShortId), BooleanClause.Occur.MUST);
				ProductBranchQuery.add(productBranchquery.getOnLineQuery("true"), BooleanClause.Occur.MUST);
				ProductBranchQuery.add(productBranchquery.getAdditionalQuery("false"), BooleanClause.Occur.MUST);
				ProductBranchQuery.add(productBranchquery.getValidQuery("Y"), BooleanClause.Occur.MUST);
				ProductBranchQuery.add(productBranchquery.getVisibleQuery("true"),BooleanClause.Occur.MUST);
				ProductBranchQuery.add(productBranchquery.getChannelQuery("FRONTEND"),BooleanClause.Occur.MUST);
				ProductBranchQuery.add(productBranchquery.getSubProductTypeQuery("SINGLE_ROOM"),BooleanClause.Occur.MUST);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ProductBranchQuery;
	}
	
	/** 门票目的地自由行搜索 **/
	public static BooleanQuery getProductByPlaceRouteQuery(String toDest) {
		BooleanQuery toDestQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		try {
			if (StringUtils.isNotEmpty(toDest)) {
				toDestQuery.add(productquery.getProductAlltoPlaceIds(toDest), BooleanClause.Occur.MUST);
				toDestQuery.add(productquery.getProductTypeQuery("ROUTE"), BooleanClause.Occur.MUST);
				toDestQuery.add(productquery.getSubProductTypeQuery("FREENESS"), BooleanClause.Occur.MUST);
				toDestQuery.add(productquery.getIsValid("Y"), BooleanClause.Occur.MUST);
				toDestQuery.add(productquery.getProductChannelQuery("FRONTEND"),BooleanClause.Occur.MUST);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toDestQuery;
	}
	
	public static Query getProductSearchQuery(String keyword) throws ParseException {
		// 预处理搜索关键词
		keyword = CommonUtil.escapeString(keyword);
		final String filed = "productName";
		// 按标题搜索，找到id 列表
		QueryParser parser = new QueryParser(Version.LUCENE_43, filed, AnalyzerUtil.getAnalyzer(AnalyzerUtil.PRODUCT_INDEX_ANALYZER));
		parser.setDefaultOperator(QueryParser.AND_OPERATOR);
		Query query = parser.parse(keyword);
		//log.info("product query:" + query);
		return query;
	}

	
	/**
	 * 新搜索关键字推荐出境产品 
	 * @param keywordMap
	 * @return
	 */
	public static BooleanQuery newForeignRecommedQuery(Map<String, String> keywordMap) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		String fromDest = CommonUtil.escapeString(keywordMap.get(ProductDocument.FROM_DEST));
		String toDest = CommonUtil.escapeString(keywordMap.get(ProductDocument.TO_DEST));
		String subProductType = CommonUtil.escapeString(keywordMap.get(ProductDocument.SUB_PRODUCT_TYPE));
		String productChannel = keywordMap.get(ProductDocument.PRODUCT_CHANNEL);
		
	try {
			//展示渠道
			if (StringUtils.isNotEmpty(productChannel)) {
				booleanQuery.add(productquery.getProductChannelQuery(productChannel), BooleanClause.Occur.MUST);
			}

			//出发地,
			if (StringUtils.isNotEmpty(fromDest)) {
				booleanQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.MUST);
			}
			//产品SUB类型为FREENESS_LONG +FREENESS_FOREIGN	
			BooleanQuery  subProductTypeQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(subProductType)) {				
				//log.info("query_rountType" + subProductType);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("GROUP_FOREIGN"),BooleanClause.Occur.SHOULD);
				subProductTypeQuery.add(productquery.getSubProductTypeQuery("FREENES_FOREIGN"),BooleanClause.Occur.SHOULD);				
			}
			if (subProductTypeQuery.clauses().size() > 0) {
				booleanQuery.add(subProductTypeQuery, BooleanClause.Occur.MUST);
			}
	
			// 目的地,标地
			BooleanQuery toDestQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(toDest)) {
				toDestQuery.add(productquery.getProductAlltoPlaceConent(toDest), BooleanClause.Occur.SHOULD);
				//toDestQuery.add(productquery.getdepartAreaQuery(toDest), BooleanClause.Occur.SHOULD);
				//toDestQuery.add(productquery.getTopicQuery(toDest), BooleanClause.Occur.SHOULD);
				//toDestQuery.add(productquery.getTagNameQuery(toDest), BooleanClause.Occur.SHOULD);
				//toDestQuery.add(productquery.getPlaceKeywordBindQuery(toDest), BooleanClause.Occur.SHOULD);
			}			
			
			if (toDestQuery.clauses().size() > 0) {
				booleanQuery.add(toDestQuery, BooleanClause.Occur.MUST);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return booleanQuery;
	}	
	
	
	/**
	 * 新搜索团购推荐query
	 * @param keywordMap
	 * @return
	 */
	public static BooleanQuery tuanGouRecommedQuery(Map<String, String> keywordMap) {
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		String fromDest = CommonUtil.escapeString(keywordMap.get(ProductDocument.FROM_DEST));
		String toDest = CommonUtil.escapeString(keywordMap.get(ProductDocument.TO_DEST));
		String productChannel = keywordMap.get(ProductDocument.PRODUCT_CHANNEL);
		String productType = CommonUtil.escapeString(keywordMap.get(ProductDocument.PRODUCT_TYPE));
		
	try {
			//展示渠道
			if (StringUtils.isNotEmpty(productChannel)) {
				booleanQuery.add(productquery.getProductChannelQuery(productChannel), BooleanClause.Occur.MUST);
			}

			//出发地,
			if (StringUtils.isNotEmpty(fromDest)) {
				booleanQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.MUST);
			}
			//产品类型
			if (StringUtils.isNotEmpty(productType)) {
				booleanQuery.add(productquery.getProductTypeQuery(productType), BooleanClause.Occur.MUST);
			}
			// 目的地,标地
			BooleanQuery toDestQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(toDest)) {
				toDestQuery.add(productquery.getProductAlltoPlaceConent(toDest), BooleanClause.Occur.MUST);
				toDestQuery.add(productquery.getdepartAreaQuery(toDest), BooleanClause.Occur.SHOULD);
				toDestQuery.add(productquery.getTopicQuery(toDest), BooleanClause.Occur.SHOULD);
				toDestQuery.add(productquery.getTagNameQuery(toDest), BooleanClause.Occur.SHOULD);
				toDestQuery.add(productquery.getPlaceKeywordBindQuery(toDest), BooleanClause.Occur.SHOULD);
			}			
			
			if (toDestQuery.clauses().size() > 0) {
				booleanQuery.add(toDestQuery, BooleanClause.Occur.MUST);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return booleanQuery;
	}	

	public static String[] splitKeyword(String keyword) {
		String[] key = keyword.split("_");
		return key;
	}
	
	public static Query getHotelQuery(Map<String, String> params){
		BooleanQuery booleanQuery = new BooleanQuery();
		String topic = params.get("topic");
		String name = params.get("name");
		String longitude = params.get("longitude");
		String latitude = params.get("latitude");
		String startPrice = params.get("startPrice");
		String endPrice = params.get("endPrice");
		String city = params.get("city");
		String promotion = params.get("promotion");
		String keyword2 = params.get("keyword2");
		String star = params.get("star");
		String hotelTopics = params.get("hotelTopics");
		String prodTopics = params.get("prodTopics");
		String localName = params.get("localName");
		if( StringUtils.isNotEmpty(longitude) && StringUtils.isNotEmpty(latitude)){//经纬度不为空,按照经纬度查询
			String[] longitudes = longitude.split(",");
			String[] latitudes = latitude.split(",");
			Integer distance = Integer.parseInt(params.get("distance"));
			BooleanQuery booleanQuery1 = new BooleanQuery();
			for(int i=0;i<longitudes.length;i++){
				BooleanQuery booleanQuery2 = new BooleanQuery();
				GeoLocation gl = GeoLocation.fromDegrees(Double.parseDouble(latitudes[i]), Double.parseDouble(longitudes[i]));
				GeoLocation[] minMaxGL = gl.boundingCoordinates(distance);
				Query longitudeQuery  =PlaceQuery.getLongitudeQuery(minMaxGL[0].getLongitudeInDegrees(), minMaxGL[1].getLongitudeInDegrees(), true, true);
				Query latitudeQuery  =PlaceQuery.getLatitudeQuery(minMaxGL[0].getLatitudeInDegrees(), minMaxGL[1].getLatitudeInDegrees(), true, true);
				booleanQuery2.add(longitudeQuery,BooleanClause.Occur.MUST);
				booleanQuery2.add(latitudeQuery,BooleanClause.Occur.MUST);
				booleanQuery1.add(booleanQuery2,BooleanClause.Occur.SHOULD);
			}
			booleanQuery.add(booleanQuery1,BooleanClause.Occur.MUST);
		}else{//按照关键词查询
			if (StringUtils.isNotEmpty(topic)) {
				BooleanQuery booleanQuery1 = new BooleanQuery();
				booleanQuery1.add(PlaceQuery.getHotelTopic(topic) , BooleanClause.Occur.SHOULD);
				booleanQuery1.add(PlaceQuery.getHotelProdTopic(topic), BooleanClause.Occur.SHOULD);
				booleanQuery1.add(PlaceQuery.getDestTagsNameQuery(topic), BooleanClause.Occur.SHOULD);
				booleanQuery1.add(PlaceQuery.getHotelProdTagsName(topic), BooleanClause.Occur.SHOULD);
				booleanQuery.add(booleanQuery1,BooleanClause.Occur.MUST);
			}else if(StringUtils.isNotEmpty(name)){
				BooleanQuery booleanQuery1 = new BooleanQuery();
				booleanQuery1.add(PlaceQuery.getDestNameIdsQuery(name), BooleanClause.Occur.SHOULD);
				booleanQuery1.add(PlaceQuery.getEnNameQuery(name), BooleanClause.Occur.SHOULD);
				booleanQuery1.add(PlaceQuery.getNameQuery(name), BooleanClause.Occur.SHOULD);
				booleanQuery1.add(PlaceQuery.getRoundPlaceNameQuery(name), BooleanClause.Occur.SHOULD);
				booleanQuery1.add( new WildcardQuery(new Term(PlaceDocument.HFKW, "*" + name + "*")), BooleanClause.Occur.SHOULD);
				booleanQuery.add(booleanQuery1,BooleanClause.Occur.MUST);
			}
		}
		if(StringUtils.isNotEmpty(localName)){
			booleanQuery.add(PlaceQuery.getDestPresentStrQuery(localName), BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(city)) {
			booleanQuery.add(PlaceQuery.getCityQuery(city), BooleanClause.Occur.MUST);
		}
		// 酒店星级
		if (StringUtils.isNotEmpty(star)) {
			BooleanQuery hotelStarQuery = new BooleanQuery();
			String[] starts = star.split(",");
			for (String s : starts) {
				hotelStarQuery.add(new TermQuery(new Term(PlaceHotelDocument.HOTEL_STAR_MERGE, s)), BooleanClause.Occur.SHOULD);
			}
			booleanQuery.add(hotelStarQuery, BooleanClause.Occur.MUST);
		}
		// 酒店主题
		if (StringUtils.isNotEmpty(hotelTopics)) {
			BooleanQuery hQuery = new BooleanQuery();
			String[] strs = hotelTopics.split(",");
			for (String s : strs) {
				hQuery.add(PlaceQuery.getHotelTopic(s.trim()), BooleanClause.Occur.SHOULD);
			}
			booleanQuery.add(hQuery, BooleanClause.Occur.MUST);
		}
		
		// 产品主题
		if (StringUtils.isNotEmpty(prodTopics)) {
			BooleanQuery hQuery = new BooleanQuery();
			String[] strs = prodTopics.split(",");
			for (String s : strs) {
				hQuery.add(PlaceQuery.getHotelProdTopic(s.trim()), BooleanClause.Occur.SHOULD);
			}
			booleanQuery.add(hQuery, BooleanClause.Occur.MUST);
		}
		
		// 二次搜索
		if (StringUtils.isNotEmpty(keyword2)) {
			booleanQuery.add(PlaceQuery.shouldQuerys(PlaceQuery.getNameQuery(keyword2),PlaceQuery.getEnNameQuery(keyword2), PlaceQuery.getHotelRecommendContent(keyword2)), BooleanClause.Occur.MUST);
		}
		//价格
		if (StringUtils.isNotBlank(startPrice)||StringUtils.isNotBlank(endPrice)) {
			booleanQuery.add(PlaceQuery.getSellPriceQuery(startPrice, endPrice),BooleanClause.Occur.MUST);
		}
		// 促销
		if (StringUtils.isNotEmpty(promotion)) {
			
			booleanQuery.add(PlaceQuery.shouldQuerys(
					PlaceQuery.getDestTagsGroupQuery(PROD_TAG_GROUP_NAME.GIVE_COUPON.getCnName()),
					NumericRangeQuery.newIntRange(PlaceHotelDocument.GROUP_PRODUCT_NUM, 1, Integer.MAX_VALUE, true, true)), //团购
					BooleanClause.Occur.MUST);
		}
		return booleanQuery;
	}
	
	
	public static Query getVerHotelQuery(VerHotelSearchVO params){
		BooleanQuery booleanQuery = new BooleanQuery();
		String keyword = params.getKeyword();
		String longitude = params.getLongitude();
		String latitude = params.getLatitude();
		String minproductsprice = params.getMinproductsprice();
		String maxproductsprice = params.getMaxproductsprice();
		String hotelstar = params.getHotelStar();
		String hotel_id=params.getSearchId();
		String facilities=params.getFacilities();
		String room_type=params.getRoom_type();
		String issale=params.getIssale();
		String districtid=params.getParentId();
		String hotel_brand=params.getHotel_brand();
		if( StringUtils.isNotEmpty(longitude) &&
				StringUtils.isNotEmpty(latitude) &&
				!longitude.equals("0.0")&&
				!latitude.equals("0.0") &&
				!"1".equals(params.getRanktype())){//经纬度不为空,按照经纬度查询
				    BooleanQuery booleanQuery1 = new BooleanQuery();
				    Query districtQuery=null;
				    //如果districtid 为空 ，从url进来和从热门目的地进来,区域用关键字
				    if(StringUtil.isEmptyString(districtid)){
				    	districtQuery =new TermQuery(new Term(VerhotelDocument.DISTRICT, keyword));
				    }
				    else{
				    	districtQuery =new TermQuery(new Term(VerhotelDocument.DISTRICTID, districtid));
				    }
				    
					booleanQuery1.add(districtQuery,BooleanClause.Occur.MUST);
					booleanQuery.add(booleanQuery1,BooleanClause.Occur.MUST);
		}else{//按照关键词查询
			if (StringUtils.isNotEmpty(keyword)) {
				//加入分词器的query
				List<String> list=new ArrayList<String>();
				list.add(VerhotelDocument.DISTRICT);
				list.add(VerhotelDocument.HOTEL_NAME);
				 Query districtQuery=null;
				if(StringUtil.isEmptyString(districtid)){
			    	districtQuery =new TermQuery(new Term(VerhotelDocument.DISTRICT, keyword));
			    }
			    else{
			    	districtQuery =new TermQuery(new Term(VerhotelDocument.DISTRICTID, districtid));
			    }
				booleanQuery.add(districtQuery, BooleanClause.Occur.MUST);
//				try {
//					Query query = QueryUtil.getMultiFiedlParserQueryByHasField(keyword,list);
//					booleanQuery.add(query, BooleanClause.Occur.MUST);
//				} catch (ParseException e) {
//					log.error("lucene切分异常", e);
//					e.printStackTrace();
//					throw new RuntimeException();
//					
//				}
				
			}
		}
//		 酒店星级
		if (StringUtils.isNotEmpty(hotelstar)) {
			BooleanQuery hotelStarQuery = new BooleanQuery();
			String[] starts = hotelstar.split(",");
			for (String s : starts) {
				String[] startss = s.split("-");
				for (String ss:startss){
					hotelStarQuery.add(new TermQuery(new Term(VerhotelDocument.HOTELSTAR, ss)), BooleanClause.Occur.SHOULD);
				}
				
			}
			booleanQuery.add(hotelStarQuery, BooleanClause.Occur.MUST);
		}
		
		//价格
		if (StringUtils.isNotBlank(minproductsprice)||StringUtils.isNotBlank(maxproductsprice)) {
			BooleanQuery pricequery = new BooleanQuery();
			pricequery.add(VerHotelQuery.getMinSellPriceQuery(minproductsprice, maxproductsprice),BooleanClause.Occur.SHOULD);
//			pricequery.add(VerHotelQuery.getMaxSellPriceQuery(minproductsprice, maxproductsprice),BooleanClause.Occur.SHOULD);
			booleanQuery.add(pricequery, BooleanClause.Occur.MUST);
		}
		
		
//		设施
		if (StringUtils.isNotEmpty(facilities)) {
			BooleanQuery facilityQuery = new BooleanQuery();
			String[] facilitieses = facilities.split(",");
			for (String s : facilitieses) {
				facilityQuery.add(new TermQuery(new Term(VerhotelDocument.FACILITIES, s)), BooleanClause.Occur.SHOULD);
			}
			booleanQuery.add(facilityQuery, BooleanClause.Occur.MUST);
		}
//		房型
		if (StringUtils.isNotEmpty(room_type)) {
			BooleanQuery roomtypeQuery = new BooleanQuery();
			String[] room_typees = room_type.split(",");
			for (String s : room_typees) {
				roomtypeQuery.add(new TermQuery(new Term(VerhotelDocument.ROOM_TYPE, s)), BooleanClause.Occur.SHOULD);
			}
			booleanQuery.add(roomtypeQuery, BooleanClause.Occur.MUST);
		}
		//品牌
		if (StringUtils.isNotEmpty(hotel_brand)) {
			BooleanQuery hotelBrandQuery= new BooleanQuery();
			String[] tmphotel_brand = hotel_brand.split(",");
			for (String s : tmphotel_brand) {
				hotelBrandQuery.add(new TermQuery(new Term(VerhotelDocument.HOTELBRANDID, s)), BooleanClause.Occur.SHOULD);
			}
			booleanQuery.add(hotelBrandQuery, BooleanClause.Occur.MUST);
		}
		
//		促销
		if (StringUtils.isNotEmpty(issale)) {
			BooleanQuery saleQuery = new BooleanQuery();
			saleQuery.add(new TermQuery(new Term(VerhotelDocument.ISSALE, issale)), BooleanClause.Occur.SHOULD);
			booleanQuery.add(saleQuery, BooleanClause.Occur.MUST);
		}
		
//		// 促销
//		if (StringUtils.isNotEmpty(promotion)) {
//			
//			booleanQuery.add(PlaceQuery.shouldQuerys(
//					PlaceQuery.getDestTagsGroupQuery(PROD_TAG_GROUP_NAME.GIVE_COUPON.getCnName()),
//					NumericRangeQuery.newIntRange(PlaceHotelDocument.GROUP_PRODUCT_NUM, 1, Integer.MAX_VALUE, true, true)), //团购
//					BooleanClause.Occur.MUST);
//		}
		return booleanQuery;
	}

	public static Query getPlaceWithTicketQuery(Map<String, String> params,
			String stage, List<String[]> keywordList) {
		return getPlaceWithTicketQuery(params,stage,"city",keywordList);
	}


	public static Query getVerPlaceQuery(VerHotelSearchVO verHotelSearchVO) {
		BooleanQuery booleanQuery = new BooleanQuery();
		String keyword = verHotelSearchVO.getKeyword();
		String parentId = verHotelSearchVO.getParentId();
		if (StringUtils.isNotEmpty(parentId)) {
			TermQuery termQuery=new TermQuery(new Term(VerplaceDocument.PARENTID,parentId));
			booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
		}
		else if(StringUtils.isNotEmpty(keyword)){
			TermQuery termQuery=new TermQuery(new Term(VerplaceDocument.PLACENAME,keyword));
			booleanQuery.add(termQuery, BooleanClause.Occur.MUST);
		}
		TermQuery stagequery=new TermQuery(new Term(VerplaceDocument.STAGE,"2"));
		booleanQuery.add(stagequery, BooleanClause.Occur.MUST);
		return booleanQuery;
	}


}
