package com.lvmama.comm.utils.edm;

/**
 * 工具类:EDM抓取模板
 * author:尚正元
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

import com.lvmama.comm.utils.StringUtil;

public class EdmTemplateGrabUtil {
    private static final Logger LOG = Logger.getLogger(EdmTemplateGrabUtil.class);
    /**
     * 获取模板
     * @param urlStr
     * @return
     * @throws IOException 
     */
     public static String getTemplateContent(final String urlStr) throws Exception{
          StringBuffer document = new StringBuffer();
          BufferedReader reader = null;
          try {
               URL url = new URL(urlStr);
               URLConnection conn = url.openConnection();
               reader = new BufferedReader(new InputStreamReader(
                         conn.getInputStream(),"UTF-8"));
               String line = null;
               while ((line = reader.readLine()) != null){
                    document.append(line + "\r\n");
               }
          }catch (Exception e) {
              LOG.info("EDM获取模板出错:IOException");
              throw e;
          }finally{
        	  if(reader!=null){
        		  reader.close();
        	  }
          }
          return document.toString();
     }
     /**
      * 取得模板title
      * @param content
      * @return
      */
     public static String extractTitle(String content){
          String[] cont1=content.split("<title\\s*[^>]*>");
          if(cont1.length >= 2){
               String[] cont2 = cont1[1].split("</\\s*title\\s*>");
               if(null != cont2[0]){
        	   cont2[0] = cont2[0].trim();
               }
               if(!StringUtil.isEmptyString(cont2[0]) && cont2[0].length()>100){
                    cont2[0] = cont2[0].substring(0, 100);
               }
               if(StringUtil.isEmptyString(cont2[0])){
        	   return "驴妈妈营销邮件";
               }
               return cont2[0];
          }
          return "驴妈妈营销邮件";
     }
}
