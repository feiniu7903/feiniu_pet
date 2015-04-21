/**
 * 
 */
package com.lvmama.pet.comment.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;
import com.lvmama.comm.vo.comment.CmtPictureVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.pet.comment.dao.CmtCommentDAO;
import com.lvmama.pet.comment.dao.CmtLatitudeDAO;
import com.lvmama.pet.comment.dao.CmtPictureDAO;
import com.lvmama.pet.comment.dao.CmtReplyDAO;
import com.lvmama.pet.comment.dao.DicCommentLatitudeDAO;
import com.lvmama.pet.user.dao.UserUserDAO;


/**
 * @author liuyi
 *
 */
public class CmtCommentServiceImpl implements CmtCommentService {
	
	
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(CmtCommentServiceImpl.class);
	
	/**
	 * 点评的数据库操作接口
	 */
	private CmtCommentDAO cmtCommentDAO;
	
	/**
	 * 点评发布图片的数据库操作接口
	 */
	private CmtPictureDAO cmtPictureDAO;
	
	/**
	 * 点评回复的数据库操作接口
	 */
	private CmtReplyDAO cmtReplyDAO;
	
	/**
	 * 点评维度关系数据库操作接口
	 */
	private CmtLatitudeDAO cmtLatitudeDAO;
	
	/**
	 * 用户数据库操作接口
	 */
	private UserUserDAO userUserDAO;
	
	 /** 查询点评
	 * @param parameters 查询参数
	 * @return 点评列表
	 */
	@Override
	public List<CommonCmtCommentVO> getCmtCommentList(Map<String, Object> parameters) {
		if (null == parameters || parameters.isEmpty()) {
			LOG.warn("can't search all comment data");
			return null;
		}
		
		if (null == parameters.get("userId") 
				&& null == parameters.get("placeId") 
				&& null == parameters.get("placeIds")
				&& null == parameters.get("productId")
				&& null == parameters.get("productIds")
				&& null == parameters.get("commentId")
				&& null == parameters.get("lastest7day")
				&& null == parameters.get("audit7day")
				&& null == parameters.get("getCount")
				&& null == parameters.get("startDate")
				&& null == parameters.get("endDate")
				&& null == parameters.get("_startRow")
				&& null == parameters.get("_endRow")
				&& null == parameters.get("orderId")) {
			parameters.put("lastest7day",true);
			LOG.info("getCmtCommentList too little parameters");
		    for (String key : parameters.keySet()) {
		    	LOG.info(key + "\t" + parameters.get(key));
		    }
		}
		
		dealDefaultCommentParameters(parameters);
		debug("search cond:" + parameters);
		List<CommonCmtCommentVO> cmtCommentList = cmtCommentDAO.getCmtCommentList(parameters);
		
		//给commit添加点评图片信息
		List<CommonCmtCommentVO> cmtComments = new ArrayList<CommonCmtCommentVO>();
		for(CommonCmtCommentVO commonCmtCommentVO:cmtCommentList){
			Map<String, Object> picParameters = new HashMap<String, Object>();
			picParameters.put("commentId", commonCmtCommentVO.getCommentId());
			List<CmtPictureVO> cmtPictureList = cmtPictureDAO.query(picParameters);
			commonCmtCommentVO.setCmtPictureList(cmtPictureList);
			cmtComments.add(commonCmtCommentVO);
		}
		
		return cmtComments;
	}
	
