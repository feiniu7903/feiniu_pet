package com.lvmama.comm.pet.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdInsurance;

/**
 * 保险类产品的逻辑操作接口
 *
 */
public interface ProdInsuranceService {
	/**
	 * 新增保险产品
	 * @param insurance 保险产品
	 * @param operatorName 操作人
	 * @return 被保存的保险产品
	 * 新增保险产品，并记录操作人创建了此产品的日志
	 */
	ProdInsurance add(ProdInsurance insurance, String operatorName);
	/**
	 * 更新保险产品
	 * @param insurance 保险产品
	 * @param operatorName 操作人
	 * @return 被更新的保险产品
	 * 更新保险产品，并记录操作人修改了此保险产品的明细日志。此方法会根据所传入的保险产品的标示，找到原保险产品与传入的保险产品比较不同的地方，然后记录日志。
	 */	
	ProdInsurance update(ProdInsurance insurance, String operatorName);
	/**
	 * 更新保险产品
	 * @param insurance 保险产品
	 * @param oldInsurance 原保险产品
	 * @param operatorName 操作人
	 * @return 被更新的保险产品
	 * 更新保险产品，并记录操作人修改了此保险产品的明细日志。此方法会根据两个保险产品，比较不同的地方，然后记录日志。
	 */
	ProdInsurance update(ProdInsurance insurance, ProdInsurance oldInsurance, String operatorName);
	
	/**
	 * 根据查询条件返回记录数
	 * @param param 查询条件
	 * @return 记录数
	 * <p>根据传入的查询条件统计符合条件的记录数</p>
	 */
	Long selectRowCount(Map<String, Object> param);
	
	/**
	 * 根据查询条件返回符合条件的记录列表
	 * @param param 查询条件
	 * @return 记录列表
	 * <p>根据传入的查询条件返回符合条件的记录，但需要注意的是，当<code>param</code>是Null或者空时，出于安全原因将会直接返回空列表而不进行查询.</p>
	 */
	List<ProdInsurance> getProdInsuranceByMap(Map<String, Object> param);
	
	/**
	 * 根据产品标识查询保险产品，请特别注意这里不是根据<code>insurance_id</code>进行查询的
	 * @param productId 产品标识
	 * @return 保险产品
	 */
	ProdInsurance getProdInsuranceByPrimaryKey(Long productId);
}
