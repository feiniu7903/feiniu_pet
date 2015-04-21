package com.lvmama.order.service.impl;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.service.IBuildOrder;
import com.lvmama.order.service.OrderCreateService;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建订单服务类.
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 * @see com.lvmama.ord.po.OrdOrder
 * @see com.lvmama.ord.service.po.BuyInfo
 * @see com.lvmama.order.service.IBuildOrder
 * @see com.lvmama.order.service.OrderCreateService
 * @see com.lvmama.order.service.IBuildOrderFactory
 */
public final class OrderBuildServiceImpl implements OrderCreateService {
	/**
	 * 订单创建服务.
	 */
	private IBuildOrder buildOrderService;
	private BCertificateTargetService bCertificateTargetService;
	private PerformTargetService performTargetService;
	/**
	 * 采购产品记录DAO.
	 */
	private MetaProductDAO metaProductDAO;
	/**
	 * 机票相关的place服务接口
	 */
	private PlaceFlightService placeFlightService;
	private MetaTimePriceDAO metaTimePriceDAO;
	/**
	 * 创建订单.
	 * 
	 * <pre>
	 * 前台下单时使用
	 * </pre>
	 * 
	 * @param buyInfo
	 *            购买信息
	 * 
	 * @return 创建的订单
	 */
	@Override
	public OrdOrder createOrder(final BuyInfo buyInfo) {
		return buildOrder(buyInfo, null, null);
	}

	/**
	 * 创建订单.
	 * 
	 * <pre>
	 * 后台下单时使用
	 * </pre>
	 * 
	 * @param buyInfo
	 *            购买信息
	 * @param operatorId
	 *            操作人ID
	 * 
	 * @return 创建的订单
	 */
	@Override
	public OrdOrder createOrder(final BuyInfo buyInfo, final String operatorId) {
		return buildOrder(buyInfo, null, operatorId);
	}

	/**
	 * 创建订单.
	 * 
	 * <pre>
	 * 后台废单重下时使用
	 * </pre>
	 * 
	 * @param buyInfo
	 *            购买信息
	 * @param orderId
	 *            原订单ID
	 * @param operatorId
	 *            操作人ID
	 * 
	 * @return 创建的订单
	 */
	@Override
	public OrdOrder createOrder(final BuyInfo buyInfo, final Long orderId,
			final String operatorId) {
		return buildOrder(buyInfo, orderId, operatorId);
	}

	/**
	 * 创建订单.
	 * 
	 * @param buyInfo
	 *            购买信息
	 * @param orderId
	 *            原订单ID 废单重下时使用
	 * @param operatorId
	 *            操作人
	 * @return OrdOrder{@link OrdOrder}
	 */
	private OrdOrder buildOrder(final BuyInfo buyInfo, final Long orderId,
			final String operatorId) {

        if(isKill(buyInfo)){
            throw new RuntimeException();
        }

		Map<Long,SupBCertificateTarget> bcertTargetMap = new HashMap<Long, SupBCertificateTarget>();
		Map<Long,List<SupPerformTarget>> performTargetMap = new HashMap<Long, List<SupPerformTarget>>();
		Map<Long, OrdOrderItemMeta> ordOrderItemMateMap=new HashMap<Long, OrdOrderItemMeta>();
		this.buildMetaProductId(buyInfo,bcertTargetMap,performTargetMap,ordOrderItemMateMap);
		return buildOrderService.buildOrderInfo(buyInfo, orderId,bcertTargetMap,performTargetMap,ordOrderItemMateMap, operatorId);
	}

    private boolean isKill(final BuyInfo buyInfo) {
        boolean isKill = false;

        for (Item item : buyInfo.getItemList()) {
            if ("true".equals(item.getIsDefault())) {
                String productId = (String) MemcachedUtil.getInstance().get("KILL_PRODUCT_" + item.getProductId());
                if (productId != null && productId.equals("Y")) {
                    isKill = true;
                }
            }
        }

        return isKill;
    }





	/**
	 * 构造订单时需要凭证对象和履行对象，避免service内部调用远程方法
	 * 此serviceImpl是代理类
	 * @param buyInfo
	 * @param bcertTargetMap
	 * @param performTargetMap
	 */
	private void buildMetaProductId(BuyInfo buyInfo,Map<Long,SupBCertificateTarget> bcertTargetMap,Map<Long,List<SupPerformTarget>> performTargetMap,Map<Long, OrdOrderItemMeta> ordOrderItemMateMap) {
		Map<Item,List<ProdProductBranchItem>> branchItemMap = this.buildOrderService.buildProductBranchItem(buyInfo);
		List<SupPerformTarget> performTargetList = null;
		List<SupBCertificateTarget> bcertTargetList = null;
		for(Item item:branchItemMap.keySet()){
			List<ProdProductBranchItem> branchItemList = branchItemMap.get(item);
			for (ProdProductBranchItem branchItem : branchItemList) {
				bcertTargetList = this.bCertificateTargetService.selectSuperMetaBCertificateByMetaProductId(branchItem.getMetaProductId());
				if(bcertTargetList != null && bcertTargetList.size() > 0){
					bcertTargetMap.put(branchItem.getMetaProductId(), bcertTargetList.get(0));
				}
				performTargetList = this.performTargetService.findSuperSupPerformTargetByMetaProductId(branchItem.getMetaProductId());
				if(performTargetList != null && performTargetList.size()>0){
					performTargetMap.put(branchItem.getMetaProductId(), performTargetList);
				}

				initOrdOrderItemMeta(ordOrderItemMateMap, branchItem,item.getVisitTime());
				//初始化机票相关信息
				initFlightInfo(ordOrderItemMateMap, branchItem,item.getVisitTime());
			}
		}
	}

