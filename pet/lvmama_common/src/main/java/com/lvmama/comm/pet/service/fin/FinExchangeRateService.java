package com.lvmama.comm.pet.service.fin;

import java.util.List;

import com.lvmama.comm.pet.po.fin.FinExchangeRate;

public interface FinExchangeRateService {
	/**
	 * 查询所有的外汇币种的汇率
	 * @return
	 */
	public List<FinExchangeRate> search();
	
	/**
	 * 新增汇率信息
	 * @return 新增数据成功，返回true
	 */
	public boolean insertRate(FinExchangeRate finExchangeRate);
	
	/**
	 * 更新汇率信息
	 * @return 更新数据成功，返回true
	 */
	public boolean updateRate(FinExchangeRate finExchangeRate);
	
	/**
	 * 根据币种查询汇率信息
	 * @param currency
	 * @return
	 */
	public FinExchangeRate queryByCurrency(String currency);
	
}
