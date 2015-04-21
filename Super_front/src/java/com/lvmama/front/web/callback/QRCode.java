package com.lvmama.front.web.callback;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;


  public class QRCode {
      private static final int BLACK = 0xff000000;
      private static final int WHITE = 0xFFFFFFFF;

      /**
      * 生成QRCode二维码<br>
       * 在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的<br>
       *  static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";<br>
       *  修改为UTF-8，否则中文编译后解析不了<br>
       */
      public static void encode(String contents, OutputStream out, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
         try {
              contents=new String(contents.getBytes("UTF-8"),"ISO-8859-1");//如果不想更改源码，则将字符串转换成ISO-8859-1编码
              BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height);
              writeToStream(bitMatrix, "png", out);
          } catch (Exception e) {
              e.printStackTrace();
          }
     }

      /**
      * 生成二维码图片<br>
       *
       * @param matrix
       * @param format
       *            图片格式
       * @param file
       *            生成二维码图片位置
       * @throws IOException
       */
      public static void writeToStream(BitMatrix matrix, String format, OutputStream out) throws IOException {
          BufferedImage image = toBufferedImage(matrix);
          ImageIO.write(image, format, out);
      }

      /**
       * 生成二维码内容<br>
       *
       * @param matrix
       * @return
       */
      public static BufferedImage toBufferedImage(BitMatrix matrix) {
          int width = matrix.getWidth();
         int height = matrix.getHeight();
          BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
         for (int x = 0; x < width; x++) {
             for (int y = 0; y < height; y++) {
                  image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
              }
          }
          return image;
     }
   }