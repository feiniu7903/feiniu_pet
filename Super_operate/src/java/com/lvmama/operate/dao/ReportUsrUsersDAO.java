package com.lvmama.operate.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.user.UserUser;

public class ReportUsrUsersDAO extends BaseIbatisDAO {
    
    /**
     * 查询Edm当中需要的用户列表
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<UserUser> selectEdmList(Map<String,Object> parameters)
    {
    	return super.queryForListForReport("EDM_USR_USERS.selectEdmAll", parameters);
    }
    
    /**
     * 查询Edm当中用户列表的总数量
     * @param parameters
     * @return
     */
    public long selectEdmCount(Map<String,Object> parameters)
    {
    	return (Long)super.queryForObject("EDM_USR_USERS.selectEdmCount",parameters);
    }
}
