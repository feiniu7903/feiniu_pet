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
import com.lvmama.comm.search.vo.VerPlaceBean;
import com.lvmama.comm.utils.GeoLocation;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.LuceneContext;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.PlaceHotelDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.document.VerhotelDocument;
import com.lvmama.search.lucene.document.VerplaceDocument;
import com.lvmama.search.lucene.ikSimilarity.IKSimilarity;
import com.lvmama.search.lucene.score.FinalSearchComparator;
import com.lvmama.search.lucene.score.LuceneCustomScoreQuery;
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
@Service("newVerPlaceSearchService")
public class NewVerPlaceSearchServiceImpl implements NewBaseSearchService {

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
		List<VerPlaceBean> verPlaceList = new ArrayList<VerPlaceBean>();
		IndexSearcher indexSearcher = LuceneContext.getVerHotelPlaceSearcher();

		indexSearcher.setSimilarity(new IKSimilarity());
		try {
			TopDocs  topDocs = indexSearcher.search(query, null,
					max_search_size);
			ScoreDoc[] hits = topDocs.scoreDocs;
			AbstactDocument abstactDocument = new VerplaceDocument();
			if (topDocs.totalHits > 0) {
				for (ScoreDoc sd : hits) {
					Document doc = indexSearcher.doc(sd.doc);
					float score = sd.score;
					double distance=0;
					VerPlaceBean bean = (VerPlaceBean) abstactDocument
							.parseDocument(doc);
					String aroundNum=bean.getAroundNum();	
					if(StringUtil.isNotEmptyString(aroundNum)){
						score=Float.parseFloat(aroundNum);
					}
					bean.setScore(score);
					verPlaceList.add(bean);
				}
			}

			FinalSearchComparator comparator = new FinalSearchComparator(1);
			Collections.sort(verPlaceList, comparator);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return verPlaceList;
	}

	@Override
	public List search(Query query, SearchVO sv, SORT... sorts) {
		return this.search(SearchConstants.MAX_SEARCH_RESULT_SIZE, query, sv,
				sorts);
	}

}
