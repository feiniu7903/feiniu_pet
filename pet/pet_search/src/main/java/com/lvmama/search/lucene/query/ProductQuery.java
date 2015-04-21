package com.lvmama.search.lucene.query;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lvmama.search.lucene.analyser.AnalyzerUtil;
import com.lvmama.search.lucene.document.ProductDocument;

/**
 * 产品查询条件
 * 
 * @author yuzhibing
 * 
 */
public class ProductQuery {
	private Query contentQuery;
	private Query payMethodQuery;
	private Query isTicketQuery;
	private Query agioQuery;
	private Query sellPriceQuery;
	private Query visitDayQuery;
	private Query couponQuery;
	private Query cashRefundQuery;
	private Query fromDestQuery;
	private Query toDestQuery;
	private Query tagQuery;
	private Query productChannelQuery;
	private Query fromDestContetQuery;
	private Query toDestContentQuery;
	private Query isOldQuery;
	private Query subProductTypeQuery;
	private Query contentPhraseQuery;
	/**加新字段Query**/
	private Query isValidQuery;
	
	private Query productAlltoPlaceIds;
	
	private Query productAlltoPlaceConentQuery;
	
	private Query topicQuery;
	
	private Query tagNameQuery;
	
	private Query productRouteTypeNameQuery;
	
	private Query productAllToPlacePinYinQuery;
	
	private Query departAreaQuery;
	
	private Query  productTypeQuery;
	
	private Query  placeKeywordBindQuery;
	
	private Query productNameQuery;
	/**产品ID号查询**/
	private Query productIdQuery;

	public Query getProductIDQuery(String productId) throws IOException {
		productIdQuery = new TermQuery(new Term(ProductDocument.PRODUCT_ID, productId));
		return this.productIdQuery; 
	}
	
	public Query getProductNameQuery(String keyord) throws IOException {
		//productNameQuery = new PrefixQuery(new Term(ProductDocument.PRODUCT_NAME, keyord));
		productNameQuery = new WildcardQuery(new Term(ProductDocument.PRODUCT_NAME, "*" + keyord + "*"));
		return this.productNameQuery;
	}
	
	public Query getTagsGroupQuery(String tagsGroup) throws IOException {
		return new WildcardQuery(new Term(ProductDocument.TAGS_GROUP, "*"+ tagsGroup +"*"));
	}
	
	/**
	 * 
	 * @param东南亚,日韩,等区域关键字
	 * @return
	 * @throws IOException
	 */
	public Query getdepartAreaQuery(String departArea) throws IOException {
		departAreaQuery = new PrefixQuery(new Term(ProductDocument.DEPART_AREA, departArea));
		return this.departAreaQuery;
	}
	
	public Query getProductAllToPlacePinYinQuery(String pinYin) {
		productAllToPlacePinYinQuery = new PrefixQuery(new Term(ProductDocument.PRODUCT_ALLTO_PLACE_PINYIN, pinYin));
		return productAllToPlacePinYinQuery;
	}
	
	public Query getProductRouteTypeNameQuery(String productRouteTypeName) throws IOException {
		productRouteTypeNameQuery = new TermQuery(new Term(ProductDocument.PROD_ROUTE_TYPE_NAME, productRouteTypeName));
		return this.productRouteTypeNameQuery;
	}
	
	public Query getTopicQuery(String topic) throws IOException {
		topicQuery = new PrefixQuery(new Term(ProductDocument.ROUTE_TOPIC, topic));
		return this.topicQuery;
	}
	
	public Query getTagNameQuery(String tagName) throws IOException {
		//tagNameQuery = new PrefixQuery(new Term(ProductDocument.TAGS_NAME, tagName));
		tagNameQuery = new WildcardQuery(new Term(ProductDocument.TAGS_NAME, "*"+ tagName +"*"));
		return this.tagNameQuery;
	}
	
	public Query getProductAlltoPlaceConent(String toDest) throws IOException {
		this.productAlltoPlaceConentQuery = new TermQuery(new Term(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT, toDest));
		return this.productAlltoPlaceConentQuery;
	}
	
	public Query getPlaceKeywordBindQuery(String placeKeywordBind) throws IOException {
		this.placeKeywordBindQuery = new TermQuery(new Term(ProductDocument.PLACE_KEYWORD_BIND, placeKeywordBind));
		return this.placeKeywordBindQuery;
	}
	
