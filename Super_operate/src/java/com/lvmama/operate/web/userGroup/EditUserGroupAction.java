package com.lvmama.operate.web.userGroup;
/**
 * 前台页面EDM用户组新增,修改操作ACTION
 * @author shangzhengyuan
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeUserGroup;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.service.EdmSubscribeUserGroupService;
import com.lvmama.operate.service.PlaceCityService;
import com.lvmama.operate.web.BaseAction;

public class EditUserGroupAction extends BaseAction {

     private static final long serialVersionUID = 1455944086706159428L;
     
     private static final String ADDRESS_SPLIT = ";";
     
     /**
      * EDM用户组服务接口
      */
     private EdmSubscribeUserGroupService edmSubscribeUserGroupService;
     
     /**
      * 地域服务接口
      */
     private PlaceCityService placeCityService;
     /**
      * EDM用户组PO
      */
     private EdmSubscribeUserGroup edmSubscribeUserGroup;
     /**
      * EDM用户组编号
      */
     private Long userGroupId;
     /**
      * 邮箱验证列表
      */
     private List<CodeItem> emailIsValidList = new ArrayList<CodeItem>();
     /**
      * 手机验证列表
      */
     private List<CodeItem> mobileIsValidList = new ArrayList<CodeItem>();
     /**
      * 地域列表
      */
     private List<CodeItem> addressList;
     
     /**
      * 敏感价值区间
      */
     private String sensitiveSegment;
     
     /**
      * 加载页面前查询要修改的EDM用户组信息
      * @author shangzhengyuan
      * createDate 20120208
      */
     public void doBefore(){
          if(null!=userGroupId){
               Map<String,Object> parameters = new HashMap<String,Object>();
               parameters.put("userGroupId", userGroupId);
               List<EdmSubscribeUserGroup> list = edmSubscribeUserGroupService.search(parameters);
               if(list != null && list.size() > 0){
                    edmSubscribeUserGroup = list.get(0);
                    getSensitive();
               }
          }else{
               edmSubscribeUserGroup = new EdmSubscribeUserGroup();
          }
          setEmailValidType();
          setMobileValidType();
          initAddressBox();
     }
     
     /**
      * 保存修改后的EDM用户组信息
      * @author shangzhengyuan
      * createDate 20120208
      */
     public void save(){
          if(validate()){
        	   setSensitive();
               selectFilterType(edmSubscribeUserGroup.getFilterType());
               String operator = getOperatorName();
               edmSubscribeUserGroup.setCreateUser(operator);
               edmSubscribeUserGroup.setUpdateUser(operator);
               StringBuffer userAddress = new StringBuffer();
               for(CodeItem item : addressList){
                    if("true".equals(item.getChecked())){
                         userAddress.append(item.getCode()).append(ADDRESS_SPLIT);
                    }
               }
               edmSubscribeUserGroup.setUserAddress(userAddress.toString());
               if(null!=userGroupId){
                    edmSubscribeUserGroupService.update(edmSubscribeUserGroup);
               }else{
                    edmSubscribeUserGroup.setUserGroupStatus(Constant.EDM_STATUS_TYPE.Y.name());
                    edmSubscribeUserGroupService.insert(edmSubscribeUserGroup);
               }
               super.refreshParent("search");
               super.closeWindow();
          }
     }
     public void checkAddress(String address, Boolean checked) {
          boolean isFor = false;
          for(int i=0;i<addressList.size();i++) {
               String code = addressList.get(i).getCode();
               if (!StringUtil.isEmptyString(code) && code.equals(address)) {
                    addressList.get(i).setChecked(checked.toString());
                    break;
               }else if(StringUtil.isEmptyString(code) && StringUtil.isEmptyString(address)){
                    addressList.get(i).setChecked(checked.toString());
                    isFor = true;
                    break;
               }
          }
          if(isFor){
               for(CodeItem item : addressList){
                    if(!StringUtil.isEmptyString(item.getCode())){
                         item.setChecked(Boolean.FALSE.toString());
                    }
               }
          }
     }
     public void selectFilterType(final String filterType){
          if(Constant.EDM_TIMING_TYPE.MARKETING_TYPE.name().equals(filterType)){
               edmSubscribeUserGroup.setUserGroupTrigger(null);
          }else{
               EdmSubscribeUserGroup group = new EdmSubscribeUserGroup();
               group.setUserGroupId(edmSubscribeUserGroup.getUserGroupId());
               group.setUserGroupName(edmSubscribeUserGroup.getUserGroupName());
               group.setFilterType(edmSubscribeUserGroup.getFilterType());
               group.setUserGroupTrigger(edmSubscribeUserGroup.getUserGroupTrigger());
               group.setUserGroupStatus(Constant.EDM_STATUS_TYPE.Y.name());
               edmSubscribeUserGroup = group;
          }
     }
     public EdmSubscribeUserGroupService getEdmSubscribeUserGroupService() {
          return edmSubscribeUserGroupService;
     }
     
     /**
      * 初始化邮箱验证下拉框
      * @author shangzhengyuan
      * 20120209
      */
     private void setEmailValidType(){
             setValidType(edmSubscribeUserGroup.getEmailIsValid(),emailIsValidList);
     }
     private void setMobileValidType(){
             setValidType(edmSubscribeUserGroup.getMobileIsValid(),mobileIsValidList);
     }
     private void setValidType(final String validateType,List<CodeItem> validList){
             CodeItem item0 = new CodeItem(null,"请选择");
             CodeItem item1 = new CodeItem("Y","已验证");
             CodeItem item2 = new CodeItem("N","未验证");
             validList.add(item0);
             validList.add(item1);
             validList.add(item2);
             if(null != edmSubscribeUserGroup){
                  for(CodeItem item : validList){
                       if(item.getCode()!=null && null!=validateType && item.getCode().equals(validateType)){
                            item.setChecked(Boolean.TRUE.toString());
                       }
                  }
             } 
     }
     /**
      * 根据用户组选择的省份显示选中地域
      * @author shangzhengyuan
      * 20120209
      */
     private void initAddressBox(){
          addressList = new ArrayList<CodeItem>();
          List<ComProvince> list = placeCityService.getProvinceList();
          for(ComProvince comProvince : list){
               if(!StringUtil.isEmptyString(comProvince.getProvinceId())){
                    CodeItem item = new CodeItem(comProvince.getProvinceId(),comProvince.getProvinceName());
                    addressList.add(item);
               }
          }
          List<String> selitems = new ArrayList<String>();
          if(null != edmSubscribeUserGroup && null!=edmSubscribeUserGroup.getUserAddress()){
               String[] addressArray = edmSubscribeUserGroup.getUserAddress().split(";");
               for(String value : addressArray){
                    selitems.add(value);
                    boolean isBreak = true;
                    inner:
                    if(isBreak){
                         for(CodeItem item : addressList){
                              if(value.equals(item.getCode())){
                                   item.setChecked("true");
                                   isBreak = false;
                                   break inner;
                              }
                         }
                    }
               }
          }
     }
     
     /**
      * 验证用户组信息
      * @author shangzhengyuan
      * 20120209
      * @return
      */
     private boolean validate(){
          if(StringUtil.isEmptyString(edmSubscribeUserGroup.getUserGroupName())){
               alert("请输入用户组名称");
               return Boolean.FALSE;
          }
          if(edmSubscribeUserGroup.getUserGroupName().length()>100){
               alert("名称过长");
               return Boolean.FALSE;
          }
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("precise", "Y");
          parameters.put("userGroupStatus", "Y");
          parameters.put("userGroupName", edmSubscribeUserGroup.getUserGroupName());
          Long count = edmSubscribeUserGroupService.count(parameters);
          if((count > 1 && null !=edmSubscribeUserGroup.getUserGroupId()) || (count > 0 && null == edmSubscribeUserGroup.getUserGroupId())){
               alert("用户组名称已存在，请输入其它名称");
               return Boolean.FALSE;
          }
          if(StringUtil.isEmptyString(edmSubscribeUserGroup.getFilterType())){
               alert("请选择用户组类型");
               return Boolean.FALSE;
          }
          if(Constant.EDM_TIMING_TYPE.MARKETING_TYPE.name().equals(edmSubscribeUserGroup.getFilterType()) && StringUtil.isEmptyString(edmSubscribeUserGroup.getUserSubscribeType())){
               alert("请选择订阅类型");
               return Boolean.FALSE;
          }
          if(Constant.EDM_TIMING_TYPE.TRIGGER_TYPE.name().equals(edmSubscribeUserGroup.getFilterType())&&StringUtil.isEmptyString(edmSubscribeUserGroup.getUserGroupTrigger())){
               alert("触发式用户组请输入正确的SQL");
               return Boolean.FALSE;
          }
          return Boolean.TRUE;
     }
     
     /**
      * 用户组设置敏感价值起止值
      */
     private void setSensitive(){
    	 if(!StringUtil.isEmptyString(sensitiveSegment) && null != edmSubscribeUserGroup && !sensitiveSegment.equals("null-null")){
    		 String[] sensitiveSegmentArray = sensitiveSegment.split("-");
    		 if(!StringUtil.isEmptyString(sensitiveSegmentArray[0]) && !sensitiveSegmentArray[0].trim().equals("null")){
				 edmSubscribeUserGroup.setSensitiveFrom(sensitiveSegmentArray[0]);
			 }
			 if(sensitiveSegmentArray.length == 2 && !StringUtil.isEmptyString(sensitiveSegmentArray[1]) && !sensitiveSegmentArray[1].trim().equals("null")){
				 edmSubscribeUserGroup.setSensitiveTo(sensitiveSegmentArray[1]);
			 }
    	 }
     }
     
     private void getSensitive(){
    	 if(null != edmSubscribeUserGroup){
    		 sensitiveSegment = edmSubscribeUserGroup.getSensitiveFrom()+"-"+edmSubscribeUserGroup.getSensitiveTo();
    	 }
     }
     
     public void setEdmSubscribeUserGroupService(
               EdmSubscribeUserGroupService edmSubscribeUserGroupService) {
          this.edmSubscribeUserGroupService = edmSubscribeUserGroupService;
     }

     public EdmSubscribeUserGroup getEdmSubscribeUserGroup() {
          return edmSubscribeUserGroup;
     }

     public void setEdmSubscribeUserGroup(EdmSubscribeUserGroup edmSubscribeUserGroup) {
          this.edmSubscribeUserGroup = edmSubscribeUserGroup;
     }

     public Long getUserGroupId() {
          return userGroupId;
     }

     public void setUserGroupId(Long userGroupId) {
          this.userGroupId = userGroupId;
     }

     public PlaceCityService getPlaceCityService() {
          return placeCityService;
     }

     public void setPlaceCityService(PlaceCityService placeCityService) {
          this.placeCityService = placeCityService;
     }

     public List<CodeItem> getEmailIsValidList() {
          return emailIsValidList;
     }

     public void setEmailIsValidList(List<CodeItem> emailIsValidList) {
          this.emailIsValidList = emailIsValidList;
     }

     public List<CodeItem> getAddressList() {
          return addressList;
     }

     public void setAddressList(List<CodeItem> addressList) {
          this.addressList = addressList;
     }

	 public List<CodeItem> getMobileIsValidList() {
	        return mobileIsValidList;
	}
	
	public void setMobileIsValidList(List<CodeItem> mobileIsValidList) {
	        this.mobileIsValidList = mobileIsValidList;
	}
	
	public String getSensitiveSegment() {
		return sensitiveSegment;
	}
	
	public void setSensitiveSegment(String sensitiveSegment) {
		this.sensitiveSegment = sensitiveSegment;
	}
}
