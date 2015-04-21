package com.lvmama.tnt.cashaccount.mapper;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.cashaccount.po.TntCashFreezeQueue;
import com.lvmama.tnt.comm.vo.Page;

/**
 * 现金账户金额冻结队列
 * @author gaoxin
 * @version 1.0
 */
@Repository
public interface TntCashFreezeQueueMapper {
	
	public void insert(TntCashFreezeQueue entity);
	
	public List<TntCashFreezeQueue> findPage(Page<TntCashFreezeQueue> page);

	public int count(TntCashFreezeQueue entity);

	public int update(TntCashFreezeQueue tntCashFreezeQueue);

	public int delete(Long tntCashFreezeQueueId);

	public TntCashFreezeQueue getById(Long tntCashFreezeQueueId);
	

}