	public Query getIsValid(String isValid) throws IOException {
		isValidQuery = new TermQuery(new Term("isValid", isValid));
		return this.isValidQuery;
	}
	
	public Query getProductAlltoPlaceIds(String shortId) throws IOException {
		this.productAlltoPlaceIds = new TermQuery(new Term(ProductDocument.PRODUCT_ALLTO_PLACE_IDS, shortId));		
		return this.productAlltoPlaceIds;
	}

	public Query getPayMethodQuery(String payMethod) {
		Term term = new Term("payMethod", "*" + payMethod + "*");
		this.payMethodQuery = new WildcardQuery(term);
		return payMethodQuery;
	}

	public Query getIsTicketQuery(String itTicket) {
		isTicketQuery = new TermQuery(new Term("isTicket", itTicket));
		return isTicketQuery;
	}

	public Query getAgioQuery(String agio) {
		String[] agioPrice = agio.split(",");
		final String field2 = "agio";
		this.agioQuery = NumericRangeQuery.newLongRange(field2, Long.parseLong(agioPrice[0]), Long.parseLong(agioPrice[1]), true, true);
		return this.agioQuery;
	}

	public Query getSellPriceQuery(String startPrice, String endPrice) {
		float start = Float.MIN_VALUE;
		if (StringUtils.isNotBlank(startPrice)&&startPrice.matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")) {
			start = Float.parseFloat(startPrice);
		}

		float end = Float.MAX_VALUE;
		if (StringUtils.isNotBlank(endPrice)&&endPrice.matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")) {
			end = Float.parseFloat(endPrice);
		}

		this.sellPriceQuery = NumericRangeQuery.newFloatRange(ProductDocument.SELL_PRICE, start, end, true, true);
		return this.sellPriceQuery;
	}

	public Query getPriceQuery(String sellPrice) {
		String[] sellPriceSpit = sellPrice.split(",");
		final String field2 = "sellPrice";
		this.sellPriceQuery = NumericRangeQuery.newFloatRange(field2, Float.parseFloat(sellPriceSpit[0]), Float.parseFloat(sellPriceSpit[1]), true, true);
		return this.sellPriceQuery;
	}

	public Query getVisitDayQuery(String visitDay) throws IOException {
		String[] agioPrice = visitDay.split(",");
		String start="1";
		String end="10";
		if(agioPrice.length == 1){
			start =  agioPrice[0];
			end = agioPrice[0];
		}else if(agioPrice.length == 2){
			start =  agioPrice[0];
			end = agioPrice[1];
		}
		final String field2 = "visitDay";
		this.visitDayQuery = NumericRangeQuery.newLongRange(field2, Long.parseLong(start), Long.parseLong(end), true, true);
		return this.visitDayQuery;
	}

	public Query getCouponQuery(String coupon) {
		couponQuery = new TermQuery(new Term("coupon", "0"));
		return couponQuery;
	}

	public Query getCashRefundQuery(String cashRefund) {
		cashRefundQuery = new TermQuery(new Term("cashRefund", "0"));
		return cashRefundQuery;
	}

	public Query getFromDestQuery(String fromDest) {
		Term term = new Term(ProductDocument.FROM_DEST, fromDest);	
		this.fromDestQuery = new TermQuery(term);
//		Analyzer analyzer = new IKAnalyzer(true);
//		QueryParser qp = new QueryParser(Version.LUCENE_43, ProductDocument.FROM_DEST, analyzer);
//	    qp.setDefaultOperator(QueryParser.OR_OPERATOR);
//	    Query query = null;
//		try {
//			query = qp.parse(fromDest);
//		} catch (ParseException e) {
//			throw new RuntimeException(e);
//		}
		return fromDestQuery;
	}

	public Query getToDestQuery(String toDest){
		Analyzer analyzer = new IKAnalyzer(false);
		QueryParser qp = new QueryParser(Version.LUCENE_43, ProductDocument.TO_DEST, analyzer);
	    qp.setDefaultOperator(QueryParser.OR_OPERATOR);
	    Query query;
		try {
			query = qp.parse(toDest);
		}catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return query;
	}

	public Query getTagQuery(String tag){
		Analyzer analyzer = new IKAnalyzer(false);
		QueryParser qp = new QueryParser(Version.LUCENE_43, ProductDocument.TAGS_NAME, analyzer);
	    qp.setDefaultOperator(QueryParser.OR_OPERATOR);
	    try {
			Query query = qp.parse(tag);
	    }catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return tagQuery;
	}

