package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;

public class ProdProductBranchDAO extends BaseIbatisDAO {

	public ProdProductBranchDAO() {
		super();
	}
	
	public List<ProdProductBranch> selectProdCanDistributeByProdType(Map<String,Object> params) {
		return super.queryForList("PROD_PRODUCT_BRANCH.selectProdCanDistributeByProdType",params);
	}

	public ProdProductBranch getProductDefaultBranchByProductId(Long productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("defaultBranch", "true");
		List<ProdProductBranch> list = selectByParam(map);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
	public List<ProdProductBranch> getProductBranchByMetaProdBranchId(Long metaBranchId) {
		return super.queryForList("PROD_PRODUCT_BRANCH.getProductBranchByMetaProdBranchId", metaBranchId);
	}
	
	public List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional,String online,String visible) {
		Map<String,Object> key = new HashMap<String, Object>();
		key.put("productId", productId);
		if(StringUtils.isNotEmpty(additional)){
			key.put("additional", additional);
		}
		if(StringUtils.isNotEmpty(online)){
			key.put("online",online);
		}
		if(StringUtils.isNotEmpty(visible)){
			key.put("visible", visible);
		}
		return super.queryForList("PROD_PRODUCT_BRANCH.getProductBranchByProductId", key);
	}

	/**
	 * 只读取上线的并且是前台可以显示的列表,主要是给前台使用.
	 * @param productId
	 * @param additional
	 * @return
	 */
	public List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional) {
		return getProductBranchByProductId(productId, additional, "true","true");
	}

	public int deleteByPrimaryKey(Long prodBranchId) {
		ProdProductBranch key = new ProdProductBranch();
		key.setProdBranchId(prodBranchId);
		int rows = super.delete("PROD_PRODUCT_BRANCH.deleteByPrimaryKey", key);
		return rows;
	}

	public Long insert(ProdProductBranch record) {
		Long pk = (Long) super.insert("PROD_PRODUCT_BRANCH.insert", record);
		return pk;
	}

	public void insertSelective(ProdProductBranch record) {
		super.insert("PROD_PRODUCT_BRANCH.insertSelective", record);
	}
	
	public ProdProductBranch selectByPrimaryKey(Long prodBranchId) {
		ProdProductBranch key = new ProdProductBranch();
		key.setProdBranchId(prodBranchId);
		ProdProductBranch record = (ProdProductBranch) super.queryForObject("PROD_PRODUCT_BRANCH.selectByPrimaryKey", key);
		return record;
	}

	public ProdProductBranch selectByPrimaryProdBranchId(Long prodBranchId) {
		ProdProductBranch key = new ProdProductBranch();
		key.setProdBranchId(prodBranchId);
		ProdProductBranch record = (ProdProductBranch) super.queryForObject("PROD_PRODUCT_BRANCH.selectByPrimaryProdBranchId", key);
		return record;
	}
	public int updateByPrimaryKeySelective(ProdProductBranch record) {
		int rows = super.update("PROD_PRODUCT_BRANCH.updateByPrimaryKeySelective", record);
		return rows;
	}

	public int updateByPrimaryKey(ProdProductBranch record) {
		int rows = super.update("PROD_PRODUCT_BRANCH.updateByPrimaryKey", record);
		return rows;
	}

	/**
	 * 清除一个产品的默认类别标记
	 * 
	 * @param productId
	 */
	public void clearProductDef(Long productId) {
		super.update("PROD_PRODUCT_BRANCH.clearProductDef", productId);
	}

	public List<ProdProductBranch> selectByParam(Map<String, Object> map) {
		return super.queryForList("PROD_PRODUCT_BRANCH.selectByParam", map);
	}

	public ProdProductBranch selectDefaultBranchByProductId(Long productId) {
		return getProductDefaultBranchByProductId(productId);
	}

	public void updatePriceByPK(ProdProductBranch branch) {
		super.update("PROD_PRODUCT_BRANCH.updatePriceByPK",
				branch);
	}

	public List<Long> selectAll(Map<String,Object> params) {
		return super.queryForList(
				"PROD_PRODUCT_BRANCH.selectAll",params);
	}
	public Long selectAllCount() {
		return (Long)super.queryForObject(
				"PROD_PRODUCT_BRANCH.selectAllCount");
	}
	
	public List<ProdProductBranch> selectListByProdJourney(final Long prodJourneyId){
		return super.queryForList("PROD_PRODUCT_BRANCH.selectListByProdJourney",prodJourneyId);
	}

	public ProdProductBranch getPreProductBranch(Map<String,Object> params){
		return (ProdProductBranch) super.queryForObject("PROD_PRODUCT_BRANCH.getPreProductBranch",params);
	}
	
	public ProdProductBranch getNextProductBranch(Map<String,Object> params){
		return (ProdProductBranch) super.queryForObject("PROD_PRODUCT_BRANCH.getNextProductBranch",params);
	}
	
	public void updateProductBranchSerialNumber(Map<String,Object> params) {
		super.update("PROD_PRODUCT_BRANCH.updateProductBranchSerialNumber",params);
	}	
	public List<ProdProductBranch> selectProdBranchsByStationStation(final Long stationStationId){
		ProdProductBranch branch = new ProdProductBranch();
		branch.setStationStationId(stationStationId);
		return super.queryForList("PROD_PRODUCT_BRANCH.selectProdBranchsByStationStation",branch);
	}
	public List<ProdProductBranch> selectProdTrainBranchsByParams(Map<String,Object> params){
		return super.queryForList("PROD_PRODUCT_BRANCH.selectProdTrainBranchsByParams",params);
	}

	public List<ProdProductBranch> selectB2BProd(Map<String, Object> map) {
		return super.queryForList("PROD_PRODUCT_BRANCH.selectB2BProd",map);
	}
	
	public long selectB2BProdCount(Map<String, Object> map) {
		return (Long)super.queryForObject(
				"PROD_PRODUCT_BRANCH.selectB2BProdCount",map);
	}
	public ProdProductBranch selectB2BProdByParam(Map<String, Object> map) {
		return (ProdProductBranch) super.queryForObject(
				"PROD_PRODUCT_BRANCH.selectB2BProdByParam",map);
	}
	
}