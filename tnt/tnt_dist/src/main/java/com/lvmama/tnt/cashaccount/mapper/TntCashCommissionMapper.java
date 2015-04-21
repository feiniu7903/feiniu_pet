package com.lvmama.tnt.cashaccount.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.tnt.cashaccount.po.TntCashCommission;
import com.lvmama.tnt.comm.vo.Page;

@Repository
public interface TntCashCommissionMapper {

	public void insert(TntCashCommission entity);
	
	public List<TntCashCommission> findPage(Page<TntCashCommission> page);

	public int count(TntCashCommission commission);
}
