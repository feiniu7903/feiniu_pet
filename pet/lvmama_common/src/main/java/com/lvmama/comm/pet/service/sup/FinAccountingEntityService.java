package com.lvmama.comm.pet.service.sup;

import java.util.List;

import com.lvmama.comm.pet.po.sup.FinAccountingEntity;

public interface FinAccountingEntityService {

	/**
	 * 查询所有的实体
	 * */
	List<FinAccountingEntity> selectEntityList();
}
