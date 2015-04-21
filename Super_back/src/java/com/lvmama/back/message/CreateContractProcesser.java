package com.lvmama.back.message;
/**
 * @author shangzhengyuan
 * @description 生成线路电子合同PROCESSER
 * @version 在线预售权
 * @time 20120801
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.TravelDescriptionService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class CreateContractProcesser  implements MessageProcesser {
	protected static final Log LOG = LogFactory.getLog(EContractProcesser.class);
	protected OrderService orderServiceProxy;
	protected EContractClient contractClient;
	protected TravelDescriptionService travelDescriptionService;
	protected FSClient fsClient;
	protected TopicMessageProducer orderMessageProducer;
	
	@Override
	public void process(Message message) {
		if(message.isOrderCreateMsg()){
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
			if (null == order) {
				LOG.error("订单为空，发送电子合同");
				return;
			}
			if (order.isNeedEContract()) {
				boolean isCreated = contractClient.createEContract(order, "system");
				if(!isCreated){
					LOG.warn("system create contract content is error!\r\n");
				}
				if (Constant.CHANNEL.BACKEND.name().equals(order.getChannel())) {
					orderMessageProducer.sendMsg(MessageFactory
							.newOrderSendEContract(order.getOrderId()));
				}	
			}
			
			
			if (Constant.PRODUCT_TYPE.ROUTE.name().equals(order.getMainProduct().getProductType())) {
				String travelXml = travelDescriptionService.getTravelDesc(order.getMainProduct().getProductId(), order.getMainProduct().getMultiJourneyId());
				if(StringUtils.isNotEmpty(travelXml)){
					try {
						Long fileId = fsClient.uploadFile(order.getOrderId()
								+ "_travel.xml", travelXml.getBytes(), "eContract");
						travelDescriptionService.initOrderTravel(fileId,
								order.getOrderId(), "system");
					} catch (Exception e) {
						LOG.warn("create order travel is error :\r\n" + e);
					}
				}
			}
		}
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public EContractClient getContractClient() {
		return contractClient;
	}
	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}
	public TravelDescriptionService getTravelDescriptionService() {
		return travelDescriptionService;
	}
	public void setTravelDescriptionService(
			TravelDescriptionService travelDescriptionService) {
		this.travelDescriptionService = travelDescriptionService;
	}
	public FSClient getFsClient() {
		return fsClient;
	}
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}
	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}
	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
}
