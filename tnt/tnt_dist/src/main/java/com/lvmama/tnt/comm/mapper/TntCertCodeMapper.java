package com.lvmama.tnt.comm.mapper;


import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.po.TntCertCode;

/**
 * 用户邮件或手机验证码
 * @author gaoxin
 * @version 1.0
 */

public interface TntCertCodeMapper {
	
	public void insert(TntCertCode entity);

	public int delete(Long tntCertCodeId);

	public int deleteCertCode(Map<String,Object> param);
	/**
	 * 根据用户的激活码找到用户的激活记录
	 * @param type  EMAIL/MOBILE
	 * @param code
	 * @return
	 */
	public TntCertCode queryByTypeAndCode(Map<String,Object> param);
	
	public TntCertCode queryCertCode(Map<String,Object> param);
}
