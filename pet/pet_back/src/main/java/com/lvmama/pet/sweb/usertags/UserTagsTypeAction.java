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

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.usertags.UserTagsType;
import com.lvmama.comm.pet.service.usertags.UserTagsTypeService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PageElementModel;

@Results({
        @Result (name="tagsType_list",location="/WEB-INF/pages/back/user/usertags/search_tagsType.jsp"),
        @Result (name="tagsType_edit",location="/WEB-INF/pages/back/user/usertags/edit_tagsType.jsp")
       
})
public class UserTagsTypeAction extends BackBaseAction{
    private static final long serialVersionUID = 1L;
    
    private UserTagsTypeService userTagsTypeService;
    private List<UserTagsType> usertagsTypeList;
    private UserTagsType userTagsType;
    private String deleteTagTypesList;
    private List<PageElementModel> userTagsFirstType = Collections.emptyList();
    
    Map<String,Object> param;
    
    @Action("/tagsType/search")
    public String execute() throws Exception {
        initTagsTypePage();
        return "tagsType_list";
    }
    
    @Action("/tagsType/isExistCheck")
    public void isExistCheck() throws Exception{
        param = new HashMap<String, Object>();
        param.put("typeFirstType", userTagsType.getTypeFirstType());
        param.put("typeSecondType",userTagsType.getTypeSecondType());
        usertagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
        if(usertagsTypeList.size()>0){
            this.outputToClient("exist");
        }else{
            try{
                UserTagsType tagType = new UserTagsType();
                tagType.setTypeFirstType(userTagsType.getTypeFirstType());
                tagType.setTypeSecondType(userTagsType.getTypeSecondType());
                userTagsTypeService.insertUserTagsType(tagType);
                this.outputToClient("true");
            }catch(Exception e){
                this.outputToClient("false");
            }
        }
        usertagsTypeList.clear();
    }
    
    @Action("/tagsType/delete")
    public void delete() throws Exception{
        try{
            param = new HashMap<String, Object>();
            if(deleteTagTypesList.length()>0){
                String[] TagsIds = deleteTagTypesList.split(",");
                for (int i = 0; i < TagsIds.length; i++) {
                    param.put("typeId",TagsIds[i]);
                    userTagsTypeService.deleteUserTagsTypeByParam(param);
                }
            }
            this.outputToClient("true");
        }catch(Exception e){
            this.outputToClient("false");
        }
    }
    
    @Action("/tagsType/update")
    public void update() throws Exception{
        try{
            if(null != userTagsType){
                param = new HashMap<String, Object>();
                param.put("typeId",userTagsType.getTypeId());
                usertagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
                if(usertagsTypeList.size()>0){
                    param.clear();
                    UserTagsType tagType = new UserTagsType();
                    param.put("typeFirstType", userTagsType.getTypeFirstType());
                    param.put("typeSecondType",userTagsType.getTypeSecondType());
                    List<UserTagsType> List = userTagsTypeService.queryTagsTypeByParam(param);
                    if(List.size()>0){
                        this.outputToClient("exist");
                    }else{
                      tagType = usertagsTypeList.get(0);
                      tagType.setTypeFirstType(userTagsType.getTypeFirstType());
                      tagType.setTypeSecondType(userTagsType.getTypeSecondType());
                      userTagsTypeService.updateUserTagsType(tagType);
                      this.outputToClient("true");
                    }
                }
            }
            this.outputToClient("existn't");
        }catch(Exception e){
            this.outputToClient("false");
        }
    }
    
    @Action("/tagsType/goUpdate")
    public String goUpdate() throws Exception{
        if(null != userTagsType){
            param = new HashMap<String, Object>();
            param.put("typeId",userTagsType.getTypeId());
            usertagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
            userTagsType = usertagsTypeList.get(0);
            userTagsFirstType = Constant.USERTAGSTYPE_FIRSTTYPE.getList();
            return "tagsType_edit";
        }
        return "tagsType_list";
    }
    
    public void initTagsTypePage(){
        param = new HashMap<String, Object>();
        pagination=initPage();
        pagination.setPageSize(10);
        pagination.setTotalResultSize(userTagsTypeService.countUserTagsTypeByParam(param));
        if(pagination.getTotalResultSize()>0){
            param.put("startRows", pagination.getStartRows());
            param.put("endRows", pagination.getEndRows());
        }
        usertagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
        userTagsFirstType = Constant.USERTAGSTYPE_FIRSTTYPE.getList();
        pagination.buildUrl(getRequest());   
        
    }

    /**
     * changeFirstTypeOnloadSecondType json
     * @param param
     * @author zhongshuangxi
     * @return
     */
    @Action("/tagsType/changeFirstTypeOnloadSecondType")
    public String queryUserTagsTypeByParam(){
        param = new HashMap<String, Object>();
        param.put("typeFirstType",userTagsType.getTypeFirstType());
        usertagsTypeList = userTagsTypeService.queryTagsTypeByParam(param);
        JSONArray jsonArr=new JSONArray();
        for (UserTagsType tagsType : usertagsTypeList) {
            JSONObject obj=new JSONObject();
            obj.put("second",tagsType.getTypeSecondType());
            jsonArr.add(obj);
        }
        sendAjaxResultByJson(jsonArr.toString());
        return null;
    }
    
    /***------------------------------getter/setter------------------------------***/
    public UserTagsTypeService getUserTagsTypeService() {
        return userTagsTypeService;
    }

    public void setUserTagsTypeService(UserTagsTypeService userTagsTypeService) {
        this.userTagsTypeService = userTagsTypeService;
    }

    public List<UserTagsType> getUsertagsTypeList() {
        return usertagsTypeList;
    }
    
    public void setUsertagsTypeList(List<UserTagsType> usertagsTypeList) {
        this.usertagsTypeList = usertagsTypeList;
    }

    public UserTagsType getUserTagsType() {
        return userTagsType;
    }

    public void setUserTagsType(UserTagsType userTagsType) {
        this.userTagsType = userTagsType;
    }

    public String getDeleteTagTypesList() {
        return deleteTagTypesList;
    }

    public void setDeleteTagTypesList(String deleteTagTypesList) {
        this.deleteTagTypesList = deleteTagTypesList;
    }

    public List<PageElementModel> getUserTagsFirstType() {
        return userTagsFirstType;
    }

    public void setUserTagsFirstType(List<PageElementModel> userTagsFirstType) {
        this.userTagsFirstType = userTagsFirstType;
    }
    
}
