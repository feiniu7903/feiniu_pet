package com.lvmama.order.sms;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComSms;

public interface MultiSmsCreator {

	List<ComSms> createSmsList();
	
}
