package com.lvmama.tnt.cashaccount.mapper;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.cashaccount.po.TntCashRefundment;
import com.lvmama.tnt.comm.vo.Page;

/**
 * TntCashRefundment
 * @author gaoxin
 * @version 1.0
 */
@Repository
public interface TntCashRefundmentMapper {
	
	public void insert(TntCashRefundment entity);
	
	public List<TntCashRefundment> findPage(Page<TntCashRefundment> page);

	public int count(TntCashRefundment entity);

	public int update(TntCashRefundment tntCashRefundment);

	public int delete(Long tntCashRefundmentId);

	public TntCashRefundment getById(Long tntCashRefundmentId);
	

}
