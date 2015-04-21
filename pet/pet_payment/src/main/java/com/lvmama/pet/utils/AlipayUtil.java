package com.lvmama.pet.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayCore;
import com.alipay.util.AlipayMd5Encrypt;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.PaymentConstant;

public class AlipayUtil {

    /**
	 * 生成签名窜
	 * @param params
	 * @param privateKey
	 * @return
	 */
	public static String getContent(Map<String,String> params, String privateKey) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);//对参数排序，已确保拼接的签名顺序正确
        String prestr = "";
		boolean first = true;
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			if (first) {
				prestr = prestr + key + "=" + value;
				first = false;
			} else {
				prestr = prestr + "&" + key + "=" + value;
			}
		}
        return prestr + privateKey;
    }
	
    /**
     * 构造模拟远程HTTP的POST请求，获取支付宝的返回XML处理结果
     * @param sParaTemp 请求参数数组
     * @param gateway 网关地址
     * @return 支付宝返回XML处理结果
     * @throws Exception
     */
	public static String sendPostInfo(Map<String, String> sParaTemp, String reqURL){
		//待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        String url = reqURL + "_input_charset="+AlipayConfig.input_charset;
        return HttpsUtil.requestPostForm(url, sPara, AlipayConfig.input_charset, "GBK");
	}
	
	
	
	/**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = buildMysign(sPara);
        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.sign_type);

        return sPara;
    }
    
    /**
     * 验证消息是否是支付宝发出的合法消息
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params) {
		String mysign = getMysign(params);
		String responseTxt = "true";
		if (params.get("notify_id") != null) {
			String partner = PaymentConstant.getInstance().getProperty("ALIPAY_PARTNER");
	         String veryfy_url = PaymentConstant.getInstance().getProperty("ALIPAY_REFUNDMENT_VERIFY_URL") 
	         		+ "service=notify_verify&partner=" + partner + "&notify_id=" + params.get("notify_id");
	         responseTxt = HttpsUtil.requestPostForm(veryfy_url, new HashMap<String,String>());
		}
		String sign = "";
		if (params.get("sign") != null) {
			sign = params.get("sign");
		}
		if (mysign.equals(sign) && responseTxt.equals("true")) {
			return true;
		} else {
			return false;
		}
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * @param Params 通知返回来的参数数组
     * @return 生成的签名结果
     */
    private static String getMysign(Map<String, String> Params) {
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);//过滤空值、sign与sign_type参数
        String mysign = AlipayUtil.buildMysign(sParaNew);//获得签名结果
        return mysign;
    }
    
    /**
     * 生成签名结果
     * @param sArray 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildMysign(Map<String, String> sArray) {
        String prestr = AlipayCore.createLinkString(sArray); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        prestr = prestr + PaymentConstant.getInstance().getProperty("ALIPAY_KEY"); //把拼接后的字符串再与安全校验码直接连接起来
        String mysign = AlipayMd5Encrypt.md5(prestr);
        return mysign;
    }
    
    /**
     * 构造账务明细查询接口
     * @param sParaTemp 请求参数集合
     * @return 表单提交HTML信息
     * @throws Exception 
     * @author ranlongfei
     */
    public static String accountPageQuery(Map<String, String> sParaTemp) {
    	//增加基本配置
        sParaTemp.put("service", "account.page.query");
        sParaTemp.put("partner", PaymentConstant.getInstance().getProperty("ALIPAY_PARTNER"));
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        return sendPostInfo(sParaTemp, PaymentConstant.getInstance().getProperty("ALIPAY_RECON_QUERY_URL"));
    }
    
    /**
     * 构造充值对账查询接口
     * @param sParaTemp 请求参数集合
     * @return 表单提交HTML信息
     * @author taiqichao
     */
    public static String accountPageQueryForRecharge(Map<String, String> sParaTemp) {
    	//增加基本配置
        sParaTemp.put("service", "account.page.query");
        sParaTemp.put("partner", PaymentConstant.getInstance().getProperty("ALIPAY_PARTNER_RECHARGE"));
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
       //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String prestr = AlipayCore.createLinkString(sPara)+PaymentConstant.getInstance().getProperty("ALIPAY_KEY_RECHARGE"); 
        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", AlipayMd5Encrypt.md5(prestr));
        sPara.put("sign_type", AlipayConfig.sign_type);
        String url = PaymentConstant.getInstance().getProperty("ALIPAY_RECON_QUERY_URL") + "_input_charset="+AlipayConfig.input_charset;
        return HttpsUtil.requestPostForm(url, sPara, AlipayConfig.input_charset, "GBK");
    }
    
    
    /**
     * 将Map中的数据组装成url
     * @param params
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String mapToUrl(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (isFirst) {
                sb.append(key + "=" + URLEncoder.encode(value, "utf-8"));
                isFirst = false;
            } else {
                if (value != null) {
                    sb.append("&" + key + "=" + URLEncoder.encode(value, "utf-8"));
                } else {
                    sb.append("&" + key + "=");
                }
            }
        }
        return sb.toString();
    }
    /**
     * 取得URL中的参数值。
     * <p>如不存在，返回空值。</p>
     * 
     * @param url
     * @param name
     * @return
     */
    public static String getParameter(String url, String name) {
        if (name == null || name.equals("")) {
            return null;
        }
        name = name + "=";
        int start = url.indexOf(name);
        if (start < 0) {
            return null;
        }
        start += name.length();
        int end = url.indexOf("&", start);
        if (end == -1) {
            end = url.length();
        }
        return url.substring(start, end);
    }
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @return 签名值
	*/
	public static String signRSA(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(AlipayBase64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
			signature.initSign(priKey);
			signature.update(content.getBytes("utf-8"));
			byte[] signed = signature.sign();
			return AlipayBase64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param publicKey 支付宝公钥
	* @return 布尔值
	*/
	public static boolean signRSADoCheck(String content, String sign, String publicKey)
	{
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = AlipayBase64.decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes("utf-8"));
			boolean bverify = signature.verify(AlipayBase64.decode(sign));
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args) {
		String privateKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDt5C+wWrO6OExoHA7xF187sO3FnjSxylbfnd22vbXBhNh5AMGAnAhcvleUoJnirv1s3CpbEIKhLqMW20dr22UHr4F046fOinCRIjuZSTMU/3XXFwVkNW99GD6cFqxHOJAqQ/QH360sHdgPrG9SpYHHGcbACFvkVtqgZMPRYhsYBQIDAQAB";
		String content="1";
		System.out.println(signRSA(content, privateKey));
		System.exit(0);
	}
}