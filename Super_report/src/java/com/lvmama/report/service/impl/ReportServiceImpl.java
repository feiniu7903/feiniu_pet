package com.lvmama.report.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.report.dao.ChannelBasicMVDAO;
import com.lvmama.report.dao.MetaProductBasicMVDAO;
import com.lvmama.report.dao.OrderBasicMVDAO;
import com.lvmama.report.dao.OrderCustomerBasicMVDAO;
import com.lvmama.report.dao.OrderSaleServiceBasicMVDAO;
import com.lvmama.report.dao.OrderTransformBasicMVDAO;
import com.lvmama.report.dao.ProdProductBasicMVDAO;
import com.lvmama.report.dao.UserRegisterBasicDAO;
import com.lvmama.report.dao.UserRegisterBasicMVDAO;
import com.lvmama.report.dao.VistorDetailBasicMVDAO;
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
import com.lvmama.report.service.ReportService;
import com.lvmama.report.vo.OrderBasicVO;
import com.lvmama.report.vo.SupportRankAnalysisVo;

/**
 *销售的业务实现类
 * @author luoyinqi
 */
public class ReportServiceImpl implements ReportService {
	/**
	 * 游客信息
	 */
	private VistorDetailBasicMVDAO vistorDetailBasicMVDAO;
	/**
	 * 订单转化
	 */
	private OrderTransformBasicMVDAO orderTransformBasicMVDAO;

	/**
	 * 销售分析
	 */
	private ProdProductBasicMVDAO prodProductBasicMVDAO;
	/**
	 * 供应商分析
	 */
	private MetaProductBasicMVDAO metaProductBasicMVDAO;
	/**
	 * 订单分析
	 */
	private OrderBasicMVDAO orderBasicMVDAO;
	/**
	 * 频道
	 */
	private ChannelBasicMVDAO channelBasicMVDAO;
	/**
	 * 注册MV
	 */
	private UserRegisterBasicMVDAO userRegisterBasicMVDAO;
	/**
	 * 注册
	 */
	private UserRegisterBasicDAO userRegisterBasicDAO;
	/**
	 * 订单销售
	 */
	private OrderSaleServiceBasicMVDAO orderSaleServiceBasicMVDAO;
	/**
	 * 订单用户分析
	 */
	private OrderCustomerBasicMVDAO orderCustomerBasicMVDAO;

