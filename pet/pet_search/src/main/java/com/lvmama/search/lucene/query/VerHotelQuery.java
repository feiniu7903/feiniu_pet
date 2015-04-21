package com.lvmama.search.lucene.query;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.PlaceHotelDocument;
import com.lvmama.search.lucene.document.VerhotelDocument;

/**
 * 景区查询条件
 * 
 * @author yuzhibing
 * 
 */
public class VerHotelQuery {
	public static BooleanQuery shouldQuerys(Query... queries) {
		if (queries.length == 0) {
			return null;
		}
		BooleanQuery bq = new BooleanQuery();
		for (Query q : queries) {
			bq.add(q, BooleanClause.Occur.SHOULD);
		}
		return bq;
	}

	public static Query getPlaceTypeQuery(String type) {
		return new TermQuery(new Term(PlaceDocument.PLACE_TYPE, type));
	}

	public static Query getNameQuery(String name) {
		//return new PrefixQuery(new Term(PlaceDocument.NAME, name));
		return new WildcardQuery(new Term(PlaceDocument.NAME, "*" + name + "*"));
	}
	public static Query getEnNameQuery(String name) {
		return new WildcardQuery(new Term(PlaceHotelDocument.EN_NAME, "*" + name + "*"));
	}
	public static Query getHfkwQuery(String hfkw) throws IOException {
		//return new PrefixQuery(new Term(PlaceDocument.HFKW, hfkw));
		return new WildcardQuery(new Term(PlaceDocument.HFKW,  "*" + hfkw + "*"));
	}

	public static Query getHfkwWildcardQuery(String hfkw) throws IOException {
		return new WildcardQuery(new Term(PlaceDocument.HFKW, "*" + hfkw + "*"));
	}

	public static Query getDestTagsNameQuery(String tag) {
		//return new PrefixQuery(new Term(PlaceDocument.DEST_TAGS_NAME, tag));
		return new WildcardQuery(new Term(PlaceDocument.DEST_TAGS_NAME, "*" + tag + "*"));
	}

	public static Query getDestTagsGroupQuery(String tagGroup) {
		return new TermQuery(new Term(PlaceDocument.DEST_TAGS_GROUP, tagGroup));
	}

	public static Query getPlaceActivityQuery(String placeActivity) throws IOException {
		return new WildcardQuery(new Term(PlaceDocument.PLACE_ACTIVITY, "*" + placeActivity + "*"));
	}

	public static Query getPlaceActivityQueryHave() throws IOException {
		return new TermQuery(new Term(PlaceDocument.PLACE_ACTIVITY_HAVE, "1"));
	}

	public static Query getDestSubjectsQuery(String subject) throws IOException {
		// this.destSubjectsQuery = IKQueryParser.parseMultiField(fields,
		// subject);
		//return new PrefixQuery(new Term(PlaceDocument.DEST_SUBJECTS, subject));
		return new WildcardQuery(new Term(PlaceDocument.DEST_SUBJECTS, "*" + subject + "*"));
	}

	public static Query getDestPresentStrQuery(String subject) {
		//return new PrefixQuery(new Term(PlaceDocument.DEST_PERSENT_STR, subject));
		return new WildcardQuery(new Term(PlaceDocument.DEST_PERSENT_STR,  "*" + subject + "*"));
	}

	public static Query getDestNameIdsQuery(String city) {
		return new TermQuery(new Term(PlaceDocument.DEST_NAME_IDS, city));
	}

	public static Query getLatitudeQuery(double min, double max, boolean minInclud, boolean maxInclud) {
		return NumericRangeQuery.newDoubleRange(PlaceDocument.LATITUDE, min, max, minInclud, maxInclud);
	}

	public static Query getLongitudeQuery(double min, double max, boolean minInclud, boolean maxInclud) {
		return NumericRangeQuery.newDoubleRange(PlaceDocument.LONGITUDE, min, max, minInclud, maxInclud);
	}

	public static Query getIsClientQuery(String isClient) {
		return new TermQuery(new Term(PlaceDocument.IS_CLIENT, isClient));
	}

	public static Query getTicketNumQuery(long min, long max, boolean minInclud, boolean maxInclud) {
		return NumericRangeQuery.newLongRange(PlaceDocument.TICKET_NUM, min, max, minInclud, maxInclud);
	}

	public static Query getHotelNumQuery(long min, long max, boolean minInclud, boolean maxInclud) {
		return NumericRangeQuery.newLongRange(PlaceDocument.HOTLE_NUM, min, max, minInclud, maxInclud);
	}

	public static Query getFreenessNumQuery(long min, long max, boolean minInclud, boolean maxInclud) {
		return NumericRangeQuery.newLongRange(PlaceDocument.FREENESS_NUM, min, max, minInclud, maxInclud);
	}

	public static Query getRouteNumQuery(int min, int max, boolean minInclud, boolean maxInclud) {
		return NumericRangeQuery.newIntRange(PlaceDocument.ROUTE_NUM, min, max, minInclud, maxInclud);
	}

