package com.lvmama.tnt.recognizance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.recognizance.mapper.TntAccountMapper;
import com.lvmama.tnt.recognizance.po.TntAccount;

@Repository("tntAccountService")
public class TntAccountServiceImpl implements TntAccountService {

	@Autowired
	private TntAccountMapper tntAccountMapper;

	@Override
	public boolean setAccount(TntAccount t) {
		save(t);
		return true;
	}

	public void save(TntAccount tntAccount) {
		if (tntAccount == null)
			return;

		if (tntAccount.getAccountId() != null) {
			tntAccountMapper.update(tntAccount);
		} else {
			tntAccountMapper.insert(tntAccount);
		}
	}

	@Override
	public TntAccount getByAccountType(String type) {
		TntAccount t = tntAccountMapper.getByAccountType(type);
		return t != null ? t : TntAccount.newDefaultLvmamaAccount();
	}

	public TntAccount getById(Long id) {
		return tntAccountMapper.getById(id);
	}

	@Override
	public TntAccount getByUserId(Long userId) {
		return userId != null ? tntAccountMapper.getByUserId(userId) : null;
	}

	@Override
	public TntAccount getLvmamaAccount() {
		return getByAccountType(TntConstant.ACCOUNT_TYPE.RECOGNIZANCE.name());
	}

}
