package com.lvmama.businesses.sweb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


import com.lvmama.businesses.review.util.KeyWordUtils;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.comment.CmtReplyService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtReplyVO;

@Results({
    @Result(name = "cmtReply",location="/WEB-INF/pages/web/review/cmtReply.jsp")
      })
public class CmtReplyAction extends ContentAction  {
    private static final long serialVersionUID = 1L;
    private CmtReplyService cmtReplyService;
    private List<CmtReplyVO> CmtReplyList;
    private String reviewStatus;
    private String startDate;
    private String endDate;
    
    
    @Action("/keyword/cmtReply")
    public String goCmtReply(){
        list_cmtReply();
        return "cmtReply";
    }
    
    @Action("/keyword/doCmtReplyUpdate")
    public void doUpdateCmtReply() {
        if(StringUtils.isNotBlank(arrayStr)){
            List<String[]> list=parseArray(arrayStr);
            for(String[] m:list){
                 CmtReplyVO vo= cmtReplyService.queryCmtReplyByKey(Long.valueOf(m[0]));
                 if(m[1].equals(Constant.REVIEW_STATUS.black.getCode())){
                    vo.setIsHide("Y");
                 }else{
                    vo.setIsHide("N");
                 }
                   //添加日志记录 
                 if(! m[1].equals(String.valueOf(vo.getReviewStatus()))){
                     ComLog comlog=new ComLog();
                     comlog.setObjectId(Long.valueOf(m[0]));
                     comlog.setObjectType(KeyWordUtils.CMTREPLY);
                     comlog.setCreateTime(new Date());
                     comlog.setContent("状态改为"+Constant.REVIEW_STATUS.getCnNameByCode(m[1]));
                     comlog.setOperatorName(super.getSessionUserName());
                     comLogService.addComLog(comlog);
                 }
                 vo.setReviewStatus(Long.valueOf(m[1]));
                 cmtReplyService.updateCmtReplyByReviewStatus(vo);
            }
            this.sendAjaxMsg("true");
        }
        this.sendAjaxMsg("false");
    }

    
    public void list_cmtReply(){
        pagination=initPage();
        Map<String,Object> param=builderParam();
        pagination.setTotalResultSize(cmtReplyService.getCountByReviewStatus(param));
        if(pagination.getTotalResultSize()>0){
            param.put("startRows", pagination.getStartRows());
            param.put("endRows",pagination.getEndRows());
            CmtReplyList=cmtReplyService.queryCmtReplyByReviewStatus(param);
            pagination.setAllItems(CmtReplyList);
        }
        pagination.buildUrl(getRequest());
    }
    
    public void update_cmtReply(){
        //TODO 点评回复修改
    }
    
    /******************************************************************************************/
    
    public Map<String,Object> builderParam(){ 
        Map<String, Object> param = new HashMap<String, Object>(); 
        if("".equals(reviewStatus)){ 
        param.put("reviewStatus","3"); 
        } 
        if(null != reviewStatus && !"".equals(reviewStatus)){ 
        param.put("reviewStatus",reviewStatus); 
        } 
        if(null != startDate &&!"".equals(startDate)){ 
        param.put("startDate",convertToDate(startDate+" 00:00:00")); 
        } 
        if(null != endDate &&!"".equals(endDate)){ 
        param.put("endDate",convertToDate(endDate+" 23:59:59")); 
        }
        param.put("isAudit","AUDIT_SUCCESS");
        return param; 
        } 

        private Date convertToDate(String dateStr){ 
        try { 
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        return format.parse(dateStr); 
        } catch (ParseException e) { 
        e.printStackTrace(); 
        } 
        return null; 
        }
    
    
    /**************************************getter/setter****************************************/

    public CmtReplyService getCmtReplyService() {
        return cmtReplyService;
    }

    public void setCmtReplyService(CmtReplyService cmtReplyService) {
        this.cmtReplyService = cmtReplyService;
    }

    public List<CmtReplyVO> getCmtReplyList() {
        return CmtReplyList;
    }

    public void setCmtReplyList(List<CmtReplyVO> cmtReplyList) {
        CmtReplyList = cmtReplyList;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
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

    @Override
    public  String getMemcachPageSizeKey() {
        return KeyWordUtils.BUSSINESS_PAGE_SIZE_+KeyWordUtils.CMTREPLY;
    }   
}
