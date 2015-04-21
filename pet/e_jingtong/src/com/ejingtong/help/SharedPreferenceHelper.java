package com.ejingtong.help;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
	
	private static SharedPreferenceHelper sharedPreferenceHelper;
	private static Context mContex;
	private static SharedPreferences settings;
	private static String mFileName;
	private SharedPreferenceHelper sharePHelper;
	
	public static SharedPreferenceHelper getInstance(Context context, String fileName){

		mFileName = fileName;
		
		if(sharedPreferenceHelper == null){
			sharedPreferenceHelper = new SharedPreferenceHelper(context);
		}
		return sharedPreferenceHelper;
	}
	
	public SharedPreferenceHelper(Context context){
		mContex = context;
		settings = mContex.getSharedPreferences(mFileName, 0);
	}
	
	
	public boolean saveStrList(String key, List<String> data){
		
		StringBuffer bufer = new StringBuffer();

		for (int i = 0; i < data.size(); i++) {
			bufer.append(data.get(i));
			bufer.append(",");
		}
		
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, bufer.toString());
		return editor.commit();
//		saveStrArray(key, (String[])data.toArray());
	}
	
	public List<String> getStrList(String key){
		List<String> data = new ArrayList<String>();
		String str = settings.getString(key, null);
		if(str == null){
			return data;
		}
		
		
		String[] ss = str.split(",");
		for (int i = 0; i < ss.length; i++) {
			data.add(ss[i]);
		}
		
		return data;
	}
	
	public boolean saveStrArray(String key, String[] data) {
		StringBuffer bufer = new StringBuffer();

		for (int i = 0; i < data.length; i++) {
			bufer.append(data[i]);
			bufer.append(",");
		}
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, bufer.toString());
		return editor.commit();
	}
	
	public String[] getStrArray(String key){
		String str = settings.getString(key, null);
		if(str == null){
			return null;
		}
		return str.split(",");
	}
	
	public boolean saveStr(String key, String str){
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, str);
		return editor.commit();
	}
	
	public String  getStr(String key){
		return settings.getString(key, null);
	}
	
	public String getStr(String key, String defaultValue){
		return settings.getString(key, defaultValue);
	}

	public boolean saveInt(String key, int value){
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	
	public int getInt(String key,  int defauleValue){
		return settings.getInt(key, defauleValue);
	}
	
	public int getInt(String key){
		return settings.getInt(key, 0);
	}
	
	public boolean saveBoolean(String key, boolean value){
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}
	
	
	public boolean  getBoleanDefaultFalse(String key){
		return settings.getBoolean(key, false);
	}
	
	public boolean  getBoleanDefaultTrue(String key){
		return settings.getBoolean(key, true);
	}
	
	public boolean saveMap(Map<String, String > data) {
		SharedPreferences.Editor editor = settings.edit();
		for(String key : data.keySet()){
			editor.putString(key, data.get(key));
		}
		
		return editor.commit();
		
	}
	
	public void clean(){
		SharedPreferences.Editor editor = settings.edit();
		editor.clear();
		editor.commit();
	}
	
}
