package com.lvmama.passport.processor.impl;

import com.lvmama.awcode.codecs.CodeMap;
import com.lvmama.comm.bee.vo.Passport;

/**
 * LVMAMA AW(T-CODE)码服务</br>
 * 
 * AWCode 编码机制由Alex.wang(王正艳)著作，历时1个月有余，主要解决了图形二维码发送到客户手机<br>
 * 到达率的问题， AWCode的明显优点是：码机制是纯文本，在客户手机没有连接互联网的情况下，也可收<br>
 * 到“二维码”，从而可实现设备扫描通关，无需手工输入通关码。
 * 
 * @author zhangkexing
 * 
 */
public class LvmamaAWProcessorImpl extends LvmamaProcessorImpl {
	
	@Override
	public String getCode(Passport passport) {
		return CodeMap.getInstance().encode(passport.getAddCode());
	}

}
