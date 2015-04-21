package com.lvmama.comm.utils;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelImport {

	public static List<String> excImport(String strPath) throws Exception{
		Workbook  xwb = WorkbookFactory.create(new FileInputStream(strPath));  
		return getSheetValues(xwb); 
	}
	
	public static List<String> excImport(FileInputStream strPath) throws Exception{
		Workbook  xwb = WorkbookFactory.create(strPath);  
		return getSheetValues(xwb); 
	}

	private static List<String> getSheetValues(Workbook xwb) {
		Sheet sheet = xwb.getSheetAt(0);  
		List<String> list = new ArrayList<String>();
		// 循环输出表格中的内容  
		for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
			Row  row = sheet.getRow(i);  
		    for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) { 
		    	Cell cell =row.getCell(j);
		    	if(cell==null){
		    		continue;
		    	}
		    	String value = cell.toString().trim();
		    	if(Cell.CELL_TYPE_NUMERIC==cell.getCellType()){
		    		value = getRightStr(cell.getNumericCellValue()+"");
		    	}
		        
		        if(StringUtils.isNotBlank(value)){
		        	list.add(value);
		        }
		    }  
		}
		return list;
	}
	
	private static String getRightStr(String sNum)
    {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String resultStr = decimalFormat.format(new Double(sNum));
        if (resultStr.matches("^[-+]?\\d+\\.[0]+$"))
        {
            resultStr = resultStr.substring(0, resultStr.indexOf("."));
        }
        return resultStr;
    }
	
	public static void main(String[] args) {
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");  
		    TimeZone t = sdf.getTimeZone();  
		    t.setRawOffset(0);  
		    sdf.setTimeZone(t);  
		    Long startTime = System.currentTimeMillis();  
		    String fileName = "E:/tet.xlsx";
		    try {  
//		    	excImport(new FileInputStream(fileName)); 
		    	excImport(fileName); 
		    } catch (Exception ex) {  
		    	System.out.println(ex);
		    }  
		    Long endTime = System.currentTimeMillis();  
		    System.out.println("用时：" + sdf.format(new Date(endTime - startTime)));  
	}
}
