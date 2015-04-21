package com.lvmama.distribution.remote;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.clutter.utils.DistributionParseUtil;
import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
import com.lvmama.comm.bee.service.distribution.IDistributionRefundRemote;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.client.jinzonglv.WebServiceLvmamaProxy;
import com.lvmama.distribution.model.lv.Order;
import com.lvmama.distribution.model.lv.Response;
import com.lvmama.distribution.model.lv.ResponseHead;
import com.lvmama.distribution.service.DistributionCommonService;
import com.lvmama.distribution.service.DistributionForJingdongService;
import com.lvmama.distribution.util.DistributionUtil;

public class DistributionRefundRemoteImpl implements IDistributionRefundRemote {
	private static final Log log = LogFactory.getLog(DistributionRefundRemoteImpl.class);
	private DistributionForJingdongService distributionForJingdongService;
	private DistributionCommonService distributionCommonService;
	/**
	 * 执行退款
	 */
	public boolean refund(DistributionOrderRefund refund) {
		if(refund==null){
			log.info("applyOrderRefund requestError:  DistributionOrderRefund is NULL");
			return false;
		}
		String distributorCode=refund.getDistributorCode();
		log.info("DistributionCode： "+distributorCode);
		if(Constant.DISTRIBUTOR.JINGDONG.name().equals(distributorCode)){
			return distributionForJingdongService.applyOrderRefund(refund);
		}else if(Constant.DISTRIBUTOR.QUNA.name().equals(distributorCode)||Constant.DISTRIBUTOR.QUNA_TUAN.name().equals(distributorCode)){
			String url=DistributionParseUtil.getPropertiesByKey("qunaer.url");
			Map<String,String> map = qunaRefundMap(refund);
			return distributionCommonService.refundForHttp(refund, url, map);
		}else if(Constant.DISTRIBUTOR.QUNA_TICKET.name().equals(distributorCode)||Constant.DISTRIBUTOR.QUNA_TICKET_TUAN.name().equals(distributorCode)){
			String url=DistributionParseUtil.getPropertiesByKey("qunaerticket.url");
			Map<String,String> map = qunaRefundMap(refund);
			return distributionCommonService.refundForHttp(refund, url, map);
		}else if(Constant.DISTRIBUTOR.JINZONGLV.name().equals(distributorCode)){
			return this.refundForJinzonglv(refund);
		}else {
			String urlKey = distributorCode.toLowerCase()+".url";
			String url=DistributionParseUtil.getPropertiesByKey(urlKey);
			Map<String,String> map=new HashMap<String, String>();
			String msg = createXml(refund);
			map.put("refund",msg);
			return distributionCommonService.refundForHttp(refund, url, map);
		}
	}
	
	/**
	 * 金棕榈退款
	 * @param refund
	 * @return
	 */
	private boolean refundForJinzonglv(DistributionOrderRefund refund){
		boolean refundSuccess=false;
		log.info("refund param="+refund.toString());
		String msg = createXml(refund);
		WebServiceLvmamaProxy proxy = new WebServiceLvmamaProxy();
		log.info("applyOrderRefund requestXml:"+msg);
        try {
            String responseStr = proxy.refund(msg);
            log.info("applyOrderRefund responseXml:"+responseStr);
            Response response=DistributionUtil.getRefundRes(responseStr);
			ResponseHead head=response.getHead();
			refundSuccess="1000".equals(head.getCode());
        } catch (Exception e) {
        	refundSuccess=false;
            e.printStackTrace();
        }
        if(refundSuccess){
        	distributionCommonService.refundStatu(refund.getPartnerOrderId(),refund.getDistributionOrderRefundId());
        }
		return refundSuccess;
	}


	private String createXml(DistributionOrderRefund refund) {
		Order order=new Order(refund);
		String msg=order.buildForRefund();
		String signed=refund.getDistributorKey()+order.getRefundSigned();
		log.info("refund sign:"+signed);
		try {
			signed=MD5.encode(signed);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		msg=DistributionUtil.createRequest(refund.getDistributorCode(), msg, signed);
		log.info("refund Xml:"+msg);
		return msg;
	}
	/**
	 * 去哪退款
	 * @param refund
	 * @return
	 */
	private Map<String,String> qunaRefundMap(DistributionOrderRefund refund){
		Map<String,String> map=new HashMap<String, String>();
		String user=DistributionParseUtil.getPropertiesByKey("qunaer.user");
		String pass=DistributionParseUtil.getPropertiesByKey("qunaer.pass");
		String method=DistributionParseUtil.getPropertiesByKey("qunaer.method");
		String sid=DistributionParseUtil.getPropertiesByKey("qunaer.sid");
		map.put("user", user);
		map.put("pass", pass);
		map.put("method",method);
		map.put("sid", sid);
		map.put("orderid", refund.getPartnerOrderId());
		map.put("refund_money", String.valueOf(refund.getRefundAmount()));
		map.put("refund_factorage", String.valueOf(refund.getFactorage()));
		log.info("refund param="+refund.toString());
		return map;
	}
	
	
	public DistributionForJingdongService getDistributionForJingdongService() {
		return distributionForJingdongService;
	}
	public void setDistributionForJingdongService(DistributionForJingdongService distributionForJingdongService) {
		this.distributionForJingdongService = distributionForJingdongService;
	}


	public DistributionCommonService getDistributionCommonService() {
		return distributionCommonService;
	}


	public void setDistributionCommonService(
			DistributionCommonService distributionCommonService) {
		this.distributionCommonService = distributionCommonService;
	}

}