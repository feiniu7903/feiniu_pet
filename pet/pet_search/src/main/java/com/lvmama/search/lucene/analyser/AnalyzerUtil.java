package com.lvmama.search.lucene.analyser;

import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.lvmama.comm.search.SearchConstants.LUCENE_INDEX_TYPE;

/**
 * 实例化分词器
 * 
 * @author yuzhibing
 * @author qiuguobin
 */
public class AnalyzerUtil {
	public static final String PLACE_INDEX_ANALYZER = "placeIndexAnalyzer";
	public static final String PRODUCT_INDEX_ANALYZER = "productIndexAnalyzer";

	//private static Analyzer defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_43);
	private static Analyzer defaultAnalyzer = new IKAnalyzer(true);
	private static Analyzer analyzer=null;
	private static HashMap<String, PerFieldAnalyzerWrapper> analyzerMap = new HashMap<String, PerFieldAnalyzerWrapper>();

	public synchronized static PerFieldAnalyzerWrapper getAnalyzer(String key) {
		PerFieldAnalyzerWrapper analyzer = analyzerMap.get(key);
		if (analyzer == null) {
			analyzer = new PerFieldAnalyzerWrapper(defaultAnalyzer);
			analyzerMap.put(key, analyzer);
		}
		return analyzer;
	}
	
	public synchronized static PerFieldAnalyzerWrapper  getAnalyzer(LUCENE_INDEX_TYPE type){
		if(LUCENE_INDEX_TYPE.PLACE.toString().equals(type.toString()) || LUCENE_INDEX_TYPE.PLACE_HOTEL.toString().equals(type.toString())){
			return getAnalyzer(AnalyzerUtil.PLACE_INDEX_ANALYZER);
		}else if( LUCENE_INDEX_TYPE.PRODUCT.toString().equals(type.toString()) || LUCENE_INDEX_TYPE.PRODUCT_BRANCH.toString().equals(type.toString())){
			return getAnalyzer(AnalyzerUtil.PRODUCT_INDEX_ANALYZER);
		}else if( LUCENE_INDEX_TYPE.VER_HOTEL.toString().equals(type.toString()) || LUCENE_INDEX_TYPE.VER_PLACE.toString().equals(type.toString())){
			return getAnalyzer(AnalyzerUtil.PRODUCT_INDEX_ANALYZER);
		}else{
			throw new RuntimeException("GetAnalyzer fail. TYPE IS :"+type);
		}
	}
	
	public synchronized static Analyzer  getIkAnalyzer(){
		if(analyzer==null){
			analyzer=new IKAnalyzer(true);
		}
		return analyzer;
	}
}
