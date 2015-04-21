package com.lvmama.comm.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IPMap {

	private static IPMap instance;
	
	private static final String DEFAULT="default";
	private static final String IPMAPDIRECTORY = "/ipmap";
	
	private Map<String, List<AddrPair>> map;
	
	private void init() {
		map = new HashMap<String, List<AddrPair>>();
		try{
			File file = ResourceUtil.getResourceFile(IPMAPDIRECTORY);
			File[] children = file.listFiles();
			for (File child : children) {
				if (child.isFile()) {
					String fileName = child.getName();
					if (fileName.matches(".*xml$")) {
						String[] sArr = fileName.split("\\.");
						String mainName = sArr[0];
						String line;
						List<AddrPair> list = new ArrayList<AddrPair>();
						BufferedReader br = new BufferedReader(new FileReader(child));
						while(( line = br.readLine())!=null) {
							line = line.trim();
							Long begin = Long.parseLong(line.split("-")[0]);
							Long end = Long.parseLong(line.split("-")[1]);
							AddrPair pair = new AddrPair();
							pair.setBeginIp(begin);
							pair.setEndIp(end);
							list.add(pair);
						}
						map.put(mainName, list);
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static IPMap getInstance() {
		if(instance == null){
			synchronized(DEFAULT) {
				if (instance==null) {
					instance = new IPMap();
					instance.init();
				}
			}
		}
		return instance;
	}
	
	public String getLocation(String ip) {
		long addr = stringToLong(ip);
		Set<String> keySet = map.keySet();
		for (String str : keySet) {
			List<AddrPair> list = map.get(str);
			for (AddrPair addrPair : list) {
				if (addrPair.contain(addr)) {
					return str;
				}
			}
		}
		return DEFAULT;
	}
	
	public static long stringToLong(String ipadd) {
	  String[] arr = ipadd.split("\\.");
	  return (Long.parseLong(arr[0]) << 24) + (Long.parseLong(arr[1]) << 16) + (Long.parseLong(arr[2]) << 8) + Long.parseLong(arr[3]);
	}
	public static String longToString(long ip)
	{
		long ui1 = ip & 0xFF000000;
		ui1 = ui1 >> 24;
		long ui2 = ip & 0x00FF0000;
		ui2 = ui2 >> 16;
		long ui3 = ip & 0x0000FF00;
		ui3 = ui3 >> 8;
		long ui4 = ip & 0x000000FF;
		String IPstr = "";
		IPstr = ui1 + "." + ui2 + "." + ui3 + "." + ui4;
		return IPstr;
	}
	 
	public static class AddrPair {
		private Long beginIp;
		private Long endIp;

		public Long getBeginIp() {
			return beginIp;
		}

		public void setBeginIp(Long beginIp) {
			this.beginIp = beginIp;
		}

		public Long getEndIp() {
			return endIp;
		}

		public void setEndIp(Long endIp) {
			this.endIp = endIp;
		}

		public boolean contain(long addr) {
			return addr>beginIp && addr<endIp;
		}

		@Override
		public boolean equals(Object obj) {
			AddrPair pair = (AddrPair) obj;
			return beginIp == pair.getBeginIp() && endIp == pair.getEndIp();
		}

		@Override
		public int hashCode() {
			Long l = beginIp + endIp;
			return l.hashCode();
		}

		@Override
		public String toString() {
			return beginIp + "-" + endIp;
		}
	}
	
//	public static void main(String[] args) {
//		//IPMap ipMap = IPMap.getInstance();//
//		System.out.println(IPMap.stringToLong("119.75.217.56"));
//		System.out.println(IPMap.longToString(1035411474));
//	}
}
