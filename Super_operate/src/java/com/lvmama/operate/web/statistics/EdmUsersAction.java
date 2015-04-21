package com.lvmama.operate.web.statistics;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.Configuration;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.operate.service.EdmUsersService;
import com.lvmama.operate.service.ExeclExportService;
import com.lvmama.operate.util.CodeSet;
import com.lvmama.operate.web.BaseAction;
/**
 * @author yangbin
 *
 */
@SuppressWarnings("serial")
public class EdmUsersAction extends BaseAction {
     private static final String EMAIL_TYPE_KEY="EDM_EMAIL_TYPE";
     private static final String EDM_TXT_SPLIT_KEY = "##";
     private EdmUsersService edmUsersService;
     
     private List<ComProvince> capitals;//省份信息
     private List<ComCity> cities;
     private List<CodeItem> codeItemList;
     
     private Map<String,Object> searchConds=new HashMap<String,Object>();
     private List<UserUser> users=Collections.emptyList();
     private long users_count=0L;
     
     @Override
     protected void doAfter() throws Exception {
          // TODO Auto-generated method stub
          super.doAfter();
          codeItemList=CodeSet.getInstance().getCachedCodeList(EMAIL_TYPE_KEY);
          codeItemList.add(0,new CodeItem(null,"请选择"));
          searchConds.put("checkMobile", "false");
     }

     @Override
     protected void doBefore() throws Exception {
          // TODO Auto-generated method stub
          super.doBefore();
          initCapitals();
     }

     private void initCapitals()
     {
          capitals=edmUsersService.selectProvinceList(); 
          cities=new Vector<ComCity>();
     }
     /**
      * 搜索数据列表
      */
     public void forwardSearch()
     {
          if(!CollectionUtils.isEmpty(users))
          {
               users.clear();
          }
          buildSearchCons();
          if(null!=searchConds.get("isCheckMobile")&&searchConds.get("isCheckMobile").toString().trim().length()==0){
               searchConds.remove("isCheckMobile");
          }
          if(null!=searchConds.get("isCheckEmail")&&searchConds.get("isCheckEmail").toString().trim().length()==0){
               searchConds.remove("isCheckEmail");
          }
          if(!StringUtil.isEmptyString((String)searchConds.get("sensitiveSegment"))){
        	  String[] array = ((String)searchConds.get("sensitiveSegment")).split("-");
        	  if(!StringUtil.isEmptyString(array[0])){
        		  searchConds.put("sensitiveFrom", array[0]);
        	  }
        	  if(array.length ==2 && !StringUtil.isEmptyString(array[1])){
        		  searchConds.put("sensitiveTo", array[1]);
        	  }
        	  searchConds.remove("sensitiveSegment");
          }
          users_count=edmUsersService.selectUsersCount(searchConds);
          Paging paging=(Paging)getComponent().getFellow("_paging");
          paging.setTotalSize((int)users_count);
          
          
          searchConds.put("_startRow", paging.getActivePage()*paging.getPageSize());
          searchConds.put("_endRow", (paging.getActivePage()+1)*paging.getPageSize());
          users=edmUsersService.selectUsers(searchConds);
     }
     final static String MOBILE_NUMBER="mobileNumber";
     private void buildSearchCons()
     {
          if(searchConds.containsKey(MOBILE_NUMBER))
          {
               String mobile=(String)searchConds.get(MOBILE_NUMBER);
               if(StringUtils.isEmpty(mobile))
               {
                    searchConds.remove(MOBILE_NUMBER);
               }else if(!StringUtil.validMobileNumber((String)searchConds.get("mobileNumber")))
                    throw new WrongValueException(getComponent().getFellow("mobileNumberTextBox"), "手机号不正确");
          }
          if(searchConds.containsKey("regStart"))
          {
               searchConds.put("regStart", DateUtil.getDayStart((Date)searchConds.get("regStart")));
          }
          
          if(searchConds.containsKey("regEnd"))
          {
               searchConds.put("regEnd", DateUtil.getDayEnd((Date)searchConds.get("regEnd")));
          }
          
          if(searchConds.containsKey("loginStart"))
          {
               searchConds.put("loginStart", DateUtil.getDayStart((Date)searchConds.get("loginStart")));
          }
          
          if(searchConds.containsKey("loginEnd"))
          {
               searchConds.put("loginEnd", DateUtil.getDayEnd((Date)searchConds.get("loginEnd")));
          }
          
          if(searchConds.containsKey("loginStartNote"))
          {
               searchConds.put("loginStartNote", DateUtil.getDayStart((Date)searchConds.get("loginStartNote")));
          }
          
          if(searchConds.containsKey("loginEndNote"))
          {
               searchConds.put("loginEndNote", DateUtil.getDayEnd((Date)searchConds.get("loginEndNote")));
          }
          
          
          updateCity("capitalListBox", "capital");
          updateCity("cityListBox", "city");
          Iterator iterator = searchConds.keySet().iterator();
          while(iterator.hasNext()){
        	  String key = (String) iterator.next();
        	  Object o = searchConds.get(key);
        	  if(null == o || (null!= o && o instanceof String && StringUtil.isEmptyString((String)o))){
        		  searchConds.remove(o);
        	  }
          }
     }
     
     private void updateCity(String listBoxId,String mapKey)
     {
          Listbox lb=(Listbox)getComponent().getFellow(listBoxId);
          Listitem item=lb.getSelectedItem();
          if(item!=null)
          {
               Object o=item.getValue();
               if(o!=null)
               {
                    searchConds.put(mapKey, o.toString());
                    return;
               }
          }
          if(searchConds.containsKey(mapKey))
               searchConds.remove(mapKey);
     }

     
     public void search()
     {
          forwardSearch();          
     }
     
