package com.lvmama.train.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.order.OrderCancelReqVo;
import com.lvmama.comm.vo.train.order.OrderCancelRspVo;
import com.lvmama.comm.vo.train.order.OrderCreateReqVo;
import com.lvmama.comm.vo.train.order.OrderCreateRspVo;
import com.lvmama.comm.vo.train.order.OrderPaidReqVo;
import com.lvmama.comm.vo.train.order.OrderPaidRspVo;
import com.lvmama.comm.vo.train.order.OrderPassengerInfo;
import com.lvmama.comm.vo.train.order.OrderQueryReqVo;
import com.lvmama.comm.vo.train.order.OrderQueryRspVo;
import com.lvmama.comm.vo.train.order.OrderRefundSuccessReqVo;
import com.lvmama.comm.vo.train.order.OrderRefundSuccessRspVo;
import com.lvmama.comm.vo.train.order.TicketDrawbackInfo;
import com.lvmama.comm.vo.train.order.TicketDrawbackReqVo;
import com.lvmama.comm.vo.train.order.TicketDrawbackRspVo;
import com.lvmama.train.service.request.CancelOrderRequest;
import com.lvmama.train.service.request.CreateOrderRequest;
import com.lvmama.train.service.request.OrderPaidRequest;
import com.lvmama.train.service.request.OrderQueryRequest;
import com.lvmama.train.service.request.OrderRefundSuccessRequest;
import com.lvmama.train.service.request.TicketDrawbackRequest;
import com.lvmama.train.service.response.CancelOrderResponse;
import com.lvmama.train.service.response.CreateOrderResponse;
import com.lvmama.train.service.response.OrderPaidResponse;
import com.lvmama.train.service.response.OrderQueryResponse;
import com.lvmama.train.service.response.OrderRefundSuccessResponse;
import com.lvmama.train.service.response.TicketDrawbackResponse;

public class TrainOrderTest {
	public static void main(String[] args) {
		TrainOrderTest order = new TrainOrderTest();
		order.testOrderCreate();
//		order.testOrderCancel();
//		order.testOrderPaid();
//		order.testOrderQuery();
//		order.testTicketDrawback();
//		order.testOrderRefundSuccess();
	}
	
	private void testOrderCreate(){
		OrderCreateReqVo vo = new OrderCreateReqVo();
		vo.setDepartStation("南京");
		vo.setArriveStation("上海");
		vo.setDepartDate("2013-09-12");
		vo.setTrainId("G7059");
		
		List<OrderPassengerInfo> passengers = new ArrayList<OrderPassengerInfo>();
		OrderPassengerInfo passenger = new OrderPassengerInfo();
		passenger.setPassenger_name("测试");
		passenger.setTicket_class(203);
		passenger.setTicket_type(301);
		passenger.setTicket_price(21900);
		passenger.setId_type(401);
		passenger.setId_num("320219198104191510");
		passenger.setPhone_num("13671612122");
		passengers.add(passenger);
		vo.setPassengers(passengers);
		
		CreateOrderRequest request = new CreateOrderRequest(vo);
		CreateOrderResponse response = new TrainClient().execute(request);
		if(response.isSuccess()){
			OrderCreateRspVo rspVo = (OrderCreateRspVo)response.getRsp().getVo();
			System.out.println(rspVo);
		}
	}
	
	private void testOrderCancel(){
		OrderCancelReqVo vo = new OrderCancelReqVo();
		vo.setOrderId("80120130827134901896270");
		
		CancelOrderRequest request = new CancelOrderRequest(vo);
		CancelOrderResponse response = new TrainClient().execute(request);
		if(response.isSuccess()){
			OrderCancelRspVo rspVo = (OrderCancelRspVo)response.getRsp().getVo();
			System.out.println(rspVo);
		}
	}
	
	private void testOrderPaid(){
		OrderPaidReqVo vo = new OrderPaidReqVo();
		vo.setOrderId("80120130827164441194703");
		vo.setPaidResult(0);
		
		OrderPaidRequest request = new OrderPaidRequest(vo);
		OrderPaidResponse response = new TrainClient().execute(request);
		
		if(response.isSuccess()){
			OrderPaidRspVo rspVo = (OrderPaidRspVo)response.getRsp().getVo();
			System.out.println(rspVo);
		}
	}
	
	private void testOrderQuery(){
		OrderQueryReqVo vo = new OrderQueryReqVo();
		vo.setOrderId("80120130827164441194703");
		
		OrderQueryRequest request = new OrderQueryRequest(vo);
		OrderQueryResponse response = new TrainClient().execute(request);
		
		if(response.isSuccess()){
			OrderQueryRspVo rspVo = (OrderQueryRspVo)response.getRsp().getVo();
			System.out.println(rspVo);
		}
	}
	
	private void testTicketDrawback(){
		TicketDrawbackReqVo vo = new TicketDrawbackReqVo();
		vo.setOrderId("80120130827164441194703");
		vo.setRefundType(505);
		vo.setTicketNum(1);
		
		TicketDrawbackInfo ticket = new TicketDrawbackInfo();
		ticket.setTicket_id(900);
		ticket.setTicket_price(933);
		List<TicketDrawbackInfo> list = new ArrayList<TicketDrawbackInfo>();
		list.add(ticket);
		vo.setTicketDrawbackInfos(list);
		
		TicketDrawbackRequest request = new TicketDrawbackRequest(vo);
		TicketDrawbackResponse response = new TrainClient().execute(request);
		
		if(response.isSuccess()){
			TicketDrawbackRspVo rspVo = (TicketDrawbackRspVo)response.getRsp().getVo();
			System.out.println(rspVo);
		}
	}
	
	private void testOrderRefundSuccess(){
		OrderRefundSuccessReqVo vo = new OrderRefundSuccessReqVo();
		vo.setRefundId("90120130827164901530370");
		vo.setOrderId("80120130827164441194703");
		vo.setTicketFee(50);
		vo.setRefundType(Constant.TRAIN_REFUND_TYPE.REFUND_TYPE_506.getCode());
		
		OrderRefundSuccessRequest request = new OrderRefundSuccessRequest(vo);
		OrderRefundSuccessResponse response = new TrainClient().execute(request);
		
		if(response.isSuccess()){
			OrderRefundSuccessRspVo rspVo = (OrderRefundSuccessRspVo)response.getRsp().getVo();
			System.out.println(rspVo);
		}
	}
}
