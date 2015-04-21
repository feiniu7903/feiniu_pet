package com.lvmama.search.lucene.service.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerHotelSearchVO;
import com.lvmama.comm.utils.GeoLocation;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.LuceneContext;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.PlaceHotelDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.document.VerhotelDocument;
import com.lvmama.search.lucene.ikSimilarity.IKSimilarity;
import com.lvmama.search.lucene.score.FinalSearchComparator;
import com.lvmama.search.lucene.score.LuceneCustomScoreQuery;
import com.lvmama.search.lucene.score.VerLuceneCustomScoreQuery;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LuceneCommonDic;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 酒店搜索
 * 
 * @author yanggan
 * 
 */
@Service("newVerHotelSearchService")
public class NewVerHotelSearchServiceImpl implements NewBaseSearchService {

	@Override
	public List<PlaceHotelBean> search(Query query, SORT... sorts) {
		return this
				.search(SearchConstants.MAX_SEARCH_RESULT_SIZE, query, sorts);
	}

	@Override
	public List<PlaceHotelBean> search(int max_search_size, Query query,
			SORT... sorts) {
		List<PlaceHotelBean> placeList = new ArrayList<PlaceHotelBean>();
		IndexSearcher indexSearcher = LuceneContext.getPlaceHotelSearcher();
		try {
			Sort sort = CommonUtil.initSortField(sorts);
			TopDocs topDocs = indexSearcher.search(query, null,
					max_search_size, sort);
			ScoreDoc[] hits = topDocs.scoreDocs;
			AbstactDocument abstactDocument = new PlaceHotelDocument();
			if (topDocs.totalHits > 0) {
				for (ScoreDoc sd : hits) {
					Document doc = indexSearcher.doc(sd.doc);
					PlaceHotelBean bean = (PlaceHotelBean) abstactDocument
							.parseDocument(doc);
					float score = sd.score;
					placeList.add(bean);
				}
			}
			// 对总分进行排序
		} catch (IOException e) {
			e.printStackTrace();
		}
		return placeList;
	}

	@Override
	public PageConfig<PlaceHotelBean> search(int pageSize, int currentPage,
			Query query, SORT... sorts) {
		List<PlaceHotelBean> productList = this.search(query, sorts);
		PageConfig<PlaceHotelBean> pageConfig = PageConfig.page(
				productList.size(), pageSize, currentPage);
		pageConfig.setAllItems(productList);
		return pageConfig;
	}

	@Override
	public PageConfig search(Integer pageSize, Integer page, Query query_2,
			SearchVO sv, SORT[] sorts) {
		List<VerHotelBean> verhotelList = this.search(query_2, sv, sorts);
		PageConfig<VerHotelBean> pageConfig = PageConfig.page(
				verhotelList.size(), pageSize, page);
		pageConfig.setAllItems(verhotelList);
		return pageConfig;
	}

