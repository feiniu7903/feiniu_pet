package com.lvmama.comm.bee.service.meta;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaTravelCode;

public interface MetaTravelCodeService {

	public int deleteByPrimaryKey(Long metaTravelCodeId);
	public Long insert(MetaTravelCode record) ;
	public List<MetaTravelCode> selectByCondition(Map<String,Object> params);
	public MetaTravelCode selectByPrimaryKey(Long metaTravelCodeId);
	public MetaTravelCode selectBySuppAndDate(String supplierProductId,Date specDate);
	
	public MetaTravelCode selectBySuppAndDateAndChannelAndBranch(String supplierProductId,Date specDate,String channel,String branch);
	
	public int updateByPrimaryKeySelective(MetaTravelCode record);
}
