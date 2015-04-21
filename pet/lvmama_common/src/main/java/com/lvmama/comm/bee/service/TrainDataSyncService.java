/**
 * 
 */
package com.lvmama.comm.bee.service;

import java.util.List;

import com.lvmama.comm.vo.train.product.CityStationInfo;
import com.lvmama.comm.vo.train.product.LineInfo;
import com.lvmama.comm.vo.train.product.LineStopsInfo;
import com.lvmama.comm.vo.train.product.Station2StationInfo;
import com.lvmama.comm.vo.train.product.StationInfo;


/**
 * 
 * @author yangbin
 *
 */
public interface TrainDataSyncService {
	/**
	 * 同步车站列表
	 * @param requestType all-获取全量数据；update-获取变化数据
	 * @param requestDate 查询数据的日期，例如2013-06-01
	 * @param stationName 车站名称
	 * @return
	 */
	boolean syncStationInfo(String requestType, String requestDate, String stationName);
	/**
	 * 同步城市车站对应关系
	 * @param requestType all-获取全量数据；update-获取变化数据
	 * @param requestDate 查询数据的日期，例如2013-06-01
	 * @param cityName 城市名称
	 * @return
	 */
	boolean syncCityStationInfo(String requestType, String requestDate, String cityName);
	/**
	 * 同步车次基本信息
	 * @param requestType all-获取全量数据；update-获取变化数据
	 * @param requestDate 查询数据的日期，例如2013-06-01
	 * @param trainId 车次Id
	 * @return
	 */
	boolean syncLineInfo(String requestType, String requestDate, String trainId);
	/**
	 * 同步车次经停信息
	 * @param requestType all-获取全量数据；update-获取变化数据
	 * @param requestDate 查询数据的日期，例如2013-06-01
	 * @param trainId 车次Id
	 * @return
	 */
	boolean syncLineStopsInfo(String requestType, String requestDate, String trainId);
	/**
	 * 同步票价信息
	 * @param requestType all-获取全量数据；update-获取变化数据
	 * @param requestDate 查询数据的日期，例如2013-06-01
	 * @param departSation 出发车站
	 * @param arriveStation 到达车站
	 * @param trainId 车次Id
	 * @return
	 */
	boolean syncTicketPriceInfo(String requestType, String requestDate, String departSation, String arriveStation, String trainId);
	/**
	 * 插入车站信息
	 * @param stationInfos
	 * @return
	 */
	boolean insertStationInfos(List<StationInfo> stationInfos);
	/**
	 * 插入城市车站信息
	 * @param cityStationInfos
	 * @return
	 */
	boolean insertCityStationInfos(List<CityStationInfo> cityStationInfos);
	/**
	 * 插入车次信息
	 * @param lineInfos
	 * @return
	 */
	boolean insertLineInfos(List<LineInfo> lineInfos, String requestDate);
	/**
	 * 插入车次经停信息
	 * @param lineStopsInfos
	 * @return
	 */
	boolean insertLineStopInfos(List<LineStopsInfo> lineStopsInfos, String requestDate);
	/**
	 * 插入火车票价格信息
	 * @param station2StationInfos
	 * @return
	 */
	boolean insertTicketPriceInfos(List<Station2StationInfo> station2StationInfos, String requestDate);
}
