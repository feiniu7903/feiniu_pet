package com.lvmama.distribution.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.transport.http.XFireServletController;

import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.distribution.util.RequestUtil;
public class BaseDistributionService {
	private static final Log log = LogFactory.getLog(BaseDistributionService.class);
	protected DistributionCommonService distributionCommonService;
	/** 联系人接口 */
	protected IReceiverUserService receiverUserService;
	protected ProdProductBranchService prodProductBranchService;
	/** 订单服务 */
	protected OrderService orderServiceProxy;
	protected UserUserProxy userUserProxy;
	protected PayPaymentService payPaymentService;
	
	protected TopicMessageProducer resourceMessageProducer;
	protected TopicMessageProducer passportMessageProducer;
	/** 订单相关消息 */
	protected TopicMessageProducer orderMessageProducer;
	protected PassCodeService passCodeService;
	/**
	 * 验证IP合法性
	 * @param distributorInfoId
	 * @return
	 */
	protected Boolean validateIp(Long distributorInfoId) {
		boolean flag = false;
		HttpServletRequest request1 = XFireServletController.getRequest();
		String requestIp = RequestUtil.getIpAddr(request1);
		log.info("validateIp:" + requestIp);
		List<String> ipList = distributionCommonService.getDistributorIps(distributorInfoId);
		if (ipList.contains(requestIp)) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 取消订单
	 * 
	 * @param orderId
	 * @return
	 */
	protected boolean cancelOrder(Long orderId, DistributorInfo distributorInfo) {
		return orderServiceProxy.cancelOrder(orderId, distributorInfo.getDistributorName() + "取消订单", distributorInfo.getDistributorCode());
	}
	
	public void setDistributionCommonService(
			DistributionCommonService distributionCommonService) {
		this.distributionCommonService = distributionCommonService;
	}
	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}
	public void setPassportMessageProducer(
			TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	
	
}
