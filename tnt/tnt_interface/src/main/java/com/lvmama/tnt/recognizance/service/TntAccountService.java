package com.lvmama.tnt.recognizance.service;

import com.lvmama.tnt.recognizance.po.TntAccount;

public interface TntAccountService {

	public boolean setAccount(TntAccount t);

	public TntAccount getByUserId(Long userId);

	public TntAccount getLvmamaAccount();
	
	public void save(TntAccount tntAccount);
	public TntAccount getById(Long id);
	
	public TntAccount getByAccountType(String type);

}
