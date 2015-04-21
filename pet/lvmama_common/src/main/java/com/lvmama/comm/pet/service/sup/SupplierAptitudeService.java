/**
 * 
 */
package com.lvmama.comm.pet.service.sup;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.sup.SupSupplierAptitude;

/**
 * @author yangbin
 *
 */
public interface SupplierAptitudeService {

	List<SupSupplierAptitude> selectByParam(Map<String,Object> param);
	
	void addSupplierAptitude(SupSupplierAptitude ssa);
	void updateSupplierAptitude(SupSupplierAptitude ssa);	
}
