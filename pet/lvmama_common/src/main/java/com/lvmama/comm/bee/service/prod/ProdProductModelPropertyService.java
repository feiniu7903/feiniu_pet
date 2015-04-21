package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdProductModelProperty;
import com.lvmama.comm.vo.ProdProductModelPropertyVO;
/**
 * ProductModelProperty服务接口
 * @author duanshuailiang
 *
 */
public interface ProdProductModelPropertyService {
	/**
	 * 保存于更新数据
	 * @param prodProductModelPropertyList
	 */
	public void saveProdProductModelProperty(List<ProdProductModelProperty> prodProductModelPropertyList);
	/**
	 * 根据productID查询数据
	 * @param productId
	 * @return
	 */
	public List<ProdProductModelProperty> getProdProductModelPropertyByProductId(String productId);
	/**
	 * 根据productId获取List<ProdProductModelPropertyVO>
	 * @param productId
	 * @return
	 */
	public List<ProdProductModelPropertyVO> getProdProductModelPropertyVOByProductId(String productId);
	
	public void updateProdRule(String productId,String routeCateGory,String routeStandard,String departArea,String travelTime,String dataStr);
	/**
	 * 根据productId清空 产品属性1及产品属性2中该产品的定制属性
	 */
	public void clearProdProductModelPropertyByProductId(Long productId);
	
	public boolean isCheckExistByProperty(String propertyId);
	
	public List<ProdProductModelProperty> selectByParam(Map<String, Object> map);
}
