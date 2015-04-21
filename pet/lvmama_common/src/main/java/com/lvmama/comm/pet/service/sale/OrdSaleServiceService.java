package com.lvmama.comm.pet.service.sale;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdSaleService;

public interface OrdSaleServiceService {
	/**
	 * 缩合查询.
	 * 
	 * @param parms
	 * @return
	 */
	boolean findOrderItemIsRefund(Long orderItemMetaId);
	/**
	 * 缩合查询.
	 * 
	 * @param parms
	 * @return
	 */
	List<OrdSaleService> getOrdSaleServiceAllByParam(Map parms);

	/**
	 * 增加售后服务对像.
	 * 
	 * @param ordSaleService
	 * @return
	 */
	Long addOrdSaleService(OrdSaleService ordSaleService);
	/**
	 * 删除售后服务对像.
	 * 
	 * @param ordSaleService
	 * @return
	 */
	public void deleteOrdSaleService(String ordSaleId);


	/**
	 * 通过主健查找售后服务对像.
	 * 
	 * @param ordSaleId
	 * @return
	 */
	OrdSaleService getOrdSaleServiceByPk(Long ordSaleId);

	/**
	 * 更新售后服务对像.
	 * 
	 * @param ordSaleService
	 */
	boolean updateOrdSaleService(OrdSaleService ordSaleService);
	
	/**
	 * 分配投诉
	 * */
	int takeOrdSaleServiceByIds(Map<String, Object> params, String operatorName);
}
