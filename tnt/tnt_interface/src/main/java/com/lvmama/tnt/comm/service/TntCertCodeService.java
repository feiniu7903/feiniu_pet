package com.lvmama.tnt.comm.service;

import java.util.Map;

import com.lvmama.tnt.comm.po.TntCertCode;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;

public interface TntCertCodeService {
	/**
	 * 查询激活记录
	 * @param type 激活类型
	 * @param code 激活码
	 * @param autoDelete 是否需要删除此条记录。如果设置为true，那么此条激活记录将会被删除
	 * @return 返回指定的激活记录。
	 */
	TntCertCode queryUserCertCode(USER_IDENTITY_TYPE type, String code, boolean autoDelete);
	
	/**
	 * 验证identityTarget和验证码是否匹配
	 * @param type
	 * @param code
	 * @param identityTarget
	 * @return 是否请求的验证信息是否正确，需要注意的是，一旦返回true，那么验证信息将不会再被保存
	 */
	boolean validateAuthenticationCode(USER_IDENTITY_TYPE type, String code, String identityTarget);
	/**
	 * 删除激活码
	 * @param Code
	 */
	void deleteUserCertCode(Map<String, Object> params);
	
	/**
	 * 保存激活码
	 * @param Code
	 */
	void saveTntCertCode(TntCertCode userCertCode);
	
}
