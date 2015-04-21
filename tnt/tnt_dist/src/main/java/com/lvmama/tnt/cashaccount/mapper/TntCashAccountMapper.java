package com.lvmama.tnt.cashaccount.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.cashaccount.po.TntCashAccount;
import com.lvmama.tnt.comm.vo.Page;

/**
 * 现金账户
 * 
 * @author gaoxin
 * @version 1.0
 */
@Repository
public interface TntCashAccountMapper {

	public int insert(TntCashAccount entity);

	public List<TntCashAccount> findPage(Page<TntCashAccount> page);

	public int count(TntCashAccount entity);

	public int update(TntCashAccount tntCashAccount);

	public int updateBalance(TntCashAccount tntCashAccount);

	public int appendBalance(TntCashAccount tntCashAccount);

	public int delete(Long tntCashAccountId);

	public TntCashAccount getById(Long tntCashAccountId);

	public TntCashAccount getByUserId(Long userId);

	public int checkUnique(Map<String, Object> map);

	public int updatePayMobile(TntCashAccount entity);

	public int updatePayPassword(TntCashAccount entity);
}
