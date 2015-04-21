package com.lvmama.tnt.prod.mapper;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.prod.po.TntProduct;

/**
 * TntProdBlacklist
 * 
 * @author gaoxin
 * @version 1.0
 */

public interface TntProductMapper {

	public int insert(TntProduct entity);

	public List<TntProduct> findPage(Page<TntProduct> page);

	public int count(TntProduct entity);

	public int update(TntProduct tntProdBlacklist);

	public int deleteById(Long tntProdBlacklistId);

	public int delete(TntProduct t);

	public TntProduct getById(Long tntProductId);

	public TntProduct selectOne(TntProduct tntProduct);

	public List<TntProduct> getByProductId(Long productId);

	public List<TntProduct> getByBranchId(Long branchId);

}
