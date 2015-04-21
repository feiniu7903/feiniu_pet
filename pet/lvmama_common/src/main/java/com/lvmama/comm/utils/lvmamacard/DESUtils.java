package com.lvmama.comm.utils.lvmamacard;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * des加密解密
 * @author nixianjun 2013/11/26
 *
 */
public class DESUtils {  
     
  private  Key key;  
  private static DESUtils instance;
  private static final String PRIKEY="ghsiuepf8ur98cvjhd9k5tg845343wufsdujfklasetf";
  
  private DESUtils(String str) {  
    setKey(str);//生成密匙  
  }  
  
  private DESUtils() {  
    setKey(PRIKEY);  //这个常数不能修改
  }
  public static DESUtils getInstance(){
	if(null==instance){
		instance=new DESUtils();
	}
	return instance;
	  
  }
  
  /** 
   * 根据参数生成KEY 
   */  
	public void setKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			/*
			 * _generator.init(new SecureRandom(strKey.getBytes())); this.key =
			 * _generator.generateKey(); _generator = null;
			 */
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			_generator.init( secureRandom);
			this.key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing lvmamacard private key. Cause: " + e);
		}
	}
  
  /** 
   * 加密String明文输入,String密文输出 
   */  
  public String getEncString(String strMing) {  
      byte[] byteMi = null;  
      byte[] byteMing = null;  
      String strMi = "";  
      BASE64Encoder base64en = new BASE64Encoder();  
      try {  
        byteMing = strMing.getBytes("UTF8");  
        byteMi = this.getEncCode(byteMing);  
        strMi = base64en.encode(byteMi);  
      } catch (Exception e) {  
        throw new RuntimeException(  
            "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
        base64en = null;  
        byteMing = null;  
        byteMi = null;  
      }  
      return strMi;  
  }  
  
  /** 
   * 解密 以String密文输入,String明文输出 
   * @param strMi 
   * @return 
   */  
  public String getDesString(String strMi) {  
      BASE64Decoder base64De = new BASE64Decoder();  
      byte[] byteMing = null;  
      byte[] byteMi = null;  
      String strMing = "";  
      try {  
        byteMi = base64De.decodeBuffer(strMi);  
        byteMing = this.getDesCode(byteMi);  
        strMing = new String(byteMing, "UTF8");  
      } catch (Exception e) {  
        throw new RuntimeException(  
            "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
        base64De = null;  
        byteMing = null;  
        byteMi = null;  
      }  
      return strMing;  
  }  
  
  /** 
   * 加密以byte[]明文输入,byte[]密文输出 
   * @param byteS 
   * @return 
   */  
  private byte[] getEncCode(byte[] byteS) {  
      byte[] byteFina = null;  
      Cipher cipher;  
      try {  
        cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.ENCRYPT_MODE, key);  
        byteFina = cipher.doFinal(byteS);  
      } catch (Exception e) {  
        throw new RuntimeException(  
            "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
        cipher = null;  
      }  
      return byteFina;  
  }  
  
  /** 
   * 解密以byte[]密文输入,以byte[]明文输出 
   * @param byteD 
   * @return 
   */  
  private byte[] getDesCode(byte[] byteD) {  
      Cipher cipher;  
      byte[] byteFina = null;  
      try {  
        cipher = Cipher.getInstance("DES");  
        cipher.init(Cipher.DECRYPT_MODE, key);  
        byteFina = cipher.doFinal(byteD);  
      } catch (Exception e) {  
        throw new RuntimeException(  
            "Error initializing SqlMap class. Cause: " + e);  
      } finally {  
        cipher = null;  
      }  
      return byteFina;  
  }  
  
  public static void main(String args[]) {  
	  System.out.println("ghsiuepf8ur98cvjh823498qewufsdujfklasetf".length());
      String str1 = "34734735";  
      //DES加密  
      String str2 = DESUtils.getInstance().getEncString(str1);  
      String deStr = DESUtils.getInstance().getDesString(str2);  
      System.out.println("密文:" + str2);  
      //DES解密  
      System.out.println("明文:" + deStr);  
  }
}
