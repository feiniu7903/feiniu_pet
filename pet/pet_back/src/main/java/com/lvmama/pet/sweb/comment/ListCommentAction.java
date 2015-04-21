/**
 * 
 */
package com.lvmama.pet.sweb.comment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComSubject;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.DicCommentLatitudeService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComSubjectService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;
import com.lvmama.comm.vo.enums.ComSubjectTypeEnum;

/**
 * 点评管理Action
 * @author liuyi
 *
 */
@Results({
	@Result(name = "showScienceCommentTopic", location = "/WEB-INF/pages/back/comment/showScienceCommentTopic.jsp"),
	@Result(name = "showUpdatePlaceCommentTopic", location = "/WEB-INF/pages/back/comment/showUpdatePlaceCommentTopic.jsp")
})
public class ListCommentAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -322941610177483649L;

	//点评招募前10景点
	private static final int NUMBER_OF_MAX_RECRUIT_COMMENT = 10;
	private String commentId; 
	/**
	 * 主题名称
	 */
	private String subjectName;
	/**
	 * 是否精华
	 */
	private String isBest;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 目的地类型
	 */
	private String stage;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 审核状态
	 */
	private String auditStatus;
	/**
	 * 开始时间
	 */
	private String startDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	/**
	 * 点评类型
	 */
	private String cmtType;
	/**
	 * 排序
	 */
	private String order;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 是否有图片
	 */
	private String hasPicture;
	/**
	 * 来源渠道
	 */
	private String channel;
	/**
	 * 点评分类
	 */
	private String cmtEffectType;
	/**
	 * 查询最近3天记录
	 */
	private String lastest3day;
	/**
	 * 点评搜索结果列表
	 */
	private List<CommonCmtCommentVO> commonCmtCommentVOList;
	/**
	 * 操作
	 */
	private String operator;
	/**
	 * 批量点评IDS
	 */
	private String batchCommentIds;
	
	private CmtCommentService cmtCommentService;
	
	private PlaceService placeService;
	
	private UserUserProxy userUserProxy;
	
	private CommonCmtCommentVO currentComment;
	
	private DicCommentLatitudeService dicCommentLatitudeService;
	private ComSubjectService comSubjectService;
	/**
	 * 维度评分描述
	 */
	private String latitudeDescriptionInfo;
	/**
	 * 更新内容
	 */
	private String editContent;
	/**
	 * 增加积分
	 */
	private Long point;
	
	private ProdProductService prodProductService;
	private ProdProductPlaceService prodProductPlaceService;
	
	/**
	 * 导出excel的文件名
	 */
	private String excelFileName;
	/**
	 * 导出excel的文件流
	 */
	private InputStream excelStream;
	private long scrollTopHight;//当前按钮高度
	private Long placeId;
	private String pids;
	private String checkType;
	
	private String content;
	private String subProductType;
	
	/**
	 * 查询
	 */
	@Action(value="/commentManager/queryCommentList",results={@Result(location = "/WEB-INF/pages/back/comment/commentList.jsp")})
	public String search() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters = initParameters(parameters);
		
		Long totalRecords = cmtCommentService.getCommentTotalCount(parameters);
		pagination = Page.page(30, page);
		pagination.setTotalResultSize(totalRecords);
		pagination.buildUrl(getRequest());

		parameters.put("_startRow", pagination.getStartRows());
		parameters.put("_endRow", pagination.getEndRows());
		commonCmtCommentVOList = cmtCommentService.getCmtCommentList(parameters);
		commonCmtCommentVOList = composeProductComment(cmtCommentService.getCmtCommentList(parameters));
		return SUCCESS;
	}
	
	/**
	 * 批量处理点评
	 */
	@Action(value="/commentManager/batchOperator")
	public void batchOperator() {
		String errorMessage = "SUCCESS";
		if(StringUtils.isNotBlank(batchCommentIds)){
			String[] batchCommentIdsArray = batchCommentIds.split(",");
			for(int i = 0; i < batchCommentIdsArray.length; i++){
				if(StringUtils.isNotBlank(batchCommentIdsArray[i]))//去除最后结尾是逗号的序列
				{
					String statusMessage = processOperator(operator, Long.parseLong(batchCommentIdsArray[i]));
					if(!statusMessage.equals("SUCCESS"))
					{
						errorMessage = statusMessage;
						break;
					}
				}
			}
		}else
		{			
			errorMessage = "请选择要操作的点评！";
		}
		Map<String, Object> param = new HashMap<String, Object>();
		
		if(errorMessage.equals("SUCCESS"))
		{
			param.put("success", true);
		}
		else
		{
			param.put("success", false);
			param.put("errorMessage", errorMessage);
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}
	 
	/**
	 * 数据导出
	 * 
	 * 
	 * 没有导出报表 ----可以导出普通点评么
	 * 
	 * @throws Exception Exception
	 *  */ 
	 @Action(value="/commentManager/doExport",results={@Result(type="stream",name="success",params={"contentType","application/vnd.ms-excel","inputName","excelStream","contentDisposition","attachment;filename=${excelFileName}","bufferSize","1024"})})
	 public String doExport() throws Exception {
		 	Map<String, Object> parameters = new HashMap<String, Object>();
			parameters = initParameters(parameters);
			
			parameters.put("_startRow", 0);
			parameters.put("_endRow", 900);
			commonCmtCommentVOList = composeProductComment(cmtCommentService.getCmtCommentList(parameters));
			
			return doExcel(commonCmtCommentVOList);
	 }
	 
	 @Action(value="/commentManager/showScienceCommentTopic")
	 public String showScienceCommentTopic() {
		 return "showScienceCommentTopic";
	 }
	 
	 @Action(value="/commentManager/updateScienceCommentTopic")
	 public void updateScienceCommentTopic() throws Exception {
		 JSONObject object = new JSONObject();
		 if (StringUtils.isNotEmpty(this.subjectName) && null != this.placeId) {
			 placeService.updateCmtTitle(placeId, subjectName, getSessionUserNameAndCheck());
			 object.put("success", "true"); 
		 } else {
			 object.put("success", "false");
		 }
		 getResponse().getWriter().print(object.toString());
	 }
	 
	 
	 
	 @Action(value="/commentManager/showUpdatePlaceCommentTopic")
	 public String showUpdatePlaceCommentTopic() {
		 return "showUpdatePlaceCommentTopic";
	 }
	 
	 @Action(value="/commentManager/updatePlaceCommentTopic")
	 public void updatePlaceCommentTopic() throws Exception {
		 JSONObject object = new JSONObject();
		 if (StringUtils.isNotEmpty(this.pids)) {
			 if("0".equalsIgnoreCase(checkType)){
				 for(String placeId : pids.split(",")){
					 Place place = placeService.queryPlaceByPlaceId(Long.parseLong(placeId));
					 if(place.getStage().equalsIgnoreCase("2")){
						 placeService.updateCmtTitle(place.getPlaceId(), place.getFirstTopic(), getSessionUserNameAndCheck());
					 }
				 }
			 }else if("1".equalsIgnoreCase(checkType)){
				 for(String productId : pids.split(",")){
					 Place place = prodProductPlaceService.getToDestByProductId(Long.parseLong(productId));
					 
					 if(place !=null && place.getStage().equalsIgnoreCase("2")){
						 placeService.replaceCmtTitle(place.getPlaceId(), Long.parseLong(productId), place.getFirstTopic(), getSessionUserNameAndCheck());
					 }
				 }
			 }
			 object.put("success", "true"); 
		 } else {
			 object.put("success", "false");
		 }
		 getResponse().getWriter().print(object.toString());
	 }
	 
	 
	 
	 public List<ComSubject> getSubjectList() {
		 ComSubject comSubject = new ComSubject();
		 comSubject.setSubjectType(ComSubjectTypeEnum.PLACE.getCode());
		 return comSubjectService.findComSubjects(comSubject,Integer.MAX_VALUE);
	 }
	 
	private String doExcel(List excelList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
			
		try {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/commentTemplate.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/excel.xls";

			Map beans = new HashMap();
			beans.put("excelList", excelList);
			XLSTransformer transformer = new XLSTransformer();
			try {
				//new XLSTransformer().transformXLS(templateFileName, beans, destFileName);
				transformer.transformXLS(templateFileName, beans, destFileName);
			} catch (ParsePropertyException e) {
				e.printStackTrace();
				return INPUT;
			} catch (InvalidFormatException e) {
				e.printStackTrace();
				return INPUT;
			}
			File file = new File(destFileName);
			
			if (file != null && file.exists()) {				
				 this.excelFileName = file.getName();
				 this.excelStream = new FileInputStream(file);
				 return SUCCESS;
			} else {
				return INPUT;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Action(value="/commentManager/operator")
	public void operator()
	{
		Map<String, Object> param = new HashMap<String, Object>();
		String statusMessage = processOperator(operator, Long.parseLong(commentId));
		if(!statusMessage.equals("SUCCESS"))
		{
			param.put("success", false);
			param.put("errorMessage", statusMessage);
		}
		else
		{
			param.put("success", true);
		}
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}
	
	/**
	 * 处理对点评的操作
	 * @param operator
	 * @param commentId
	 * @return
	 */
	private String processOperator(String operator, Long commentId)
	{
		CommonCmtCommentVO comment = cmtCommentService.getCmtCommentByKey(commentId);
		if (comment == null) {
			return "无法找到点评" + commentId;
		}
		Long userUserId = comment.getUserId();
		String isAuditStatus = comment.getIsAudit();
		
		if ("isAuditAndIsBest".equals(operator)) { 	/* 审核通过并精华 */	
			if (!Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name().equals(isAuditStatus)) {
	    		return "只有待审核才能做该操作";
	    	}
			comment.setIsBest("Y");
			comment.setReviewStatus(Long.valueOf(Constant.REVIEW_STATUS.white.getCode()));
			fixIsAuditOperator(comment);
			
			if(userUserId != null){
				userUserProxy.addUserPoint(userUserId, "POINT_FOR_ESSENTIAL_COMMENT", null, comment.getCommentId().toString());
				if(comment.getProductId()!=null){
					//发送站内信
					ProdProduct product = prodProductService.getProdProductById(comment.getProductId());
					UserUser user=userUserProxy.getUserUserByPk(userUserId);
					commentLetter("Y","N",product.getProductName(),user,comment.getCommentId(),150);
				}else{
					//发送站内信
					Place place = placeService.queryPlaceByPlaceId(comment.getPlaceId());
					UserUser user=userUserProxy.getUserUserByPk(userUserId);
					commentLetter("Y","N",place.getName(),user,comment.getCommentId(),150);
				}
			} 
		} else if ("isBest".equals(operator)) { 	/* 设为精华 */
			if (!Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(isAuditStatus)) {
	    		return "只有审核通过才能做该操作";
	    	}
			comment.setIsBest("Y");
			comment.setIsHide("N");
			comment.setReviewStatus(Long.valueOf(Constant.REVIEW_STATUS.white.getCode()));
			cmtCommentService.update(comment);
			
			if(userUserId != null){
				userUserProxy.addUserPoint(userUserId, "POINT_FOR_ESSENTIAL_COMMENT", null, comment.getCommentId().toString());
				if(comment.getProductId()!=null){
					//发送站内信
					ProdProduct product = prodProductService.getProdProductById(comment.getProductId());
					UserUser user=userUserProxy.getUserUserByPk(userUserId);
					commentLetter("Y","N",product.getProductName(),user,comment.getCommentId(),150);
				}else{
					//发送站内信
					Place place = placeService.queryPlaceByPlaceId(comment.getPlaceId());
					UserUser user=userUserProxy.getUserUserByPk(userUserId);
					commentLetter("Y","N",place.getName(),user,comment.getCommentId(),150);
				}
			} 
		} else if ("isAudit".equals(operator)) { 	/* 审核通过 */
			if (Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(isAuditStatus)) {
	    		return "已经审核通过了！";
	    	}
			comment.setReviewStatus(Long.valueOf(Constant.REVIEW_STATUS.white.getCode()));
			fixIsAuditOperator(comment);
			
		} else if ("isNAudit".equals(operator)) { 	/* 审核不通过 */
			if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(isAuditStatus)) {
	    		return "已经审核不通过了！";
	    	}
			comment.setIsAudit(Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name());
			comment.setIsBest("N");
			comment.setIsHide("Y");
			comment.setReviewStatus(Long.valueOf(Constant.REVIEW_STATUS.black.getCode()));
			cmtCommentService.update(comment);
		} else if("show".equals(operator) || "hide".equals(operator)) {	//显示 或 隐藏 点评
			if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(isAuditStatus) || 
					Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name().equals(isAuditStatus)) {
	    		return "只有审核通过才能做该操作";
	    	}
			if("show".equals(operator)){
				comment.setIsHide("N");
			}
			if("hide".equals(operator)){
				comment.setIsHide("Y");
			} 
			cmtCommentService.update(comment);
		} else if ("isAuditAndHide".equals(operator)) { 	/* 审核通过并隐藏 */
			if (Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(isAuditStatus)) {
	    		return "已经审核通过了！";
	    	}
			fixIsAuditOperator(comment);
			comment.setReviewStatus(Long.valueOf(Constant.REVIEW_STATUS.white.getCode()));
			comment.setIsHide("Y");
			cmtCommentService.update(comment);
		} else if (Constant.CMT_EFFECT_TYPE.PROPOSAL.getCode().equals(operator)
				|| Constant.CMT_EFFECT_TYPE.COMPLAINT.getCode().equals(operator)) {
			/* 建议 或 投诉  */
			comment.setCmtEffectType(operator);
			cmtCommentService.update(comment);
		}
		
		return "SUCCESS";
	}
	
	
	/**
	 * 打开增加积分窗口
	 * @return
	 */
	@Action(value="/commentManager/openAddPoint",results={@Result(location = "/WEB-INF/pages/back/comment/addPoint.jsp")}) 
	public String openAddPoint()
	{
		getCurrentCommentInfo();
		return SUCCESS;
	}
	
	
	
	/**
	 * 手动增加积分
	 * @param point 积分
	 * @param parames 参数列表
	 */
	@Action(value="/commentManager/addPoint")
	public void addPoint() {
		if (point != 0L) {
			CommonCmtCommentVO commonCmtCommentVO = cmtCommentService.getCmtCommentByKey(Long.parseLong(commentId));
			Map<String, Object> param = new HashMap<String, Object>();
			if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(commonCmtCommentVO.getIsAudit()) || 
					Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name().equals(commonCmtCommentVO.getIsAudit())) {
				param.put("success", false);
				param.put("errorMessage", "只有审核通过才能加积分");
	    	}
			else
			{
				UserUser user = userUserProxy.getUserUserByPk(commonCmtCommentVO.getUserId());
				if(user == null){
					param.put("success", false);
					param.put("errorMessage", "接口有问题或系统没有找到该用户。");
				} else {
					userUserProxy.addUserPoint(user.getId(), "POINT_FOR_CUSTOMIZED_COMMENT", point, " 点评手动增加积分");
					param.put("success", true);
					param.put("successMessage", "增加成功。");
				}
			}
			try {
				getResponse().getWriter().print(JSONObject.fromObject(param));
			} catch (IOException ioe) {
				LOG.error(ioe.getMessage(), ioe.toString());
			}
		}
	}
	
	
	/**
	 * 打开.点评编辑窗口
	 * @return
	 */
	@Action(value="/commentManager/openEditComment",results={@Result(location = "/WEB-INF/pages/back/comment/editComment.jsp")}) 
	public String openEditComment() {
		getCurrentCommentInfo();
		return SUCCESS;
	}
	
	private void getCurrentCommentInfo()
	{
		currentComment = cmtCommentService.getCmtCommentByKey(Long.parseLong(commentId));
		
		if(currentComment.getCmtType().equals(Constant.EXPERIENCE_COMMENT_TYPE))
		{
			ProdProduct product = prodProductService.getProdProductById(currentComment.getProductId());
			if(product != null)
			{
				ProductCmtCommentVO productCmtCommentVO = CommentUtil.composeProductComment(currentComment, product, null);
				currentComment = productCmtCommentVO;
			}
		}
		else//Constant.EXPERIENCE_COMMON_TYPE
		{
			Place place = placeService.queryPlaceByPlaceId(currentComment.getPlaceId());
			PlaceCmtCommentVO placeCmtCommentVO = CommentUtil.composePlaceComment(currentComment, place);
			if(placeCmtCommentVO != null)
			{
				currentComment = placeCmtCommentVO;
			}
		}
		
		latitudeDescriptionInfo = getCmtLatitudeInfo(currentComment);
	}
	
	
	/**
	 * 保存更新点评
	 */
	@Action(value="/commentManager/saveComment")
	public void saveComment() throws Exception 
	{
		if (null != editContent) {
			editContent = URLDecoder.decode(editContent, "UTF-8");
		}
		
		currentComment = cmtCommentService.getCmtCommentByKey(Long.parseLong(commentId));
		currentComment.setContent(editContent);
		cmtCommentService.update(currentComment);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("success", true);
		try {
			getResponse().getWriter().print(JSONObject.fromObject(param));
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage(), ioe.toString());
		}
	}
	
	/**
	 * 获取点评维度描述信息
	 * @param comment
	 * @return
	 */
	private String getCmtLatitudeInfo(CommonCmtCommentVO comment) {
		List<CmtLatitudeVO> cmtLatitudes = comment.getCmtLatitudes();
		String info = "";
		if (cmtLatitudes != null) {
			for (CmtLatitudeVO cmtLatitudeVO : cmtLatitudes) {
				String name = getNameByLatitudeId(cmtLatitudeVO.getLatitudeId());
				info = info + name + "："
					+ Constant.getGradeNameByScore(cmtLatitudeVO.getScore()==null?0:cmtLatitudeVO.getScore()) + "  ";
			}
		}
		return info;
	}
	
	/**
	 * 根据纬度ID获取纬度名称
	 * @param latitudeId 纬度ID
	 * @return 纬度名称
	 */
	private String getNameByLatitudeId(final String latitudeId) {
		List<DicCommentLatitude> dicList = dicCommentLatitudeService.getDicCommentLatitudeList(new HashMap());
		for (DicCommentLatitude latitude : dicList) {
			if (latitude.getLatitudeId().equals(latitudeId)) {
				return latitude.getName();
			}
		}
		return "";
	}
	
	/**
	 * 处理点评审核通过操作(修改状态，送积分返现等)
	 * @param comment
	 */
	private void fixIsAuditOperator(CommonCmtCommentVO comment){
			Long userUserId = comment.getUserId();
			comment.setIsAudit(Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
			comment.setIsHide("N");	//审核通过默认显示
			comment.setAuditTime(new Date());
			cmtCommentService.update(comment);
			
			if(comment.getProductId() != null){
				//产品点评送积分(单条点评送积分)
				if(userUserId != null){
					userUserProxy.addUserPoint(userUserId, "POINT_FOR_EXPERIENCE_COMMENT", null, comment.getCommentId().toString());
					//发送站内信
					ProdProduct product = prodProductService.getProdProductById(comment.getProductId());
					UserUser user=userUserProxy.getUserUserByPk(userUserId);
					commentLetter("N","Y",product.getProductName(),user,comment.getCommentId(),100);
				} 
				//产品点评返现(订单一次性返现) 修改返现逻辑不实时返现，还是等履行完JOB来返现
				//cashRemoteService.cashByOrderIdAndCommentId(comment.getOrderId(), commentId);
			}else{
				//景点点评送积分
				if(userUserId != null){
					getUserUserProxy().addUserPoint(userUserId, "POINT_FOR_COMMON_COMMENT", null, comment.getCommentId().toString());
					//发送站内信
					Place place = placeService.queryPlaceByPlaceId(comment.getPlaceId());
					UserUser user=userUserProxy.getUserUserByPk(userUserId);
					commentLetter("N","Y",place.getName(),user,comment.getCommentId(),50);
				} 
					
				//景点点评招募送积分
				boolean needGiveRecruitPoint = false;
				Long result = null;
				//Long result = this.cmtRecommendPlaceService.queryByPlaceId(comment.getPlaceId());
				if(result != null)
				{
					//点评招募表里有这个景点，则必然给予招募积分
					needGiveRecruitPoint = true;
				}
				else
				{
				    //如果该点评对应的景点总点评数<=10条，则必然给予招募积分
					Map<String,Object> parm = new HashMap<String, Object>();
					parm.put("placeId", comment.getPlaceId());
					Long count = cmtCommentService.getCommentTotalCount(parm);
					if (count.intValue() <= NUMBER_OF_MAX_RECRUIT_COMMENT) {//如果这个景点点评数小于10条则送点评招募的积分
						needGiveRecruitPoint = true;
					}
				}
				if(needGiveRecruitPoint && (userUserId != null)) {
					//给予点评招募积分
					getUserUserProxy().addUserPoint(userUserId, "POINT_FOR_RECRUIT_COMMENT", null, comment.getCommentId().toString());
				}
			}
			
	}
	
	private List<CommonCmtCommentVO> composeProductComment(List<CommonCmtCommentVO> commonCmtCommentVOList){
		for(int i = 0; i < commonCmtCommentVOList.size(); i++)
		{
			CommonCmtCommentVO commonCmtCommentVO = commonCmtCommentVOList.get(i);
			if(commonCmtCommentVO.getCmtType().equals(Constant.EXPERIENCE_COMMENT_TYPE)
					&& (null != commonCmtCommentVO.getProductId()))
			{
				ProdProduct product = prodProductService.getProdProductById(commonCmtCommentVO.getProductId());
				if(product != null)
				{
					ProductCmtCommentVO productCmtCommentVO = CommentUtil.composeProductComment(commonCmtCommentVO, product, null);
					commonCmtCommentVOList.set(i, productCmtCommentVO);
				}
			}
			else if(null != commonCmtCommentVO.getPlaceId()){//Constant.EXPERIENCE_COMMON_TYPE
				Place place = placeService.queryPlaceByPlaceId(commonCmtCommentVO.getPlaceId());
				PlaceCmtCommentVO placeCmtCommentVO = CommentUtil.composePlaceComment(commonCmtCommentVO, place);
				if(placeCmtCommentVO != null)
				{
					commonCmtCommentVOList.set(i, placeCmtCommentVO);
				}
			}
		}
		return commonCmtCommentVOList;
	}
	
	private Map<String, Object> initParameters(Map<String, Object> parameters){
		if (StringUtils.isNotBlank(commentId)) {
			parameters.put("commentId", commentId);
		}
		if (StringUtils.isNotBlank(stage)) {
			parameters.put("stage", stage);
		}
		if (StringUtils.isNotBlank(productType)) {
			parameters.put("productType", productType);
		}
		if (StringUtils.isNotBlank(subProductType)) {
			parameters.put("subProductType", subProductType);
		}
		if (StringUtils.isNotBlank(subjectName)) {
			if (StringUtils.isNotBlank(stage)) {
				parameters.put("placeName", subjectName);
			}
			if (StringUtils.isNotBlank(productType)) {
				parameters.put("productName", subjectName);
			}
		}
		if (StringUtils.isNotBlank(isBest)) {
			parameters.put("isBest", isBest);
		}
		if (StringUtils.isNotBlank(userName)) {
			parameters.put("userName", userName);
		}
		if (StringUtils.isNotBlank(auditStatus)) {
			if(!"AUDIT_ALL".equalsIgnoreCase(auditStatus))
			{
				parameters.put("isAudit", auditStatus);
			}
		}
		if (StringUtils.isNotBlank(startDate)) {
			parameters.put("startDate", DateUtil.toDate(startDate, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(endDate)) {
			parameters.put("endDate", DateUtil.toDate(endDate, "yyyy-MM-dd"));
		}
		
		if (StringUtils.isNotBlank(cmtType)) {
			parameters.put("cmtType", cmtType);
		}
		if (StringUtils.isNotBlank(lastest3day)) {
			parameters.put("lastest3day", lastest3day);
		}
		if (StringUtils.isNotBlank(order)) {
			parameters.put(order, "true");
		} else {
			parameters.put("createTime321", "true");
		}
		if (StringUtils.isNotBlank(productId)) {
			parameters.put("productId", productId);
		}
		if (StringUtils.isNotBlank(hasPicture)) {
			parameters.put("hasPicture", hasPicture);
		}
		if (StringUtils.isNotBlank(channel)) {
			parameters.put("channel", channel);
		}
		if (StringUtils.isNotBlank(cmtEffectType)) {
			parameters.put("cmtEffectType", cmtEffectType);
		}
		if (StringUtils.isNotBlank(content)) {
			parameters.put("content", content);
		}
		
		parameters.put("isHide", "displayall");
		return parameters;
	}
	/**
	 * 站内信发送
	 * @param isBest
	 * @param isAudit
	 * @param subjectName
	 * @param user
	 * @param commentId
	 */
	private void commentLetter(String isBest ,String isAudit,String subjectName,UserUser user,Long commentId,Integer point){
		//加精华
		if("Y".equals(isBest)){
			String subject ="您发表的点评被加为精华";
			String message ="亲爱的 "+user.getUserName()+"：<br/>"+
							"<p>您发表的< "+subjectName+" >点评已被驴妈妈加为“精华点评”，获赠150积分。</p>" +
							"<p>详情请点击:<a target='_blank' href='http://www.lvmama.com/comment/"+commentId+"'>http://www.lvmama.com/comment/"+commentId+"</a></p>"+
							"<p>十分感谢你热心分享，你可以继续分享其它宝贵的旅行体验，给即将出发的驴友们更多帮助。 继续分享点评：<a href='http://www.lvmama.com/comment'>http://www.lvmama.com/comment</a></p><br/>";
			String type ="COMMENT";
			CommentUtil.synchLetter(subject,message,type,user.getUserNo());
		}
		//审核通过
		if("Y".equals(isAudit)){
			String subject ="您发表的点评已经通过审核";
			String jifen="同时获赠100积分，如该产品有对应奖金返回，随后将发送至您的账户。";
			if(point==50){
				jifen="同时获赠50积分。";
			}
			String message ="亲爱的 "+user.getUserName()+"：<br/>"+
							"<p>您对< "+subjectName+" >的点评已通过驴妈妈审核，"+jifen+"十分感谢您的热心分享！</p>" +
							"<p>详情请点击:<a target='_blank' href='http://www.lvmama.com/comment/"+commentId+"'>http://www.lvmama.com/comment/"+commentId+"</a></p><br/>";
			String type ="COMMENT";
			
			CommentUtil.synchLetter(subject,message,type,user.getUserNo());
		}
	}
	
	
		
	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getIsBest() {
		return isBest;
	}

	public void setIsBest(String isBest) {
		this.isBest = isBest;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
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

	public String getCmtType() {
		return cmtType;
	}

	public void setCmtType(String cmtType) {
		this.cmtType = cmtType;
	}
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getLastest3day() {
		return lastest3day;
	}

	public void setLastest3day(String lastest3day) {
		this.lastest3day = lastest3day;
	}

	public List<CommonCmtCommentVO> getCommonCmtCommentVOList() {
		return commonCmtCommentVOList;
	}

	public void setCommonCmtCommentVOList(List<CommonCmtCommentVO> commonCmtCommentVOList) {
		this.commonCmtCommentVOList = commonCmtCommentVOList;
	}

	public CmtCommentService getCmtCommentService() {
		return cmtCommentService;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public PlaceService getPlaceService() {
		return placeService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBatchCommentIds() {
		return batchCommentIds;
	}

	public void setBatchCommentIds(String batchCommentIds) {
		this.batchCommentIds = batchCommentIds;
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public CommonCmtCommentVO getCurrentComment() {
		return currentComment;
	}

	public void setCurrentComment(CommonCmtCommentVO currentComment) {
		this.currentComment = currentComment;
	}

	public DicCommentLatitudeService getDicCommentLatitudeService() {
		return dicCommentLatitudeService;
	}

	public void setDicCommentLatitudeService(DicCommentLatitudeService dicCommentLatitudeService) {
		this.dicCommentLatitudeService = dicCommentLatitudeService;
	}

	public String getLatitudeDescriptionInfo() {
		return latitudeDescriptionInfo;
	}

	public void setLatitudeDescriptionInfo(String latitudeDescriptionInfo) {
		this.latitudeDescriptionInfo = latitudeDescriptionInfo;
	}

	public String getEditContent() {
		return editContent;
	}

	public void setEditContent(String editContent) {
		this.editContent = editContent;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}
	
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getHasPicture() {
		return hasPicture;
	}

	public void setHasPicture(String hasPicture) {
		this.hasPicture = hasPicture;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCmtEffectType() {
		return cmtEffectType;
	}

	public void setCmtEffectType(String cmtEffectType) {
		this.cmtEffectType = cmtEffectType;
	}

	public long getScrollTopHight() {
		return scrollTopHight;
	}

	public void setScrollTopHight(long scrollTopHight) {
		this.scrollTopHight = scrollTopHight;
	}

	public void setComSubjectService(ComSubjectService comSubjectService) {
		this.comSubjectService = comSubjectService;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public ProdProductPlaceService getProdProductPlaceService() {
		return prodProductPlaceService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public List<CodeItem> getSubProductTypeList(){
		 List<CodeItem> codeList = new ArrayList<CodeItem>();
		 CodeItem code=new CodeItem("", "全部");
		 codeList.add(code);
		 codeList.addAll(com.lvmama.comm.utils.ProductUtil.getRouteSubTypeList());
		 return codeList;
	}

	/**
	 * @return the subProductType
	 */
	public String getSubProductType() {
		return subProductType;
	}

	/**
	 * @param subProductType the subProductType to set
	 */
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

}
