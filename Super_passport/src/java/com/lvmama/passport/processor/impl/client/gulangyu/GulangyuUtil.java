package com.lvmama.passport.processor.impl.client.gulangyu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.passport.processor.impl.client.gulangyu.model.OrderCancelResponse;
import com.lvmama.passport.processor.impl.client.gulangyu.model.OrderSubmitResponse;
import com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpotResponse;
import com.lvmama.passport.processor.impl.client.gulangyu.model.SendMsgResponse;
import com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicketResponse;
import com.lvmama.passport.processor.impl.client.gulangyu.model.TicketResponse;
import com.lvmama.passport.utils.Configuration;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 鼓浪屿返回报文解析工具类
 * @author lipengcheng
 *
 */
public class GulangyuUtil {
	private  static final Log log = LogFactory.getLog(GulangyuUtil.class);
	
	/**
	 * 解析获取景区接口返回结果
	 * @param result
	 * @return
	 */
	public static ScenicSpotResponse getScenicSpotResponse(String result) {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("Data", com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpotResponse.class);
		xStream.alias("Rec", com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpot.class);

		xStream.addImplicitCollection(com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpotResponse.class, "scenicSpotList");
		xStream.aliasField("UUid", com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpot.class, "uuId");
		xStream.aliasField("UUtitle", com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpot.class, "uuTitle");
		xStream.aliasField("UUaddtime", com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpot.class, "uuAddtime");
		xStream.aliasField("UUimgpath", com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpot.class, "uuImgPath");
		xStream.aliasField("UUarea", com.lvmama.passport.processor.impl.client.gulangyu.model.ScenicSpot.class, "uuArea");
		ScenicSpotResponse scenicSpotResponse = (ScenicSpotResponse) xStream.fromXML(result);
		return scenicSpotResponse;
	}
	
