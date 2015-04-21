package com.lvmama.search.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

import com.lvmama.comm.search.SearchConstants.LUCENE_INDEX_TYPE;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.PlaceFromDestBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.ProductBranchBean;
import com.lvmama.search.lucene.LuceneContext;
import com.lvmama.search.lucene.document.AbstactDocument;
import com.lvmama.search.lucene.document.DestinactionDocument;
import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.PlaceFromDestDocument;
import com.lvmama.search.lucene.document.ProductBranchDocument;
import com.lvmama.search.lucene.document.ProductDocument;
/**
 * 处理结果集
 * @author yuzhibing
 *
 */
public class ResutlParser {
	public static PageConfig resutlParser(int pageSize, int currentPage, IndexSearcher indexSearcher, ScoreDoc[] hits, int resutNum, LUCENE_INDEX_TYPE indexType) throws IOException {
		if (LUCENE_INDEX_TYPE.PLACE.equals(indexType)) {
			return resutlParserPlace(pageSize, currentPage, indexSearcher, hits, resutNum);
		} else if (LUCENE_INDEX_TYPE.PRODUCT.equals(indexType)) {
			return resutlParserProduct(pageSize, currentPage, indexSearcher, hits, resutNum);
		} else if (LUCENE_INDEX_TYPE.PRODUCT_BRANCH.equals(indexType)) {
			return resutlParserProductBranch(pageSize, currentPage, indexSearcher, hits, resutNum);
		} 
		return null;
	}

	/**
	 * 自动补全分页
	 * @param indexSearcher
	 * @param hits
	 * @return
	 * @throws IOException
	 */
	private static PageConfig resutlParserAutoComplete(int pageSize, int currentPage, IndexSearcher indexSearcher, ScoreDoc[] hits, int resutNum) throws IOException {
		List<PlaceFromDestBean> resutlList = new ArrayList<PlaceFromDestBean>();
		PageConfig pageConfig = PageConfig.page(resutNum, pageSize, currentPage);
		AbstactDocument abstactDocument = new PlaceFromDestDocument();
		if (resutNum > 0) {
			for (int i = pageConfig.getStartResult(); i < pageConfig.getCurrentRowNum(); i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				resutlList.add((PlaceFromDestBean) abstactDocument.parseDocument(doc));
			}
		}
		pageConfig.setItems(resutlList);
		return pageConfig;
	}

	/**
	 * 产品分页
	 * @param indexSearcher
	 * @param hits
	 * @return
	 * @throws IOException
	 */
	private static PageConfig resutlParserProduct(int pageSize, int currentPage, IndexSearcher indexSearcher, ScoreDoc[] hits, int resutNum) throws IOException {
		List<ProductBean> placeList = new ArrayList<ProductBean>();
		PageConfig pageConfig = PageConfig.page(resutNum, pageSize, currentPage);
		AbstactDocument abstactDocument = new ProductDocument();
		if (resutNum > 0) {
			for (int i = pageConfig.getStartResult(); i < pageConfig.getCurrentRowNum(); i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				placeList.add((ProductBean) abstactDocument.parseDocument(doc));
			}
		}
		pageConfig.setItems(placeList);
		return pageConfig;
	}
	
	/**
	 * 产品类别分页
	 * @param indexSearcher
	 * @param hits
	 * @return
	 * @throws IOException
	 */
	private static PageConfig resutlParserProductBranch(int pageSize, int currentPage, IndexSearcher indexSearcher, ScoreDoc[] hits, int resutNum) throws IOException {
		List<ProductBranchBean> placeList = new ArrayList<ProductBranchBean>();
		PageConfig pageConfig = PageConfig.page(resutNum, pageSize, currentPage);
		AbstactDocument abstactDocument = new ProductBranchDocument();
		if (resutNum > 0) {
			for (int i = pageConfig.getStartResult(); i < pageConfig.getCurrentRowNum(); i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				placeList.add((ProductBranchBean) abstactDocument.parseDocument(doc));
			}
		}
		pageConfig.setItems(placeList);
		return pageConfig;
	}


	/**
	 * 景区结果分页
	 * @param indexSearcher
	 * @param hits
	 * @return
	 * @throws IOException
	 */
	private static PageConfig resutlParserPlace(int pageSize, int currentPage, IndexSearcher indexSearcher, ScoreDoc[] hits, int resutNum) throws IOException {
		List<PlaceBean> placeList = new ArrayList<PlaceBean>();
		PageConfig pageConfig = PageConfig.page(resutNum, pageSize, currentPage);
		AbstactDocument abstactDocument = new PlaceDocument();
		if (resutNum > 0) {
			for (int i = pageConfig.getStartResult(); i < pageConfig.getCurrentRowNum(); i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				placeList.add((PlaceBean)abstactDocument.parseDocument(doc));
			}
		}
		pageConfig.setItems(placeList);
		return pageConfig;
	}
	/**
	 * 目的地结果分页
	 * @param indexSearcher
	 * @param hits
	 * @return
	 * @throws IOException
	 */
	private static PageConfig resutlParserDestinaction(int pageSize, int currentPage, IndexSearcher indexSearcher, ScoreDoc[] hits, int resutNum) throws IOException {
		List<PlaceBean> placeList = new ArrayList<PlaceBean>();
		PageConfig pageConfig = PageConfig.page(resutNum, pageSize, currentPage);
		AbstactDocument abstactDocument = new DestinactionDocument();
		if (resutNum > 0) {
			for (int i = pageConfig.getStartResult(); i < pageConfig.getCurrentRowNum(); i++) {
				Document doc = indexSearcher.doc(hits[i].doc);
				placeList.add((PlaceBean)abstactDocument.parseDocument(doc));
			}
		}
		pageConfig.setItems(placeList);
		return pageConfig;
	}
}
