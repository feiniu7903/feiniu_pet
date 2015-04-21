package com.lvmama.operate.service.impl;
/**
 * EDM用户组服务实现类
 * @author shangzhengyuan
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeUserGroup;
import com.lvmama.operate.dao.EdmSubscribeUserGroupDAO;
import com.lvmama.operate.dao.OnlineMarketingDAO;
import com.lvmama.operate.service.EdmSubscribeUserGroupService;
import com.lvmama.operate.util.EdmLogUtil;
import com.lvmama.operate.util.LogViewUtil;

public class EdmSubscribeUserGroupServiceImpl implements
          EdmSubscribeUserGroupService {
     
     private EdmSubscribeUserGroupDAO edmSubscribeUserGroupDAO;
     private OnlineMarketingDAO onlineMarketingDAO;
     
     @Override
     public Long count(Map<String, Object> parameters) {
          return edmSubscribeUserGroupDAO.count(parameters);
     }

     @Override
     public Long insert(EdmSubscribeUserGroup object) {
          Long key = edmSubscribeUserGroupDAO.insert(object);
          EdmLogUtil.insert("EDM_SUBSCRIBE_USER_GROUP", object.getCreateUser(),key);
          return key;
     }

     @Override
     public List<EdmSubscribeUserGroup> search(Map<String, Object> parameters) {
          return edmSubscribeUserGroupDAO.search(parameters);
     }

     @Override
     public Long searchUserCount(final EdmSubscribeUserGroup object){
    	 return onlineMarketingDAO.searchUserCount(object);
     }
     @Override
     public List<Map<String, Object>> searchUser(EdmSubscribeUserGroup object) {
          return onlineMarketingDAO.searchUser(object);
     }
     public Long searchUserBySqlCount(final String sql){
    	 return onlineMarketingDAO.searchUserBySqlCount(sql);
     }
     @Override
     public List<Map<String, Object>> searchUserBySql(final String sql,final Integer startRow,final Integer endRow){
          return onlineMarketingDAO.searchUserBySql(sql,startRow,endRow);
     }
     @Override
     public int update(EdmSubscribeUserGroup object) {
          Map<String,Object> param = new HashMap<String,Object>();
          param.put("userGroupId", object.getUserGroupId());
          List<EdmSubscribeUserGroup> list = edmSubscribeUserGroupDAO.search(param);
          if(null == list || (null != list && list.size() == 0)){
               return 0;
          }
          EdmSubscribeUserGroup oldGroup = list.get(0);
          int result = edmSubscribeUserGroupDAO.update(object);
          EdmLogUtil.update("EDM_SUBSCRIBE_USER_GROUP", 
                    object.getUserGroupId(), object.getUpdateUser(),
                    LogViewUtil.logEditStr("用户组名称",oldGroup.getUserGroupName()," ")
                    +LogViewUtil.logEditStr("所属省份",oldGroup.getUserAddress()," ")
                    +LogViewUtil.logEditStr("订阅类型",oldGroup.getUserSubscribeType()," ")
                    +LogViewUtil.logEditStr("手机是否已验证",oldGroup.getMobileIsValid()," ")
                    +LogViewUtil.logEditStr("过滤手机?邮箱",oldGroup.getEmailOrMobile()," ")
                    +LogViewUtil.logEditStr("最后登录时间开始点",oldGroup.getFormatLastLoginFrom()," ")
                    +LogViewUtil.logEditStr("最后登录时间结束点",oldGroup.getFormatLastLoginTo()," ")
                    +LogViewUtil.logEditStr("邮箱是否已验证",oldGroup.getEmailIsValid()," ")
                    +LogViewUtil.logEditStr("注册时间开始点",oldGroup.getFormatRegisterDateFrom()," ")
                    +LogViewUtil.logEditStr("注册时间结束点",oldGroup.getFormatRegisterDateTo()," ")
                    +LogViewUtil.logEditStr("登录时间开始点",oldGroup.getFormatLoginDateFrom()," ")
                    +LogViewUtil.logEditStr("登录时间结束点",oldGroup.getFormatLoginDateTo()," ")
                    +LogViewUtil.logEditStr("用户组类型",oldGroup.getFilterType()," ")
                    +LogViewUtil.logEditStr("用户组状态",oldGroup.getUserGroupStatus()," "));
          return result;
     }

     public EdmSubscribeUserGroupDAO getEdmSubscribeUserGroupDAO() {
          return edmSubscribeUserGroupDAO;
     }

     public void setEdmSubscribeUserGroupDAO(
               EdmSubscribeUserGroupDAO edmSubscribeUserGroupDAO) {
          this.edmSubscribeUserGroupDAO = edmSubscribeUserGroupDAO;
     }
	public OnlineMarketingDAO getOnlineMarketingDAO() {
	        return onlineMarketingDAO;
	}
	
	public void setOnlineMarketingDAO(OnlineMarketingDAO onlineMarketingDAO) {
	        this.onlineMarketingDAO = onlineMarketingDAO;
	}

}
