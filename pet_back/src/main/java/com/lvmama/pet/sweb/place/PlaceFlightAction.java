package com.lvmama.pet.sweb.place;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceAirline;
import com.lvmama.comm.pet.po.place.PlaceAirport;
import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.pet.po.place.PlacePlaneModel;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
/**
 * 航班管理
 *
 */
@Results( {
	@Result(name = "input", location = "/WEB-INF/pages/back/place/place_flight.jsp", type = "dispatcher"),
	@Result(name = "planFlightList", location = "/WEB-INF/pages/back/place/place_flight_list.jsp", type = "dispatcher")
	})
public class PlaceFlightAction extends BackBaseAction{
	private static final long serialVersionUID = -1933262201783089923L;
	
	private PlaceFlightService placeFlightService;
	
	public PlaceFlight placeFlight;
	
    private Long startPlaceId;

    private Long arrivePlaceId;
    
    private String flightNo;
    
    private String airlineId;
    
	private Long placeFlightId;
    
	public List<PlacePlaneModel> placePlaneModel=Collections.emptyList();
	
	public List<PlaceAirline> placeAirlineList=Collections.emptyList();
	
	public List<CodeItem> trafficeBranchList;
	
	private String search;
	
	private Long placeId;
	
	private String [] berthInfoOptions;
	
	private PlaceService placeService;
	
	@Action("/place/placeFlightList")
	public String placeAirlineList() throws Exception {
		Map<String,Object> param = initParam();
		pagination = initPage();
		param.put("startRows", pagination.getStartRows());
		param.put("endRows", pagination.getEndRows());
		pagination.setTotalResultSize(placeFlightService.countPlaceFlightListByParam(param));
	    List<PlaceFlight> list=placeFlightService.queryPlaceFlightListByParam(param);
		for(PlaceFlight ss:list){
			if(ss.getAirlineId()!=null){
				ss.setAirline(placeFlightService.queryPlaceAirline(ss.getAirlineId()));
			}
			if(ss.getStartPlaceId()!=null){
				ss.setStartPlace(placeFlightService.queryPlaceByPlaceId(ss.getStartPlaceId()));
			}
			if(ss.getArrivePlaceId()!=null){
				ss.setArrivePlace(placeFlightService.queryPlaceByPlaceId(ss.getArrivePlaceId()));
			}
		}
		pagination.setItems(list);
		pagination.buildUrl(getRequest());
		//航空信息
		placeAirlineList = placeFlightService.queryPlaceAirlineList();
		
		return "planFlightList";
	}
	
	@Action("/place/placeFlightAdd")
	public String placeAirlineAdd(){
		//航空信息
		placeAirlineList = placeFlightService.queryPlaceAirlineList();
		//机型信息
		placePlaneModel = placeFlightService.queryPlacePlaneModelList();
		
		trafficeBranchList=ProductUtil.getTrafficBranchList();
		
		return "input";
	}
	
	@Action("/place/placeFlightview")
	public String placeFlightview(){
		//航空信息
		placeAirlineList = placeFlightService.queryPlaceAirlineList();
		//机型信息
		placePlaneModel = placeFlightService.queryPlacePlaneModelList();
		
		trafficeBranchList=ProductUtil.getTrafficBranchList();
		if (null !=placeFlightId ) {
		  placeFlight=placeFlightService.queryPlaceFlightDetail(placeFlightId);
		  //处理舱位信息值
		  berthInfoOptions=placeFlight.getBerthInfoOptions();
		  }
		return "input";
	}
	
	@Action("/place/placeFlightSave")
	public void placeFlightSave(){
		JSONResult result = new JSONResult(getResponse());
		try {
			if(!ArrayUtils.isEmpty(berthInfoOptions)){
				placeFlight.setBerthInfo(merge(berthInfoOptions));
			}
			if (null == placeFlight.getPlaceFlightId()) {
				// 检测是否航班编号存在
				Long count = placeFlightService
						.countPlaceFlightByflightNo(placeFlight.getFlightNo());
				if (count > 0) {
					result.raise("航班编号已存在！").output();
					return;
				} else {
					placeFlightService.saveFlight(placeFlight);
				}
			} else {
				placeFlightService.updateByPrimaryKey(placeFlight);
			}
		} catch (Exception e) {
			result.raise(e);			
		}
		result.output(getResponse());
	}
	
