package com.lvmama.search.lucene.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.GeoLocation;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.search.action.web.OneSearchAction;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.service.client.utils.ClientKeywordUtils;
import com.lvmama.search.synonyms.LikeHashMap;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LocalCacheManager;
import com.lvmama.search.util.LoggerParms;

public class QueryUtilForClient {
	private static final Log log = LogFactory.getLog(QueryUtilForClient.class);

	public static Query getCityIdQuery(String cityId)  {
		Query query = null;
		if (StringUtils.isNotEmpty(cityId)) {
			query = PlaceQuery.getShortIdQuery(cityId);
		}
		return query;
	}

	/**
	 * 统一搜索接口(带经纬度算差距) ：省市(则转换为找省市下的景点和酒店)+景点+酒店 . 接受 中文/简拼/全拼 可以做邻近搜索
	 * 
	 * @author huangzhi
	 * @param keyword
	 *            =&[cityId=||cityName=]&fromPage=isClient&sort=[juli||seq||
	 *            zidian]
	 *            &priceRange=min,max&star=&stage=2||3&productType=[hasTicket||
	 *            allTicket||hasHotel||allHotel]&x=&y=
	 * @return
	 */
	public static Query ClientPlaceSearchQuery(ClientPlaceSearchVO searchVo,List<String[]> keywordList,String transkeyword) throws Exception {
		String keyword = treatKeyWord(searchVo.getKeyword());
		String cityId = treatKeyWord(searchVo.getCityId());
		String cityName = treatKeyWord(searchVo.getCityName());
		String subject = treatKeyWord(searchVo.getSubject());
		String spot = treatKeyWord(searchVo.getSpot());
		String priceRange = treatKeyWord(searchVo.getPriceRange());
		List<String> stage = searchVo.getStage();
		List<String> productType = searchVo.getProductType();
		String longitude = searchVo.getLongitude();
		String latitude = searchVo.getLatitude();
		String windage = searchVo.getWindage();
		List<Long> placeIds = searchVo.getPlaceIds(); 
		boolean hasFreeness = searchVo.isHasFreenes();
		/** keyword **/
		BooleanQuery booleanQuery = new BooleanQuery();
		
		if( StringUtils.isNotEmpty(longitude) && StringUtils.isNotEmpty(latitude)){//经纬度不为空,按照经纬度查询
			String[] longitudes = longitude.split(",");
			String[] latitudes = latitude.split(",");
			Float distanceFloat = (float) (Integer.parseInt(windage)/1000);
			Integer distance = distanceFloat.intValue();
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
		}
		
		if (StringUtils.isNotEmpty(keyword)) {
			List<String> list=new ArrayList<String>();
			list.add(PlaceDocument.NAME);
			list.add(PlaceDocument.DEST_SUBJECTS);
			list.add(PlaceDocument.CITY);
			list.add(PlaceDocument.DEST_TAGS_NAME);
			list.add(PlaceDocument.PLACE_ACTIVITY);
			list.add(PlaceDocument.HFKW);
			list.add(PlaceDocument.OWNFIELD);
			QueryUtil.builderMultiFiedlParserQueryByHasFieldForClientPlaceSearch(transkeyword,list,booleanQuery,keywordList);
		}

		BooleanQuery booleanQuery2 = new BooleanQuery();
		/** cityId||cityName **/
		if (StringUtils.isNotEmpty(cityId)) {
			booleanQuery2.add(PlaceQuery.getCityIdQuery(cityId), BooleanClause.Occur.MUST);
		} else if (StringUtils.isNotEmpty(cityName)) {
			booleanQuery2.add(PlaceQuery.getCityQuery(cityName), BooleanClause.Occur.MUST);
		}
		
		if(hasFreeness){
			BooleanQuery subProductTypebooleanQuery = new BooleanQuery();
			subProductTypebooleanQuery.add(PlaceQuery.getFreenessNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
			booleanQuery2.add(subProductTypebooleanQuery, BooleanClause.Occur.MUST);
		}

		BooleanQuery idsQuery = new BooleanQuery();
		if(placeIds!=null){
			for (Long placeId : placeIds) {
				Query query = PlaceQuery.getShortIdQuery(placeId.toString());
				idsQuery.add(query, BooleanClause.Occur.SHOULD);
			}
		}
		
		/** subject **/
		if (StringUtils.isNotEmpty(subject)) {
//			booleanQuery2.add(PlaceQuery.getDestSubjectsQuery(subject), BooleanClause.Occur.MUST);
			//主题进行分词
			Query query = QueryUtil.getQueryParser(subject,PlaceDocument.DEST_SUBJECTS);
			booleanQuery.add(query, BooleanClause.Occur.MUST);
		}
		/** 酒店周边景点 */
		if (StringUtils.isNotEmpty(spot)) {
			booleanQuery2.add(PlaceQuery.getRoundPlaceNameQuery(spot), BooleanClause.Occur.MUST);
		}
		/** priceRange **/
		if (StringUtils.isNotEmpty(priceRange)) {
			String[] prices = priceRange.split(",");
			String minPrice = prices[0];
			String maxPrice = prices[1];
			booleanQuery2.add(PlaceQuery.getSellPriceQuery(Float.valueOf(minPrice), Float.valueOf(maxPrice)), BooleanClause.Occur.MUST);
		}
		/** 产品类型 */
		BooleanQuery productTypebooleanQuery = new BooleanQuery();
		if (productType!=null &&!productType.isEmpty() && productType.size() > 0) {
			String productTypeStr = "";
			for (int i = 0; i < productType.size(); i++) {
				productTypeStr = productTypeStr + productType.get(i).toUpperCase() + ",";
			}
			if (productTypeStr.indexOf("TICKET") >= 0) {
				productTypebooleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			}
			if (productTypeStr.indexOf("HOTEL") >= 0) {
				productTypebooleanQuery.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			}
			if (productTypeStr.indexOf("ROUTE") >= 0) {
				productTypebooleanQuery.add(PlaceQuery.getRouteNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			}
			booleanQuery2.add(productTypebooleanQuery, BooleanClause.Occur.MUST);
		} else {
			productTypebooleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			productTypebooleanQuery.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			productTypebooleanQuery.add(PlaceQuery.getRouteNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			booleanQuery2.add(productTypebooleanQuery, BooleanClause.Occur.MUST);
		}
		/** 地标类型 */
		BooleanQuery stagebooleanQuery = new BooleanQuery();
		if (stage!=null &&!stage.isEmpty() && stage.size() > 0) {
			String productTypeStr = "";
			for (int i = 0; i < stage.size(); i++) {
				productTypeStr = productTypeStr + stage.get(i) + ",";
			}
			if (productTypeStr.indexOf("1") >= 0) {
				stagebooleanQuery.add(PlaceQuery.getStageQuery("1"), BooleanClause.Occur.SHOULD);
			}
			if (productTypeStr.indexOf("2") >= 0) {
				stagebooleanQuery.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.SHOULD);
			}
			if (productTypeStr.indexOf("3") >= 0) {
				stagebooleanQuery.add(PlaceQuery.getStageQuery("3"), BooleanClause.Occur.SHOULD);
			}
			booleanQuery2.add(stagebooleanQuery, BooleanClause.Occur.MUST);
		} else {
			stagebooleanQuery.add(PlaceQuery.getStageQuery("1"), BooleanClause.Occur.SHOULD);
			stagebooleanQuery.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.SHOULD);
			stagebooleanQuery.add(PlaceQuery.getStageQuery("3"), BooleanClause.Occur.SHOULD);
			booleanQuery2.add(stagebooleanQuery, BooleanClause.Occur.MUST);
		}
//		/** 邻近搜索 **/
//		if (StringUtils.isNotEmpty(longitude)) {
//			booleanQuery.add(PlaceQuery.getLongitudeQuery((Float.parseFloat(longitude) * 100000f - Float.parseFloat(windage)) / 100000f, (Float.parseFloat(longitude) * 100000f + Float.parseFloat(windage)) / 100000f, true, true), BooleanClause.Occur.MUST);
//		}
//		if (StringUtils.isNotEmpty(latitude)) {
//			float cos = (float) (1 / Math.abs(Math.cos(Float.parseFloat(latitude) * Math.PI / 180)));
//			booleanQuery.add(PlaceQuery.getLatitudeQuery((Float.parseFloat(latitude) * 100000f - Float.parseFloat(windage) * cos) / 100000f, (Float.parseFloat(latitude) * 100000f + Float.parseFloat(windage) * cos) / 100000f, true, true), BooleanClause.Occur.MUST);
//		}

		/** 客户端过滤 **/
		BooleanQuery booleanQuery4 = new BooleanQuery();
		if (booleanQuery.clauses().size() > 0) {
			booleanQuery4.add(booleanQuery, BooleanClause.Occur.MUST);
		}
		
		if (idsQuery.clauses().size() > 0) {
			booleanQuery4.add(idsQuery, BooleanClause.Occur.MUST);
		}
		
		if (booleanQuery2.clauses().size() > 0) {
			booleanQuery4.add(booleanQuery2, BooleanClause.Occur.MUST);
		}
		//cdf 增加搜索段为 client的
		booleanQuery4.add(new TermQuery(new Term(PlaceDocument.CASH_REFUND, "-1")), BooleanClause.Occur.MUST_NOT);
		return booleanQuery4;
	}
	
	
	/**
	 * 根据经纬度度邻近搜索 :景点+酒店 . stage=景点|酒店+经纬度+目的地城市ID+价格+酒店星级等参数
	 * 
	 * @author huangzhi
	 * @param keywordMap
	 * @return
	 */
	public static Query getClientNearSearchQuery(Map<String, String> keywordMap) throws IOException {
		String name = treatKeyWord(keywordMap.get("name"));
		String cityId = treatKeyWord(keywordMap.get("cityId"));
		String longitude = keywordMap.get("longitude");
		String latitude = keywordMap.get("latitude");
		String windage = treatKeyWord(keywordMap.get("windage"));
		String productType = treatKeyWord(keywordMap.get("productType"));
		String priceRange = treatKeyWord(keywordMap.get("priceRange"));
		String stage = treatKeyWord(keywordMap.get("stage"));
		BooleanQuery booleanQuery = new BooleanQuery();

		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(PlaceQuery.getNameQuery(name), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getDestTagsNameQuery(name), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getDestPresentStrQuery(name), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getHfkwQuery(name), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getDestSubjectsQuery(name), BooleanClause.Occur.SHOULD);
			// 只有cityid||cityname为空不限定省市时，keyword才可以搜索省,市字段
			booleanQuery.add(PlaceQuery.getCityQuery(name), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getCapitalQuery(name), BooleanClause.Occur.SHOULD);

		}
		if (StringUtils.isNotEmpty(cityId)) {
			booleanQuery.add(PlaceQuery.getCityIdQuery(cityId), BooleanClause.Occur.MUST);
		}
		if (StringUtils.isNotEmpty(longitude)) {
			booleanQuery.add(PlaceQuery.getLongitudeQuery((Float.parseFloat(longitude) * 100000f - Float.parseFloat(windage)) / 100000f, (Float.parseFloat(longitude) * 100000f + Float.parseFloat(windage)) / 100000f, true, true), BooleanClause.Occur.MUST);
			// System.out.println("=="+(Float.parseFloat(longitude) * 100000f -
			// Float.parseFloat(windage)) / 100000f);
			// System.out.println("=="+(Float.parseFloat(longitude) * 100000f +
			// Float.parseFloat(windage)) / 100000f);
		}
		if (StringUtils.isNotEmpty(latitude)) {
			float cos = (float) (1 / Math.abs(Math.cos(Float.parseFloat(latitude) * Math.PI / 180)));
			booleanQuery.add(PlaceQuery.getLatitudeQuery((Float.parseFloat(latitude) * 100000f - Float.parseFloat(windage) * cos) / 100000f, (Float.parseFloat(latitude) * 100000f + Float.parseFloat(windage) * cos) / 100000f, true, true), BooleanClause.Occur.MUST);
			// System.out.println("=="+(Float.parseFloat(latitude) * 100000f -
			// Float.parseFloat(windage) * cos) / 100000f);
			// System.out.println("=="+(Float.parseFloat(latitude) * 100000f +
			// Float.parseFloat(windage) * cos) / 100000f);

		}

		/**
		 * 查景点|酒店|景点+酒店 BooleanQuery booleanQuery2 = new BooleanQuery(); if
		 * (productType.equals("all")) {
		 * booleanQuery2.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE,
		 * true, true), BooleanClause.Occur.SHOULD);
		 * booleanQuery2.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE,
		 * true, true), BooleanClause.Occur.SHOULD);
		 * booleanQuery2.add(PlaceQuery.getStageQuery("2"),
		 * BooleanClause.Occur.SHOULD);
		 * booleanQuery2.add(PlaceQuery.getStageQuery("3"),
		 * BooleanClause.Occur.SHOULD);
		 * 
		 * } else if (productType.equals("hasticket")) {
		 * booleanQuery2.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE,
		 * true, true), BooleanClause.Occur.MUST);
		 * booleanQuery2.add(PlaceQuery.getStageQuery("2"),
		 * BooleanClause.Occur.MUST);
		 * 
		 * } else if (productType.equals("hashotel")) {
		 * booleanQuery2.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE,
		 * true, true), BooleanClause.Occur.MUST);
		 * booleanQuery2.add(PlaceQuery.getStageQuery("3"),
		 * BooleanClause.Occur.MUST);
		 * 
		 * } else {// 参数不匹配，返回空结果
		 * booleanQuery2.add(PlaceQuery.getIsClientQuery("N"),
		 * BooleanClause.Occur.MUST); }
		 **/

		/** stage->(priceRange||star||productType) **/
		BooleanQuery booleanQuery3 = new BooleanQuery();
		if (StringUtils.isNotEmpty(stage)) {
			if ("2".equals(stage)) {
				booleanQuery3.add(PlaceQuery.getStageQuery(stage), BooleanClause.Occur.MUST);
				if (StringUtils.isNotEmpty(productType)) {
					if ("hasticket".equals(productType)) {
						booleanQuery3.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
					} else if ("allticket".equals(productType)) {
						booleanQuery3.add(PlaceQuery.getTicketNumQuery(0, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
					}
				}
			} else if ("3".equals(stage)) {
				booleanQuery3.add(PlaceQuery.getStageQuery(stage), BooleanClause.Occur.MUST);
				/** priceRange **/
				if (StringUtils.isNotEmpty(priceRange)) {
					String[] prices = priceRange.split(",");
					String minPrice = prices[0];
					String maxPrice = prices[1];
					booleanQuery3.add(PlaceQuery.getSellPriceQuery(Float.valueOf(minPrice), Float.valueOf(maxPrice)), BooleanClause.Occur.MUST);
				}

				if (StringUtils.isNotEmpty(productType)) {
					if ("hashotel".equals(productType)) {
						booleanQuery3.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
					} else if ("allhotel".equals(productType)) {
						booleanQuery3.add(PlaceQuery.getHotelNumQuery(0, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
					}
				}
			} else {
				// stage为其他时时需要加上搜索类型为stage=2或者STAGE=3
				booleanQuery3.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.SHOULD);
				booleanQuery3.add(PlaceQuery.getStageQuery("3"), BooleanClause.Occur.SHOULD);
			}

		} else {
			// stage为空时需要加上搜索类型为stage=2或者STAGE=3
			booleanQuery3.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.SHOULD);
			booleanQuery3.add(PlaceQuery.getStageQuery("3"), BooleanClause.Occur.SHOULD);
		}

		/** 客户端过滤 **/
		BooleanQuery booleanQuery4 = new BooleanQuery();
		booleanQuery4.add(PlaceQuery.getIsClientQuery("Y"), BooleanClause.Occur.MUST);
		booleanQuery4.add(booleanQuery, BooleanClause.Occur.MUST);
		// booleanQuery4.add(booleanQuery2, BooleanClause.Occur.MUST);
		booleanQuery4.add(booleanQuery3, BooleanClause.Occur.MUST);
		return booleanQuery4;
	}

	/**
	 * 统一搜索接口(带经纬度算差距) ：省市(则转换为找省市下的景点和酒店)+景点+酒店 . 接受 中文/简拼/全拼
	 * 
	 * @author huangzhi
	 * @param keyword
	 *            =&[cityId=||cityName=]&fromPage=isClient&sort=[juli||seq||
	 *            zidian]
	 *            &priceRange=min,max&star=&stage=2||3&productType=[hasTicket||
	 *            allTicket||hasHotel||allHotel]&x=&y=
	 * @return
	 */
	public static Query getClientPlaceQuery(Map<String, String> keywordMap) throws IOException {
		String name = treatKeyWord(keywordMap.get("name"));
		String cityId = treatKeyWord(keywordMap.get("cityId"));
		String cityName = treatKeyWord(keywordMap.get("cityName"));
		String subject = treatKeyWord(keywordMap.get("subject"));
		String spot = treatKeyWord(keywordMap.get("spot"));
		String priceRange = treatKeyWord(keywordMap.get("priceRange"));
		String stage = treatKeyWord(keywordMap.get("stage"));
		String productType = treatKeyWord(keywordMap.get("productType"));

		/** keyword **/
		BooleanQuery booleanQuery = new BooleanQuery();
		if (StringUtils.isNotEmpty(name)) {
			booleanQuery.add(PlaceQuery.getNameQuery(name), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getDestTagsNameQuery(name), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getDestPresentStrQuery(name), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getHfkwQuery(name), BooleanClause.Occur.SHOULD);
			if (StringUtils.isEmpty(subject)) {
				booleanQuery.add(PlaceQuery.getDestSubjectsQuery(name), BooleanClause.Occur.SHOULD);
			}
			if (StringUtils.isEmpty(cityId) && StringUtils.isEmpty(cityName)) {
				// 只有cityid||cityname为空不限定省市时，keyword才可以搜索省,市字段
				booleanQuery.add(PlaceQuery.getCityQuery(name), BooleanClause.Occur.SHOULD);
				booleanQuery.add(PlaceQuery.getCapitalQuery(name), BooleanClause.Occur.SHOULD);
			}
		}

		/** cityId||cityName **/
		BooleanQuery booleanQuery2 = new BooleanQuery();
		if (StringUtils.isNotEmpty(cityId)) {
			booleanQuery2.add(PlaceQuery.getCityIdQuery(cityId), BooleanClause.Occur.MUST);
			if (StringUtils.isNotEmpty(subject) && StringUtils.isEmpty(name)) {
				booleanQuery2.add(PlaceQuery.getDestSubjectsQuery(subject), BooleanClause.Occur.MUST);
			}
		} else if (StringUtils.isNotEmpty(cityName)) {
			booleanQuery2.add(PlaceQuery.getCityQuery(cityName), BooleanClause.Occur.MUST);
		}

		/** stage->(priceRange||star||productType) **/
		BooleanQuery booleanQuery3 = new BooleanQuery();
		if (StringUtils.isNotEmpty(stage)) {
			if ("2".equals(stage)) {
				booleanQuery3.add(PlaceQuery.getStageQuery(stage), BooleanClause.Occur.MUST);
				if (StringUtils.isNotEmpty(productType)) {
					if ("hasticket".equals(productType)) {
						booleanQuery3.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
					} else if ("allticket".equals(productType)) {
						booleanQuery3.add(PlaceQuery.getTicketNumQuery(0, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
					}
				}
			} else if ("3".equals(stage)) {
				booleanQuery3.add(PlaceQuery.getStageQuery(stage), BooleanClause.Occur.MUST);
				booleanQuery3.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);
				/** priceRange **/
				if (StringUtils.isNotEmpty(priceRange)) {
					String[] prices = priceRange.split(",");
					String minPrice = prices[0];
					String maxPrice = prices[1];
					booleanQuery3.add(PlaceQuery.getSellPriceQuery(Float.valueOf(minPrice), Float.valueOf(maxPrice)), BooleanClause.Occur.MUST);
				}

				// 酒店周边景点
				if (StringUtils.isNotEmpty(spot)) {
					booleanQuery3.add(PlaceQuery.getRoundPlaceNameQuery(spot), BooleanClause.Occur.MUST);
				}
				// hashotel ,allhotel 参数废弃 ,必须有酒店产品
				/*
				 * if (StringUtils.isNotEmpty(productType)) { if
				 * ("hashotel".equals(productType)) {
				 * booleanQuery3.add(PlaceQuery.getHotelNumQuery(1,
				 * Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST); }
				 * else if ("allhotel".equals(productType)) {
				 * booleanQuery3.add(PlaceQuery.getHotelNumQuery(0,
				 * Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST); }
				 * }
				 */
			}

		} else {
			// stage为空时需要加上搜索类型为stage=2或者STAGE=3
			booleanQuery3.add(PlaceQuery.getStageQuery("2"), BooleanClause.Occur.SHOULD);
			booleanQuery3.add(PlaceQuery.getStageQuery("3"), BooleanClause.Occur.SHOULD);
		}

		/** 客户端过滤 **/
		BooleanQuery booleanQuery4 = new BooleanQuery();
		if (StringUtils.isNotEmpty(subject)) {
			booleanQuery4.add(PlaceQuery.getDestSubjectsQuery(subject), BooleanClause.Occur.MUST);
		}
		booleanQuery4.add(PlaceQuery.getIsClientQuery("Y"), BooleanClause.Occur.MUST);
		if (booleanQuery.clauses().size() > 0) {
			booleanQuery4.add(booleanQuery, BooleanClause.Occur.MUST);
		}
		if (booleanQuery2.clauses().size() > 0) {
			booleanQuery4.add(booleanQuery2, BooleanClause.Occur.MUST);
		}
		if (booleanQuery3.clauses().size() > 0) {
			booleanQuery4.add(booleanQuery3, BooleanClause.Occur.MUST);
		}
		return booleanQuery4;
	}

	/**
	 * CLIENT手机端:构造jasonTree中国树 ,得到地标下有 景点|酒店|自由行线路|所有三种| 的省市树
	 * 
	 * @author huangzhi
	 * @param keywordMap
	 * @return
	 */
	public static Query getChinaTreeByHasProductQuery(Map<String, String> keywordMap) throws IOException {
		String porductNumType = treatKeyWord(keywordMap.get("productType"));
		BooleanQuery booleanQuery = new BooleanQuery();
		if (porductNumType.equals("all")) {
			booleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getFreenessNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);

		} else if (porductNumType.equals("hasticket")) {
			booleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);

		} else if (porductNumType.equals("hashotel")) {
			booleanQuery.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);

		} else if (porductNumType.equals("hasfreeness")) {
			booleanQuery.add(PlaceQuery.getFreenessNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.MUST);

		} else {// 参数不匹配，返回空结果
			booleanQuery.add(PlaceQuery.getIsClientQuery("N"), BooleanClause.Occur.MUST);
		}

		/** 客户端过滤 **/
		BooleanQuery booleanQuery2 = new BooleanQuery();
		booleanQuery2.add(PlaceQuery.getIsClientQuery("Y"), BooleanClause.Occur.MUST);
		booleanQuery2.add(PlaceQuery.getStageQuery("1"), BooleanClause.Occur.MUST);
		booleanQuery2.add(booleanQuery, BooleanClause.Occur.MUST);

		return booleanQuery2;
	}

