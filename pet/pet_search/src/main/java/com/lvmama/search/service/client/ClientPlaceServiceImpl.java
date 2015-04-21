package com.lvmama.search.service.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.service.ClientPlaceService;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.CitySubject;
import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.TreeBean;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.lucene.dao.impl.AutoCompleteDao;
import com.lvmama.search.lucene.query.QueryUtil;
import com.lvmama.search.lucene.query.QueryUtilForClient;
import com.lvmama.search.lucene.service.autocomplete.AutoCompleteService;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.service.client.utils.ClientKeywordUtils;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;

@HessianService("clientPlaceService")
@Service("clientPlaceService")
public class ClientPlaceServiceImpl implements ClientPlaceService {
	private Log loger = LogFactory.getLog(getClass());

	@Resource
	protected NewBaseSearchService newPlaceSearchService;
	@Resource
	protected AutoCompleteService autoCompleteService;
	@Resource
	private AutoCompleteDao autoCompleteDao;
	@Override
	public Page<PlaceBean> placeSearch(ClientPlaceSearchVO searchVo) {		
		//loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + "统一搜索" + ";" + searchVo.getStage() + ";" + searchVo.getKeyword() + searchVo.getCityName() + ";" + searchVo.getCityId() + ";" + searchVo.getX() + ";" + searchVo.getY() + ";" + searchVo.getPage() + ";" + searchVo.getPageSize() + ";"
			//	+ searchVo.getStar() + ";" + searchVo.getLatitude() + ";" + searchVo.getLongitude() + ";" + searchVo.getHotelType() + ";" + searchVo.getPriceRange() + ";" + searchVo.getProductType() + ";" + searchVo.getSpot() + ";" + searchVo.getWindage() + ";" + "TRUE" + ";" + "SEND!");
		ArrayList<PlaceBean> matchList = new ArrayList<PlaceBean>();
		Query q = new BooleanQuery();
		try {
			if (searchVo.getPlaceIds()!=null|| StringUtils.isNotEmpty(searchVo.getKeyword()) || StringUtils.isNotEmpty(searchVo.getCityId()) || StringUtils.isNotEmpty(searchVo.getCityName()) || StringUtils.isNotEmpty(searchVo.getSpotId()) || StringUtils.isNotEmpty(searchVo.getLatitude())) {
				// 对于spotId=景点ID|酒店ID,x=null,y=null的情况B,必须先把景点/酒店ID转换为坐标XY
				if (StringUtils.isNotEmpty(searchVo.getSpotId()) & StringUtils.isEmpty(searchVo.getLatitude()) & StringUtils.isEmpty(searchVo.getLongitude())) {
					@SuppressWarnings("unchecked")
					List<PlaceBean> placeList = newPlaceSearchService.search(10, QueryUtilForClient.getCityIdQuery(searchVo.getSpot()));
					searchVo.setLongitude(String.valueOf(placeList.get(0).getLongitude()));
					searchVo.setLatitude(String.valueOf(placeList.get(0).getLatitude()));
				}
				
				
				SORT[] ss = null;
				String sort = searchVo.getSort();
				if(StringUtil.isEmptyString(sort)){
					ss = new SORT[]{};
				}else if ("juli".equals(sort.toLowerCase())) {
					ss = new SORT[]{SORT.distance};
				} else if ("pricedesc".equals(sort.toLowerCase())) {// 价格降序
					ss = new SORT[]{SORT.priceDown};
				} else if ("priceasc".equals(sort.toLowerCase())) {// 价格升序

					ss = new SORT[]{SORT.priceUp};
				}  else if ("seq".equals(sort.toLowerCase())) {
					ss = new SORT[]{};
				} else if ("cmtavg".equals(sort.toLowerCase())) {// 点评数降序
					ss = new SORT[]{SORT.cmtNumDown};
				} else if ("cmtnum".equals(sort.toLowerCase())) {// 点评分数降序
					ss = new SORT[]{SORT.cmtNumUp};
				} else if ("salse".equals(sort.toLowerCase())) {// 一周销售额排序
					ss = new SORT[]{SORT.sales};
				}
				PageConfig<PlaceBean> placePageConfig = null;
				String transKeyWord="";
				List<String[]> keywordList=new ArrayList<String[]>();
				String transCode_keyword = searchVo.getKeyword();
				if(StringUtil.isNotEmptyString(transCode_keyword)){
					/**
					 * 处理过的关键字
					 */
					 transKeyWord = ClientKeywordUtils.transKeyWords(transCode_keyword);
					
					List<String> tmpkeywordList=(ArrayList<String>)LocalSession.get(transKeyWord);
					
					for (String keywords : tmpkeywordList) {
						keywordList.add(keywords.split(","));
					}
					
					
					Map<String, String> params = new HashMap<String, String>();
					if (StringUtils.isNotEmpty(searchVo.getKeyword())) {
						params.put("name", searchVo.getKeyword());
					}
					
					
					Query cityQuery = QueryUtil.getPlaceWithTicketQuery(params,"1",keywordList);
					
					List<PlaceBean> cityList = null;
					
					cityList = newPlaceSearchService.search(400, cityQuery);
					
					if (cityList.size() > 0) {
						List<String> cityIdlist = new ArrayList<String>();
						/*
						 * maxClauseCount不能大于1024个,设置最大城市为400个
						 */
						for (PlaceBean pb : cityList) {
							cityIdlist.add(pb.getId());
						}
						params.remove("name");
						//当在目的地 的时候就是城市搜索的时候，需要出来0的景点，所以用frompage 不为空的时候门票数量就会0-max
						//params.put("fromPage", "1");
						// 查询城市下的景点,按SEQ排序，拿出
						params.put("fromPage", "2");
						
						if (StringUtils.isNotEmpty(searchVo.getSubject())) {
							params.put("subject", searchVo.getSubject());
						}
						Query query_1 = QueryUtil.getPlaceWithTicketQueryForClient(params,"2",cityIdlist.toArray(new String[cityIdlist.size()]));
						placePageConfig = newPlaceSearchService.search(searchVo.getPageSize(), searchVo.getPage(), query_1, searchVo, ss);
			
						
					}
				}
				
				
				if(placePageConfig==null ||placePageConfig.getAllItems().size()==0){
					q = QueryUtilForClient.ClientPlaceSearchQuery(searchVo, keywordList,transKeyWord);
					placePageConfig = (PageConfig<PlaceBean>) newPlaceSearchService.search(searchVo.getPageSize(), searchVo.getPage(), q, searchVo, ss);
					//filterResultData(matchList);
					
					
				} 
				matchList = (ArrayList<PlaceBean>) placePageConfig.getAllItems();
				getDetaXY(matchList, searchVo.getLongitude(), searchVo.getLatitude());
				placeListSort(matchList, searchVo.getKeyword(), searchVo.getLongitude(), searchVo.getLatitude(), searchVo.getSort());
				debugPrint(matchList);
				/**
				 * 移除heap缓存
				 */
				LocalSession.remove();
			}
		
		} catch (Exception e) {
			System.out.println("手机端景点酒店统一搜索出错！");
			e.printStackTrace();
		}
		Page<PlaceBean> pageConfigClient = new Page<PlaceBean>(matchList.size(), searchVo.getPageSize(), searchVo.getPage());
		pageConfigClient.setAllItems(matchList);
		return pageConfigClient;
	}

