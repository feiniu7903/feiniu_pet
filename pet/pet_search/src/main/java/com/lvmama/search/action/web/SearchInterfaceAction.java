package com.lvmama.search.action.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.search.vo.AutoCompletePlaceObject;
import com.lvmama.comm.search.vo.HotelSearchVO;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.RouteSearchVO;
import com.lvmama.comm.search.vo.TicketSearchVO;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.service.HotelSearchService;
import com.lvmama.search.service.RouteSearchService;
import com.lvmama.search.service.TicketSearchService;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.JsonUtil;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SearchStringUtil;

public class SearchInterfaceAction extends BaseAction {

	private static final long serialVersionUID = -2161508894972378430L;
	
	private Log loger = LogFactory.getLog(this.getClass());
	
	
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public static String getSessionId(HttpSession session) {
		return session.getId();
	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	/** 关键字 */
	private String orign = "";
	
	/** 目的地1 */
	private String dest1 = "";
	
	/** 目的地2 */
	private String dest2 = "";
	
	private String stage="";
	
	@Resource
	private RouteSearchService groupRouteSearchService;
	@Resource
	private RouteSearchService freetourSearchService;
	@Resource
	private TicketSearchService ticketSearchService;
	@Resource
	private HotelSearchService hotelSearchService;
	
	
	@Action("getConsultRoute")
	public void getConsultRoute() throws IOException{
		
		List<ProductBean> returnList = new ArrayList<ProductBean>() ;
		int returnSize=3;
		int allSize=6;
		String groupdest="";
		String freetourdest="";
		//2个目的地都有是攻略
		if (StringUtils.isNotEmpty(orign)&& StringUtils.isNotEmpty(dest1)&& StringUtils.isNotEmpty(dest2)) {
			putLocalSession(dest1);
			putLocalSession(dest2);
			RouteSearchVO routeSearchVO = new RouteSearchVO(orign,dest1);
			groupdest=dest1;
			freetourdest=dest1;
			PageConfig<ProductBean> groupPgcfg = groupRouteSearchService.search(routeSearchVO);
			PageConfig<ProductBean> freetourPgcfg = freetourSearchService.search(routeSearchVO);
			//如果第一个目的地没有结果用第二个目的地
			if(groupPgcfg.getTotalResultSize()==0){
				routeSearchVO.setKeyword(dest2);
				groupdest=dest2;
				groupPgcfg = groupRouteSearchService.search(routeSearchVO);
			}
			if(freetourPgcfg.getTotalResultSize()==0){
				routeSearchVO.setKeyword(dest2);
				freetourdest=dest2;
				freetourPgcfg = freetourSearchService.search(routeSearchVO);
			}
			int aroundNum=Math.min(returnSize,groupPgcfg.getTotalResultSize());
			int freetourNum=Math.min(returnSize,freetourPgcfg.getTotalResultSize());
			//加入跟团游的数据
			for(int i=0;i<aroundNum;i++){
				ProductBean productBean=groupPgcfg.getAllItems().get(i);
				productBean.setToDest(groupdest);
				returnList.add(productBean);
			}
			for(int i=0;i<freetourNum;i++){
				ProductBean productBean=freetourPgcfg.getAllItems().get(i);
				productBean.setToDest(freetourdest);
				returnList.add(productBean);
			}
			
		}else if (StringUtils.isNotEmpty(orign)&& StringUtils.isNotEmpty(dest1)) {
			putLocalSession(dest1);
			RouteSearchVO routeSearchVO = new RouteSearchVO(orign,dest1);
			groupdest=dest1;
			freetourdest=dest1;
			PageConfig<ProductBean> groupPgcfg = groupRouteSearchService.search(routeSearchVO);
			PageConfig<ProductBean> freetourPgcfg = freetourSearchService.search(routeSearchVO);
			int aroundNum=Math.min(returnSize,groupPgcfg.getTotalResultSize());
			int freetourNum=Math.min(returnSize,freetourPgcfg.getTotalResultSize());
			//加入跟团游的数据
			for(int i=0;i<aroundNum;i++){
				ProductBean productBean=groupPgcfg.getAllItems().get(i);
				productBean.setToDest(groupdest);
				returnList.add(productBean);
			}
			for(int i=0;i<freetourNum;i++){
				ProductBean productBean=freetourPgcfg.getAllItems().get(i);
				productBean.setToDest(freetourdest);
				returnList.add(productBean);
			}
			
		}
			LocalSession.remove();
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			JsonUtil.outputJson(returnList, this.getResponse());

	}
	
	@Action("getConsultPlace")
	public void getConsultPlace() throws IOException{
		
		List<PlaceBean> returnList = new ArrayList<PlaceBean>() ;
		int ticketReturnSize=5;
		int hotelReturnSize=5;
		int allSize=6;
		String ticketdest="";
		String hoteldest="";
		//2个目的地都有是攻略
		if (StringUtils.isNotEmpty(orign)&& StringUtils.isNotEmpty(dest1)&& StringUtils.isNotEmpty(dest2)) {
			putLocalSession(dest1);
			putLocalSession(dest2);
			ticketdest=dest1;
			hoteldest=dest1;
			PageConfig<PlaceBean> hotelPgcfg = hotelSearchService.search(new HotelSearchVO(orign,dest1));
			TicketSearchVO tsv = new TicketSearchVO(orign, dest1);
			PageConfig<PlaceBean> ticketPgcfg = ticketSearchService.search(tsv);
			//如果第一个目的地没有结果用第二个目的地
			if(hotelPgcfg.getTotalResultSize()==0){
				hoteldest=dest2;
				hotelPgcfg = hotelSearchService.search(new HotelSearchVO(orign,dest2));
			}
			if(ticketPgcfg.getTotalResultSize()==0){
				ticketdest=dest2;
				tsv.setKeyword(dest2);
				ticketPgcfg = ticketSearchService.search(tsv);
			}
			int hotelNum=Math.min(hotelReturnSize,hotelPgcfg.getTotalResultSize());
			int ticketNum=Math.min(ticketReturnSize,ticketPgcfg.getTotalResultSize());
			//stage 2 是门票  3是酒店  然后空是全部吧
			if("3".equals(stage)||StringUtil.isEmptyString(stage)){
				
				for(int i=0;i<hotelNum;i++){
					PlaceBean place=hotelPgcfg.getAllItems().get(i);
					place.setDestTagsName(hoteldest);
					returnList.add(place);
				}
			}
			if("2".equals(stage)||StringUtil.isEmptyString(stage)){
			for(int i=0;i<ticketNum;i++){
				PlaceBean place=ticketPgcfg.getAllItems().get(i);
				place.setDestTagsName(ticketdest);
				returnList.add(place);
			}
			}
			
		}else if (StringUtils.isNotEmpty(orign)&& StringUtils.isNotEmpty(dest1)) {
			putLocalSession(dest1);
			ticketdest=dest1;
			hoteldest=dest1;
			PageConfig<PlaceBean> hotelPgcfg = hotelSearchService.search(new HotelSearchVO(orign,dest1));
			TicketSearchVO tsv = new TicketSearchVO(orign, dest1);
			PageConfig<PlaceBean> ticketPgcfg = ticketSearchService.search(tsv);
			RouteSearchVO routeSearchVO = new RouteSearchVO(orign,dest1);
			int hotelNum=Math.min(hotelReturnSize,hotelPgcfg.getTotalResultSize());
			int ticketNum=Math.min(ticketReturnSize,ticketPgcfg.getTotalResultSize());
			if("3".equals(stage)||StringUtil.isEmptyString(stage)){
				
				for(int i=0;i<hotelNum;i++){
					PlaceBean place=hotelPgcfg.getAllItems().get(i);
					place.setDestTagsName(hoteldest);
					returnList.add(place);
				}
			}
			if("2".equals(stage)||StringUtil.isEmptyString(stage)){
			for(int i=0;i<ticketNum;i++){
				PlaceBean place=ticketPgcfg.getAllItems().get(i);
				place.setDestTagsName(ticketdest);
				returnList.add(place);
			}
			}
			
		}
		LocalSession.remove();
			this.getRequest().setCharacterEncoding("UTF-8");
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			JsonUtil.outputJson(returnList, this.getResponse());

	}

	

	public String getOrign() {
		return orign;
	}

	public void setOrign(String orign) {
		this.orign = orign;
	}

	public String getDest1() {
		return dest1;
	}

	public void setDest1(String dest1) {
		this.dest1 = dest1;
	}

	public String getDest2() {
		return dest2;
	}

	public void setDest2(String dest2) {
		this.dest2 = dest2;
	}
	
	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public static void putLocalSession (String keyword){
		if (StringUtils.isNotEmpty(keyword)) {
			//先对keyword进行拆分，查找是否其中有同义词存在，分别对拆分后的keyword进行同义词追加
			List ikKeywords =OneSearchAction.ikSegmenter(keyword);
			LocalSession.set(keyword, ikKeywords);
	}
	}

}
