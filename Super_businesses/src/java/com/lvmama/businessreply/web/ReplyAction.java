/**
 * 
 */
package com.lvmama.businessreply.web;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;

import com.lvmama.businessreply.vo.BusinessConstant;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.comment.CmtBusinessUser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtReplyService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtReplyVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;


/**
 * @author liuyi
 *
 */
public class ReplyAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ReplyAction.class);
	private CommonCmtCommentVO cmtCommentVO;
	private CmtReplyVO cmtReply = new CmtReplyVO();
	private CmtReplyService cmtReplyService = (CmtReplyService)SpringBeanProxy.getBean("cmtReplyService");;
	private CmtCommentService cmtCommentService =  (CmtCommentService) SpringBeanProxy.getBean("cmtCommentService");
	private UserUserProxy userUserProxy = (UserUserProxy) SpringBeanProxy.getBean("userUserProxy");
	private PlaceService placeService =(PlaceService) SpringBeanProxy.getBean("placeService");;
	private ProdProductService prodProductService =(ProdProductService) SpringBeanProxy.getBean("prodProductService");;
	
	
	protected void doBefore() throws Exception {
		Map arg = Executions.getCurrent().getArg();
		if (arg.get("provider") != null) {
			cmtCommentVO = (CommonCmtCommentVO) arg.get("provider");
		}
	}
	
	/**
	 * 保存回复
	 */
	public void doSaveReply() {
		
		CmtBusinessUser cmtBusinessUser = (CmtBusinessUser)(super.session.getAttribute(BusinessConstant.SESSION_USER));
		if(cmtBusinessUser != null)
		{
			long commentId = cmtCommentVO.getCommentId();
			cmtReply.setCommentId(commentId);
			cmtReply.setCreateTime(new Date());
			String userType = cmtBusinessUser.getUserType();
			String replyType = "";
			if(userType.equals(BusinessConstant.USER_TYPE_LVMAMA))
			{
				replyType = Constant.CMT_REPLY_TYPE.LVMAMA.toString();
			}
			else if(userType.equals(BusinessConstant.USER_TYPE_MERCHANT))
			{
				replyType = Constant.CMT_REPLY_TYPE.MERCHANT.toString();
			}
			cmtReply.setReplyType(replyType);
			//cmtReply.setUserId(cmtBusinessUser.getUserID());
			cmtReply.setUserId(1l);
			cmtReply.setUserName(cmtBusinessUser.getUserName());
			cmtReply.setIsAudit(Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name()); //商家回复永远默认是审核通过
			long replyId=cmtReplyService.insert(cmtReply);
			CommonCmtCommentVO comment = cmtCommentService.getCmtCommentByKey(commentId);
			
			if(userType.equals(BusinessConstant.USER_TYPE_LVMAMA))
			{   comment.setLvmamaReplyCount(comment.getLvmamaReplyCount()+1);
				cmtCommentService.update(comment);
				//站内信
				UserUser user=userUserProxy.getUserUserByPk(comment.getUserId());
				String subjectName="";
				if(comment.getProductId()!=null){
					ProdProduct product = prodProductService.getProdProductById(comment.getProductId());
					subjectName=product.getProductName();
				}else{
					Place place = placeService.queryPlaceByPlaceId(comment.getPlaceId());
					subjectName=place.getName();
				}
				String subject ="您发表的点评有新的官方回复";
				String message ="亲爱的 "+user.getUserName()+"：<br/>"+
								"<p>您发表的< "+subjectName+" >点评，收到新的官方回复，详情请点击：" +
								"<a target='_blank' href='http://www.lvmama.com/comment/"+comment.getCommentId()+"'>http://www.lvmama.com/comment/"+comment.getCommentId()+"</a></p><br/>";
				String type ="COMMENT";
				CommentUtil.synchLetter(subject,message,type,user.getUserNo());
			}
			else if(userType.equals(BusinessConstant.USER_TYPE_MERCHANT))
			{
				comment.setMerchantReplyCount(comment.getMerchantReplyCount()+1);
				cmtCommentService.update(comment);
				
				//站内信
				UserUser user=userUserProxy.getUserUserByPk(comment.getUserId());
				String subjectName="";
				if(comment.getProductId()!=null){
					ProdProduct product = prodProductService.getProdProductById(comment.getProductId());
					subjectName=product.getProductName();
				}else{
					Place place = placeService.queryPlaceByPlaceId(comment.getPlaceId());
					subjectName=place.getName();
				}
				String subject ="您发表的点评有新的商家回复";
				String message ="亲爱的 "+user.getUserName()+"：<br/>"+
								"<p>有商家对您发表的< "+subjectName+" >点评进行了回复，详情请点击：" +
								"<a target='_blank' href='http://www.lvmama.com/comment/"+comment.getCommentId()+"'>http://www.lvmama.com/comment/"+comment.getCommentId()+"</a></p><br/>";
				String type ="COMMENT";
				CommentUtil.synchLetter(subject,message,type,user.getUserNo());
			}
			cmtCommentService.addUsefulCount(commentId);
		}
		
		this.refreshParent("search");
		this.closeWindow();
	}
	
	public void setCmtCommentVO(CommonCmtCommentVO cmtCommentVO) {
		this.cmtCommentVO = cmtCommentVO;
	}

	public CommonCmtCommentVO getCmtCommentVO() {
		return cmtCommentVO;
	}

	public void setCmtReply(CmtReplyVO cmtReply) {
		this.cmtReply = cmtReply;
	}

	public CmtReplyVO getCmtReply() {
		return cmtReply;
	}
}
