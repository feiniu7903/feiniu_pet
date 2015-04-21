package com.lvmama.tnt.comm.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.mapper.TntCertCodeMapper;
import com.lvmama.tnt.comm.po.TntCertCode;
import com.lvmama.tnt.comm.vo.TntConstant.USER_IDENTITY_TYPE;
@Repository("tntCertCodeService")
public class TntCertCodeServiceImpl implements TntCertCodeService {
	private static final Log log = LogFactory.getLog(TntCertCodeServiceImpl.class);
	@Autowired
	TntCertCodeMapper tntCertCodeMapper;
	
	@Override
	public TntCertCode queryUserCertCode(USER_IDENTITY_TYPE type, String code, boolean autoDelete) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type.name());
		param.put("code", code);
		TntCertCode tntCertCode  = tntCertCodeMapper.queryByTypeAndCode(param);
		if (null != tntCertCode && autoDelete) {
			param.put("type", tntCertCode.getType());
			param.put("code", tntCertCode.getCode());
			tntCertCodeMapper.deleteCertCode(param);
		}
		return tntCertCode;
	}

	@Override
	public boolean validateAuthenticationCode(USER_IDENTITY_TYPE type, String code, String identityTarget) {

		if (code == null) {
			return false;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type.name());
		param.put("code", code);
		param.put("identityTarget", identityTarget);
		TntCertCode tntCertCode  = tntCertCodeMapper.queryCertCode(param);
		
		if(tntCertCode != null && tntCertCode.getIdentityTarget()!=null)
		{
			if(tntCertCode.getIdentityTarget().equals(identityTarget))
			{
				param.put("type", tntCertCode.getType());
				param.put("code", tntCertCode.getCode());
				tntCertCodeMapper.deleteCertCode(param);
				log.info("tnt cert code was deleted: " + identityTarget + ", " + code);
				return true;
			}
			else
			{
				log.error("tnt cert code not exist: " + identityTarget + ", " + code);
				return false;
			}
		}
		else
		{
			log.error("tnt cert code not exist: " + identityTarget + ", " + code);
			return false;
		}
	
	}

	@Override
	public void saveTntCertCode(TntCertCode tntCertCode) {
		if (null != tntCertCode && null != tntCertCode.getIdentityTarget() && null!= tntCertCode.getType()) {
			tntCertCodeMapper.insert(tntCertCode);
		} else {
			log.error("邮件激活信息为空或者无法找到用户信息,丢弃保存");
		}

	}

	@Override
	public void deleteUserCertCode(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

}
