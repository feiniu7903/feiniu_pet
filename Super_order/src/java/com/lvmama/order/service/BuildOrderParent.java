package com.lvmama.order.service;

import com.lvmama.client.dao.ClientOrderReportDAO;
import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.mark.logic.FavorOrderServiceImpl;
import com.lvmama.ord.dao.OrdOrderChannelDAO;
import com.lvmama.order.dao.OrderAmountItemDAO;
import com.lvmama.order.dao.OrderAuditDAO;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderExpressDAO;
import com.lvmama.order.dao.OrderInvoiceDAO;
import com.lvmama.order.dao.OrderInvoiceRelationDAO;
import com.lvmama.order.dao.OrderItemMetaAperiodicDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.logic.BonusReturnLogic;
import com.lvmama.order.logic.ProductStockLogic;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdJourneyProductDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdProductItemDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.logic.ProductResourceConfirmLogic;
import com.lvmama.prd.logic.ProductTimePriceLogic;

/**
 * 订单创建服务实现类父类.
 * 
 * <pre>
 * 用来注入bean
 * </pre>
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 */
public class BuildOrderParent {
	/**
	 * 销售产品DAO.
	 */
	protected ProdProductDAO prodProductDAO;
	/**
	 * 销售分类产品DAO.
	 */
	protected ProdProductBranchDAO prodProductBranchDAO;
	/**
	 * 产品分支子项DAO
	 */
	protected ProdProductBranchItemDAO prodProductBranchItemDAO;
	/**
	 * 采购产品记录DAO.
	 */
	protected MetaProductDAO metaProductDAO;
	/**
	 * 采购产品分类记录DAO.
	 */
	protected MetaProductBranchDAO metaProductBranchDAO;
	/**
	 * 销售产品子项DAO.
	 */
	protected ProdProductItemDAO prodProductItemDAO;
	/**
	 * 订单金额明细DAO.
	 */
	protected OrderAmountItemDAO orderAmountItemDAO;
	/**
	 * 订单DAO.
	 */
	protected OrderDAO orderDAO;

	/**
	 * 订单快递DAO.
	 */
	protected OrderExpressDAO orderExpressDAO;
	/**
	 * 订单领单DAO.
	 */
	protected OrderAuditDAO orderAuditDAO;
	/**
	 * 订单游玩人DAO.
	 */
	protected OrderPersonDAO orderPersonDAO;
	/**
	 * 订单发票DAO.
	 */
	protected OrderInvoiceDAO orderInvoiceDAO;
	
	/**
	 * 订单与发票关联DAO
	 */
	protected OrderInvoiceRelationDAO orderInvoiceRelationDAO;
	/**
	 * 日志DAO.
	 */
	protected ComLogDAO comLogDAO;

	/**
	 * 销售产品订单子项DAO.
	 */
	protected OrderItemProdDAO orderItemProdDAO;

	/**
	 * 采购产品订单子项DAO.
	 */
	protected OrderItemMetaDAO orderItemMetaDAO;

	/**
	 * 采购产品时间价格DAO.
	 */
	protected MetaTimePriceDAO metaTimePriceDAO;
	/**
	 * 销售产品时间价格DAO.
	 */
	protected ProdTimePriceDAO prodTimePriceDAO;
	/**
	 * 订单的团信息Service.
	 */
	protected OrderRouteService orderRouteService;
	/**
	 * 订单更新服务.
	 */
	protected OrderUpdateService orderUpdateService;
	/**
	 * 产品库存服务.
	 */
	protected ProductStockLogic productStockLogic;
	
	/**
	 * 时间价格表服务
	 */
	protected ProductTimePriceLogic productTimePriceLogic;
	
	protected BonusReturnLogic bonusReturnLogic;
	
	/**
	 * 产品资源确认服务.
	 */
	protected ProductResourceConfirmLogic productResourceConfirmLogic;
	/**
	 * 创建产品订单工厂.
	 */
	protected IBuildOrderFactory buildOrderFactory;

	protected ProdJourneyProductDAO prodJourneyProductDAO;
	
	protected ClientOrderReportDAO clientOrderReportDAO;
	
	protected OrdOrderChannelDAO ordOrderChannelDAO;
	
	/**
	 * 优惠日志记录类
	 */
	protected FavorOrderService favorOrderService;
	
