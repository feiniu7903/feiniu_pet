package com.lvmama.pet.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bocom.netpay.b2cAPI.BOCOMB2CClient;
import com.bocom.netpay.b2cAPI.BOCOMB2COPReply;
import com.bocom.netpay.b2cAPI.BOCOMSetting;
import com.bocom.netpay.b2cAPI.NetSignServer;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MD5ToGBK;
import com.lvmama.comm.vo.PaymentConstant;

public class COMMUtil {
	
	private static final Logger LOG = Logger.getLogger(COMMUtil.class);
	
	/**
	 * 订单金额转化为12位的格式,前面加" ",最后两个是小数.
	 * @param payAmount
	 * @return
	 */
	public static  String formartAmount(Long payAmount) {
        int a=12-payAmount.toString().length();
        String stra = payAmount.toString();
		for(int i=0;i<a;i++){
			stra=" "+stra;
		}
		return stra;
	}
	
	public static String verify(final String sourceMsg) {
		doCOMMClient();
		NetSignServer nss = new NetSignServer(); // 初始化签名库
		try {
			nss.NSSetPlainText(sourceMsg.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			LOG.error("包装数据进行签名是出错");
			e.printStackTrace();
		}
		String merchantDN = BOCOMSetting.MerchantCertDN;
		byte[] bSignMsg = nss.NSDetachedSign(merchantDN);
		String signMsg = "";
		try {
			signMsg = new String(bSignMsg, "GBK");
		} catch (UnsupportedEncodingException e) {
			LOG.error("包装数据进行验签是出错");
			e.printStackTrace();
		}
		return signMsg;
	}
	
	private static BOCOMB2CClient doCOMMClient(){
		BOCOMB2CClient client = new BOCOMB2CClient();
		int ret = client.initialize(PaymentConstant.getInstance().getProperty("COMM_CONFIG_PATH"));
		if (ret != 0){  
			//初始化失败
			LOG.error("初始化失败,错误信息："+client.getLastErr());
			return null;
		}
		return client;
	}
	/**
	 * 下载交易数据
	 * 
	 * @author: ranlongfei 2012-6-29 下午03:31:47
	 * @param sParaTemp
	 * @return
	 * @throws Exception
	 */
	public static String downLoadSettlement(String settleDate) throws Exception {
		BOCOMB2CClient client = doCOMMClient();
		if(client == null) {
			return null;
		}
		BOCOMB2COPReply rep = client.downLoadSettlement(settleDate);//对帐单下载
		if (rep == null || !"0".equals(rep.getRetCode())){  
			LOG.error("接口错误信息："+ client.getLastErr());
			return null;
		}
		return rep.getOpResult().getValueByName("settlementFile");
	}
	
	/**
	 * 生成签名窜，参数为空不参与(MD5ToGBK，MD5签名用UTF-8编码)
	 * @param resultMap
	 * @param gatewayKey
	 * @return
	 */
	public static String getSignature(Map<String, String> resultMap,String gatewayKey) {
		List<String> keys = new ArrayList<String>(resultMap.keySet());
        Collections.sort(keys);//对参数升序排序，已确保拼接的签名顺序正确
        StringBuffer prestr = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = resultMap.get(key);
			if (!"sign".equals(key) && value != null && StringUtils.isNotBlank(value)) {
				if (first) {
					prestr.append(key + "=" + value);
					first = false;
				} else {
					prestr.append("&" + key + "=" + value);
				}
			}
		}
		String mdsString = prestr.append("&key="+gatewayKey).toString();
		LOG.info("getSignature MD5 string："+ mdsString);
		 System.out.println("mdsString="+mdsString);
		return MD5.md5(mdsString).toUpperCase();
	}
	
