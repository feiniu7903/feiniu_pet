package com.lvmama.finance.settlement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.finance.BaseService;
import com.lvmama.finance.group.ibatis.po.FinExchangeRate;
import com.lvmama.finance.settlement.ibatis.dao.RateSetDao;
import com.lvmama.finance.settlement.ibatis.po.ExchangeRate;

@Service
public class RateSetServiceImpl extends BaseService implements RateSetService {

	@Autowired
	private RateSetDao rateSetDao;
	private final String LOG_OBJECT_TYPE = "FIN_EXCHANGE_RATE";

	/**
	 * 查询所有外汇币种的汇率
	 */
	@Override
	public List<ExchangeRate> search() {
		return rateSetDao.search();
	}

	@Override
	public boolean insertRate(FinExchangeRate finExchangeRate) {
		boolean flag = rateSetDao.insertRate(finExchangeRate);
		this.log(finExchangeRate.getId(), this.LOG_OBJECT_TYPE, "RATE", "新增汇率", "新增为"+finExchangeRate.getRate());
		return flag;
	}

	@Override
	public boolean updateRate(FinExchangeRate finExchangeRate) {
		boolean flag = rateSetDao.updateRate(finExchangeRate);
		this.log(finExchangeRate.getId(), this.LOG_OBJECT_TYPE, "RATE", "修改汇率", "修改为"+finExchangeRate.getRate());
		return flag;
	}

	@Override
	public FinExchangeRate queryByCurrency(String currency) {
		return rateSetDao.queryByCurrency(currency);
	}

}
