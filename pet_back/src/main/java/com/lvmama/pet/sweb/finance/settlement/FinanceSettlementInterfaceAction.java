package com.lvmama.pet.sweb.finance.settlement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vst.service.VstProdProductService;
import com.lvmama.comm.vst.service.VstSuppSupplierService;
import com.lvmama.comm.vst.vo.VstProdGoodsVo;
import com.lvmama.comm.vst.vo.VstProdProductVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierSettlementVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

@Namespace(value = "/finance/settlementInterface")
public class FinanceSettlementInterfaceAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7988182141582376342L;
	@Autowired
	private VstSuppSupplierService vstSuppSupplierService;
	@Autowired
	private VstProdProductService vstProdProductService;
	
	private String search;
	private Long metaProductId;
	/**
	 * 查询供应商
	 */
	public void searchSupplierList() {
		List<VstSuppSupplierVo> result=vstSuppSupplierService.findVstSuppSupplierByBlurName(search);
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	/**
	 * 查询结算对象
	 */
	@Action("searchSupplierSettleRule")
	public void searchSupplierSettleRule() {
		List<VstSuppSupplierSettlementVo> result=vstSuppSupplierService.findSuppSupplierSettlementByBlurName(search);
		
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(result)){
			for(VstSuppSupplierSettlementVo ss:result){
				JSONObject obj=new JSONObject();
				obj.put("id", ss.getSettleRuleId());
				obj.put("text", ss.getSettleName()+"("+ss.getSettleRuleId()+")");
				array.add(obj);
			}
		}
		sendAjaxResultByJson(array.toString());
	}
	/**
	 * 根据ID，或名称查找供应商
	 */
	@Action("searchSupplierList")
	public void search(){
		List<VstSuppSupplierVo> list = new ArrayList<VstSuppSupplierVo>();
		if (StringUtils.isNotEmpty(search)) {
			if(search.matches("\\d+")){
				VstSuppSupplierVo vstSuppSupplierVo = vstSuppSupplierService.findVstSuppSupplierById(Long.parseLong(search));
				if(null!=vstSuppSupplierVo){
					list.add(vstSuppSupplierVo);
				}
			}else{
				list =vstSuppSupplierService.findVstSuppSupplierByBlurName(search);
			}
		}
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(VstSuppSupplierVo ss:list){
				JSONObject obj=new JSONObject();
				obj.put("id", ss.getSupplierId());
				obj.put("text", ss.getSupplierName()+"("+ss.getSupplierId()+")");
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	/**
	 * ajax读取产品信息.
	 */
	@Action("searchGoodsList")
	public void searchMetaList(){
		Map<String,Object> param = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(search)) {
			param.put("productName", search);
			if(search.matches("\\d+")){
				param.put("orProductId", NumberUtils.toLong(search));
			}
		}
		param.put("valid", "Y");
		List<VstProdProductVo> list = vstProdProductService.findProdProductListByBlurName(search);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			try{
				for(VstProdProductVo mp:list){
					JSONObject obj=new JSONObject();
					obj.put("id", mp.getProductId());
					obj.put("text", mp.getProductName());
					obj.put("branchType", mp.getGoodsList());
					array.add(obj);
				}
			}catch(Exception ex){				
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	@Action("searchGoodsTypeList")
	public void getMetaBranchJSON(){
		VstProdProductVo vstProdProductVo = vstProdProductService.findProdProductListById(metaProductId);
		JSONArray array=new JSONArray();
		if(null!=vstProdProductVo && null!=vstProdProductVo.getGoodsList() && CollectionUtils.isNotEmpty(vstProdProductVo.getGoodsList())){
			try{
				for(VstProdGoodsVo branch:vstProdProductVo.getGoodsList()){
					JSONObject obj=new JSONObject();
					obj.put("branchId", branch.getGoodsId());
					obj.put("branchName", branch.getGoodsName());
					array.add(obj);
				}
			}catch(Exception ex){				
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public Long getMetaProductId() {
		return metaProductId;
	}
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}
}
