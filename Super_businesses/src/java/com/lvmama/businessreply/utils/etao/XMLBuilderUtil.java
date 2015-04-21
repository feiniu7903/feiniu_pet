package com.lvmama.businessreply.utils.etao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

import com.lvmama.comm.vo.Constant;

/**
 * XML文档建立工具类.
 * 
 * @author huyunyan
 */
public class XMLBuilderUtil {

	/**
	 * 获取标签字符串 .
	 * 
	 * @param tags
	 *            标签列表
	 * @param block
	 *            分隔符
	 * @return 标签字符串
	 */
	public static String getTags(List<String> tags, String block) {
		int tagSize = tags.size();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < tagSize; i++) {
			result.append(tags.get(i) + block);
		}
		result.deleteCharAt(result.lastIndexOf(block));
		return result.toString();
	}

	public static void saveXML(String path, String xml) {
		saveStr(path, xml, false);
	}

	public static void appendToXML(String path, String xml) {
		saveStr(path, xml, true);
	}

	/**
	 * 向本地写入XML文件
	 * 
	 * @param path
	 *            ：保存XML文件的路径
	 * @param doc
	 *            ：需要保存的XML字符串流
	 */
	private static void saveStr(String path, String xml, boolean isAppend) {
		OutputStream out = null;
		OutputStreamWriter outwriter = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(path, isAppend);
			outwriter = new OutputStreamWriter(out, "UTF-8");
			outwriter.write(xml);
			outwriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (outwriter != null) {
					outwriter.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 以tag为标签名称，以value为内容，拼装xml单元.
	 * 
	 * @param tag
	 *            标签名称
	 * @param value
	 *            值
	 * @return 返回拼装后的字符串
	 */
	public static String buildElement(String tag, String value) {
		if (tag == null)
			return "";
		if (value == null)
			value = "";
		return new String("<" + tag + ">" + value + "</" + tag + ">\r\n");
	}

	/**
	 * 删除文件夹里的文件 注：不删除文件夹里的文件夹
	 * 
	 * @param xmlPath
	 */
	public static void delFile(String path) {
		File file = new File(path);
		if (!file.mkdir()) {
			File[] fileList = file.listFiles();
			if(null==fileList)return;
			for (int i = 0; i < fileList.length; i++) {
				// 删除子文件
				if (fileList[i].isFile() && fileList[i].exists()) {
					fileList[i].delete();
				}
			}
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 */
	public static void createFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public static String getXmlFile(String fileName) {
		StringBuffer buf = new StringBuffer();
		BufferedReader bReader = null;
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			bReader = new BufferedReader(new InputStreamReader(in, Charset.forName("utf-8")));
			while (bReader.ready()) {
				buf.append((char) bReader.read());
			}
			bReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	public static String getDirPath(String base, String dir) {
		return base + dir + File.separator;
	}

	public static String getFilePath(String base, String fileName) {
		return base + fileName;
	}

	public static String getFilePath(String base, String fileName, String tail) {
		return getFilePath(base, fileName) + '.' + tail;
	}

	public static String inintTuanGouBasePath(String xmlPath, String filePath) {
		String tgPath = XMLBuilderUtil.getDirPath(xmlPath, Constant.getInstance().getProperty("TUANGOU_PATH"));
		File file = new File(tgPath);
		file.mkdir();
		String filepath = XMLBuilderUtil.getFilePath(tgPath, filePath);
		return filepath;
	}
}
