package com.lvmama.tnt.user.mapper;

import java.util.List;

import com.lvmama.tnt.user.po.TntContract;
import com.lvmama.tnt.user.po.TntContractAttach;

/**
 * 分销商合同
 * 
 * @author hupeipei
 * 
 */
public interface TntContractAttachMapper {

	public List<TntContractAttach> selectList(TntContractAttach tntContract);

	public TntContractAttach selectByPrimaryKey(Long contractId);

	public int insert(TntContractAttach tntContractAttach);

}
