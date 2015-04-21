package com.lvmama.prd.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.DateUtil;

public  class MetaTimePriceDAO extends BaseIbatisDAO {

	public void insert(TimePrice record) {
		super.insert("META_TIME_PRICE.insert", record);
	}

	public void update(TimePrice record) {
		super.update("META_TIME_PRICE.updateByPrimaryKey", record);
	}
	
	public void updateDynamic(TimePrice record) {
		super.update("META_TIME_PRICE.updateDynamicByPrimaryKey", record);
	}
	
	public List<TimePrice> getMetaTimePriceByBranchIdAsc(Map param) {
		return super.queryForList("META_TIME_PRICE.selectByParamsOrderBY", param);
	}
	

	public List<TimePrice> getMetaTimePriceByMetaBranchId(Map param) {
		//暂时使用该接口保证当中的数据准确未来再改用1000的查询接口
		return super.queryForListForReport("META_TIME_PRICE.selectByParams", param);
	}

	public void deleteByBeginDateAndEndDate(Map param) {
		super.delete("META_TIME_PRICE.deleteByBeginDateAndEndDate",param);
	}
	
	public void deleteByPK(Long timePriceId) {
		Map param=new HashMap();
		param.put("timePriceId", timePriceId);
		super.delete("META_TIME_PRICE.deleteByPK",param);
	}
	
	public TimePrice getMetaTimePriceByIdAndDate(Long metaBranchId, Date specDate) {
		TimePrice timePrice = new TimePrice();
		timePrice.setMetaBranchId(metaBranchId);
		timePrice.setSpecDate(DateUtil.getDayStart(specDate));
		return (TimePrice)super.queryForObject("META_TIME_PRICE.getTimePriceByIdAndDate",timePrice);
	}
	
	public List<TimePrice> selectMetaTimePriceByProdBranchIdAndDate(Long prodBranchId,Date specDate){
		TimePrice timePrice = new TimePrice();
		timePrice.setProdBranchId(prodBranchId);
		timePrice.setSpecDate(DateUtil.getDayStart(specDate));
		return super.queryForList("META_TIME_PRICE.selectMetaTimePriceByProdBranchIdAndDate",timePrice);
	}
	
	/**
	 * 按销售产品的类别Id与时间查询时间价格表.
	 * @param prodBranchId
	 * @param specDate
	 * @return
	 */
	public TimePrice getGroupTimePriceByBranchIdAndDate(Long prodBranchId,Date specDate){
		TimePrice tp=new TimePrice();
		tp.setProdBranchId(prodBranchId);
		tp.setSpecDate(specDate);
		return (TimePrice)super.queryForObject("META_TIME_PRICE.getGroupTimePriceByBranchIdAndDate",tp);
	}
	
	public Long selectIsExistProdProduct(Map<String,Object> params){
		return (Long) super.queryForObject("META_TIME_PRICE.selectIsExistProdProduct", params);
	}
	/**
	 * 通过销售产品类别ID和日期查询市场价
	 * @param prodBranchId
	 * @param date
	 * @return
	 */
	public Long selectMarkPriceForProdByDate(Long prodBranchId,Date date){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("prodBranchId", prodBranchId);
		params.put("date", date);
		return (Long) super.queryForObject("META_TIME_PRICE.selectMarkPriceForProdByDate", params);
	}
	
	/**
	 * 通过销售产品类别ID和日期查询结算价
	 * @param prodBranchId 销售产品类别ID
	 * @param date 日期
	 * @return 结算价
	 */
	public Long selectTotalSettlementPriceForProdByDate(Long prodBranchId,Date date){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("prodBranchId", prodBranchId);
		params.put("date", date);
		return (Long) super.queryForObject("META_TIME_PRICE.selectTotalSettlementPriceForProdByDate", params);
	}

	public Long selectNotNeedResourceConfirm(Map<String,Object> map){
		return (Long)super.queryForObject("META_TIME_PRICE.selectNotNeedResourceConfirm", map);
	}
	
	public void resetStock(){
		super.update("META_TIME_PRICE.resetStock");		
	}
	
	public TimePrice getMinOrMaxTimePriceByMetaBranchId(Long metaBranchId, boolean isMax) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("metaBranchId", metaBranchId);
		params.put("isMax", isMax);
		return (TimePrice) super.queryForObject("META_TIME_PRICE.getMinOrMaxTimePriceByMetaBranchId", params);
	}

	public void updateTrainTimePrice(TimePrice bean) {
		// TODO Auto-generated method stub
		super.update("META_TIME_PRICE.updateTrainTimePrice", bean);
	}
}
