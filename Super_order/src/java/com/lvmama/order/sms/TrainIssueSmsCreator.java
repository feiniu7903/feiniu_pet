/**
 * 
 */
package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderTrafficTicketInfo;
import com.lvmama.comm.bee.po.prod.LineStationStation;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
public class TrainIssueSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator{

	private Long orderItemMetaId;
	private OrdOrder order;
	private OrderService orderServiceImpl = SpringBeanProxy.getBean(OrderService.class,"orderServiceImpl");
	private ProdTrainService prodTrainService = SpringBeanProxy.getBean(ProdTrainService.class,"prodTrainService");
	private MetaProductBranchService metaProductBranchService = SpringBeanProxy.getBean(MetaProductBranchService.class,"metaProductBranchService");
	private OrdOrderItemMeta orderItemMeta;
	private Log logger = LogFactory.getLog(TrainIssueSmsCreator.class);
	
	public TrainIssueSmsCreator(Long objectId,String mobile,Long orderItemMetaId) {
		super(objectId,mobile);
		this.orderItemMetaId = orderItemMetaId;
		order =orderServiceImpl.queryOrdOrderByOrderId(objectId);
		orderItemMeta = getItemMeta();
	}
	
	@Override
	Map<String, Object> getContentData() {
		Map<String,Object> map = new HashMap<String, Object>();
		//List<OrdOrderTraffic> list  =order.getOrderTrafficList();
		OrdOrderTraffic traffic = getTraffic();
		map.put("visitTime", DateUtil.formatDate(orderItemMeta.getVisitTime(),"MM月dd日"));
		map.put("lineName", traffic.getTrainName());
		map.put("departureStation", traffic.getDepartureStationName());
		map.put("arrivalStation", traffic.getArrivalStationName());
		
		MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(orderItemMeta.getMetaBranchId());
		LineStationStation stationStation = prodTrainService.getStationStationById(metaBranch.getStationStationId());
		map.put("departureTime", TimePriceUtil.formatTime(stationStation.getDepartureTime()));
		
		StringBuffer travellerNames = new StringBuffer();
		StringBuffer seatNo = new StringBuffer();
		for(OrdOrderTrafficTicketInfo ticketInfo:traffic.getOrderTrafficTicketInfoList()){
			travellerNames.append(ticketInfo.getPerson().getName());
			travellerNames.append("、");
			seatNo.append(ticketInfo.getSeatNo());
			seatNo.append("、");
		}
		travellerNames.setLength(travellerNames.length()-1);
		seatNo.setLength(seatNo.length());
		map.put("traveller", travellerNames.toString());
		map.put("seatNo", seatNo.toString());
		return map;
	}
	
	private OrdOrderTraffic getTraffic(){
		for(OrdOrderTraffic traffic:order.getOrderTrafficList()){
			if(traffic.getOrderItemMetaId().equals(orderItemMetaId)){
				return traffic;
			}
		}
		return null;
	}
	
	private OrdOrderItemMeta getItemMeta(){
		for(OrdOrderItemMeta itemMeta:order.getAllOrdOrderItemMetas()){
			if(itemMeta.getOrderItemMetaId().equals(orderItemMetaId)){
				return itemMeta;
			}
		}
		return null;
	}
	

	@Override
	ProdChannelSms getSmsTemplate() {
		logger.info("sms template:"+order.getChannel()+"   templateId:TRAIN_ISSUE_SMS");
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.TRAIN_ISSUE_SMS.name());
	}

}
