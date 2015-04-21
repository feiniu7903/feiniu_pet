/**
 * 
 */
package com.lvmama.comm.bee.service.prod;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.LineInfo;
import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.bee.po.prod.LineStationStation;
import com.lvmama.comm.bee.po.prod.LineStopVersion;
import com.lvmama.comm.bee.po.prod.LineStops;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdTrainFetchInfo;
import com.lvmama.comm.bee.vo.train.TrainParamInfo;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.train.product.CityStationInfo;
import com.lvmama.comm.vo.train.product.LineStopsInfo;
import com.lvmama.comm.vo.train.product.LineStopsStationInfo;
import com.lvmama.comm.vo.train.product.Station2StationInfo;
import com.lvmama.comm.vo.train.product.StationInfo;

/**
 * @author yangbin
 *
 */
public interface ProdTrainService {

	LineStationStation getStationStationById(Long stationStationId);
	
	/**
	 * 读取LineStationStation之外另读取到达站与发车站的信息
	 * @param stationStationId
	 * @return
	 */
	LineStationStation getStationStationDetailById(Long stationStationId);
	
	/**
	 * 更新一个车次的信息
	 * @param lineInfo
	 * @param stopInfos
	 * @param specDate
	 * @param supplierId
	 * @param prodInsuranceId
	 * @return
	 */
	ResultHandleT<Long> updateTrainLineInfo(LineInfo lineInfo, Date specDate, final TrainParamInfo trainParamInfo);
	
	/**
	 * 更新一个站站信息
	 * @param ssi
	 * @param specDate
	 * @return
	 */
	Map<String,Object> updateStation2StationInfo(Station2StationInfo ssi, String requestDate);
	
	public com.lvmama.comm.bee.po.prod.LineInfo selectLineInfoByLineName(String lineName);
	
	/**
	 * 查询一个车次的所有站站信息
	 * @param lineInfoId
	 * @return
	 */
	List<LineStationStation> selectStationStationByLineInfo(final Long lineInfoId);
	
	
	/**
	 * 查询车站
	 * @param param
	 * @return
	 */
	List<LineStation> selectLineStationByParam(Map<String,Object> param);
	
	
	/**
	 * 查询一个
	 * @param lineInfoId
	 * @return
	 */
	List<LineStops> selectLineStopsByLineInfoId(final Long lineInfoId,Date visitTime);
	/**
	 * 按车次查询对应的所有的车站的车站信息
	 * @param lineInfoId
	 * @return
	 */
	List<LineStation> selectLineStationByLineInfoId(final Long lineInfoId);
	
	
	com.lvmama.comm.bee.po.prod.LineInfo getLineInfo(final Long lineInfoId);
	
	/**
	 * 按拼音取车站的名称
	 * @param pinyin
	 * @return
	 */
	LineStation getLineStationByStationPinyin(String pinyin);
	
	
	/**
	 * 添加记录
	 * @param info
	 */
	void addFetch(ProdTrainFetchInfo info);
	
	/**
	 * 读取待更新的列表
	 * @return
	 */
	List<ProdTrainFetchInfo> selectFetchInfoList();
	
	/**
	 * 更新状态
	 * @param info
	 */
	void updateFetchInfoStatus(ProdTrainFetchInfo info);
	
	Long selectCountFetchInfo(String fetchKey,Date visitTime);
	/**
	 * 根据车站名列表，取得车站信息列表
	 * @param stationNames
	 * @return
	 */
	Map<String, LineStation> getLineStationByNames(List<String> stationNames);
	/**
	 * 增加车次经停版本信息
	 * @param lineInfo
	 * @param requestDate
	 * @return
	 */
	public LineStopVersion addLineStopsVersion(LineInfo lineInfo, Date requestDate);

	/**
	 * 插入新版本的车次经停信息
	 * @param station
	 * @param lineStation
	 * @param lineInfo
	 * @param version
	 */
	public void addLineStopsInfo(LineStopsStationInfo station, LineStation lineStation, LineInfo lineInfo, LineStopVersion version);
	
	/**
	 * 创建一个火车票对应的类别的时间价格表
	 * @param branch
	 * @param visitTime
	 * @param price
	 */
	void createTimePrice(final ProdProductBranch branch, Date visitTime,long price);
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
	 * @param requestDate
	 * @param trainParamInfo
	 * @return
	 */
	List<Long> insertLineInfos(List<com.lvmama.comm.vo.train.product.LineInfo> lineInfos, String requestDate, TrainParamInfo trainParamInfo);
	/**
	 * @param lineStopsInfos
	 * @param requestDate
	 * @return
	 */
	boolean insertLineStopInfo(List<LineStopsInfo> lineStopsInfos, String requestDate);
	/**
	 * 根据车站名称获取车站信息
	 * @param depart_station
	 * @return
	 */
	LineStation getLineStationByName(String depart_station);
	/**
	 * 插入火车票价格信息
	 * @param station2StationInfos
	 * @param requestDate
	 * @return
	 */
	List<Map<String, Object>> insertTicketPriceInfos(List<Station2StationInfo> station2StationInfos, String requestDate);

	public abstract void initTrainCacheMap(boolean create);
	
	/**
	 * 查询所有车站
	 * @param param
	 * @return
	 */
	List<LineStation> selectLineStationAll(Map<String, Object> param);
	
	/**
	 * 查询车次 根据车站
	 * @param param
	 * @return
	 */
	List<LineInfo> selectCheZhan(Map<String, Object> param);
	
	/**
	 * 查询经停站 根据车站
	 * @param param
	 * @return
	 */
	List<LineStops> selectCheZhanStops(Map<String, Object> param);
	
	/**
	 * 查询所有车次
	 * @param param
	 * @return
	 */
	List<LineInfo> selectAllLineInfo(Map<String, Object> param);
	
	/**
	 * 查询车次 通过车次全名
	 * @param fullName
	 * @return
	 */
	LineInfo selectLineInfoByFullName(String fullName);
	
	/**
	 * 查询站站信息 
	 * @param param
	 * @return
	 */
	List<LineStationStation> selectLineStationStationByPinyinKey(
			Map<String, Object> param);
	
	/**
	 * 查询站站的经停站
	 * @param param
	 * @return
	 */
	List<LineStops> selectZhanZhanStops(Map<String, Object> param);
	
	/**
	 * 查询车次的经停站
	 * @param param
	 * @return
	 */
	List<LineStops> selectLineStopsCheci(Map<String, Object> param);
	
	/**
	 * 取30个经过车站的城市
	 * @param param
	 * @return
	 */
	List<LineStation> selectLineStationByChezhan(Map<String, Object> param);
}

