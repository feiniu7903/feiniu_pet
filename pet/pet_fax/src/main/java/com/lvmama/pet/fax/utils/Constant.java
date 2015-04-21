package com.lvmama.pet.fax.utils;

import java.util.ResourceBundle;

public class Constant {
	
	private static ResourceBundle resourceBundle;
	
	static {
		resourceBundle = ResourceBundle.getBundle("const");
	}
	
	public final static String getTempDirectory() {
		return resourceBundle.getString("TEMP_DIRECTORY");
	}
	
	public final static String getFaxDirectory() {
		return resourceBundle.getString("FAX_DIRECTORY");
	}
	
	public final static String getFaxSendBackDirectory() {
		return resourceBundle.getString("FAX_SEND_BACK_DIRECTORY");
	}
	public final static String getSendStatusDirectory() {
		return resourceBundle.getString("STATUS_DIRECTORY");
	}
	
	public final static String getRecvDirectory() {
		return resourceBundle.getString("RECV_DIRECTORY");
	}
	
	public final static String getNotifyUrl() {
		return resourceBundle.getString("CC_NOTIFY_URL");
	}
	
	public final static String getSuperNotifyErrorUrl() {
		return resourceBundle.getString("SUPER_NOTIFY_ERROR_URL");
	}
	public final static String getVstNotifyErrorUrl() {
		return resourceBundle.getString("VST_NOTIFY_ERROR_URL");
	}
	public final static String getSuperNotifyUrl() {
		return resourceBundle.getString("SUPER_NOTIFY_URL");
	}
	public final static String getVstNotifyUrl() {
		return resourceBundle.getString("VST_NOTIFY_URL");
	}
	public final static String getSuperFaxrecvUrl() {
		return resourceBundle.getString("SUPER_FAXRECV_URL");
	}
	public final static String getVstFaxrecvUrl() {
		return resourceBundle.getString("VST_FAXRECV_URL");
	}
	public final static String getUploadUrl() {
		return resourceBundle.getString("uploadurl");
	}
	public final static boolean hasTest() {
		String test = resourceBundle.getString("test");
		if (test != null) {
			return "true".equalsIgnoreCase(test)?true:false;
		} else {
			return false;
		}
	}
	public final static boolean isTraFaxServerError() {
		return Boolean.valueOf(resourceBundle.getString("trafaxserverError"));
	}
	public final static boolean isTraFaxServerSend() {
		return Boolean.valueOf(resourceBundle.getString("trafaxserverSend"));
	}
	public final static boolean isImageMagick() {
		return Boolean.valueOf(resourceBundle.getString("IMAGE_MAGICK"));
	}
	public final static boolean isTraFaxServerRecv() {
		return Boolean.valueOf(resourceBundle.getString("trafaxserverRecv"));
	}
	public final static boolean isOracleDB() {
		String dbStatus = resourceBundle.getString("db_status");
		if (dbStatus != null) {
			return "oracle".equalsIgnoreCase(dbStatus)?true:false;
		} else {
			return false;
		}
	}
 
}
