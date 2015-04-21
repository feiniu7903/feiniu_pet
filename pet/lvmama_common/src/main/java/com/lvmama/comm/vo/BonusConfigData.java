/**
 * 
 */
package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.List;

import com.lvmama.comm.bee.po.ord.BonusConfig;
import com.lvmama.comm.bee.service.ord.BonusConfigService;
import com.lvmama.comm.spring.SpringBeanProxy;

/**
 * 
 * 订单返现/可使用奖金规则
 * 
 * @author 张振华
 * 
 */
@Deprecated
public class BonusConfigData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7525957326893434583L;
	private static Object LOCK = new Object();
	private static List<BonusConfig> listBonusConfig;
	/**
	 * 订单发放奖金类型
	 */
	public static final String ORDER_PROFIT_ISSUE="issue";
	
	/**
	 * 订单可使用奖金类型
	 */
	public static final String ORDER_PROFIT_DEDUCTION="deduction";
	
	private static void getInstance(){
		if(listBonusConfig == null){
			synchronized (LOCK) {
				if(listBonusConfig == null){
					BonusConfigService bonusConfigService = (BonusConfigService)SpringBeanProxy.getBean("bonusConfigService");
					listBonusConfig  = bonusConfigService.selectBonusConfig();
				}
			}
		}
	}
	
	/**
	 * 根据毛利润获得可用奖金额度，单位：返回的是分
	 * 
	 * @param type issue(发放),deduction(使用)
	 * @param profit 毛利润
	 * @return
	 */
	@Deprecated
	public  static Long getBonusByProfit(Long profit,String type) {
		if(profit==null || profit==0) {
			return 0L;
		}
		getInstance();
		if(listBonusConfig!=null) {
			for (BonusConfig bonusConfig:listBonusConfig) {
				if(bonusConfig.getMaxProfit()==null) {
					bonusConfig.setMaxProfit(Long.MAX_VALUE);
				}
				if(bonusConfig.getType().equals(type)&&bonusConfig.getMinProfit()*100<=profit&&(bonusConfig.getMaxProfit()>=(profit/100))) {
					return bonusConfig.getAmount()*100>(profit*bonusConfig.getPercent()/100) ? bonusConfig.getAmount()*100:(profit*bonusConfig.getPercent()/100);
				}
			}
		}
		return 0L;
	}
}
