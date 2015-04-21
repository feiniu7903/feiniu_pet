package com.lvmama.search.lucene.service.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Service;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBranchBean;
import com.lvmama.comm.search.vo.SearchVO;
import com.lvmama.search.lucene.LuceneContext;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.ProductBranchDocument;
import com.lvmama.search.lucene.ikSimilarity.IKSimilarity;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

/**
 * 产品下类别产品搜索
 * 
 * @author yanggan
 * 
 */
@Service("newProductBranchSearchService")
public class NewProductBranchSearchServiceImpl implements NewBaseSearchService{

	@Override
	public List<ProductBranchBean> search(Query query, SORT... sorts) {
		return this.search(SearchConstants.MAX_SEARCH_RESULT_SIZE, query, sorts);
	}

	@Override
	public List<ProductBranchBean> search(int max_search_size, Query query, SORT... sorts) {
		List<ProductBranchBean> productBranchList = new ArrayList<ProductBranchBean>();
		IndexSearcher indexSearcher = LuceneContext.getProductBranchSearcher();
		indexSearcher.setSimilarity(new IKSimilarity());
		try {
			Sort sort = CommonUtil.initSortField(sorts);
			TopDocs topDocs = indexSearcher.search(query, null, max_search_size, sort);
			ScoreDoc[] hits = topDocs.scoreDocs;
			
			AbstactDocument abstactDocument = new ProductBranchDocument();
			if (topDocs.totalHits > 0) {
				for (ScoreDoc sd : hits) {
					Document doc = indexSearcher.doc(sd.doc);
					productBranchList.add((ProductBranchBean) abstactDocument.parseDocument(doc));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return productBranchList;
	}
	@Override
	public PageConfig<ProductBranchBean> search(int pageSize, int currentPage, Query query, SORT... sorts) {
		List<ProductBranchBean> productList = this.search(query, sorts);
		PageConfig<ProductBranchBean> pageConfig = PageConfig.page(productList.size(), pageSize, currentPage);
		pageConfig.setAllItems(productList);
		return pageConfig;
	}

	@Override
	public PageConfig<PlaceBean> search(Integer pageSize, Integer page,
			Query query_2, SearchVO sv, SORT[] sorts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(int max_search_size, Query query, SearchVO sv,
			SORT... sorts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List search(Query query, SearchVO sv, SORT... sorts) {
		// TODO Auto-generated method stub
		return null;
	}

}
