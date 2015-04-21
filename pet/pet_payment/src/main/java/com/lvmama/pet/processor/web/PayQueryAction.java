package com.lvmama.pet.processor.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.pet.service.pay.PaymentQueryService;
import com.lvmama.comm.utils.StringUtil;

/**
 * 查询类的action入口
 * 所有支付机构的查询都在此写，通过页面查询
 */
@Results({ @Result(name = "success", type = "plainText") })
public class PayQueryAction extends BaseAction {
	private static final long serialVersionUID = 7808002094921703955L;
	private PaymentQueryService baiduQueryService;
	private PaymentQueryService tenpayPhoneQueryService;
	private PaymentQueryService unionpayPaymentQueryService;
	private PaymentQueryService unionpayPrePaymentQueryService;
	private PaymentQueryService upompQueryService;
	
	
	private PayPayment payment;

	@Action(value = "/query/baidu")
	public String queryBaidu() {
		PaymentQueryReturnInfo info = baiduQueryService
				.paymentStateQuery(payment);
		PrintWriter print = null;
		try {
			print = getPrint();
			print.write(StringUtil.printParam(info));
			print.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (print != null) {
				print.close();
			}
		}
		return SUCCESS;
	}
	
	@Action(value = "/query/tenpayphone")
	public String querytenpayphone() {
		PaymentQueryReturnInfo info = tenpayPhoneQueryService
				.paymentStateQuery(payment);
		PrintWriter print = null;
		try {
			print = getPrint();
			print.write(StringUtil.printParam(info));
			print.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (print != null) {
				print.close();
			}
		}
		return SUCCESS;
	}
	@Action(value = "/query/chinapay")
	public String chinapay() {
		PaymentQueryReturnInfo info = unionpayPaymentQueryService
				.paymentStateQuery(payment);
		PrintWriter print = null;
		try {
			print = getPrint();
			print.write(StringUtil.printParam(info));
			print.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (print != null) {
				print.close();
			}
		}
		return SUCCESS;
	}
	
	@Action(value = "/query/upomp")
	public String upomp() {
		PaymentQueryReturnInfo info = upompQueryService
				.paymentStateQuery(payment);
		PrintWriter print = null;
		try {
			print = getPrint();
			print.write(StringUtil.printParam(info));
			print.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (print != null) {
				print.close();
			}
		}
		return SUCCESS;
	}
	
	private PrintWriter getPrint() throws IOException {
		HttpServletResponse res = ServletActionContext.getResponse();
		PrintWriter printer = null;
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");
		printer = res.getWriter();
		return printer;
	}

	public PayPayment getPayment() {
		return payment;
	}

	public void setPayment(PayPayment payment) {
		this.payment = payment;
	}

	public PaymentQueryService getBaiduQueryService() {
		return baiduQueryService;
	}

	public void setBaiduQueryService(PaymentQueryService baiduQueryService) {
		this.baiduQueryService = baiduQueryService;
	}

	public PaymentQueryService getTenpayPhoneQueryService() {
		return tenpayPhoneQueryService;
	}

	public void setTenpayPhoneQueryService(
			PaymentQueryService tenpayPhoneQueryService) {
		this.tenpayPhoneQueryService = tenpayPhoneQueryService;
	}

	public PaymentQueryService getUnionpayPaymentQueryService() {
		return unionpayPaymentQueryService;
	}

	public void setUnionpayPaymentQueryService(
			PaymentQueryService unionpayPaymentQueryService) {
		this.unionpayPaymentQueryService = unionpayPaymentQueryService;
	}

	public PaymentQueryService getUnionpayPrePaymentQueryService() {
		return unionpayPrePaymentQueryService;
	}

	public void setUnionpayPrePaymentQueryService(
			PaymentQueryService unionpayPrePaymentQueryService) {
		this.unionpayPrePaymentQueryService = unionpayPrePaymentQueryService;
	}

	public PaymentQueryService getUpompQueryService() {
		return upompQueryService;
	}

	public void setUpompQueryService(PaymentQueryService upompQueryService) {
		this.upompQueryService = upompQueryService;
	}

}