	public Page<CommonCmtCommentVO> queryCmtCommentListForApp(Map<String, Object> parameters) {
		Long count = this.getCommentTotalCount(parameters);
		Page<CommonCmtCommentVO> pageConfig = new Page<CommonCmtCommentVO>(count, Long.valueOf(parameters.get("pageSize").toString()), 
				Long.valueOf(parameters.get("currentPage").toString()));
		parameters.put("_startRow",pageConfig.getStartRows());
		parameters.put("_endRow", pageConfig.getEndRows());
		
		dealDefaultCommentParameters(parameters);
		debug("search cond:" + parameters);
		List<CommonCmtCommentVO> cmtCommentList = cmtCommentDAO.getCmtCommentList(parameters);
		
		//pageConfig.setItems(cmtCommentList);
		
		//给commit添加点评图片信息
		List<CommonCmtCommentVO> cmtComments = new ArrayList<CommonCmtCommentVO>();
		for(CommonCmtCommentVO commonCmtCommentVO:cmtCommentList){
			Map<String, Object> picParameters = new HashMap<String, Object>();
			picParameters.put("commentId", commonCmtCommentVO.getCommentId());
			List<CmtPictureVO> cmtPictureList = cmtPictureDAO.query(picParameters);
			commonCmtCommentVO.setCmtPictureList(cmtPictureList);
			cmtComments.add(commonCmtCommentVO);
		}
		pageConfig.setItems(cmtComments);
		return pageConfig;
	}

	/**
	 * 根据点评主键获取点评对象
	 * @param commentId 点评主键
	 * @return 点评对象
	 */
	@Override
	public CommonCmtCommentVO getCmtCommentByKey(Long commentId) {
		return (CommonCmtCommentVO) cmtCommentDAO.getCmtCommentByKey(commentId);
	}

	/**
	 * 填充点评列的审核通过图片
	 * @param comments 点评列表
	 */
	@Override
	public void fillApprovedPicture(List<CommonCmtCommentVO> comments) {
		if (null == comments || comments.isEmpty()) {
			return;
		}
		for (CommonCmtCommentVO comment : comments) {
			fillApprovedPicture(comment);
		}
	}

