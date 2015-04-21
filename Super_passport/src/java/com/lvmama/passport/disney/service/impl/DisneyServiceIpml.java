package com.lvmama.passport.disney.service.impl;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.vo.EmailAttachmentData;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.passport.disney.model.MailBean;
import com.lvmama.passport.disney.model.OrderRespose;
import com.lvmama.passport.disney.service.DisneyService;
import com.lvmama.passport.utils.WebServiceConstant;

public class DisneyServiceIpml implements DisneyService {
	private static Log log = LogFactory.getLog(DisneyServiceIpml.class);
	private EmailClient emailClient;

	@Override
	public void sendMail(MailBean bean, List<EmailAttachmentData> files) {
		try {
			String content = "<html><p>感谢您在驴妈妈网订购香港迪士尼乐园产品。</p> <p>请参考附件之正式换领凭证。宾客须打印换领凭证方可於香港迪士尼乐园领取所订购的产品(换领凭证上的条码须清晰及完整)。宾客须持有有效及附有照片之身份证明，以供查阅。如换领凭证之号码重复将视为作废。</p><p>感谢您一直以来您的支持和信任!</p><p>祝您有开心奇妙的一天!</p></html>";
			EmailContent email = new EmailContent();
			email.setFromName("驴妈妈旅游网");
			email.setToAddress(bean.getToAddress());
			email.setSubject("香港迪士尼产品订单确认信（" + bean.getVoucherNo() + "/"
					+ bean.getReservationNo() + "/" + bean.getNum() + "张)");
			email.setContentText(content);
			email.setCreateTime(new java.util.Date());
			emailClient.sendEmailDirect(email, files);
			;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("replace email content error!");
		}
	}

	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}

	@Override
	public OrderRespose getOrderStatus(String reservationNo) throws Exception {
		String agentId = WebServiceConstant.getProperties("disney.agentId");
		String requestTime = DateFormatUtils.format(new Date(),
				"yyyy-MM-dd HH:mm:ss");
		String message = "{\"agentId\" : \"" + agentId
				+ "\",\"requestTime\" : \"" + requestTime
				+ "\",\"reservationNo\" : \"" + reservationNo + "\"}";
		System.out.println(message);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("message", message);
		paramMap.put("signature", makeSign(message));
		String jsonResult = HttpsUtil.requestPostForm(
				WebServiceConstant.getProperties("disney.url")
						+ "/OTA/GetOrderStatus", paramMap);
		log.info("Disney GetOrderStatus http post response" + jsonResult);
		return parseOrderStatusResponse(jsonResult);
	}

	public OrderRespose parseOrderStatusResponse(String response)
			throws Exception {
		JSONObject orderdetail = new JSONObject(response);
		OrderRespose res = new OrderRespose();
		String responseCode = orderdetail.getString("responseCode");
		res.setResponseCode(responseCode);
		if (StringUtils.equals(responseCode, "0000")) {
			res.setStatus(orderdetail.getString("status"));
		}
		return res;

	}

	private String makeSign(String message) throws Exception {
		String key = WebServiceConstant.getProperties("disney.key");
		String text = message + "||" + key;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(text.getBytes("UTF-8"));
		String sign = Hex.encodeHexString(hash);
		return sign;
	}

}
