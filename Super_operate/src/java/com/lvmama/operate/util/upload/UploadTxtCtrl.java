package com.lvmama.operate.util.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.api.Textbox;

import com.lvmama.comm.utils.RandomFactory;


public class UploadTxtCtrl extends UploadCtrl {
     /**
      * 
      */
     private static final long serialVersionUID = -7631852639202352270L;

     /**
      * 把上传后的URL置入传入的textbox
      * @param event
      * @param furl
      */
     public void onUpload(UploadEvent event, Textbox furl) {
          try{
               Media media = event.getMedia();
               byte[] bytes = media.getStringData().getBytes();
               File file = writeFile(bytes);
               if (file!=null) {
                    String url = file.getAbsolutePath();
                    System.out.println("+++++++:"+url);
                    if (url!=null) {
                         furl.setValue(url);
                    }
               }
          }catch(Exception e) {
               e.printStackTrace();
          }
     }
     
     private File writeFile(byte[] bytes) {
          String filename = System.getProperty("java.io.tmpdir")
               +System.getProperty("file.separator")
               +System.currentTimeMillis()
               +"_"
               +RandomFactory.generateMixed(5);
          try{
               File file =new File(filename);
               FileOutputStream fout = new FileOutputStream(file);
               fout.write(bytes);
               fout.close();
               return file;
          }catch(IOException e) {
               e.printStackTrace();
          }
          return null;
     }     
}
