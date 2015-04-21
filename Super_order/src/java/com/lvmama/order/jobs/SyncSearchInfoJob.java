package com.lvmama.order.jobs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
import com.lvmama.comm.search.SearchConstants.LUCENE_INDEX_TYPE;
import com.lvmama.comm.vo.Constant;
/**
 * 同步更新PLACE_SEARCH_INFO,PRODUCT_SEARCH_INFO,PROD_BRANCH_SEARCH_INFO
 * 
 * @author yanggan
 *
 */
public class SyncSearchInfoJob implements Runnable {
	
	private static final Log log = LogFactory.getLog(AutoPerformOrderJob.class);
	
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	
	private TopicMessageProducer resourceMessageProducer;
	
	/**
	 * 定时根据COM_SEARCH_INFO_UPDAE表中的数据
	 * 批量更新PRODUCT_SEARCH_INFO,PROD_BRANCH_SEARCH_INFO,PLACE_SEARCH_INFO的数据
	 */
	@Override
	public void run() {
		Constant constant = Constant.getInstance();
		String syncSearchInfoJobStr = constant.getValue("syncSearchInfoJob.job.enabled");
		boolean syncSearchInfoJob = false;
		if(!StringUtils.isEmpty(syncSearchInfoJobStr) && "true".equals(syncSearchInfoJobStr)){
			syncSearchInfoJob=true;
		}
		if(constant.isJobRunnable() && syncSearchInfoJob){
			log.info("bigen run auto BatchUpdateSearchInfoJob");
			
			log.info("SYNC PROD_BRANCH_SEARCH_INFO....");
			//批量更新PROD_BRANCH_SEARCH_INFO
			comSearchInfoUpdateService.syncProdBranchSearchInfo();
			List<Long> branchIds = comSearchInfoUpdateService.deleteUpdated("PROD_BRANCH_SEARCH_INFO");
			if(branchIds.size() > 0 ){
				//发送更新PROD_BRANCH索引的消息
				resourceMessageProducer.sendMsg(MessageFactory.newLuceneUpdateMessage(LUCENE_INDEX_TYPE.PRODUCT_BRANCH,branchIds.toArray(new Long[branchIds.size()])));
			}
			log.info("SYNC PROD_BRANCH_SEARCH_INFO success.");
			
			log.info("SYNC PRODUCT_SEARCH_INFO....");
			//批量更新PRODUCT_SEARCH_INFO的数据
			comSearchInfoUpdateService.syncProductSearchInfo();
			List<Long> productIds = comSearchInfoUpdateService.deleteUpdated("PRODUCT_SEARCH_INFO");
			if(productIds.size() > 0 ){
				//发送更新PRODUCT索引的消息
				resourceMessageProducer.sendMsg(MessageFactory.newLuceneUpdateMessage(LUCENE_INDEX_TYPE.PRODUCT,productIds.toArray(new Long[productIds.size()])));
			}
			log.info("SYNC PRODUCT_SEARCH_INFO success.");
			log.info("SYNC PLACE_SEARCH_INFO....");
			//批量更新PROD_BRANCH_SEARCH_INFO
			comSearchInfoUpdateService.syncPlaceSearchInfo();
			List<String> placeIdExtCols = comSearchInfoUpdateService.deleteUpdatedWithExtCol("PLACE_SEARCH_INFO");
			if(placeIdExtCols.size() > 0 ){
				List<Long> hotelIds = new ArrayList<Long>();
				List<Long> placeIds = new ArrayList<Long>();
				for(String s : placeIdExtCols ){
					String[] ss = s.split(",");
					if(ss.length == 2 && ss[1].equals("3")){
						hotelIds.add(Long.parseLong(ss[0]));
					}else{
						placeIds.add(Long.parseLong(ss[0]));
					}
				}
				if(hotelIds.size() > 0 ){
					//发送更新PLACE_HOTEL索引的消息
					resourceMessageProducer.sendMsg(MessageFactory.newLuceneUpdateMessage(LUCENE_INDEX_TYPE.PLACE_HOTEL,hotelIds.toArray(new Long[hotelIds.size()])));
				}
				if (placeIds.size() > 0){
					//发送更新PLACE索引的消息
					resourceMessageProducer.sendMsg(MessageFactory.newLuceneUpdateMessage(LUCENE_INDEX_TYPE.PLACE,placeIds.toArray(new Long[placeIds.size()])));
				}
			
			}
			log.info("SYNC PLACE_SEARCH_INFO success.");
			log.info("end run auto BatchUpdateSearchInfoJob");
		}
	}

	public void setComSearchInfoUpdateService(ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}

	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

}
