package com.lvmama.comm.bee.service.ebooking;

import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
public interface EbkProdBranchService {
	
	/**
	 * 根据查询条件查询EBK类型列表信息
	 * @param params
	 * @return
	 */
	List<EbkProdBranch> query(final EbkProdBranch ebkProdBranch);
	
	/**
	 * 根据EBK产品类型主键查询类型信息
	 * @param ebkProdBranchId
	 * @return
	 */
	EbkProdBranch queryForEbkProdBranchId(final Long ebkProdBranchId);
	
	/**
	 * 插入EBK产品类型信息
	 * @param ebkProdBranch
	 * @return
	 */
	EbkProdBranch insert(final EbkProdBranch ebkProdBranch);
	/**
	 * 根据EBK产品类型主键更新类型信息
	 * @param ebkProdBranch
	 * @return
	 */
	int update(final EbkProdBranch ebkProdBranch);
	
	/**
	 * 根据EBK产品主键删除类型信息
	 * @param ebkProdBranchId
	 * @return
	 */
	int delete(final Long ebkProdBranchId);
	/**
	 * 通过branchId获取类别名称
	 * @author ZHANG Nan
	 * @param branchId 类别ID 示例 001,002
	 * @return 类别名称1,类别名称2
	 */
	public String getBrancheNames(String branchId);
}
