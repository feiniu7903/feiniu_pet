package com.lvmama.train;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PinyinCache {
	private Map<String, Integer> cache = new HashMap<String, Integer>();
	//String格式为"中文zhongwen"
	private Map<String, Integer> citys = new HashMap<String, Integer>();
	private String baseUrl = PinyinCache.class.getResource("/").getPath(); 
	private String fileName = "py_cache.txt";
	
	private static PinyinCache instance;
	private PinyinCache(){
		init();
	}
	public static synchronized PinyinCache getInstance(){
		if(instance == null)
			instance = new PinyinCache();
		return instance;
	} 
	private void init() {
		try {
			//Add Sth to cache from file
			File file = new File(baseUrl + fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line=br.readLine()) != null){
				if(!"".equals(line)){
					if(line.indexOf("=") > 0){
						String[] ss = line.split("=");
						if(ss[0].indexOf("|") > 0){
							String[] _ss = ss[0].split("\\|");
							try {
								set(_ss[1], _ss[0], Integer.parseInt(ss[1]));
							} catch (Exception e) {
								continue;
							}
						}
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public synchronized int getNext(String key, String name){
		int next;
		if(name.equals("南靖"))
			System.out.println();
		if(cache.containsKey(key)){
			//如果拼音存在，且中文拼音不存在，则返回数字+1
			if(!citys.containsKey(name+key)){
				next = cache.get(key).intValue() + 1;
				set(key, name, new Integer(next));
				saveToFile(key, name, next);
			}else{
				next = citys.get(name+key);
			}
		}else{
			next = 0;
			set(key, name, next);
			saveToFile(key, name, next);
		}
		return next;
	}
	
	public synchronized void set(String key, String name, Integer next){
		if(!cache.containsKey(key) || (cache.containsKey(key) && cache.get(key).intValue() < next))
			cache.put(key, next);
		citys.put(name+key, next);
	}
	private void saveToFile(String key, String name, Integer next) {
		// TODO Auto-generated method stub
		append(baseUrl + fileName, name + "|" + key + "=" + next.toString() + "\r\n");
	}
	
	public static void append(String fileName, String content){
		try {
			//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件 
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(PinyinCache.class.getResource("/").getPath());
		System.out.println(new Integer(3).toString());
		PinyinCache.getInstance();
	}
}
