package com.lvmama.comm.pet.service.sup;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.sup.SupSupplierAptitude;
import com.lvmama.comm.pet.po.sup.SupSupplierAssess;
import com.lvmama.comm.pet.vo.Page;

public interface SupplierService {
	/**
	 * 添加供应商
	 * 
	 * @param supplier
	 */
	Long addSupplier(SupSupplier supplier,String operatorName);

	/**
	 * 根据ID获取供应商
	 * 
	 * @param supplierId
	 * @return 指定供应商
	 */
	SupSupplier getSupplier(Long supplierId);

	/**
	 * 
	 * @param param
	 * @return
	 */
	List<SupSupplier> getSupSuppliers(Map param);

	/**
	 * 修改供应商
	 * 
	 * @Title: updateSupplierByPk
	 * @Description:
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	void updateSupplier(SupSupplier ss,String operatorName);

	/**
	 * 根据供应商ID删除记录(修改VALID状态值)
	 */
	void deleteSupplier(Map params);
	
	Long selectRowCount(Map searchConds);
	
	
	List<SupSupplier> getSupplierForParent(String supplierName);
	/**
	 * 供应商资质
	 * @param supSupplierAptitude
	 */
	void insertSupSupplierAptitude(SupSupplierAptitude supSupplierAptitude,String operatorName);
	
	/**
	 * 按供应商查询
	 * @param supplierId
	 * @return
	 */
	SupSupplierAptitude getSupplierAptitudeBySupplierId(Long supplierId);
	SupSupplierAptitude getSupplierAptitudeByPrimaryKey(Long pk);
	
	/**
	 * 供应商考核
	 * @param supSupplierAssess
	 */
	void insertSupSupplierAssess(SupSupplierAssess supSupplierAssess);
	
	/**
	 *
	 * @param supplierId
	 * @return
	 */
	List<SupSupplierAssess> selectSupplierAssessBySupplierId(Long supplierId);
	
	Map<Long,SupSupplier> getSupSupplierBySupplierId(List<Long> supplierId);
	Page<SupSupplier> getSupSupplierByParam(Map param,Long pageSize,Long currentPage);
	List<SupSupplier> getSupplierByParam(Map<String,Object> param);

}
