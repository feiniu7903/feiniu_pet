package com.lvmama.passport.processor.impl.client.chimelong.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.pass.PassProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.PassportConstant;
import com.lvmama.passport.processor.impl.client.chimelong.TicketServiceClient;
import com.lvmama.passport.processor.impl.client.chimelong.TicketServicePortType;
import com.lvmama.passport.processor.impl.client.chimelong.model.ArrayOfMaXiActTime;
import com.lvmama.passport.processor.impl.client.chimelong.model.MaXiActTime;

/**
 * 封装长隆购票请求信息字段
 * 
 * 
 */

public class BuyTicket_zh {
	private static Log log = LogFactory.getLog(BuyTicket_zh.class);
	PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	public Order getOrder(PassCode passCode) throws Exception {

		String key = ChimelongUtils.getMd5Key_zh();
		String ver_no = ChimelongConfig.getVersion();
		String mer_no = ChimelongConfig.getZhUserId();
		String orderInfo = "";
		StringBuilder buf = new StringBuilder();
		// String s ="{\"tk\":\"000030,1,150\",\"tkType\": \"2\"}";
		String tk = "";
		String tkType = "";
		String validTime = "";
		String invalidTime = "";
		String maxiTime="";
//		List<Long> supplierIds = new ArrayList<Long>();
//		for (PassPortCode passPort : passCode.getPassPortList()) {
//			supplierIds.add(passPort.getSupplierId());
//		}
		
		PassPortCode passPort=passCode.getPassPortList().get(0);
		Order order = ChimelongUtils.getOrder(passCode.getOrderId(),String.valueOf(passPort.getTargetId()), passCode.getObjectId());

		/*
		StringBuilder bufTargetId=new StringBuilder();
		for (PassPortCode passPort : passCode.getPassPortList()) {
			bufTargetId.append(passPort.getTargetId().toString()+",");
		}
		int len=bufTargetId.length();
		bufTargetId.deleteCharAt(len-1);
		order = StringUtils.getOrder(passCode.getSerialNo(), bufTargetId.toString());
		*/
		
		tk = order.getTk();
		tkType = order.getTkType();
		validTime = order.getValidTime();
		invalidTime = order.getInvalidTime();
		Map<String,String> data =this.getMaXiTimes(order.getTypeCode(),order.getValidDate());
		if(data!=null){
			order.setMaxiTime(data.get("dateTemp"));
			maxiTime=data.get("maxiTime");
			ver_no="12";
		}
		buf.append(mer_no + "|");// 商户编号
		buf.append(passCode.getSerialNo() + "|");// 定单号（流水号
		buf.append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "|");// 商家订单日期时间
		buf.append(validTime + "|");// 该订单中各门票的生效时间
		buf.append(invalidTime + "|");// 该订单中各门票的失效时间
		buf.append(tkType + "|");// tkType:0:指定日子票 1:期票 2:期证
		buf.append(tk + "|");// tk:门票类型,张数,单价
		buf.append(passCode.getMobile() + "|||||");// 手机号码
		if(data!=null){
			buf.append("|"+maxiTime);
		}
		buf.append("|00");
		orderInfo = buf.toString();
		//orderInfo = "0001|2011042700004-1|2011-04-27 11:55:25|2011-05-02 00:00:00|2011-05-02 00:00:00|0|000050,2,150.00;|13126435676| |20110427000000000023|069969690349|2011-04-27 11:55:38|300.0|0,2011-04-27 19:30,2011-04-27 21:00|00";
		log.info("Chimelong Apply Code Request Param :" + orderInfo);
		orderInfo = ChimelongUtils.enCode(key, orderInfo);
		String sign = ChimelongUtils.getSign(ver_no + mer_no + orderInfo + key);
		order.setMer_no(mer_no);
		order.setVer_no(ver_no);
		order.setOrderInfo(orderInfo);
		order.setSign(sign);