	public Query getProductChannelQuery(String productChannel) {
		Term term = new Term(ProductDocument.PRODUCT_CHANNEL, productChannel);	
		this.productChannelQuery = new TermQuery(term);
		return productChannelQuery;
	}

	public Query getIsOldQuery(String isOld) {
		isOldQuery = new TermQuery(new Term("isOld", isOld));
		return isOldQuery;
	}

	public Query getSubProductTypeQuery(String subProductType) {
		Term term = new Term(ProductDocument.SUB_PRODUCT_TYPE, subProductType);
		subProductTypeQuery = new TermQuery(term);
		return subProductTypeQuery;
	}

	public Query getProductTypeQuery(String productType) {
		Term term = new Term(ProductDocument.PRODUCT_TYPE, productType);
		productTypeQuery = new TermQuery(term);
		return productTypeQuery;
	}
	
	public Query getConentPhraseQuery(String content) throws ParseException {
		QueryParser queryParser = new QueryParser(Version.LUCENE_43, "productInfo", AnalyzerUtil.getAnalyzer(AnalyzerUtil.PRODUCT_INDEX_ANALYZER));
		queryParser.setPhraseSlop(10);
		contentPhraseQuery = queryParser.parse(content);
		return contentPhraseQuery;
	}
	
	public Query getContentQuery(String content) throws ParseException {
		Analyzer analyzer = new IKAnalyzer(false);
		QueryParser qp = new QueryParser(Version.LUCENE_43, ProductDocument.PRODUCT_INFO, analyzer);
	    qp.setDefaultOperator(QueryParser.OR_OPERATOR);
	    Query query = qp.parse(content);
		return query;
	}

	private Query getTermQuery(String documentName, String propertyName) {
		return new TermQuery(new Term(documentName, propertyName));
	}
	/**
	 * 旅游区域
	 * 
	 * @param playArea
	 * @return
	 */
	public Query getPlayAreaQuery(String playArea) {
		return getTermQuery(ProductDocument.PLAY_AREA, playArea);
	}

	/**
	 * 游玩线路
	 * 
	 * @param content
	 * @return
	 */
	public Query getPlayLineQuery(String content) {
		return getTermQuery(ProductDocument.PLAY_LINE, content);
	}
	/**
	 * 旅游特色
	 * 
	 * @param content
	 * @return
	 */
	public Query getPlayFeaturesQuery(String content) {
		return getTermQuery(ProductDocument.PLAY_FEATURES, content);
	}
	/**
	 * 岛屿特色
	 * 
	 * @param content
	 * @return
	 */
	public Query getLandFeaturesQuery(String content) {
		return getTermQuery(ProductDocument.LAND_FEATURE, content);
	}
	/**
	 * 上岛交通
	 * 
	 * @param content
	 * @return
	 */
	public Query getLandTrafficQuery(String content) {
		return getTermQuery(ProductDocument.LAND_TRAFFIC, content);
	}
	/**
	 * 游玩特色-品牌
	 * 
	 * @param content
	 * @return
	 */
	public Query getPlayBrandQuery(String content) {
		return getTermQuery(ProductDocument.PLAY_BRAND, content);
	}
	/**
	 * 旅游人数
	 * 
	 * @param content
	 * @return
	 */
	public Query getPlayNumQuery(String content) {
		return getTermQuery(ProductDocument.PLAY_NUM, content);
	}
	/**
	 * 往返交通
	 * 
	 * @param content
	 * @return
	 */
	public Query getTrafficQuery(String content) {
		return getTermQuery(ProductDocument.TRAFFIC, content);
	}
	/**
	 * 酒店类型
	 * 
	 * @param content
	 * @return
	 */
	public Query getHotelTypeQuery(String content) {
		return getTermQuery(ProductDocument.HOTEL_TYPE, content);
	}
	/**
	 * 酒店位置
	 * 
	 * @param content
	 * @return
	 */
	public Query getHotelLocationQuery(String content) {
		return getTermQuery(ProductDocument.HOTEL_LOCATION, content);
	}

	/**
	 * 途径景点
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public Query getScenicPlaceQuery(String content){
		return getTermQuery(ProductDocument.SCENIC_PLACE, content);
	}
}
