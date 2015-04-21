package com.lvmama.comm.bee.service.ebooking;

import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdPlace;

public interface EbkProdPlaceService {
	/**
	 * EBK产品管理 >产品景点地区 2013-9-25
	 */
	/**
     * 获取对象列表
     * @param ebkProdPlaceDO
     * @return 对象列表
     */
    public List<EbkProdPlace> findListByTerm(EbkProdPlace ebkProdPlaceDO) ;
    
    public int deleteListByProductId(final Long ebkProductId);
    
    public EbkProdPlace insert(EbkProdPlace ebkProdPlaceDO);
}
