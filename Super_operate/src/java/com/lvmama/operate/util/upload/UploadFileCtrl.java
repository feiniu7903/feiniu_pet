package com.lvmama.operate.util.upload;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Pattern;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.api.Textbox;

import com.lvmama.operate.util.Constant;
import com.lvmama.operate.util.FtpUtil;
import com.lvmama.operate.util.ZkMessage;

public class UploadFileCtrl extends UploadCtrl {
     /**
      * 
      */
     private static final long serialVersionUID = 2518892299165290577L;

     public void onUpload(UploadEvent event, Textbox furl) {
          try {
               Media media = event.getMedia();
               String filename = media.getName();
               String fixname=filename.substring(filename.lastIndexOf('.')).toLowerCase();
               byte[] bytes =null;
               File file = null;
               try{
                    bytes = media.getByteData();
                    if(null!=bytes && bytes.length>0){
                         file = writeFile(bytes,filename,fixname);
                    }else{
                         InputStream is = media.getStreamData();
                         file = writeFile(is,filename);
                    }
               }catch(Exception e){
                    Reader reader=media.getReaderData();
                    char[] cbuf=new char[1024];
                    StringBuffer stre=new StringBuffer();
                    int i=-1;
                    while((i=reader.read(cbuf))!=-1){
                         stre.append(cbuf);
                         cbuf=new char[1024];
                    }
                    bytes = stre.toString().replaceFirst(Pattern.quote("?"), "").trim().getBytes();
                    file = writeFile(bytes,filename,fixname);
               }
               if (file != null) {
                         String fileurl = file.getAbsolutePath();
                         String url =(new FtpUtil()).uploadFile(fileurl);
                    if (url != null) {
                         furl.setValue(url);
                    }
               }
          } catch (Exception e) {
               ZkMessage.showError("上传文件报错：" + e);
          }
     }
     
     private File writeFile(InputStream is,final String fn) {
          String filename = System.getProperty("java.io.tmpdir")
          + System.getProperty("file.separator")
          + fn;
          try{
               File file =new File(filename);
               FileOutputStream fout = new FileOutputStream(file);
               byte buffer[] = new byte[1024];
               while (is.read(buffer)!=-1) {
                    fout.write(buffer);
               }
               fout.close();
               return file;
          }catch(IOException e) {
               ZkMessage.showError("上传文件报错：" + e);
          }
          return null;
     }
     public File writeFile(final byte[] bytes,final String filename,final String fixname) {
          String fn = System.getProperty("java.io.tmpdir")
                    + System.getProperty("file.separator")
                    + filename;
          try {
               File file = new File(fn);
               FileOutputStream fout = new FileOutputStream(file);
               if(fixname.matches("^.*[html|htm|txt]$")){
                   Writer out = new BufferedWriter(new OutputStreamWriter(fout,Constant.EN_CODEING));
                   out.write((new String(bytes,Constant.EN_CODEING)).replaceFirst(Pattern.quote("?"), ""));
                   out.close();
               }else{
               // byte buffer[] = new byte[1024];
               // while (is.read(buffer)!=-1) {
                    fout.write(bytes);
               // }
               }
               fout.close();
               return file;
          } catch (IOException e) {
               ZkMessage.showError("上传文件报错：" + e);
          }
          return null;
     }     
}
