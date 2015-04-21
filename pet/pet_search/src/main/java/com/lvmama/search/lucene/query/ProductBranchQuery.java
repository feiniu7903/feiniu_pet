package com.lvmama.search.lucene.query;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

import com.lvmama.search.lucene.document.ProductBranchDocument;

/**
 * 产品查询条件
 * 
 * @author huangzhi
 * 
 */
public class ProductBranchQuery {
	private Query productAllPlaceIdsQuery;
	private Query additionalQuery;
	private Query onLineQuery;
	private Query validQuery;
	private Query visibleQuery;
	private Query defaultBranchQuery;
	private Query channelQuery;
	private Query sellPriceQuery;
	private Query subProductTypeQuery;	
	
	public Query getproductAllPlaceIdsQuery(String placeId) throws IOException {
		productAllPlaceIdsQuery = new PrefixQuery(new Term(ProductBranchDocument.PRODUCT_ALL_PLACE_IDS, placeId));
		return this.productAllPlaceIdsQuery;
	}
	
	public Query getAdditionalQuery(String isAdditional) {
		additionalQuery = new TermQuery(new Term(ProductBranchDocument.ADDITIONAL, isAdditional));
		return additionalQuery;
	}
	
	public Query getOnLineQuery(String isOnLine) {
		onLineQuery = new TermQuery(new Term(ProductBranchDocument.ON_LINE, isOnLine));
		return onLineQuery;
	}
	
	public Query getValidQuery(String isValid) {
		validQuery = new TermQuery(new Term(ProductBranchDocument.VALID, isValid));
		return validQuery;
	}
	
	public Query getVisibleQuery(String isVisible) {
		visibleQuery = new TermQuery(new Term(ProductBranchDocument.VISIBLE, isVisible));
		return visibleQuery;
	}
	
	public Query getDefaultBranchQuery(String isDefaultBranch) {
		defaultBranchQuery = new TermQuery(new Term(ProductBranchDocument.DEFAULT_BRANCH, isDefaultBranch));
		return defaultBranchQuery;
	}
	
	public Query getChannelQuery(String channel) {
		Term term = new Term(ProductBranchDocument.CHANNEL, "*" + channel + "*");
		this.channelQuery = new WildcardQuery(term);
		return channelQuery;
	}
	
	public Query getSellPriceQuery(String sellPrice) {
		String[] sellPriceSpit = sellPrice.split("_");
		sellPriceQuery = NumericRangeQuery.newFloatRange(ProductBranchDocument.SELL_PRICE, Float.parseFloat(sellPriceSpit[0]), Float.parseFloat(sellPriceSpit[1]), true, true);
		return sellPriceQuery;
	}
	
	public Query getSubProductTypeQuery(String subProductType) {
		subProductTypeQuery = new TermQuery(new Term(ProductBranchDocument.SUB_PRODUCT_TYPE, subProductType));
		return subProductTypeQuery;
	}
	
}
