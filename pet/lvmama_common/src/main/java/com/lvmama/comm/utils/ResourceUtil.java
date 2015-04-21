package com.lvmama.comm.utils;

import java.io.File;

public class ResourceUtil {

	public static void main(String args[]) {
		System.out.println(getResourceFile("/WEB-INF/resources/abc.txt").toString());
	}
	
	public static File getResourceFile(String resourceName) {
		String path = ResourceUtil.class.getResource("/").getFile();
		String root = null;
		if (path.lastIndexOf("/WEB-INF/")>0) {
			root = path.substring(0, path.lastIndexOf("/WEB-INF/"));
		}else if (path.lastIndexOf("/target/classes/")>0){
			root = path.substring(0, path.lastIndexOf("/target/classes/"));
		}else if (path.lastIndexOf("/build/classes/")>0){
			root = path.substring(0, path.lastIndexOf("/build/classes/"));
		}else if (path.lastIndexOf("/bin/")>0){
			root = path.substring(0, path.lastIndexOf("/bin/"));
		}
		return new File(root + resourceName);
	}
	/**
	 * 
	 * @param resourceName
	 * @return
	 * @author nixianjun 2013.8.9
	 */
	public static String getResourceFileName(String resourceName) {
		String path = ResourceUtil.class.getResource("/").getFile();
		String root = null;
		if (path.lastIndexOf("/WEB-INF/")>0) {
			root = path.substring(0, path.lastIndexOf("/WEB-INF/"));
		}else if (path.lastIndexOf("/target/classes/")>0){
			root = path.substring(0, path.lastIndexOf("/target/classes/"));
		}else if (path.lastIndexOf("/build/classes/")>0){
			root = path.substring(0, path.lastIndexOf("/build/classes/"));
		}else if (path.lastIndexOf("/bin/")>0){
			root = path.substring(0, path.lastIndexOf("/bin/"));
		}
		return  root + resourceName;
	}
}
