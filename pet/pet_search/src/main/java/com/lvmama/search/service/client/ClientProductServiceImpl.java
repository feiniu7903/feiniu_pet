package com.lvmama.search.service.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Query;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.service.ClientProductService;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.ClientRouteSearchVO;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.search.lucene.query.QueryUtilForClient;
import com.lvmama.search.lucene.service.autocomplete.AutoCompleteService;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.util.LoggerParms;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;
import com.lvmama.search.util.SearchStringUtil;

@HessianService("clientProductService")
@Service("clientProductService")
public class ClientProductServiceImpl implements ClientProductService {
	private Log loger = LogFactory.getLog(getClass());
	@Resource
	protected NewBaseSearchService newProductSearchService;
	
	@Resource
	protected AutoCompleteService autoCompleteService;

	/**
	 * 线路产品统一搜索接口(带经纬度算差距) ：省市(则转换为找省市下的线路产品, 接受关键字/省市/主题/标签
	 * 
	 * @param fromDest
	 *            =出发点ID&toDest=中文/简拼/全拼/主题/城市/景点&keyword2=&city=cityId||
	 *            cityName
	 *            &visitDay=&subject=&tag=&priceType=&sort=[up||dn||空（SEQ）]
	 *            &routeType
	 *            =[freeness||destroute||around||abroad||all]&fromPage=
	 *            isClient&page=&pagesize=
	 * @throws IOException
	 * @return JASON格式
	 */
	@Override
	public Page<ProductBean> routeSearch(ClientRouteSearchVO searchVo) {
		//loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + "线路搜索" + ";" + searchVo.getCityId() + ";" + searchVo.getFromDest() + ";" + searchVo.getFromDestId() + ";" + searchVo.getKeyword() + ";" + searchVo.getKeyword2() + ";" + searchVo.getPoductNameSearchKeywords() + ";"
			//	+ searchVo.getPriceType() + ";" + searchVo.getPriceType() + ";" + searchVo.getSort() + ";" + searchVo.getTag() + ";" + searchVo.getToDest() + ";" + searchVo.getVisitDay() + ";" + searchVo.getPage() + ";" + searchVo.getPageSize() + ";" + "TRUE" + ";" + "SEND!");
		List<ProductBean> allMatchList = new ArrayList<ProductBean>();
		List<ProductBean> list =this.searchRoute(searchVo);
		allMatchList.addAll(list);

		Page<ProductBean> pageConfigClient = new Page<ProductBean>(allMatchList.size(), searchVo.getPageSize(), searchVo.getPage());
		pageConfigClient.setAllItems(allMatchList);
		return pageConfigClient;
	}
	
	private List<ProductBean> searchRoute(ClientRouteSearchVO searchVo){
		List<ProductBean> matchList = new ArrayList<ProductBean>();
		try {
			Query q = QueryUtilForClient.getProductSearchAllQuery(searchVo);
			// 手机端排序统一参数priceAsc||priceDesc||空（SEQ）
				SORT[] sorts = null;
	
				if ("priceAsc".equals(searchVo.getSort())) {
					sorts = new SORT[]{SORT.priceUp};
				} else if ("priceDesc".equals(searchVo.getSort())) {
					sorts = new SORT[]{SORT.priceDown};
				}  else {
					sorts = new SORT[]{};
				}
			PageConfig<ProductBean> pageConfig = newProductSearchService.search(searchVo.getPageSize(), searchVo.getPage(), q, searchVo, sorts);
			/*
			 * 当对线路的搜索结果为空时，重新对产品的名称进行一次搜索，对关键字具有空格分词的功能
			 */
			if (pageConfig== null || pageConfig.getAllItems().size()==0) {
				if (StringUtils.isNotEmpty(searchVo.getToDest())) {
					String todest = searchVo.getToDest();
					searchVo.setPoductNameSearchKeywords(todest);
					q = QueryUtilForClient.getProductSearchAllQuery(searchVo);
					pageConfig = newProductSearchService.search(searchVo.getPageSize(), searchVo.getPage(), q, searchVo, sorts);
				}
			}
			if(pageConfig!=null){
				matchList = pageConfig.getAllItems();
			}
			debugPrint(matchList);
		} catch (Exception e) {
			System.out.println("线路搜索手机端接口出错！");
			e.printStackTrace();
		}
		return matchList;
	}

