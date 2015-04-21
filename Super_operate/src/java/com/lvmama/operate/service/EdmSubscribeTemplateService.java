package com.lvmama.operate.service;
/**
 * @version 1.0
 * @author shangzhengyuan
 * EDM模板服务接口
 */
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeTemplate;

public interface EdmSubscribeTemplateService {
     /**
      * 根据条件查询EDM模板列表
      * @param parameters
      * @return
      */
     public List<EdmSubscribeTemplate> search(Map<String,Object> parameters);
     /**
      * 根据条件查询EDM模板总数
      * @param parameters
      * @return
      */
     public Long count(Map<String,Object> parameters);
     /**
      * 插入一条EDM模板
      * @param object
      * @return
      */
     public Long insert(EdmSubscribeTemplate object);
     /**
      * 更新一条EDM模板信息
      * @param object
      * @return
      */
     public int update(EdmSubscribeTemplate object);
}
