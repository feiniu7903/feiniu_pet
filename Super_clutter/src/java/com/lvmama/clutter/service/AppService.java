package com.lvmama.clutter.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.vo.view.MarkCouponUserInfo;
import com.lvmama.comm.pet.po.client.ClientCmtLatitude;
import com.lvmama.comm.pet.po.client.ClientCmtPlace;
import com.lvmama.comm.pet.po.client.ClientCommitCmtResult;
import com.lvmama.comm.pet.po.client.ClientGroupon2;
import com.lvmama.comm.pet.po.client.ClientOrderCmt;
import com.lvmama.comm.pet.po.client.ClientPlace;
import com.lvmama.comm.pet.po.client.ClientProduct;
import com.lvmama.comm.pet.po.client.ClientTimePrice;
import com.lvmama.comm.pet.po.client.ClientUserCouponInfo;
import com.lvmama.comm.pet.po.client.ViewClientOrder;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.vo.comment.DicCommentLatitudeVO;

public interface AppService {
	/***
	 * 获取标的详细包括城市
	 * @param ro
	 * @return
	 */
	ClientPlace getPlaceDetails(Long placeId);
	/**
	 * 根据id 获得产品相关的一些信息
	 * @param productId
	 * @return
	 */
	List<?> getProductList(Long placeId,String stage);
	/**
	 * 查询产品相关信息
	 * @param productId
	 * @return
	 */
	ClientProduct getProductDetails(Long productId);
	/**
	 * 获得产品或者分类的时间价格表
	 * @param productId
	 * @param branchId
	 * @return
	 */
	List<ClientTimePrice> getMainProdTimePrice(Long productId,Long branchId);
	
	/**
	 * 处理酒店单房类型的产品和相关产品
	 * @param prod
	 * @param branchId
	 * @param visitDate
	 * @return
	 */
	
	List<ClientProduct> querySingleRoomProductBranchByBrachId(ProdProduct prod,Long branchId,Date visitDate);
	
	/**
	 * 通过选择的主产品查询相关关联产品和附加产品
	 */
	List<ClientProduct> queryTimePriceByProductIdAndDate(ProdProduct prod,Long branchId,Date visitDate,String udid);
	 /**
	  * 查询用户优惠信息
	  * @param userId
	  * @return
	  */
	 List<ClientUserCouponInfo>  queryUserCouponInfo(UserUser user,String state);
	 
	 String commitOrder();
	 Map<String,Object> queryCommentByUserId(String userId,Long page,String cmtType);
	 ClientCommitCmtResult commitComments(String userId,List<ClientCmtLatitude> ccList,String objectId,String content,String cmtType,String firstChannel);
	 List<ClientOrderCmt>  getOrderWaitingComments(String userId,Long page);
	 ClientPlace getPlaceByName(String keyword);
	 Map<String,Object> queryPlaceComments(String userId,Long placeId,Long page);
	 void addfeedBack(String content,String email,String userId,String firstChannel);
	 List<DicCommentLatitudeVO> getCommentLatitudeInfos(Long placeId,String orderId);
	 /**
	  * 你懂的
	  * @param orderId
	  * @param userId
	  * @param page
	  * @return
	  */
	 Map<String,Object> getOrderListByOrderId(Long orderId,String userId,Long page);
	 /**
	  * 你懂的
	  * @param orderId
	  * @param userId
	  * @return
	  */
	 ViewClientOrder getOrderByOrderId(Long orderId,String userId);
	 /**
	  * 查询团购产品
	  * @return
	  */
	 List<ClientGroupon2> queryGrouponList();
	/**
	 * 分享日志
	 * @param uid
	 * @param screenName
	 * @param shareChannel
	 * @param shareTarget
	 */
	 void insertShareLog(String uid,String screenName,String shareChannel,String shareTarget);
	 /**
	  * 根据产品类型组装产品类别 并分组
	  * @param placeId
	  * @param stage
	  * @return
	  */
	 Map<String,Object> queryGroupProductList(Long placeId,String stage); 
	 
	 /**
	  * 查询上海的团购产品
	  * @return
	  */
	 List<ClientProduct> queryGroupOnListForSh();
	 /**
	  * 查询今日可预订的类别产品
	  * @param productId
	  * @param branchId
	  * @param visitDate
	  * @return
	  */
	List<ClientProduct> queryTimePriceByProductIdAndDateForTodayPhoneOrder(Long productId,Long branchId,Date visitDate);
	/**
	 * 
	 * @param param
	 */
	Map<String, Object> fastLogin(Map<String, Object> param);
	 
}
