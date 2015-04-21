package com.lvmama.report.service;

import java.util.List;
import java.util.Map;

import com.lvmama.report.po.ChannelBasicMV;
import com.lvmama.report.po.LoscOrderStatisticsMV;
import com.lvmama.report.po.MetaProductBasicMV;
import com.lvmama.report.po.OrderBasicMV;
import com.lvmama.report.po.OrderCustomerBasicMV;
import com.lvmama.report.po.OrderSaleServiceBasicMV;
import com.lvmama.report.po.OrderTransformBasiceMV;
import com.lvmama.report.po.ProdProductBasicMV;
import com.lvmama.report.po.ProductTransformBasicMV;
import com.lvmama.report.po.UserRegisterBasic;
import com.lvmama.report.po.UserRegisterBasicMV;
import com.lvmama.report.po.VistorDetailBasicMV;
import com.lvmama.report.vo.OrderBasicVO;
import com.lvmama.report.vo.SupportRankAnalysisVo;

/**
 * @author luoyinqi
 */
public interface ReportService {
	/**
	 * @param param
	 *            p
	 * @return result
	 */
	List<ProdProductBasicMV> queryProdProductBasicMVByTime(Map param,boolean isForReportExport);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	Long countProdProductBasicMVByTime(Map param);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	List<ChannelBasicMV> queryChannelBasicMV(Map param,boolean isForReportExport);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	Long countChannelBasicMV(Map param,boolean isForReportExport);

	/**
	 * 统计总金额
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumAmontProdProductBasicMV(Map param);

	/**
	 * 统计景区数
	 * 
	 * @return result
	 */
	Long sumDestProdProductBasicMV();

	/**
	 * 统计产生销售的景区数
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumDestAmontProdProductBasicMV(Map param);

	/**
	 * 统计产品数
	 * 
	 * @return result
	 */
	Long sumProdProductBasicMV();

	/**
	 * 统计产生销售的产品数
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumProdAmontProdProductBasicMV(Map param);

	/**
	 * 毛利润
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumPerfitProdProductBasicMV(Map param);

	/**
	 * 订单总数
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumOrderQuantityProdProductBasicMV(Map param);

	/**
	 * 销量总数
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumAmountQuantityProdProductBasicMV(Map param);

	/***
	 * @param param
	 *            p
	 * @return result
	 */
	List<UserRegisterBasicMV> queryUserRegisterBasicMV(Map param);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	List<UserRegisterBasic> queryUserRegisterBasic(Map param,boolean isForReportExport);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	List<UserRegisterBasicMV> queryUserOverviewRegisterBasicMV(Map param,boolean isForReportExport);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	Long countUserRegisterBasic(Map param);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	Long countUserOverviewRegisterBasicMV(Map param);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	List<OrderSaleServiceBasicMV> queryOrderSaleServiceBasicMV(Map param,boolean isForReportExport);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	Long countOrderSaleServiceBasicMV(Map param);

	/**
	 * 会员增长总量
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumUserUpdate(Map param);

	/**
	 * 付费会员量（区间）
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumPayUserUpdate(Map param);

	/**
	 * 二次付费用户总量（区间）
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumPay2UserUpdate(Map param);

	/**
	 * 会员总量
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long allUserUpdate(Map param);

	/**
	 * 付费会员数（总）
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long allPayUserUpdate(Map param);

	/**
	 * 二次付费会员数（总）
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long allPay2UserUpdate(Map param);

	/**
	 * 退款总额
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumRefundAmountOrderSaleServiceBasicMV(Map param);

	/**
	 * 补偿总额
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumCompensationAmountOrderSaleServiceBasicMV(Map param);

	/**
	 * 销售总额(渠道)
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumAmountChannelBasicMV(Map param);

	/**
	 * 毛利润（渠道）
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumProfitChannelBasicMV(Map param);

	/**
	 * 在线销售产品数
	 * 
	 * @return result
	 */
	Long sumProdProductOnlineBasicMV();

	/**
	 * losc订单统计列表
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	List<LoscOrderStatisticsMV> queryLoscOrderStatisticsList(Map param,boolean isForReportExport);

	/**
	 * losc订单统计总数
	 * 
	 * @param param
	 *            p
	 * @return result
	 */
	Long loscOrderStatisticsCount(Map param);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	List<OrderCustomerBasicMV> queryOrderCustomerBasicMVByTime(Map param,boolean isForReportExport);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	Long countOrderCustomerBasicMVByTime(Map param);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumAmountOrderCustomerBasicMV(Map param);

	/**
	 * @param param
	 *            p
	 * @return result
	 */
	Long sumProfitOrderCustomerBasicMV(Map param);

	/**
	 * 查询OrderBasicMV记录
	 * @param param     查询条件
	 * @return OrderBasicMV列表
	 * */
	List<OrderBasicMV> queryOrderBasicMVByParam(Map param,boolean isForReportExport);

	/**
	 * 查询记录总数
	 * @param param  查询条件
	 * @return 记录总数
	 * */
	Long countOfOrderBasicMVByParam(Map param);

