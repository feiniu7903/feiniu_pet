package com.lvmama.finance.settlement.ibatis.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.vo.Constant;
import com.lvmama.finance.base.PageDao;
import com.lvmama.finance.group.ibatis.po.FinExchangeRate;
import com.lvmama.finance.settlement.ibatis.po.ExchangeRate;

@SuppressWarnings({ "unchecked" })
@Repository
public class RateSetDao extends PageDao {
	
	/**
	 * 查询所有外汇币种的汇率
	 * @return
	 */
	public List<ExchangeRate> search(){
		List<ExchangeRate> ers = queryForList("RATESET.search");
		for(ExchangeRate er: ers){
			er.setCurrencyName(Constant.FIN_CURRENCY.getCnName(er.getForeignCurrency()));
		}
		return ers;
	}

	/**
	 * 新增汇率信息
	 * @param finExchangeRate
	 */
	public boolean insertRate(FinExchangeRate finExchangeRate){
		boolean flag = true;
		try{
			insert("RATESET.insertRate", finExchangeRate);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 更新汇率信息
	 * @param finExchangeRate
	 */
	public boolean updateRate(FinExchangeRate finExchangeRate){
		boolean flag = true;
		try{
			update("RATESET.updateRate", finExchangeRate);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 根据币种查询汇率信息
	 * @param currency
	 * @return
	 */
	public FinExchangeRate queryByCurrency(String currency){
		return (FinExchangeRate)queryForObject("RATESET.queryByCurrency", currency);
	}
	
}
