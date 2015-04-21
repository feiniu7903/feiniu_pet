package com.lvmama.operate.web.statistics;
/**
 *  查询订阅用户信息
 */
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zul.Filedownload;

import com.lvmama.comm.pet.po.edm.EdmSubscribe;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.edm.EdmSubscribeService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.util.CodeSet;
import com.lvmama.operate.web.BaseAction;
public class EdmSubscribeAction extends BaseAction {
     /**
      * 
      */
     private static final long serialVersionUID = 6488483771010268379L;
     private static final String EMAIL_TYPE_KEY="EDM_EMAIL_TYPE";
     private EdmSubscribeService edmSubscribeService;
     private Map<String, Object> searchConds = new HashMap<String, Object>(); // 查询条件映射
     private List<EdmSubscribe> result;
     private List<CodeItem> codeItemList;
     /**
      * 初始化页面前加载
      */
     protected void doBefore() throws Exception {
          codeItemList=CodeSet.getInstance().getCachedCodeList(EMAIL_TYPE_KEY);
          codeItemList.add(0,new CodeItem(null,"请选择"));
     }
     /**
      * 查询订阅用户列表
      */
     public void query(){
          if(validate()){
               Date endCreateDate = (Date)searchConds.get("endCreateDate");
               if(null != endCreateDate){
        	   searchConds.put("endCreateDate",DateUtil.getDayEnd(endCreateDate));
               }
               searchConds = initialPageInfoByMap(edmSubscribeService.count(searchConds), searchConds);
               result = edmSubscribeService.query(searchConds);
          }
     }
     public void download() {
          try {
               File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/edmSubscribeUsers.xls");
               String templateFileName = templateResource.getAbsolutePath();
               String destFileName = Constant.getTempDir() + "/edmSubscribeUsers.xls";
               
               Map<String,Object> param = new HashMap<String,Object>();
               searchConds.remove("_endRow");
               searchConds.remove("_startRow");
               result = edmSubscribeService.query(searchConds);
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
     
     public void downloadTxt() {
          try {
               Configuration conf  = desktop.getWebApp().getConfiguration(); 
              conf.setUploadCharset(com.lvmama.operate.util.Constant.EN_CODEING); 
               searchConds.remove("_endRow");
               searchConds.remove("_startRow");
               result = edmSubscribeService.query(searchConds);
               StringBuffer sb = new StringBuffer("emailAddr\r\n");
               for(int i=0;i<result.size();i++){
                    EdmSubscribe subscriber = result.get(i);
                    sb.append(subscriber.getEmail())
                    .append("\r\n");
               }
               Long currentTime=(new java.util.Date()).getTime();
               Filedownload.save(sb.toString().getBytes(com.lvmama.operate.util.Constant.EN_CODEING), "application/force-download;charset="+com.lvmama.operate.util.Constant.EN_CODEING,"edmSubscribeUser_"+currentTime+".txt");
               alert("下载成功");
          } catch (Exception e) {
               alert(e.getMessage());
          }
     }
     private boolean validate(){
          if(null == searchConds.get("beginCreateDate") && null == searchConds.get("endCreateDate")){
               alert("请选择时间段");
               return Boolean.FALSE;
          }
          Date beginCreateDate = (Date) searchConds.get("beginCreateDate");
          Date endCreateDate = (Date)searchConds.get("endCreateDate");
          if(null != beginCreateDate && null !=endCreateDate && beginCreateDate.after(endCreateDate)){
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
     public EdmSubscribeService getEdmSubscribeService() {
          return edmSubscribeService;
     }

     public void setEdmSubscribeService(EdmSubscribeService edmSubscribeService) {
          this.edmSubscribeService = edmSubscribeService;
     }

     public Map<String, Object> getSearchConds() {
          return searchConds;
     }

     public void setSearchConds(Map<String, Object> searchConds) {
          this.searchConds = searchConds;
     }

     public List<EdmSubscribe> getResult() {
          return result;
     }

     public void setResult(List<EdmSubscribe> result) {
          this.result = result;
     }
     public List<CodeItem> getCodeItemList() {
          return codeItemList;
     }
     public void setCodeItemList(List<CodeItem> codeItemList) {
          this.codeItemList = codeItemList;
     }
}
