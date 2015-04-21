package com.lvmama.comm.utils;

import java.io.InputStream;
import java.util.Properties;

public class RecommendBlockProperties {
	private static Properties properties;
	
	private static void init(){
			  try {
				  ClassLoader classLoader = RecommendBlockProperties.class.getClassLoader();
				  if(properties==null){
					  properties = new Properties();
					  InputStream inputStream = classLoader.getResourceAsStream("recommend-block.properties");   
					  properties.load(inputStream);
					  inputStream.close();
				  }
			  }catch(Exception ex){
					  ex.printStackTrace();
			  }
	}
	
	public static String getBlockProperties(String key){
		if(properties==null){
			init();
		}
		return properties.getProperty(key);
	}
}
