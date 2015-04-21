package com.lvmama.comm.utils;

public class UserLevelUtils {
	private static int lv0= 0;
	private static int lv1= 150;
	private static int lv2= 200;
	private static int lv3= 300;
	private static int lv4= 500;
	private static int lv5= 1000;
	private static int lv6= 1500;
	private static int lv7= 2000;
	private static int lv8= 3000;
	private static int lv9= 4000;
	private static int lv10= 5000;
	private static int lv11= 6000;
	private static int lv12= 8000;
	private static int lv13= 10000;
	private static int lv14= 50000;
	private static String lv0Str = "赤脚学步";
	private static String lv1Str = "草鞋上路";
	private static String lv2Str = "布鞋踏青";
	private static String lv3Str = "皮鞋游走";
	private static String lv4Str = "骑驴走";
	private static String lv5Str = "骑马走";
	private static String lv6Str = "马车行";
	private static String lv7Str = "自行车行";
	private static String lv8Str = "摩托车行";
	private static String lv9Str = "火车行";
	private static String lv10Str = "宝马行";
	private static String lv11Str = "飞机行";
	private static String lv12Str = "云游四海";
	private static String lv13Str = "环球遍旅";
	private static String lv14Str = "星际航游";
	
	public static String getLevel(Long point){
		StringBuffer out=new StringBuffer();
		if(point<lv1){
			out.append(lv0Str);
		}else if(point>=lv1&&point<lv2){
			out.append(lv1Str);
		}else if(point>=lv2&&point<lv3){
			out.append(lv2Str);
		}else if(point>=lv3&&point<lv4){
			out.append(lv3Str);
		}else if(point>=lv4&&point<lv5){
			out.append(lv4Str);
		}else if(point>=lv5&&point<lv6){
			out.append(lv5Str);
		}else if(point>=lv6&&point<lv7){
			out.append(lv6Str);
		}else if(point>=lv7&&point<lv8){
			out.append(lv7Str);
		}else if(point>=lv8&&point<lv9){
			out.append(lv8Str);
		}else if(point>=lv9&&point<lv10){
			out.append(lv9Str);
		}else if(point>=lv10&&point<lv11){
			out.append(lv10Str);
		}else if(point>=lv11&&point<lv12){
			out.append(lv11Str);
		}else if(point>=lv12&&point<lv13){
			out.append(lv12Str);
		}else if(point>=lv13&&point<lv14){
			out.append(lv13Str);
		}else if(point>=lv14){
			out.append(lv14Str);
		}
		return out.toString();
	}
	
}
