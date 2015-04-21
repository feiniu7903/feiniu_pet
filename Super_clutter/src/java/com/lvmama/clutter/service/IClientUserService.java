package com.lvmama.clutter.service;

import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobileOrderCmt;
import com.lvmama.clutter.model.MobileReceiver;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.model.MobileUserCoupon;
import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.user.UserUser;
/**
 * api 接口 用户相关
 * @author dengcheng
 *
 */
public interface IClientUserService {

	/**
	 * 获得用户信息
	 * @param param
	 * @return
	 */
	MobileUser getUser(Map<String,String> param);
	
	/**
	 * 获得优惠券
	 * @param param
	 * @return
	 */
	List<MobileUserCoupon> getCoupon(Map<String, Object> param);
	/**
	 * 提交订单
	 * @param param
	 * @return
	 */
	String subOrder(Map<String,String> param);
	/**
	 * 添加到收藏
	 * @param param
	 * @return
	 */
	String addFavorite(Map<String,Object> param); 
	/**
	 * 取消收藏
	 * @param param
	 * @return
	 */
	boolean cancelFavorite(Map<String,Object> param); 
	/**
	 * 查询收藏列表
	 * @param param
	 * @return
	 */
	Map<String, Object> getFavoriteList(Map<String,Object> param);
	
	/**
	 * 判断攻略是否收藏. 
	 * @param params {objectId,objectType,userId}
	 * @return boolean 
	 */
	boolean isStrategyCollected(Map<String,Object> params);
	
	/**
	 * 获得订单
	 * @param param
	 * @return
	 */
	MobileOrder getOrder(Map<String,Object> param);
	/**
	 * 获得订单列表
	 * @param param
	 * @return
	 */
	 Map<String,Object> getOrderList(Map<String,Object> param);
	
	/**
	 * 添加联系人
	 * @param param
	 * @return
	 */
	String addContact(Map<String,String> param);
	
	/**
	 * 批量添加联系人
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	String addContacts(Map<String,String> param) throws Exception;
	/**
	 * 删除一个联系人
	 * @param param
	 * @return
	 */
	String removeContact(Map<String,String> param);

	/**
	 * 获得联系人
	 * @return
	 */
	List<MobileReceiver> getContact(Map<String,Object> param);
	
	/**
	 * 获取待点评的订单. 
	 * @param param  
	 * @return  map 
	 */
	List<MobileOrderCmt> queryCommentWaitForOrder(Map<String,Object> param);
	
	/**
	 * 获取待点评订单.V3.1
	 * @param param
	 * @return
	 */
	Map<String,Object> queryCmtWaitForOrder(Map<String, Object> param);
	
	/**
	 * 获取已点评的订单. 
	 * @param param  
	 * @return  map 
	 */
	Map<String, Object> queryCommentForOrder(Map<String,Object> param);
	
	/**
	 * 待点评订单的点评纬度. 
	 * @param param  
	 * @return  map 
	 * @throws Exception 
	 */
	List<DicCommentLatitude> getCommentLatitudeInfos(Map<String,Object> param) throws Exception;
	
	/**
	 * 提交点评. 
	 * @param param  
	 * @return  map 
	 * @throws Exception 
	 */
	String commitComment(Map<String,Object> param) throws Exception;
	
	// v3.1新加 
	/**
	 * 奖金 统计信息.
	 * @param params
	 * @return map
	 * @throws Exception
	 */
	Map<String,Object> getBonusInfo(Map<String,Object> params) throws Exception; 
	
	/**
	 * 奖金 - 收入
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBonusIncome(Map<String,Object> params) throws Exception; 
	
	/**
	 * 奖金 - 支出
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBonusPayment(Map<String,Object> params) throws Exception; 
	
	/**
	 * 奖金 - 退款
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getBonusRefund(Map<String,Object> params) throws Exception; 
	
	/**
	 * 重发短信凭证 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> reSendSmsCert(Map<String,Object> params) throws Exception; 
	
	/**
	 * 奖金支付 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> bonusPay(Map<String,Object> params) throws Exception;
	
	/**
	 * 优惠券和账号绑定 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> bindingCouponToUser(Map<String,Object> params) throws Exception;

	Map<String, Object> commitOrderComment(Map<String, Object> param)
			throws Exception;
	
	/**
	 * 根据微信openid获取驴妈妈用户
	 * @param value
	 * @return
	 */
	UserUser getUsersByMobOrNameOrEmailOrCard(String value);
	
	/**
	 * 获取用户现金账号信息. 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getMoneyInfo(Map<String,Object> params) throws Exception;

	Map<String, Object> getAdvanceOrder(Map<String, Object> params);

	Map<String, Object> getBindingInfo(Map<String, Object> params);

	Map<String, Object> getPaymentTarget(Map<String, Object> params);

	Map<String, Object> commitPayment(Map<String, Object> params);

}
