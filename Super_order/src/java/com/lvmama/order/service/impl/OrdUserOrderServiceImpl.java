package com.lvmama.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdUserOrder;
import com.lvmama.comm.bee.service.ord.IOrdUserOrderService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrdUserOrderDAO;

public class OrdUserOrderServiceImpl implements IOrdUserOrderService {
	
	private OrdUserOrderDAO ordUserOrderDAO;

	@Override
	public int deleteOrdUserOrderByPrimaryKey(Long userOrderId) {
		int ret = 0;
		if (userOrderId != null) {
			ret = ordUserOrderDAO.deleteByPrimaryKey(userOrderId);
		}
		
		return ret;
	}

	@Override
	public int insertOrdUserOrder(OrdUserOrder record) {
		int ret = 0;
		if (record != null) {
			ret = ordUserOrderDAO.insertSelective(record);
		}
		
		return ret;
	}

	@Override
	public OrdUserOrder queryOrdUserOrderByPrimaryKey(Long userOrderId) {
		OrdUserOrder ret = null;
		if (userOrderId != null) {
			ret = ordUserOrderDAO.selectByPrimaryKey(userOrderId);
		}

		return ret;
	}

	@Override
	public int updateOrdUserOrderByPrimaryKey(OrdUserOrder record) {
		int ret = 0;
		if (record != null) {
			ret = ordUserOrderDAO.updateByPrimaryKeySelective(record);
		}
		
		return ret;
	}

	@Override
	public List<OrdUserOrder> queryOrdUserOrderListByParams(
			Map<String, Object> params) {
		return ordUserOrderDAO.selectListByParams(params);
	}
	
	@Override
	public Long getTotalCount(Map<String, Object> params) {
		return ordUserOrderDAO.queryTotalCount(params);
	}

	public OrdUserOrderDAO getOrdUserOrderDAO() {
		return ordUserOrderDAO;
	}

	public void setOrdUserOrderDAO(OrdUserOrderDAO ordUserOrderDAO) {
		this.ordUserOrderDAO = ordUserOrderDAO;
	}

	@Override
	public OrdUserOrder queryByUserOrderIdAndPaymentBizType(Long userOrderId,
			String paymentBizType) {
		OrdUserOrder userOrder = null;
		if (userOrderId != null && paymentBizType != null) {
			String bizType = null;
			if (Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name().equals(paymentBizType)
					|| Constant.PAYMENT_BIZ_TYPE.BEE_ORDER.name().equals(paymentBizType)) {
				bizType = OrdUserOrder.BIZ_TYPE.BIZ_BEE.name();
			} else if (Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name().equals(paymentBizType)) {
				bizType = OrdUserOrder.BIZ_TYPE.BIZ_VST.name();
			}
			
			if (bizType != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orderId", userOrderId);
				params.put("bizType", bizType);
				List<OrdUserOrder> userOrderList = ordUserOrderDAO.selectListByParams(params);
				if (userOrderList != null) {
					userOrder = userOrderList.get(0);
				}
			}
		}
		
		return userOrder;
	}
}
