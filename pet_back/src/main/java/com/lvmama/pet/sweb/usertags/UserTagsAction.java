package com.lvmama.pet.sweb.usertags;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.PlaceSearchPinyin;
import com.lvmama.comm.pet.po.usertags.UserTags;
import com.lvmama.comm.pet.po.usertags.UserTagsSearchLogs;
import com.lvmama.comm.pet.po.usertags.UserTagsType;
import com.lvmama.comm.pet.service.place.PlaceSearchPinyinService;
import com.lvmama.comm.pet.service.usertags.UserTagsSearchLogsService;
import com.lvmama.comm.pet.service.usertags.UserTagsService;
import com.lvmama.comm.pet.service.usertags.UserTagsTypeService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PageElementModel;

@Results({
    @Result( name="add_tags",location="/WEB-INF/pages/back/user/usertags/add_tags.jsp" ),
    @Result( name="search_tags",location="/WEB-INF/pages/back/user/usertags/search_tags.jsp" ),
    @Result( name="edit_tags",location="/WEB-INF/pages/back/user/usertags/edit_tags.jsp" ),
    @Result( name="empty_tagsPinyin",location="/WEB-INF/pages/back/user/usertags/empty_tagsPinyin.jsp")
})
public class UserTagsAction extends BackBaseAction{
    
    private static final long serialVersionUID = 1L;
    private List<PageElementModel> userTagsFirstType = Collections.emptyList();
    private UserTags tags;
    private UserTagsService userTagsService;
    private UserTagsTypeService userTagsTypeService;
    private UserTagsSearchLogsService userTagsSearchLogsService;
    private UserTagsType userTagsType;
    private List<UserTags> userTagsList;
    Map<String,Object> param;
    private String saveUserTagsBySearchLogsInfo;
    UserTagsSearchLogs searchLogs;
    private String tagsPinyin;//compareValue = 'search' ;search tagsPinyin is null
    
    @Autowired
    private PlaceSearchPinyinService placeSearchPinyinService;
    
    @Action("/tags/search")
    public String execute() throws Exception {
        initTagsTypePage();
        return "search_tags";
    }
    
    @Action("/tags/searchEmptyTagsPinyin")
    public String executeEmptyTagsPinyin() throws Exception {
        initTagsTypePage();
        return "empty_tagsPinyin";
    }
    
    @Action("/tags/handAddTags")
    public String handAddTags() throws Exception {
        userTagsFirstType = Constant.USERTAGSTYPE_FIRSTTYPE.getList();
        return "add_tags";
    }
    
    @Action("/tags/searchPinyinByTagsName")
    public String searchPinyinByTagsName(){
        if(null != tags && "" != tags.getTagsName()){
            JSONArray jsonArr=new JSONArray();
            List<PlaceSearchPinyin> pinyinList = placeSearchPinyinService.queryPinyinListByName(tags.getTagsName());
            for (int j = 0; j < pinyinList.size(); j++) {
                JSONObject obj=new JSONObject();
                obj.put("pinyin",pinyinList.get(j).getPinYin());
                jsonArr.add(obj);
            }
            sendAjaxResultByJson(jsonArr.toString());
        }
        return null;
    }

