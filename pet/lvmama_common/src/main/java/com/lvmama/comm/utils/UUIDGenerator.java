package com.lvmama.comm.utils;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * 生成UUID。从Hibernate中提取出来。
 * 用法请看main方法。
 * @author <a href="mailto:xuj@channelcampus.com">Jun Xu</a>
 *
 */
public class UUIDGenerator {
	/**
	 * 循环次数
	 */
	private static final int N = 4;
	/**
	 * 移动位数
	 */
	private static final int MOVE_LEN = 8;
	/**
	 * 步长
	 */
	private static final int STEP = 8;
	/**
	 * 高位时间
	 */
	private static final int HIGHT_TIME = 32;
	/**
	 * 整型最大字节数
	 */
	private static final int INT_LEN = 8;
	/**
	 * 短整型最大字节数
	 */
	private static final int SHORT_LEN = 4;
	/**
	 * 初始化容量
	 */
	private static final int CAPACITY = 36;
	/**
	 * IP地址整数值
	 */
	private static final int IP;
	/**
	 * 次数
	 */
	private static short counter = (short) 0;
	/**
	 * 次数byte值
	 */
	private static byte counterByte = (byte) 0;
	/**
	 * 当前系统时间
	 */
	private static final int JVM = (int) (System.currentTimeMillis() >>> STEP);
	/**
	 * SEP
	 */
	private static final String SEP = "";

	/**
	 * 无参构造函数
	 */
	public UUIDGenerator() {
	}

	/**
	 * IP转成整数
	 * @param bytes IP的byte值
	 * @return 整数
	 */
	public static int ipToInt(final byte[] bytes) {
		int result = 0;
		for (int i = 0; i < N; i++) {
			result = (result << MOVE_LEN) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	static {
		int ipadd;
		try {
			ipadd = ipToInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	/**
	 * Unique across JVMs on this machine (unless they load this class in the
	 * same quater second - very unlikely)
	 * @return Unique across JVMs
	 */
	protected int getJVM() {
		return JVM;
	}

	/**
	 * Unique in a millisecond for this JVM instance (unless there are >
	 * Short.MAX_VALUE instances created in a millisecond)
	 * @return counter
	 */
	protected short getCount() {
		synchronized (UUIDGenerator.class) {
			if (counter < 0) {
				counter = 0;
			}
			return counter++;
		}
	}

	/**
	 * getByteCount
	 * @return counterByte
	 */
	protected byte getByteCount() {
		synchronized (UUIDGenerator.class) {
			if (counterByte < 0) {
				counterByte = 0;
			}
			return counterByte++;
		}
	}

	/**
	 * Unique in a local network
	 * @return IP
	 */
	protected int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 * @return 高位时间
	 */
	protected short getHiTime() {
		return (short) (System.currentTimeMillis() >>> HIGHT_TIME);
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	/**
	 * 格式化整形数值
	 * @param intval 整型值
	 * @return 数值字符串
	 */
	protected String format(final int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(INT_LEN - formatted.length(), INT_LEN, formatted);
		return buf.toString();
	}

	/**
	 * 格式化短整形值
	 * @param shortval 短整型值
	 * @return 数值字符串
	 */
	protected String format(final short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(SHORT_LEN - formatted.length(), SHORT_LEN, formatted);
		return buf.toString();
	}

	/**
	 * 格式化BYT型数值
	 * @param shortval BYTE型值
	 * @return 数值字符串
	 */
	protected String format(final byte shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("00");
		buf.replace(2 - formatted.length(), 2, formatted);
		return buf.toString();
	}

	/**
	 * generate
	 * @return Serializable
	 */
	public Serializable generate() {
		return new StringBuffer(CAPACITY).append(format(getIP())).append(SEP).append(
				format(getJVM())).append(SEP).append(format(getHiTime()))
				.append(SEP).append(format(getLoTime())).append(SEP).append(
						format(getCount())).toString();
	}

	/**
	 * 生成4位字串，步长为1自动增长
	 * @author William Liu
	 * @return 字串
	 */
	public Serializable generateFor4Pos() {
		return new StringBuffer(CAPACITY).append(format(getCount())).toString();
	}
	/**
	 * 生成2位字串，步长为1自动增长
	 * @author William Liu
	 * @return 最大值7e,而后从头循环
	 */
	public Serializable generateFor2Pos() {
		return new StringBuffer(CAPACITY).append(format(getByteCount())).toString();
	}

//	public static void main(String args[]){
//		UUIDGenerator gen = new UUIDGenerator();
//	}
}