	/**
	 * 密码券
	 * */
	protected OrderItemMetaAperiodicDAO orderItemMetaAperiodicDAO;
	/**
	 * setProdProductDAO.
	 * 
	 * @param prodProductDAO
	 *            销售产品DAO
	 */
	public void setProdProductDAO(final ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	/**
	 * setProdProductBranchDAO.
	 * 
	 * @param prodProductBranchDAO
	 *            销售分类产品DAO
	 */
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	/**
	 * setProdProductBranchItemDAO
	 * 
	 * @param prodProductBranchItemDAO
	 * 				产品分支子项DAO
	 */
	public void setProdProductBranchItemDAO(
			ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
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

	/**
	 * setMetaProductBranchDAO.
	 * 
	 * @param metaProductBranchDAO
	 *            采购产品分类记录DAO
	 */
	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}
	
	/**
	 * setProdProductItemDAO.
	 * 
	 * @param prodProductItemDAO
	 *            销售产品子项DAO
	 */
	public void setProdProductItemDAO(
			final ProdProductItemDAO prodProductItemDAO) {
		this.prodProductItemDAO = prodProductItemDAO;
	}
 

	/**
	 * setOrderAmountItemDAO.
	 * 
	 * @param orderAmountItemDAO
	 *            订单金额明细DAO
	 */
	public void setOrderAmountItemDAO(
			final OrderAmountItemDAO orderAmountItemDAO) {
		this.orderAmountItemDAO = orderAmountItemDAO;
	}

	/**
	 * setOrderDAO.
	 * 
	 * @param orderDAO
	 *            订单DAO
	 */
	public void setOrderDAO(final OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	/**
	 * setOrderExpressDAO.
	 * 
	 * @param orderExpressDAO
	 *            订单快递DAO
	 */
	public void setOrderExpressDAO(final OrderExpressDAO orderExpressDAO) {
		this.orderExpressDAO = orderExpressDAO;
	}

	/**
	 * setOrderAuditDAO.
	 * 
	 * @param orderAuditDAO
	 *            订单领单DAO
	 */
	public void setOrderAuditDAO(final OrderAuditDAO orderAuditDAO) {
		this.orderAuditDAO = orderAuditDAO;
	}

	/**
	 * setOrderPersonDAO.
	 * 
	 * @param orderPersonDAO
	 *            订单游玩人DAO
	 */
	public void setOrderPersonDAO(final OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}

	/**
	 * setOrderInvoiceDAO.
	 * 
	 * @param orderInvoiceDAO
	 *            订单发票DAO
	 */
	public void setOrderInvoiceDAO(final OrderInvoiceDAO orderInvoiceDAO) {
		this.orderInvoiceDAO = orderInvoiceDAO;
	}

	/**
	 * setOrderUpdateService.
	 * 
	 * @param orderUpdateService
	 *            订单更新服务
	 */
	public void setOrderUpdateService(
			final OrderUpdateService orderUpdateService) {
		this.orderUpdateService = orderUpdateService;
	}


	/**
	 * setProductStockLogic.
	 * 
	 * @param productStockLogic
	 *            产品库存服务
	 */
	public void setProductStockLogic(final ProductStockLogic productStockLogic) {
		this.productStockLogic = productStockLogic;
	}

	/**
	 * 
	 * @param productTimePriceLogic
	 */
	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	/**
	 * setComLogDAO.
	 * 
	 * @param comLogDAO
	 *            日志DAO
	 */
	public void setComLogDAO(final ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	/**
	 * 设置订单团附加信息.
	 * 
	 * @param orderRouteService
	 *            订单团附加信息的Service.
	 */
	public void setOrderRouteService(OrderRouteService orderRouteService) {
		this.orderRouteService = orderRouteService;
	}

	/**
	 * setOrderItemProdDAO.
	 * 
	 * @param orderItemProdDAO
	 *            销售产品订单子项DAO
	 */
	public void setOrderItemProdDAO(final OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	/**
	 * setProdTimePriceDAO.
	 * 
	 * @param prodTimePriceDAO
	 *            销售产品时间价格DAO
	 */
	public void setProdTimePriceDAO(final ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
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
	 * setOrderItemMetaDAO.
	 * 
	 * @param orderItemMetaDAO
	 *            采购产品订单子项DAO
	 */
	public void setOrderItemMetaDAO(final OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	/**
	 * setBuildOrderFactory.
	 * 
	 * @param buildOrderFactory
	 *            创建产品订单工厂
	 */
	public void setBuildOrderFactory(final IBuildOrderFactory buildOrderFactory) {
		this.buildOrderFactory = buildOrderFactory;
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
	 * @param orderInvoiceRelationDAO the orderInvoiceRelationDAO to set
	 */
	public void setOrderInvoiceRelationDAO(
			OrderInvoiceRelationDAO orderInvoiceRelationDAO) {
		this.orderInvoiceRelationDAO = orderInvoiceRelationDAO;
	}
	
	/**
	 * @param prodJourneyProductDAO the prodJourneyProductDAO to set
	 */
	public void setProdJourneyProductDAO(ProdJourneyProductDAO prodJourneyProductDAO) {
		this.prodJourneyProductDAO = prodJourneyProductDAO;
	}

	public void setClientOrderReportDAO(ClientOrderReportDAO clientOrderReportDAO) {
		this.clientOrderReportDAO = clientOrderReportDAO;
	}

	public void setOrdOrderChannelDAO(OrdOrderChannelDAO ordOrderChannelDAO) {
		this.ordOrderChannelDAO = ordOrderChannelDAO;
	}

	public void setFavorOrderService(FavorOrderService favorOrderService) {
		this.favorOrderService = favorOrderService;
	}

	public void setOrderItemMetaAperiodicDAO(
			OrderItemMetaAperiodicDAO orderItemMetaAperiodicDAO) {
		this.orderItemMetaAperiodicDAO = orderItemMetaAperiodicDAO;
	}

	public void setBonusReturnLogic(BonusReturnLogic bonusReturnLogic) {
		this.bonusReturnLogic = bonusReturnLogic;
	}
	
	
}
