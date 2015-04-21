package com.lvmama.pet.pub.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComBank;

/**
 * 财务相关银行DAO实现类.
 *
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public final class ComBankDAO extends BaseIbatisDAO {
	/**
	 * 获取财务相关银行列表.
	 *
	 * @return 财务相关银行列表
	 */
	public List<ComBank> getComBankList() {
		return super.queryForList(
				"COM_BANK.getComBankList");
	}

}
