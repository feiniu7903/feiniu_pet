/**
 * 
 */
package com.lvmama.comm.pet.vo.favor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;

/**
 * 订单优惠结果返回对象
 * @author liuyi
 *
 */
public class FavorOrderResult implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -1654282204334838948L;
	/**
	 * 订单优惠策略列表
	 */
	private List<OrderFavorStrategy> favorStrategyList = new ArrayList<OrderFavorStrategy>();
	/**
	 * 优惠总金额
	 */
	private Long discountAmount;
	
	/**
	 * 订单优惠总金额
	 * @param buyInfo 订单信息
	 * @param existedAmount 已存在的优惠(作用在产品上的优惠总额)
	 * @param refresh 是否强制刷新
	 * @return 订单优惠总金额
	 * <p>计算订单优惠的总金额，当计算完成后优惠金额会被缓存，除非<code>refresh</code>设置为true，则将会重新计算</p>
	 */
	public Long getDiscountAmount(final OrdOrder order,final long existedAmount, final boolean refresh, final ValidateCodeInfo info) {
		if (discountAmount == null || refresh) {
			long amount = 0L;
			List<OrderFavorStrategy> discardStrategy = new ArrayList<OrderFavorStrategy>(favorStrategyList.size());
			for (OrderFavorStrategy strategy : favorStrategyList) {
				if (strategy.isApply(order, amount + existedAmount)) {
					amount += strategy.getDiscountAmount(order, amount + existedAmount);
				} else {
					info.setValue(strategy.getInvalidDesc());
					discardStrategy.add(strategy);
				}
			}
			//丢弃无效的优惠策略
			for (OrderFavorStrategy strategy : discardStrategy) {
				favorStrategyList.remove(strategy);
			}
			
			discountAmount = amount;
		}		
		return discountAmount;
	}

	/**
	 * 获取优惠策略列表
	 * @return
	 */
	public List<OrderFavorStrategy> getFavorStrategyList() {
		return favorStrategyList;
	}

	/**
	 * 新增优惠策略
	 * @param favorStrategy
	 */
	public void addFavorStrategy(OrderFavorStrategy favorStrategy){
		favorStrategyList.add(favorStrategy);
	}
	
	@Override
	public String toString(){
		return new ToStringBuilder(this).append("discountAmount", discountAmount).append("favorStrategyList", favorStrategyList).toString();
	}
}

