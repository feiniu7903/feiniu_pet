package com.lvmama.tnt.user.service;

import java.util.List;
import java.util.Map;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
import com.lvmama.tnt.user.po.TntUser;
import com.lvmama.tnt.user.po.TntUserDetail;

/**
 * 分销商信息
 * 
 * @author gaoxin
 * 
 */
public interface TntUserService {

	/**
	 * 保存用户信息
	 * 
	 * @param userDto
	 * @return
	 */
	public long insert(TntUser tntUser);

	/**
	 * 更新用户信息
	 * 
	 * @param TntUser
	 * @return
	 */
	public void update(TntUser tntUser);

	/**
	 * 更新用户信息
	 * 
	 * @param TntUser
	 * @return
	 */
	public void updatePassword(TntUser tntUser);

	/**
	 * 更新用户详情
	 * 
	 * @param detail
	 */
	public void updateDetail(TntUserDetail detail);

	/**
	 * 删除一个用户信息
	 * 
	 * @param userId
	 */
	public void del(Long userId);

	/**
	 * 根据条件查询用户信息
	 * 
	 * @param map
	 * @return List<TntUser>
	 */
	public List<TntUser> query(Map<String, Object> map);

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param userName
	 * @return TntUser
	 */
	public TntUser queryUserByUserName(String userName);

	/**
	 * 根据条件查询用户信息数
	 * 
	 * @param map
	 * @return count
	 */
	public int queryCount(Map<String, Object> map);

	/**
	 * 根据用户名查询用户信息及用户详情
	 * 
	 * @param String
	 * @return TntUser
	 */
	public TntUser findWithDetailByUserName(String userName);

	/**
	 * 根据用户id查询用户信息及用户详情
	 * 
	 * @param Long
	 * @return TntUser
	 */
	public TntUser findWithDetailByUserId(Long userId);

	/**
	 * 根据条件查询用户信息及用户详情
	 * 
	 * @param TntUser
	 * @return TntUser
	 */
	public TntUser findWithDetail(TntUser tntUser);

	public TntUser queryUserByMobileOrEmail(String mobile, String email);

	/**
	 * 根据条件查询用户信息和详情列表
	 * 
	 * @param TntUser
	 * @return List<TntUser>
	 */
	public List<TntUser> queryWithDetail(TntUser tntUser);

	/**
	 * 根据条件查询用户信息和详情列表,分页
	 * 
	 * @param TntUser
	 * @return List<TntUser>
	 */
	public List<TntUser> queryPageWithDetail(Page<TntUser> page);

	/**
	 * 根据条件查询用户信息和详情数
	 * 
	 * @param TntUser
	 * @return count
	 */
	public int queryWithDetailCount(TntUser tntUser);

	/**
	 * 基本资料审核通过
	 * 
	 * @param userId
	 * @return
	 */
	public boolean agree(Long userId);

	/**
	 * 基本资料审核不通过
	 * 
	 * @param userId
	 * @param failReason
	 * @return
	 */
	public boolean reject(Long userId, String failReason);

	/**
	 * 基本资料审核不通过
	 * 
	 * @param userId
	 * @param failReason
	 * @return
	 */
	public boolean reject(TntUser tntUser);

	/**
	 * 终审通过
	 * 
	 * @return
	 */
	public boolean finalAgree(Long userId);

	/**
	 * 终审不通过
	 * 
	 * @return
	 */
	public boolean finalReject(Long userId, String reason);

	/**
	 * 判断用户是否可以终审
	 * 
	 * 终审条件是用户必须通过基本信息审核
	 * 
	 * @param userId
	 * @return
	 */
	public boolean canFinalApprove(Long userId);

	/**
	 * 校验用户是否存在
	 * 
	 * @param type
	 * @param value
	 * @return false 已经存在，不能注册，可以发送校验码
	 */
	boolean isUserRegistrable(final USER_IDENTITY_TYPE type, final String value);

	public boolean setContractDate(TntUserDetail detail);

	/**
	 * 终止合同
	 * 
	 * @param userId
	 * @param reason
	 * @return
	 */
	public boolean endContract(Long userId, String reason);

	/**
	 * 重新合作
	 * 
	 * @param userId
	 * @return
	 */
	public boolean repeatDoing(Long userId);

	/**
	 * 激活用户邮箱
	 * 
	 * @param userId
	 * @return
	 */
	public boolean activeUser(Long userId);

	/**
	 * 支付类型
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public boolean setPayType(Long userId, String type, String reason);

	/**
	 * 支付方式Map
	 * 
	 * @return
	 */
	public Map<String, String> getPayTypeMap();

	public void updateMaterialStatus(TntUserDetail detail);

}