	/**
	 * 查询下拉提示补全 ：城市+景点+酒店 , 接受 中文/简拼/全拼
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	@Override
	public List<AutoCompletePlaceObject> getAutoCompletePlace(ClientPlaceSearchVO searchVo) {
		//loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + "统一搜索" + ";" + searchVo.getStage() + ";" + searchVo.getKeyword() + searchVo.getCityName() + ";" + searchVo.getCityId() + ";" + searchVo.getX() + ";" + searchVo.getY() + ";" + searchVo.getPage() + ";" + searchVo.getPageSize() + ";"
			//	+ searchVo.getStar() + ";" + searchVo.getLatitude() + ";" + searchVo.getLongitude() + ";" + searchVo.getHotelType() + ";" + searchVo.getPriceRange() + ";" + searchVo.getProductType() + ";" + searchVo.getSpot() + ";" + searchVo.getWindage() + ";" + "TRUE" + ";" + "SEND!");
		List<AutoCompletePlaceObject> matchList = new ArrayList<AutoCompletePlaceObject>();
		try {
			if (StringUtils.isNotEmpty(searchVo.getKeyword())) {
				matchList = autoCompleteService.getAutoCompletePlaceObjectListMatched(SearchConstants.CLIENT_PLACE_AUTOCOMPLETE, searchVo, 100);
			}
			
		} catch (Exception e) {
			System.out.println("手机端提示补全查询出错！");
			e.printStackTrace();
		}
		return matchList;
	}

	/**
	 * 计算到中心位置XY的距离 , 单位为米,M
	 * 
	 * @author huangzhi
	 **/
	private void getDetaXY(List<PlaceBean> matchList, String x, String y) {
		if (StringUtils.isNotEmpty(x) && StringUtils.isNotEmpty(y) && !matchList.isEmpty()) {
			if (x.matches("[\\d]*[\\.]*[\\d]*") && y.matches("[\\d]*[\\.]*[\\d]*")) {// 正则法则判读是否为浮点数
				for (int i = 0; i < matchList.size(); i++) {
					double detaY = Math.abs(matchList.get(i).getLatitude() - Double.parseDouble(y)) * 100000;
					double detaX = Math.abs(matchList.get(i).getLongitude() - Double.parseDouble(x)) * 100000;
					int detaXY = (int) Math.sqrt(detaX * detaX + detaY * detaY);
					matchList.get(i).setBoost(detaXY);// Boost字段借用设距离数值。
				}
			}
		}
	}

