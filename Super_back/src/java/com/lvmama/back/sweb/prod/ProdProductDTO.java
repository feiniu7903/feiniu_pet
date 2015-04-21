package com.lvmama.back.sweb.prod;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.lvmama.comm.bee.po.prod.ProdProductRelation;

public abstract class ProdProductDTO {

	public static JSONObject conver(ProdProductRelation relation){
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"branch","relationProduct"});
		JSONObject obj=JSONObject.fromObject(relation, config);
		if(relation.getBranch()!=null){
			config=new JsonConfig();
			JSONObject branch=JSONObject.fromObject(relation.getBranch(),config);
			obj.put("branch", branch);
		}
		if(relation.getRelationProduct()!=null){
			JSONObject prod=new JSONObject();
			prod.put("productName", relation.getRelationProduct().getProductName());
			prod.put("zhSubProductType", relation.getRelationProduct().getZhSubProductType());
			obj.put("relationProduct", prod);
		}
		
		return obj;
	}
}
