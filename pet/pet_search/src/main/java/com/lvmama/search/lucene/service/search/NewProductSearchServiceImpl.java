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
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.search.bigData.HbaseData;
import com.lvmama.search.lucene.LuceneContext;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.ikSimilarity.IKSimilarity;
import com.lvmama.search.lucene.score.FinalSearchComparator;
import com.lvmama.search.lucene.score.LuceneCustomScoreQuery;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LuceneCommonDic;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 产品搜索Service
 * @author yanggan
 *
 */
@Service("newProductSearchService")
public class NewProductSearchServiceImpl implements NewBaseSearchService{
	

	@Override
	public List<ProductBean> search(Query query, SORT... sorts) {
		return this.search(SearchConstants.MAX_SEARCH_RESULT_SIZE, query, sorts);
	}

	@Override
	public List<ProductBean> search(int max_search_size, Query query, SORT... sorts) {
		List<ProductBean> productList = new ArrayList<ProductBean>();
		IndexSearcher indexSearcher = LuceneContext.getProductSearcher();
		indexSearcher.setSimilarity(new IKSimilarity());
		try {
			Sort sort =  CommonUtil.initSortField(sorts);
			TopDocs topDocs = indexSearcher.search(query, null, max_search_size, sort);
			ScoreDoc[] hits = topDocs.scoreDocs;
			AbstactDocument abstactDocument = new ProductDocument();
			if (topDocs.totalHits > 0) {
				for (ScoreDoc sd : hits) {
					Document doc = indexSearcher.doc(sd.doc);

					if (StringUtils.isNotBlank(doc.get(ProductDocument.SELL_PRICE))&&doc.get(ProductDocument.SELL_PRICE).matches("[1-9][0-9]*\\.[0-9]*|[1-9][0-9]*")) {
						double price =Double.parseDouble(doc.get(ProductDocument.SELL_PRICE));
//						if(price==0){
//							continue;
//						}
					}

					productList.add((ProductBean) abstactDocument.parseDocument(doc));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}
	@Override
	public PageConfig<ProductBean> search(int pageSize, int currentPage, Query query, SORT... sorts) {
		List<ProductBean> productList = this.search(query, sorts);
		PageConfig<ProductBean> pageConfig = PageConfig.page(productList.size(), pageSize, currentPage);
		pageConfig.setAllItems(productList);
		return pageConfig;
	}


	@Override
	public List search(int max_search_size, Query query, SearchVO sv,
			SORT... sorts) {
		List<ProductBean> productList = new ArrayList<ProductBean>();
		List<String> productids = new ArrayList<String>();
		IndexSearcher indexSearcher = LuceneContext.getProductSearcher();
		LuceneCustomScoreQuery customerQuery = new LuceneCustomScoreQuery(query);
		indexSearcher.setSimilarity(new IKSimilarity());
		try {
			 Sort sort = CommonUtil.initSortField(sorts);
			 TopDocs topDocs = CommonUtil.toSearchIsHasSort(max_search_size, indexSearcher,
						customerQuery, sort, sorts);
			ScoreDoc[] hits = topDocs.scoreDocs;
			AbstactDocument abstactDocument = new ProductDocument();
			//获取所有的placeid
			for (ScoreDoc sd : hits) {
				Document doc = indexSearcher.doc(sd.doc);
				String productid = doc.get(ProductDocument.PRODUCT_ID);
				productids.add(productid);
			}
			// 获取搜索字，默认为上海
			String keyword = LuceneCommonDic.defaultWord;
			if (StringUtils.isNotEmpty(sv.getKeyword())) {
				keyword = sv.getKeyword();
			}
			//调用hbase接口来获取数据
			// 返回key是placeid value 是分数
			Map<String, String> returnMap = CommonUtil.getHbaseData(productids, keyword);
			String printcontent="";
			if (topDocs.totalHits > 0) {
				for (ScoreDoc sd : hits) {
					Document doc = indexSearcher.doc(sd.doc);
					if (StringUtils.isNotBlank(doc.get(ProductDocument.SELL_PRICE))&&doc.get(ProductDocument.SELL_PRICE).matches("[0-9][0-9]*\\.[0-9]*|[0-9][0-9]*")) {
						double price =Double.parseDouble(doc.get(ProductDocument.SELL_PRICE));
//						if(price==0){
//							continue;
//						}
					}
					String tmpthing="keyword:"+sv.getKeyword()+"subtype:"+doc.get(ProductDocument.SUB_PRODUCT_TYPE)+"--"+"id"+doc.get(ProductDocument.PRODUCT_ID)+"--"+"id"+doc.get(ProductDocument.PRODUCT_NAME)+"--"+"allscore"+sd.score+"--"+doc.get("aaaaa");
					printcontent=printcontent+"\n"+tmpthing;
					ProductBean bean = (ProductBean) abstactDocument
							.parseDocument(doc);
					// 获得lucene score
					float score = sd.score;
					if("SELFHELP_BUS".equals(doc.get(ProductDocument.SUB_PRODUCT_TYPE))){
						score=100+sd.score;
					}
					if(doc.get(ProductDocument.TAGS_NAME_ORI)!=null){
						if(doc.get(ProductDocument.TAGS_NAME_ORI).indexOf("特推")!=-1){
							score=score+200+sd.score;
						}
					}
					String productid = doc.get(ProductDocument.PRODUCT_ID);
					String tmphbaseScore = null;
					if (returnMap != null) {
						tmphbaseScore = returnMap.get(productid);
					}
					float hbaseScore = 0f;
					if (StringUtils.isNotEmpty(tmphbaseScore)) {
					// 获得hbase score
						hbaseScore = Float.parseFloat(tmphbaseScore);
					}
					
					// 求总分，这里的0.1默认设置
					score = score + 0.1f * hbaseScore;
					String tmpnormalscore=doc.get(ProductDocument.NORMALSCORE);
					float normalscore=0f;
					if(tmpnormalscore!=null){
						normalscore=Float.parseFloat(tmpnormalscore);
					}
					bean.setHbasescore(hbaseScore);
					bean.setNormalscore(normalscore);
					bean.setScore(score);
					productList.add(bean);
				}
			}
			//对总分进行排序
			FinalSearchComparator comparator=new FinalSearchComparator();
			Collections.sort(productList,comparator);

//			CommonUtil.printTest(printcontent);


		} catch (IOException e) {
			e.printStackTrace();
		}
		return productList;
	}
	
	

	@Override
	public List search(Query query, SearchVO sv, SORT... sorts) {
		return this.search(SearchConstants.MAX_SEARCH_RESULT_SIZE, query, sv,sorts);
	}

	@Override
	public PageConfig search(Integer pageSize, Integer page, Query query_2,
			SearchVO sv, SORT[] sorts) {
		List<ProductBean> productList = this.search(query_2,sv, sorts);
		PageConfig<ProductBean> pageConfig = PageConfig.page(productList.size(), pageSize, page);
		pageConfig.setAllItems(productList);
		return pageConfig;
	}

}
