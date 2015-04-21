package com.lvmama.comm.utils;

import java.text.DecimalFormat;
import java.util.Date;

public class SerialUtil {

	public static String generate24ByteSerialAttaObjectId(Long randomId) {
		String serial = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS") + randomId;
		String key = "PAYMENT_TRADE_NO"+ serial;
		//判断生成的流水号是否在memCached中存在，如果存在则重新生成并重新在memCached验证 直到流水号不存在再返回
		while(isSerialExists(key)){
			serial = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS") + randomId;
			key = "PAYMENT_TRADE_NO"+ serial;
		}
		return serial;
	}
	
	/**
	 * 20位对账流水号(百度钱包使用)
	 * @author ZHANG Jie
	 * @return
	 */
	public static String generate20ByteSerialAttaObjectId(Long randomId) {
		String serial = DateUtil.getFormatDate(new Date(), "MMddHHmmssSSS") + randomId;
		String key = "PAYMENT_TRADE_NO"+ serial;
		//判断生成的流水号是否在memCached中存在，如果存在则重新生成并重新在memCached验证 直到流水号不存在再返回
		while(isSerialExists(key)){
			serial = DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmssSSS") + randomId;
			key = "PAYMENT_TRADE_NO"+ serial;
		}
		return serial;
	}
	
	public static String generate15ByteSerial() {
		String serial=DateUtil.getFormatDate(new Date(), "yyMMddHHmmssSSS");
		String key = "PAYMENT_TRADE_NO_SHORT"+ serial;
		//判断生成的流水号是否在memCached中存在，如果存在则重新生成并重新在memCached验证 直到流水号不存在再返回
		while(isSerialExists(key)){
			serial=DateUtil.getFormatDate(new Date(), "yyMMddHHmmssSSS");
			key = "PAYMENT_TRADE_NO_SHORT"+ serial;
		}
		return serial;
	}
	
	public static String generate12ByteSerial() {
		String serial=DateUtil.getFormatDate(new Date(), "yyMMddHHmmss");
		String key = "PAYMENT_TRADE_NO_SHORT12"+ serial;
		//判断生成的流水号是否在memCached中存在，如果存在则重新生成并重新在memCached验证 直到流水号不存在再返回
		while(isSerialExists(key)){
			serial=DateUtil.getFormatDate(new Date(), "yyMMddHHmmss");
			key = "PAYMENT_TRADE_NO_SHORT12"+ serial;
		}
		return serial;
	}
	
	/**
	 * 10位对账流水号(合并支付使用)
	 * @author ZHANG Nan
	 * @return
	 */
	public static String generate10ByteSerial() {
		String serial=DateUtil.getFormatDate(new Date(), "HHmmssSSS")+"0";
		String key = "PAYMENT_TRADE_NO_SHORT10"+ serial;
		//判断生成的流水号是否在memCached中存在，如果存在则重新生成并重新在memCached验证 直到流水号不存在再返回
		while(isSerialExists(key)){
			serial=DateUtil.getFormatDate(new Date(), "HHmmssSSS")+"0";
			key = "PAYMENT_TRADE_NO_SHORT10"+ serial;
		}
		return serial;
	}
	/**
	 * 在MemCached中是否存在
	 * @author ZHANG Nan
	 * @param key KEY
	 * @return 是否存在
	 */
	private static boolean isSerialExists(String key){
		if (SynchronizedLock.isOnDoingMemCached(key)) {
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 对帐流水号(10位).
	 * 
	 * @param objectId
	 * @return
	 */
	public static String generate10Byte(final Long objectId, final Long times) {
		DecimalFormat orderIdFormat = null;
		if (objectId.toString().length() == 6) {
			orderIdFormat = new DecimalFormat("0000");
		} else if (objectId.toString().length() == 7) {
			orderIdFormat = new DecimalFormat("000");
		} else if (objectId.toString().length() == 8) {
			orderIdFormat = new DecimalFormat("00");
		}else {
			orderIdFormat = new DecimalFormat("00000");
		}
		
		String serial="" + objectId + orderIdFormat.format(times);
		String key = "PAYMENT_TRADE_NO_SHORT10Byte"+ serial;
		Long tempTimes=times;
		//判断生成的流水号是否在memCached中存在，如果存在则重新生成并重新在memCached验证 直到流水号不存在再返回
		while(isSerialExists(key)){
			serial="" + objectId + orderIdFormat.format(tempTimes+=1);
			key = "PAYMENT_TRADE_NO_SHORT10Byte"+ serial;
		}
		return serial;
	}
	public static void main(String[] args) {
		for(int i=0;i<1000;i++){
			System.out.println(generate10Byte(1301234L,1L));	
		}
	}
	
}
