package com.lvmama.passport.processor.impl.client.watercube;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.passport.processor.impl.client.watercube.model.Response;
import com.lvmama.passport.utils.WebServiceConstant;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 水魔方相关工具类
 * @author lipengcheng
 *
 */
public class WaterCubeUtil {
	private static Log LOG = LogFactory.getLog(WaterCubeUtil.class);
	/**
	 * 请求类型
	 * @author lipengcheng
	 *
	 */
	public static enum REQUESET_TYPE {
		/** 出票 */
		add_order,
		/** 查询订单 */
		query_order,
		/** 取消订单 */
		cancel_order,
		/** 重发电子票 */
		repeat_order,
		/** 转发电子票 */
		sendto_order
	} 
	
	/**
	 * 生成流水号
	 * @return
	 */
	public static String getReqSeq(){
		StringBuilder reqSeq = new StringBuilder();
		reqSeq.append(WebServiceConstant.getProperties("watercube.organization"));
		reqSeq.append(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		reqSeq.append(getRandomCharNum(6));
		return reqSeq.toString();
	}

	/**
	 * 获取六位随机数
	 * @param length
	 * @return
	 */
	private static String getRandomCharNum(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(10)]);
		}
		return buffer.toString();
	}
	
	/**
	 * 构造XML元素
	 * @param tag
	 * @param value
	 * @return
	 */
	public static String buildXmlElement(String tag, String value) {
		StringBuilder str = new StringBuilder();
		str.append("<").append(tag).append(">").append(value).append("</").append(tag).append(">");
		return str.toString();
	}
	
	public DES getDes(String strKey){
		DES des = null;
		try{
			des = new DES(strKey);
		}catch (Exception e){
			LOG.error("创建des错误"+e);
		}
		return des;
	}
	
	/**
	 * 加密
	 * @param te（明文）
	 * @return 密文
	 */
	public static String encrypt(String content){
		String result = null;
		try{
			DES des = new DES(WebServiceConstant.getProperties("watercube.deskey"));
			content = URLEncoder.encode(content, "utf-8");
			result = des.encrypt(content);
		}catch (Exception e){
			LOG.error("encrypt error:",e);
		}
		return result;
	}
	
	/**
	 * 解密
	 * @param te  密文
	 * @return 明文
	 * @throws UnsupportedEncodingException 
	 */
	public static String decrypt(String content) {
		String result = null;
		try {
			DES des = new DES(WebServiceConstant.getProperties("watercube.deskey"));
			result = des.decrypt(content);
			result = URLDecoder.decode(result, "utf-8");
		} catch (Exception e) {
			LOG.error("decrypt error:", e);
		}
		LOG.info("responseXml: " + result);
		return result;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		String str = "3e3ea550f9330c06c7f4e8c4c49a9b32f6c24086d91817cb57f31b5226efb1fa668cdd4b07300221ebef21cb5613ba97c15944d4db3cdcbe88b39c1b8ba1add3e790d5f4e3a8f71da1692805cdca635b573568d0756674d2637023a1ffb2d370ad1cbb95c6d2a1aec62377cf18a3ed1a7d34caaee3160408675229b9bdebc6c38c7445676352cf1ec79ae4c32e26c2b98c7445676352cf1e274eaa4a2acae38e571a6a3194f7f7045be6facbb5c99a8d5dbd9d59fd92791338f5c3a6d254a1d915b94eb332040eb1bd30192a0b908b8dc95fe80772f69352b57572f6a05ce6220c7eecb0d57d9609b969be2191832214eff2441903c2c8b5bdb9e49c6b336b4d844fa116a4eb531ded56c2ba22d723b02ae710b1a71cdaf9fef954959ffb7f9af95ee583176e1a23b6c79a3d387da321bcc446775edf607859b5b6bf90f047a6b26cc9185ed63043ff31fe95e678bf1db3e078eb6f5c6a0ce790d5f4e3a8f71de00c170ccf7981c2";
//		System.out.println(decrypt(str));
	/*	String request = "e1866a0504281238bd1220c6c0413ebb9319fb22337c930c9473e26cc573acee257511765ad8b49676fd463c2114f86209449b9851d6b60cffbbcc04147c5dc06785f601eba35b5728bcaf64e23e04fbffbbcc04147c5dc04b795b526641134e4d57184b6ad3ba9d4f0f7ebd05943d08d7dfa98694dc8597e669183190884a46254bf49d5007ac27f4e96510ffb71fe8c433b88b98f5a29b28528c49b1de9e7f84f2988cd4e4ef85c0526156244bf1bba64787b6249fb774e0f3dee9f4c686a39d8ae865058a5bf41be800ff14a9c32f46ba13f90662ba342a004ec170f1f2f4b811cf77a478290459e4d62fa2f3680a3e71aa75ac2e305f9cfcf812f8556c0a93d6916e9c9df3378daf686149f5e32339e3f2ee1d830c92d156b43e9af75c1d3fb13abae7cc866273b79eb8470093baccfb5190cdb72e0787636da6f0d857f63d6bfd32258e331d2fc34018b0a0fbe079ecb414d1f197b3d3c11feca0a857f993dce190ad317d2ff9042ce0e9a63932a08ee3b1394a8964a02102047927d82fe950cd3636337f3ef11c7d127d3619d5620d3592b89b8a88aacafb2f9285f3e3";
			//System.out.println(ds.encrypt("beijingyangguangtaobaojyw", "bjlyw2012"));
			String xml = decrypt(request);
			//String xml = ds.decrypt("1a26324b9fff9c8764e2fd6b33e462dfe2cf0100a5d0ed4f5294ad07444a90034aabe0a576e813480551d3888576160d37239c2213b506ecf0e01d1e6003fff583150081814fd87129238d90f127e7d80bd265a5c7f88d2d1d2c5cf5ddf750c145a579ae1f8c7e0b0bd265a5c7f88d2d3ff2e8164924dccd4a1649d332156d3d43c77300eabe8fe99c1c054c1521a8f3128eecf354257c25bc9ea1865347e7139f6a6b3e782ab66b674eb5396b59e9075b22c96a85a8b32fb026171daaaee9f5083a119defdee5bc0f924d77bc8991045f6709853197b34ebc2bb4a59db67a7fd814be0e5c9d4c18510d66c2a247cc8cbdcc315bd83a9dc614986f4713e4efe5baf16307d46471b78886224cf17f3c4286f1f60d23ede710cd7108a6dbb472980989de8fa399087f59d59bc5545ad07a4d1d07c36a10fe11a25ac1ba83cc8aa8b70e42af452db4eee66c130cc64d18cd0c59972b2a7aca5f241482f7f77a3577bae95021ab92e0cc610971eb68720ea5b4f32e18e3b3102d170054563faa29066209b191913046f6ee303f5a0f03ad1e2952a5c8652745705e44637ab66eb26ec8454c38efdd74bd2976aa91a327d400996a3bc0fae74e441c21f38cb5c65a883cd56eafb531f5909847f07781e54f03ebb8a72e8f740b8072bb59e7747fdcfaf4b419cc0aa8f8a195d265e75ea882c02a7583577de91e173ebe4fdac9769074620fd27af7cbcaf0367c67e9f03f8b5e4275eee37e514ca0165e72824494f4f546b5ea7a26eac6469a79a1761ca0a941f56b8beb5caa8d0eebb8a72e8f740b80b644c6aaf5e4753530265b82464fbee26f554331c748f678f5d2f5db86e73223d1ed211350edd3c21ba3207e0a45f857dce6a094a948e6bf0f8ba68f5a7d6989813f609c432ec8c7cf876a0c195a1675c50ba67091b5822b4f341815e780f8438f1f7e3b12406982", "vSK5eIRC");
			System.out.println(xml);
			try {
				
//				String decXml = URLDecoder.decode(xml,"UTF-8");
				System.out.println("---client receive decXml:---"+decXml);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		
		
//		String request = "<?xml version=\"1.0\" encoding=\"utf-8\"?><business_trans><request_type>add_order</request_type><organization>1000004232</organization><password>KslTjXk33E</password><req_seq>100000423220120702203145892134</req_seq><order><product_num>15000000411</product_num><num>1</num><mobile>18221076042</mobile><use_date>2012-07-05</use_date><real_name_type>1</real_name_type><real_name>李朋成</real_name></order></business_trans>";
//		String str = encrypt(request);
//		String str = "be273aa97b62c2befc6944bad9cb00f7e00b54676dbd1136cffecb8beeab7eaa19c10ebfd929c2a6308f14d69e257e2f761777fef3486d7e471b336d7342db8a9e23c36360c88fa32b646e06357f74f4fb3c68663871f510917909457200f1624d2a89d2e033c3ac4cd9aac3ff84e0d4ffbbcc04147c5dc036f430f1704aa0c6f6a6aa27704ab6fe5ccbf3c035f8062126be70455934e107680c21be872dc0ff2ebe5b7be95516d08ac374074c601a85d7fd10721a377ab4d60282f39739454f9347f6863d3a91142526aa0d767cd1f77c7314d800141ef6b466babbf2774a09d9cb0e25758c1bf53bd684305fd83461ed2a2eb344f7ffdfe4cd53d736c09998961a3defbda2736bc1092ea01a9e154209b995c53b898ec37100f62ab5a33d24e960ae14478bc447260eb93d280847c759e4d62fa2f3680a11e9c974735fa3936c4be6572f97455b6412e91346d9c17931352d1f02ab3116f559105ae56620f7ad228a55bff8193028f15878a766df99fecce2deb1f3821a75e17a90357ea369c97b749f822e697a0d848dd4c5fabd09e6c8a6bda99bf93496341d5ae216a8793be3bebfa48aa3cc4fb32ff87e5128af630a66cdab743d947d1725dd84a627a35ae502a16fc20f39e9077e83dd90cf207d1725dd84a627a37c2a5c57deaa2c235c8e161af918a089aa4b29d17ae498415193796991d12ea8f2fc394149199c6101b6ae86ba6f55a1dd4c048776bb3b6a0e6a232b9941815411f8c2b66c93926d7463e363f0c0b3a79be13bcf7fdfdd8976fd463c2114f862aa2754224880b4fae02e13f13967fd2d";
		String str = "3e3ea550f9330c06c7f4e8c4c49a9b32f6c24086d91817cb57f31b5226efb1fa668cdd4b07300221ebef21cb5613ba97c15944d4db3cdcbe88b39c1b8ba1add3e790d5f4e3a8f71da1692805cdca635b573568d0756674d2637023a1ffb2d370ad1cbb95c6d2a1aec62377cf18a3ed1a7d34caaee3160408675229b9bdebc6c38c7445676352cf1ec79ae4c32e26c2b98c7445676352cf1e274eaa4a2acae38e571a6a3194f7f7045be6facbb5c99a8d5dbd9d59fd92791338f5c3a6d254a1d915b94eb332040eb1bd30192a0b908b8dc95fe80772f6935273865c94cf2b76b6985f999741892201dbe1054b7694fc96f02c604744ab906ea420c0895c1d587c323c560f6345ab15588bcc62651b2ba42d867b231963c9158dcefa2fcf5ba36cd4125b1dfa807e6c14055593eb6160d6c45119030336fdea3ee0328a88b4040c";
		String result = decrypt(str);
		System.out.println(result);
		
	}
	
	/**
	 * 解析提交订单返回的报文
	 * @param responseXml
	 * @return
	 */
	public static Response getAddOrderResponse(String responseXml){
		XStream xStream = getCommonStream();
		xStream.aliasField("order_num", com.lvmama.passport.processor.impl.client.watercube.model.Order.class,"orderNum");
		xStream.aliasField("code", com.lvmama.passport.processor.impl.client.watercube.model.Order.class,"code");
		
		return (Response)xStream.fromXML(decrypt(responseXml));
	}
	
	/**
	 * 解析查询订单返回的报文
	 * @param responseXml
	 * @return
	 */
	public static Response getQueryOrderResponse(String responseXml){
		XStream xStream = getCommonStream();
		xStream.aliasField("order_num", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "orderNum");
		xStream.aliasField("product_name", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "productName");
		xStream.aliasField("product_type", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "productType");
		xStream.aliasField("product_num", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "productNum");
		xStream.aliasField("phone_rev", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "phoneRev");
		xStream.aliasField("buy_num", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "num");
		xStream.aliasField("spare_num", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "spareNum");
		xStream.aliasField("use_num", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "useNum");
		xStream.aliasField("start_validity_date", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "startValidityDate");
		xStream.aliasField("end_validity_date", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "endValidityDate");
		xStream.aliasField("end_validity_date", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "endValidityDate");
		xStream.aliasField("add_time", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "addTime");
		xStream.aliasField("real_name_type", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "realNameType");
		xStream.aliasField("real_name", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "realName");
		xStream.aliasField("status", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "status");
		
		return (Response)xStream.fromXML(decrypt(responseXml));
	}
	
	/**
	 * 解析取消订单返回的报文
	 * @param responseXml
	 * @return
	 */
	public static Response getCancelOrderResponse(String responseXml){
		XStream xStream = getCommonStream();
		xStream.aliasField("order_num", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "orderNum");
		xStream.aliasField("num", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "num");
		
		return (Response)xStream.fromXML(decrypt(responseXml));
	}
	
	/**
	 * 解析取消订单返回的报文
	 * @param responseXml
	 * @return
	 */
	public static Response getRepeatOrderResponse(String responseXml){
		XStream xStream = getCommonStream();
		xStream.aliasField("order_num", com.lvmama.passport.processor.impl.client.watercube.model.Order.class, "orderNum");
		
		return (Response)xStream.fromXML(decrypt(responseXml));
	}
	
	/**
	 * 构造基础映射
	 * @return
	 */
	private static XStream getCommonStream(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("business_trans", Response.class);
		xStream.aliasField("response_type", com.lvmama.passport.processor.impl.client.watercube.model.Response.class,"responseType");
		xStream.aliasField("req_seq", com.lvmama.passport.processor.impl.client.watercube.model.Response.class,"reqSeq");
		xStream.aliasField("result", com.lvmama.passport.processor.impl.client.watercube.model.Response.class,"result");
		xStream.aliasField("id", com.lvmama.passport.processor.impl.client.watercube.model.Result.class, "id");
		xStream.aliasField("comment", com.lvmama.passport.processor.impl.client.watercube.model.Result.class, "comment");
		xStream.aliasField("order", com.lvmama.passport.processor.impl.client.watercube.model.Response.class,"order");
		return xStream;
	}
	
}
