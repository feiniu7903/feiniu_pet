package com.lvmama.pet.payment.post.web;

import java.io.PrintWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.payment.post.data.UpompPostData;
import com.merPlus.OrdersBean;
import com.merPlus.PlusTools;

/**
 * 移动客户端支付.
 * 
 * @author fengyu
 * @see  com.lvmama.comm.pet.po.pay.PayPayment
 * @see  com.lvmama.comm.vo.Constant
 * @see  com.lvmama.pet.payment.post.data.PostData
 * @see  com.lvmama.pet.payment.post.data.UpompPostData
 * @see  com.merPlus.OrdersBean
 * @see  com.merPlus.PlusTools
 */
public class UpompAction extends PayAction {
	private static final long serialVersionUID = -5259429608500937183L;
	private Logger LOG = Logger.getLogger(this.getClass());
	
	@Action("/pay/upompPay")
	public void upompXml() {
		if (payment()) {
			UpompPostData upd = (UpompPostData) this.payInfo;
			Map<String, String> keyMap = PlusTools.getCertKey(
					upd.getMerchantPfxCertPath(), upd.getKeyPassWord(),
					upd.getMerchantPublicCerKeyPath());

			try {
				String mechantName = upd.getMerchantName();
				String merchantId = upd.getMerchantId();
				String metchantOrderId = upd.getMerchantOrderId();
				String merchantOrderTime = upd.getMerchantOrderTime();
				String metchantOrderAmt = upd.getMerchantOrderAmt();
				String metchantOrderDesc = upd.getMerchantOrderDesc();
				String transTimeout = upd.getTransTimeout();
				String backEndUrl = upd.getBackEndUrl();
				LOG.info("mechantName:" + mechantName + " merchantId:"
						+ merchantId + " metchantOrderId:" + metchantOrderId
						+ " merchantOrderTime:" + merchantOrderTime
						+ " metchantOrderAmt:" + metchantOrderAmt
						+ " metchantOrderDesc:" + metchantOrderDesc
						+ " transTimeout:" + transTimeout + "backEndUrl:"
						+ backEndUrl);
				OrdersBean ordersBean = PlusTools.submitXml(mechantName,"","",
						merchantId, metchantOrderId, merchantOrderTime,
						upd.getMerchantOrderAmt(), metchantOrderDesc,
						transTimeout, backEndUrl, keyMap.get("publicCert"),
						keyMap.get("privateKey"));
				LOG.info("response orderId:+" + metchantOrderId + "code:"
						+ ordersBean.getRespCode() + " respDesc:"
						+ ordersBean.getRespDesc());
			} catch (Exception e) {
				e.printStackTrace();
			}

			String xml = PlusTools.payXml(upd.getMerchantId(),
					upd.getMerchantOrderId(), upd.getMerchantOrderTime(),
					keyMap.get("publicCert"), keyMap.get("privateKey"));

			this.sendXmlResult(xml);
		}
	}

	
	@Override
	PostData getPostData(PayPayment payPayment) {
		return new UpompPostData(payPayment);
	}

	/**
	 * 获取支付网关.
	 * @return
	 */
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.UPOMP.name();
	}

	private void sendXmlResult(String xml) {
		this.getResponse().setContentType("text/xml;charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.write(xml);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
}
