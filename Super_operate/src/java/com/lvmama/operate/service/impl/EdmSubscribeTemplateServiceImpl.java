package com.lvmama.operate.service.impl;
/**
 * EDM模板服务实现类
 * @author shangzhengyuan
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeTemplate;
import com.lvmama.operate.dao.EdmSubscribeTemplateDAO;
import com.lvmama.operate.service.EdmSubscribeTemplateService;
import com.lvmama.operate.util.EdmLogUtil;
import com.lvmama.operate.util.LogViewUtil;

public class EdmSubscribeTemplateServiceImpl implements
          EdmSubscribeTemplateService {

     private EdmSubscribeTemplateDAO edmSubscribeTemplateDAO;
     
     @Override
     public Long count(Map<String, Object> parameters) {
          return edmSubscribeTemplateDAO.count(parameters);
     }

     @Override
     public Long insert(EdmSubscribeTemplate object) {
          Long key = edmSubscribeTemplateDAO.insert(object);
          EdmLogUtil.insert("EDM_SUBSCRIBE_TEMPLATE",object.getCreateUser(),key);
          return key;
     }

     @Override
     public List<EdmSubscribeTemplate> search(Map<String, Object> parameters) {
          return edmSubscribeTemplateDAO.search(parameters);
     }

     @Override
     public int update(EdmSubscribeTemplate object) {
          Map<String,Object> param = new HashMap<String,Object>();
          param.put("tempId", object.getTempId());
          List<EdmSubscribeTemplate> list = search(param);
          if(null == list || (null!=list && list.size()==0) ){
               return 0;
          }
          EdmSubscribeTemplate oldTemplate = list.get(0);
          int result = edmSubscribeTemplateDAO.update(object);
          EdmLogUtil.update("EDM_SUBSCRIBE_TEMPLATE",object.getTempId(),
        		  object.getUpdateUser(),
                    LogViewUtil.logEditStr(
                    		"旧模板路径："+oldTemplate.getTempUrl()
                    		+"\r\n旧模板状态："+oldTemplate.getTempStatus(), 
                            "新模板路径："+object.getTempUrl()+
                            "\r\n新模板状态："+object.getTempStatus()));
          return result;
     }

     public EdmSubscribeTemplateDAO getEdmSubscribeTemplateDAO() {
          return edmSubscribeTemplateDAO;
     }

     public void setEdmSubscribeTemplateDAO(
               EdmSubscribeTemplateDAO edmSubscribeTemplateDAO) {
          this.edmSubscribeTemplateDAO = edmSubscribeTemplateDAO;
     }
}
