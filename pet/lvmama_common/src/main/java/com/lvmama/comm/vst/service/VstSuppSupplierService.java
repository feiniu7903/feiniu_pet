package com.lvmama.comm.vst.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.vst.vo.VstSuppContactVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierSettlementVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

public interface VstSuppSupplierService {

	/**
	 * 根据供应商ID查供应商信息
	 * 
	 * @author: ranlongfei 2013-12-18 下午6:34:53
	 * @param supplierId
	 * @return
	 */
	VstSuppSupplierVo findVstSuppSupplierById(Long supplierId);
	/**
	 * 根据供应商结算ID查询结算信息
	 * 
	 * @author: ranlongfei 2013-12-18 下午6:35:21
	 * @param settelmentId
	 * @return
	 */
	VstSuppSupplierSettlementVo findSuppSupplierSettlementById(Long settelmentId);
	
	/**
	 * 根据供应商模糊名称查询供应商列表
	 * @param blurName
	 * @return
	 */
	List<VstSuppSupplierVo> findVstSuppSupplierByBlurName(String blurName);
	/**
	 * 根据供应商结算账户模糊名称查询供应商结算列表
	 * @param blurName
	 * @return
	 */
	List<VstSuppSupplierSettlementVo>  findSuppSupplierSettlementByBlurName(String blurName);
	
	/**
	 * 根据查询条件查询供应商列表(ID,名称...)
	 * @param params
	 * @return
	 */
	List<VstSuppSupplierVo> findVstSuppSupplierByParams(Map<String,Object> params);
	
	/**
	 * 根据查询条件查询供应商结算列表
	 * @param params
	 * @return
	 */
	List<VstSuppSupplierSettlementVo> findSuppSupplierSettlementByParams(Map<String,Object> params);

	/**
	 * 根据查询条件查询供应商联系人列表(结算规则ID)
	 * @param params
	 * @return
	 */
	List<VstSuppContactVo> findSuppContactVoByParams(Map<String,Object> params);
	
	/**
	 * 根据结算规则ID查询联系人
	 */
	List<VstSuppContactVo> findSuppContactVoBySettleRuleId(Long settleRuleId);
}
