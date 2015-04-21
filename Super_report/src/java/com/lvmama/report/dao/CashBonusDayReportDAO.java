package com.lvmama.report.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.CashBonusDayReport;

public class CashBonusDayReportDAO extends BaseIbatisDAO{
	
	
	public void insertData(CashBonusDayReport cashBonusDayReport){
		super.insert("CASH_BONUS_DAY_REPORT.insertData",cashBonusDayReport);
	}
	
	
	/**
	 * 查询指定日期奖金账户余额总金额(分)
	 * @param createDate 指定日期某天
	 * @return 奖金账户余额总金额(分)
	 */
	public Long getTotalSumCash(Date createDate){
		Long totalSumCash=(Long)super.queryForObject("CASH_BONUS_DAY_REPORT.getTotalSumCash",createDate);
		if(null==totalSumCash){
			return 0L;
		}
		return totalSumCash;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, BigDecimal> getTotalAddedBonus(String startDate,String endDate){
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return super.queryForMap("CASH_BONUS_DAY_REPORT.getTotalAddedBonus", map, "comefrom", "total");
	}
	
	public Long getTotalPayBonus(String startDate,String endDate){
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		Long totalPayBonus=(Long) super.queryForObject("CASH_BONUS_DAY_REPORT.getTotalPayBonus", map);
		if(null==totalPayBonus){
			return 0L;
		}
		return totalPayBonus;
	}
	
	
	public Long getCounts(Date createDate){
		Long counts=(Long)super.queryForObject("CASH_BONUS_DAY_REPORT.getCounts",createDate);
		if(null==counts){
			return 0L;
		}
		return counts;
	}
	
	public void delete(Date createDate){
		super.delete("CASH_BONUS_DAY_REPORT.delete", createDate);
	}
	
	public Long sumTotalBonusBalance(){
		Long balance=(Long)super.queryForObject("CASH_BONUS_DAY_REPORT.sumTotalBonusBalance");
		if(null==balance){
			return 0L;
		}
		return balance;
	}
	

}
