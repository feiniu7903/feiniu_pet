package com.lvmama.back.message;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.prod.ProductProductPlaceService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;

/**
 * PRODUCT_SEARCH_INFO,PROD_BRANCH_SEARCH_INFO,PLACE_SEARCH_INFO更新的
 * @author yanggan
 *
 */
public class SearchInfoUpdateProcesser implements MessageProcesser {

	private static final Log log = LogFactory.getLog(SearchInfoUpdateProcesser.class);

	
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	private ProductProductPlaceService productProductPlaceService;
	private PlaceService placeService;
	@Override
	public void process(Message message) {
		log.info("received message:"+message.toString());
		if (message.isProductCreateMsg() || message.isProductChangeMsg() || message.isProductOnoffMsg() || message.isProductProductChangeMsg()) {
			Long productId = message.getObjectId();
			comSearchInfoUpdateService.productUpdated(productId);
			List<Place> list = placeService.getPlaceByProductIdAndStage(productId, 2l);
			for(Place item: list){
				comSearchInfoUpdateService.placeUpdated(item.getPlaceId(),item.getStage());
			}
		}else if (message.isProdBranchItemChangeMsg() ||  message.isProductBranchEditMsg() || message.isChangeSellPriceMsg()) {
			Long prodBranchId = message.getObjectId();
			comSearchInfoUpdateService.productBranchUpdated(prodBranchId);
		}else if(message.isProductBranchOnoffMsg()){
			Long prodBranchId = Long.parseLong(message.getAddition());
			comSearchInfoUpdateService.productBranchUpdated(prodBranchId);
		}
	}
	
	public void setComSearchInfoUpdateService(ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}

	public void setProductProductPlaceService(ProductProductPlaceService productProductPlaceService) {
		this.productProductPlaceService = productProductPlaceService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

}
