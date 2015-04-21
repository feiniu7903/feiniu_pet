package com.lvmama.comm.bee.service.eplace;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.pass.EplaceMetaProduct;
import com.lvmama.comm.bee.po.pass.EplaceSupplier;
import com.lvmama.comm.bee.po.pass.PassPortAuthResources;
import com.lvmama.comm.bee.po.pass.PassPortLog;
import com.lvmama.comm.bee.po.pass.PassPortUser;
import com.lvmama.comm.bee.po.pass.SupplierRelateProduct;
import com.lvmama.comm.bee.po.pass.UserRelateMenu;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;
import com.lvmama.comm.pet.po.sup.SupSupplier;

/**
 * 
 * @author luoyinqi
 * 
 */
@SuppressWarnings("unchecked")
public interface EPlaceService extends PassPortAuthService {
	// --------------------------------通关系统-供应商-Lvmama用户菜单权限设置------------------//
	/**
	 * 通过用户名称和密码查询用户对像是否存在.
	 */
	public PassPortUser getPassPortUserByUserName(Map map) ;
	/**
	 * 查询当前所有的菜单,由当前用户选中的菜单权限列表.
	 * 
	 * @param param
	 * @return
	 */
	public List<PassPortAuthResources> findPassPortAuthResourcesByMapIsChecked(Map param);
	/**
	 *  查询当前所有的采购产品,由当前用户选中的采购产品列表.
	 * @param map
	 * @return
	 */
	public List<SupplierRelateProduct> findSupplierRelateProductByMapIsChecked(Map map);
	/**
	 * 综合查询用户下权限计数.
	 * 
	 * @param param
	 * @return
	 */
	public Long findUserRelateMenuByMapCount(Map param);

	/**
	 * 综合查询用户下权限.
	 * 
	 * @param param
	 * @return
	 */
	public List<UserRelateMenu> findUserRelateMenuByMap(Map param);

	/**
	 * 查询当前所有的菜单.
	 * 
	 * @param param
	 * @return
	 */
	public List<PassPortAuthResources> findPassPortAuthResourcesByMap(Map param);
	
	/**
	 * 查询所有的当前供应商下选择的采购产品.
	 * @param map
	 * @return
	 */
	public List<SupplierRelateProduct> findSupplierRelateProductByMap(Map map) ;
	// --------------------------------通关平台用户(通关用户/驴妈妈登陆用户/供应商用户)操作------------------//
	/**
	 * 通过主健得到对像.
	 * 
	 * @param passPortUserId
	 * @return
	 */
	public PassPortUser getPassPortUserByPk(String passPortUserId);

	/**
	 * 修改用户.
	 * 
	 * @param eplaceSupplier
	 */
	public void updatePassPortUser(PassPortUser passPortUser, List ordItemIds);
	/**
	 * 修改E景通账号用户,菜单权限,采购产品权限相关.
	 * 
	 * @param eplaceSupplier
	 */
	public void updatePassPortUserResources(PassPortUser passPortUser,List resourcesIds,List productIds);
	/**
	 * 增加E景通账号用户,菜单权限,采购产品权限相关.
	 * 
	 * @param eplaceSupplier
	 */
	public void addPassPortUserResources(PassPortUser passPortUser,List resourcesIds,List productIds);
	/**
	 * 增加E景通账号用户.并增加Lvmama用户菜单权限,如果其菜单权限还包含有父类菜单，也会一起插入到用户菜单权限表中.
	 * 
	 * @param eplaceSupplier
	 */
	public void addPassPortUser(PassPortUser passPortUser,List resourcesIds);

	/**
	 * 综合查询,查询E景通供应商或LV客户端用户.
	 */
	public List<PassPortUser> findPassPortUserByMap(Map param);

	/**
	 * 综合查询-计数.
	 * 
	 * @param orderItemMetaId
	 * @return
	 */
	public Long findPassPortUserByMapCount(Map param);

	// ----------------------------------E景通供应商-----------------------------------------//
	/**
	 * 通过供应商主健查询到下面绑定的采购产品.
	 */
	public List<MetaProduct> findMetaProduct(Map param);

	/**
	 * 通过供应商主健查询到下面绑定的电子商务采购产品.
	 * @param param
	 * @param ids 远程通过targetId取出来的数据
	 * @return
	 */
	public List<EplaceMetaProduct> findEplaceMetaProductByMap(Map param,List<Long> ids);

	/**
	 * 通过主健得到对像.
	 * 
	 * @param eplaceSupplierId
	 * @return
	 */
	public EplaceSupplier getEplaceSupplierByPk(Long eplaceSupplierId);

	/**
	 * 关闭E景通供应商对像.
	 * 
	 * @param eplaceSupplier
	 */
	public void closeEplaceSupplier(EplaceSupplier eplaceSupplier);

	/**
	 * 修改E景通供应商对像并且与之对应的采购产品权限.
	 * 
	 * @param eplaceSupplier
	 */
	public void updateEplaceSupplier(EplaceSupplier eplaceSupplier,
			List ordItemIds);

	/**
	 * 增加E景通供应商对像.
	 * 
	 * @param eplaceSupplier
	 */
	public void addEplaceSupplier(EplaceSupplier eplaceSupplier, List ordItemIds);
 
	/**
	 * 根据orderItemMetaId查询通关日志
	 * 
	 * @param orderItemMetaId
	 * @return
	 */
	public List<PassPortLog> queryPassPortLogByOrderItemMetaId(
			Long orderItemMetaId);
	/**
	 * 查询供应商用户对应得采购产品信息
	 * @param userId
	 * @return
	 */
	public  List<UserRelateSupplierProduct> getMeatProductBySupplierUserId(Long userId);
	/**
	 * 通过用户编号查询到集合.展示其详情的所属履行对象.
	 * @param userId
	 * @return
	 */
	public List<UserRelateSupplierProduct> getSupplierUserListByTargetId(Long userId);
	
	/**
	 * 查询供应商用户
	 * @param userId
	 * @return
	 */
	public UserRelateSupplierProduct getSupplierUserForTargetId(Long userId);
	/**
	 * 添加通关日志
	 * @param passPortLog
	 */
	public void addPassPortLog(PassPortLog passPortLog);
	/**
	 * 查询供应商用户对应的履行对象个数
	 * @param userId
	 * @return
	 */
	public long getSupplierUserTargetIdTotal(Long userId);
	
	/**
	 * 通过供应商ID查询供应下的采购产品的全部类别 
	 * @param supplierId
	 * @return
	 */
	public List<MetaProductBranch> selectMetaProductBranchBySupplierId(Long supplierId);
	
	
	/**
	 * 通过供应商Id和代理产品类型货代理产品编号查询采购产品类别
	 * @param param supplierId productIdSupplier productTypeSupplier
	 * @return
	 */
	public List<MetaProductBranch> selectMetaProductBranchBySupplierType(Map<String,Object> param);
	/**
	 * 
	 * @param suppliers
	 * @return
	 */
	List<EplaceSupplier> findEplaceSupplierByMap(List<SupSupplier> suppliers);
	
	/**
	 * 通过ID列表查询采购产品列表
	 * @param ids
	 * @return
	 */
	public List<MetaProduct> selectMetaProductByIds(List<Long> ids);
	/**
	 * 查询供应商用户对应的产品
	 * @param userId
	 * @return
	 */
	public List<UserRelateSupplierProduct> getSupplierUserProductByUserId(Long userId);
}