	/**
	 * 排序:邻近距离由近到远||seq||伪字典顺序
	 * 
	 * @author huangzhi
	 **/
	private void placeListSort(List<PlaceBean> matchList, String keyword, String x, String y, String sort) {
		if (StringUtils.isNotEmpty(sort) && !matchList.isEmpty()) {
			if ("juli".equals(sort.toLowerCase())) {
				if (StringUtils.isNotEmpty(x) && StringUtils.isNotEmpty(y)) {
					Collections.sort(matchList, new PlaceBean.comparatorBoost());
				}
			}
		}
	}

	/**
	 * 构造jasonTree中国树 ,得到地标下有 景点|酒店|自由行线路|所有三种| 的省市树
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */

	@Override
	public TreeBean<PlaceBean> getChinaTreeByHasProduct(ClientPlaceSearchVO searchVo) {
		//loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + "城市树" + ";" + searchVo.getStage() + ";" + searchVo.getKeyword() + searchVo.getCityName() + ";" + searchVo.getCityId() + ";" + searchVo.getX() + ";" + searchVo.getY() + ";" + searchVo.getPage() + ";" + searchVo.getPageSize() + ";"
			//	+ searchVo.getStar() + ";" + searchVo.getLatitude() + ";" + searchVo.getLongitude() + ";" + searchVo.getHotelType() + ";" + searchVo.getPriceRange() + ";" + searchVo.getProductType() + ";" + searchVo.getSpot() + ";" + searchVo.getWindage() + ";" + "TRUE" + ";" + "SEND!");
		TreeBean<PlaceBean> chinaTree = new TreeBean<PlaceBean>();
		try {
			if (!searchVo.getProductType().isEmpty()) {
				Query q = QueryUtilForClient.getChinaTreeByHasProductQuery(searchVo);
				PageConfig<PlaceBean> placePageConfig = newPlaceSearchService.search(1000, 1, q);
				List<PlaceBean> matchList = placePageConfig.getItems();
				// 构造中国树

				getChinaTree(matchList, chinaTree);
				debugPrintChinaTree(matchList, chinaTree);

			}
		} catch (Exception e) {
			System.out.println("手机端提示补全查询出错！");
			e.printStackTrace();
		}
		return chinaTree;
	}