	/**
	 * 获取统计的OrderBasicVO总额和数量
	 * @param param  查询条件
	 * @return OrderBasicVO订单统计数据
	 * */
	OrderBasicVO sumOrderBasicVOByParam(Map param,boolean isForReportExport);

	/**
	 * 供应商分析的订单总数
	 * @param param   查询条件
	 * @return result result
	 */
	Long sumOrderQuantityMetaProductBasicMV(Map param);

	/**
	 * 供应商分析的销量总数
	 * @param param     p
	 * @return result
	 */
	Long sumAmountQuantityMetaProductBasicMV(Map param);

	/**
	 * 供应商分析销售总额
	 * @param param   p
	 * @return result
	 */

	Long sumAmountMetaProductBasicMV(Map param);

	/**
	 * 获取供应商对象
	 * @param param     p
	 * @return result
	 */
	List<MetaProductBasicMV> queryMetaProductBasicMV(Map param,boolean isForReportExport);

	/**
	 * 总条数
	 * @param param       p
	 * @return result
	 */
	Long countMetaProductBasicMV(Map param);

	/**
	 * 供应商排行分析对象列表集合
	 * @param param   p
	 * @return result
	 */
	List<SupportRankAnalysisVo> querySupportRankAnalysis(Map param,boolean isForReportExport);

	/**
	 * 供应商排行分析对象总数
	 * @param param     p
	 * @return result
	 */
	Long countSupportRankAnalysis(Map param);

	/**
	 * 供应商排行分析下人数总数
	 * @param param p
	 * @return result
	 */
	Long sumPersonQuantity(Map param);

	/**
	 * 供应商排行分析下房间总数
	 * @param param    p
	 * @return result
	 */
	Long sumRoomQuantity(Map param);

	/**
	 * 供应商排行分析下套总数
	 * @param param     p
	 * @return result
	 */
	Long sumSuitQuantity(Map param);

	/**
	 * 供应商排行分析下订单总数
	 * @param param  p
	 * @return result
	 */
	Long sumOrderQuantity(Map param);

	/***
	 * 订单转化的对象的列表集合
	 * @param param       参数
	 * @return OrderTransformBasiceMV列表的集合
	 */
	List<OrderTransformBasiceMV> queryOrderTransformBasicMV(
			Map<String, Object> param,boolean isForReportExport);

	/***
	 * 获取前台已预订的总数
	 * @param param  参数
	 * @return 前台已预订的总数
	 */
	Long sumFOrderQuantity(Map<String, Object> param);

	/**
	 * 获取前台已支付的总数
	 * @param param    参数
	 * @return 前台已支付的总数
	 */
	Long sumFPayedOrderQuantity(Map<String, Object> param);

	/***
	 * 获取后台已预订的总数
	 * @param param      参数
	 * @return 后台已预订的总数
	 */
	Long sumBOrderQuantity(Map<String, Object> param);

	/**
	 * 获取后台已支付的总数
	 * @param param  参数
	 * @return 后台已支付的总数
	 */
	Long sumBPayedOrderQuantity(Map<String, Object> param);

	/**
	 * 获取总条数
	 * @param param 参数
	 * @return 获取总条数
	 */
	Long countOrderTransformQuantity(Map<String, Object> param);

	/***
	 * 产品转化的对象的列表集合
	 * @param param 参数
	 * @return 产品转化的对象的列表集合
	 */
	List<ProductTransformBasicMV> queryProductTransformBasicMV(
			Map<String, Object> param,boolean isForReportExport);

	/**
	 * 获取产品转化总条数
	 * @param param 参数
	 * @return 获取产品转化总条数
	 */

	Long countProductTransformQuantity(Map<String, Object> param);

	/***
	 *  获取产品已预订的总数
	 * @param param 参数
	 * @return  获取产品已预订的总数
	 */
	Long sumProductQuantity(Map<String, Object> param);

	/**
	 * 获取产品已支付的总数
	 * @param param 参数
	 * @return 获取产品已支付的总数
	 */
	Long sumPayedProductQuantity(Map<String, Object> param);

	/**
	 * 获取电话总数
	 * @param param 参数
	 * @return 获取电话总数
	 */
	Long sumCallQuantity(Map<String, Object> param);
	/**
	 * 获取产品预订转化率总数
	 * @param param 参数
	 * @return 获取产品预订转化率总数
	 */
	Double avgProductConvert(Map<String, Object> param);

	/**
	 * 获取产品支付转化率总数
	 * @param param 参数
	 * @return 获取产品支付转化率总数
	 */
	Double avgPayedConvert(Map<String, Object> param);

	/***
	 * 查询游客信息的列表的集合
	 * @param param 参数
	 * @return 游客信息的列表的集合
	 */
	List<VistorDetailBasicMV> queryVistorDetailBasicMV(Map<String, Object> param,boolean isForReportExport);

	/***
	 * 查询总条数
	 * @param param 参数
	 * @return 总条数
	 */
	Long countVistorDetailBasicMV(final Map<String, Object> param);
}
