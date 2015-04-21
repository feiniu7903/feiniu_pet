package com.lvmama.search.lucene.query;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

import com.lvmama.search.lucene.document.PlaceFromDestDocument;

/**
 * 自动补全查询条件
 * 
 * @author 张振华
 * 
 */
public class PlaceFromDestQuery {
	private Query placeNamePrefixQuery;
	private Query placePinyinPrefixQuery;
	private Query placeNameWildcardQuery;
	private Query placePinyinWildcardQuery;
	private Query fromDestIdQuery;
	private Query fromDestNameQuery;
	private Query stageQuery;
	private Query stageBooleanQuery;
	private Query isValidQuery;
	private Query destPeripheryNumQuery;
	private Query destAbroadNumQuery;
	private Query destInternalNumQuery;

	public Query getPlaceNamePrefixQuery(String placeName) {
		placeNamePrefixQuery = new PrefixQuery(new Term(PlaceFromDestDocument.PLACE_NAME, placeName));
		return placeNamePrefixQuery;
	}
	
	public Query getPlaceNameWildcardQuery(String placeName) {
		placeNameWildcardQuery = new WildcardQuery(new Term(PlaceFromDestDocument.PLACE_NAME, "*" + placeName + "*"));
		return placeNameWildcardQuery;
	}

	public Query getFromDestIdQuery(String fromDestId) {
		fromDestIdQuery = new TermQuery(new Term(PlaceFromDestDocument.FROM_DEST_ID, fromDestId));
		return fromDestIdQuery;
	}

	public Query getPlacePinyinWildcardQuery(String placePinyin) {
		placePinyinWildcardQuery = new WildcardQuery(new Term(PlaceFromDestDocument.PLACE_PINYIN, "*" + placePinyin + "*"));
		return placePinyinWildcardQuery;
	}


	public Query getPlacePinyinPrefixQuery(String placePinyin) {
		placePinyinPrefixQuery = new PrefixQuery(new Term(PlaceFromDestDocument.PLACE_PINYIN, placePinyin));
		return placePinyinPrefixQuery;
	}

	public Query getFromDestNameQuery(String fromDestName) {
		fromDestNameQuery = new TermQuery(new Term(PlaceFromDestDocument.FROM_DEST_NAME, fromDestName));
		return fromDestNameQuery;
	}

	public Query getStageQuery(String stage) {
		stageQuery = new TermQuery(new Term(PlaceFromDestDocument.STAGE, stage));
		return stageQuery;
	}

	public Query getStageBooleanQuery(String stage1,String stage2) {
		Query stageQuery1 = new TermQuery(new Term(PlaceFromDestDocument.STAGE, stage1));
		Query stageQuery2 = new TermQuery(new Term(PlaceFromDestDocument.STAGE, stage2));
		
		stageBooleanQuery = new BooleanQuery();
		((BooleanQuery) stageBooleanQuery).add(stageQuery1, BooleanClause.Occur.SHOULD); 
		((BooleanQuery) stageBooleanQuery).add(stageQuery2, BooleanClause.Occur.SHOULD);
		
		return stageBooleanQuery;
	}

	public Query getIsValidQuery(String isValid) {
		isValidQuery = new TermQuery(new Term(PlaceFromDestDocument.IS_VALID, isValid));
		return isValidQuery;
	}
	
	public Query getDestPeripheryNumQuery() {
		destPeripheryNumQuery = NumericRangeQuery.newIntRange(PlaceFromDestDocument.DEST_PERIPHERY_NUM, 1, Integer.MAX_VALUE, Boolean.TRUE, Boolean.TRUE);
		return destPeripheryNumQuery;
	}

	public Query getDestAbroadNumQuery() {
		destAbroadNumQuery = NumericRangeQuery.newIntRange(PlaceFromDestDocument.DEST_ABROAD_NUM, 1, Integer.MAX_VALUE, Boolean.TRUE, Boolean.TRUE);
		return destAbroadNumQuery;
	}

	public Query getDestInternalNumQuery() {
		destInternalNumQuery = NumericRangeQuery.newIntRange(PlaceFromDestDocument.DEST_INTERNAL_NUM, 1, Integer.MAX_VALUE, Boolean.TRUE, Boolean.TRUE);
		return destInternalNumQuery;
	}

}
