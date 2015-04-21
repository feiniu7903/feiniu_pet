package com.lvmama.businesses.sweb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.businesses.po.review.ReviewSendEmail;
import com.lvmama.comm.businesses.service.review.BbsService;
import com.lvmama.comm.pet.service.review.ReviewSendEmailService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;

@Results({
        @Result(name = "reviewSendEmail_list", location = "/WEB-INF/pages/web/review/reviewSendEmail_list.jsp"),
        @Result(name = "go_reviewSendEmail_list", type = "redirect", location = "/keyword/reviewSendEmailList.do") })
public class ReviewSendEmailAction extends BackBaseAction {
    private static final long serialVersionUID = 1L;

    private ReviewSendEmailService reviewSendEmailService;
    private List<ReviewSendEmail> ReviewContentList;
    private ReviewSendEmail reviewSendEmail;
    @Autowired
    private BbsService bbsService;

    @Action("/keyword/reviewSendEmailList")
    public String execute() throws Exception {
        reviewSendEmail_list();
        return "reviewSendEmail_list";
    }

    @Action("/keyword/reviewSendEmail")
    public String goSendEmail() {
        if (!StringUtil.isEmptyString(reviewSendEmail.getKeyWordStartDate())
                || !StringUtil.isEmptyString(reviewSendEmail.getKeyWordEndDate())
                || !StringUtil.isEmptyString(reviewSendEmail.getContentStartDate())
                || !StringUtil.isEmptyString(reviewSendEmail.getContentEndDate())
                || !StringUtil.isEmptyString(reviewSendEmail.getReviewChannel())
                || !StringUtil.isEmptyString(reviewSendEmail.getReviewStatus())) {
            try {
            	 scanning();
                 reviewSendEmailService.insert(reviewSendEmail);
            } catch (Exception e) {
                log.error("saveReviewSendEmail error:\r\n" + e);
            }

        }
        return "go_reviewSendEmail_list";
    }
    /**
     * 扫描
     * 
     * @author nixianjun 2013-10-22
     */
    public void scanning(){
    	//扫描
    	if(StringUtil.isNotEmptyString(reviewSendEmail.getReviewChannel())){
    		Map map=new HashMap();
    	    map.put("keyword_b", reviewSendEmail.getKeyWordStartDate());
    		map.put("keyword_e", reviewSendEmail.getKeyWordEndDate());
       		 map.put("param_b", reviewSendEmail.getContentStartDate());
       		 map.put("param_e", reviewSendEmail.getContentEndDate());
       		 if("0".equals(reviewSendEmail.getReviewStatus())){
       			 map.put("param_r1", 3);
           		 map.put("param_r2", 5);
       		 }else{
       			 map.put("param_r1", Integer.valueOf(reviewSendEmail.getReviewStatus()));
       			 map.put("param_r2", Integer.valueOf(reviewSendEmail.getReviewStatus()));
       		 }
       		 Integer count=0;
    		if(reviewSendEmail.getReviewChannel().equals("1")){
     			count+=bbsService.exeScanningForumPost(map);
     			count+=bbsService.exeScanningForumThread(map);
    		}else if(reviewSendEmail.getReviewChannel().equals("2")){
    			Map map2=new HashMap();
        	    map2.put("keyword_b", DateUtil.toDate(reviewSendEmail.getKeyWordStartDate(), "yyyy-MM-dd hh:mm:ss"));
        		map2.put("keyword_e", DateUtil.toDate(reviewSendEmail.getKeyWordEndDate(),"yyyy-MM-dd hh:mm:ss"));
           		 map2.put("param_b", DateUtil.toDate(reviewSendEmail.getContentStartDate(),"yyyy-MM-dd hh:mm:ss"));
           		 map2.put("param_e", DateUtil.toDate(reviewSendEmail.getContentEndDate(),"yyyy-MM-dd hh:mm:ss"));
           		 if("0".equals(reviewSendEmail.getReviewStatus())){
           			 map2.put("param_r1", 3);
               		 map2.put("param_r2", 5);
           		 }else{
           			 map2.put("param_r1", Integer.valueOf(reviewSendEmail.getReviewStatus()));
           			 map2.put("param_r2", Integer.valueOf(reviewSendEmail.getReviewStatus()));
           		 }
     				count+=reviewSendEmailService.exeScanningComment(map2);
     				count+=reviewSendEmailService.exeScanningCmtReply(map2);
     		}else if(reviewSendEmail.getReviewChannel().equals("3")){
     				count+=bbsService.exeScanningGlArticle(map);
     				count+=bbsService.exeScanningGlComment(map);
     		}else if(reviewSendEmail.getReviewChannel().equals("4")){
     				count+=bbsService.exeScanningPhpcmsComment(map);
     				count+=bbsService.exeScanningPhpcmsContentAll(map);
     		}
    		reviewSendEmail.setCount(count);
    	}
    }

    
    public void reviewSendEmail_list() {
        pagination=initPage();
        pagination.setPageSize(10);
        Map<String,Object> param= new HashMap<String, Object>();
        pagination.setTotalResultSize(reviewSendEmailService.count());
            if(pagination.getTotalResultSize()>0){
                param.put("startRows", pagination.getStartRows());
                param.put("endRows", pagination.getEndRows());
                ReviewContentList = reviewSendEmailService.query(param);
            }
        pagination.buildUrl(getRequest());
    }

    /**************************************** getter / setter ******************************************/
    

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ReviewSendEmailService getReviewSendEmailService() {
        return reviewSendEmailService;
    }

    public void setReviewSendEmailService(
            ReviewSendEmailService reviewSendEmailService) {
        this.reviewSendEmailService = reviewSendEmailService;
    }

    public List<ReviewSendEmail> getReviewContentList() {
        return ReviewContentList;
    }

    public void setReviewContentList(List<ReviewSendEmail> reviewContentList) {
        ReviewContentList = reviewContentList;
    }

	/**
	 * @return the reviewSendEmail
	 */
	public ReviewSendEmail getReviewSendEmail() {
		return reviewSendEmail;
	}

	/**
	 * @param reviewSendEmail the reviewSendEmail to set
	 */
	public void setReviewSendEmail(ReviewSendEmail reviewSendEmail) {
		this.reviewSendEmail = reviewSendEmail;
	}
	
	public Date getCreateTimeBegin(){
		return new Date();
	}
}
