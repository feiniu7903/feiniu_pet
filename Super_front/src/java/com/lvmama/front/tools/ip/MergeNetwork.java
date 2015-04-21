package com.lvmama.front.tools.ip;

import java.io.IOException;

/**
 * 本程序的功能是把类似于这样的IP段尽量合并成少的行数
 * 
 * 222.209.222.50-222.209.222.56
 * 222.209.206.59-222.209.206.101
 * 221.237.167.8-221.237.167.15 
 * 222.209.208.176-222.209.208.188
 * 222.209.207.203-222.209.207.205 
 * 222.209.205.110-222.209.205.184
 * 222.209.214.11-222.209.214.11 
 * 222.209.254.115-222.209.254.115
 * 222.209.200.120-222.209.200.120 
 * 222.209.208.75-222.209.208.78
 * 222.209.214.45-222.209.214.45 
 * 222.209.68.229-222.209.68.229
 * 222.209.208.135-222.209.208.135
 * 
 */
public class MergeNetwork {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		/*BufferedReader br = new BufferedReader(new FileReader("E://guangdong.conf"));
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

		Set<Long> keySet = map.keySet();

		for (Long key : keySet) {
			AddrPair obj = map.get(key);
			System.out.println(obj);
		}*/
		System.out.println(getLong("192.168.111.100"));
	}

	public static long getLong(String ipadd) {
		String[] arr = ipadd.split("\\.");
		return (Long.parseLong(arr[0]) << 24) + (Long.parseLong(arr[1]) << 16) + (Long.parseLong(arr[2]) << 8) + Long.parseLong(arr[3]);
	}
}