	@Override
	public List search(int max_search_size, Query query, SearchVO sv,
			SORT... sorts) {
		VerHotelSearchVO verHotelSearchVO = (VerHotelSearchVO) sv;
		List<VerHotelBean> verHotelList = new ArrayList<VerHotelBean>();
		List<VerHotelBean> tmpverHotelList = new ArrayList<VerHotelBean>();
		IndexSearcher indexSearcher = LuceneContext.getVerHotelSearcher();

		VerLuceneCustomScoreQuery customerQuery = new VerLuceneCustomScoreQuery(query);
		indexSearcher.setSimilarity(new IKSimilarity());
		try {
			Sort sort = CommonUtil.initSortField(sorts);
			TopDocs topDocs = CommonUtil.toSearchIsHasSort(max_search_size, indexSearcher, customerQuery, sort, sorts);
			ScoreDoc[] hits = topDocs.scoreDocs;
			AbstactDocument abstactDocument = new VerhotelDocument();
			// 获取搜索字，默认为上海
			String keyword = LuceneCommonDic.defaultWord;
			if (StringUtils.isNotEmpty(sv.getKeyword())) {
				keyword = sv.getKeyword();
			}
			if (topDocs.totalHits > 0) {
				for (ScoreDoc sd : hits) {
					Document doc = indexSearcher.doc(sd.doc);
					float score = sd.score;
					double distance=0;
					VerHotelBean bean = (VerHotelBean) abstactDocument
							.parseDocument(doc);
					if("101634".equals(bean.getHotel_id())){
						System.out.println(111);
					}
					//如果查的是产品本身的话,就不考虑是不是有效的产品
					if(StringUtil.isNotEmptyString(bean.getHotel_id())&& !bean.getHotel_id().equals(verHotelSearchVO.getSearchId()))
					{
						//没有可售的直接沉底用queryTimes   effectTimes
						if (StringUtil.isNotEmptyString(bean.getEffect_times())) {
							if (StringUtil.isNotEmptyString(verHotelSearchVO.getQueryTimes())&& verHotelSearchVO.getQueryTimes().length() > 0) {
								String tmp1[]=verHotelSearchVO.getQueryTimes().split(",");
								String tmp2[]=bean.getEffect_times().split(",");
								boolean isEffect=isEffectProduct(tmp1,tmp2,bean.getMin_stay_day(),bean.getMax_stay_day());
								if(!isEffect){
									tmpverHotelList.add(bean);
									continue;
								}
							}
							//有问题就判断数字为1
							else{
								if(!bean.getEffect_times().contains("1")){
									tmpverHotelList.add(bean);
									continue;
								}
							}
						}
						//为空 就是没有有效的 直接沉底
						else{
							tmpverHotelList.add(bean);
							continue;
						}
					}
						
					if (verHotelSearchVO.getRanktype().equals("2") && sorts==null) {
						if(StringUtil.isNotEmptyString(bean.getLongitude())&& StringUtil.isNotEmptyString(bean.getLatitude())){
							distance = GeoLocation.getDistanceBy2point(
										new Float(verHotelSearchVO.getLongitude()),
										new Float(verHotelSearchVO.getLatitude()),
										new Float(bean.getLongitude()),
										new Float(bean.getLatitude()));
							score = new Float(distance);
						}
						else{
							score=new Float(Long.MAX_VALUE);
						}
						
					}
					bean.setDistance(distance);
					bean.setScore(score);
					verHotelList.add(bean);
				}
			}
			// 对总分进行排序
			if (verHotelSearchVO.getRanktype().equals("2")) {
				FinalSearchComparator comparator = new FinalSearchComparator(1);
				Collections.sort(verHotelList, comparator);
			}else{
				FinalSearchComparator comparator = new FinalSearchComparator();
				Collections.sort(verHotelList, comparator);

			}
			if(tmpverHotelList!=null && tmpverHotelList.size()>0){
				for(VerHotelBean verHotelBean:tmpverHotelList){
					verHotelList.add(verHotelBean);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return verHotelList;
	}

	private boolean isEffectProduct(String[] queryTimes, String[] effectTimes,String minStay,String maxStay) {
		String tmpchoose="";
		int min=0;
		int max=0;
		if(StringUtil.isNotEmptyString(minStay)&&minStay.matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")){
			min=Integer.parseInt(minStay);
		}else
		{
			return false;
		}
		if(StringUtil.isNotEmptyString(maxStay)&&maxStay.matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")){
			max=Integer.parseInt(maxStay);
		}else
		{
			return false;
		}
		if(queryTimes.length<min || queryTimes.length>max ){
			return false;
		}
		for(int i=0;i<queryTimes.length;i++){
			for(int j=0;j<effectTimes.length;j++){
				if(queryTimes[i].equals(effectTimes[j])){
					tmpchoose="true"+","+tmpchoose;
					continue;
				}
			}
		}
		if(StringUtil.isNotEmptyString(tmpchoose) && tmpchoose.split(",").length==queryTimes.length  ){
			return true;
		}
		
		return false;
	}

	@Override
	public List search(Query query, SearchVO sv, SORT... sorts) {
		return this.search(SearchConstants.MAX_SEARCH_RESULT_SIZE, query, sv,
				sorts);
	}

}