	/**
	 * 构造中国树
	 **/
	private void getChinaTree(List<PlaceBean> matchList, TreeBean<PlaceBean> chinaTree) {
		PlaceBean rootBean = new PlaceBean();
		rootBean.setName("中国");
		rootBean.setStage("0");
		chinaTree.setNode(rootBean);
		List<TreeBean> provinceList = new ArrayList<TreeBean>();
		// 遍历列表得到省|直辖市|特别行政区的节点
		for (int i = 0; i < matchList.size(); i++) {
			if (matchList.get(i).getPlaceType().equals("PROVINCE") || matchList.get(i).getPlaceType().equals("ZXS") || matchList.get(i).getPlaceType().equals("TBXZQ")) {
				TreeBean<PlaceBean> provinceTree = new TreeBean<PlaceBean>();
				provinceTree.setNode(matchList.get(i));
				PlaceBean placebean = matchList.get(i);
				if (!StringUtil.isEmptyString(matchList.get(i).getPinYin())) {
					provinceTree.setSort(matchList.get(i).getPinYin());
				} else {// 没有拼音则修正为Z排到最后
					provinceTree.setSort("z");
				}
				if (matchList.get(i).getPlaceType().equals("ZXS") || matchList.get(i).getPlaceType().equals("TBXZQ")) {
					TreeBean<PlaceBean> cityTree = new TreeBean<PlaceBean>();
					cityTree.setNode(matchList.get(i));
					if (!StringUtil.isEmptyString(matchList.get(i).getSeq())) {
						cityTree.setSort(matchList.get(i).getSeq());
					} else {
						cityTree.setSort("0");
					}
					List<TreeBean> cityList = new ArrayList<TreeBean>();
					cityList.add(cityTree);
					provinceTree.setSubNode(cityList);
				}
				provinceList.add(provinceTree);
			}
		}
		Collections.sort(provinceList, new TreeBean.comparatorChinaTree());
		// 遍历列表得到市的节点
		for (int i = 0; i < matchList.size(); i++) {
			if (matchList.get(i).getPlaceType().equals("CITY")) {
				PlaceBean placeBean = matchList.get(i);
				boolean addCity = addCityTree(provinceList, placeBean);
				if (!addCity) {// 对于象山这样的地标，升级为CITY，但是上级目的地也为CITY宁波，所以需要重新找宁波的上级目的地
					String destId = placeBean.getDestId();
					for (int j = 0; j < matchList.size(); j++) {
						if (matchList.get(j).getShortId().equals(destId)) {
							placeBean.setDestId(matchList.get(j).getDestId());
							break;
						}
					}
					addCityTree(provinceList, placeBean);
				}
			}
		}
		for (int i = 0; i < provinceList.size(); i++) {
			if (provinceList.get(i).getSubNode() != null) {
				Collections.sort(provinceList.get(i).getSubNode(), new TreeBean.comparatorChinaTree());
			}
		}
		chinaTree.setSubNode(provinceList);
	}

	/**
	 * 构造中国树时加入城市节点
	 **/
	private boolean addCityTree(List<TreeBean> provinceList, PlaceBean placeBean) {
		boolean addCity = false;
		for (int j = 0; j < provinceList.size(); j++) {
			PlaceBean provincebean = (PlaceBean) provinceList.get(j).getNode();
			if (provincebean.getShortId().equals(placeBean.getDestId())) {
				TreeBean<PlaceBean> cityTree = new TreeBean<PlaceBean>();
				cityTree.setNode(placeBean);
				if (!StringUtil.isEmptyString(placeBean.getSeq())) {
					cityTree.setSort(placeBean.getSeq());
				} else {
					cityTree.setSort("0");
				}
				if (provinceList.get(j).getSubNode() == null) {
					List<TreeBean> cityList = new ArrayList<TreeBean>();
					cityList.add(cityTree);
					provinceList.get(j).setSubNode(cityList);
					addCity = true;
				} else {
					provinceList.get(j).getSubNode().add(cityTree);
					addCity = true;
				}
			}
		}
		return addCity;
	}

	/**
	 * 打印中国树,调试用
	 **/
	private void debugPrintChinaTree(List<PlaceBean> matchList, TreeBean<PlaceBean> chinaTree) {
		Collections.sort(matchList, new PlaceBean.comparatorChinaTree());
		loger.debug(" ++中国 +++++++++++++++++++++++++++");
		int sum = 1;
		for (int i = 0; i < chinaTree.getSubNode().size(); i++) {
			PlaceBean provinceBean = (PlaceBean) chinaTree.getSubNode().get(i).getNode();
			loger.debug((sum++) + "    " + provinceBean.getName() + "  " + provinceBean.getDestId() + "  " + provinceBean.getPlaceType() + "  " + provinceBean.getShortId());
			List<TreeBean> cityList = chinaTree.getSubNode().get(i).getSubNode();
			if (cityList != null) {
				for (int j = 0; j < cityList.size(); j++) {
					PlaceBean cityBean = (PlaceBean) cityList.get(j).getNode();
					loger.debug((sum++) + "        " + cityBean.getName() + "  " + cityBean.getDestId() + "  " + cityBean.getPlaceType() + "  " + cityBean.getShortId());
				}
			}
		}
		loger.debug("=====省市总数：" + matchList.size());
		for (int i = 0; i < matchList.size(); i++) {
			loger.debug("中国树：" + matchList.get(i).getPlaceType() + ";" + matchList.get(i).getDestId() + ";" + matchList.get(i).getName() + ": " + matchList.get(i).getShortId() + ": ");
		}
	}

	private void debugPrint(List<PlaceBean> matchList) {
		for (int i = 0; i < matchList.size(); i++) {

			loger.debug(i + "搜索结果：" + matchList.get(i).getName() + ":" + matchList.get(i).getPinYin() + ": " + matchList.get(i).getStage() + ": " + matchList.get(i).getShortId() + ": " + matchList.get(i).getPlaceType() + "SEQ=" + matchList.get(i).getSeq() + ": 价格 = " + matchList.get(i).getSellPrice() + "               " + matchList.get(i).getBoost());
		}
	}

