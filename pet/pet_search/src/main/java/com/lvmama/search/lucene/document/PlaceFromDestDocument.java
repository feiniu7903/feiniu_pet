package com.lvmama.search.lucene.document;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;

import com.lvmama.comm.search.vo.PlaceFromDestBean;
import com.lvmama.search.util.LuceneCommonDic;



/**
 * documet实现类
 * 
 * @author yuzhibing
 * 
 */
public class PlaceFromDestDocument extends AbstactDocument {
	public static String ID = "id";
	public static String SEQ = "seq";
	public static String PLACE_ID = "placeId";
	public static String PLACE_NAME = "placeName";
	public static String PLACE_PINYIN = "placePinyin";
	public static String FROM_DEST_ID = "fromDestId";
	public static String FROM_DEST_NAME = "fromDestName";
	public static String STAGE = "stage";
	public static String IS_VALID = "isValid";
	public static String DEST_PERIPHERY_NUM = "destPeripheryNum";
	public static String DEST_ABROAD_NUM = "destAbroadNum";
	public static String DEST_INTERNAL_NUM = "destInternalNum";
	public static String OWNFIELD = "ownfield";
	
	public PlaceFromDestDocument() {

	}

	public Document createDocument(Object t) {
		PlaceFromDestBean placeFromDestBean = (PlaceFromDestBean) t;
		Document doc = new Document();
		doc.add(new Field(PlaceFromDestDocument.ID, placeFromDestBean.getId(), Field.Store.YES, Field.Index.NO));
		doc.add(new Field(PlaceFromDestDocument.SEQ, placeFromDestBean.getPlaceId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(PlaceFromDestDocument.PLACE_ID, placeFromDestBean.getPlaceId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(PlaceFromDestDocument.PLACE_NAME, placeFromDestBean.getPlaceName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		//加入默认字段
		doc.add(new TextField(PlaceFromDestDocument.OWNFIELD,LuceneCommonDic.OWNFIELD,Field.Store.YES));
		doc.add(new Field(PlaceFromDestDocument.PLACE_PINYIN, placeFromDestBean.getPlacePinyin(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(PlaceFromDestDocument.FROM_DEST_ID, placeFromDestBean.getFromDestId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(PlaceFromDestDocument.FROM_DEST_NAME, placeFromDestBean.getFromDestName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(PlaceFromDestDocument.STAGE, placeFromDestBean.getStage(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(PlaceFromDestDocument.IS_VALID, placeFromDestBean.getIsValid(), Field.Store.YES, Field.Index.NOT_ANALYZED));

		doc.add(new IntField(PlaceDocument.DEST_PERIPHERY_NUM,Integer.valueOf(placeFromDestBean.getDestPeripheryNum()), Field.Store.YES));

		doc.add(new IntField(PlaceDocument.DEST_ABROAD_NUM,Integer.valueOf(placeFromDestBean.getDestAbroadNum()), Field.Store.YES));

		doc.add(new IntField(PlaceDocument.DEST_INTERNAL_NUM,Integer.valueOf(placeFromDestBean.getDestInternalNum()), Field.Store.YES));
		
		return doc;
	}

	public Object parseDocument(Document doc) {
		PlaceFromDestBean result = new PlaceFromDestBean();
		result.setId(doc.get(PlaceFromDestDocument.ID));
		result.setSeq(doc.get(PlaceFromDestDocument.SEQ));
		result.setPlaceId(doc.get(PlaceFromDestDocument.PLACE_ID));
		result.setPlaceName(doc.get(PlaceFromDestDocument.PLACE_NAME));
		result.setPlacePinyin(doc.get(PlaceFromDestDocument.PLACE_PINYIN));
		result.setFromDestId(doc.get(PlaceFromDestDocument.FROM_DEST_ID));
		result.setFromDestName(doc.get(PlaceFromDestDocument.FROM_DEST_NAME));
		result.setStage(doc.get(PlaceFromDestDocument.STAGE));
		result.setIsValid(doc.get(PlaceFromDestDocument.IS_VALID));
		result.setDestPeripheryNum(doc.get(PlaceFromDestDocument.DEST_PERIPHERY_NUM));
		result.setDestAbroadNum(doc.get(PlaceFromDestDocument.DEST_ABROAD_NUM));
		result.setDestInternalNum(doc.get(PlaceFromDestDocument.DEST_INTERNAL_NUM));
		return result;
	}
}
