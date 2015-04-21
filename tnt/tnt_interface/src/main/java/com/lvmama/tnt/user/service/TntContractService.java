package com.lvmama.tnt.user.service;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntContract;

public interface TntContractService {

	public List<TntContract> queryByUserId(Long userId);

	public boolean deleteById(Long contractId);

	public boolean uploadContract(TntContract tntContract);

	public String getContractName(String fileName);

	public List<TntContract> pageQuery(Page<TntContract> page);

	public int count(TntContract tntContract);

}
