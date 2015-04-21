package com.lvmama.push.util;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MD5;
import com.lvmama.push.model.ClientSessionInfo;

public class SyncDataRunnable implements Runnable {
	private final Log log = LogFactory.getLog(this.getClass());
	private ClientSessionInfo csi  ;
	public SyncDataRunnable(ClientSessionInfo csi){
		this.csi = csi;
	}
	@Override
	public void run() {
		
	}

}
