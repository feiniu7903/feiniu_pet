package com.lvmama.operate.web.statistics;
/**
 * 查询退订用户邮件信息
 */
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.zkoss.zul.Filedownload;

import com.lvmama.comm.pet.po.edm.EdmSubscribeInfo;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.edm.EdmSubscribeInfoService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.util.CodeSet;
import com.lvmama.operate.web.BaseAction;

public class EdmUnSubscribeAction extends BaseAction {
     /**
      * 
      */
     private static final long serialVersionUID = -8316212479638751859L;
     private static final String EMAIL_TYPE_KEY="EDM_EMAIL_TYPE";
     private EdmSubscribeInfoService edmSubscribeInfoService;
     private Map<String, Object> searchConds = new HashMap<String, Object>(); // 查询条件映射
     private List<EdmSubscribeInfo> result;
     private List<CodeItem> codeItemList;
     /**
      * 初始化页面前加载
      */
     protected void doBefore() throws Exception {
          codeItemList=CodeSet.getInstance().getCachedCodeList(EMAIL_TYPE_KEY);
          codeItemList.add(0,new CodeItem(null,"请选择"));
     }
     /**
      * 查询退订用户邮件信息列表
      */
     public void query(){
          if(validate()){
               Date endCancelDate = (Date)searchConds.get("endCancelDate");
               if(null != endCancelDate){
        	  searchConds.put("endCancelDate",DateUtil.getDayEnd(endCancelDate));
               }
               searchConds = initialPageInfoByMap(edmSubscribeInfoService.count(searchConds), searchConds);
               result = edmSubscribeInfoService.query(searchConds);
          }
     }
     public void download() {
          try {
               File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/edmUnSubscribeUsers.xls");
               String templateFileName = templateResource.getAbsolutePath();
               String destFileName = Constant.getTempDir() + "/edmUnSubscribeUsers.xls";
               
               Map<String,Object> param = new HashMap<String,Object>();
               searchConds.remove("_endRow");
               searchConds.remove("_startRow");
               result = edmSubscribeInfoService.query(searchConds);
               param.put("result", result);
               param.put("floorCount", searchConds.get("floorCount"));
               
               XLSTransformer transformer = new XLSTransformer();
               transformer.transformXLS(templateFileName, param, destFileName);
               File file = new File(destFileName);
               if (file != null && file.exists()) {
                    Filedownload.save(file, "application/vnd.ms-excel");
               } else {
                    alert("下载失败");
                    return;
               }
               alert("下载成功");
          } catch (Exception e) {
               alert(e.getMessage());
          }
     }
     
     private boolean validate(){
          if(null == searchConds.get("beginCancelDate") && null == searchConds.get("endCancelDate")){
               alert("请选择时间段");
               return Boolean.FALSE;
          }
          Date beginCancelDate = (Date) searchConds.get("beginCancelDate");
          Date endCancelDate = (Date)searchConds.get("endCancelDate");
          if(null != beginCancelDate && null !=endCancelDate && beginCancelDate.after(endCancelDate)){
              alert("开始时间不能大于结束时间");
              return Boolean.FALSE;
          }
          /**
          if(null == searchConds.get("type")){
               alert("请选择订阅类型");
               return Boolean.FALSE;
          }
          */
          return Boolean.TRUE;
     }
     public EdmSubscribeInfoService getEdmSubscribeInfoService() {
          return edmSubscribeInfoService;
     }
     public void setEdmSubscribeInfoService(
               EdmSubscribeInfoService edmSubscribeInfoService) {
          this.edmSubscribeInfoService = edmSubscribeInfoService;
     }
     public Map<String, Object> getSearchConds() {
          return searchConds;
     }
     public void setSearchConds(Map<String, Object> searchConds) {
          this.searchConds = searchConds;
     }
     public List<EdmSubscribeInfo> getResult() {
          return result;
     }
     public void setResult(List<EdmSubscribeInfo> result) {
          this.result = result;
     }
     public List<CodeItem> getCodeItemList() {
          return codeItemList;
     }
     public void setCodeItemList(List<CodeItem> codeItemList) {
          this.codeItemList = codeItemList;
     }
}
