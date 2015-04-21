/**
 * 
 */
package com.lvmama.op.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.op.OpTravelGroup;

/**
 * @author yangbin
 *
 */
public class OpTravelGroupDAO extends BaseIbatisDAO {

	public Long insert(OpTravelGroup group){
		return (Long)super.insert("OP_TRAVEL_GROUP.insert", group);
	}
	
	public List<OpTravelGroup> selectByParam(Map<String,Object> parameter){
		return super.queryForList("OP_TRAVEL_GROUP.selectByParam", parameter);
	}
	
	public void update(OpTravelGroup group){
		super.update("OP_TRAVEL_GROUP.updateByPrimaryKey", group);
	}
	
	/**
	 * 直接使用映射的sql更新操作
	 * @param statmentSuffix 映射的后辍
	 * @param parameter
	 */
	public void update(final String statmentSuffix,final Map<String,Object> parameter){
		super.update("OP_TRAVEL_GROUP."+statmentSuffix, parameter);
	}
	public OpTravelGroup selectByGroupCode(String groupCode){
		OpTravelGroup group=new OpTravelGroup();
		group.setTravelGroupCode(groupCode);
		return (OpTravelGroup)super.queryForObject("OP_TRAVEL_GROUP.selectByPrimary",group);
	}
	
	public OpTravelGroup selectByPrimary(OpTravelGroup group){
		return (OpTravelGroup)super.queryForObject("OP_TRAVEL_GROUP.selectByPrimary",group);
	}
	
	public Long selectCountByParam(Map<String,Object> parameter){
		try
		{
			return (Long)super.queryForObject("OP_TRAVEL_GROUP.selectByParamCount",parameter);
		}catch(Exception ex)
		{
			return 0L;
		}
	}
	
	/**
	 * 读产品列表
	 * @param parameter
	 * @return
	 */
	public List<OpTravelGroup> selectProductListByParam(final Map<String,Object> parameter){
		return super.queryForList("OP_TRAVEL_GROUP.selectProductListByParam", parameter);
	}
	
	/**
	 * 取产品数量.
	 * @param parameter
	 * @return
	 */
	public Long selectProductCount(final Map<String,Object> parameter){
		try{
			return (Long)super.queryForObject("OP_TRAVEL_GROUP.selectProductCount",parameter);
		}catch(Exception ex){
			return 0L;
		}
	}
	
	/**
	 * 根据团号查询组团的类型
	 * @param travelGroupCode 团号
	 * @return 
	 */
	public String selectGroupTypeByGroupCode(String travelGroupCode) {
		return (String)super.queryForObject("OP_TRAVEL_GROUP.selectGroupTypeByGroupCode",travelGroupCode);
	}
	
}
