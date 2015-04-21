package com.lvmama.comm.pet.service.conn;

import java.util.List;

import com.lvmama.comm.pet.po.ord.OrdOrderHead;

/**
 * 订单二次跟踪处理,取特定取消的订单.
 * 原来名称是IOrderCancleService
 * @author liwenzhan
 *
 */
public interface OrdConnTrackService {

	/**
	 * 订单二次跟踪处理,取特定取消的订单.
	 *  <pre>
	 * 因资源审核未通过而取消的，可立即进行二次跟踪处理.
	 * 超时未支付系统自动取消的，取消半个小时后可进行二次跟踪处理.
	 * 客户线上自己取消的，取消半小时后可进行二次跟踪处理.
	 * </pre>
	 * @return
	 */
	List<OrdOrderHead> queryOrderNotTrack(Long RowNum);
	
	/**
	 * 生成二次跟踪处理记录.
	 * @param number
	 * @param userName
	 */
	 void saveOrdertrack(final Long number,final String userName);

}