package com.lvmama.comm.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

public class ObjectUtil {
	
	/**
	 * 将二进制转化成对象
	 * @param bytes
	 * @return
	 */
	public static Object ByteToObject(byte[] bytes) {
		Object obj = null;
		try {
			if (bytes!=null) {
				ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
				ObjectInputStream oi = new ObjectInputStream(bi);
				obj = oi.readObject();
				bi.close();
				oi.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 将对象转化成二进制
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] ObjectToByte(java.lang.Object obj) {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream outObj = new ObjectOutputStream(byteOut);
			outObj.writeObject(obj);

			bytes = byteOut.toByteArray();
			
			byteOut.close();
			outObj.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (bytes);
	}
	
	/**
	 * 将对象转化成Blob
	 * 
	 * @param obj
	 * @return
	 */
	public static Blob ObjectToBlob(Object obj) {
		Blob blob = null;
		
		try {
			blob = new SerialBlob(ObjectToByte(obj));
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return blob;
	}
	
	/**
	 * 判断一个String 对象不为空字符串和空值
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if("".equals(obj.toString()) || "null".equals(obj.toString())) {
			return true;
		}
		
		return false;
	}
}
