package com.lvmama.passport.callback.ws.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.callback.ws.IDinosaurTownService;
import com.lvmama.passport.processor.UsedCodeProcessor;
import com.lvmama.passport.processor.impl.client.dinosaurtown.model.DinosaurtownClientParser;
import com.lvmama.passport.processor.impl.client.dinosaurtown.model.Order;
import com.lvmama.passport.processor.impl.client.dinosaurtown.model.Response;
import com.lvmama.passport.processor.impl.client.dinosaurtown.model.Ticket;

/**
 * 常州环球恐龙城服务接口实现
 * 
 * @author chenlinjun
 * 
 */
public class DinosaurTownService implements IDinosaurTownService {
	private static final Log log = LogFactory.getLog(DinosaurTownService.class);
	private OrderService orderServiceProxy;
	private PassCodeService  passCodeService;
	private UsedCodeProcessor usedCodeProcessor;

	@Override
	public String usePerFormOrder(String reqXml) {
		log.info("DinosaurTown usePerFormOrder : " + reqXml);
		Response response = DinosaurtownClientParser.getResponse(reqXml);
		String code = response.getOrder().getNo();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("code", code);
		PassCode passCode = passCodeService.getPassCodeByParams(data);
		List<PassPortCode> passPortList = passCodeService.queryProviderByCode(passCode.getCodeId());
		PassPortCode passPortCode=passPortList.get(0);
		//履行对象
		Long targetId=passPortCode.getTargetId();
		String child = "0";
		String adult = "0";
		Passport passport = new Passport();
		passport.setSerialno(passCode.getSerialNo());
		passport.setPortId(targetId);
		passport.setOutPortId(targetId.toString());
		passport.setChild(child);
		passport.setAdult(adult);
		passport.setDeviceId("DinosaurTown");
		String codeData = usedCodeProcessor.update(passport);
		boolean flag = false;
		// 业务系统更新成功
		if (PassportConstant.PASSCODE_APPLY_STATUS.SUCCESS.name().equalsIgnoreCase(codeData.trim())) {
			flag = true;
		} else if ("NODATA".equalsIgnoreCase(codeData)) {
			flag = false;
		}
		StringBuilder buf = new StringBuilder();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("<Response>");
		if (flag) {
			buf.append("<Order No=\"" + response.getOrder().getNo() + "\" flag =\"1\"/>");
		} else {
			buf.append("<Order No=\"" + response.getOrder().getNo() + "\" flag =\"0\"/>");
		}
		buf.append("</Response>");
		log.info("DinosaurTown Add Perform Info Response Data: " + buf.toString());
		return buf.toString();
	}

	@Override
	public String getOrder(String orderNO, String user, String check) {
		log.info("DinosaurTown Get Order : orderNO=" + orderNO + ", user=" + user + ", check=" + check);
		if (DinosaurtownClientParser.validate(user, check)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("providerId", PassportConstant.getInstance().getDinosaurtownId());
			data.put("code", orderNO);
			List<PassCode> passCodeList =this.passCodeService.selectCodeByProviderIdAndValidTime(data);
			String response =getResponseXml(passCodeList);
			return response;
		} else {
			return "";
		}
	}

	@Override
	public String queryOrderList(String startTime, String endTime, String user, String check) {
		log.info("DinosaurTown Query Order List : startTime=" + startTime + ", endTime=" + endTime + ", user=" + user
				+ ", check=" + check);
		if (DinosaurtownClientParser.validate(user, check)) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("providerId", PassportConstant.getInstance().getDinosaurtownId());
			data.put("startTime", startTime);
			data.put("endTime", endTime);
			List<PassCode> passCodeList =this.passCodeService.selectCodeByProviderIdAndValidTime(data);
			String response =getResponseXml(passCodeList);
			return response;
		} else {
			return "";
		}
	}

	/**
	 * 获得响应XML
	 * 
	 * @param list
	 * @return
	 */
	public String getResponseXml(List<PassCode> passCodeList) {
		Response response = new Response();
		List<Order> orders = new ArrayList<Order>();
		for (PassCode passCode : passCodeList) {
			CompositeQuery compositeQuery = new CompositeQuery();
			compositeQuery.getPageIndex().setBeginIndex(0);
			compositeQuery.getPageIndex().setEndIndex(1000000000);
			List<String> targetIdlist = this.buildTargetId(passCode.getPassPortList());
			String targetIds = this.join(",", targetIdlist.iterator());
			compositeQuery.getMetaPerformRelate().setTargetId(targetIds);
			compositeQuery.getMetaPerformRelate().setOrderId(passCode.getOrderId());
			List<OrdOrderItemMeta> orderitemMeta = orderServiceProxy
					.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
			OrdOrder ordorder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
			// 订单子指向
			for (OrdOrderItemMeta itemMeta : orderitemMeta) {
				// 代理产品编号
				// String no = "5892";// metaProduct.getProductIdSupplier();

				String no = itemMeta.getProductIdSupplier();
				// 代理产品名称
				// String name = "恐龙园+恐龙谷温泉";//
				// metaProduct.getProductTypeSupplier();
				String name = itemMeta.getProductTypeSupplier();
				String visitTime = DateFormatUtils.format(itemMeta.getVisitTime(), "yyyy-MM-dd");
				Ticket ticket = new Ticket();
				ticket.setNo(no);
				ticket.setName(name);
				ticket.setVisitDate(visitTime);
				// 票数
				ticket.setCount(String.valueOf((itemMeta.getTotalChildQuantity() + itemMeta.getTotalAdultQuantity())));
				ticket.setState_id("0");
				ticket.setChild(String.valueOf(itemMeta.getTotalChildQuantity()));
				// 成人数
				ticket.setMan(String.valueOf(itemMeta.getTotalAdultQuantity()));
				ticket.setHasMan("0");
				Order order = new Order();
				order.setNo(passCode.getCode());
				order.setCreateDate(DateFormatUtils.format(ordorder.getCreateTime(), "yyyy-MM-dd"));
				order.setLinkMobile(ordorder.getContact().getMobile());
				order.setLinkMan(ordorder.getContact().getName());
				order.setLinkCard(ordorder.getContact().getCertNo() == null ? "" : ordorder.getContact().getCertNo());
				order.setTicket(ticket);
				orders.add(order);
			}
		}
		response.setOrders(orders);
		return response.toResponseXml();
	}

	@SuppressWarnings("rawtypes")
	private String join(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext()){
			buf.append( objects.next() );
		}
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}
	
	private List<String> buildTargetId(List<PassPortCode> passPortCodeList) {
		List<String> targetIdlist = new ArrayList<String>();
		for (PassPortCode passPortCode : passPortCodeList) {
			targetIdlist.add(passPortCode.getTargetId().toString());
		}
		return targetIdlist;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
 
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}

}