	/**
	 * 生成签名窜，参数为空不参与(MD5ToGBK，MD5签名用GBK编码)
	 * @param resultMap
	 * @param tenpayKey
	 * @return
	 */
	public static String getGBKSignature(Map<String, String> resultMap,String gatewayKey) {
		List<String> keys = new ArrayList<String>(resultMap.keySet());
        Collections.sort(keys);//对参数升序排序，已确保拼接的签名顺序正确
        StringBuffer prestr = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = resultMap.get(key);
			if (!"sign".equals(key) && value != null && StringUtils.isNotBlank(value)) {
				if (first) {
					prestr.append(key + "=" + value);
					first = false;
				} else {
					prestr.append("&" + key + "=" + value);
				}
			}
		}
		String mdsString = prestr.append("&key="+gatewayKey).toString();
		LOG.info("getGBKSignature MD5 string："+ mdsString);
		return MD5ToGBK.md5Digest(mdsString).toUpperCase();
	}
	
	
	/**
	 * 生成签名窜，参数为空也参与
	 * @param resultMap
	 * @param tenpayKey
	 * @return
	 */
	public static String getSignatureTwo(Map<String, String> resultMap,String gatewayKey) {
		List<String> keys = new ArrayList<String>(resultMap.keySet());
        Collections.sort(keys);//对参数升序排序，已确保拼接的签名顺序正确
        StringBuffer prestr = new StringBuffer();
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = resultMap.get(key);
			if (!"sign".equals(key)) {
				if (first) {
					prestr.append(key + "=" + StringUtils.trimToEmpty(value));
					first = false;
				} else {
					prestr.append("&" + key + "=" + StringUtils.trimToEmpty(value));
				}
			}
		}
		String mdsString = prestr.append("&key="+gatewayKey).toString();
		LOG.info("getSignatureTwo MD5 string："+ mdsString);
		return MD5.md5(mdsString).toUpperCase();
	}
	
	
	public static void main(String args[]){
		try {
			String tt = new String("【浙江、江苏大学生特".getBytes("UTF-8"),"GBK");
			String ww = new String("半价".getBytes("UTF-8"),"GBK");
			String md1 = "currency=1&expire_time=20140331190255&goods_desc="+ww+"&goods_name="+tt+"&goods_url=http://m.lvmama.com/clutter/order/order_detail.htm?orderId=3394824&input_charset=1&order_create_time=20140331170255&order_no=03311703420503394824&pay_type=2&return_url=http://180.169.51.94:8244/payment/pay/baidupayWap_notify.do&service_code=1&sign_method=1&sp_no=9000100005&total_amount=12000&version=2&key=EPY8umfs9f7sJhtcv2tdLYjK9xHX3BsF";
			System.out.println(md1);
			System.out.println(MD5.md5(md1));
			System.out.println(MD5ToGBK.md5Digest(md1));
			System.out.println("----------------------");
			
			String md2 = "currency=1&expire_time=20140331190255&goods_desc=半价&goods_name=【浙江、江苏大学生特&goods_url=http://m.lvmama.com/clutter/order/order_detail.htm?orderId=3394824&input_charset=1&order_create_time=20140331170255&order_no=03311703420503394824&pay_type=2&return_url=http://180.169.51.94:8244/payment/pay/baidupayWap_notify.do&service_code=1&sign_method=1&sp_no=9000100005&total_amount=12000&version=2&key=EPY8umfs9f7sJhtcv2tdLYjK9xHX3BsF";
			System.out.println(md2);
			System.out.println(MD5.md5(md2));
			System.out.println(MD5ToGBK.md5Digest(md2));
			System.out.println("----------------------");
			
			String dd = URLEncoder.encode("【浙江、江苏大学生特","GBK");
			String df = URLEncoder.encode("半价","GBK");
			String md4 = "currency=1&expire_time=20140331190255&goods_desc="+df+"&goods_name="+dd+"&goods_url=http://m.lvmama.com/clutter/order/order_detail.htm?orderId=3394824&input_charset=1&order_create_time=20140331170255&order_no=03311703420503394824&pay_type=2&return_url=http://180.169.51.94:8244/payment/pay/baidupayWap_notify.do&service_code=1&sign_method=1&sp_no=9000100005&total_amount=12000&version=2&key=EPY8umfs9f7sJhtcv2tdLYjK9xHX3BsF";
			System.out.println(md4);
			System.out.println(MD5.md5(md4));
			System.out.println(MD5ToGBK.md5Digest(md4));
			System.out.println("----------------------");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
