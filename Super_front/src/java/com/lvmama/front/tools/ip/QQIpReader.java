package com.lvmama.front.tools.ip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComIps;

/**
 * IP库的工具类，依赖qq的纯真IP 
 * 将
 * 0.0.0.0         0.255.255.255   IANA保留地址  CZ88.NET
 * 1.0.0.0         1.0.0.255       澳大利亚  CZ88.NET
 * 1.0.1.0         1.0.3.255       福建省 电信
 * 1.0.4.0         1.0.7.255       澳大利亚  CZ88.NET
 * 1.0.8.0         1.0.15.255      广东省 电信
 * 1.0.16.0        1.0.31.255      日本  CZ88.NET
 * 1.0.32.0        1.0.63.255      广东省 电信
 * 转化成下面的格式
 * 58.66.8.0-58.66.25.255 
 * 58.66.35.0-58.66.39.255 
 * 58.66.72.0-58.66.72.255
 * 58.66.224.0-58.66.231.255 
 * 58.67.31.0-58.67.36.255 
 * @author zhangzhenhua
 * 
 */
public class QQIpReader {
	
	/**
	 * 生成IPMAP
	 * @throws IOException
	 */
	public static void updateIpMap() throws IOException {
		// "218.75.242.246  218.75.242.246  湖南省湘潭市 电信"
		String outFileStr = "E:/LVMAMA_IP/newOut_ipMap/";
		BufferedReader br = new BufferedReader(new FileReader("E:/LVMAMA_IP/IP_QQ1.txt"));
		String alineString = "";
		while ((alineString = br.readLine()) != null && alineString.length()>32) {
			String beginStr = alineString.substring(0, 15).trim();
			String endStr = alineString.substring(16, 31).trim();
			String addrStr = alineString.substring(32);
			
			String province = getIpMapProvincePY(addrStr);
			
			String outFileName = "";
			if (province != null) {
				outFileName = outFileStr + province + ".xml";
				File outFile = new File(outFileName);
				insertTextbyline(outFile, getLong(beginStr) + "-" + getLong(endStr));
			//	System.out.println(outFile.getName() + "::::" + getLong(beginStr) + "-" + getLong(endStr));
			}
			
		}
		br.close();
	}
	
	/**
	 * 生成ComIpsList
	 * @throws IOException
	 */
	public static List<ComIps> createComIpsList() throws IOException {
		// "218.75.242.246  218.75.242.246  湖南省湘潭市 电信"
		BufferedReader br = new BufferedReader(new FileReader("D:/IP_QQ1.txt"));
		String alineString = "";

		List<ComIps> resultList = new ArrayList<ComIps>();
		ComIps ComIps;
		
		while ((alineString = br.readLine()) != null && alineString.length()>32) {
			ComIps = new ComIps();
			String beginStr = alineString.substring(0, 15).trim();
			String endStr = alineString.substring(16, 31).trim();
			String addrStr = alineString.substring(32);
			ComIps.setIpStart(getLong(beginStr));
			ComIps.setIpEnd(getLong(endStr));
			ComIps.setCityName(addrStr);
			ComIps.setCapitalName(addrStr);
			
		//	System.out.println(addrStr + "::::" + getLong(beginStr) + "-" + getLong(endStr));
			resultList.add(ComIps);
		}
		br.close();
		
		return resultList;
	}
	

	public static long getLong(String ipadd) {
		String[] arr = ipadd.split("\\.");
		return (Long.parseLong(arr[0]) << 24) + (Long.parseLong(arr[1]) << 16) + (Long.parseLong(arr[2]) << 8) + Long.parseLong(arr[3]);
	}
	
	public static void main(String args[]) throws IOException {
		//updateAllIp();
		System.out.println(getLong("192.168.30.161"));
		System.out.println(getLong("192.168.30.21"));
	}
	
