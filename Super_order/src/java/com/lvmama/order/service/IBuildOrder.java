package com.lvmama.order.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrderInfoDTO;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;

/**
 * 订单创建服务接口.
 * 
 * @author liwenzhan
 * @author sunruyi
 * @version Super订单创建重构
 * @since Super订单创建重构
 * @see com.lvmama.ord.service.po.BuyInfo
 * @see com.lvmama.order.po.OrderInfoDTO
 */
public interface IBuildOrder {
	/**
	 * 初始化订单创建DTO.
	 * 
	 * @param buyInfo
	 *            购买信息
	 * @param originalOrderId
	 *            原订单ID
	 * @param operatorId
	 *            操作人
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	OrderInfoDTO initOrderInfo(BuyInfo buyInfo, Long originalOrderId, Map<Long,SupBCertificateTarget> bcertTargetMap, Map<Long,List<SupPerformTarget>> performTargetMap,
			 Map<Long, OrdOrderItemMeta> ordOrderItemMateMap,
			String operatorId);

	/**
	 * 保存订单创建DTO.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	OrderInfoDTO saveOrderInfo(OrderInfoDTO orderInfo);

	/**
	 * 保存操作日志.
	 * 
	 * @param orderInfo
	 *            订单创建DTO{@link OrderInfoDTO}
	 */
	void saveLog(OrderInfoDTO orderInfo);

	/**
	 * 创建订单.
	 * 
	 * @param buyInfo
	 *            购买信息
	 * @param orderId
	 *            订单ID
	 * @param operatorId
	 *            操作人
	 * @return 订单创建DTO{@link OrderInfoDTO}
	 */
	OrderInfoDTO buildOrderInfo(BuyInfo buyInfo, Long orderId, Map<Long,SupBCertificateTarget> bcertTargetMap, Map<Long,List<SupPerformTarget>> performTargetMap, Map<Long, OrdOrderItemMeta> ordOrderItemMateMap,String operatorId);
	/**
	 * 下单的销售产品的采购产品id
	 * @param buyInfo
	 * @return
	 */
	Map<Item,List<ProdProductBranchItem>> buildProductBranchItem(BuyInfo buyInfo);
}
