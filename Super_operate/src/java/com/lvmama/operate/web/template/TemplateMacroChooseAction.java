package com.lvmama.operate.web.template;
/**
 * 根据模板名称模糊查询模板列表
 * @author shangzhengyuan
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.api.Bandbox;

import com.lvmama.comm.pet.po.edm.EdmSubscribeTemplate;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.service.EdmSubscribeTemplateService;
import com.lvmama.operate.web.BaseAction;

public class TemplateMacroChooseAction extends BaseAction {

     private static final long serialVersionUID = 5417054759358904614L;
     
     /**
      * EDM模板服务接口
      * @author shangzhengyuan
      */
     private EdmSubscribeTemplateService edmSubscribeTemplateService;
     /**
      * 模板列表控件
      */
     private Bandbox bandTemplate;
     /**
      * 模板名称
      */
     private String tempName;
     /**
      * 模板列表
      */
     private List<EdmSubscribeTemplate> tempList;
     /**
      * 页面显示前操作
      */
     public void doAfter() throws Exception {
          if (StringUtils.isNotEmpty(tempName)) {
               bandTemplate.setValue(tempName);
          }
     }
     
     /**
      * 查找模板列表
      * @author shangzhengyuan
      * @param event
      */
     public void changeTemplate(InputEvent event) {
          String tempName = event.getValue();
          Map<String,Object> parameters = new HashMap<String,Object>();
          parameters.put("tempName",tempName);
          parameters.put("tempStatus", Constant.EDM_STATUS_TYPE.Y.name());
          tempList = edmSubscribeTemplateService.search(parameters);
     }
     
     public EdmSubscribeTemplateService getEdmSubscribeTemplateService() {
          return edmSubscribeTemplateService;
     }

     public void setEdmSubscribeTemplateService(
               EdmSubscribeTemplateService edmSubscribeTemplateService) {
          this.edmSubscribeTemplateService = edmSubscribeTemplateService;
     }

     public Bandbox getBandTemplate() {
          return bandTemplate;
     }

     public void setBandTemplate(Bandbox bandTemplate) {
          this.bandTemplate = bandTemplate;
     }

     public String getTempName() {
          return tempName;
     }

     public void setTempName(String tempName) {
          this.tempName = tempName;
     }

     public List<EdmSubscribeTemplate> getTempList() {
          return tempList;
     }

     public void setTempList(List<EdmSubscribeTemplate> tempList) {
          this.tempList = tempList;
     }

}
