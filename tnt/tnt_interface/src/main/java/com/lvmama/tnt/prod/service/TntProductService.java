package com.lvmama.tnt.prod.service;

import java.util.List;
import java.util.Map;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant.OBJECT_TYPE;
import com.lvmama.tnt.prod.po.TntProduct;

public interface TntProductService {

	// 根据产品Id 查询 以channelId为key,TntProdBlacklist对象为value的Map
	public Map<Long, TntProduct> getBlackListMapByBranchId(Long branchId);

	public boolean pullToBlack(TntProduct t);
	/**
	 * 是否在黑名单中
	 * @param branchId
	 * @param channelId
	 * @return （true 在黑名单/false 不在黑名单）
	 */
	public boolean isInBlack(Long branchId, Long channelId);

	public boolean pullFromBlack(TntProduct t);

	public List<TntProduct> search(Page<TntProduct> page);

	public long count(TntProduct t);

	public TntProduct getByBranchId(Long branchId);
	
	
	/**
	 * 更新产品
	 * @param t
	 * @return
	 */
	public boolean saveOrUpdateProduct(TntProduct t);

	/**
	 * lvmama系统修改产品，分销平台同步更新产品
	 * @param objectId productid/metaProductId
	 * @param objectType Product/metaProduct
	 */
	public void sync(Long objectId, OBJECT_TYPE objectType);

}