	/**
	 * 生成DNS所需要的txt文件
	 * @throws IOException
	 */
	public static void updateAllIp() throws IOException {
		// "218.75.242.246  218.75.242.246  湖南省湘潭市 电信"
		String outFileStr = "E:/LVMAMA_IP/newOut/";
		BufferedReader br = new BufferedReader(new FileReader("E:/LVMAMA_IP/IP_QQ1.txt"));
		String alineString = "";
		while ((alineString = br.readLine()) != null && alineString.length()>32) {
			String beginStr = alineString.substring(0, 15).trim();
			String endStr = alineString.substring(16, 31).trim();
			String addrStr = alineString.substring(32);
			
			String province = getProvincePY(addrStr);
			
			String outFileName = "";
			if (addrStr.indexOf("电信")>-1) {
				outFileName = outFileStr +"dx_"+ province + ".txt";
			} else {
				outFileName = outFileStr +"ot_"+ province + ".txt";
			}
			

			File outFile = new File(outFileName);
			insertTextbyline(outFile, beginStr + "-" + endStr);
		//	System.out.println(outFile.getName() + "::::" + beginStr + "-" + endStr);
		}
		br.close();
	}
	
	public static void insertTextbyline(File file, String text) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(text);
			out.newLine();
			out.close();
			out = null;
		} catch (Exception ex) {
			System.out.println("没有保存成功：" + text);
		}
	}

	public static String getIpMapProvincePY(String NAME) {
		if (NAME.indexOf("北京市") > -1) {
			return "bj";
		} else if (NAME.indexOf("广东省") > -1) {
			return "gd";
		} else if (NAME.indexOf("四川省") > -1) {
			return "sc";
		}
		return null;
	}
	public static String getProvincePY(String NAME) {
		if (NAME.indexOf("北京市") > -1) {
			return "beijin";
		} else if (NAME.indexOf("天津市") > -1) {
			return "tianjin";
		} else if (NAME.indexOf("河北省") > -1) {
			return "hebeishen";
		} else if (NAME.indexOf("山西省") > -1) {
			return "shanxishen";
		} else if (NAME.indexOf("内蒙古") > -1) {
			return "neimengu";
		} else if (NAME.indexOf("辽宁省") > -1) {
			return "liaoningshen";
		} else if (NAME.indexOf("吉林省") > -1) {
			return "jilinshen";
		} else if (NAME.indexOf("黑龙江省") > -1) {
			return "heilongjiangshen";
		} else if (NAME.indexOf("上海市") > -1) {
			return "shanghaishi";
		} else if (NAME.indexOf("江苏省") > -1) {
			return "jiangsushen";
		} else if (NAME.indexOf("浙江省") > -1) {
			return "zhejiangshen";
		} else if (NAME.indexOf("安徽省") > -1) {
			return "anhuishen";
		} else if (NAME.indexOf("福建省") > -1) {
			return "fujianshen";
		} else if (NAME.indexOf("江西省") > -1) {
			return "jiangxishen";
		} else if (NAME.indexOf("山东省") > -1) {
			return "shandongshen";
		} else if (NAME.indexOf("河南省") > -1) {
			return "henanshen";
		} else if (NAME.indexOf("湖北省") > -1) {
			return "hubeishen";
		} else if (NAME.indexOf("湖南省") > -1) {
			return "hunanshen";
		} else if (NAME.indexOf("广东省") > -1) {
			return "guangdongshen";
		} else if (NAME.indexOf("广西省") > -1) {
			return "guangxishen";
		} else if (NAME.indexOf("海南省") > -1) {
			return "hainanshen";
		} else if (NAME.indexOf("重庆市") > -1) {
			return "chongqingshi";
		} else if (NAME.indexOf("四川省") > -1) {
			return "sichuanshen";
		} else if (NAME.indexOf("贵州省") > -1) {
			return "guizhoushen";
		} else if (NAME.indexOf("云南省") > -1) {
			return "yunnanshen";
		} else if (NAME.indexOf("西藏") > -1) {
			return "xizan";
		} else if (NAME.indexOf("陕西省") > -1) {
			return "shangxishen";
		} else if (NAME.indexOf("甘肃省") > -1) {
			return "gansushen";
		} else if (NAME.indexOf("青海省") > -1) {
			return "qinghaishen";
		} else if (NAME.indexOf("宁夏") > -1) {
			return "ningxia";
		} else if (NAME.indexOf("新疆") > -1) {
			return "xinjiang";
		} else if (NAME.indexOf("香港") > -1) {
			return "xianggang";
		} else if (NAME.indexOf("澳门") > -1) {
			return "aomen";
		} else if (NAME.indexOf("台湾") > -1) {
			return "taiwan";
		}
		return "other";
	}
}
