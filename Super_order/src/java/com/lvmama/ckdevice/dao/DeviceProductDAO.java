package com.lvmama.ckdevice.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ckdevice.CkDeviceProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
/**
 * 设备产品
 * @author gaoxin
 *
 */
public class DeviceProductDAO extends BaseIbatisDAO{
	
	@SuppressWarnings("unchecked")
	public Boolean hasProduct(Map<String,Object> params){
		Boolean count =Boolean.FALSE;
		List<Long> list = super.queryForList("CK_DEVICE_PRODUCT.selectProdBranchIdByCondition", params);
		if(list!=null && !list.isEmpty()){
			count = Boolean.TRUE;
		}else{
			count = Boolean.FALSE;
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdProduct> selectDeviceProductInfo(Map<String,Object> params){
		return (List<ProdProduct>)super.queryForList("CK_DEVICE_PRODUCT.selectDeviceProductInfo",params);
	}
	
	public Long selectDeviceProductInfoCount(Map<String, Object> params) {
		return (Long) super.queryForObject("CK_DEVICE_PRODUCT.selectDeviceProductInfoCount", params);
	}
	
	@SuppressWarnings("unchecked")
	public List<ProdProductBranch>	selectProdProductBranchInfo(Map<String,Object> params){
		return super.queryForList("CK_DEVICE_PRODUCT.selectProdProductBranchInfo",params);
	}
	
	
	/**
	 * 新增销售产品
	 * @param productId
	 * @param productBranchId
	 * @param distributionProductId
	 */
	public void insert(CkDeviceProduct deviceProduct){
		super.insert("CK_DEVICE_PRODUCT.insert", deviceProduct);
	}

	public List<CkDeviceProduct> queryDeviceProductInfo(
			Map<String, Object> params) {
		return super.queryForList("CK_DEVICE_PRODUCT.queryDeviceProductInfo",params);
	}

	public void update(CkDeviceProduct cp) {
		super.update("CK_DEVICE_PRODUCT.update", cp);
		
	}

	public void del(Long deviceProductId) {
		super.update("CK_DEVICE_PRODUCT.del", deviceProductId);
		
	}

	public List<CkDeviceProduct> query(Map<String, Object> params) {
		return super.queryForList("CK_DEVICE_PRODUCT.query",params);
		
	}

	
	
	
}