	/**
	 * 填充该点评的审核通过图片
	 * @param comments 点评
	 */
	@Override
	public CommonCmtCommentVO fillApprovedPicture(CommonCmtCommentVO comment) {
		if (null == comment || null == comment.getCommentId()) {
			return null;
		}
		comment.setCmtPictureList(queryApprovedPictureList(comment.getCommentId()));
		return comment;
	}
	
	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.comment.CmtCommentService#queryApprovedPictureList(java.lang.Long)
	 */
	@Override
	public List<CmtPictureVO> queryApprovedPictureList(Long commentId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("commentId", commentId);
		parameters.put("isAudit", Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		return cmtPictureDAO.query(parameters);	
	}

	/**
	 * 获取点评总数
	 * @param parameters 查询参数
	 * @return 点评总数
	 */
	@Override
	public Long getCommentTotalCount(Map<String, Object> parameters) {
		dealDefaultCommentParameters(parameters);
		return cmtCommentDAO.getCommentTotalCount(parameters);
	}

	/**
	 * 修改CmtComment属性
	 * @param comment 
	 * @return 
	 */
	@Override
	public int update(CommonCmtCommentVO comment) {
		return cmtCommentDAO.update(comment);	
	}

	/**
	 * 指定点评添加有用记数
	 * @param commentId
	 */
	@Override
	public void addUsefulCount(Long commentId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("commentId", commentId);
		//增加虚假有用数
		parameters.put("randomUsefulCount", (getRandomUsefulCount() + 1));
		cmtCommentDAO.addUsefulCount(parameters);
	}

	/**
	 * 填充点评的回复
	 * @param comments 点评列表
	 * @param parms 获取点评列表的条件，如果参数为null，那么系统会填充所有待审核与审核通过的回复且按回复时间倒序排列
	 */
	@Override
	public void fillReply(List<CommonCmtCommentVO> comments,
			Map<String, Object> parms) {
		if (null == comments || comments.isEmpty()) {
			return;
		}
		for (CommonCmtCommentVO comment : comments) {
			fillReply(comment, parms);
		}
	}

	/**
	 * 填充点评的回复
	 * @param comments 点评列表
	 * @param parms 获取点评列表的条件，如果参数为null，那么系统会填充所有待审核与审核通过的回复且按回复时间倒序排列
	 */
	@Override
	public CommonCmtCommentVO fillReply(CommonCmtCommentVO comment, Map<String, Object> parm) {
		if (null == comment || null == comment.getCommentId()) {
			return null;
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("commentId", comment.getCommentId());
	    parameters.put("isHide","N");
		if (null != parm && !parm.isEmpty()) {
			parameters.putAll(parm);
			comment.setReplies(cmtReplyDAO.query(parameters));
		} else {
			parameters.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
			//设置用户回复
			parameters.put("replyType",
					Constant.CMT_REPLY_TYPE.CUSTOMER.name());
			comment.setReplies(cmtReplyDAO.query(parameters));
			//商家回复
			parameters.put("replyType",
					Constant.CMT_REPLY_TYPE.MERCHANT.name());
			comment.setMerchantReplies(cmtReplyDAO.query(parameters));
			//获取lvmama回复
			parameters.put("replyType",
					Constant.CMT_REPLY_TYPE.LVMAMA.name());
			comment.setLvmamaReplies(cmtReplyDAO.query(parameters));
		}
		
		return comment;
	}

	/**
	 * 添加回复数
	 * @param commentId
	 */
	@Override
	public void addReplyCount(Long commentId) {
		cmtCommentDAO.addReply(commentId);
	}

	/**
	 * 移除回复数
	 * @param commentId
	 */
	@Override
	public void removeReplyCount(Long commentId) {
		cmtCommentDAO.removeReply(commentId);
	}

	/**
	 * 点评人数
	 * @return
	 */
	@Override
	public Long getCommentUserCount(Map<String, Object> parameters) {
		dealDefaultCommentParameters(parameters);
		return cmtCommentDAO.getCommentUserCount(parameters);
	}
	
	
	/**
	 * 插入点评
	 */
	@Override
	public Long insert(final UserUser user,  final CommonCmtCommentVO commonCmtCommentVO) {
		if (null == commonCmtCommentVO) {
			return null;
		}
		insertComment(user, commonCmtCommentVO);
		return commonCmtCommentVO.getCommentId();
	}
	
	/**
	 * 批量插入点评
	 * @param commentVOs
	 */
	public void insertBatchComment(List<CommonCmtCommentVO> commentVOs){
		for (CommonCmtCommentVO commonCmtCommentVO : commentVOs) {
			insertComment(null, commonCmtCommentVO);
		}
	}
	
	
	
	/**
	 * 添加标识
	 * @param latitudes 标识
	 * @param pictures 图片
	 * @param comment 点评
	 * @param content 内容
	 * @param userId 用户Id
	 */
	private void insertComment(final UserUser user, final CommonCmtCommentVO commonCmtCommentVO)
	{
		if (null != user) {
			commonCmtCommentVO.setUserId(user.getId());
			commonCmtCommentVO.setUserName(user.getUserName());
		}
		commonCmtCommentVO.setShamUsefulCount(getRandomUsefulCount());
		cmtCommentDAO.insert(commonCmtCommentVO);
		List<CmtLatitudeVO> latitudes = commonCmtCommentVO.getCmtLatitudes();
		if (null != latitudes && !latitudes.isEmpty()) {
			for (CmtLatitudeVO latitude : latitudes) {
				latitude.setCommentId(commonCmtCommentVO.getCommentId());
				getCmtLatitudeDAO().insert(latitude);
			}
		}
		
		List<CmtPictureVO> pictures = commonCmtCommentVO.getCmtPictureList();
		if (null != pictures && !pictures.isEmpty()) {
			for (CmtPictureVO picture : pictures) {
				picture.setCommentId(commonCmtCommentVO.getCommentId());
				picture.setIsAudit(Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());//系统默认审核通过
				if (null != user) {
					picture.setUserId(user.getId());
					picture.setUserName(user.getUserName());
				}
				cmtPictureDAO.insert(picture);
			}
		}
	}
	
	
	/**
	 * 处理默认点评参数
	 * @param parameters
	 */
	private void dealDefaultCommentParameters(final Map<String, Object> parameters)
	{
		if(parameters != null)
		{
			if(parameters.get("isHide") == null)
			{
				//默认显示不隐藏的
				parameters.put("isHide", "N");
			}
			else if(parameters.get("isHide").equals("displayall"))
			{
				//显示所有的
				parameters.remove("isHide");
			}
		}
	}
	
	/**
	 * 产生随机数(1-5)
	 * */
	public long getRandomUsefulCount() {
		Random random = new Random();
		Long randomNumber = (long) random.nextInt(5);
		return (long) randomNumber + 1;
	}
	
	/**
	 * 	 获取同时满足订单和点评条件的记录,游玩完4月内的互动体验点评
	 * @param orderList(能返现订单)
	 * @return
	 */
	public List<OrderAndComment> getCanRefundObjectMeetCommentCondition(Map<String, Object> map) {

//		map.put("orderId", Long.valueOf(orderAndComment.getOrderId()));
//		map.put("userId", Long.valueOf(orderAndComment.getUserId()));
//		map.put("orderVisitTime", orderAndComment.getOrderVisitTime());
		List<OrderAndComment> cmtList = cmtCommentDAO.queryCommentAndOrderOnPeriod(map);

		if (cmtList == null) {
			return null;
		} else {
			return cmtList;
		}
	}
	 
		
	/**
	 * 获取返现的点评,没时间限制(游玩后写的体验点评,一笔订单默认只能有一条体验点评)
	 * 
	 * @param parameters
	 * @return
	 */
	public OrderAndComment selectCanRefundComment(Long orderId, Long userId, Date orderVisitTime) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("userId", userId);
		map.put("orderVisitTime", orderVisitTime);
		List<OrderAndComment> list = cmtCommentDAO.selectCanRefundComment(map);
		if (list == null || list.size() == 0)  return null;
		
		return list.get(0);
	}
	
	/**
	 * 将点评更新为体验点评
	 * @param orderId 订单ID
	 * @param commendId 点评标识
	 * @param cashRefund 返现金额
	 * @return 是否更新成功
	 */
	public boolean updateExperienceComment(Long orderId, Long commentId, Long cashRefund){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("orderId", orderId);
		parameters.put("commentId", commentId);
		parameters.put("cashRefund", cashRefund);
		int result = cmtCommentDAO.updateExperienceComment(parameters);
		if(result < 1) return Boolean.FALSE;
		
		return Boolean.TRUE;
	}
	
	/**
	 * 获取产品的最新点评(审核通过)
	 * @param productID
	 * @param rows
	 * @return
	 */
	public List<CommonCmtCommentVO> getNewestCommentByProductID(final Long productID, final Integer rows){
		if (null == productID) {
			LOG.error("未能获取产品标识，无法获取最新的产品点评");
			return null;
		}
		int i = 10;
		if (null != rows) {
			i = rows;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productID);
		parameters.put("isAudit", Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		parameters.put("_startRow", 0);
		parameters.put("_endRow", i);
		parameters.put("isHide", "N");
		parameters.put("createTime321", "Y");
		
		List<CommonCmtCommentVO> cmtCommentList = cmtCommentDAO.getCmtCommentList(parameters);
		return cmtCommentList;
	}
	
	@Override
	public List<CommonCmtCommentVO> getNewestCommentByPlaceId(final Long placeId, final Integer rows) {
		if (null == placeId) {
			LOG.error("未能获取到景点标识，无法获取最新的景点点评");
			return null;
		}
		int i = 10;
		if (null != rows) {
			i = rows;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("placeId", placeId);
		parameters.put("isAudit", Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		parameters.put("_startRow", 0);
		parameters.put("_endRow", i);
		parameters.put("isHide", "N");
		parameters.put("createTime321", "Y");
		
		List<CommonCmtCommentVO> cmtCommentList = cmtCommentDAO.getCmtCommentList(parameters);
		return cmtCommentList;
	}
	
	/**
	 * 打印调试信息
	 * @param message
	 */
	private final void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	//设置用户图像
	public List<CommonCmtCommentVO> composeUserImagOfComment(List<CommonCmtCommentVO> cmtCommentList){
//			if(cmtCommentList != null && cmtCommentList.size() > 0 && cmtCommentList.size() < 1000){
//				List<Long> userIdList = new ArrayList<Long>();
//				for(CommonCmtCommentVO vo : cmtCommentList){
//					userIdList.add(vo.getUserId());
//				}
//				List<UserUser> userUserList = userUserDAO.queryUserUserByUserId(userIdList);
//				
//				//设置用户图像给CmtCommentVO
//				if(cmtCommentList != null && cmtCommentList.size() > 0){
//				    for (int i = 0; i < cmtCommentList.size(); i++) {
//				    	for(UserUser userUser : userUserList){
//				    		CommonCmtCommentVO cmtVo = cmtCommentList.get(i);
//				    		if(cmtVo.getUserId() != null && (cmtVo.getUserId().longValue() == userUser.getId().longValue())){
//				    			cmtCommentList.get(i).setUserImg(userUser.getImageUrl());
//				    		}
//				    	}
//					}
//				}
//			}
			return cmtCommentList;
		}
		
	//当前订单能返现的点评,游玩完4月内的互动体验点评总数
	public Long queryCountOfCommentAndOrderOnPeriod(final Map<String, Object> parameters) {
		 
		return (Long) cmtCommentDAO.queryCountOfCommentAndOrderOnPeriod(parameters);
	}
	
	/**
	 * 根据条件查询点评信息 
	 * 针对 内容审核
	 * @author zhongshuangxi 
	 */
	@Override
	public List<CommonCmtCommentVO> getCmtCommentByParam(final Map<String, Object> param) {
	    return cmtCommentDAO.selectParamByComment(param);
	}
	
	/**
	 * 根据条件查询点评数量
	 * 针对 内容审核
	 * @author zhongshuangxi 
	 */
	@Override
	public Long getCountOfCmtParam(final Map<String, Object> param) {
	    return cmtCommentDAO.queryCountOfCommentReview(param);
	}
	
	/**
	 * 修改点评审核状态
	 * 针对 内容审核
	 * @author zhongshuangxi 
	 */
	@Override
	public void updateCmtCommentToReviewStatus(CommonCmtCommentVO cmt) {
	    cmtCommentDAO.updateReviewStatusByComment(cmt);
	}
	
	/**
	 *  ------------------------------------------  get and set property ---------------------------------------------------
	 */
	
	public CmtCommentDAO getCmtCommentDAO() {
		return cmtCommentDAO;
	}

	public void setCmtCommentDAO(CmtCommentDAO cmtCommentDAO) {
		this.cmtCommentDAO = cmtCommentDAO;
	}

	public CmtPictureDAO getCmtPictureDAO() {
		return cmtPictureDAO;
	}

	public void setCmtPictureDAO(CmtPictureDAO cmtPictureDAO) {
		this.cmtPictureDAO = cmtPictureDAO;
	}

	public CmtReplyDAO getCmtReplyDAO() {
		return cmtReplyDAO;
	}

	public void setCmtReplyDAO(CmtReplyDAO cmtReplyDAO) {
		this.cmtReplyDAO = cmtReplyDAO;
	}

	public CmtLatitudeDAO getCmtLatitudeDAO() {
		return cmtLatitudeDAO;
	}

	public void setCmtLatitudeDAO(CmtLatitudeDAO cmtLatitudeDAO) {
		this.cmtLatitudeDAO = cmtLatitudeDAO;
	}

	public void setUserUserDAO(UserUserDAO userUserDAO) {
		this.userUserDAO = userUserDAO;
	}

	
}
