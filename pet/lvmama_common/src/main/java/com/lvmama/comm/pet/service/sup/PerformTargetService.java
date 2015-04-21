package com.lvmama.comm.pet.service.sup;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.vo.Constant;

public interface PerformTargetService {

	/**
	 * 添加履行对象
	 * @param performTarget
	 */
	Long addPerformTarget(SupPerformTarget performTarget,String operatorName);

	/**
	 * 更新履行对象
	 * @param performTarget
	 */
	void updatePerformTarget(SupPerformTarget performTarget,String operatorName);

	/**
	 * 删除履行对象(采用更新)
	 * @param performTarget
	 */
	@Deprecated
	void deletePerformTarget(SupPerformTarget performTarget,String operatorName);

	/**
	 * 根据ID获取履行对象
	 * @param supplierId
	 * @return 指定的履行对象
	 */
	SupPerformTarget getSupPerformTarget(Long performTargetId);

	/**
	 * 根据查询条件查找LIST
	 * @param param
	 * @return
	 */
	List<SupPerformTarget> findSupPerformTarget(Map param);
	/**
	 * 修改是否有效状态
	 * @param performTargetId
	 * */
	void markIsValid(Map params);
	
	public Integer selectRowCount(Map searchConds);

	/**
	 * 通过供应商ID拿到对应的全部履行对象
	 * @param param
	 * @return
	 */
	List<SupPerformTarget> findAllSupPerformTarget(Map<String,Object> param);
	List<SupPerformTarget> findSuperSupPerformTargetByMetaProductId(Long metaProductId);

	/**
	 * 删除采购产品履行对象
	 * @param metaPerform
	 */
	void deleteMetaRelation(MetaPerform metaPerform,String operatorName);
	Integer selectByMetaProductId(Long metaProductId);
	/**
	 * 添加采购产品履行对象
	 * @param metaPerform
	 */
	void addMetaRelation(MetaPerform metaPerform,String operatorName);
	
	
	/**
	 * 通过对象ID取对应类型的采购绑定的产品ID列表
	 * @param targetId
	 * @param type
	 * @return
	 */
	List<Long> selectMetaRelationByParam(Long targetId,Constant.PRODUCT_BIZ_TYPE type);
	
	/**
	 * 
	 * @param supplierId
	 * @param type
	 * @return
	 */
	List<Long> selectMetaRelationBySupplierId(Long supplierId,Constant.PRODUCT_BIZ_TYPE type);
	
	/**
	 * 根据产品Id获取履行对象
	 * @param metaProductId
	 * @return
	 */
	List<MetaPerform> getMetaPerformByMetaProductId(Long metaProductId);
}
