package test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;

import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.utils.TemplateUtils.XMLEncoder;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;
import com.lvmama.passport.yiyou.model.ApplyRefundBean;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class YiyouTest {
	private static String baseTemplateDir = "/com/lvmama/passport/yiyou/template";
	private static String url = WebServiceConstant.getProperties("yiyou.url");
	private static String key = WebServiceConstant.getProperties("yiyou.key");
	private static String partnerCode = WebServiceConstant.getProperties("yiyou.partnerCode");
	
	public static void main(String[] args) throws Exception {
		ApplyRefundBean applyRefundBean = new ApplyRefundBean();
		applyRefundBean.setTimeStamp("20121220121044000");
		applyRefundBean.setOrderId("183");
		applyRefundBean.setOrderNo("20121220102343546220");
		applyRefundBean.setSequenceId("20121220121044000");
		applyRefundBean.setPartnerCode(partnerCode);
		//applyRefundBean.setRefundReason("abc");
		
		applyRefundBean.setSigned(getBASE64Encoder(getMD5String(
			applyRefundBean.getTimeStamp() 
			+ applyRefundBean.getOrderId() 
			+ applyRefundBean.getOrderNo() 
			//+ applyRefundBean.getRefundReason() 
			+ applyRefundBean.getSequenceId() 
			+ applyRefundBean.getPartnerCode()
		)));
		
		String req1 = TemplateUtils.fillFileTemplate(baseTemplateDir, "applyRefundReq.xml", applyRefundBean);
		String req2 = TemplateUtils.encodeStringTemplate(req1, "Body", true, new XMLEncoder() {
			@Override
			public String encode(String data) throws Exception {
				//BASE64+3DES加密
				Key deskey = null;
		        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
		        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		        deskey = keyfactory.generateSecret(spec);
		        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		        cipher.init(Cipher.ENCRYPT_MODE, deskey);
		        byte[] bOut = cipher.doFinal(data.getBytes("UTF-8"));
		        return Base64.encodeBase64String(bOut);
			}
		});
		String res = WebServiceClient.call(url, new Object[]{req2}, "applyRefund");
		System.out.println("req1=" + req1);
		System.out.println("req2=" + req2);
		System.out.println("res=" + res);
		
		/*ResendSmsBean resendSmsBean = new ResendSmsBean();
		resendSmsBean.setTimeStamp("20121219195228859");
		resendSmsBean.setOrderNo("20121219203423439969");
		resendSmsBean.setSequenceId("20121220101122297");
		resendSmsBean.setPartnerCode(partnerCode);
		
		resendSmsBean.setSigned(getBASE64Encoder(getMD5String(
			resendSmsBean.getTimeStamp() 
			+ resendSmsBean.getOrderId() 
			+ resendSmsBean.getOrderNo() 
			+ resendSmsBean.getSequenceId() 
			+ resendSmsBean.getPartnerCode()
		)));
		
		String req1 = TemplateUtils.fillFileTemplate(baseTemplateDir, "resendSmsReq.xml", resendSmsBean);
		String req2 = TemplateUtils.encodeStringTemplate(req1, "Body", true, new XMLEncoder() {
			@Override
			public String encode(String data) throws Exception {
				//BASE64+3DES加密
				Key deskey = null;
		        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
		        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		        deskey = keyfactory.generateSecret(spec);
		        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		        cipher.init(Cipher.ENCRYPT_MODE, deskey);
		        byte[] bOut = cipher.doFinal(data.getBytes("UTF-8"));
		        return Base64.encodeBase64String(bOut);
			}
		});
		String res = WebServiceClient.call(url, new Object[]{req2}, "resendSms");
		System.out.println("req1=" + req1);
		System.out.println("req2=" + req2);
		System.out.println("res=" + res);*/
		
		/*SubmitOrderBean submitOrderBean = new SubmitOrderBean();
		submitOrderBean.setTimeStamp("20121219092102781");
		submitOrderBean.setPiaoId("26");
		submitOrderBean.setBuyNum("1");
		submitOrderBean.setMobile("13916122222");
		submitOrderBean.setPartnerOrderNo("12345678");
		submitOrderBean.setSequenceId(submitOrderBean.getTimeStamp());
		submitOrderBean.setPartnerCode(partnerCode);
		submitOrderBean.setSigned(getBASE64Encoder(getMD5String(
			submitOrderBean.getTimeStamp() 
			+ submitOrderBean.getPiaoId()
			+ submitOrderBean.getBuyNum() 
			+ submitOrderBean.getMobile()
			+ submitOrderBean.getPartnerOrderNo() 
			+ submitOrderBean.getSequenceId() 
			+ submitOrderBean.getPartnerCode()
		)));
		
		String req1 = TemplateUtils.fillFileTemplate(baseTemplateDir, "submitOrderReq.xml", submitOrderBean);
		String req2 = TemplateUtils.encodeStringTemplate(req1, "Body", true, new XMLEncoder() {
			@Override
			public String encode(String data) throws Exception {
				//BASE64+3DES加密
				Key deskey = null;
		        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
		        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		        deskey = keyfactory.generateSecret(spec);
		        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
		        cipher.init(Cipher.ENCRYPT_MODE, deskey);
		        byte[] bOut = cipher.doFinal(data.getBytes("UTF-8"));
		        return Base64.encodeBase64String(bOut);
			}
		});
		String res = WebServiceClient.call(url, new Object[]{req2}, "submitOrder");
		System.out.println("req1=" + req1);
		System.out.println("req2=" + req2);
		System.out.println("res=" + res);*/
	}

	public static String ees3DecodeECB(String data) throws Exception {
    	BASE64Decoder dec = new BASE64Decoder();
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(dec.decodeBuffer(data));
        return new String(bOut, "UTF-8");
	}
	
	public static String fillFileTemplate(String templateDir, String templateFilename, Object model) throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		cfg.setTemplateLoader(new ClassTemplateLoader(TemplateUtils.class, templateDir));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setClassicCompatible(true);
		cfg.setNumberFormat("#");
		Template t = cfg.getTemplate(templateFilename);
		StringWriter writer = new StringWriter();
		t.process(model, writer);
		return writer.toString();
	}

    public static byte[] getMD5String(String str){
    	byte [] MD5Message = null; 
		try {
			MD5Message =  MessageDigest.getInstance("MD5".toUpperCase()).digest(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return MD5Message;
	}
    
    public static String getBASE64Encoder(byte [] data){
    	return new sun.misc.BASE64Encoder().encode(data);
    }
}
