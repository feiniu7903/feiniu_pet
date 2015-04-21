package com.lvmama.tnt.recognizance.mapper;

import com.lvmama.tnt.recognizance.po.TntAccount;

/**
 * 保证金账户
 * 
 * @author gaoxin
 * @version 1.0
 */
public interface TntAccountMapper {

	public int insert(TntAccount entity);

	public int update(TntAccount tntRecognizance);

	public TntAccount getById(Long account);

	public TntAccount getByUserId(Long userId);

	public TntAccount getByAccountType(String type);
}
