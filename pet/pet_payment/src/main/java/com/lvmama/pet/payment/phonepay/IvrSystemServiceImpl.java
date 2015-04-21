package com.lvmama.pet.payment.phonepay;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;

/**
 * 针对于汇付天下的WEBSERVICE实现类
 * 接收IVR传过来的有关汇付天下电话支付数据并向汇付天下发送交易报文的服务.
 * @author Alex Wang
 *
 */
public class IvrSystemServiceImpl implements IvrSystemService {
	
	private Logger LOG = Logger.getLogger(getClass());
	
	private PhonepayClient chinapnrClient;
	
	private PayPaymentService payPaymentService;
	
	public void receiveDataFromIVRSystem(String xmlRequest) {
		/** ******LVCC info********* */
		LOG.info("LVCC info, chinapnr:"+xmlRequest);
        CardAndOrderObject caoo = CardAndOrderObject.createInstance(xmlRequest);
        LOG.info("cardNo:"+caoo.getCard().getCardNo());
		LOG.info("idno:"+caoo.getCard().getIdNo());
		LOG.info("Cvv2:"+caoo.getCard().getCvv2());
		LOG.info("ValidDate:"+caoo.getCard().getValidDate());
         if (caoo.verifySignature()) {
            PayPayment payment = caoo.initPayPayment();
            payPaymentService.savePayment(payment);
            chinapnrClient.sendData(caoo,payment.getPaymentTradeNo());
        }
	}

	public void setChinapnrClient(PhonepayClient chinapnrClient) {
		this.chinapnrClient = chinapnrClient;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public static void main(String[] args) {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><Head><Version>1</Version><SequenceId>1</SequenceId><Signed>XFpgRFljYGJkZVhaayoZYRNlbXJziFhuGnYwKB4pOh8+KyQnMSwrKXtshoKDWVta</Signed></Head><Body>VlUWIQ9LIhUmJx0kDx4qXy1nWl4dQyYgaBkzKW5kRYWINy05Ki40mDFAR4OGPgMDGTgXEQ0kXFJRaCocZBNibXFwjVhuH3FdcFxOOX5riWl4eGwyNk07QC2EVgkPBActC1xiaFJPayEcYBNocGVyhlxsIF8uIzkeWnwwKGd4eYN8ni8wK4OCWFtaUkwVEQ4NEkgiJVYObGAhLmxmilZpIF8uOjV0ai1ngIlwbXcyKn54dTouPhMeGV1fCRYqC05QaCkdYBJoZW9vhVttH3Rdc3Z2e3pqln4tKX55hSt23PDcqKvMUkwNEQ0RXFBkOlpaFB1rNywcOhdvIB8fKCY0WXUwKSIoLCYsNnFFQC0vGCMKUF8JEQwPEyUNFB94PR9aGh0WQCoeWCVjcCgzWnwwe3l8dH40Qy0pVDSEVhwPDRcOJBEIXFRjaSoQXVMYJTIXSSghIm40ISkpPHZgb4B5gGoyKX1zf3x8XV1dRFZfSw0LGB0lFE9ZG2EUKmhkhFIraCUfcm9nTnxqYy8=</Body></Request>";
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-payment-beans.xml");
		IvrSystemService service = (IvrSystemService)context.getBean("chinapnrIvrSystem");
		service.receiveDataFromIVRSystem(str);
	}

}