	/**
	 * 获得制定城市的景点主题/标红主题
	 * 
	 * @author huangzhi
	 * @throws IOException
	 * @return JASON格式
	 */
	@Override
	public List<AutoCompletePlaceObject> getClientSubjectByCity(ClientPlaceSearchVO searchVo) {
		//loger.info("SECH!;" + LoggerParms.getNowTime() + ";" + LoggerParms.getSessionId(getSession()) + ";" + LoggerParms.getIpAddr(getRequest()) + ";" + "手机端" + ";" + "城市主题" + ";" + searchVo.getStage() + ";" + searchVo.getKeyword() + searchVo.getCityName() + ";" + searchVo.getCityId() + ";" + searchVo.getX() + ";" + searchVo.getY() + ";" + searchVo.getPage() + ";" + searchVo.getPageSize() + ";"
			//	+ searchVo.getStar() + ";" + searchVo.getLatitude() + ";" + searchVo.getLongitude() + ";" + searchVo.getHotelType() + ";" + searchVo.getPriceRange() + ";" + searchVo.getProductType() + ";" + searchVo.getSpot() + ";" + searchVo.getWindage() + ";" + "TRUE" + ";" + "SEND!");
		List<AutoCompletePlaceObject> matchList = new ArrayList<AutoCompletePlaceObject>();
		try {

			if (StringUtils.isNotEmpty(searchVo.getKeyword())) {
				if (StringUtils.isNotEmpty(searchVo.getCityId())) {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.CLIENT_CHANNEL_SUBJECT, Long.parseLong(searchVo.getCityId()), searchVo.getKeyword(), 1000);
				} else {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListMatched(SearchConstants.CLIENT_CHANNEL_SUBJECT, 9999L, searchVo.getKeyword(), 1000);
				}
			} else {
				if (StringUtils.isNotEmpty(searchVo.getCityId())) {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListDefault(SearchConstants.CLIENT_CHANNEL_SUBJECT, Long.parseLong(searchVo.getCityId()), 1000);
				} else {
					matchList = autoCompleteService.getAutoCompletePlacePlaceObjectListDefault(SearchConstants.CLIENT_CHANNEL_SUBJECT, 9999L, 1000);
				}
			}
		} catch (Exception e) {
			System.out.println("手机端景点主题出错！");
			e.printStackTrace();
		}
		return matchList;
	}

//	protected HttpSession getSession() {
//		if(ServletActionContext.getRequest()!=null){
//			return ServletActionContext.getRequest().getSession();
//		}
//		return null;
//		
//	}
//	
	
	public Map<Long,List<String>> getCitiesSubjects(Map<String,Object> params) {
		Map<Long,List<String>> resultMap =  new HashMap<Long, List<String>>();
		
		Map<Long,Map<String,Object>> map  = new HashMap<Long, Map<String,Object>>();
		Long count = autoCompleteDao.countAllCityAllSubjects(params);
		Page<CitySubject> page = new Page<CitySubject>(count, 200, 1L);
		for (int j = 1; j <= page.getTotalPages(); j++) {
		Page<CitySubject> pageConfig = new Page<CitySubject>(count, 200, j);
		params.put("_startRow", pageConfig.getStartRows());
		params.put("_endRow", pageConfig.getEndRows());
		List<CitySubject> list = autoCompleteDao.selectAllCityAllSubjects(params);

		for (int i = 0; i < list.size(); i++) {
			CitySubject result = list.get(i);
			Long key = result.getCityId();
			String subject =  result.getSubject();
			Map<String,Object> subjects = map.get(key);
			if(subjects==null){
				subjects = new HashMap<String, Object>();
			}
			subjects.put(subject, subject);
			map.put(key, subjects);
		}
		
	 Iterator<Entry<Long, Map<String,Object>>>	it  =map.entrySet().iterator();
	 while(it.hasNext()) {
		 Entry<Long, Map<String,Object>> entry = it.next();
		 Long key = entry.getKey();
		 Map<String,Object> childMap = entry.getValue();
		 Iterator<Entry<String, Object>> cit = childMap.entrySet().iterator();
		 List<String> subjects = new ArrayList<String>();
		 while(cit.hasNext()){
			 subjects.add(cit.next().getKey());
		 }
		resultMap.put(key, subjects);
	 	}
		
		}
		return resultMap;
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
}
