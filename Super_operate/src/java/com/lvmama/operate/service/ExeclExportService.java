package com.lvmama.operate.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.operate.util.CityCache;

/**
 * 导出用户的execl的数据,按结果分sheet写入
 * @author yangbin
 *
 */
public class ExeclExportService {

     private EdmUsersService edmUsersService;
     private Map<String,Object> parameters;
     private Workbook wb;
     private long total_size=0;
     private int page_size=65500;//最大页大小 本是65536
     private int startRow=0;
     private int endRow=0;
     public ExeclExportService(EdmUsersService edmUsersService,
               Map<String, Object> parameters) {
          super();
          this.edmUsersService = edmUsersService;
          this.parameters = parameters;
          wb=new HSSFWorkbook();
     }
     
     public void build()
     {
          if(parameters.containsKey("_startRow"))
          {
               parameters.remove("_startRow");               
          }
          if(parameters.containsKey("_endRow"))
          {
               parameters.remove("_endRow");               
          }
          total_size=edmUsersService.selectUsersCount(parameters);
          while(startRow<total_size)
          {               
               parameters.put("_startRow", startRow);
               parameters.put("_endRow", endRow+=page_size);               
               List<UserUser> list=edmUsersService.selectUsers(parameters);
               if(CollectionUtils.isNotEmpty(list))
               {
                    Sheet sheet=wb.createSheet();
                    initHeader(sheet);
                    int pos=1;
                    for(UserUser u:list)
                    {
                         Row row=sheet.createRow(pos++);
                         insertRow(row,u);
                    }
               }
               startRow+=page_size;
          }
          
     }
     private void initHeader(Sheet sheet)
     {
          Row row=sheet.createRow(0);
          for(int i=0;i<headers.length;i++)
          {
               Cell cell=row.createCell(i);
               cell.setCellValue(headers[i]);
          }
     }
     
     public long getTotal()
     {
          return total_size;
     }
     
     static final String[] headers={"用户名","省份","城市","性别","邮箱","手机"};
     private void insertRow(Row row,UserUser u)
     {
          int p=0;
          Cell cell=row.createCell(p++);
          cell.setCellValue(u.getUserName());
          
          
          ComCity city=CityCache.getInstance().getCity(u.getCityId());
          if(city==null)
          {
               city=edmUsersService.getCity(u.getCityId());
               CityCache.getInstance().putCity(city);
          }
          Cell cellCapital=row.createCell(p++);
          Cell cellCity=row.createCell(p++);
          if(city!=null)
          {
               cellCity.setCellValue(city.getCityName());
               
               ComProvince capital=CityCache.getInstance().getCapital(city.getProvinceId());
               if(capital==null)
               {
                    capital=edmUsersService.getProvince(city.getProvinceId());
                    CityCache.getInstance().putCapital(capital);
               }
               if(capital!=null)
               {
                    cellCapital.setCellValue(capital.getProvinceName());
               }
          }
          
          cell=row.createCell(p++);
          if(StringUtils.isNotEmpty(u.getGender()))
          {
               cell.setCellValue(u.getGender().equalsIgnoreCase("F")?"女":"男");
          }
          cell=row.createCell(p++);
          cell.setCellValue(u.getEmail());
          
          cell=row.createCell(p++);
          cell.setCellValue(u.getMobileNumber());
     }
     
     public void output(OutputStream os)throws IOException
     {
          wb.write(os);
     }
     
}
