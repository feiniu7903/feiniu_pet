package com.lvmama.front.web.myspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.onlineLetter.LetterTemplate;
import com.lvmama.comm.pet.onlineLetter.LetterUserMessage;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.onlineLetter.OnlineLetterService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.Pagination;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtProdTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.front.web.BaseAction;
@Results({
	@Result(name = "message", location = "/WEB-INF/pages/myspace/sub/message.ftl", type = "freemarker")
})
public class MyOnlineLetterAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8313306103508560947L;
	private Logger LOG = Logger.getLogger(MyOnlineLetterAction.class);
	
	private OnlineLetterService onlineLetterService;
	private CmtCommentService cmtCommentService;
	private OrderService orderServiceProxy;
	
	private Page<LetterUserMessage> pageConfig;
	private static final int pageSize = 15;
	private int currentPage = 1;
	private Long id;
	private String userId;
	private int defaultPageSize;
	private int pageCount;
	
	@Action("/myspace/message")
	public String proclamation(){
		if(!isLogin()){
			return ERROR;
		}
		userId=getUser().getUserId();	
		return "message";
	}
	@Action("/myspace/message/personelLetter")
	public String execute(){
		if(!isLogin()){
			return ERROR;
		}
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("userId",getUser().getId());
		Long total = onlineLetterService.countMessage(parameters);
		this.pageConfig = Page.page(total, pageSize, currentPage);
		parameters.put("skipResult", pageConfig.getStartRows());
		parameters.put("maxResult",pageConfig.getStartRows()-1+pageConfig.getPageSize());
		List<LetterUserMessage> list = onlineLetterService.queryMessage(parameters);
		this.pageConfig.setItems(list);
		if(pageConfig.getItems().size()>0){
			this.pageConfig.setUrl("/myspace/message/personelLetter.do?currentPage=");
		}	
		return "message";
	}

	@Action("/myspace/readMessage")
	public void readMessage(){
		String method = getMethod();
		if(!"POST".equals(method)){
			sendAjaxResultByJson("{\"success\":false}");
			return;
		}
		String userId = getRequest().getParameter("userId");
		String readTime = getRequest().getParameter("readTime");
		String templateType = getRequestParameter("templateType");
		String templateId = getRequest().getParameter("templateId");
		
		if(isLogin() && (null!=id || Constant.ONLINE_LETTER_TYPE.PROCLAMATION.name().equals(templateType)) && !StringUtil.isEmptyString(templateId) 
				&& !StringUtil.isEmptyString(userId) && getUser().getId()==Long.parseLong(userId) 
				&& !StringUtil.isEmptyString(templateType)&& StringUtil.isEmptyString(readTime)){
			LetterTemplate template = new LetterTemplate();
			template.setId(Long.valueOf(templateId));
			onlineLetterService.updateTemplate(template);
			onlineLetterService.updateUserLetter(id);
			sendAjaxResultByJson("{\"success\":true}");
			return;
		}
		sendAjaxResultByJson("{\"success\":false}");
	}
	
	@Action("/myspace/message/unReadCount")
	public void unReadCount(){
		if(!isLogin()){
			return;
		}
//		Map<String,Object> parameters = new HashMap<String,Object>();
//		parameters.put("userId",getUser().getId());
//		parameters.put("beginTime", new Date());
//		parameters.put("endTime", new Date());
//		parameters.put("unReadTime",new Date());
//		Long count = onlineLetterService.countMessage(parameters);
		//
		List<CmtProdTitleStatisticsVO> needProductCommentInfoList = new ArrayList<CmtProdTitleStatisticsVO>();
		//获取可点评的订单信息
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByUserNo(getUser().getUserId());
		
		for(int i = 0; i < canCommentOrderProductList.size(); i++)
		{
			OrderAndComment canCommentOrderProduct = canCommentOrderProductList.get(i);
			Map<String,Object> parameters2 = new HashMap<String, Object>();
			parameters2.put("orderId", canCommentOrderProduct.getOrderId());
			parameters2.put("productId", canCommentOrderProduct.getProductId());
			parameters2.put("isHide", "displayall");
			List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parameters2);
			if(cmtCommentList == null || cmtCommentList.size() == 0)//该订单产品未被点评过，可以点评
			{
				CmtProdTitleStatisticsVO cmtProdTitleStatisticsVO = CommentUtil.composeProdTitleStatistics(canCommentOrderProduct);
				needProductCommentInfoList.add(cmtProdTitleStatisticsVO);
			}
		}
		long waittingCommentNumber = needProductCommentInfoList.size();
		sendAjaxResultByJson("{\"waittingCommentNumber\":\""+waittingCommentNumber+"\"}");
	}
	
	@Action("/myspace/message/getPage")
	public void getPageForJava(){
		if(!isLogin()){
			return;
		}
		String page=Pagination.pagination(defaultPageSize,pageCount, "javascript:getMyMessage(argPage);",currentPage,"js");
		page=page.replaceAll("\"", "'");
		sendAjaxResultByJson("{\"pages\":\""+page+"\"}");
	}
	public Logger getLOG() {
		return LOG;
	}
	public void setLOG(Logger lOG) {
		LOG = lOG;
	}
	public OnlineLetterService getOnlineLetterService() {
		return onlineLetterService;
	}
	public void setOnlineLetterService(OnlineLetterService onlineLetterService) {
		this.onlineLetterService = onlineLetterService;
	}
	public Page<LetterUserMessage> getPageConfig() {
		return pageConfig;
	}
	public void setPageConfig(Page<LetterUserMessage> pageConfig) {
		this.pageConfig = pageConfig;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}
