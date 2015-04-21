package com.lvmama.clutter.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class Test2 {

	@Test
	public void test() throws Exception {
		String info = readFileByChars("D:/test2.text");
		try{
			for(int i = 0 ;i < 5 ; i++){
				//String regEx_Str1 = "[/>][<br><br><br />\\s\t\n\r&nbsp;]*</span[\\s\t\n\r]*>";
				//regEx_Str = "<span [\\s\\S^>]*>+[<br><br><br />\\s\t\n\r&nbsp;]*</span[\\s\t\n\r]*>+";
				//regEx_Str = "/>[<br><br><br />\\s\t\n\r&nbsp;]*</span[\\s\t\n\r]*>";
				List<String> regExStrList = new LinkedList<String>();
				/** 过滤空有样式的 空span */
				regExStrList.add("<span style[^>]*>(<br>|<br/>|<br />|&nbsp;)*[\\s]*</span[\\s]*>");
				/** 过滤无样式的空span */
				regExStrList.add("<span [\\s]*>(<br>|<br/>|<br />|&nbsp;)*[\\s]*</span[\\s]*>");
				/** 过滤空p标签  */
				for(String regExStr : regExStrList){
					Pattern patternStr = Pattern.compile(regExStr,Pattern.CASE_INSENSITIVE);
					Matcher matcherStr = patternStr.matcher(info);
					info = matcherStr.replaceAll("");
				}
			}
			System.out.println(info);
		}catch(Exception e){
			System.err.println("过滤html标签出错 " + e.getMessage()); 
		}
	}


	public static String readFileByChars(String fileName) {
		StringBuffer sb = new StringBuffer();
		Reader reader = null;
		try {
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			while ((charread = reader.read(tempchars)) != -1) {
				if ((charread == tempchars.length)) {
					sb.append(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						sb.append(tempchars[i]);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}

}	
