package com.lvmama.comm.abroad.service;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.abroad.vo.request.ReservationsOrderReq;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderHotelDetailRes;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderPersonDetailRes;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderRes;
import com.lvmama.comm.abroad.vo.response.ReservationsOrderRoomDetailRes;
/**
 * 已下单（已取消）订单信息查询接口
 * @author ruanxiequan
 *
 */
public interface IReservationsOrder {
	/**
	 * 查询入住房间信息
	 * @param id
	 * @return
	 */
	public List<ReservationsOrderRoomDetailRes> queryForRoomDetailByAbroadhotelId(long id);
	/**
	 * 按条件查询订单信息
	 * @param getReservationMadeReq
	 * @return
	 */
	public ReservationsOrderRes queryForOrderList(ReservationsOrderReq reservationsOrderReq,String sessionId);
	
	/**
	 * 按条件查询订单信息
	 * @param reservationsOrderReq
	 * @param sessionId
	 * @param startResult
	 * @param maxResults
	 * @param orderByMethodString
	 * @return
	 */
	public ReservationsOrderRes queryForOrderList(ReservationsOrderReq reservationsOrderReq,String sessionId,int startResult, int maxResults, String orderByMethodString);

	/**
	 * 查询订单酒店详细信息
	 * @param keyId
	 * @return
	 */
	public ReservationsOrderHotelDetailRes queryForHotelDetailByKey(long keyId);
	/**
	 * 查询订单酒店详细信息
	 * @param orderId
	 * @return
	 */
	public List<ReservationsOrderHotelDetailRes> queryForHotelDetailByOrderId(long orderId);
	/**
	 * 查询入住人（联系人）详细信息
	 * @param id
	 * @return
	 */
	public List<ReservationsOrderPersonDetailRes> queryForPersonDetailByAbroadhotelId(long id);
	/**
	 * 查询入住人（联系人）详细信息
	 * @param keyId
	 * @return
	 */
	public ReservationsOrderPersonDetailRes queryForPersonDetailByKeyId(long keyId);
	/**
	 * 查询入住房间信息
	 * @param keyId
	 * @return
	 */
	public ReservationsOrderRoomDetailRes queryForRoomDetailByKeyId(long keyId);
	/**
	 * 组合查询总条数
	 * @param reservationsOrderReq
	 * @param sessionId
	 * @return
	 */
	public int selectCountInComplex(ReservationsOrderReq reservationsOrderReq,String sessionId);
	
	/**
	 * 驴妈妈后台最晚取消时间
	 * @param keyId
	 * @return
	 */
	public Date getLastCancelTimeForBack(long keyId);
	
	/**
	 * 获取凭证xml
	 * @param orderNo 订单号
	 * @return
	 */
	public String getVoucherXml(String orderNo);
	
	/**
	 * 获取凭证pdf(iText5)
	 * @param orderNo
	 * @return
	 */
	public byte[] getVoucherPdf(String orderNo);
	
	/**
	 * 对取消失败的订单重置订单状态
	 * @param orderNo
	 * @param orderStatus
	 * @param paymentStatus
	 * @param approveStatus
	 * @param operator
	 * @param reason
	 * @return
	 */
	public boolean resetOrderStatus(String orderNo,String orderStatus,String paymentStatus,String approveStatus,String operator,String reason);
}
