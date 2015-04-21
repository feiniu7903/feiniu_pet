package com.lvmama.comm.search.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.search.vo.ProductBean;


/**
 * 手机端PRODUCT相关搜索
 * 
 * @author hz
 * 
 */
@RemoteService("clientProductService")
public interface ClientProductService {
	
	/**
	 * 线路产品统一搜索接口(带经纬度算差距) ：省市(则转换为找省市下的线路产品, 接受关键字/省市/主题/标签
	 * 
	 * @param fromDest =出发点ID&toDest=中文/简拼/全拼/主题/城市/景点&keyword2=&city=cityId||cityName&visitDay=&subject=&tag=&priceType=&sort=[up||dn||空（SEQ）]
	 *        &routeType=[freeness||destroute||around||abroad||all]&fromPage=isClient&page=&pagesize=
	 * @throws IOException
	 * @return JASON格式
	 */
	public Page<ProductBean> routeSearch(ClientRouteSearchVO searchVo) ;
	
	/**
	 * 搜索自动补全景点/城市/主题/标签/叙词/出境区域
	 */
	public List<AutoCompletePlaceObject> getAutoComplete(ClientRouteSearchVO searchVo) ;
	
	/**
	 * 新客户端线路搜索接口
	 * @param searchVo
	 * @return
	 */
	public Page<ProductBean> newRouteSearch(ClientRouteSearchVO searchVo) ;

}