	public static Query getdestFreenessNumQuery(long min, long max, boolean minInclud, boolean maxInclud) {
		return NumericRangeQuery.newLongRange(PlaceDocument.DEST_FREENESS_NUM, min, max, minInclud, maxInclud);
	}

	public static Query getTopicQuery(String topic) throws IOException {
		return new TermQuery(new Term(PlaceDocument.TOPIC, topic));
	}

	public static Query getCapitalQuery(String capital) throws IOException {
		return new TermQuery(new Term(PlaceDocument.PROVINCE, capital));
	}

	public static Query getCityQuery(String city) {
		return new TermQuery(new Term(PlaceDocument.CITY, city));
	}

	public static Query getProductsNumQuery(String productNum) throws IOException {
		return new TermQuery(new Term(PlaceDocument.PRODUCT_NUM, "0"));
	}

	public static Query getStageQuery(String stage) {
		return new TermQuery(new Term(PlaceDocument.STAGE, stage));
	}

	public static Query getMinSellPriceQuery(String startPrice, String endPrice) {
		float start = Long.MIN_VALUE;
		if (StringUtils.isNotBlank(startPrice) && startPrice.matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")) {
			BigDecimal bigDecimal_s = new BigDecimal(startPrice);
			start = bigDecimal_s.floatValue();
		}

		float end = Long.MAX_VALUE;
		if (StringUtils.isNotBlank(endPrice) && endPrice.matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")) {
			BigDecimal bigDecimal_e = new BigDecimal(endPrice);
			end =bigDecimal_e.floatValue();
		}
		return NumericRangeQuery.newFloatRange(VerhotelDocument.MINPRODUCTSPRICE,start , end, true, true);
	}
	
	public static Query getMaxSellPriceQuery(String startPrice, String endPrice) {
		long start = Long.MIN_VALUE;
		if (StringUtils.isNotBlank(startPrice) && startPrice.matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")) {
			BigDecimal bigDecimal_s = new BigDecimal(startPrice);
			start = bigDecimal_s.longValue();
		}

		long end = Long.MAX_VALUE;
		if (StringUtils.isNotBlank(endPrice) && endPrice.matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")) {
			BigDecimal bigDecimal_e = new BigDecimal(endPrice);
			end = bigDecimal_e.longValue();
		}
		return NumericRangeQuery.newLongRange(VerhotelDocument.MAXPRODUCTSPRICE,start , end, true, true);
	}

	public static Query getPriceQuery(Float minPrice, Float maxPrice) {
		return NumericRangeQuery.newFloatRange(PlaceDocument.PRICE, minPrice, maxPrice, true, true);
	}

	public static Query getSellPriceQuery(Float minPrice, Float maxPrice) {
		return NumericRangeQuery.newFloatRange(PlaceDocument.SELL_PRICE_RANG, minPrice, maxPrice, true, true);
	}

	public static Query getPinYinQuery(String pinYin) {
		// pinYinQuery = new PrefixQuery(new Term(PlaceDocument.PIN_YIN,
		// pinYin));
		return new WildcardQuery(new Term(PlaceDocument.PIN_YIN, "*" + pinYin + "*"));
	}

	public static Query getPinYinWildcardQuery(String pinYin) {
		return new WildcardQuery(new Term(PlaceDocument.PIN_YIN, "*" + pinYin + "*"));
	}

	public static Query getHasHotelQuery(String hasHotel) {
		return new TermQuery(new Term(PlaceDocument.HAS_HOTEL, hasHotel));
	}

	public static Query getNameWildcardQuery(String name) {
		return new WildcardQuery(new Term(PlaceDocument.NAME, "*" + name + "*"));
	}

	public static Query getRoundPlaceNameQuery(String roundPlaceName) {
		return new WildcardQuery(new Term(PlaceDocument.ROUND_PLACE_NAME, "*" + roundPlaceName + "*"));
	}

	public static Query getCityIdQuery(String cityId) {
		return new TermQuery(new Term(PlaceDocument.CITY_ID, cityId));
	}

	public static Query getPlaceIdQuery(String placeId) {
		return new TermQuery(new Term(PlaceDocument.SHORT_ID, placeId));
	}

	public static Query getShortIdQuery(String shortId) {
		return new TermQuery(new Term(PlaceDocument.SHORT_ID, shortId));
	}

	public static Query getHotelTopic(String topic) {
		return new TermQuery(new Term(PlaceHotelDocument.HOTEL_TOPIC, topic));
	}

	public static Query getHotelProdTopic(String topic) {
		return new TermQuery(new Term(PlaceHotelDocument.PROD_TOPIC, topic));
	}
	public static Query getHotelProdTagsName(String name) {
		return new TermQuery(new Term(PlaceHotelDocument.PROD_TAGS_NAME, name));
	}
	public static Query getHotelRecommendContent(String content) {
		return new WildcardQuery(new Term(PlaceHotelDocument.RECOMMEND_CONTENT, "*" + content + "*"));
	}
}
