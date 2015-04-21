package com.lvmama.clutter.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobileComment;
import com.lvmama.clutter.service.IClientCommentService;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.client.ClientCmtPlace;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtPictureVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;

public class ClientCommentServiceImpl extends AppServiceImpl implements IClientCommentService  {

	@Override
	public Map<String,Object> getPlaceComment(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("placeId","page",param);
		Long placeId = Long.valueOf(param.get("placeId").toString());
		Long page = Long.valueOf(param.get("page").toString());
		String userId  = null;
		if(param.get("userId")!=null){
			userId = param.get("userId").toString();
		}
		
		Map<String,Object> paramters = this.defaultParamters(page);
		String[] isAudits={com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name()};
		paramters.put("isAudits", isAudits);
		paramters.put("placeId", placeId);
		UserUser user = null;
		if(!StringUtil.isEmptyString(userId)){
			user =  userUserProxy.getUserUserByUserNo(userId);
			if(null != user) {
				// CMT_COMMENT.xml 82行 <isNotEmpty prepend=" AND " property="notEqualUserId">CC.USER_ID &lt;&gt; #notEqualUserId#</isNotEmpty>
				paramters.put("notEqualUserId", user.getId());
			}
		}
	
		paramters.put("isHid", "N");
		paramters.put("createTime321", "true");
		Page<CommonCmtCommentVO> pageConfig = 	this.cmtCommentService.queryCmtCommentListForApp(paramters);
		List<MobileComment> userAuditCmtList = this.queryMyComment(user, placeId);
		List<MobileComment> cmtList = new ArrayList<MobileComment> ();
		if (userAuditCmtList!=null&&userAuditCmtList.size()!=0&&page==1L) {
			cmtList.addAll(userAuditCmtList);
		}
	
		for (CommonCmtCommentVO cmtCommentVO : pageConfig.getItems()) {
			MobileComment ccp   = new MobileComment();
			this.covertComment(ccp, cmtCommentVO);
			if(null == cmtCommentVO.getSumaryLatitude()) {
				ccp.setAvgScore("0");
			} else {
				ccp.setAvgScore(cmtCommentVO.getSumaryLatitude().getScore()+"");
			}
			cmtList.add(ccp);
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("datas", cmtList);
		resultMap.put("isLastPage", this.isLastPage(pageConfig));
		return resultMap;
	}
	
	
	/**
	 * 根据用户ID 查询我点评的未审核的点评数据
	 * @return
	 */
	private List<MobileComment> queryMyComment(UserUser user,Long placeId){
		if (user==null) {
			return null;
		}
		Map<String,Object> paramters = this.defaultParamters(1L);
		paramters.put("pageSize", 100L);
		paramters.put("currentPage", "1");
		String[] isAudits={com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name(),com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name(),com.lvmama.comm.vo.Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name()};
		paramters.put("isAudits", isAudits);
		paramters.put("userId", user.getId());
		paramters.put("placeId", placeId);
		paramters.put("isHid", "N");
		paramters.put("createTime321", "true");
		Page<CommonCmtCommentVO> pageConfig = 	this.cmtCommentService.queryCmtCommentListForApp(paramters);
		List<MobileComment> cmtList = new ArrayList<MobileComment> ();
		for (CommonCmtCommentVO cmtCommentVO : pageConfig.getItems()) {
			MobileComment ccp   = new MobileComment();
			 this.covertComment(ccp, cmtCommentVO);
			 if(cmtCommentVO.getSumaryLatitude()==null){
				 continue;
			 }
			 ccp.setAvgScore(cmtCommentVO.getSumaryLatitude().getScore()+"");
			 cmtList.add(ccp);
		}
		return cmtList;
	}
	
	
	private void covertComment( MobileComment ccp,CommonCmtCommentVO commonCmtCommentVO){
		 ccp.setAvgScore(commonCmtCommentVO.getAvgScore()==null?"":commonCmtCommentVO.getAvgScore()+"");
		 ccp.setContent(commonCmtCommentVO.getContent());
		 ccp.setCreatedTimeStr(com.lvmama.comm.utils.DateUtil.formatDate(commonCmtCommentVO.getCreatedTime(), "yyyy-MM-dd HH:mm:ss"));
		 ccp.setBest(Boolean.valueOf("Y".equals(commonCmtCommentVO.getIsBest())));
		 ccp.setExperience(com.lvmama.comm.vo.Constant.EXPERIENCE_COMMENT_TYPE.equals(commonCmtCommentVO.getCmtType())?true:false);
		 ccp.setCashRefund(commonCmtCommentVO.getCashRefund()==null?"":commonCmtCommentVO.getCashRefund()+"");
		 ccp.setPoint(commonCmtCommentVO.getPoint()+"");
		 ccp.setUserName(commonCmtCommentVO.getUserName());
		 
		 /*
		  * 设置点评的图片
		  */
		 List<CmtPictureVO> cmtPictureVOs = commonCmtCommentVO.getCmtPictureList();
		 if(null!=cmtPictureVOs){
			 List<String> largeImages = new ArrayList<String>();
			 for(CmtPictureVO cmtPictureVO:cmtPictureVOs){
				 largeImages.add(cmtPictureVO.getPicUrl());
			 }
			 ccp.setLargeImages(largeImages);
		 }
		 
		 if(commonCmtCommentVO.getUserName()!=null ){
			String shortName = StringUtil.replaceOrCutUserName(-1, commonCmtCommentVO.getUserName());
			 ccp.setUserName(shortName);
		 }
		 ccp.setAuditStatu(commonCmtCommentVO.getIsAudit());
		 ccp.setChAudit(commonCmtCommentVO.getChAudit());
		
		 if(null != commonCmtCommentVO.getCmtType() && commonCmtCommentVO.getCmtType().equals(Constant.EXPERIENCE_COMMENT_TYPE))
		    {
		     ProdProduct product = prodProductService.getProdProductById(commonCmtCommentVO.getProductId());
		     if(product != null)
		     {
		      ProductCmtCommentVO productCmtCommentVO = CommentUtil.composeProductComment(commonCmtCommentVO, product, null);
		      ccp.setProductName(productCmtCommentVO.getProductName());
		     }
		    }
		    else
		    {
		     Place place = placeService.queryPlaceByPlaceId(commonCmtCommentVO.getPlaceId());
		     PlaceCmtCommentVO placeCmtCommentVO = CommentUtil.composePlaceComment(commonCmtCommentVO, place);
		     if(placeCmtCommentVO != null)
		     {
		    	 ccp.setPlaceName(placeCmtCommentVO.getPlaceName());
		     }
		     }

		 ccp.setPlaceId(commonCmtCommentVO.getPlaceId()+"");
		 ccp.setCmtLatitudes(commonCmtCommentVO.getCmtLatitudes());
		 ccp.setCmtType(commonCmtCommentVO.getCmtType());
		 ccp.setOrderId(commonCmtCommentVO.getOrderId()+"");
	}


	/**
	 * 产品点评列表
	 */
	@Override
	public Map<String, Object> getProductComment(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("productId","page",param);
		
		Long productId = Long.valueOf(param.get("productId")+"");
		long productPage = Long.valueOf(param.get("page")+"");
		long productPageSize = 10;
		if(null != param.get("count")) {
			productPageSize = Long.valueOf(param.get("count")+"");
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		parameters.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		Long totalRecords = cmtCommentService.getCommentTotalCount(parameters);
		
		long pageConfigNum = 120;
		pageConfigNum = Math.min(totalRecords, pageConfigNum);//控制页面显示记录数
		// 产品点评分页配置信息
		Page<MobileComment>  productPageConfig = Page.page(pageConfigNum, productPageSize, productPage);
		List<CommonCmtCommentVO>  cmtCommentVOList = null;
		parameters.put("_startRow", productPageConfig.getStartRows() + "");
		parameters.put("_endRow", productPageConfig.getEndRows() + "");
		parameters.put("createTime321", "true");
		List<MobileComment> cmtList = new ArrayList<MobileComment> ();
		
		/************* 获取用户的点评数 最新100条*************/
		String userId  = null;
		if(param.get("userId")!=null){
			userId = param.get("userId").toString();
		}
		UserUser user = null;
		if(!StringUtil.isEmptyString(userId)){
			user =  userUserProxy.getUserUserByUserNo(userId);
		}
		if(user != null){
			Long uId = user.getId();
			//获取用户的100条最新点评
			if(userId != null && productPage == 1){
				cmtCommentVOList = getLastestUserProductCmts(uId, productId); 
				// 如果用户登陆
				parameters.put("notEqualUserId", user.getId());
			}
		}	
		
		/************* 获取其它点评*************/
		if(null == cmtCommentVOList) {
			cmtCommentVOList = new ArrayList<CommonCmtCommentVO>();
		}
		cmtCommentVOList.addAll(cmtCommentService.getCmtCommentList(parameters));
		if(null != cmtCommentVOList) {
			for(CommonCmtCommentVO cmtCommentVO : cmtCommentVOList){
				MobileComment ccp   = new MobileComment();
				this.covertComment(ccp, cmtCommentVO);
				if(null == cmtCommentVO.getSumaryLatitude()) {
					ccp.setAvgScore("0");
				} else {
					ccp.setAvgScore(cmtCommentVO.getSumaryLatitude().getScore()+"");
				}
				cmtList.add(ccp);
			}
		}
		
		
		productPageConfig.setItems(cmtList);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("datas", cmtList);
		resultMap.put("isLastPage", this.isLastPage(productPageConfig));
		return resultMap;
	}
	
	
	/**
	 * 获取用户当前产品最新的100条点评
	 * @return
	 */
	private List<CommonCmtCommentVO> getLastestUserProductCmts(Long userId, long productId) {
		Map<String, Object> lastestCmtParams = new HashMap<String, Object>();
		lastestCmtParams.put("userId", userId);
		lastestCmtParams.put("productId", productId);
		lastestCmtParams.put("_startRow", 0);
		lastestCmtParams.put("_endRow", 100);
		lastestCmtParams.put("createTime321", "createTime321");
		lastestCmtParams.put("isHide", "displayall");
		return cmtCommentService.getCmtCommentList(lastestCmtParams);
	}
}
