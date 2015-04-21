package com.lvmama.train.service;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.train.product.CityStationInfo;
import com.lvmama.comm.vo.train.product.CityStationReqVo;
import com.lvmama.comm.vo.train.product.CityStationRspVo;
import com.lvmama.comm.vo.train.product.LineInfo;
import com.lvmama.comm.vo.train.product.LineReqVo;
import com.lvmama.comm.vo.train.product.LineRspVo;
import com.lvmama.comm.vo.train.product.LineStopsInfo;
import com.lvmama.comm.vo.train.product.LineStopsReqVo;
import com.lvmama.comm.vo.train.product.LineStopsRspVo;
import com.lvmama.comm.vo.train.product.Station2StationInfo;
import com.lvmama.comm.vo.train.product.Station2StationReqvo;
import com.lvmama.comm.vo.train.product.Station2StationRspVo;
import com.lvmama.comm.vo.train.product.StationInfo;
import com.lvmama.comm.vo.train.product.StationReqVo;
import com.lvmama.comm.vo.train.product.StationRspVo;
import com.lvmama.comm.vo.train.product.TicketBookInfo;
import com.lvmama.comm.vo.train.product.TicketBookReqVo;
import com.lvmama.comm.vo.train.product.TicketBookRspVo;
import com.lvmama.comm.vo.train.product.TicketInventoryReqVo;
import com.lvmama.comm.vo.train.product.TicketInventoryRspVo;
import com.lvmama.train.service.request.CityStationQueryRequest;
import com.lvmama.train.service.request.LineInfoQueryRequest;
import com.lvmama.train.service.request.LineStopsRequest;
import com.lvmama.train.service.request.Station2StationListRequest;
import com.lvmama.train.service.request.StationQueryRequest;
import com.lvmama.train.service.request.TicketBookInfoQueryRequest;
import com.lvmama.train.service.request.TicketInventoryRequest;
import com.lvmama.train.service.response.CityStationQueryResponse;
import com.lvmama.train.service.response.LineInfoQueryResponse;
import com.lvmama.train.service.response.LineStopsResponse;
import com.lvmama.train.service.response.Station2StationListResponse;
import com.lvmama.train.service.response.StationQueryResponse;
import com.lvmama.train.service.response.TicketBookInfoQueryResponse;
import com.lvmama.train.service.response.TicketInventoryResponse;

public class TrainProductTest {
	public static void main(String[] args) {
		TrainProductTest product = new TrainProductTest();
//		product.testStation();
//		product.testCityStation();
//		product.testLine();
//		product.testLineStops();
//		product.testTicketPrice();
//		product.testTicketBookQuery();
		product.testTicketInventory();
	}
	
	private void testStation(){
		StationReqVo vo = new StationReqVo();
		vo.setRequestType("all");
		vo.setRequestDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
//		vo.setStationName("上海");
		
		StationQueryRequest request = new StationQueryRequest(vo);
		StationQueryResponse response = new TrainClient().execute(request);
		if(response.isSuccess()){
			StationRspVo rspVo = (StationRspVo)response.getRsp().getVo();
			List<StationInfo> stationInfos = rspVo.getStationInfos();
			for(StationInfo stationInfo : stationInfos){
				System.out.println(stationInfo.toString());
			}
		}
	}
	
	private void testCityStation(){
		CityStationReqVo vo = new CityStationReqVo();
		vo.setRequestType("all");
		vo.setRequestDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
//		vo.setCityName("上海");
		
		CityStationQueryRequest request = new CityStationQueryRequest(vo);
		CityStationQueryResponse response = new TrainClient().execute(request);
		if(response.isSuccess()){
			CityStationRspVo rspVo = (CityStationRspVo)response.getRsp().getVo();
			List<CityStationInfo> cityStationInfos = rspVo.getCityStationInfos();
			for(CityStationInfo cityStationInfo : cityStationInfos){
				System.out.println(cityStationInfo.toString());
			}
		}
	}
	
	private void testLine(){
		LineReqVo vo = new LineReqVo();
		vo.setRequestType("all");
		vo.setRequestDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
//		vo.setRequestDate(DateUtil.formatDate(DateUtil.dsDay_Date(new Date(), -1), "yyyy-MM-dd"));
		
		LineInfoQueryRequest request = new LineInfoQueryRequest(vo);
		LineInfoQueryResponse response = new TrainClient().execute(request);
		if(response.isSuccess()){
			LineRspVo lvo = (LineRspVo)response.getRsp().getVo();
			List<LineInfo> list = lvo.getLineInfos();
			for(LineInfo line : list){
				System.out.println(line);
			}
		}
	}
	
	private void testLineStops(){
		LineStopsReqVo vo = new LineStopsReqVo();
		vo.setRequestType("all");
		vo.setRequestDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		vo.setTrainId("G4");
		
		LineStopsRequest request = new LineStopsRequest(vo);
		LineStopsResponse response = new TrainClient().execute(request);
		if(response.isSuccess()){
			LineStopsRspVo rspVo = (LineStopsRspVo)response.getRsp().getVo();
			List<LineStopsInfo> lineStopsInfos = rspVo.getLineStopsInfos();
			for(LineStopsInfo lineStopsInfo : lineStopsInfos){
				lineStopsInfo.toString();
			}
		}
	}
	
	private void testTicketPrice(){
		Station2StationReqvo vo = new Station2StationReqvo();
		vo.setRequestType("all");
		vo.setRequestDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		vo.setDepartStation("上海");
		vo.setArriveStation("北京");
		
		Station2StationListRequest request = new Station2StationListRequest(vo);
		Station2StationListResponse response = new TrainClient().execute(request);
		if(response.isSuccess()){
			Station2StationRspVo rspVo = (Station2StationRspVo)response.getRsp().getVo();
			List<Station2StationInfo> station2StationInfos = rspVo.getStation2StationInfos();
			for(Station2StationInfo station2StationInfo : station2StationInfos){
				System.out.println(station2StationInfo);
			}
		}
	}
	
	private void testTicketBookQuery(){
		TicketBookReqVo vo = new TicketBookReqVo();
		vo.setDepartStation("上海");
		vo.setArriveStation("北京");
		vo.setDepartDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		
		TicketBookInfoQueryRequest request = new TicketBookInfoQueryRequest(vo);
		TicketBookInfoQueryResponse response = new TrainClient().execute(request);
		if(response.isSuccess()){
			TicketBookRspVo rspVo = (TicketBookRspVo)response.getRsp().getVo();
			List<TicketBookInfo> ticketBookInfos = rspVo.getTicketBookInfos();
			for(TicketBookInfo ticketBookInfo : ticketBookInfos){
				System.out.println(ticketBookInfo);
			}
		}
	}
	
	private void testTicketInventory(){
		TicketInventoryReqVo vo = new TicketInventoryReqVo();
		vo.setDepartStation("南京");
		vo.setArriveStation("上海");
		vo.setDepartDate("2013-09-12");
		vo.setTrainId("G7059");
		vo.setTicketClass("203");
		
		TicketInventoryRequest request = new TicketInventoryRequest(vo);
		TicketInventoryResponse response = new TrainClient().execute(request);
		if(response.isSuccess()){
			TicketInventoryRspVo rspVo = (TicketInventoryRspVo)response.getRsp().getVo();
			System.out.println(rspVo);
		}
	}
}
