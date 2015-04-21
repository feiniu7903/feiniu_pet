package com.lvmama.operate.web.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeTemplate;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.service.EdmSubscribeTemplateService;
import com.lvmama.operate.web.BaseAction;

public class EditTemplateAction extends BaseAction {

     /**
      * 
      */
     private static final long serialVersionUID = 6467169644262513073L;
     
     private EdmSubscribeTemplateService edmSubscribeTemplateService;
     
     private EdmSubscribeTemplate template;
     
     private Long tempId;
     /**
      * 修改，新增模板页面加载操作
      * @author shangzhengyuan
      */
     public void doBefore(){
          if(null!=tempId){
               Map<String,Object> parameters = new HashMap<String,Object>();
               parameters.put("tempId", tempId);
               List<EdmSubscribeTemplate> list = edmSubscribeTemplateService.search(parameters);
               if(null != list && list.size() > 0){
                    template = list.get(0);
               }
          }else{
               template = new EdmSubscribeTemplate();
          }
     }
     
     /**
      * 修改，新增模板
      * @author shangzhengyuan
      */
     public void save(){
          if(validate()){
               String operator = getOperatorName();
               template.setCreateUser(operator);
               template.setUpdateUser(operator);
               if(null != tempId){
                    edmSubscribeTemplateService.update(template);
               }else{
                    template.setTempStatus(Constant.EDM_STATUS_TYPE.Y.name());
                    edmSubscribeTemplateService.insert(template);
               }
               super.refreshParent("search");
               super.closeWindow();
          }
     }
     
     /**
      * 验证模板名称，路径是否为空
      * @return
      */
     private boolean validate(){
          String tempName = template.getTempName();
          String tempUrl = template.getTempUrl();
          if(StringUtil.isEmptyString(tempName)){
               alert("请输入模板名称");
               return Boolean.FALSE;
          }
          if(tempName.length()>100){
               alert("模板名称过长");
               return Boolean.FALSE;
          }
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("precise", "Y");
          parameters.put("tempStatus", "Y");
          parameters.put("tempName", tempName);
          Long count = edmSubscribeTemplateService.count(parameters);
          if((count > 1 && null != template.getTempId()) || (count > 0 && null == template.getTempId())){
               alert("模板名称已存在，请输入其它名称");
               return Boolean.FALSE;
          }
          if(StringUtil.isEmptyString(tempUrl)){
               alert("请输入模板路径");
               return Boolean.FALSE;
          }
          if(!tempUrl.matches("^(http://)?\\S+$")){
               alert("请输入合法的URL地址");
               return Boolean.FALSE;
          }
          if(tempUrl.length()>400){
               alert("URL地址过长");
               return Boolean.FALSE;
          }
          return Boolean.TRUE;
     }
     public EdmSubscribeTemplateService getEdmSubscribeTemplateService() {
          return edmSubscribeTemplateService;
     }

     public void setEdmSubscribeTemplateService(
               EdmSubscribeTemplateService edmSubscribeTemplateService) {
          this.edmSubscribeTemplateService = edmSubscribeTemplateService;
     }

     public EdmSubscribeTemplate getTemplate() {
          return template;
     }

     public void setTemplate(EdmSubscribeTemplate template) {
          this.template = template;
     }

     public Long getTempId() {
          return tempId;
     }

     public void setTempId(Long tempId) {
          this.tempId = tempId;
     }
}
