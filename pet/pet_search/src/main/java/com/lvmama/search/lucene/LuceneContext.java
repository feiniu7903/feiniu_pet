package com.lvmama.search.lucene;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager.TrackingIndexWriter;
import org.apache.lucene.util.Version;

import com.lvmama.comm.search.SearchConstants.LUCENE_INDEX_TYPE;
import com.lvmama.search.lucene.analyser.AnalyzerUtil;
import com.lvmama.search.lucene.search.Searcher;
import com.lvmama.search.util.ConfigHelper;

/**
 * Lucene全局上下文
 * 
 * 包含每个索引的IndexSearcher,IndexReader,IndexWriter的单实例对象
 * 
 * @author yanggan
 * 
 */
public class LuceneContext {
	private static Log log = LogFactory.getLog(LuceneContext.class);
	
	public static final String INDEX_PLACE_PATH = ConfigHelper.getString("INDEX_PATH");
	public static final String INDEX_PLACE_HOTEL_PATH = ConfigHelper.getString("INDEX_PLACE_HOTEL_PATH");
	public static final String INDEX_VER_HOTEL_PATH = ConfigHelper.getString("INDEX_VER_HOTEL_PATH");
	public static final String INDEX_VER_PLACE_PATH = ConfigHelper.getString("INDEX_VER_PLACE_PATH");
	public static final String INDEX_PRODUCT_PATH = ConfigHelper.getString("INDEX_PRODUCT_PATH");
	public static final String INDEX_PRODUCT_BRANCH_PATH = ConfigHelper.getString("INDEX_PRODUCT_BRANCH_PATH");
	/**
	 * Lucene的当前版本
	 */
	public static final Version LUCENE_VERSION = Version.LUCENE_43;

	private static Map<String, Searcher> indexSearcherMap = new HashMap<String, Searcher>();
	
	public static void init(){
		/** 景区索引器 */
		indexSearcherMap.put(LUCENE_INDEX_TYPE.PLACE.toString(), new Searcher(INDEX_PLACE_PATH, AnalyzerUtil.getAnalyzer(AnalyzerUtil.PLACE_INDEX_ANALYZER)));
		/** 酒店索引器 */
		indexSearcherMap.put(LUCENE_INDEX_TYPE.PLACE_HOTEL.toString(), new Searcher(INDEX_PLACE_HOTEL_PATH, AnalyzerUtil.getAnalyzer(AnalyzerUtil.PLACE_INDEX_ANALYZER)));
		/** ver酒店索引器 */
		indexSearcherMap.put(LUCENE_INDEX_TYPE.VER_HOTEL.toString(), new Searcher(INDEX_VER_HOTEL_PATH, AnalyzerUtil.getAnalyzer(AnalyzerUtil.PLACE_INDEX_ANALYZER)));
		/** verplace索引器 */
		indexSearcherMap.put(LUCENE_INDEX_TYPE.VER_PLACE.toString(), new Searcher(INDEX_VER_PLACE_PATH, AnalyzerUtil.getAnalyzer(AnalyzerUtil.PLACE_INDEX_ANALYZER)));
		/** 产品索引器 */
		indexSearcherMap.put(LUCENE_INDEX_TYPE.PRODUCT.toString(), new Searcher(INDEX_PRODUCT_PATH, AnalyzerUtil.getAnalyzer(AnalyzerUtil.PRODUCT_INDEX_ANALYZER)));
		/** 产品下类别产品索引器 */
		indexSearcherMap.put(LUCENE_INDEX_TYPE.PRODUCT_BRANCH.toString(), new Searcher(INDEX_PRODUCT_BRANCH_PATH, AnalyzerUtil.getAnalyzer(AnalyzerUtil.PRODUCT_INDEX_ANALYZER)));
	}

	/**
	 * 获取景区的InxdexSearcher
	 * 
	 * @return IndexSearcher
	 */
	public static IndexSearcher getPlaceSearcher() {
		return LuceneContext.getIndexSearcher(LUCENE_INDEX_TYPE.PLACE);
	}

	/**
	 * 获取酒店的InxdexSearcher
	 * 
	 * @return IndexSearcher
	 */
	public static IndexSearcher getPlaceHotelSearcher() {
		return LuceneContext.getIndexSearcher(LUCENE_INDEX_TYPE.PLACE_HOTEL);
	}
	
	/**
	 * 获取ver酒店的InxdexSearcher
	 * 
	 * @return IndexSearcher
	 */
	public static IndexSearcher getVerHotelSearcher() {
		return LuceneContext.getIndexSearcher(LUCENE_INDEX_TYPE.VER_HOTEL);
	}
	
	/**
	 * 获取verPlace的InxdexSearcher
	 * 
	 * @return IndexSearcher
	 */
	public static IndexSearcher getVerHotelPlaceSearcher() {
		return LuceneContext.getIndexSearcher(LUCENE_INDEX_TYPE.VER_PLACE);
	}
	
	/**
	 * 获取销售产品的IndexSearcher
	 * 
	 * @return IndexSearcher
	 */
	public static IndexSearcher getProductSearcher() {
		return LuceneContext.getIndexSearcher(LUCENE_INDEX_TYPE.PRODUCT);
	}

	/**
	 * 获取销售产品类型的IndexSearcher
	 * 
	 * @return IndexSearcher
	 */
	public static IndexSearcher getProductBranchSearcher() {
		return LuceneContext.getIndexSearcher(LUCENE_INDEX_TYPE.PRODUCT_BRANCH);
	}

	/**
	 * 根据类型查找索引器
	 * 
	 * @param searcherType
	 *            索引类型
	 * @return IndexSearcher
	 */
	public static IndexSearcher getIndexSearcher(LUCENE_INDEX_TYPE indexType) {
		Searcher searcher = indexSearcherMap.get(indexType.toString());
		return searcher.openSearcher();
	}

	/**
	 * 获取IndexWriter
	 * 
	 * @param indexType
	 *            索引类型
	 * @return IndexWriter
	 */
	public static TrackingIndexWriter getIndexWriter(LUCENE_INDEX_TYPE indexType) {
		Searcher searcher = indexSearcherMap.get(indexType.toString());
		return searcher.getWriter();
	}
	
	public static void replaceSearcher(LUCENE_INDEX_TYPE indexType,Searcher searcher){
		Searcher searcher_old = indexSearcherMap.get(indexType.toString());
		indexSearcherMap.put(indexType.toString(), searcher);
		searcher_old.destroy();
	}
	
}
