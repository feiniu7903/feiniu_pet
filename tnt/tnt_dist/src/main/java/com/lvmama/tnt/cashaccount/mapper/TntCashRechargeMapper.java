package com.lvmama.tnt.cashaccount.mapper;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.cashaccount.po.TntCashRecharge;
import com.lvmama.tnt.comm.vo.Page;

/**
 * 现金帐户充值记录
 * @author gaoxin
 * @version 1.0
 */
@Repository
public interface TntCashRechargeMapper {
	
	public void insert(TntCashRecharge entity);
	
	public List<TntCashRecharge> findPage(Page<TntCashRecharge> page);

	public int count(TntCashRecharge entity);

	public int update(TntCashRecharge tntCashRecharge);

	public int delete(Long tntCashRechargeId);

	public TntCashRecharge getById(Long tntCashRechargeId);
	

}
