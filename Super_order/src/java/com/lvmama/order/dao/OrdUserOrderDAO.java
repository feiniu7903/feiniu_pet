package com.lvmama.order.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdUserOrder;

public interface OrdUserOrderDAO {
	public int deleteByPrimaryKey(Long userOrderId);
	
    public int insert(OrdUserOrder record);

    public int insertSelective(OrdUserOrder record);

    public OrdUserOrder selectByPrimaryKey(Long userOrderId);

    public int updateByPrimaryKeySelective(OrdUserOrder record);

    public int updateByPrimaryKey(OrdUserOrder record);
    
    public List<OrdUserOrder> selectListByParams(Map<String, Object> params);
    
    public Long queryTotalCount(Map<String, Object> params);
}
