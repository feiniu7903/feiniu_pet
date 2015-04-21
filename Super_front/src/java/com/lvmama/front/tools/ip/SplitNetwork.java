package com.lvmama.front.tools.ip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.lvmama.comm.utils.IPMap.AddrPair;

/**
 * 本程序的功能是把如下格式的地址变成子网掩码的格式 与MergeNetwork配合可以实现把QQWry IP库导出到尽量少行数的子网掩码的形式
 * 
 * 58.66.8.0-58.66.25.255 
 * 58.66.35.0-58.66.39.255 
 * 58.66.72.0-58.66.72.255
 * 58.66.224.0-58.66.231.255 
 * 58.67.31.0-58.67.36.255 
 * 58.67.38.0-58.67.63.255
 * 58.67.88.0-58.67.88.255 
 * 58.67.94.0-58.67.95.255 
 * 58.68.136.0-58.68.136.255
 * 
 * @author Alex Wang
 * 
 */
public class SplitNetwork {

	private static BufferedWriter bw;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
//		createNetIpFile("E:/gd_net.txt","E:/gd.conf");
		File fileDirectory = new File("E:/LVMAMA_IP/LVMAMA_IP");
		if (fileDirectory.isDirectory()) {
			File inFile[] = fileDirectory.listFiles();
			for(int i=0;i<inFile.length;i++) {
				if(inFile[i].isFile()) {
					createNetIpFile("E:/LVMAMA_IP/LVMAMA_IP/result/"+inFile[i].getName(),inFile[i].getCanonicalFile().toString());
					System.out.println(i+"--创建文件E:/LVMAMA_IP/LVMAMA_IP/result/"+inFile[i].getName());
				}
			}
		}
	}
	
	/**
	 * 生成最短的需要的IP网络需要文件；
	 * 
	 * @throws IOException
	 */
	private static void createNetIpFile(String outFileFullName, String inFileFullName) throws IOException {
		bw = new BufferedWriter(new FileWriter(outFileFullName));
		Map<Long, AddrPair> inIpMap = getMinIpMap(inFileFullName);
		Set<Long> keySet = inIpMap.keySet();
		for (Long key : keySet) {
			AddrPair obj = inIpMap.get(key);
			List<IpAddress> list = new ArrayList<IpAddress>();
			calc(list, obj.getBeginIp(), obj.getEndIp(), 32);
		}
		bw.close();
	}
	
	/**
	 * 获得最短的IP段的IPMap
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static Map<Long, AddrPair> getMinIpMap(String fileFullName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileFullName));
		String addr; // addr="192.168.1.0-192.168.24.255"
		Map<Long, AddrPair> map = new TreeMap<Long, AddrPair>();
		List<AddrPair> list = new ArrayList<AddrPair>();
		while ((addr = br.readLine()) != null) {
			addr = addr.trim();
			String beginIP = addr.split("-")[0];
			String endIP = addr.split("-")[1];
			Long begin = getLong(beginIP);
			Long end = getLong(endIP);
			AddrPair pair = new AddrPair();
			pair.setBeginIp(begin);
			pair.setEndIp(end);
			map.put(begin, pair);
			list.add(pair);
		}
		br.close();
	
		for (AddrPair addrPair : list) {
			Long nextIp = addrPair.getEndIp() + 1;
			AddrPair beginObj = map.get(nextIp);
			if (beginObj != null) {
				map.remove(beginObj.getBeginIp());
				beginObj.setBeginIp(addrPair.getBeginIp());
				map.put(beginObj.getBeginIp(), beginObj);
			}
		}
		
		return map;
	} 
	
	/**
	 * IP转化
	 * 
	 * @param list
	 * @param begin
	 * @param end
	 * @param maskLen
	 * @return
	 * @throws IOException
	 */
	public static boolean calc(List<IpAddress> list, long begin, long end, int maskLen) throws IOException {
		long mask = getMask(maskLen);
		long capacity = getCapacity(maskLen);
		long bip = (begin & mask);
		long eip = bip + capacity;
		if (bip >= begin && eip <= end) {
			boolean res = calc(list, begin, end, maskLen - 1);
			if (!res) {
				IpAddress add = new IpAddress();
				add.setNetwork(longToIP(bip));
				add.setNetmask(longToIP(mask));
				add.setLen(maskLen);
				bw.write(add.toString()+";");
				bw.newLine();
				long newBegin = bip + capacity + 1;
				res = calc(list, newBegin, end, 32);
			}
			return true;
		} else {
			return false;
		}
	}

	public static long getMask(int length) {
		String str = "";
		for (int i = 0; i < length; i++) {
			str = str + "1";
		}
		for (int i = 0; i < (32 - length); i++) {
			str = str + "0";
		}
		long mask = Long.parseLong(str, 2);
		return mask;
	}

	public static long getCapacity(int length) {
		String str = "";
		for (int i = 0; i < (32 - length); i++) {
			str = str + "1";
		}
		if (str.equals("")) {
			return 0;
		}
		long cap = Long.parseLong(str, 2);
		return cap;
	}

	public static String longToIP(long longIP) { // 将10进制整数形式转换成127.0.0.1形式的IP地址
		StringBuffer sb = new StringBuffer(""); // 直接右移24位
		sb.append(String.valueOf(longIP >>> 24));
		sb.append("."); // 将高8位置0，然后右移16位
		sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
		sb.append(".");
		sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(longIP & 0x000000FF));
		return sb.toString();
	}

	public static long getLong(String ipadd) {
		String[] arr = ipadd.split("\\.");
		return (Long.parseLong(arr[0]) << 24) + (Long.parseLong(arr[1]) << 16) + (Long.parseLong(arr[2]) << 8) + Long.parseLong(arr[3]);
	}
}
