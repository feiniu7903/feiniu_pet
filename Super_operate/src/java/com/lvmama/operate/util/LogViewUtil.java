package com.lvmama.operate.util;

import org.apache.commons.lang3.StringUtils;

public class LogViewUtil {
     public static String logIsEmptyStr(String str){   
          if(!"".equals(str)&&str!=null){
               return str;
          }
          return "";
     }
     public static String logNewStrByColumnName(String columnName,String oldStr){   
          if(isEmptyString(oldStr)){
               oldStr="";
          }
          String str="创建了 ["+columnName+"] ,新值为  "+oldStr+";";
          return str;
     }
     
     public static String logEditStr(String columnName,String oldStr,String newStr){   
          if(isEmptyString(oldStr)){
               oldStr="";
          }
          if(isEmptyString(newStr)){
               newStr="";
          }
          String str="修改了 ["+columnName+"] ,旧值为  \""+oldStr+"\",新值为  \""+newStr+"\";";
          return str;
     }
     
     public static String logEditStr(String columnName,String newStr){   
          if(isEmptyString(newStr)){
               newStr="";
          }
          String str="修改了 ["+columnName+"] ,新值为  \""+newStr+"\";";
          return str;
     }
     
     public static String logNewStr(String newStr){   
          if(isEmptyString(newStr)){
               newStr="";
          }
          String str="由【"+newStr+"】创建;";
          return str;
     }
     
     public static String logDeleteStr(String newStr){   
          if(isEmptyString(newStr)){
               newStr="";
          }
          String str="由【"+newStr+"】删除;";
          return str;
     }
     public static String subStringStr(String str,int size)
     {
          if(StringUtils.isEmpty(str))
               return "";
          if(str.length()<size)
               return str;
          
          String lastString = str.substring(0,size)+"...";
          return lastString;
     }
     /**
      * 判断传入的字符串是否为空串
      * 
      * @return
      */
     public static boolean isEmptyString(String str) {
          return str == null ? true : str.trim().equals("") ? true : false;
     }
     
}
