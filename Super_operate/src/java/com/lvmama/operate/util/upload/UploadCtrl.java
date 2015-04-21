package com.lvmama.operate.util.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.vo.Constant;


/**
 * 通用图片上传组件，可回传图片文件名，存放URL，如果传入Image对象则可以预览
 * 
 * @author Alex Wang
 * 
 */
public class UploadCtrl extends GenericForwardComposer {
     private static final long serialVersionUID = -5067328176187541336L;
     public void doAfterCompose(Component comp) throws Exception {
          super.doAfterCompose(comp);
     }
     
     /**
      * 
      * @param is
      * @return
      */
     

     /**
      * 把指定的文件上传到专用的静态文件服务器上，返回URL
      * 
      * @param file
      * @return
      */
     public String postToRemote(File f, String filename) {
          try{
               String ext = filename.substring(filename.lastIndexOf('.')).toLowerCase();
               PostMethod filePost = new PostMethod(Constant.getInstance().getUploadUrl());
               String fileName = "super/"+DateUtil.getFormatDate(new Date(), "yyyy/MM/")+RandomFactory.generateMixed(5)+ext;
               Part[] parts = { new StringPart("fileName", fileName), new FilePart("ufile", f) };
               filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
               HttpClient client = new HttpClient();
               int status = client.executeMethod(filePost);
               if (status == 200) {
                    System.out.println("upload success");
                    return fileName;
               } else {
                    System.out.println("ERROR, return: " + status);
               }
               }catch(IOException e) {
                    e.printStackTrace();
               }
          return null;
     }
     
     /**
      * 判断文件是否大于指定大小
      * @param file
      * @return
      */
     public boolean checkImgSize(File f,int picSize){
          FileInputStream fis;
          try {
               fis = new FileInputStream(f);
               long size = fis.available();
               System.out.println(size);
               if(size>picSize*1024){
                    return true;
               }else
                    return false;
          } catch (IOException e) {
               e.printStackTrace();
          }
          return false;
     }

}
