package com.lvmama.passport.processor.impl.client.chimelong.util;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;

public class ChimelongUtils {

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * 把字节数组转换成16进制字符串(主要用于调试程序,输出日志)
	 * 
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
		return bytesToHexString(bArray, false);
	}

	/**
	 * 把字节数组转换成16进制字符串(主要用于调试程序,输出日志)
	 * 
	 * @param bArray
	 * @param isFormat
	 *            是否结式化，即两个字间之间是否插入空格
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray, boolean isFormat) {
		if (bArray == null)
			return " ";
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = fillToHexString(Integer.toHexString(0xFF & bArray[i]));
			sb.append(sTemp.toLowerCase());
			if (isFormat)
				sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 补齐字节数
	 * 
	 * @param bArray
	 * @param length
	 *            长度
	 * @param isleft
	 *            补往高位
	 * @return
	 */
	public static final byte[] fillBytesToLength(byte[] bArray, int length, boolean isleft) {
		byte[] bs = new byte[length];
		if (bArray == null || bArray.length == 0)
			return bs;
		if (length <= bArray.length)
			return arraycopy(bArray);
		if (isleft) {
			System.arraycopy(bArray, 0, bs, length - bArray.length, bArray.length);
		} else {
			System.arraycopy(bArray, 0, bs, 0, bArray.length);
		}
		return bs;
	}

	/**
	 * 把16进制字符串转换成Byte, 如输入: "eb303135" 输出:byte: 0xeb, 0x30, 0x31, 0x35
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = hex.length();
		if (len % 2 != 0) {
			hex = "0" + hex;
			len++;
		}
		len = len / 2;
		byte[] result = new byte[len];
		char[] achar = hex.toUpperCase().toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	/**
	 * 给出一个字符串和长度，如果字符串的长度达不到指定的长度，则往左补上一指定字符，以达到要求的长度
	 * 
	 * @param str
	 *            需要补缺的字符串
	 * @param length
	 *            需要的长度
	 * @param c
	 *            补上去的字符
	 * @return 补缺之后的字符串
	 */
	public static String fillString(String str, int length, char c) {
		if (str == null || str.length() >= length)
			return str;
		StringBuffer sb = new StringBuffer();
		int tempLength = length - str.length();
		if (tempLength <= 0)
			return str;
		for (int i = length - 1; i >= 0; i--) {
			if (i >= tempLength)
				sb.append(str.charAt(i - tempLength));
			else
				sb.append(c);
		}
		return sb.reverse().toString();
	}

	public static String fillToHexString(String inputStr) {
		return fillString(inputStr, 2, '0');
	}

	public static String fillToBinaryString(String inputStr) {
		return fillString(inputStr, 8, '0');
	}

	/**
	 * 数字转换成16进制的byte, 如输入:18, 输出:byte: 0x12
	 * 
	 * @param i
	 * @return
	 */
	public static final byte[] integerToHexBytes(int i) {
		return hexStringToByte(Integer.toHexString(i));
	}

	/**
	 * 数字转换成BCD的Byte, 如输入: 100, 输出: Byte: 0x01,0x00
	 * 
	 * @param i
	 * @return
	 */
	public static final byte[] integerToBcdBytes(int i) {
		return strToBcdBytes(Integer.toString(i));
	}

	/**
	 * 输入字转换成ASC的Byte, 如输入: 100, 输出: Byte: 0x31, 0x30, 0x30
	 * 
	 * @param i
	 * @return
	 */
	public static final byte[] integerToAscBytes(int i) {
		return strToAscBytes(Integer.toString(i));
	}

	public static final int hexBytesToInteger(byte[] bArray) {
		try {
			return Integer.parseInt(bytesToHexString(bArray), 16);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static final int bcdBytesToInteger(byte[] bArray) {
		try {
			return Integer.parseInt(bcdBytesToStr(bArray));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static final int ascBytesToInteger(byte[] bArray) {
		try {
			return Integer.parseInt(ascBytesToStr(bArray));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/** 按BCD对bytes进行解码 */
	public static String bcdBytesToStr(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString();
	}

	/** 按UTF-8对bytes进行解码 */
	public static String ascBytesToStr(byte[] bytes) {
		try {
			return new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return new String(bytes);
		}
	}

	/** 用UTF对字符串进行编码 */
	public static byte[] strToAscBytes(String str) {
		try {
			return str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			return str.getBytes();
		}
	}

	/** 用BCD对字符串进行编码 */
	public static byte[] strToBcdBytes(String asc) {
		int len = asc.length();
		if (len % 2 != 0) {
			asc = "0" + asc;
			len++;
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	/** 数组扩容 */
	public static byte[] grow(byte[] src, int size) {
		if (src == null)
			return new byte[size];
		byte[] tmp = new byte[src.length + size];
		System.arraycopy(src, 0, tmp, 0, src.length);
		return tmp;
	}

	/** 字节数组拷贝 */
	public static byte[] arraycopy(byte[] src) {
		if (src == null)
			return null;
		byte[] tmp = new byte[src.length];
		System.arraycopy(src, 0, tmp, 0, tmp.length);
		return tmp;
	}

	/**
	 * 连接字节数组,把bArray连接到src上
	 * 
	 * @param src
	 * @param bArray
	 * @return
	 */
	public static byte[] join(byte[] src, byte[] bArray) {
		return join(src, bArray, 0, bArray.length);
	}

	/**
	 * 连接字节数组,把bArray连接到src上
	 * 
	 * @param src
	 * @param bArray
	 * @return
	 */
	public static byte[] join(byte[] src, byte[] bArray, int length) {
		return join(src, bArray, 0, length);
	}

	/**
	 * 连接字节数组,把bArray中指定位置的字节连接到src上
	 * 
	 * @param src
	 * @param bArray
	 * @param offset
	 *            需要连接到src的bArray的偏移量
	 * @param length
	 *            需要连接到src的bArray的字节数
	 * @return
	 */
	public static byte[] join(byte[] src, byte[] bArray, int offset, int length) {
		if (src == null || src.length == 0) {
			byte[] tmp = new byte[length];
			System.arraycopy(bArray, offset, tmp, 0, tmp.length);
			return tmp;
		}
		if (bArray == null || bArray.length == 0) {
			return arraycopy(src);
		}
		int index = src.length;
		src = grow(src, length);
		System.arraycopy(bArray, offset, src, index, length);
		return src;
	}

	/** 日期－－时间 */
	public static String getStringDate(Date date) {
		return getStringDate(date,"yyyy-MM-dd HH:mm:ss");
	}
	public static String getStringDate(Date date,String pattern) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date).trim();
	}
	public static Date getDate(String dateText) {
		Date d = getDate(dateText,"yyyy-MM-dd HH:mm:ss");
		if(d==null){
			d = getDate(dateText,"yyyy-MM-dd");
		}
		return d;
	}
	public static Date getDate(String dateText,String pattern) {
		if (dateText == null || dateText.trim().length() == 0) {
			return null;
		}
		try {
			DateFormat df = new SimpleDateFormat(pattern);
			df.setLenient(false);
			return df.parse(dateText);
		} catch (ParseException e) {
			return null;
		}
	}
	public static String[] split(String source, String spliter) {
		int index = 0;
		String tmp = source;
		Vector<String> vArray = new Vector<String>();
		while ((index = tmp.indexOf(spliter)) != -1) {
			vArray.add(tmp.substring(0, index));
			tmp = tmp.substring(index + spliter.length());
		}
		if (tmp.length() > 0)
			vArray.add(tmp);
		String[] b = new String[vArray.size()];
		vArray.toArray(b);
		return b;
	}
	public static String blank(String input){
		if(input==null || input.length()==0)
			return " ";
		return input;
	}

	/**字符串转化成BigDecimal,保留两位小数,后面的四舍五入*/
	public static java.math.BigDecimal strToMoney(String str){
		if(str.length()==0) str="0";
		java.math.BigDecimal s = new java.math.BigDecimal(str);
		s=s.setScale(2,java.math.BigDecimal.ROUND_HALF_UP);
		return s;
	}

	public static void main(String[] args) {

//		String sb = "51092abcd中全全";
//		byte[] bs = Util.strToAscBytes(sb);
//
//		System.out.println(bytesToHexString(bs, true));
//
//		String hexMessageHeader = "eb3600";
//		byte[] bArray = Util.hexStringToByte(hexMessageHeader);
//		System.out.println(bytesToHexString(bArray, true));
		Date d = ChimelongUtils.getDate("2009-01-01 00:00:00");
		if(d!=null){
			System.out.println("sssssssssss");
		}else{
			System.out.println("sdcccccccccc");
		}
	}

	/**
	 * 获取MD5Key
	 * @return
	 */
	public static String getMd5Key(){
		String key =ChimelongConfig.getKey();
		if (key==null) {
			key="sh0001";
		}
		String md5key = ChimelongMD5.getMD5(key);
		return md5key;
	}
	
	/**
	 * 获取MD5Key
	 * @return
	 */
	public static String getMd5Key_zh(){
		String key =ChimelongConfig.getZhKey();
		if (key==null) {
			key="sh0001";
		}
		String md5key = ChimelongMD5.getMD5(key);
		return md5key;
	}

	/**
	 * 获取签名
	 * @param orderInfo
	 * @return
	 */
	public static String getSign(String orderInfo){
		return ChimelongMD5.getMD5(orderInfo);
	}

	/**
	 * 订单信息加密
	 * @param md5key
	 * @param orderInfo
	 * @return
	 */
	public static String enCode(String md5key,String orderInfo){
		ChimelongEncrypt encrypt = new ChimelongEncrypt();//加密
		encrypt.setKey(md5key);
		String encryptorderInfo = "";
		try {
			encryptorderInfo= encrypt.strEncode(orderInfo);
		} catch (Exception e) {
			
		}
		return encryptorderInfo;
	}

	/**
	 * 解析生产订单
	 * @param md5key
	 * @param responseInfo
	 * @return
	 */
	public static String deCode(String md5key,String responseInfo){
		ChimelongEncrypt encrypt = new ChimelongEncrypt();//加密
		encrypt.setKey(md5key);
		String decodeorderInfo = "";
		try {
			decodeorderInfo= encrypt.strDecode(responseInfo);
		} catch (Exception e) {
		}
		return decodeorderInfo;
	}

	/**
	 * 构造订单信息
	 * @param orderheadId
	 * @param visitdate
	 * @param tk  特殊说明：(长隆类型(000030:00003基本票，0门票类型),张数,价格)
	 * @param mobilenumber
	 * @return
	 */
	public static String buildOrderInfo(String orderheadId,Date visitdate,String tk,String mobilenumber,String tkType,Date createDate){
		String orderInfo=orderheadId+"|"
		+DateUtil.formatDate(createDate, "yyyy-MM-dd HH:mm:ss")+"|"
		+DateUtil.formatDate(visitdate,"yyyy-MM-dd HH:mm:ss")+"|"
		+DateUtil.formatDate(visitdate,"yyyy-MM-dd HH:mm:ss")+"|"
		+tkType+"|"+tk+"|"+mobilenumber+"||||||0";
		return orderInfo;
	}

	/**
	 * 获得订单信息
	 * @param serialno
	 * @param supplierIds
	 * @return
	 */
	public static Order getOrder(String serialno,List<Long> supplierIds){
		Order Order=new Order();
		StringBuilder tk = new StringBuilder();
		String tkType = "";
		String validTime = "";
		String invalidTime = "";
		 OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
		OrdOrder ordOrder=orderServiceProxy.queryOrdOrderBySerialNo(serialno);
		for (OrdOrderItemProd itemProd : ordOrder.getOrdOrderItemProds()) {
			// 订单子指向
			for (OrdOrderItemMeta itemMeta : itemProd.getOrdOrderItemMetas()) {
				//提供商对应编号为长隆
				if (supplierIds.contains(itemMeta.getSupplierId())) {
					// 代理产品编号
					String ticketType = itemMeta.getProductIdSupplier();
					// 代理产品类型
					tkType = itemMeta.getProductTypeSupplier();
					// 价格
					String price = itemProd.getPrice().toString();
					// 票数(成人数，或儿童数*销售产品下的订单子指向的数目)
					Long sheetTotal = (itemMeta.getChildQuantity() + itemMeta.getAdultQuantity())
							* itemProd.getQuantity();
	
					validTime = DateUtil.formatDate(itemProd.getVisitTime(), "yyyy-MM-dd HH:mm:ss");
					Date temp = DateUtil.getDateAfterDays(itemProd.getVisitTime(), itemMeta.getValidDays().intValue());
					invalidTime = DateUtil.formatDate(temp, "yyyy-MM-dd HH:mm:ss");
					// "000030,1,150;000032,1,90;000040,2,45";
					tk.append(ticketType + "," + sheetTotal + "," + price + ";");
				}
			}
		}
		Order.setTkType(tkType);
		Order.setTk(tk.toString());
		Order.setValidTime(validTime);
		Order.setInvalidTime(invalidTime);
		return Order;
	}

	/**
	 * 获得订单信息
	 * @param serialno
	 * @param supplierIds
	 * @return
	 */
	public static Order getOrder(Long orderId,String targetIds,Long OrderItemMetaId){
		Order Order=new Order();
		StringBuilder tk = new StringBuilder();
		String tkType = "";
		String validTime = "";
		String invalidTime = "";
		OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
		OrdOrder ordOrder=orderServiceProxy.queryOrdOrderByOrderId(orderId);
		boolean exit=false;
			// 订单子指向
		 for (OrdOrderItemProd itemProd : ordOrder.getOrdOrderItemProds()) {
				if(exit){
					break;
				}
				for (OrdOrderItemMeta itemMeta : itemProd.getOrdOrderItemMetas()) {
					if(itemMeta.getOrderItemMetaId().equals(OrderItemMetaId)){
						// 代理产品编号
						String ticketType = itemMeta.getProductIdSupplier();
						Order.setTypeCode(ticketType);
						// 代理产品类型
						tkType = itemMeta.getProductTypeSupplier();
						// 价格
						String price = String.valueOf(itemProd.getPriceYuan());
						// 票数(成人数，或儿童数*销售产品下的订单子指向的数目)
						Long sheetTotal = itemMeta.getTotalQuantity();
						Order.setValidDate(DateUtil.getFormatDate(ordOrder.getVisitTime(), "yyyy-MM-dd"));
						validTime = DateUtil.getFormatDate(ordOrder.getVisitTime(), "yyyy-MM-dd HH:mm:ss");
						Date temp = DateUtil.getDateAfterDays(ordOrder.getVisitTime(), 0);
						invalidTime = DateUtil.getFormatDate(temp, "yyyy-MM-dd HH:mm:ss");
						// "000030,1,150;000032,1,90;000040,2,45";
						tk.append(ticketType + "," + sheetTotal + "," + price + ";");
						exit=true;
						break;
					}
			}
		 }
		Order.setTkType(tkType);
		Order.setTk(tk.toString());
		Order.setValidTime(validTime);
		Order.setInvalidTime(invalidTime);
		return Order;
	}
}

