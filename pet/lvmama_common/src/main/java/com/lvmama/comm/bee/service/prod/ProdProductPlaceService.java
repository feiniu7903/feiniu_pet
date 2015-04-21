package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.utils.json.ResultHandleT;
/**
 * 标的维护
 * @author MrZhu
 *
 */
public interface ProdProductPlaceService {
	/**
	 * 返回列表
	 * @param productId 销售产品id
	 * @return
	 */
	List<ProdProductPlace> selectByProductId(Long productId);
	
	/**
	 * 新增一条记录
	 * @param prodProductPlace
	 */
	ProdProductPlace insert(ProdProductPlace prodProductPlace,String operatorName);
	
	/**
	 * 删除一条记录
	 * @param prodProductId
	 */
	 void delete(Long prodProductId,String operatorName);
	 
	 List<ProdProductPlace> findProdProductPlace(Long prodProductId);
	 
	 /**
	  * 修改标的为出发或目的地,一次只能设置一个.
	  * @param place
	  */
	 ResultHandleT<ProdProductPlace> changeFT(Long productPlaceId,String ft, String operatorName);
	 
	 ProdProductPlace selectByPrimaryKey(Long PK);
	 
	 Place getToDestByProductId(long productId);
	 
	 Long selectDestByProductId(Long productId);
	 List<Place> getComPlaceByProductId(Long productId);
	 /**
	  * 根据产品获取标的目的地数据
	  * @param productId
	  * @return
	  */
	 List<ProdProductPlace> getProdProductPlaceListByProductId(Long productId);
	 /**
	  * 自动生成目的地信息
	  * @param productId
	  * @param fromPlaceId
	  * @param toPlaceId
	  */
	 void insertOrUpdateTrafficPlace(final Long productId,final Long fromPlaceId,Long toPlaceId);

	Place getFromDestByProductId(long productId);

	List<Place> getNewComPlaceByProductId(Long productId);
	
}
