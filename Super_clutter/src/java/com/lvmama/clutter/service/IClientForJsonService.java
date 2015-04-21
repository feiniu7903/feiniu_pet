package com.lvmama.clutter.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lvmama.clutter.xml.lv.po.RequestObject;
import com.lvmama.clutter.xml.lv.po.ResponseObject;
import com.lvmama.comm.pet.po.client.ClientCmtLatitude;
import com.lvmama.comm.pet.po.client.ClientPlace;
/**
 * 客户端2.2期接口
 * @author dengcheng
 *
 */
public interface IClientForJsonService {
	/**
	 * 查询我的优惠券
	 * @param ro
	 * @return
	 */
	 String  queryUserCouponInfo(RequestObject ro);
	/**
	 * 获取景区酒店列表
	 * @param reqXml
	 * @return String
	 */
	String queryPlaceList(RequestObject ro);
	
	/**
	 * gps获取数据
	 */
	String queryGps(RequestObject ro);
	
	
	/**
	 * 目的地详细信息
	 */
	ResponseObject queryDestDetails(RequestObject ro);
	
	/**
	 * 自动定位返回城市信息
	 */
	String queryAutoLocationInfos(RequestObject ro);
	
	/**
	 * 获得门票列表
	 */
	String queryTicketsList(RequestObject ro);

	/**
	 * 登陆
	 */
	String clientLogin(RequestObject ro,HttpServletRequest request);
	/**
	 * 获取验证码
	 */
	String clientGetVlidateCode(RequestObject ro);
	/**
	 * 提交注册
	 */
	String clientSubRegister(RequestObject ro);
	
	
	/**
	 * 获取主产品的时间价格表
	 */
	String getMainProdTimePrice(RequestObject ro);
	/**
	 * 获得产品详细页面的必要数据
	 * @param ro
	 * @return
	 */
	String queryProductDetails(RequestObject ro);
	/**
	 * 通过产品id和选择的日期查询当天的日期时间价格信息
	 * @param ro
	 * @return
	 */
	 ResponseObject queryTimePriceByProductIdAndDate(RequestObject ro);
	/**
	 * 提交订单接口
	 * @param ro
	 * @return
	 */
	String commitOrder(RequestObject ro);
	/**
	 * 查询用户订单列表
	 * @param ro
	 * @return
	 */
	String queryUserOrderList(RequestObject ro);
	/**
	 * 用户意见反馈
	 * @param ro
	 * @return
	 */
	String commitSuggest(RequestObject ro);
	/**
	 * 查询团购信息列表
	 * @param ro
	 * @return
	 */
	String queryGouponOnList(RequestObject ro);
	
	/**
	 * 重发凭证
	 * 
	 * @param reqXml
	 * @return
	 * */
	String reSendSms(RequestObject ro);

	/**
	 * 意见反馈
	 * 
	 * @param reqXml
	 * @return
	 * */
	String addfeedBack(RequestObject ro);
	/**
	 * 统计place是否有产品
	 * @param ro
	 * @return
	 */
	String countPlaceInfos(RequestObject ro);
	
	/***
	 * 获取标的详细
	 * @param ro
	 * @return
	 */
	ClientPlace getPlaceDetails(RequestObject ro);
	/**
	 * 查询产品基本信息
	 */
	String querySimpleProductDetails(RequestObject ro);
	/**
	 * 根据景点id查询酒店类别
	 */
	String queryHotelBranches(RequestObject ro);
	/**
	 * 获得景点酒店点评
	 * @return
	 */
	String queryPlaceComments(String userId,String placeId,Long page);
	/**
	 * 根据标的主题或者产品类型查询点评纬度信息
	 * @param placeId
	 * @param productType
	 * @return
	 */
	String getCommentLatitudeInfos(String placeId,String orderId);
	/**
	 * 更加用户Id 查询点评
	 * @param userId
	 * @param page
	 * @return
	 */
	String queryCommentByUserId(String userId,Long page,String cmtType);
	/**
	 * 发起一个点评
	 * @param userId
	 * @param ccList
	 * @return
	 */
	String commitComments(String userId,List<ClientCmtLatitude> ccList,String objectId,String content,String cmtType);
	/**
	 * 查询一个用户待点评数据
	 * @param userId
	 * @param page
	 * @return
	 */
	String getOrderWaitingComments(String userId,Long page);
	/**
	 * 
	 * @param userId
	 * @param page
	 * @return
	 */
	String queryWaitForCommentNumber (String userId);
	/**
	 * 查询客户端标的详细 v2.4 添加
	 * @param placeId
	 * @return
	 */
	ClientPlace queryPlaceDetails(String placeId);
	/**
	 * 
	 * @param uid
	 * @param screenName
	 * @param shareChannel
	 * @param shareTarget
	 */
	void insertShareLog(String uid,String screenName,String shareChannel,String shareTarget);
	String getUserByUserId(String userId);
}
