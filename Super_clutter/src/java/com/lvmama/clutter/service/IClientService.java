package com.lvmama.clutter.service;

import com.lvmama.clutter.xml.lv.po.RequestObject;
import com.lvmama.comm.pet.po.client.ViewPlace;


/**
 * lvmama客户端二期接口
 * @author dengcheng
 *
 */
public interface IClientService {
	/**
	 * 城市切换数据来源
	 * @param reqXml
	 * @return String
	 */
	String queryDestList(RequestObject ro);
	
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
	String queryDestDetails(RequestObject ro);
	
	/**
	 * 自动定位返回城市信息
	 */
	String queryAutoLocationInfos(RequestObject ro);
	
	/**
	 * 获得门票列表
	 */
	String queryTicketsList(RequestObject ro);
	
	/**
	 * 获得酒店所有产品列表String reqXml;
	 */
	String querHotelProductList(RequestObject ro);
	/**
	 * 获得目的地自由行
	 */
	String queryDestRouteList(RequestObject ro);
	
	/**
	 * 登陆
	 */
	String clientLogin(RequestObject ro);
	/**
	 * 获取验证码
	 */
	String clientGetVlidateCode(RequestObject ro);
	/**
	 * 提交注册
	 */
	String clientSubRegister(RequestObject ro);
	
	/**
	 * 填写订单接口 获取主产品的附加产品
	 */
	String getRelateProduct(RequestObject ro);
	
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
	String queryTimePriceByProductIdAndDate(RequestObject ro);
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
	 * 帮助
	 * 
	 * @param reqXml
	 * @return
	 * */
	String queryAboutOrHelp(RequestObject ro);
	/**
	 * 重发凭证
	 * 
	 * @param reqXml
	 * @return
	 * */
	String reSendSms(RequestObject ro);
	/**
	 * 查看凭证信息
	 * @param reqXml
	 * @return
	 * @author dengcheng
	 */
	String getPassCode(RequestObject ro);
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
	String getPlaceDetails(RequestObject ro);
	/**
	 * 通过城市名称查询城市ID返回json
	 * @param ro
	 * @return
	 */
	String getNameByLocation(RequestObject ro);
	/**
	 * 通过城市id查询
	 * @param placeId
	 * @return
	 */
	ViewPlace getDestInfoById(String placeId);
}
