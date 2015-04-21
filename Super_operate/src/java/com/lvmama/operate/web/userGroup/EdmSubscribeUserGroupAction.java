package com.lvmama.operate.web.userGroup;
/**
 * 前台页面EDM用户组查询，新增，修改，删除操作ACTION
 * @author shangzhengyuan
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeUserGroup;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.service.EdmSubscribeUserGroupService;
import com.lvmama.operate.util.ZkMessage;
import com.lvmama.operate.util.ZkMsgCallBack;
import com.lvmama.operate.web.BaseAction;
//import org.apache.log4j.Logger;

public class EdmSubscribeUserGroupAction extends BaseAction {
     //private static final Logger log = Logger.getLogger(EdmSubscribeUserGroupAction.class);
     private static final long serialVersionUID = 7790886266664999188L;
     
     private EdmSubscribeUserGroupService edmSubscribeUserGroupService;
     private Map<String, Object> searchConds = new HashMap<String, Object>(); 
     private EdmSubscribeUserGroup edmSubscribeUserGroup;
     private List<EdmSubscribeUserGroup> list;
     
     /**
      * 根据条件查询EDM用户组列表
      */
     public void search(){
          searchConds.put("userGroupStatus", Constant.EDM_STATUS_TYPE.Y.name());
          searchConds = initialPageInfoByMap(edmSubscribeUserGroupService.count(searchConds),searchConds);
          list = edmSubscribeUserGroupService.search(searchConds);
     }
     
     /**
      * 根据用户组编号删除用户组
      * @param params
      */
     public void delete(final Map<String,Object> params){ 
          String userGroupName=(String)params.get("userGroupName");
          Long userGroupId = (Long)params.get("userGroupId");
          edmSubscribeUserGroup =getCurrentGroup(userGroupId);
          if(null != edmSubscribeUserGroup){
               edmSubscribeUserGroup.setUserGroupStatus(Constant.EDM_STATUS_TYPE.N.name());
               edmSubscribeUserGroup.setUpdateUser(getOperatorName());
               ZkMessage.showQuestion("您要删除【"+userGroupName+"】, 请确认?", new ZkMsgCallBack(){
                    public void  execute(){
                         int result = edmSubscribeUserGroupService.update(edmSubscribeUserGroup);
                         if(result == 1){
                              alert("修改成功");
                         }else{
                              alert("修改失败");
                         }
                         refreshComponent("search");
                    }
               }, new ZkMsgCallBack(){
                    public void  execute(){
                    }
               });
          }else{
               alert("没有找到要修改的用户组【"+userGroupName+"】");
          }
          
     }
     private EdmSubscribeUserGroup getCurrentGroup(Long userGroupId){
          if(null != list && null != userGroupId){
               for(EdmSubscribeUserGroup group: list){
                    if(group.getUserGroupId().equals(userGroupId)){
                         return group;
                    }
               }
          }else if(null != userGroupId){
               Map<String,Object> parameters = new HashMap<String,Object>();
               parameters.put("userGroupId", userGroupId);
               List<EdmSubscribeUserGroup> result = edmSubscribeUserGroupService.search(parameters);
               for(EdmSubscribeUserGroup group : result){
                    return group;
               }
          }
          return null;
     }
     public EdmSubscribeUserGroupService getEdmSubscribeUserGroupService() {
          return edmSubscribeUserGroupService;
     }
     public void setEdmSubscribeUserGroupService(
               EdmSubscribeUserGroupService edmSubscribeUserGroupService) {
          this.edmSubscribeUserGroupService = edmSubscribeUserGroupService;
     }
     public Map<String, Object> getSearchConds() {
          return searchConds;
     }
     public void setSearchConds(Map<String, Object> searchConds) {
          this.searchConds = searchConds;
     }
     public EdmSubscribeUserGroup getEdmSubscribeUserGroup() {
          return edmSubscribeUserGroup;
     }
     public void setEdmSubscribeUserGroup(EdmSubscribeUserGroup edmSubscribeUserGroup) {
          this.edmSubscribeUserGroup = edmSubscribeUserGroup;
     }
     public List<EdmSubscribeUserGroup> getList() {
          return list;
     }
     public void setList(List<EdmSubscribeUserGroup> list) {
          this.list = list;
     }

}
