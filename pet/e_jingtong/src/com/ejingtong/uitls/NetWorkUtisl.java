package com.ejingtong.uitls;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetWorkUtisl  {
	private static NetWorkUtisl instance;
	private Context context;
	private NetWorkUtisl(Context context){
		this.context = context;
	}
	public static NetWorkUtisl getInstance(Context context){
		if(instance==null){
			synchronized (NetWorkUtisl.class) {
				instance = new NetWorkUtisl(context);
			}
		}
		return instance;
	}
	
	public boolean isWifi(){
		
		ConnectivityManager connectMgr = (ConnectivityManager) context
		        .getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if(info==null){
			return false;
		}
		if(ConnectivityManager.TYPE_WIFI==info.getType()){
			return true;
		} else if(ConnectivityManager.TYPE_MOBILE == info.getType()){
			return false;
		}
		return false;
	}
	
	public String getNetWorkType(){
		 ConnectivityManager connectMgr = (ConnectivityManager) context
		        .getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = connectMgr.getActiveNetworkInfo();
		if(ConnectivityManager.TYPE_WIFI==info.getType()){
			return "WIFI";
		} else if (info !=null && info.getType() ==  ConnectivityManager.TYPE_MOBILE) {
			if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_CDMA){
				return "CDMA";
			} else if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_EDGE){
				return "EDGE";
			} else if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_EVDO_0){
				return "EVDO_0";
			} else if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_EVDO_A){
				return "EVDO_A";
			} else if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_GPRS){
				return "GPRS";
			} else if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_HSDPA){
				return "HSDPA";
			} else if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_HSPA){
				return "HSPA";
			} else if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_HSUPA){
				return "HSUPA";
			} else if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_IDEN){
				return "IDEN";
			} else if(info.getSubtype()==TelephonyManager.NETWORK_TYPE_UMTS){
				return "UMTS";
			}
		}
		return "UNKNOW";
	}
}
