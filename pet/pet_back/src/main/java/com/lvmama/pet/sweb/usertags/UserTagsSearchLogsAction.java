package com.lvmama.pet.sweb.usertags;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.PlaceSearchPinyin;
import com.lvmama.comm.pet.po.usertags.UserTagsSearchLogs;
import com.lvmama.comm.pet.service.place.PlaceSearchPinyinService;
import com.lvmama.comm.pet.service.usertags.UserTagsSearchLogsService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PageElementModel;

@Results({
    @Result (name="search_tagLogs",location="/WEB-INF/pages/back/user/usertags/search_tagLogs.jsp"),
    @Result (name="ishide_tagLogs",location="/WEB-INF/pages/back/user/usertags/ishide_tagLogs.jsp")
})
public class UserTagsSearchLogsAction extends BackBaseAction{
    private static final long serialVersionUID = 1L;
    private UserTagsSearchLogsService userTagsSearchLogsService;
    private List<UserTagsSearchLogs> userTagsSearchLogsList;
    private UserTagsSearchLogs tagsLogs;
    private String TagLogsList;
    private String isHide="1";
    private String startDate;
    private String endDate;
    
    protected String pageSize;
    protected String memcachedparam;
    
    @Autowired
    private PlaceSearchPinyinService placeSearchPinyinService;
    
    private List<PageElementModel> userTagsFirstType = Collections.emptyList();
    Map<String,Object> param ;
    
    @Action("/tagLogs/search")
    public String execute() throws Exception {
        param = new HashMap<String, Object>();
        if(null != tagsLogs){
            param.put("searchLogsName",tagsLogs.getSearchLogsName());
        }
        param.put("isHide",isHide);
        if (null != startDate && !"".equals(startDate)) {
            param.put("startDate", convertToDate(startDate + " 00:00:00"));
        }
        if (null != endDate && !"".equals(endDate)) {
            param.put("endDate", convertToDate(endDate + " 23:59:59"));
        }
        initTagsTypePage();
        
        if("1".equals(isHide)){
            //去到从日志表添加到标签表
            return "search_tagLogs";
        }else{
            //去到已忽略日志关键字
            return "ishide_tagLogs";
        }
    }
    
    @Action("/tagLogs/isHideUpdate")
    public void isHideUpdate() throws Exception{
        try{
            param = new HashMap<String, Object>();
            if(null != TagLogsList ){
                String[] TagLogsId = TagLogsList.split(",");
                for (int i = 0; i < TagLogsId.length; i++) {
                    param.put("searchLogsId",TagLogsId[i]);
                    userTagsSearchLogsList = userTagsSearchLogsService.queryAllUserTagsLogByParam(param);
                    tagsLogs = userTagsSearchLogsList.get(0);
                    if("1".equals(isHide)){
                        tagsLogs.setIsHide(2);
                    }else{
                        tagsLogs.setIsHide(1);
                    }
                    userTagsSearchLogsService.updateUserTagsLog(tagsLogs);
                    param.clear();
                }
                this.outputToClient("true");
            }
        }catch(Exception e){
            this.outputToClient("false");
        }
    }
    
    @Action("/tagLogs/pagesize")
    public void pageSizeSumit() {
        String flag = "false";
        if (StringUtils.isNotBlank(this.pageSize)
                && StringUtils.isNotBlank(this.memcachedparam)) {
            MemcachedUtil.getInstance().set(this.memcachedparam, pageSize);
            flag = "true";
        }
        this.sendAjaxMsg(flag);
    }
    
    public void initTagsTypePage(){
        pagination=initPage();
        pageSizeSet();
        userTagsFirstType = Constant.USERTAGSTYPE_FIRSTTYPE.getList();
        pagination.setTotalResultSize(userTagsSearchLogsService.countUserTagsLogByParam(param));
        if(pagination.getTotalResultSize()>0){
            param.put("startRows", pagination.getStartRows());
            param.put("endRows", pagination.getEndRows());
        }
        userTagsSearchLogsList = userTagsSearchLogsService.queryAllUserTagsLogByParam(param);
        //查询出汉字对应的拼音 只有在从日志添加到标签需要此操作
        if("1".equals(isHide)){
            for (int i = 0; i < userTagsSearchLogsList.size(); i++) {
                UserTagsSearchLogs tagLogs = userTagsSearchLogsList.get(i);
                List<PlaceSearchPinyin> pinyinList = placeSearchPinyinService.queryPinyinListByName(tagLogs.getSearchLogsName());
                if(null != pinyinList && pinyinList.size()>0){
                    tagLogs.setSearchLogsPinYin(pinyinList.get(0).getPinYin());
                }
            }
        }
        
        pagination.buildUrl(getRequest());
    }
    
    public void pageSizeSet() {
        String ps = (String) MemcachedUtil.getInstance().get("USERTAGS_SEARCHLOGS_PAGESIZE");
        if (StringUtils.isNotBlank(ps)) {
            pagination.setPageSize(Long.valueOf(ps));
        } else {
            pagination.setPageSize(20);
        }
    }

    /**
     * 时间转换
     * @param dateStr
     * @return
     */
    private Date convertToDate(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /******************************************getter/setter************************************************/
    public UserTagsSearchLogsService getUserTagsSearchLogsService() {
        return userTagsSearchLogsService;
    }

    public void setUserTagsSearchLogsService(
            UserTagsSearchLogsService userTagsSearchLogsService) {
        this.userTagsSearchLogsService = userTagsSearchLogsService;
    }

    public List<UserTagsSearchLogs> getUserTagsSearchLogsList() {
        return userTagsSearchLogsList;
    }

    public void setUserTagsSearchLogsList(
            List<UserTagsSearchLogs> userTagsSearchLogsList) {
        this.userTagsSearchLogsList = userTagsSearchLogsList;
    }

    public List<PageElementModel> getUserTagsFirstType() {
        return userTagsFirstType;
    }

    public void setUserTagsFirstType(List<PageElementModel> userTagsFirstType) {
        this.userTagsFirstType = userTagsFirstType;
    }

    public UserTagsSearchLogs getTagsLogs() {
        return tagsLogs;
    }

    public void setTagsLogs(UserTagsSearchLogs tagsLogs) {
        this.tagsLogs = tagsLogs;
    }

    public PlaceSearchPinyinService getPlaceSearchPinyinService() {
        return placeSearchPinyinService;
    }

    public void setPlaceSearchPinyinService(
            PlaceSearchPinyinService placeSearchPinyinService) {
        this.placeSearchPinyinService = placeSearchPinyinService;
    }

    public String getTagLogsList() {
        return TagLogsList;
    }

    public void setTagLogsList(String tagLogsList) {
        TagLogsList = tagLogsList;
    }

    public String getIsHide() {
        return isHide;
    }

    public void setIsHide(String isHide) {
        this.isHide = isHide;
    }


    public String getStartDate() {
        return startDate;
    }


    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getMemcachedparam() {
        return memcachedparam;
    }

    public void setMemcachedparam(String memcachedparam) {
        this.memcachedparam = memcachedparam;
    }
    
}
