package com.lvmama.pet.recon.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipInputStream;

import net.HttpClientNOSSL31;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.PaymentConstant;

public class UnionpayReconUtil {
	private static Log log = LogFactory.getLog(UnionpayReconUtil.class.getName());

	/**
	 * 请求银联对账数据
	 */
	public static String queryReconData(Date date){
		try {
			// 日期
			String fileDate = DateUtil.formatDate(date, "yyyyMMdd");
			// 文件类型
			String fileType = "06"; // 01：对账单
			// 商户类型
			String mchntTp = "0"; // 0：商户
			// 商户代码
			String mchntCd = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_MERID");
	
			// 收单机构代码
			String acqCode = "";
			// 密钥
			String key = PaymentConstant.getInstance().getProperty("CHINAPAY_PRE_KEY");
	
			// 请求地址
			String reqUrl = PaymentConstant.getInstance().getProperty("UNIONPAY_RECON_QUERY_URL");
	
			String signMethod = "MD5";
			String signature = "";
			// 组织请求报文
			TreeMap<String, Object> data = new TreeMap<String, Object>();
			
			data.put("fileType", fileType);
			data.put("fileDate", fileDate);
			data.put("mchntTp", mchntTp);
			data.put("acqCode", acqCode);
			data.put("mchntCd", mchntCd);
			signature = md5String(pasMap(data) + md5String(key, "UTF-8"), "UTF-8");
			data.put("signature", signature);
			data.put("signMethod", signMethod);
			
			log.info("request param: " + data.toString());
			
			String res = "";
	
			// 发送请求
			res = send(data, reqUrl);
			log.info("return data=[" + res + "]");
			if (StringUtils.isBlank(res)) {
				log.info("return data is null");
				return null;
			}
			// 解析返回报文
			TreeMap<String, Object> resMap = pasRes(res);
			log.info("resMap:" + resMap.toString());
			
			String resSign = (String) resMap.get("signature");// 响应报文的签名信息
			String fileCont = (String) resMap.get("fileContent");// 经过base64编码的文件
			/*
			 * 下面四个字段不参与md5校验
			 */
			resMap.remove("fileContent");
			resMap.remove("signature");
			resMap.remove("signMethod");
			resMap.remove("rspDesc");
	
			// 计算md5
			String aSgin = md5String(pasMap(resMap) + md5String(key, "UTF-8"),"UTF-8");
			
			// 比较md5值
			if (!resSign.trim().equals(aSgin)) {
				log.error("Verify the signature fails!");
				return null;
			}
			// 获取响应报文
			String resCode = (String) resMap.get("rspCode");
			if (!"00".equals(resCode)) {
				log.error("resCode is not equal success");
				return null;
			}
	
			byte[] fileByte = net.Base64Coder.decode(fileCont.getBytes("UTF-8"));
			return unzipTar(fileByte,resMap.get("fileName")+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * md5计算
	 * 
	 * @param s
	 *            参与运算的字符串
	 * @param enconding
	 *            字符集
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	private static String md5String(String s, String enconding) throws Exception{
		byte[] unencodedPassword = s.getBytes(enconding);
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(unencodedPassword);
		byte[] encodedPassword = md.digest();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * 字段排序
	 * 
	 * @param data
	 * @return
	 */
	private static String pasMap(TreeMap<String, Object> data) {
		StringBuffer sf = new StringBuffer();
		Iterator<String> keys = data.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			sf.append(key + "=" + (data.get(key) == null ? "" : data.get(key))
					+ "&");
		}
		return sf.toString();
	}

	/**
	 * 发送请求
	 */
	private static String send(Map<String, Object> data, String reqUrl){
		HttpClientNOSSL31 httpC = new HttpClientNOSSL31(reqUrl);
		httpC.process(data);
		return httpC.getResultString();
	}

	/**
	 * 解析返回报文
	 */
	private static TreeMap<String, Object> pasRes(String res) {
		TreeMap<String, Object> tm = new TreeMap<String, Object>();
		if (!StringUtil.isEmptyString(res)) {
			String[] ss = res.split("\\&");
			for (String s : ss) {
				if (s.startsWith("fileContent")) {
					tm.put("fileContent", s.substring(12));
					continue;
				}
				String[] subs = s.split("\\=", -1);
				if (2 == subs.length) {
					tm.put(subs[0], subs[1]);
				}
			}
		}
		return tm;
	}

	private static String unzipTar(byte[] fileByte,String name){
//		File file = new File("D:\\temp\\"+name);
//		if (!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}else{
//			file.deleteOnExit();
//		}
//		System.out.println("返回的文件流长度=[" + fileByte.length + "]");
//		FileOutputStream out = null;
//		FileChannel fc = null;
//		try {
//			out = new FileOutputStream(file);
//			fc = out.getChannel();
//			ByteBuffer bb = ByteBuffer.allocate(fileByte.length);
//			bb.put(fileByte);
//			bb.flip();
//			fc.write(bb);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			try{
//				if (null != out) {
//					out.close();
//				}
//				if (null != fc) {
//					fc.close();
//				}
//			}catch(IOException ioex){
//				ioex.printStackTrace();
//			}
//		}
		
		
		
		ZipInputStream 	tis =null;
		String retStr = null;
		try {
			InputStream is = new ByteArrayInputStream(fileByte);
			tis = new ZipInputStream(new BufferedInputStream(is));
			if (tis.getNextEntry() != null) {
				int count;
				byte[] data = new byte[204800];
	
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				BufferedOutputStream dest = new BufferedOutputStream(bos);
				while ((count = tis.read(data)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
				retStr = new String(bos.toByteArray(), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(tis!=null){
				try {
					tis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return retStr;
	}
}