package com.lvmama.passport.processor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;

/**
 * 第三方通信处理工厂
 * 
 * @author chenlinjun
 * 
 */
public class ProcessorFactory {
	private static final Log log = LogFactory.getLog(ProcessorFactory.class);
	private ProcessorFactory(){}
	
	public static Processor create(PassCode passCode) {
		List<PassPortCode> passPortList = passCode.getPassPortList();
		if (passPortList.size()>0) {
			PassPortCode passPortCode = passPortList.get(0);
			String processorClassname = passPortCode.getProcessor();
			try {
				processorClassname = processorClassname.trim();
				Processor processor = (Processor) Class.forName(processorClassname).newInstance();
				return processor;
			} catch (Exception e) {
				log.error("Create ProcessorAdapter Exception ", e);
			}
		}
		return null;
	}
	
}
