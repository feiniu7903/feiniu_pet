package com.lvmama.order.logic;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.utils.MemcachedSeckillUtil;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 秒杀库存相关
 * 
 * @author zenglei
 *
 */
public class ProductSeckillLogic {
	
	static Log LOG = LogFactory.getLog(ProductSeckillLogic.class);

	private ProdSeckillRuleService prodSeckillRuleService;
	
	public void restoreStock(OrdOrderItemMeta itemMeta,OrdOrderItemProd itemProd,OrdOrder order) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("seckillId", order.getSeckillId());
		
		List<ProdSeckillRule> seckillList = prodSeckillRuleService.queryValidSeckillRule(param);

		if(seckillList != null && seckillList.size() > 0){
			ProdSeckillRule seckill = seckillList.get(0);
			if(seckill.getBranchId().longValue() == itemProd.getProdBranchId().longValue()){
				Long decreaseStock = 0L;
				if (Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(itemProd.getSubProductType())) { //对酒店单房型的总量递减计算消耗的库存
					List<OrdOrderItemMetaTime> list = itemMeta.getAllOrdOrderItemMetaTime();
					for (OrdOrderItemMetaTime ordOrderItemMetaTime : list) {
						decreaseStock+=ordOrderItemMetaTime.getQuatity()*itemMeta.getProductQuantity();
					}
				} else {
					decreaseStock = itemProd.getQuantity()*itemMeta.getProductQuantity();
				}
				LOG.info(" restore seckill Stock decreaseStock = " + decreaseStock+" ; orderId = " + itemMeta.getOrderId());
			
			
				//更新memcached中的值
				MemcachedSeckillUtil.getMemCachedSeckillClient().incr(Constant.SECKILL.PRODUCT_REPERTORY_NUMBER+seckill.getBranchId().toString(),decreaseStock);
				MemcachedSeckillUtil.getMemCachedSeckillClient().incr(Constant.SECKILL.WAIT_NUMBER_OF_PEOPLE+seckill.getBranchId().toString(),decreaseStock);
			
				param = new HashMap<String, Object>();
				param.put("id", order.getSeckillId());
				param.put("decreaseStock", decreaseStock);
				prodSeckillRuleService.restoreStockSeckill(param);
			}
		}
	}

	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
	}
}
