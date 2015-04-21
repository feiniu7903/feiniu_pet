package com.lvmama.comm.search.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.TreeBean;


/**
 * 手机端PLACE相关搜索
 * 
 * @author hz
 * 
 */
@RemoteService("clientPlaceService")
public interface ClientPlaceService {
	
	/**
	 * 统一搜索+邻近搜索接口(带经纬度算差距) ：省市(则转换为找省市下的景点和酒店)+景点+酒店 . 接受 中文/简拼/全拼/城市编号/酒店星级/酒店价格范围
	 * 
	 * @param keyword
	 *            =&[cityId=||cityName=]&fromPage=isClient&sort=[juli||seq||
	 *            zidian||salse一周销售额
	 *            ]&priceRange=min,max&star=&stage=2||3&productType=[hasTicket||
	 *            noTicket||hasHotel||noHotel]&x=&y=
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	/**
	 * 根据经纬度度邻近搜索 :景点+酒店 ,分如下三种情况: A:
	 * cityId=城市ID,x=经度,y=纬度,windage=半径[米],productType=ALL|hasHotel|hasTicket B:
	 * cityId=景点ID|酒店ID,x=null,y=null,windage=半径[米],productType=ALL|hasHotel|
	 * hasTicket C:
	 * cityId=null,x=经度,y=纬度,windage=半径[米],productType=ALL|hasHotel|hasTicket
	 * http
	 * ://www.lvmama.com/search/clientSearch!nearSearch.do?cityId=城市ID&x=经度&y
	 * =纬度&windage=半径【米】
	 * &productType=ALL|hasHotel|hasTicket&fromPage=isClient&stage
	 * =2||3||空&sort=[juli||seq||zidian||priceAsc||priceDesc||空（SEQ）]
	 * &priceRange=min,max&star=&hotelType=[D度假酒店||P精品酒店||G高档酒店||K客栈||J经济酒店]
	 * &productType=[hasTicket||ALLTicket||hasHotel||ALLHotel]
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	public Page<PlaceBean> placeSearch(ClientPlaceSearchVO searchVo) ;
	
	/**
	 * 查询下拉提示补全 ：城市+景点+酒店 , 接受 中文/简拼/全拼
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	public List<AutoCompletePlaceObject> getAutoCompletePlace(ClientPlaceSearchVO searchVo);
	
	/**
	 * 获得制定城市的景点主题/标红主题
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	public List<AutoCompletePlaceObject> getClientSubjectByCity(ClientPlaceSearchVO searchVo);
	
	/**
	 * 构造jasonTree中国树 ,得到地标下有 景点|酒店|自由行线路|所有三种| 的省市树
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	public TreeBean<PlaceBean> getChinaTreeByHasProduct(ClientPlaceSearchVO searchVo);
	/**
	 * 活动所有城市的主题
	 * @param params
	 * @return
	 */
	public Map<Long,List<String>> getCitiesSubjects(Map<String,Object> params);

}
