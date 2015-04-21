/**
 * 
 */
package com.lvmama.comm.pet.service.comment;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.comment.CmtPictureVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * @author liuyi
 *
 */
public interface CmtCommentService {
	
	
	 /** 查询点评
	 * @param parameters 查询参数
	 * @return 点评列表
	 */
	List<CommonCmtCommentVO> getCmtCommentList(final Map<String, Object> parameters);
	
	/**
	 * 根据点评主键获取点评对象
	 * @param commentId 点评主键
	 * @return 点评对象
	 */
	CommonCmtCommentVO getCmtCommentByKey(final Long commentId);
	
	/**
	 * 填充点评列的审核通过图片
	 * @param comments 点评列表
	 */
	void fillApprovedPicture(List<CommonCmtCommentVO> comments);
	
	/**
	 * 填充该点评的审核通过图片
	 * @param comments 点评
	 */
	CommonCmtCommentVO fillApprovedPicture(CommonCmtCommentVO comment);
	
	/**
	 * 获取点评总数
	 * @param parameters 查询参数
	 * @return 点评总数
	 */
	Long getCommentTotalCount(Map<String, Object> parameters);

	/**
	 * 修改CmtComment属性
	 * @param comment 
	 * @return 
	 */
	int update(CommonCmtCommentVO comment);
	
	/**
	 * 指定点评添加有用记数
	 * @param commentId
	 */
	void addUsefulCount(Long commentId);
	
	/**
	 * 填充点评的回复
	 * @param comments 点评列表
	 * @param parms 获取点评列表的条件，如果参数为null，那么系统会填充所有待审核与审核通过的回复且按回复时间倒序排列
	 */
	void fillReply(final List<CommonCmtCommentVO> comments, Map<String, Object> parms);

	/**
	 * 填充点评的回复
	 * @param comments 点评列表
	 * @param parms 获取点评列表的条件，如果参数为null，那么系统会填充所有待审核与审核通过的回复且按回复时间倒序排列
	 */
	CommonCmtCommentVO fillReply(CommonCmtCommentVO comment, Map<String, Object> parms);
	 
	/**
	 * 添加回复数
	 * @param commentId
	 */
	void addReplyCount(Long commentId);
	
	/**
	 * 移除回复数
	 * @param commentId
	 */
	void removeReplyCount(Long commentId);
	
	
	/**
	 * 点评人数
	 * @return
	 */
	 Long getCommentUserCount(final Map<String,Object> parameters);
	
	
	/**
	 * 获取前台可见图片
	 * @param commentId
	 * @return 图片列表
	 */
	List<CmtPictureVO> queryApprovedPictureList(Long commentId);
	

	/**
	 * 插入景区/产品点评
	 * @param user
	 * @param commonCmtCommentVO
	 * @return
	 */
	Long insert(UserUser user, CommonCmtCommentVO commonCmtCommentVO);
	
	/**
	 * 批量插入点评
	 * @param commentVOs
	 */
	public void insertBatchComment(List<CommonCmtCommentVO> commentVOs);

//	
//	
//	/**
//	 * 返回满足条件的景点和点评数量
//	 * @param parameters 查询条件
//	 * @return 
//	 */
//	 List<PlaceCommentVO> queryHotCommentOfPlace(final Map<String, Object> parameters);
	 
	 /**
	  * 获取返现的点评,没时间限制(游玩后写的体验点评)
	  * @param parameters
	  * @return
	  */
	OrderAndComment selectCanRefundComment(Long orderId, Long userId, Date orderVisitTime);
	
	/**
	 * 	查询返现的点评,游玩完4月内的互动体验点评
	 * @param orderList
	 * @return
	 */
	List<OrderAndComment> getCanRefundObjectMeetCommentCondition(Map<String, Object> map);

	/**
	 * 将点评更新为体验点评
	 * @param orderId 订单ID
	 * @param commendId 点评标识
	 * @param cashRefund 返现金额
	 * @return 是否更新成功
	 */
	boolean updateExperienceComment(Long orderId, Long commentId, Long cashRefund);	
	/**
	 * 分页查询
	 * @param parameters
	 * @return
	 */
	Page<CommonCmtCommentVO> queryCmtCommentListForApp(Map<String, Object> parameters);
	
	/**
	 * 获取产品的最新点评(审核通过)
	 * @param productID
	 * @param rows
	 * @return
	 */
	List<CommonCmtCommentVO> getNewestCommentByProductID(final Long productID, final Integer rows);
	
	/**
	 * 获取景点的最新点评(审核通过)
	 * @param productID
	 * @param rows
	 * @return
	 */
	List<CommonCmtCommentVO> getNewestCommentByPlaceId(final Long placeId, final Integer rows);
	
	/**
	 * 组装cmtCommentList中的用户图像
	 * @param cmtCommentList
	 * @return
	 */
	List<CommonCmtCommentVO> composeUserImagOfComment(List<CommonCmtCommentVO> cmtCommentList);
	
	//当前订单能返现的点评,游玩完4月内的互动体验点评总数
	Long queryCountOfCommentAndOrderOnPeriod(final Map<String, Object> parameters);
	
	/**
	 * 根据内容审核条件返回点评信息
	 * @param param
	 * @return
	 */
	List<CommonCmtCommentVO> getCmtCommentByParam(final Map<String, Object> param);
	 
	/**
	 * 修改点评内容审核类型
	 * @param cmt
	 */
	void updateCmtCommentToReviewStatus(CommonCmtCommentVO cmt);
	
	/**
	 * 根据内容审核条件返回点评总数
	 * @param param
	 * @return
	 */
	Long getCountOfCmtParam(final Map<String, Object> param);
	
}
