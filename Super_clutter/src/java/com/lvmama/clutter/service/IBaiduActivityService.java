package com.lvmama.clutter.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobileBranchItem;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.user.UserCooperationUser;

public interface IBaiduActivityService {
	/**
	 * 获得产品类别信息
	 * @param placeId
	 * @return
	 */
	List<ProdBranchSearchInfo> getActivityBranches(Long placeId);
	/**
	 * 下单前取数据
	 * @param branchId
	 * @param visitTime
	 * @return
	 */
	ProdProductBranch getActivityBranch(Long branchId,Date visitTime);
	/**
	 * 提交订单
	 * @param branchItem
	 * @param visitDate
	 * @param userId
	 * @param receiverName
	 * @param receiverMobile
	 * @return
	 */
	public Long commitActivityOrder(MobileBranchItem branchItem, Date visitDate,
			String userId, String receiverName,String receiverMobile, String idCard,String couponCode);
	
	
	/**
	 * 百度查询CooperationUser
	 * @param token
	 * @return
	 */
	public UserCooperationUser  getCooperationUserByToken(String token,String lvsessionid);
	
	
	 /**
	  * 点击预订按钮是否可以进入详情页面 
	  * @param productid
	  * @return map
	  */
	 Map<String,Object> baiduBooking(Long productid,String userNo);
	 
	 /**
	  * 点击提交按钮之前，判断是否可以提交该订单。 
	  * @param productid
	  * @param inOrOut 框内 1； 框外0 
	  * @return map  
	  */
	 Map<String, Object> baiduBeforSubmit(Long placeid,Long productid, String userNo,String inOrOut);
	 
	 /**
	  * 点击提交按钮之后，判断是否提交成功，否则回滚。 
	  * @param productid
	  * @return map  
	  */
	 Map<String, Object> baiduAfterSubmit(Long productid, String userNo,String inOrOut);
	 /**
	  * 通过百度ID登陆
	  * @param uid
	  * @param lvsessionid
	  * @return
	  */
	boolean loginWithBaiduUid(String uid,String lvsessionid);
	
	 
	 /**
	  * 只是用来初始化数据 - 
	  * @param params
	  * @return map
	  */
	 Map<String, Object> only4JunitTest(Map<String, Object> params);
	 
	 
	 /**
	  * 返回产品销售状态
	  * @param params
	  * @return map
	  */
	 String getProductStatus(Long productId);
	 
	 /**
	  * 生成百度xml
	  * @param params
	  * @return
	  */
	 Map<String,Object> generatorBaiduXml(Map<String,Object> params);
	 
	 /**
	  * 立减票
	  * 
	  * 框内：判断框内立减票是否可定，是一个总开关，在php中设置，默认是可以预定；否则提示去下载app
	  * 
	  * 框外：判断是否达到今天预定数量，如果达到提示去下载app ；否则可以下载； 
	  *  
	  * @param productId 产品id
	  * @param type 1 ：框内； 0：框外
	  * @return
	  */
	 String booking4HalfAndSandbyTicket(Long productId,String type);

	 
}
