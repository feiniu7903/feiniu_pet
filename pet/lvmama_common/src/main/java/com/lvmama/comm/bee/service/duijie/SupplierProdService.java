package com.lvmama.comm.bee.service.duijie;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.duijie.SupplierProd;


/**
 * 供应商产品服务接口
 * @author yanzhirong
 */
public interface SupplierProdService {
	
	public Long insert(SupplierProd prod);
	
	public void update(SupplierProd prod);
	
	public Long selectBySupplierProdCount(Map<String,Object> params);
	
	public List<SupplierProd> selectSupplierProd(Map<String,Object> params);
	
	public Long getProductIdByCondition(Map<String,Object> params);
}
