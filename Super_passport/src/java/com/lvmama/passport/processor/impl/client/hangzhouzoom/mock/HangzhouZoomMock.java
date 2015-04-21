package com.lvmama.passport.processor.impl.client.hangzhouzoom.mock;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
public class HangzhouZoomMock {
	private Properties properties;
	public HangzhouZoomMock(){
		properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/const.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isMockEnabled() {
		String enableHotelMock = properties.getProperty("actualApplyPassCode.enabled");
		if (StringUtils.equals(enableHotelMock,"false")) {
			return true;
		}else{
			return false;
		}
	}

	public String applyCodeRequest() throws Exception {
		return "{\"orderId\":\"987654\"}";
	}

	public String destoryCodeRequest() throws Exception {
		return "{\"done\":\"1\"}";
	}
}
