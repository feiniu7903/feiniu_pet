package com.lvmama.clutter.service;

import java.util.List;
import java.util.Map;

import com.lvmama.clutter.model.MobileGroupOn;
import com.lvmama.clutter.model.MobileProduct;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.model.MobileTimePrice;
import com.lvmama.clutter.model.MobileTrainSeatType;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.vo.visa.VisaVO;
/**
 * 
 * @author dengcheng
 *
 */
public interface IClientProductService {
	/**
	 * 获得产品信息
	 * @param params
	 * @return
	 */
	MobileProduct getProduct(Map<String,Object> params);
	/**
	 * 获得类别时间价格表
	 * @param params
	 * @return
	 */
	List<MobileTimePrice> timePrice(Map<String,Object> params);
	/**
	 * 获得团购产品
	 * @param param
	 * @return
	 */
	Map<String,Object>  getGroupOnList(Map<String,Object> param);
	
	/**
	 * 获得团购产品详情 
	 * @param param
	 * @return
	 */
	MobileGroupOn  getGroupOnDetail(Map<String,Object> param);
	/**
	 * 获得产品的类别产品
	 * @param param
	 * @return
	 */
	Map<String,Object> getBranches(Map<String,Object> param);
	/**
	 * 获得景点的相关自由行产品
	 * @param param
	 * @return
	 */
	List<MobileProductTitle> getPlaceRoutes(Map<String,Object> param);
	/**
	 * 下单强
	 * @param param
	 * @return
	 */
	Map<String,Object> getProductItems(Map<String,Object> param);
	
	/**
	 * 查询线路（自由行）详情 .
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	MobileProductRoute getRouteDetail(Map<String,Object> param) throws Exception;
	
	/**
	 * 查询线路（自由行）详情 wap专用  .
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	Map<String,Object> getRouteDetail4Wap(Map<String,Object> param) throws Exception;
	
	/**
	 * 线路行程列表 . 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<ViewJourney> getViewJourneyList(Map<String,Object> param) throws Exception;
	/**
	 * 检查库存
	 * @param param
	 * @return
	 */
	Map<String,Object> checkStock(Map<String,Object> param);
	
	/**
	 * 线路行程列表 . 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<VisaVO> getVisaList(Map<String,Object> param);
	
	/**
	 * 查询火车票类别。
	 * @param param
	 * @return
	 */
	List<MobileTrainSeatType> getTrainSeatTypes(Map<String,Object> param) ;
	
	/**
	 * 秒杀产品列表
	 * @param param
	 * @return
	 */
	Map<String,Object>  listSeckilles(Map<String,Object> param);
	
	/**
	 * 获得秒杀产品详情 
	 * @param param
	 * @return
	 */
	MobileGroupOn  detailSeckill(Map<String,Object> param);
	
	/**
	 * 获得秒杀产品状态
	 * @param param productId,brachId
	 * @return
	 */
	Map<String, Object>  getStatusById(Map<String,Object> param);
}
