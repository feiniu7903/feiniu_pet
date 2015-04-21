package com.lvmama.eplace.dao;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.EplaceSupplier;
@SuppressWarnings("unchecked")
public class EplaceSupplierDAO extends BaseIbatisDAO {
	public EplaceSupplier getEplaceSupplierByPk(Long eplaceSupplierId){
		List<EplaceSupplier> list = super.queryForList("EPLACE_SUPPLIER.getEplaceSupplierByPk", eplaceSupplierId);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	 
	public void deleteEplaceSupplier(Long eplaceSupplierId){
		 super.delete("EPLACE_SUPPLIER.deleteByPrimaryKey", eplaceSupplierId);
	}
	
	public void updateEplaceSupplier(EplaceSupplier eplaceSupplier){
		 super.update("EPLACE_SUPPLIER.updateByPrimaryKey", eplaceSupplier);
	}
	public Long addEplaceSupplier(EplaceSupplier eplaceSupplier){
		Long supplyId=(Long)super.insert("EPLACE_SUPPLIER.insert", eplaceSupplier);
		return supplyId;
	}
	public List<EplaceSupplier> findEplaceSupplierBySupplierId(List<Long> list){
		if(CollectionUtils.isEmpty(list)){
			return Collections.emptyList();
		}
		return super.queryForList("EPLACE_SUPPLIER.findEplaceSupplierBySupplierId", list);
	}
}