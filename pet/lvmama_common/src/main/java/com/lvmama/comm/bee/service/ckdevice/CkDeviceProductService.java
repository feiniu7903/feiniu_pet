package com.lvmama.comm.bee.service.ckdevice;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ckdevice.CkDeviceProduct;

public interface CkDeviceProductService {

	Boolean hasProduct(Map<String, Object> params);

	List<CkDeviceProduct> getDeviceProductList(Map<String, Object> params);
	List<CkDeviceProduct> queryCanPrintDeviceProductInfo(Map<String, Object> params);
	Long getDeviceProductCount(Map<String, Object> params);

	void save(CkDeviceProduct cp);

	void update(CkDeviceProduct cp);

	void del(Long  deviceProductId);

	List<CkDeviceProduct> query(Map<String, Object> params);

}
