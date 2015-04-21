package com.lvmama.pet.processor.web;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.pet.service.pay.PaymentQueryService;
import com.lvmama.comm.utils.StringUtil;
@Results({@Result(type="plainText",name="success")})
public class TenPayQueryAction extends BaseAction{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7808002094921703955L;
	private String payment_trade_no;
	private PaymentQueryService tenpayQueryService;
	private Message message;
	private PayPayment payment;
	@Action("/query/tenpayQuery")
	public String  excute(){
		
		
		PaymentQueryReturnInfo info =	tenpayQueryService.paymentStateQuery(payment);
		
		PrintWriter write = null;
		HttpServletResponse  res = ServletActionContext.getResponse();
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");
		try {
			res.getWriter().write(StringUtil.printParam(info));
			write = res.getWriter();
		
//			write.write(info.getCallbackInfo());
			//write.println(StringUtil.printParam(info));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(write!=null){
				write.close();
			}
		}
	return SUCCESS;
		
		
	}


	public PaymentQueryService getTenpayQueryService() {
		return tenpayQueryService;
	}


	public String getPayment_trade_no() {
		return payment_trade_no;
	}


	public void setPayment_trade_no(String payment_trade_no) {
		this.payment_trade_no = payment_trade_no;
	}


	public PayPayment getPayment() {
		return payment;
	}


	public void setPayment(PayPayment payment) {
		this.payment = payment;
	}


	public void setTenpayQueryService(PaymentQueryService tenpayQueryService) {
		this.tenpayQueryService = tenpayQueryService;
	}




	public Message getMessage() {
		return message;
	}


	public void setMessage(Message message) {
		this.message = message;
	}
	
}