     /**
      * 改变省下拉框
      */
     public void changeCapitals()
     {
          Listbox lb=(Listbox)getComponent().getFellow("capitalListBox");
          Listitem item=lb.getSelectedItem();
          
          Object c=item.getValue();          
          if(c==null||(StringUtils.isEmpty(c.toString())))//为空的操作 
          {               
               cities=new Vector<ComCity>();
          }else
          {     
               cities=edmUsersService.selectCitiesByProvinceId(c.toString());
          }
          
     }
     
     
     

     public List<ComProvince> getCapitals() {
          return capitals;
     }

     public List<ComCity> getCities() {
          return cities;
     }

     public void setEdmUsersService(EdmUsersService edmUsersService) {
          this.edmUsersService = edmUsersService;
     }

     public Map<String, Object> getSearchConds() {
          return searchConds;
     }

     public void setSearchConds(Map<String, Object> searchConds) {
          this.searchConds = searchConds;
     }

     public List<UserUser> getUsers() {
          return users;
     }
     
     
     /**
      * 数据导出操作
      */
     public void exportUsers()
     {
          ExeclExportService service=new ExeclExportService(edmUsersService, searchConds);
          service.build();
          
          ByteArrayOutputStream buf=null;
          try
          {
               buf=new ByteArrayOutputStream();
               service.output(buf);
               Filedownload.save(buf.toByteArray(), "application/vnd.ms-excel","edm.xls");
               
               edmUsersService.saveNote(getLogin(),service.getTotal());
          }catch(Exception ex)
          {
               alert("生成Execl文件失败");
          }finally
          {
               if(buf!=null)
               {
                    try
                    {
                         buf.close();
                    }catch(IOException ex)
                    {
                         
                    }
               }
          }
     }
     
     public String getLogin()
     {
          Object obj = getSessionUser();
          if(obj!=null)
          {
               return getSessionUserName();
          }
          
          return "未登录";
     }
     
     public void checkDate(String start,String end)
     {
          Datebox startBox=(Datebox)getComponent().getFellow(start);
          Datebox endBox=(Datebox)getComponent().getFellow(end);
          
          if(startBox.getValue()==null||endBox.getValue()==null)
               return;
          
          if(startBox.getValue().after(endBox.getValue()))
          {
               throw new WrongValueException(endBox, "开始时间不能晚于结束时间");
          }
     }

     public void downloadTxt() {
          try {
               Configuration conf  = desktop.getWebApp().getConfiguration(); 
              conf.setUploadCharset(com.lvmama.operate.util.Constant.EN_CODEING);  
               searchConds.remove("_endRow");
               searchConds.remove("_startRow");
               users=edmUsersService.selectUsers(searchConds);
               StringBuffer sb = new StringBuffer();
               sb.append("emailAddr").append(EDM_TXT_SPLIT_KEY)
               .append("name")//.append(EDM_TXT_SPLIT_KEY)
          //     .append("sex").append(EDM_TXT_SPLIT_KEY)
          //     .append("mobile").append(EDM_TXT_SPLIT_KEY)
          //     .append("province").append(EDM_TXT_SPLIT_KEY)
          //     .append("city")
               .append("\r\n");
               for(int i=0;i<users.size();i++){
                    sb.append(edmUserInfo( users.get(i)));
               }
               Long currentTime=(new java.util.Date()).getTime();
               Filedownload.save(sb.toString().getBytes(com.lvmama.operate.util.Constant.EN_CODEING), "application/txt;charset="+com.lvmama.operate.util.Constant.EN_CODEING,"edmUser_"+currentTime+".txt");
               edmUsersService.saveNote(getLogin(),users.size());
               alert("下载成功");
          } catch (Exception e) {
               alert(e.getMessage());
          }
     }
     private StringBuffer edmUserInfo(final UserUser user){
          StringBuffer sb = new StringBuffer();
          final String BLANK_SIGN = " ";
          String emailAddr = user.getEmail();
          String name = user.getUserName();
          /**
          String sex = BLANK_SIGN ; 
          if(null!=user.getGender()){
               sex = "F".equalsIgnoreCase(user.getGender())?"女":"男";
          }
          String mobile = user.getMobileNumber();
          String userCity =BLANK_SIGN;
          String province =BLANK_SIGN;
          ComCity city=CityCache.getInstance().getCity(user.getCityId());
          if(city==null)
          {
               city=edmUsersService.getCity(user.getCityId());
               CityCache.getInstance().putCity(city);
          }
          if(city!=null)
          {     
               userCity=city.getName();
               ComCity capital=CityCache.getInstance().getCapital(city.getParent());
               if(capital==null)
               {
                    capital=edmUsersService.getCaptical(city.getParent());
                    CityCache.getInstance().putCapital(capital);
                    province = capital.getName();
               }
          }
          */
          sb.append(emailAddr).append(EDM_TXT_SPLIT_KEY)
          .append(name)//.append(EDM_TXT_SPLIT_KEY)
          //.append(sex).append(EDM_TXT_SPLIT_KEY)
          //.append(null!=mobile?mobile:BLANK_SIGN).append(EDM_TXT_SPLIT_KEY)
          //.append(province).append(EDM_TXT_SPLIT_KEY)
          //.append(userCity)
          .append("\r\n");
          return sb;
     }
     public List<CodeItem> getCodeItemList() {
          return codeItemList;
     }

     public void setCodeItemList(List<CodeItem> codeItemList) {
          this.codeItemList = codeItemList;
     }
}
 