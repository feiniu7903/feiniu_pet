package com.lvmama.passport.processor.impl.client.newland.model;

import java.util.ArrayList;
import java.util.List;

public class PGoodsList {
	private String pgoods_id;
    private List<String> pgoodsId=new ArrayList<String>();
	public String toPGoodsListXml() {
		StringBuilder buf = new StringBuilder();
		buf.append("<PGoodsID>").append(this.pgoods_id).append("</PGoodsID>");
		return buf.toString();
	}
    
	public List<String> getPgoodsId() {
		return pgoodsId;
	}

	public void setPgoodsId(List<String> pgoodsId) {
		this.pgoodsId = pgoodsId;
	}

	public String getPgoods_id() {
		return pgoods_id;
	}

	public void setPgoods_id(String pgoods_id) {
		this.pgoods_id = pgoods_id;
	}
}
