package com.lvmama.comm.pet.service.fin;


/**
 * 系统成本入账
 * @author shangzhengyuan
 *
 */
public interface FinGLCostService {

	/**
	 * 生成订单成本入账
	 */
	public void generateOrderCostGLData(Long orderId);
	
	/**
	 * 生成团成本入账
	 */
	public String generateTravelGroupCostGLData(final String[] travelGroupCodes);
}
