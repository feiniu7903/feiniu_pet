package com.lvmama.tnt.cashaccount.mapper;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.cashaccount.po.TntCashMoneyDraw;
import com.lvmama.tnt.comm.vo.Page;

/**
 * 现金帐户提现记录
 * @author gaoxin
 * @version 1.0
 */
@Repository
public interface TntCashMoneyDrawMapper {
	
	public void insert(TntCashMoneyDraw entity);
	
	public List<TntCashMoneyDraw> findPage(Page<TntCashMoneyDraw> page);

	public int count(TntCashMoneyDraw entity);

	public int update(TntCashMoneyDraw tntCashMoneyDraw);

	public int delete(Long tntCashMoneyDrawId);

	public TntCashMoneyDraw getById(Long tntCashMoneyDrawId);
	

}
