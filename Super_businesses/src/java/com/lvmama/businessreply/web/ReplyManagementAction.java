/**
 * 
 */
package com.lvmama.businessreply.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.businessreply.utils.ZkMessage;
import com.lvmama.businessreply.utils.ZkMsgCallBack;
import com.lvmama.businessreply.vo.BusinessConstant;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.comment.CmtBusinessUser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;
import com.opensymphony.oscache.util.StringUtil;

/**
 * @author liuyi
 *
 */
public class ReplyManagementAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ReplyManagementAction.class);
	
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	
	private CmtCommentService cmtCommentService =  (CmtCommentService) SpringBeanProxy.getBean("cmtCommentService");
	
	private List<CommonCmtCommentVO> commentList;
	
	private Long totalRowCount;
	
	private PlaceService placeService;
	
	private ProdProductService prodProductService;
	
	/**
	 * 查询
	 */
	public void doQuery() {
		
		CmtBusinessUser cmtBusinessUser = (CmtBusinessUser)(super.session.getAttribute(BusinessConstant.SESSION_USER));
		if(cmtBusinessUser != null)
		{
			String userType = cmtBusinessUser.getUserType();
			if(userType.equals(BusinessConstant.USER_TYPE_LVMAMA))
			{
				getQueryOption().put("cmtType", Constant.EXPERIENCE_COMMENT_TYPE);//体验点评
			}
			else if(userType.equals(BusinessConstant.USER_TYPE_MERCHANT))
			{
				getQueryOption().put("cmtType", Constant.COMMON_COMMENT_TYPE);//普通点评
				
				List<Place> placeList = cmtBusinessUser.getPlaceList();
				if(placeList != null && placeList.size() > 0)
				{
					List<Long> placeIDLongList = new ArrayList<Long>();
					for(int i = 0; i < placeList.size(); i++)
					{
						placeIDLongList.add(placeList.get(i).getPlaceId());
					}
					
					getQueryOption().put("placeIds", placeIDLongList);//商店点评，查询其关联标的的评论
				}
				
			}
		}
		
		String orderRule = (String)(getQueryOption().get("orderRule"));
		if("createTime321".equals(orderRule))
		{
			getQueryOption().put("createTime321", "true");
		}
		else if("createTime123".equals(orderRule))
		{
			getQueryOption().put("createTime123", "true");
		}
		else if("replyCount321".equals(orderRule))
		{
			getQueryOption().put("replyCount321", "true");
		}
		else if("replyCount123".equals(orderRule))
		{
			getQueryOption().put("replyCount123", "true");
		}
		else if(null == (orderRule))
		{
			getQueryOption().put("createTime321", "true");
		}
		
		
		String isBest = (String)(getQueryOption().get("isBest"));
		if("".equals(isBest))
		{
			getQueryOption().remove("isBest");
		}
	
		String replyStatus = (String)(getQueryOption().get("replyStatus"));
		if("all".equals(replyStatus))
		{
			getQueryOption().remove("hasLvmamaReply");
			getQueryOption().remove("hasMerchantReply");
			getQueryOption().remove("needManageAttention");
		}
		else if("hasReply".equals(replyStatus))
		{
			getQueryOption().remove("needManageAttention");
			
			String userType = cmtBusinessUser.getUserType();
			if (!StringUtil.isEmpty(userType))
			{
				if(userType.equals(BusinessConstant.USER_TYPE_MERCHANT))
				{
					getQueryOption().put("hasMerchantReply", "true");
				}
				else
				{
					getQueryOption().put("hasLvmamaReply", "true");
				}
			}
		}
		else if("needAttention".equals(replyStatus))
		{
			getQueryOption().remove("hasLvmamaReply");
			getQueryOption().remove("hasMerchantReply");
			getQueryOption().put("needManageAttention", "Y");
		}
		else if("noNeedAttention".equals(replyStatus))
		{
			getQueryOption().remove("hasLvmamaReply");
			getQueryOption().remove("hasMerchantReply");
			getQueryOption().put("needManageAttention", "N");
		}
		getQueryOption().put("isHide", "displayall");
	 	totalRowCount= Long.valueOf((Long)(cmtCommentService.getCommentTotalCount(queryOption)));
		_totalRowCountLabel.setValue(totalRowCount.toString()); 
		_paging.setTotalSize(totalRowCount.intValue());
		queryOption.put("_startRow", _paging.getActivePage()*_paging.getPageSize()+1);
		queryOption.put("_endRow", _paging.getActivePage()*_paging.getPageSize()+_paging.getPageSize());
		
		commentList = cmtCommentService.getCmtCommentList(queryOption);
		
		for (int i = 0; i < commentList.size(); i++) {
			CommonCmtCommentVO commonCmtCommentVO = commentList.get(i);
			if (commonCmtCommentVO.getCmtType().equals(Constant.EXPERIENCE_COMMENT_TYPE)) {
				ProdProduct product = prodProductService.getProdProductById(commonCmtCommentVO.getProductId());
				if (product != null) {
					ProductCmtCommentVO productCmtCommentVO = CommentUtil.composeProductComment(commonCmtCommentVO, product, null);
					commentList.set(i, productCmtCommentVO);
				}
			} else{// Constant.EXPERIENCE_COMMON_TYPE
				Place place = placeService.queryPlaceByPlaceId(commonCmtCommentVO.getPlaceId());
				PlaceCmtCommentVO placeCmtCommentVO = CommentUtil.composePlaceComment(commonCmtCommentVO, place);
				if (placeCmtCommentVO != null) {
					commentList.set(i, placeCmtCommentVO);
				}
			}
		}
	}
	
	
	
	/**
	 * 根据查询类型改变参数列表值
	 * @param type 查询类型
	 * @param value 参数值
	 */
	public void changeValue(final String type, final String value) {
            if ("orderRule".equals(type)) { /* 点评排序  */
            	getQueryOption().remove("createTime321");
            	getQueryOption().remove("createTime123");
            	getQueryOption().remove("replyCount321");
            	getQueryOption().remove("replyCount123");
			
			  if (!StringUtil.isEmpty(value)) {
				getQueryOption().put(type, value);
			  }
			 return;
		   }
          else if ("isBest".equals(type)) { /* 精华状态  */
          	getQueryOption().remove("isBest");
		
		  if (!StringUtil.isEmpty(value)) {
			getQueryOption().put(type, value);
		  }
		   return;
	    } 
          else if ("replyStatus".equals(type)) { /* 点评回复状态  */
          	    getQueryOption().remove("replyStatus");
  		
  		  if (!StringUtil.isEmpty(value)) {
  			getQueryOption().put(type, value);
  		  }
  		   return;
  	    } 
	}
	
	
	
	
	
	
	/**
	 * 关注评论
	 * @param deviceId
	 */
	public void enableAttentionReply (final long commentId){
		ZkMessage.showQuestion("您确认关注这条评论吗", new ZkMsgCallBack() {
			public void execute() {
				CommonCmtCommentVO cmtComment = cmtCommentService.getCmtCommentByKey(commentId);
				if(cmtComment != null)
				{
					cmtComment.setNeedManageAttention("Y");
					cmtCommentService.update(cmtComment);
				}
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});
	}
	
	/**
	 * 取消关注评论
	 * @param deviceId
	 */
	public void disableAttentionReply (final long commentId){
		ZkMessage.showQuestion("您确认要取消关注这条评论吗", new ZkMsgCallBack() {
			public void execute() {
				CommonCmtCommentVO cmtComment = cmtCommentService.getCmtCommentByKey(commentId);
				if(cmtComment != null)
				{
					cmtComment.setNeedManageAttention("N");
					cmtCommentService.update(cmtComment);
				}
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {

			}
		});
	}
	
	
	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}
	public void setCommentList(List<CommonCmtCommentVO> commentList) {
		this.commentList = commentList;
	}

	public List<CommonCmtCommentVO> getCommentList() {
		return commentList;
	}


	public void setTotalRowCount(Long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}


	public Long getTotalRowCount() {
		return totalRowCount;
	}
	
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

}
