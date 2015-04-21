package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProdTime;
import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrdOrderItemMetaTimeDAO;
import com.lvmama.order.dao.OrdOrderItemProdTimeDAO;
import com.lvmama.order.service.IProductOrder;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdJourneyProductDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.logic.ProductResourceConfirmLogic;
import com.lvmama.prd.logic.ProductTimePriceLogic;

/**
 * 酒店销售产品订单创建服务.
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 * @see com.lvmama.ord.po.OrdOrderItemMeta
 * @see com.lvmama.ord.po.OrdOrderItemMetaTime
 * @see com.lvmama.ord.po.OrdOrderItemProd
 * @see com.lvmama.ord.po.OrdOrderItemProdTime
 * @see com.lvmama.ord.service.po.BuyInfo.OrdTimeInfo
 * @see com.lvmama.prd.po.TimePrice
 * @see com.lvmama.vo.Constant
 * @see com.lvmama.order.po.OrderInfoDTO
 * 
 */
public final class HotelBuildImpl implements IProductOrder {
	/**
	 * 销售产品订单子项时间价格DAO.
	 */
	private OrdOrderItemProdTimeDAO ordOrderItemProdTimeDAO;
	/**
	 * 采购产品订单子项时间价格DAO.
	 */
	private OrdOrderItemMetaTimeDAO ordOrderItemMetaTimeDAO;
	/**
	 * 销售产品时间价格DAO.
	 */
	private ProdTimePriceDAO prodTimePriceDAO;
	/**
	 * 采购产品时间价格DAO.
	 */
	private MetaTimePriceDAO metaTimePriceDAO;
	/**
	 * 产品时间价格服务.
	 */
	private ProductTimePriceLogic productTimePriceLogic;
	/**
	 * 产品资源确认服务.
	 */
	private ProductResourceConfirmLogic productResourceConfirmLogic;
	/**
	 * 采购产品记录DAO.
	 */
	private MetaProductDAO metaProductDAO;
	
	private MetaProductBranchDAO metaProductBranchDAO;
	
	private ProdJourneyProductDAO prodJourneyProductDAO;
	/**
	 * 修改销售产品订单子项.
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项{@link OrdOrderItemProd}
	 * @return 销售产品订单子项{@link OrdOrderItemProd}
	 */
	@Override
	public OrdOrderItemProd modifyOrderInfo(final OrdOrder order,OrdOrderItemProd ordOrderItemProd) {
		// 销售产品订单子项子类型为单房型
		// 酒店单房型对于价格的计算有特殊规则
		// 特殊处理ordOrderItemProd
		// 特殊处理OrdOrderItemMeta
		if (ordOrderItemProd.getSubProductType().equals(
				Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name())) {
			ordOrderItemProd = makeOrdOrderItemProd(order,ordOrderItemProd);
		}else{//非单房型酒店直接计总价
			ordOrderItemProd.setOughtPay(ordOrderItemProd.getPrice()*ordOrderItemProd.getQuantity());
		}
		return ordOrderItemProd;
	}

	/**
	 * 构建销售产品订单子项.
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项{@link OrdOrderItemProd}
	 * @return 销售产品订单子项{@link OrdOrderItemProd}
	 */
	private OrdOrderItemProd makeOrdOrderItemProd(final OrdOrder order,
			OrdOrderItemProd ordOrderItemProd) {
		if (UtilityTool.isNotNull(ordOrderItemProd.getTimeInfoList())) {
			// 构建销售产品订单子项价格
			ordOrderItemProd = makeOrdOrderItemProdPrice(order,ordOrderItemProd);
			// 构建采购产品订单子项列表
			final List<OrdOrderItemMeta> orderItemMetaList = makeOrdOrderItemMetaListList(ordOrderItemProd);
			ordOrderItemProd.setOrdOrderItemMetas(orderItemMetaList);
			// 构造资源确认
			ordOrderItemProd.setResourceStatus(String
					.valueOf(makeResourceConfirm(ordOrderItemProd)));
		}
		return ordOrderItemProd;
	}

