package com.lvmama.tnt.cashaccount.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.cashaccount.po.TntCashPay;
import com.lvmama.tnt.comm.vo.Page;

/**
 * 现金帐户支付记录
 * 
 * @author gaoxin
 * @version 1.0
 */
@Repository
public interface TntCashPayMapper {

	public int insert(TntCashPay entity);

	public List<TntCashPay> findPage(Page<TntCashPay> page);

	public int count(TntCashPay entity);

	public int update(TntCashPay tntCashPay);

	public int delete(Long tntCashPayId);

	public TntCashPay getById(Long tntCashPayId);

	public TntCashPay getByTntOrderId(Long tntOrderId);

	public int updateStatus(TntCashPay tntCashPay);

}
