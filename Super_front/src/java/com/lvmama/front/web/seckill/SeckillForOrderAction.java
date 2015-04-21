package com.lvmama.front.web.seckill;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.service.businessCoupon.ProdSeckillRuleService;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ViewBuyInfo;
import com.lvmama.front.web.BaseAction;

public class SeckillForOrderAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6982187423432786712L;
	private ViewBuyInfo buyInfo;
    //
    private ProdSeckillRuleService prodSeckillRuleService;
    
	@Action("/seckill/order")
	public String validateSeckillOrder(){
		
			JSONResult jsonObject=new JSONResult(getResponse());
			//读取秒杀类别的购买数量
			Map<Long, Long> branchMap = buyInfo.getOrdItemProdList();
			Long branchNum = 0L;
			if(branchMap != null){
				branchNum = branchMap.get(buyInfo.getSeckillBranchId());
			}
			if(branchNum == 0L){
				jsonObject.put("flag",false);
				jsonObject.put("msg","未选择秒杀商品!");
				jsonObject.output();
				return null;
			}
			
			ProdSeckillRule seckillRule = SeckillMemcachedUtil.getSeckillMemcachedUtil().getSeckillRule(buyInfo.getSeckillBranchId());
			
			if(seckillRule != null){
				if(seckillRule.getId().longValue() != buyInfo.getSeckillId().longValue() ){
					jsonObject.put("flag",false);
					jsonObject.put("msg","秒杀己结束!");
					jsonObject.output();
					return null;
				}
				//校验产品库存
				Long productNumber = SeckillMemcachedUtil.getSeckillMemcachedUtil().getProductNumberByMemcached(buyInfo.getSeckillBranchId(), true, branchNum);
				if(productNumber >= 1){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("id", seckillRule.getId());
					param.put("decreaseStock", branchNum);
					int updateNum = prodSeckillRuleService.minusStockSeckill(param);
					if(updateNum > 0){
						jsonObject.put("flag",true);
						jsonObject.put("msg","");
						String uuid = UUID.randomUUID().toString();
						ServletUtil.putSession(getRequest(), getResponse(), Constant.SECKILL.SECKILL_SUBMIT_ORDER_UUID.getCode(), uuid);
						jsonObject.put("token", uuid);
						jsonObject.output();
						return null;
					}else{
						jsonObject.put("flag",false);
						jsonObject.put("msg","秒杀商品，库存不足！");
						jsonObject.output();
						return null;
					}
				}else{
					jsonObject.put("flag",false);
					jsonObject.put("msg","秒杀商品，库存不足！");
					jsonObject.output();
					return null;
				}
			}
		return null;
	}

	public void setProdSeckillRuleService(
			ProdSeckillRuleService prodSeckillRuleService) {
		this.prodSeckillRuleService = prodSeckillRuleService;
	}

	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}

	public void setBuyInfo(ViewBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}

	
}
