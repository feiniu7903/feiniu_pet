/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.comm.bee.service.eplace;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.pet.po.sup.SupSupplier;
/**
 * EbkUser 的基本的业务流程逻辑的接口
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public interface EbkUserService{
	/**
	 * 持久化对象
	 * @param EbkUser
	 * @return
	 */
	public Long insert(EbkUser EbkUser);
	/**
	 * 根据主键id查询
	 */
	public EbkUser getEbkUserById(Long id);
	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	public EbkUser getEbkUserByUserName(String userName);
	/**
	 * 根据供应商Id查询
	 * @param supplierId
	 * @return
	 */
	public List<EbkUser> getEbkUserBySupplierId(String supplierId);
	/**
	 * 根据供应商Id查询
	 * @param supplierId
	 * @param isAdmin 是否是管理员
	 * @return
	 */
	public List<EbkUser> getEbkUserBySupplierId(String supplierId,boolean isAdmin);
	
	/**
	 *  修改admin用户信息
	 * @param EbkUser
	 * @return
	 */
	public Integer updateEbkUserById(EbkUser EbkUser);
	
	public Integer updateUser(EbkUser ebkUser);
	/**
	 * 校验用户名密码及用户是否有效
	 * @param userName
	 * @param password
	 * @return true（通过密码校验，且用户有效），否则false
	 */
	public boolean checkUserAndPassword(String userName,String password);
	
	public Long getEbkUserCount(Map<String, Object> params);
	public List<EbkUser> getEbkUser(Map<String, Object> params);
	
	public void changePassword(Long userId,String initPassword);
	
	List<Long> getEbkUserMetaBranchIds(Long userId);
	
	Long getEbkSupplierCount(Map<String, Object> map);
	List<SupSupplier> getEbkSupplier(Map<String, Object> map);
	public EbkUser getEbkUserWithTargetAndDeviceForEplace(Map params);
}