	/**
	 * 初始OrdOrderItemMeta相关信息
	 * @param ordOrderItemMateMap
	 * @param branchItem
	 */
	private void initOrdOrderItemMeta(Map<Long, OrdOrderItemMeta> ordOrderItemMateMap,ProdProductBranchItem branchItem,Date visitTime) {
		TimePrice timePrice=metaTimePriceDAO.getMetaTimePriceByIdAndDate(branchItem.getMetaBranchId(), visitTime);
		
		OrdOrderItemMeta ordOrderItemMeta = new OrdOrderItemMeta();
		ordOrderItemMeta.setSettlementPrice(timePrice.getSettlementPrice());
		ordOrderItemMateMap.put(branchItem.getMetaProductId(), ordOrderItemMeta);
	}
	
	/**
	 * 初始化机票相关信息
	 * @param ordOrderItemMateMap
	 * @param branchItem
	 */
	private void initFlightInfo(Map<Long, OrdOrderItemMeta> ordOrderItemMateMap,ProdProductBranchItem branchItem,Date visitTime) {
		
		MetaProduct metaProduct = metaProductDAO.getMetaProductByPk(branchItem.getMetaProductId());
		
		if(Constant.PRODUCT_TYPE.TRAFFIC.name().equals(metaProduct.getProductType())){//交通
			
			if(Constant.SUB_PRODUCT_TYPE.FLIGHT.name().equals(metaProduct.getSubProductType())){//机票			
				
				MetaProductTraffic metaProductTraffic=(MetaProductTraffic)metaProductDAO.getMetaProduct(branchItem.getMetaProductId(), Constant.PRODUCT_TYPE.TRAFFIC.name());
				
				if(null!=metaProductTraffic){
					
					OrdOrderItemMeta ordOrderItemMeta = new OrdOrderItemMeta();
					ordOrderItemMeta.setVisitTime(visitTime);
					ordOrderItemMeta.setDirection(metaProductTraffic.getDirection());
					
					PlaceFlight placeFlight=placeFlightService.queryPlaceFlight(metaProductTraffic.getGoFlight());
					
					if(null!=placeFlight){
						ordOrderItemMeta.setGoFlightCode(placeFlight.getFlightNo());
						if(StringUtils.isNotBlank(placeFlight.getStartTime())){
							//去的航班时间=(去玩的日期+起飞时间)
							String time=ordOrderItemMeta.getVisitTimeDay()+" "+placeFlight.getStartTime();
							ordOrderItemMeta.setGoFlightTime(DateUtil.getDateByStr(time, "yyyy-MM-dd HH:mm"));
						}
					}
					
					if(Constant.TRAFFIC_DIRECTION.ROUND.name().equals(metaProductTraffic.getDirection())
							&&null!=metaProductTraffic.getBackFlight()){//往返
						placeFlight=placeFlightService.queryPlaceFlight(metaProductTraffic.getBackFlight());
						if(null!=placeFlight){
							ordOrderItemMeta.setBackFlightCode(placeFlight.getFlightNo());
							if(StringUtils.isNotBlank(placeFlight.getStartTime())){
								//返回的航班时间=(去玩的日期+玩的天数)+起飞时间-1天
								Date temp=DateUtil.dsDay_Date(ordOrderItemMeta.getVisitTime(), metaProductTraffic.getDays().intValue()-1);
								String time=DateUtil.getDateTime("yyyy-MM-dd", temp)+" "+placeFlight.getStartTime();
								ordOrderItemMeta.setBackFlightTime(DateUtil.getDateByStr(time, "yyyy-MM-dd HH:mm"));
							}
						}
					}
					
					ordOrderItemMateMap.put(branchItem.getMetaProductId(), ordOrderItemMeta);
				}
			}
		}
	}
	/**
	 * setBuildOrderService.
	 * 
	 * @param buildOrderService
	 *            buildOrderService
	 */
	public void setBuildOrderService(final IBuildOrder buildOrderService) {
		this.buildOrderService = buildOrderService;
	}
	
	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	public void setPlaceFlightService(PlaceFlightService placeFlightService) {
		this.placeFlightService = placeFlightService;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}
	
	
}
