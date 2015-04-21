package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.BOCInstalmentPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 中国银行网上分期支付.
 * @author sunruyi
 */
@Result(name = ActionSupport.SUCCESS,location = "/WEB-INF/pages/forms/bocInstalment.ftl",type="freemarker")
public class BOCInstalmentAction extends PayAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7461648010342966228L;
	/**
	 * 中国银行执行form表单提交.
	 */
	@Action("/pay/bocInstalment")
	public String execute(){
		if (payment()) {
			return SUCCESS;
		} else {
			return "errorno";
		}
	}
	/**
	 * 获取支付网关名称.
	 * @return 支付网关名称
	 */
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.BOC_INSTALMENT.name();
	}
	
	@Override
	PostData getPostData(PayPayment payPayment) {
		String customerIP = InternetProtocol.getRemoteAddr(super.getRequest());
		BOCInstalmentPostData bocInstalmentPostData = new BOCInstalmentPostData(payPayment,super.getInstalmentNum(),customerIP,super.getBeforeURL());
		return bocInstalmentPostData;
	}
	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
}
