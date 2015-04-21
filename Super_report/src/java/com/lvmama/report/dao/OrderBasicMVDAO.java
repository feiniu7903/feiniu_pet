package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.OrderBasicMV;
import com.lvmama.report.vo.OrderBasicVO;

public class OrderBasicMVDAO extends BaseIbatisDAO {
	
	/*
	 * 查询OrderBasicMV记录
	 * @param param 查询条件
	 * @return OrderBasicMV列表
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OrderBasicMV> queryOrderBasicMVByParam(Map param,boolean isForReportExport){
		return super.queryForList("ORDER_BASIC_MV.queryOrderBasicMVByParam", param,isForReportExport);
	}
	
	/*
	 * 查询记录总数
	  * @param param 查询条件
	 * @return 记录总数
	 * */
	@SuppressWarnings("rawtypes")
	public Long countOfOrderBasicMVByParam(Map param){
		return (Long)super.queryForObject("ORDER_BASIC_MV.countOfOrderBasicMVByParam", param);
	}
	
	/*
	 * 获取统计的OrderBasicVO总额和数量
	  * @param param 查询条件
	 * @return OrderBasicVO订单统计数据
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<OrderBasicVO> sumOrderBasicVOByParam(Map param,boolean isForReportExport){
		return (List<OrderBasicVO>) super.queryForList("ORDER_BASIC_MV.sumOrderBasicVOByParam", param,isForReportExport);
	}
	
}
