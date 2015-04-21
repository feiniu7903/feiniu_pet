package com.lvmama.back.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;
import com.lvmama.comm.pet.service.prod.ProductProductPlaceService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ContainerProductVO;

public class ProductProcesser implements MessageProcesser {
	private ProdContainerProductService prodContainerProductService;
	private ProdProductService prodProductService;
	public ProdProductService getProdProductService() {
		return prodProductService;
	}


	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	private ProdProductPlaceService prodProductPlaceService;
	private ProductProductPlaceService productProductPlaceService;
	public void process(Message message) {
		try {
			Long productId = message.getObjectId();
			if(message.isProductProductChangeMsg()){
				caculateProductProductPlace(message.getObjectId());
			}
			if (message.isProductOnoffMsg() || message.isProductCreateMsg() || message.isProductChangeMsg() || message.isProductProductChangeMsg()) {
				if(message.isProductOnoffMsg()){
					caculateProductProductPlace(message.getObjectId());
				}
				
				ContainerProductVO containerProductVO = prodContainerProductService.createContainerProductVO(productId);
				if (containerProductVO.getProductId() != null) {
					if (message.isProductCreateMsg() && this.isFrontend(containerProductVO)) {
						withCheckInsertContainerData(containerProductVO);
					}
					if (message.isProductOnoffMsg()) {
						if (containerProductVO.isOnline() && this.isFrontend(containerProductVO)) {
							withCheckInsertContainerData(containerProductVO);
						}else{
							prodContainerProductService.deleteContainerProduct(null, productId);
						}
						
					}
					if (message.isProductChangeMsg()) {
						if ("N".equals(containerProductVO.getIsValid()) || !this.isFrontend(containerProductVO)) {
							prodContainerProductService.deleteContainerProduct(null, productId);
						} else {
							withCheckInsertContainerData(containerProductVO);
						}
					}
					if (message.isProductProductChangeMsg()) {
						prodContainerProductService.deleteFromCorrespondingContainers(containerProductVO);
						withCheckInsertContainerData(containerProductVO);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		
	}
	private void caculateProductProductPlace(Long productId){
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("productId", productId);
//		if(productSearchInfoService.countProductSearchInfoByParam(param)>0){ modify by yanggan 解决PLACE_SEARCH_IFNO中的TICKET_NUM,HOTEL_NUM为0的情况（销售产品偶尔标的绑定不上）的问题
			List<ProdProductPlace> prodProductPlaceList= prodProductPlaceService.getProdProductPlaceListByProductId(productId);
			productProductPlaceService.updateProductProductPlaceByProductId(productId, prodProductPlaceList);
//		}
		
	}
	private void withCheckInsertContainerData(ContainerProductVO containerProductVO){
		this.insertTagContainerProduct(containerProductVO);
		prodContainerProductService.insertIntoCorrespondingContainers(containerProductVO, true);
	}

	public void setProdContainerProductService(
			ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}
	private boolean isFrontend(ContainerProductVO containerProductVO){
		return containerProductVO.getChannel() != null && containerProductVO.getChannel().indexOf("FRONTEND") >= 0;
	}
	
	private void insertTagContainerProduct(ContainerProductVO containerProductVO) {
		List<Long> productTagIds = containerProductVO.getProductTagIds();
	    if (productTagIds.contains(Constant.TAG_ID_ON_SALE)) {
	    	prodContainerProductService.insertTagContainerProduct(containerProductVO.getProductId(), Constant.TAG_ID_ON_SALE);
	    }
	    if (productTagIds.contains(Constant.TAG_ID_NEW_ARRIVAL)) {
	    	prodContainerProductService.insertTagContainerProduct(containerProductVO.getProductId(), Constant.TAG_ID_NEW_ARRIVAL);
        }
	}


	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}


	public void setProductProductPlaceService(
			ProductProductPlaceService productProductPlaceService) {
		this.productProductPlaceService = productProductPlaceService;
	}

}
