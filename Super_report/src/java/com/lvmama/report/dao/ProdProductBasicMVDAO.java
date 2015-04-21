package com.lvmama.report.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.ProdProductBasicMV;

/**
 * 
 * @author luoyinqi
 *
 */
public class ProdProductBasicMVDAO extends BaseIbatisDAO {
	
	public List<ProdProductBasicMV> queryProdProductBasicMVByTime(Map param,boolean isForReportExport){
		List<ProdProductBasicMV> result = new ArrayList<ProdProductBasicMV>();
		if (null == param.get("tableName")) {
			param.put("tableName", "PROD_PRODUCT_BASIC_MV");
		}
		result = super.queryForList("PROD_PRODUCT_BASIC_MV.queryProdProductBasicMVByTime", param,isForReportExport);
		return result;
	}
	
	public Long countProdProductBasicMVByTime(Map param){
		if (null == param.get("tableName")) {
			param.put("tableName", "PROD_PRODUCT_BASIC_MV");
		}
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.countProdProductBasicMVByTime", param);
	}
	
	public Long sumAmontProdProductBasicMV(Map param){
		if (null == param.get("tableName")) {
			param.put("tableName", "PROD_PRODUCT_BASIC_MV");
		}
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.sumAmountProdProductBasicMV", param);
	}
	
	public Long sumDestProdProductBasicMV(){
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.sumdestProdProductBasicMV");
	}
	
	public Long sumAmountQuantityProdProductBasicMV(Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "PROD_PRODUCT_BASIC_MV");
		}
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.sumAmountQuantityProdProductBasicMV",param);
	}
	
	public Long sumProdProductOnlineBasicMV(){
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.sumProdProductOnlineBasicMV");
	}
	
	public Long sumDestAmontProdProductBasicMV(Map param){
		if (null == param.get("tableName")) {
			param.put("tableName", "PROD_PRODUCT_BASIC_MV");
		}
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.sumdestAmountProdProductBasicMV", param);
	}
	
	public Long sumPerfitProdProductBasicMV(Map param){
		if (null == param.get("tableName")) {
			param.put("tableName", "PROD_PRODUCT_BASIC_MV");
		}
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.sumPerfitProdProductBasicMV", param);
	}
	
	public Long sumProdProductBasicMV(){
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.sumProdProductBasicMV");
	}
	
	public Long sumProdAmontProdProductBasicMV(Map param){
		if (null == param.get("tableName")) {
			param.put("tableName", "PROD_PRODUCT_BASIC_MV");
		}
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.sumProdAmountProdProductBasicMV", param);
	}
	
	public Long sumOrderQuantityProdProductBasicMV(Map param) {
		if (null == param.get("tableName")) {
			param.put("tableName", "PROD_PRODUCT_BASIC_MV");
		}
		return (Long)super.queryForObject("PROD_PRODUCT_BASIC_MV.sumOrderQuantityProdProductBasicMV", param);
	}

}
