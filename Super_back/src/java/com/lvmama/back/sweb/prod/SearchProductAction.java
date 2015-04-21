/**
 * 
 */
package com.lvmama.back.sweb.prod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.utils.json.JSONOutput;

/**
 * @author yangbin
 *
 */
public class SearchProductAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2181109949954589350L;
	private ProdProductService prodProductService;
	private String search;
	private String type;
	private String productType;//如果type为空查询该属性
	private String useType;
	
	@Action("/prod/searchProductJSON")
	public void searchProduct(){
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(search)) {
			param.put("productSearch", search);
			if(NumberUtils.toLong(search)>0){
				param.put("productSearchId", search);
			}
		}
		if(StringUtils.equals(useType, "true")){
			param.put("productTypeList",new String[]{productType});
			if(StringUtils.isNotEmpty(type)){
				param.put("subProductTypeList", new String[]{type});
			}
		}else{
			if(StringUtils.isNotEmpty(type)){
				param.put("productTypeList",new String[]{"OTHER"});
				param.put("subProductTypeList", new String[]{type});
			}else{
				param.put("productTypeList", new String[]{productType});
			}
		}
		param.put("_startRow", "0");
		param.put("_endRow", "10");
		
		List<ProdProduct> list = prodProductService.selectProductByParms(param);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(ProdProduct pp:list){
				JSONObject obj=new JSONObject();
				obj.put("id", pp.getProductId());
				obj.put("text", pp.getProductName());
				StringBuffer sb=new StringBuffer();				
				sb.append(pp.getProductId()).append(
						"------").append(
						pp.getBizcode()).append(
						"------").append(
						pp.getProductName());
				obj.put("extra",sb.toString());
				array.add(obj);
			}
				
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	
	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	/**
	 * @param productType the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}


	/**
	 * @param useType the useType to set
	 */
	public void setUseType(String useType) {
		this.useType = useType;
	}
	
}