	/**
	 * 构造资源确认.
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项{@link OrdOrderItemProd}
	 * @return true代表构造资源确认，false代表构造资源未确认
	 */
	private boolean makeResourceConfirm(final OrdOrderItemProd ordOrderItemProd) {
		// 订单资源资源状态
		boolean orderResourceConfirm = false;
		for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemProd
				.getOrdOrderItemMetas()) {
			// 采购产品订单子项资源状态
			boolean resourceConfirm = false;
			for (OrdTimeInfo ordTimeInfo : ordOrderItemProd.getTimeInfoList()) {
				// 采购产品
				final MetaProductBranch metaProduct = metaProductBranchDAO.selectBrachByPrimaryKey(ordOrderItemMeta.getMetaBranchId());
				resourceConfirm = productResourceConfirmLogic
						.isResourceConfirm(metaProduct,
								ordTimeInfo.getVisitTime());
				if (resourceConfirm) {
					orderResourceConfirm = resourceConfirm;
					break;
				}
			}
			// 资源确认状态
			ordOrderItemMeta
					.setResourceConfirm(String.valueOf(resourceConfirm));
			// 资源审核状态
			ordOrderItemMeta.setResourceStatus(BuildOrderInfoDTO
					.makeResourceConfirmStatus(resourceConfirm));
		}
		return orderResourceConfirm;
	}

	/**
	 * 构建采购产品订单子项列表.
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项{@link OrdOrderItemMeta}
	 * @return 采购产品订单子项{@link OrdOrderItemMeta}列表
	 */
	private List<OrdOrderItemMeta> makeOrdOrderItemMetaListList(
			final OrdOrderItemProd ordOrderItemProd) {
		final List<OrdOrderItemMeta> ordOrderItemMetaList = new ArrayList<OrdOrderItemMeta>();
		for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemProd
				.getOrdOrderItemMetas()) {
			// 构建采购产品订单子项价格
			ordOrderItemMeta = makeOrdOrderItemMetaPrice(ordOrderItemProd,
					ordOrderItemProd.getTimeInfoList(), ordOrderItemMeta);
			ordOrderItemMetaList.add(ordOrderItemMeta);
		}
		return ordOrderItemMetaList;
	}

	/**
	 * 构建采购产品订单子项价格.
	 * 
	 * <pre>
	 * 计算酒店单房型预订时间区间的合计结算价.
	 * 关于for循环的解释：
	 * 1.算出产品总数量
	 * 2.（市场OR结算）总价格   =  （市场OR结算）价   *  产品数量   *  份数
	 * 3.（市场OR结算）平均价格   = （市场OR结算）总价格  / 产品总数量
	 * </pre>
	 * 
	 * @param timeInfoList
	 *            {@link OrdTimeInfo}列表
	 * @param ordOrderItemMeta
	 *            采购产品订单子项
	 * @return 采购产品订单子项{@link OrdOrderItemMeta}
	 */
	private OrdOrderItemMeta makeOrdOrderItemMetaPrice(final OrdOrderItemProd ordOrderItemProd,
			final List<OrdTimeInfo> timeInfoList,
			final OrdOrderItemMeta ordOrderItemMeta) {
		// 总产品数量
		Long sumQuantity = 0L;
		// 总市场价
		Long sumMarketPrice = 0L;
		// 总结算价
		Long sumSettlementPrice = 0L;
		// 早餐数
		Long breakfastCount = 0L;
		for (OrdTimeInfo ordTimeInfo : timeInfoList) {
			// 数量累加
			sumQuantity += ordTimeInfo.getQuantity()*ordOrderItemMeta.getProductQuantity();
			final TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(ordOrderItemMeta.getMetaBranchId(), ordTimeInfo.getVisitTime());
			// 市场价累加
			sumMarketPrice += timePrice.getMarketPrice()
					* ordTimeInfo.getQuantity()
					* ordOrderItemMeta.getProductQuantity();
			// 结算价累加
			sumSettlementPrice += timePrice.getSettlementPrice()
					* ordTimeInfo.getQuantity()
					* ordOrderItemMeta.getProductQuantity();
			breakfastCount += timePrice.getBreakfastCount() == null ? 0 : timePrice.getBreakfastCount();
		}
		// 平均市场价
		Long avgMarketPrice = sumMarketPrice / sumQuantity;
		// 平均结算价
		Long avgSettlementPrice = sumSettlementPrice / sumQuantity;
		ordOrderItemMeta.setMarketPrice(avgMarketPrice);
		ordOrderItemMeta.setSettlementPrice(avgSettlementPrice);
		ordOrderItemMeta.setActualSettlementPrice(avgSettlementPrice);
		ordOrderItemMeta.setBreakfastCount(breakfastCount);
		ordOrderItemMeta.setTotalSettlementPrice(sumSettlementPrice);
		return ordOrderItemMeta;
	}

	/**
	 * 构建销售产品订单子项价格.
	 * 
	 * <pre>
	 * 计算酒店单房型预订时间区间内价格:销售价、市场价.
	 * 关于for循环的解释：
	 * 1.算出产品总数量
	 * 2.（市场OR销售）总价格   =  （市场OR销售）价   *  产品数量
	 * 3.（市场OR销售）平均价格   = （市场OR销售）总价格  / 产品总数量
	 * </pre>
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项
	 * @return 销售产品订单子项{@link OrdOrderItemProd}
	 */
	private OrdOrderItemProd makeOrdOrderItemProdPrice(final OrdOrder order,
			final OrdOrderItemProd ordOrderItemProd) {
		// 总产品数量
		Long sumQuantity = 0L;
		// 总市场价
		Long sumMarketPrice = 0L;
		// 总销售价
		Long sumPrice = 0L;
		
		//优惠金额
		Long discount=0L;
		//如果是超级自由行,酒店在此重新读取过单价，所以重新计算
		if(order.hasSelfPack()&&ordOrderItemProd.getJourneyProductId()!=null){
			ProdJourneyProduct pjp=prodJourneyProductDAO.selectByPrimaryKey(ordOrderItemProd.getJourneyProductId());
			if(pjp!=null){
				discount=pjp.getDiscount();
			}
		}
		
		for (OrdTimeInfo ordTimeInfo : ordOrderItemProd.getTimeInfoList()) {
			// 数量累加
			sumQuantity += ordTimeInfo.getQuantity();
//			final Long marketPrice = productTimePriceLogic
//					.selectMarkPriceForProdByDate(
//							ordOrderItemProd.getProductId(),
//							ordTimeInfo.getVisitTime());
			final TimePrice timePrice=productTimePriceLogic.calcProdTimePrice(ordOrderItemProd.getProdBranchId(), ordTimeInfo.getVisitTime(), false);
			// 市场价累加
			sumMarketPrice += timePrice.getMarketPrice() * ordTimeInfo.getQuantity();			
			// 销售价累加
			sumPrice += (timePrice.getPrice()-discount) * ordTimeInfo.getQuantity();
		}
		// 平均市场价
		Long avgMarketPrice = sumMarketPrice / sumQuantity;
		// 平均销售价
		Long avgPrice = sumPrice / sumQuantity;
		ordOrderItemProd.setMarketPrice(avgMarketPrice);
		ordOrderItemProd.setPrice(avgPrice);

		// 强制覆盖订单应付总金额
		ordOrderItemProd.setHotelOughtPay(sumPrice);

		return ordOrderItemProd;
	}

	/**
	 * 保存额外数据.<br>
	 * 需要进行额外CRUD操作时所要实现的方法
	 * 
	 * @param ordOrderItemProd
	 *            销售产品订单子项
	 * @return true代表保存额外数据成功，false代表保存额外数据失败
	 */
	@Override
	public boolean saveAdditionData(final OrdOrderItemProd ordOrderItemProd) {
		// 酒店单房型订单
		if (UtilityTool.isNotNull(ordOrderItemProd.getTimeInfoList())) {
			for (OrdTimeInfo ordTimeInfo : ordOrderItemProd.getTimeInfoList()) {
				// 跨多天的订单，只对应一条ordOrderItemProd
				// 每条ordOrderItemProd对应多条orderItemProdTime
				// 每条orderItemProdTime对应一条orderItemMetaTime

				// 构建销售产品订单子项时间价格
				final OrdOrderItemProdTime ordOrderItemProdTime = makeOrdOrderItemProdTime(
						ordTimeInfo, ordOrderItemProd.getOrderItemProdId());
				ordOrderItemProdTimeDAO.insert(ordOrderItemProdTime);
//				final OrdOrderItemMeta ordOrderItemMeta = ordOrderItemProd
//						.getOrdOrderItemMetas().get(0);
				for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemProd.getOrdOrderItemMetas()) {
					// 构建采购产品订单子项时间价格
					final OrdOrderItemMetaTime ordOrderItemMetaTime = makeOrdOrderItemMetaTime(
							ordTimeInfo, ordOrderItemMeta);
					ordOrderItemMetaTimeDAO.insert(ordOrderItemMetaTime);
				}
				
			}
		}
		return true;
	}

	/**
	 * 构建销售产品订单子项时间价格.
	 * 
	 * @param ordTimeInfo
	 *            {@link ordTimeInfo}
	 * @param orderItemProdId
	 *            销售产品订单子项ID
	 * @return 销售产品订单子项时间价格{@link OrdOrderItemProdTime}
	 */
	private OrdOrderItemProdTime makeOrdOrderItemProdTime(
			final OrdTimeInfo ordTimeInfo, final Long orderItemProdId) {
		final OrdOrderItemProdTime orderItemProdTime = new OrdOrderItemProdTime();
		orderItemProdTime.setOrderItemProdId(orderItemProdId);
		orderItemProdTime.setVisitTime(ordTimeInfo.getVisitTime());
		orderItemProdTime.setQuantity(ordTimeInfo.getQuantity());
		
		final TimePrice timePrice=prodTimePriceDAO.getProdTimePrice(ordTimeInfo.getProductId(), ordTimeInfo.getProductBranchId(), ordTimeInfo.getVisitTime());		
		orderItemProdTime.setPrice(timePrice.getPrice());
		return orderItemProdTime;
	}

	/**
	 * 构建采购产品订单子项时间价格.
	 * 
	 * @param ordTimeInfo
	 *            {@link ordTimeInfo}
	 * @param ordOrderItemMeta
	 *            采购产品订单子项
	 * @return 采购产品订单子项时间价格{@link OrdOrderItemMetaTime}
	 */
	private OrdOrderItemMetaTime makeOrdOrderItemMetaTime(
			final OrdTimeInfo ordTimeInfo,
			final OrdOrderItemMeta ordOrderItemMeta) {
		final OrdOrderItemMetaTime orderItemMetaTime = new OrdOrderItemMetaTime();
		final TimePrice timePrice = metaTimePriceDAO
				.getMetaTimePriceByIdAndDate(
						ordOrderItemMeta.getMetaBranchId(),
						ordTimeInfo.getVisitTime());
		orderItemMetaTime.setMarketPrice(timePrice.getMarketPrice());
		orderItemMetaTime.setSettlementPrice(timePrice.getSettlementPrice());
		orderItemMetaTime.setVisitTime(ordTimeInfo.getVisitTime());
		orderItemMetaTime.setQuatity(ordTimeInfo.getQuantity());
		orderItemMetaTime.setOrderItemMetaId(ordOrderItemMeta.getOrderItemMetaId());
		//建议售价
		orderItemMetaTime.setSuggestPrice(timePrice.getSuggestPrice());
		//早餐份数
		orderItemMetaTime.setBreakfastCount(timePrice.getBreakfastCount());
		
		return orderItemMetaTime;
	}

	/**
	 * 修改采购产品订单子项.
	 * 
	 * @param ordOrderItemMeta
	 *            采购产品订单子项
	 * @return 采购产品订单子项{@link OrdOrderItemMeta}
	 */
	@Override
	public OrdOrderItemMeta modifyOrdOrderItemMeta(
			final OrdOrderItemMeta ordOrderItemMeta) {
		return ordOrderItemMeta;
	}

	/**
	 * setOrdOrderItemProdTimeDAO.
	 * 
	 * @param ordOrderItemProdTimeDAO
	 *            销售产品订单子项时间价格DAO
	 */
	public void setOrdOrderItemProdTimeDAO(
			final OrdOrderItemProdTimeDAO ordOrderItemProdTimeDAO) {
		this.ordOrderItemProdTimeDAO = ordOrderItemProdTimeDAO;
	}

	/**
	 * setOrdOrderItemMetaTimeDAO.
	 * 
	 * @param ordOrderItemMetaTimeDAO
	 *            采购产品订单子项时间价格DAO
	 */
	public void setOrdOrderItemMetaTimeDAO(
			final OrdOrderItemMetaTimeDAO ordOrderItemMetaTimeDAO) {
		this.ordOrderItemMetaTimeDAO = ordOrderItemMetaTimeDAO;
	}

	/**
	 * setMetaTimePriceDAO.
	 * 
	 * @param metaTimePriceDAO
	 *            采购产品时间价格DAO
	 */
	public void setMetaTimePriceDAO(final MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	/**
	 * setProductTimePriceLogic.
	 * 
	 * @param productTimePriceLogic
	 *            产品时间价格服务
	 */
	public void setProductTimePriceLogic(
			final ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	/**
	 * setProdTimePriceDAO.
	 * 
	 * @param prodTimePriceDAO
	 *            销售产品时间价格DAO.
	 */
	public void setProdTimePriceDAO(final ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}

	/**
	 * setProductResourceConfirmLogic.
	 * 
	 * @param productResourceConfirmLogic
	 *            产品资源确认服务
	 */
	public void setProductResourceConfirmLogic(
			final ProductResourceConfirmLogic productResourceConfirmLogic) {
		this.productResourceConfirmLogic = productResourceConfirmLogic;
	}

	/**
	 * setMetaProductDAO.
	 * 
	 * @param metaProductDAO
	 *            采购产品记录DAO
	 */
	public void setMetaProductDAO(final MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	/**
	 * @param prodJourneyProductDAO the prodJourneyProductDAO to set
	 */
	public void setProdJourneyProductDAO(ProdJourneyProductDAO prodJourneyProductDAO) {
		this.prodJourneyProductDAO = prodJourneyProductDAO;
	}
	
	
}
