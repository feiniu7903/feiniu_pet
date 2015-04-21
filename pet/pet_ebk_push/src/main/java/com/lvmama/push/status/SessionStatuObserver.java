package com.lvmama.push.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.comm.bee.service.ebkpush.IEbkPushService;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.pet.po.ebkpush.EbkPushMessage;
import com.lvmama.comm.pet.po.ebkpush.ModelUtils;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.push.model.ClientSessionInfo;
import com.lvmama.push.util.ConstantPush;
import com.lvmama.push.util.DataSyncThreadPoolExecutor;
import com.lvmama.push.util.SyncLogicUtils;


public class SessionStatuObserver implements Observer{

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionStatuObserver.class);
	LinkedList<ClientSessionInfo> csiQueue = new LinkedList<ClientSessionInfo>();
	private static SessionStatuObserver instance;
	
  
	public static SessionStatuObserver getInstance() {  
    	if (instance ==null){
    		synchronized (DataSyncThreadPoolExecutor.class) {
    			instance = new SessionStatuObserver();
    		
			}
    	}
    	return instance;
    }
	
	@Override
  	public void update(Observable arg0, Object arg1) {
	// TODO Auto-generated method stub
	  try {
		//等待 15秒
		Thread.sleep(15000);
		synchronized (SessionStatuObserver.class) {
		 final ClientSessionInfo csi =(ClientSessionInfo)arg0;
		if(ConstantPush.CLIENT_SESSION_STATUS.ONLINE.name().equals(csi.getState()) ) {
			
			IEbkPushService ebkPushService = (IEbkPushService)SpringBeanProxy.getBean("ebkPushService");
			OrderPerformService orderPerformProxy  = (OrderPerformService) SpringBeanProxy.getBean("orderPerformProxy");
			// TODO Auto-generated method stub
			long pageSize =200;
			//分页查询待同步的数据
			Page<EbkPushMessage> page  = ebkPushService.selectPushFailedMessage(csi.getUdid(),1L,pageSize);

			page.setTotalResultSize(page.getTotalResultSize());
			LOGGER.info("共找到"+page.getTotalResultSize()+"条数据");
			if(page.getTotalResultSize()>0){

			
		for (int i = 1; i <= page.getTotalPages(); i++) {
				Page<EbkPushMessage> pageConfig  = ebkPushService.selectPushFailedMessage(csi.getUdid(),Long.valueOf(i),pageSize);
				LOGGER.info("分页："+i+" 获得 "+pageConfig.getItems().size()+" 条数据");
				List<Long> pushIds = new ArrayList<Long>();
				List<OrdOrderPerformResourceVO> performResourcesList = new ArrayList<OrdOrderPerformResourceVO>();
				for (final EbkPushMessage ebkPushMessage : pageConfig.getItems()) {
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("udid", csi.getUdid());
						param.put("addCode", ebkPushMessage.getAddInfo());
						pushIds.add(ebkPushMessage.getId());
						List<OrdOrderPerformResourceVO> performResources = orderPerformProxy.queryOrderPerformByEBK(param);
						if (performResources!=null &&performResources.size()!=0){
							performResourcesList.add(performResources.get(0));
						}

				}

			IoSession session = csi.getSession();
			Map<String,Object> datas = new HashMap<String,Object>();
			datas.put("command", ConstantPush.PUSH_MSG_TYPE.SYNC_DATAS.name());
			datas.put("datas", ModelUtils.buildSendDatas(performResourcesList));
			SyncLogicUtils.sync(session, datas, pushIds, csi.getUdid());
			/**
			 * sleep 20秒
			 */
			try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		}
		}
		}
		} catch(Exception ex){
		 ex.printStackTrace();
		}
	  
  }

}
