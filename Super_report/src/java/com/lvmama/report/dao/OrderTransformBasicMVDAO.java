package com.lvmama.report.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.report.po.OrderTransformBasiceMV;
import com.lvmama.report.po.ProductTransformBasicMV;

/**
 * 订单转化分析率
 * @author yangchen
 *
 */
public class OrderTransformBasicMVDAO extends BaseIbatisDAO {
	/***
	 * 订单转化的对象的列表集合
	 * @param param 参数
	 * @return OrderTransformBasiceMV列表的集合
	 */
	public List<OrderTransformBasiceMV> queryOrderTransformBasicMV(final Map<String, Object> param,boolean isForReportExport)
	{
		if(isForReportExport){
			return super.queryForListForReport(
					"ORDER_TRANSFORM_BASIC_MV.queryOrderTransformBasicMV", param);
		}else{
			return super.queryForList(
					"ORDER_TRANSFORM_BASIC_MV.queryOrderTransformBasicMV", param);
		}
	}

	/***
	 * 获取前台已预订的总数
	 * @param param 参数
	 * @return 前台已预订的总数
	 */
	public Long sumFOrderQuantity(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.sumFOrderQuantityOrderTransformBasicMV", param);
	}

	/**
	 * 获取前台已支付的总数
	 * @param param 参数
	 * @return 前台已支付的总数
	 */
	public Long sumFPayedOrderQuantity(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.sumFPayedQuantityOrderTransformBasicMV", param);
	}

	/***
	 * 获取后台已预订的总数
	 * @param param 参数
	 * @return 后台已预订的总数
	 */
	public Long sumBOrderQuantity(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.sumBOrderQuantityOrderTransformBasicMV", param);
	}

	/**
	 * 获取后台已支付的总数
	 * @param param 参数
	 * @return 后台已支付的总数
	 */
	public Long sumBPayedOrderQuantity(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.sumBPayedQuantityOrderTransformBasicMV", param);
	}

	/**
	 * 获取订单总条数
	 * @param param 参数
	 * @return 订单总条数
	 */
	public Long countOrderTransformQuantity(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.countOrderTransformBasicMV", param);
	}


	/***
	 * 产品转化的对象的列表集合
	 * @param param 参数
	 * @return OrderTransformBasiceMV列表的集合
	 */
	public List<ProductTransformBasicMV> queryProductTransformBasicMV(final Map<String, Object> param,boolean isForReportExport) {
		return super.queryForList(
				"ORDER_TRANSFORM_BASIC_MV.queryProductTransformBasicMV", param,isForReportExport);
	}
	/**
	 * 获取产品转化总条数
	 * @param param 参数
	 * @return 产品转化总条数
	 */
	public Long countProductTransformQuantity(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.countProductTransformBasicMV", param);
	}

	/***
	 * 获取产品已预订的总数
	 * @param param 参数
	 * @return 产品已预订的总数
	 */
	public Long sumProductQuantity(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.sumOrderQuantityProductTransformBasicMV", param);
	}
	/***
	 * 获取产品已支付的总数
	 * @param param 参数
	 * @return 产品已预订的总数
	 */
	public Long sumPayedProductQuantity(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.sumPayedQuantityProductTransformBasicMV", param);
	}
	/***
	 * 获取电话总数
	 * @param param 参数
	 * @return 电话的总数
	 */
	public Long sumCallQuantity(final Map<String, Object> param) {
		return (Long) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.sumCallQuantityProductTransformBasicMV", param);
	}

	/***
	 * 获取预订转化率总数
	 * @param param 参数
	 * @return 获取预订转化率
	 */
	public Double avgProductConvert(final Map<String, Object> param) {
		return (Double) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.avgProductTransformBasicMV", param);
	}

	/***
	 * 获取支付转化率总数
	 * @param param 参数
	 * @return 获取支付转化率
	 */
	public Double avgPayedConvert(final Map<String, Object> param) {
		return (Double) super.queryForObject(
				"ORDER_TRANSFORM_BASIC_MV.avgPayedProductTransformBasicMV", param);
	}
}
