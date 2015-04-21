package com.lvmama.tnt.user.mapper;

import java.util.List;

import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntContract;

/**
 * 分销商合同
 * 
 * @author hupeipei
 * 
 */
public interface TntContractMapper {

	public List<TntContract> selectList(TntContract tntContract);

	public List<TntContract> selectListWithAttach(TntContract tntContract);

	public int deleteById(Long contractId);

	public int insert(TntContract tntContract);

	public int updateStatus(TntContract t);

	public List<TntContract> fetchPageWithAttach(Page<TntContract> page);

	public int count(TntContract t);

	public int update(TntContract t);

}
