package com.lvmama.comm.jms;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SSOMessageConsumer {
	private static final Log LOG = LogFactory.getLog(SSOMessageConsumer.class);
	
	private List<SSOMessageProcesser> processerList = new ArrayList<SSOMessageProcesser>();
	
	public void receive(SSOMessage m) {
		LOG.info(m);
		for(int i=0;i < processerList.size();i++) {
			try {
				processerList.get(i).process(m);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setProcesserList(List<SSOMessageProcesser> processerList) {
		this.processerList = processerList;
	}
}