	private void debugPrint(List<ProductBean> matchList) {
		for (int i = 0; i < matchList.size(); i++) {
			loger.debug(i + "搜索结果：" + matchList.get(i).getProductName() + ":" + matchList.get(i).getFromDest() + ": " + matchList.get(i).getToDest() + ": " + matchList.get(i).getProductId() + ": " + matchList.get(i).getSellPrice() + "SEQ=" + matchList.get(i).getSeq() + ": 价格 = " + matchList.get(i).getSellPrice() + "               " + matchList.get(i).getVisitDay());
		}
	}

	
	/**
	 * 搜索自动补全景点/城市/主题/标签/叙词/出境区域
	 */
	@Override
	public List<AutoCompletePlaceObject> getAutoComplete(ClientRouteSearchVO searchVo) {
		List<AutoCompletePlaceObject> matchList = new ArrayList<AutoCompletePlaceObject>();
		String toDest = SearchStringUtil.treatKeyWord(searchVo.getToDest());
		if (StringUtils.isNotEmpty(toDest)) {
			loger.info("toDest: " + toDest);
			loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "自游自在" + ";" + searchVo.getProductType() + ";" + "" + ";" + toDest + searchVo.getToDest() + ";;;" + toDest + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + "" + ";" + "自动补全" + ";" + "SEND!");
			if(searchVo.getFromDest()!=null&&searchVo.getFromDest().contains(",")){
				String[] fromDestArray  = searchVo.getFromDest().split(",");
				for (String fromDest : fromDestArray) {
					searchVo.setFromDest(fromDest);
					List<AutoCompletePlaceObject> list = autoCompleteService.getAutoCompleteRouteObjectListMatched(SearchConstants.CLIENT_ROUTE_AUTOCOMPLETE,searchVo , 50);
					matchList.addAll(list);
				}
			} else {
				matchList = autoCompleteService.getAutoCompleteRouteObjectListMatched(SearchConstants.CLIENT_ROUTE_AUTOCOMPLETE,searchVo , 50);
			}
		}
		return matchList;
	}

	
	public Page<ProductBean> newRouteSearch(ClientRouteSearchVO searchVo)  {
		List<ProductBean> allMatchList = new ArrayList<ProductBean>();
		List<ProductBean> list =this.newSearchRoute(searchVo);
		allMatchList.addAll(list);

		Page<ProductBean> pageConfigClient = new Page<ProductBean>(allMatchList.size(), searchVo.getPageSize(), searchVo.getPage());
		pageConfigClient.setAllItems(allMatchList);
		return pageConfigClient;
	}
	
	private List<ProductBean> newSearchRoute(ClientRouteSearchVO searchVo){
		List<ProductBean> matchList = new ArrayList<ProductBean>();
		try {			Query q = QueryUtilForClient.getNewProductSearchForClient(searchVo);
			// 手机端排序统一参数priceAsc||priceDesc||空（SEQ）
			SORT[] sorts = null;
			
			if ("priceAsc".equals(searchVo.getSort())) {
				sorts = new SORT[]{SORT.priceUp};
			} else if ("priceDesc".equals(searchVo.getSort())) {
				sorts = new SORT[]{SORT.priceDown};
			}  else {
				sorts = new SORT[]{};
			}
			PageConfig<ProductBean> pageConfig = newProductSearchService.search(searchVo.getPageSize(), searchVo.getPage(), q, searchVo, sorts);
			matchList = pageConfig.getAllItems();
			/*
			 * 当对线路的搜索结果为空时，重新对产品的名称进行一次搜索，对关键字具有空格分词的功能
			 */
//			if (matchList.size() == 0) {
//				if (StringUtils.isNotEmpty(searchVo.getToDest())) {
//					String todest = searchVo.getToDest();
//					searchVo.setPoductNameSearchKeywords(todest);
//					q = QueryUtilForClient.getProductSearchAllQuery(searchVo);
//					matchList = newProductSearchService.search(q, sorts);
//				}
//			}
			debugPrint(matchList);
		} catch (Exception e) {
			System.out.println("线路搜索手机端接口出错！");
			e.printStackTrace();
		}
		return matchList;
	}
	

	protected HttpSession getSession() {
		if(ServletActionContext.getRequest()!=null){
			System.out.println("##### "+ServletActionContext.getRequest().getSession().getId());
			return ServletActionContext.getRequest().getSession();
		}
		return null;
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}


}
