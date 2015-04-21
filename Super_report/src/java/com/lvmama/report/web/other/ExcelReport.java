package com.lvmama.report.web.other;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

/**
 * 传真Excel表格样式
 * 
 * @author huangl
 * 
 */
public class ExcelReport{

	public byte[] export(String[] columnName,String[] heading,List<HashMap<String,Object>> dataList) {
		ByteArrayOutputStream outData = new ByteArrayOutputStream();
		HSSFWorkbook book =new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		//cell style
		HSSFCellStyle cellStyle_Content = book.createCellStyle();			
		cellStyle_Content.setWrapText(true);// 文本区域随内容多少自动调整
		HSSFFont font = book.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle_Content.setFont(font);
		//首行
		HSSFRow newRow = sheet.createRow(0); //标题
		HSSFCell celll = null;		
		for (int i = 0; i < heading.length; i++) {
			sheet.setColumnWidth((short) i, (short) 3500);
			celll = newRow.createCell((short) (i));
			celll.setCellValue(new HSSFRichTextString(heading[i]));
			celll.setCellStyle(cellStyle_Content);					
		}
		//数据记录
		for (int i = 0; i < dataList.size(); i++) {
			newRow = sheet.createRow(i+1);
			HashMap dataRow = dataList.get(i);

			for (int j=0;j<columnName.length;j++){
				celll = newRow.createCell((short) (j));
				celll.setCellValue(new HSSFRichTextString(nvl(dataList.get(i).get(columnName[j]))));
			}
					
		}
		//导出
		try{
		book.write(outData);
		}catch(Exception e)
		{	
			e.printStackTrace();
			throw new RuntimeException("excel export fail!");
		}
		return outData.toByteArray();
	}
	
	private String nvl(Object obj){
		if (obj==null) return "";
		else return String.valueOf(obj);
	}
	
}
