package com.lvmama.front.web.comment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.po.comment.CmtActivity;
import com.lvmama.comm.pet.po.comment.CmtRecommendPlace;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.conn.SensitiveKeys;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.service.comment.CmtActivityService;
import com.lvmama.comm.pet.service.comment.DicCommentLatitudeService;
import com.lvmama.comm.pet.service.conn.SensitiveKeysService;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;
import com.lvmama.comm.vo.comment.CmtPictureVO;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 写点评
 * @author yuzhizeng
 */
@ParentPackage("lvInputInterceptorPackage")
@ResultPath("/lvInputInterceptor")
@Results({
	@Result(name = "writeComment", location = "/WEB-INF/pages/comment/writeComment.ftl", type = "freemarker"),
	@Result(name = "writeCmtCommon", location = "/WEB-INF/pages/comment/writeCmtCommon.ftl", type = "freemarker"),
	@Result(name = "writeSuccess", location = "/WEB-INF/pages/comment/writeCommentSucceed.ftl", type = "freemarker"),
	@Result(name = "cmtIndexPage", location = "/comment/comment.do", params={"errorNo","%{errorNo}"},type = "redirect"),
	@Result(name = "fillCommentFail",params={"actionName","fillCommentFail","namespace","/comment"}, type = "chain")

})
public class WriteCommentAction extends CmtBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -5553323042965308823L;
	 
	/**
	 * 点评活动的Service
	 */
	private CmtActivityService cmtActivityService;
	/**
	 * 关键词过滤Service
	 */
	private SensitiveKeysService sensitiveKeysService;
	/**
	 * 景区标识
	 */
	private Long placeId;
	/** 
	 * 产品标识
	 */
	private String productId;

	/**
	 * 景区
	 */
	private Place place;
	/**
	 * 点评维度列表
	 */
	private List<DicCommentLatitude> commentLatitudeList;
	/**
	 * 上传的图片列表
	 */
	private File[] fileData;
	/**
	 * 上传图片的文件名
	 */
	private String[] fileDataFileName;
	/**
	 * 点评维度列表
	 */
	private String[] latitudeIds;
	/**
	 * 点评维度评分列表
	 */
	private String[] scores;
	/**
	 * 点评内容
	 */
	private String content;
	/**
	 * 订单ID
	 */
	private Long orderId;
	/**
	 * 点评招募景点
	 */
	private List<CmtRecommendPlace> recommendPlaceList = new ArrayList<CmtRecommendPlace>();
	/**
	 * 是否显示错误信息
	 */
	private String showErrorMessage = "false";
	/**
	 * 预览的点评
	 */
	private CommonCmtCommentVO cmtComment;
	/**
	 * 该景区其它点评
	 */
	private List<CommonCmtCommentVO> otherCommentList;
	/**
	 * 景区统计
	 */
	private CmtTitleStatisticsVO cmtCommentStatisticsVO;
	/**
	 * 点评标识
	 */
	private Long commentId;
	/**
	 * 点评活动
	 */
	private CmtActivity cmtActivity;
	
	/**
	 * 是否是独立的写点评页面(VALUE=Y,N)
	 */
	private String siglePage = "Y";
	/**
	 * 上传的照片描述
	 */
	private String[] photoDescList;
	
	//产品的类型
	private String productType;
	
	private ProdProductPlaceService prodProductPlaceService;
	
	private ProdProduct product;
	//用户当前待点评数
	private int needProductCommentCount = 0;
	
	/**
	 * 填写点评的页面
	 * @return 填写点评的页面
	 */
	@Action(
			value="/comment/writeComment/fillComment"
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					/*@InterceptorRef(value = "params", params = {"excludeParams","dojo\..*"}),
					@InterceptorRef(value = "validation", params = {"excludeMethods","input,back,cancel,browse"}),
					@InterceptorRef(value = "workflow", params = {"input,back,cancel,browse"}),*/
					@InterceptorRef("defaultStack")
			}
	)
	public String fillComment() {
		users = getUser();//页面使用 用来判断是否已经登录
		String returnPage =  fillCommentCommonFun();
		if(ERROR_PAGE.equals(returnPage)){
			return ERROR_PAGE;
		}else if(SUCCESS.equals(returnPage)){
			return "writeComment";
		} else if(!StringUtils.isEmpty(returnPage)){
			return returnPage;
		}else{
			return ERROR_PAGE;
		}		
	}
	
	/**
	 * 填写点评的失败页面
	 * @return 
	 */
	@Action(value="/comment/fillCommentFail")
	public String fillCommentFail() {
		
		users = getUser();//页面使用 用来判断是否已经登录
		showErrorMessage = "true";
		String returnPage = fillCommentCommonFun();
		if (ERROR_PAGE.equals(returnPage)) {
			return ERROR_PAGE;
		} else if (SUCCESS.equals(returnPage)) {
			return "writeComment";
		} else if (!StringUtils.isEmpty(returnPage)) {
			return returnPage;
		} else {
			return ERROR_PAGE;
		}
	}
	
	/**
	 * 填写点评的IFRAME子页面
	 * @return 填写点评的IFRAME子页面
	 */
	@Action(
			value="/comment/writeComment/getCommonFillComment"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String getCommonFillComment() {
		users = getUser();//页面使用 用来判断是否已经登录
		String returnPage =  fillCommentCommonFun();
		if(ERROR_PAGE.equals(returnPage)) {
			return ERROR_PAGE;
		} else if(SUCCESS.equals(returnPage)){
			return "writeCmtCommon";
		} else if(!StringUtils.isEmpty(returnPage)){
			return returnPage;
		} else {
			return ERROR_PAGE;
		}		
	}
	

	/**
	 * 保存点评内容，包括图片
	 * @return 点评成功页面或者失败页面
	 */
	@Action(
			value="/comment/writeComment"		
			,interceptorRefs={
					@InterceptorRef(value = "lvFileUploadInterceptor", params = {"allowedTypes","image/jpeg,image/gif, image/png,image/pjpeg,image/x-png","maximumSize","5242880"}),
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					/*@InterceptorRef(value = "params", params = {"excludeParams","dojo\..*"}),
					@InterceptorRef(value = "validation", params = {"excludeMethods","input,back,cancel,browse"}),
					@InterceptorRef(value = "workflow", params = {"input,back,cancel,browse"}),*/
					@InterceptorRef("defaultStack")
			}
	)
	@InputConfig(resultName="fillCommentFail")
	public String writeComment() {
		users = getUser();
		
		String flag="N";
		if (null == users) {
			LOG.info("user not exists, can't comment");
			return "cmtIndexPage";
		}
		log.info("user "+users.getId()+" save write comment");
		if (null == placeId && null == productId) {
			LOG.error("place id is null or product id is null, can't comment");
			return ERROR_PAGE;
		}

		Map<String,Object> parameters = new HashMap<String, Object>();
		
		//控制用户1天写8条普通点评
		if (placeId != null && StringUtils.isBlank(productId)) {
			parameters.put("userId", users.getId());
			parameters.put("cmtType", Constant.COMMON_COMMENT_TYPE);
			parameters.put("isHide", "displayall");
			Calendar currentDate = Calendar.getInstance();
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			parameters.put("startDate", currentDate.getTime());
			parameters.put("endDate",  currentDate.getTime());
			Long count = cmtCommentService.getCommentTotalCount(parameters);
			if(count >= 8){
				LOG.info(users.getId() + " userId write comment is frequently.");
				errorNo = OVER;
				return "cmtIndexPage";
			}
		}
		  
		String saveContent = null;
		if (!StringUtils.isEmpty(content)) {
			saveContent = changeContent(content);
		}
		else
		{
			LOG.info("comment content is null");
			return ERROR_PAGE;
		}
		
		List<SensitiveKeys> sensitiveKeys = sensitiveKeysService.queryAll();
		for (SensitiveKeys sensitiveKey : sensitiveKeys) {
			if (saveContent.indexOf(sensitiveKey.getKeys()) != -1) {
				flag = "Y";
			}
		}
		String reqIp = InternetProtocol.getRemoteAddr(getRequest());
		    
		if (null != placeId) {//1,插入景点点评
			place = placeService.queryPlaceByPlaceId(placeId);
			if (null == place) {
				LOG.error("can't find place: " + placeId + ", so can't write place comment");
				return ERROR_PAGE;
			}
			
			CommonCmtCommentVO vo = new CommonCmtCommentVO();
			vo.setPlaceId(place.getPlaceId());
			vo.setCmtType(Constant.COMMON_COMMENT_TYPE);
			vo.setContent(saveContent);
			vo.setCmtLatitudes(getCmtLatitude());
			vo.setCmtPictureList(getCmtPicture());
			vo.setChannel(Constant.CHANNEL.FRONTEND.getCode());
			vo.setIsHasSensitivekey(flag);
			vo.setReqIp(reqIp);
			commentId = cmtCommentService.insert(users, vo);
		}
		else if (null != productId) {//2,插入产品点评
			product = queryProductInfoByProductId(Long.parseLong(productId));
			if (null == product) {
				LOG.error("product "+productId+" is null, can't comment");
				return ERROR_PAGE;
			}
			String result = checkConditionForProdCmting(product, orderId);
			if(!SUCCESS.equals(result)){
				return result;
			}
			CommonCmtCommentVO comment = new CommonCmtCommentVO();
			
			//对于门票产品点评和酒店产品点评需要保存目的地
			if(product.getProductType().equals(Constant.PRODUCT_TYPE.TICKET.name()) 
					|| product.getProductType().equals(Constant.PRODUCT_TYPE.HOTEL.name())) {
				Long destPlaceId = getProductCommentDestId(Long.parseLong(productId));
				if(destPlaceId == null)
				{
					return ERROR_PAGE;
				}
				comment.setPlaceId(destPlaceId);
			} 
			comment.setCmtType(Constant.EXPERIENCE_COMMENT_TYPE);
			comment.setProductId(product.getProductId());
			comment.setOrderId(orderId);
			comment.setContent(saveContent);
			comment.setCmtLatitudes(getCmtLatitude());
			comment.setCmtPictureList(getCmtPicture());
			comment.setChannel(Constant.CHANNEL.FRONTEND.getCode());
			comment.setIsHasSensitivekey(flag);
			comment.setReqIp(reqIp);
			commentId = cmtCommentService.insert(users, comment);
		}

		//活动广告
		cmtActivity = cmtActivityService.queryById(Long.valueOf(2));
		recommendPlaceList = getRecruitPlaceCommentList();
		needProductCommentCount = needProductCommentNum();
		
		return "writeSuccess";
	}
	 
	private int needProductCommentNum(){
		int count = 0;
		//获取可点评的订单信息
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByUserNo(getUser().getUserId());
		for(OrderAndComment canCommentOrderProduct : canCommentOrderProductList)
		{
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("orderId", canCommentOrderProduct.getOrderId());
			parameters.put("productId", canCommentOrderProduct.getProductId());
			parameters.put("isHide", "displayall");
			List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parameters);
			if(cmtCommentList == null || cmtCommentList.size() == 0){
				count ++;	//该订单产品未被点评过，可以点评
			}
		}
		return count;
	}
	
	/**
	 * 初始化写点界面
	 * @return
	 */
	private String fillCommentCommonFun()
	{
		if (null == placeId && null == productId) {
			LOG.error("place id is null or product id is null, can't comment");
			return ERROR_PAGE;
		} 

		if (null != placeId) {//1,写景点点评获取纬度
			
			//写普通点评控制曾经写敏感词的用户发普通点评he写点评过于批量
			String result = checkConditionForPlaceCmting();
			if(!SUCCESS.equals(result)){
				return result;
			}
			place = placeService.queryPlaceByPlaceId(placeId);
			if (null == place) {
				LOG.error("place is null, can't comment");
				return ERROR_PAGE;
			}
			commentLatitudeList = dicCommentLatitudeService.getDicCommentLatitudeListBySubject(place);
			if(commentLatitudeList == null || commentLatitudeList.size() == 0){
				log.error("place "+placeId+" get commentLatitudeList is null");
				return ERROR_PAGE;
			}
		}
		else if(null != productId){//2,写产品点评获取维度
			product = queryProductInfoByProductId(Long.parseLong(productId));
			if (null == product) {
				LOG.error("product "+productId+" is null, can't comment");
				return ERROR_PAGE;
			}
			String result = checkConditionForProdCmting(product, orderId);
			if(!SUCCESS.equals(result)){
				return result;
			}
			
			//对于酒店和门票的产品点评，要插入对应目的地信息，并使用对应目的地的维度
			if(product.getProductType().equals(Constant.PRODUCT_TYPE.TICKET.name()) 
					|| product.getProductType().equals(Constant.PRODUCT_TYPE.HOTEL.name()))
			{
				//产品目的地参数
				Long destPlaceId = getProductCommentDestId(Long.parseLong(productId));
				if(destPlaceId == null) return ERROR_PAGE; 
				
				Place toPlace = placeService.queryPlaceByPlaceId(destPlaceId);
				if(toPlace == null) return ERROR_PAGE; 
				commentLatitudeList = dicCommentLatitudeService.getLatitudesOfProduct(toPlace, productType);
				
			} else {
				//线路
				commentLatitudeList = dicCommentLatitudeService.getLatitudesOfProduct(null, productType);
			}
			
			if(commentLatitudeList == null || commentLatitudeList.size() == 0){ 
				log.error("product "+productId+" get commentLatitudeList is null");
				return ERROR_PAGE;
			}	
		}
		return SUCCESS;
	}

	/**
	 * 获取点评产品所对应的目的地
	 * @return
	 */
	private Long getProductCommentDestId(Long productId) {
		Long destPlaceId = prodProductPlaceService.selectDestByProductId(productId);
		
		if(destPlaceId == null){
			List<ProdProductPlace> prodProductPlaceList =  prodProductPlaceService.getProdProductPlaceListByProductId(productId);
			if (prodProductPlaceList != null && prodProductPlaceList.size() > 0) {
				destPlaceId = prodProductPlaceList.get(0).getPlaceId();
			} else {
				LOG.error("the dest place of product is null " + productId);
				return null;
			}
		}
		return destPlaceId;
	}

	/**
	 * 把指定的文件上传到专用的静态文件服务器上，返回URL
	 * @param file
	 * @return String
	 */
	private String postToRemote(File f, String filePath, String fileName) {
		try {
			PostMethod filePost = new PostMethod(Constant.getInstance().getUploadUrl());
			String path = filePath + fileName;
			Part[] parts = { new StringPart("fileName", path),
					new FilePart("ufile", f) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			int status = client.executeMethod(filePost);
			if (status == 200) {
				return path;
			} else {
				LOG.error("ERROR, return: " + status);
			}
		} catch (IOException e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}
		return null;
	}

	/**
	 * 获取点评纬度的对象
	 * @return 纬度的集合
	 */
	private List<CmtLatitudeVO> getCmtLatitude() {
		// 获取指标
		List<CmtLatitudeVO> latitudes = new ArrayList<CmtLatitudeVO>();
		for (int i = 0; i < latitudeIds.length; i++) {
			CmtLatitudeVO cmtLatitudeVO = new CmtLatitudeVO();
			cmtLatitudeVO.setLatitudeId(latitudeIds[i]);
			cmtLatitudeVO.setScore(Integer.parseInt(scores[i].substring(scores[i]
					.length() - 1)));
			latitudes.add(cmtLatitudeVO);
		}

		return latitudes;
	}

	/**
	 * 获取图片
	 * @return 图片的集合
	 */
	private List<CmtPictureVO> getCmtPicture() {
		// 获取图片
		List<CmtPictureVO> pictures = new ArrayList<CmtPictureVO>();
		if (null != fileData && null != fileDataFileName) {
			for (int i = 0; i < fileData.length; i++) {
				String fileName = new UUIDGenerator().generate().toString()
						+ getSuffixName(fileDataFileName[i]);
				String imageUrl = postToRemote(fileData[i],Constant.getInstance().getFckeditorImgContextPath(), fileName);
				CmtPictureVO cp = new CmtPictureVO();
				cp.setDescription(photoDescList[i]);
				cp.setPicUrl(imageUrl);
				pictures.add(cp);
			}
		}
		return pictures;
	}

	/**
	 * 获取后缀名
	 * @param filename 文件名 
	 * @return String
	 */
	private String getSuffixName(final String filename) {
		if (null != filename && filename.indexOf(".") != -1) {
			return filename.substring(filename.lastIndexOf("."));
		} else {
			return "";
		}
	}

	/*
	 * 获取维度前校验业务(针对产品)
	 * */
	private String checkConditionForProdCmting(ProdProduct product, Long orderId){
		if (null == product || null == product.getProductId()) {
			log.error("product or product id is is null can't comment");
			return ERROR_PAGE;
		}
		if (null == orderId) {
			log.error("order is is null can't comment");
			return ERROR_PAGE;
		}
		
		users = getUser();
		if(null == users){
			return "cmtIndexPage";
		}
			
		//检查该订单是否是本人的
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(ordOrder != null){
			if(ordOrder.getUserId().equals(users.getUserNo())){
				//确保订单是本人的
			}else{
				LOG.error("not user " + users.getUserId() + "'s order "+orderId+", can't comment");
				return ERROR_PAGE;
			}
		} else {
			LOG.error("order doesn't exist, can't comment "+orderId+","+users.getUserId());
			return ERROR_PAGE;
		}
		
		//该订单已写产品点评,转点评首页
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orderId", orderId);
		parameters.put("productId", product.getProductId());
		parameters.put("getProductCmts", "Y");
		parameters.put("isHide", "displayall");//查所有点评
		Long count = cmtCommentService.getCommentTotalCount(parameters);
		if(count > 0){
			errorNo = "REPEAT_CMT_ORDER";
			LOG.error("user order: "+ orderId +"product: "+product.getProductId()+"has already commented");
			return "cmtIndexPage";
		}
		return SUCCESS;
	}
	
	/*
	 * 获取维度前校验业务(针对景点普通点评)
	 * */
	private String checkConditionForPlaceCmting(){
			Map<String, Object> parameters = new HashMap<String, Object>();
			users = getUser();
			if(users != null){
				parameters.put("userId", users.getId());
				parameters.put("cmtType", Constant.COMMON_COMMENT_TYPE);
				parameters.put("isHasSensitivekey", "Y");
				parameters.put("isHide", "displayall");
				Long userCount = cmtCommentService.getCommentTotalCount(parameters);
				if (userCount > 0) {
					LOG.info(users.getId() + " userId write comment with Sensitivekey.");
					return ERROR_PAGE;
				}
				/*parameters.clear();
				String reqIp = InternetProtocol.getRemoteAddr(getRequest());
				parameters.put("reqIp", reqIp);
				parameters.put("cmtType", Constant.COMMON_COMMENT_TYPE);
				parameters.put("isHasSensitivekey", "Y");
				parameters.put("isHide", "displayall");
				Long reqIpCount = cmtCommentService.getCommentTotalCount(parameters);
				if (reqIpCount > 0) {
					LOG.info(reqIp + " IP write comment with Sensitivekey.");
					return ERROR_PAGE;
				}
				*/
				parameters.clear();
				parameters.put("userId", users.getId());
				parameters.put("cmtType", Constant.COMMON_COMMENT_TYPE);
				parameters.put("isHide", "displayall");
				parameters.put("lastest1day", "Y");
				Long threeCmtCount = cmtCommentService.getCommentTotalCount(parameters);
				if (threeCmtCount > 7) {
					LOG.info(users.getId() + " userId write comment is frequently.");
					errorNo = OVER;
					return "cmtIndexPage";
				}
		}
		return SUCCESS;
	}
	
	 
	/**
	 *  ------------------------------------  get and set property -------------------------------------------
	 */
	
	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(final Long placeId) {
		this.placeId = placeId;
	}

	public Place getPlace() {
		return place;
	}

	public void setFileData(final File[] fileData) {
		this.fileData = fileData;
	}

	public void setFileDataFileName(final String[] fileDataFileName) {
		this.fileDataFileName = fileDataFileName;
	}

	public List<DicCommentLatitude> getCommentLatitudeList() {
		return commentLatitudeList;
	}

	public void setDicCommentLatitudeService(
			final DicCommentLatitudeService dicCommentLatitudeService) {
		this.dicCommentLatitudeService = dicCommentLatitudeService;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public String getShowErrorMessage() {
		return showErrorMessage;
	}

	public void setShowErrorMessage(String showErrorMessage) {
		this.showErrorMessage = showErrorMessage;
	}

	public void setLatitudeIds(final String[] latitudeIds) {
		this.latitudeIds = latitudeIds;
	}

	public void setScores(final String[] scores) {
		this.scores = scores;
	}

	public Long getCommentId() {
		return commentId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(final String productId) {
		this.productId = productId;
	}
	 
	public void setOrderId(final Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setCmtActivityService(CmtActivityService cmtActivityService) {
		this.cmtActivityService = cmtActivityService;
	}

	public void setSensitiveKeysService(SensitiveKeysService sensitiveKeysService) {
		this.sensitiveKeysService = sensitiveKeysService;
	}

	public String getSiglePage() {
		return siglePage;
	}

	public void setSiglePage(String siglePage) {
		this.siglePage = siglePage;
	}

	public String[] getPhotoDescList() {
		return photoDescList;
	}

	public void setPhotoDescList(String[] photoDescList) {
		this.photoDescList = photoDescList;
	}

	public CmtActivity getCmtActivity() {
		return cmtActivity;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setCmtComment(CommonCmtCommentVO cmtComment) {
		this.cmtComment = cmtComment;
	}

	public void setOtherCommentList(List<CommonCmtCommentVO> otherCommentList) {
		this.otherCommentList = otherCommentList;
	}

	public void setCmtCommentStatisticsVO(
			CmtTitleStatisticsVO cmtCommentStatisticsVO) {
		this.cmtCommentStatisticsVO = cmtCommentStatisticsVO;
	}


	public List<CmtRecommendPlace> getRecommendPlaceList() {
		return recommendPlaceList;
	}

	public CommonCmtCommentVO getCmtComment() {
		return cmtComment;
	}

	public List<CommonCmtCommentVO> getOtherCommentList() {
		return otherCommentList;
	}

	public CmtTitleStatisticsVO getCmtCommentStatisticsVO() {
		return cmtCommentStatisticsVO;
	}

	public ProdProductPlaceService getProdProductPlaceService() {
		return prodProductPlaceService;
	}

	public void setProdProductPlaceService(ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public ProdProduct getProduct() {
		return product;
	}

	public void setProduct(ProdProduct product) {
		this.product = product;
	}

	public String getContent() {
		return content;
	}

	public int getNeedProductCommentCount() {
		return needProductCommentCount;
	}

	public void setNeedProductCommentCount(int needProductCommentCount) {
		this.needProductCommentCount = needProductCommentCount;
	}
}