		return order;
	}
	
	
	/**
	 * 马戏申请
	 * @param passCode
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public Order getOrder(PassCode passCode,Order order) throws Exception {
		String orderInfo = "";
		StringBuilder buf = new StringBuilder();
		// String s ="{\"tk\":\"000030,1,150\",\"tkType\": \"2\"}";
		String tk = "";
		String tkType = "";
		String validTime = "";
		String invalidTime = "";
		String maxiTime="maxiTime";

		tk = order.getTk();
		tkType = order.getTkType();
		validTime = order.getValidTime();
		invalidTime = order.getInvalidTime();
		
		buf.append(order.getMer_no() + "|");// 商户编号
		buf.append(passCode.getSerialNo() + "|");// 定单号（流水号
		buf.append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "|");// 商家订单日期时间
		buf.append(validTime + "|");// 该订单中各门票的生效时间
		buf.append(invalidTime + "|");// 该订单中各门票的失效时间
		buf.append(tkType + "|");// tkType:0:指定日子票 1:期票 2:期证
		buf.append(tk + "|");// tk:门票类型,张数,单价
		buf.append(passCode.getMobile() + "|||||");// 手机号码
		if(order.isMaxi()){
			buf.append("|"+maxiTime);
		}
		buf.append("|00");
		orderInfo = buf.toString();
		//orderInfo = "0001|2011042700004-1|2011-04-27 11:55:25|2011-05-02 00:00:00|2011-05-02 00:00:00|0|000050,2,150.00;|13126435676| |20110427000000000023|069969690349|2011-04-27 11:55:38|300.0|0,2011-04-27 19:30,2011-04-27 21:00|00";
		log.info("Chimelong Apply Code Request Param :" + orderInfo);
		order.setOrderInfo(orderInfo);
		return order;
	}
	
	public Map<String,String> getMaXiTimes(String tkType, String validTime ) throws Exception {
		Map<String, String> data = new HashMap<String, String>();
		TicketServiceClient client = new TicketServiceClient("zh");
		TicketServicePortType service = client.getTicketServiceHttpPort();
		String flag = service.checkHasMaXi(tkType);
		MaXiActTime temp = null;
		if ("1".equalsIgnoreCase(flag)) {
			ArrayOfMaXiActTime maXiActTime = service.findMaXiActTime(validTime);
			List<MaXiActTime> maXiTimes = maXiActTime.getMaXiActTime();
			for (MaXiActTime maXiTime : maXiTimes) {
				if (maXiTime != null) {
					temp = maXiTime;
					break;
				}
			}
		
		}
		if (temp != null) {
			String start = DateFormatUtils.format(temp.getActTimeBegin()
					.toGregorianCalendar().getTime(), "yyyy-MM-dd HH:mm");
			String end = DateFormatUtils.format(temp.getActTimeEnd()
					.toGregorianCalendar().getTime(), "yyyy-MM-dd HH:mm");
			String id = temp.getActId().getValue();
			String maxiTime = id + "," + start + "," + end;
			String dateTemp = "场次:" + id + ",场次开演时间:" + start + ",场次结束时间:"
					+ end;
			data.put("dateTemp", dateTemp);
			data.put("maxiTime", maxiTime);
			return data;
		} else {
			return null;
		}
		
	}
	
    /**
	 * 获得订单信息
	 * @param serialno
	 * @param supplierIds
	 * @return
	 */
    public  Order getOrderInfo(Long orderId,String targetIds,Long OrderItemMetaId)throws Exception{
    	 
    	Order Order=new Order();
    	StringBuilder tk = new StringBuilder();
		String tkType = "";
		String validTime = "";
		String invalidTime = "";
		OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
		OrdOrder ordOrder=orderServiceProxy.queryOrdOrderByOrderId(orderId);
		boolean exit=false;
			// 订单子指向
		 for (OrdOrderItemProd itemProd : ordOrder.getOrdOrderItemProds()) {
				if(exit){
					break;
				}
				for (OrdOrderItemMeta itemMeta : itemProd.getOrdOrderItemMetas()) {
					if(itemMeta.getOrderItemMetaId().equals(OrderItemMetaId)){

						Map<String,Object> params=new HashMap<String,Object>();
						String date = DateUtil.getFormatDate(itemMeta.getVisitTime(), "yyyy-MM-dd");
						params.put("visitDate", date);
						params.put("objectId", itemMeta.getMetaProductId());
						params.put("provider", PassportConstant.PASS_PROVIDER_TYPE.CHIMELONG.name());
						PassProduct passProduct =passCodeService.selectPassProductByParams(params);
						// 代理产品编号
						String ticketType="";
						if(passProduct!=null){
							ticketType = passProduct.getProductIdSupplier();
							tkType = passProduct.getProductTypeSupplier();
						}else{
							// 代理产品编号
							ticketType = itemMeta.getProductIdSupplier();
							Order.setTypeCode(ticketType);
							// 代理产品类型
							tkType = itemMeta.getProductTypeSupplier();
						}
						
						// 价格
						String price = String.valueOf(itemProd.getPriceYuan());
						// 票数(打包分数*订购分数)
						Long sheetTotal = itemMeta.getProductQuantity()*itemMeta.getQuantity();
						Order.setValidDate(DateUtil.getFormatDate(itemMeta.getVisitTime(), "yyyy-MM-dd"));
						validTime = DateUtil.getFormatDate(itemMeta.getVisitTime(), "yyyy-MM-dd HH:mm:ss");
						Date temp = DateUtil.getDateAfterDays(itemMeta.getVisitTime(), 0);
						invalidTime = DateUtil.getFormatDate(temp, "yyyy-MM-dd HH:mm:ss");
						// "000030,1,150;000032,1,90;000040,2,45";
						tk.append(ticketType + "," + sheetTotal + "," + price + ";");
						exit=true;
						break;
					}
			}
		 }
		log.info("Chimelong Apply Code ticketType:"+tk.toString());
		String ver_no = ChimelongConfig.getVersion();
		String mer_no = ChimelongConfig.getZhUserId();
		TicketServiceClient client = new TicketServiceClient("zh");
		TicketServicePortType service = client.getTicketServiceHttpPort();
		String flag = service.checkHasMaXi(Order.getTypeCode());
		if ("1".equalsIgnoreCase(flag)) {
			Order.setMaxi(true);
			ver_no="12";
		}
		Order.setMer_no(mer_no);
		Order.setVer_no(ver_no);
		Order.setTkType(tkType);
		Order.setTk(tk.toString());
		Order.setValidTime(validTime);
		Order.setInvalidTime(invalidTime);
    	return Order;
    }
}
