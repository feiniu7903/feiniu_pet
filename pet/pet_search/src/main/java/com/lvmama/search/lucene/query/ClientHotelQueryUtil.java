package com.lvmama.search.lucene.query;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.VerHotelSearchVO;
import com.lvmama.comm.utils.GeoLocation;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.document.VerhotelDocument;
import com.lvmama.search.synonyms.LikeHashMap;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.LocalCacheManager;

public class ClientHotelQueryUtil {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Query getVerHotelQuery(VerHotelSearchVO params){
		
		BooleanQuery booleanQuery = new BooleanQuery();
		String keyword = params.getKeyword();
		String longitude = params.getLongitude();
		String latitude = params.getLatitude();
		String distance = params.getDistance();
		String minproductsprice = params.getMinproductsprice();
		String maxproductsprice = params.getMaxproductsprice();
		String hotelstar = params.getHotelStar();
//		String hotel_id=params.getSearchId();
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
//				    BooleanQuery booleanQuery1 = new BooleanQuery();
//				    Query districtQuery=null;
//				    //如果districtid 为空 ，从url进来和从热门目的地进来,区域用关键字
//				    if(StringUtil.isEmptyString(districtid)){
//				    	districtQuery =new TermQuery(new Term(VerhotelDocument.DISTRICT, keyword));
//				    }
//				    else{
//				    	districtQuery =new TermQuery(new Term(VerhotelDocument.DISTRICTID, districtid));
//				    }
//				    
//					booleanQuery1.add(districtQuery,BooleanClause.Occur.MUST);
//					booleanQuery.add(booleanQuery1,BooleanClause.Occur.MUST);
//					
					String[] longitudes = longitude.split(",");
					String[] latitudes = latitude.split(",");
					BooleanQuery booleanQuery1 = new BooleanQuery();
					for(int i=0;i<longitudes.length;i++){
						BooleanQuery booleanQuery2 = new BooleanQuery();
						GeoLocation gl = GeoLocation.fromDegrees(Double.parseDouble(latitudes[i]), Double.parseDouble(longitudes[i]));
						GeoLocation[] minMaxGL = gl.boundingCoordinates(Double.valueOf(distance));
						Query longitudeQuery = PlaceQuery.getLongitudeQuery(minMaxGL[0].getLongitudeInDegrees(), minMaxGL[1].getLongitudeInDegrees(), true, true);
						Query latitudeQuery = PlaceQuery.getLatitudeQuery(minMaxGL[0].getLatitudeInDegrees(), minMaxGL[1].getLatitudeInDegrees(), true, true);
						booleanQuery2.add(longitudeQuery,BooleanClause.Occur.MUST);
						booleanQuery2.add(latitudeQuery,BooleanClause.Occur.MUST);
						booleanQuery1.add(booleanQuery2,BooleanClause.Occur.SHOULD);
					}
					booleanQuery.add(booleanQuery1,BooleanClause.Occur.MUST);
					
		}else{//按照关键词查询
			if (StringUtils.isNotEmpty(keyword)) {
//				if(SearchConstants.FROMDESTS.getCode(keyword) == null){//错误的出发地,默认使用上海
//					keyword = "上海";
//				}
				//先对keyword进行拆分，查找是否其中有同义词存在，分别对拆分后的keyword进行同义词追加
				List<String> ikKeywords = ikSegmenter(keyword);
				int j = 0;
				String[] arrSynonyms = new String[ikKeywords.size()];
				keyword = "";
				for (int i = 0; i < ikKeywords.size(); i++) {
					//取得keyword同义词进行search
					LikeHashMap synonymsMap = (LikeHashMap) LocalCacheManager.get("COM_SEARCH_KEYWORD_SYNONYMS");
					//keyword满足同义词的分词数量最大为3组
					if(synonymsMap.get(String.valueOf(ikKeywords.get(i)), true).size()>0 && j<3){
						arrSynonyms[i]="";
						for (Iterator iter = ((List)synonymsMap.get(String.valueOf(ikKeywords.get(i)), true).get(0)).iterator(); iter.hasNext();) {
							arrSynonyms[i] = arrSynonyms[i] + (String)iter.next() + ",";
						}
						keyword = keyword + arrSynonyms[i];
						arrSynonyms[i] = arrSynonyms[i].substring(0, arrSynonyms[i].length()-1).trim();
						++j;
					}else{
						if(StringUtils.isNotBlank(arrSynonyms[i])){
							arrSynonyms[i] = arrSynonyms[i] + String.valueOf(ikKeywords.get(i));
						}else{
							arrSynonyms[i] = String.valueOf(ikKeywords.get(i));
						}
						keyword = keyword + String.valueOf(ikKeywords.get(i))  + ",";
					}
				}
				//把搜索内容的拆分的各个数组进行配对
				List synonymsList = new ArrayList<String[]>();
				for (int i = 0; i < arrSynonyms.length; i++) {
					List tempList = new ArrayList<String>();
					String[] arr = arrSynonyms[i].split(",");
					if (synonymsList.size()>0){
						for (Object object : synonymsList) {
							String synonyms1 = new String();
							synonyms1 = String.valueOf(object);
							for (int l = 0; l < arr.length; l++) {
								String synonyms = new String();
								synonyms = synonyms1 + arr[l]+",";
								tempList.add(synonyms);
							}
						}
						synonymsList = (List) ((ArrayList) tempList).clone();
					}else{
						for (int l = 0; l < arr.length; l++) {
							String synonyms = new String();
							synonyms = synonyms + arr[l]+",";
							tempList.add(synonyms);
						}
						synonymsList = (List) ((ArrayList) tempList).clone();
					}
					
				}
				for (int i = 0; i < synonymsList.size(); i++) {
					synonymsList.set(i, String.valueOf(synonymsList.get(i)).substring(0, String.valueOf(synonymsList.get(i)).length()-1).trim());
				}
				
				keyword = keyword.substring(0, keyword.length()-1);
				//把分组同义词配对结果放入缓存
				LocalSession.set(keyword, synonymsList);
				System.out.println("keyword拆分结果===================:"+keyword);
				
				
				//加入分词器的query
				List<String> list=new ArrayList<String>();
				list.add(VerhotelDocument.DISTRICT);
				list.add(VerhotelDocument.HOTEL_NAME);
//				 Query districtQuery=null;
//				if(StringUtil.isEmptyString(districtid)){
//			    	districtQuery =new TermQuery(new Term(VerhotelDocument.DISTRICT, keyword));
//			    }
//			    else{
//			    	districtQuery =new TermQuery(new Term(VerhotelDocument.DISTRICTID, districtid));
//			    }
//				booleanQuery.add(districtQuery, BooleanClause.Occur.MUST);
				try {
					Query query = getMultiFiedlParserQueryByHasField(keyword,list);
					booleanQuery.add(query, BooleanClause.Occur.MUST);
				} catch (ParseException e) {
					e.printStackTrace();
					throw new RuntimeException();
					
				}
				
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

	// 分词器
	private static List<String> ikSegmenter(String str) {
		List<String> iklist = new ArrayList<String>();
		StringReader reader = new StringReader(str);
		IKSegmenter ik = new IKSegmenter(reader, true);// 后一个变量决定是否消歧
		Lexeme lexeme = null;
		try {
			while ((lexeme = ik.next()) != null) {
				iklist.add(lexeme.getLexemeText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(iklist.size()<=0){
			iklist.add(str);
		}
		return iklist;
	}
	
	
	/**
	 * 分词query
	 * 通过方法，可以自己传进field来，用list封装
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Query getMultiFiedlParserQueryByHasField(String keyword, List<String> fieldlist) throws ParseException {
		List<String> tmpkeywordList = (ArrayList<String>) LocalSession.get(keyword);
		List<String[]> keywordList = new ArrayList<String[]>();
		for (String keywords : tmpkeywordList) {
			keywordList.add(keywords.split(","));
		}
		BooleanQuery listquery = new BooleanQuery();
		if (keywordList != null && keywordList.size() > 0) {

			for (String[] keywords : keywordList) {
				if (keywords != null && keywords.length > 0) {
					// 定义一个数组的query
					BooleanQuery arrayquery = new BooleanQuery();
					for (int i = 0; i < keywords.length; i++) {
						String name = keywords[i];
						if (StringUtils.isNotEmpty(name)) {
							BooleanQuery multifieldquery = new BooleanQuery();
							if (fieldlist != null && fieldlist.size() > 0) {
								for (String field : fieldlist) {
									WildcardQuery termquery = new WildcardQuery(
											new Term(field, "*" + name + "*"));
									multifieldquery.add(termquery,
											BooleanClause.Occur.SHOULD);
								}
							}
							arrayquery.add(multifieldquery,
									BooleanClause.Occur.MUST);
						}

					}
					listquery.add(arrayquery, BooleanClause.Occur.SHOULD);
				}
			}
		}
		return listquery;
	}
}
