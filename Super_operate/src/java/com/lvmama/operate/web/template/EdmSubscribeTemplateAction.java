package com.lvmama.operate.web.template;
/**
 * EDM模板查询，删除ACTION
 * @author shangzhengyuan
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeTemplate;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.service.EdmSubscribeTemplateService;
import com.lvmama.operate.util.ZkMessage;
import com.lvmama.operate.util.ZkMsgCallBack;
import com.lvmama.operate.web.BaseAction;

public class EdmSubscribeTemplateAction extends BaseAction {

     private static final long serialVersionUID = -8793716556308207853L;
     
     private EdmSubscribeTemplateService edmSubscribeTemplateService;
     
     private Map<String, Object> searchConds = new HashMap<String, Object>(); 
     
     private List<EdmSubscribeTemplate> list;
     
     private EdmSubscribeTemplate template;
     
     /**
      * 根据条件查询EDM模板列表
      */
     public void search(){
          searchConds.put("tempStatus", Constant.EDM_STATUS_TYPE.Y.name());
          searchConds = initialPageInfoByMap(edmSubscribeTemplateService.count(searchConds),searchConds);
          list = edmSubscribeTemplateService.search(searchConds);
     }
     
     /**
      * 根据模板编号删除模板
      * @param params
      */
     public void delete(final Map<String,Object> params){ 
          String tempName=(String)params.get("tempName");
          Long tempId = (Long)params.get("tempId");
          template = getCurrentTemplate(tempId);
          if(null != template){
               template.setTempStatus(Constant.EDM_STATUS_TYPE.N.name());
               template.setUpdateUser(getOperatorName());
               ZkMessage.showQuestion("您要删除【"+tempName+"】, 请确认?", new ZkMsgCallBack(){
                    public void  execute(){
                         int result = edmSubscribeTemplateService.update(template);
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
               alert("根据名称【"+tempName+"】没有找到要修改的模板");
          }
     }
     private EdmSubscribeTemplate getCurrentTemplate(Long tempId){
          if(null != list && null != tempId){
               for(EdmSubscribeTemplate template: list){
                    if(template.getTempId().equals(tempId)){
                         return template;
                    }
               }
          }else if(null != tempId){
               Map<String,Object> parameters = new HashMap<String,Object>();
               parameters.put("userGroupId", tempId);
               List<EdmSubscribeTemplate> result = edmSubscribeTemplateService.search(parameters);
               for(EdmSubscribeTemplate template : result){
                    return template;
               }
          }
          return null;
     }
     public EdmSubscribeTemplateService getEdmSubscribeTemplateService() {
          return edmSubscribeTemplateService;
     }

     public void setEdmSubscribeTemplateService(
               EdmSubscribeTemplateService edmSubscribeTemplateService) {
          this.edmSubscribeTemplateService = edmSubscribeTemplateService;
     }

     public Map<String, Object> getSearchConds() {
          return searchConds;
     }

     public void setSearchConds(Map<String, Object> searchConds) {
          this.searchConds = searchConds;
     }

     public List<EdmSubscribeTemplate> getList() {
          return list;
     }

     public void setList(List<EdmSubscribeTemplate> list) {
          this.list = list;
     }

     public EdmSubscribeTemplate getTemplate() {
          return template;
     }

     public void setTemplate(EdmSubscribeTemplate template) {
          this.template = template;
     }

}
