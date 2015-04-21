package com.lvmama.sms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.com.ISmsHistoryService;
import com.lvmama.comm.pet.po.pub.ComSmsHistory;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sms.dao.ComSmsHistoryDAO;

public class SmsHistoryServiceImpl implements ISmsHistoryService {
	private ComSmsHistoryDAO comSmsHistoryDAO;
	
	public ComSmsHistory getSmsHistoryByKey(Long smsId) {
		return comSmsHistoryDAO.selectByPrimaryKey(smsId);
	}

	public void setComSmsHistoryDAO(ComSmsHistoryDAO comSmsHistoryDAO) {
		this.comSmsHistoryDAO = comSmsHistoryDAO;
	}

	/**
	 * 返回订单的短信凭证，以及履行短信信息
	 * @param order
	 * @return
	 * @author dengcheng
	 */
	public List<ComSmsHistory> getCertSmsContent(OrdOrder order){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("objectId", order.getOrderId());
		List<ComSmsHistory> listTemp = null;
		if (!order.isPassportOrder() && order.isPayToLvmama() && order.isPaymentSucc()) {
			params.put("templateId", Constant.SMS_TEMPLATE.NORM_PAYMENT_SUCC.name());
			List<ComSmsHistory> list = comSmsHistoryDAO.selectPassSmsByParam(params);
			listTemp = new ArrayList<ComSmsHistory>();
			if(list!=null&&list.size()>0){
				listTemp.add(list.get(0));
			}
		}else if (!order.isPassportOrder() && !order.isPayToLvmama() && order.isApprovePass()) {
			params.put("templateId", Constant.SMS_TEMPLATE.NORM_PAYTO_SUP.name());
			List<ComSmsHistory> list = comSmsHistoryDAO.selectPassSmsByParam(params);
			listTemp = new ArrayList<ComSmsHistory>();
			if(list!=null&&list.size()>0){
				listTemp.add(list.get(0));
			}
		}else if (order.isPassportOrder() && order.isPayToLvmama() && order.isPaymentSucc()) {
			params.put("templateId", Constant.SMS_TEMPLATE.DIEM_PAYMENT_SUCC.name());
			List<ComSmsHistory> list = comSmsHistoryDAO.selectPassSmsByParam(params);
			listTemp = list;
		}else if (order.isPassportOrder() && !order.isPayToLvmama() && order.isApprovePass() ) {
			params.put("templateId", Constant.SMS_TEMPLATE.DIEM_PAYMENT_SUCC.name());
			List<ComSmsHistory> list = comSmsHistoryDAO.selectPassSmsByParam(params);
			listTemp = list;
		} 
		return listTemp;
	}

	@Override
	public Integer selectRowCount(Map searchConds) {
		return comSmsHistoryDAO.selectRowCount(searchConds);
	}
}
