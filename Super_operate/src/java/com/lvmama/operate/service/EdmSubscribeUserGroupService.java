package com.lvmama.operate.service;
/**
 * @version 1.0
 * @author shangzhengyuan
 * EDM用户组服务接口
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeUserGroup;

public interface EdmSubscribeUserGroupService {
     /**
      * 根据条件查询EDM用户组列表
      * @param parameters
      * @return
      */
     public List<EdmSubscribeUserGroup> search(Map<String,Object> parameters);
     /**
      * 根据条件查询EDM用户组总数
      * @param parameters
      * @return
      */
     public Long count(Map<String,Object> parameters);
     /**
      * 插入一条EDM用户组
      * @param object
      * @return
      */
     public Long insert(EdmSubscribeUserGroup object);
     /**
      * 更新一条EDM用户组信息
      * @param object
      * @return
      */
     public int update(EdmSubscribeUserGroup object);
     /**
      * 根据用户组中的查询条件取得用户信息
      * @param parameters
      * @return
      */
     public Long searchUserCount(EdmSubscribeUserGroup object);
     public List<Map<String,Object>> searchUser(EdmSubscribeUserGroup object);
     public Long searchUserBySqlCount(final String sql);
     public List<Map<String, Object>> searchUserBySql(final String sql,final Integer startRow,final Integer endRow);
}
