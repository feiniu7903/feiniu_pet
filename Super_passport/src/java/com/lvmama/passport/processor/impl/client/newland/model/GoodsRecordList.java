package com.lvmama.passport.processor.impl.client.newland.model;

import java.util.List;

public class GoodsRecordList {
     private  List<GoodsRecord> goodsRecords;

 	public String toGoodsRecordListXml() {
		StringBuilder buf = new StringBuilder();
		for(GoodsRecord goodsRecord:this.goodsRecords){
		 buf.append("<GoodsRecord>").append(goodsRecord.toVerifyResXml()).append("</GoodsRecord>");
		}
		return buf.toString();
	}
	public List<GoodsRecord> getGoodsRecords() {
		return goodsRecords;
	}

	public void setGoodsRecords(List<GoodsRecord> goodsRecords) {
		this.goodsRecords = goodsRecords;
	} 
}
