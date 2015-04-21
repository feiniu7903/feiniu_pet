package com.lvmama.clutter.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class HashFile {
	private static char[] hexChar = { '0', '1', '2', '3', 
	    '4', '5', '6', '7', 
	    '8', '9', 'a', 'b', 
	    'c', 'd', 'e', 'f' };

	  public static String getFileMD5(String filename)
	  {
	    String str = "";
	    try {
	      str = getHash(filename, "MD5");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static String getFileSHA1(String filename)
	  {
	    String str = "";
	    try {
	      str = getHash(filename, "SHA1");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static String getFileSHA256(String filename)
	  {
	    String str = "";
	    try {
	      str = getHash(filename, "SHA-256");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static String getFileSHA384(String filename)
	  {
	    String str = "";
	    try {
	      str = getHash(filename, "SHA-384");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  public static String getFileSHA512(String filename) {
	    String str = "";
	    try {
	      str = getHash(filename, "SHA-512");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return str;
	  }

	  private static String getHash(String fileName, String hashType)
	    throws Exception
	  {
	    InputStream fis = new FileInputStream(fileName);
	    byte[] buffer = new byte[1024];
	    MessageDigest md5 = MessageDigest.getInstance(hashType);
	    int numRead = 0;
	    while ((numRead = fis.read(buffer)) > 0) {
	      md5.update(buffer, 0, numRead);
	    }
	    fis.close();
	    return toHexString(md5.digest());
	  }

	  private static String toHexString(byte[] b) {
	    StringBuilder sb = new StringBuilder(b.length * 2);
	    for (int i = 0; i < b.length; i++) {
	      sb.append(hexChar[((b[i] & 0xF0) >>> 4)]);
	      sb.append(hexChar[(b[i] & 0xF)]);
	    }
	    return sb.toString();
	  }
}
