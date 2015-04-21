package com.lvmama.ebk.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.service.ebooking.EbkProdBranchService;
import com.lvmama.ebk.dao.EbkProdBranchDAO;

public class EbkProdBranchServiceImpl implements EbkProdBranchService {
	@Autowired
	private EbkProdBranchDAO ebkProdBranchDAO;
	
	@Override
	public List<EbkProdBranch> query(EbkProdBranch ebkProdBranch) {
		return ebkProdBranchDAO.findListByTerm(ebkProdBranch);
	}

	/**
	 * 通过branchId获取类别名称
	 * @author ZHANG Nan
	 * @param branchId 类别ID 示例 001,002
	 * @return 类别名称1,类别名称2
	 */
	public String getBrancheNames(String branchId){
		String brancheNames="";
		if(StringUtils.isNotBlank(branchId)){
			String branchIds[]=branchId.split(",");
			for (String branchIdTemp : branchIds) {
				EbkProdBranch EbkProdBranch=queryForEbkProdBranchId(Long.parseLong(branchIdTemp));
				brancheNames+=EbkProdBranch.getBranchName()+",";
			}
		}
		if(StringUtils.isNotBlank(brancheNames) && brancheNames.length()>0){
			brancheNames=brancheNames.substring(0,brancheNames.length()-1);
		}
		return brancheNames;
	}
	
	@Override
	public	EbkProdBranch insert(final EbkProdBranch ebkProdBranch){
		Long id = ebkProdBranchDAO.insertEbkProdBranchDO(ebkProdBranch);
		ebkProdBranch.setProdBranchId(id);
		return ebkProdBranch;
	}
	@Override
	public EbkProdBranch queryForEbkProdBranchId(Long ebkProdBranchId) {
		return ebkProdBranchDAO.findEbkProdBranchDOByPrimaryKey(ebkProdBranchId);
	}

	@Override
	public int update(EbkProdBranch ebkProdBranch) {
		return ebkProdBranchDAO.updateEbkProdBranchDO(ebkProdBranch);
	}

	@Override
	public int delete(Long ebkProdBranchId) {
		return ebkProdBranchDAO.deleteEbkProdBranchDOByPrimaryKey(ebkProdBranchId);
	}

}
