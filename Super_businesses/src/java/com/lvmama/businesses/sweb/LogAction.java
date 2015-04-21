package com.lvmama.businesses.sweb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.WebUtils;

/**
 * 显示日志使用,ajax操作
 * @author yangbin
 *
 */
@Results({ 
    @Result(name="log",location="/WEB-INF/pages/log/log_page.jsp") 
})
public class LogAction extends BackBaseAction{

    /**
     * 日志service.
     */
    private ComLogService comLogRemoteService;
    /**
     * 对象查看的类型.例:订单表ORD_ORDER
     */
    private String objectType;
    /**
     * 日志查看对像编号.例：订单编号.
     */
    private Long objectId;
    /**
     * 父类编号.
     */
    private Long parentId;
    
    /**
     * 
     */
    private String parentType;
    /**
     * 日志结果集.
     */
    private List<ComLog> comLogList;

    private Map<String, Comparable> paramMap=new HashMap<String, Comparable>();
    
    /**
     * 
     */
    private static final long serialVersionUID = 1102496921821767344L;
    
    
    @Action("/keyword/loadLogs")
    public String loadPage(){
        boolean flag=true;
        if(parentId!=null&&StringUtils.isNotEmpty(parentType)&&StringUtils.isNotEmpty(objectType)){
            paramMap.put("parentId", parentId);
            paramMap.put("parentId",parentId);
            paramMap.put("parentType",parentType);
            paramMap.put("objectType",objectType);
            initPage();
            pagination.setTotalResultSize(this.comLogRemoteService.queryByParentIdAndObjectTypeMapCount(paramMap));
            initPageSize();
            comLogList = comLogRemoteService.queryByParentIdAndObjectTypeMap(paramMap);
        }else if(StringUtils.isNotEmpty(parentType)&&parentId!=null){
            paramMap.put("objectId",parentId);
            paramMap.put("objectType",parentType);
            initPage();
            pagination.setTotalResultSize(this.comLogRemoteService.queryByParentIdMapCount(paramMap));
            initPageSize();
            comLogList = comLogRemoteService.queryByParentIdMap(paramMap);
        }else if(StringUtils.isNotEmpty(objectType)&&objectId!=null){
            paramMap.put("objectId",objectId);
            paramMap.put("objectType",objectType);
            initPage();
            pagination.setTotalResultSize(this.comLogRemoteService.queryByObjectIdMapCount(paramMap));
            initPageSize();
            comLogList = comLogRemoteService.queryByObjectIdMap(paramMap);
        }else{
            flag=false;
        }
        
        if(flag){
            pagination.setUrl(WebUtils.getUrl(getRequest()));
        }
        
        return "log";
    }

    private void initPageSize() {
        paramMap.put("skipResults",pagination.getStartRows()-1);
        paramMap.put("maxResults",pagination.getEndRows());
    }

    /**
     * @return the comLogList
     */
    public List<ComLog> getComLogList() {
        return comLogList;
    }


    public void setComLogRemoteService(ComLogService comLogRemoteService) {
        this.comLogRemoteService = comLogRemoteService;
    }

    /**
     * @param objectType the objectType to set
     */
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    /**
     * @param objectId the objectId to set
     */
    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @param parentType the parentType to set
     */
    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    /**
     * @param paramMap the paramMap to set
     */
    public void setParamMap(Map<String, Comparable> paramMap) {
        this.paramMap = paramMap;
    }
    
    
}
