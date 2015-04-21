package com.lvmama.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.service.IBuildOrderFactory;
import com.lvmama.order.service.IProductOrder;

/**
 * 创建产品订单工厂实现类.
 * 
 * <pre>
 * </pre>
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 */
public final class BuildOrderFactory implements IBuildOrderFactory {
	/**
	 * 其他销售产品订单创建服务.
	 */
	private IProductOrder otherBuildService;
	/**
	 * 酒店销售产品订单创建服务.
	 */
	private IProductOrder hotelBuildService;
	/**
	 * 线路销售产品订单创建服务.
	 */
	private IProductOrder routeBuildService;
	/**
	 * 门票销售产品订单创建服务.
	 */
	private IProductOrder ticketBuildService;
	
	/**
	 * 交通销售产品订单创建服务.
	 */
	private IProductOrder trafficBuildService;

	/**
	 * 服务映射.
	 */
	private final Map<Constant.PRODUCT_TYPE, IProductOrder> serviceMap = new HashMap<Constant.PRODUCT_TYPE, IProductOrder>();

	/**
	 * 选择订单服务实现.
	 * 
	 * @param productType
	 *            销售产品类型
	 * @return 产品订单{@link IProductOrder}
	 */
	@Override
	public IProductOrder chooseOrderServiceImpl(final OrdOrderItemProd orderItemProd) {
		if (serviceMap.isEmpty()) {
			serviceMap.put(Constant.PRODUCT_TYPE.ROUTE, routeBuildService);
			serviceMap.put(Constant.PRODUCT_TYPE.HOTEL, hotelBuildService);
			serviceMap.put(Constant.PRODUCT_TYPE.TICKET, ticketBuildService);
			serviceMap.put(Constant.PRODUCT_TYPE.OTHER, otherBuildService);
			serviceMap.put(Constant.PRODUCT_TYPE.TRAFFIC, trafficBuildService);
		}
		//所有的附加产品统一走其他的接口
		if(orderItemProd.isAdditionalProduct()){
			return serviceMap.get(Constant.PRODUCT_TYPE.OTHER);
		}
		return serviceMap.get(Constant.PRODUCT_TYPE.valueOf(orderItemProd.getProductType()));
	}

	/**
	 * setOtherBuildService.
	 * 
	 * @param otherBuildService
	 *            其他销售产品订单创建服务
	 */
	public void setOtherBuildService(final IProductOrder otherBuildService) {
		this.otherBuildService = otherBuildService;
	}

	/**
	 * setHotelBuildService.
	 * 
	 * @param hotelBuildService
	 *            酒店销售产品订单创建服务
	 */
	public void setHotelBuildService(final IProductOrder hotelBuildService) {
		this.hotelBuildService = hotelBuildService;
	}

	/**
	 * setRouteBuildService.
	 * 
	 * @param routeBuildService
	 *            线路销售产品订单创建服务
	 */
	public void setRouteBuildService(final IProductOrder routeBuildService) {
		this.routeBuildService = routeBuildService;
	}

	/**
	 * setTicketBuildService.
	 * 
	 * @param ticketBuildService
	 *            门票销售产品订单创建服务
	 */
	public void setTicketBuildService(final IProductOrder ticketBuildService) {
		this.ticketBuildService = ticketBuildService;
	}

	public void setTrafficBuildService(IProductOrder trafficBuildService) {
		this.trafficBuildService = trafficBuildService;
	}
}