	@Action("/place/queryPlaceByPlaceId")
	public void queryPlaceByPlaceId(){
		JSONResult result=new JSONResult(getResponse());
		if(placeId!=null){
		    Place place=placeFlightService.queryPlaceByPlaceId(placeId);
		    result.put("placeId",place.getPlaceId());
			result.put("cityName",place.getName());
		}
		result.output(getResponse());
	}
	
	@Action("/place/placeFligthBysearch")
	public void placeFligthBysearch(){
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(search)) {
			param.put("flightNo", search);
		}
		param.put("_startRow",0);
		param.put("_endRow", ""+10);
		List<PlaceFlight> list =placeFlightService.queryPlaceFlightListByParam(param);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(PlaceFlight ss:list){
				JSONObject obj=new JSONObject();
				obj.put("id",ss.getPlaceFlightId());
				obj.put("text", ss.getFlightNo());
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	/**
	 * 初始化查询参数
	 * @return
	 */
	private Map<String, Object> initParam() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(flightNo)) {
			param.put("flightNo", flightNo.trim());
		}
		if (StringUtils.isNotBlank(airlineId)) {
			param.put("airlineId", airlineId.trim());
		}
		if (null!=startPlaceId) {
			param.put("startPlaceId", startPlaceId);
		}
		if (null!=arrivePlaceId) {
			param.put("arrivePlaceId",arrivePlaceId);
		}
		return param;
	}

	protected String merge(String[] array){
		StringBuffer sb=new StringBuffer();		
		for(String a:array){
			sb.append(a);
			sb.append(",");
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	//同步航班信息
	@Action("/place/getFlightInfo")
	public void getFlightInfo(){
		JSONResult result=new JSONResult(getResponse());
		if(StringUtils.isNotEmpty(flightNo)) {
			try {
				Parser parser = new Parser();
				parser.setEncoding("GBK");
				parser.setURL("http://flight.qunar.com/status/fquery.jsp?flightCode=" + flightNo);
				boolean isDdomestic = true;

				// 组合过滤器，获取航班状态详细信息
				// 父类div
				NodeFilter pAttrF = new HasAttributeFilter("class", "search_result");
				NodeFilter parentF = new HasParentFilter(pAttrF);

				NodeFilter attrF = new HasAttributeFilter("class", "state_detail");
				NodeFilter andFilter = new AndFilter(parentF, attrF);

				NodeList nlist = parser.extractAllNodesThatMatch(andFilter);
				// 国内航班取不到信息,则查询国际航班
				if (nlist.size() <= 0) {
					parser.setURL("http://flight.qunar.com/status/international/fquery_list.jsp?flightCode=" + flightNo);
					nlist = parser.extractAllNodesThatMatch(andFilter);
					isDdomestic = false;
				}
				if(nlist.size() <= 0) {
					throw new Exception("无法获取该航班信息！");
				} else {
					Node stateNode = nlist.elementAt(0);// 一般只有一个状态信息栏
					// 循环子标签获取信息
					NodeList nChildren = stateNode.getChildren();
					int j = 1;
					for (int i = 0; i < nChildren.size(); i++) {
						Node node = nChildren.elementAt(i);
						if (node instanceof TextNode) {
						} else {
							// 信息栏头部信息,取得航空公司
							if (node.getText().equalsIgnoreCase("dt")) {
								String dtContent = node.toPlainTextString().trim();
								int beiginIndex = dtContent.indexOf("(") + 1;
								int endIndex = dtContent.lastIndexOf(")");
								result.put("airlineId", dtContent.substring(beiginIndex, endIndex));
							} else {
								// 取得机场信息,国内和国际展现方式不同,取的方式也不同
								NodeList nl = node.getChildren();
								if (j == 1) {
									String ddContent = nl.elementAt(1)
											.toPlainTextString().trim();
									String[] spanContent = ddContent.split("：");
									String[] stations = spanContent[1].split("—");
									String airportName = stations[0].trim();
									int Tindex = airportName.indexOf("T");
									String Tstr = "";
									//分离机场和航站楼
									if(Tindex > 0) {
										Tstr = airportName.substring(Tindex);
										airportName = airportName.substring(0,Tindex);
									}
									
									//根据机场名称查询机场id和所在城市名称
									PlaceAirport placeAirport = getStationAndPlaceInfo(airportName);
									if(placeAirport != null) {
										//没有目的地信息
										if(placeAirport.getPlaceId() == null) {
											if(placeAirport.getCityName() == null) {
												throw new Exception("无法获取该机场的出发城市信息！");
											} else {
												throw new Exception("数据库中无出发城市 "+placeAirport.getCityName()+" 的目的地信息,请先插入数据！");
											}
										} else {
											result.put("startAirportName", airportName);
											result.put("startAirportId", placeAirport.getPlaceAirportId());
											result.put("startPlaceName", placeAirport.getCityName());
											result.put("startPlaceId", placeAirport.getPlaceId());
										}
									}
									result.put("startTerminal", Tstr);
									
									airportName = stations[1].trim();
									Tindex = airportName.indexOf("T");
									//分离机场和航站楼
									Tstr = "";
									if(Tindex > 0) {
										Tstr = airportName.substring(Tindex);
										airportName = airportName.substring(0,Tindex);
									}
									
									placeAirport = getStationAndPlaceInfo(airportName);
									if(placeAirport != null) {
										//没有目的地信息
										if(placeAirport.getPlaceId() == null) {
											if(placeAirport.getCityName() == null) {
												throw new Exception("无法获取该机场的抵达城市信息！");
											} else {
												throw new Exception("数据库中无抵达城市 "+placeAirport.getCityName()+" 的目的地信息,请先插入数据！");
											}
										} else {
											result.put("arriveAirportName", airportName);
											result.put("arriveAirportId", placeAirport.getPlaceAirportId());
											result.put("arrivePlaceName", placeAirport.getCityName());
											result.put("arrivePlaceId", placeAirport.getPlaceId());
										}
									}
									result.put("arriveTerminal", Tstr);
								} else if (j == 2) {
									String ddContent = nl.elementAt(1)
											.toPlainTextString().trim();
									String[] spanContent = ddContent.split("：");
									result.put("airplaneId", spanContent[1]);

									if (isDdomestic) {
										ddContent = nl.elementAt(5).toPlainTextString()
												.trim();
									} else {
										ddContent = nl.elementAt(3).toPlainTextString()
												.trim();
									}
									spanContent = ddContent.split("：");
									String[] preTime = spanContent[1].split("-");
									result.put("startTime", preTime[0].trim());
									result.put("arriveTime", preTime[1].trim());
									if (!isDdomestic) {
										ddContent = nl.elementAt(5).toPlainTextString()
												.trim();
										spanContent = ddContent.split("：");
										String flightTime = spanContent[1];

										int index1 = flightTime.indexOf("小时");
										int index2 = flightTime.indexOf("分");
										int hour = Integer.parseInt(flightTime.substring(0, index1));
										//没有分钟
										if(index2 < 0) {
										} else {
											String minStr = flightTime.substring(index1 + 2, index2);
											//分钟大于30,默认为1小时
											if(Integer.parseInt(minStr) >= 30) {
												hour++;
											}
										}
										result.put("flightTime", hour);
										break;
									}
								} else {
									String ddContent = nl.elementAt(3)
											.toPlainTextString().trim();
									String[] spanContent = ddContent.split("：");
									String flightTime = spanContent[1];

									int index1 = flightTime.indexOf("小时");
									int index2 = flightTime.indexOf("分");
									int hour = Integer.parseInt(flightTime.substring(0, index1));
									//没有分钟
									if(index2 < 0) {
									} else {
										String minStr = flightTime.substring(index1 + 2, index2);
										//分钟大于30,默认为1小时
										if(Integer.parseInt(minStr) >= 30) {
											hour++;
										}
									}
									result.put("flightTime", hour);
								}
								j++;
							}
						}
					}
				}
			} catch (Exception e) {
				result.raise(e);
			}
		}
		result.output(getResponse());
	}
	
	//获取机场信息
	public PlaceAirport getStationAndPlaceInfo(String airportName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("airportName", airportName);
		List<PlaceAirport> airportList = placeFlightService.queryPlaceAirportListByParam(params);
		//该机场不存在,则从网上取得该机场所在的城市,并插入数据库
		if(airportList.size() < 1) {
			Map<String, String> res = getPlaceInfo(airportName);
			PlaceAirport airport = new PlaceAirport();
			Object cityName = res.get("cityName");
			if(cityName != null) {
				airport.setCityName(cityName.toString());
			}
			if(res.get("placeId") == null) {//只返回目的地名称,提示操作人先插入目的地信息
			} else {//根据返回的目的地,插入机场信息
				airport.setAirportCode(res.get("airportCode").toString());
				airport.setAirportName(airportName);
				airport.setPlaceId(Long.valueOf(res.get("placeId").toString()));
				Long id = placeFlightService.savePlaceAirport(airport);
				airport.setPlaceAirportId(id);
			}
			return airport;
		} else {
			return airportList.get(0);
		}
	}
	
	//根据机场名称从网上获取所在城市信息
	public Map<String, String> getPlaceInfo(String airportName) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(ResourceUtil.getResourceFile("/WEB-INF/classes/airport_name.txt"));
			while (scanner.hasNext()) {
				String line = scanner.next();
	            if (line.contains(airportName)) {
	            	airportName = line.substring(line.indexOf("=") + 1);
	            	break;
	            }
		    } 
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		params.put("txtname", airportName);
		String content = HttpsUtil.requestPostForm("http://www.zou114.com/sanzidaima/index.asp", params, "GBK", "GBK");
		Parser parser = new Parser();
		try {
			parser.setEncoding("GBK");
			parser.setInputHTML(content);
			
			NodeFilter attrFilter = new HasAttributeFilter("id", "table4");
			NodeFilter parentFilter = new HasParentFilter(attrFilter);
			NodeFilter tagNameFilter = new TagNameFilter("tr");
			NodeFilter filter = new AndFilter(parentFilter, tagNameFilter);
			NodeList nlist = parser.extractAllNodesThatMatch(filter);
			if(nlist.size() > 1) {
				Node node = nlist.elementAt(1);
				params.put("airportCode", node.getChildren().elementAt(1).toPlainTextString().replace("&nbsp;", "").trim());
				String cityName = node.getChildren().elementAt(3).toPlainTextString().replace("&nbsp;", "").trim();
				params.put("cityName", cityName);
				Place place = placeService.getPlaceByName(cityName,"Y");
				//数据库中没有该目的地信息,不保存目的地id
				if(place == null) {
				} else {
					params.put("placeId", place.getPlaceId().toString());
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return params;
	}
    
	public void setPlaceFlight(PlaceFlight placeFlight) {
		this.placeFlight = placeFlight;
	}
	
	public void setPlaceFlightService(PlaceFlightService placeFlightService) {
		this.placeFlightService = placeFlightService;
	}

	public Long getStartPlaceId() {
		return startPlaceId;
	}

	public void setStartPlaceId(Long startPlaceId) {
		this.startPlaceId = startPlaceId;
	}

	public Long getArrivePlaceId() {
		return arrivePlaceId;
	}

	public void setArrivePlaceId(Long arrivePlaceId) {
		this.arrivePlaceId = arrivePlaceId;
	}

	public Long getPlaceFlightId() {
		return placeFlightId;
	}

	public void setPlaceFlightId(Long placeFlightId) {
		this.placeFlightId = placeFlightId;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(String airlineId) {
		this.airlineId = airlineId;
	}

	public void setPlacePlaneModel(List<PlacePlaneModel> placePlaneModel) {
		this.placePlaneModel = placePlaneModel;
	}

	public void setPlaceAirlineList(List<PlaceAirline> placeAirlineList) {
		this.placeAirlineList = placeAirlineList;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String[] getBerthInfoOptions() {
		return berthInfoOptions;
	}

	public PlaceFlight getPlaceFlight() {
		return placeFlight;
	}

	public void setBerthInfoOptions(String[] berthInfoOptions) {
		this.berthInfoOptions = berthInfoOptions;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
}
