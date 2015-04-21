package com.lvmama.pet.search.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;

public class ProdBranchSearchInfoDAO extends BaseIbatisDAO{
	/**
	 * 根据参数获取符合条件的列表信息后台关联产品修改排序值用
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProdBranchSearchInfo> queryProdBranchSearchInfoListByProductId(Long productId){
		return super.queryForList("PROD_BRANCH_SEARCH_INFO.querySimpleProdBranchSearchInfoListByProductId",productId);
	}

	/**
	 * 只读取上线的并且是前台可以显示的列表,主要是给前台使用.
	 * @param productId
	 * @param additional
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProdBranchSearchInfo> getProductBranchByProduct(Long productId, String additional,String online,String visible) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		if(StringUtils.isNotEmpty(additional)){
			param.put("additional", additional);
		}
		if(StringUtils.isNotEmpty(online)){
			param.put("online",online);
		}
		if(StringUtils.isNotEmpty(visible)){
			param.put("visible", visible);
		}
		return super.queryForList("PROD_BRANCH_SEARCH_INFO.queryProductBranch", param);
	}
	/**
	 * 查询 酒店placeId 下可用 房型，用于前台展示
	 * @param param
	 * @return
	 */
	public List<ProdBranchSearchInfo> getProductBranchByPlaceId(Long placeId,String additional,String online,String visible){
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("productId", placeId);
		if(StringUtils.isNotEmpty(additional)){
			param.put("additional", additional);
		}
		if(StringUtils.isNotEmpty(online)){
			param.put("online",online);
		}
		if(StringUtils.isNotEmpty(visible)){
			param.put("visible", visible);
		}
		return super.queryForList("PROD_BRANCH_SEARCH_INFO.queryProductBranchByPlaceId", param);
	}
	public List<ProdBranchSearchInfo> query(Map<String,Object> param){
		return super.queryForList("PROD_BRANCH_SEARCH_INFO.query", param);
	}
}
