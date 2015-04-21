/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */
package com.lvmama.comm.vst.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkHousePrice;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.utils.json.ResultHandleT;

/**
 * 提供super系统调用VST数据插入接口定义
 * @author XIEXUN
 * @version 1.0
 * @since 1.0
 * @DATE 2014-2-11
 */
public interface EbkSuperClientService {
	
		
	/**
	 * 向中间表添加酒店产品信息
	 * @param metaProduct 酒店产品信息
	 * @return 0：操作失败  1：操作成功
	 */
	public ResultHandleT<Integer> addEbkSuperProd(MetaProduct metaProduct);
	
	/**
	 * 修改中间表酒店产品信息
	 * @param metaProduct 酒店产品信息
	 * @param saleFlag 是否可售 Y/N
	 * @return 0：操作失败  1：操作成功
	 */
	public ResultHandleT<ArrayList<String>> updateEbkSuperProd(Map<MetaProduct, String> metaProductMap);
	
	
	/**
	 * 向中间表添加ebk任务信息
	 * @param ebkTask ebk任务信息
	 * @return  0：操作失败  1：操作成功
	 */
	public ResultHandleT<Integer> addEbkSuperTask(EbkTask ebkTask);
	
	/**
	 * 修改中间表ebk任务信息
	 * @param ebkTask ebk任务信息
	 * @return  0：操作失败  1：操作成功
	 */
	public ResultHandleT<Integer> updateEbkSuperTask(EbkTask ebkTask);
	    
	/**
	 * 向中间表添加ebk产品修改申请信息
	 * @param ebkHousePrice 酒店产品修改申请信息
	 * @return  0：操作失败  1：操作成功
	 */
	public ResultHandleT<Integer> addEbkSuperApply(EbkHousePrice ebkHousePrice);
	
	/**
	 * 修改中间表ebk产品修改申请信息
	 * @param ebkHousePrice 酒店产品修改申请信息
	 * @return  0：操作失败  1：操作成功
	 */
	public ResultHandleT<Integer> updateEbkSuperApply(EbkHousePrice ebkHousePrice);
	
	
	/**
	 * 修改中间表ebk产品修改申请信息
	 * @param housePriceId 酒店产品修改申请ID
	 * @return  0：操作失败  1：操作成功
	 */
	public ResultHandleT<Integer> deleteEbkSuperApply(Long housePriceId);
}
