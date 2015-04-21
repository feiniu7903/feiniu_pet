package com.lvmama.operate.dao;
/**
 * 根据订单查找目的地，发送攻略邮件
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.edm.EdmOrderPlaceGuide;
import com.lvmama.comm.pet.po.edm.EdmSubscribeUserGroup;

public class OnlineMarketingDAO  extends BaseIbatisDAO {
	private static final Logger LOG = Logger.getLogger(EdmSubscribeUserGroupDAO.class); 
	/**
	 * EDM模板SQL空间
	 */
	private static final String SQL_NAME_SPACE = "ONLINE_MARKETING.";
    	/**
	 * 查询EDM用户
	 */
	private static final String GET_PLACE_EMAIL = SQL_NAME_SPACE + "getPlaceEmail";
	private static final String SEARCH_USER = SQL_NAME_SPACE + "searchUser";
	private static final String SEARCH_USER_BY_SQL = SQL_NAME_SPACE +"searchUserBySql";
	@SuppressWarnings("unchecked")
	public List<EdmOrderPlaceGuide> getPlaceEmail(){
		List<EdmOrderPlaceGuide> result= super.queryForListForReport(GET_PLACE_EMAIL);
		return result;
	}
	
	public Long searchUserCount(final EdmSubscribeUserGroup object){
		return (Long)queryForObject(SQL_NAME_SPACE+"searchUserCount",object);
	}
	/**
	 * 根据条件查询用户
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchUser(EdmSubscribeUserGroup object){
		List<Map<String,Object>> result = super.queryForListForReport(SEARCH_USER,object);
		return result;
	}
	public Long searchUserBySqlCount(final String sql){
			Long result = (Long)queryForObject("ONLINE_MARKETING.searchUserBySqlCount", sql);
	        LOG.info("result.size = "+result); 
	        return result;
	}
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> searchUserBySql(final String sql,final Integer startRow,final Integer endRow){
		List<Map<String,Object>> result = super.queryForListForReport(SEARCH_USER_BY_SQL, appendPageSql(sql,startRow,endRow));
        LOG.info("result.size = "+result.size()); 
        if(null!=result && !result.isEmpty()){
        	LOG.info("Map String = "+result.get(0).toString());
        }
	    return result;
	}
	private String appendPageSql(final String sql,final Integer startRow,final Integer endRow){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ( SELECT T.*,ROWNUM RN FROM (");
		sb.append(sql);
		sb.append(") T WHERE ROWNUM<=").append(endRow);
		sb.append(") T WHERE RN >=").append(startRow);
		return sb.toString();
	}
}
