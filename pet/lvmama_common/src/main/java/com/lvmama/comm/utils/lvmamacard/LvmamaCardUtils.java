package com.lvmama.comm.utils.lvmamacard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipFile;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.xhtmlrenderer.util.Zipper;

import com.lvmama.comm.vo.Constant;

/**
 * 驴妈妈新作储值卡工具类
 * 
 * @author nixianjun
 * 
 */
public class LvmamaCardUtils {
	public static final int yearDays = 365;
	public static final String LVMAMACARDINEXCELFILE = "/WEB-INF/resources/lvmamacard/lvmamacardTemp.xls";
	public static final String OUTSTORAGECARDTEMP = "/WEB-INF/resources/lvmamacard/outStorageCardTemp.xls";
	public static final String OUTSTORAGECARDTEMP1 = "/WEB-INF/resources/lvmamacard/temp/outStorageCardTempNew.xls";
	public static final String LVMAMACARDINEXCELFILETEMPDIR = "/WEB-INF/resources/lvmamacard/temp";
	public static final String LVMAMACARDINEXCELFILETEMP1 = LVMAMACARDINEXCELFILETEMPDIR+"/lvmamacardcard1.xls";
	public static final String LVMAMACARDINEXCELFILETEMP2 = LVMAMACARDINEXCELFILETEMPDIR+"/lvmamacard_";
	public static final String PASSWORD = "4589063";
	public static final String STORED_CARD_OUT = "STORED_CARD_OUT";
	public static final String STORED_CARD_IN = "STORED_CARD_IN";
	public static final String STORED_CARD = "STORED_CARD";
	
	
 	public static final String FILE = "C://Users/nixianjun/Desktop/myexcel.xls";
	public static final Long FIFTY = 5000L;//单位分，50元

	public static String getLvmamacardFilePassword() {
		return Constant.getInstance().getProperty("lvmamacardFilePassword");
	}
	public static void main(String[] args) {
		doEncryption("C://Users/nixianjun/Desktop/test.xls", "C://Users/nixianjun/Desktop/excel2.xls", "23");
	}
	
	/**
	 * 文件加密
	 * @param filePath
	 * @author nixianjun 2013-12-4
	 */
	public static void doEncryption(String filePath,String outFilePath,String password){
		FileOutputStream fileOut = null;
		try {
			
 			// 创 建一个工作薄
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(filePath));
			 // 设置密 码 保 护 ·
			wb.writeProtectWorkbook(password, "卡");
 			// 写入excel文件
			fileOut = new FileOutputStream(outFilePath);
			wb.write(fileOut); 
			fileOut.close();
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	public static String getLvmamacardCardEmailSubject() {
		return "驴妈妈储值卡制作卡密";
	}
	public static String getLvmamacardCardEmailContentText() {
		return "  附件中是驴妈妈此次需要制作的礼品卡的卡密，请查收！";
	}
	public static String getLvmamacardCardEmailToAddress() {
		return Constant.getInstance().getProperty("lvmamacardCardEmailToAddress");
 	}
	public static String getLvmamacardCardEmailFromName() {
 		return "驴妈妈";
	}

}
