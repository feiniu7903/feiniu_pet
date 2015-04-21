package com.lvmama.search.util;

import org.apache.lucene.search.SortField;

import com.lvmama.search.lucene.document.PlaceDocument;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.document.VerhotelDocument;

public enum SORT {
	seq("seq", 1, ProductDocument.SEQ, SortField.Type.LONG, false),
	priceDown("sortPrice",2,ProductDocument.SELL_PRICE, SortField.Type.FLOAT,true),//价格降序
	priceUp("sortPrice",3,ProductDocument.SELL_PRICE, SortField.Type.FLOAT,false),//价格升序
	avgScoreDown("avgScore",4,PlaceDocument.AVG_SCORE, SortField.Type.FLOAT,true),//点评分数降序
	avgScoreUp("avgScore",5,PlaceDocument.AVG_SCORE, SortField.Type.FLOAT,false),//点评分数升序    应该没有
	cmtNumDown("cmtNum",6,PlaceDocument.CMT_NUM, SortField.Type.INT,true),//点评数升序 
	cmtNumUp("cmtNum",7,PlaceDocument.CMT_NUM, SortField.Type.INT,false),//点评数降序    应该没有
	subProductTypeDown("subProductType",8,ProductDocument.SUB_PRODUCT_TYPE, SortField.Type.STRING,true),//产品子类型降序
	sales("sales",9,ProductDocument.WEEK_SALES, SortField.Type.INT,true),//一周产品销量
	verPriceDown("sortPrice",13,VerhotelDocument.MINPRODUCTSPRICE, SortField.Type.FLOAT,true),//价格降序
	verPriceUp("sortPrice",14,VerhotelDocument.MINPRODUCTSPRICE, SortField.Type.FLOAT,false),//价格升序
	distance("distance",10,"distance", SortField.Type.DOUBLE,true);//距离排序
	
	private String type;
	private Integer val;
	private String fieldName;
	private SortField.Type fieldType;
	
	/**true:降序   false：升序*/
	private boolean reverse;
	SORT(String type,Integer val,String fieldName,SortField.Type fieldType,boolean reverse){
		this.type=type;
		this.val =val;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.reverse = reverse;
	}
	public SortField getSortField(){
		return new SortField(this.fieldName, this.fieldType, this.reverse);
	}
	public String getCode(){
		return this.name();
	}
	public String getType(){
		return this.type;
	}
	public Integer getVal(){
		return this.val;
	}
	public String getFieldName() {
		return fieldName;
	}
	public SortField.Type getFieldType() {
		return fieldType;
	}
	public boolean isReverse() {
		return reverse;
	}
	public static SORT getSort(int val){
		for(SORT item:SORT.values()){
			if(item.getVal() == val)
			{
				return item;
			}
		}
		return SORT.seq;
	}
	public String toString(){
		return this.name();
	}
}