    @Action("/tags/isExistCheck")
    public void doHandAddTags() throws Exception {
        try{
            if(null != tags && null != userTagsType){
                UserTags tag = userTagsService.queryUserTagsByName(tags);
                if(null != tag){
                    this.outputToClient("exist");
                }else{
                    Map<String,Object> param = new HashMap<String, Object>();
                    param.put("typeFirstType", userTagsType.getTypeFirstType());
                    param.put("typeSecondType", userTagsType.getTypeSecondType());
                    List<UserTagsType> tagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
                    if(tagsTypeList.size()>0){
                        userTagsType = tagsTypeList.get(0);
                        tags.setTagsTypeId(userTagsType.getTypeId());
                        userTagsService.saveUserTags(tags);
                        this.outputToClient("true");
                    }else{
                        this.outputToClient("type_existn't");
                    }
                }
            }else{
                this.outputToClient("existn't");
            }
        }catch(Exception e){
            e.printStackTrace();
            this.outputToClient("false");
        }
    }
    
    
    /**
     * saveUserTagsBySearchLogsInfo
     * tagsInfo[0]表示SearchLogs的ID tagsInfo[1]表示拼音 tagsInfo[4]表示是否可用状态 tagsInfo[5]表示标签名称
     * tagsInfo[2]表示一级类别 tagsInfo[3]表示二级类别 
     * 元素元素之间用","分隔
     */
    @Action("/tags/saveTagsByLogs")
    public void saveUserTagsBySearchLogs() throws Exception{
        try {
            if(null != saveUserTagsBySearchLogsInfo){
                String[] temp = saveUserTagsBySearchLogsInfo.split(",");
                for (int i = 0; i < temp.length; i++) {
                    String index = temp[i];
                    String[] tagsInfo = index.split("/");
                    tags = new UserTags();
                    tags.setTagsName(tagsInfo[5]);
                    tags.setTagsPinYin(tagsInfo[1]);
                    tags.setTagsStatus(Long.valueOf(tagsInfo[4]));
                    if(null != tagsInfo[3]){
                        Map<String,Object> param = new HashMap<String, Object>();
                        param.put("typeFirstType", tagsInfo[2]);
                        param.put("typeSecondType", tagsInfo[3]);
                        List<UserTagsType> tagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
                        if(tagsTypeList.size()>0){
                            userTagsType = tagsTypeList.get(0);
                            tags.setTagsTypeId(userTagsType.getTypeId());
                        }
                    }
                    searchLogs = userTagsSearchLogsService.querySearchLogsByLogsId(Long.valueOf(tagsInfo[0]));
                    searchLogs.setIsHide(3);
                    userTagsSearchLogsService.updateUserTagsLog(searchLogs);
                    userTagsService.saveUserTags(tags);
                }
                this.outputToClient("true");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.outputToClient("false");
        }
    }
    
    /**
     * saveUserTagsBySearchLogsInfo
     * tagsInfo[0]表示SearchLogs的ID tagsInfo[1]表示拼音
     * 元素元素之间用","分隔
     */
    @Action("/tags/doUpdateTagsPinYin")
    public void doUpdateTagsPinYin() throws Exception{
        try {
            if(null != saveUserTagsBySearchLogsInfo){
                String[] temp = saveUserTagsBySearchLogsInfo.split(",");
                for (int i = 0; i < temp.length; i++) {
                    String index = temp[i];
                    String[] tagsInfo = index.split("/");
                    tags = new UserTags();
                    tags.setTagsId(Long.valueOf(tagsInfo[0]));
                    //根据ID查找到userTags对象
                    tags = userTagsService.queryUserTagsByName(tags);
                    //为无拼音的用户标签添加注入输入拼音~
                    tags.setTagsPinYin(tagsInfo[1]);
                    userTagsService.updateUserTags(tags);
                }
                this.outputToClient("true");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            this.outputToClient("false");
        }
    }
    
    @Action("/tags/goUpdate")
    public String goUpdate() throws Exception{
        if(null != tags){
            param = new HashMap<String, Object>();
            tags = userTagsService.queryUserTagsByName(tags);
            param.put("typeId",tags.getTagsTypeId());
            List<UserTagsType> tagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
            userTagsFirstType = Constant.USERTAGSTYPE_FIRSTTYPE.getList();
            userTagsType = tagsTypeList.get(0);
        }
        return "edit_tags";
    }
    
    @Action("/tags/update")
    public void doUpdate() throws Exception{
        try {
            if(null != tags ){
                param = new HashMap<String, Object>();
                param.put("typeFirstType",userTagsType.getTypeFirstType());
                param.put("typeSecondType",userTagsType.getTypeSecondType());
                List<UserTagsType> tagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
                userTagsType = tagsTypeList.get(0);
                param.clear();
               
                UserTags userTags = userTagsService.queryUserTagsByName(tags);
                userTags.setTagsPinYin(tags.getTagsPinYin());
                userTags.setTagsStatus(tags.getTagsStatus());
                userTags.setTagsTypeId(userTagsType.getTypeId());
                userTagsService.updateUserTags(userTags);
                this.outputToClient("true");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.outputToClient("false");
        }
    }
    
    public void initTagsTypePage(){
        param = getQuestionParam();
        pagination=initPage();
        if(null != tagsPinyin){
            pagination.setPageSize(30);
        }else{
            pagination.setPageSize(10);
        }
        pagination.setTotalResultSize(userTagsService.countByParam(param));
        if(pagination.getTotalResultSize()>0){
            param.put("startRows", pagination.getStartRows());
            param.put("endRows", pagination.getEndRows());
        }
        userTagsList = userTagsService.queryAllUserTagsByParam(param);
        userTagsFirstType = Constant.USERTAGSTYPE_FIRSTTYPE.getList();
        if(null != tagsPinyin){
            for (int i = 0; i < userTagsList.size(); i++) {
                UserTags tags = userTagsList.get(i);
                List<PlaceSearchPinyin> pinyinList = placeSearchPinyinService.queryPinyinListByName(tags.getTagsName());
                if(null != pinyinList && pinyinList.size()>0){
                    tags.setTagsPinYin(pinyinList.get(0).getPinYin());
                }
            }
        }   
        pagination.buildUrl(getRequest());
    } 
    
    public Map<String,Object> getQuestionParam(){
        param = new HashMap<String, Object>();
        if(null != userTagsType && null != tags){
            if(!"".equals(userTagsType.getTypeFirstType()) && null != userTagsType.getTypeSecondType()){
                Map<String,Object> temp = new HashMap<String, Object>();
                temp.put("typeFirstType", userTagsType.getTypeFirstType());
                temp.put("typeSecondType", userTagsType.getTypeSecondType());
                List<UserTagsType> tagsTypeList = userTagsTypeService.queryTagsTypeByParam(temp);
                
                if(tagsTypeList.size()>0){
                    userTagsType = tagsTypeList.get(0);
                    param.put("tagsTypeId",userTagsType.getTypeId());
                }
            }
            param.put("tagsName",tags.getTagsName());
            param.put("tagsStatus",tags.getTagsStatus());
        }
        if(null != tagsPinyin){
            param.put("tagsPinYin", tagsPinyin);
        }
        return param;
    }

    /****************************************getter/setter****************************************/
    public List<PageElementModel> getUserTagsFirstType() {
        return userTagsFirstType;
    }

    public void setUserTagsFirstType(List<PageElementModel> userTagsFirstType) {
        this.userTagsFirstType = userTagsFirstType;
    }

    public UserTags getTags() {
        return tags;
    }

    public void setTags(UserTags tags) {
        this.tags = tags;
    }

    public UserTagsService getUserTagsService() {
        return userTagsService;
    }

    public void setUserTagsService(UserTagsService userTagsService) {
        this.userTagsService = userTagsService;
    }

    public UserTagsTypeService getUserTagsTypeService() {
        return userTagsTypeService;
    }

    public void setUserTagsTypeService(UserTagsTypeService userTagsTypeService) {
        this.userTagsTypeService = userTagsTypeService;
    }

    public UserTagsType getUserTagsType() {
        return userTagsType;
    }

    public void setUserTagsType(UserTagsType userTagsType) {
        this.userTagsType = userTagsType;
    }

    public List<UserTags> getUserTagsList() {
        return userTagsList;
    }

    public void setUserTagsList(List<UserTags> userTagsList) {
        this.userTagsList = userTagsList;
    }

    public String getSaveUserTagsBySearchLogsInfo() {
        return saveUserTagsBySearchLogsInfo;
    }

    public void setSaveUserTagsBySearchLogsInfo(String saveUserTagsBySearchLogsInfo) {
        this.saveUserTagsBySearchLogsInfo = saveUserTagsBySearchLogsInfo;
    }

    public UserTagsSearchLogsService getUserTagsSearchLogsService() {
        return userTagsSearchLogsService;
    }

    public void setUserTagsSearchLogsService(
            UserTagsSearchLogsService userTagsSearchLogsService) {
        this.userTagsSearchLogsService = userTagsSearchLogsService;
    }

    public String getTagsPinyin() {
        return tagsPinyin;
    }

    public void setTagsPinyin(String tagsPinyin) {
        this.tagsPinyin = tagsPinyin;
    }
    
}
