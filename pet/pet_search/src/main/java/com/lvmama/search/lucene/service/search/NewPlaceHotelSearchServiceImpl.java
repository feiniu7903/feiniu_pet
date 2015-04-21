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
import com.lvmama.search.lucene.LuceneContext;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.PlaceHotelDocument;
import com.lvmama.search.lucene.document.ProductDocument;
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
@Service("newPlaceHotelSearchService")
public class NewPlaceHotelSearchServiceImpl  implements NewBaseSearchService{

	@Override
	public List<PlaceHotelBean>search(Query query, SORT... sorts) {
		return this.search(SearchConstants.MAX_SEARCH_RESULT_SIZE, query, sorts);
	}

	@Override
	public List<PlaceHotelBean> search(int max_search_size, Query query, SORT... sorts) {
		List<PlaceHotelBean> placeList = new ArrayList<PlaceHotelBean>();
		IndexSearcher indexSearcher = LuceneContext.getPlaceHotelSearcher();
		try {
			Sort sort = CommonUtil.initSortField(sorts);
			TopDocs topDocs = indexSearcher.search(query, null, max_search_size,sort);
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
			//对总分进行排序
		} catch (IOException e) {
			e.printStackTrace();
		}
		return placeList;
	}

	@Override
	public PageConfig<PlaceHotelBean> search(int pageSize, int currentPage, Query query, SORT... sorts) {
		List<PlaceHotelBean> productList = this.search(query, sorts);
		PageConfig<PlaceHotelBean> pageConfig = PageConfig.page(productList.size(), pageSize, currentPage);
		pageConfig.setAllItems(productList);
		return pageConfig;
	}

	@Override
	public PageConfig search(Integer pageSize, Integer page, Query query_2,
			SearchVO sv, SORT[] sorts) {
		List<PlaceHotelBean> productList = this.search(query_2, sv,sorts);
		PageConfig<PlaceHotelBean> pageConfig = PageConfig.page(productList.size(), pageSize, page);
		pageConfig.setAllItems(productList);
		return pageConfig;
	}

	@Override
	public List search(int max_search_size, Query query, SearchVO sv,
			SORT... sorts) {
		List<PlaceHotelBean> placeList = new ArrayList<PlaceHotelBean>();
		List<String> placeidList = new ArrayList<String>();
		IndexSearcher indexSearcher = LuceneContext.getPlaceHotelSearcher();
		LuceneCustomScoreQuery customerQuery = new LuceneCustomScoreQuery(query);
		indexSearcher.setSimilarity(new IKSimilarity());
		String printcontent="";
		try {
			 Sort sort = CommonUtil.initSortField(sorts);
			 TopDocs topDocs = CommonUtil.toSearchIsHasSort(max_search_size, indexSearcher,
					customerQuery, sort, sorts);
			 ScoreDoc[] hits = topDocs.scoreDocs;
			AbstactDocument abstactDocument = new PlaceHotelDocument();
			//获取所有的placeid
			for (ScoreDoc sd : hits) {
				Document doc = indexSearcher.doc(sd.doc);
				String placeid = doc.get(PlaceDocument.ID);
				placeidList.add(placeid);
			}
			// 获取搜索字，默认为上海
			String keyword = LuceneCommonDic.defaultWord;
			if (StringUtils.isNotEmpty(sv.getKeyword())) {
				keyword = sv.getKeyword();
			}
			//调用hbase接口来获取数据
			// 返回key是placeid value 是分数
			Map<String, String> returnMap = CommonUtil.getHbaseData(placeidList, keyword);
			if (topDocs.totalHits > 0) {
				for (ScoreDoc sd : hits) {
					Document doc = indexSearcher.doc(sd.doc);
					if (StringUtils.isNotBlank(doc.get(PlaceDocument.PRODUCTS_PRICE))&&doc.get(PlaceDocument.PRODUCTS_PRICE).matches("[0-9][0-9]*\\.[0-9]*|[0-9][0-9]*")) {
						double price =Double.parseDouble(doc.get(PlaceDocument.PRODUCTS_PRICE));
						if(price==0){
							continue;
						}
					}
					String tmpthing="keyword:"+sv.getKeyword()+"subtype:"+doc.get(PlaceDocument.STAGE)+"--"+"id"+doc.get(PlaceDocument.ID)+"--"+"name"+doc.get(PlaceDocument.NAME)+"--"+"allscore"+sd.score+"--"+doc.get("aaaaa");
					printcontent=printcontent+"\n"+tmpthing;
					PlaceHotelBean bean = (PlaceHotelBean) abstactDocument
							.parseDocument(doc);
					// 获得lucene score
					float score = sd.score;
					if(doc.get(ProductDocument.TAGS_NAME_ORI)!=null){
						if(doc.get(ProductDocument.TAGS_NAME_ORI).indexOf("特推")!=-1){
							score=200+sd.score;
						}
					}
					String placeid = doc.get(PlaceDocument.ID);
					String tmphbaseScore = null;
					if (returnMap != null) {
						tmphbaseScore = returnMap.get(placeid);
					}
					float hbaseScore = 0f;
					if (StringUtils.isNotEmpty(tmphbaseScore)) {
					// 获得hbase score
						hbaseScore = Float.parseFloat(tmphbaseScore);
					}
					// 求总分，这里的0.1默认设置
					score = score + 0.1f * hbaseScore;

					String tmpnormalscore=doc.get(PlaceDocument.NORMALSCORE);
					float normalscore=0f;
					if(tmpnormalscore!=null){
						normalscore=Float.parseFloat(tmpnormalscore);
					}
					bean.setHbasescore(hbaseScore);
					bean.setNormalscore(normalscore);
					
					bean.setScore(score);
					placeList.add(bean);
				}
			}
			//对总分进行排序
			FinalSearchComparator comparator=new FinalSearchComparator();
			Collections.sort(placeList,comparator);

//			CommonUtil.printTest(printcontent);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return placeList;
	}

	@Override
	public List search(Query query, SearchVO sv, SORT... sorts) {
		return this.search(SearchConstants.MAX_SEARCH_RESULT_SIZE,query,sv, sorts);
	}

}