	/**
	 * 解析获取景区接口返回结果
	 * @param result
	 * @return
	 */
	public static TicketResponse getTicketResponse(String result){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("Data", com.lvmama.passport.processor.impl.client.gulangyu.model.TicketResponse.class);
		xStream.alias("Rec", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class);
		
		xStream.addImplicitCollection(com.lvmama.passport.processor.impl.client.gulangyu.model.TicketResponse.class, "ticketList");
		xStream.aliasField("UUlandid", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuLandid");
		xStream.aliasField("UUltitle", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuLtitle");
		xStream.aliasField("UUid", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuId");
		xStream.aliasField("UUtitle", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuTitle");
		xStream.aliasField("UUtprice", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuTprice");
		xStream.aliasField("UUuprice", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuUprice");
		xStream.aliasField("UUgprice", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuGprice");
		xStream.aliasField("UUdelaydays", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuDelaydays");
		xStream.aliasField("UUuniont", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuUniont");
		xStream.aliasField("UUpay", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuPay");
		xStream.aliasField("UUnotes", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuNotes");
		xStream.aliasField("UUwprice", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuWprice");
		xStream.aliasField("UUifs", com.lvmama.passport.processor.impl.client.gulangyu.model.Ticket.class, "uuIfs");
		
		TicketResponse ticketResponse = (TicketResponse)xStream.fromXML(result);
		return ticketResponse;
	}
	
	/**
	 * 解析特价门票接口返回结果
	 * @param result
	 * @return
	 */
	public static SpecialTicketResponse getSpecialTicketResponse(String result) {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("Data", com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicketResponse.class);
		xStream.alias("Rec", com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicket.class);

		xStream.addImplicitCollection(com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicketResponse.class, "specialTicketList");
		xStream.aliasField("UUid", com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicket.class, "uuId");
		xStream.aliasField("UUlid", com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicket.class, "uuLid");
		xStream.aliasField("UUtid", com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicket.class, "uuTid");
		xStream.aliasField("UUptime", com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicket.class, "uuPtime");
		xStream.aliasField("UUpprice", com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicket.class, "uuPprice");
		xStream.aliasField("UUnotes", com.lvmama.passport.processor.impl.client.gulangyu.model.SpecialTicket.class, "uuNotes");
		
		SpecialTicketResponse specialTicketResponse = (SpecialTicketResponse)xStream.fromXML(result);
		return specialTicketResponse;
	}
	
	/**
	 * 解析提交订单接口返回结果
	 * @param result
	 * @return
	 */
	public static OrderSubmitResponse getOrderSubmitResponse(String result){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("Data", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderSubmitResponse.class);
		xStream.alias("Rec", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class);
		
		xStream.aliasField("Rec", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderSubmitResponse.class,"orderInfo");
		xStream.aliasField("UUordernum", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uuOrderNum");
		xStream.aliasField("UU16uorder", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uu16uOrder");
		xStream.aliasField("UUcertnum", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uuCertnum");
		xStream.aliasField("UUlid", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uuLid");
		xStream.aliasField("UUtid", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uuTid");
		xStream.aliasField("UUplaytime", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uuPlayTime");
		xStream.aliasField("UUordername", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uuOrderName");
		xStream.aliasField("UUordertel", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uuOrderTel");
		xStream.aliasField("UUtotal", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uuTotal");
		xStream.aliasField("UUpayinfo", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderInfo.class, "uuPayInfo");
		
		OrderSubmitResponse orderSubmitResponse = (OrderSubmitResponse)xStream.fromXML(result);
		return orderSubmitResponse;
	}
	
	/**
	 * 解析取消订单接口返回报文
	 * @param result
	 * @return
	 */
	public static OrderCancelResponse getOrderCancelResponse(String result){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("Data", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderCancelResponse.class);
		xStream.alias("Rec", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderCancel.class);
		
		xStream.aliasField("Rec", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderCancelResponse.class, "orderCancel");
		xStream.aliasField("UUstatus", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderCancel.class, "uuStatus");
		xStream.aliasField("UUordernum", com.lvmama.passport.processor.impl.client.gulangyu.model.OrderCancel.class, "uuOrdernum");
		OrderCancelResponse orderCancelData = (OrderCancelResponse)xStream.fromXML(result);
		return orderCancelData;
	}
	
	/**
	 * 解析重发短信返回的报文
	 * @param result
	 * @return
	 */
	public static SendMsgResponse getSendMsgResponse(String result){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("Data", com.lvmama.passport.processor.impl.client.gulangyu.model.SendMsgResponse.class);
		xStream.alias("Rec", com.lvmama.passport.processor.impl.client.gulangyu.model.SendMsg.class);
		
		xStream.aliasField("Rec", com.lvmama.passport.processor.impl.client.gulangyu.model.SendMsgResponse.class, "sendMsg");
		xStream.aliasField("UU16uorder", com.lvmama.passport.processor.impl.client.gulangyu.model.SendMsg.class, "uu16uorder");
		xStream.aliasField("UUresend", com.lvmama.passport.processor.impl.client.gulangyu.model.SendMsg.class, "uuResend");
		
		SendMsgResponse sendMsgResponse = (SendMsgResponse) xStream.fromXML(result);
		return sendMsgResponse;
	}
	
	public static void main(String args []){
		Configuration configuration = Configuration.getConfiguration();

		StringBuilder data = new StringBuilder();
		InputStream in = configuration.getResourceAsStream("/data.properties");
		InputStreamReader inRead = null;
		BufferedReader readBuf = null;
		try {
			inRead = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码
			String inStr;

			// 组合控制台输出信息字符串
			readBuf = new BufferedReader(inRead);
			while ((inStr = readBuf.readLine()) != null) {
				data.append(inStr);
			}
			//Mo mo=StringUtil.getMo(data.toString());
			String temp=data.toString();
			log.info(temp);
			/*OrderCancelResponse orderCancelResponse = GulangyuUtil.getOrderCancelResponse(temp);
			log.info(orderCancelResponse);*/
			SendMsgResponse sendMsgResponse = GulangyuUtil.getSendMsgResponse(temp);
			log.info(sendMsgResponse);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
