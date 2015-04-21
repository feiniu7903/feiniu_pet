package com.lvmama.operate.web.userGroup;
/**
 * 根据用户组名称模糊查询用户组列表
 * @author shangzhengyuan
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.api.Bandbox;

import com.lvmama.comm.pet.po.edm.EdmSubscribeUserGroup;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.service.EdmSubscribeUserGroupService;
import com.lvmama.operate.web.BaseAction;

public class UserGroupMacroChooseAction extends BaseAction {

     private static final long serialVersionUID = 8073374182404736237L;
     
     /**
      * EDM用户组服务接口
      */
     private EdmSubscribeUserGroupService edmSubscribeUserGroupService;
     
     /**
      * 绑定组件
      */
     private Bandbox bandUserGroup;
     
     /**
      * 用户组名称
      */
     private String userGroupName;
     
     /**
      * 用户组列表
      */
     private List<EdmSubscribeUserGroup> userGroupList;
     
     /**
      * 页面显示前操作
      */
     public void doAfter() throws Exception {
          if (StringUtils.isNotEmpty(userGroupName)) {
               bandUserGroup.setValue(userGroupName);
          }
     }
     
     /**
      * 查找用户组列表
      * @author shangzhengyuan
      * @param event
      */
     public void changeUserGroup(InputEvent event) {
          String userGroupName = event.getValue();
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("userGroupName",userGroupName);
          parameters.put("userGroupStatus", Constant.EDM_STATUS_TYPE.Y.name());
          userGroupList = edmSubscribeUserGroupService.search(parameters);
     }

     public EdmSubscribeUserGroupService getEdmSubscribeUserGroupService() {
          return edmSubscribeUserGroupService;
     }

     public void setEdmSubscribeUserGroupService(
               EdmSubscribeUserGroupService edmSubscribeUserGroupService) {
          this.edmSubscribeUserGroupService = edmSubscribeUserGroupService;
     }

     public Bandbox getBandUserGroup() {
          return bandUserGroup;
     }

     public void setBandUserGroup(Bandbox bandUserGroup) {
          this.bandUserGroup = bandUserGroup;
     }

     public String getUserGroupName() {
          return userGroupName;
     }

     public void setUserGroupName(String userGroupName) {
          this.userGroupName = userGroupName;
     }

     public List<EdmSubscribeUserGroup> getUserGroupList() {
          return userGroupList;
     }

     public void setUserGroupList(List<EdmSubscribeUserGroup> userGroupList) {
          this.userGroupList = userGroupList;
     }
}
