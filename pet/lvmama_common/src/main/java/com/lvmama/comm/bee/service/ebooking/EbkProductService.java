package com.lvmama.comm.bee.service.ebooking;

import java.util.Date;


public interface EbkProductService {

	/**
	 * 保存产品信息
	 * @param ebkProdProductId ebk产品id
	 * @param onlineTime 上线时间
	 * @param offlineTime 下线时间
	 * @param online 是否上线
	 */
	public void saveProduct(Long ebkProdProductId,Date onlineTime,Date offlineTime,boolean online) throws Exception;
	
	

}