	/**
	 * @param param p
	 * @return result
	 */
	public List<ProdProductBasicMV> queryProdProductBasicMVByTime(final Map param,boolean isForReportExport) {
		return prodProductBasicMVDAO.queryProdProductBasicMVByTime(param,isForReportExport);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long countProdProductBasicMVByTime(final Map param) {
		return prodProductBasicMVDAO.countProdProductBasicMVByTime(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long countChannelBasicMV(final Map param,boolean isForReportExport) {
		return channelBasicMVDAO.countChannelBasicMV(param,isForReportExport);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public List<ChannelBasicMV> queryChannelBasicMV(final Map param,boolean isForReportExport) {
		return channelBasicMVDAO.queryChannelBasicMV(param,isForReportExport);
	}

	/**
	 * @param param p
	 * @return result
	 */
	public Long sumAmontProdProductBasicMV(final Map param) {
		return this.prodProductBasicMVDAO.sumAmontProdProductBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumDestAmontProdProductBasicMV(final Map param) {
		return this.prodProductBasicMVDAO.sumDestAmontProdProductBasicMV(param);
	}
	/**
	 * @return result
	 */
	public Long sumDestProdProductBasicMV() {
		return this.prodProductBasicMVDAO.sumDestProdProductBasicMV();
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumUserUpdate(final Map param) {
		return this.userRegisterBasicDAO.countNewUser(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumPayUserUpdate(final Map param) {
		return this.userRegisterBasicMVDAO.sumPayUserUpdate(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumPay2UserUpdate(final Map param) {
		return this.userRegisterBasicMVDAO.sumPay2UserUpdate(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long allUserUpdate(final Map param) {
		return this.userRegisterBasicMVDAO.allUserUpdate(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long allPayUserUpdate(final Map param) {
		return this.userRegisterBasicMVDAO.allPayUserUpdate(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long allPay2UserUpdate(final Map param) {
		return this.userRegisterBasicMVDAO.allPay2UserUpdate(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumRefundAmountOrderSaleServiceBasicMV(final Map param) {
		return this.orderSaleServiceBasicMVDAO
				.sumRefundAmountOrderSaleServiceBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumCompensationAmountOrderSaleServiceBasicMV(final Map param) {
		return this.orderSaleServiceBasicMVDAO
				.sumCompensationAmountOrderSaleServiceBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumAmountChannelBasicMV(final Map param) {
		return this.channelBasicMVDAO.sumAmountChannelBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumProfitChannelBasicMV(final Map param) {
		return this.channelBasicMVDAO.sumProfitChannelBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public List<UserRegisterBasicMV> queryUserOverviewRegisterBasicMV(final Map param,boolean isForReportExport) {
		return this.userRegisterBasicMVDAO
				.queryUserOverviewRegisterBasicMV(param,isForReportExport);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long countUserOverviewRegisterBasicMV(final Map param) {
		return this.userRegisterBasicMVDAO
				.countUserOverviewRegisterBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumProdAmontProdProductBasicMV(final Map param) {
		return this.prodProductBasicMVDAO.sumProdAmontProdProductBasicMV(param);
	}
	/**
	 * @param p
	 * @return result
	 */
	public Long sumProdProductBasicMV() {
		return this.prodProductBasicMVDAO.sumProdProductBasicMV();
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long countMetaProductBasicMV(final Map param) {
		return this.metaProductBasicMVDAO.countMetaProductBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long countUserRegisterBasic(final Map param) {
		return this.userRegisterBasicDAO.countUserRegisterBasic(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumOrderQuantityProdProductBasicMV(final Map param) {
		return this.prodProductBasicMVDAO
				.sumOrderQuantityProdProductBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumAmountQuantityProdProductBasicMV(final Map param) {
		return this.prodProductBasicMVDAO
				.sumAmountQuantityProdProductBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumPerfitProdProductBasicMV(final Map param) {
		return this.prodProductBasicMVDAO.sumPerfitProdProductBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public List<UserRegisterBasicMV> queryUserRegisterBasicMV(final Map param) {
		return this.userRegisterBasicMVDAO.queryUserRegisterBasicMV(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public List<UserRegisterBasic> queryUserRegisterBasic(final Map param,boolean isForReportExport) {
		return this.userRegisterBasicDAO.queryUserRegisterBasic(param,isForReportExport);
	}
	
	/**
	 * @param param p
	 * @return result
	 */
	public Long countOrderSaleServiceBasicMV(final Map param) {
		return orderSaleServiceBasicMVDAO.countOrderSaleServiceBasicMV(param);
	}

	/**
	 * @param param p
	 * @return result
	 */
	public List<OrderSaleServiceBasicMV> queryOrderSaleServiceBasicMV(final Map param,boolean isForReportExport) {
		return orderSaleServiceBasicMVDAO.queryOrderSaleServiceBasicMV(param,isForReportExport);
	}
	
	/**
	 * @param param p
	 * @return result
	 */
	@SuppressWarnings("rawtypes")
	public List<OrderCustomerBasicMV> queryOrderCustomerBasicMVByTime(final Map param,boolean isForReportExport) {
		return orderCustomerBasicMVDAO.queryOrderCustomerBasicMVByTime(param, isForReportExport);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long countOrderCustomerBasicMVByTime(final Map param) {
		return this.orderCustomerBasicMVDAO
				.countOrderCustomerBasicMVByTime(param);
	}
	/**
	 * @param param p
	 * @return result
	 */
	public Long sumAmountOrderCustomerBasicMV(final Map param) {
		return this.orderCustomerBasicMVDAO
				.sumAmountOrderCustomerBasicMV(param);
	}

	/**
	 * 计算毛利率
	 * @param param p
	 * @return result
	 */
	public Long sumProfitOrderCustomerBasicMV(final Map param) {
		return this.orderCustomerBasicMVDAO
				.sumProfitOrderCustomerBasicMV(param);
	}

	/**
	 * 查询订单分析OrderBasicMV记录
	 * @param param p
	 * @return result
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OrderBasicMV> queryOrderBasicMVByParam(final Map param,boolean isForReportExport) {
		return orderBasicMVDAO.queryOrderBasicMVByParam(param,isForReportExport);
	}

	/**
	 * 查询记录总数
	 * @param param p
	 * @return result
	 */
	@SuppressWarnings("rawtypes")
	public Long countOfOrderBasicMVByParam(final Map param) {
		return orderBasicMVDAO.countOfOrderBasicMVByParam(param);
	}

	/**
	 * 获取订单统计数据：OrderBasicVO总额和数量
	 * @param param p
	 * @return result
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OrderBasicVO sumOrderBasicVOByParam(final Map param,boolean isForReportExport) {
		List<OrderBasicVO> list = orderBasicMVDAO.sumOrderBasicVOByParam(param,isForReportExport);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * * * * * * * * * * * * * set and get property * * * * * * * *
	 * @return result
	 * */
	public OrderTransformBasicMVDAO getOrderTransformBasicMVDAO() {
		return orderTransformBasicMVDAO;
	}
	public void setOrderTransformBasicMVDAO(
			OrderTransformBasicMVDAO orderTransformBasicMVDAO) {
		this.orderTransformBasicMVDAO = orderTransformBasicMVDAO;
	}

	public OrderBasicMVDAO getOrderBasicMVDAO() {
		return orderBasicMVDAO;
	}

	public ChannelBasicMVDAO getChannelBasicMVDAO() {
		return channelBasicMVDAO;
	}

	public OrderSaleServiceBasicMVDAO getOrderSaleServiceBasicMVDAO() {
		return orderSaleServiceBasicMVDAO;
	}

	public void setOrderSaleServiceBasicMVDAO(
			final OrderSaleServiceBasicMVDAO orderSaleServiceBasicMVDAO) {
		this.orderSaleServiceBasicMVDAO = orderSaleServiceBasicMVDAO;
	}

	public void setChannelBasicMVDAO(final ChannelBasicMVDAO channelBasicMVDAO) {
		this.channelBasicMVDAO = channelBasicMVDAO;
	}

	public void setOrderBasicMVDAO(final OrderBasicMVDAO orderBasicMVDAO) {
		this.orderBasicMVDAO = orderBasicMVDAO;
	}

	public ProdProductBasicMVDAO getProdProductBasicMVDAO() {
		return prodProductBasicMVDAO;
	}

	public void setProdProductBasicMVDAO(
			final ProdProductBasicMVDAO prodProductBasicMVDAO) {
		this.prodProductBasicMVDAO = prodProductBasicMVDAO;
	}

	public MetaProductBasicMVDAO getMetaProductBasicMVDAO() {
		return metaProductBasicMVDAO;
	}

	public void setMetaProductBasicMVDAO(
			final MetaProductBasicMVDAO metaProductBasicMVDAO) {
		this.metaProductBasicMVDAO = metaProductBasicMVDAO;
	}

	public UserRegisterBasicMVDAO getUserRegisterBasicMVDAO() {
		return userRegisterBasicMVDAO;
	}

	public void setUserRegisterBasicMVDAO(
			final UserRegisterBasicMVDAO userRegisterBasicMVDAO) {
		this.userRegisterBasicMVDAO = userRegisterBasicMVDAO;
	}

	@Override
	public Long sumProdProductOnlineBasicMV() {
		return this.prodProductBasicMVDAO.sumProdProductOnlineBasicMV();
	}

	public UserRegisterBasicDAO getUserRegisterBasicDAO() {
		return userRegisterBasicDAO;
	}

	public void setUserRegisterBasicDAO(
			final UserRegisterBasicDAO userRegisterBasicDAO) {
		this.userRegisterBasicDAO = userRegisterBasicDAO;
	}

	public VistorDetailBasicMVDAO getVistorDetailBasicMVDAO() {
		return vistorDetailBasicMVDAO;
	}
	public void setVistorDetailBasicMVDAO(
			VistorDetailBasicMVDAO vistorDetailBasicMVDAO) {
		this.vistorDetailBasicMVDAO = vistorDetailBasicMVDAO;
	}
	@Override
	public List<LoscOrderStatisticsMV> queryLoscOrderStatisticsList(final Map param,boolean isForReportExport) {
		return channelBasicMVDAO.loscOrderStatisticsList(param,isForReportExport);
	}

	@Override
	public Long loscOrderStatisticsCount(final Map param) {
		return channelBasicMVDAO.loscOrderStatisticsCount(param);
	}

	/**
	 * 供应商分析对象的列表集合
	 * @param param p
	 * @return result
	 */
	public List<MetaProductBasicMV> queryMetaProductBasicMV(final Map param,boolean isForReportExport) {
		return this.metaProductBasicMVDAO.queryMetaProductBasicMV(param,isForReportExport);
	}

	public void setOrderCustomerBasicMVDAO(
			final OrderCustomerBasicMVDAO orderCustomerBasicMVDAO) {
		this.orderCustomerBasicMVDAO = orderCustomerBasicMVDAO;
	}


	public Long sumOrderQuantityMetaProductBasicMV(final Map param) {
		return metaProductBasicMVDAO.sumOrderQuantityMetaProductBasicMV(param);
	}

	public Long sumAmountQuantityMetaProductBasicMV(final Map param) {
		return metaProductBasicMVDAO.sumAmountQuantityMetaProductBasicMV(param);
	}

	public Long sumAmountMetaProductBasicMV(final Map param) {
		return metaProductBasicMVDAO.sumAmountProductBasicMV(param);
	}

	/**
	 * 供应商排行分析SupportRankAnalysisVo列表对象
	 * @param param p
	 * @return result
	 */
	public List<SupportRankAnalysisVo> querySupportRankAnalysis(final Map param,boolean isForReportExport) {
		return metaProductBasicMVDAO.querySupportRankAnalysis(param,isForReportExport);
	}

	/**
	 * 供应商排行分析总条数
	 * @param param p
	 * @return result
	 */
	public Long countSupportRankAnalysis(final Map param) {
		return metaProductBasicMVDAO.countSupportRankAnalysis(param);
	}

	/**
	 * 供应商排行分析下人数总数
	 * @param param p
	 * @return result
	 */
	public Long sumPersonQuantity(final Map param) {
		return metaProductBasicMVDAO.sumPersonQuantity(param);
	}

	/**
	 * 供应商排行分析下房间总数
	 * @param param p
	 * @return result
	 */
	public Long sumRoomQuantity(final Map param) {
		return metaProductBasicMVDAO.sumRoomQuantity(param);
	}

	/**
	 * 供应商排行分析下套总数
	 * @param param p
	 * @return result
	 */
	public Long sumSuitQuantity(final Map param) {
		return metaProductBasicMVDAO.sumSuitQuantity(param);
	}

	/**
	 * 供应商排行分析下订单总数
	 * @param param p
	 * @return result
	 */
	public Long sumOrderQuantity(final Map param) {
		return metaProductBasicMVDAO.sumOrderQuantity(param);
	}
	/***
	 * 订单转化的对象的列表集合
	 * @param param 参数
	 * @return OrderTransformBasiceMV列表的集合
	 */
	public List<OrderTransformBasiceMV> queryOrderTransformBasicMV(final Map<String, Object> param,boolean isForReportExport) {
		return orderTransformBasicMVDAO.queryOrderTransformBasicMV(param,isForReportExport);
	}
	/***
	 * 获取前台已预订的总数
	 * @param param 参数
	 * @return 前台已预订的总数
	 */
	public Long sumFOrderQuantity(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.sumFOrderQuantity(param);
	}
	/**
	 * 获取前台已支付的总数
	 * @param param 参数
	 * @return 前台已支付的总数
	 */
	public Long sumFPayedOrderQuantity(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.sumFPayedOrderQuantity(param);
	}
	/***
	 * 获取后台已预订的总数
	 * @param param 参数
	 * @return 后台已预订的总数
	 */
	public Long sumBOrderQuantity(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.sumBOrderQuantity(param);
	}

	/**
	 * 获取后台已支付的总数
	 * @param param 参数
	 * @return 后台已支付的总数
	 */
	public Long sumBPayedOrderQuantity(final Map<String, Object> param) {
		return	orderTransformBasicMVDAO.sumBPayedOrderQuantity(param);
	}

	/**
	 * 获取总条数
	 * @param param 参数
	 * @return  获取总条数
	 */
	public Long countOrderTransformQuantity(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.countOrderTransformQuantity(param);
	}

	/**
	 * 产品转化的对象的列表集合
	 * @param param 参数
	 * @return 产品转化的对象的列表集合
	 */
	public List<ProductTransformBasicMV> queryProductTransformBasicMV(
			final Map<String, Object> param,boolean isForReportExport) {
		return orderTransformBasicMVDAO.queryProductTransformBasicMV(param,isForReportExport);
	}
	/**
	 * 产品转化的总条数
	 * @param param  参数
	 * @return 产品转化的总条数
	 */
	public Long countProductTransformQuantity(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.countProductTransformQuantity(param);
	}
	/**
	 * 产品已预订的数量
	 * @param param 参数
	 * @return 产品已预订的数量
	 */
	public Long sumProductQuantity(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.sumProductQuantity(param);
	}
	/**
	 * 产品支付的数量
	 * @param param 参数
	 * @return 产品支付的数量
	 */
	public Long sumPayedProductQuantity(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.sumPayedProductQuantity(param);
	}
	/***
	 * 电话总数
	 * @param param 参数
	 * @return 电话总数
	 */
	public Long sumCallQuantity(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.sumCallQuantity(param);
	}
	/**
	 * 平均预订转化率
	 * @param param 参数
	 * @return 平均预订转化率
	 */
	public Double avgProductConvert(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.avgProductConvert(param);
	}
	/**
	 * 平均支付转化率
	 * @param param 参数
	 * @return 平均支付转化率
	 * */
	public Double avgPayedConvert(final Map<String, Object> param) {
		return orderTransformBasicMVDAO.avgPayedConvert(param);
	}

	/***
	 * 查询游客信息的列表的集合
	 * @param param 参数
	 * @return 游客信息的列表的集合
	 */
	public List<VistorDetailBasicMV> queryVistorDetailBasicMV(
			final Map<String, Object> param,boolean isForReportExport) {
		return vistorDetailBasicMVDAO.queryVistorDetaiBasicMV(param, isForReportExport);
	}
	/***
	 * 查询总条数
	 * @param param 参数
	 * @return 总条数
	 */
	public Long countVistorDetailBasicMV(final Map<String, Object> param) {
		return vistorDetailBasicMVDAO.countVistorDetailBasicMV(param);
	}
}
