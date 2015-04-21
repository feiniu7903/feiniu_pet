package com.lvmama.back.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.tmall.OrdTmallMap;
import com.lvmama.comm.bee.service.DownTmallOrderInterface;
import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.taobao.api.response.TradeFullinfoGetResponse;

public class DownOrderTmallJob {
	
	private static final Log log = LogFactory.getLog(CreateTmallOrderJob.class);
	
	private DownTmallOrderInterface downTmallOrderInterface;
	private  OrdTmallMapService ordTmallMapService; 

	/***
	 * 由于淘宝订单会出现一对多的情况，即淘宝订单id可能会出现多个相同的，所以分批处理每次 只处理一条然后 根据状态进行过滤
	 */
	public void run() {
		if (Constant.getInstance().isJobRunnable() && Constant.getInstance().isSyncTmallOrder()) {
			log.info("DownOrderTmallJob begin run.");
				List<String> lists = ordTmallMapService.selectOrdOfCreate();
				log.info("This time process sum(ordernum) is:"+lists.size());
				String error="";
				if(lists!=null&&lists.size()>0){
					for(int i=0;i<lists.size();i++){
						try{
							TradeFullinfoGetResponse response = TOPInterface.getFullIfo(lists.get(i));// 调淘宝详情接口 得到
							if(response!=null && response.getErrorCode()!=null){
								error = response.getErrorCode()+":"+response.getMsg()+response.getSubCode()+":"+response.getSubMsg();
							}else if(response!=null){
								    log.info("Present Process tmall_order_no is:"+lists.get(i)+";begin time:"+DateUtil.getFormatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
								    long beginTime=System.currentTimeMillis();
									downTmallOrderInterface.backDownOrder(lists.get(i),response);//后台下单
									long endTime=System.currentTimeMillis();
									long costTime=endTime-beginTime;
									log.info("Proces this order cost time is:"+costTime);
									log.info("endTime is:"+DateUtil.getFormatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
							}
						}catch (Exception e){
							log.error(this.getClass(), e);
							error=e.getMessage();
						}
						if(!StringUtil.isEmptyString(error)){
							updateOrder2Failure(lists.get(i),error);	
						}
					}
					
				}
		}
	}

	private void updateOrder2Failure(String tmallOrderNo,String error) {
		OrdTmallMap order = new OrdTmallMap();
		order.setStatus("failure");
		order.setProcessTime(new Date());
		order.setOperatorName("system");
		order.setFailedReason(error);
		ordTmallMapService.updateByOrdSelective(order);
	}

	public void setOrdTmallMapService(OrdTmallMapService ordTmallMapService) {
		this.ordTmallMapService = ordTmallMapService;
	}

	public void setDownTmallOrderInterface(
			DownTmallOrderInterface downTmallOrderInterface) {
		this.downTmallOrderInterface = downTmallOrderInterface;
	}

	
	
}