	/**
	 * CLIENT手机端:构造jasonTree中国树 ,得到地标下有 景点|酒店|自由行线路|所有三种| 的省市树
	 * 
	 * @author huangzhi
	 * @param keywordMap
	 * @return
	 */
	public static Query getChinaTreeByHasProductQuery(ClientPlaceSearchVO searchVo) throws IOException {
		List<String> productType = searchVo.getProductType();
		BooleanQuery booleanQuery = new BooleanQuery();
		if (!productType.isEmpty() && productType.size() > 0) {
			String productTypeStr = "";
			for (int i = 0; i < productType.size(); i++) {
				productTypeStr = productTypeStr + productType.get(i).toUpperCase() + ",";
			}			
			if (productTypeStr.indexOf("TICKET") >= 0) {
				booleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			}
			if (productTypeStr.indexOf("HOTEL") >= 0) {
				booleanQuery.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			}
			if (productTypeStr.indexOf("ROUTE") >= 0) {
				booleanQuery.add(PlaceQuery.getRouteNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			}
		} else {
			booleanQuery.add(PlaceQuery.getTicketNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getHotelNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
			booleanQuery.add(PlaceQuery.getRouteNumQuery(1, Integer.MAX_VALUE, true, true), BooleanClause.Occur.SHOULD);
		}

		
		
		/** 客户端过滤 **/
		BooleanQuery booleanQuery2 = new BooleanQuery();
		booleanQuery2.add(PlaceQuery.getIsClientQuery("Y"), BooleanClause.Occur.MUST);
		booleanQuery2.add(PlaceQuery.getStageQuery("1"), BooleanClause.Occur.MUST);
		if(searchVo.getKeyword()!=null){
			booleanQuery2.add(PlaceQuery.getNameQuery(searchVo.getKeyword()),BooleanClause.Occur.MUST);
		}
		
		booleanQuery2.add(booleanQuery, BooleanClause.Occur.MUST);

		return booleanQuery2;
	}
	
//	private  void addKeyWordQuery(String word,String field,BooleanQuery booleanQuery){
//		if(StringUtil.isEmptyString(word)){
//			return;
//		}
//		Query query = null;
//		try {
//			query = QueryUtil.getQueryParser(word,field);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		booleanQuery.add(query, BooleanClause.Occur.MUST);
//	}
	
	public static BooleanQuery getNewProductSearchForClient(ClientRouteSearchVO searchVo){
		String poductNameSearchKeywords = searchVo.getPoductNameSearchKeywords();
		String keyword2 = treatKeyWord(searchVo.getKeyword2());
		String visitDay = treatKeyWord(searchVo.getVisitDay());
		String fromDest = treatKeyWord(searchVo.getFromDest());
		String toDest = treatKeyWord(searchVo.getToDest());
		String tagName = treatKeyWord(searchVo.getTag());
		String productChannel = searchVo.getChannel();
		List<String> productType = searchVo.getProductType();
		List<String> subProductType = searchVo.getSubProductType();
		List<Long> productIds = searchVo.getProductIds();
		String city = treatKeyWord(searchVo.getCity());
		String subject = treatKeyWord(searchVo.getSubject());	
		String traffic = treatKeyWord(searchVo.getTraffic());
		String playLine = treatKeyWord(searchVo.getPlayLine());
		String playFeature = treatKeyWord(searchVo.getPlayFeature());
		String hotelType = treatKeyWord(searchVo.getHotelType());
		String hotelLocation = treatKeyWord(searchVo.getHotelLocation());
		String playBrand = treatKeyWord(searchVo.getPlayBrand());
		String playNum = CommonUtil.escapeString(treatKeyWord(searchVo.getPlayNum()));
		String scenicPlace = treatKeyWord(searchVo.getScenicPlace());
		String landTraffic = treatKeyWord(searchVo.getLandTraffic());
		String landFeature = treatKeyWord(searchVo.getLandFeature());
		String keyword = searchVo.getKeyword();
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();
		
		try {
			// 二次搜索
			if (StringUtils.isNotEmpty(keyword2)) {
				booleanQuery.add(productquery.getProductNameQuery(keyword2), BooleanClause.Occur.MUST);
			}
			
			/**
			 * 判断是否是id搜索 看是否是数字
			 */
			if(StringUtil.isNumber(toDest)){
				if(productIds == null)	
					productIds=new ArrayList<Long>();
				productIds.add(Long.valueOf(toDest));
				toDest = null;
			}
			
			BooleanQuery idsQuery = new BooleanQuery();
			if(productIds!=null){
				for (Long productId : productIds) {
					Query query = productquery.getProductIDQuery(productId.toString());
					idsQuery.add(query, BooleanClause.Occur.SHOULD);
				}
			}
			
			
			if (StringUtils.isNotEmpty(subject)) {
				booleanQuery.add(new TermQuery(new Term(ProductDocument.ROUTE_TOPIC_NOTOKEN,subject)), BooleanClause.Occur.MUST);
			}
			
			// 标签筛选
			if (StringUtils.isNotEmpty(tagName)) {
				//log.info("tagName_query" + tagName);
				booleanQuery.add(productquery.getTagNameQuery(tagName), BooleanClause.Occur.MUST);
			}
			
			if (StringUtils.isNotEmpty(traffic)) {
				Query query = QueryUtil.getQueryParser(traffic,ProductDocument.TRAFFIC);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			
						
			// 游玩天数
			if (StringUtils.isNotEmpty(visitDay)) {
				booleanQuery.add(productquery.getVisitDayQuery(visitDay), BooleanClause.Occur.MUST);
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
			
			//游玩特色
			if(StringUtils.isNotBlank(playFeature)){
				//对 游玩特色进行分词
//				booleanQuery.add(productquery.getPlayFeaturesQuery(playFeature),BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.PLAY_FEATURES_NOTOKEN,playFeature)), BooleanClause.Occur.MUST);
			}
			
			// 游玩线路
			if (StringUtils.isNotBlank(playLine)) {
				//对游玩线路进行分词
//				booleanQuery.add(productquery.getPlayLineQuery(playLine), BooleanClause.Occur.MUST);
				booleanQuery.add(new TermQuery(new Term(ProductDocument.PLAY_LINE_NOTOKEN,playLine)), BooleanClause.Occur.MUST);
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
			
			if (StringUtils.isNotEmpty(productChannel)) {
				booleanQuery.add(productquery.getProductChannelQuery(productChannel), BooleanClause.Occur.MUST);
			}
			
			if (StringUtils.isNotEmpty(city)) {
				booleanQuery.add(new TermQuery(new Term(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT_NOTOKEN,city)), BooleanClause.Occur.MUST);
			}

			// 出发地
			BooleanQuery formDestQuery = new BooleanQuery();
			if(!Constant.CHANNEL.TUANGOU.name().equals(productChannel)){
				//id搜索为0的时候 不设置出发地
				if(idsQuery.clauses().size() == 0) {
					formDestQuery.add(productquery.getFromDestQuery("ANYWHERE"), BooleanClause.Occur.SHOULD);
				}
			}

			List<String> fromDestArray = new ArrayList<String>();
			if (StringUtil.isNotEmptyString(fromDest)) {
				StringTokenizer st = new StringTokenizer(fromDest, ",");
				while (st.hasMoreTokens()) {
					String dest = st.nextToken();
					fromDestArray.add(dest);
				}
			}
			
			for (String dest : fromDestArray) {
				 formDestQuery.add(productquery.getFromDestQuery(dest), BooleanClause.Occur.SHOULD);
			}
			
			// 目的地,标地
			if (StringUtils.isNotEmpty(toDest)&& StringUtils.isEmpty(poductNameSearchKeywords)) {// 注意直接在老版本上更改
				putLocalSession(toDest);
//				List<String> list=new ArrayList<String>();
//				list.add(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT);
//				Query query = QueryUtil.getMultiFiedlParserQueryByHasField(toDest,list);
//				booleanQuery.add(query, BooleanClause.Occur.MUST);
				
				Query query = QueryUtil.getMultiFiedlParserQuery(toDest);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			
			/** 产品类型 */
			BooleanQuery productTypebooleanQuery = new BooleanQuery();
			if (!productType.isEmpty() && productType.size() > 0) {
				for (int i = 0; i < productType.size(); i++) {
					productTypebooleanQuery.add(productquery.getProductTypeQuery(productType.get(i)) , BooleanClause.Occur.SHOULD);
				}
			} 
			
			/** sub产品类型 */
			BooleanQuery subProductTypebooleanQuery = new BooleanQuery();
			if (subProductType != null&&!subProductType.isEmpty() && subProductType.size() > 0) {
				for (int i = 0; i < subProductType.size(); i++) {
					subProductTypebooleanQuery.add(productquery.getSubProductTypeQuery(subProductType.get(i)) , BooleanClause.Occur.SHOULD);
				}
			} 
			
			// 对线路产品的名称进行搜索,支持空格分词功能
			BooleanQuery poductNameSearchQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(poductNameSearchKeywords)) {
				putLocalSession(poductNameSearchKeywords);
				List<String> list=new ArrayList<String>();
				list.add(ProductDocument.PRODUCT_NAME);
				Query query = QueryUtil.getMultiFiedlParserQueryByHasField(poductNameSearchKeywords,list);
				booleanQuery.add(query, BooleanClause.Occur.MUST);

			}

			if (StringUtils.isNotEmpty(keyword) && StringUtils.isEmpty(poductNameSearchKeywords)) {
				//加入分词器的query
				putLocalSession(keyword);
				Query query = QueryUtil.getMultiFiedlParserQuery(keyword);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			
			if (formDestQuery.clauses().size() > 0) {
				booleanQuery.add(formDestQuery, BooleanClause.Occur.MUST);
			}

//			if (cityQuery.clauses().size() > 0) {
//				booleanQuery.add(cityQuery, BooleanClause.Occur.MUST);
//			}

			if (productTypebooleanQuery.clauses().size() > 0) {
				booleanQuery.add(productTypebooleanQuery, BooleanClause.Occur.MUST);
			}
			
			if (idsQuery.clauses().size() > 0) {
				booleanQuery.add(idsQuery, BooleanClause.Occur.MUST);
			}
			
			
			if (subProductTypebooleanQuery.clauses().size() > 0) {
				booleanQuery.add(subProductTypebooleanQuery, BooleanClause.Occur.MUST);
			}

			if (poductNameSearchQuery.clauses().size() > 0) {
				booleanQuery.add(poductNameSearchQuery, BooleanClause.Occur.MUST);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建产品query异常，关键字" +  "，QueryUtil.getPlacesSearchQuery(...)");
		}
		LocalSession.remove();
		return booleanQuery;
		
		
	}

	/**
	 * 构建线路的搜索查询Query
	 * 
	 * @param keywordMap
	 * @return
	 */
	public static BooleanQuery getProductSearchAllQuery(ClientRouteSearchVO searchVo ){
		
		String poductNameSearchKeywords = searchVo.getPoductNameSearchKeywords();
		String keyword2 = treatKeyWord(searchVo.getKeyword2());
		String visitDay = treatKeyWord(searchVo.getVisitDay());
		String fromDest = treatKeyWord(searchVo.getFromDest());
		String toDest = treatKeyWord(searchVo.getToDest());
		String tagName = treatKeyWord(searchVo.getTag());
		String productChannel = searchVo.getChannel();
		List<String> productType = searchVo.getProductType();
		List<String> subProductType = searchVo.getSubProductType();
		String city = treatKeyWord(searchVo.getCity());
		String subject = treatKeyWord(searchVo.getSubject());		
		
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();

		try {
			// 二次搜索
			if (StringUtils.isNotEmpty(keyword2)) {
				// log.info("keyword2 : " + keyword2);
				booleanQuery.add(productquery.getProductNameQuery(keyword2), BooleanClause.Occur.MUST);
			}

			// 主题筛选
			if (StringUtils.isNotEmpty(subject)) {
				// log.info("subject_query" + subject);
//				booleanQuery.add(productquery.getTopicQuery(subject), BooleanClause.Occur.MUST);
				//主题进行分词
				Query query = QueryUtil.getQueryParser(subject,ProductDocument.ROUTE_TOPIC);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			// 标签筛选
			if (StringUtils.isNotEmpty(tagName)) {
				// log.info("tagName_query" + tagName);
				booleanQuery.add(productquery.getTagNameQuery(tagName), BooleanClause.Occur.MUST);
			}			
			// 游玩天数
			if (StringUtils.isNotEmpty(visitDay)) {
				booleanQuery.add(productquery.getVisitDayQuery(visitDay), BooleanClause.Occur.MUST);
			}			
					
			if (StringUtils.isNotEmpty(productChannel)) {
				booleanQuery.add(productquery.getProductChannelQuery(productChannel), BooleanClause.Occur.MUST);
			}
			

			// 地区筛选
			BooleanQuery cityQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(city)) {
				// log.info("city_query" + city);
				cityQuery.add(productquery.getProductAlltoPlaceConent(city), BooleanClause.Occur.SHOULD);
				cityQuery.add(productquery.getProductAlltoPlaceIds(city), BooleanClause.Occur.SHOULD);
			}

			// 出发地
			BooleanQuery formDestQuery = new BooleanQuery();
			/*
			 * 由于fromDest存在，routeType=domesticAll 中查询不到
			 * subPrudctType=FREENESS的自由行产品，需要单独查. *
			 */			
		    if (StringUtils.isNotEmpty(fromDest) &&subProductType!=null && !subProductType.contains("FREENESS")) {
					// log.info("query_fromDest="+fromDest);
		    	if (subProductType==null||subProductType.isEmpty()) {
		    		/**
		    		 * 区部类型需要添加自由行的查询数据。
		    		 */
		    		formDestQuery.add(new TermQuery(new Term(ProductDocument.FROM_DEST, "ANYWHERE")), BooleanClause.Occur.SHOULD);
		    		 if (fromDest!=null&&fromDest.contains(",")) {
						 String[] fromDests = fromDest.split(",");
						 formDestQuery.add(productquery.getFromDestQuery(fromDests[0]), BooleanClause.Occur.SHOULD);
						 formDestQuery.add(productquery.getFromDestQuery(fromDests[1]), BooleanClause.Occur.SHOULD);
					 } else if(fromDest!=null){
						 formDestQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.SHOULD);
					 }
				} else {
					formDestQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.MUST);
				}
			} else if (StringUtils.isNotEmpty(fromDest)&& subProductType != null && !subProductType.isEmpty()) {//&&subProductType != null && subProductType.indexOf(",") > 0
				 if (fromDest!=null&&fromDest.contains(",")){
					 String[] fromDests = fromDest.split(",");
					 formDestQuery.add(productquery.getFromDestQuery(fromDests[0]), BooleanClause.Occur.SHOULD);
					 formDestQuery.add(productquery.getFromDestQuery(fromDests[1]), BooleanClause.Occur.SHOULD);
				 } else if(fromDest!=null){
					formDestQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.SHOULD);
				 }
				formDestQuery.add(new TermQuery(new Term(ProductDocument.FROM_DEST, "ANYWHERE")), BooleanClause.Occur.SHOULD);
				// log.info("fromdest_query =ANYWHERE+"+fromDest);
			}
			BooleanQuery toDestQuery = new BooleanQuery();
			// 目的地,标地
			if (StringUtils.isNotEmpty(toDest) && StringUtils.isEmpty(poductNameSearchKeywords)) {// 注意直接在老版本上更改
//				toDestQuery.add(productquery.getProductAlltoPlaceConent(toDest), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getdepartAreaQuery(toDest), BooleanClause.Occur.SHOULD);
//				toDestQuery.add(productquery.getPlaceKeywordBindQuery(toDest), BooleanClause.Occur.SHOULD);
//				if (StringUtils.isEmpty(subject)) {
//					toDestQuery.add(productquery.getTopicQuery(subject), BooleanClause.Occur.SHOULD);
//				}
//				if (StringUtils.isEmpty(tagName)) {
//					toDestQuery.add(productquery.getTagNameQuery(tagName), BooleanClause.Occur.SHOULD);
//				}
//				if (StringUtils.isEmpty(city)) {
//					toDestQuery.add(productquery.getProductAlltoPlaceIds(city), BooleanClause.Occur.SHOULD);
//				}
				
				// toDestQuery.add(productquery.getProductNameQuery(toDest),
				// BooleanClause.Occur.SHOULD);
				//cdf修改增加分词
				putLocalSession(toDest);
				List<String> list=new ArrayList<String>();
				list.add(ProductDocument.PRODUCT_ALLTO_PLACE_CONTENT);
				list.add(ProductDocument.DEPART_AREA);
				list.add(ProductDocument.PLACE_KEYWORD_BIND);
				Query query = QueryUtil.getMultiFiedlParserQueryByHasField(toDest,list);
				
				booleanQuery.add(query, BooleanClause.Occur.MUST);
				
			}
			
			/** 产品类型 */
			BooleanQuery productTypebooleanQuery = new BooleanQuery();
			if (!productType.isEmpty() && productType.size() > 0) {
				for (int i = 0; i < productType.size(); i++) {
					productTypebooleanQuery.add(productquery.getProductTypeQuery(productType.get(i)) , BooleanClause.Occur.SHOULD);
				}
			} 
			
			/** sub产品类型 */
			BooleanQuery subProductTypebooleanQuery = new BooleanQuery();
			if (subProductType != null&&!subProductType.isEmpty() && subProductType.size() > 0) {
				for (int i = 0; i < subProductType.size(); i++) {
					subProductTypebooleanQuery.add(productquery.getSubProductTypeQuery(subProductType.get(i)) , BooleanClause.Occur.SHOULD);
				}
			} 
			
			// 对线路产品的名称进行搜索,支持空格分词功能
			BooleanQuery poductNameSearchQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(poductNameSearchKeywords)) {
//				 考虑空格分词情况，先切词
//				StringTokenizer stringSplit = new StringTokenizer(poductNameSearchKeywords, " ");
//				while (stringSplit.hasMoreTokens()) {
//					poductNameSearchQuery.add(productquery.getProductNameQuery(stringSplit.nextToken()), BooleanClause.Occur.MUST);
//				}
				//加入分词器的query
				putLocalSession(toDest);
				List<String> list=new ArrayList<String>();
				list.add(ProductDocument.PRODUCT_NAME);
				Query query = QueryUtil.getMultiFiedlParserQueryByHasField(poductNameSearchKeywords,list);
				booleanQuery.add(query, BooleanClause.Occur.MUST);

			}

			if (formDestQuery.clauses().size() > 0) {
				booleanQuery.add(formDestQuery, BooleanClause.Occur.MUST);
			}

			if (cityQuery.clauses().size() > 0) {
				booleanQuery.add(cityQuery, BooleanClause.Occur.MUST);
			}

//			if (toDestQuery.clauses().size() > 0) {
//				booleanQuery.add(toDestQuery, BooleanClause.Occur.MUST);
//			}
			
			if (productTypebooleanQuery.clauses().size() > 0) {
				booleanQuery.add(productTypebooleanQuery, BooleanClause.Occur.MUST);
			}
			
			if (subProductTypebooleanQuery.clauses().size() > 0) {
				booleanQuery.add(subProductTypebooleanQuery, BooleanClause.Occur.MUST);
			}

			if (poductNameSearchQuery.clauses().size() > 0) {
				booleanQuery.add(poductNameSearchQuery, BooleanClause.Occur.MUST);
			}

		} catch (Exception e) {
			log.error("创建产品query异常，关键字" +  "，QueryUtil.getPlacesSearchQuery(...)");
		}
		LocalSession.remove();
		return booleanQuery;
		
		
	}
	
	/**
	 * 构建线路的搜索查询Query
	 * 
	 * @param keywordMap
	 * @return
	 */
	public static BooleanQuery getProductSearchAllQuery(Map<String, String> keywordMap) {
		BooleanQuery booleanQuery = getProductSearchQuery(keywordMap);
		String keyword = treatKeyWord(keywordMap.get("name"));
		ProductQuery productquery = new ProductQuery();
		try {
			if (StringUtils.isNotEmpty(keyword)) {
				Query query = productquery.getConentPhraseQuery(keyword);
				if (query != null && "".equals(query.toString())) {
					query = productquery.getContentQuery(keyword);
				}
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
		}  catch (ParseException e) {
			e.printStackTrace();
		} 
		return booleanQuery;
	}

	public static BooleanQuery getProductSearchQuery(Map<String, String> keywordMap) {
		String poductNameSearchKeywords = keywordMap.get("poductNameSearchKeywords");
		String keyword2 = treatKeyWord(keywordMap.get("keyword2"));
		String keyword = treatKeyWord(keywordMap.get("name"));
		String payMethod = treatKeyWord(keywordMap.get("payMethod"));
		String isTicket = treatKeyWord(keywordMap.get("isTicket"));
		String agio = treatKeyWord(keywordMap.get("agio"));
		String visitDay = treatKeyWord(keywordMap.get("visitDay"));
		String coupon = treatKeyWord(keywordMap.get("coupon"));
		String cashRefund = treatKeyWord(keywordMap.get("cashRefund"));
		String sellPrice = treatKeyWord(keywordMap.get("sellPrice"));
		String isOld = treatKeyWord(keywordMap.get("isOld"));
		// String fromDestContent =
		// treatKeyWord(keywordMap.get(ProductDocument.FROM_DEST_CONTENT));
		// treatKeyWord(keywordMap.get(ProductDocument.TO_DEST_CONTENT));
		String fromDest = treatKeyWord(keywordMap.get(ProductDocument.FROM_DEST));
		String toDest = treatKeyWord(keywordMap.get(ProductDocument.TO_DEST));
		String toDestPinYin = treatKeyWord(keywordMap.get(ProductDocument.PRODUCT_ALLTO_PLACE_PINYIN));
		// String tag = treatKeyWord(keywordMap.get(ProductDocument.TAG));
		String productChannel = keywordMap.get(ProductDocument.PRODUCT_CHANNEL);
		String subProductType = treatKeyWord(keywordMap.get(ProductDocument.SUB_PRODUCT_TYPE));
		String city = treatKeyWord(keywordMap.get("city"));
		String subject = treatKeyWord(keywordMap.get(ProductDocument.SUBJECT));
		String tagName = treatKeyWord(keywordMap.get("tagName"));
		String productRouteTypeName = treatKeyWord(keywordMap.get("productRouteTypeName"));
		BooleanQuery booleanQuery = new BooleanQuery();
		ProductQuery productquery = new ProductQuery();

		try {
			// 二次搜索
			if (StringUtils.isNotEmpty(keyword2)) {
				// log.info("keyword2 : " + keyword2);
				booleanQuery.add(productquery.getProductNameQuery(keyword2), BooleanClause.Occur.MUST);
			}

			// 经济性筛选
			if (StringUtils.isNotEmpty(productRouteTypeName)) {
				// log.info("prodRouteType : " + productRouteTypeName);
				booleanQuery.add(productquery.getProductRouteTypeNameQuery(productRouteTypeName), BooleanClause.Occur.MUST);
			}

			// 主题筛选
			if (StringUtils.isNotEmpty(subject)) {
				// log.info("subject_query" + subject);
				booleanQuery.add(productquery.getTopicQuery(subject), BooleanClause.Occur.MUST);
			}
			// 标签筛选
			if (StringUtils.isNotEmpty(tagName)) {
				// log.info("tagName_query" + tagName);
				booleanQuery.add(productquery.getTagNameQuery(tagName), BooleanClause.Occur.MUST);
			}
			// 支付方式
			if (StringUtils.isNotEmpty(payMethod)) {
				booleanQuery.add(productquery.getPayMethodQuery(payMethod), BooleanClause.Occur.MUST);
			}
			// 门票类型
			if (StringUtils.isNotEmpty(isTicket)) {
				Query query = productquery.getIsTicketQuery(isTicket);
				booleanQuery.add(query, BooleanClause.Occur.MUST);
			}
			// 折扣
			if (StringUtils.isNotEmpty(agio)) {
				booleanQuery.add(productquery.getAgioQuery(agio), BooleanClause.Occur.MUST);
			}
			// 游玩天数
			if (StringUtils.isNotEmpty(visitDay)) {
				booleanQuery.add(productquery.getVisitDayQuery(visitDay), BooleanClause.Occur.MUST);
			}
			// 优惠方式
			if (StringUtils.isNotEmpty(coupon)) {
				booleanQuery.add(productquery.getCouponQuery(coupon), BooleanClause.Occur.MUST_NOT);
			}
			// 优惠方式-返现
			if (StringUtils.isNotEmpty(cashRefund)) {
				booleanQuery.add(productquery.getCashRefundQuery(cashRefund), BooleanClause.Occur.MUST_NOT);
			}
			// 是否是老产品
			if (StringUtils.isNotEmpty(isOld)) {
				booleanQuery.add(productquery.getIsOldQuery(isOld), BooleanClause.Occur.MUST);
			}
			// 售票价格
			if (StringUtils.isNotEmpty(sellPrice)) {
				booleanQuery.add(productquery.getPriceQuery(sellPrice), BooleanClause.Occur.MUST);
			}
			/*
			 * if
			 * (StringUtils.isNotEmpty(fromDestContent)&&!subProductType.equals
			 * ("FREENESS")) {
			 * booleanQuery.add(productquery.getFromDestContetQuery
			 * (fromDestContent), BooleanClause.Occur.MUST); }
			 */

			if (StringUtils.isNotEmpty(productChannel)) {
				booleanQuery.add(productquery.getProductChannelQuery(productChannel), BooleanClause.Occur.MUST);
			}
			/** 页面内栏目 **/
			if (StringUtils.isNotEmpty(subProductType)) {
				// log.info("query_rountType" + subProductType);
				String[] types = subProductType.split(",");
				BooleanQuery q = new BooleanQuery();
				for (String type : types) {
					q.add(productquery.getSubProductTypeQuery(type.trim()), BooleanClause.Occur.SHOULD);
				}

				if (booleanQuery.clauses().size() > 0) {
					booleanQuery.add(q, BooleanClause.Occur.MUST);
				}

			}

			// 地区筛选
			BooleanQuery cityQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(city)) {
				// log.info("city_query" + city);
				cityQuery.add(productquery.getProductAlltoPlaceConent(city), BooleanClause.Occur.SHOULD);
				cityQuery.add(productquery.getProductAlltoPlaceIds(city), BooleanClause.Occur.SHOULD);
			}

			// 出发地
			BooleanQuery formDestQuery = new BooleanQuery();
			/*
			 * 由于fromDest存在，routeType=domesticAll 中查询不到
			 * subPrudctType=FREENESS的自由行产品，需要单独查. *
			 */
			if (StringUtils.isNotEmpty(fromDest) && subProductType.indexOf(",") > 0) {
				formDestQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.SHOULD);
				formDestQuery.add(new TermQuery(new Term(ProductDocument.FROM_DEST, "ANYWHERE")), BooleanClause.Occur.SHOULD);
				// log.info("fromdest_query =ANYWHERE+"+fromDest);

			} else if (StringUtils.isNotEmpty(fromDest) && !subProductType.equals("FREENESS")) {
				// log.info("query_fromDest="+fromDest);
				formDestQuery.add(productquery.getFromDestQuery(fromDest), BooleanClause.Occur.MUST);
			}

			BooleanQuery toDestQuery = new BooleanQuery();
			// 目的地,标地
			if (StringUtils.isNotEmpty(toDest) && StringUtils.isEmpty(poductNameSearchKeywords)) {// 注意直接在老版本上更改
				toDestQuery.add(productquery.getProductAlltoPlaceConent(toDest), BooleanClause.Occur.SHOULD);
				toDestQuery.add(productquery.getdepartAreaQuery(toDest), BooleanClause.Occur.SHOULD);
				if (StringUtils.isEmpty(subject)) {
					toDestQuery.add(productquery.getTopicQuery(toDest), BooleanClause.Occur.SHOULD);
				}
				if (StringUtils.isEmpty(tagName)) {
					toDestQuery.add(productquery.getTagNameQuery(toDest), BooleanClause.Occur.SHOULD);
				}
				if (StringUtils.isEmpty(city)) {
					toDestQuery.add(productquery.getProductAlltoPlaceIds(toDest), BooleanClause.Occur.SHOULD);
				}
				toDestQuery.add(productquery.getPlaceKeywordBindQuery(toDest), BooleanClause.Occur.SHOULD);
				// toDestQuery.add(productquery.getProductNameQuery(toDest),
				// BooleanClause.Occur.SHOULD);
			}

			// 对线路产品的名称进行搜索,支持空格分词功能
			BooleanQuery poductNameSearchQuery = new BooleanQuery();
			if (StringUtils.isNotEmpty(poductNameSearchKeywords)) {
				// 考虑空格分词情况，先切词
				StringTokenizer stringSplit = new StringTokenizer(poductNameSearchKeywords, " ");
				while (stringSplit.hasMoreTokens()) {
					poductNameSearchQuery.add(productquery.getProductNameQuery(stringSplit.nextToken()), BooleanClause.Occur.MUST);
				}
			}

			if (formDestQuery.clauses().size() > 0) {
				booleanQuery.add(formDestQuery, BooleanClause.Occur.MUST);
			}

			if (cityQuery.clauses().size() > 0) {
				booleanQuery.add(cityQuery, BooleanClause.Occur.MUST);
			}

			if (toDestQuery.clauses().size() > 0) {
				booleanQuery.add(toDestQuery, BooleanClause.Occur.MUST);
			}

			if (poductNameSearchQuery.clauses().size() > 0) {
				booleanQuery.add(poductNameSearchQuery, BooleanClause.Occur.MUST);
			}

		} catch (IOException e) {
			log.error("创建产品query异常，关键字" + keyword + "，QueryUtil.getPlacesSearchQuery(...)");
		}

		return booleanQuery;
	}

	public static Query getCityQuery(String keyword) {
		BooleanQuery booleanQuery = new BooleanQuery();
		Query query = new TermQuery(new Term(PlaceDocument.NAME, keyword));
		Query stageQuery = new TermQuery(new Term(PlaceDocument.STAGE, "1"));
		booleanQuery.add(query, BooleanClause.Occur.MUST);
		booleanQuery.add(stageQuery, BooleanClause.Occur.MUST);
		return booleanQuery;
	}

	/**
	 * 预处理搜索关键词
	 * 
	 * @param keyword
	 * @return
	 */
	public static String treatKeyWord(String keyword) {
		if (StringUtils.isEmpty(keyword)) {
			return null;
		}
		return keyword.replace("?", "").replace("+", "").replace("-", "").replace("*", "").replace("\\", "").replace("/", "").replace("=", "").replace("<", "").replace(">", "").replace("!", "").replace(".", "").replace("@", "").replace("$", "").replace("%", "").replace("。", "").replace("#", "").replace(" ", "");

	}

	public static String[] splitKeyword(String keyword) {
		String[] key = keyword.split("_");
		return key;
	}

	public static void putLocalSession (String keyword){
		if (StringUtils.isNotEmpty(keyword)) {
			//先对keyword进行拆分，查找是否其中有同义词存在，分别对拆分后的keyword进行同义词追加
			List ikKeywords =OneSearchAction.ikSegmenter(keyword);
			log.info("keyword拆分结果1:"+ikKeywords);
			LocalSession.set(keyword, ikKeywords);
	}
	}
	
}
