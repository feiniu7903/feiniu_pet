package com.lvmama.comm.pet.service.user;

/**
 * 
 * 用户行为分析逻辑类
 * @author Brian
 *
 */
public interface UserActionCollectionService {
	/**
	 * 保存用户行为(已过时,需要使用包含端口的接口方法)
	 * @param userId  用户标识
	 * @param ipAddr ip地址
	 * @param action  行为名称
	 * @param memo  属性
	 */
	@Deprecated
	void save(Long userId, String ipAddr, String action, String memo);
	/**
	 * 保存用户行为
	 * @param userId用户标识
	 * @param ipAddr 客户端的IP地址
	 * @param remotePort 客户端的端口
	 * @param action  行为名称
	 * @param memo  属性
	 */
	@Deprecated
	void save(Long userId, String ipAddr, Long remotePort ,String action, String memo);
	
	/**
	 * 保存用户行为(添加登陆方式，渠道，referer的接口方法)
	 * @param userId用户标识
	 * @param ipAddr客户端的IP地址
	 * @param remotePort客户端的端口
	 * @param action行为名称
	 * @param memo属性
	 * @param loginType登陆方式
	 * @param loginChannel渠道
	 * @param referer前跳转url
	 */
	void save(Long userId, String ipAddr, Long remotePort ,String action, String memo,String loginType,String loginChannel,String referer);
	/**
	 * 保存用户行为(已过时,需要使用包含端口的接口方法)
	 * @param userNo  用户no
	 * @param ipAddr ip地址
	 * @param action  行为名称
	 * @param memo  属性
	 */
	@Deprecated
	void save(String userNo, String ipAddr, String action, String memo);
	@Deprecated
	void save(String userNo, String ipAddr, Long remotePort ,String action, String memo);
	
	void save(String userNo, String ipAddr, Long remotePort ,String action, String memo,String loginType,String loginChannel,String referer);
	
}
